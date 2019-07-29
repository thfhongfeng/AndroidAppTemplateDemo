package com.pine.demo.old.dagger2;

import javax.inject.Inject;

/**
 * Created by tanghongfeng on 2017/8/10.
 */

public class NormalInjectee {
    private final static String TAG = "NormalInjectee";

    @Inject
    ClassOne mClassOne;
    @Inject
    ConstructorClass mConstructorClass;

    public NormalInjectee(NormalComponent normalComponent) {
        normalComponent.inject(this);
    }

    public String getInjectTrace() {
        return "NormalInjectee:\n"
                + " - mClassOne: " + mClassOne.toString()
                + "\n - mConstructorClass: " + mConstructorClass.toString() + "\n";
    }
}
