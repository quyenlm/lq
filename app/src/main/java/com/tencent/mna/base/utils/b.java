package com.tencent.mna.base.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.google.firebase.analytics.FirebaseAnalytics;

/* compiled from: BatteryListener */
public final class b {
    /* access modifiers changed from: private */
    public int a = 0;
    /* access modifiers changed from: private */
    public int b = -1;

    /* compiled from: BatteryListener */
    public interface a {
        void a(int i, int i2);
    }

    public void a(Context context, final a aVar) {
        if (context == null) {
            h.d("startBatteryListener context null");
            return;
        }
        try {
            context.registerReceiver(new BroadcastReceiver() {
                public void onReceive(Context context, Intent intent) {
                    int i = 0;
                    int intExtra = intent.getIntExtra(FirebaseAnalytics.Param.LEVEL, -1);
                    int intExtra2 = intent.getIntExtra("status", 1);
                    if (intExtra2 == 2 || intExtra2 == 5) {
                        i = 1;
                    }
                    if (intExtra != b.this.b || i != b.this.a) {
                        aVar.a(i, intExtra);
                        int unused = b.this.a = i;
                        int unused2 = b.this.b = intExtra;
                    }
                }
            }, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        } catch (Exception e) {
            h.a("startBatteryListener " + e.getMessage());
        }
    }
}
