package com.yupaits.msg.controller;

import com.yupaits.msg.entity.SystemNotice;
import com.yupaits.msg.service.ISystemNoticeService;
import com.yupaits.msg.vo.SystemNoticeVO;
import com.yupaits.msg.dto.SystemNoticeCreate;
import com.yupaits.msg.dto.SystemNoticeUpdate;
import com.yupaits.commons.utils.ValidateUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.time.LocalDateTime;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import com.yupaits.commons.result.Result;
import com.yupaits.commons.result.ResultWrapper;
import com.yupaits.commons.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
 * 系统公告 前端控制器
 * </p>
 *
 * @author yupaits
 * @since 2018-11-08
 */
@Slf4j
@Api(tags = "系统公告接口")
@RestController
@RequestMapping("/msg/system-notice")
public class SystemNoticeController {

    private final ISystemNoticeService systemNoticeService;

    @Autowired
    public SystemNoticeController(ISystemNoticeService systemNoticeService) {
        this.systemNoticeService = systemNoticeService;
    }

    @ApiOperation("创建系统公告")
    @PostMapping("")
    public Result addSystemNotice(@RequestBody SystemNoticeCreate systemNoticeCreate) {
        if (!systemNoticeCreate.isValid()) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        SystemNotice systemNotice = new SystemNotice();
        BeanUtils.copyProperties(systemNoticeCreate, systemNotice);
        return systemNoticeService.save(systemNotice) ? ResultWrapper.success() : ResultWrapper.fail(ResultCode.CREATE_FAIL);
    }

    @ApiOperation("编辑系统公告")
    @PutMapping("/{id:\\d{19}}")
    public Result updateSystemNotice(@RequestBody SystemNoticeUpdate systemNoticeUpdate) {
        if (!systemNoticeUpdate.isValid()) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        SystemNotice systemNotice = new SystemNotice();
        BeanUtils.copyProperties(systemNoticeUpdate, systemNotice);
        return systemNoticeService.updateById(systemNotice) ? ResultWrapper.success() : ResultWrapper.fail(ResultCode.UPDATE_FAIL);
    }

    @ApiOperation("批量保存系统公告")
    @PutMapping("/batch-save")
    public Result batchSaveSystemNotice(@RequestBody List<SystemNoticeUpdate> systemNoticeUpdateList) {
        if (!SystemNoticeUpdate.isValid(systemNoticeUpdateList)) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        List<SystemNotice> systemNoticeList = systemNoticeUpdateList.stream().map(systemNoticeUpdate -> {
            SystemNotice systemNotice = new SystemNotice();
            BeanUtils.copyProperties(systemNoticeUpdate, systemNotice);
            return systemNotice;
        }).collect(Collectors.toList());
        return systemNoticeService.saveOrUpdateBatch(systemNoticeList) ? ResultWrapper.success() : ResultWrapper.fail(ResultCode.SAVE_FAIL);
    }

    @ApiOperation("根据ID删除系统公告")
    @DeleteMapping("/{id:\\d{19}}")
    public Result deleteSystemNotice(@PathVariable Long id) {
        if (!ValidateUtils.idValid(id)) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        return systemNoticeService.removeById(id) ? ResultWrapper.success() : ResultWrapper.fail(ResultCode.DELETE_FAIL);
    }

    @ApiOperation("批量删除系统公告")
    @PutMapping("/batch-delete")
    public Result batchDeleteSystemNotice(@RequestBody List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        return systemNoticeService.removeByIds(ids) ? ResultWrapper.success() : ResultWrapper.fail(ResultCode.DELETE_FAIL);
    }

    @ApiOperation("根据ID获取系统公告信息")
    @GetMapping("/{id:\\d{19}}")
    public Result getSystemNotice(@PathVariable Long id) {
        if (!ValidateUtils.idValid(id)) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        SystemNotice systemNotice = systemNoticeService.getById(id);
        if (systemNotice == null) {
            return ResultWrapper.fail(ResultCode.DATA_NOT_FOUND);
        }
        SystemNoticeVO systemNoticeVO = new SystemNoticeVO();
        BeanUtils.copyProperties(systemNotice, systemNoticeVO);
        return ResultWrapper.success(systemNoticeVO);
    }

    @ApiOperation("按条件获取系统公告列表")
    @PostMapping("/list")
    public Result getSystemNoticeList(@RequestBody(required = false) Map<String, Object> query) {
        QueryWrapper<SystemNotice> queryWrapper = new QueryWrapper<>();
        if (MapUtils.isNotEmpty(query)) {
            query.forEach((key, value) -> {
                if (StringUtils.equals(key, "type")) {
                    LocalDateTime now = LocalDateTime.now();
                    if (StringUtils.equals("active", String.valueOf(value))) {
                        queryWrapper.gt("end_time", now).lt("start_time", now).orderByAsc("start_time");
                    } else if (StringUtils.equals("available", String.valueOf(value))) {
                        queryWrapper.gt("end_time", now).orderByAsc("start_time");
                    }
                }
            });
        }
        List<SystemNoticeVO> systemNoticeVOList = systemNoticeService.list(queryWrapper).stream().map(systemNotice -> {
            SystemNoticeVO systemNoticeVO = new SystemNoticeVO();
            BeanUtils.copyProperties(systemNotice, systemNoticeVO);
            return systemNoticeVO;
        }).collect(Collectors.toList());
        return ResultWrapper.success(systemNoticeVOList);
    }

    @ApiOperation("获取系统公告分页信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "当前页码", defaultValue = "1", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页数量", defaultValue = "10", dataType = "int", paramType = "query")})
    @PostMapping("/page")
    public Result getSystemNoticePage(@RequestParam(required = false, defaultValue = "1") int page,
                              @RequestParam(required = false, defaultValue = "10") int size,
                              @RequestParam(required = false) @ApiParam(value = "降序字段") List<String> descs,
                              @RequestParam(required = false) @ApiParam(value = "升序字段") List<String> ascs,
                              @RequestBody(required = false) Map<String, Object> query) {
        Page<SystemNotice> pager = new Page<>(page, size);
        if (CollectionUtils.isNotEmpty(descs)) {
            pager.setDescs(descs);
        }
        if (CollectionUtils.isNotEmpty(ascs)) {
            pager.setAscs(ascs);
        }
        QueryWrapper<SystemNotice> queryWrapper = new QueryWrapper<>();
        if (MapUtils.isNotEmpty(query)) {
            query.forEach((key, value) -> {
                //TODO 设置查询条件
            });
        }
        IPage<SystemNoticeVO> systemNoticeVOPage = new Page<>();
        BeanUtils.copyProperties(systemNoticeService.page(pager, queryWrapper), systemNoticeVOPage);
        systemNoticeVOPage.setRecords(pager.getRecords().stream().map(systemNotice -> {
            SystemNoticeVO systemNoticeVO = new SystemNoticeVO();
            BeanUtils.copyProperties(systemNotice, systemNoticeVO);
            return systemNoticeVO;
        }).collect(Collectors.toList()));
        return ResultWrapper.success(systemNoticeVOPage);
    }

}