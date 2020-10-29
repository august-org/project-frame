package com.august.config;

import com.august.shiro.CustomFilter;
import com.august.shiro.CustomMatcher;
import com.august.shiro.CustomRealm;
import com.august.shiro.ShiroCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;

/**
 * @author AUGUST
 * @description TODO
 * @date 2020/10/23 21:38
 */
@Configuration
public class ShiroConfig {
    @Bean
    public ShiroCacheManager shiroCacheManager(){
        return new ShiroCacheManager();
    }
    /**
     * 自定义密码校验
     */
    @Bean
    public CustomMatcher customMatcher(){
        return new CustomMatcher();
    }
    /**
     * 自定义域
     */
    @Bean
    public CustomRealm customRealm(){
        CustomRealm realm = new CustomRealm();
        realm.setCredentialsMatcher(customMatcher());
        return realm;
    }
    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(customRealm());
        manager.setCacheManager(shiroCacheManager());
        return manager;
    }
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager manager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(manager);
        LinkedHashMap<String, Filter> map = new LinkedHashMap<>();
        map.put("token",new CustomFilter());
        bean.setFilters(map);
        LinkedHashMap<String, String> hashMap = new LinkedHashMap<>();
        hashMap.put("/api/user/login","anon");
        // swagger ui
        hashMap.put("/swagger/**","anon");
        hashMap.put("/v2/api-docs","anon");
        hashMap.put("/swagger-ui.html","anon");
        hashMap.put("/swagger-resources/**","anon");
        hashMap.put("/webjars/**","anon");
        hashMap.put("/favicon.ico","anon");
        hashMap.put("/captcha.jpg","anon");
        // durid sql
        hashMap.put("/durid/**","anon");
        hashMap.put("/**","token,authc");
        bean.setFilterChainDefinitionMap(hashMap);
        return bean;
    }
    /**
     * 开启shiro aop 注解支持
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
