package org.hopter.framework.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Method;

/**
 * 封装 Action 信息
 *
 * @author Angus
 * @date 2018/11/29
 */
@Data
@AllArgsConstructor
public class Handler {
    /**
     * Controller 类
     */
    private Class<?> controllerClass;

    /**
     * Action 方法
     */
    private Method actionMethod;
}
