package com.yupaits.commons.consts;


import com.google.common.base.Charsets;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.shiro.codec.Base64;

/**
 * 安全相关常量
 * @author yupaits
 * @date 2018/10/15
 */
public class SecurityConsts {

    public static final String CREDENTIALS_SALT = StringUtils.newString(Base64.decode("eXVwYWl0cw=="), Charsets.UTF_8.name());
    public static final int ITERATIONS = 3;

}
