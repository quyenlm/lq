package com.garena.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import bolts.Task;
import com.beetalk.sdk.cache.SharedPrefStorage;
import com.beetalk.sdk.data.AuthToken;
import com.beetalk.sdk.helper.BBLogger;
import com.beetalk.sdk.helper.Helper;
import com.garena.android.beepost.service.BeePostAPI;
import com.garena.android.gpns.utility.DeviceUtil;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.JSONException;

public abstract class BaseTokenUpdateReceiver extends BroadcastReceiver {
    public static final String ACTION_TOKEN_UPDATE = "com.garena.android.msdk.TOKEN_UPDATE";
    public static final String EXTRA_TOKEN = "token";
    public static final String EXTRA_TOKEN_TYPE = "token_type";
    public static final String PERMISSION_SUFFIX = ".permission.PUSH_NOTIFICATION_TOKEN_UPDATE";
    public static final int TOKEN_TYPE_FCM = 4;
    public static final int TOKEN_TYPE_GCM = 3;
    public static final int TOKEN_TYPE_GPNS = 2;
    private static final ExecutorService UPDATE_EXECUTOR = Executors.newSingleThreadExecutor();

    /* access modifiers changed from: protected */
    public abstract String getPushAppKey();

    public void onReceive(final Context context, final Intent intent) {
        Task.call(new Callable<Void>() {
            public Void call() throws Exception {
                BaseTokenUpdateReceiver.this.serialExecBg(context, intent);
                return null;
            }
        }, (Executor) UPDATE_EXECUTOR);
    }

    /* access modifiers changed from: private */
    public void serialExecBg(Context context, Intent intent) {
        if (intent != null && intent.hasExtra(EXTRA_TOKEN_TYPE)) {
            int type = intent.getIntExtra(EXTRA_TOKEN_TYPE, 0);
            if (type == 3 || type == 4 || type == 2) {
                String token = intent.getStringExtra("token");
                if (TextUtils.isEmpty(token)) {
                    BBLogger.d("err: token is empty", new Object[0]);
                    return;
                }
                String appId = Helper.getMetaDataAppId(context);
                if (TextUtils.isEmpty(appId)) {
                    BBLogger.d("err: app id is empty", new Object[0]);
                    return;
                }
                AuthToken auth = new SharedPrefStorage(context).getToken();
                String account = auth == null ? "" : auth.getOpenId();
                if (TextUtils.isEmpty(account)) {
                    BBLogger.d("user not logged in, ignored", new Object[0]);
                    return;
                }
                String appKey = getPushAppKey();
                if (TextUtils.isEmpty(appKey)) {
                    BBLogger.d("err: app push key is empty", new Object[0]);
                    return;
                }
                String deviceId = String.valueOf(DeviceUtil.generateDeviceId(context));
                if (TextUtils.isEmpty(deviceId)) {
                    BBLogger.d("err: device id is empty", new Object[0]);
                    return;
                }
                BBLogger.d("submitting new token to beepost", new Object[0]);
                try {
                    BeePostAPI.submitToken(context, appId, appKey, account, token, type, deviceId);
                } catch (IOException e) {
                    BBLogger.e(e);
                } catch (JSONException e2) {
                    BBLogger.e(e2);
                }
            } else {
                BBLogger.d("err: unknown service type", new Object[0]);
            }
        }
    }
}
