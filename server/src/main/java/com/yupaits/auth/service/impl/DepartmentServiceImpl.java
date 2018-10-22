package com.yupaits.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupaits.auth.entity.Department;
import com.yupaits.auth.mapper.DepartmentMapper;
import com.yupaits.auth.service.IDepartmentService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 部门 服务实现类
 * </p>
 *
 * @author yupaits
 * @since 2018-10-20
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

}
