package com.yupaits.web.controller;

import com.yupaits.commons.result.ModelWrapper;
import com.yupaits.commons.result.ResultCode;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author yupaits
 * @date 2018/10/16
 */
@Controller
public class WebController {

    @GetMapping({"/index", "/"})
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String loginPage(@RequestParam(required = false) String error, Model model) {
        if (error != null) {
            ModelWrapper.fail(model, ResultCode.LOGIN_FAIL);
        }
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
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        if (StringUtils.isAnyBlank(username, password)) {
            ModelWrapper.fail(model, ResultCode.PARAMS_ERROR);
        }
        try {
            SecurityUtils.getSubject().login(new UsernamePasswordToken(username, password));
            return "redirect:/index";
        } catch (AuthenticationException e) {
            return "redirect:/login?error";
        }
    }

    @GetMapping("/logout")
    public String logout() {
        SecurityUtils.getSubject().logout();
        return "redirect:/login";
    }
}
