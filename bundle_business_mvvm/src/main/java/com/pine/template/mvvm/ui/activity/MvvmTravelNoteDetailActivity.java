package com.pine.template.mvvm.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.pine.template.base.architecture.mvvm.ui.activity.BaseMvvmActionBarActivity;
import com.pine.template.base.recycle_view.adapter.BaseListAdapter;
import com.pine.template.mvvm.R;
import com.pine.template.mvvm.adapter.MvvmTravelNoteDetailComplexAdapter;
import com.pine.template.mvvm.bean.MvvmTravelNoteCommentEntity;
import com.pine.template.mvvm.bean.MvvmTravelNoteDetailEntity;
import com.pine.template.mvvm.databinding.MvvmTravelNoteDetailActivityBinding;
import com.pine.template.mvvm.vm.MvvmTravelNoteDetailVm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tanghongfeng on 2018/10/9
 */

public class MvvmTravelNoteDetailActivity extends
        BaseMvvmActionBarActivity<MvvmTravelNoteDetailActivityBinding, MvvmTravelNoteDetailVm>
        implements SwipeRefreshLayout.OnRefreshListener {
    private MvvmTravelNoteDetailComplexAdapter mTravelNoteDetailAdapter;

    @Override
    public void observeInitLiveData(Bundle savedInstanceState) {
        mViewModel.getTravelNoteDetailDate().observe(this, new Observer<MvvmTravelNoteDetailEntity>() {
            @Override
            public void onChanged(@Nullable MvvmTravelNoteDetailEntity mvvmTravelNoteDetailEntity) {
                List<MvvmTravelNoteDetailEntity> list = new ArrayList<>();
                list.add(mvvmTravelNoteDetailEntity);
                mTravelNoteDetailAdapter.setFirstPartData(list);
            }
        });
        mViewModel.getTravelNoteCommentListDate().observe(this, new Observer<List<MvvmTravelNoteCommentEntity>>() {
            @Override
            public void onChanged(@Nullable List<MvvmTravelNoteCommentEntity> mvvmTravelNoteCommentEntities) {
                if (mViewModel.getTravelNoteCommentListDate().getCustomData()) {
                    mTravelNoteDetailAdapter.setSecondPartData(mvvmTravelNoteCommentEntities);
                } else {
                    mTravelNoteDetailAdapter.addSecondPartData(mvvmTravelNoteCommentEntities);
                }
            }
        });
    }

    @Override
    protected int getActivityLayoutResId() {
        return R.layout.mvvm_activity_travel_note_detail;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initView();
    }

    private void initView() {
        mBinding.swipeRefreshLayout.setOnRefreshListener(this);
        mBinding.swipeRefreshLayout.setColorSchemeResources(
                R.color.red,
                R.color.yellow,
                R.color.green
        );
        mBinding.swipeRefreshLayout.setDistanceToTriggerSync(250);
        if (mBinding.swipeRefreshLayout != null) {
            mBinding.swipeRefreshLayout.setRefreshing(true);
        }
        mBinding.swipeRefreshLayout.setEnabled(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mBinding.recycleView.setLayoutManager(linearLayoutManager);
        mBinding.recycleView.setHasFixedSize(true);
        mTravelNoteDetailAdapter = new MvvmTravelNoteDetailComplexAdapter();
        mTravelNoteDetailAdapter.enableInitLoading(true);
        mTravelNoteDetailAdapter.setOnScrollListener(mBinding.recycleView,
                new BaseListAdapter.IOnScrollListener() {
                    @Override
                    public void onLoadMore() {
                        onLoadingMore();
                    }
                });
        mBinding.recycleView.setAdapter(mTravelNoteDetailAdapter);

        mBinding.swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mBinding.swipeRefreshLayout.setRefreshing(true);
                onRefresh();
            }
        });
    }

    @Override
    public void observeSyncLiveData(int liveDataObjTag) {

    }

    @Override
    protected void setupActionBar(View actionbar, ImageView goBackIv, TextView titleTv) {
        titleTv.setText(R.string.mvvm_travel_note_detail_title);
    }

    @Override
    public void onRefresh() {
        mViewModel.loadTravelNoteDetailData(mTravelNoteDetailAdapter.getPageSize());
    }

    public void onLoadingMore() {
        mViewModel.loadTravelNoteCommentData(false, mTravelNoteDetailAdapter.getNextPageNo(),
                mTravelNoteDetailAdapter.getPageSize());
    }

    @Override
    public void setLoadingUiVisibility(boolean processing) {
        if (mBinding.swipeRefreshLayout == null) {
            return;
        }
        mBinding.swipeRefreshLayout.setRefreshing(processing);
    }
}
