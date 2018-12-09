package org.hopter.plugin.security;

import java.util.Set;

/**
 * Hopter Security 接口
 * <br/>
 * 可在应用中实现该接口，或者在 hopter.properties 文件中提供以下基于 SQL 的配置项：
 * <ul>
 *     <li>hopter.plugin.security.jdbc.authc-query: 根据用户名获取密码</li>
 *     <li>hopter.plugin.security.jdbc.roles-query: 根据用户名获取角色名集合</li>
 *     <li>hopter.plugin.security.jdbc.permissions-query: 根据角色名获取权限名集合</li>
 * </ul>
 *
 * @author Angus
 * @date 2018/12/8
 */
public interface HopterSecurity {

    /**
     * 根据用户名获取密码
     *
     * @param username 用户名
     * @return 密码
     */
    String getPassword(String username);

    /**
     * 根据用户名获取角色名集合
     *
     * @param username 角色名
     * @return 角色名集合
     */
    Set<String> getRoleNameSet(String username);

    /**
     * 根据角色名获取权限名集合
     *
     * @param roleName 角色名
     * @return 权限名集合
     */
    Set<String> getPermissionNameSet(String roleName);
}
