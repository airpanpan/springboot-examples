package com.example.demo.groovy;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import org.codehaus.groovy.control.CompilerConfiguration;

public class GroovyShellDemo {

    public static void main(String[] args) {

      /*  Binding binding = new Binding();
        binding.setProperty("name", "李四");
        binding.setProperty("age", 38);
        GroovyShell shell = new GroovyShell(binding);
        Object evaluate = shell.evaluate("name == '李四' && age == 38");
        System.out.println(evaluate);*/





       /* Binding bd2 = new Binding();
        bd2.setProperty("loginDays", 30);
        GroovyShell shell2 = new GroovyShell(bd2);
        Object run = shell2.parse("def str =  (loginDays >= 20 ? '超过登陆天数' : '未超过登陆天数');str; ").run();
        System.out.println(run);
*/


/*        Binding bd2 = new Binding();
        bd2.setVariable("loginDays", 30);
        GroovyShell shell2 = new GroovyShell(bd2);
        Object run = shell2.parse("def str =  (loginDays >= 20 ? '超过登陆天数' : '未超过登陆天数'); str; ").run();
        System.out.println(run);*/


       /* Binding bd3 = new Binding();
        bd3.setVariable("loginDays", 30);
        GroovyShell shell2 = new GroovyShell();
        Script parse = shell2.parse("def str =  (loginDays >= 20 ? '超过登陆天数' : '未超过登陆天数'); str; ");
        Script parse2 = shell2.parse("def str =  (loginDays >= 20 ? '超过登陆天数' : '未超过登陆天数'); str; ");
        parse.setBinding(bd3);
        System.out.println(parse.run());

        System.out.println(parse.getClass().getName());
        System.out.println(parse2.getClass().getName());*/


        Binding bd2 = new Binding();
        bd2.setVariable("loginDays", 30);
        bd2.setVariable("str", "---");
        GroovyShell shell2 = new GroovyShell();
        Script parse = shell2.parse("public String loginCheck(Integer loginDays, String str){\n" +
                "    if (loginDays >= 20){\n" +
                "        return  str + \"超过登录时间\";\n" +
                "    }\n" +
                "    return \"未超过登录时间\";\n" +
                "}\n" +
                "\n" +
                "loginCheck(loginDays, str);");
        parse.setBinding(bd2);
        System.out.println(parse.run());



    }
}
