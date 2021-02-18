package com.pine.template.demo.old.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.pine.template.demo.R;
import com.pine.tool.util.LogUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by tanghongfeng on 2017/8/10.
 */

public class CustomConsoleContainer extends LinearLayout {
    private final static String TAG = "CustomConsoleContainer";

    private final static int SCROLL_X = 1;
    private final static int SCROLL_Y = 2;

    public final static int TEXT_MODE = 1;
    public final static int IMAGE_MODE = 2;

    public final static int ITEM_FIRE = 0;
    public final static int ITEM_COMPUTER = 1;
    public final static int ITEM_TREE = 2;
    public final static int ITEM_STAR = 3;
    public final static int ITEM_PHONE = 4;
    public final static int ITEM_PAD = 5;

    private final int[] mAllImageItems = {
            R.drawable.demo_cv_fire, R.drawable.demo_cv_computer, R.drawable.demo_cv_tree, R.drawable.demo_cv_star,
            R.drawable.demo_cv_phone, R.drawable.demo_cv_pad
    };
    private final String[] mAllTextItems = {
            "Fire", "Computer", "Tree", "Star", "Phone", "Pad"
    };

    private Context mContext;

    private FrameLayout mConsoleScroll;
    private LinearLayout mConsoleContainerLL;
    private ProgressBar mProgressbar;

    private TextView mTextConsole;
    private MyImageGallery mGalleryConsole;

    private int mConsoleMode = TEXT_MODE;
    private int mScrollOrientaion = SCROLL_X;

    private ArrayList<Integer> mGalleryItemList = new ArrayList<Integer>();
    private ArrayList<Date> mItemCreateTimeList = new ArrayList<Date>();

    public CustomConsoleContainer(Context context) {
        super(context);
        mContext = context;
        init(null, 0);
    }

    public CustomConsoleContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(attrs, 0);
    }

    public CustomConsoleContainer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        TypedArray typedArray =
                mContext.obtainStyledAttributes(attrs, R.styleable.DemoCustomConsoleContainerCV);

        if (typedArray != null) {
            mConsoleMode = typedArray
                    .getInt(R.styleable.DemoCustomConsoleContainerCV_demo_dccccv_consoleMode, TEXT_MODE);
            mScrollOrientaion = typedArray.getInt(
                    R.styleable.DemoCustomConsoleContainerCV_demo_dccccv_scrollOrientation, SCROLL_X);
        }
        LayoutInflater.from(mContext).inflate(
                R.layout.demo_custom_console_container, this, true);
        mConsoleContainerLL = (LinearLayout) findViewById(R.id.console_container_ll);
        changeConsoleMode(mConsoleMode);
        typedArray.recycle();
    }

    private void refreshConsole() {
        LogUtils.d(TAG, "refreshConsole mGalleryItemList.size: "
                + mGalleryItemList.size() + ", mConsoleMode: " + mConsoleMode);
        mConsoleScroll.removeAllViews();
        if (mGalleryItemList == null || mGalleryItemList.size() < 1) {
            return;
        }
        switch (mConsoleMode) {
            case IMAGE_MODE:
                mGalleryConsole = new MyImageGallery(mContext);
                ViewGroup.LayoutParams imgLayoutParams = new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                // 设置宽高，不然自定义控件放在ScrollView里面没有高度不显示（ScrollView的特殊性）
                mGalleryConsole.setTextOrientaion(mScrollOrientaion);
                mGalleryConsole.setWidthHeight(mConsoleContainerLL.getWidth(),
                        mConsoleContainerLL.getHeight());
                mGalleryConsole.setImageList(convertIndexToDrawableRes(mGalleryItemList)
                        , mItemCreateTimeList);
                mConsoleScroll.addView(mGalleryConsole, imgLayoutParams);
                break;
            default:
                mTextConsole = new TextView(mContext);
                ViewGroup.LayoutParams tvLayoutParams = new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                mTextConsole.setLayoutParams(tvLayoutParams);
                mTextConsole.setBackgroundResource(android.R.color.white);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String text = "";
                for (int i = 0; i < mGalleryItemList.size(); i++) {
                    text += "Add " + mAllTextItems[mGalleryItemList.get(i)] + " at "
                            + simpleDateFormat.format(mItemCreateTimeList.get(i)).toString() + "\n";
                }
                mTextConsole.setText(text);
                mConsoleScroll.addView(mTextConsole);
                break;
        }
        // addView完之后，不等于马上就会显示，而是在队列中等待处理，虽然很快，但是如果立即调用fullScroll，
        // view可能还没有显示出来，会失败，所以应该通过handler在主线程排队更新
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (mConsoleScroll instanceof ScrollView) {
                    ((ScrollView) mConsoleScroll).fullScroll(ScrollView.FOCUS_DOWN);
                }
                if (mConsoleScroll instanceof HorizontalScrollView) {
                    ((HorizontalScrollView) mConsoleScroll).fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                }
            }
        });

    }

    private int[] convertIndexToDrawableRes(ArrayList<Integer> indexList) {
        int[] resList = new int[indexList.size()];
        for (int i = 0; i < indexList.size(); i++) {
            resList[i] = mAllImageItems[indexList.get(i)];
        }
        return resList;
    }

    private void setupScrollOrientation() {
        mConsoleContainerLL.removeAllViews();
        LayoutInflater.from(mContext).inflate(
                mScrollOrientaion == SCROLL_X && mConsoleMode == IMAGE_MODE ?
                        R.layout.demo_custom_horizontal_console_container :
                        R.layout.demo_custom_vertical_console_container, mConsoleContainerLL, true);
        mConsoleScroll = (FrameLayout) mConsoleContainerLL.findViewById(R.id.console_sv);
    }

    public void changeConsoleMode(int mode) {
        mConsoleMode = mode;
        setupScrollOrientation();
        refreshConsole();
    }

    public void printlnAppended(int item) {
        mGalleryItemList.add(item);
        mItemCreateTimeList.add(new Date());
        refreshConsole();
    }

    public void printlnAll(ArrayList<Integer> itemList, ArrayList<Date> itemCreateTimeList) {
        mGalleryItemList = itemList;
        mItemCreateTimeList = itemCreateTimeList;
        refreshConsole();
    }

    public void clearAll() {
        mGalleryItemList.clear();
        mItemCreateTimeList.clear();
        refreshConsole();
    }

    public void toggleTextOrientationForImgMode() {
        if (mConsoleMode == IMAGE_MODE && mGalleryConsole != null) {
            mScrollOrientaion = mScrollOrientaion == SCROLL_X
                    ? SCROLL_Y : SCROLL_X;
            setupScrollOrientation();
            refreshConsole();
        }
    }
}
