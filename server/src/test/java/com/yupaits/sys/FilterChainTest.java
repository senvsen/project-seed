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
        FilterChain filterChain1 = new FilterChain().setUrl("/index").setFilter("authc").setSortCode(1);
        FilterChain filterChain2 = new FilterChain().setUrl("/auth/**").setFilter("authc").setSortCode(2);
        FilterChain filterChain3 = new FilterChain().setUrl("/login").setFilter("anon").setSortCode(3);
        FilterChain filterChain4 = new FilterChain().setUrl("/static/**").setFilter("anon").setSortCode(4);
        filterChainService.saveBatch(Lists.newArrayList(filterChain1, filterChain2, filterChain3, filterChain4));
    }

    @Test
    public void testDeleteFilterChain() {
        List<FilterChain> filterChainList = filterChainService.list(new QueryWrapper<FilterChain>().likeRight("url", "/"));
        filterChainService.removeByIds(filterChainList.stream().map(FilterChain::getId).collect(Collectors.toList()));
    }

}
