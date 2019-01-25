package com.lynpo.daggersample.di;

import com.lynpo.daggersample.DaggerApp;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * AppComponent
 * *
 * Create by fujw on 2019/1/21.
 */
@Component(modules = {AppModule.class,
        AndroidInjectionModule.class,
        AndroidSupportInjectionModule.class,
        ActivityBuilderModule.class})
public interface AppComponent {

    void inject(DaggerApp application);
}
