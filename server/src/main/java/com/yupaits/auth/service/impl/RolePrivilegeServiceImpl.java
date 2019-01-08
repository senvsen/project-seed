package com.yupaits.auth.service.impl;

import com.yupaits.auth.entity.RolePrivilege;
import com.yupaits.auth.mapper.RolePrivilegeMapper;
import com.yupaits.auth.service.IRolePrivilegeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.yupaits.commons.exceptions.ServiceException;;
import com.yupaits.commons.core.identity.RelatedId;;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.google.common.collect.Lists;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.List;

/**
 * <p>
 * 角色-权限 服务实现类
 * </p>
 *
 * @author yupaits
 * @since 2018-11-02
 */
@Slf4j
@Service
public class RolePrivilegeServiceImpl extends ServiceImpl<RolePrivilegeMapper, RolePrivilege> implements IRolePrivilegeService {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean batchSave(RelatedId<Long, Long> relatedId) {
        remove(new QueryWrapper<RolePrivilege>()
                    .eq(StringUtils.camelToUnderline(relatedId.getFirstId().getFieldName()), relatedId.getFirstId().getValue())
                    .notIn(StringUtils.camelToUnderline(relatedId.getSecondIds().getFieldName()), relatedId.getSecondIds().getValues()));
        List<RolePrivilege> addRolePrivilegeList = Lists.newArrayList();
        relatedId.getSecondIds().getValues().forEach(secondId -> {
            RolePrivilege rolePrivilege = getOne(new QueryWrapper<RolePrivilege>()
                    .eq(StringUtils.camelToUnderline(relatedId.getFirstId().getFieldName()), relatedId.getFirstId().getValue())
                    .eq(StringUtils.camelToUnderline(relatedId.getSecondIds().getFieldName()), secondId));
            if (rolePrivilege == null) {
                rolePrivilege = new RolePrivilege();
                try {
                    Field firstField = RolePrivilege.class.getDeclaredField(relatedId.getFirstId().getFieldName());
                    Field secondField = RolePrivilege.class.getDeclaredField(relatedId.getSecondIds().getFieldName());
                    firstField.setAccessible(true);
                    secondField.setAccessible(true);
                    firstField.set(rolePrivilege, relatedId.getFirstId().getValue());
                    secondField.set(rolePrivilege, secondId);
                    addRolePrivilegeList.add(rolePrivilege);
                } catch (IllegalAccessException | NoSuchFieldException e) {
                    log.warn("创建角色-权限关系出错, 参数: {}[{}], {}[{}]", relatedId.getFirstId().getFieldName(),
                            relatedId.getFirstId().getValue(), relatedId.getSecondIds().getFieldName(), secondId, e);
                    throw new ServiceException("创建角色-权限关系出错");
                }
            }
        });
        return addRolePrivilegeList.size() == 0 || saveBatch(addRolePrivilegeList);
    }
}
