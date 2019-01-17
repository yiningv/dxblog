package com.yiningv.dxblog.service;

import com.yiningv.dxblog.model.Article;

import java.util.List;

public interface ArticleService {
    void save(Article article);

    List<Article> findAll();
}
