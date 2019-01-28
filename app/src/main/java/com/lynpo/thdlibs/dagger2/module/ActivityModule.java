package com.lynpo.thdlibs.dagger2.module;

import com.lynpo.thdlibs.dagger2.sample.DaggerSample2Activity;
import com.lynpo.thdlibs.dagger2.sample.DaggerSampleActivity;
import com.lynpo.thdlibs.dagger2.sample.Sample2Module;
import com.lynpo.thdlibs.dagger2.sample.SampleModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * ActivityModule
 * *
 * Create by fujw on 2019/1/21.
 */
@Module
public abstract class ActivityModule {

    @ContributesAndroidInjector(modules = SampleModule.class)
    abstract DaggerSampleActivity contributeSampleActivity();

    @ContributesAndroidInjector(modules = Sample2Module.class)
    abstract DaggerSample2Activity contributeSample2Activity();
}
