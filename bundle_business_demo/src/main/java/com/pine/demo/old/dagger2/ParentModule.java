package com.pine.demo.old.dagger2;

import dagger.Module;
import dagger.Provides;

/**
 * Created by tanghongfeng on 2017/8/10.
 */

@Module
public class ParentModule {
    @Provides
    public ClassOne provideClassOne() {
        ClassOne classOne = new ClassOne("ParentModule Provide ClassOne; ");
        return classOne;
    }

    @Provides
    public ClassTwo provideClassTwo() {
        ClassTwo classTwo = new ClassTwo("ParentModule Provide ClassTwo; ");
        return classTwo;
    }
}
