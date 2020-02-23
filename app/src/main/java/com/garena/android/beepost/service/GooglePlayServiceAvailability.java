package com.garena.android.beepost.service;

import android.content.Context;
import android.util.Log;
import com.google.android.gms.common.GoogleApiAvailability;

public class GooglePlayServiceAvailability {
    private static volatile boolean classAvail = false;
    private static volatile boolean initialised = false;

    public static boolean isAvailable(Context context) {
        if (!initialised) {
            synchronized (GooglePlayServiceAvailability.class) {
                if (!initialised) {
                    initialised = true;
                    classAvail = checkClass();
                }
            }
        }
        if (!classAvail || GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context) != 0) {
            return false;
        }
        return true;
    }

    private static boolean checkClass() {
        try {
            Class.forName("com.google.android.gms.common.GoogleApiAvailability");
            return true;
        } catch (ClassNotFoundException e) {
            if (BeePostRuntimeConfig.LogEnabled) {
                Log.e(BeePostRuntimeConfig.LOG_TAG, "google play service not available");
            }
            return false;
        }
    }
}
