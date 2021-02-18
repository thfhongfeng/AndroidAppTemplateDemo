package com.pine.template.demo.old.dagger2;

import dagger.Subcomponent;

/**
 * Created by tanghongfeng on 2017/8/10.
 */

@Subcomponent(modules = {SubModule.class})
public interface SubComponent {
    void inject(SubInjectee subInjectee);
}
