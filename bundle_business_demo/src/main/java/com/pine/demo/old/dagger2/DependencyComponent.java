package com.pine.demo.old.dagger2;

import dagger.Component;

/**
 * Created by tanghongfeng on 2017/8/10.
 */

@Component(dependencies = {ParentComponent.class}, modules = {DependencyModule.class})
public interface DependencyComponent {
    void inject(DependencyInjectee dependencyInjectee);
}
