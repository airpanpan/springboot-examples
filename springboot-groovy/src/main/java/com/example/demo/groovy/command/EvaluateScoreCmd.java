package com.example.demo.groovy.command;

import com.example.demo.groovy.GroovyClassLoaderDemo;
import com.example.demo.groovy.entity.Fact;
import com.example.demo.groovy.entity.LoanFact;
import groovy.lang.GroovyClassLoader;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 评估分数Cmd
 */
public class EvaluateScoreCmd extends Command{
    @Override
    public void execute(Fact fact) {

        try {
            GroovyClassLoader groovyClassLoader = new GroovyClassLoader();
            //加载groovy脚本
            String file = GroovyClassLoaderDemo.class.getClassLoader().getResource(this.getScript().getScript()).getFile();
            Class c = groovyClassLoader.parseClass(new File(file));
            //获取groovy方法
            Method m = c.getDeclaredMethod("evaluate", new Class[]{LoanFact.class});
            Object val = m.invoke(c.newInstance(), fact);//执行
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (next != null){
            next.execute(fact);
        } else {
            System.out.println("无下一个可执行命令");
        }
    }
}
