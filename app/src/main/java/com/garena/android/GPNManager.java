package com.garena.android;

import android.content.Context;
import android.os.Build;
import com.btalk.helper.BBAppLogger;
import com.garena.android.beepost.service.BeePostAPI;
import com.garena.android.gpns.utility.DeviceUtil;
import com.garena.android.push.PushClient;
import com.garena.msdk.R;
import java.lang.ref.WeakReference;

public class GPNManager {
    private static Context applicationContext;
    private static GPNManager mInstance;
    private static PushClient.PushRequest pushRequest;
    private WeakReference<PushNotificationStateListener> listener;

    private GPNManager() {
    }

    public static void register(Context context, PushClient.PushRequest aRequest) {
        applicationContext = context.getApplicationContext();
        pushRequest = aRequest;
    }

    public static synchronized GPNManager getInstance() {
        GPNManager gPNManager;
        synchronized (GPNManager.class) {
            if (mInstance == null) {
                mInstance = new GPNManager();
            }
            gPNManager = mInstance;
        }
        return gPNManager;
    }

    @Deprecated
    public void setListener(PushNotificationStateListener stateListener) {
        this.listener = new WeakReference<>(stateListener);
    }

    public void startService() {
        int svcType;
        int gcmOrFcm = applicationContext.getResources().getBoolean(R.bool.is_using_fcm) ? 4 : 3;
        if (Build.VERSION.SDK_INT >= 24) {
            svcType = gcmOrFcm;
        } else {
            svcType = 1;
        }
        BBAppLogger.d("beepost mode: %d", Integer.valueOf(svcType));
        BeePostAPI.registerBeePost(applicationContext, pushRequest.appId.toString(), pushRequest.appKey, pushRequest.account, (String) null, applicationContext.getString(R.string.gcm_defaultSenderId), svcType, String.valueOf(DeviceUtil.generateDeviceId(applicationContext)));
    }
}
