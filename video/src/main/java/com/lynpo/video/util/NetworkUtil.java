package com.lynpo.video.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtil {

    public static final int NOT_CONNECTED = 0;
    public static final int MOBILE_DATA = 1;
    public static final int WIFI = 2;

    public static boolean isNetworkConnected(Context context) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            return (networkInfo != null && networkInfo.isConnected());
    }

    public static String getConnectivityStatusString(Context context) {
        int conn = getConnectivityStatus(context);
        String status = null;
        if (conn == WIFI) {
            status = "wifi";
        } else if (conn == MOBILE_DATA) {
            status = "wwan";
        } else if (conn == NOT_CONNECTED) {
            status = "offline";
        }
        return status;
    }

    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return WIFI;

            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return MOBILE_DATA;
        }
        return NOT_CONNECTED;
    }
}
