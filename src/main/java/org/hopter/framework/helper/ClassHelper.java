package org.hopter.framework.helper;

import lombok.extern.slf4j.Slf4j;
import org.hopter.framework.annotation.Controller;
import org.hopter.framework.annotation.Service;
import org.hopter.framework.util.ClassUtil;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/**
 * 类操作助手类
 *
 * @author Angus
 * @date 2018/11/29
 */
@Slf4j
public class ClassHelper {

    /**
     * 定义类集合（用于存放所加载的类）
     */
    private static final Set<Class<?>> CLASS_SET;

    static {
        String basePackage = ConfigHelper.getAppBasePackage();
        CLASS_SET = ClassUtil.getClassSet(basePackage);
    }

    /**
     * 获取应用包名下的所有类
     *
     * @return
     */
    public static Set<Class<?>> getClassSet() {
        return CLASS_SET;
    }

    /**
     * 获取应用包名下被特定注解修饰的所有类
     *
     * @param annotation
     * @return
     */
    private static Set<Class<?>> getAnnoClassSet(Class<? extends Annotation> annotation) {
        Set<Class<?>> classSet = new HashSet<>();
        CLASS_SET.forEach(cls -> {
            if (cls.isAnnotationPresent(annotation)) {
                classSet.add(cls);
            }
        });
        return classSet;
    }

    /**
     * 获取应用包名下的所有 Service 类
     *
     * @return
     */
    public static Set<Class<?>> getServiceClassSet() {
        return getAnnoClassSet(Service.class);
    }

    /**
     * 获取应用包名下的所有 Controller 类
     *
     * @return
     */
    public static Set<Class<?>> getControllerClassSet() {
        return getAnnoClassSet(Controller.class);
    }

    /**
     * 获取应用包名下所有 Bean 类（Service、Controller...）
     *
     * @return
     */
    public static Set<Class<?>> getBeanClassSet() {
        Set<Class<?>> beanClassSet = new HashSet<>();
        beanClassSet.addAll(getServiceClassSet());
        beanClassSet.addAll(getControllerClassSet());
        return beanClassSet;
    }
}
