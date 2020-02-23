package com.appsflyer;

import android.content.Context;
import android.support.v4.app.NotificationCompat;
import com.android.installreferrer.api.InstallReferrerClient;
import com.android.installreferrer.api.InstallReferrerStateListener;
import com.android.installreferrer.api.ReferrerDetails;
import com.tencent.component.plugin.PluginReporter;
import com.tencent.midas.oversea.api.UnityPayHelper;
import java.util.HashMap;

final class d implements InstallReferrerStateListener {

    /* renamed from: ˊ  reason: contains not printable characters */
    private InstallReferrerClient f78;

    /* renamed from: ॱ  reason: contains not printable characters */
    private a f79;

    d() {
    }

    /* access modifiers changed from: protected */
    /* renamed from: ॱ  reason: contains not printable characters */
    public final void m57(Context context, a aVar) {
        this.f79 = aVar;
        this.f78 = InstallReferrerClient.newBuilder(context).build();
        try {
            this.f78.startConnection(this);
        } catch (Exception e) {
            AFLogger.afErrorLog("referrerClient -> startConnection", e);
        }
    }

    public final void onInstallReferrerSetupFinished(int i) {
        HashMap hashMap = new HashMap();
        hashMap.put("code", String.valueOf(i));
        ReferrerDetails referrerDetails = null;
        switch (i) {
            case 0:
                try {
                    AFLogger.afDebugLog("InstallReferrer connected");
                    if (!this.f78.isReady()) {
                        AFLogger.afWarnLog("ReferrerClient: InstallReferrer is not ready");
                        hashMap.put(NotificationCompat.CATEGORY_ERROR, "ReferrerClient: InstallReferrer is not ready");
                        break;
                    } else {
                        referrerDetails = this.f78.getInstallReferrer();
                        this.f78.endConnection();
                        break;
                    }
                } catch (Throwable th) {
                    AFLogger.afWarnLog(new StringBuilder("Failed to get install referrer: ").append(th.getMessage()).toString());
                    hashMap.put(NotificationCompat.CATEGORY_ERROR, th.getMessage());
                    break;
                }
            case 1:
                AFLogger.afWarnLog("InstallReferrer not supported");
                break;
            case 2:
                AFLogger.afWarnLog("InstallReferrer not supported");
                break;
            default:
                AFLogger.afWarnLog("responseCode not found.");
                break;
        }
        if (referrerDetails != null) {
            try {
                if (referrerDetails.getInstallReferrer() != null) {
                    hashMap.put("val", referrerDetails.getInstallReferrer());
                }
                hashMap.put("clk", Long.toString(referrerDetails.getReferrerClickTimestampSeconds()));
                hashMap.put(PluginReporter.EVENT_INSTALL, Long.toString(referrerDetails.getInstallBeginTimestampSeconds()));
            } catch (Exception e) {
                e.printStackTrace();
                hashMap.put("val", UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR);
                hashMap.put("clk", UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR);
                hashMap.put(PluginReporter.EVENT_INSTALL, UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR);
            }
        }
        if (this.f79 != null) {
            this.f79.onHandleReferrer(hashMap);
        }
    }

    public final void onInstallReferrerServiceDisconnected() {
        AFLogger.afDebugLog("Install Referrer service disconnected");
    }
}
