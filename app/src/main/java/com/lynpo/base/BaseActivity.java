package com.lynpo.base;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

import dagger.android.AndroidInjector;
import dagger.android.HasActivityInjector;


/**
 * Create by fujw on 2018/4/1.
 * *
 * BaseActivity
 */
public class BaseActivity extends AppCompatActivity implements HasActivityInjector {

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return null;
    }
}
