package com.garena.android.gpns.logic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import com.garena.android.gpns.GNotificationService;

public class BroadcastManager {
    private BroadcastReceiver internalReceiver;
    private boolean isNetworkChangeRegistered = false;
    private Context mContext;
    private NetworkChangeReceiver mNetworkChangeReceiver;

    public BroadcastManager(Context context) {
        this.mContext = context;
        this.mNetworkChangeReceiver = new NetworkChangeReceiver();
    }

    public void registerNetworkChangeReceiver() {
        if (!this.isNetworkChangeRegistered) {
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            this.mContext.registerReceiver(this.mNetworkChangeReceiver, filter);
        }
        this.isNetworkChangeRegistered = true;
    }

    public void unregisterNetworkChangeReceiver() {
        if (this.isNetworkChangeRegistered) {
            this.mContext.unregisterReceiver(this.mNetworkChangeReceiver);
        }
        this.isNetworkChangeRegistered = false;
    }

    public void registerLocalHandler(BroadcastReceiver receiver) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(GNotificationService.INTENT_LOCAL_ACTION);
        this.mContext.registerReceiver(receiver, filter);
        this.internalReceiver = receiver;
    }

    public void unregisterLocalHandler() {
        if (this.internalReceiver != null) {
            this.mContext.unregisterReceiver(this.internalReceiver);
        }
    }
}
