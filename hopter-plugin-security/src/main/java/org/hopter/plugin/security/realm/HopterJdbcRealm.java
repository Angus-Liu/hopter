package org.hopter.plugin.security.realm;

import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.hopter.framework.helper.DatabaseHelper;
import org.hopter.plugin.security.SecurityConfig;
import org.hopter.plugin.security.password.Md5CredentialsMatcher;

/**
 * 基于 Hopter 的 JDBC Realm
 * <p>
 * 需提供相关 hopter.plugin.security.jdbc.* 配置项
 *
 * @author Angus
 * @date 2018/12/9
 */
public class HopterJdbcRealm extends JdbcRealm {

    public HopterJdbcRealm() {
        super.setDataSource(DatabaseHelper.getDataSource());
        super.setAuthenticationQuery(SecurityConfig.getJdbcAuthcQuery());
        super.setUserRolesQuery(SecurityConfig.getJdbcRolesQuery());
        super.setPermissionsQuery(SecurityConfig.getPermissionsQuery());
        // 开启 PermissionsLookup，允许连接 permission 表进行查询
        super.setPermissionsLookupEnabled(true);
        // 自定义 Md5CredentialsMatcher 对象提供基于 MD5 的密码匹配机制
        super.setCredentialsMatcher(new Md5CredentialsMatcher());
    }
}
