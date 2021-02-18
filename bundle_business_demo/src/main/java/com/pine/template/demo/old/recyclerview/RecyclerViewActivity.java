package com.pine.template.demo.old.recyclerview;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.pine.template.demo.R;

/**
 * Created by tanghongfeng on 2017/8/10.
 */

public class RecyclerViewActivity extends AppCompatActivity {
    private final static String TAG = "RecyclerViewActivity";

    private final static int INIT_STATE = 1;
    private final static int IS_INIT = 1;

    private ScrollView mConsoleScroll;

    private RecyclerView mRecyclerView;
    private RecyclerView mHorRecyclerView;
    private RecyclerView mGridRecyclerView;
    private RecyclerView mStaggeredRecyclerView;
    private RecyclerView mDecorRecyclerView;
    private RecyclerView mAdvanceDecorRecyclerView;
    private RecyclerView mCurrentRecyclerView;

    private EditText mPositionEditText;
    private int mAddIndex = 0;

    private String[] mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_activity_recycler_view);

        mConsoleScroll = (ScrollView) findViewById(R.id.console_sv);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mHorRecyclerView = (RecyclerView) findViewById(R.id.horizontal_recycler_view);
        mGridRecyclerView = (RecyclerView) findViewById(R.id.grid_recycler_view);
        mStaggeredRecyclerView = (RecyclerView) findViewById(R.id.staggered_grid_recycler_view);
        mDecorRecyclerView = (RecyclerView) findViewById(R.id.decoration_recycler_view);
        mAdvanceDecorRecyclerView = (RecyclerView) findViewById(R.id.advance_decor_recycler_view);
        mPositionEditText = (EditText) findViewById(R.id.item_position_et);

        mTitle = new String[20];
        for (int i = 0; i < mTitle.length; i++) {
            mTitle[i] = "Item_" + i;
        }

        Button rvBtn = (Button) findViewById(R.id.rv_btn);
        rvBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideAllRecyclerView();
                // 定义一个 final 类型的 int 变量和硬编码一个值的方式都是行不通的（如INIT_STATE），
                // 原因就在于 key 不唯一，会报：
                // java.lang.IllegalArgumentException: The key must be an application-specific resource id.
                // 通过资源ID来绑定就没有问题了
//                if (mRecyclerView.getTag(IS_INIT) == null) {
                if (mRecyclerView.getTag(R.id.demo_rv_tag_is_init) == null) {
                    // 设置固定大小
                    mRecyclerView.setHasFixedSize(true);
                    // 创建线性布局管理器
                    LinearLayoutManager llm = new LinearLayoutManager(RecyclerViewActivity.this);
                    // 设置垂直方向
                    llm.setOrientation(RecyclerView.VERTICAL);
                    // 给RecyclerView设置布局管理器
                    mRecyclerView.setLayoutManager(llm);
                    // 创建和设置适配器
                    SimpleRecyclerAdapter simpleRecyclerAdapter =
                            new SimpleRecyclerAdapter(RecyclerViewActivity.this);
                    mRecyclerView.setAdapter(simpleRecyclerAdapter);
                }
                mRecyclerView.setVisibility(View.VISIBLE);
                mCurrentRecyclerView = mRecyclerView;
                // java.lang.IllegalArgumentException: The key must be an application-specific resource id.
//                mRecyclerView.setTag(INIT_STATE, IS_INIT);
                mRecyclerView.setTag(R.id.demo_rv_tag_is_init, IS_INIT);
            }
        });

        Button horizontalBtn = (Button) findViewById(R.id.horizontal_rv_btn);
        horizontalBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                hideAllRecyclerView();
                if (mHorRecyclerView.getTag(R.id.demo_rv_tag_is_init) == null) {
                    // 设置固定大小
                    mHorRecyclerView.setHasFixedSize(true);
                    // 创建线性布局管理器
                    LinearLayoutManager llm = new LinearLayoutManager(RecyclerViewActivity.this);
                    // 设置水平方向
                    llm.setOrientation(RecyclerView.HORIZONTAL);
                    // 给RecyclerView设置布局管理器
                    mHorRecyclerView.setLayoutManager(llm);
                    // 创建和设置适配器
                    AdvanceRecyclerAdapter advanceRecyclerAdapter =
                            new AdvanceRecyclerAdapter(RecyclerViewActivity.this,
                                    R.layout.demo_item_recycler_layout_wrap, mTitle, mItemClickListener);
                    mHorRecyclerView.setAdapter(advanceRecyclerAdapter);
                }
                mHorRecyclerView.setVisibility(View.VISIBLE);
                mCurrentRecyclerView = mHorRecyclerView;
                mHorRecyclerView.setTag(R.id.demo_rv_tag_is_init, IS_INIT);
            }
        });

        Button gridBtn = (Button) findViewById(R.id.grid_rv_btn);
        gridBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                hideAllRecyclerView();
                if (mGridRecyclerView.getTag(R.id.demo_rv_tag_is_init) == null) {
                    // 设置固定大小
                    mGridRecyclerView.setHasFixedSize(true);
                    // 创建线性布局管理器
                    GridLayoutManager glm = new GridLayoutManager(RecyclerViewActivity.this, 4);
                    // 给RecyclerView设置布局管理器
                    mGridRecyclerView.setLayoutManager(glm);
                    // 创建和设置适配器
                    AdvanceRecyclerAdapter advanceRecyclerAdapter =
                            new AdvanceRecyclerAdapter(RecyclerViewActivity.this,
                                    R.layout.demo_item_recycler_layout_match, mTitle, mItemClickListener);
                    mGridRecyclerView.setAdapter(advanceRecyclerAdapter);
                }
                mGridRecyclerView.setVisibility(View.VISIBLE);
                mCurrentRecyclerView = mGridRecyclerView;
                mGridRecyclerView.setTag(R.id.demo_rv_tag_is_init, IS_INIT);
            }
        });

        Button staggeredBtn = (Button) findViewById(R.id.staggered_grid_rv_btn);
        staggeredBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                hideAllRecyclerView();
                if (mStaggeredRecyclerView.getTag(R.id.demo_rv_tag_is_init) == null) {
                    // 设置固定大小
                    mStaggeredRecyclerView.setHasFixedSize(true);
                    // 创建线性布局管理器
                    StaggeredGridLayoutManager sglm =
                            new StaggeredGridLayoutManager(4, RecyclerView.VERTICAL);
                    // 给RecyclerView设置布局管理器
                    mStaggeredRecyclerView.setLayoutManager(sglm);
                    // 创建和设置适配器
                    AdvanceRecyclerAdapter advanceRecyclerAdapter =
                            new AdvanceRecyclerAdapter(RecyclerViewActivity.this,
                                    R.layout.demo_item_recycler_layout_match, mTitle, mItemClickListener);
                    mStaggeredRecyclerView.setAdapter(advanceRecyclerAdapter);
                }
                mStaggeredRecyclerView.setVisibility(View.VISIBLE);
                mCurrentRecyclerView = mStaggeredRecyclerView;
                mStaggeredRecyclerView.setTag(R.id.demo_rv_tag_is_init, IS_INIT);
            }
        });

        Button decorBtn = (Button) findViewById(R.id.decoration_rv_btn);
        decorBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                hideAllRecyclerView();
                if (mDecorRecyclerView.getTag(R.id.demo_rv_tag_is_init) == null) {
                    // 设置固定大小
                    mDecorRecyclerView.setHasFixedSize(true);
                    // 创建线性布局管理器
                    LinearLayoutManager llm = new LinearLayoutManager(RecyclerViewActivity.this);
                    // 设置垂直方向
                    llm.setOrientation(RecyclerView.VERTICAL);
                    // 给RecyclerView设置布局管理器
                    mDecorRecyclerView.setLayoutManager(llm);
                    // 给RecyclerView添加装饰（比如divider）
                    mDecorRecyclerView.addItemDecoration(
                            new SimpleDecoration(RecyclerViewActivity.this));
                    // 创建和设置适配器
                    AdvanceRecyclerAdapter advanceRecyclerAdapter =
                            new AdvanceRecyclerAdapter(RecyclerViewActivity.this,
                                    R.layout.demo_item_recycler_layout_match, mTitle, mItemClickListener);
                    mDecorRecyclerView.setAdapter(advanceRecyclerAdapter);
                }
                mDecorRecyclerView.setVisibility(View.VISIBLE);
                mCurrentRecyclerView = mDecorRecyclerView;
                mDecorRecyclerView.setTag(R.id.demo_rv_tag_is_init, IS_INIT);
            }
        });

        Button advanceDecorBtn = (Button) findViewById(R.id.advance_decor_rv_btn);
        advanceDecorBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                hideAllRecyclerView();
                if (mAdvanceDecorRecyclerView.getTag(R.id.demo_rv_tag_is_init) == null) {
                    // 设置固定大小
                    mAdvanceDecorRecyclerView.setHasFixedSize(true);
                    // 创建线性布局管理器
                    LinearLayoutManager llm = new LinearLayoutManager(RecyclerViewActivity.this);
                    // 设置垂直方向
                    llm.setOrientation(RecyclerView.VERTICAL);
                    // 给RecyclerView设置布局管理器
                    mAdvanceDecorRecyclerView.setLayoutManager(llm);
                    // 给RecyclerView添加装饰（比如divider）
                    mAdvanceDecorRecyclerView.addItemDecoration(
                            new AdvanceDecoration(RecyclerViewActivity.this,
                                    R.drawable.demo_rv_divider, 3, AdvanceDecoration.VERTICAL));
                    // 添加默认的动画效果
                    mAdvanceDecorRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    // 创建和设置适配器
                    AdvanceRecyclerAdapter advanceRecyclerAdapter =
                            new AdvanceRecyclerAdapter(RecyclerViewActivity.this,
                                    R.layout.demo_item_recycler_layout_match, mTitle, mItemClickListener);
                    mAdvanceDecorRecyclerView.setAdapter(advanceRecyclerAdapter);
                }
                mAdvanceDecorRecyclerView.setVisibility(View.VISIBLE);
                mCurrentRecyclerView = mAdvanceDecorRecyclerView;
                mAdvanceDecorRecyclerView.setTag(R.id.demo_rv_tag_is_init, IS_INIT);
            }
        });

        Button addItemBtn = (Button) findViewById(R.id.add_item_btn);
        addItemBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mCurrentRecyclerView != null && mCurrentRecyclerView != mRecyclerView) {
                    int position = 0;
                    try {
                        position = Integer.parseInt(mPositionEditText.getText().toString());
                    } catch (Exception ex) {

                    }
                    AdvanceRecyclerAdapter advanceRecyclerAdapter =
                            (AdvanceRecyclerAdapter) mCurrentRecyclerView.getAdapter();
                    int count = advanceRecyclerAdapter.getItemCount();
                    position = position < 0 || position > count - 1 ? 0 : position;
                    advanceRecyclerAdapter.addItem("added item_" + mAddIndex++, position);
                }
            }
        });

        Button deleteItemBtn = (Button) findViewById(R.id.delete_item_btn);
        deleteItemBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mCurrentRecyclerView != null && mCurrentRecyclerView != mRecyclerView) {
                    AdvanceRecyclerAdapter advanceRecyclerAdapter =
                            (AdvanceRecyclerAdapter) mCurrentRecyclerView.getAdapter();
                    int count = advanceRecyclerAdapter.getItemCount();
                    if (count > 0) {
                        int position = 0;
                        try {
                            position = Integer.parseInt(mPositionEditText.getText().toString());
                        } catch (Exception ex) {

                        }
                        position = position < 0 || position > count - 1 ? 0 : position;
                        advanceRecyclerAdapter.removeItem(position);
                    }
                }
            }
        });
    }

    private void scrollDown() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (mConsoleScroll instanceof ScrollView) {
                    ((ScrollView) mConsoleScroll).fullScroll(ScrollView.FOCUS_DOWN);
                }
            }
        });
    }

    private void scrollUp() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (mConsoleScroll instanceof ScrollView) {
                    ((ScrollView) mConsoleScroll).fullScroll(ScrollView.FOCUS_UP);
                }
            }
        });
    }

    AdvanceRecyclerAdapter.OnRecyclerItemClickListener mItemClickListener =
            new AdvanceRecyclerAdapter.OnRecyclerItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Toast.makeText(RecyclerViewActivity.this, "Item " + position + " clicked!",
                            Toast.LENGTH_SHORT).show();
                }
            };

    private void hideAllRecyclerView() {
        mRecyclerView.setVisibility(View.GONE);
        mHorRecyclerView.setVisibility(View.GONE);
        mGridRecyclerView.setVisibility(View.GONE);
        mStaggeredRecyclerView.setVisibility(View.GONE);
        mDecorRecyclerView.setVisibility(View.GONE);
        mAdvanceDecorRecyclerView.setVisibility(View.GONE);
    }
}
