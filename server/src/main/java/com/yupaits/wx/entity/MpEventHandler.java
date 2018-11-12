package com.yupaits.wx.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Arrays;

import com.yupaits.commons.utils.ValidateUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import me.chanjar.weixin.common.api.WxConsts;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * <p>
 * 公众号事件处理
 * </p>
 *
 * @author yupaits
 * @since 2018-11-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wx_mp_event_handler")
public class MpEventHandler extends Model<MpEventHandler> {

    private static final long serialVersionUID = 1L;

    /**
     * 事件处理ID
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 公众号账户ID
     */
    private Long accountId;

    /**
     * 事件类型
     */
    private String eventType;

    /**
     * 处理类路径
     */
    private String handlerClass;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    /**
     * 创建人ID
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createdBy;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    /**
     * 更新人ID
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updatedBy;

    /**
     * 删除标记
     */
    @TableField(fill = FieldFill.INSERT)
    @TableLogic
    private Boolean deleted;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public boolean isValid() {
        if (StringUtils.isBlank(eventType) || StringUtils.isBlank(handlerClass)) {
            return false;
        }
        //检查事件类型
        Field[] fields = WxConsts.EventType.class.getFields();
        Object[] fieldNames = Arrays.stream(fields).map(Field::getName).toArray();
        if (!eventTypeIsValid(fieldNames, eventType)) {
            return false;
        }
        //检查处理类路径
        try {
            Class.forName(handlerClass);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    private boolean eventTypeIsValid(Object[] fieldNames, String eventType) {
        if (ArrayUtils.isEmpty(fieldNames)) {
            return false;
        }
        for (Object name : fieldNames) {
            if (eventType.toUpperCase().equals(name)) {
                return true;
            }
        }
        return false;
    }
}