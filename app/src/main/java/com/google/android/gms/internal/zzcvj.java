package com.google.android.gms.internal;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import java.util.HashMap;
import java.util.Map;

public class zzcvj {
    private static String zzbEX;
    private static Map<String, String> zzbEY = new HashMap();

    public static String zzL(Context context, String str) {
        if (zzbEX == null) {
            synchronized (zzcvj.class) {
                if (zzbEX == null) {
                    SharedPreferences sharedPreferences = context.getSharedPreferences("gtm_install_referrer", 0);
                    if (sharedPreferences != null) {
                        zzbEX = sharedPreferences.getString("referrer", "");
                    } else {
                        zzbEX = "";
                    }
                }
            }
        }
        return zzV(zzbEX, str);
    }

    public static String zzV(String str, String str2) {
        if (str2 != null) {
            String valueOf = String.valueOf(str);
            return Uri.parse(valueOf.length() != 0 ? "http://hostname/?".concat(valueOf) : new String("http://hostname/?")).getQueryParameter(str2);
        } else if (str.length() > 0) {
            return str;
        } else {
            return null;
        }
    }
}
