package com.pine.template.demo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pine.template.base.recycle_view.BaseListViewHolder;
import com.pine.template.base.recycle_view.adapter.BaseNoPaginationListAdapter;
import com.pine.template.base.recycle_view.bean.BaseListAdapterItemProperty;
import com.pine.template.demo.DemoApplication;
import com.pine.template.demo.R;
import com.pine.template.demo.bean.DemoItemEntity;

/**
 * Created by tanghongfeng on 2019/1/16
 */

public class DemoAdapter extends BaseNoPaginationListAdapter {

    @Override
    public BaseListViewHolder getViewHolder(ViewGroup parent, int viewType) {
        BaseListViewHolder viewHolder = new DemoViewHolder(parent.getContext(), LayoutInflater.from(parent.getContext())
                .inflate(R.layout.demo_item, parent, false));
        return viewHolder;
    }

    public class DemoViewHolder extends BaseListViewHolder<DemoItemEntity> {
        private Context mContext;
        private TextView name_tv;

        public DemoViewHolder(Context context, View itemView) {
            super(itemView);
            mContext = context;
            name_tv = itemView.findViewById(R.id.name_tv);
        }

        @Override
        public void updateData(final DemoItemEntity content,
                               BaseListAdapterItemProperty propertyEntity, int position) {
            name_tv.setText(content.getName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DemoApplication.mCurResumedActivity.startActivity(new Intent(
                            DemoApplication.mCurResumedActivity, content.getClazz()));
                }
            });
        }
    }
}
