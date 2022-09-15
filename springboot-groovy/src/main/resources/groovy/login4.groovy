package com.example.demo.groovy;

public String loginCheck(Integer loginDays){
    if (loginDays >= 20){
        return "超过登录时间";
    }
    def demo = new LoginDemo()
    demo.loginCheck(100);
    return "未超过登录时间";
}

loginCheck(loginDays);