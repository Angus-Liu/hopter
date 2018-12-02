package org.hopter.framework.annotation;

import java.lang.annotation.*;

/**
 * 切面注解
 *
 * @author Angus
 * @date 2018/12/2
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {

    /**
     * 注解
     *
     * @return
     */
    Class<? extends Annotation> value();
}
