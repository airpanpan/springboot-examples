package com.example.demo.groovy;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class GroovyClassLoaderDemo {


    public static void main(String[] args) throws Exception {
        GroovyClassLoader groovyClassLoader = new GroovyClassLoader();
        String file = GroovyClassLoaderDemo.class.getClassLoader().getResource("groovy/login.groovy").getFile();
        System.out.println(file);
        //Class c = groovyClassLoader.parseClass(new File(file));



        /**
         * 方式一
         */
        /*Method loginCheck = c.getDeclaredMethod("loginCheck", new Class[]{Integer.class});
        Object invoke = loginCheck.invoke(c.newInstance(), 30);
        System.out.println(invoke);*/

        /**
         * 方式二
         */
        /*GroovyObject groovyObject= (GroovyObject)c.newInstance();
        String result = (String) groovyObject.invokeMethod("loginCheck", 30);
        System.out.println(result);*/


        Class loginTest = groovyClassLoader.parseClass("package com.example.demo.groovy;\n" +
                "\n" +
                "public class LoginDemo {\n" +
                "\n" +
                "    public String loginCheck(Integer loginDays){\n" +
                "        if (loginDays >= 500){\n" +
                "            return \"超过登录时间\";\n" +
                "        }\n" +
                "        System.out.println(\"loginDemo = \" + loginDays);\n" +
                "        return \"未超过登录时间\";\n" +
                "    }\n" +
                "\n" +
                "\n" +
                "}", "loginTest");
        GroovyObject groovyObject= (GroovyObject)loginTest.newInstance();
        String result = (String) groovyObject.invokeMethod("loginCheck", 500);
        System.out.println(result);

        /**
         * 这种方式创建script不支持缓存
         */
        Class loginTest2 = groovyClassLoader.parseClass("package com.example.demo.groovy;\n" +
                "\n" +
                "public class LoginDemo {\n" +
                "\n" +
                "    public String loginCheck(Integer loginDays){\n" +
                "        if (loginDays >= 500){\n" +
                "            return \"超过登录时间\";\n" +
                "        }\n" +
                "        System.out.println(\"loginDemo = \" + loginDays);\n" +
                "        return \"未超过登录时间\";\n" +
                "    }\n" +
                "\n" +
                "\n" +
                "}", "loginTest");



    }
}
