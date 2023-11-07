package com.example.demo.groovy;

import com.example.demo.groovy.command.*;
import com.example.demo.groovy.entity.Fact;
import com.example.demo.groovy.entity.LoanFact;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Test {
    public Test() {
    }


    public static void main(String[] args) {
        //加载groovyScript，demo顺序：年龄校验规则 -> 芝麻信用分校验规则 -> 是否有犯罪记录校验规则 -> 贷款金额校验规则 -> 评分规则 -> 结束
        ScriptCmd scriptCmd = loadGroovyScript();
        LoanFact fact = new LoanFact();
        fact.setFactId(System.currentTimeMillis());
        fact.setFactName("小额贷款");
        fact.setAge(18);
        fact.setSex("男");
        fact.setName("张三");
        fact.setCard("11111");
        fact.setMoney(new BigDecimal(50000));
        System.out.println("-----------------------------------张三借款规则执行---------------------------------------");
        scriptCmd.execute(fact);


        LoanFact fact2 = new LoanFact();
        fact2.setFactId(System.currentTimeMillis());
        fact2.setFactName("小额贷款");
        fact2.setAge(50);
        fact2.setSex("男");
        fact2.setName("李四");
        fact2.setCard("22222");
        fact2.setMoney(new BigDecimal(5000));
        System.out.println("-----------------------------------李四借款规则执行---------------------------------------");
        scriptCmd.execute(fact2);
    }

    private static ScriptCmd loadGroovyScript(){
        GroovyScript ageRule = new GroovyScript("groovy/age.groovy", "年龄校验规则");
        GroovyScript zhimaRule = new GroovyScript("groovy/zhimaCredit.groovy", "芝麻信用分校验规则");
        GroovyScript crRule = new GroovyScript("groovy/cr.groovy", "是否有犯罪记录校验规则");
        GroovyScript moneyRule = new GroovyScript("groovy/money.groovy", "贷款金额校验规则");
        List<GroovyScript> list = new ArrayList<>();
        list.add(ageRule);
        list.add(zhimaRule);
        list.add(crRule);
        list.add(moneyRule);
        ScriptCmd first = load(list);
        return first;
    }


    private static ScriptCmd load(List<GroovyScript> list){
        ScriptCmd first = null;
        ScriptCmd next = null;
        for (GroovyScript groovyScript : list) {
            if (first == null){
                first =new ScriptCmd();
                first.setScript(groovyScript);

            } else {
                if (next == null){
                    ScriptCmd temp = new ScriptCmd();
                    temp.setScript(groovyScript);
                    first.setNext(temp);
                    next = temp;
                } else {
                    ScriptCmd temp = new ScriptCmd();
                    temp.setScript(groovyScript);
                    next.setNext(temp);
                    next = temp;

                }

            }
        }
        EvaluateScoreCmd scoreCmd = new EvaluateScoreCmd();
        scoreCmd.setScript(new GroovyScript("groovy/evaluateScore.groovy", "评分规则"));
        next.setNext(scoreCmd);

        EndCmd endCmd = new EndCmd();
        scoreCmd.setNext(endCmd);
        return first;
    }




}
