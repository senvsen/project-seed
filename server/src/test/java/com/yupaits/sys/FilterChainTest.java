package com.yupaits.sys;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yupaits.sys.entity.FilterChain;
import com.yupaits.sys.service.IFilterChainService;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yupaits
 * @date 2018/10/18
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class FilterChainTest {

    @Autowired
    private IFilterChainService filterChainService;

    @Test
    public void testAddFilterChain() {
        List<FilterChain> filterChains = Lists.newArrayList(
                new FilterChain().setUrl("/favicon.ico").setFilter("anon"),
                new FilterChain().setUrl("/login").setFilter("anon"),
                new FilterChain().setUrl("/css/**.css").setFilter("anon"),
                new FilterChain().setUrl("/js/**.js").setFilter("anon"),
                new FilterChain().setUrl("/img/**").setFilter("anon"),
                new FilterChain().setUrl("/logout").setFilter("logout"),
                new FilterChain().setUrl("/auth/**").setFilter("authc"),
                new FilterChain().setUrl("/**").setFilter("user")
        );
        int sortCode = 1;
        for (FilterChain filterChain : filterChains) {
            filterChain.setSortCode(sortCode++);
        }
        filterChainService.saveBatch(filterChains);
    }

    @Test
    public void testDeleteFilterChain() {
        List<FilterChain> filterChainList = filterChainService.list(new QueryWrapper<FilterChain>().likeRight("url", "/"));
        filterChainService.removeByIds(filterChainList.stream().map(FilterChain::getId).collect(Collectors.toList()));
    }

}
