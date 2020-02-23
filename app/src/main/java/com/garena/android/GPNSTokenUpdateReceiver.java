package com.garena.android;

import android.content.Context;
import android.content.Intent;
import com.beetalk.sdk.helper.BBLogger;
import com.garena.android.beepost.service.PushTokenUpdateBroadcastReceiver;

public class GPNSTokenUpdateReceiver extends PushTokenUpdateBroadcastReceiver {
    /* access modifiers changed from: protected */
    public void onNewPushToken(Context context, String token) {
        BBLogger.i("new GPNS token %s", token);
        Intent intent = new Intent(BaseTokenUpdateReceiver.ACTION_TOKEN_UPDATE);
        intent.putExtra(BaseTokenUpdateReceiver.EXTRA_TOKEN_TYPE, 2);
        intent.putExtra("token", token);
        context.sendBroadcast(intent, context.getPackageName() + BaseTokenUpdateReceiver.PERMISSION_SUFFIX);
    }
}
