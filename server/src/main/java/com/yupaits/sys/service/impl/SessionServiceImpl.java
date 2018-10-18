package com.yupaits.sys.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupaits.commons.result.Result;
import com.yupaits.commons.result.ResultWrapper;
import com.yupaits.sys.service.SessionService;
import com.yupaits.web.shiro.redis.RedisCacheManager;
import com.yupaits.web.shiro.redis.RedisSessionDAO;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

/**
 * @author yupaits
 * @date 2018/10/17
 */
@Service
@SuppressWarnings("unchecked")
public class SessionServiceImpl implements SessionService {

    private final Cache<Serializable, Session> redisCache;

    @Autowired
    public SessionServiceImpl(RedisCacheManager redisCacheManager) {
        this.redisCache = redisCacheManager.getCache(RedisSessionDAO.SHIRO_SESSION_STORE);
    }

    @Override
    public Result getSessionPage(Page page) {
        List<Session> sessions = (List<Session>) redisCache.values();
        page.setTotal(sessions.size());
        //按会话最后活动时间排序
        sessions.sort(Comparator.comparing(Session::getLastAccessTime));
        page.setRecords(sessions.subList(Long.valueOf(page.offset()).intValue(),
                Long.valueOf(page.offset() + page.getSize()).intValue()));
        return ResultWrapper.success(page);
    }

    @Override
    public Result invalidSession(Serializable sessionId) {
        redisCache.remove(sessionId);
        return ResultWrapper.success();
    }
}
