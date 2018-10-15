package com.yupaits.biz.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author yupaits
 * @date 2018/10/15
 */
@Configuration
@MapperScan("com.yupaits.biz.mapper")
public class MybatisPlusConfig {
}
