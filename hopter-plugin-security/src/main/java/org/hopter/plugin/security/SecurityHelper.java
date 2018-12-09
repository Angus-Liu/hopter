package org.hopter.plugin.security;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.hopter.plugin.security.exception.AuthcException;

/**
 * Security 助手类
 *
 * @author Angus
 * @date 2018/12/9
 */
@Slf4j
public final class SecurityHelper {

    /**
     * 登录
     *
     * @param username
     * @param password
     */
    public static void login(String username, String password) throws AuthcException {
        // 通过 Shiro 提供的 SecurityUtils，可随时获取当前登录的用户对象（Subject）
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser != null) {
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            try {
                currentUser.login(token);
            } catch (AuthenticationException e) {
                log.error("login failure", e);
                throw new AuthcException(e);
            }
        }
    }

    /**
     * 注销
     */
    public static void logout() {
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser != null) {
            currentUser.logout();
        }
    }
}
