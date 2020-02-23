package com.garena.android.beepost.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.garena.android.gpns.utility.CONSTANT;

public abstract class PushTokenUpdateBroadcastReceiver extends BroadcastReceiver {
    /* access modifiers changed from: protected */
    public abstract void onNewPushToken(Context context, String str);

    public void onReceive(Context context, Intent intent) {
        onNewPushToken(context, String.valueOf(intent.getLongExtra(CONSTANT.INTENT_EXTRA.GPID_UPDATE_INTENT_EXTRA_GPID, -1)));
    }
}
