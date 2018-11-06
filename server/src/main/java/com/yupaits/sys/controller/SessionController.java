package com.yupaits.sys.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupaits.commons.annotation.NoCache;
import com.yupaits.commons.result.Result;
import com.yupaits.sys.service.SessionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

/**
 * 会话管理控制器
 * @author yupaits
 * @date 2018/10/17
 */
@NoCache
@Api(tags = "会话管理")
@RestController
@RequestMapping("/sys/session")
public class SessionController {

    private final SessionService sessionService;

    @Autowired
    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @ApiOperation("获取会话分页信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "当前页码", defaultValue = "1", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页数量", defaultValue = "10", dataType = "int", paramType = "query")})
    @GetMapping("/page")
    public Result getSessionPage(@RequestParam(required = false, defaultValue = "1") int page,
                                 @RequestParam(required = false, defaultValue = "10") int size) {
        return sessionService.getSessionPage(new Page(page, size));
    }

    @ApiOperation("强制结束会话")
    @ApiImplicitParam(name = "sessionId", value = "会话ID", required = true, paramType = "path")
    @DeleteMapping("/{sessionId}")
    public Result invalidSession(@PathVariable Serializable sessionId) {
        return sessionService.invalidSession(sessionId);
    }
}
