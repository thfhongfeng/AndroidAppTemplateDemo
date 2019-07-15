package com.pine.demo.dagger2;

/**
 * Created by tanghongfeng on 2017/8/10.
 */

public class ClassThree {
    private final static String TAG = "ClassThree";
    private String mToStringStr;

    public ClassThree() {
        setToStringStr("Constructor Provide ClassThree; ");
    }

    public ClassThree(String str) {
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
