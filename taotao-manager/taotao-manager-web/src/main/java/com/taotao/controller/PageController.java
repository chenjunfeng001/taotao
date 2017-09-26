package com.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    @RequestMapping("/")
    public String showIndex() {

        return "index";
    }

    /** 展示页面 */
    @RequestMapping("/{page}")
    public String showPage(String page) {
        return page;
    }
}
