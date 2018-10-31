package com.yupaits.auth.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yupaits.auth.entity.RolePrivilege;
import com.yupaits.auth.service.IRolePrivilegeService;
import com.yupaits.auth.vo.RolePrivilegeVO;
import com.yupaits.commons.core.identity.ForeignId;
import com.yupaits.commons.core.identity.RelatedId;
import com.yupaits.commons.result.Result;
import com.yupaits.commons.result.ResultCode;
import com.yupaits.commons.result.ResultWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

;
;


/**
 * <p>
 * 角色-权限 前端控制器
 * </p>
 *
 * @author yupaits
 * @since 2018-10-31
 */
@Slf4j
@Api(tags = "角色-权限接口")
@RestController
@RequestMapping("/auth/role-privilege")
public class RolePrivilegeController {

    private final IRolePrivilegeService rolePrivilegeService;

    @Autowired
    public RolePrivilegeController(IRolePrivilegeService rolePrivilegeService) {
        this.rolePrivilegeService = rolePrivilegeService;
    }

    @ApiOperation("按条件获取角色-权限关系列表")
    @PostMapping("/list")
    public Result getRolePrivilegeList(@RequestBody ForeignId foreignId) {
        if (!foreignId.isValid(RolePrivilege.class)) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        List<RolePrivilegeVO> rolePrivilegeVOList = rolePrivilegeService.list(new QueryWrapper<RolePrivilege>()
                .eq("deleted", false)
                .eq(foreignId.getFieldName(), foreignId.getValue()))
                .stream().map(rolePrivilege -> {
                    RolePrivilegeVO rolePrivilegeVO = new RolePrivilegeVO();
                    BeanUtils.copyProperties(rolePrivilege, rolePrivilegeVO);
                    return rolePrivilegeVO;
                }).collect(Collectors.toList());
        return ResultWrapper.success(rolePrivilegeVOList);
    }

    @ApiOperation("批量保存角色-权限关系")
    @PostMapping("/batch-save")
    public Result batchSave(@RequestBody RelatedId relatedId) {
        if (!relatedId.isValid(RolePrivilege.class)) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        if (CollectionUtils.isEmpty(relatedId.getSecondIds().getValues())) {
            rolePrivilegeService.remove(new QueryWrapper<RolePrivilege>()
                    .eq(relatedId.getFirstId().getFieldName(), relatedId.getFirstId().getValue()));
            return ResultWrapper.success();
        } else {
            return rolePrivilegeService.batchSave(relatedId) ? ResultWrapper.success() : ResultWrapper.fail(ResultCode.SAVE_FAIL);
        }
    }

}