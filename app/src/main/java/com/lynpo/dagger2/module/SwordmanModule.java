package com.lynpo.dagger2.module;

import com.lynpo.dagger2.model.Swordman;

import dagger.Module;
import dagger.Provides;

/**
 * Create by fujw on 2018/4/1.
 * *
 * SwordmanModule
 */
@Module
public class SwordmanModule {

    @Provides
    public Swordman provideSwordman() {
        return new Swordman();
    }
}
