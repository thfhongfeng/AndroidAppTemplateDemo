package com.pine.demo.eventbus;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by tanghongfeng on 2017/8/10.
 */

public class AnotherEventSubscriber {

    public void registerEventBus() {
        EventBus.getDefault().register(this);
    }

    public void unregisterEventBus() {
        EventBus.getDefault().unregister(this);
    }

    public boolean isRegisterEventBus() {
        return EventBus.getDefault().isRegistered(this);
    }

    @Override
    protected void finalize() throws java.lang.Throwable {
        EventBus.getDefault().unregister(this);
        super.finalize();
    }

    @Subscribe
    public void onEventOne(EventOne eventOne) {
        String content = "\n AnotherEventSubscriber received EventOne:\n"
                + " - method: onEventOne\n"
                + " - ThreadMode: Default\n"
                + " - eventOne.msg: " + eventOne.getMsg() + "\n"
                + " - threadName: " + Thread.currentThread().getName() + "\n"
                + " - threadId: " + Thread.currentThread().getId() + "\n";
        showInConsole(content);
    }

    /**
     * Sticky事件: 当事件发布后，再有订阅者开始订阅该类型事件，依然能收到该类型事件最近一个Sticky 事件。
     *
     * @param eventThree
     */
    @Subscribe(sticky = true)
    public void onEventThreeSticky(EventThree eventThree) {
        String content = "\n AnotherEventSubscriber received eventThree:\n"
                + " - method: onEventThreeSticky\n"
                + " - eventThree.msg: " + eventThree.getMsg() + "\n"
                + " - threadName: " + Thread.currentThread().getName() + "\n"
                + " - threadId: " + Thread.currentThread().getId() + "\n";
        showInConsole(content);
    }

    private void showInConsole(String content) {
        EventShow eventShow = new EventShow(content);
        eventShow.setShowType(EventShow.APPEND);
        EventBus.getDefault().post(eventShow);
    }
}
