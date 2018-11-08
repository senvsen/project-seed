package com.yupaits.msg.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 消息
 * </p>
 *
 * @author yupaits
 * @since 2018-11-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("msg_message")
public class Message extends Model<Message> {

    private static final long serialVersionUID = 1L;

    /**
     * 消息ID
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 消息类型
     */
    private Integer msgType;

    /**
     * 使用消息模板
     */
    private Boolean useTemplate;

    /**
     * 消息模板ID
     */
    private Long msgTemplateId;

    /**
     * 模板参数
     */
    private String payload;

    /**
     * 消息内容
     */
    private String content;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}