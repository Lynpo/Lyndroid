package com.lynpo.jniinvoke;

import android.content.Context;

/**
 * Create by fujw on 2018/7/3.
 * *
 * JniVisitor
 */
public class JniVisitor {

    static {
        System.loadLibrary("native-lib");
    }

    public static native String stringFromJNI(Context context);
}