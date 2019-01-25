package com.lynpo.daggersample.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import dagger.android.AndroidInjection;


/**
 * Create by fujw on 2018/4/1.
 * *
 * BaseActivity
 */
public class BaseActivity extends AppCompatActivity {

    protected Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        mContext = this;
    }

    public static void start(Context context, Class<?> targetName) {
        Intent starter = new Intent(context, targetName);
        if (starter.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(starter);
        }
    }
}
