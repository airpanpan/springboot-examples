package com.zzq.flowableui.config;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.idm.spring.SpringIdmEngineConfiguration;
import org.flowable.ldap.LDAPConfiguration;
import org.flowable.ldap.LDAPIdentityServiceImpl;
import org.flowable.spring.boot.EngineConfigurationConfigurer;
import org.flowable.ui.common.security.SecurityConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class FlowableConfiguration {


    @Bean
    public ProcessEngine processEngine(){
        StandaloneProcessEngineConfiguration configuration = new StandaloneProcessEngineConfiguration();
        //配置JDBC
        configuration.setJdbcDriver("com.mysql.cj.jdbc.Driver");
        configuration.setJdbcUsername("root");
        configuration.setJdbcPassword("root");
        configuration.setJdbcUrl("jdbc:mysql://localhost:3306/flowable_test?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT%2B8");
        //如果数据库中不存在表结构，则做新建操作
        configuration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        //构建flowable流程引擎
        return configuration.buildProcessEngine();
    }


    /**
     * 参考 LDAPIdentityServiceImpl  的注册到 flowable 引擎配置类的实现方式
     * 用于自定义用户、组
     * @return
     */
/*
    @Bean
    public EngineConfigurationConfigurer<SpringIdmEngineConfiguration> myIdmEngineConfigurer() {
        return idmEngineConfiguration -> idmEngineConfiguration
                .setIdmIdentityService(new CustomIdmIdentityServiceImpl(idmEngineConfiguration));
    }
*/


}
