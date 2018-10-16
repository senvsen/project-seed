package com.yupaits.commons.core;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author yupaits
 * @date 2018/10/15
 */
@Data
public class BaseEntity extends Model implements Serializable {

    @ApiModelProperty(value = "主键ID")
    @TableId(type = IdType.ID_WORKER)
    private Long id;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @ApiModelProperty(value = "创建人ID")
    @TableField(fill = FieldFill.INSERT)
    private Long createdBy;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    @ApiModelProperty(value = "更新人ID")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updatedBy;

    @ApiModelProperty(value = "删除标记")
    @TableLogic
    private Integer deleted;

    @ApiModelProperty(value = "乐观锁version")
    @Version
    private Integer version;

    @Override
    protected Serializable pkVal() {
        return id;
    }
}