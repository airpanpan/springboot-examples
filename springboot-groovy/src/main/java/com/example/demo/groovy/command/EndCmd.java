package com.example.demo.groovy.command;

import com.example.demo.groovy.entity.Fact;

public class EndCmd extends Command{

    @Override
    public void execute(Fact fact) {
        System.out.println("-----------------结束事件，输出风控报告------------------- FactId:" + fact.getFactId());
        if (next != null){
            next.execute(fact);
        } else {
            System.out.println("无下一个可执行命令");

        }
    }
}
