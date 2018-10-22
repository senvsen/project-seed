package com.yupaits.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupaits.sys.entity.FilterChain;
import com.yupaits.sys.mapper.FilterChainMapper;
import com.yupaits.sys.service.IFilterChainService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 权限过滤链 服务实现类
 * </p>
 *
 * @author yupaits
 * @since 2018-10-20
 */
@Service
public class FilterChainServiceImpl extends ServiceImpl<FilterChainMapper, FilterChain> implements IFilterChainService {

}
