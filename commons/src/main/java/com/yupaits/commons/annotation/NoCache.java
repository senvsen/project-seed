package com.yupaits.commons.annotation;

import java.lang.annotation.*;

/**
 * 该注解用户标注不需要使用缓存的类
 * @author yupaits
 * @date 2018/11/6
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NoCache {
}
