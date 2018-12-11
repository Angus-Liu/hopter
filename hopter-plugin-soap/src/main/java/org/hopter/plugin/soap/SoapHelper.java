package org.hopter.plugin.soap;

import org.apache.cxf.ext.logging.LoggingInInterceptor;
import org.apache.cxf.ext.logging.LoggingOutInterceptor;
import org.apache.cxf.frontend.ClientProxyFactoryBean;
import org.apache.cxf.frontend.ServerFactoryBean;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.message.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * SOAP 助手类
 *
 * @author Angus
 * @date 2018/12/11
 */
public class SoapHelper {

    private static final List<Interceptor<? extends Message>> IN_INTERCEPTOR_LIST = new ArrayList<>();
    private static final List<Interceptor<? extends Message>> OUT_INTERCEPTOR_LIST = new ArrayList<>();

    static {
        // 添加 Logging Interceptor
        if (SoapConfig.isLog()) {
            LoggingInInterceptor loggingInInterceptor = new LoggingInInterceptor();
            IN_INTERCEPTOR_LIST.add(loggingInInterceptor);
            LoggingOutInterceptor loggingOutInterceptor = new LoggingOutInterceptor();
            OUT_INTERCEPTOR_LIST.add(loggingOutInterceptor);
        }
    }

    /**
     * 发布 SOAP 服务
     */
    public static void publishService(String wsdl, Class<?> interfaceClass, Object implementInstance) {
        ServerFactoryBean factory = new ServerFactoryBean();
        factory.setAddress(wsdl);
        factory.setServiceClass(interfaceClass);
        factory.setServiceBean(implementInstance);
        factory.setInInterceptors(IN_INTERCEPTOR_LIST);
        factory.setOutInterceptors(OUT_INTERCEPTOR_LIST);
        factory.create();
    }

    /**
     * 创建 SOAP 客户端
     */
    public static <T> T createClient(String wsdl, Class<? extends T> interfaceClass) {
        ClientProxyFactoryBean factory = new ClientProxyFactoryBean();
        factory.setAddress(wsdl);
        factory.setServiceClass(interfaceClass);
        factory.setInInterceptors(IN_INTERCEPTOR_LIST);
        factory.setOutInterceptors(OUT_INTERCEPTOR_LIST);
        return factory.create(interfaceClass);
    }
}
