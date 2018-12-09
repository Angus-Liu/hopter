package org.hopter.framework;

import org.hopter.framework.helper.*;
import org.hopter.framework.util.ClassUtil;

/**
 * 加载相应的 Helper
 *
 * @author Angus
 * @date 2018/11/29
 */
public class HelperLoader {
    public static void init() {
        Class<?>[] classList = {
                ClassHelper.class,
                BeanHelper.class,
                // 需要先通过 AopHelper 获取代理对象，然后才能通过 IocHelper 进行依赖注入
                AopHelper.class,
                IocHelper.class,
                ControllerHelper.class
        };
        for (Class<?> cls : classList) {
            ClassUtil.loadClass(cls.getName());
        }
    }
}
