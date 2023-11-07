package com.example.demo.groovy.command;

public class GroovyScript {

    private String script;

    private String scriptName;

    public GroovyScript(String script, String scriptName) {
        this.script = script;
        this.scriptName = scriptName;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public String getScriptName() {
        return scriptName;
    }

    public void setScriptName(String scriptName) {
        this.scriptName = scriptName;
    }
}
