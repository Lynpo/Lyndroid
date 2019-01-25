package com.lynpo.daggersample.student;

import dagger.Module;
import dagger.Provides;

/**
 * SampleModule
 * *
 * Create by fujw on 2019/1/21.
 */
@Module
public class ClassModule {

    @Provides
    static String provideName() {
        return ClassActivity.class.getSimpleName();
    }
}
