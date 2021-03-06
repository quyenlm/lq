package com.google.android.gms.gcm;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

final class zzc extends Handler {
    private /* synthetic */ GoogleCloudMessaging zzbfU;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzc(GoogleCloudMessaging googleCloudMessaging, Looper looper) {
        super(looper);
        this.zzbfU = googleCloudMessaging;
    }

    public final void handleMessage(Message message) {
        if (message == null || !(message.obj instanceof Intent)) {
            Log.w(GoogleCloudMessaging.INSTANCE_ID_SCOPE, "Dropping invalid message");
        }
        Intent intent = (Intent) message.obj;
        if ("com.google.android.c2dm.intent.REGISTRATION".equals(intent.getAction())) {
            this.zzbfU.zzbfS.add(intent);
        } else if (!this.zzbfU.zze(intent)) {
            intent.setPackage(this.zzbfU.zzqD.getPackageName());
            this.zzbfU.zzqD.sendBroadcast(intent);
        }
    }
}
