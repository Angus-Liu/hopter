package org.hopter.framework.enums;

/**
 * HTTP 请求方法枚举
 *
 * @author Angus
 * @date 2018/11/29
 */
public enum RequestMethod {
    /**
     * HTTP 方法
     */
    GET("get"),
    HEAD("head"),
    POST("post"),
    PUT("put"),
    PATCH("patch"),
    DELETE("delete"),
    OPTIONS("options"),
    TRACE("trace"),
    /**
     * 无效方法
     */
    NONE("none");

    private String name;

    RequestMethod(String name) {
        this.name = name;
    }

    public static RequestMethod of(String name) {
        for (RequestMethod requestMethod : RequestMethod.values()) {
            if (requestMethod.name.equals(name)) {
                return requestMethod;
            }
        }
        return NONE;
    }

    public String getName() {
        return name;
    }
}
