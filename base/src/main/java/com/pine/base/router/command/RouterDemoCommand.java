package com.pine.base.router.command;

import com.pine.config.ConfigKey;
import com.pine.router.annotation.ARouterRemoteAction;

/**
 * Created by tanghongfeng on 2019/1/25
 */

@ARouterRemoteAction(Key = ConfigKey.BUNDLE_BUSINESS_DEMO_KEY, RemoteAction = "/demo/service")
public interface RouterDemoCommand {
    String goDemoHomeActivity = "goDemoHomeActivity";
}