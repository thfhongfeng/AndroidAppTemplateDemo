package com.pine.template.demo.old.dagger2;

import javax.inject.Inject;

/**
 * Created by tanghongfeng on 2017/8/10.
 */

public class ConstructorClass {
    private final static String TAG = "ConstructorClass";
    private String mToStringStr;

    @Inject
    public ConstructorClass() {
        setToStringStr("Constructor Provide ConstructorClass; ");
    }

    public ConstructorClass(String str) {
        setToStringStr(str);
    }

    public void setToStringStr(String str) {
        mToStringStr = str;
    }

    @Override
    public String toString() {
        return mToStringStr;
    }
}
