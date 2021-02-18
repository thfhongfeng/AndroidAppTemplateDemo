package com.pine.template.demo.old.dagger2;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by tanghongfeng on 2017/8/10.
 */

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface QualifierMyAnnForName {
    String value();
}
