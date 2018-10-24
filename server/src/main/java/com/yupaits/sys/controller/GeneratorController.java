package com.yupaits.sys.controller;

import com.yupaits.commons.result.Result;
import com.yupaits.commons.result.ResultWrapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 代码生成控制器
 * @author yupaits
 * @date 2018/10/23
 */
@RestController
@RequestMapping("/sys/code-gen")
public class GeneratorController {

    @Value("${restful.enabled:true}")
    private Boolean restful;

    @PostMapping("")
    public Result genCode(@RequestBody Map<String, Object> config) {
        return ResultWrapper.success();
    }
}
