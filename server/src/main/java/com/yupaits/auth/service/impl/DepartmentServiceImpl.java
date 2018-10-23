package com.yupaits.auth.service.impl;

import com.yupaits.auth.entity.Department;
import com.yupaits.auth.mapper.DepartmentMapper;
import com.yupaits.auth.service.IDepartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 部门 服务实现类
 * </p>
 *
 * @author yupaits
 * @since 2018-10-23
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

}
