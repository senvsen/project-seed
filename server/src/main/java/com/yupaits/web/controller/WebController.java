package com.yupaits.web.controller;

import com.google.common.collect.Maps;
import com.yupaits.commons.result.ModelWrapper;
import com.yupaits.commons.result.ResultCode;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

/**
 * @author yupaits
 * @date 2018/10/16
 */
@Controller
public class WebController {

    @Value("${restful.enabled:true}")
    private Boolean restful;

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping({"/home", "/"})
    public String home() {
        if (restful) {
            return "redirect:/index";
        }
        return "home";
    }

    @GetMapping("/login")
    public String loginPage() {
        if (SecurityUtils.getSubject().isAuthenticated()) {
            //已登录的用户打开登录页面时直接跳转至主页
            return "redirect:/home";
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
    public String login(@RequestParam String username, @RequestParam String password,
                        @RequestParam(required = false) Object rememberMe, RedirectAttributes model) {
        if (SecurityUtils.getSubject().isAuthenticated()) {
            //已登录的用户再次执行登录操作时不做验证直接登录
            return "redirect:/home";
        }
        ResultCode resultCode;
        if (StringUtils.isAnyBlank(username, password)) {
            resultCode = ResultCode.PARAMS_ERROR;
        } else {
            try {
                SecurityUtils.getSubject().login(new UsernamePasswordToken(username, password, rememberMe != null));
                return "redirect:/home";
            } catch (AuthenticationException e) {
                resultCode = ResultCode.LOGIN_FAIL;
            }
        }
        Map<String, String> data = Maps.newHashMap();
        data.put("username", username);
        data.put("password", password);
        ModelWrapper.fail(model, resultCode, data);
        return "redirect:/login?error";
    }
}
