package com.pine.base.component.editor.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.pine.base.component.editor.bean.TextImageEntity;
import com.pine.base.component.uploader.ui.UploadFileLinearLayout;
import com.pine.tool.ui.Activity;

import java.util.ArrayList;
import java.util.List;

public class ArticleEditorView extends LinearLayout {
    public ArticleEditorView(Context context) {
        super(context);
        setOrientation(VERTICAL);
    }

    public ArticleEditorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
    }

    public ArticleEditorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
    }

    public List<TextImageEntity> getSectionList() {
        List<TextImageEntity> list = new ArrayList<>();
        for (int i = 0; i < getChildCount(); i++) {
            list.add(((TextImageEditorView) getChildAt(i)).getData());
        }
        return list;
    }

    public void setSectionCount(Activity activity, int sectionCount, List<String> sectionTitleList,
                                UploadFileLinearLayout.OneByOneUploadAdapter adapter) {
        int childCount = getChildCount();
        if (sectionCount > childCount) {
            for (int i = childCount; i < sectionCount; i++) {
                String title = sectionTitleList == null ? "" :
                        (sectionTitleList.size() > i ? sectionTitleList.get(i) : "");
                addSectionView(activity, title, i + 1, adapter);
            }
        } else if (sectionCount < childCount) {
            removeViews(sectionCount, childCount - sectionCount);
        }
        ((TextImageEditorView) getChildAt(0))
                .setTitle(sectionTitleList != null && sectionTitleList.size() > 0 ? sectionTitleList.get(0) : "");
        invalidate();
    }

    private void addSectionView(Activity activity, String title, int section,
                                UploadFileLinearLayout.OneByOneUploadAdapter adapter) {
        TextImageEditorView view = new TextImageEditorView(getContext());
        view.init(activity, section, title, adapter, 100 + section);
        addView(view);
    }
}
