package com.pine.demo.eventbus;

/**
 * Created by tanghongfeng on 2017/8/10.
 */

public class EventOne {
    private String mMsg;

    public EventOne() {
        mMsg = "empty msg";
    }

    public EventOne(String msg) {
        mMsg = msg;
    }

    public String getMsg() {
        return mMsg;
    }
}
