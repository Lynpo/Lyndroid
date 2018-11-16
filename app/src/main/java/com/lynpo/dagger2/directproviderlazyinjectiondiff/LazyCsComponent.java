package com.lynpo.dagger2.directproviderlazyinjectiondiff;

import dagger.Component;

/**
 * Create by fujw on 2018/11/7.
 * *
 * LazyComponent
 */
@Component(modules = LazyCounterModule.class)
public interface LazyCsComponent {

    void inject(LazyCounters providerCounter);
}
