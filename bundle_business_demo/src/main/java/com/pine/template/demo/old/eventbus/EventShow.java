package com.pine.template.demo.old.eventbus;

/**
 * Created by tanghongfeng on 2017/8/10.
 */

public class EventShow {
    private String mMsg;
    public final static int OVERRIDE = 1;
    public final static int APPEND = 2;
    private int mShowType = OVERRIDE;

    public EventShow() {
        mMsg = "empty msg";
    }

    public EventShow(String msg) {
        mMsg = msg;
    }

    public EventShow(String msg, int showType) {
        mMsg = msg;
        setShowType(showType);
    }

    public String getMsg() {
        return mMsg;
    }

    public void setShowType(int showType) {
        mShowType = showType == APPEND ? 2 : 1;
    }

    public int getShowType() {
        return mShowType;
    }
}
