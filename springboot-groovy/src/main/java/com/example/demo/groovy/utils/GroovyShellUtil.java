package com.example.demo.groovy.utils;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import org.junit.platform.commons.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GroovyShellUtil {

    private static final Logger log = LoggerFactory.getLogger(GroovyShellUtil.class);

    private final static GroovyShell shell = new GroovyShell();

    //可以采用guava cache的本地缓存方式
    private static ConcurrentHashMap<String, ScriptEntity> SCRIPT_CACHE = new ConcurrentHashMap<>();


    public static String run(String scriptName, String scriptText, Binding bd){
        if (StringUtils.isBlank(scriptName) || StringUtils.isBlank(scriptText)){
            throw new IllegalArgumentException("scriptName 和 scriptText 不能为空");
        }
        Script script = null;
        ScriptEntity scriptEntity = SCRIPT_CACHE.get(scriptName);
        if (scriptEntity == null){
            script = shell.parse(scriptText, scriptName);
            SCRIPT_CACHE.put(scriptName, new ScriptEntity(script, scriptName, scriptText));
        } else {
            log.info("从缓存中获取script:{}", scriptName);
            script = scriptEntity.getScript();
            if (!MD5Util.hash(scriptText).equals(MD5Util.hash(scriptEntity.getScriptText()))){
                throw new IllegalArgumentException("缓存中已存在scriptName:" + scriptName);
            }
        }

        synchronized(script){
            script.setBinding(bd);
            Object result = script.run();
            return result != null ? result.toString() : "";
        }
    }

    private static String run(String scriptName, String scriptText, Map<String, Object> bdMap){
        Binding binding = new Binding(bdMap);
        return run(scriptName, scriptText, binding);
    }


    /**
     * script缓存对象
     */
    static class ScriptEntity {

        private Script script;

        private String scriptName;

        private String scriptText;

        public ScriptEntity() {
        }

        public ScriptEntity(Script script, String scriptName, String scriptText) {
            this.script = script;
            this.scriptName = scriptName;
            this.scriptText = scriptText;
        }

        public Script getScript() {
            return script;
        }

        public void setScript(Script script) {
            this.script = script;
        }

        public String getScriptName() {
            return scriptName;
        }

        public void setScriptName(String scriptName) {
            this.scriptName = scriptName;
        }

        public String getScriptText() {
            return scriptText;
        }

        public void setScriptText(String scriptText) {
            this.scriptText = scriptText;
        }
    }




/*    Script parse = shell2.parse("public String loginCheck(Integer loginDays, String str){\n" +
            "    if (loginDays >= 20){\n" +
            "        return  str + \"超过登录时间\";\n" +
            "    }\n" +
            "    return \"未超过登录时间\";\n" +
            "}\n" +
            "\n" +
            "loginCheck(loginDays, str);");
        parse.setBinding(bd2);
        System.out.println(parse.run());*/

}
