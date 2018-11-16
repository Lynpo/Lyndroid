package com.lynpo.thdlibs.dagger2.directproviderlazyinjectiondiff;

import dagger.Module;
import dagger.Provides;

/**
 * Create by fujw on 2018/11/7.
 * *
 * CounterModule
 */
@Module
final class LazyCounterModule {

    LazyCounter lazyCounter = new LazyCounter();

    @Provides
    LazyCounter provideLazyCounter() {
        System.out.println("provideLazyCounter...");
        return lazyCounter;
    }
}
