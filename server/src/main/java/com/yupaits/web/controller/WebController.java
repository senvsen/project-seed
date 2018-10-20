package com.yupaits.web.controller;

import org.apache.commons.collections4.MapUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

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

    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> loginForm, Model model) {
        String username = MapUtils.getString(loginForm, "username");
        String password = MapUtils.getString(loginForm, "password");
        try {
            SecurityUtils.getSubject().login(new UsernamePasswordToken(username, password));
        } catch (AuthenticationException e) {
            return "/login";
        }
        return "redirect:/index";
    }
}
