package com.pine.demo.dagger2;

import dagger.Module;
import dagger.Provides;

/**
 * Created by tanghongfeng on 2017/8/10.
 */

@Module
public class NormalModule {
    @Provides
    public ClassOne provideClassOne() {
        ClassOne classOne = new ClassOne("NormalModule Provide ClassOne; ");
        return classOne;
    }

    @Provides
    public ConstructorClass provideConstructorClass() {
        ConstructorClass constructorClass = new ConstructorClass("NormalModule Provide ConstructorClass; ");
        return constructorClass;
    }
}
