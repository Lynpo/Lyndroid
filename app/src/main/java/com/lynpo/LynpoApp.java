package com.lynpo;

import android.content.Context;

import com.lynpo.dagger2.component.ActivityComponent;
import com.lynpo.dagger2.component.SwordmanComponent;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;


/**
 * Create by fujw on 2018/4/1.
 * *
 * LynpoApp
 */
public class LynpoApp extends DaggerApplication {

    ActivityComponent activityComponent;
    SwordmanComponent swordmanComponent;

    @Override
    public void onCreate() {
        super.onCreate();
//        activityComponent = DaggerActivityComponent.builder().buld();
//        activityComponent = DaggerActivityComponent.builder().buld().swordmanComponent(DaggerSwordmanComponent.builder().build()).build();
    }

    public static LynpoApp get(Context context) {
        return (LynpoApp) context.getApplicationContext();
    }

    public ActivityComponent getActivityComponent() {
        return activityComponent;
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return null;
    }
}
