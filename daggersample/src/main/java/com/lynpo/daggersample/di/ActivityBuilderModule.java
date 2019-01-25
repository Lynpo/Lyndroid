package com.lynpo.daggersample.di;

import com.lynpo.daggersample.student.ClassActivity;
import com.lynpo.daggersample.student.ClassModule;
import com.lynpo.daggersample.student.StudentActivity;
import com.lynpo.daggersample.student.StudentModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * AllActivityModule
 * *
 * Create by fujw on 2019/1/23.
 */
@Module
public abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = StudentModule.class)
    abstract StudentActivity contributesSampleActivity();

    @ContributesAndroidInjector(modules = ClassModule.class)
    abstract ClassActivity contributesSample2Activity();
}
