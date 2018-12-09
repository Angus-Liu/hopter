package org.hopter.plugin.security;

import org.apache.shiro.web.env.EnvironmentLoaderListener;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Set;

/**
 * Hopter Security 插件
 *
 * @author Angus
 * @date 2018/12/9
 */
public class HopterSecurityPlugin implements ServletContainerInitializer {
    @Override
    public void onStartup(Set<Class<?>> handlesTypes, ServletContext servletContext) throws ServletException {
        // 设置初始化参数
        servletContext.setInitParameter("shiroConfigLocations", "classpath:hopter-security.ini");
        // 注册 Listener
        servletContext.addListener(EnvironmentLoaderListener.class);
        // 注册 Filter
        FilterRegistration.Dynamic hopterSecurityFilter = servletContext.addFilter("HopterSecurityFilter", HopterSecurityFilter.class);
        hopterSecurityFilter.addMappingForUrlPatterns(null, false, "/*");
    }
}
