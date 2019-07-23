package com.pine.router.impl.arouter.manager;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.pine.config.switcher.ConfigSwitcherServer;
import com.pine.router.IRouterCallback;
import com.pine.router.R;
import com.pine.router.annotation.ARouterRemoteAction;
import com.pine.router.impl.IRouterManager;
import com.pine.router.impl.arouter.ARouterBundleRemote;
import com.pine.tool.util.AndroidClassUtils;
import com.pine.tool.util.AppUtils;
import com.pine.tool.util.LogUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.pine.router.RouterConstants.TYPE_DATA_COMMAND;
import static com.pine.router.RouterConstants.TYPE_OP_COMMAND;
import static com.pine.router.RouterConstants.TYPE_UI_COMMAND;

/**
 * Created by tanghongfeng on 2019/2/21
 */

public class ARouterManager implements IRouterManager {
    private final String TAG = LogUtils.makeLogTag(this.getClass());

    private static volatile List<String> mClassNameList = new ArrayList<>();
    private static volatile HashMap<String, ARouterManager> mInstanceMap = new HashMap<>();

    private String mBundleKey = "";
    private String mUiRemoteAction = "";
    private String mDataRemoteAction = "";
    private String mOpRemoteAction = "";

    static {
        try {
            mClassNameList = AndroidClassUtils.getFileNameByPackageName(AppUtils.getApplicationContext(),
                    "com.pine.router.command");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private ARouterManager(@NonNull String bundleKey) {
        mBundleKey = bundleKey;
        for (int i = 0; i < mClassNameList.size(); i++) {
            try {
                Class<?> clazz = Class.forName(mClassNameList.get(i));
                ARouterRemoteAction remoteAction = clazz.getAnnotation(ARouterRemoteAction.class);
                if (remoteAction != null) {
                    if (mBundleKey.equals(remoteAction.Key())) {
                        mUiRemoteAction = remoteAction.UiRemoteAction();
                        mDataRemoteAction = remoteAction.DataRemoteAction();
                        mOpRemoteAction = remoteAction.OpRemoteAction();
                        break;
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static ARouterManager getInstance(@NonNull String bundleKey) {
        if (mInstanceMap.get(bundleKey) == null) {
            synchronized (ARouterManager.class) {
                if (mInstanceMap.get(bundleKey) == null) {
                    mInstanceMap.put(bundleKey, new ARouterManager(bundleKey));
                }
            }
        }
        return mInstanceMap.get(bundleKey);
    }

    @Override
    public void callUiCommand(final Context context, String commandName,
                              Bundle args, final IRouterCallback callback) {
        if (!checkBundleValidity(TYPE_UI_COMMAND, context, callback)) {
            return;
        }
        ARouterBundleRemote routerService = ((ARouterBundleRemote) ARouter.getInstance().build(mUiRemoteAction)
                .navigation(context, new NavigationCallback() {
                    @Override
                    public void onFound(Postcard postcard) {
                        LogUtils.d(TAG, "callUiCommand path:'" + postcard.getPath() + "'onFound");
                    }

                    @Override
                    public void onLost(Postcard postcard) {
                        LogUtils.d(TAG, "callUiCommand path:'" + postcard.getPath() + "'onLost");
                        if (callback != null && !callback.onFail(IRouterManager.FAIL_CODE_LOST, "onLost")) {
                            onCommandFail(TYPE_UI_COMMAND, context, IRouterManager.FAIL_CODE_LOST, "onLost");
                        }
                    }

                    @Override
                    public void onArrival(Postcard postcard) {
                        LogUtils.d(TAG, "callUiCommand path:'" + postcard.getPath() + "'onArrival");
                    }

                    @Override
                    public void onInterrupt(Postcard postcard) {
                        LogUtils.d(TAG, "callUiCommand path:'" + postcard.getPath() + "'onInterrupt");
                        if (callback != null && !callback.onFail(IRouterManager.FAIL_CODE_INTERRUPT, "onInterrupt")) {
                            onCommandFail(TYPE_UI_COMMAND, context, IRouterManager.FAIL_CODE_INTERRUPT, "onInterrupt");
                        }
                    }
                }));
        if (routerService != null) {
            routerService.call(context, commandName, args, callback);
        }
    }

    @Override
    public void callDataCommand(final Context context, String commandName, Bundle args, final IRouterCallback callback) {
        if (!checkBundleValidity(TYPE_DATA_COMMAND, context, callback)) {
            return;
        }
        ARouterBundleRemote routerService = ((ARouterBundleRemote) ARouter.getInstance().build(mDataRemoteAction)
                .navigation(context, new NavigationCallback() {
                    @Override
                    public void onFound(Postcard postcard) {
                        LogUtils.d(TAG, "callDataCommand path:'" + postcard.getPath() + "'onFound");
                    }

                    @Override
                    public void onLost(Postcard postcard) {
                        LogUtils.d(TAG, "callDataCommand path:'" + postcard.getPath() + "'onLost");
                        if (callback != null && !callback.onFail(IRouterManager.FAIL_CODE_LOST, "onLost")) {
                            onCommandFail(TYPE_DATA_COMMAND, context, IRouterManager.FAIL_CODE_LOST, "onLost");
                        }
                    }

                    @Override
                    public void onArrival(Postcard postcard) {
                        LogUtils.d(TAG, "callDataCommand path:'" + postcard.getPath() + "'onArrival");
                    }

                    @Override
                    public void onInterrupt(Postcard postcard) {
                        LogUtils.d(TAG, "callDataCommand path:'" + postcard.getPath() + "'onInterrupt");
                        if (callback != null && !callback.onFail(IRouterManager.FAIL_CODE_INTERRUPT, "onInterrupt")) {
                            onCommandFail(TYPE_DATA_COMMAND, context, IRouterManager.FAIL_CODE_INTERRUPT, "onInterrupt");
                        }
                    }
                }));
        if (routerService != null) {
            routerService.call(context, commandName, args, callback);
        }
    }

    @Override
    public void callOpCommand(final Context context, String commandName,
                              Bundle args, final IRouterCallback callback) {
        if (!checkBundleValidity(TYPE_OP_COMMAND, context, callback)) {
            return;
        }
        ARouterBundleRemote routerService = ((ARouterBundleRemote) ARouter.getInstance().build(mOpRemoteAction)
                .navigation(context, new NavigationCallback() {
                    @Override
                    public void onFound(Postcard postcard) {
                        LogUtils.d(TAG, "callOpCommand path:'" + postcard.getPath() + "'onFound");
                    }

                    @Override
                    public void onLost(Postcard postcard) {
                        LogUtils.d(TAG, "callOpCommand path:'" + postcard.getPath() + "'onLost");
                        if (callback != null && !callback.onFail(IRouterManager.FAIL_CODE_LOST, "onLost")) {
                            onCommandFail(TYPE_OP_COMMAND, context, IRouterManager.FAIL_CODE_LOST, "onLost");
                        }
                    }

                    @Override
                    public void onArrival(Postcard postcard) {
                        LogUtils.d(TAG, "callOpCommand path:'" + postcard.getPath() + "'onArrival");
                    }

                    @Override
                    public void onInterrupt(Postcard postcard) {
                        LogUtils.d(TAG, "callOpCommand path:'" + postcard.getPath() + "'onInterrupt");
                        if (callback != null && !callback.onFail(IRouterManager.FAIL_CODE_INTERRUPT, "onInterrupt")) {
                            onCommandFail(TYPE_OP_COMMAND, context, IRouterManager.FAIL_CODE_INTERRUPT, "onInterrupt");
                        }
                    }
                }));
        if (routerService != null) {
            routerService.call(context, commandName, args, callback);
        }
    }

    @Override
    public <R> R callUiCommandDirect(final Context context, String commandName, Bundle args) {
        if (!checkBundleValidity(TYPE_UI_COMMAND, context, null)) {
            return null;
        }
        ARouterBundleRemote routerService = ((ARouterBundleRemote) ARouter.getInstance().build(mUiRemoteAction)
                .navigation(context, null));
        if (routerService != null) {
            return (R) routerService.callDirect(context, commandName, args);
        }
        return null;
    }

    @Override
    public <R> R callDataCommandDirect(final Context context, String commandName, Bundle args) {
        if (!checkBundleValidity(TYPE_DATA_COMMAND, context, null)) {
            return null;
        }
        ARouterBundleRemote routerService = ((ARouterBundleRemote) ARouter.getInstance().build(mDataRemoteAction)
                .navigation(context, null));
        if (routerService != null) {
            return (R) routerService.callDirect(context, commandName, args);
        }
        return null;
    }

    @Override
    public <R> R callOpCommandDirect(final Context context, String commandName, Bundle args) {
        if (!checkBundleValidity(TYPE_OP_COMMAND, context, null)) {
            return null;
        }
        ARouterBundleRemote routerService = ((ARouterBundleRemote) ARouter.getInstance().build(mOpRemoteAction)
                .navigation(context, null));
        if (routerService != null) {
            return (R) routerService.callDirect(context, commandName, args);
        }
        return null;
    }

    private boolean checkBundleValidity(final String commandType, final Context context,
                                        final IRouterCallback callback) {
        boolean typeValidity = false;
        switch (commandType) {
            case TYPE_UI_COMMAND:
                typeValidity = !TextUtils.isEmpty(mUiRemoteAction);
                break;
            case TYPE_DATA_COMMAND:
                typeValidity = !TextUtils.isEmpty(mDataRemoteAction);
                break;
            case TYPE_OP_COMMAND:
                typeValidity = !TextUtils.isEmpty(mOpRemoteAction);
                break;
        }
        if (!typeValidity) {
            LogUtils.releaseLog(TAG, "ui remote action is null");
            if (callback != null && !callback.onFail(IRouterManager.FAIL_CODE_INVALID,
                    context.getString(R.string.router_remote_action_empty))) {
                onCommandFail(commandType, context, IRouterManager.FAIL_CODE_INVALID,
                        context.getString(R.string.router_remote_action_empty));
            }
            return false;
        }
        if (!ConfigSwitcherServer.getInstance().isEnable(mBundleKey)) {
            LogUtils.releaseLog(TAG, mBundleKey + " is not opened");
            if (callback != null && !callback.onFail(IRouterManager.FAIL_CODE_INVALID,
                    context.getString(R.string.router_bundle_not_open))) {
                onCommandFail(commandType, context, IRouterManager.FAIL_CODE_INVALID,
                        context.getString(R.string.router_bundle_not_open));
            }
            return false;
        }
        return true;
    }

    private void onCommandFail(String commandType, Context context, int failCode, String message) {
        if (!TextUtils.isEmpty(message)) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }
}
