package com.google.android.gms.internal;

import android.content.Context;
import android.util.Log;
import com.google.android.gms.measurement.AppMeasurement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class zzbhk {
    private static AppMeasurement zzaQ(Context context) {
        try {
            return AppMeasurement.getInstance(context);
        } catch (NoClassDefFoundError e) {
            return null;
        }
    }

    public static List<zzbhi> zzaR(Context context) {
        Map<String, Object> map;
        AppMeasurement zzaQ = zzaQ(context);
        if (zzaQ != null) {
            try {
                map = zzaQ.getUserProperties(false);
            } catch (NullPointerException e) {
                if (Log.isLoggable("FRCAnalytics", 3)) {
                    Log.d("FRCAnalytics", "Unable to get user properties.", e);
                }
                map = null;
            }
            if (map == null) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            for (Map.Entry next : map.entrySet()) {
                if (next.getValue() != null) {
                    arrayList.add(new zzbhi((String) next.getKey(), next.getValue().toString()));
                }
            }
            return arrayList;
        } else if (!Log.isLoggable("FRCAnalytics", 3)) {
            return null;
        } else {
            Log.d("FRCAnalytics", "Unable to get user properties: analytics library is missing.");
            return null;
        }
    }
}
