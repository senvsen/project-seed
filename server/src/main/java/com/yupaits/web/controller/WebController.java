package com.yupaits.web.controller;

import com.yupaits.commons.result.Result;
import com.yupaits.commons.result.ResultCode;
import com.yupaits.commons.result.ResultWrapper;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

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
    public String loginPage() {
        if (SecurityUtils.getSubject().isAuthenticated()) {
            //已登录的用户打开登录页面时直接跳转至主页
            return "redirect:/index";
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
    @ResponseBody
    public Result login(@RequestBody Map<String, Object> loginForm) {
        if (SecurityUtils.getSubject().isAuthenticated()) {
            //已登录的用户再次执行登录操作时不做验证直接登录
            return ResultWrapper.success();
        }
        String username = MapUtils.getString(loginForm, "username");
        String password = MapUtils.getString(loginForm, "password");
        boolean rememberMe = MapUtils.getBooleanValue(loginForm, "rememberMe", false);
        if (StringUtils.isAnyBlank(username, password)) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        try {
            SecurityUtils.getSubject().login(new UsernamePasswordToken(username, password, rememberMe));
            return ResultWrapper.success();
        } catch (AuthenticationException e) {
            return ResultWrapper.fail(ResultCode.LOGIN_FAIL);
        }
    }
}
