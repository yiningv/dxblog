package com.yiningv.dxblog.api.service;

import com.yiningv.dxblog.model.Article;

import java.util.Optional;

public interface GitHubService {

    Optional<Article> getArticleContent(String url, String reposId, String reposName, String articleId);
}
