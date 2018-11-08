package com.yupaits.msg.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.yupaits.commons.core.serializer.LongDeserializer;
import com.yupaits.commons.core.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * TemplateUpdate
 * </p>
 *
 * @author yupaits
 * @date 2018-11-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "TemplateUpdate", description = "TemplateUpdate对象")
public class TemplateUpdate extends BaseDTO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "消息模板ID")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long id;

    @ApiModelProperty(value = "模板名称")
    private String name;

    @ApiModelProperty(value = "消息类型")
    private Integer msgType;

    @ApiModelProperty(value = "模板格式")
    private String templatePattern;

    @ApiModelProperty(value = "模板填充字段")
    private String fillFields;

    @Override
    @ApiModelProperty(hidden = true)
    public boolean isValid() {
        return true;
    }

    public void setFillFields(List<String> fillFields) {
        this.fillFields = JSON.toJSONString(fillFields);
    }
}
