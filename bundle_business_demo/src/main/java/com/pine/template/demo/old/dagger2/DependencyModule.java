package com.pine.template.demo.old.dagger2;

import dagger.Module;
import dagger.Provides;

/**
 * Created by tanghongfeng on 2017/8/10.
 */

@Module
public class DependencyModule {
    @Provides
    public ClassThree provideClassThree() {
        ClassThree classThree = new ClassThree("DependencyModule Provide ClassThree; ");
        return classThree;
    }

/*    //Fail: ClassOne is bound multiple times
    @Provides
    public ClassOne provideClassOne(){
        ClassOne classOne =  new ClassOne("DependencyModule Provide ClassOne; ");
        return  classOne;
    }*/

    @Provides
    public ClassTwo provideClassTwo() {
        ClassTwo classTwo = new ClassTwo("DependencyModule Provide ClassTwo; ");
        return classTwo;
    }
}
