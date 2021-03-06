package org.hopter.framework.helper;

import lombok.extern.slf4j.Slf4j;
import org.hopter.framework.annotation.Aspect;
import org.hopter.framework.proxy.AspectProxy;
import org.hopter.framework.proxy.Proxy;
import org.hopter.framework.proxy.ProxyManager;
import org.hopter.framework.proxy.TransactionProxy;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * 方法拦截助手类
 *
 * @author Angus
 * @date 2018/12/3
 */
@Slf4j
public final class AopHelper {

    static {
        try {
            // 获取代理类及其目标类集合的映射关系
            Map<Class<?>, Set<Class<?>>> proxyMap = createProxyMap();
            // 进一步获取目标类与代理对象列表的映射关系
            Map<Class<?>, List<Proxy>> targetMap = createTargetMap(proxyMap);
            targetMap.forEach((targetClass, proxyList) -> {
                // 通过 ProxyManager.createProxy 方法获取代理对象，并放入 Bean Map 中
                Object proxy = ProxyManager.createProxy(targetClass, proxyList);
                BeanHelper.setBean(targetClass, proxy);
                log.debug("Inject target class [{}] with proxy object [{}]", targetClass.getName(), proxy);
            });
        } catch (Throwable e) {
            log.error("aop failure", e);
        }
    }


    /**
     * 通过 Aspect 注解中设置的注解类来获取目标类
     *
     * @param aspect
     * @return
     * @throws Exception
     */
    private static Set<Class<?>> createTargetClassSet(Aspect aspect) {
        Set<Class<?>> targetClassSet = new HashSet<>();
        // 获取 Aspect 注解中设置的注解类
        Class<? extends Annotation> annotation = aspect.value();
        if (!annotation.equals(Aspect.class)) {
            // 通过该注解类获取目标类
            targetClassSet.addAll(ClassHelper.getClassSetByAnnotation(annotation));
        }
        return targetClassSet;
    }

    /**
     * 获取代理类及其目标类之间的映射关系
     *
     * @return
     * @throws Exception
     */
    private static Map<Class<?>, Set<Class<?>>> createProxyMap() {
        Map<Class<?>, Set<Class<?>>> proxyMap = new HashMap<>();
        addAspectProxy(proxyMap);
        addTransactionProxy(proxyMap);
        return proxyMap;
    }

    /**
     * 添加事务代理类及服务类之间的映射关系
     *
     * @param proxyMap
     */
    public static void addTransactionProxy(Map<Class<?>, Set<Class<?>>> proxyMap) {
        Set<Class<?>> serviceClassSet = ClassHelper.getServiceClassSet();
        proxyMap.put(TransactionProxy.class, serviceClassSet);
    }

    /**
     * 添加普通切面代理类及其目标类之间的映射关系
     *
     * @param proxyMap
     */
    private static void addAspectProxy(Map<Class<?>, Set<Class<?>>> proxyMap) {
        Set<Class<?>> proxyClassSet = ClassHelper.getClassSetBySuper(AspectProxy.class);
        log.debug("Proxy class set: {}", proxyClassSet);
        proxyClassSet.forEach(proxyClass -> {
            if (proxyClass.isAnnotationPresent(Aspect.class)) {
                Aspect aspect = proxyClass.getAnnotation(Aspect.class);
                Set<Class<?>> targetClassSet = createTargetClassSet(aspect);
                proxyMap.put(proxyClass, targetClassSet);
            }
        });
        log.debug("The map of proxy class and target classes: {}", proxyMap);
    }

    /**
     * 获取目标类与代理对象之间的映射关系
     *
     * @param proxyMap
     * @return
     * @throws Exception
     */
    private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, Set<Class<?>>> proxyMap) throws Exception {
        Map<Class<?>, List<Proxy>> targetMap = new HashMap<>();
        for (Map.Entry<Class<?>, Set<Class<?>>> proxyEntry : proxyMap.entrySet()) {
            Class<?> proxyClass = proxyEntry.getKey();
            Set<Class<?>> targetClassSet = proxyEntry.getValue();
            for (Class<?> targetClass : targetClassSet) {
                Proxy proxy = (Proxy) proxyClass.newInstance();
                List<Proxy> proxyList = targetMap.getOrDefault(targetClass, new ArrayList<>());
                proxyList.add(proxy);
                targetMap.put(targetClass, proxyList);
            }
        }
        log.debug("The map of target class and proxy objects: {}", targetMap);
        return targetMap;
    }

}
