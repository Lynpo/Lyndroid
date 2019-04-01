package com.lynpo.eternal.base.ui;

import android.os.Bundle;
import androidx.annotation.Nullable;

import dagger.android.AndroidInjection;


/**
 * Create by fujw on 2018/4/1.
 * *
 * BaseDaggerActivity, All activity injection handled here.
 * Activity(s) that without provide in com.lynpo.thdlibs.dagger2.module.ActivityModule
 * would not extends from this class, or there will be a crash.
 */
public class BaseDaggerActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
    }
}
