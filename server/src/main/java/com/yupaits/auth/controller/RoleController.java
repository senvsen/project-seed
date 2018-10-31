package com.yupaits.auth.controller;

import com.yupaits.auth.entity.Role;
import com.yupaits.auth.service.IRoleService;
import com.yupaits.auth.vo.RoleVO;
import com.yupaits.auth.dto.RoleCreate;
import com.yupaits.auth.dto.RoleUpdate;
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
 * 角色 前端控制器
 * </p>
 *
 * @author yupaits
 * @since 2018-10-31
 */
@Slf4j
@Api(tags = "角色接口")
@RestController
@RequestMapping("/auth/role")
public class RoleController {

    private final IRoleService roleService;

    @Autowired
    public RoleController(IRoleService roleService) {
        this.roleService = roleService;
    }

    @ApiOperation("创建角色")
    @PostMapping("")
    public Result addRole(@RequestBody RoleCreate roleCreate) {
        if (!roleCreate.isValid()) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        Role role = new Role();
        BeanUtils.copyProperties(roleCreate, role);
        return roleService.save(role) ? ResultWrapper.success() : ResultWrapper.fail(ResultCode.CREATE_FAIL);
    }

    @ApiOperation("编辑角色")
    @PutMapping("/{id:\\d{19}}")
    public Result updateRole(@RequestBody RoleUpdate roleUpdate) {
        if (!roleUpdate.isValid()) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        Role role = new Role();
        BeanUtils.copyProperties(roleUpdate, role);
        return roleService.updateById(role) ? ResultWrapper.success() : ResultWrapper.fail(ResultCode.UPDATE_FAIL);
    }

    @ApiOperation("批量保存角色")
    @PutMapping("/batch-save")
    public Result batchSaveRole(@RequestBody List<RoleUpdate> roleUpdateList) {
        if (!RoleUpdate.isValid(roleUpdateList)) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        List<Role> roleList = roleUpdateList.stream().map(roleUpdate -> {
            Role role = new Role();
            BeanUtils.copyProperties(roleUpdate, role);
            return role;
        }).collect(Collectors.toList());
        return roleService.saveOrUpdateBatch(roleList) ? ResultWrapper.success() : ResultWrapper.fail(ResultCode.SAVE_FAIL);
    }

    @ApiOperation("根据ID删除角色")
    @DeleteMapping("/{id:\\d{19}}")
    public Result deleteRole(@PathVariable Long id) {
        if (!ValidateUtils.idValid(id)) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        return roleService.removeById(id) ? ResultWrapper.success() : ResultWrapper.fail(ResultCode.DELETE_FAIL);
    }

    @ApiOperation("批量删除角色")
    @PutMapping("/batch-delete")
    public Result batchDeleteRole(@RequestBody List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        return roleService.removeByIds(ids) ? ResultWrapper.success() : ResultWrapper.fail(ResultCode.DELETE_FAIL);
    }

    @ApiOperation("根据ID获取角色信息")
    @GetMapping("/{id:\\d{19}}")
    public Result getRole(@PathVariable Long id) {
        if (!ValidateUtils.idValid(id)) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        Role role = roleService.getById(id);
        if (role == null) {
            return ResultWrapper.fail(ResultCode.DATA_NOT_FOUND);
        }
        RoleVO roleVO = new RoleVO();
        BeanUtils.copyProperties(role, roleVO);
        return ResultWrapper.success(roleVO);
    }

    @ApiOperation("按条件获取角色列表")
    @PostMapping("/list")
    public Result getRoleList(@RequestBody(required = false) Map<String, Object> query) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        if (MapUtils.isNotEmpty(query)) {
            query.forEach((key, value) -> {
                //TODO 设置查询条件
            });
        }
        List<RoleVO> roleVOList = roleService.list(queryWrapper).stream().map(role -> {
            RoleVO roleVO = new RoleVO();
            BeanUtils.copyProperties(role, roleVO);
            return roleVO;
        }).collect(Collectors.toList());
        return ResultWrapper.success(roleVOList);
    }

    @ApiOperation("获取角色分页信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "当前页码", defaultValue = "1", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页数量", defaultValue = "10", dataType = "int", paramType = "query")})
    @PostMapping("/page")
    public Result getRolePage(@RequestParam(required = false, defaultValue = "1") int page,
                              @RequestParam(required = false, defaultValue = "10") int size,
                              @RequestParam(required = false) @ApiParam(value = "降序字段") List<String> descs,
                              @RequestParam(required = false) @ApiParam(value = "升序字段") List<String> ascs,
                              @RequestBody(required = false) Map<String, Object> query) {
        Page<Role> pager = new Page<>(page, size);
        if (CollectionUtils.isNotEmpty(descs)) {
            pager.setDescs(descs);
        }
        if (CollectionUtils.isNotEmpty(ascs)) {
            pager.setAscs(ascs);
        }
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        if (MapUtils.isNotEmpty(query)) {
            query.forEach((key, value) -> {
                //TODO 设置查询条件
            });
        }
        IPage<RoleVO> roleVOPage = new Page<>();
        BeanUtils.copyProperties(roleService.page(pager, queryWrapper), roleVOPage);
        roleVOPage.setRecords(pager.getRecords().stream().map(role -> {
            RoleVO roleVO = new RoleVO();
            BeanUtils.copyProperties(role, roleVO);
            return roleVO;
        }).collect(Collectors.toList()));
        return ResultWrapper.success(roleVOPage);
    }

}