package com.pine.demo.old.dagger2;

import dagger.Component;

/**
 * Created by tanghongfeng on 2017/8/10.
 */

@Component(modules = {ParentModule.class})
public interface ParentComponent {
    // for dependency begin
    ClassOne getClassOne();
    // for dependency end

    // for subcomponent begin
    SubComponent createSubComponent(SubModule subModule);
    // for subcomponent end
}
