package org.hopter.plugin.soap;

import org.hopter.framework.helper.ConfigHelper;

/**
 * 从配置文件中获取相关属性
 *
 * @author Angus
 * @date 2018/12/11
 */
public class SoapConfig {

    public static boolean isLog() {
        return ConfigHelper.getBoolean(SoapConstant.LOG);
    }
}
