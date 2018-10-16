package com.yupaits.auth.controller;


import com.yupaits.commons.exceptions.ServiceException;
import com.yupaits.commons.result.Result;
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
@RestController
@RequestMapping("/auth/role")
public class RoleController {

    @GetMapping("/page")
    public Result getRolePage() {
        throw new ServiceException("获取角色分页信息出错");
    }
}
