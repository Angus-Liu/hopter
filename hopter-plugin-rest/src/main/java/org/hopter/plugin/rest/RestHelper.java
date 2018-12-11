package org.hopter.plugin.rest;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.apache.cxf.ext.logging.LoggingInInterceptor;
import org.apache.cxf.ext.logging.LoggingOutInterceptor;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;
import org.apache.cxf.jaxrs.provider.jsonp.JsonpInInterceptor;
import org.apache.cxf.jaxrs.provider.jsonp.JsonpPostStreamInterceptor;
import org.apache.cxf.jaxrs.provider.jsonp.JsonpPreStreamInterceptor;
import org.apache.cxf.message.Message;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharingFilter;
import org.hopter.framework.helper.BeanHelper;
/**
 * REST 助手类
 *
 * @author Angus
 * @date 2018/12/11
 */
public class RestHelper {

    private static final List<Object> providerList = new ArrayList<>();
    private static final List<Interceptor<? extends Message>> IN_INTERCEPTOR_LIST = new ArrayList<>();
    private static final List<Interceptor<? extends Message>> OUT_INTERCEPTOR_LIST = new ArrayList<>();

    static {
        // 添加 JSON Provider
        JacksonJsonProvider jsonProvider = new JacksonJsonProvider();
        providerList.add(jsonProvider);
        // 添加 Logging Interceptor
        if (RestConfig.isLog()) {
            LoggingInInterceptor loggingInInterceptor = new LoggingInInterceptor();
            IN_INTERCEPTOR_LIST.add(loggingInInterceptor);
            LoggingOutInterceptor loggingOutInterceptor = new LoggingOutInterceptor();
            OUT_INTERCEPTOR_LIST.add(loggingOutInterceptor);
        }
        // 添加 JSONP Interceptor
        if (RestConfig.isJsonp()) {
            JsonpInInterceptor jsonpInInterceptor = new JsonpInInterceptor();
            jsonpInInterceptor.setCallbackParam(RestConfig.getJsonpFunction());
            IN_INTERCEPTOR_LIST.add(jsonpInInterceptor);
            JsonpPreStreamInterceptor jsonpPreStreamInterceptor = new JsonpPreStreamInterceptor();
            OUT_INTERCEPTOR_LIST.add(jsonpPreStreamInterceptor);
            JsonpPostStreamInterceptor jsonpPostStreamInterceptor = new JsonpPostStreamInterceptor();
            OUT_INTERCEPTOR_LIST.add(jsonpPostStreamInterceptor);
        }
        // 添加 CORS Provider
        if (RestConfig.isCors()) {
            CrossOriginResourceSharingFilter corsProvider = new CrossOriginResourceSharingFilter();
            corsProvider.setAllowOrigins(RestConfig.getCorsOriginList());
            providerList.add(corsProvider);
        }
    }

    /**
     * 发布 REST 服务
     *
     * @param wadl
     * @param resourceClass
     */
    public static void publishService(String wadl, Class<?> resourceClass) {
        JAXRSServerFactoryBean factory = new JAXRSServerFactoryBean();
        factory.setAddress(wadl);
        factory.setResourceClasses(resourceClass);
        factory.setResourceProvider(resourceClass, new SingletonResourceProvider(BeanHelper.getBean(resourceClass)));
        factory.setProviders(providerList);
        factory.setInInterceptors(IN_INTERCEPTOR_LIST);
        factory.setOutInterceptors(OUT_INTERCEPTOR_LIST);
        factory.create();
    }

    /**
     * 创建 REST 客户端
     *
     * @param wadl
     * @param resourceClass
     * @param <T>
     * @return
     */
    public static <T> T createClient(String wadl, Class<? extends T> resourceClass) {
        return JAXRSClientFactory.create(wadl, resourceClass, providerList);
    }
}
