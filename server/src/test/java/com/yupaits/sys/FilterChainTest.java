package com.yupaits.sys;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yupaits.sys.entity.FilterChain;
import com.yupaits.sys.service.IFilterChainService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

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
        FilterChain filterChain = new FilterChain()
                .setUrl("/druid/**")
                .setFilter("anon")
                .setSortCode(0);
        filterChainService.save(filterChain);
    }

    @Test
    public void testDeleteFilterChain() {
        FilterChain filterChain = filterChainService.getOne(new QueryWrapper<FilterChain>().eq("url", "/druid/**").eq("deleted", false));
        filterChainService.removeById(filterChain.getId());
    }

}
