package com.taotao.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/page")
public class PageController {

    @RequestMapping("/register")
    public String register() {
        return "register";
    }

    @RequestMapping("/login")
    public String login(String redirect, Model model) {
        model.addAttribute("redirect", redirect);
        return "login";
    }
    // @RequestMapping("/login")
    // public String login() {
    // return "login";
    // }

}
