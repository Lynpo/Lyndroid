package com.lynpo.jni;

/**
 * Create by fujw on 2018/7/3.
 * *
 * JniVisitor
 */
public class JniVisitor {

    static {
        System.loadLibrary("native-lib");
    }

    public static native String stringFromJNI();

    public static native String hashSignFromJNI();
}
