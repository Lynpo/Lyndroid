package com.lynpo.dagger2.directproviderlazyinjectiondiff;

import javax.inject.Inject;

/**
 * Create by fujw on 2018/11/7.
 * *
 * LazyCounters
 *
 * Lazy != Singleton
 */
final class LazyCounters {

    @Inject LazyCounter counter1;
    @Inject LazyCounter counter2;

    void inject() {
        DaggerLazyCsComponent.create().inject(this);
    }

    void print() {
        counter1.print();
        counter2.print();
    }
}
