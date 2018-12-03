package org.hopter.framework.helper;

import lombok.extern.slf4j.Slf4j;
import org.hopter.framework.annotation.Inject;
import org.hopter.framework.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 依赖注入助手类
 *
 * @author Angus
 * @date 2018/11/29
 */
@Slf4j
public final class IocHelper {
    static {
        // 获取所有的 Bean 类与 Bean 实例之间的映射关系（Bean Map）
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        beanMap.forEach((beanClass, beanInstance) -> {
            // 获取 Bean 类定义的所有成员变量
            Field[] beanFields = beanClass.getDeclaredFields();
            for (Field beanField : beanFields) {
                // 判断当前 Bean Field 是否带有 Inject 注解
                if (beanField.isAnnotationPresent(Inject.class)) {
                    // 在 Bean Map 中获取 Bean Field 对应的实例
                    Class<?> beanFieldClass = beanField.getType();
                    Object beanFieldInstance = beanMap.get(beanFieldClass);
                    log.debug("Inject field [{}.{}] with object [{}]",
                            beanClass.getName(), beanField.getName(), beanFieldInstance);
                    if (beanFieldInstance != null) {
                        // 通过反射初始化 Bean Filed 的值
                        ReflectionUtil.setField(beanInstance, beanField, beanFieldInstance);
                    }
                }
            }
        });
    }
}
