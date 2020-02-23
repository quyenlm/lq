package com.tencent.wetest;

import android.app.Activity;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.util.Log;
import com.tencent.midas.oversea.network.http.APBaseHttpParam;
import com.tencent.tp.a.h;
import java.lang.Thread;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class WetestReport {
    public static final String TAG = "WeTestReport";
    private static WetestReport a = null;
    /* access modifiers changed from: private */
    public static int b = 0;
    private static boolean c = false;
    private static boolean d = false;
    /* access modifiers changed from: private */
    public static Thread.UncaughtExceptionHandler e;
    private static Thread.UncaughtExceptionHandler f = new h();

    static {
        c();
    }

    private static void a(List list) {
        try {
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(list, "UTF-8");
            HttpPost httpPost = new HttpPost("http://wetest.qq.com/cloudapi/api_v2/sdkreport");
            httpPost.setEntity(urlEncodedFormEntity);
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            HttpResponse execute = new DefaultHttpClient().execute(httpPost);
            Log.d("wetest", EntityUtils.toString(urlEncodedFormEntity, "UTF-8"));
            Log.d("wetest", EntityUtils.toString(execute.getEntity(), "UTF-8"));
        } catch (Exception e2) {
            Log.e("wetest", e2.getMessage(), e2);
        }
    }

    private static void c() {
        String str;
        int i;
        if (!d) {
            try {
                ArrayList arrayList = new ArrayList();
                d = true;
                Activity GetPlayerActivity = U3DAutomation.GetPlayerActivity();
                try {
                    str = GetPlayerActivity.getApplicationContext().getPackageName();
                } catch (Exception e2) {
                    str = "";
                }
                try {
                    i = ((WifiManager) GetPlayerActivity.getSystemService("wifi")).getConnectionInfo().getIpAddress();
                } catch (Exception e3) {
                    i = 0;
                }
                String str2 = Build.MODEL;
                Integer valueOf = Integer.valueOf(Build.VERSION.SDK_INT);
                String str3 = Build.FINGERPRINT;
                MessageDigest instance = MessageDigest.getInstance("MD5");
                instance.update(str.getBytes());
                byte[] digest = instance.digest();
                StringBuilder sb = new StringBuilder();
                for (byte b2 : digest) {
                    String hexString = Integer.toHexString(b2 & 255);
                    while (hexString.length() < 2) {
                        hexString = "0" + hexString;
                    }
                    sb.append(hexString);
                }
                String sb2 = sb.toString();
                Log.i("wetest", "PackageName:" + str + h.a + sb2 + ") Host:" + i + " model:" + str2 + " version:" + valueOf + " FingerInt:" + str3);
                arrayList.add(new BasicNameValuePair("packagename", sb2));
                arrayList.add(new BasicNameValuePair(APBaseHttpParam.IP_ACCESS, Integer.toString(i)));
                arrayList.add(new BasicNameValuePair("model", str2));
                arrayList.add(new BasicNameValuePair("fingerprint", str3));
                a((List) arrayList);
            } catch (Exception e4) {
                Log.e(TAG, e4.getMessage(), e4);
            }
        }
    }

    public static void initCrashReport() {
        if (c) {
            Log.d("wetest", "java crash monitor has inited");
            return;
        }
        try {
            Log.i("wetest", "Register java uncaught exception");
            e = Thread.getDefaultUncaughtExceptionHandler();
            Thread.setDefaultUncaughtExceptionHandler(f);
            c = true;
        } catch (Exception e2) {
        }
    }

    public static void logAgent(String str) {
        Log.d(TAG, str);
    }

    public static void logError(String str, String str2, String str3, String str4, boolean z) {
        if (str == null) {
            str = "";
        }
        if (str2 == null) {
            str2 = "";
        }
        if (str3 == null) {
            str3 = "";
        }
        if (str4 == null) {
            str4 = "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("*****************<WetestReport>*********************\n");
        sb.append("EXCEPTION TYPE: ");
        sb.append(str).append("\n");
        sb.append("MESSAGE: ");
        sb.append(str2).append("\n");
        sb.append("STACKTRACE: \n");
        sb.append(str3).append("\n\n");
        sb.append("SCENE: ");
        sb.append(str4).append("\n");
        sb.append("UNCAUGHT: ");
        sb.append(z ? "True" : "False").append("\n");
        sb.append("CRASH TYPE: ");
        sb.append("C# CRASH\n");
        sb.append("INDEX: ");
        int i = b;
        b = i + 1;
        sb.append(i);
        sb.append("\n");
        sb.append("***************************************************\n");
        Log.e(TAG, sb.toString());
    }

    public static void testCrash() {
        Log.e("wetest", "TestCrash");
        try {
            Activity GetPlayerActivity = U3DAutomation.GetPlayerActivity();
            if (GetPlayerActivity == null) {
                Log.e("wetest", "Can not find UnityActivity");
            } else {
                GetPlayerActivity.runOnUiThread(new i());
            }
        } catch (Exception e2) {
        }
    }
}
