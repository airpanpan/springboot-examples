package com.example.demo.groovy

import com.example.demo.groovy.entity.LoanFact;

public class zhimaCredit {
    public String evaluate(LoanFact fact){
        if (fact.getName().equals("李四")){
            return "芝麻信用分达到600，符合规则";
        }

        if (fact.getName().equals("张三")){
            return "芝麻信用分未达到600，不符合规则";
        }
        return "查无芝麻信用分，不符合规则";
    }
}