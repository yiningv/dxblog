package com.yiningv.dxblog.controller;

import com.yiningv.dxblog.DX;
import org.springframework.ui.Model;

public class BaseController {

    public void title(String title, Model model) {
        model.addAttribute("title", title);
    }

    public void subtitle(String subtitle, Model model) {
        model.addAttribute("subtitle", subtitle);
    }

    public void description(String description, Model model) {
        model.addAttribute("description", description);
    }

    public void author(String author, Model model) {
        model.addAttribute("author", author);
    }

    public void metaInfo(String title, String subtitle, String description, Model model) {
        model.addAttribute("title", title);
        model.addAttribute("subtitle", subtitle);
        model.addAttribute("description", description);
        model.addAttribute("author", DX.AUTHOR);
    }

    public void body(String body, Model model) {
        model.addAttribute("body", body);
    }
}
