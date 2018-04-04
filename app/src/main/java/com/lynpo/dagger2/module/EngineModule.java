package com.lynpo.dagger2.module;

import com.lynpo.dagger2.model.Engine;
import com.lynpo.dagger2.model.DieselEngine;
import com.lynpo.dagger2.model.GasolineEngine;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Create by fujw on 2018/4/1.
 * *
 * EngineModule
 */
@Module
public class EngineModule {

    @Provides
    @Named("GasolineEngine")
    public Engine provideGasoline() {
        return new GasolineEngine();
    }

    @Provides
    @Named("DieselEngine")
    public Engine provideDieselEngine() {
        return new DieselEngine();
    }

}
