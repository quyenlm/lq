package com.tencent.ieg.ntv.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NetworkStateReciver extends BroadcastReceiver {
    public NetworkChangerListener changerListener;

    public interface NetworkChangerListener {
        void onChange();
    }

    public void onReceive(Context context, Intent intent) {
        if (this.changerListener != null) {
            this.changerListener.onChange();
        }
    }
}
