package com.lynpo;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.lynpo.thdlibs.dagger2.component.ActivityComponent;
import com.lynpo.thdlibs.dagger2.component.DaggerAppComponent;
import com.lynpo.thdlibs.dagger2.component.SwordmanComponent;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;


/**
 * Create by fujw on 2018/4/1.
 * *
 * LynpoApp
 */
public class LynpoApp extends Application implements HasActivityInjector {

    ActivityComponent activityComponent;
    SwordmanComponent swordmanComponent;

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerAppComponent
//                .builder().appModule(new AppModule()).build().inject(this);
                .create().inject(this);
//        activityComponent = DaggerActivityComponent.builder().buld();
//        activityComponent = DaggerActivityComponent.builder().buld().swordmanComponent(DaggerSwordmanComponent.builder().build()).build();
    }

    public static LynpoApp get(Context context) {
        return (LynpoApp) context.getApplicationContext();
    }

    public ActivityComponent getActivityComponent() {
        return activityComponent;
    }

//    @Override
//    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
//        return null;
//    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}
