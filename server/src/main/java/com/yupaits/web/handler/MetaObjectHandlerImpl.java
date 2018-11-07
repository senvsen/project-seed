package com.yupaits.web.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.yupaits.auth.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 自动填充字段实现
 * @author yupaits
 * @date 2018/10/15
 */
@Slf4j
@Component
public class MetaObjectHandlerImpl implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createdAt", LocalDateTime.now(), metaObject);
        this.setFieldValByName("createdBy", principalId(), metaObject);
        this.setFieldValByName("updatedAt", LocalDateTime.now(), metaObject);
        this.setFieldValByName("updatedBy", principalId(), metaObject);
        this.setFieldValByName("deleted", false, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updatedAt", LocalDateTime.now(), metaObject);
        this.setFieldValByName("updatedBy", principalId(), metaObject);
    }

    private Long principalId() {
        try {
            Subject subject = SecurityUtils.getSubject();
            UserVO currentUser = (UserVO) subject.getPrincipal();
            return currentUser.getId();
        } catch (Exception e) {
            log.error("获取当前操作人用户ID失败", e);
            return null;
        }
    }
}
