package com.yupaits.msg.service.impl;

import com.yupaits.msg.entity.MessageUser;
import com.yupaits.msg.mapper.MessageUserMapper;
import com.yupaits.msg.service.IMessageUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupaits.web.mq.MsgSender;
import org.springframework.beans.factory.annotation.Autowired;
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
 * 消息-用户 服务实现类
 * </p>
 *
 * @author yupaits
 * @since 2018-11-08
 */
@Slf4j
@Service
public class MessageUserServiceImpl extends ServiceImpl<MessageUserMapper, MessageUser> implements IMessageUserService {

    private final MsgSender msgSender;

    @Autowired
    public MessageUserServiceImpl(MsgSender msgSender) {
        this.msgSender = msgSender;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean batchSave(RelatedId<Long, Long> relatedId) {
        remove(new QueryWrapper<MessageUser>()
                    .eq(StringUtils.camelToUnderline(relatedId.getFirstId().getFieldName()), relatedId.getFirstId().getValue())
                    .notIn(StringUtils.camelToUnderline(relatedId.getSecondIds().getFieldName()), relatedId.getSecondIds().getValues()));
        List<MessageUser> addMessageUserList = Lists.newArrayList();
        relatedId.getSecondIds().getValues().forEach(secondId -> {
            MessageUser messageUser = new MessageUser();
            try {
                Field firstField = MessageUser.class.getDeclaredField(relatedId.getFirstId().getFieldName());
                Field secondField = MessageUser.class.getDeclaredField(relatedId.getSecondIds().getFieldName());
                firstField.setAccessible(true);
                secondField.setAccessible(true);
                firstField.set(messageUser, relatedId.getFirstId().getValue());
                secondField.set(messageUser, secondId);
                addMessageUserList.add(messageUser);
            } catch (IllegalAccessException | NoSuchFieldException e) {
                log.warn("创建消息-用户关系出错, 参数: {}[{}], {}[{}]", relatedId.getFirstId().getFieldName(),
                        relatedId.getFirstId().getValue(), relatedId.getSecondIds().getFieldName(), secondId, e);
                throw new ServiceException("创建消息-用户关系出错");
            }
        });
        boolean batchSaveResult = addMessageUserList.size() == 0 || saveBatch(addMessageUserList);
        if (batchSaveResult) {
            //将消息添加到消息队列，由消息队列消费者处理实际的消息发送业务
            addMessageUserList.forEach(msgSender::send);
        }
        return batchSaveResult;
    }
}
