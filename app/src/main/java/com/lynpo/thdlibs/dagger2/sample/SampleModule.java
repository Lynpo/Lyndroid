package com.lynpo.thdlibs.dagger2.sample;

import android.content.Context;
import android.content.SharedPreferences;

import dagger.Module;
import dagger.Provides;

/**
 * SampleModule
 * *
 * Create by fujw on 2019/1/21.
 */
@Module
public class SampleModule {

    @Provides
    static String provideName() {
        return DaggerSampleActivity.class.getSimpleName();
    }

    @Provides
    static Student provideStudent(String name) {
        return new Student(name);
    }

    @Provides
    static SharedPreferences provideSp(DaggerSampleActivity activity) {
        return activity.getSharedPreferences("def", Context.MODE_PRIVATE);
    }
}
