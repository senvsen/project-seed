package com.yupaits.session.controller;

import com.yupaits.commons.result.Result;
import com.yupaits.session.service.SessionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

/**
 * @author yupaits
 * @date 2018/10/17
 */
@Api(tags = "会话管理")
@RestController
@RequestMapping("/session")
public class SessionController {

    private final SessionService sessionService;

    @Autowired
    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @ApiOperation("获取会话分页信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "当前页码", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页数量", dataType = "int", paramType = "query")})
    @GetMapping("/page")
    public Result getSessionPage(@PageableDefault Pageable pageable) {
        return sessionService.getSessionPage(pageable);
    }

    @ApiOperation("强制结束会话")
    @ApiImplicitParam(name = "sessionId", value = "会话ID", required = true, paramType = "path")
    @DeleteMapping("/{sessionId}")
    public Result invalidSession(@PathVariable Serializable sessionId) {
        return sessionService.invalidSession(sessionId);
    }
}
