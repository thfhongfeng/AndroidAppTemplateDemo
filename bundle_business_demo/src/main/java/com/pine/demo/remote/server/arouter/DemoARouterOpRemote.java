package com.pine.demo.remote.server.arouter;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pine.demo.remote.server.DemoOpRemoteService;
import com.pine.router.impl.arouter.ARouterBundleRemote;

/**
 * Created by tanghongfeng on 2019/2/21
 */

@Route(path = "/demo/opService")
public class DemoARouterOpRemote extends ARouterBundleRemote<DemoOpRemoteService> {

}