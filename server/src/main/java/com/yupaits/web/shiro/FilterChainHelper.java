package com.yupaits.web.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Maps;
import com.yupaits.sys.entity.FilterChain;
import com.yupaits.sys.service.IFilterChainService;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

/**
 * 权限过滤链辅助类
 * @author yupaits
 * @date 2018/10/17
 */
@Component
public class FilterChainHelper {

    private final ShiroFilterFactoryBean shiroFilterFactoryBean;
    private final IFilterChainService filterChainService;

    @Autowired
    public FilterChainHelper(ShiroFilterFactoryBean shiroFilterFactoryBean, IFilterChainService filterChainService) {
        this.shiroFilterFactoryBean = shiroFilterFactoryBean;
        this.filterChainService = filterChainService;
    }

    /**
     * 重新加载权限过滤链
     */
    public void reloadFilterChains() throws Exception {
        synchronized (shiroFilterFactoryBean) {
            AbstractShiroFilter shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean.getObject();
            Assert.notNull(shiroFilter, "ShiroFilter为空!");
            PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter.getFilterChainResolver();
            DefaultFilterChainManager filterChainManager = (DefaultFilterChainManager) filterChainResolver.getFilterChainManager();
            //清空老的权限过滤链
            filterChainManager.getFilterChains().clear();
            shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
            //重新构建生成
            Map<String, String> filterChains = loadFilterChains();
            shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChains);
            filterChains.forEach(filterChainManager::createChain);
        }
    }

    /**
     * 从数据库中获取权限过滤链信息
     */
    private Map<String, String> loadFilterChains() {
        Map<String, String> filterChains = Maps.newLinkedHashMap();
        List<FilterChain> filterChainList = filterChainService.list(new QueryWrapper<>());
        filterChainList.forEach(filterChain -> {
            filterChains.put(filterChain.getUrl(), filterChain.getFilter());
        });
        return filterChains;
    }
}
