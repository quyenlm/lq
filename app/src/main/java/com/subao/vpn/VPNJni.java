package com.subao.vpn;

import android.text.TextUtils;
import android.util.Log;

public class VPNJni {
    private static volatile JniCallback a;
    private static boolean b;

    public static native void addAccelAddress(int i, int i2, String str, int i3);

    public static native void defineConst(int i, byte[] bArr, byte[] bArr2);

    public static native int detectAccessDelay(int i);

    public static native int detectTimeDelay(int i, String str, int i2, String str2, int i3, int i4, int i5);

    public static native void domainNameResolveResult(int i, String str);

    public static native int getAccelRecommendation(int i);

    public static native String getAccelRecommendationData(int i, int i2);

    public static native int getAccelerationStatus(int i);

    public static native int getInt(int i, String str, String str2);

    public static native String getLastAuthServerTime(int i);

    public static native boolean getProxyIsStart(int i);

    public static native boolean getSDKUDPIsProxy(int i);

    public static native int getScriptBit(int i);

    public static native String getString(int i, String str, String str2);

    public static native String getVIPValidTime(int i);

    public static native String getWebUIUrl(int i, int i2);

    public static native int getXunYouServiceRemindType(int i);

    public static native void httpResponse(int i, int i2, String str, String str2, String str3);

    public static native boolean init(int i, String str, int i2, int i3, byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4);

    public static native void injectPCode(int i, byte[] bArr);

    public static native boolean isNodeDetected(int i, int i2);

    public static native int networkCheck(int i);

    public static native void onAccelRecommendationResult(int i, int i2, boolean z);

    public static native void onListDataResult(int i, String str);

    public static native void onLoadDataResult(int i, String str);

    public static native void onUDPDelay(int i, int i2);

    public static native void processEvent();

    public static native void proxyLoop(int i, boolean z);

    public static native void qosPrepareResult(int i, String str, String str2);

    public static native void queryActivities(int i, int i2);

    public static native int queryTrialNotice(int i, int i2);

    public static native void refreshUserState(int i, int i2);

    public static native void replyTrialNotice(int i, int i2);

    public static native void requestIPRegionResult(int i, String str);

    public static native void requestMobileFDResult(int i, int i2, int i3, boolean z);

    public static native void setActivityExposure(int i, int i2, String str);

    public static native void setInt(int i, byte[] bArr, int i2);

    public static native void setRecommendationGameIP(int i, byte[] bArr, int i2);

    public static native void setString(int i, byte[] bArr, byte[] bArr2);

    public static native void setUDPEchoPort(int i, int i2);

    public static native void setUserToken(int i, int i2, byte[] bArr, byte[] bArr2, byte[] bArr3, int i3);

    public static native void startNodeDetect(int i, int i2, int i3, String str);

    public static native boolean startProxy(int i);

    private static native boolean startVPN(int i, int i2);

    public static native void stopProxy(int i);

    private static native void stopVPN(int i);

    private VPNJni() {
    }

    public static void loadLibrary(JniCallback jniCallback, String str) {
        a = jniCallback;
        if (!TextUtils.isEmpty(str)) {
            synchronized (VPNJni.class) {
                if (!b) {
                    b = true;
                    System.loadLibrary(str);
                }
            }
        }
    }

    public static JniCallback setCallback(JniCallback jniCallback) {
        JniCallback jniCallback2 = a;
        a = jniCallback;
        return jniCallback2;
    }

    public static JniCallback getCallback() {
        return a;
    }

    public static boolean doStartVPN(int i) {
        return startVPN(0, i);
    }

    public static void doStopVPN() {
        stopVPN(0);
    }

    public static void onProxyActive(int i, boolean z) {
        a.a(z);
    }

    public static void onLuaError(int i, String str) {
        a.b(str);
    }

    public static void requestMobileFD(int i, String str, int i2) {
        a.a(i);
    }

    public static void onReleaseMobileFD(int i, int i2) {
    }

    public static void getISP(int i, int i2) {
        a.a(i, i2);
    }

    public static void onReportEvent(int i, String str) {
        a.a(str);
    }

    public static void onAccelInfoUpload(int i, String str, String str2, String str3) {
        if (TextUtils.isEmpty(str2)) {
            Log.w("SubaoProxy", "onAccelInfoUpload, userId is empty");
        } else if (TextUtils.isEmpty(str)) {
            Log.w("SubaoProxy", "onAccelInfoUpload, content is empty");
        } else {
            a.a(str, str2, str3);
        }
    }

    public static void onListData(int i, String str) {
        a.a(i, str);
    }

    public static void onDeleteData(int i, String str, String str2) {
        a.a(i, str, str2);
    }

    public static void onCacheData(int i, String str, String str2, String str3) {
        a.a(i, str, str2, str3);
    }

    public static void onCacheDataForAppend(int i, String str, String str2, String str3) {
        a.b(i, str, str2, str3);
    }

    public static void onLoadData(int i, String str, String str2) {
        a.b(i, str, str2);
    }

    public static void requestBeaconCounter(int i, String str) {
        a.b(i, str);
    }

    public static int protectFD(int i) {
        return a.b(i);
    }

    public static void onCouponExchange(int i, String str, String str2, String str3) {
        a.b(str, str2, str3);
    }

    public static void createOrders(int i, String str, int i2, String str2) {
        a.a(i, str, i2, str2);
    }

    public static void httpRequest(int i, int i2, String str, String str2, String str3, String str4) {
        a.a(i, i2, str, str2, str3, str4);
    }

    public static void qosPrepare(int i, String str, String str2, String str3, int i2) {
        a.a(i, str, str2, str3, i2);
    }

    public static void requestDomainNameResolve(int i, String str) {
        a.c(i, str);
    }

    public static void onNodeDetectResult(int i, int i2, int i3, boolean z) {
    }

    public static void onUserAuthResult(int i, int i2, int i3, String str) {
        a.a(i2, i3, str);
    }

    public static void onEvent(int i, String str, String str2) {
        Log.d("SubaoGame", String.format("onEvent('%s', '%s')", new Object[]{str, str2}));
    }

    public static void requestIPRegion(int i, String str) {
        a.d(i, str);
    }

    public static void onDetectTimeDelay(int i, int i2, String str) {
        a.e(i2, str);
    }

    public static void onQueryActivitiesResult(int i, int i2, int i3, boolean z, String str) {
        a.a(i, i2, i3, z, str);
    }

    public static void onSetActivityExposure(int i, int i2, int i3) {
        a.a(i, i2, i3);
    }
}
