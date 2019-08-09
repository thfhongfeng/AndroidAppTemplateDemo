package com.pine.demo.test;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.pine.tool.util.LogUtils;

public class DemoTestView extends View {
    private final String TAG = LogUtils.makeLogTag(this.getClass());

    public DemoTestView(Context context) {
        super(context);
    }

    public DemoTestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DemoTestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        LogUtils.d(TAG, "onAttachedToWindow");
    }

    @Override
    public void onDetachedFromWindow() {
        LogUtils.d(TAG, "onDetachedFromWindow");
        super.onDetachedFromWindow();
    }
}
