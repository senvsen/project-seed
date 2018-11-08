package com.yupaits.msg.vo;

import java.time.LocalDateTime;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yupaits.commons.core.serializer.LongSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 消息模板VO
 * </p>
 *
 * @author yupaits
 * @date 2018-11-08
 */
@Data
@ApiModel(value = "TemplateVO", description = "TemplateVO对象")
public class TemplateVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "消息模板ID")
    @JsonSerialize(using = LongSerializer.class)
    private Long id;

    @ApiModelProperty(value = "模板名称")
    private String name;

    @ApiModelProperty(value = "消息类型")
    private Integer msgType;

    @ApiModelProperty(value = "模板格式")
    private String templatePattern;

    @ApiModelProperty(value = "模板填充字段")
    private String fillFields;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createdAt;

    @ApiModelProperty(value = "创建人ID")
    @JsonSerialize(using = LongSerializer.class)
    private Long createdBy;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updatedAt;

    @ApiModelProperty(value = "更新人ID")
    @JsonSerialize(using = LongSerializer.class)
    private Long updatedBy;

    public List<String> getFillFields() {
        return JSON.parseArray(fillFields, String.class);
    }
}
