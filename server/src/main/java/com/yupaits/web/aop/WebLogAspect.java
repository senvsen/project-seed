package com.yupaits.web.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * API请求日志切面
 * @author yupaits
 * @date 2018/11/6
 */
@Slf4j
@Aspect
@Order(Ordered.LOWEST_PRECEDENCE - 1)
@Component
public class WebLogAspect {

    private static final String CLASS_METHOD_DELIMITER = ".";
    private static final String FLAG_KEY = "web:request:flag";
    private static final String WEB_LOG_POINT_CUT = "execution(public * com.yupaits.*.controller..*.*(..))";

    private ThreadLocal<Long> startTime = new ThreadLocal<>();
    private ThreadLocal<Long> flag = new ThreadLocal<>();
    private RedisAtomicLong requestFlag;

    @Autowired
    @SuppressWarnings("unchecked")
    public WebLogAspect(RedisConnectionFactory redisConnectionFactory) {
        this.requestFlag = new RedisAtomicLong(FLAG_KEY, redisConnectionFactory);
    }

    @Pointcut(WEB_LOG_POINT_CUT)
    public void webLog() {}

    @Before("webLog()")
    public void deBefore(JoinPoint joinPoint) {
        startTime.set(System.currentTimeMillis());
        flag.set(requestFlag.incrementAndGet());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //记录请求日志
        log.info("flag: {}, method: {}, url: {}, ip: {}, class_method: {}, params: {}",
                flag.get(),
                request.getMethod(),
                request.getRequestURL().toString(),
                request.getRemoteAddr(),
                joinPoint.getSignature().getDeclaringTypeName() + CLASS_METHOD_DELIMITER + joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(returning = "result", pointcut = "webLog()")
    public void doAfterReturning(Object result) {
        log.info("flag: {}, spend: {}ms, result: {}", flag.get(), System.currentTimeMillis() - startTime.get(), result);
        startTime.remove();
        flag.remove();
    }
}
