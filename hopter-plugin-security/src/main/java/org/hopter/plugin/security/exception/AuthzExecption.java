package org.hopter.plugin.security.exception;

/**
 * 授权异常（权限无效时抛出）
 *
 * @author Angus
 * @date 2018/12/9
 */
public class AuthzExecption extends Exception {

    public AuthzExecption() {
    }

    public AuthzExecption(String message) {
        super(message);
    }

    public AuthzExecption(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthzExecption(Throwable cause) {
        super(cause);
    }
}
