package com.pine.login.remote.server.arouter;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pine.login.remote.server.LoginDataRemoteService;
import com.pine.router.impl.arouter.ARouterBundleRemote;

/**
 * Created by tanghongfeng on 2018/9/12
 */

@Route(path = "/login/dataService")
public class LoginARouterDataRemote extends ARouterBundleRemote<LoginDataRemoteService> {

}
