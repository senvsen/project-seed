package com.yupaits.auth.controller;

import com.yupaits.auth.entity.UserRole;
import com.yupaits.auth.service.IUserRoleService;
import com.yupaits.auth.vo.UserRoleVO;
import com.yupaits.commons.core.identity.ForeignId;;
import com.yupaits.commons.core.identity.RelatedId;;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
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
 * 用户-角色 前端控制器
 * </p>
 *
 * @author yupaits
 * @since 2018-11-02
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
    public Result getUserRoleList(@RequestBody ForeignId<Long> foreignId) {
        if (!foreignId.isValid(UserRole.class)) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        List<UserRoleVO> userRoleVOList = userRoleService.list(new QueryWrapper<UserRole>()
                .eq("deleted", false)
                .eq(StringUtils.camelToUnderline(foreignId.getFieldName()), foreignId.getValue()))
                .stream().map(userRole -> {
                    UserRoleVO userRoleVO = new UserRoleVO();
                    BeanUtils.copyProperties(userRole, userRoleVO);
                    return userRoleVO;
                }).collect(Collectors.toList());
        return ResultWrapper.success(userRoleVOList);
    }

    @ApiOperation("批量保存用户-角色关系")
    @PostMapping("/batch-save")
    public Result batchSave(@RequestBody RelatedId<Long> relatedId) {
        if (!relatedId.isValid(UserRole.class)) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        if (CollectionUtils.isEmpty(relatedId.getSecondIds().getValues())) {
            userRoleService.remove(new QueryWrapper<UserRole>()
                    .eq(StringUtils.camelToUnderline(relatedId.getFirstId().getFieldName()), relatedId.getFirstId().getValue()));
            return ResultWrapper.success();
        } else {
            return userRoleService.batchSave(relatedId) ? ResultWrapper.success() : ResultWrapper.fail(ResultCode.SAVE_FAIL);
        }
    }

}