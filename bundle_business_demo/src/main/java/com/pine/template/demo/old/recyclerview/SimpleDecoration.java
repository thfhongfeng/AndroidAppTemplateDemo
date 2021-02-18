package com.pine.template.demo.old.recyclerview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by tanghongfeng on 2017/8/10.
 */

public class SimpleDecoration extends RecyclerView.ItemDecoration {

    public SimpleDecoration(Context context) {

    }

    // 设置item之间的边距（只是设置边距Rec大小，边距的divider图片等去需要在onDraw或者onDrawOver单独画出来）
    // getItemOffsets通过outRect.set(l,t,r,b)指定
    // item view的left，top，right，bottom方向的边距
    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        // 通过设置item之间的下边距来达到divider的效果，但需要配合item的背景不同于RecyclerView的背景
        // 来实现该效果。如果背景一样，除了要设置间距外，还这需要在onDraw或者onDrawOver中画出divider。
        // 具体参靠AdvanceDecoration
        outRect.set(0, 0, 0, 3);
    }

    // 在draw item之前先draw装饰（比如画边距的图片等）
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

    }

    // 在draw item之后再draw装饰
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {

    }
}
