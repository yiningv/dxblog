package com.yiningv.dxblog.service.impl;

import com.yiningv.dxblog.model.Article;
import com.yiningv.dxblog.model.TagCount;
import com.yiningv.dxblog.repository.ArticleRepository;
import com.yiningv.dxblog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public void save(Article article) {
        articleRepository.save(article);
    }

    @Override
    public Page<Article> findAll(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }

    @Override
    public Optional<Article> findById(String articleId) {
        return articleRepository.findById(articleId);
    }

    @Override
    public List<TagCount> findAllTags() {
        return articleRepository.findAllTags();
    }

    @Override
    public List<Article> findByTags(String tag) {
        return articleRepository.findByTagsOrderByCreatedDesc(tag);
    }

}
