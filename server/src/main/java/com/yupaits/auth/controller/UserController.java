package com.yupaits.auth.controller;

import com.yupaits.auth.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户 前端控制器
 * </p>
 *
 * @author yupaits
 * @since 2018-10-18
 */
@RestController
@RequestMapping("/auth/user")
public class UserController {

    private final IUserService authService;

    @Autowired
    public UserController(IUserService authService) {
        this.authService = authService;
    }

}
