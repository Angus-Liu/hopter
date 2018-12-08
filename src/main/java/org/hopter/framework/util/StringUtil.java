package org.hopter.framework.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 字符串工具类
 *
 * @author Angus
 * @date 2018/12/6
 */
public final class StringUtil {
    /**
     * 字符串分割符
     */
    public static final String SEPARATOR = String.valueOf((char) 29);


    /**
     * 判断字符串是否为空
     */
    public static boolean isEmpty(String str) {
        if (str != null) {
            str = str.trim();
        }
        return StringUtils.isEmpty(str);
    }

    /**
     * 判断字符串是否非空
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 分割固定格式的字符串
     */
    public static String[] splitString(String str, String separator) {
        return StringUtils.splitByWholeSeparator(str, separator);
    }
}
