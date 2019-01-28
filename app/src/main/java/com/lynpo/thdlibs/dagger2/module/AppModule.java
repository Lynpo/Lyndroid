package com.lynpo.thdlibs.dagger2.module;

import android.content.Context;

import com.lynpo.LynpoApp;

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
    Context provideContext(LynpoApp app) {
        return app.getApplicationContext();
    }
}
