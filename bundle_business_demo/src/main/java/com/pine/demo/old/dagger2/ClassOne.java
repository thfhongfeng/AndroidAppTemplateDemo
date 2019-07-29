package com.pine.demo.old.dagger2;

/**
 * Created by tanghongfeng on 2017/8/10.
 */

public class ClassOne {
    private final static String TAG = "ClassOne";
    private String mToStringStr;

    public ClassOne() {
        setToStringStr("Constructor Provide ClassOne; ");
    }

    public ClassOne(String str) {
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
