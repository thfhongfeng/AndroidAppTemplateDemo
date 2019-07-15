package com.pine.user.remote.server;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.pine.router.IServiceCallback;
import com.pine.router.annotation.RouterAnnotation;
import com.pine.router.command.RouterUserCommand;
import com.pine.user.ui.activity.UserHomeActivity;

/**
 * Created by tanghongfeng on 2018/9/13
 */

public class UserUiRemoteService {

    @RouterAnnotation(CommandName = RouterUserCommand.goUserHomeActivity)
    public void goUserCenterActivity(@NonNull Context context, Bundle args, @NonNull final IServiceCallback callback) {
        Bundle responseBundle = new Bundle();
        Intent intent = new Intent(context, UserHomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        callback.onResponse(responseBundle);
    }
}
