package com.yupaits.web.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Maps;
import com.yupaits.sys.entity.FilterChain;
import com.yupaits.sys.service.IFilterChainService;
import com.yupaits.web.shiro.redis.RedisCacheManager;
import com.yupaits.web.shiro.redis.RedisSessionDAO;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Map;

/**
 * Shiro配置
 * @author yupaits
 * @date 2018/10/16
 */
@Configuration
public class ShiroConfig {

    private final RedisTemplate redisTemplate;
    private final UserRealm userRealm;
    private final IFilterChainService filterChainService;
    private static byte[] rememberMeCipherKey = Base64.decode("aG93Y2FuaWdldHlvdWh5ag==");

    @Autowired
    public ShiroConfig(RedisTemplate redisTemplate, UserRealm userRealm, IFilterChainService filterChainService) {
        this.redisTemplate = redisTemplate;
        this.userRealm = userRealm;
        this.filterChainService = filterChainService;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager());
        factoryBean.setLoginUrl("/login");
        factoryBean.setSuccessUrl("/index");
        factoryBean.setUnauthorizedUrl("/403");
        List<FilterChain> filterChainList = filterChainService.list(
                new QueryWrapper<FilterChain>().orderByAsc("sort_code"));
        Map<String, String> filterChains = Maps.newLinkedHashMap();
        filterChainList.forEach(filterChain -> {
            filterChains.put(filterChain.getUrl(), filterChain.getFilter());
        });
        factoryBean.setFilterChainDefinitionMap(filterChains);
        return factoryBean;
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        securityManager.setCacheManager(redisCacheManager());
        securityManager.setSessionManager(sessionManager());
        securityManager.setRememberMeManager(rememberMeManager());
        return securityManager;
    }

    /**
     * 基于redis的CacheManager实现
     */
    @Bean
    public RedisCacheManager redisCacheManager() {
        return new RedisCacheManager(redisTemplate);
    }

    /**
     * 基于redis的SessionManager
     */
    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO());
        sessionManager.setSessionValidationSchedulerEnabled(true);
        return sessionManager;
    }

    /**
     * 基于redis的SessionDAO实现
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        return new RedisSessionDAO(redisCacheManager());
    }

    /**
     * 记住我Cookie
     */
    private SimpleCookie rememberMeCookie() {
        SimpleCookie cookie = new SimpleCookie("rememberMe");
        //记住我Cookie生效时间：7天
        cookie.setMaxAge(604800);
        return cookie;
    }

    private CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
        rememberMeManager.setCookie(rememberMeCookie());
        rememberMeManager.setCipherKey(rememberMeCipherKey);
        return rememberMeManager;
    }
}
