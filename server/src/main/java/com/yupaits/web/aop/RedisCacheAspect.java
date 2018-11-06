package com.yupaits.web.aop;

import com.yupaits.commons.utils.aop.CacheUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * Redis缓存切面
 * @author yupaits
 * @date 2018/11/6
 */
@SuppressWarnings("unchecked")
@Slf4j
@Aspect
@Order(2)
@Component
public class RedisCacheAspect {

    private final RedisTemplate redisTemplate;
    private static final String FETCH_CACHE_ADVICES = "execution(public * com.yupaits.*.controller..*.get*(..)) " +
            "&& !@annotation(com.yupaits.commons.annotation.NoCache)";
    private static final String EVICT_CACHE_ADVICES = "(" +
            "execution(public * com.yupaits.*.controller..*.add*(..)) " +
            "|| execution(public * com.yupaits.*.controller..*.delete*(..)) " +
            "|| execution(public * com.yupaits.*.controller..*.update*(..)) " +
            "|| execution(public * com.yupaits.*.controller..*.batch*(..))" +
            ") && !@annotation(com.yupaits.commons.annotation.NoCache)";

    @Autowired
    public RedisCacheAspect(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Around(FETCH_CACHE_ADVICES)
    public Object getCache(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        //获取类名、方法名、参数
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        Object[] params = joinPoint.getArgs();

        //生成缓存相关Key
        String cacheStore = CacheUtils.genCacheStoreKey(className);
        String cacheKey = CacheUtils.genCacheKey(methodName, params);

        //根据缓存key、field尝试获取缓存信息
        Object value = redisTemplate.opsForHash().get(cacheStore, cacheKey);

        Object result;

        if (null == value) {
            //缓存未命中
            result = joinPoint.proceed(params);
            redisTemplate.opsForHash().put(cacheStore, cacheKey, result);
            log.debug("spend: {}ms, set cache[{}, {}]", System.currentTimeMillis() - startTime, cacheStore, cacheKey);
        } else {
            //缓存命中
            log.debug("spend: {}ms, hit cache[{}, {}]", System.currentTimeMillis() - startTime, cacheStore, cacheKey);
            result = value;
        }
        return result;
    }

    @Around(EVICT_CACHE_ADVICES)
    public Object evictCache(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        String cacheStore = CacheUtils.genCacheStoreKey(joinPoint.getSignature().getDeclaringTypeName());
        redisTemplate.delete(cacheStore);
        log.debug("spend: {}ms, evict cache[{}]", System.currentTimeMillis() - startTime, cacheStore);
        return joinPoint.proceed(joinPoint.getArgs());
    }
}
