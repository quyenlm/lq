package com.tencent.msdk.stat.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.tencent.tp.r;
import java.util.Collections;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class p {
    public static String a(Context context) {
        try {
            if (a(context, r.a)) {
                String deviceId = ((TelephonyManager) context.getSystemService("phone")).getDeviceId();
                if (deviceId != null) {
                    return deviceId;
                }
            } else {
                Log.e(StatConstants.LOG_TAG, "Could not get permission of android.permission.READ_PHONE_STATE");
            }
        } catch (Throwable th) {
            Log.e(StatConstants.LOG_TAG, "get device id error", th);
        }
        return null;
    }

    public static String a(String str) {
        if (str == null) {
            return null;
        }
        if (Build.VERSION.SDK_INT < 8) {
            return str;
        }
        try {
            return new String(e.b(f.a(str.getBytes("UTF-8"), 0)), "UTF-8");
        } catch (Throwable th) {
            Log.e(StatConstants.LOG_TAG, "decode error", th);
            return str;
        }
    }

    public static JSONArray a(Context context, int i) {
        List<ScanResult> scanResults;
        try {
            if (!a(context, "android.permission.INTERNET") || !a(context, "android.permission.ACCESS_NETWORK_STATE")) {
                Log.e(StatConstants.LOG_TAG, "can not get the permisson of android.permission.INTERNET");
                return null;
            }
            WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
            if (!(wifiManager == null || (scanResults = wifiManager.getScanResults()) == null || scanResults.size() <= 0)) {
                Collections.sort(scanResults, new q());
                JSONArray jSONArray = new JSONArray();
                int i2 = 0;
                while (i2 < scanResults.size() && i2 < i) {
                    ScanResult scanResult = scanResults.get(i2);
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("bs", scanResult.BSSID);
                    jSONObject.put("ss", scanResult.SSID);
                    jSONObject.put("dBm", scanResult.level);
                    jSONArray.put(jSONObject);
                    i2++;
                }
                return jSONArray;
            }
            return null;
        } catch (Throwable th) {
            Log.e(StatConstants.LOG_TAG, "isWifiNet error", th);
        }
    }

    public static void a(JSONObject jSONObject, String str, String str2) {
        if (str2 != null) {
            try {
                if (str2.length() > 0) {
                    jSONObject.put(str, str2);
                }
            } catch (Throwable th) {
                Log.e(StatConstants.LOG_TAG, "jsonPut error", th);
            }
        }
    }

    public static boolean a(Context context, String str) {
        try {
            return context.getPackageManager().checkPermission(str, context.getPackageName()) == 0;
        } catch (Throwable th) {
            Log.e(StatConstants.LOG_TAG, "checkPermission error", th);
            return false;
        }
    }

    public static String b(Context context) {
        if (a(context, "android.permission.ACCESS_WIFI_STATE")) {
            try {
                WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
                return wifiManager == null ? "" : wifiManager.getConnectionInfo().getMacAddress();
            } catch (Exception e) {
                Log.e(StatConstants.LOG_TAG, "get wifi address error", e);
                return "";
            }
        } else {
            Log.e(StatConstants.LOG_TAG, "Could not get permission of android.permission.ACCESS_WIFI_STATE");
            return "";
        }
    }

    public static String b(String str) {
        if (str == null) {
            return null;
        }
        if (Build.VERSION.SDK_INT < 8) {
            return str;
        }
        try {
            return new String(f.b(e.a(str.getBytes("UTF-8")), 0), "UTF-8");
        } catch (Throwable th) {
            Log.e(StatConstants.LOG_TAG, "encode error", th);
            return str;
        }
    }

    public static WifiInfo c(Context context) {
        WifiManager wifiManager;
        if (!a(context, "android.permission.ACCESS_WIFI_STATE") || (wifiManager = (WifiManager) context.getApplicationContext().getSystemService("wifi")) == null) {
            return null;
        }
        return wifiManager.getConnectionInfo();
    }

    public static String d(Context context) {
        try {
            WifiInfo c = c(context);
            if (c != null) {
                return c.getBSSID();
            }
        } catch (Throwable th) {
            Log.e(StatConstants.LOG_TAG, "encode error", th);
        }
        return null;
    }

    public static String e(Context context) {
        try {
            WifiInfo c = c(context);
            if (c != null) {
                return c.getSSID();
            }
        } catch (Throwable th) {
            Log.e(StatConstants.LOG_TAG, "encode error", th);
        }
        return null;
    }

    public static boolean f(Context context) {
        try {
            if (!a(context, "android.permission.INTERNET") || !a(context, "android.permission.ACCESS_NETWORK_STATE")) {
                Log.e(StatConstants.LOG_TAG, "can not get the permisson of android.permission.INTERNET");
                return false;
            }
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager != null) {
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if (activeNetworkInfo != null && activeNetworkInfo.isAvailable()) {
                    return true;
                }
                Log.w(StatConstants.LOG_TAG, "Network error");
                return false;
            }
            return false;
        } catch (Throwable th) {
            Log.e(StatConstants.LOG_TAG, "isNetworkAvailable error", th);
        }
    }
}
