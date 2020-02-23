package com.google.android.gms.tagmanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import java.util.HashMap;
import java.util.Map;

public class zzcx {
    private static String zzbEX;
    static Map<String, String> zzbEY = new HashMap();

    static void zzK(Context context, String str) {
        zzfu.zzd(context, "gtm_install_referrer", "referrer", str);
        zzM(context, str);
    }

    public static String zzL(Context context, String str) {
        if (zzbEX == null) {
            synchronized (zzcx.class) {
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

    public static void zzM(Context context, String str) {
        String zzV = zzV(str, "conv");
        if (zzV != null && zzV.length() > 0) {
            zzbEY.put(zzV, str);
            zzfu.zzd(context, "gtm_click_referrers", zzV, str);
        }
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

    public static void zzfn(String str) {
        synchronized (zzcx.class) {
            zzbEX = str;
        }
    }
}
