package com.pine.template.demo.old;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pine.template.base.ui.BaseActionBarActivity;
import com.pine.template.demo.R;
import com.pine.template.demo.adapter.DemoAdapter;
import com.pine.template.demo.bean.DemoItemEntity;
import com.pine.template.demo.old.customview.CustomViewActiviy;
import com.pine.template.demo.old.dagger2.Dagger2Activity;
import com.pine.template.demo.old.eventbus.EventBusActivity;
import com.pine.template.demo.old.okhttp.OkHttpActivity;
import com.pine.template.demo.old.ormlite.OrmLiteActivity;
import com.pine.template.demo.old.recyclerview.RecyclerViewActivity;
import com.pine.tool.widget.decor.GridSpacingItemDecoration;

import java.util.ArrayList;

/**
 * Created by tanghongfeng on 2019/1/14
 */

public class DemoOldHomeActivity extends BaseActionBarActivity {
    private RecyclerView demo_rv;
    private DemoAdapter mDemoAdapter;

    @Override
    protected boolean beforeInitOnCreate(@Nullable Bundle savedInstanceState) {
        super.beforeInitOnCreate(savedInstanceState);
        setActionBarTag(ACTION_BAR_CENTER_TITLE_TAG | ACTION_BAR_NO_GO_BACK_TAG);
        return false;
    }

    @Override
    protected void setupActionBar(View actionbar, ImageView goBackIv, TextView titleTv) {
        titleTv.setText(R.string.demo_home_title);
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        clipboard.getPrimaryClip();
    }

    @Override
    protected int getActivityLayoutResId() {
        return R.layout.demo_activity_old_home;
    }

    @Override
    protected void findViewOnCreate(Bundle savedInstanceState) {
        demo_rv = findViewById(R.id.demo_rv);
    }

    @Override
    protected boolean parseIntentData() {
        return false;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        demo_rv.setLayoutManager(layoutManager);
        demo_rv.addItemDecoration(new GridSpacingItemDecoration(2,
                getResources().getDimensionPixelOffset(R.dimen.dp_10), true));
        demo_rv.setHasFixedSize(true);
        mDemoAdapter = new DemoAdapter();
        mDemoAdapter.enableEmptyComplete(true, false);

        ArrayList<DemoItemEntity> list = new ArrayList<>();
        DemoItemEntity entity;

        entity = new DemoItemEntity();
        entity.setName("Dagger2");
        entity.setClazz(Dagger2Activity.class);
        list.add(entity);

        entity = new DemoItemEntity();
        entity.setName("EventBus");
        entity.setClazz(EventBusActivity.class);
        list.add(entity);

        entity = new DemoItemEntity();
        entity.setName("OrmLite");
        entity.setClazz(OrmLiteActivity.class);
        list.add(entity);

        entity = new DemoItemEntity();
        entity.setName("OkHttp");
        entity.setClazz(OkHttpActivity.class);
        list.add(entity);

        entity = new DemoItemEntity();
        entity.setName("CustomView");
        entity.setClazz(CustomViewActiviy.class);
        list.add(entity);

        entity = new DemoItemEntity();
        entity.setName("RecyclerView");
        entity.setClazz(RecyclerViewActivity.class);
        list.add(entity);

        mDemoAdapter.setData(list);
        demo_rv.setAdapter(mDemoAdapter);
    }
}
