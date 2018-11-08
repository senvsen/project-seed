package com.yupaits.msg.controller;

import com.yupaits.msg.entity.MessageUser;
import com.yupaits.msg.service.IMessageUserService;
import com.yupaits.msg.vo.MessageUserVO;
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
 * 消息-用户 前端控制器
 * </p>
 *
 * @author yupaits
 * @since 2018-11-08
 */
@Slf4j
@Api(tags = "消息-用户接口")
@RestController
@RequestMapping("/msg/message-user")
public class MessageUserController {

    private final IMessageUserService messageUserService;

    @Autowired
    public MessageUserController(IMessageUserService messageUserService) {
        this.messageUserService = messageUserService;
    }

    @ApiOperation("按条件获取消息-用户关系列表")
    @PostMapping("/list")
    public Result getMessageUserList(@RequestBody ForeignId<Long> foreignId) {
        if (!foreignId.isValid(MessageUser.class)) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        List<MessageUserVO> messageUserVOList = messageUserService.list(new QueryWrapper<MessageUser>()
                .eq("deleted", false)
                .eq(StringUtils.camelToUnderline(foreignId.getFieldName()), foreignId.getValue()))
                .stream().map(messageUser -> {
                    MessageUserVO messageUserVO = new MessageUserVO();
                    BeanUtils.copyProperties(messageUser, messageUserVO);
                    return messageUserVO;
                }).collect(Collectors.toList());
        return ResultWrapper.success(messageUserVOList);
    }

    @ApiOperation("批量保存消息-用户关系")
    @PostMapping("/batch-save")
    public Result batchSave(@RequestBody RelatedId<Long> relatedId) {
        if (!relatedId.isValid(MessageUser.class)) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        if (CollectionUtils.isEmpty(relatedId.getSecondIds().getValues())) {
            messageUserService.remove(new QueryWrapper<MessageUser>()
                    .eq(StringUtils.camelToUnderline(relatedId.getFirstId().getFieldName()), relatedId.getFirstId().getValue()));
            return ResultWrapper.success();
        } else {
            return messageUserService.batchSave(relatedId) ? ResultWrapper.success() : ResultWrapper.fail(ResultCode.SAVE_FAIL);
        }
    }

}