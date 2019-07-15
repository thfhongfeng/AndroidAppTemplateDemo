package com.pine.demo.eventbus;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by tanghongfeng on 2017/8/10.
 */

public class EventSubscriber {

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

    // default方式（默认为ThreadMode.POSTING）执行订阅的EventOne事件
    @Subscribe
    public void onEventOne(EventOne eventOne) {
        String content = "\n EventSubscriber received EventOne:\n"
                + " - method: onEventOne\n"
                + " - ThreadMode: Default\n"
                + " - eventOne.msg: " + eventOne.getMsg() + "\n"
                + " - threadName: " + Thread.currentThread().getName() + "\n"
                + " - threadId: " + Thread.currentThread().getId() + "\n";
        showInConsole(content);
    }

    /**
     * ThreadMode.POSTING: 在执行 Post 操作的线程直接调用订阅者的事件响应方法，不论该线程是否为主线程（UI 线程）。
     * 当该线程为主线程时，响应方法中不能有耗时操作，否则有卡主线程的风险。
     * 适用场景：对于是否在主线程执行无要求，但若 Post 线程为主线程，不能耗时的操作；
     *
     * @param eventTwo
     */
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onEventTwoInPostingMode(EventTwo eventTwo) {
        String content = "\n EventSubscriber received eventTwo:\n"
                + " - method: onEventTwoInPostingMode\n"
                + " - ThreadMode: ThreadMode.POSTING\n"
                + " - eventTwo.msg: " + eventTwo.getMsg() + "\n"
                + " - threadName: " + Thread.currentThread().getName() + "\n"
                + " - threadId: " + Thread.currentThread().getId() + "\n";
        showInConsole(content);
    }

    /**
     * ThreadMode.MAIN: 在主线程中执行响应方法。
     * 如果发布线程就是主线程，则直接调用订阅者的事件响应方法，否则通过主线程的 Handler 发送消息在主线程中处理——调用订阅者的事件响应函数。
     * 显然，MainThread类的方法也不能有耗时操作，以避免卡主线程。
     * 适用场景：必须在主线程执行的操作；
     *
     * @param eventTwo
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventTwoInMainMode(EventTwo eventTwo) {
        String content = "\n EventSubscriber received eventTwo:\n"
                + " - method: onEventTwoInMainMode\n"
                + " - ThreadMode: ThreadMode.MAIN\n"
                + " - eventTwo.msg: " + eventTwo.getMsg() + "\n"
                + " - threadName: " + Thread.currentThread().getName() + "\n"
                + " - threadId: " + Thread.currentThread().getId() + "\n";
        showInConsole(content);
    }

    /**
     * ThreadMode.ASYNC: 不论发布线程是否为主线程，都使用一个空闲线程来处理。
     * 和BackgroundThread不同的是，Async类的所有线程是相互独立的，因此不会出现卡线程的问题。
     * 适用场景：长耗时操作，例如网络访问。
     *
     * @param eventTwo
     */
    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onEventTwoInAsyncMode(EventTwo eventTwo) {
        String content = "\n EventSubscriber received eventTwo:\n"
                + " - method: onEventTwoInAsyncMode\n"
                + " - ThreadMode: ThreadMode.ASYNC\n"
                + " - eventTwo.msg: " + eventTwo.getMsg() + "\n"
                + " - threadName: " + Thread.currentThread().getName() + "\n"
                + " - threadId: " + Thread.currentThread().getId() + "\n";
        showInConsole(content);
    }

    /**
     * ThreadMode.BACKGROUND: 在后台线程中执行响应方法。
     * 如果发布线程不是主线程，则直接调用订阅者的事件响应函数，否则启动唯一的后台线程去处理。
     * 由于后台线程是唯一的，当事件超过一个的时候，它们会被放在队列中依次执行，
     * 因此该类响应方法虽然没有PostThread类和MainThread类方法对性能敏感，但最好不要有重度耗时的操作或太频繁的轻度耗时操作，以造成其他操作等待。
     * 适用场景：操作轻微耗时且不会过于频繁，即一般的耗时操作都可以放在这里；
     *
     * @param eventTwo
     */
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onEventTwoInBackgroundMode(EventTwo eventTwo) {
        String content = "\n EventSubscriber received eventTwo:\n"
                + " - method: onEventTwoInBackgroundMode\n"
                + " - ThreadMode: ThreadMode.BACKGROUND\n"
                + " - eventTwo.msg: " + eventTwo.getMsg() + "\n"
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
