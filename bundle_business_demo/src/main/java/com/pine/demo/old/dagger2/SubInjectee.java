package com.pine.demo.old.dagger2;

import javax.inject.Inject;

/**
 * Created by tanghongfeng on 2017/8/10.
 */

public class SubInjectee {
    private final static String TAG = "SubInjectee";

    @Inject
    ClassOne mClassOne;
    @Inject
    ClassTwo mClassTwo;
    @Inject
    ClassThree mClassThree;

    public SubInjectee(SubComponent subcomponent) {
        subcomponent.inject(this);
    }

    public String getInjectTrace() {
        return "SubInjectee:\n"
                + " - mClassOne: " + mClassOne.toString()
                + "\n - mClassTwo: " + mClassTwo.toString()
                + "\n - mClassThree: " + mClassThree.toString() + "\n";
    }
}
