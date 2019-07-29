package com.pine.demo.old.dagger2;

import dagger.Module;
import dagger.Provides;

/**
 * Created by tanghongfeng on 2017/8/10.
 */

@Module
public class QualifierModule {
    @QualifierMyAnnForName("Tom")
    @Provides
    public QualifierPerson provideQualifierPersonTom() {
        QualifierPerson qualifierPerson = new QualifierPerson("Tom");
        return qualifierPerson;
    }

    @Provides
    @QualifierMyAnnForName("Jim")
    public QualifierPerson provideQualifierPersonJim() {
        QualifierPerson qualifierPerson = new QualifierPerson("Jim");
        return qualifierPerson;
    }

    @Provides
    public QualifierPerson provideQualifierPersonDefault() {
        QualifierPerson qualifierPerson = new QualifierPerson("Default");
        return qualifierPerson;
    }

    /*//Fail: QualifierPerson is bound multiple times
    @Provides
    public QualifierPerson provideQualifierPersonAnother(){
        QualifierPerson qualifierPerson = new QualifierPerson("Another");
        return  qualifierPerson;
    }*/
}
