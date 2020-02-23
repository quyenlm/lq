package com.tencent.gsdk.api;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.tencent.gsdk.utils.Logger;

/* compiled from: BatteryListener */
public class a {
    /* access modifiers changed from: private */
    public static int a = 0;
    private static int b = -1;

    public static void a(Context context) {
        if (context == null) {
            Logger.d("startBatteryListener context is null");
            return;
        }
        try {
            context.registerReceiver(new BroadcastReceiver() {
                public void onReceive(Context context, Intent intent) {
                    int unused = a.a = intent.getIntExtra(FirebaseAnalytics.Param.LEVEL, -1);
                    int intExtra = intent.getIntExtra("status", 1);
                    if (intExtra == 2 || intExtra == 5) {
                        a.a(1);
                    }
                }
            }, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        } catch (Exception e) {
            Logger.e("BatteryListener startBatteryListener: " + e.getMessage());
        }
    }

    public static int a() {
        return a;
    }

    public static int b() {
        return b;
    }

    public static void a(int i) {
        b = i;
    }
}
