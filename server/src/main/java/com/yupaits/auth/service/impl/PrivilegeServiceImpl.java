package com.yupaits.auth.service.impl;

import com.yupaits.auth.entity.Privilege;
import com.yupaits.auth.mapper.PrivilegeMapper;
import com.yupaits.auth.service.IPrivilegeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author yupaits
 * @since 2018-10-18
 */
@Service
public class PrivilegeServiceImpl extends ServiceImpl<PrivilegeMapper, Privilege> implements IPrivilegeService {

}
