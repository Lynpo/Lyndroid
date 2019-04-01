package com.lynpo.daggersample.base;

import android.os.Bundle;
import androidx.annotation.Nullable;

import dagger.android.AndroidInjection;


/**
 * Create by fujw on 2018/4/1.
 * *
 * BaseDaggerActivity
 */
public class BaseDaggerActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
    }
}
