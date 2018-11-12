package com.yupaits.wx.controller;

import com.yupaits.wx.entity.MpWelcomeMessage;
import com.yupaits.wx.service.IMpWelcomeMessageService;
import com.yupaits.wx.vo.MpWelcomeMessageVO;
import com.yupaits.wx.dto.MpWelcomeMessageCreate;
import com.yupaits.wx.dto.MpWelcomeMessageUpdate;
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
        return mpWelcomeMessageService.updateById(mpWelcomeMessage) ? ResultWrapper.success() : ResultWrapper.fail(ResultCode.UPDATE_FAIL);
    }

    @ApiOperation("批量保存公众号欢迎语")
    @PutMapping("/batch-save")
    public Result batchSaveMpWelcomeMessage(@RequestBody List<MpWelcomeMessageUpdate> mpWelcomeMessageUpdateList) {
        if (!MpWelcomeMessageUpdate.isValid(mpWelcomeMessageUpdateList)) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        List<MpWelcomeMessage> mpWelcomeMessageList = mpWelcomeMessageUpdateList.stream().map(mpWelcomeMessageUpdate -> {
            MpWelcomeMessage mpWelcomeMessage = new MpWelcomeMessage();
            BeanUtils.copyProperties(mpWelcomeMessageUpdate, mpWelcomeMessage);
            return mpWelcomeMessage;
        }).collect(Collectors.toList());
        return mpWelcomeMessageService.saveOrUpdateBatch(mpWelcomeMessageList) ? ResultWrapper.success() : ResultWrapper.fail(ResultCode.SAVE_FAIL);
    }

    @ApiOperation("根据ID删除公众号欢迎语")
    @DeleteMapping("/{id:\\d{19}}")
    public Result deleteMpWelcomeMessage(@PathVariable Long id) {
        if (!ValidateUtils.idValid(id)) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        return mpWelcomeMessageService.removeById(id) ? ResultWrapper.success() : ResultWrapper.fail(ResultCode.DELETE_FAIL);
    }

    @ApiOperation("批量删除公众号欢迎语")
    @PutMapping("/batch-delete")
    public Result batchDeleteMpWelcomeMessage(@RequestBody List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        return mpWelcomeMessageService.removeByIds(ids) ? ResultWrapper.success() : ResultWrapper.fail(ResultCode.DELETE_FAIL);
    }

    @ApiOperation("根据ID获取公众号欢迎语信息")
    @GetMapping("/{id:\\d{19}}")
    public Result getMpWelcomeMessage(@PathVariable Long id) {
        if (!ValidateUtils.idValid(id)) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        MpWelcomeMessage mpWelcomeMessage = mpWelcomeMessageService.getById(id);
        if (mpWelcomeMessage == null) {
            return ResultWrapper.fail(ResultCode.DATA_NOT_FOUND);
        }
        MpWelcomeMessageVO mpWelcomeMessageVO = new MpWelcomeMessageVO();
        BeanUtils.copyProperties(mpWelcomeMessage, mpWelcomeMessageVO);
        return ResultWrapper.success(mpWelcomeMessageVO);
    }

    @ApiOperation("按条件获取公众号欢迎语列表")
    @PostMapping("/list")
    public Result getMpWelcomeMessageList(@RequestBody(required = false) Map<String, Object> query) {
        QueryWrapper<MpWelcomeMessage> queryWrapper = new QueryWrapper<>();
        if (MapUtils.isNotEmpty(query)) {
            query.forEach((key, value) -> {
                //TODO 设置查询条件
            });
        }
        List<MpWelcomeMessageVO> mpWelcomeMessageVOList = mpWelcomeMessageService.list(queryWrapper).stream().map(mpWelcomeMessage -> {
            MpWelcomeMessageVO mpWelcomeMessageVO = new MpWelcomeMessageVO();
            BeanUtils.copyProperties(mpWelcomeMessage, mpWelcomeMessageVO);
            return mpWelcomeMessageVO;
        }).collect(Collectors.toList());
        return ResultWrapper.success(mpWelcomeMessageVOList);
    }

    @ApiOperation("获取公众号欢迎语分页信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "当前页码", defaultValue = "1", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页数量", defaultValue = "10", dataType = "int", paramType = "query")})
    @PostMapping("/page")
    public Result getMpWelcomeMessagePage(@RequestParam(required = false, defaultValue = "1") int page,
                              @RequestParam(required = false, defaultValue = "10") int size,
                              @RequestParam(required = false) @ApiParam(value = "降序字段") List<String> descs,
                              @RequestParam(required = false) @ApiParam(value = "升序字段") List<String> ascs,
                              @RequestBody(required = false) Map<String, Object> query) {
        Page<MpWelcomeMessage> pager = new Page<>(page, size);
        if (CollectionUtils.isNotEmpty(descs)) {
            pager.setDescs(descs);
        }
        if (CollectionUtils.isNotEmpty(ascs)) {
            pager.setAscs(ascs);
        }
        QueryWrapper<MpWelcomeMessage> queryWrapper = new QueryWrapper<>();
        if (MapUtils.isNotEmpty(query)) {
            query.forEach((key, value) -> {
                //TODO 设置查询条件
            });
        }
        IPage<MpWelcomeMessageVO> mpWelcomeMessageVOPage = new Page<>();
        BeanUtils.copyProperties(mpWelcomeMessageService.page(pager, queryWrapper), mpWelcomeMessageVOPage);
        mpWelcomeMessageVOPage.setRecords(pager.getRecords().stream().map(mpWelcomeMessage -> {
            MpWelcomeMessageVO mpWelcomeMessageVO = new MpWelcomeMessageVO();
            BeanUtils.copyProperties(mpWelcomeMessage, mpWelcomeMessageVO);
            return mpWelcomeMessageVO;
        }).collect(Collectors.toList()));
        return ResultWrapper.success(mpWelcomeMessageVOPage);
    }

}