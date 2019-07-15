package com.pine.demo.dagger2;

import dagger.Component;

/**
 * Created by tanghongfeng on 2017/8/10.
 */

@ScopeMyAnnForSingleton
@Component(modules = ScopeModule.class)
public interface ScopeComponent {
    void inject(ScopeInjectee scopeInjectee);
}
