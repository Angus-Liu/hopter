package org.hopter.framework.util;

import org.apache.commons.lang3.ArrayUtils;

/**
 * 数组工具类
 *
 * @author Angus
 * @date 2018/12/08
 */
public final class ArrayUtil {

    /**
     * 判断数组是否非空
     */
    public static boolean isNotEmpty(Object[] array) {
        return ArrayUtils.isNotEmpty(array);
    }

    /**
     * 判断数组是否为空
     */
    public static boolean isEmpty(Object[] array) {
        return ArrayUtils.isEmpty(array);
    }
}
