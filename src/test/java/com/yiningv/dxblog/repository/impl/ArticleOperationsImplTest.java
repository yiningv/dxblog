package com.yiningv.dxblog.repository.impl;

import com.google.common.collect.Lists;
import com.yiningv.dxblog.model.Article;
import com.yiningv.dxblog.model.TagCount;
import com.yiningv.dxblog.repository.ArticleRepository;
import com.yiningv.dxblog.testutil.MockBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleOperationsImplTest {

    private static final String reposId = "reposId";

    @Autowired
    private ArticleRepository articleRepository;

    @Before
    public void setUp() throws Exception {
        List<Article> articles = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            String index = String.format("%d", i);
            Article article = MockBuilder.mockArticle(index, reposId);
            articles.add(article);
        }
        articleRepository.saveAll(articles);
    }

    @After
    public void tearDown() throws Exception {
        articleRepository.deleteByReposId(reposId);
    }

    @Test
    public void findAllTags() {
        List<TagCount> allTags = articleRepository.findAllTags();
        allTags.forEach(System.out::println);
    }

    @Test
    public void deleteByReposId() {
        articleRepository.deleteByReposId(reposId);
        List<Article> articles = articleRepository.findAllByReposId(reposId);
        assertTrue("deleteByReposId删除失败", articles.isEmpty());
    }
}