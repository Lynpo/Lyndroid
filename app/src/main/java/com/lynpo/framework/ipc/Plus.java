package com.lynpo.framework.ipc;

import android.os.IBinder;
import android.os.IInterface;
import android.util.Log;

import com.lynpo.eternal.LynConstants;


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
        Log.d(LynConstants.LOG_TAG, "Plus.plus:" + "::a:" + a + ", b:" + b);
        return a + b;
    }
}
