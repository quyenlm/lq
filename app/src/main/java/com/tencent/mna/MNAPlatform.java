package com.tencent.mna;

import android.app.Activity;
import android.content.Context;
import com.tencent.midas.oversea.api.UnityPayHelper;
import com.tencent.mna.b.a.c.g;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MNAPlatform {
    private static final String MNA_PLATFORM_IMPL = "com.tencent.mna.MNAPlatformImpl";
    private static GHObserver sGhObserver = null;
    private static MNAObserver sMnaObserver = null;
    private static NetworkBindingListener sNetworkBindingListener = null;
    private static RouterObserver sRouterObserver = null;

    public static void MNAInit(String str, boolean z, int i, boolean z2, boolean z3, String str2) {
        MNAPlatformImpl.MNAInit(str, z, i, z2, z3, str2);
    }

    public static void MNAInit(Context context, String str, boolean z, int i, boolean z2, boolean z3, String str2) {
        try {
            if (MNAPlatformImpl.loadSdk(context)) {
                Class<?> cls = Class.forName(MNA_PLATFORM_IMPL);
                if (sMnaObserver != null) {
                    MNASetObserver((MNAObserver) new a().a(sMnaObserver));
                    sMnaObserver = null;
                }
                if (sGhObserver != null) {
                    MNASetGHObserver((GHObserver) new a().a(sGhObserver));
                    sGhObserver = null;
                }
                if (sNetworkBindingListener != null) {
                    MNASetNetworkBindingListener((NetworkBindingListener) new a().a(sNetworkBindingListener));
                    sNetworkBindingListener = null;
                }
                if (sRouterObserver != null) {
                    MNASetRouterObserver((RouterObserver) new a().a(sRouterObserver));
                    sRouterObserver = null;
                }
                Method declaredMethod = cls.getDeclaredMethod("MNAInit", new Class[]{Context.class, String.class, Boolean.TYPE, Integer.TYPE, Boolean.TYPE, Boolean.TYPE, String.class});
                declaredMethod.setAccessible(true);
                declaredMethod.invoke((Object) null, new Object[]{context, str, Boolean.valueOf(z), Integer.valueOf(i), Boolean.valueOf(z2), Boolean.valueOf(z3), str2});
                return;
            }
            MNAPlatformImpl.MNAInit(context, str, z, i, z2, z3, str2);
        } catch (Throwable th) {
        }
    }

    public static void MNASetObserver(MNAObserver mNAObserver) {
        try {
            if (MNAPlatformImpl.isLoaded()) {
                Method declaredMethod = Class.forName(MNA_PLATFORM_IMPL).getDeclaredMethod("MNASetObserver", new Class[]{MNAObserver.class});
                declaredMethod.setAccessible(true);
                declaredMethod.invoke((Object) null, new Object[]{mNAObserver});
                return;
            }
            MNAPlatformImpl.MNASetObserver(mNAObserver);
            sMnaObserver = mNAObserver;
        } catch (Throwable th) {
        }
    }

    public static void MNASetUserName(int i, String str) {
        try {
            if (MNAPlatformImpl.isLoaded()) {
                Method declaredMethod = Class.forName(MNA_PLATFORM_IMPL).getDeclaredMethod("MNASetUserName", new Class[]{Integer.TYPE, String.class});
                declaredMethod.setAccessible(true);
                declaredMethod.invoke((Object) null, new Object[]{Integer.valueOf(i), str});
                return;
            }
            MNAPlatformImpl.MNASetUserName(i, str);
        } catch (Throwable th) {
        }
    }

    public static void MNASetZoneId(int i) {
        try {
            if (MNAPlatformImpl.isLoaded()) {
                Method declaredMethod = Class.forName(MNA_PLATFORM_IMPL).getDeclaredMethod("MNASetZoneId", new Class[]{Integer.TYPE});
                declaredMethod.setAccessible(true);
                declaredMethod.invoke((Object) null, new Object[]{Integer.valueOf(i)});
                return;
            }
            MNAPlatformImpl.MNASetZoneId(i);
        } catch (Throwable th) {
        }
    }

    public static void MNASetObserver(long j, long j2) {
        try {
            if (MNAPlatformImpl.isLoaded()) {
                Method declaredMethod = Class.forName(MNA_PLATFORM_IMPL).getDeclaredMethod("MNASetObserver", new Class[]{Long.TYPE, Long.TYPE});
                declaredMethod.setAccessible(true);
                declaredMethod.invoke((Object) null, new Object[]{Long.valueOf(j), Long.valueOf(j2)});
                return;
            }
            MNAPlatformImpl.MNASetObserver(j, j2);
        } catch (Throwable th) {
        }
    }

    public static void MNAStartSpeed(String str, int i, int i2, String str2, int i3, int i4, int i5, String str3) {
        try {
            if (MNAPlatformImpl.isLoaded()) {
                Method declaredMethod = Class.forName(MNA_PLATFORM_IMPL).getDeclaredMethod("MNAStartSpeed", new Class[]{String.class, Integer.TYPE, Integer.TYPE, String.class, Integer.TYPE, Integer.TYPE, Integer.TYPE, String.class});
                declaredMethod.setAccessible(true);
                declaredMethod.invoke((Object) null, new Object[]{str, Integer.valueOf(i), Integer.valueOf(i2), str2, Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(i5), str3});
                return;
            }
            MNAPlatformImpl.MNAStartSpeed(str, i, i2, str2, i3, i4, i5, str3);
        } catch (Throwable th) {
        }
    }

    public static void MNAEnterMapLoading() {
        try {
            if (MNAPlatformImpl.isLoaded()) {
                Method declaredMethod = Class.forName(MNA_PLATFORM_IMPL).getDeclaredMethod("MNAEnterMapLoading", new Class[0]);
                declaredMethod.setAccessible(true);
                declaredMethod.invoke((Object) null, new Object[0]);
                return;
            }
            MNAPlatformImpl.MNAEnterMapLoading();
        } catch (Throwable th) {
        }
    }

    public static void MNAStopMNA(String str, int i) {
        try {
            if (MNAPlatformImpl.isLoaded()) {
                Method declaredMethod = Class.forName(MNA_PLATFORM_IMPL).getDeclaredMethod("MNAStopMNA", new Class[]{String.class, Integer.TYPE});
                declaredMethod.setAccessible(true);
                declaredMethod.invoke((Object) null, new Object[]{str, Integer.valueOf(i)});
                return;
            }
            MNAPlatformImpl.MNAStopMNA(str, i);
        } catch (Throwable th) {
        }
    }

    public static void MNAEndSpeed(String str, int i, String str2) {
        try {
            if (MNAPlatformImpl.isLoaded()) {
                Method declaredMethod = Class.forName(MNA_PLATFORM_IMPL).getDeclaredMethod("MNAEndSpeed", new Class[]{String.class, Integer.TYPE, String.class});
                declaredMethod.setAccessible(true);
                declaredMethod.invoke((Object) null, new Object[]{str, Integer.valueOf(i), str2});
                return;
            }
            MNAPlatformImpl.MNAEndSpeed(str, i, str2);
        } catch (Throwable th) {
        }
    }

    public static void MNAEndSpeed(String str, int i) {
        try {
            if (MNAPlatformImpl.isLoaded()) {
                Method declaredMethod = Class.forName(MNA_PLATFORM_IMPL).getDeclaredMethod("MNAEndSpeed", new Class[]{String.class, Integer.TYPE});
                declaredMethod.setAccessible(true);
                declaredMethod.invoke((Object) null, new Object[]{str, Integer.valueOf(i)});
                return;
            }
            MNAPlatformImpl.MNAEndSpeed(str, i);
        } catch (Throwable th) {
        }
    }

    public static g MNAGetSpeedInfo(String str, int i) {
        try {
            if (!MNAPlatformImpl.isLoaded()) {
                return MNAPlatformImpl.MNAGetSpeedInfo(str, i);
            }
            Method declaredMethod = Class.forName(MNA_PLATFORM_IMPL).getDeclaredMethod("MNAGetSpeedInfo", new Class[]{String.class, Integer.TYPE});
            declaredMethod.setAccessible(true);
            return (g) declaredMethod.invoke((Object) null, new Object[]{str, Integer.valueOf(i)});
        } catch (Throwable th) {
            return null;
        }
    }

    public static int MNAGetSpeedFlag() {
        try {
            if (!MNAPlatformImpl.isLoaded()) {
                return MNAPlatformImpl.MNAGetSpeedFlag();
            }
            Method declaredMethod = Class.forName(MNA_PLATFORM_IMPL).getDeclaredMethod("MNAGetSpeedFlag", new Class[0]);
            declaredMethod.setAccessible(true);
            return ((Integer) declaredMethod.invoke((Object) null, new Object[0])).intValue();
        } catch (Throwable th) {
            return -100;
        }
    }

    public static void MNAGoBack() {
        try {
            if (MNAPlatformImpl.isLoaded()) {
                Method declaredMethod = Class.forName(MNA_PLATFORM_IMPL).getDeclaredMethod("MNAGoBack", new Class[0]);
                declaredMethod.setAccessible(true);
                declaredMethod.invoke((Object) null, new Object[0]);
                return;
            }
            MNAPlatformImpl.MNAGoBack();
        } catch (Throwable th) {
        }
    }

    public static void MNAGoFront() {
        try {
            if (MNAPlatformImpl.isLoaded()) {
                Method declaredMethod = Class.forName(MNA_PLATFORM_IMPL).getDeclaredMethod("MNAGoFront", new Class[0]);
                declaredMethod.setAccessible(true);
                declaredMethod.invoke((Object) null, new Object[0]);
                return;
            }
            MNAPlatformImpl.MNAGoFront();
        } catch (Throwable th) {
        }
    }

    public static void MNAAddData(String str, String str2, String str3) {
        try {
            if (MNAPlatformImpl.isLoaded()) {
                Method declaredMethod = Class.forName(MNA_PLATFORM_IMPL).getDeclaredMethod("MNAAddData", new Class[]{String.class, String.class, String.class});
                declaredMethod.setAccessible(true);
                declaredMethod.invoke((Object) null, new Object[]{str, str2, str3});
                return;
            }
            MNAPlatformImpl.MNAAddData(str, str2, str3);
        } catch (Throwable th) {
        }
    }

    public static void MNASetGameIp(String str) {
        try {
            if (MNAPlatformImpl.isLoaded()) {
                Method declaredMethod = Class.forName(MNA_PLATFORM_IMPL).getDeclaredMethod("MNASetGameIp", new Class[]{String.class});
                declaredMethod.setAccessible(true);
                declaredMethod.invoke((Object) null, new Object[]{str});
                return;
            }
            MNAPlatformImpl.MNASetGameIp(str);
        } catch (Throwable th) {
        }
    }

    public static void MNASetNetworkBindingListener(NetworkBindingListener networkBindingListener) {
        try {
            if (MNAPlatformImpl.isLoaded()) {
                Method declaredMethod = Class.forName(MNA_PLATFORM_IMPL).getDeclaredMethod("MNASetNetworkBindingListener", new Class[]{NetworkBindingListener.class});
                declaredMethod.setAccessible(true);
                declaredMethod.invoke((Object) null, new Object[]{networkBindingListener});
                return;
            }
            MNAPlatformImpl.MNASetNetworkBindingListener(networkBindingListener);
            sNetworkBindingListener = networkBindingListener;
        } catch (Throwable th) {
        }
    }

    public static void MNAToggleNetworkBinding(int i, boolean z) {
        try {
            if (MNAPlatformImpl.isLoaded()) {
                Method declaredMethod = Class.forName(MNA_PLATFORM_IMPL).getDeclaredMethod("MNAToggleNetworkBinding", new Class[]{Integer.TYPE, Boolean.TYPE});
                declaredMethod.setAccessible(true);
                declaredMethod.invoke((Object) null, new Object[]{Integer.valueOf(i), Boolean.valueOf(z)});
                return;
            }
            MNAPlatformImpl.MNAToggleNetworkBinding(i, z);
        } catch (Throwable th) {
        }
    }

    public static int MNAIsQOSWork() {
        try {
            if (!MNAPlatformImpl.isLoaded()) {
                return MNAPlatformImpl.MNAIsQOSWork();
            }
            Method declaredMethod = Class.forName(MNA_PLATFORM_IMPL).getDeclaredMethod("MNAIsQOSWork", new Class[0]);
            declaredMethod.setAccessible(true);
            return ((Integer) declaredMethod.invoke((Object) null, new Object[0])).intValue();
        } catch (Throwable th) {
            return 0;
        }
    }

    public static String MNAGetQosSid() {
        try {
            if (!MNAPlatformImpl.isLoaded()) {
                return MNAPlatformImpl.MNAGetQosSid();
            }
            Method declaredMethod = Class.forName(MNA_PLATFORM_IMPL).getDeclaredMethod("MNAGetQosSid", new Class[0]);
            declaredMethod.setAccessible(true);
            return (String) declaredMethod.invoke((Object) null, new Object[0]);
        } catch (Throwable th) {
            return UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR;
        }
    }

    public static void MNAQueryKartin(String str) {
        try {
            if (MNAPlatformImpl.isLoaded()) {
                Method declaredMethod = Class.forName(MNA_PLATFORM_IMPL).getDeclaredMethod("MNAQueryKartin", new Class[]{String.class});
                declaredMethod.setAccessible(true);
                declaredMethod.invoke((Object) null, new Object[]{str});
                return;
            }
            MNAPlatformImpl.MNAQueryKartin(str);
        } catch (Throwable th) {
        }
    }

    public static int MNAStartWifiActivity(String str) {
        try {
            if (!MNAPlatformImpl.isLoaded()) {
                return MNAPlatformImpl.MNAStartWifiActivity(str);
            }
            Method declaredMethod = Class.forName(MNA_PLATFORM_IMPL).getDeclaredMethod("MNAStartWifiActivity", new Class[]{String.class});
            declaredMethod.setAccessible(true);
            return ((Integer) declaredMethod.invoke((Object) null, new Object[]{str})).intValue();
        } catch (Throwable th) {
            return -2;
        }
    }

    public static int MNAStartWifiActivity(Activity activity, String str) {
        try {
            if (!MNAPlatformImpl.isLoaded()) {
                return MNAPlatformImpl.MNAStartWifiActivity(activity, str);
            }
            Method declaredMethod = Class.forName(MNA_PLATFORM_IMPL).getDeclaredMethod("MNAStartWifiActivity", new Class[]{Activity.class, String.class});
            declaredMethod.setAccessible(true);
            return ((Integer) declaredMethod.invoke((Object) null, new Object[]{activity, str})).intValue();
        } catch (Throwable th) {
            return -2;
        }
    }

    public static void MNASetGameMode(boolean z) {
        try {
            if (MNAPlatformImpl.isLoaded()) {
                Method declaredMethod = Class.forName(MNA_PLATFORM_IMPL).getDeclaredMethod("MNASetGameMode", new Class[]{Boolean.TYPE});
                declaredMethod.setAccessible(true);
                declaredMethod.invoke((Object) null, new Object[]{Boolean.valueOf(z)});
                return;
            }
            MNAPlatformImpl.MNASetGameMode(z);
        } catch (Throwable th) {
        }
    }

    public static void MNAPauseBattle() {
        try {
            if (MNAPlatformImpl.isLoaded()) {
                Method declaredMethod = Class.forName(MNA_PLATFORM_IMPL).getDeclaredMethod("MNAPauseBattle", new Class[0]);
                declaredMethod.setAccessible(true);
                declaredMethod.invoke((Object) null, new Object[0]);
                return;
            }
            MNAPlatformImpl.MNAPauseBattle();
        } catch (Throwable th) {
        }
    }

    public static void MNAResumeBattle() {
        try {
            if (MNAPlatformImpl.isLoaded()) {
                Method declaredMethod = Class.forName(MNA_PLATFORM_IMPL).getDeclaredMethod("MNAResumeBattle", new Class[0]);
                declaredMethod.setAccessible(true);
                declaredMethod.invoke((Object) null, new Object[0]);
                return;
            }
            MNAPlatformImpl.MNAResumeBattle();
        } catch (Throwable th) {
        }
    }

    public static void MNASetGHObserver(GHObserver gHObserver) {
        try {
            if (MNAPlatformImpl.isLoaded()) {
                Method declaredMethod = Class.forName(MNA_PLATFORM_IMPL).getDeclaredMethod("MNASetGHObserver", new Class[]{GHObserver.class});
                declaredMethod.setAccessible(true);
                declaredMethod.invoke((Object) null, new Object[]{gHObserver});
                return;
            }
            MNAPlatformImpl.MNASetGHObserver(gHObserver);
            sGhObserver = gHObserver;
        } catch (Throwable th) {
        }
    }

    public static int MNAGetSOVersion() {
        try {
            if (!MNAPlatformImpl.isLoaded()) {
                return MNAPlatformImpl.MNAGetSOVersion();
            }
            Method declaredMethod = Class.forName(MNA_PLATFORM_IMPL).getDeclaredMethod("MNAGetSOVersion", new Class[0]);
            declaredMethod.setAccessible(true);
            return ((Integer) declaredMethod.invoke((Object) null, new Object[0])).intValue();
        } catch (Throwable th) {
            return 0;
        }
    }

    public static int MNAGetBatteryLevel() {
        try {
            if (!MNAPlatformImpl.isLoaded()) {
                return MNAPlatformImpl.MNAGetBatteryLevel();
            }
            Method declaredMethod = Class.forName(MNA_PLATFORM_IMPL).getDeclaredMethod("MNAGetBatteryLevel", new Class[0]);
            declaredMethod.setAccessible(true);
            return ((Integer) declaredMethod.invoke((Object) null, new Object[0])).intValue();
        } catch (Throwable th) {
            return -1;
        }
    }

    public static int[] MNAGetBatteryLevelAndCharging() {
        try {
            if (!MNAPlatformImpl.isLoaded()) {
                return MNAPlatformImpl.MNAGetBatteryLevelAndCharging();
            }
            Method declaredMethod = Class.forName(MNA_PLATFORM_IMPL).getDeclaredMethod("MNAGetBatteryLevelAndCharging", new Class[0]);
            declaredMethod.setAccessible(true);
            return (int[]) declaredMethod.invoke((Object) null, new Object[0]);
        } catch (Throwable th) {
            return new int[]{-1, 0};
        }
    }

    public static void SetBandwidth(String str, String str2, String str3, String str4) {
        try {
            if (MNAPlatformImpl.isLoaded()) {
                Method declaredMethod = Class.forName(MNA_PLATFORM_IMPL).getDeclaredMethod("SetBandwidth", new Class[]{String.class, String.class, String.class, String.class});
                declaredMethod.setAccessible(true);
                declaredMethod.invoke((Object) null, new Object[]{str, str2, str3, str4});
                return;
            }
            MNAPlatformImpl.SetBandwidth(str, str2, str3, str4);
        } catch (Throwable th) {
        }
    }

    public static void MNASetNetworkObserver(NetworkObserver networkObserver) {
        try {
            if (MNAPlatformImpl.isLoaded()) {
                Method declaredMethod = Class.forName(MNA_PLATFORM_IMPL).getDeclaredMethod("MNASetNetworkObserver", new Class[]{NetworkObserver.class});
                declaredMethod.setAccessible(true);
                declaredMethod.invoke((Object) null, new Object[]{networkObserver});
                return;
            }
            MNAPlatformImpl.MNASetNetworkObserver(networkObserver);
        } catch (Throwable th) {
        }
    }

    public static void MNAQueryNetwork(boolean z, boolean z2, boolean z3) {
        try {
            if (MNAPlatformImpl.isLoaded()) {
                Method declaredMethod = Class.forName(MNA_PLATFORM_IMPL).getDeclaredMethod("MNAQueryNetwork", new Class[]{Boolean.TYPE, Boolean.TYPE, Boolean.TYPE});
                declaredMethod.setAccessible(true);
                declaredMethod.invoke((Object) null, new Object[]{Boolean.valueOf(z), Boolean.valueOf(z2), Boolean.valueOf(z3)});
                return;
            }
            MNAPlatformImpl.MNAQueryNetwork(z, z2, z3);
        } catch (Throwable th) {
        }
    }

    public static void MNASetRouterObserver(RouterObserver routerObserver) {
        try {
            if (MNAPlatformImpl.isLoaded()) {
                Method declaredMethod = Class.forName(MNA_PLATFORM_IMPL).getDeclaredMethod("MNASetRouterObserver", new Class[]{RouterObserver.class});
                declaredMethod.setAccessible(true);
                declaredMethod.invoke((Object) null, new Object[]{routerObserver});
                return;
            }
            MNAPlatformImpl.MNASetRouterObserver(routerObserver);
            sRouterObserver = routerObserver;
        } catch (Throwable th) {
        }
    }

    public static void MNAQueryRouter(String str) {
        try {
            if (MNAPlatformImpl.isLoaded()) {
                Method declaredMethod = Class.forName(MNA_PLATFORM_IMPL).getDeclaredMethod("MNAQueryRouter", new Class[]{String.class});
                declaredMethod.setAccessible(true);
                declaredMethod.invoke((Object) null, new Object[]{str});
                return;
            }
            MNAPlatformImpl.MNAQueryRouter(str);
        } catch (Throwable th) {
        }
    }

    public static void MNASetGameDelay(int i) {
        try {
            if (MNAPlatformImpl.isLoaded()) {
                Method declaredMethod = Class.forName(MNA_PLATFORM_IMPL).getDeclaredMethod("MNASetGameDelay", new Class[]{Integer.TYPE});
                declaredMethod.setAccessible(true);
                declaredMethod.invoke((Object) null, new Object[]{Integer.valueOf(i)});
                return;
            }
            MNAPlatformImpl.MNASetGameDelay(i);
        } catch (Throwable th) {
        }
    }

    private static class a implements InvocationHandler {
        private Object a;

        private a() {
        }

        /* access modifiers changed from: package-private */
        public Object a(Object obj) {
            this.a = obj;
            Class<?> cls = obj.getClass();
            if (cls != null) {
                return Proxy.newProxyInstance(cls.getClassLoader(), cls.getInterfaces(), this);
            }
            throw new NullPointerException("target to bind is null");
        }

        public Object invoke(Object obj, Method method, Object[] objArr) {
            return method.invoke(this.a, objArr);
        }
    }
}
