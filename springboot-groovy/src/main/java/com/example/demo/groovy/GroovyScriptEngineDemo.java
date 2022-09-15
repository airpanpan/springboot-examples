package com.example.demo.groovy;

import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import groovy.lang.Script;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceConnector;

import java.io.File;
import java.io.IOException;

public class GroovyScriptEngineDemo {


    public static void main(String[] args) throws Exception {
        /**
         * 此调用方式类结构和闭包结构都试用
         */
       /* String groovy = GroovyScriptEngineDemo.class.getClassLoader().getResource("groovy").getFile();
        GroovyScriptEngine engine = new GroovyScriptEngine(groovy);
        Class aClass = engine.loadScriptByName("login2.groovy");
        GroovyObject groovyObject = (GroovyObject)aClass.newInstance();
        Object loginCheck = groovyObject.invokeMethod("loginCheck", 20);
        System.out.println(loginCheck);*/


        /**
         * 此调用方式只能调用闭包结构的groovy
         */
       /* Binding binding = new Binding();
        binding.setVariable("loginDays", 30);
        Script script = engine.createScript("login2.groovy", binding);
        Object loginCheck2 = script.run();
        //Object loginCheck = script.invokeMethod("loginCheck", 30); 方式二
        System.out.println(loginCheck2);*/

        Binding binding = new Binding();
        binding.setVariable("loginDays", 30);
        String groovy = GroovyScriptEngineDemo.class.getClassLoader().getResource("groovy").getFile();
        System.out.println("path = " + groovy);
        GroovyScriptEngine engine = new GroovyScriptEngine(groovy);
        //GroovyScriptEngine engine = new GroovyScriptEngine(groovy, ClassLoader.getSystemClassLoader());
        while (true) {
            Thread.sleep(3000L);


            /**
             * 方式一：创建script，调用script的run方法
             */
            //Script script = engine.createScript("login2.groovy", binding);
            //Object loginCheck = script.run();
            /**
             * 方式二：直接调用engine的run方法
             */
           // Object loginCheck = engine.run("login2.groovy", binding);
            //System.out.println(loginCheck);


            /**
             * 方式三：反射调用
             */
           /* Class aClass = engine.loadScriptByName("login3.groovy");
            GroovyObject groovyObject = (GroovyObject)aClass.newInstance();
            Object loginCheck = groovyObject.invokeMethod("loginCheck", 10);
            System.out.println(loginCheck);*/


            /**
             * 脚本关联其他脚本方式、目前做的案例需要先前置创建依赖的script
             */
           /* Script script2 = engine.createScript("login.groovy", binding);
            Script script = engine.createScript("login4.groovy", binding);
            Object loginCheck = script.run();
            System.out.println(loginCheck);*/


            Script script = engine.createScript("login5.groovy", binding);
            Object loginCheck = script.run();
            System.out.println(loginCheck);
        }

    }
}
