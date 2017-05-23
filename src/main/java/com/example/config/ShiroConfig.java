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

    /**
     * Shiro的Web过滤器Factory 命名:shiroFilter<br />
     * *  @param securityManager
     * * @return
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter() {

        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        //Shiro的核心安全接口,这个属性是必须的
        shiroFilter.setSecurityManager(securityManager());
        /*要求登录时的链接(可根据项目的URL进行替换),非必须的属性,默认会自动寻找Web工程根目录下的"/login.jsp"页面*/
        shiroFilter.setLoginUrl("/login");
        /*登录成功页面*/
        shiroFilter.setSuccessUrl("/index");
        /*无权限转页面*/
        shiroFilter.setUnauthorizedUrl("/forbidden");

        /*定义shiro过滤链 Map结构 * Map中key(xml中是指value值)的第一个'/'代表
        的路径是相对于HttpServletRequest.getContextPath()的值来的
        *anon：它对应的过滤器里面是空的,什么都没做,这里.do和.jsp后面的*表示参数,比方说login.jsp?main这种
        * authc：该过滤器下的页面必须验证后才能访问,它是Shiro内置的一个拦截器org.apache.shiro.web.filter.authc.FormAuthenticationFilter */
        Map<String, String> filterChainDefinitionMapping = new HashMap<String, String>();
        filterChainDefinitionMapping.put("/favicon.ico", "anon");
        filterChainDefinitionMapping.put("/forbidden", "anon");
        filterChainDefinitionMapping.put("/assets/**", "anon");
        filterChainDefinitionMapping.put("/webjars/**", "anon");
        filterChainDefinitionMapping.put("/v2/api-docs", "anon");

        filterChainDefinitionMapping.put("/login", "authc");
        filterChainDefinitionMapping.put("/logout", "logout");
        filterChainDefinitionMapping.put("/mislogin", "anon");
//        filterChainDefinitionMapping.put("/**", "anon");
        filterChainDefinitionMapping.put("/**", "user");

        shiroFilter.setFilterChainDefinitionMap(filterChainDefinitionMapping);

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
