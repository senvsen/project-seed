package com.yupaits.web.shiro.redis;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.Set;

/**
 * 使用Redis的Hash数据结构实现Cache
 * @author yupaits
 * @date 2018/10/17
 */
@SuppressWarnings("unchecked")
public class RedisCache<K, V> implements Cache<K, V> {

    private final RedisTemplate redisTemplate;
    private String cacheStore;

    public RedisCache(RedisTemplate redisTemplate, String cacheStore) {
        this.redisTemplate = redisTemplate;
        this.cacheStore = cacheStore;
    }

    @Override
    public V get(K k) throws CacheException {
        return (V) redisTemplate.opsForHash().get(cacheStore, k);
    }

    @Override
    public V put(K k, V v) throws CacheException {
        redisTemplate.opsForHash().put(cacheStore, k, v);
        return v;
    }

    @Override
    public V remove(K k) throws CacheException {
        V value = (V) redisTemplate.opsForHash().get(cacheStore, k);
        long count = redisTemplate.opsForHash().delete(cacheStore, k);
        if (count > 0) {
            return value;
        }
        return null;
    }

    @Override
    public void clear() throws CacheException {
        redisTemplate.delete(cacheStore);
    }

    @Override
    public int size() {
        return redisTemplate.opsForHash().size(cacheStore).intValue();
    }

    @Override
    public Set<K> keys() {
        return redisTemplate.opsForHash().keys(cacheStore);
    }

    @Override
    public Collection<V> values() {
        return redisTemplate.opsForHash().values(cacheStore);
    }

    @Override
    public String toString() {
        return "RedisCache '" + cacheStore + "' (" + size() + " 条记录)";
    }
}
