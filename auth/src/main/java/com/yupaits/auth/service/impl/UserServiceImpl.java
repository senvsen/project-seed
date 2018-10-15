package com.yupaits.auth.service.impl;

import com.yupaits.auth.entity.User;
import com.yupaits.auth.mapper.UserMapper;
import com.yupaits.auth.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yupaits
 * @since 2018-10-16
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
