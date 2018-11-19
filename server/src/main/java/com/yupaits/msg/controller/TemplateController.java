package com.yupaits.msg.controller;

import com.yupaits.msg.entity.Template;
import com.yupaits.msg.service.ITemplateService;
import com.yupaits.msg.vo.TemplateVO;
import com.yupaits.msg.dto.TemplateCreate;
import com.yupaits.msg.dto.TemplateUpdate;
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
 * 消息模板 前端控制器
 * </p>
 *
 * @author yupaits
 * @since 2018-11-08
 */
@Slf4j
@Api(tags = "消息模板接口")
@RestController
@RequestMapping("/msg/template")
public class TemplateController {

    private final ITemplateService templateService;

    @Autowired
    public TemplateController(ITemplateService templateService) {
        this.templateService = templateService;
    }

    @ApiOperation("创建消息模板")
    @PostMapping("")
    public Result addTemplate(@RequestBody TemplateCreate templateCreate) {
        if (!templateCreate.isValid()) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        Template template = new Template();
        BeanUtils.copyProperties(templateCreate, template);
        return templateService.save(template) ? ResultWrapper.success() : ResultWrapper.fail(ResultCode.CREATE_FAIL);
    }

    @ApiOperation("编辑消息模板")
    @PutMapping("/{id:\\d{19}}")
    public Result updateTemplate(@RequestBody TemplateUpdate templateUpdate) {
        if (!templateUpdate.isValid()) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        Template template = new Template();
        BeanUtils.copyProperties(templateUpdate, template);
        return templateService.updateById(template) ? ResultWrapper.success() : ResultWrapper.fail(ResultCode.UPDATE_FAIL);
    }

    @ApiOperation("批量保存消息模板")
    @PutMapping("/batch-save")
    public Result batchSaveTemplate(@RequestBody List<TemplateUpdate> templateUpdateList) {
        if (!TemplateUpdate.isValid(templateUpdateList)) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        List<Template> templateList = templateUpdateList.stream().map(templateUpdate -> {
            Template template = new Template();
            BeanUtils.copyProperties(templateUpdate, template);
            return template;
        }).collect(Collectors.toList());
        return templateService.saveOrUpdateBatch(templateList) ? ResultWrapper.success() : ResultWrapper.fail(ResultCode.SAVE_FAIL);
    }

    @ApiOperation("根据ID删除消息模板")
    @DeleteMapping("/{id:\\d{19}}")
    public Result deleteTemplate(@PathVariable Long id) {
        if (!ValidateUtils.idValid(id)) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        return templateService.removeById(id) ? ResultWrapper.success() : ResultWrapper.fail(ResultCode.DELETE_FAIL);
    }

    @ApiOperation("批量删除消息模板")
    @PutMapping("/batch-delete")
    public Result batchDeleteTemplate(@RequestBody List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        return templateService.removeByIds(ids) ? ResultWrapper.success() : ResultWrapper.fail(ResultCode.DELETE_FAIL);
    }

    @ApiOperation("根据ID获取消息模板信息")
    @GetMapping("/{id:\\d{19}}")
    public Result getTemplate(@PathVariable Long id) {
        if (!ValidateUtils.idValid(id)) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        Template template = templateService.getById(id);
        if (template == null) {
            return ResultWrapper.fail(ResultCode.DATA_NOT_FOUND);
        }
        TemplateVO templateVO = new TemplateVO();
        BeanUtils.copyProperties(template, templateVO);
        return ResultWrapper.success(templateVO);
    }

    @ApiOperation("按条件获取消息模板列表")
    @PostMapping("/list")
    public Result getTemplateList(@RequestBody(required = false) Map<String, Object> query) {
        QueryWrapper<Template> queryWrapper = new QueryWrapper<>();
        if (MapUtils.isNotEmpty(query)) {
            query.forEach((key, value) -> {
                //TODO 设置查询条件
            });
        }
        List<TemplateVO> templateVOList = templateService.list(queryWrapper).stream().map(template -> {
            TemplateVO templateVO = new TemplateVO();
            BeanUtils.copyProperties(template, templateVO);
            return templateVO;
        }).collect(Collectors.toList());
        return ResultWrapper.success(templateVOList);
    }

    @ApiOperation("获取消息模板分页信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "当前页码", defaultValue = "1", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页数量", defaultValue = "10", dataType = "int", paramType = "query")})
    @PostMapping("/page")
    public Result getTemplatePage(@RequestParam(required = false, defaultValue = "1") int page,
                              @RequestParam(required = false, defaultValue = "10") int size,
                              @RequestParam(required = false) @ApiParam(value = "降序字段") List<String> descs,
                              @RequestParam(required = false) @ApiParam(value = "升序字段") List<String> ascs,
                              @RequestBody(required = false) Map<String, Object> query) {
        Page<Template> pager = new Page<>(page, size);
        if (CollectionUtils.isNotEmpty(descs)) {
            pager.setDescs(descs);
        }
        if (CollectionUtils.isNotEmpty(ascs)) {
            pager.setAscs(ascs);
        }
        QueryWrapper<Template> queryWrapper = new QueryWrapper<>();
        if (MapUtils.isNotEmpty(query)) {
            query.forEach((key, value) -> {
                if (StringUtils.equals(key, "keyword")) {
                    queryWrapper.and(i -> i.like("name", value).or().like("template_pattern", value));
                }
                if (StringUtils.equals(key, "msgType") && value != null) {
                    queryWrapper.eq("msg_type", value);
                }
            });
        }
        IPage<TemplateVO> templateVOPage = new Page<>();
        BeanUtils.copyProperties(templateService.page(pager, queryWrapper), templateVOPage);
        templateVOPage.setRecords(pager.getRecords().stream().map(template -> {
            TemplateVO templateVO = new TemplateVO();
            BeanUtils.copyProperties(template, templateVO);
            return templateVO;
        }).collect(Collectors.toList()));
        return ResultWrapper.success(templateVOPage);
    }

}