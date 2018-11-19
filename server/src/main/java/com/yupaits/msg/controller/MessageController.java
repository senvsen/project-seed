package com.yupaits.msg.controller;

import com.yupaits.commons.annotation.NoCache;
import com.yupaits.msg.dto.MessagePayload;
import com.yupaits.msg.entity.Message;
import com.yupaits.msg.service.IMessageService;
import com.yupaits.msg.service.IMessageUserService;
import com.yupaits.msg.vo.MessageVO;
import com.yupaits.commons.utils.ValidateUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
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
 * 消息 前端控制器
 * </p>
 *
 * @author yupaits
 * @since 2018-11-09
 */
@Slf4j
@NoCache
@Api(tags = "消息接口")
@RestController
@RequestMapping("/msg/message")
public class MessageController {

    private final IMessageService messageService;
    private final IMessageUserService messageUserService;

    @Autowired
    public MessageController(IMessageService messageService, IMessageUserService messageUserService) {
        this.messageService = messageService;
        this.messageUserService = messageUserService;
    }

    @ApiOperation("创建消息")
    @PostMapping("")
    public Result addMessage(@RequestBody MessagePayload messagePayload) {
        if (!messagePayload.isValid()) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        Message message = new Message();
        BeanUtils.copyProperties(messagePayload.getMessage(), message);
        message.setPayload(messagePayload.getMessage().getPayload());
        if (messageService.save(message)) {
            messagePayload.getRelatedId().getFirstId().setValue(message.getId());
            return messageUserService.batchSave(messagePayload.getRelatedId()) ?
                    ResultWrapper.success() : ResultWrapper.fail(ResultCode.SAVE_FAIL);
        }
        return ResultWrapper.fail(ResultCode.CREATE_FAIL);
    }

    @ApiOperation("根据ID删除消息")
    @DeleteMapping("/{id:\\d{19}}")
    public Result deleteMessage(@PathVariable Long id) {
        if (!ValidateUtils.idValid(id)) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        return messageService.removeById(id) ? ResultWrapper.success() : ResultWrapper.fail(ResultCode.DELETE_FAIL);
    }

    @ApiOperation("批量删除消息")
    @PutMapping("/batch-delete")
    public Result batchDeleteMessage(@RequestBody List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        return messageService.removeByIds(ids) ? ResultWrapper.success() : ResultWrapper.fail(ResultCode.DELETE_FAIL);
    }

    @ApiOperation("根据ID获取消息信息")
    @GetMapping("/{id:\\d{19}}")
    public Result getMessage(@PathVariable Long id) {
        if (!ValidateUtils.idValid(id)) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        Message message = messageService.getById(id);
        if (message == null) {
            return ResultWrapper.fail(ResultCode.DATA_NOT_FOUND);
        }
        MessageVO messageVO = new MessageVO();
        BeanUtils.copyProperties(message, messageVO);
        return ResultWrapper.success(messageVO);
    }

    @ApiOperation("按条件获取消息列表")
    @PostMapping("/list")
    public Result getMessageList(@RequestBody(required = false) Map<String, Object> query) {
        QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
        if (MapUtils.isNotEmpty(query)) {
            query.forEach((key, value) -> {
                //TODO 设置查询条件
            });
        }
        List<MessageVO> messageVOList = messageService.list(queryWrapper).stream().map(message -> {
            MessageVO messageVO = new MessageVO();
            BeanUtils.copyProperties(message, messageVO);
            return messageVO;
        }).collect(Collectors.toList());
        return ResultWrapper.success(messageVOList);
    }

    @ApiOperation("获取消息分页信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "当前页码", defaultValue = "1", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页数量", defaultValue = "10", dataType = "int", paramType = "query")})
    @PostMapping("/page")
    public Result getMessagePage(@RequestParam(required = false, defaultValue = "1") int page,
                              @RequestParam(required = false, defaultValue = "10") int size,
                              @RequestParam(required = false) @ApiParam(value = "降序字段") List<String> descs,
                              @RequestParam(required = false) @ApiParam(value = "升序字段") List<String> ascs,
                              @RequestBody(required = false) Map<String, Object> query) {
        Page<Message> pager = new Page<>(page, size);
        if (CollectionUtils.isNotEmpty(descs)) {
            pager.setDescs(descs);
        }
        if (CollectionUtils.isNotEmpty(ascs)) {
            pager.setAscs(ascs);
        }
        QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
        if (MapUtils.isNotEmpty(query)) {
            query.forEach((key, value) -> {
                //TODO 设置查询条件
            });
        }
        IPage<MessageVO> messageVOPage = new Page<>();
        BeanUtils.copyProperties(messageService.page(pager, queryWrapper), messageVOPage);
        messageVOPage.setRecords(pager.getRecords().stream().map(message -> {
            MessageVO messageVO = new MessageVO();
            BeanUtils.copyProperties(message, messageVO);
            return messageVO;
        }).collect(Collectors.toList()));
        return ResultWrapper.success(messageVOPage);
    }

}