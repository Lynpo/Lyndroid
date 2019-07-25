package com.lynpo.androidarchitecture;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.content.ContentProvider;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.LocationManager;
import android.telecom.TelecomManager;
import android.util.Log;
import android.view.ViewManager;
import android.view.WindowManager;

import com.lynpo.eternal.LynConstants;

/**
 * Create by fujw on 2018/4/1.
 * *
 * AndroidArchitecture
 */
public class AndroidArchitecture {

    public void theseManager() {
        ActivityManager manager;
//        manager.getAppTasks();

        LocationManager locationManager;
//        locationManager.getAllProviders();

        PackageManager packageManager;
//        packageManager.getActivityInfo(null, 1);

        NotificationManager notificationManager;

        Resources resources;    // Resource manager ?

        TelecomManager telecomManager;

        WindowManager windowManager;

        ContentProvider contentProvider;

        ViewManager viewManager;    // View system
    }

    public void theseNativeLayer() {
        Log.d(LynConstants.LOG_TAG, "OpenGL ES");   // 3D 绘图
        Log.d(LynConstants.LOG_TAG, "Libc");
        Log.d(LynConstants.LOG_TAG, "Media Framework");
        Log.d(LynConstants.LOG_TAG, "SQLite");

        // ...
    }
}
