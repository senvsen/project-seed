package ${package.Controller};

import ${package.Entity}.${entity};
import ${package.Service}.${table.serviceName};
import ${cfg.package.vo}.${cfg.className.vo};
<#if cfg.notRelateTable??>
import ${cfg.package.dto}.${cfg.className.create};
import ${cfg.package.dto}.${cfg.className.update};
import ${cfg.class.validateUtils};
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
<#else>
import ${cfg.class.foreignId};
import ${cfg.class.relatedId};
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
</#if>
<#if restControllerStyle>
import ${cfg.class.result};
import ${cfg.class.resultWrapper};
<#else>
import ${cfg.class.modelWrapper};
import org.springframework.ui.Model;
</#if>
import ${cfg.class.resultCode};
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
<#if swagger2>
import io.swagger.annotations.*;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

import java.util.List;
import java.util.stream.Collectors;


/**
 * <p>
 * ${table.comment!} 前端控制器
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Slf4j
<#if swagger2>
@Api(tags = "${table.comment!}接口")
</#if>
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>

    <#if !restControllerStyle>
    private final static String VIEW = "<#if package.ModuleName??>${package.ModuleName}</#if><#if controllerMappingHyphenStyle??>/${controllerMappingHyphen}</#if>";
    </#if>
    private final ${table.serviceName} ${cfg.obj.service};

    @Autowired
    public ${table.controllerName}(${table.serviceName} ${cfg.obj.service}) {
        this.${cfg.obj.service} = ${cfg.obj.service};
    }

<#if cfg.notRelateTable??>
    <#if swagger2>
    @ApiOperation("创建${table.comment!}")
    </#if>
    @PostMapping("")
    <#if restControllerStyle>
    public Result add${entity}(@RequestBody ${cfg.className.create} ${cfg.obj.create}) {
    <#else>
    public String add${entity}(@RequestBody ${cfg.className.create} ${cfg.obj.create}, Model model) {
    </#if>
        if (!${cfg.obj.create}.isValid()) {
            <#if restControllerStyle>
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
            <#else>
            ModelWrapper.fail(model, ResultCode.PARAMS_ERROR.getMsg());
            return VIEW;
            </#if>
        }
        ${entity} ${cfg.obj.entity} = new ${entity}();
        BeanUtils.copyProperties(${cfg.obj.create}, ${cfg.obj.entity});
        <#if restControllerStyle>
        return ${cfg.obj.service}.save(${cfg.obj.entity}) ? ResultWrapper.success() : ResultWrapper.fail(ResultCode.CREATE_FAIL);
        <#else>
        if (${cfg.obj.service}.save(${cfg.obj.entity})) {
            ModelWrapper.success(model);
        } else {
            ModelWrapper.fail(model, ResultCode.CREATE_FAIL);
        }
        return VIEW;
        </#if>
    }

    <#if swagger2>
    @ApiOperation("编辑${table.comment!}")
    </#if>
    @PutMapping("/{id:\\d{19}}")
    <#if restControllerStyle>
    public Result update${entity}(@RequestBody ${cfg.className.update} ${cfg.obj.update}) {
    <#else>
    public String update${entity}(@RequestBody ${cfg.className.update} ${cfg.obj.update}, Model model) {
    </#if>
        if (!${cfg.obj.update}.isValid()) {
            <#if restControllerStyle>
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
            <#else>
            ModelWrapper.fail(model, ResultCode.PARAMS_ERROR.getMsg());
            return VIEW;
            </#if>
        }
        ${entity} ${cfg.obj.entity} = new ${entity}();
        BeanUtils.copyProperties(${cfg.obj.update}, ${cfg.obj.entity});
        <#if restControllerStyle>
        return ${cfg.obj.service}.updateById(${cfg.obj.entity}) ? ResultWrapper.success() : ResultWrapper.fail(ResultCode.UPDATE_FAIL);
        <#else>
        if (${cfg.obj.service}.updateById(${cfg.obj.entity})) {
            ModelWrapper.success(model);
        } else {
            ModelWrapper.fail(model, ResultCode.UPDATE_FAIL);
        }
        return VIEW;
        </#if>
    }

    <#if swagger2>
    @ApiOperation("批量保存${table.comment!}")
    </#if>
    @PutMapping("/batch-save")
    <#if restControllerStyle>
    public Result batchSave${entity}(@RequestBody List<${cfg.className.update}> ${cfg.obj.update}List) {
    <#else>
    public String batchSave${entity}(@RequestBody List<${cfg.className.update}> ${cfg.obj.update}List, Model model) {
    </#if>
        if (!${cfg.className.update}.isValid(${cfg.obj.update}List)) {
            <#if restControllerStyle>
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
            <#else>
            ModelWrapper.fail(model, ResultCode.PARAMS_ERROR.getMsg());
            return VIEW;
            </#if>
        }
        List<${entity}> ${cfg.obj.entity}List = ${cfg.obj.update}List.stream().map(${cfg.obj.update} -> {
            ${entity} ${cfg.obj.entity} = new ${entity}();
            BeanUtils.copyProperties(${cfg.obj.update}, ${cfg.obj.entity});
            return ${cfg.obj.entity};
        }).collect(Collectors.toList());
        <#if restControllerStyle>
        return ${cfg.obj.service}.saveOrUpdateBatch(${cfg.obj.entity}List) ? ResultWrapper.success() : ResultWrapper.fail(ResultCode.SAVE_FAIL);
        <#else>
        if (${cfg.obj.service}.saveOrUpdateBatch(${cfg.obj.entity}List)) {
            ModelWrapper.success(model);
        } else {
            ModelWrapper.fail(model, ResultCode.SAVE_FAIL);
        }
        return VIEW;
        </#if>
    }

    <#if swagger2>
    @ApiOperation("根据ID删除${table.comment!}")
    </#if>
    @DeleteMapping("/{id:\\d{19}}")
    <#if restControllerStyle>
    public Result delete${entity}(@PathVariable Long id) {
    <#else>
    public String delete${entity}(@PathVariable Long id, Model model) {
    </#if>
        if (!ValidateUtils.idValid(id)) {
            <#if restControllerStyle>
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
            <#else>
            ModelWrapper.fail(model, ResultCode.PARAMS_ERROR.getMsg());
            return VIEW;
            </#if>
        }
        <#if restControllerStyle>
        return ${cfg.obj.service}.removeById(id) ? ResultWrapper.success() : ResultWrapper.fail(ResultCode.DELETE_FAIL);
        <#else>
        if (${cfg.obj.service}.removeById(id)) {
            ModelWrapper.success(model);
        } else {
            ModelWrapper.fail(model, ResultCode.DELETE_FAIL);
        }
        return VIEW;
        </#if>
    }

    <#if swagger2>
    @ApiOperation("批量删除${table.comment!}")
    </#if>
    @PutMapping("/batch-delete")
    <#if restControllerStyle>
    public Result batchDelete${entity}(@RequestBody List<Long> ids) {
    <#else>
    public String delete${entity}(@RequestBody List<Long> ids, Model model) {
    </#if>
        if (CollectionUtils.isEmpty(ids)) {
            <#if restControllerStyle>
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
            <#else>
            ModelWrapper.fail(model, ResultCode.PARAMS_ERROR.getMsg());
            return VIEW;
            </#if>
        }
        <#if restControllerStyle>
        return ${cfg.obj.service}.removeByIds(ids) ? ResultWrapper.success() : ResultWrapper.fail(ResultCode.DELETE_FAIL);
        <#else>
        if (${cfg.obj.service}.removeByIds(ids)) {
            ModelWrapper.success(model);
        } else {
            ModelWrapper.fail(model, ResultCode.DELETE_FAIL);
        }
        return VIEW;
        </#if>
    }

    <#if swagger2>
    @ApiOperation("根据ID获取${table.comment!}信息")
    </#if>
    @GetMapping("/{id:\\d{19}}")
    <#if restControllerStyle>
    public Result get${entity}(@PathVariable Long id) {
    <#else>
    public String get${entity}(@PathVariable Long id, Model model) {
    </#if>
        if (!ValidateUtils.idValid(id)) {
            <#if restControllerStyle>
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
            <#else>
            ModelWrapper.fail(model, ResultCode.PARAMS_ERROR.getMsg());
            return VIEW;
            </#if>
        }
        ${entity} ${cfg.obj.entity} = ${cfg.obj.service}.getById(id);
        if (${cfg.obj.entity} == null) {
            <#if restControllerStyle>
            return ResultWrapper.fail(ResultCode.DATA_NOT_FOUND);
            <#else>
            ModelWrapper.fail(model, ResultCode.DATA_NOT_FOUND.getMsg());
        } else {
            ${cfg.className.vo} ${cfg.obj.vo} = new ${cfg.className.vo}();
            BeanUtils.copyProperties(${cfg.obj.entity}, ${cfg.obj.vo});
            ModelWrapper.success(model, ${cfg.obj.vo});
            </#if>
        }
        <#if restControllerStyle>
        ${cfg.className.vo} ${cfg.obj.vo} = new ${cfg.className.vo}();
        BeanUtils.copyProperties(${cfg.obj.entity}, ${cfg.obj.vo});
        return ResultWrapper.success(${cfg.obj.vo});
        <#else>
        return VIEW;
        </#if>
    }

    <#if swagger2>
    @ApiOperation("按条件获取${table.comment!}列表")
    </#if>
    @PostMapping("/list")
    <#if restControllerStyle>
    public Result get${entity}List(@RequestBody(required = false) Map<String, Object> query) {
    <#else>
    public String get${entity}List(@RequestBody(required = false) Map<String, Object> query, Model model) {
    </#if>
        QueryWrapper<${entity}> queryWrapper = new QueryWrapper<>();
        if (MapUtils.isNotEmpty(query)) {
            query.forEach((key, value) -> {
                //TODO 设置查询条件
            });
        }
        List<${cfg.className.vo}> ${cfg.obj.vo}List = ${cfg.obj.service}.list(queryWrapper).stream().map(${cfg.obj.entity} -> {
            ${cfg.className.vo} ${cfg.obj.vo} = new ${cfg.className.vo}();
            BeanUtils.copyProperties(${cfg.obj.entity}, ${cfg.obj.vo});
            return ${cfg.obj.vo};
        }).collect(Collectors.toList());
        <#if restControllerStyle>
        return ResultWrapper.success(${cfg.obj.vo}List);
        <#else>
        ModelWrapper.success(model, ${cfg.obj.vo}List);
        return VIEW;
        </#if>
    }

    <#if swagger2>
    @ApiOperation("获取${table.comment!}分页信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "当前页码", defaultValue = "1", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页数量", defaultValue = "10", dataType = "int", paramType = "query")})
    </#if>
    @PostMapping("/page")
    public <#if restControllerStyle>Result<#else>String</#if> get${entity}Page(@RequestParam(required = false, defaultValue = "1") int page,
                              @RequestParam(required = false, defaultValue = "10") int size,
                              @RequestParam(required = false) <#if swagger2>@ApiParam(value = "降序字段")</#if> List<String> descs,
                              @RequestParam(required = false) <#if swagger2>@ApiParam(value = "升序字段")</#if> List<String> ascs,
                              @RequestBody(required = false) Map<String, Object> query<#if !restControllerStyle>, Model model</#if>) {
        Page<${entity}> pager = new Page<>(page, size);
        if (CollectionUtils.isNotEmpty(descs)) {
            pager.setDescs(descs);
        }
        if (CollectionUtils.isNotEmpty(ascs)) {
            pager.setAscs(ascs);
        }
        QueryWrapper<${entity}> queryWrapper = new QueryWrapper<>();
        if (MapUtils.isNotEmpty(query)) {
            query.forEach((key, value) -> {
                //TODO 设置查询条件
            });
        }
        IPage<${cfg.className.vo}> ${cfg.obj.vo}Page = new Page<>();
        BeanUtils.copyProperties(${cfg.obj.service}.page(pager, queryWrapper), ${cfg.obj.vo}Page);
        ${cfg.obj.vo}Page.setRecords(pager.getRecords().stream().map(${cfg.obj.entity} -> {
            ${cfg.className.vo} ${cfg.obj.vo} = new ${cfg.className.vo}();
            BeanUtils.copyProperties(${cfg.obj.entity}, ${cfg.obj.vo});
            return ${cfg.obj.vo};
        }).collect(Collectors.toList()));
        <#if restControllerStyle>
        return ResultWrapper.success(${cfg.obj.vo}Page);
        <#else>
        ModelWrapper.success(model, ${cfg.obj.vo}Page);
        return VIEW;
        </#if>
    }
<#else>
    <#if swagger2>
    @ApiOperation("按条件获取${table.comment!}关系列表")
    </#if>
    @PostMapping("/list")
    <#if restControllerStyle>
    public Result get${entity}List(@RequestBody ForeignId<Long> foreignId) {
    <#else>
    public String get${entity}List(@RequestBody ForeignId<Long> foreignId, Model model) {
    </#if>
        if (!foreignId.isValid(${entity}.class)) {
            <#if restControllerStyle>
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
            <#else>
            ModelWrapper.fail(model, ResultCode.PARAMS_ERROR.getMsg());
            return VIEW;
            </#if>
        }
        List<${cfg.className.vo}> ${cfg.obj.vo}List = ${cfg.obj.service}.list(new QueryWrapper<${entity}>()
                .eq("deleted", false)
                .eq(StringUtils.camelToUnderline(foreignId.getFieldName()), foreignId.getValue()))
                .stream().map(${cfg.obj.entity} -> {
                    ${cfg.className.vo} ${cfg.obj.vo} = new ${cfg.className.vo}();
                    BeanUtils.copyProperties(${cfg.obj.entity}, ${cfg.obj.vo});
                    return ${cfg.obj.vo};
                }).collect(Collectors.toList());
        <#if restControllerStyle>
        return ResultWrapper.success(${cfg.obj.vo}List);
        <#else>
        ModelWrapper.success(model, ${cfg.obj.vo}List);
        return VIEW;
        </#if>
    }

    <#if swagger2>
    @ApiOperation("批量保存${table.comment!}关系")
    </#if>
    @PostMapping("/batch-save")
    <#if restControllerStyle>
    public Result batchSave(@RequestBody RelatedId<Long> relatedId) {
    <#else>
    public String batchSave(@RequestBody RelatedId<Long> relatedId, Model model) {
    </#if>
        if (!relatedId.isValid(${entity}.class)) {
            <#if restControllerStyle>
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
            <#else>
            ModelWrapper.fail(model, ResultCode.PARAMS_ERROR.getMsg());
            return VIEW;
            </#if>
        }
        if (CollectionUtils.isEmpty(relatedId.getSecondIds().getValues())) {
            ${cfg.obj.service}.remove(new QueryWrapper<${entity}>()
                    .eq(StringUtils.camelToUnderline(relatedId.getFirstId().getFieldName()), relatedId.getFirstId().getValue()));
            <#if restControllerStyle>
            return ResultWrapper.success();
            <#else>
            ModelWrapper.success(model);
            return VIEW;
            </#if>
        } else {
            <#if restControllerStyle>
            return ${cfg.obj.service}.batchSave(relatedId) ? ResultWrapper.success() : ResultWrapper.fail(ResultCode.SAVE_FAIL);
            <#else>
            if (${cfg.obj.service}.batchSave(relatedId)) {
                ModelWrapper.success(model);
            } else {
                ModelWrapper.fail(model, ResultCode.SAVE_FAIL);
            }
            return VIEW;
            </#if>
        }
    }
</#if>

}