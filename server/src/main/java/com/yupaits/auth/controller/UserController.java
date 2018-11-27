package com.yupaits.auth.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupaits.auth.dto.ModifyPasswordForm;
import com.yupaits.auth.dto.UserCreate;
import com.yupaits.auth.dto.UserUpdate;
import com.yupaits.auth.entity.User;
import com.yupaits.auth.entity.UserRole;
import com.yupaits.auth.service.IUserRoleService;
import com.yupaits.auth.service.IUserService;
import com.yupaits.auth.vo.UserVO;
import com.yupaits.commons.consts.SecurityConsts;
import com.yupaits.commons.consts.enums.MsgType;
import com.yupaits.commons.result.Result;
import com.yupaits.commons.result.ResultCode;
import com.yupaits.commons.result.ResultWrapper;
import com.yupaits.commons.utils.ValidateUtils;
import com.yupaits.msg.entity.Message;
import com.yupaits.msg.entity.MessageUser;
import com.yupaits.msg.service.IMessageService;
import com.yupaits.msg.service.IMessageUserService;
import com.yupaits.web.mq.MsgSender;
import com.yupaits.web.shiro.ShiroHelper;
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
    private final IUserRoleService userRoleService;
    private final IMessageService messageService;
    private final IMessageUserService messageUserService;
    private final MsgSender msgSender;

    private static final String SEND_PASSWORD_PATTERN = "【Seed】您的初始登录密码为：%s 。您可以在登录之后修改此密码。";
    private static final String SEND_PASSWORD_EMAIL_SUBJECT = "【Seed】初始登录密码";

    @Autowired
    public UserController(IUserService userService, IUserRoleService userRoleService, IMessageService messageService,
                          IMessageUserService messageUserService, MsgSender msgSender) {
        this.userService = userService;
        this.userRoleService = userRoleService;
        this.messageService = messageService;
        this.messageUserService = messageUserService;
        this.msgSender = msgSender;
    }

    @ApiOperation("创建用户")
    @PostMapping("")
    public Result addUser(@RequestBody UserCreate userCreate) {
        if (!userCreate.isValid()) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        if (StringUtils.isAllBlank(userCreate.getEmail(), userCreate.getPhone())) {
            return ResultWrapper.fail("未设置该用户的邮箱或手机，无法完成创建");
        }
        User user = new User();
        BeanUtils.copyProperties(userCreate, user);
        String password = initPassword(user);
        if (userService.save(user)) {
            Message message = new Message().setUseTemplate(false).setContent(String.format(SEND_PASSWORD_PATTERN, password));
            if (StringUtils.isNotBlank(user.getPhone())) {
                message.setMsgType(MsgType.SMS);
            } else if (StringUtils.isNotBlank(user.getEmail())) {
                message.setMsgType(MsgType.EMAIL);
                JSONObject payload = new JSONObject();
                payload.put("subject", SEND_PASSWORD_EMAIL_SUBJECT);
                message.setPayload(JSON.toJSONString(payload));
            }
            if (!messageService.save(message)) {
                userService.removeById(user.getId());
                return ResultWrapper.fail("保存发送密码消息失败");
            }
            MessageUser messageUser = new MessageUser().setMessageId(message.getId()).setUserId(user.getId()).setNeedRemove(true);
            if (messageUserService.save(messageUser)) {
                messageService.removeById(message.getId());
                userService.removeById(user.getId());
                return ResultWrapper.fail("保存发送密码消息和用户对应关系失败");
            }
            msgSender.send(messageUser);
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
        if (userService.removeById(id)) {
            userRoleService.remove(new QueryWrapper<UserRole>().eq("user_id", id));
            return ResultWrapper.success();
        }
        return ResultWrapper.fail(ResultCode.DELETE_FAIL);
    }

    @ApiOperation("批量删除用户")
    @PutMapping("/batch-delete")
    public Result batchDeleteUser(@RequestBody List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        if (userService.removeByIds(ids)) {
            userRoleService.remove(new QueryWrapper<UserRole>().in("user_id", ids));
            return ResultWrapper.success();
        }
        return ResultWrapper.fail(ResultCode.DELETE_FAIL);
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

    @ApiOperation("修改登录密码")
    @PutMapping("/modify-password")
    public Result modifyPassword(@RequestBody ModifyPasswordForm modifyPasswordForm) {
        if (!modifyPasswordForm.isValid()) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        if (!StringUtils.equals(modifyPasswordForm.getNewPassword(), modifyPasswordForm.getConfirmPassword())) {
            return ResultWrapper.fail("新密码与确认密码不匹配");
        }
        UserVO currentUser = ShiroHelper.principal();
        if (!ValidateUtils.idValid(currentUser.getId())) {
            return ResultWrapper.fail("请先登录再进行修改密码操作");
        }
        User user = userService.getById(currentUser.getId());
        if (user == null) {
            return ResultWrapper.fail("当前用户不存在，无法进行修改密码操作");
        }
        if (StringUtils.equals(modifyPasswordForm.getOldPassword(), modifyPasswordForm.getNewPassword())) {
            return ResultWrapper.success();
        }
        if (!StringUtils.equals(user.getPassword(), new Sha256Hash(modifyPasswordForm.getOldPassword(),
                currentUser.getUsername() + SecurityConsts.CREDENTIALS_SALT, SecurityConsts.ITERATIONS).toHex())) {
            return ResultWrapper.fail("原密码输入有误");
        }
        user.setPassword(new Sha256Hash(modifyPasswordForm.getNewPassword(),
                currentUser.getUsername() + SecurityConsts.CREDENTIALS_SALT, SecurityConsts.ITERATIONS).toHex());
        return userService.updateById(user) ? ResultWrapper.success() : ResultWrapper.fail("修改密码失败");
    }

    @ApiOperation("获取当前登录用户信息")
    @GetMapping("/current")
    public Result currentUser() {
        UserVO currentUser = ShiroHelper.principal();
        if (ValidateUtils.idValid(currentUser.getId())) {
            User user = userService.getById(currentUser.getId());
            BeanUtils.copyProperties(user, currentUser);
            return ResultWrapper.success(currentUser);
        }
        return ResultWrapper.fail("请先登录再进行获取用户信息的操作");
    }

}