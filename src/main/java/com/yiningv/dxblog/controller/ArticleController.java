package com.yiningv.dxblog.controller;

import com.yiningv.dxblog.DX;
import com.yiningv.dxblog.model.Article;
import com.yiningv.dxblog.model.PageInfo;
import com.yiningv.dxblog.model.TagCount;
import com.yiningv.dxblog.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@Slf4j
public class ArticleController extends BaseController {

    @Autowired
    private ArticleService articleService;

    @RequestMapping(path = {"/", "/index"})
    public String index(
            @PageableDefault(sort = {"created"}, direction = Sort.Direction.DESC) Pageable pageable,
            Model model
    ) {
        Page<Article> articlePage = articleService.findAll(pageable);
        List<Article> articles = articlePage.getContent();
        model.addAttribute("posts", articles);
        PageInfo pageInfo = new PageInfo(pageable.getPageNumber(), articlePage.getTotalPages());
        model.addAttribute("pageInfo", pageInfo);
        metaInfo(DX.TITLE, DX.SUBTITLE, DX.DESCRIPTION, model);
        body("index", model);
        return "layout";
    }

    @RequestMapping(path = "/post/{articleId}")
    public String article(
            @PathVariable("articleId") String articleId,
            Model model) {
        Optional<Article> articleOptional = articleService.findById(articleId);
        Article article = articleOptional.get();
        metaInfo(DX.TITLE, DX.SUBTITLE, DX.DESCRIPTION, model);
        model.addAttribute("post", article);
        body("post", model);
        new ModelAndView().setStatus(HttpStatus.NOT_FOUND);
        return "layout";
    }

    @RequestMapping(path = "/archive", method = RequestMethod.GET)
    public String archive(Model model) {
        body("archive", model);
        return "layout";
    }

    @RequestMapping(path = "/tags", method = RequestMethod.GET)
    public String tags(Model model) {
        List<TagCount> tags = articleService.findAllTags();
        model.addAttribute("tags", tags);
        body("tags", model);
        return "layout";
    }

    @RequestMapping(path = "/tag/{tag}", method = RequestMethod.GET)
    public String tag(@PathVariable("tag") String tag, Model model) {
        List<Article> articles = articleService.findByTags(tag);
        model.addAttribute("posts", articles);
        body("index", model);
        return "layout";
    }

    @RequestMapping(path = "/about", method = RequestMethod.GET)
    public String about(Model model) {
        body("about", model);
        return "layout";
    }
}
