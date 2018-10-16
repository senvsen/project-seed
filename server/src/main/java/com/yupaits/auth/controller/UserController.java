package com.yupaits.auth.controller;


import com.yupaits.commons.result.Result;
import com.yupaits.commons.result.ResultWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yupaits
 * @since 2018-10-16
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/auth/user")
public class UserController {

    @ApiOperation("获取用户分页信息")
    @GetMapping("/page")
    public Result getUserPage() {
        return ResultWrapper.success();
    }
}
