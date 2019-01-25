package com.lynpo.daggersample.di;

import android.content.Context;

import com.lynpo.daggersample.DaggerApp;

import dagger.Module;
import dagger.Provides;

/**
 * AppModule
 * *
 * Create by fujw on 2019/1/25.
 */
@Module
public class AppModule {

    @Provides
    Context provideContext(DaggerApp app) {
        return app.getApplicationContext();
    }
}
