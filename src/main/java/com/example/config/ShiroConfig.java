package com.example.config;

import com.example.shiro.ShiroRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    private static Logger logger = LoggerFactory.getLogger(ShiroConfig.class);

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter() {

        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setLoginUrl("/login");
        shiroFilter.setSuccessUrl("/index");
        shiroFilter.setUnauthorizedUrl("/forbidden");
        Map<String, String> filterChainDefinitionMapping = new HashMap<String, String>();
        filterChainDefinitionMapping.put("/favicon.ico", "anon");
        filterChainDefinitionMapping.put("/forbidden", "anon");
        filterChainDefinitionMapping.put("/assets/**", "anon");
        filterChainDefinitionMapping.put("/swagger-resources/**", "anon");
        filterChainDefinitionMapping.put("/swagger-ui.html", "anon");
        filterChainDefinitionMapping.put("/webjars/**", "anon");
        filterChainDefinitionMapping.put("/v2/api-docs", "anon");

        filterChainDefinitionMapping.put("/login", "authc");
        filterChainDefinitionMapping.put("/logout", "logout");
        filterChainDefinitionMapping.put("/mislogin", "anon");
//        filterChainDefinitionMapping.put("/**", "anon");
        filterChainDefinitionMapping.put("/**", "user");
        shiroFilter.setFilterChainDefinitionMap(filterChainDefinitionMapping);
        shiroFilter.setSecurityManager(securityManager());
        Map<String, Filter> filters = new HashMap<String, Filter>();
        filters.put("anon", new AnonymousFilter());
        filters.put("authc", new FormAuthenticationFilter());
        filters.put("logout", new LogoutFilter());
        filters.put("user", new UserFilter());
        shiroFilter.setFilters(filters);

        return shiroFilter;
    }





    @Bean
    public RememberMeManager rememberMeManager() {
        logger.debug("create remember me manager.");

        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        simpleCookie.setHttpOnly(true);
        simpleCookie.setMaxAge(2592000);

        CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
        rememberMeManager.setCipherKey(org.apache.shiro.codec.Base64.decode("Pis2016%KyEe^!#/"));
        rememberMeManager.setCookie(simpleCookie);
        return rememberMeManager;
    }
    @Bean(name = "shiroCacheManager")
    public CacheManager cacheManager() {
        return new MemoryConstrainedCacheManager();
    }
    @Bean(name = "securityManager")
    public org.apache.shiro.mgt.SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setCacheManager(cacheManager());
        securityManager.setRealm(realm());
        securityManager.setRememberMeManager(rememberMeManager());
        SecurityUtils.setSecurityManager(securityManager);
        return securityManager;
    }



    @Bean(name = "realm")
    @DependsOn("lifecycleBeanPostProcessor")
    public ShiroRealm realm() {
        ShiroRealm realm = new ShiroRealm();
//        realm.setCredentialsMatcher(new HashedCredentialsMatcher(Md5Hash.ALGORITHM_NAME));
        return realm;
    }


    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
        proxyCreator.setProxyTargetClass(true);
        return proxyCreator;
    }

    @Bean
    @ConditionalOnMissingBean
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(org.apache.shiro.mgt.SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
        aasa.setSecurityManager(securityManager);
        return aasa;
    }

}
