package com.yupaits.wx.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupaits.commons.result.Result;
import com.yupaits.commons.result.ResultCode;
import com.yupaits.commons.result.ResultWrapper;
import com.yupaits.commons.utils.ValidateUtils;
import com.yupaits.wx.dto.MpEventHandlerCreate;
import com.yupaits.wx.dto.MpEventHandlerUpdate;
import com.yupaits.wx.entity.MpEventHandler;
import com.yupaits.wx.helper.MpEventHandlerHelper;
import com.yupaits.wx.service.IMpEventHandlerService;
import com.yupaits.wx.vo.MpEventHandlerVO;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
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
 * 公众号事件处理 前端控制器
 * </p>
 *
 * @author yupaits
 * @since 2018-11-12
 */
@Slf4j
@Api(tags = "公众号事件处理接口")
@RestController
@RequestMapping("/wx/mp-event-handler")
public class MpEventHandlerController {

    private final IMpEventHandlerService mpEventHandlerService;
    private final MpEventHandlerHelper mpEventHandlerHelper;

    @Autowired
    public MpEventHandlerController(IMpEventHandlerService mpEventHandlerService, MpEventHandlerHelper mpEventHandlerHelper) {
        this.mpEventHandlerService = mpEventHandlerService;
        this.mpEventHandlerHelper = mpEventHandlerHelper;
    }

    @ApiOperation("创建公众号事件处理")
    @PostMapping("")
    public Result addMpEventHandler(@RequestBody MpEventHandlerCreate mpEventHandlerCreate) {
        if (!mpEventHandlerCreate.isValid()) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        MpEventHandler mpEventHandler = new MpEventHandler();
        BeanUtils.copyProperties(mpEventHandlerCreate, mpEventHandler);
        return mpEventHandlerService.save(mpEventHandler) ? ResultWrapper.success() : ResultWrapper.fail(ResultCode.CREATE_FAIL);
    }

    @ApiOperation("编辑公众号事件处理")
    @PutMapping("/{id:\\d{19}}")
    public Result updateMpEventHandler(@RequestBody MpEventHandlerUpdate mpEventHandlerUpdate) {
        if (!mpEventHandlerUpdate.isValid()) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        MpEventHandler mpEventHandler = new MpEventHandler();
        BeanUtils.copyProperties(mpEventHandlerUpdate, mpEventHandler);
        if (mpEventHandlerService.updateById(mpEventHandler)) {
            mpEventHandlerHelper.evictHandlerMap(mpEventHandlerUpdate.getAccountId());
            return ResultWrapper.success();
        }
        return ResultWrapper.fail(ResultCode.UPDATE_FAIL);
    }

    @ApiOperation("根据ID删除公众号事件处理")
    @DeleteMapping("/{id:\\d{19}}")
    public Result deleteMpEventHandler(@PathVariable Long id) {
        if (!ValidateUtils.idValid(id)) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        MpEventHandler mpEventHandler = mpEventHandlerService.getById(id);
        if (mpEventHandler == null) {
            return ResultWrapper.fail(ResultCode.DATA_NOT_FOUND);
        }
        if (mpEventHandlerService.removeById(id)) {
            mpEventHandlerHelper.evictHandlerMap(mpEventHandler.getAccountId());
            return ResultWrapper.success();
        }
        return ResultWrapper.fail(ResultCode.DELETE_FAIL);
    }

    @ApiOperation("获取公众号事件处理分页信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "当前页码", defaultValue = "1", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页数量", defaultValue = "10", dataType = "int", paramType = "query")})
    @PostMapping("/page")
    public Result getMpEventHandlerPage(@RequestParam(required = false, defaultValue = "1") int page,
                              @RequestParam(required = false, defaultValue = "10") int size,
                              @RequestParam(required = false) @ApiParam(value = "降序字段") List<String> descs,
                              @RequestParam(required = false) @ApiParam(value = "升序字段") List<String> ascs,
                              @RequestBody(required = false) Map<String, Object> query) {
        Page<MpEventHandler> pager = new Page<>(page, size);
        if (CollectionUtils.isNotEmpty(descs)) {
            pager.setDescs(descs);
        }
        if (CollectionUtils.isNotEmpty(ascs)) {
            pager.setAscs(ascs);
        }
        QueryWrapper<MpEventHandler> queryWrapper = new QueryWrapper<>();
        if (MapUtils.isNotEmpty(query)) {
            query.forEach((key, value) -> {
                if (StringUtils.equals(key, "accountId")) {
                    queryWrapper.eq("account_id", value);
                }
            });
        }
        IPage<MpEventHandlerVO> mpEventHandlerVOPage = new Page<>();
        BeanUtils.copyProperties(mpEventHandlerService.page(pager, queryWrapper), mpEventHandlerVOPage);
        mpEventHandlerVOPage.setRecords(pager.getRecords().stream().map(mpEventHandler -> {
            MpEventHandlerVO mpEventHandlerVO = new MpEventHandlerVO();
            BeanUtils.copyProperties(mpEventHandler, mpEventHandlerVO);
            return mpEventHandlerVO;
        }).collect(Collectors.toList()));
        return ResultWrapper.success(mpEventHandlerVOPage);
    }

}