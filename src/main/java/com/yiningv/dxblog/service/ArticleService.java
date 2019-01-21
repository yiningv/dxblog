package com.yiningv.dxblog.service;

import com.yiningv.dxblog.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ArticleService {
    void save(Article article);

    Page<Article> findAll(Pageable pageable);

    Optional<Article> findById(String articleId);
}
