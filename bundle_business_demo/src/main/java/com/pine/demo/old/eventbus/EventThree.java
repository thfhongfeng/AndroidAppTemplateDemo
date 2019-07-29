package com.pine.demo.old.eventbus;

/**
 * Created by tanghongfeng on 2017/8/10.
 */

public class EventThree {
    private String mMsg;

    public EventThree() {
        mMsg = "empty msg";
    }

    public EventThree(String msg) {
        mMsg = msg;
    }

    public String getMsg() {
        return mMsg;
    }
}
