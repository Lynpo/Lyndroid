package com.lynpo.dagger2.directproviderlazyinjectiondiff;

import dagger.Module;
import dagger.Provides;

/**
 * Create by fujw on 2018/11/7.
 * *
 * CounterModule
 */
@Module
final class CounterModule {

    int next = 100;

    @Provides
    Integer provideInteger() {
        System.out.println("computing...");
        return next++;
    }
}
