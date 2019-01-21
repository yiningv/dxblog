package com.yiningv.dxblog.controller;

import com.yiningv.dxblog.model.Article;
import com.yiningv.dxblog.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @RequestMapping(path = {"/", "/index", "/home"})
    public String index(
            @PageableDefault(sort = {"created"}, direction = Sort.Direction.DESC) Pageable pageable,
            Model model
    ) {
        Page<Article> articlePage = articleService.findAll(pageable);
        List<Article> articles = articlePage.getContent();
        model.addAttribute("posts", articles);
        model.addAttribute("currentPage", pageable.getPageNumber() + 1);
        model.addAttribute("pageSize", articlePage.getTotalPages());
        model.addAttribute("totalCount", articlePage.getTotalElements());
        model.addAttribute("body", "index");
        return "layout";
    }

    @RequestMapping(path = "/post/{articleId}")
    public String article(
            @PathVariable("articleId") String articleId,
            Model model) {
        Optional<Article> articleOptional = articleService.findById(articleId);
        model.addAttribute("post", articleOptional.get());
        model.addAttribute("body", "post");
        return "layout";
    }
}
