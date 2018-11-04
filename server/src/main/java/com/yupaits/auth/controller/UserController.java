package com.yupaits.auth.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupaits.auth.dto.UserCreate;
import com.yupaits.auth.dto.UserUpdate;
import com.yupaits.auth.entity.User;
import com.yupaits.auth.service.IUserService;
import com.yupaits.auth.vo.UserVO;
import com.yupaits.commons.consts.SecurityConsts;
import com.yupaits.commons.result.Result;
import com.yupaits.commons.result.ResultCode;
import com.yupaits.commons.result.ResultWrapper;
import com.yupaits.commons.utils.ValidateUtils;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * <p>
 * 用户 前端控制器
 * </p>
 *
 * @author yupaits
 * @since 2018-10-31
 */
@Slf4j
@Api(tags = "用户接口")
@RestController
@RequestMapping("/auth/user")
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @ApiOperation("创建用户")
    @PostMapping("")
    public Result addUser(@RequestBody UserCreate userCreate) {
        if (!userCreate.isValid()) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        User user = new User();
        BeanUtils.copyProperties(userCreate, user);
        String password = initPassword(user);
        if (userService.save(user)) {
            //TODO 创建用户完成发送初始密码至用户邮箱或手机短信
            return ResultWrapper.success();
        }
        return ResultWrapper.fail(ResultCode.CREATE_FAIL);
    }

    /**
     * 初始化用户密码
     */
    private String initPassword(User user) {
        String randomPassword = RandomStringUtils.randomAscii(8);
        user.setPassword(new Sha256Hash(randomPassword,
                user.getUsername() + SecurityConsts.CREDENTIALS_SALT,
                SecurityConsts.ITERATIONS).toHex());
        return randomPassword;
    }

    @ApiOperation("编辑用户")
    @PutMapping("/{id:\\d{19}}")
    public Result updateUser(@RequestBody UserUpdate userUpdate) {
        if (!userUpdate.isValid()) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        User user = new User();
        BeanUtils.copyProperties(userUpdate, user);
        return userService.updateById(user) ? ResultWrapper.success() : ResultWrapper.fail(ResultCode.UPDATE_FAIL);
    }

    @ApiOperation("批量保存用户")
    @PutMapping("/batch-save")
    public Result batchSaveUser(@RequestBody List<UserUpdate> userUpdateList) {
        if (!UserUpdate.isValid(userUpdateList)) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        List<User> userList = userUpdateList.stream().map(userUpdate -> {
            User user = new User();
            BeanUtils.copyProperties(userUpdate, user);
            return user;
        }).collect(Collectors.toList());
        return userService.saveOrUpdateBatch(userList) ? ResultWrapper.success() : ResultWrapper.fail(ResultCode.SAVE_FAIL);
    }

    @ApiOperation("根据ID删除用户")
    @DeleteMapping("/{id:\\d{19}}")
    public Result deleteUser(@PathVariable Long id) {
        if (!ValidateUtils.idValid(id)) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        return userService.removeById(id) ? ResultWrapper.success() : ResultWrapper.fail(ResultCode.DELETE_FAIL);
    }

    @ApiOperation("批量删除用户")
    @PutMapping("/batch-delete")
    public Result batchDeleteUser(@RequestBody List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        return userService.removeByIds(ids) ? ResultWrapper.success() : ResultWrapper.fail(ResultCode.DELETE_FAIL);
    }

    @ApiOperation("根据ID获取用户信息")
    @GetMapping("/{id:\\d{19}}")
    public Result getUser(@PathVariable Long id) {
        if (!ValidateUtils.idValid(id)) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        User user = userService.getById(id);
        if (user == null) {
            return ResultWrapper.fail(ResultCode.DATA_NOT_FOUND);
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return ResultWrapper.success(userVO);
    }

    @ApiOperation("按条件获取用户列表")
    @PostMapping("/list")
    public Result getUserList(@RequestBody(required = false) Map<String, Object> query) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (MapUtils.isNotEmpty(query)) {
            query.forEach((key, value) -> {
                //TODO 设置查询条件
            });
        }
        List<UserVO> userVOList = userService.list(queryWrapper).stream().map(user -> {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            return userVO;
        }).collect(Collectors.toList());
        return ResultWrapper.success(userVOList);
    }

    @ApiOperation("获取用户分页信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "当前页码", defaultValue = "1", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页数量", defaultValue = "10", dataType = "int", paramType = "query")})
    @PostMapping("/page")
    public Result getUserPage(@RequestParam(required = false, defaultValue = "1") int page,
                              @RequestParam(required = false, defaultValue = "10") int size,
                              @RequestParam(required = false) @ApiParam(value = "降序字段") List<String> descs,
                              @RequestParam(required = false) @ApiParam(value = "升序字段") List<String> ascs,
                              @RequestBody(required = false) Map<String, Object> query) {
        Page<User> pager = new Page<>(page, size);
        if (CollectionUtils.isNotEmpty(descs)) {
            pager.setDescs(descs);
        }
        if (CollectionUtils.isNotEmpty(ascs)) {
            pager.setAscs(ascs);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (MapUtils.isNotEmpty(query)) {
            query.forEach((key, value) -> {
                //TODO 设置查询条件
                if (StringUtils.equals(key, "id") && StringUtils.isNotBlank(String.valueOf(value))) {
                    queryWrapper.eq("id", value);
                }
                if (StringUtils.equals(key, "keyword")) {
                    queryWrapper.and(i -> i.like("username", value).or().like("name", value).or()
                            .like("phone", value).or().like("email", value));
                }
                if (StringUtils.equals(key, "gender") && value != null) {
                    queryWrapper.eq("gender", value);
                }
                if (StringUtils.equals(key, "enabled") && value != null) {
                    queryWrapper.eq("enabled", value);
                }
            });
        }
        IPage<UserVO> userVOPage = new Page<>();
        BeanUtils.copyProperties(userService.page(pager, queryWrapper), userVOPage);
        userVOPage.setRecords(pager.getRecords().stream().map(user -> {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            return userVO;
        }).collect(Collectors.toList()));
        return ResultWrapper.success(userVOPage);
    }

}