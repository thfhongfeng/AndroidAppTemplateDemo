package com.pine.main.remote;

import android.content.Context;
import android.os.Bundle;

import com.pine.tool.router.IRouterCallback;
import com.pine.tool.router.RouterManager;

public class MainRouterClient {
    public static void callCommand(Context context, String bundleKey,
                                   String command, Bundle args, IRouterCallback callback) {
        RouterManager.getInstance(bundleKey).callUiCommand(context,
                command, args, callback);
    }
}
