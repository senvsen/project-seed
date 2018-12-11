package com.yupaits.wx.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.yupaits.commons.core.serializer.LongDeserializer;
import com.yupaits.commons.core.BaseDTO;
import com.yupaits.commons.utils.ValidateUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.chanjar.weixin.common.api.WxConsts;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * <p>
 * MpEventHandlerUpdate
 * </p>
 *
 * @author yupaits
 * @date 2018-11-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "MpEventHandlerUpdate", description = "MpEventHandlerUpdate对象")
public class MpEventHandlerUpdate extends BaseDTO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "事件处理ID")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long id;

    @ApiModelProperty(value = "公众号账户ID")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long accountId;

    @ApiModelProperty(value = "事件类型")
    private String eventType;

    @ApiModelProperty(value = "处理类路径")
    private String handlerClass;

    @Override
    @ApiModelProperty(hidden = true)
    public boolean isValid() {
        if (!ValidateUtils.idValid(this.id) || StringUtils.isAnyBlank(eventType, handlerClass)) {
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
