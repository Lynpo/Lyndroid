package com.lynpo.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import dagger.android.AndroidInjector;
import dagger.android.HasActivityInjector;


/**
 * Create by fujw on 2018/4/1.
 * *
 * BaseActivity
 */
public class BaseActivity extends AppCompatActivity implements HasActivityInjector {

    protected Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return null;
    }
}
