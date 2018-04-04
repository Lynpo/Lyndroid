package com.lynpo.dagger2.module;

import com.lynpo.dagger2.model.Engine;
import com.lynpo.dagger2.anno.Diesel;
import com.lynpo.dagger2.anno.Gasoline;
import com.lynpo.dagger2.model.DieselEngine;
import com.lynpo.dagger2.model.GasolineEngine;

import dagger.Module;
import dagger.Provides;

/**
 * Create by fujw on 2018/4/1.
 * *
 * EngineModule2
 */
@Module
public class EngineModule2 {

    @Provides
    @Gasoline
    public Engine provideGasoline() {
        return new GasolineEngine();
    }

    @Provides
    @Diesel
    public Engine provideDieselEngine() {
        return new DieselEngine();
    }
}
