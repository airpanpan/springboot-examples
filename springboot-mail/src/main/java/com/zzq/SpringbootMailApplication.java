package com.zzq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
/*@ComponentScan(basePackages = "com.mail.util")*/
//@SpringBootApplication(scanBasePackages = "cn.zzq")
public class SpringbootMailApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootMailApplication.class, args);
    }

}
