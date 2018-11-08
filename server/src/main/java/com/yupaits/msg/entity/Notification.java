package com.yupaits.msg.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 通知
 * </p>
 *
 * @author yupaits
 * @since 2018-11-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("msg_notification")
public class Notification extends Model<Notification> {

    private static final long serialVersionUID = 1L;

    /**
     * 通知ID
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 操作者ID
     */
    private Long operator;

    /**
     * 动作
     */
    private Integer action;

    /**
     * 目标对象ID
     */
    private Long targetId;

    /**
     * 目标对象类型
     */
    private Integer targetType;

    /**
     * 目标对象所有者
     */
    private Long targetOwner;

    /**
     * 发送时间
     */
    private LocalDateTime sendAt;

    /**
     * 已读标记
     */
    private Boolean hasRead;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}