package com.tencent.gsdk.api;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.gsdk.utils.Logger;
import com.tencent.gsdk.utils.b.f;
import com.tencent.gsdk.utils.c;
import com.tencent.gsdk.utils.c.d;
import com.tencent.gsdk.utils.g;
import com.tencent.gsdk.utils.j;
import com.tencent.midas.oversea.api.UnityPayHelper;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONObject;

public final class GSDKPlatform {
    private static c info;
    public static String mFpsMode;
    public static String mOptionLevel;
    public static String mQualityLevel;
    public static String mRoomIp;
    public static String mTag = UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR;
    public static String mZoneId;

    public static void GSDKInit(Context context, String str, boolean z, int i) {
        GSDKInit(context, str, z, i, 1);
    }

    public static void GSDKInit(Context context, String str, boolean z, int i, int i2) {
        GSDKSystem.getInstance().a(context, str, z, i, i2);
    }

    public static void GSDKInitWithBeacon(Context context, String str, boolean z, int i) {
        GSDKInit(context, str, z, i, 1);
        GSDKSystem.getInstance().a(context, str);
    }

    public static void GSDKSetUserName(int i, String str) {
        GSDKSystem.getInstance().a(i, str);
    }

    public static void GSDKSetUserNameWithQuality(int i, String str, String str2) {
        GSDKSystem.getInstance().a(i, str, str2);
    }

    public static void GSDKStart(String str, String str2, String str3) {
        mZoneId = str;
        mTag = str2;
        mRoomIp = str3;
        GSDKSystem.getInstance().a(str);
    }

    public static void GSDKStartWithQuality(String str, String str2, String str3, String str4, String str5, String str6) {
        mZoneId = str;
        mTag = str2;
        mRoomIp = str3;
        mFpsMode = str4;
        mQualityLevel = str5;
        mOptionLevel = str6;
        GSDKSystem.getInstance().a(str);
    }

    public static void GSDKEnd() {
        GSDKSystem.getInstance().a(info);
    }

    public static void GSDKSaveFps(float f, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, String str) {
        info = new c(f, i, i2, i3, i4, i5, i6, i7, i8, i9, str);
    }

    public static void GSDKSaveGpuInfo(String str) {
        if (!TextUtils.isEmpty(str)) {
            g.e = str;
        }
    }

    public static void GSDKSetEvent(int i, boolean z, String str, boolean z2, boolean z3) {
        GSDKSystem.getInstance().a(i, z, str, z2, z3);
    }

    public static void GSDKSetPayEvent(int i, int i2, boolean z, String str) {
        GSDKSystem.getInstance().a(i, i2, z, str);
    }

    public static void GSDKTimeOutDetect() {
        GSDKSystem.getInstance().c();
    }

    public static int getBatteryLevel(Context context) {
        return a.a();
    }

    public static void GSDKReportEvent(String str, Map<String, String> map) {
        if (map == null) {
            map = new HashMap<>(2);
        }
        map.put("xg", c.b(GSDKSystem.getInstance().a()));
        map.put("ldns", j.b());
        for (Map.Entry next : map.entrySet()) {
            Logger.d(((String) next.getKey()) + ":" + ((String) next.getValue()));
        }
        f.a(3, str, map);
    }

    public static void reportEvent(String str, String str2) {
        try {
            JSONObject jSONObject = new JSONObject(str2);
            HashMap hashMap = new HashMap();
            Iterator<String> keys = jSONObject.keys();
            while (keys.hasNext()) {
                String next = keys.next();
                hashMap.put(next, String.valueOf(jSONObject.get(next)));
            }
            GSDKReportEvent(str, hashMap);
        } catch (Throwable th) {
        }
    }

    public static String GSDKUDPDetect() {
        return d.b();
    }

    public static String SpeedReturn() {
        return GSDKSystem.getInstance().e();
    }

    public static int getCurrentDelay() {
        return GSDKSystem.getInstance().f();
    }

    public static void setEventObserver(IEventObserver iEventObserver) {
        GSDKSystem.getInstance().a(iEventObserver);
    }

    public static void setLogObserver(Logger.ILogObserver iLogObserver) {
        Logger.setLogObserver(iLogObserver);
    }
}
