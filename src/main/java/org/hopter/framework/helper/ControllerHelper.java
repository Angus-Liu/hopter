package org.hopter.framework.helper;

import lombok.extern.slf4j.Slf4j;
import org.hopter.framework.annotation.Action;
import org.hopter.framework.bean.Handler;
import org.hopter.framework.bean.Request;
import org.hopter.framework.enums.RequestMethod;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 控制器助手类
 *
 * @author Angus
 * @date 2018/11/29
 */
@Slf4j
public final class ControllerHelper {
    /**
     * 用于存放请求与处理器的映射关系（Action Map）
     */
    private static final Map<Request, Handler> ACTION_MAP = new HashMap<>();

    static {
        // 获取所有的 Controller 类
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
        log.debug("All controllers: {}", controllerClassSet);
        controllerClassSet.forEach(controllerClass -> {
            // 获取 Controller 类中定义的方法
            Method[] methods = controllerClass.getDeclaredMethods();
            for (Method method : methods) {
                // 判断当前方法是否带有 Action 注解
                if (method.isAnnotationPresent(Action.class)) {
                    // 从 Action 中获取 URL 映射规则
                    Action action = method.getAnnotation(Action.class);
                    RequestMethod requestMethod = action.method();
                    String requestPath = action.path();
                    // 根据 URL 配置相应的请求与处理器
                    Request request = new Request(requestMethod, requestPath);
                    Handler handler = new Handler(controllerClass, method);
                    log.info("Mapping HTTP action [{}:{}] to [{}:{}] method", requestMethod.getName(), requestPath, controllerClass.getName(), method.getName());
                    // 初始化 Action map
                    ACTION_MAP.put(request, handler);
                }
            }
        });
    }

    /**
     * 根据请求获取 Handler
     *
     * @param requestMethod 请求方法
     * @param requestPath   请求路径
     * @return
     */
    public static Handler getHandler(RequestMethod requestMethod, String requestPath) {
        Request request = new Request(requestMethod, requestPath);
        return ACTION_MAP.get(request);
    }
}
