package com.yupaits.msg.service.impl;

import com.yupaits.msg.entity.Message;
import com.yupaits.msg.mapper.MessageMapper;
import com.yupaits.msg.service.IMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 消息 服务实现类
 * </p>
 *
 * @author yupaits
 * @since 2018-11-08
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {

}
