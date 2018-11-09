package com.yupaits.msg.dto;

import com.alibaba.fastjson.JSON;
import com.yupaits.commons.consts.enums.MsgType;
import com.yupaits.commons.core.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

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
    private MsgType msgType;

    @ApiModelProperty(value = "模板格式")
    private String templatePattern;

    @ApiModelProperty(value = "模板填充字段")
    private List<String> fillFields;

    @Override
    @ApiModelProperty(hidden = true)
    public boolean isValid() {
        return true;
    }

    public String getFillFields() {
        return JSON.toJSONString(fillFields);
    }
}
