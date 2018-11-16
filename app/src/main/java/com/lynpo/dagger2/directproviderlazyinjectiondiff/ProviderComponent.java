package com.lynpo.dagger2.directproviderlazyinjectiondiff;

import dagger.Component;

/**
 * Create by fujw on 2018/11/7.
 * *
 * ProviderComponent
 */
@Component(modules = CounterModule.class)
public interface ProviderComponent {

    void inject(ProviderCounter providerCounter);
}
