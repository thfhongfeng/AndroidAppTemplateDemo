package com.pine.template.base.router.command;

import com.pine.template.config.ConfigKey;
import com.pine.tool.router.annotation.ARouterRemoteAction;

/**
 * Created by tanghongfeng on 2019/1/25
 */

@ARouterRemoteAction(Key = ConfigKey.BUNDLE_BUSINESS_DEMO_KEY, RemoteAction = "/demo/service")
public interface RouterDemoCommand {
    String goDemoHomeActivity = "goDemoHomeActivity";
}
