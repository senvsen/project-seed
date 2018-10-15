package com.yupaits.commons.core;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;

import java.io.Serializable;

/**
 * @author yupaits
 * @date 2018/10/15
 */
public abstract class BaseEntity implements Serializable {

    @TableId(type = IdType.ID_WORKER)
    private Integer id;

    @TableLogic
    private Integer deleted;

    @Version
    private Integer version;
}
