package com.tencent.mna;

import android.app.Activity;
import android.content.Context;
import android.util.Base64;
import android.util.Log;
import com.amazonaws.services.s3.internal.Constants;
import com.tencent.imsdk.sns.base.IMFriendInfoExViber;
import com.tencent.midas.oversea.api.UnityPayHelper;
import com.tencent.mna.a.b;
import com.tencent.mna.b.a.c.g;
import com.tencent.mna.b.e.a;
import com.tencent.mna.b.e.c;
import com.tencent.mna.base.b.a;
import com.tencent.mna.base.c.f;
import com.tencent.mna.base.jni.e;
import com.tencent.mna.base.utils.d;
import com.tencent.mna.base.utils.h;
import com.tencent.mna.base.utils.i;
import com.tencent.mna.base.utils.k;
import com.tencent.mna.base.utils.m;
import com.tencent.mna.base.utils.o;
import com.tencent.mna.base.utils.q;

public class MNAPlatformImpl {
    private static volatile boolean sIsLoaded = false;

    public static boolean loadSdk(Context context) {
        if (sIsLoaded) {
            return true;
        }
        boolean a = a.a(context);
        if (a) {
            sIsLoaded = true;
        }
        return a;
    }

    public static boolean isLoaded() {
        return sIsLoaded;
    }

    public static void MNAInit(String str, boolean z, int i, boolean z2, boolean z3, String str2) {
        try {
            h.b("MNAInit called qqappid:" + str + ",debug:" + z + ",zoneid:" + i + ",env:" + z2 + ",useBattery:" + z3 + ",tcloudkey:" + str2);
            if (b.a()) {
                h.b("MNAInit succeed");
                return;
            }
            final Activity b = d.b();
            if (b != null) {
                final String str3 = str;
                final boolean z4 = z;
                final int i2 = i;
                final boolean z5 = z2;
                final boolean z6 = z3;
                final String str4 = str2;
                b.runOnUiThread(new Runnable() {
                    public void run() {
                        MNAPlatformImpl.MNAInit(b, str3, z4, i2, z5, z6, str4);
                    }
                });
                return;
            }
            h.b("MNAInit fail");
        } catch (Throwable th) {
        }
    }

    public static void MNAInit(Context context, String str, boolean z, int i, boolean z2, boolean z3, String str2) {
        try {
            Log.i("TClient", "init: 5.5.2 | " + str + " | " + z + " | " + i + " | " + z2 + " | " + z3 + " | " + str2);
            b.a(context.getApplicationContext(), str, z, i, z2, z3, str2);
            if (b.a()) {
                h.b("MNAInit succeed");
            } else {
                h.b("MNAInit fail");
            }
        } catch (Throwable th) {
            h.d("MNAInit fail, exception:" + th.getMessage());
        }
    }

    public static void MNASetObserver(MNAObserver mNAObserver) {
        if (mNAObserver == null) {
            try {
                h.b("MNASetObserver called: null");
            } catch (Throwable th) {
                return;
            }
        } else {
            h.b("MNASetObserver called");
        }
        b.a(mNAObserver);
    }

    public static void MNASetUserName(int i, String str) {
        if (str == null) {
            str = Constants.NULL_VERSION_ID;
        }
        try {
            Log.i("TClient", "base: " + i + "_" + Base64.encodeToString(str.getBytes("UTF-8"), 0) + "key");
            b.f = str;
            b.g = i;
            com.tencent.mna.b.f.a.a(str);
        } catch (Throwable th) {
        }
    }

    public static void MNASetZoneId(int i) {
        try {
            h.b("MNASetZoneId called zoneid:" + i);
            b.h = i;
        } catch (Throwable th) {
        }
    }

    public static void MNASetObserver(long j, long j2) {
        try {
            h.b("MNASetObserver called startSpeedPtr:" + j + ",kartinPtr:" + j2);
            b.a(j, j2);
        } catch (Throwable th) {
        }
    }

    public static void MNAStartSpeed(String str, int i, int i2, String str2, int i3, int i4, int i5, String str3) {
        try {
            h.b("MNAStartSpeed called domain:" + str + ",vport:" + i + ",htype:" + i2 + ",hookModules:" + str2 + ",zoneid:" + i3 + ",stopMNA:" + i4 + ",start_timeout:" + i5 + ",pvpid:" + str3);
            if (b.a()) {
                b.a = str3;
                b.h = i3;
                com.tencent.mna.b.a.b.a(str, i, i2, str2, i3, i4, i5, str3);
                return;
            }
            h.b("MNAStartSpeed fail, not init");
        } catch (Throwable th) {
        }
    }

    public static void MNAEnterMapLoading() {
        try {
            h.b("MNAEnterMapLoading called");
            if (b.a()) {
                com.tencent.mna.b.a.b.b();
            } else {
                h.b("MNAEnterMapLoading fail");
            }
        } catch (Throwable th) {
        }
    }

    public static void MNAStopMNA(String str, int i) {
        try {
            h.b("MNAStopMNA called domain:" + str + ",vport:" + i);
            if (b.a()) {
                com.tencent.mna.b.a.b.a(str, i);
            } else {
                h.b("MNAStopMNA fail");
            }
        } catch (Throwable th) {
        }
    }

    public static void MNAEndSpeed(String str, int i, String str2) {
        try {
            h.b("MNAEndSpeed called domain:" + str + ",vport:" + i + ",extrainfo:" + str2);
            if (b.a()) {
                com.tencent.mna.b.a.b.a(str, i, str2);
            } else {
                h.b("MNAEndSpeed fail");
            }
        } catch (Throwable th) {
        }
    }

    public static void MNAEndSpeed(String str, int i) {
        try {
            MNAEndSpeed(str, i, "");
        } catch (Throwable th) {
        }
    }

    public static g MNAGetSpeedInfo(String str, int i) {
        g gVar = null;
        try {
            h.b("MNAGetSpeedInfo called vip:" + str + ",vport:" + i);
            if (b.a()) {
                gVar = com.tencent.mna.b.a.b.b(str, i);
            }
        } catch (Throwable th) {
        }
        h.b("MNAGetSpeedInfo return:" + gVar);
        return gVar;
    }

    public static int MNAGetSpeedFlag() {
        int i = -100;
        try {
            if (b.a()) {
                i = com.tencent.mna.b.a.b.c();
            }
        } catch (Throwable th) {
        }
        h.b("MNAGetSpeedInfo return:" + i);
        return i;
    }

    public static void MNAGoBack() {
        try {
            if (b.a()) {
                com.tencent.mna.b.a.b.e();
                h.b("MNAGoBack succeed");
                return;
            }
            h.b("MNAGoBack fail");
        } catch (Throwable th) {
        }
    }

    public static void MNAGoFront() {
        try {
            if (b.a()) {
                com.tencent.mna.b.a.b.f();
                h.b("MNAGoFront succeed");
                return;
            }
            h.b("MNAGoFront fail");
        } catch (Throwable th) {
        }
    }

    public static void MNAAddData(String str, String str2, String str3) {
        try {
            h.b("MNAAddData called fps:" + str + ",move:" + str2 + ",click:" + str3);
            if (!b.a() || !com.tencent.mna.b.a.b.a(str, str2, str3)) {
                h.b("MNAAddData fail");
            } else {
                h.b("MNAAddData succeed");
            }
        } catch (Throwable th) {
        }
    }

    public static void MNASetGameIp(String str) {
        try {
            h.b("MNASetGameIp called gameIp:" + str);
            if (b.a()) {
                com.tencent.mna.b.a.b.a(str);
            } else {
                h.b("MNASetGameIp fail");
            }
        } catch (Throwable th) {
        }
    }

    public static void MNASetNetworkBindingListener(NetworkBindingListener networkBindingListener) {
        try {
            h.b("MNASetNetworkBindingListener called");
            b.a(networkBindingListener);
        } catch (Throwable th) {
        }
    }

    public static void MNAToggleNetworkBinding(int i, boolean z) {
        h.b("MNAToggleNetworkBinding called bufferScore:" + i + ",playerSetting:" + z);
        if (b.a()) {
            com.tencent.mna.b.a.b.a(i, z);
        } else {
            h.b("MNAToggleNetworkBinding fail");
        }
    }

    public static int MNAIsQOSWork() {
        int i = 0;
        try {
            h.b("MNAIsQOSWork called");
            if (b.a()) {
                i = com.tencent.mna.b.f.a.a;
            }
        } catch (Throwable th) {
        }
        h.b("MNAIsQOSWork return:" + i);
        return i;
    }

    public static String MNAGetQosSid() {
        try {
            h.b("MNAGetQosSid called");
            String str = com.tencent.mna.b.f.a.e;
            h.b("MNAGetQosSid return:" + str);
            return str;
        } catch (Throwable th) {
            return UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR;
        }
    }

    public static void MNAQueryKartin(String str) {
        try {
            h.b("MNAQueryKartin called tag:" + str);
            if (b.a()) {
                com.tencent.mna.b.d.b.a(str);
            } else {
                h.b("MNAQueryKartin fail");
            }
        } catch (Throwable th) {
        }
    }

    public static int MNAStartWifiActivity(String str) {
        int i = -2;
        try {
            h.b("MNAStartWifiActivity called tag:" + str);
            if (b.a()) {
                i = o.a();
            } else {
                h.b("MNAStartWifiActivity fail, not init");
            }
            reportWifiEvent(str, i);
        } catch (Throwable th) {
        }
        return i;
    }

    public static int MNAStartWifiActivity(Activity activity, String str) {
        int i = -2;
        try {
            h.b("MNAStartWifiActivity called activity:" + activity + ",tag:" + str);
            if (b.a()) {
                i = o.a(activity);
            } else {
                h.b("MNAStartWifiActivity(Activity;String) fail, not init");
            }
            reportWifiEvent(str, i);
        } catch (Throwable th) {
        }
        return i;
    }

    public static void MNASetGameMode(boolean z) {
        try {
            h.b("MNASetGameMode called isMatch:" + z);
            com.tencent.mna.b.a.b.b(z);
        } catch (Throwable th) {
        }
    }

    public static void MNAPauseBattle() {
        try {
            h.b("MNAPauseBattle called");
            if (b.a()) {
                com.tencent.mna.b.a.b.g();
                h.b("MNAPauseBattle succeed");
                return;
            }
            h.b("MNAPauseBattle fail");
        } catch (Throwable th) {
        }
    }

    public static void MNAResumeBattle() {
        try {
            h.b("MNAResumeBattle called");
            if (b.a()) {
                com.tencent.mna.b.a.b.h();
                h.b("MNAResumeBattle succeed");
                return;
            }
            h.b("MNAResumeBattle fail");
        } catch (Throwable th) {
        }
    }

    public static void MNASetGHObserver(GHObserver gHObserver) {
        try {
            h.a("MNASetGHObserver called");
            b.a(gHObserver);
        } catch (Throwable th) {
        }
    }

    public static int MNAGetSOVersion() {
        try {
            h.b("MNAGetSOVersion called");
            return e.a();
        } catch (Throwable th) {
            return 0;
        }
    }

    public static int MNAGetBatteryLevel() {
        try {
            h.b("MNAGetBatteryLevel called");
            return m.a(b.g());
        } catch (Throwable th) {
            return -1;
        }
    }

    public static int[] MNAGetBatteryLevelAndCharging() {
        int[] iArr = {-1, 0};
        try {
            h.b("MNAGetBatteryLevelAndCharging called");
            return m.b(b.g());
        } catch (Throwable th) {
            return iArr;
        }
    }

    public static void SetBandwidth(final String str, final String str2, final String str3, final String str4) {
        try {
            h.b("SetBandwidth called bandwidthValuesStr:" + str + ",totalSize:" + str2 + ",elapseTime:" + str3 + ",maxBandValue:" + str4);
            new Thread(new Runnable() {
                public void run() {
                    String str;
                    String c2;
                    try {
                        Activity b2 = d.b();
                        if (k.a((Context) b2) == 4) {
                            str = "1";
                            c2 = q.d(b2);
                        } else {
                            str = "0";
                            c2 = i.c((Context) b2);
                        }
                        b.c = str + ";" + c2 + ";" + str2 + ";" + str3 + ";" + str4 + ";" + str;
                    } catch (Throwable th) {
                        h.a("SetBandwidth exception:" + th.getMessage());
                    }
                }
            }).start();
        } catch (Throwable th) {
        }
    }

    public static void MNASetNetworkObserver(NetworkObserver networkObserver) {
        if (networkObserver == null) {
            try {
                h.b("MNASetNetworkObserver called: null");
            } catch (Throwable th) {
                return;
            }
        } else {
            h.b("MNASetNetworkObserver called");
        }
        b.a(networkObserver);
    }

    public static void MNAQueryNetwork(boolean z, boolean z2, boolean z3) {
        try {
            h.b("MNAQueryNetwork called isGetNetcardInfo:" + z + ",isGetRouterDelay:" + z2);
            if (b.a()) {
                final Context g = b.g();
                final NetworkObserver d = b.d();
                final boolean z4 = z;
                final boolean z5 = z2;
                final boolean z6 = z3;
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            a.C0028a a2 = com.tencent.mna.b.e.a.a(g, z4, z5, z6, com.tencent.mna.b.a.b.l());
                            if (a2 == null) {
                                return;
                            }
                            if (d != null) {
                                d.OnQueryNetworkNotify(a2.a, a2.b, a2.c, a2.d, a2.e, a2.f, a2.g, a2.h, a2.i, a2.j, a2.k, a2.l, a2.m, a2.n, a2.o, a2.p, a2.q, a2.r);
                            } else {
                                e.i("OnQueryNetworkNotify:" + a2.a + "_" + a2.b + "_" + a2.c + "_" + a2.d + "_" + a2.e + "_" + a2.f + "_" + a2.g + "_" + a2.h + "_" + a2.i + "_" + a2.j + "_" + a2.k + "_" + a2.l + "_" + a2.m + "_" + a2.n + "_" + a2.o + "_" + a2.p + "_" + a2.q + "_" + a2.r);
                            }
                        } catch (Throwable th) {
                            h.a("MNAQueryNetwork exception:" + th.getMessage());
                        }
                    }
                }).start();
                return;
            }
            h.b("MNAQueryNetwork failed, not init");
        } catch (Throwable th) {
        }
    }

    public static void MNASetRouterObserver(RouterObserver routerObserver) {
        if (routerObserver == null) {
            try {
                h.b("MNASetRouterObserver called: null");
            } catch (Throwable th) {
                return;
            }
        } else {
            h.b("MNASetRouterObserver called");
        }
        b.a(routerObserver);
    }

    public static void MNAQueryRouter(final String str) {
        try {
            h.b("MNAQueryRouter called tag:" + str);
            if (b.a()) {
                final Context g = b.g();
                final RouterObserver e = b.e();
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            c.a a2 = c.a(g, str, com.tencent.mna.base.a.c.D(), com.tencent.mna.base.a.c.E(), com.tencent.mna.base.a.c.F(), com.tencent.mna.base.a.c.G());
                            if (a2 != null) {
                                if (e != null) {
                                    e.OnQueryRouterNotify(a2.a, a2.b, a2.c, a2.d, a2.e);
                                } else {
                                    e.i("OnQueryRouterNotify:" + a2.a + "_" + a2.b + a2.d + "_" + a2.e);
                                }
                                h.a("OnQueryRouterNotify:" + a2.a + "_" + a2.b + a2.d + "_" + a2.e);
                            }
                        } catch (Throwable th) {
                            h.a("MNAQueryNetwork exception:" + th.getMessage());
                        }
                    }
                }).start();
                return;
            }
            h.b("MNAQueryNetwork failed, not init");
        } catch (Throwable th) {
        }
    }

    public static void MNASetGameDelay(final int i) {
        try {
            if (b.a()) {
                a.e(new Runnable() {
                    public void run() {
                        try {
                            com.tencent.mna.b.e.b.a(i);
                        } catch (Throwable th) {
                        }
                    }
                });
            } else {
                h.a("SetGameDelay fail");
            }
        } catch (Throwable th) {
        }
    }

    private static void reportWifiEvent(final String str, final int i) {
        a.a((Runnable) new Runnable() {
            public void run() {
                f.a(com.tencent.mna.base.c.c.WIFI).a("callret", String.valueOf(i)).a(IMFriendInfoExViber.TAG, "" + str).a("openid", "" + b.f).g();
            }
        });
    }
}
