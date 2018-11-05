package com.yupaits.schedule.dto;

import com.yupaits.commons.core.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * JobCreate
 * </p>
 *
 * @author yupaits
 * @date 2018-11-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "JobCreate", description = "JobCreate对象")
public class JobCreate extends BaseDTO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "任务执行类路径")
    private String className;

    @ApiModelProperty(value = "CRON表达式")
    private String cronExpression;

    @ApiModelProperty(value = "任务名称")
    private String jobName;

    @ApiModelProperty(value = "任务组")
    private String jobGroup;

    @ApiModelProperty(value = "触发器名称")
    private String triggerName;

    @ApiModelProperty(value = "触发器组")
    private String triggerGroup;

    @ApiModelProperty(value = "已暂停")
    private boolean paused;

    @ApiModelProperty(value = "定时任务描述")
    private String description;

    @Override
    @ApiModelProperty(hidden = true)
    public boolean isValid() {
        return true;
    }

}
