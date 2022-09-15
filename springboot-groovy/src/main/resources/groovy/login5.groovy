package com.example.demo.groovy;
import com.example.demo.groovy.Test

public String loginCheck(Integer loginDays){
    if (loginDays >= 20){
        return "超过登录时间";
    }
    def t = new Test()
    t.handle("1");
    return "未超过登录时间";
}

loginCheck(loginDays);