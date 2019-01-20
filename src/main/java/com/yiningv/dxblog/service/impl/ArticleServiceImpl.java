package com.yiningv.dxblog.service.impl;

import com.yiningv.dxblog.model.Article;
import com.yiningv.dxblog.repository.ArticleRepository;
import com.yiningv.dxblog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public void save(Article article) {
        articleRepository.save(article);
    }

    @Override
    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    @Override
    public void aaaa() {
        articleRepository.findAllTags();
    }
}
