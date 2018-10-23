package com.yupaits.sys.controller;

import com.yupaits.sys.entity.FilterChain;
import com.yupaits.sys.service.IFilterChainService;
import com.yupaits.sys.vo.FilterChainVO;
import com.yupaits.sys.dto.FilterChainCreate;
import com.yupaits.sys.dto.FilterChainUpdate;
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
 * 权限过滤链 前端控制器
 * </p>
 *
 * @author yupaits
 * @since 2018-10-23
 */
@Slf4j
@Api(tags = "权限过滤链接口")
@RestController
@RequestMapping("/sys/filter-chain")
public class FilterChainController {

    private final IFilterChainService filterChainService;

    @Autowired
    public FilterChainController(IFilterChainService filterChainService) {
        this.filterChainService = filterChainService;
    }

    @ApiOperation("创建权限过滤链")
    @PostMapping("")
    public Result addFilterChain(@RequestBody FilterChainCreate filterChainCreate) {
        if (!filterChainCreate.isValid()) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        FilterChain filterChain = new FilterChain();
        BeanUtils.copyProperties(filterChainCreate, filterChain);
        FilterChain maxSortCodeFilterChain = filterChainService.getOne(new QueryWrapper<FilterChain>().select("sort_code").orderByDesc("sort_code"));
        filterChain.setSortCode(maxSortCodeFilterChain == null ? 1 : maxSortCodeFilterChain.getSortCode() + 1);
        return filterChainService.save(filterChain) ? ResultWrapper.success() : ResultWrapper.fail(ResultCode.CREATE_FAIL);
    }

    @ApiOperation("编辑权限过滤链")
    @PutMapping("/{id:\\d{19}}")
    public Result updateFilterChain(@RequestBody FilterChainUpdate filterChainUpdate) {
        if (!filterChainUpdate.isValid()) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        FilterChain filterChain = new FilterChain();
        BeanUtils.copyProperties(filterChainUpdate, filterChain);
        return filterChainService.updateById(filterChain) ? ResultWrapper.success() : ResultWrapper.fail(ResultCode.UPDATE_FAIL);
    }

    @ApiOperation("批量保存权限过滤链")
    @PutMapping("/batch-save")
    public Result batchSaveFilterChain(@RequestBody List<FilterChainUpdate> filterChainUpdateList) {
        if (!FilterChainUpdate.isValid(filterChainUpdateList)) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        List<FilterChain> filterChainList = filterChainUpdateList.stream().map(filterChainUpdate -> {
            FilterChain filterChain = new FilterChain();
            BeanUtils.copyProperties(filterChainUpdate, filterChain);
            return filterChain;
        }).collect(Collectors.toList());
        return filterChainService.saveOrUpdateBatch(filterChainList) ? ResultWrapper.success() : ResultWrapper.fail(ResultCode.SAVE_FAIL);
    }

    @ApiOperation("根据ID删除权限过滤链")
    @DeleteMapping("/{id:\\d{19}}")
    public Result deleteFilterChain(@PathVariable Long id) {
        if (!ValidateUtils.idValid(id)) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        return filterChainService.removeById(id) ? ResultWrapper.success() : ResultWrapper.fail(ResultCode.DELETE_FAIL);
    }

    @ApiOperation("批量删除权限过滤链")
    @PutMapping("/batch-delete")
    public Result batchDeleteFilterChain(@RequestBody List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        return filterChainService.removeByIds(ids) ? ResultWrapper.success() : ResultWrapper.fail(ResultCode.DELETE_FAIL);
    }

    @ApiOperation("根据ID获取权限过滤链信息")
    @GetMapping("/{id:\\d{19}}")
    public Result getFilterChain(@PathVariable Long id) {
        if (!ValidateUtils.idValid(id)) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        FilterChain filterChain = filterChainService.getById(id);
        if (filterChain == null) {
            return ResultWrapper.fail(ResultCode.DATA_NOT_FOUND);
        }
        FilterChainVO filterChainVO = new FilterChainVO();
        BeanUtils.copyProperties(filterChain, filterChainVO);
        return ResultWrapper.success(filterChainVO);
    }

    @ApiOperation("按条件获取权限过滤链列表")
    @GetMapping("/list")
    public Result getFilterChainList(@RequestBody(required = false) Map<String, Object> query) {
        QueryWrapper<FilterChain> queryWrapper = new QueryWrapper<>();
        if (MapUtils.isNotEmpty(query)) {
            query.forEach((key, value) -> {
                //TODO 设置查询条件
            });
        }
        List<FilterChainVO> filterChainVOList = filterChainService.list(queryWrapper).stream().map(filterChain -> {
            FilterChainVO filterChainVO = new FilterChainVO();
            BeanUtils.copyProperties(filterChain, filterChainVO);
            return filterChainVO;
        }).collect(Collectors.toList());
        return ResultWrapper.success(filterChainVOList);
    }

    @ApiOperation("获取权限过滤链分页信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "当前页码", defaultValue = "1", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页数量", defaultValue = "10", dataType = "int", paramType = "query")})
    @PostMapping("/page")
    public Result getFilterChainPage(@RequestParam(required = false, defaultValue = "1") int page,
                              @RequestParam(required = false, defaultValue = "10") int size,
                              @RequestParam(required = false) @ApiParam(value = "降序字段") List<String> descs,
                              @RequestParam(required = false) @ApiParam(value = "升序字段") List<String> ascs,
                              @RequestBody(required = false) Map<String, Object> query) {
        Page<FilterChain> pager = new Page<>(page, size);
        if (CollectionUtils.isNotEmpty(descs)) {
            pager.setDescs(descs);
        }
        if (CollectionUtils.isNotEmpty(ascs)) {
            pager.setAscs(ascs);
        }
        QueryWrapper<FilterChain> queryWrapper = new QueryWrapper<>();
        if (MapUtils.isNotEmpty(query)) {
            query.forEach((key, value) -> {
                //TODO 设置查询条件
            });
        }
        IPage<FilterChainVO> filterChainVOPage = new Page<>();
        BeanUtils.copyProperties(filterChainService.page(pager, queryWrapper), filterChainVOPage);
        filterChainVOPage.setRecords(pager.getRecords().stream().map(filterChain -> {
            FilterChainVO filterChainVO = new FilterChainVO();
            BeanUtils.copyProperties(filterChain, filterChainVO);
            return filterChainVO;
        }).collect(Collectors.toList()));
        return ResultWrapper.success(filterChainVOPage);
    }

}