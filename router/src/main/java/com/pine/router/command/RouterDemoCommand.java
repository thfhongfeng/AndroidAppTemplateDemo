package com.pine.router.command;

import com.pine.config.ConfigKey;
import com.pine.router.annotation.ARouterRemoteAction;

/**
 * Created by tanghongfeng on 2019/1/25
 */

@ARouterRemoteAction(Key = ConfigKey.BUNDLE_BUSINESS_DEMO_KEY, UiRemoteAction = "/demo/uiService",
        DataRemoteAction = "/demo/dataService", OpRemoteAction = "/demo/opService")
public interface RouterDemoCommand {
    // Ui command begin
    String goDemoHomeActivity = "goDemoHomeActivity";
    // Ui command end

    // Data command begin
    // Data command end

    // Op command begin
    // Op command end
}
