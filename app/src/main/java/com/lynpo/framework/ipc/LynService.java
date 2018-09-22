package com.lynpo.framework.ipc;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.support.annotation.Nullable;
import android.util.Log;


/**
 * Create by fujw on 2018/9/22.
 * *
 * LynService
 */
public class LynService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("debug_info", "LynService=====onBind");
        IInterface owner = new Plus();
        Binder binder = new Stub();
        binder.attachInterface(owner, Stub.ADD_DESCRIPTOR);
        return binder;
    }
}
