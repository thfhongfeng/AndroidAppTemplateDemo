package com.pine.demo.old;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.pine.base.ui.BaseActionBarActivity;
import com.pine.demo.R;
import com.pine.demo.adapter.DemoAdapter;
import com.pine.demo.bean.DemoItemEntity;
import com.pine.demo.console.DemoConsoleActivity;
import com.pine.demo.novice_guide.DemoNoviceGuideActivity;
import com.pine.demo.old.customview.CustomViewActiviy;
import com.pine.demo.old.dagger2.Dagger2Activity;
import com.pine.demo.old.eventbus.EventBusActivity;
import com.pine.demo.old.okhttp.OkHttpActivity;
import com.pine.demo.old.ormlite.OrmLiteActivity;
import com.pine.demo.old.recyclerview.RecyclerViewActivity;
import com.pine.demo.wan_android.DemoWanAndroidActivity;
import com.pine.tool.widget.decor.GridSpacingItemDecoration;

import java.util.ArrayList;

/**
 * Created by tanghongfeng on 2019/1/14
 */

public class DemoOldHomeActivity extends BaseActionBarActivity {
    private RecyclerView demo_rv;
    private DemoAdapter mDemoAdapter;

    @Override
    protected void beforeInitOnCreate(@Nullable Bundle savedInstanceState) {
        super.beforeInitOnCreate(savedInstanceState);
        setActionBarTag(ACTION_BAR_CENTER_TITLE_TAG | ACTION_BAR_NO_GO_BACK_TAG);
    }

    @Override
    protected void setupActionBar(ImageView goBackIv, TextView titleTv) {
        titleTv.setText(R.string.demo_home_title);
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        clipboard.getPrimaryClip();
    }

    @Override
    protected int getActivityLayoutResId() {
        return R.layout.demo_activity_old_home;
    }

    @Override
    protected void findViewOnCreate() {
        demo_rv = findViewById(R.id.demo_rv);
    }

    @Override
    protected boolean parseIntentData() {
        return false;
    }

    @Override
    protected void init() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        demo_rv.setLayoutManager(layoutManager);
        demo_rv.addItemDecoration(new GridSpacingItemDecoration(2,
                getResources().getDimensionPixelOffset(R.dimen.dp_10), true));
        demo_rv.setHasFixedSize(true);
        mDemoAdapter = new DemoAdapter(
                DemoAdapter.DEMO_VIEW_HOLDER);
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
