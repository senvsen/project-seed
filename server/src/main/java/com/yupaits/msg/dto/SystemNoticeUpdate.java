package com.yupaits.msg.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.yupaits.commons.consts.enums.MsgLevel;
import com.yupaits.commons.core.BaseDTO;
import com.yupaits.commons.core.serializer.LongDeserializer;
import com.yupaits.commons.utils.ValidateUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * <p>
 * SystemNoticeUpdate
 * </p>
 *
 * @author yupaits
 * @date 2018-11-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SystemNoticeUpdate", description = "SystemNoticeUpdate对象")
public class SystemNoticeUpdate extends BaseDTO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "系统公告ID", required = true)
    @JsonDeserialize(using = LongDeserializer.class)
    private Long id;

    @ApiModelProperty(value = "公告等级", required = true)
    private MsgLevel msgLevel;

    @ApiModelProperty(value = "公告内容", required = true)
    private String msgContent;

    @ApiModelProperty(value = "开始时间", required = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "结束时间", required = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    @Override
    @ApiModelProperty(hidden = true)
    public boolean isValid() {
        return ValidateUtils.idValid(id) && msgLevel != null && StringUtils.isNotBlank(msgContent) && startTime != null
                && endTime != null && startTime.isBefore(endTime);
    }

}
