package com.yupaits.auth.controller;

import com.yupaits.auth.entity.Department;
import com.yupaits.auth.service.IDepartmentService;
import com.yupaits.auth.vo.DepartmentVO;
import com.yupaits.auth.dto.DepartmentCreate;
import com.yupaits.auth.dto.DepartmentUpdate;
import com.yupaits.commons.utils.ValidateUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;
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
 * 部门 前端控制器
 * </p>
 *
 * @author yupaits
 * @since 2018-10-20
 */
@Slf4j
@Api(tags = "部门接口")
@RestController
@RequestMapping("/auth/department")
public class DepartmentController {

    private final IDepartmentService departmentService;

    @Autowired
    public DepartmentController(IDepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @ApiOperation("创建部门")
    @PostMapping("")
    public Result addDepartment(@RequestBody DepartmentCreate departmentCreate) {
        if (!departmentCreate.isValid()) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        Department department = new Department();
        BeanUtils.copyProperties(departmentCreate, department);
        return departmentService.save(department) ? ResultWrapper.success() : ResultWrapper.fail(ResultCode.CREATE_FAIL);
    }

    @ApiOperation("编辑部门")
    @PutMapping("/{id:\\d{19}}")
    public Result updateDepartment(@RequestBody DepartmentUpdate departmentUpdate) {
        if (!departmentUpdate.isValid()) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        Department department = new Department();
        BeanUtils.copyProperties(departmentUpdate, department);
        return departmentService.updateById(department) ? ResultWrapper.success() : ResultWrapper.fail(ResultCode.UPDATE_FAIL);
    }

    @ApiOperation("批量保存部门")
    @PutMapping("/batch-save")
    public Result batchSaveDepartment(@RequestBody List<DepartmentUpdate> departmentUpdateList) {
        if (!DepartmentUpdate.isValid(departmentUpdateList)) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        List<Department> departmentList = departmentUpdateList.stream().map(departmentUpdate -> {
            Department department = new Department();
            BeanUtils.copyProperties(departmentUpdate, department);
            return department;
        }).collect(Collectors.toList());
        return departmentService.saveOrUpdateBatch(departmentList) ? ResultWrapper.success() : ResultWrapper.fail(ResultCode.SAVE_FAIL);
    }

    @ApiOperation("根据ID删除部门")
    @DeleteMapping("/{id:\\d{19}}")
    public Result deleteDepartment(@PathVariable Long id) {
        if (!ValidateUtils.idValid(id)) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        return departmentService.removeById(id) ? ResultWrapper.success() : ResultWrapper.fail(ResultCode.DELETE_FAIL);
    }

    @ApiOperation("批量删除部门")
    @PutMapping("/batch-delete")
    public Result batchDeleteDepartment(@RequestBody List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        return departmentService.removeByIds(ids) ? ResultWrapper.success() : ResultWrapper.fail(ResultCode.DELETE_FAIL);
    }

    @ApiOperation("根据ID获取部门信息")
    @GetMapping("/{id:\\d{19}}")
    public Result getDepartment(@PathVariable Long id) {
        if (!ValidateUtils.idValid(id)) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        Department department = departmentService.getById(id);
        if (department == null) {
            return ResultWrapper.fail(ResultCode.DATA_NOT_FOUND);
        }
        DepartmentVO departmentVO = new DepartmentVO();
        BeanUtils.copyProperties(department, departmentVO);
        return ResultWrapper.success(departmentVO);
    }

    @ApiOperation("按条件获取部门列表")
    @GetMapping("/list")
    public Result getDepartmentList(@RequestBody(required = false) Map<String, Object> query) {
        QueryWrapper<Department> queryWrapper = new QueryWrapper<>();
        query.forEach((key, value) -> {
            //TODO 设置查询条件
        });
        return ResultWrapper.success(departmentService.list(queryWrapper));
    }

    @ApiOperation("获取部门分页信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "当前页码", defaultValue = "1", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页数量", defaultValue = "10", dataType = "int", paramType = "query")})
    @PostMapping("/page")
    public Result getDepartmentPage(@RequestParam(required = false, defaultValue = "1") int page,
                              @RequestParam(required = false, defaultValue = "10") int size,
                              @RequestParam(required = false) @ApiParam(value = "降序字段") List<String> descs,
                              @RequestParam(required = false) @ApiParam(value = "升序字段") List<String> ascs,
                              @RequestBody(required = false) Map<String, Object> query) {
        Page<Department> pager = new Page<>(page, size);
        if (CollectionUtils.isNotEmpty(descs)) {
            pager.setDescs(descs);
        }
        if (CollectionUtils.isNotEmpty(ascs)) {
            pager.setAscs(ascs);
        }
        QueryWrapper<Department> queryWrapper = new QueryWrapper<>();
        query.forEach((key, value) -> {
            //TODO 设置查询条件
        });
        return ResultWrapper.success(departmentService.pageMaps(pager, queryWrapper));
    }

}