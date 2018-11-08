package com.yupaits.msg.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.yupaits.commons.core.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * TemplateCreate
 * </p>
 *
 * @author yupaits
 * @date 2018-11-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "TemplateCreate", description = "TemplateCreate对象")
public class TemplateCreate extends BaseDTO {

    private static final long serialVersionUID = 1L;

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
