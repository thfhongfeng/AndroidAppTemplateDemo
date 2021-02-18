package com.pine.template.demo.util;

import android.os.Handler;
import android.os.Looper;
import android.widget.ScrollView;
import android.widget.TextView;

public class ConsoleUtils {

    public static void out(ScrollView sv, TextView console, String msg) {
        console.setText(msg);
        final ScrollView finalSv = sv;
        // addView完之后，不等于马上就会显示，而是在队列中等待处理，虽然很快，但是如果立即调用fullScroll，
        // view可能还没有显示出来，会失败，所以应该通过handler在主线程排队更新
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                finalSv.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    public static void outAppend(ScrollView sv, TextView console, String msg) {
        console.setText(console.getText() + msg + "\n");
        final ScrollView finalSv = sv;
        // addView完之后，不等于马上就会显示，而是在队列中等待处理，虽然很快，但是如果立即调用fullScroll，
        // view可能还没有显示出来，会失败，所以应该通过handler在主线程排队更新
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                finalSv.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    public static void clear(TextView console) {
        console.setText("");
    }

}
