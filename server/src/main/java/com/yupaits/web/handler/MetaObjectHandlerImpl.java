package com.yupaits.web.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.yupaits.auth.vo.UserVO;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 自动填充字段实现
 * @author yupaits
 * @date 2018/10/15
 */
@Component
public class MetaObjectHandlerImpl implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        metaObject.setValue("createdAt", LocalDateTime.now());
        metaObject.setValue("createdBy", ((UserVO) SecurityUtils.getSubject().getPrincipal()).getId());
        metaObject.setValue("updatedAt", LocalDateTime.now());
        metaObject.setValue("updatedBy", ((UserVO) SecurityUtils.getSubject().getPrincipal()).getId());
        metaObject.setValue("deleted", false);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updatedAt", LocalDateTime.now(), metaObject);
        this.setFieldValByName("updatedBy", ((UserVO) SecurityUtils.getSubject().getPrincipal()).getId(), metaObject);
    }
}
