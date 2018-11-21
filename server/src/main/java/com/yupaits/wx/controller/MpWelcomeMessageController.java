package com.yupaits.wx.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yupaits.commons.result.Result;
import com.yupaits.commons.result.ResultCode;
import com.yupaits.commons.result.ResultWrapper;
import com.yupaits.wx.dto.MpWelcomeMessageCreate;
import com.yupaits.wx.dto.MpWelcomeMessageUpdate;
import com.yupaits.wx.entity.MpWelcomeMessage;
import com.yupaits.wx.service.IMpWelcomeMessageService;
import com.yupaits.wx.vo.MpWelcomeMessageVO;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * <p>
 * 公众号欢迎语 前端控制器
 * </p>
 *
 * @author yupaits
 * @since 2018-11-12
 */
@Slf4j
@Api(tags = "公众号欢迎语接口")
@RestController
@RequestMapping("/wx/mp-welcome-message")
public class MpWelcomeMessageController {

    private final IMpWelcomeMessageService mpWelcomeMessageService;

    @Autowired
    public MpWelcomeMessageController(IMpWelcomeMessageService mpWelcomeMessageService) {
        this.mpWelcomeMessageService = mpWelcomeMessageService;
    }

    @ApiOperation("创建公众号欢迎语")
    @PostMapping("")
    public Result addMpWelcomeMessage(@RequestBody MpWelcomeMessageCreate mpWelcomeMessageCreate) {
        if (!mpWelcomeMessageCreate.isValid()) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        MpWelcomeMessage mpWelcomeMessage = new MpWelcomeMessage();
        BeanUtils.copyProperties(mpWelcomeMessageCreate, mpWelcomeMessage);
        mpWelcomeMessage.setMessage(mpWelcomeMessageCreate.getMessage());
        return mpWelcomeMessageService.save(mpWelcomeMessage) ? ResultWrapper.success() : ResultWrapper.fail(ResultCode.CREATE_FAIL);
    }

    @ApiOperation("编辑公众号欢迎语")
    @PutMapping("/{id:\\d{19}}")
    public Result updateMpWelcomeMessage(@RequestBody MpWelcomeMessageUpdate mpWelcomeMessageUpdate) {
        if (!mpWelcomeMessageUpdate.isValid()) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        MpWelcomeMessage mpWelcomeMessage = new MpWelcomeMessage();
        BeanUtils.copyProperties(mpWelcomeMessageUpdate, mpWelcomeMessage);
        mpWelcomeMessage.setMessage(mpWelcomeMessageUpdate.getMessage());
        return mpWelcomeMessageService.updateById(mpWelcomeMessage) ? ResultWrapper.success() : ResultWrapper.fail(ResultCode.UPDATE_FAIL);
    }

    @ApiOperation("按条件获取公众号欢迎语列表")
    @PostMapping("/list")
    public Result getMpWelcomeMessageList(@RequestBody(required = false) Map<String, Object> query) {
        QueryWrapper<MpWelcomeMessage> queryWrapper = new QueryWrapper<>();
        if (MapUtils.isNotEmpty(query)) {
            query.forEach((key, value) -> {
                if (StringUtils.equals(key, "accountId")) {
                    queryWrapper.eq("account_id", value);
                }
            });
        }
        List<MpWelcomeMessageVO> mpWelcomeMessageVOList = mpWelcomeMessageService.list(queryWrapper).stream().map(mpWelcomeMessage -> {
            MpWelcomeMessageVO mpWelcomeMessageVO = new MpWelcomeMessageVO();
            BeanUtils.copyProperties(mpWelcomeMessage, mpWelcomeMessageVO);
            return mpWelcomeMessageVO;
        }).collect(Collectors.toList());
        return ResultWrapper.success(mpWelcomeMessageVOList);
    }

}