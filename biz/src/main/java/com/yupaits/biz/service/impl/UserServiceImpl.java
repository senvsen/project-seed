package com.yupaits.biz.service.impl;

import com.yupaits.biz.entity.User;
import com.yupaits.biz.mapper.UserMapper;
import com.yupaits.biz.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yupaits
 * @since 2018-10-15
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
