package com.pine.demo.dagger2;

import dagger.Module;
import dagger.Provides;

/**
 * Created by tanghongfeng on 2017/8/10.
 */

@Module
public class ScopeModule {
    @ScopeMyAnnForSingleton
    @Provides
    public ClassOne provideClassOne() {
        ClassOne classOne = new ClassOne("ScopeModule Provide ClassOne; ");
        return classOne;
    }

    @Provides
    public ClassTwo provideClassTwo() {
        ClassTwo classTwo = new ClassTwo("ScopeModule Provide ClassTwo; ");
        return classTwo;
    }
}
