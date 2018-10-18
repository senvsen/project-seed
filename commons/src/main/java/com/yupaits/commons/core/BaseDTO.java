package com.yupaits.commons.core;

import java.io.Serializable;

/**
 * DTO基类
 * @author yupaits
 * @date 2018/10/18
 */
public abstract class BaseDTO implements Serializable {

    /**
     * DTO校验方法
     * @return 校验结果
     */
    public abstract boolean isValid();
}
