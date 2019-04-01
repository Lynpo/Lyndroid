package com.lynpo.daggersample.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


/**
 * Create by fujw on 2018/4/1.
 * *
 * BaseActivity
 */
public class BaseActivity extends AppCompatActivity {

    protected Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
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
