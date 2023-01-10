package com.example.flowable.conf;

import org.flowable.ui.common.security.SecurityConstants;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration {


    @Configuration(proxyBeanMethods = false)
    @Order(SecurityConstants.FORM_LOGIN_SECURITY_ORDER - 1)
    public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .csrf().disable() //必须设置csrf为disable，否则POST请求403
                    //放行modeler请求
                    .authorizeRequests().antMatchers("/modeler/**").permitAll();
        }
    }

}
