package com.lynpo.dagger2.module;

import com.google.gson.Gson;
import com.lynpo.dagger2.anno.ApplicationScope;

import dagger.Module;
import dagger.Provides;

/**
 * Create by fujw on 2018/4/1.
 * *
 * GsonModule
 */
@Module
public class GsonModule {

    @Provides
//    @Singleton
    @ApplicationScope
    public Gson provideGson() {
        return new Gson();
    }
}
