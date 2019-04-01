package com.lynpo.framework.ipc;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

import com.lynpo.eternal.base.ui.BaseActivity;

import androidx.annotation.Nullable;

/**
 * Create by fujw on 2018/9/22.
 * *
 * Client
 */
public class IPCClient extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        plus(2, 5);
    }

    public void plus(final int a, final int b) {
        Intent intent = new Intent(mContext, LynService.class);
        bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d("debug_info", "onServiceConnected");
                Parcel data = Parcel.obtain();
                Parcel reply = Parcel.obtain();
//                data.writeInterfaceToken(Stub.ADD_DESCRIPTOR);
                data.writeInt(a);
                data.writeInt(b);
                try {
                    service.transact(Stub.ADD, data, reply, 0);
                    reply.readException();
                    int result = reply.readInt();
//                    Log.d("debug_info", "Client::result:" + result);
                    printResult(result);
                } catch (RemoteException ignored) {

                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Context.BIND_AUTO_CREATE);
    }

    private void printResult(int result) {
        Log.d("debug_info", "printResult:" + result);
    }
}
