package com.lynpo.thdlibs.dagger2.directproviderlazyinjectiondiff;

import javax.inject.Inject;

import dagger.Lazy;

/**
 * Create by fujw on 2018/11/7.
 * *
 * LazyCounter
 */
final class LazyCounter {

    @Inject
    Lazy<Integer> lazy;

    void inject() {
        DaggerLazyComponent.create().inject(this);
    }

    void print() {
        System.out.println("LazyCounter printing...");
        System.out.println("LazyCounter " + lazy.get());
        System.out.println("LazyCounter " + lazy.get());
        System.out.println("LazyCounter " + lazy.get());
    }
}
