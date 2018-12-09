package org.hopter.framework.util;

/**
 * 转型操作工具类
 *
 * @author Angus
 * @date 2018/11/29
 */
public final class CastUtil {
    /**
     * 转为 String 型
     *
     * @param obj
     * @return
     */
    public static String castString(Object obj) {
        return castString(obj, "");
    }

    /**
     * 转为 String 型
     *
     * @param obj
     * @param defaultValue 默认值
     * @return
     */
    public static String castString(Object obj, String defaultValue) {
        return obj != null ? String.valueOf(obj) : defaultValue;
    }

    /**
     * 转为 double 型
     *
     * @param obj
     * @return
     */
    public static double castDouble(Object obj) {
        return castDouble(obj, 0);
    }

    /**
     * 转为 double 型
     *
     * @param obj
     * @param defaultValue 默认值
     * @return
     */
    public static double castDouble(Object obj, double defaultValue) {
        double doubleValue = defaultValue;
        if (obj != null) {
            String strValue = castString(obj);
            if (StringUtil.isNotEmpty(strValue)) {
                try {
                    doubleValue = Double.parseDouble(strValue);
                } catch (NumberFormatException e) {
                    return defaultValue;
                }
            }
        }
        return doubleValue;
    }

    /**
     * 转为 long 型
     *
     * @param obj
     * @return
     */
    public static long castLong(Object obj) {
        return castLong(obj, 0);
    }

    /**
     * 转为 long 型
     *
     * @param obj
     * @param defaultValue 默认值
     * @return
     */
    public static long castLong(Object obj, long defaultValue) {
        long loneValue = defaultValue;
        if (obj != null) {
            String strValue = castString(obj);
            if (StringUtil.isNotEmpty(strValue)) {
                try {
                    loneValue = Long.parseLong(strValue);
                } catch (NumberFormatException e) {
                    return defaultValue;
                }
            }
        }
        return loneValue;
    }

    /**
     * 转为 int 型
     *
     * @param obj
     * @return
     */
    public static int castInt(Object obj) {
        return castInt(obj, 0);
    }

    /**
     * 转为 double 型
     *
     * @param obj
     * @param defaultValue 默认值
     * @return
     */
    public static int castInt(Object obj, int defaultValue) {
        int intValue = defaultValue;
        if (obj != null) {
            String strValue = castString(obj);
            if (StringUtil.isNotEmpty(strValue)) {
                try {
                    intValue = Integer.parseInt(strValue);
                } catch (NumberFormatException e) {
                    return defaultValue;
                }
            }
        }
        return intValue;
    }

    /**
     * 转为 boolean 型
     *
     * @param obj
     * @return
     */
    public static boolean castBoolean(Object obj) {
        return castBoolean(obj, false);
    }

    /**
     * 转为 boolean 型
     *
     * @param obj
     * @param defaultValue 默认值
     * @return
     */
    public static boolean castBoolean(Object obj, boolean defaultValue) {
        boolean booleanValue = defaultValue;
        if (obj != null) {
            booleanValue = Boolean.parseBoolean(castString(obj));
        }
        return booleanValue;
    }
}
