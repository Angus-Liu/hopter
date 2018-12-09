package org.hopter.framework.proxy;

/**
 * 代理接口
 *
 * @author Angus
 * @date 2018/12/2
 */
public interface Proxy {

    /**
     * 执行链式代理
     *
     * @param proxyChain
     * @return
     * @throws Throwable
     */
    Object doProxy(ProxyChain proxyChain) throws Throwable;
}
