package com.example.demo.groovy.command;

import com.example.demo.groovy.entity.Fact;

/**
 * 抽象处理
 */
public abstract class Command {

    protected Command next;

    private GroovyScript script;

    public Command(GroovyScript script) {
        this.script = script;
    }

    public Command(Command next, GroovyScript script) {
        this.next = next;
        this.script = script;
    }

    public void setNext(Command next) {
        this.next = next;
    }


    public Command() {
    }

    public abstract void execute(Fact fact);

    public Command getNext() {
        return next;
    }

    public GroovyScript getScript() {
        return script;
    }

    public void setScript(GroovyScript script) {
        this.script = script;
    }
}
