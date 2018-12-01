package org.hopter.framework;

/**
 * 提供相关配置项常量
 *
 * @author Angus
 * @date 2018/11/29
 */
public interface ConfigConstant {
    /**
     * 默认配置文件名
     */
    String CONFIG_FILE = "hopter.properties";

    /**
     * JDBC 相关
     */
    String JDBC_DRIVER = "hopter.jdbc.driver";
    String JDBC_URL = "hopter.jdbc.url";
    String JDBC_USERNAME = "hopter.jdbc.username";
    String JDBC_PASSWORD = "hopter.jdbc.password";

    /**
     * 应用基础配置
     */
    String APP_BASE_PACKAGE = "hopter.app.base-package";
    String APP_JSP_PATH = "hopter.app.jsp-path";
    String APP_ASSET_PATH = "hopter.app.asset-path";
}
