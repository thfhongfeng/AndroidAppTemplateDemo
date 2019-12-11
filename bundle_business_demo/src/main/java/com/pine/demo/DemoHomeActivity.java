package com.pine.demo;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pine.base.ui.BaseActionBarActivity;
import com.pine.demo.adapter.DemoAdapter;
import com.pine.demo.bean.DemoItemEntity;
import com.pine.demo.novice_guide.DemoNoviceGuideActivity;
import com.pine.demo.old.DemoOldHomeActivity;
import com.pine.demo.test.DemoTestActivity;
import com.pine.demo.wan_android.DemoWanAndroidActivity;
import com.pine.tool.widget.decor.GridSpacingItemDecoration;

import java.util.ArrayList;

/**
 * Created by tanghongfeng on 2019/1/14
 */

public class DemoHomeActivity extends BaseActionBarActivity {
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
        return R.layout.demo_activity_home;
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
        mDemoAdapter = new DemoAdapter(
                DemoAdapter.DEMO_VIEW_HOLDER);
        mDemoAdapter.enableEmptyComplete(true, false);

        ArrayList<DemoItemEntity> list = new ArrayList<>();
        DemoItemEntity entity;

        entity = new DemoItemEntity();
        entity.setName("Wan Android网站");
        entity.setClazz(DemoWanAndroidActivity.class);
        list.add(entity);

        entity = new DemoItemEntity();
        entity.setName("控制台");
        entity.setClazz(DemoTestActivity.class);
        list.add(entity);

        entity = new DemoItemEntity();
        entity.setName("新手引导");
        entity.setClazz(DemoNoviceGuideActivity.class);
        list.add(entity);

        mDemoAdapter.setData(list);
        demo_rv.setAdapter(mDemoAdapter);

        findViewById(R.id.old_demo_btn_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DemoHomeActivity.this, DemoOldHomeActivity.class));
            }
        });
    }
}
