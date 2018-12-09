package org.hopter.plugin.security.exception;

/**
 * 认证异常（非法访问时抛出）
 *
 * @author Angus
 * @date 2018/12/9
 */
public class AuthcException extends Exception {

    public AuthcException() {
    }

    public AuthcException(String message) {
        super(message);
    }

    public AuthcException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthcException(Throwable cause) {
        super(cause);
    }
}
