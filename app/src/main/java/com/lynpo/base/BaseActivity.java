package com.lynpo.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.lynpo.LynConstants;
import com.lynpo.annos.ActivityConfig;


/**
 * Create by fujw on 2018/4/1.
 * *
 * BaseActivity
 */
public class BaseActivity extends AppCompatActivity {

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
            } else {
                Log.e(LynConstants.LOG_TAG, "cannot resolve target, class " + targetName + " is null");
            }
        } catch (ClassNotFoundException e) {
            Log.e(LynConstants.LOG_TAG, e.getMessage());
        }
    }
}
