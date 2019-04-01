package com.lynpo.framework.ipc;

import android.os.Binder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


/**
 * Create by fujw on 2018/9/22.
 * *
 * Stub
 */
public class Stub extends Binder {

    public static final int ADD = 1;
    public static final String ADD_DESCRIPTOR = "add two int";

    @Override
    protected boolean onTransact(int code, @NonNull Parcel data, @Nullable Parcel reply, int flags) throws RemoteException {
        Log.d("debug_info", "Stub=====onTransact, code: " + code);
        int arg0 = data.readInt();
        int arg1 = data.readInt();
        Log.d("debug_info", "arg0:" + arg0 + ", arg1:" + arg1);
        switch (code) {
            case INTERFACE_TRANSACTION:
                reply.writeString("trans");
                return true;
            case ADD:
//                data.enforceInterface(ADD_DESCRIPTOR);
//                int arg0 = data.readInt();
//                int arg1 = data.readInt();
                Log.d("debug_info", "arg0:" + arg0 + ", arg1:" + arg1);
                int result = ((Plus) queryLocalInterface(ADD_DESCRIPTOR)).plus(arg0, arg1);
                Log.d("debug_info", "result:" + result);
                reply.writeNoException();
                reply.writeInt(result);
                return true;
        }
        return super.onTransact(code, data, reply, flags);
    }
}
