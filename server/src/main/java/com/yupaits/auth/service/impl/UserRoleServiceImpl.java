package com.yupaits.auth.service.impl;

import com.yupaits.auth.entity.UserRole;
import com.yupaits.auth.mapper.UserRoleMapper;
import com.yupaits.auth.service.IUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户-角色 服务实现类
 * </p>
 *
 * @author yupaits
 * @since 2018-10-19
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

}
