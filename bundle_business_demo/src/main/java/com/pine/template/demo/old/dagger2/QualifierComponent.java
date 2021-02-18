package com.pine.template.demo.old.dagger2;

import dagger.Component;

/**
 * Created by tanghongfeng on 2017/8/10.
 */

@Component(modules = QualifierModule.class)
public interface QualifierComponent {
    void inject(QualifierInjectee qualifierInjectee);
}
