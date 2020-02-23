package com.garena.android.beepost.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public abstract class MessageIntentService extends IntentService {
    /* access modifiers changed from: protected */
    public abstract void showNotification(String str) throws Exception;

    public MessageIntentService() {
        super("MessageIntentService");
    }

    /* access modifiers changed from: protected */
    public void onHandleIntent(Intent intent) {
        try {
            showNotification(intent.getExtras().getString("DATA"));
        } catch (Exception e) {
            if (BeePostRuntimeConfig.LogEnabled) {
                Log.wtf(BeePostRuntimeConfig.LOG_TAG, e);
            }
        }
    }
}
