package com.yupaits.auth.service.impl;

import com.yupaits.auth.entity.Role;
import com.yupaits.auth.mapper.RoleMapper;
import com.yupaits.auth.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色 服务实现类
 * </p>
 *
 * @author yupaits
 * @since 2018-10-31
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
