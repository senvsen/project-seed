package com.yupaits.commons.utils;

import com.yupaits.commons.consts.SecurityConsts;
import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * @author yupaits
 * @date 2018/11/7
 */
public class EncryptUtils {

    public static String genPrivacyCode(String rowData) {
        return new Md5Hash(rowData, rowData + SecurityConsts.CREDENTIALS_SALT, SecurityConsts.ITERATIONS).toHex();
    }
}
