package com.yupaits.auth.service;

import com.yupaits.auth.entity.RolePrivilege;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yupaits.commons.core.identity.RelatedId;;

/**
 * <p>
 * 角色-权限 服务类
 * </p>
 *
 * @author yupaits
 * @since 2018-10-31
 */
public interface IRolePrivilegeService extends IService<RolePrivilege> {

    /**
     * 增量保存角色-权限关系
     * @param relatedId 关联关系
     * @return 保存结果
     */
    boolean batchSave(RelatedId relatedId);
}
