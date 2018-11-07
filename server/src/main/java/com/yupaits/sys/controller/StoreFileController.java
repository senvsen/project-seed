package com.yupaits.sys.controller;

import com.yupaits.sys.entity.StoreFile;
import com.yupaits.sys.service.IStoreFileService;
import com.yupaits.sys.vo.StoreFileVO;
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
 * 上传文件 前端控制器
 * </p>
 *
 * @author yupaits
 * @since 2018-11-07
 */
@Slf4j
@Api(tags = "文件管理接口")
@RestController
@RequestMapping("/sys/store-file")
public class StoreFileController {

    private final IStoreFileService storeFileService;

    @Autowired
    public StoreFileController(IStoreFileService storeFileService) {
        this.storeFileService = storeFileService;
    }

    @ApiOperation("根据ID删除上传文件")
    @DeleteMapping("/{id:\\d{19}}")
    public Result deleteStoreFile(@PathVariable Long id) {
        if (!ValidateUtils.idValid(id)) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        return storeFileService.removeById(id) ? ResultWrapper.success() : ResultWrapper.fail(ResultCode.DELETE_FAIL);
    }

    @ApiOperation("批量删除上传文件")
    @PutMapping("/batch-delete")
    public Result batchDeleteStoreFile(@RequestBody List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        return storeFileService.removeByIds(ids) ? ResultWrapper.success() : ResultWrapper.fail(ResultCode.DELETE_FAIL);
    }

    @ApiOperation("根据ID获取上传文件信息")
    @GetMapping("/{id:\\d{19}}")
    public Result getStoreFile(@PathVariable Long id) {
        if (!ValidateUtils.idValid(id)) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        StoreFile storeFile = storeFileService.getById(id);
        if (storeFile == null) {
            return ResultWrapper.fail(ResultCode.DATA_NOT_FOUND);
        }
        StoreFileVO storeFileVO = new StoreFileVO();
        BeanUtils.copyProperties(storeFile, storeFileVO);
        return ResultWrapper.success(storeFileVO);
    }

    @ApiOperation("按条件获取上传文件列表")
    @PostMapping("/list")
    public Result getStoreFileList(@RequestBody(required = false) Map<String, Object> query) {
        QueryWrapper<StoreFile> queryWrapper = new QueryWrapper<>();
        if (MapUtils.isNotEmpty(query)) {
            query.forEach((key, value) -> {
                //TODO 设置查询条件
            });
        }
        List<StoreFileVO> storeFileVOList = storeFileService.list(queryWrapper).stream().map(storeFile -> {
            StoreFileVO storeFileVO = new StoreFileVO();
            BeanUtils.copyProperties(storeFile, storeFileVO);
            return storeFileVO;
        }).collect(Collectors.toList());
        return ResultWrapper.success(storeFileVOList);
    }

    @ApiOperation("获取上传文件分页信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "当前页码", defaultValue = "1", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页数量", defaultValue = "10", dataType = "int", paramType = "query")})
    @PostMapping("/page")
    public Result getStoreFilePage(@RequestParam(required = false, defaultValue = "1") int page,
                              @RequestParam(required = false, defaultValue = "10") int size,
                              @RequestParam(required = false) @ApiParam(value = "降序字段") List<String> descs,
                              @RequestParam(required = false) @ApiParam(value = "升序字段") List<String> ascs,
                              @RequestBody(required = false) Map<String, Object> query) {
        Page<StoreFile> pager = new Page<>(page, size);
        if (CollectionUtils.isNotEmpty(descs)) {
            pager.setDescs(descs);
        }
        if (CollectionUtils.isNotEmpty(ascs)) {
            pager.setAscs(ascs);
        }
        QueryWrapper<StoreFile> queryWrapper = new QueryWrapper<>();
        if (MapUtils.isNotEmpty(query)) {
            query.forEach((key, value) -> {
                //TODO 设置查询条件
            });
        }
        IPage<StoreFileVO> storeFileVOPage = new Page<>();
        BeanUtils.copyProperties(storeFileService.page(pager, queryWrapper), storeFileVOPage);
        storeFileVOPage.setRecords(pager.getRecords().stream().map(storeFile -> {
            StoreFileVO storeFileVO = new StoreFileVO();
            BeanUtils.copyProperties(storeFile, storeFileVO);
            return storeFileVO;
        }).collect(Collectors.toList()));
        return ResultWrapper.success(storeFileVOPage);
    }

}