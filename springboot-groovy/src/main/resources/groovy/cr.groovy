package com.example.demo.groovy

import com.example.demo.groovy.entity.LoanFact;

public class cr {

    public String evaluate(LoanFact fact){
        if (fact.getName().equals("张三")){
            return "命中犯罪记录规则，前科：寻衅滋事，信息提供:天机360";
        } else {
            return "查无犯罪记录，符合规则";
        }
        return "不符合规则";
    }
}