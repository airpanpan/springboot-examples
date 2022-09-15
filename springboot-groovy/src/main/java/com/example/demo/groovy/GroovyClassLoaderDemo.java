package com.example.demo.groovy;

import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import groovy.lang.Script;

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


        /**
         * 这种方式不支持缓存类，所以如果不优化会导致OOM
         */
        Class loginTest = groovyClassLoader.parseClass("package com.example.demo.groovy;\n" +
                "\n" +
                "public class LoginDemo {\n" +
                "\n" +
                "    public String loginCheck(Integer loginDays, String str){\n" +
                "        if (loginDays >= 500){\n" +
                "            return \"超过登录时间\";\n" +
                "        }\n" +
                "        System.out.println(\"loginDemo = \" + loginDays + str);\n" +
                "        return \"未超过登录时间\";\n" +
                "    }\n" +
                "\n" +
                "\n" +
                "}", "loginTest");
        GroovyObject groovyObject = (GroovyObject)loginTest.newInstance();
        String result = (String) groovyObject.invokeMethod("loginCheck", new Object[]{30,"xxt"});
        System.out.println(result);




    }
}
