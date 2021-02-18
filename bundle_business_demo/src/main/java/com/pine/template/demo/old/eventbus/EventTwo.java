package com.pine.template.demo.old.eventbus;

/**
 * Created by tanghongfeng on 2017/8/10.
 */

public class EventTwo {
    private String mMsg;

    public EventTwo() {
        mMsg = "empty msg";
    }

    public EventTwo(String msg) {
        mMsg = msg;
    }

    public String getMsg() {
        return mMsg;
    }
}
