package com.tencent.kgvmp.report;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.tencent.kgvmp.a.a;
import com.tencent.kgvmp.a.c;
import com.tencent.kgvmp.e.f;
import java.util.Locale;

public class BatteryInfoReceiver extends BroadcastReceiver {
    private static final String a = a.a;
    private double b = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;

    public void onReceive(Context context, Intent intent) {
        try {
            this.b = ((double) intent.getIntExtra("temperature", 0)) / 10.0d;
            d.a(c.BATTERY_TEMP.getKey(), String.format(Locale.CHINA, "%.1f", new Object[]{Double.valueOf(this.b)}));
        } catch (Exception e) {
            e.printStackTrace();
            f.a(a, "BatteryInfoReceiver:onReceive: exception");
        }
    }
}
