package com.lynpo.thdlibs.dagger2.directproviderlazyinjectiondiff;

import javax.inject.Inject;

/**
 * Create by fujw on 2018/11/7.
 * *
 * DirectCounter
 */
final class DirectCounter {

    @Inject Integer value;

    void inject() {
        DaggerDirectComponent.create().inject(this);
    }

    void print() {
        System.out.println("DirectCounter printing...");
        System.out.println("DirectCounter " + value);
        System.out.println("DirectCounter " + value);
        System.out.println("DirectCounter " + value);
    }
}
