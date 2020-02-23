package com.appsflyer;

import android.os.Bundle;
import com.appsflyer.b;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.google.android.gms.iid.InstanceIDListenerService;

public class GcmInstanceIdListener extends InstanceIDListenerService {
    public void onTokenRefresh() {
        String str;
        super.onTokenRefresh();
        String string = AppsFlyerProperties.getInstance().getString("gcmProjectNumber");
        long currentTimeMillis = System.currentTimeMillis();
        try {
            str = InstanceID.getInstance(getApplicationContext()).getToken(string, GoogleCloudMessaging.INSTANCE_ID_SCOPE, (Bundle) null);
        } catch (Throwable th) {
            AFLogger.afErrorLog("Error registering for uninstall tracking", th);
            str = null;
        }
        if (str != null) {
            AFLogger.afInfoLog("GCM Refreshed Token = ".concat(String.valueOf(str)));
            b.e.C0035b r1 = b.e.C0035b.m51(AppsFlyerProperties.getInstance().getString("afUninstallToken"));
            b.e.C0035b bVar = new b.e.C0035b(currentTimeMillis, str);
            if (r1.m53(bVar)) {
                y.m167(getApplicationContext(), bVar);
            }
        }
    }
}
