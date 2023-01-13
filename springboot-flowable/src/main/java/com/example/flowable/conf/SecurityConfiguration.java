package com.example.flowable.conf;

import org.flowable.ui.common.security.SecurityConstants;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration {

    //Order配置说明
    // 这个地方相同会报错
    //这个地方如果大于则该配置在FlowableUiSecurityAutoConfiguratio中对应项后加载，不能起到绕过授权作用
    //所以这个地方-1让该配置项在FlowableUiSecurityAutoConfiguratio中对应配置项前加载，以跳过授权
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
