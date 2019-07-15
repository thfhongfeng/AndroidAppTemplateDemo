package com.pine.demo.customview;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import com.pine.demo.R;
import com.pine.tool.util.ImageUtils;
import com.pine.tool.util.LogUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by tanghongfeng on 2017/8/10.
 */

public class MyImageGallery extends View {
    private final static String TAG = "MyImageGallery";

    private final static int ORIENTATION_X = 1;
    private final static int ORIENTATION_Y = 2;

    private int mTextOrientaion;

    private int mImageWidth;
    private int mImageHeight;
    private int mTextSize;

    private int mTextColor;

    private Context mContext;
    private int mViewWidth;
    private int mViewHeight;

    private int[] mGalleryItemList;
    private ArrayList<Date> mItemCreateTimeList = new ArrayList<Date>();

    public MyImageGallery(Context context) {
        super(context);
        mContext = context;
        init(null, 0);
    }

    public MyImageGallery(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(attrs, 0);
    }

    public MyImageGallery(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        TypedArray typedArray =
                mContext.obtainStyledAttributes(attrs, R.styleable.MyImageGalleryCV);
        if (typedArray != null) {
            mTextOrientaion = typedArray.getInt(
                    R.styleable.MyImageGalleryCV_textOrientation, ORIENTATION_X);
            mImageWidth = typedArray.getDimensionPixelSize(
                    R.styleable.MyImageGalleryCV_imageWidth, 320);
            mImageHeight = typedArray.getDimensionPixelSize(
                    R.styleable.MyImageGalleryCV_imageHeight, 320);
            mTextSize = typedArray.getDimensionPixelSize(
                    R.styleable.MyImageGalleryCV_imageTextSize, 12);
            mTextColor = typedArray.getColor(
                    R.styleable.MyImageGalleryCV_imageTextColor, 0xCCC0FF3E);
        }
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        LogUtils.d(TAG, "onMeasure mGalleryItemList: "
                + mGalleryItemList);
        // 设置宽高，不然自定义控件放在ScrollView里面没有高度不显示（ScrollView的特殊性）
        if (mGalleryItemList != null && mGalleryItemList.length > 0) {
            int paddingLeft = getPaddingLeft();
            int paddingTop = getPaddingTop();
            int paddingRight = getPaddingRight();
            int paddingBottom = getPaddingBottom();
            int contentWidth = mViewWidth - paddingLeft - paddingRight;
            int contentHeight = mViewHeight - paddingTop - paddingBottom;
            int imgCount = mGalleryItemList.length;
            int width = mViewWidth;
            int height = mViewHeight;
            LogUtils.d(TAG, "before onMeasure paddingLeft: " + paddingLeft
                    + ", paddingTop: " + paddingTop + ", paddingRight: " + paddingRight
                    + ", paddingBottom: " + paddingBottom + ", contentWidth: " + contentWidth
                    + ", contentHeight: " + contentHeight + ", imgCount: " + imgCount
                    + ", width: " + width + ", height: " + height);
            switch (mTextOrientaion) {
                case ORIENTATION_X:
                    int imgCountPerColumn = 2 * contentHeight / mImageHeight - 1;
                    int columnCount = (imgCount - 1) / imgCountPerColumn + 1;
                    width = columnCount * (paddingLeft + mImageWidth) + paddingRight;
                    break;
                default:
                    int imgCountPerRow = 2 * contentWidth / mImageWidth - 1;
                    int rowCount = (imgCount - 1) / imgCountPerRow + 1;
                    height = rowCount * (paddingTop + mImageHeight) + paddingBottom;
                    break;
            }
            LogUtils.d(TAG, "after onMeasure  width: " + width
                    + ", height: " + height);
            setMeasuredDimension(width < mViewWidth ? mViewWidth : width,
                    height < mViewHeight ? mViewHeight : height);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        LogUtils.d(TAG, "onDraw mGalleryItemList: "
                + mGalleryItemList);
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;
        if (mGalleryItemList != null && mGalleryItemList.length > 0) {
            Resources res = mContext.getResources();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss");
            int leftPos = paddingLeft;
            int topPos = paddingTop;
            for (int i = 0; i < mGalleryItemList.length; i++) {
                Bitmap bitmap = BitmapFactory.decodeResource(res, mGalleryItemList[i]);
                if (bitmap != null) {
                    bitmap = ImageUtils.scale(bitmap, mImageWidth, mImageHeight);
                    switch (mTextOrientaion) {
                        case ORIENTATION_X:
                            bitmap = ImageUtils.addTextWatermark(bitmap,
                                    simpleDateFormat.format(mItemCreateTimeList.get(i)).toString(),
                                    mTextSize, mTextColor, 36, 36);
                            canvas.drawBitmap(bitmap, leftPos, topPos, null);
                            topPos += bitmap.getHeight() / 2;
                            if (topPos >= getHeight() - bitmap.getHeight() - paddingBottom) {
                                topPos = paddingTop;
                                leftPos += paddingLeft + bitmap.getWidth();
                            }
                            break;
                        default:
                            bitmap = ImageUtils.addTextWatermark(bitmap,
                                    simpleDateFormat.format(mItemCreateTimeList.get(i)).toString(),
                                    mTextSize, mTextColor, 36, 36);
                            canvas.drawBitmap(bitmap, leftPos, topPos, null);
                            leftPos += bitmap.getWidth() / 2;
                            if (leftPos >= getWidth() - bitmap.getWidth() - paddingRight) {
                                leftPos = paddingLeft;
                                topPos += paddingTop + bitmap.getHeight();
                            }
                            break;
                    }
                }
            }
        }
    }

    public void setImageList(int[] itemList, ArrayList<Date> itemCreateTimeList) {
        LogUtils.d(TAG, "setImageList itemList: "
                + itemList);
        if (itemList.length == itemCreateTimeList.size()) {
            mGalleryItemList = itemList;
            mItemCreateTimeList = itemCreateTimeList;
        }
    }

    public void setWidthHeight(int width, int height) {
        mViewWidth = width;
        mViewHeight = height;
    }

    public int getTextOrientaion() {
        return mTextOrientaion;
    }

    public void setTextOrientaion(int textOrientaion) {
        this.mTextOrientaion = textOrientaion;
    }

    public int getTextSize() {
        return mTextSize;
    }

    public void setTextSize(int textSize) {
        this.mTextSize = textSize;
    }

    public int getTextColor() {
        return mTextColor;
    }

    public void setTextColor(int textColor) {
        this.mTextColor = textColor;
    }
}
