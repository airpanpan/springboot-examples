package com.example.demo.groovy.entity;

public class EvaluateScore {

    public String evaluate(LoanFact fact){
        System.out.println("groovy脚本调用java代码，开始评估得分");
        if (fact.getName().equals("张三")){
            System.out.println("张三，经评估信用报告得分20分，不满足放款条件");
        } else if (fact.getName().equals("李四")){
            System.out.println("李四，经评估信用报告得分80分，满足放款条件");
        }
        return "";
    }
}
