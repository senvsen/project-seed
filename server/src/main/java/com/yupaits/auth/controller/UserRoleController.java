package com.yupaits.auth.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.yupaits.auth.entity.UserRole;
import com.yupaits.auth.service.IUserRoleService;
import com.yupaits.auth.vo.UserRoleVO;
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
 * 用户-角色 前端控制器
 * </p>
 *
 * @author yupaits
 * @since 2018-10-20
 */
@Slf4j
@Api(tags = "用户-角色接口")
@RestController
@RequestMapping("/auth/user-role")
public class UserRoleController {

    private final IUserRoleService userRoleService;

    @Autowired
    public UserRoleController(IUserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @ApiOperation("按条件获取用户-角色关系列表")
    @PostMapping("/list")
    public Result getUserRoleList(@RequestBody ForeignId foreignId) {
        if (!foreignId.isValid(UserRole.class)) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        List<UserRoleVO> userRoleVOList = userRoleService.list(new QueryWrapper<UserRole>()
                .eq("deleted", false)
                .eq(foreignId.getFieldName(), foreignId.getValue()))
                .stream().map(userRole -> {
                    UserRoleVO userRoleVO = new UserRoleVO();
                    BeanUtils.copyProperties(userRole, userRoleVO);
                    return userRoleVO;
                }).collect(Collectors.toList());
        return ResultWrapper.success(userRoleVOList);
    }

    @ApiOperation("批量保存用户-角色关系")
    @PostMapping("/batch-save")
    public Result batchSave(@RequestBody RelatedId relatedId) {
        if (!relatedId.isValid(UserRole.class)) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        if (CollectionUtils.isEmpty(relatedId.getSecondIds().getValues())) {
            userRoleService.remove(new QueryWrapper<UserRole>()
                    .eq(relatedId.getFirstId().getFieldName(), relatedId.getFirstId().getValue()));
        } else {
            userRoleService.remove(new QueryWrapper<UserRole>()
                    .eq(relatedId.getFirstId().getFieldName(), relatedId.getFirstId().getValue())
                    .notIn(relatedId.getSecondIds().getFieldName(), relatedId.getSecondIds().getValues()));
            List<UserRole> addUserRoleList = Lists.newArrayList();
            relatedId.getSecondIds().getValues().forEach(secondId -> {
                UserRole userRole = userRoleService.getOne(new QueryWrapper<UserRole>()
                        .eq("deleted", false)
                        .eq(relatedId.getFirstId().getFieldName(), relatedId.getFirstId().getValue())
                        .eq(relatedId.getSecondIds().getFieldName(), secondId));
                if (userRole == null) {
                    userRole = new UserRole();
                    try {
                        UserRole.class.getDeclaredField(relatedId.getFirstId().getFieldName()).set(userRole, relatedId.getFirstId().getValue());
                        UserRole.class.getDeclaredField(relatedId.getSecondIds().getFieldName()).set(userRole, secondId);
                        addUserRoleList.add(userRole);
                    } catch (IllegalAccessException | NoSuchFieldException e) {
                        log.warn("创建用户-角色关系出错, 参数: {}[{}], {}[{}]", relatedId.getFirstId().getFieldName(),
                                relatedId.getFirstId().getValue(), relatedId.getSecondIds().getFieldName(), secondId, e);
                    }
                }
            });
            userRoleService.saveBatch(addUserRoleList);
        }
        return ResultWrapper.success();
    }

}