package com.yiningv.dxblog.api.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.yiningv.dxblog.DxConst;
import com.yiningv.dxblog.api.service.GitHubService;
import com.yiningv.dxblog.api.service.GitHubWebhookService;
import com.yiningv.dxblog.model.Article;
import com.yiningv.dxblog.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@Slf4j
@Service
public class GitHubWebhookServiceImpl implements GitHubWebhookService {

    @Autowired
    private GitHubService gitHubService;
    @Autowired
    private ArticleRepository articleRepository;

    @Async
    @Override
    public void handlePushPayload(JsonNode payloadJson) {
        /**
         * 推送的内容里有文章、配置文件更新、命令（目前只支持同步）
         */
        try {
            JsonNode reposJson = payloadJson.get("repository");
            String reposId = reposJson.get("id").asText();
            String reposName = reposJson.get("full_name").asText();
            JsonNode headCommitJson = payloadJson.get("head_commit");

            // 按照删除、增加、修改的顺序处理数据
            Set<String> removedSet = Sets.newHashSet();
            JsonNode removedJson = headCommitJson.get("removed");
            for (int i = 0; i < removedJson.size(); i++) {
                String path = removedJson.get(i).asText();
                // 看不得不统一的后缀，即使大写后缀也不行

                if (path.startsWith(DxConst.ARTICLE_FOLDER) && path.endsWith(".md")) {
                    // 计算articleId
                    String pathMd5 = DigestUtils.md5Hex(path);

                    removedSet.add(String.format("%s:%s", reposId, pathMd5));
                }
            }
            articleRepository.deleteByArticleIdSet(removedSet);

            List<Article> articles = Lists.newArrayList();
            JsonNode addedJson = headCommitJson.get("added");
            for (int i = 0; i < addedJson.size(); i++) {
                String path = addedJson.get(i).asText();
                if (path.startsWith(DxConst.ARTICLE_FOLDER) && path.endsWith(".md")) {
                    // 计算articleId
                    String pathMd5 = DigestUtils.md5Hex(path);
                    String articleId = String.format("%s:%s", reposId, pathMd5);
                    String url = String.format("https://api.github.com/repos/%s/contents/%s?ref=master", reposName, path);
                    Optional<Article> articleContent = gitHubService.getArticleContent(url, reposId, reposName, articleId);
                    articleContent.ifPresent(it -> articles.add(it));
                }
            }
            articleRepository.saveAll(articles);

            JsonNode modifiedJson = headCommitJson.get("modified");

            // 处理命令
        } catch (Exception e) {
            log.error("handlePushPayload error.", e);
        }
    }

}
