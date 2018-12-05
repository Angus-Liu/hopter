package org.hopter.framework.proxy;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * 切面代理
 *
 * @author Angus
 * @date 2018/12/2
 */
@Slf4j
public abstract class AspectProxy implements Proxy {

    /**
     * 在 Proxy 接口中的实现中提供相应的横切逻辑，并调用 ProxyChain 对象的 doProxyChain 方法
     *
     * @param proxyChain
     * @return
     * @throws Throwable
     */
    @Override
    public final Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result;

        Class<?> cls = proxyChain.getTargetClass();
        Method method = proxyChain.getTargetMethod();
        Object[] params = proxyChain.getMethodParams();

        begin();

        try {
            if (intercept(cls, method, params)) {
                before(cls, method, params);
                result = proxyChain.doProxyChain();
                after(cls, method, params);
            } else {
                result = proxyChain.doProxyChain();
            }
        } catch (Exception e) {
            log.error("proxy failure", e);
            error(cls, method, params, e);
            throw e;
        } finally {
            end();
        }

        return result;
    }

    public void begin() {
    }

    public boolean intercept(Class<?> cls, Method method, Object[] params) throws Throwable {
        return true;
    }

    public void before(Class<?> cls, Method method, Object[] params) throws Throwable {
    }

    public void after(Class<?> cls, Method method, Object[] params) throws Throwable {
    }

    public void error(Class<?> cls, Method method, Object[] params, Throwable e) {
    }

    public void end() {
    }
}
