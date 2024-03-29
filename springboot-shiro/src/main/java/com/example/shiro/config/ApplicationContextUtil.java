package com.example.shiro.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextUtil implements ApplicationContextAware {


    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (context == null){
            context = applicationContext;
        }
    }

    public static Object getBean(String beanName){
       return context.getBean(beanName);
    }

    public static Object getBean(Class cls){
        return context.getBean(cls);
    }
}
