package com.pine.demo.old.dagger2;

/**
 * Created by tanghongfeng on 2017/8/10.
 */

public class QualifierPerson {
    private String name;
    private int age;

    public QualifierPerson(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "My name is " + name + ". ";
    }
}
