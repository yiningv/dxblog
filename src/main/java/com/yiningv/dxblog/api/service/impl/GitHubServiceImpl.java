package com.yiningv.dxblog.api.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Splitter;
import com.google.common.collect.Sets;
import com.yiningv.dxblog.DxConst;
import com.yiningv.dxblog.api.service.GitHubService;
import com.yiningv.dxblog.model.Article;
import com.yiningv.dxblog.util.MarkdownUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class GitHubServiceImpl implements GitHubService {

    private String getBody(String url) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<String> stringHttpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, stringHttpEntity, String.class);
        if (responseEntity.getStatusCode().isError()) {
            log.error("rest exchange error.", responseEntity.getStatusCode());
            return null;
        }
        String body = responseEntity.getBody();

        return body;
    }

    private JsonNode getBodyAsJsonNode(String url) {
        String body = getBody(url);
        if (body == null) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode bodyJson = objectMapper.readTree(body);
            return bodyJson;
        } catch (Exception e) {
            log.error("parse content error.", e);
        }
        return null;
    }

    private String getContentFromJson(JsonNode bodyJson) {
        if (bodyJson == null) {
            return null;
        }
        String result = null;
        try {
            String content = bodyJson.get("content").asText();
            // 默认是base64, 不放心的话可以使用下面代码获取编码
//            String encoding = bodyJson.get("encoding").asText();
            byte[] bytes = Base64.decodeBase64(content);
            result = new String(bytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("parse content error.", e);
        }
        return result;
    }

    /**
     *
     * @param contentsUrl posts文件夹的url
     * @return 所有文件的路径
     */
    @Override
    public Set<String> getArticlePath(String contentsUrl) {
        Set<String> pathSet = Sets.newHashSet();
        JsonNode bodyJson = this.getBodyAsJsonNode(contentsUrl);
        for (int i = 0; i < bodyJson.size(); i++) {
            JsonNode infoNode = bodyJson.get(i);
            String type = infoNode.get("type").asText();
            if ("dir".equals(type)) {
                String url = infoNode.get("url").asText();
                pathSet.addAll(this.getArticlePath(url));
            }
            String path = infoNode.get("path").asText();
            if ("file".equals(type) && StringUtils.endsWith(path, ".md")) {
                pathSet.add(path);
            }
        }
        return pathSet;
    }

    @Override
    public Optional<Article> getArticleContent(String url, String reposId, String reposName, String articleId) {
        JsonNode bodyJson = this.getBodyAsJsonNode(url);
        String content = this.getContentFromJson(bodyJson);
        String sha = bodyJson.get("sha").asText();
        String htmlUrl = bodyJson.get("html_url").asText();
        if (StringUtils.isAnyBlank(content, sha)) {
            return Optional.empty();
        }
        String imgSourceUrl = String.format("https://raw.githubusercontent.com/%s/master", reposName);
        MarkdownUtils.ArticleMeta articleMeta = MarkdownUtils.renderToArticle(content, imgSourceUrl);
        String title = articleMeta.getTitle();
        String htmlContent = articleMeta.getContent();
        if (StringUtils.isAnyBlank(title, htmlContent)) {
            return Optional.empty();
        }

        String description = articleMeta.getDescription();
        String category = articleMeta.getCategory();
        category = StringUtils.isBlank(category)? DxConst.OTHER : category;
        String tagsStr = articleMeta.getTags();
        Set<String> tags;
        if (StringUtils.isNotBlank(tagsStr)) {
            Iterable<String> tagsIt = Splitter.on(',').trimResults().split(tagsStr);
            tags = Sets.newHashSet(tagsIt);
        } else {
            tags = Sets.newHashSet(DxConst.OTHER);
        }

        Date created;
        String dateStr = articleMeta.getDate();
        try {
            ZoneId zoneId = ZoneId.systemDefault();
            DateTimeFormatter ymd = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(dateStr, ymd);
            ZonedDateTime zonedDateTime = localDate.atStartOfDay(zoneId);
            created = Date.from(zonedDateTime.toInstant());
        } catch (Exception e1) {
            created = new Date();
        }

        Article article = Article.builder()
                .id(articleId)
                .reposId(reposId)
                .sha(sha)
                .htmlUrl(htmlUrl)
                .title(title)
                .description(description)
                .content(htmlContent)
                .category(category)
                .tags(tags)
                .created(created)
                .build();
        return Optional.ofNullable(article);
    }

}
