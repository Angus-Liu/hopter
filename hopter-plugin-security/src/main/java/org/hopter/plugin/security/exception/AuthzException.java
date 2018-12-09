package org.hopter.plugin.security.exception;

/**
 * 授权异常（权限无效时抛出）
 *
 * @author Angus
 * @date 2018/12/9
 */
public class AuthzException extends RuntimeException {

    public AuthzException() {
    }

    public AuthzException(String message) {
        super(message);
    }

    public AuthzException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthzException(Throwable cause) {
        super(cause);
    }
}
