package com.yiningv.dxblog.api.service.impl;

import com.google.common.collect.Lists;
import com.yiningv.dxblog.DxConst;
import com.yiningv.dxblog.api.service.GitHubService;
import com.yiningv.dxblog.model.Article;
import com.yiningv.dxblog.repository.ArticleRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GitHubServiceImplTest {

    @Autowired
    private GitHubService gitHubService;
    @Autowired
    private ArticleRepository articleRepository;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
        articleRepository.deleteAll();
    }

    @Test
    public void getArticlePathAndContent() {
        String reposId = "reposId";
        String reposName = "yiningv/blog";
        String url = String.format("https://api.github.com/repos/%s/contents/%s?ref=master", reposName, DxConst.ARTICLE_ROOT);
        Set<String> articlePathSet = gitHubService.getArticlePath(url);
        articlePathSet.forEach(System.out::println);
        List<String> articlePaths = Lists.newArrayList(articlePathSet);
        for (int i = 0; i < articlePaths.size(); i++) {
            String articleId = String.format("%d", i);
            String path = articlePaths.get(i);
            url = String.format("https://api.github.com/repos/%s/contents/%s?ref=master", reposName, path);
            Optional<Article> articleContent = gitHubService.getArticleContent(url, reposId, reposName, articleId, path);
            articleContent.ifPresent(System.out::println);
        }

    }
}