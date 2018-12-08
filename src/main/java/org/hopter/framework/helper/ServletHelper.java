package org.hopter.framework.helper;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet 助手类
 * <p>
 * 封装 Request 与 Response 对象，提供常用的 ServletAPI 工具方法，
 * 利用 ThreadLocal 保证线程安全
 *
 * @author Angus
 * @date 2018/12/8
 */
@Slf4j
public final class ServletHelper {

    /**
     * 每个线程拥有各自的 ServletHelper 实例
     */
    private static final ThreadLocal<ServletHelper> SERVLET_HELPER_HOLDER = new ThreadLocal<>();

    private HttpServletRequest request;
    private HttpServletResponse response;

    private ServletHelper(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    /**
     * 初始化
     *
     * @param request
     * @param response
     */
    public static void init(HttpServletRequest request, HttpServletResponse response) {
        SERVLET_HELPER_HOLDER.set(new ServletHelper(request, response));
    }

    /**
     * 销毁
     */
    public static void destroy() {
        SERVLET_HELPER_HOLDER.remove();
    }

    /**
     * 获取 Request 对象
     *
     * @return
     */
    private static HttpServletRequest getRequest() {
        return SERVLET_HELPER_HOLDER.get().request;
    }

    /**
     * 获取 Response 对象
     *
     * @return
     */
    private static HttpServletResponse getResponse() {
        return SERVLET_HELPER_HOLDER.get().response;
    }

    /**
     * 获取 Session 对象
     *
     * @return
     */
    private static HttpSession getSession() {
        return getRequest().getSession();
    }

    /**
     * 获取 ServletContext 对象
     *
     * @return
     */
    private static ServletContext getServletContext() {
        return getRequest().getServletContext();
    }

    /**
     * 将属性放入 Request 中
     *
     * @param key
     * @param value
     */
    public static void setRequestAttribute(String key, Object value) {
        getRequest().setAttribute(key, value);
    }

    /**
     * 从 request 中获取属性
     *
     * @param key
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getRequestAttribute(String key) {
        return (T) getRequest().getAttribute(key);
    }

    /**
     * 从 Request 中移除属性
     *
     * @param key
     */
    public  static void removeRequestAttribute(String key) {
        getRequest().removeAttribute(key);
    }

    /**
     * 发送重定向响应
     *
     * @param location
     */
    public static void sendRedirect(String location) {
        try {
            getResponse().sendRedirect(getRequest().getContextPath() + location);
        } catch (IOException e) {
            log.error("redirect failure", e);
        }
    }

    /**
     * 将属性放入 Session 中
     *
     * @param key
     * @param value
     */
    public static void setSessionAttribute(String key, Object value) {
        getSession().setAttribute(key, value);
    }

    /**
     * 从 Session 中获取属性
     *
     * @param key
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getSeesionAttribute(String key) {
        return (T) getSession().getAttribute(key);
    }

    /**
     * 从 Session 中移除属性
     *
     * @param key
     */
    public static void removeSessionAttribute(String key) {
        getSession().removeAttribute(key);
    }

    /**
     * 使 Session 失效
     */
    public static void invalidateSession() {
        getSession().invalidate();
    }
}
