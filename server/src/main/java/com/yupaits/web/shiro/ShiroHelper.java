package com.yupaits.web.shiro;

import com.yupaits.auth.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * @author yupaits
 * @date 2018/11/8
 */
@Slf4j
public class ShiroHelper {

    /**
     * 获取当前登录用户ID
     */
    public static Long principalId() {
        try {
            Subject subject = SecurityUtils.getSubject();
            UserVO currentUser = (UserVO) subject.getPrincipal();
            return currentUser.getId();
        } catch (Exception e) {
            log.error("获取当前操作人用户ID失败");
            return null;
        }
    }

    /**
     * 获取当前登录用户信息
     */
    public static UserVO principal() {
        try {
            Subject subject = SecurityUtils.getSubject();
            return (UserVO) subject.getPrincipal();
        } catch (Exception e) {
            log.error("获取当前操作人用户信息失败");
            return new UserVO();
        }
    }
}
