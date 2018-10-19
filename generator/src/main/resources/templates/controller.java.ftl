package ${package.Controller};

import ${package.Entity}.${entity};
import ${package.Service}.${table.serviceName};
<#if cfg.relateTable??>
import ${cfg.package.dto}.${cfg.className.create};
import ${cfg.package.dto}.${cfg.className.update};
</#if>
<#if restControllerStyle>
import ${cfg.class.result};
import ${cfg.class.resultWrapper};
<#else>
import ${cfg.class.modelWrapper};
import org.springframework.ui.Model;
</#if>
import ${cfg.class.resultCode};
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if swagger2>
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

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
</#if>

}