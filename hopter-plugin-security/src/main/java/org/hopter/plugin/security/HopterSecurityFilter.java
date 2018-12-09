package org.hopter.plugin.security;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.CachingSecurityManager;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.ShiroFilter;
import org.hopter.framework.util.StringUtil;
import org.hopter.plugin.security.realm.HopterCustomRealm;
import org.hopter.plugin.security.realm.HopterJdbcRealm;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 安全过滤器
 *
 * @author Angus
 * @date 2018/12/9
 */
public class HopterSecurityFilter extends ShiroFilter {
    @Override
    public void init() throws Exception {
        super.init();
        WebSecurityManager webSecurityManager = super.getSecurityManager();
        setRealms(webSecurityManager);
        setCache(webSecurityManager);
    }

    /**
     * 设置 Realm，可同时支持多个 Realm
     *
     * @param webSecurityManager
     */
    private void setRealms(WebSecurityManager webSecurityManager) {
        // 读取 hopter.plugin.security.realms 配置项
        String securityRealms = SecurityConfig.getRealms();
        if (StringUtil.isNotEmpty(securityRealms)) {
            String[] securityRealmArray = securityRealms.split(",");
            if (securityRealmArray.length > 0) {
                // 使 Realm 具备唯一性与顺序性
                Set<Realm> realms = new LinkedHashSet<>();
                for (String securityRealm : securityRealmArray) {
                    if (securityRealm.equalsIgnoreCase(SecurityConstant.REALMS_JDBC)) {
                        // 添加基于 JDBC 的 Realm，需配置相关 SQL 查询语句
                        addJdbcRealm(realms);
                    } else if (securityRealm.equalsIgnoreCase(SecurityConstant.REALMS_CUSTOM)) {
                        // 添加基于定制化的 Realm，需实现 HopterSecurity 接口
                        addCustomRealm(realms);
                    }
                }
                RealmSecurityManager realmSecurityManager = (RealmSecurityManager) webSecurityManager;
                // 设置 Realm
                realmSecurityManager.setRealms(realms);
            }
        }
    }

    /**
     * 添加自己实现的基于 JDBC 的 Realm
     *
     * @param realms
     */
    private void addJdbcRealm(Set<Realm> realms) {
        HopterJdbcRealm hopterJdbcRealm = new HopterJdbcRealm();
        realms.add(hopterJdbcRealm);
    }

    /**
     * 添加自己实现的 Realm
     *
     * @param realms
     */
    private void addCustomRealm(Set<Realm> realms) {
        // 读取 hopter.plugin.security.custom.class 配置项
        HopterSecurity hopterSecurity = SecurityConfig.getHopterSecurity();
        HopterCustomRealm hopterCustomRealm = new HopterCustomRealm(hopterSecurity);
        realms.add(hopterCustomRealm);
    }

    /**
     * 设置 Cache，用于减少数据查询次数，降低 I/O 访问
     *
     * @param webSecurityManager
     */
    private void setCache(WebSecurityManager webSecurityManager) {
        // 读取 hopter.plugin.security.cache 配置项
        if (SecurityConfig.isCache()) {
            CachingSecurityManager cachingSecurityManager = (CachingSecurityManager) webSecurityManager;
            // 使用基于内存的 CacheManager
            CacheManager cacheManager = new MemoryConstrainedCacheManager();
            cachingSecurityManager.setCacheManager(cacheManager);
        }
    }
}
