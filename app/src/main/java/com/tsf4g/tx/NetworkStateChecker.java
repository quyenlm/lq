package com.tsf4g.tx;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkStateChecker {
    private static final String tag = "NetworkStateChecker";

    public int CheckNetworkState(Context context) {
        NetworkState.NotReachable.ordinal();
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager == null) {
                return NetworkState.NotReachable.ordinal();
            }
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
                return NetworkState.NotReachable.ordinal();
            }
            switch (activeNetworkInfo.getType()) {
                case 0:
                    return NetworkState.ReachableViaWWAN.ordinal();
                case 1:
                    return NetworkState.ReachableViaWiFi.ordinal();
                default:
                    return NetworkState.ReachableViaOthers.ordinal();
            }
        } catch (Exception e) {
            TXLog.e(tag, "check Get exception:" + e.toString());
            return NetworkState.NotReachable.ordinal();
        }
    }
}
