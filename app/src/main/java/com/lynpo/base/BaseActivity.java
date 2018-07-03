package com.lynpo.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.lynpo.annos.ActivityConfig;

import dagger.android.AndroidInjector;
import dagger.android.HasActivityInjector;


/**
 * Create by fujw on 2018/4/1.
 * *
 * BaseActivity
 */
public class BaseActivity extends AppCompatActivity implements HasActivityInjector {

    protected Context mContext;

    protected ActivityConfig mActivityConfig;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
    }

    public static void start(Context context, String targetName) {
        try {
            Intent starter = new Intent(context, Class.forName(targetName));
            if (starter.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(starter);
            }
        } catch (ClassNotFoundException ignored) {
        }
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return null;
    }
}
