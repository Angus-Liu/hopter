package org.hopter.framework.annotation;

import org.hopter.framework.enums.RequestMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Action 方法注解
 *
 * @author Angus
 * @date 2018/11/29
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Action {
    /**
     * 请求类型
     *
     * @return
     */
    RequestMethod method();

    /**
     * 请求路径
     *
     * @return
     */
    String path();
}
