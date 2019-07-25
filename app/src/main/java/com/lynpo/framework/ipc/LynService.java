package com.lynpo.framework.ipc;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.util.Log;

import androidx.annotation.Nullable;

import com.lynpo.eternal.LynConstants;


/**
 * Create by fujw on 2018/9/22.
 * *
 * LynService
 */
public class LynService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(LynConstants.LOG_TAG, "LynService=====onBind");
        IInterface owner = new Plus();
        Binder binder = new Stub();
        binder.attachInterface(owner, Stub.ADD_DESCRIPTOR);
        return binder;
    }
}
