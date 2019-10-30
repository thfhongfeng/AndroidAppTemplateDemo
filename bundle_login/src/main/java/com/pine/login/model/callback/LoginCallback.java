package com.pine.login.model.callback;

import android.content.Intent;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pine.config.BuildConfig;
import com.pine.config.switcher.ConfigSwitcherServer;
import com.pine.login.LoginApplication;
import com.pine.login.LoginConstants;
import com.pine.login.bean.AccountBean;
import com.pine.login.manager.LoginManager;
import com.pine.login.model.ILoginResponse;
import com.pine.login.ui.activity.LoginActivity;
import com.pine.tool.request.RequestManager;
import com.pine.tool.request.Response;
import com.pine.tool.request.callback.JsonCallback;
import com.pine.tool.util.AppUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.pine.tool.request.IRequestManager.SESSION_ID;

/**
 * Created by tanghongfeng on 2018/9/10.
 */

public class LoginCallback extends JsonCallback {
    public static final int LOGIN_CODE = 4001;
    public static final int LOGOUT_CODE = 4002;
    public static final int AUTO_LOGIN_CODE = 4003;
    public static final int RE_LOGIN_CODE = 4004;
    private ILoginResponse mCallback;

    public LoginCallback() {

    }

    public LoginCallback(ILoginResponse callback) {
        mCallback = callback;
    }

    @Override
    public void onResponse(int what, Response response) {
        HashMap<String, String> cookies = response.getCookies();
        Iterator<Map.Entry<String, String>> iterator = cookies.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            if (SESSION_ID.equalsIgnoreCase(entry.getKey())) {
                RequestManager.setSessionId(entry.getValue());
                break;
            }
        }
        super.onResponse(what, response);
    }

    @Override
    public void onResponse(final int what, final JSONObject jsonObject, Response response) {
        if (LOGOUT_CODE == what) {
            LoginManager.clearLoginInfo();
            LoginApplication.setLogin(false);
            return;
        } else {
            if (jsonObject == null || !jsonObject.optBoolean(LoginConstants.SUCCESS, false)) {
                loginFail(what, jsonObject == null ?
                        "" : jsonObject.optString(LoginConstants.MESSAGE), "登陆失败！");
                if (AUTO_LOGIN_CODE != what) {
                    goLoginActivity();
                }
                return;
            }
            AccountBean responseAccount = new Gson().fromJson(jsonObject.optString(LoginConstants.DATA), AccountBean.class);
            // Test code begin
            if (!"local".equalsIgnoreCase(BuildConfig.APP_THIRD_DATA_SOURCE_PROVIDER)) {
                responseAccount = new AccountBean();
                responseAccount.setId(jsonObject.optString("userId"));
                responseAccount.setAccount(jsonObject.optString("mobile"));
                responseAccount.setName(jsonObject.optString("userRealName"));
                responseAccount.setMobile(jsonObject.optString("mobile"));
                responseAccount.setState(1);
                responseAccount.setAccountType(100);
            }
            // Test code end
            final AccountBean accountBean = responseAccount;
            LoginManager.saveLoginInfo(accountBean);
            LoginApplication.setLogin(true);
            ConfigSwitcherServer.getInstance().setupConfigSwitcher(true, new ConfigSwitcherServer.IConfigSwitcherCallback() {
                @Override
                public void onSetupComplete() {
                    loginSuccess(what, accountBean, "登陆成功！");
                }

                @Override
                public boolean onSetupFail() {
                    LoginManager.logout();
                    loginFail(what, jsonObject == null ?
                            "" : jsonObject.optString(LoginConstants.MESSAGE), "登陆失败，服务器异常，请重试！");
                    return true;
                }
            });
        }
    }

    private void loginSuccess(int what, AccountBean accountBean, String defaultMsg) {
        if (mCallback != null) {
            if (mCallback.onLoginResponse(true, "") && LOGIN_CODE == what) {
                Toast.makeText(AppUtils.getApplication(), defaultMsg, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void loginFail(int what, String serverMsg, String defaultMsg) {
        LoginApplication.setLogin(false);
        if (mCallback != null) {
            if (!mCallback.onLoginResponse(false, serverMsg) && LOGIN_CODE == what) {
                Toast.makeText(AppUtils.getApplication(), defaultMsg, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onFail(int what, Exception e, Response response) {
        LoginApplication.setLogin(false);
        boolean consumed = false;
        if (mCallback != null) {
            consumed = mCallback.onLoginResponse(false, e.getMessage());
        }
        if (AUTO_LOGIN_CODE != what) {
            goLoginActivity();
        }
        return consumed;
    }

    @Override
    public void onCancel(int what) {
        if (mCallback != null) {
            mCallback.onCancel();
        }
    }

    private void goLoginActivity() {
        Intent intent = new Intent(AppUtils.getApplication(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppUtils.getApplication().startActivity(intent);
    }
}
