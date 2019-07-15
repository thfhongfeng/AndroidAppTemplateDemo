package com.pine.demo.dagger2;

/**
 * Created by tanghongfeng on 2017/8/10.
 */

public class ClassTwo {
    private final static String TAG = "ClassTwo";
    private String mToStringStr;

    public ClassTwo() {
        setToStringStr("Constructor Provide ClassTwo; ");
    }

    public ClassTwo(String str) {
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
