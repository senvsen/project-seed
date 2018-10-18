package com.yupaits.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author yupaits
 * @date 2018/10/16
 */
@Controller
public class WebController {

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/404")
    public String page404() {
        return "error/404";
    }

    @GetMapping("/403")
    public String page403() {
        return "error/403";
    }

    @GetMapping("/500")
    public String page500() {
        return "error/500";
    }
}
