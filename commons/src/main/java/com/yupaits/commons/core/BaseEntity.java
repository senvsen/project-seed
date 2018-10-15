package com.yupaits.commons.core;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author yupaits
 * @date 2018/10/15
 */
public class BaseEntity extends Model implements Serializable {

    @TableId(type = IdType.ID_WORKER)
    private Long id;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime lastModifiedAt;

    @TableField(fill = FieldFill.INSERT)
    private Long createdBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long lastModifiedBy;

    @TableLogic
    private Integer deleted;

    @Version
    private Integer version;

    @Override
    protected Serializable pkVal() {
        return id;
    }
}
