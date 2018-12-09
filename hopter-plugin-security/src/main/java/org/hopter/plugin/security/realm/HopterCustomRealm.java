package org.hopter.plugin.security.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.hopter.framework.util.CollectionUtil;
import org.hopter.plugin.security.HopterSecurity;
import org.hopter.plugin.security.SecurityConstant;
import org.hopter.plugin.security.password.Md5CredentialsMatcher;

import java.util.HashSet;
import java.util.Set;

/**
 * 基于 Hopter 的自定义 Realm
 * <p>
 * 需要实现 HopterSecurity 接口
 *
 * @author Angus
 * @date 2018/12/9
 */
public class HopterCustomRealm extends AuthorizingRealm {

    private final HopterSecurity hopterSecurity;

    public HopterCustomRealm(HopterSecurity hopterSecurity) {
        this.hopterSecurity = hopterSecurity;
        super.setName(SecurityConstant.REALMS_CUSTOM);
        // 使用 MD5 加密
        super.setCredentialsMatcher(new Md5CredentialsMatcher());
    }

    /**
     * 用于认证
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        if (token == null) {
            throw new AuthenticationException("parameter token is null");
        }

        // 通过 AuthenticationToken 对象获取从表单中提交过来的用户名
        String username = ((UsernamePasswordToken) token).getUsername();

        // 通过 HopterSecurity 接口并根据用户名获取数据库中存放的密码
        String password = hopterSecurity.getPassword(username);

        // 将用户名与密码放入 AuthenticationInfo 对象中，便于后续的认证操作
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo();
        authenticationInfo.setPrincipals(new SimplePrincipalCollection(username, super.getName()));
        authenticationInfo.setCredentials(password);
        return authenticationInfo;
    }

    /**
     * 用于授权
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        if (principals == null) {
            throw new AuthorizationException("parameter principals is null");
        }

        // 获取已认证的用户名
        String username = (String) super.getAvailablePrincipal(principals);

        // 通过 HopterSecurity 接口并根据用户名获取角色名集合
        Set<String> roleNameSet = hopterSecurity.getRoleNameSet(username);

        // 通过 HopterSecurity 接口并根据角色名获取与其对应的权限名集合
        Set<String> permissionNameSet = new HashSet<>();
        if (CollectionUtil.isNotEmpty(roleNameSet)) {
            roleNameSet.forEach(roleName -> {
                Set<String> currentPermissionNameSet = hopterSecurity.getPermissionNameSet(roleName);
                permissionNameSet.addAll(currentPermissionNameSet);
            });
        }

        // 将角色名集合与权限名集合放入 AuthorizationInfo 对象中，便于后续的授权操作
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(roleNameSet);
        authorizationInfo.setStringPermissions(permissionNameSet);
        return authorizationInfo;
    }
}
