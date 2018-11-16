package com.lynpo.thdlibs.dagger2.directproviderlazyinjectiondiff;

import dagger.Component;

/**
 * Create by fujw on 2018/11/7.
 * *
 * LazyComponent
 */
@Component(modules = CounterModule.class)
public interface LazyComponent {

    void inject(LazyCounter providerCounter);
}
