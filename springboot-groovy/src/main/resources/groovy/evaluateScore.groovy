package com.example.demo.groovy

import com.example.demo.groovy.entity.EvaluateScore
import com.example.demo.groovy.entity.LoanFact;

public class evaluateScore {
    public String evaluate(LoanFact fact){
        //调用java业务代码，评分
        //不能直接用Autowired注入bean，可以通过SpringUtils.getBean通过上下文方式获取
        def score = new EvaluateScore();
        score.evaluate(fact);
    }
}