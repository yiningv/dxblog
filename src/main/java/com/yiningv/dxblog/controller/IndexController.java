package com.yiningv.dxblog.controller;

import com.google.common.collect.Sets;
import com.yiningv.dxblog.DxConst;
import com.yiningv.dxblog.model.Article;
import com.yiningv.dxblog.model.SiteSetting;
import com.yiningv.dxblog.service.ArticleService;
import com.yiningv.dxblog.util.MapCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@Slf4j
public class IndexController {

    @Autowired
    private ArticleService articleService;

    @RequestMapping(path = {"/"})
    public String index(Model model) {
        Article article = Article.builder()
                .id("sdaas")
                .reposId("12")
                .category("aaa")
                .title("test")
                .content("sasa")
                .tags(Sets.newHashSet("aa", "java"))
                .created(new Date())
                .build();
        articleService.save(article);

        List<Article> list = articleService.findAll();
        list.forEach(System.out::println);

        articleService.aaaa();

        Object o = MapCache.single().get(DxConst.SITE_SETTING);
        System.out.println(o);

        List<String> demos = new ArrayList<>();
        for (int i = 0; i < 10 ; i++) {
            demos.add(String.format("demo%d%d%d", i, i, i));
        }
        model.addAttribute("demos", demos);
        model.addAttribute("body", "index");
        return "layout";
    }

    public String index(
            @PageableDefault(sort = {"created"}, direction = Sort.Direction.DESC) Pageable pageable,
            Model model
    ) {
        model.addAttribute("body", "index");
        return "layout";
    }
}
