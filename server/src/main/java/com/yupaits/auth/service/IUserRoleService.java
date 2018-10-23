package com.yupaits.auth.service;

import com.yupaits.auth.entity.UserRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yupaits.commons.core.identity.RelatedId;;

/**
 * <p>
 * 用户-角色 服务类
 * </p>
 *
 * @author yupaits
 * @since 2018-10-23
 */
public interface IUserRoleService extends IService<UserRole> {

    /**
     * 增量保存用户-角色关系
     * @param relatedId 关联关系
     * @return 保存结果
     */
    boolean batchSave(RelatedId relatedId);
}
