package com.garena.android.gpns.external;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import com.garena.android.gpns.utility.AppLogger;

public final class ApplicationManager {
    static final /* synthetic */ boolean $assertionsDisabled = (!ApplicationManager.class.desiredAssertionStatus());
    public static final String APPLICATION_ID = "com.garena.sdk.push.applicationId";

    public static String getPackageForId(int appId, Context context) {
        if ($assertionsDisabled || context.getPackageManager() != null) {
            AppLogger.d("Receive Package App ID " + appId);
            for (ApplicationInfo applicationInfo : context.getPackageManager().getInstalledApplications(128)) {
                if (applicationInfo.metaData != null && Integer.valueOf(applicationInfo.metaData.getInt("com.garena.sdk.push.applicationId", -1)).intValue() == appId) {
                    AppLogger.d("Found a match for push notification " + applicationInfo.packageName);
                    return applicationInfo.packageName;
                }
            }
            return "";
        }
        throw new AssertionError();
    }

    private ApplicationManager() {
    }
}
