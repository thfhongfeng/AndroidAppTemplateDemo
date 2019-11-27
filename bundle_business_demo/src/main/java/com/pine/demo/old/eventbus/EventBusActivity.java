package com.pine.demo.old.eventbus;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.pine.demo.R;
import com.pine.demo.util.ConsoleUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by tanghongfeng on 2017/8/10.
 */

public class EventBusActivity extends AppCompatActivity {
    private final static String TAG = "EventBusActivity";

    private ScrollView mConsoleSv;
    private TextView mConsoleTv;

    private Button mSdEvtCurExecuteCurThreadBtn;
    private Button mSdEvtNewExecuteCurThreadBtn;

    private Button mSdEvtCurExecuteDiffThreadBtn;
    private Button mSdEvtNewExecuteDiffThreadBtn;

    private Button mSdEvtBtn;
    private Button mSdEvtStickyBtn;

    private EventSubscriber mEventSubscriber;
    private AnotherEventSubscriber mAnotherEventSubscriber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_activity_event_bus);
        mEventSubscriber = new EventSubscriber();
        mAnotherEventSubscriber = new AnotherEventSubscriber();
        mEventSubscriber.registerEventBus();
        // for Sticky Events
        // mAnotherEventSubscriber.registerEventBus();

        EventBus.getDefault().register(this);

        mConsoleSv = (ScrollView) findViewById(R.id.console_sv);
        mConsoleTv = (TextView) findViewById(R.id.console_tv);

        mSdEvtCurExecuteCurThreadBtn = (Button) findViewById(R.id.send_cur_execute_cur_btn_aeb);
        mSdEvtNewExecuteCurThreadBtn = (Button) findViewById(R.id.send_new_execute_cur_btn_aeb);

        mSdEvtCurExecuteDiffThreadBtn = (Button) findViewById(R.id.send_cur_execute_diff_btn_aeb);
        mSdEvtNewExecuteDiffThreadBtn = (Button) findViewById(R.id.send_new_execute_diff_btn_aeb);

        mSdEvtBtn = (Button) findViewById(R.id.send_then_subscribe_btn_aeb);
        mSdEvtStickyBtn = (Button) findViewById(R.id.send_sticky_then_subscribe_btn_aeb);

        // 当前线程发布EventOne事件
        mSdEvtCurExecuteCurThreadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventOne eventOne = new EventOne("Hello, I am EventOne");
                EventBus.getDefault().post(new EventShow("Post EventOne in Current Thread(Main Thread):\n"
                        + " - message: " + eventOne.getMsg() + "\n"
                        + " - threadName: " + Thread.currentThread().getName() + "\n"
                        + " - threadId: " + Thread.currentThread().getId() + "\n"));
                EventBus.getDefault().post(eventOne);
            }
        });
        // 另起线程发布EventOne事件
        mSdEvtNewExecuteCurThreadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        EventOne eventOne = new EventOne("Hello, I am EventOne");
                        EventBus.getDefault().post(new EventShow("Post EventOne in New Thread(Sub Thread):\n"
                                + " - message: " + eventOne.getMsg() + "\n"
                                + " - threadName: " + Thread.currentThread().getName() + "\n"
                                + " - threadId: " + Thread.currentThread().getId() + "\n"));
                        EventBus.getDefault().post(eventOne);
                    }
                }).start();
            }
        });
        // 当前线程发布EventTwo事件
        mSdEvtCurExecuteDiffThreadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventTwo eventTwo = new EventTwo("Hello, I am EventTwo");
                EventBus.getDefault().post(new EventShow("\nPost EventTwo in Current Thread(Main Thread):\n"
                        + " - message: " + eventTwo.getMsg() + "\n"
                        + " - threadName: " + Thread.currentThread().getName() + "\n"
                        + " - threadId: " + Thread.currentThread().getId() + "\n"));
                EventBus.getDefault().post(eventTwo);
            }
        });
        // 另起线程发布EventTwo事件
        mSdEvtNewExecuteDiffThreadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        EventTwo eventTwo = new EventTwo("Hello, I am EventTwo");
                        EventBus.getDefault().post(new EventShow("\nPost EventTwo in New Thread(Sub Thread):\n"
                                + " - message: " + eventTwo.getMsg() + "\n"
                                + " - threadName: " + Thread.currentThread().getName() + "\n"
                                + " - threadId: " + Thread.currentThread().getId() + "\n"));
                        EventBus.getDefault().post(eventTwo);
                    }
                }).start();
            }
        });
        // 当前线程post方式发布EventThree事件
        mSdEvtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventThree eventThree = new EventThree("Hello, I am eventThree");
                EventBus.getDefault().post(new EventShow("Post eventThree in Current Thread(Main Thread):\n"
                        + " - message: " + eventThree.getMsg() + "\n"
                        + " - threadName: " + Thread.currentThread().getName() + "\n"
                        + " - threadId: " + Thread.currentThread().getId() + "\n"));
                EventBus.getDefault().post(eventThree);
                if (!mAnotherEventSubscriber.isRegisterEventBus()) {
                    mAnotherEventSubscriber.registerEventBus();
                }
            }
        });
        // 当前线程postSticky发布EventThree事件
        mSdEvtStickyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventThree eventThree = new EventThree("Hello, I am eventThree");
                EventBus.getDefault().postSticky(new EventShow("Post eventThree in Current Thread(Main Thread):\n"
                        + " - message: " + eventThree.getMsg() + "\n"
                        + " - threadName: " + Thread.currentThread().getName() + "\n"
                        + " - threadId: " + Thread.currentThread().getId() + "\n"));
                EventBus.getDefault().post(eventThree);
                if (!mAnotherEventSubscriber.isRegisterEventBus()) {
                    mAnotherEventSubscriber.registerEventBus();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        mEventSubscriber.unregisterEventBus();
        mAnotherEventSubscriber.unregisterEventBus();
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventShow(EventShow eventShow) {
        if (eventShow.getShowType() == EventShow.APPEND) {
            ConsoleUtils.outAppend(mConsoleSv, mConsoleTv, eventShow.getMsg());
        } else {
            ConsoleUtils.out(mConsoleSv, mConsoleTv, eventShow.getMsg());
        }
    }
}
