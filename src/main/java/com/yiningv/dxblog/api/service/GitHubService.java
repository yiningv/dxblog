package com.yiningv.dxblog.api.service;

import com.yiningv.dxblog.model.Article;

import java.util.Optional;
import java.util.Set;

public interface GitHubService {

    Set<String> getArticlePath(String contentsUrl);

    Optional<Article> getArticleContent(String url, String reposId, String reposName, String articleId, String path);
}
