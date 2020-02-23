package com.tencent.smtt.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import com.tencent.imsdk.framework.request.HttpRequest;
import com.tencent.smtt.sdk.TbsConfig;
import com.tencent.smtt.sdk.WebView;

public class w {
    private static String a = null;
    private static String b = "GA";
    private static String c = "GE";
    private static String d = "9422";
    private static String e = "0";
    private static String f = "";
    private static boolean g = false;
    private static boolean h = false;
    private static boolean i = false;

    private static String a() {
        return " " + Build.MODEL.replaceAll("[ |\\/|\\_|\\&|\\|]", "") + " ";
    }

    public static String a(Context context) {
        if (!TextUtils.isEmpty(a)) {
            return a;
        }
        a = a(context, String.valueOf(WebView.getTbsSDKVersion(context)), "0", b, c, d, e, f, g);
        return a;
    }

    private static String a(Context context, String str, String str2, String str3, String str4, String str5, String str6, String str7, boolean z) {
        String str8;
        String str9;
        String str10;
        String str11 = "";
        StringBuilder sb = new StringBuilder();
        String str12 = b(context) + "*" + c(context);
        try {
            ApplicationInfo applicationInfo = context.getApplicationContext().getApplicationInfo();
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(applicationInfo.packageName, 0);
            str11 = applicationInfo.packageName;
            if (TextUtils.isEmpty(str7)) {
                str7 = packageInfo.versionName;
            }
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
            str7 = "";
        }
        String a2 = a(str11);
        if ("QB".equals(a2)) {
            if (z) {
                str8 = "PAD";
            }
            str8 = "PHONE";
        } else {
            if (d(context)) {
                str8 = "PAD";
            }
            str8 = "PHONE";
        }
        sb.append("QV").append(HttpRequest.HTTP_REQ_ENTITY_MERGE).append("3");
        a(sb, "PL", "ADR");
        a(sb, "PR", a2);
        a(sb, "PP", str11);
        a(sb, "PPVN", str7);
        if (!TextUtils.isEmpty(str)) {
            a(sb, "TBSVC", str);
        }
        a(sb, "CO", "SYS");
        if (!TextUtils.isEmpty(str2)) {
            a(sb, "COVC", str2);
        }
        a(sb, "PB", str4);
        a(sb, "VE", str3);
        a(sb, "DE", str8);
        if (TextUtils.isEmpty(str6)) {
            str6 = "0";
        }
        a(sb, "CHID", str6);
        a(sb, "LCID", str5);
        String a3 = a();
        try {
            str9 = new String(a3.getBytes("UTF-8"), "ISO8859-1");
        } catch (Exception e3) {
            str9 = a3;
        }
        if (!TextUtils.isEmpty(str9)) {
            a(sb, "MO", str9);
        }
        a(sb, "RL", str12);
        String str13 = Build.VERSION.RELEASE;
        try {
            str10 = new String(str13.getBytes("UTF-8"), "ISO8859-1");
        } catch (Exception e4) {
            str10 = str13;
        }
        if (!TextUtils.isEmpty(str10)) {
            a(sb, "OS", str10);
        }
        a(sb, "API", Build.VERSION.SDK_INT + "");
        return sb.toString();
    }

    private static String a(String str) {
        return str.equals(TbsConfig.APP_WX) ? "WX" : str.equals(TbsConfig.APP_QQ) ? "QQ" : str.equals(TbsConfig.APP_QZONE) ? "QZ" : str.equals(TbsConfig.APP_QB) ? "QB" : "TRD";
    }

    private static void a(StringBuilder sb, String str, String str2) {
        sb.append(HttpRequest.HTTP_REQ_ENTITY_JOIN).append(str).append(HttpRequest.HTTP_REQ_ENTITY_MERGE).append(str2);
    }

    private static int b(Context context) {
        Display defaultDisplay = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        if (defaultDisplay != null) {
            return defaultDisplay.getWidth();
        }
        return -1;
    }

    private static int c(Context context) {
        Display defaultDisplay = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        if (defaultDisplay != null) {
            return defaultDisplay.getHeight();
        }
        return -1;
    }

    private static boolean d(Context context) {
        boolean z = true;
        if (h) {
            return i;
        }
        try {
            if ((Math.min(b(context), c(context)) * 160) / e(context) < 700) {
                z = false;
            }
            i = z;
            h = true;
            return i;
        } catch (Throwable th) {
            return false;
        }
    }

    private static int e(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        Display defaultDisplay = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        if (defaultDisplay == null) {
            return 160;
        }
        defaultDisplay.getMetrics(displayMetrics);
        return displayMetrics.densityDpi;
    }
}
