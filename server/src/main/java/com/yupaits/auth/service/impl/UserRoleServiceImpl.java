package com.yupaits.auth.service.impl;

import com.yupaits.auth.entity.UserRole;
import com.yupaits.auth.mapper.UserRoleMapper;
import com.yupaits.auth.service.IUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.yupaits.commons.core.identity.RelatedId;;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * <p>
 * 用户-角色 服务实现类
 * </p>
 *
 * @author yupaits
 * @since 2018-10-30
 */
@Slf4j
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean batchSave(RelatedId relatedId) {
        remove(new QueryWrapper<UserRole>()
                    .eq(relatedId.getFirstId().getFieldName(), relatedId.getFirstId().getValue())
                    .notIn(relatedId.getSecondIds().getFieldName(), relatedId.getSecondIds().getValues()));
        List<UserRole> addUserRoleList = Lists.newArrayList();
        relatedId.getSecondIds().getValues().forEach(secondId -> {
            UserRole userRole = getOne(new QueryWrapper<UserRole>()
                    .eq("deleted", false)
                    .eq(relatedId.getFirstId().getFieldName(), relatedId.getFirstId().getValue())
                    .eq(relatedId.getSecondIds().getFieldName(), secondId));
            if (userRole == null) {
                userRole = new UserRole();
                try {
                    UserRole.class.getDeclaredField(relatedId.getFirstId().getFieldName()).set(userRole, relatedId.getFirstId().getValue());
                    UserRole.class.getDeclaredField(relatedId.getSecondIds().getFieldName()).set(userRole, secondId);
                    addUserRoleList.add(userRole);
                } catch (IllegalAccessException | NoSuchFieldException e) {
                    log.warn("创建用户-角色关系出错, 参数: {}[{}], {}[{}]", relatedId.getFirstId().getFieldName(),
                            relatedId.getFirstId().getValue(), relatedId.getSecondIds().getFieldName(), secondId, e);
                }
            }
        });
        return addUserRoleList.size() == 0 || saveBatch(addUserRoleList);
    }
}
