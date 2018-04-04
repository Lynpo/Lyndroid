package com.lynpo.dagger2.component;

import com.lynpo.dagger2.module.EngineModule;
import com.lynpo.dagger2.module.EngineModule2;
import com.lynpo.detail.DetailActivity;
import com.lynpo.home.MainActivity;
import com.lynpo.dagger2.anno.ApplicationScope;
import com.lynpo.dagger2.module.GsonModule;

import dagger.Component;

/**
 * Create by fujw on 2018/4/1.
 * *
 * ActivityComponent
 */
//@Singleton
@ApplicationScope
@Component(modules = {EngineModule.class, EngineModule2.class, GsonModule.class},
        /*component dependency*/
        dependencies = SwordmanComponent.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);
    void inject(DetailActivity detailActivity);
}
