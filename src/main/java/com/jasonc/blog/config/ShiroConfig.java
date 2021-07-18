package com.jasonc.blog.config;

import com.jasonc.blog.realm.UserRealm;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Program: blog
 * @Package: com.jasonc.blog.config
 * @ClassName: ShiroConfig
 * @Author: Jason Chan
 * @Description:
 */
@Configuration
public class ShiroConfig {

    //    权限管理，配置主要是Realm的管理认证
    @Bean
    public SecurityManager securityManager(UserRealm userRealm) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(userRealm);
        return defaultWebSecurityManager;
    }

    //将自定义的验证方式加入spring容器
    @Bean
    public UserRealm userShiroRealm(CredentialsMatcher credentialsMatcher) {
        UserRealm userRealm = new UserRealm();
        // 加密处理,设置密码处理凭证器
        userRealm.setCredentialsMatcher(credentialsMatcher);
        return userRealm;
    }

    @Bean
    public CredentialsMatcher credentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        //设置算法名字
        credentialsMatcher.setHashAlgorithmName("md5");
        //设置散列次数
        credentialsMatcher.setHashIterations(1024);
        return credentialsMatcher;
    }

    //添加shiro的内置过滤器,设置对应的过滤条件和跳转条件

    /*
     * anno:无需认证就可以访问
     * authc:必须认证才能访问
     * user:必须拥有记住我功能才能用
     * perms:拥有对某个资源的权限
     * role:拥有某个角色权限才能访问
     * */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl("/admin");
        shiroFilterFactoryBean.setSuccessUrl("/admin/index.html");
        shiroFilterFactoryBean.setUnauthorizedUrl("/admin");
//      配置权限拦截规则
        Map<String, String> filterChainMap = new LinkedHashMap<>();
//        登录请求不需要认证
        /**
         * anno:无需认证就可以访问
         * authc:必须认证才能访问
         * user:必须拥有记住我功能才能用
         * perms:拥有对某个资源的权限
         * role:拥有某个角色权限才能访问
         * */
        filterChainMap.put("/admin", "anon");
        filterChainMap.put("/logout", "logout");
////以admin开头的请求登录需要验证,admin角色获得授权
        filterChainMap.put("/admin/blogs/**", "authc,roles[admin]");
        filterChainMap.put("/admin/types/**", "authc,roles[admin]");
        filterChainMap.put("/admin/tags/**", "authc,roles[admin]");
//        filterChainMap.put("/user/**", "authc,perms[\"user:add\"]");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainMap);
        return shiroFilterFactoryBean;
    }

    /**
     * @return org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor
     * @Author Jason Chan
     * @Description 以下AuthorizationAttributeSourceAdvisor, DefaultAdvisorAutoProxyCreator两个类是为了支持shiro注解
     * @Param [securityManager]
     **/
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
        defaultAAP.setProxyTargetClass(true);
        return defaultAAP;
    }
}
