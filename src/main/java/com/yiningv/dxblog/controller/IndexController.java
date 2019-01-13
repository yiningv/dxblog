package com.yiningv.dxblog.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class IndexController {

    @RequestMapping(path = {"", "/", "/index"})
    public String index(Model model) {
        List<String> demos = new ArrayList<>();
        demos.add("aaa");
        demos.add("bbb");
        demos.add("ccc");
        model.addAttribute("demos", demos);
        return "index";
    }
}
