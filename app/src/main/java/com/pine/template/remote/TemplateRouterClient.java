package com.pine.template.remote;

import android.content.Context;
import android.os.Bundle;

import com.pine.config.ConfigKey;
import com.pine.router.IRouterCallback;
import com.pine.base.router.command.RouterLoginCommand;
import com.pine.router.impl.RouterManager;

public class TemplateRouterClient {
    public static void callCommand(Context context, String bundleKey,
                                   String command, Bundle args, IRouterCallback callback) {
        RouterManager.getInstance(bundleKey).callUiCommand(context,
                command, args, callback);
    }

    public static void goLoginActivity(Context context, Bundle args, IRouterCallback callback) {
        RouterManager.getInstance(ConfigKey.BUNDLE_LOGIN_KEY).callUiCommand(context,
                RouterLoginCommand.goLoginActivity, args, callback);
    }
}
