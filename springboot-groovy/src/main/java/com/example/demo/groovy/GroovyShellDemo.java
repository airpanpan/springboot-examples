package com.example.demo.groovy;

import com.example.demo.groovy.utils.GroovyShellUtil;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import org.codehaus.groovy.control.CompilerConfiguration;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class GroovyShellDemo {

    public static void main(String[] args) {

        Binding binding = new Binding();
        binding.setProperty("name", "李四");
        binding.setProperty("age", 38);
        GroovyShell shell = new GroovyShell(binding);
        Object evaluate = shell.evaluate("name == '李四' && age == 38");
        shell.getClassLoader().clearCache(); //清除缓存
        System.out.println(evaluate);





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


        /**
         * 直接用script脚本方式运行
         */
/*        Binding bd2 = new Binding();
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
        System.out.println(parse.run());*/


   /*     while (true) {
            new Thread() {
                @Override
                public void run() {
                    Binding bd2 = new Binding();
                    bd2.setVariable("loginDays", 30);
                    String test1 = GroovyShellUtil.run("test1", "public String loginCheck(Integer loginDays){\n" +
                            "    if (loginDays >= 20){\n" +
                            "        return \"超过登录时间\";\n" +
                            "    }\n" +
                            "    return \"未超过登录时间\";\n" +
                            "}\n" +
                            "\n" +
                            "loginCheck(loginDays);", bd2);
                    System.out.println(test1);
                }
            }.run();
        }*/


        LocalDate of = LocalDate.of(2023, 01, 01);
        Instant instant = of.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        long time = Date.from(instant).getTime();
        System.out.println("time = " + time);
        ZonedDateTime zonedDateTime = LocalDate.of(2023, 01, 01).atStartOfDay().atZone(ZoneId.systemDefault());
    }
}
