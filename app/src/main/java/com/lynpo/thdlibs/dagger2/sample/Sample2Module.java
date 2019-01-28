package com.lynpo.thdlibs.dagger2.sample;

import dagger.Module;
import dagger.Provides;

/**
 * SampleModule
 * *
 * Create by fujw on 2019/1/21.
 */
@Module
public class Sample2Module {

    @Provides
    static String provideName() {
        return DaggerSample2Activity.class.getSimpleName();
    }
}
