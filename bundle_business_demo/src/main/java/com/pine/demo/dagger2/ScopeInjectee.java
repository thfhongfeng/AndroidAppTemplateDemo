package com.pine.demo.dagger2;

import javax.inject.Inject;

/**
 * Created by tanghongfeng on 2017/8/10.
 */

public class ScopeInjectee {
    private final static String TAG = "ScopeInjectee";

    @Inject
    ClassOne mClassOne;
    @Inject
    ClassOne mClassOneAnother;
    @Inject
    ClassTwo mClassTwo;
    @Inject
    ClassTwo mClassTwoAnother;

    public ScopeInjectee(ScopeComponent scopeComponent) {
        scopeComponent.inject(this);
    }

    public String getInjectTrace() {
        return "ScopeInjectee:\n"
                + " - mClassOne==mClassOneAnother: " + (mClassOne == mClassOneAnother)
                + "\n - mClassTwo==mClassTwoAnother: " + (mClassTwo == mClassTwoAnother) + "\n";
    }
}
