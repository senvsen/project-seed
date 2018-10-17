package com.yupaits.web.shiro.redis;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 基于redis的CacheManager实现
 * @author yupaits
 * @date 2018/10/17
 */
@SuppressWarnings("unchecked")
public class RedisCacheManager implements CacheManager {

    private final RedisTemplate redisTemplate;

    public RedisCacheManager(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        return new RedisCache<>(redisTemplate, name);
    }
}
