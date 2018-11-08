package com.yupaits.msg.service.impl;

import com.yupaits.msg.entity.Notification;
import com.yupaits.msg.mapper.NotificationMapper;
import com.yupaits.msg.service.INotificationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 通知 服务实现类
 * </p>
 *
 * @author yupaits
 * @since 2018-11-08
 */
@Service
public class NotificationServiceImpl extends ServiceImpl<NotificationMapper, Notification> implements INotificationService {

}
