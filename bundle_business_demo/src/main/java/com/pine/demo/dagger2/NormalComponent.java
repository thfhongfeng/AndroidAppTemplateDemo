package com.pine.demo.dagger2;

import dagger.Component;

/**
 * Created by tanghongfeng on 2017/8/10.
 */

@Component(modules = {NormalModule.class})
public interface NormalComponent {
    void inject(NormalInjectee normalInjectee);
}
