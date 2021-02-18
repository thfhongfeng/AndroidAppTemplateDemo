package com.pine.template.demo.old.dagger2;

import javax.inject.Inject;

/**
 * Created by tanghongfeng on 2017/8/10.
 */

public class QualifierInjectee {
    private final static String TAG = "QualifierInjectee";

    @Inject
    @QualifierMyAnnForName("Tom")
    QualifierPerson mTom;

    @QualifierMyAnnForName("Jim")
    @Inject
    QualifierPerson mJim;

    @Inject
    QualifierPerson mDefault;

    public QualifierInjectee(QualifierComponent qualifierComponent) {
        qualifierComponent.inject(this);
    }

    public String getInjectTrace() {
        return "QualifierInjectee:\n"
                + " - mTom: " + mTom.toString()
                + "\n - mJim: " + mJim.toString()
                + "\n - mDefault: " + mDefault.toString() + "\n";
    }
}
