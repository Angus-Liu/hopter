package org.hopter.framework.util;

import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 属性文件工具类
 *
 * @author Angus
 * @date 2018/11/29
 */
@Slf4j
public final class PropsUtil {
    /**
     * 加载属性文件
     *
     * @param fileName
     * @return
     */
    public static Properties loadProps(String fileName) {
        Properties props = null;
        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName)) {
            if (is == null) {
                throw new FileNotFoundException(fileName + " file is not found");
            }
            props = new Properties();
            props.load(is);
        } catch (IOException e) {
            log.error("load properties file failure", e);
        }
        return props;
    }

    /**
     * 获取字符型属性（默认值为空字符串）
     *
     * @param props
     * @param key
     * @return
     */
    public static String getString(Properties props, String key) {
        return getString(props, key, "");
    }

    /**
     * 获取字符型属性
     *
     * @param props
     * @param key
     * @param defaultValue 默认值
     * @return
     */
    public static String getString(Properties props, String key, String defaultValue) {
        String value = defaultValue;
        if (props.containsKey(key)) {
            value = props.getProperty(key);
        }
        return value;
    }

    /**
     * 获取数值型属性（默认值为 0）
     *
     * @param props
     * @param key
     * @return
     */
    public static int getInt(Properties props, String key) {
        return getInt(props, key, 0);
    }

    /**
     * 获取数值型属性
     *
     * @param props
     * @param key
     * @param defaultValue 默认值
     * @return
     */
    public static int getInt(Properties props, String key, int defaultValue) {
        int value = defaultValue;
        if (props.containsKey(key)) {
            value = CastUtil.castInt(props.getProperty(key));
        }
        return value;
    }

    /**
     * 获取布尔型属性（默认值为 false）
     *
     * @param props
     * @param key
     * @return
     */
    public static boolean getBoolean(Properties props, String key) {
        return getBoolean(props, key, false);
    }


    /**
     * 获取布尔型属性
     *
     * @param props
     * @param key
     * @param defaultValue 默认值
     * @return
     */
    public static boolean getBoolean(Properties props, String key, boolean defaultValue) {
        boolean value = defaultValue;
        if (props.containsKey(key)) {
            value = CastUtil.castBoolean(props.getProperty(key));
        }
        return value;
    }
}
