package com.pine.demo.dagger2;

import javax.inject.Inject;

/**
 * Created by tanghongfeng on 2017/8/10.
 */

public class DependencyInjectee {
    private final static String TAG = "DependencyInjectee";

    @Inject
    ClassOne mClassOne;
    @Inject
    ClassTwo mClassTwo;
    @Inject
    ClassThree mClassThree;

    public DependencyInjectee(DependencyComponent dependencyComponent) {
        dependencyComponent.inject(this);
    }

    public String getInjectTrace() {
        return "SubInjectee:\n"
                + " - mClassOne: " + mClassOne.toString()
                + "\n - mClassTwo: " + mClassTwo.toString()
                + "\n - mClassThree: " + mClassThree.toString() + "\n";
    }
}
