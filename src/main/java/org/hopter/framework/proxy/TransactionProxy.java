package org.hopter.framework.proxy;

import lombok.extern.slf4j.Slf4j;
import org.hopter.framework.annotation.Transaction;
import org.hopter.framework.helper.DatabaseHelper;

import java.lang.reflect.Method;

/**
 * 事务代理
 *
 * @author Angus
 * @date 2018/12/3
 */
@Slf4j
public class TransactionProxy implements Proxy {

    /**
     * FLAG_HOLDER 用于保证同一线程中事务控制相关逻辑只会执行一次
     */
    private static final ThreadLocal<Boolean> FLAG_HOLDER = ThreadLocal.withInitial(() -> false);


    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result;
        boolean flag = FLAG_HOLDER.get();
        Method method = proxyChain.getTargetMethod();
        // 为带有 Transaction 注解的方法开启事务控制
        if (!flag && method.isAnnotationPresent(Transaction.class)) {
            FLAG_HOLDER.set(true);
            try {
                // 开启事务
                DatabaseHelper.beginTransaction();
                log.debug("begin transaction");
                // 执行目标方法
                result = proxyChain.doProxyChain();
                // 提交事务
                DatabaseHelper.commitTransaction();
                log.debug("commit transaction");
            } catch (Exception e) {
                // 回滚事务
                DatabaseHelper.rollbackTransaction();
                log.debug("rollback transaction");
                throw e;
            } finally {
                FLAG_HOLDER.remove();
            }
        } else {
            result = proxyChain.doProxyChain();
        }
        return result;
    }
}
