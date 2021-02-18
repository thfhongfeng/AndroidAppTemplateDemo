package com.pine.template.demo.old.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.pine.template.demo.R;

/**
 * Created by tanghongfeng on 2017/8/10.
 */

// 自定义RecyclerView的数据Adapter
public class SimpleRecyclerAdapter extends
        RecyclerView.Adapter<SimpleRecyclerAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private String[] mTitles;

    public SimpleRecyclerAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
        mTitles = new String[20];
        for (int i = 0; i < mTitles.length; i++) {
            mTitles[i] = "item_" + i;
        }
    }

    /**
     * 创建Item ViewHold
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.demo_item_recycler_layout_match, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    /**
     * 数据的绑定显示
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mItemTv.setText(mTitles[position]);
    }

    @Override
    public int getItemCount() {
        return mTitles.length;
    }

    // 自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mItemTv;

        public ViewHolder(View view) {
            super(view);
            mItemTv = (TextView) view.findViewById(R.id.rv_item_text);
        }
    }
}
