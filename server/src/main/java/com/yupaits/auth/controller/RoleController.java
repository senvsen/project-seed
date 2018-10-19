package com.yupaits.auth.controller;

import com.yupaits.auth.entity.Role;
import com.yupaits.auth.service.IRoleService;
import com.yupaits.auth.dto.RoleCreate;
import com.yupaits.auth.dto.RoleUpdate;
import com.yupaits.commons.result.Result;
import com.yupaits.commons.result.ResultWrapper;
import com.yupaits.commons.result.ResultCode;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 角色 前端控制器
 * </p>
 *
 * @author yupaits
 * @since 2018-10-19
 */
@Api(tags = "角色接口")
@RestController
@RequestMapping("/auth/role")
public class RoleController {

    private final IRoleService roleService;

    @Autowired
    public RoleController(IRoleService roleService) {
        this.roleService = roleService;
    }

    @ApiOperation("创建角色")
    @PostMapping("")
    public Result addRole(@RequestBody RoleCreate roleCreate) {
        if (!roleCreate.isValid()) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        Role role = new Role();
        BeanUtils.copyProperties(roleCreate, role);
        return roleService.save(role) ? ResultWrapper.success() : ResultWrapper.fail(ResultCode.CREATE_FAIL);
    }

    @ApiOperation("编辑角色")
    @PostMapping("/{id:\\d{19}}")
    public Result updateRole(@RequestBody RoleUpdate roleUpdate) {
        if (!roleUpdate.isValid()) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        Role role = new Role();
        BeanUtils.copyProperties(roleUpdate, role);
        return roleService.updateById(role) ? ResultWrapper.success() : ResultWrapper.fail(ResultCode.UPDATE_FAIL);
    }

}