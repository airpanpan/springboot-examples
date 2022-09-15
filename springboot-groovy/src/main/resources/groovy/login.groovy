package com.example.demo.groovy;

public class LoginDemo {

    public String loginCheck(Integer loginDays){
        if (loginDays >= 500){
            return "超过登录时间";
        }
        System.out.println("loginDemo = " + loginDays);
        return "未超过登录时间";
    }


}