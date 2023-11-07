package com.example.demo.groovy

import com.example.demo.groovy.entity.LoanFact;

public class money {
    public String evaluate(LoanFact fact){
        if (fact.getMoney()>= 30000){
            return "贷款金额超过3w元，超过个人贷款额度";
        } else {
            return "贷款金额" + fact.getMoney()+",符合贷款金额";
        }

        return "不符合规则";
    }
}