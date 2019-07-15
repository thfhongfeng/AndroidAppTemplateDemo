package com.pine.mvc.remote;

import android.content.Context;
import android.os.Bundle;

import com.pine.router.IRouterCallback;
import com.pine.router.impl.RouterManager;

public class MvcClientManager {
    public static void callCommand(Context context, String bundleKey,
                                   String command, Bundle args, IRouterCallback callback) {
        RouterManager.getInstance(bundleKey).callUiCommand(context,
                command, args, callback);
    }
}
