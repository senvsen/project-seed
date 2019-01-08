package com.yupaits.msg.service;

import com.yupaits.msg.entity.MessageUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yupaits.commons.core.identity.RelatedId;;

/**
 * <p>
 * 消息-用户 服务类
 * </p>
 *
 * @author yupaits
 * @since 2018-11-08
 */
public interface IMessageUserService extends IService<MessageUser> {

    /**
     * 增量保存消息-用户关系
     * @param relatedId 关联关系
     * @return 保存结果
     */
    boolean batchSave(RelatedId<Long, Long> relatedId);
}
