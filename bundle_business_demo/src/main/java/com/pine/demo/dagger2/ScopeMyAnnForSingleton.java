package com.pine.demo.dagger2;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by tanghongfeng on 2017/8/10.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ScopeMyAnnForSingleton {
}
