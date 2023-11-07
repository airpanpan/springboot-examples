package com.example.demo.groovy

import com.example.demo.groovy.entity.LoanFact;

public class AgeRule {

    public String evaluate(LoanFact fact){
        if (fact.getAge() < 18){

            return "年龄未满18岁，未达评估标准";
        }
       //男性 18岁以上，50岁以内
        if (fact.getSex().equals("男") && fact.getAge() <= 50){
            return "男性，年龄在18岁以上，50岁以内，符合规则";
        }
        if (fact.getSex().equals("女") && fact.getAge() <= 45){
            return "男性，年龄在18岁以上，45岁以内，符合规则";
        }
        return "年龄评估失败，请确认提交信息";
    }

}