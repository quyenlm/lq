package com.appsflyer;

import com.appsflyer.b;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class FirebaseInstanceIdListener extends FirebaseInstanceIdService {
    public void onTokenRefresh() {
        String str;
        super.onTokenRefresh();
        long currentTimeMillis = System.currentTimeMillis();
        try {
            str = FirebaseInstanceId.getInstance().getToken();
        } catch (Throwable th) {
            AFLogger.afErrorLog("Error registering for uninstall tracking", th);
            str = null;
        }
        if (str != null) {
            AFLogger.afInfoLog("Firebase Refreshed Token = ".concat(String.valueOf(str)));
            b.e.C0035b r1 = b.e.C0035b.m51(AppsFlyerProperties.getInstance().getString("afUninstallToken"));
            b.e.C0035b bVar = new b.e.C0035b(currentTimeMillis, str);
            if (r1.m53(bVar)) {
                y.m167(getApplicationContext(), bVar);
            }
        }
    }
}
