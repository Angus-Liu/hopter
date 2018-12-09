package org.hopter.plugin.security;

import org.hopter.framework.helper.ConfigHelper;
import org.hopter.framework.util.ReflectionUtil;

/**
 * 读取配置文件获取相关属性
 *
 * @author Angus
 * @date 2018/12/9
 */
public class SecurityConfig {

    public static String getRealms() {
        return ConfigHelper.getString(SecurityConstant.REALMS);
    }

    public static HopterSecurity getHopterSecurity() {
        String className = ConfigHelper.getString(SecurityConstant.HOPTER_SECURITY);
        return (HopterSecurity) ReflectionUtil.newInstance(className);
    }

    public static String getJdbcAuthcQuery() {
        return ConfigHelper.getString(SecurityConstant.JDBC_AUTHC_QUERY);
    }

    public static String getJdbcRolesQuery() {
        return ConfigHelper.getString(SecurityConstant.JDBC_ROLES_QUERY);
    }

    public static String getPermissionsQuery() {
        return ConfigHelper.getString(SecurityConstant.PERMISSIONS_QUERY);
    }

    public static boolean isCache() {
        return ConfigHelper.getBoolean(SecurityConstant.CACHE);
    }

}
