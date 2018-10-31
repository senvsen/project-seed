package com.yupaits.sys.controller;

import com.yupaits.commons.result.Result;
import com.yupaits.commons.result.ResultCode;
import com.yupaits.commons.result.ResultWrapper;
import com.yupaits.generator.GeneratorConfig;
import com.yupaits.sys.helper.CodeGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 代码生成控制器
 * @author yupaits
 * @date 2018/10/23
 */
@Api(tags = "代码生成接口")
@RestController
@RequestMapping("/sys/code-gen")
public class GeneratorController {

    @ApiOperation("执行代码生成操作")
    @PostMapping("")
    public Result genCode(@RequestBody GeneratorConfig generatorConfig) {
        if (!generatorConfig.isValid()) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        CodeGenerator generator = new CodeGenerator(generatorConfig);
        generator.generate();
        return ResultWrapper.success();
    }
}
