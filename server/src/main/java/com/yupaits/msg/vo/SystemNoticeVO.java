package com.yupaits.msg.vo;

import java.time.LocalDateTime;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yupaits.commons.core.serializer.LongSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 系统公告VO
 * </p>
 *
 * @author yupaits
 * @date 2018-11-08
 */
@Data
@ApiModel(value = "SystemNoticeVO", description = "SystemNoticeVO对象")
public class SystemNoticeVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "系统公告ID")
    @JsonSerialize(using = LongSerializer.class)
    private Long id;

    @ApiModelProperty(value = "公告等级")
    private Integer msgLevel;

    @ApiModelProperty(value = "公告内容")
    private String msgContent;

    @ApiModelProperty(value = "开始时间")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "结束时间")
    private LocalDateTime endTime;

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
}
