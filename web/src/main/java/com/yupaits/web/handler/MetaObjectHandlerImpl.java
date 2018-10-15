package com.yupaits.web.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.yupaits.commons.utils.HttpUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author yupaits
 * @date 2018/10/15
 */
@Component
public class MetaObjectHandlerImpl implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        metaObject.setValue("createdAt", LocalDateTime.now());
        metaObject.setValue("createdBy", HttpUtils.getUserId());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        metaObject.setValue("lastModifiedAt", LocalDateTime.now());
        metaObject.setValue("createdBy", HttpUtils.getUserId());
    }
}
