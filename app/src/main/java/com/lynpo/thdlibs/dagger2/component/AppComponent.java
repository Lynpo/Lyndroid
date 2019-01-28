package com.lynpo.thdlibs.dagger2.component;

import com.lynpo.LynpoApp;
import com.lynpo.thdlibs.dagger2.module.ActivityModule;
import com.lynpo.thdlibs.dagger2.module.AppModule;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * AppComponent
 * *
 * Create by fujw on 2019/1/21.
 */
@Component(modules = {AppModule.class,
        AndroidSupportInjectionModule.class,
        AndroidInjectionModule.class,
        ActivityModule.class
        })
public interface AppComponent {

    void inject(LynpoApp application);
}
