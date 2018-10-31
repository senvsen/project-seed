package com.yupaits.sys.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupaits.commons.result.Result;
import com.yupaits.commons.result.ResultWrapper;
import com.yupaits.sys.service.SessionService;
import com.yupaits.web.shiro.redis.RedisCacheManager;
import com.yupaits.web.shiro.redis.RedisSessionDAO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yupaits
 * @date 2018/10/17
 */
@Slf4j
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
        Collection<Session> sessions = redisCache.values();
        int total = sessions.size();
        page.setTotal(total);
        //按会话最后活动时间排序
        List<Session> sessionList = sessions.stream().sorted(Comparator.comparing(Session::getLastAccessTime).reversed())
                .limit(page.getSize()).collect(Collectors.toList());
        page.setRecords(sessionList);
        return ResultWrapper.success(page);
    }

    @Override
    public Result invalidSession(Serializable sessionId) {
        redisCache.remove(sessionId);
        return ResultWrapper.success();
    }
}
