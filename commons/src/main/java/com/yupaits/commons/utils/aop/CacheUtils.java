package com.yupaits.commons.utils.aop;

/**
 * @author yupaits
 * @date 2018/11/6
 */
public class CacheUtils {

    private static final String KEY_DELIMITER = ":";
    private static final String CACHE_STORE_PREFIX = "data";

    /**
     * 生成缓存仓库Key，即Redis Hash类型的Key
     */
    public static String genCacheStoreKey(String className) {
        return CACHE_STORE_PREFIX + KEY_DELIMITER + className;
    }

    /**
     * 生成缓存Key，即Redis Hash类型的Field
     */
    public static String genCacheKey(String methodName, Object[] params) {
        StringBuilder builder = new StringBuilder(methodName);
        for (Object param : params) {
            builder.append(KEY_DELIMITER).append(param.toString());
        }
        return builder.toString();
    }
}
