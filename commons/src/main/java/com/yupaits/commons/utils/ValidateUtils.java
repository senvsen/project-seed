package com.yupaits.commons.utils;

import com.github.stuxuhai.jpinyin.ChineseHelper;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.regex.Pattern;

/**
 * 校验工具
 * @author yupaits
 * @date 2018/10/18
 */
public class ValidateUtils {

    /**
     * 检查ID是否有效
     * @param id id
     */
    public static boolean idValid(Long id) {
        return id != null && id.compareTo(0L) > 0;
    }

    /**
     * 检查ID是否有效
     * @param id id
     */
    public static boolean idValid(Integer id) {
        return id != null && id.compareTo(0) > 0;
    }

    /**
     * 判断是否为正整数
     * @param number 待判断的数
     */
    public static boolean isPositive(Integer number) {
        return number != null && number.compareTo(0) > 0;
    }

    /**
     * 判断是否为正小数
     * @param number 待判断的数
     */
    public static boolean isPositive(BigDecimal number) {
        return number != null && number.compareTo(BigDecimal.ZERO) > 0;
    }

    /**
     * 判断是否为负整数
     * @param number 待判断的数
     */
    public static boolean isNegative(Integer number) {
        return number != null && number.compareTo(0) < 0;
    }

    /**
     * 判断是否为负小数
     * @param number 待判断的数
     */
    public static boolean isNegative(BigDecimal number) {
        return number != null && number.compareTo(BigDecimal.ZERO) < 0;
    }

    /**
     * 判断是否为非负整数
     * @param number 待判断的数
     */
    public static boolean isNotNegative(Integer number) {
        return number != null && number.compareTo(0) >= 0;
    }

    /**
     * 判断是否为非负小数
     * @param number 待判断的数
     */
    public static boolean isNotNegative(BigDecimal number) {
        return number != null && number.compareTo(BigDecimal.ZERO) >= 0;
    }

    /**
     * 检查ID是否有效
     * @param id id
     */
    public static boolean idValid(String id) {
        return StringUtils.isNotBlank(id);
    }

    /**
     * 判断字符串中是否包含繁体中文
     * @param source 待判断的字符串
     * @return 返回true即包含繁体中文
     */
    public static boolean hasTraditionalChinese(String source) {
        return !StringUtils.equals(source, ChineseHelper.convertToSimplifiedChinese(source));
    }

    /**
     * 检查手机号码是否有效
     * @param phoneNumber 手机号码
     */
    public static boolean isPhoneNumber(String phoneNumber) {
        if (StringUtils.isBlank(phoneNumber)) {
            return false;
        }
        String regex = "^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$";
        return Pattern.compile(regex).matcher(phoneNumber).matches();
    }

}
