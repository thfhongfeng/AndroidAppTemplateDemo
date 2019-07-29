package com.pine.demo.wan_android;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.GeolocationPermissions;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.pine.base.ui.BaseNoActionBarActivity;
import com.pine.base.widget.view.CommonWebView;
import com.pine.demo.DemoUrlConstants;
import com.pine.demo.R;
import com.pine.tool.util.WebViewUtils;

import cn.pedant.SafeWebViewBridge.InjectedChromeClient;

public class DemoWanAndroidActivity extends BaseNoActionBarActivity implements View.OnClickListener {
    private CommonWebView web_view;
    private TextView refresh_btn_tv;

    private String mH5Url;

    @Override
    protected int getActivityLayoutResId() {
        return R.layout.demo_activity_wan_android;
    }

    @Override
    protected void findViewOnCreate() {
        refresh_btn_tv = findViewById(R.id.refresh_btn_tv);
        web_view = findViewById(R.id.web_view);
    }

    @Override
    protected boolean parseIntentData() {
        mH5Url = DemoUrlConstants.H5_WanAndroidUrl;
        return false;
    }

    @Override
    protected void init() {
        initEvent();
        initWebView();
    }

    private void initEvent() {
        refresh_btn_tv.setOnClickListener(this);
    }

    private void initWebView() {
        web_view.init(this, mH5Url);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.refresh_btn_tv) {
            loadUrl(mH5Url);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            web_view.goBackAction();
            return true;
        }
        return false;
    }

    public void loadUrl(String url) {
        web_view.loadUrl(url);
    }

    static class JsInterface {

    }
}
