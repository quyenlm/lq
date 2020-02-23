package com.tencent.tdm.system;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class TXReceiver extends BroadcastReceiver {
    private NetworkType LastNet = NetworkType.NETWORK_UNKNOWN;

    public void onReceive(Context context, Intent intent) {
        NetworkType netType = TXSystem.getInstance().GetNetworkType(context);
        if (netType != this.LastNet) {
            this.LastNet = netType;
            TX.GetInstance().OnNetworkChanged(netType.ordinal(), true);
        }
    }
}
