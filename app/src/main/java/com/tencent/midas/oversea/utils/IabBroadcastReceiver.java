package com.tencent.midas.oversea.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class IabBroadcastReceiver extends BroadcastReceiver {
    public static final String ACTION = "com.android.vending.billing.PURCHASES_UPDATED";
    private final IabBroadcastListener a;

    public interface IabBroadcastListener {
        void receivedBroadcast();
    }

    public IabBroadcastReceiver(IabBroadcastListener iabBroadcastListener) {
        this.a = iabBroadcastListener;
    }

    public void onReceive(Context context, Intent intent) {
        if (this.a != null) {
            this.a.receivedBroadcast();
        }
    }
}
