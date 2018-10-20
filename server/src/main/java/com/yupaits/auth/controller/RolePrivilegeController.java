package com.yupaits.auth.controller;

import com.yupaits.auth.entity.RolePrivilege;
import com.yupaits.auth.service.IRolePrivilegeService;
import com.yupaits.auth.vo.RolePrivilegeVO;
import com.yupaits.commons.core.identity.ForeignId;;
import com.yupaits.commons.core.identity.RelatedId;;
import com.google.common.collect.Lists;
import com.yupaits.commons.result.Result;
import com.yupaits.commons.result.ResultWrapper;
import com.yupaits.commons.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.*;

import java.util.List;
import java.util.stream.Collectors;


/**
 * <p>
 * 角色-权限 前端控制器
 * </p>
 *
 * @author yupaits
 * @since 2018-10-20
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
        } else {
            rolePrivilegeService.remove(new QueryWrapper<RolePrivilege>()
                    .eq(relatedId.getFirstId().getFieldName(), relatedId.getFirstId().getValue())
                    .notIn(relatedId.getSecondIds().getFieldName(), relatedId.getSecondIds().getValues()));
            List<RolePrivilege> addRolePrivilegeList = Lists.newArrayList();
            relatedId.getSecondIds().getValues().forEach(secondId -> {
                RolePrivilege rolePrivilege = rolePrivilegeService.getOne(new QueryWrapper<RolePrivilege>()
                        .eq("deleted", false)
                        .eq(relatedId.getFirstId().getFieldName(), relatedId.getFirstId().getValue())
                        .eq(relatedId.getSecondIds().getFieldName(), secondId));
                if (rolePrivilege == null) {
                    rolePrivilege = new RolePrivilege();
                    try {
                        RolePrivilege.class.getDeclaredField(relatedId.getFirstId().getFieldName()).set(rolePrivilege, relatedId.getFirstId().getValue());
                        RolePrivilege.class.getDeclaredField(relatedId.getSecondIds().getFieldName()).set(rolePrivilege, secondId);
                        addRolePrivilegeList.add(rolePrivilege);
                    } catch (IllegalAccessException | NoSuchFieldException e) {
                        log.warn("创建角色-权限关系出错, 参数: {}[{}], {}[{}]", relatedId.getFirstId().getFieldName(),
                                relatedId.getFirstId().getValue(), relatedId.getSecondIds().getFieldName(), secondId, e);
                    }
                }
            });
            rolePrivilegeService.saveBatch(addRolePrivilegeList);
        }
        return ResultWrapper.success();
    }

}