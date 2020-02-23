package com.garena.android.beepost.service;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import com.garena.android.gpns.external.ServiceManager;
import com.google.firebase.iid.FirebaseInstanceId;
import java.io.IOException;
import org.json.JSONException;

public class BeePostIntentService extends IntentService {
    protected static final String INTENT_APP_ACCOUNT = "account";
    protected static final String INTENT_APP_GCM_DEFAULT_SENDER_ID = "service_gcm_send_id";
    protected static final String INTENT_APP_ID = "app_id";
    protected static final String INTENT_APP_KEY = "app_key";
    protected static final String INTENT_APP_SERVICE_TYPE = "service";
    protected static final String INTENT_APP_TOKEN = "token";
    protected static final String INTENT_DEVICE_ID = "device_id";
    protected static final String INTENT_RETRIES_COUNT = "failed_count";
    private static final int MAX_RETRIES = 6;
    private static final long TIME_INTERVAL_BASE = 10000;
    private static final int mRequestCode = 100;

    public BeePostIntentService() {
        super("BeePostIntentService");
        setIntentRedelivery(true);
    }

    /* access modifiers changed from: protected */
    public void onHandleIntent(Intent intent) {
        int serviceType = intent.getIntExtra("service", 1);
        ServiceManager serviceManager = new ServiceManager(getApplicationContext());
        if (serviceType == 1) {
            if (GooglePlayServiceAvailability.isAvailable(getApplicationContext())) {
                serviceType = 4;
                if (BeePostRuntimeConfig.LogEnabled) {
                    Log.i(BeePostRuntimeConfig.LOG_TAG, "BeePost uses FCM");
                }
            } else {
                serviceType = 2;
                if (BeePostRuntimeConfig.LogEnabled) {
                    Log.i(BeePostRuntimeConfig.LOG_TAG, "BeePost uses GPNS");
                }
            }
        }
        if (intent.hasExtra("token")) {
            handleNewToken(intent.getStringExtra("token"), intent);
        } else if (serviceType == 2) {
            handleNewToken(serviceManager.getToken(), intent);
        } else {
            handleNewToken(FirebaseInstanceId.getInstance().getToken(), intent);
        }
    }

    private void handleNewToken(String token, Intent intent) {
        String appId = intent.getStringExtra("app_id");
        String appKey = intent.getStringExtra("app_key");
        String account = intent.getStringExtra(INTENT_APP_ACCOUNT);
        String deviceId = intent.getStringExtra("device_id");
        int serviceType = intent.getIntExtra("service", 1);
        if (BeePostRuntimeConfig.LogEnabled) {
            Log.i(BeePostRuntimeConfig.LOG_TAG, "new token:" + (TextUtils.isEmpty(token) ? "n.a" : token));
        }
        try {
            if (!(!TextUtils.isEmpty(token) && BeePostAPI.submitToken(getApplicationContext(), appId, appKey, account, token, serviceType, deviceId))) {
                int failedCount = intent.getIntExtra(INTENT_RETRIES_COUNT, 0);
                if (BeePostRuntimeConfig.LogEnabled) {
                    Log.e(BeePostRuntimeConfig.LOG_TAG, "BeePost acquires token RETRY " + failedCount);
                }
                if (failedCount <= 6) {
                    intent.putExtra(INTENT_RETRIES_COUNT, failedCount + 1);
                    ((AlarmManager) getSystemService(NotificationCompat.CATEGORY_ALARM)).set(1, System.currentTimeMillis() + (10000 * ((long) failedCount)), PendingIntent.getService(getApplicationContext(), 100, intent, 1073741824));
                }
            }
        } catch (IOException | JSONException e) {
            if (BeePostRuntimeConfig.LogEnabled) {
                Log.wtf(BeePostRuntimeConfig.LOG_TAG, e);
            }
        }
    }
}
