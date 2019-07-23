package com.pine.demo.remote.server.arouter;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pine.demo.remote.server.DemoUiRemoteService;
import com.pine.router.impl.arouter.ARouterBundleRemote;

/**
 * Created by tanghongfeng on 2019/2/21
 */

@Route(path = "/demo/uiService")
public class DemoARouterUiRemote extends ARouterBundleRemote<DemoUiRemoteService> {

}
