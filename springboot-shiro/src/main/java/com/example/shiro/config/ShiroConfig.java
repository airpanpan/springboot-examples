package com.example.shiro.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.example.shiro.config.session.CustomSessionIdGenerator;
import com.example.shiro.config.session.UserRealm;
import com.example.shiro.config.token.AuthToken;
import com.example.shiro.config.token.CustomAuthFilter;
import com.example.shiro.config.token.UserTokenRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {


/*    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        Map<String, String> map = new HashMap<>();
        map.put("/**", "authc"); //需要认证、授权
        map.put("/login", "anon"); //不需要认证授权
        map.put("/toLogin", "anon");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        shiroFilterFactoryBean.setLoginUrl("/toLogin");
        return shiroFilterFactoryBean;
    }



    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        Map<String, Filter> filterHashMap = new HashMap<>();
        filterHashMap.put("auth", new CustomAuthFilter()); //自定义认证授权过滤器
        shiroFilterFactoryBean.setFilters(filterHashMap);

        Map<String, String> map = new HashMap<>();
        map.put("/**", "auth"); //需要认证、授权
        map.put("/mainToken/login", "anon");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        //shiroFilterFactoryBean.setLoginUrl("/toLogin");
        return shiroFilterFactoryBean;
    }


    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(Realm realm){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setSessionManager(sessionManager());
        defaultWebSecurityManager.setCacheManager(cacheManager());
        defaultWebSecurityManager.setRealm(realm);
        return defaultWebSecurityManager;
    }

       @Bean
    public Realm realm(){
        UserRealm userRealm = new UserRealm();
        //设置加密的方式
        userRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return userRealm;
    }




    */


    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     */
/*    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        //设置redis缓存
        redisSessionDAO.setRedisManager(redisManager());
        //redisSessionDAO.setKeyPrefix("shiro:zzq:session:"); 设置redis key的前缀，默认shiro:session:
        //设置sessionid生成器
        redisSessionDAO.setSessionIdGenerator(new CustomSessionIdGenerator());
        return redisSessionDAO;
    }
    */


    /**
     * shiro session管理器
     * @return
     */
/*    public DefaultWebSessionManager sessionManager(){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO());
        return sessionManager;
    }*/





    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        Map<String, Filter> filterHashMap = new HashMap<>();
        filterHashMap.put("auth", new CustomAuthFilter()); //自定义认证授权过滤器
        shiroFilterFactoryBean.setFilters(filterHashMap);

        Map<String, String> map = new HashMap<>();
        map.put("/**", "auth"); //需要认证、授权
        map.put("/mainToken/login", "anon");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        //shiroFilterFactoryBean.setLoginUrl("/toLogin");
        return shiroFilterFactoryBean;
    }


    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(UserTokenRealm userTokenRealm){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setCacheManager(cacheManager());
        defaultWebSecurityManager.setRealm(userTokenRealm);

        // 禁用session
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        defaultWebSecurityManager.setSubjectDAO(subjectDAO);

        return defaultWebSecurityManager;
    }


    @Bean
    public UserTokenRealm userTokenRealm(){
        /*UserRealm userRealm = new UserRealm();
        //设置加密的方式
        userRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return userRealm;*/

        UserTokenRealm userRealm = new UserTokenRealm();
        //userRealm.setAuthenticationTokenClass(AuthToken.class);
        return userRealm;
    }

    /**
     * 用户处理加入RequiresRoles，RequiresPermissions注解后404的问题
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setUsePrefix(true);
        return defaultAdvisorAutoProxyCreator;
    }

    /**
     * 注解aop，但是不加好像也可以
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean("hashedCredentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashIterations(1024);
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        //是否存储16进制，为true则需要使用toHex()进行字符串转换，默认使用toBase64()
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
        return hashedCredentialsMatcher;
    }


    /**
     * springboot整合tymeleaf框架，需要将shiro的语法转换为tymeleaf语法
     * @return
     */
    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }


    /**
     * cacheManager 缓存  接入redis
     * @return
     */
    public RedisCacheManager cacheManager(){
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setExpire(2000);
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }

    /**
     * redis管理器
     * @return
     */
    public RedisManager redisManager(){
        RedisManager redisManager = new RedisManager();
        redisManager.setHost("r-pz5pspe5ujnsluhzrv.redis.rds.aliyuncs.com:6379");
        redisManager.setDatabase(1);
        redisManager.setPassword("OUAJjuHQ2oIyviGSaXUmH7rhcziHXBLt");
        return redisManager;
    }











}
