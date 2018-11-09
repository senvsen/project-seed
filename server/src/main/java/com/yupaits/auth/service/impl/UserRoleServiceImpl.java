package com.yupaits.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.yupaits.auth.entity.UserRole;
import com.yupaits.auth.mapper.UserRoleMapper;
import com.yupaits.auth.service.IUserRoleService;
import com.yupaits.commons.core.identity.RelatedId;
import com.yupaits.commons.exceptions.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.List;

;
;

/**
 * <p>
 * 用户-角色 服务实现类
 * </p>
 *
 * @author yupaits
 * @since 2018-11-02
 */
@Slf4j
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean batchSave(RelatedId<Long> relatedId) {
        remove(new QueryWrapper<UserRole>()
                    .eq(StringUtils.camelToUnderline(relatedId.getFirstId().getFieldName()), relatedId.getFirstId().getValue())
                    .notIn(StringUtils.camelToUnderline(relatedId.getSecondIds().getFieldName()), relatedId.getSecondIds().getValues()));
        List<UserRole> addUserRoleList = Lists.newArrayList();
        relatedId.getSecondIds().getValues().forEach(secondId -> {
            UserRole userRole = getOne(new QueryWrapper<UserRole>()
                    .eq(StringUtils.camelToUnderline(relatedId.getFirstId().getFieldName()), relatedId.getFirstId().getValue())
                    .eq(StringUtils.camelToUnderline(relatedId.getSecondIds().getFieldName()), secondId));
            if (userRole == null) {
                userRole = new UserRole();
                try {
                    Field firstField = UserRole.class.getDeclaredField(relatedId.getFirstId().getFieldName());
                    Field secondField = UserRole.class.getDeclaredField(relatedId.getSecondIds().getFieldName());
                    firstField.setAccessible(true);
                    secondField.setAccessible(true);
                    firstField.set(userRole, relatedId.getFirstId().getValue());
                    secondField.set(userRole, secondId);
                    addUserRoleList.add(userRole);
                } catch (IllegalAccessException | NoSuchFieldException e) {
                    log.warn("创建用户-角色关系出错, 参数: {}[{}], {}[{}]", relatedId.getFirstId().getFieldName(),
                            relatedId.getFirstId().getValue(), relatedId.getSecondIds().getFieldName(), secondId, e);
                    throw new ServiceException("创建用户-角色关系出错");
                }
            }
        });
        return addUserRoleList.size() == 0 || saveBatch(addUserRoleList);
    }
}
