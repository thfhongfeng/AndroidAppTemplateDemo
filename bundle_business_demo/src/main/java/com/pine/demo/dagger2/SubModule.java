package com.pine.demo.dagger2;

import dagger.Module;
import dagger.Provides;

/**
 * Created by tanghongfeng on 2017/8/10.
 */

@Module
public class SubModule {
    @Provides
    public ClassThree provideClassThree() {
        ClassThree classThree = new ClassThree("SubModule Provide ClassThree; ");
        return classThree;
    }

/*    //Fail: ClassOne is bound multiple times
    @Provides
    public ClassOne provideClassOne(){
        ClassOne classOne =  new ClassOne("SubModule Provide ClassOne; ");
        return  classOne;
    }*/
}
