package com.yiningv.dxblog.api.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;
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
    public void syncReposPosts(JsonNode payloadJson) {
        // 由于在没有接入webhook期间，文档结构会发生变化，
        // 所以干脆使用删除后再添加的方式同步，反正github有文章的备份
        JsonNode reposJson = payloadJson.get("repository");
        String reposId = reposJson.get("id").asText();
        String reposName = reposJson.get("full_name").asText();
        String url = String.format("https://api.github.com/repos/%s/contents/%s?ref=master", reposName, DxConst.ARTICLE_ROOT);
        Set<String> articlePathSet = gitHubService.getArticlePath(url);
        List<Article> articles = Lists.newArrayList();
        articlePathSet.forEach(path -> {
            Optional<Article> articleOptional = this.getArticle(reposId, reposName, path);
            articleOptional.ifPresent(it -> articles.add(it));
        });
        articleRepository.deleteByReposId(reposId);
        articleRepository.saveAll(articles);
    }

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
            List<Article> removedArticles = Lists.newArrayList();
            JsonNode removedJson = headCommitJson.get("removed");
            for (int i = 0; i < removedJson.size(); i++) {
                String path = removedJson.get(i).asText();
                // 看不得不统一的后缀，即使大写后缀也不行

                if (path.startsWith(DxConst.ARTICLE_ROOT.concat("/")) && path.endsWith(".md")) {
                    // 计算articleId
                    String pathMd5 = DigestUtils.md5Hex(path);
                    String id = formatArticleId(reposId, pathMd5);
                    Article article = Article.builder().id(id).build();
                    removedArticles.add(article);
                }
            }
            articleRepository.deleteAll(removedArticles);

            List<Article> articles = Lists.newArrayList();
            JsonNode addedJson = headCommitJson.get("added");
            List<Article> addedArticles = getUpdatedArticles(reposId, reposName, addedJson);
            articles.addAll(addedArticles);

            JsonNode modifiedJson = headCommitJson.get("modified");
            List<Article> updatedArticles = getUpdatedArticles(reposId, reposName, modifiedJson);
            articles.addAll(updatedArticles);

            articleRepository.saveAll(articles);

            // 处理命令
        } catch (Exception e) {
            log.error("handlePushPayload error.", e);
        }
    }

    private String formatArticleId(String reposId, String pathMd5) {
        return String.format("%s:%s", reposId, pathMd5);
    }

    private List<Article> getUpdatedArticles(String reposId, String reposName, JsonNode jsonNode) {
        List<Article> articles = Lists.newArrayList();
        for (int i = 0; i < jsonNode.size(); i++) {
            String path = jsonNode.get(i).asText();
            if (path.startsWith(DxConst.ARTICLE_ROOT.concat("/")) && path.endsWith(".md")) {
                Optional<Article> articleContent = this.getArticle(reposId, reposName, path);
                articleContent.ifPresent(it -> articles.add(it));
            }
        }
        return articles;
    }

    private Optional<Article> getArticle(String reposId, String reposName, String path) {
        // 计算articleId
        String pathMd5 = DigestUtils.md5Hex(path);
        String articleId = formatArticleId(reposId, pathMd5);
        String url = String.format("https://api.github.com/repos/%s/contents/%s?ref=master", reposName, path);
        Optional<Article> articleContent = gitHubService.getArticleContent(url, reposId, reposName, articleId, path);
        return articleContent;
    }

}
