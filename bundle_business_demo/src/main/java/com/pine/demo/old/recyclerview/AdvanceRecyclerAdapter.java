package com.pine.demo.old.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.pine.demo.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by tanghongfeng on 2017/8/10.
 */

// 自定义RecyclerView的数据Adapter
public class AdvanceRecyclerAdapter extends
        RecyclerView.Adapter<AdvanceRecyclerAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private List<String> mDataArray;
    private OnRecyclerItemClickListener mOnRecyclerItemClickListener;
    private int mResId;

    public AdvanceRecyclerAdapter(Context context, int resId, String[] dataArray) {
        this(context, resId, dataArray, null);
    }

    public AdvanceRecyclerAdapter(Context context, int resId, String[] dataArray,
                                  OnRecyclerItemClickListener onRecyclerItemClickListener) {
        // 需要重新构造下ArrayList，否则会报异常：Java.lang.UnsupportedOperationException
        this(context, resId, new ArrayList<String>(Arrays.asList(dataArray)), onRecyclerItemClickListener);
    }

    public AdvanceRecyclerAdapter(Context context, int resId, List<String> dataArray) {
        this(context, resId, dataArray, null);
    }

    public AdvanceRecyclerAdapter(Context context, int resId, List<String> dataArray,
                                  OnRecyclerItemClickListener onRecyclerItemClickListener) {
        this.mInflater = LayoutInflater.from(context);
        this.mResId = resId;
        this.mOnRecyclerItemClickListener = onRecyclerItemClickListener;
        this.mDataArray = dataArray;
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
        View view = mInflater.inflate(mResId < 1 ? R.layout.demo_item_recycler_layout_match
                : mResId, parent, false);
        // 为RecyclerView的item view设计事件监听机制
        if (mOnRecyclerItemClickListener != null) {
            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    mOnRecyclerItemClickListener.onItemClick(v, (int) v.getTag());
                }
            });
        }
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
        holder.mItemTv.setText(mDataArray.get(position));
        // 为RecyclerView的item view设计事件监听机制
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mDataArray.size();
    }

    public void addItem(String data, int position) {
        mDataArray.add(position, data);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        mDataArray.remove(position);
        notifyItemRemoved(position);
    }

    // 自定义的ViewHolder，持有每个Item的的所有界面元素
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mItemTv;

        public ViewHolder(View view) {
            super(view);
            mItemTv = (TextView) view.findViewById(R.id.rv_item_text);
        }
    }

    // 自定义RecyclerView中的item view的click回调方法
    public interface OnRecyclerItemClickListener {
        void onItemClick(View view, int position);
    }
}
