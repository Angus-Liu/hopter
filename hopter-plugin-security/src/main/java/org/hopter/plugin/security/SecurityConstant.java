package org.hopter.plugin.security;

/**
 * 常量接口
 *
 * @author Angus
 * @date 2018/12/9
 */
public interface SecurityConstant {

    String REALMS = "hopter.plugin.security.realms";
    String REALMS_JDBC = "jdbc";
    String REALMS_CUSTOM = "custom";

    String HOPTER_SECURITY = "hopter.plugin.security.custom.class";

    String JDBC_AUTHC_QUERY = "hopter.plugin.security.jdbc.authc-query";
    String JDBC_ROLES_QUERY = "hopter.plugin.security.jdbc.roles-query";
    String PERMISSIONS_QUERY = "hopter.plugin.security.jdbc.permissions-query";

    String CACHE = "hopter.plugin.security.cache";
}
