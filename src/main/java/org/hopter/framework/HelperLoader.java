package org.hopter.framework;

import org.hopter.framework.helper.BeanHelper;
import org.hopter.framework.helper.ClassHelper;
import org.hopter.framework.helper.ControllerHelper;
import org.hopter.framework.helper.IocHelper;
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
                IocHelper.class,
                ControllerHelper.class
        };
        for (Class<?> cls : classList) {
            ClassUtil.loadClass(cls.getName());
        }
    }
}
