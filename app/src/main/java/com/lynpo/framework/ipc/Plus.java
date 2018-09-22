package com.lynpo.framework.ipc;

import android.os.IBinder;
import android.os.IInterface;
import android.util.Log;


/**
 * Create by fujw on 2018/9/22.
 * *
 * Plus
 */
public class Plus implements IInterface {

    @Override
    public IBinder asBinder() {
        return null;
    }

    public int plus(int a, int b) {
        Log.d("debug_info", "Plus.plus:" + "::a:" + a + ", b:" + b);
        return a + b;
    }
}
