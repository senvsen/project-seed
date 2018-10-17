package com.yupaits.session.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupaits.commons.result.Result;

import java.io.Serializable;

/**
 * @author yupaits
 * @date 2018/10/17
 */
public interface SessionService {

    /**
     * 当前会话分页信息
     * @param page 分页参数
     * @return Result
     */
    Result getSessionPage(Page page);

    /**
     * 强制结束会话
     * @param sessionId 会话ID
     * @return Result
     */
    Result invalidSession(Serializable sessionId);
}
