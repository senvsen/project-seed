package ${package.Controller};

import ${package.Entity}.${entity};
import ${package.Service}.${table.serviceName};
<#if cfg.relateTable??>
import ${cfg.package.dto}.${cfg.className.create};
import ${cfg.package.dto}.${cfg.className.update};
import ${cfg.package.vo}.${cfg.className.vo};
</#if>
<#if restControllerStyle>
import ${cfg.class.result};
import ${cfg.class.resultWrapper};
<#else>
import ${cfg.class.modelWrapper};
import org.springframework.ui.Model;
</#if>
import ${cfg.class.resultCode};
import ${cfg.class.validateUtils};
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
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
<#if swagger2>
import io.swagger.annotations.*;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

import java.util.List;
import java.util.Map;


/**
 * <p>
 * ${table.comment!} 前端控制器
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
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

    private final ${table.serviceName} ${cfg.obj.service};

    @Autowired
    public ${table.controllerName}(${table.serviceName} ${cfg.obj.service}) {
        this.${cfg.obj.service} = ${cfg.obj.service};
    }

<#if cfg.relateTable??>
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
        return "<#if package.ModuleName??>${package.ModuleName}</#if><#if controllerMappingHyphenStyle??>/${controllerMappingHyphen}</#if>";
        </#if>
    }

    <#if swagger2>
    @ApiOperation("编辑${table.comment!}")
    </#if>
    @PostMapping("/{id:\\d{19}}")
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
        return "<#if package.ModuleName??>${package.ModuleName}</#if><#if controllerMappingHyphenStyle??>/${controllerMappingHyphen}</#if>";
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
        return "<#if package.ModuleName??>${package.ModuleName}</#if><#if controllerMappingHyphenStyle??>/${controllerMappingHyphen}</#if>";
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
        return "<#if package.ModuleName??>${package.ModuleName}</#if><#if controllerMappingHyphenStyle??>/${controllerMappingHyphen}</#if>";
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
        return "<#if package.ModuleName??>${package.ModuleName}</#if><#if controllerMappingHyphenStyle??>/${controllerMappingHyphen}</#if>";
        </#if>
    }

    <#if swagger2>
    @ApiOperation("按条件获取${table.comment!}列表")
    </#if>
    @GetMapping("/list")
    <#if restControllerStyle>
    public Result get${entity}List(@RequestBody(required = false) Map<String, Object> query) {
    <#else>
    public String get${entity}List(@RequestBody(required = false) Map<String, Object> query, Model model) {
    </#if>
        QueryWrapper<${entity}> queryWrapper = new QueryWrapper<>();
        query.forEach((key, value) -> {
            //TODO 设置查询条件
        });
        <#if restControllerStyle>
        return ResultWrapper.success(${cfg.obj.service}.list(queryWrapper));
        <#else>
        ModelWrapper.success(model, ${cfg.obj.service}.list(queryWrapper));
        return "<#if package.ModuleName??>${package.ModuleName}</#if><#if controllerMappingHyphenStyle??>/${controllerMappingHyphen}</#if>";
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
        query.forEach((key, value) -> {
            //TODO 设置查询条件
        });
        <#if restControllerStyle>
        return ResultWrapper.success(${cfg.obj.service}.pageMaps(pager, queryWrapper));
        <#else>
        ModelWrapper.success(model, ${cfg.obj.service}.pageMaps(pager, queryWrapper));
        return "<#if package.ModuleName??>${package.ModuleName}</#if><#if controllerMappingHyphenStyle??>/${controllerMappingHyphen}</#if>";
        </#if>
    }
<#else>

    //TODO
</#if>

}