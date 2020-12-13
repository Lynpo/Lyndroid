package com.lynpo.daggersample;

import android.app.Activity;
import android.app.Application;

import com.lynpo.daggersample.di.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;


/**
 * DaggerApp
 * *
 * Create by fujw on 2019/1/23.
 */
public class DaggerApp extends Application implements HasAndroidInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerAppComponent.create().inject(this);
    }

    @Override
    public AndroidInjector<Object> androidInjector() {
        return null;
    }
}
