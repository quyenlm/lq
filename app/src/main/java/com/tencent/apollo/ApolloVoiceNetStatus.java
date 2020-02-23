package com.tencent.apollo;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import com.facebook.internal.AnalyticsEvents;

public class ApolloVoiceNetStatus {
    private static String LOGTAG = "ApolloVoice";
    private static String UNKNOWN = AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
    private static Context mainContext;

    public static void SetContext(Context ctxt) {
        mainContext = ctxt;
    }

    public static String Net() {
        String type = UNKNOWN;
        if (mainContext == null) {
            Log.e(LOGTAG, "mainContext is null .May init java first");
            return type;
        }
        ConnectivityManager cm = (ConnectivityManager) mainContext.getSystemService("connectivity");
        if (cm == null) {
            return UNKNOWN;
        }
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null) {
            type = UNKNOWN;
        } else if (info.getType() == 1) {
            type = "WiFi";
        } else if (info.getType() == 0) {
            int subType = info.getSubtype();
            if (subType == 4 || subType == 1 || subType == 2 || subType == 1) {
                type = "2G";
            } else if (subType == 3 || subType == 8 || subType == 6 || subType == 5 || subType == 12) {
                type = "3G";
            } else if (subType == 13) {
                type = "4G";
            }
        }
        Log.i(LOGTAG, "Android Java Get Net status:" + type);
        return type;
    }
}
