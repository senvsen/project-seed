package com.yupaits.commons.core;

import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.Collection;

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

    /**
     * DTO集合校验方法
     * @param collection DTO集合
     * @return 校验结果
     */
    public static <DTO extends BaseDTO> boolean isValid(Collection<DTO> collection) {
        if (CollectionUtils.isEmpty(collection)) {
            return false;
        }
        for (DTO dto : collection) {
            if (!dto.isValid()) {
                return false;
            }
        }
        return true;
    }
}
