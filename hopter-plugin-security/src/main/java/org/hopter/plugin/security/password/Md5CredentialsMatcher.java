package org.hopter.plugin.security.password;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.hopter.framework.util.CodecUtil;

/**
 * MD5 密码匹配器
 *
 * @author Angus
 * @date 2018/12/9
 */
public class Md5CredentialsMatcher implements CredentialsMatcher {

    /**
     * 密码匹配
     *
     * @param token 可通过该参数获取从表单提交过来的密码，明文
     * @param info  可通过该参数获取数据库中存储的密码，MD5 加密
     * @return
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {

        // 获取从表单提交过来的密码、明文、尚未通过 MD5 加密
        String submitted = String.valueOf(((UsernamePasswordToken) token).getPassword());
        //获取数据库中存储的密码，已通过 MD5 加密
        String encrypted = String.valueOf(info.getCredentials());
        return CodecUtil.md5(submitted).equals(encrypted);
    }
}
