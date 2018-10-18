package com.yupaits.web.shiro.redis;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author yupaits
 * @date 2018/10/17
 */
public class RedisSessionDAO extends AbstractSessionDAO {

    private final Cache<Serializable, Session> redisCache;
    public static final String SHIRO_SESSION_STORE = "shiro:session";

    public RedisSessionDAO(RedisCacheManager redisCacheManager) {
        this.redisCache = redisCacheManager.getCache(SHIRO_SESSION_STORE);
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        redisCache.put(sessionId, session);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        return redisCache.get(sessionId);
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        if (session == null || session.getId() == null) {
            throw new UnknownSessionException();
        }
        redisCache.put(session.getId(), session);
    }

    @Override
    public void delete(Session session) {
        redisCache.remove(session.getId());
    }

    @Override
    public Collection<Session> getActiveSessions() {
        return redisCache.values();
    }
}
