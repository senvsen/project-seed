package com.yupaits.sys.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupaits.commons.result.Result;
import com.yupaits.commons.result.ResultWrapper;
import com.yupaits.sys.service.SessionService;
import com.yupaits.web.shiro.redis.RedisCacheManager;
import com.yupaits.web.shiro.redis.RedisSessionDAO;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SimpleSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        Set<Serializable> sessionKeys = redisCache.keys();
        int total = sessionKeys.size();
        page.setTotal(total);
        //按会话最后活动时间排序
        List<Session> sessions = sessionKeys.stream().map(sessionKey -> {
            SimpleSession session = (SimpleSession) redisCache.get(sessionKey);
            session.setId(sessionKey);
            return session;
        }).sorted(Comparator.comparing(Session::getLastAccessTime)).collect(Collectors.toList());
        if (page.offset() <= (total - 1)) {
            int fromIndex = Long.valueOf(page.offset()).intValue();
            int toIndex = Long.valueOf(page.offset() + page.getSize()).intValue();
            page.setRecords(sessions.subList(fromIndex, toIndex > total ? total : toIndex));
        }
        return ResultWrapper.success(page);
    }

    @Override
    public Result invalidSession(Serializable sessionId) {
        redisCache.remove(sessionId);
        return ResultWrapper.success();
    }
}
