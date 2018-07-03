package com.lynpo.util;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;


/**
 * Toast 工具类
 */

public class ToastUtils {

    public static void makeToastLong(Context context, String msg) {
        if (context != null) {
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        }
    }

    public static void makeToast(Context context, String msg) {
        if (context != null) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }
    }

    public static void makeToast(Context context, int resId) {
        if (context != null) {
            Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
        }
    }

    public static void showShortMessage(Context context, String msg) {
        if (context != null) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }
    }

    public static void showShortMessage(Context context, int resId) {
        if (context != null) {
            Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
        }
    }

    public static void showLongMessage(Context context, String msg) {
        if (context != null) {
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        }
    }

    public static void showLongMessage(Context context, int resId) {
        if (context != null) {
            Toast.makeText(context, resId, Toast.LENGTH_LONG).show();
        }
    }

    public static void showCustomMsg(Context context, View view) {
        Toast toast = new Toast(context);
        toast.setView(view);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();

    }
}
