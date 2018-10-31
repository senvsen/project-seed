package com.yupaits.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.yupaits.auth.entity.RolePrivilege;
import com.yupaits.auth.mapper.RolePrivilegeMapper;
import com.yupaits.auth.service.IRolePrivilegeService;
import com.yupaits.commons.core.identity.RelatedId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

;

/**
 * <p>
 * 角色-权限 服务实现类
 * </p>
 *
 * @author yupaits
 * @since 2018-10-31
 */
@Slf4j
@Service
public class RolePrivilegeServiceImpl extends ServiceImpl<RolePrivilegeMapper, RolePrivilege> implements IRolePrivilegeService {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean batchSave(RelatedId relatedId) {
        remove(new QueryWrapper<RolePrivilege>()
                    .eq(relatedId.getFirstId().getFieldName(), relatedId.getFirstId().getValue())
                    .notIn(relatedId.getSecondIds().getFieldName(), relatedId.getSecondIds().getValues()));
        List<RolePrivilege> addRolePrivilegeList = Lists.newArrayList();
        relatedId.getSecondIds().getValues().forEach(secondId -> {
            RolePrivilege rolePrivilege = getOne(new QueryWrapper<RolePrivilege>()
                    .eq("deleted", false)
                    .eq(relatedId.getFirstId().getFieldName(), relatedId.getFirstId().getValue())
                    .eq(relatedId.getSecondIds().getFieldName(), secondId));
            if (rolePrivilege == null) {
                rolePrivilege = new RolePrivilege();
                try {
                    RolePrivilege.class.getDeclaredField(relatedId.getFirstId().getFieldName()).set(rolePrivilege, relatedId.getFirstId().getValue());
                    RolePrivilege.class.getDeclaredField(relatedId.getSecondIds().getFieldName()).set(rolePrivilege, secondId);
                    addRolePrivilegeList.add(rolePrivilege);
                } catch (IllegalAccessException | NoSuchFieldException e) {
                    log.warn("创建角色-权限关系出错, 参数: {}[{}], {}[{}]", relatedId.getFirstId().getFieldName(),
                            relatedId.getFirstId().getValue(), relatedId.getSecondIds().getFieldName(), secondId, e);
                }
            }
        });
        return addRolePrivilegeList.size() == 0 || saveBatch(addRolePrivilegeList);
    }
}
