package com.lynpo.thdlibs.dagger2.directproviderlazyinjectiondiff;

import javax.inject.Inject;
import javax.inject.Provider;

/**
 * Create by fujw on 2018/11/7.
 * *
 * ProviderCounter
 */
final class ProviderCounter {

    @Inject Provider<Integer> provider;

    void inject() {
        DaggerProviderComponent.create().inject(this);
    }

    void print() {
        System.out.println("ProviderCounter printing...");
        System.out.println("ProviderCounter " + provider.get());
        System.out.println("ProviderCounter " + provider.get());
        System.out.println("ProviderCounter " + provider.get());
    }
}
