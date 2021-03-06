package com.pine.template.mvvm.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.pine.template.base.recycle_view.BaseListViewHolder;
import com.pine.template.base.recycle_view.adapter.BasePaginationListAdapter;
import com.pine.template.base.recycle_view.bean.BaseListAdapterItemProperty;
import com.pine.template.mvvm.R;
import com.pine.template.mvvm.bean.MvvmTravelNoteItemEntity;
import com.pine.template.mvvm.databinding.MvvmTravelNoteItemBinding;
import com.pine.template.mvvm.ui.activity.MvvmTravelNoteDetailActivity;

/**
 * Created by tanghongfeng on 2018/9/28
 */

public class MvvmTravelNoteListPaginationAdapter extends BasePaginationListAdapter {

    @Override
    public BaseListViewHolder getViewHolder(ViewGroup parent, int viewType) {
        BaseListViewHolder viewHolder = new TravelNoteViewHolder(parent.getContext(), LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mvvm_item_travel_note, parent, false));
        return viewHolder;
    }

    public class TravelNoteViewHolder extends BaseListViewHolder<MvvmTravelNoteItemEntity> {
        private Context mContext;
        private MvvmTravelNoteItemBinding mBinding;

        public TravelNoteViewHolder(Context context, View itemView) {
            super(itemView);
            mContext = context;
            mBinding = DataBindingUtil.bind(itemView);
        }

        @Override
        public void updateData(final MvvmTravelNoteItemEntity content, BaseListAdapterItemProperty propertyEntity, int position) {
            mBinding.setTravelNote(content);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, MvvmTravelNoteDetailActivity.class);
                    intent.putExtra("id", content.getId());
                    mContext.startActivity(intent);
                }
            });
            // 数据改变时立即刷新数据，解决DataBinding导致的刷新闪烁问题
            mBinding.executePendingBindings();
        }
    }
}
