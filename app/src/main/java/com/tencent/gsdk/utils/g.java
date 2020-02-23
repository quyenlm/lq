package com.tencent.gsdk.utils;

import android.content.Context;
import com.tencent.midas.oversea.api.UnityPayHelper;

/* compiled from: Hardware */
public final class g {
    public static String a = com.tencent.gsdk.api.g.l();
    public static String b = com.tencent.gsdk.api.g.b();
    public static String c = com.tencent.gsdk.api.g.c().get("Hardware");
    public static String d = String.valueOf(com.tencent.gsdk.api.g.a());
    public static String e = UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR;
    public static String f = UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR;
    public static String g = UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR;

    public static void a(Context context) {
        if (context != null) {
            Context applicationContext = context.getApplicationContext();
            f = String.valueOf(com.tencent.gsdk.api.g.a(applicationContext));
            g = String.valueOf(com.tencent.gsdk.api.g.e(applicationContext));
        }
    }
}
