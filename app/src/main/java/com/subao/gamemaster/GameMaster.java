package com.subao.gamemaster;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.VpnService;
import android.os.ConditionVariable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.JsonWriter;
import android.util.Log;
import android.util.Pair;
import com.facebook.internal.ServerProtocol;
import com.subao.common.a.e;
import com.subao.common.e.ai;
import com.subao.common.e.aj;
import com.subao.common.e.al;
import com.subao.common.e.h;
import com.subao.common.e.n;
import com.subao.common.e.s;
import com.subao.common.i.j;
import com.subao.common.i.l;
import com.subao.common.intf.AccelSwitchListener;
import com.subao.common.intf.AccelerateGameCallback;
import com.subao.common.intf.ActivityExposureCallback;
import com.subao.common.intf.AsyncInitCallback;
import com.subao.common.intf.DetectTimeDelayCallback;
import com.subao.common.intf.GameInformation;
import com.subao.common.intf.InstalledApplicationsSupplier;
import com.subao.common.intf.ProductList;
import com.subao.common.intf.QueryActivitiesCallback;
import com.subao.common.intf.QueryOriginUserStateCallback;
import com.subao.common.intf.QueryProductCallback;
import com.subao.common.intf.QuerySignCouponsCallback;
import com.subao.common.intf.QueryThirdPartyAuthInfoCallback;
import com.subao.common.intf.QueryTwiceTrialStateCallback;
import com.subao.common.intf.RequestBuyCallback;
import com.subao.common.intf.RequestBuyResult;
import com.subao.common.intf.RequestTrialCallback;
import com.subao.common.intf.RequestTwiceTrialCallback;
import com.subao.common.intf.SupportGameLabel;
import com.subao.common.intf.ThirdPartyAuthInfo;
import com.subao.common.intf.UserInfo;
import com.subao.common.intf.UserStateListener;
import com.subao.common.intf.VPNStateListener;
import com.subao.common.intf.XunyouTokenStateListener;
import com.subao.common.intf.XunyouUserStateCallback;
import com.subao.common.j.d;
import com.subao.common.j.o;
import com.subao.common.j.p;
import com.subao.common.k.b;
import com.tencent.imsdk.framework.consts.InnerErrorCode;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class GameMaster {
    public static final int ACCEL_RECOMMENDATION_HAS_NEW_FEATURE = 3;
    public static final int ACCEL_RECOMMENDATION_MOBILE_SWITCH = 6;
    public static final int ACCEL_RECOMMENDATION_NONE = 0;
    public static final int ACCEL_RECOMMENDATION_NOTICE = 1;
    public static final int ACCEL_RECOMMENDATION_PROMPT_MONTH_REPORT = 4;
    public static final int ACCEL_RECOMMENDATION_UNKNOWN = -1;
    public static final int ACCEL_RECOMMENDATION_VIP_EXPIRED = 5;
    public static final int ACCEL_RECOMMENDATION_WIFI = 2;
    public static final String BUILD_TIME = "20190614_161048";
    public static final String COMMIT_ID = "709e38f2bc4e2d59192b3c310aac5c6bf2c48b55";
    public static final long DEFAULT_NODE_DETECT_TIMEOUT = 8000;
    public static final int DEFAULT_UDP_ECHO_PORT = 222;
    public static final int GM_INIT_ALREADY = 1;
    public static final int GM_INIT_FAILURE = -1;
    public static final int GM_INIT_ILLEGAL_ARGUMENT = -4;
    public static final int GM_INIT_NOT_IN_MAIN_THREAD = -3;
    public static final int GM_INIT_NO_PERMISSION = -2;
    public static final int GM_INIT_PENDING = 2;
    public static final int GM_INIT_SUCCESS = 0;
    public static final int HOOK_TYPE_CONNECT = 0;
    public static final int HOOK_TYPE_SENDMSG_RECVMSG = 2;
    public static final int HOOK_TYPE_SENDTO_RECVFROM = 1;
    public static final int HOOK_TYPE_SENDTO_RECVFROM_AND_TCP = 3;
    public static final int NETWORK_CLASS_2G = 2;
    public static final int NETWORK_CLASS_3G = 3;
    public static final int NETWORK_CLASS_4G = 4;
    public static final int NETWORK_CLASS_DISCONNECT = -1;
    public static final int NETWORK_CLASS_UNKNOWN = 0;
    public static final int NETWORK_CLASS_WIFI = 1;
    public static final int PAY_TYPE_ALIPAY = 0;
    public static final int PAY_TYPE_OTHER = 5;
    public static final int PAY_TYPE_PHONE = 4;
    public static final int PAY_TYPE_QQ = 2;
    public static final int PAY_TYPE_UNIONPAY = 3;
    public static final int PAY_TYPE_WECHAT = 1;
    public static final int SDK_EXPIRED = 5;
    public static final int SDK_FREE = 6;
    public static final int SDK_FREE_TRIAL = 2;
    public static final int SDK_IN_USE = 4;
    @Deprecated
    public static final int SDK_MODE_FREE = 3;
    @Deprecated
    public static final int SDK_MODE_GRAY = 1;
    @Deprecated
    public static final int SDK_MODE_OFFICIAL = 2;
    public static final int SDK_NOT_QUALIFIED = 0;
    public static final int SDK_QUALIFIED = 1;
    public static final int SDK_TRIAL_EXPIRED = 3;
    public static final String VERSION_NAME = "5.2.1.1";
    public static final int XUNYOU_SERVICE_REMIND_TYPE_FIRST_FREE_TRIAL = 1;
    public static final int XUNYOU_SERVICE_REMIND_TYPE_FREE_AGAIN = 3;
    public static final int XUNYOU_SERVICE_REMIND_TYPE_NONE = 0;
    public static final int XUNYOU_SERVICE_REMIND_TYPE_VIP_EXPIRED = 8;
    public static final int XUNYOU_SERVICE_REMIND_TYPE_WIFI_ACCEL = 11;
    @SuppressLint({"StaticFieldLeak"})
    static com.subao.common.a.c a;
    @Nullable
    private static i b;

    public interface I1 {
        void a(boolean z);
    }

    public interface I2 {
        void a(int i);
    }

    interface a extends AsyncInitCallback {
        @Nullable
        Context a();
    }

    interface e {
        boolean a(Context context);
    }

    static {
        n.c = n.a.SDK;
    }

    private GameMaster() {
    }

    static com.subao.common.g.a a(int i2) {
        switch (i2) {
            case 0:
                return com.subao.common.g.a.TCP;
            case 1:
            case 2:
                return com.subao.common.g.a.UDP;
            case 3:
                return com.subao.common.g.a.UDP_TCP;
            default:
                return null;
        }
    }

    public static int getJNIBits() {
        com.subao.common.a.c cVar = a;
        if (cVar != null) {
            return cVar.m();
        }
        return 0;
    }

    public static void setU3DObserver(String str, String str2) {
        Log.d("SubaoGame", String.format("setU3DObserver(%s, %s)", new Object[]{str, str2}));
        i a2 = i.a(str, str2);
        synchronized (GameMaster.class) {
            b = a2;
        }
    }

    @Nullable
    static i a() {
        i iVar;
        synchronized (GameMaster.class) {
            iVar = b;
        }
        return iVar;
    }

    public static int init(Context context, int i2, String str, String str2, int i3) {
        return init(context, i2, str, (String) null, str2, i3);
    }

    public static int init(Context context, int i2, String str, String str2, String str3, int i3) {
        b bVar;
        com.subao.common.g.a a2 = a(i2);
        if (a2 == null) {
            return -4;
        }
        i a3 = a();
        if (a3 != null) {
            bVar = new b(a3);
        } else if (context == null) {
            return -4;
        } else {
            bVar = null;
        }
        return a(context, str, n.a.SDK, a2, str3, i3, (byte[]) null, (com.subao.common.a.c) null, false, (InstalledApplicationsSupplier) null, new f(), bVar);
    }

    public static int initWithVPN(Context context, String str) {
        return initWithVPN(context, str, (InstalledApplicationsSupplier) null);
    }

    public static int initWithVPN(Context context, String str, InstalledApplicationsSupplier installedApplicationsSupplier) {
        return initWithVPN(context, str, false, (byte[]) null, installedApplicationsSupplier);
    }

    public static int initWithVPN(Context context, String str, boolean z) {
        return initWithVPN(context, str, z, (byte[]) null);
    }

    public static int initWithVPN(Context context, String str, boolean z, byte[] bArr) {
        return initWithVPN(context, str, z, bArr, (InstalledApplicationsSupplier) null);
    }

    public static int initWithVPN(Context context, String str, boolean z, byte[] bArr, InstalledApplicationsSupplier installedApplicationsSupplier) {
        return a(context, str, n.a.ROM, com.subao.common.g.a.VPN, (String) null, -1, bArr, (com.subao.common.a.c) null, z, installedApplicationsSupplier, (e) null, (a) null);
    }

    static int a(Context context, String str, n.a aVar, com.subao.common.g.a aVar2, String str2, int i2, byte[] bArr, com.subao.common.a.c cVar, boolean z, InstalledApplicationsSupplier installedApplicationsSupplier, e eVar, a aVar3) {
        if (aVar3 == null) {
            int a2 = a(context, str, aVar, aVar2, str2, i2, bArr, cVar, z, installedApplicationsSupplier, eVar);
            Log.d("SubaoGame", String.format(n.b, "GameMaster.init() result: %d", new Object[]{Integer.valueOf(a2)}));
            return a2;
        }
        d dVar = new d(context, str, aVar, aVar2, str2, i2, bArr, cVar, z, installedApplicationsSupplier, eVar, aVar3);
        Log.d("SubaoGame", "GameMaster.init() async running");
        new Thread(dVar).start();
        return 2;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:33:0x009c, code lost:
        if (r15 != com.subao.common.e.n.a.ROM) goto L_0x00c1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x009e, code lost:
        r3 = "android_rom";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00a0, code lost:
        r2 = r1.a(r16, r3, r17, r18, r19);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00ac, code lost:
        if (r2 != 0) goto L_0x00c4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00b2, code lost:
        if (r16 != com.subao.common.g.a.VPN) goto L_0x00bc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00b4, code lost:
        r1.a((com.subao.common.a.e.a) new com.subao.gamemaster.GameMaster.h());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00bc, code lost:
        com.subao.common.a.b.a(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00c1, code lost:
        r3 = "android_sdk";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00c4, code lost:
        r1.a();
        a = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:?, code lost:
        return r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static int a(android.content.Context r13, java.lang.String r14, com.subao.common.e.n.a r15, com.subao.common.g.a r16, java.lang.String r17, int r18, byte[] r19, com.subao.common.a.c r20, boolean r21, com.subao.common.intf.InstalledApplicationsSupplier r22, com.subao.gamemaster.GameMaster.e r23) {
        /*
            java.lang.String r1 = "SubaoGame"
            java.lang.String r2 = "GameMaster %s (%s)\ncommit-id: %s\n"
            r3 = 3
            java.lang.Object[] r3 = new java.lang.Object[r3]
            r4 = 0
            java.lang.String r5 = "5.2.1.1"
            r3[r4] = r5
            r4 = 1
            java.lang.String r5 = "20190614_161048"
            r3[r4] = r5
            r4 = 2
            java.lang.String r5 = "709e38f2bc4e2d59192b3c310aac5c6bf2c48b55"
            r3[r4] = r5
            java.lang.String r2 = java.lang.String.format(r2, r3)
            android.util.Log.i(r1, r2)
            java.lang.String r1 = "SubaoGame"
            boolean r1 = com.subao.common.d.a(r1)
            if (r1 == 0) goto L_0x0042
            java.lang.String r2 = "SubaoGame"
            java.lang.String r3 = "[%s] with %s, installed applications supplier: %s"
            r1 = 3
            java.lang.Object[] r4 = new java.lang.Object[r1]
            r1 = 0
            r4[r1] = r14
            r1 = 1
            java.lang.String r5 = r15.g
            r4[r1] = r5
            r5 = 2
            if (r22 != 0) goto L_0x0051
            java.lang.String r1 = "null"
        L_0x0039:
            r4[r5] = r1
            java.lang.String r1 = java.lang.String.format(r3, r4)
            android.util.Log.d(r2, r1)
        L_0x0042:
            boolean r1 = android.text.TextUtils.isEmpty(r14)
            if (r1 == 0) goto L_0x0054
            java.lang.String r1 = "SubaoGame"
            java.lang.String r2 = "Null game-guid, init failed"
            android.util.Log.e(r1, r2)
            r1 = -4
        L_0x0050:
            return r1
        L_0x0051:
            java.lang.String r1 = "custom"
            goto L_0x0039
        L_0x0054:
            if (r23 == 0) goto L_0x0067
            r0 = r23
            boolean r1 = r0.a(r13)
            if (r1 != 0) goto L_0x0067
            java.lang.String r1 = "SubaoGame"
            java.lang.String r2 = "You are not granted to use GameMaster SDK, please add related permission to your Manifest.xml!"
            android.util.Log.e(r1, r2)
            r1 = -2
            goto L_0x0050
        L_0x0067:
            if (r22 != 0) goto L_0x00ce
            com.subao.common.e.a$a r10 = new com.subao.common.e.a$a
            r0 = r21
            r10.<init>(r0)
        L_0x0070:
            java.lang.Class<com.subao.gamemaster.GameMaster> r12 = com.subao.gamemaster.GameMaster.class
            monitor-enter(r12)
            com.subao.common.a.c r1 = a     // Catch:{ all -> 0x007a }
            if (r1 == 0) goto L_0x007d
            r1 = 1
            monitor-exit(r12)     // Catch:{ all -> 0x007a }
            goto L_0x0050
        L_0x007a:
            r1 = move-exception
            monitor-exit(r12)     // Catch:{ all -> 0x007a }
            throw r1
        L_0x007d:
            if (r20 != 0) goto L_0x00cb
            com.subao.common.a.c r1 = new com.subao.common.a.c     // Catch:{ all -> 0x007a }
            java.lang.String r5 = "5.2.1.1"
            com.subao.common.j.h r6 = com.subao.common.j.h.a((android.content.Context) r13)     // Catch:{ all -> 0x007a }
            com.subao.common.g.c r7 = new com.subao.common.g.c     // Catch:{ all -> 0x007a }
            java.lang.String r2 = "gamemaster"
            r7.<init>(r2)     // Catch:{ all -> 0x007a }
            r8 = 0
            r9 = 1
            r11 = 0
            r2 = r13
            r3 = r15
            r4 = r14
            r1.<init>(r2, r3, r4, r5, r6, r7, r8, r9, r10, r11)     // Catch:{ all -> 0x007a }
        L_0x0097:
            a = r1     // Catch:{ all -> 0x007a }
            monitor-exit(r12)     // Catch:{ all -> 0x007a }
            com.subao.common.e.n$a r2 = com.subao.common.e.n.a.ROM
            if (r15 != r2) goto L_0x00c1
            java.lang.String r3 = "android_rom"
        L_0x00a0:
            r2 = r16
            r4 = r17
            r5 = r18
            r6 = r19
            int r2 = r1.a((com.subao.common.g.a) r2, (java.lang.String) r3, (java.lang.String) r4, (int) r5, (byte[]) r6)
            if (r2 != 0) goto L_0x00c4
            com.subao.common.g.a r3 = com.subao.common.g.a.VPN
            r0 = r16
            if (r0 != r3) goto L_0x00bc
            com.subao.gamemaster.GameMaster$h r3 = new com.subao.gamemaster.GameMaster$h
            r3.<init>()
            r1.a((com.subao.common.a.e.a) r3)
        L_0x00bc:
            com.subao.common.a.b.a(r1)
        L_0x00bf:
            r1 = r2
            goto L_0x0050
        L_0x00c1:
            java.lang.String r3 = "android_sdk"
            goto L_0x00a0
        L_0x00c4:
            r1.a()
            r1 = 0
            a = r1
            goto L_0x00bf
        L_0x00cb:
            r1 = r20
            goto L_0x0097
        L_0x00ce:
            r10 = r22
            goto L_0x0070
        */
        throw new UnsupportedOperationException("Method not decompiled: com.subao.gamemaster.GameMaster.a(android.content.Context, java.lang.String, com.subao.common.e.n$a, com.subao.common.g.a, java.lang.String, int, byte[], com.subao.common.a.c, boolean, com.subao.common.intf.InstalledApplicationsSupplier, com.subao.gamemaster.GameMaster$e):int");
    }

    @TargetApi(14)
    public static Intent prepareVPN(Context context) {
        try {
            return VpnService.prepare(context);
        } catch (RuntimeException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static int openVPN() {
        com.subao.common.a.c cVar = a;
        if (cVar != null) {
            return cVar.B();
        }
        return 1000;
    }

    public static void closeVPN() {
        com.subao.common.a.c cVar = a;
        if (cVar != null) {
            cVar.C();
        }
    }

    public static boolean isVpnEstablished() {
        com.subao.common.a.c cVar = a;
        return cVar != null && cVar.D();
    }

    public static boolean setVPNStateListener(VPNStateListener vPNStateListener) {
        com.subao.common.a.c cVar = a;
        if (cVar == null) {
            return false;
        }
        cVar.a(vPNStateListener);
        return true;
    }

    public static void stopService(Context context) {
        GameMasterVpnService.c(context);
    }

    public static void setVpnSessionName(String str) {
        GameMasterVpnService.a(str);
    }

    public static boolean start(int i2) {
        com.subao.common.a.c cVar = a;
        if (cVar == null) {
            return false;
        }
        int o = cVar.o();
        if (o == 0 || o == 1002) {
            return true;
        }
        return false;
    }

    public static void stop() {
        com.subao.common.a.c cVar = a;
        if (cVar != null) {
            cVar.p();
        }
    }

    public static boolean setAccelSwitchListener(AccelSwitchListener accelSwitchListener) {
        com.subao.common.a.c cVar = a;
        if (cVar == null) {
            return false;
        }
        cVar.a(accelSwitchListener);
        return true;
    }

    public static boolean isEngineRunning() {
        return a != null;
    }

    public static boolean isUDPProxy() {
        com.subao.common.a.c cVar = a;
        return cVar != null && cVar.y();
    }

    public static boolean isAccelOpened() {
        com.subao.common.a.c cVar = a;
        return cVar != null && cVar.c();
    }

    public static void accelerateGame(GameInformation gameInformation) {
        accelerateGame(gameInformation, 0, (AccelerateGameCallback) null);
    }

    public static boolean accelerateGame(GameInformation gameInformation, long j, AccelerateGameCallback accelerateGameCallback) {
        com.subao.common.a.c cVar;
        if (gameInformation == null || (cVar = a) == null) {
            return false;
        }
        cVar.a(gameInformation, j, accelerateGameCallback);
        return true;
    }

    public static boolean isNodeDetected(int i2) {
        com.subao.common.a.c cVar = a;
        return cVar != null && cVar.k(i2);
    }

    public static void setUserStateListener(UserStateListener userStateListener) {
        com.subao.common.a.c cVar = a;
        if (cVar != null) {
            cVar.a(userStateListener);
        }
    }

    public static void setUserToken(String str, String str2, String str3) {
        com.subao.common.a.c cVar = a;
        if (cVar != null) {
            cVar.a(new UserInfo(str, str2, str3), (XunyouUserStateCallback) null, (Object) null, 2);
        }
    }

    public static int getCurrentUserFreeFlowType() {
        com.subao.common.a.c cVar = a;
        if (cVar != null) {
            return cVar.s();
        }
        return -1;
    }

    public static void setFreeFlowUser(int i2) {
        if (com.subao.common.d.a("SubaoGame")) {
            Log.d("SubaoGame", "Free flow user: " + i2);
        }
        com.subao.common.a.c cVar = a;
        if (cVar != null) {
            cVar.g(i2);
        }
    }

    public static void onNetDelay(int i2) {
        com.subao.common.a.c cVar = a;
        if (cVar != null) {
            cVar.e(i2);
        }
    }

    public static int getAccelRecommendation() {
        int i2;
        com.subao.common.a.c cVar = a;
        if (cVar != null) {
            i2 = cVar.t();
        } else {
            i2 = -1;
        }
        if (com.subao.common.d.a("SubaoGame")) {
            Log.d("SubaoGame", "getAccelRecommendation() return: " + i2);
        }
        return i2;
    }

    public static void setUdpEchoPort(int i2) {
        com.subao.common.a.c cVar = a;
        if (cVar != null) {
            cVar.f(i2);
        }
    }

    public static int getCurrentConnectionType() {
        com.subao.common.a.c cVar = a;
        if (cVar != null) {
            return cVar.z();
        }
        return 0;
    }

    public static void addAccelAddress(String str, String str2, int i2) {
        if (com.subao.common.d.a("SubaoGame")) {
            Log.d("SubaoGame", String.format(n.b, "addAccelAddress(%s, %s, %d)", new Object[]{str, str2, Integer.valueOf(i2)}));
        }
        com.subao.common.a.c cVar = a;
        if (cVar != null) {
            cVar.a(str, str2, i2);
        }
    }

    @Deprecated
    public static boolean isNodeDetectSucceed() {
        return true;
    }

    @Deprecated
    public static String getString(int i2) {
        return "";
    }

    @Deprecated
    public static void setString(int i2, String str) {
    }

    @Deprecated
    public static long getLong(int i2) {
        return 0;
    }

    @Deprecated
    public static void setLong(int i2, long j) {
    }

    @Deprecated
    public static void setSDKMode(int i2) {
    }

    @Deprecated
    public static String getWebUIUrl() {
        return getWebUIUrl(0);
    }

    public static String getWebUIUrl(int i2) {
        if (com.subao.common.d.a("SubaoGame")) {
            Log.d("SubaoGame", String.format(n.b, "getWebUIUrl(%d)", new Object[]{Integer.valueOf(i2)}));
        }
        com.subao.common.a.c cVar = a;
        if (cVar != null) {
            return cVar.h(i2);
        }
        return com.subao.common.a.c.a("", "");
    }

    public static String getServiceUrl() {
        com.subao.common.a.c cVar = a;
        if (cVar != null) {
            return cVar.u();
        }
        return com.subao.common.a.c.a("", "");
    }

    @Deprecated
    public static void onNetDelayQuality(float f2, float f3, float f4, int i2) {
    }

    @Deprecated
    public static void onNetDelayQuality2(float f2, float f3, float f4, float f5, float f6) {
        onNetDelayQuality3(f2, f3, f4, f5, f6, InnerErrorCode.DEFAULT_ERROR);
    }

    @Deprecated
    public static void onNetDelayQuality3(float f2, float f3, float f4, float f5, float f6, int i2) {
    }

    public static String getVIPValidTime() {
        String v;
        com.subao.common.a.c cVar = a;
        if (cVar == null || (v = cVar.v()) == null) {
            return "";
        }
        return v;
    }

    public static int getAccelerationStatus() {
        com.subao.common.a.c cVar = a;
        if (cVar != null) {
            return cVar.w();
        }
        return 0;
    }

    public static String getUserConfig() {
        return j.f();
    }

    public static void gameForeground() {
        com.subao.common.a.c cVar = a;
        if (cVar != null) {
            cVar.c(true);
        }
    }

    public static void gameBackground() {
        com.subao.common.a.c cVar = a;
        if (cVar != null) {
            cVar.c(false);
        }
    }

    @Deprecated
    public static void clearUDPCache() {
    }

    public static void setGameId(int i2) {
        setGameId(Integer.toString(i2));
    }

    public static void setGameId(String str) {
        com.subao.common.a.c cVar = a;
        if (cVar != null) {
            cVar.a(str);
        }
    }

    public static void setPlayerLevel(int i2) {
        com.subao.common.a.c cVar = a;
        if (cVar != null) {
            cVar.i(i2);
        }
    }

    public static void setRecommendationGameIP(String str, int i2) {
        if (com.subao.common.d.a("SubaoGame")) {
            Log.d("SubaoGame", String.format(n.b, "setRecommendationGameIP(%s, %d", new Object[]{str, Integer.valueOf(i2)}));
        }
        com.subao.common.a.c cVar = a;
        if (cVar != null) {
            cVar.a(str, i2);
        }
    }

    public static String getAccelRecommendationData(int i2) {
        if (com.subao.common.d.a("SubaoGame")) {
            Log.d("SubaoGame", String.format(n.b, "getAccelRecommendationData(%d)", new Object[]{Integer.valueOf(i2)}));
        }
        com.subao.common.a.c cVar = a;
        if (cVar != null) {
            return cVar.j(i2);
        }
        return "";
    }

    public static void onAccelRecommendationResult(int i2, boolean z) {
        if (com.subao.common.d.a("SubaoGame")) {
            Log.d("SubaoGame", String.format(n.b, "onAccelRecommendationResult(%d, %b)", new Object[]{Integer.valueOf(i2), Boolean.valueOf(z)}));
        }
        com.subao.common.a.c cVar = a;
        if (cVar != null) {
            cVar.a(i2, z);
        }
    }

    public static void setWiFiAccelSwitch(boolean z) {
        com.subao.common.a.c cVar = a;
        if (cVar != null) {
            cVar.d(z);
        }
    }

    public static void enableWiFiAccelSwitch() {
        setWiFiAccelSwitch(true);
    }

    public static void beginRound(String str, String str2) {
        com.subao.common.a.c cVar = a;
        if (cVar != null) {
            cVar.c(str, str2);
        }
    }

    public static void setPayTypeWhiteList(String str) {
        com.subao.common.a.c cVar = a;
        if (cVar != null) {
            cVar.b(str);
        }
    }

    public static void setPayTypeWhiteList(int i2) {
        if (i2 == 0) {
            setPayTypeWhiteList((String) null);
            return;
        }
        StringBuilder sb = new StringBuilder(6);
        for (int i3 = 0; i3 < 6; i3++) {
            if (((1 << i3) & i2) != 0) {
                sb.append(i3);
            }
        }
        setPayTypeWhiteList(sb.toString());
    }

    public static List<String> getSupportGameList() {
        return getSupportGameList(false);
    }

    public static List<String> getSupportGameList(boolean z) {
        return a(z, "getSupportGameList", new al.c());
    }

    @NonNull
    public static List<GameInformation> getSupportGameInformationList(boolean z) {
        return a(z, "getSupportGameInformationList", new al.b());
    }

    @NonNull
    private static <T> List<T> a(boolean z, String str, al.a<T> aVar) {
        al b2;
        List<T> list = null;
        com.subao.common.a.c cVar = a;
        if (!(cVar == null || (b2 = cVar.b(z)) == null)) {
            list = b2.a(aVar, false);
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        if (com.subao.common.d.a("SubaoGame")) {
            Log.d("SubaoGame", String.format(n.b, "%s(%b) return %d element(s)", new Object[]{str, Boolean.valueOf(z), Integer.valueOf(list.size())}));
        }
        return list;
    }

    @NonNull
    public static List<SupportGameLabel> getSupportGameLabelList() {
        com.subao.common.a.c cVar = a;
        return cVar == null ? new ArrayList() : cVar.h();
    }

    public static boolean launcherGame(Context context, String str) {
        return com.subao.common.n.a.a(context, str);
    }

    public static void queryOriginUserState(UserInfo userInfo, long j, QueryOriginUserStateCallback queryOriginUserStateCallback, Object obj) {
        com.subao.common.a.c cVar = a;
        if (cVar == null) {
            queryOriginUserStateCallback.onOriginUserState(userInfo, obj, 1000, 0, (String) null);
        } else {
            cVar.a(userInfo, j, queryOriginUserStateCallback, obj);
        }
    }

    public static long getLastServerTime() {
        com.subao.common.a.c cVar = a;
        if (cVar == null) {
            return 0;
        }
        return cVar.x();
    }

    @Deprecated
    public static void queryXunyouUserState(UserInfo userInfo, long j, XunyouUserStateCallback xunyouUserStateCallback, Object obj) {
        queryXunyouUserState(userInfo, j, xunyouUserStateCallback, obj, false);
    }

    public static void queryXunyouUserState(UserInfo userInfo, long j, XunyouUserStateCallback xunyouUserStateCallback, Object obj, boolean z) {
        int i2 = 0;
        if (userInfo == null) {
            xunyouUserStateCallback.onXunyouUserState((UserInfo) null, obj, 1012, 0, "");
            return;
        }
        com.subao.common.a.c cVar = a;
        if (cVar == null) {
            xunyouUserStateCallback.onXunyouUserState(userInfo, obj, 1000, 0, "");
            return;
        }
        if (!z) {
            i2 = 1;
        }
        cVar.a(userInfo, xunyouUserStateCallback, obj, i2);
    }

    @Deprecated
    public static void refreshXunyouUserState(long j, XunyouUserStateCallback xunyouUserStateCallback, Object obj) {
        com.subao.common.a.c cVar = a;
        if (cVar != null) {
            cVar.a(j, xunyouUserStateCallback, obj);
        } else if (xunyouUserStateCallback != null) {
            xunyouUserStateCallback.onXunyouUserState((UserInfo) null, obj, 1000, 0, "");
        }
    }

    public static byte[] getXunyouAccessToken() {
        com.subao.common.a.c cVar = a;
        if (cVar != null) {
            return cVar.q();
        }
        return null;
    }

    public static void loginByXunyouToken(String str, byte[] bArr, XunyouTokenStateListener xunyouTokenStateListener) {
        com.subao.common.a.c cVar;
        if (bArr != null && bArr.length != 0 && (cVar = a) != null) {
            cVar.a(str, bArr, xunyouTokenStateListener);
        }
    }

    public static void queryThirdPartyAuthInfo(UserInfo userInfo, long j, QueryThirdPartyAuthInfoCallback queryThirdPartyAuthInfoCallback) {
        if (queryThirdPartyAuthInfoCallback != null) {
            if (userInfo == null || TextUtils.isEmpty(userInfo.getToken())) {
                queryThirdPartyAuthInfoCallback.onThirdPartyAuthInfoResult(1012, (ThirdPartyAuthInfo) null);
                return;
            }
            com.subao.common.a.c cVar = a;
            if (cVar == null) {
                queryThirdPartyAuthInfoCallback.onThirdPartyAuthInfoResult(1000, (ThirdPartyAuthInfo) null);
            } else {
                cVar.a(userInfo, (int) j, queryThirdPartyAuthInfoCallback);
            }
        }
    }

    public static boolean requestTrial(@Nullable RequestTrialCallback requestTrialCallback) {
        com.subao.common.a.c cVar = a;
        if (cVar != null) {
            return cVar.a(requestTrialCallback);
        }
        if (requestTrialCallback != null) {
            requestTrialCallback.onRequestTrialResult(1000);
        }
        return false;
    }

    public static void queryProductList(@NonNull QueryProductCallback queryProductCallback, boolean z) {
        if (a != null) {
            a.a(queryProductCallback, z);
        } else {
            queryProductCallback.onQueryProductResult(1000, (ProductList) null);
        }
    }

    public static void requestBuy(String str, String str2, int i2, RequestBuyCallback requestBuyCallback) {
        int i3;
        if (requestBuyCallback == null) {
            throw new NullPointerException("Callback can not be null");
        }
        if (TextUtils.isEmpty(str2)) {
            i3 = 1012;
        } else if (i2 != 12 && i2 != 14) {
            i3 = 1011;
        } else if (a == null) {
            i3 = 1000;
        } else {
            a.a(str, str2, i2, requestBuyCallback);
            return;
        }
        requestBuyCallback.onRequestBuyResult(i3, (RequestBuyResult) null);
    }

    public static void queryAvailableSignCoupons(QuerySignCouponsCallback querySignCouponsCallback) {
        if (querySignCouponsCallback != null) {
            com.subao.common.a.c cVar = a;
            if (cVar != null) {
                cVar.a(querySignCouponsCallback);
            } else {
                querySignCouponsCallback.onQuerySignCouponsResult(1000, (List<String>) null);
            }
        }
    }

    public static void queryTwiceTrialState(QueryTwiceTrialStateCallback queryTwiceTrialStateCallback) {
        if (queryTwiceTrialStateCallback != null) {
            com.subao.common.a.c cVar = a;
            if (cVar != null) {
                cVar.a(queryTwiceTrialStateCallback);
            } else {
                queryTwiceTrialStateCallback.onQueryTwiceTrailStateResult(1000, (String) null, 0);
            }
        }
    }

    public static void requestTwiceTrial(String str, RequestTwiceTrialCallback requestTwiceTrialCallback) {
        if (!TextUtils.isEmpty(str)) {
            com.subao.common.a.c cVar = a;
            if (cVar != null) {
                cVar.a(str, requestTwiceTrialCallback);
            } else if (requestTwiceTrialCallback != null) {
                requestTwiceTrialCallback.onRequestTwiceTrialResult(1000, (String) null);
            }
        } else if (requestTwiceTrialCallback != null) {
            requestTwiceTrialCallback.onRequestTwiceTrialResult(1012, (String) null);
        }
    }

    public static int getXunYouServiceRemindType() {
        int i2;
        com.subao.common.a.c cVar = a;
        if (cVar != null) {
            i2 = cVar.F();
        } else {
            i2 = 0;
        }
        if (com.subao.common.d.a("SubaoGame")) {
            Log.d("SubaoGame", "getXunYouServiceRemindType() return: " + i2);
        }
        return i2;
    }

    public static String queryAccelerateEffect(String str) {
        com.subao.common.a.c cVar = a;
        if (cVar == null || TextUtils.isEmpty(str)) {
            return "";
        }
        return cVar.c(str);
    }

    public static int detectTimeDelay(String str, int i2, String str2, int i3, int i4, int i5, DetectTimeDelayCallback detectTimeDelayCallback) {
        com.subao.common.a.c cVar = a;
        if (cVar == null) {
            return 2;
        }
        if (TextUtils.isEmpty(str)) {
            return 3;
        }
        return cVar.a(str, i2, str2, i3, i4, i5, detectTimeDelayCallback);
    }

    public static int detectAccessDelay(DetectTimeDelayCallback detectTimeDelayCallback) {
        com.subao.common.a.c cVar = a;
        if (cVar == null) {
            return 2;
        }
        return cVar.a(detectTimeDelayCallback);
    }

    public static String getLocalIp() {
        com.subao.common.a.c cVar = a;
        if (cVar != null) {
            return cVar.E();
        }
        return "";
    }

    public static void queryActivities(@NonNull QueryActivitiesCallback queryActivitiesCallback) {
        if (a == null) {
            queryActivitiesCallback.onResult(1000, false, (String) null);
        } else {
            a.a(queryActivitiesCallback, true);
        }
    }

    public static void setActivityExposure(String str, @NonNull ActivityExposureCallback activityExposureCallback) {
        if (a == null) {
            activityExposureCallback.onResult(1000);
        } else {
            a.a(str, activityExposureCallback);
        }
    }

    public static int queryTrialNotice(long j) {
        com.subao.common.a.c cVar = a;
        if (cVar == null) {
            return 1000;
        }
        return cVar.a(j);
    }

    public static int replyTrialNotice(int i2) {
        com.subao.common.a.c cVar = a;
        if (cVar == null) {
            return 1000;
        }
        cVar.l(i2);
        return 0;
    }

    public static int setLinkLiving(boolean z) {
        com.subao.common.a.c cVar = a;
        if (cVar == null) {
            return 1000;
        }
        cVar.e(z);
        return 0;
    }

    public static void setWorkerSleep(int i2) {
        com.subao.common.a.c cVar = a;
        if (cVar != null) {
            cVar.m(i2);
        }
    }

    static Pair<Integer, Integer> x1() {
        int a2;
        int i2 = -1;
        com.subao.common.a.c cVar = a;
        if (cVar == null) {
            a2 = 1000;
        } else {
            try {
                i2 = cVar.r();
                a2 = 0;
            } catch (b.d e2) {
                a2 = e2.a();
            }
        }
        return new Pair<>(Integer.valueOf(i2), Integer.valueOf(a2));
    }

    static void x2(String str, String str2) {
        com.subao.common.a.c cVar = a;
        if (cVar != null) {
            cVar.b(str, str2);
        }
    }

    static String x3(Context context) {
        l lVar = new l(context);
        StringWriter stringWriter = new StringWriter(2048);
        JsonWriter jsonWriter = new JsonWriter(stringWriter);
        try {
            lVar.serialize(jsonWriter);
            com.subao.common.e.a((Closeable) jsonWriter);
            return stringWriter.toString();
        } catch (IOException e2) {
            e2.printStackTrace();
            com.subao.common.e.a((Closeable) jsonWriter);
            return null;
        } catch (Throwable th) {
            com.subao.common.e.a((Closeable) jsonWriter);
            throw th;
        }
    }

    static void x4() {
        com.subao.common.a.c cVar = a;
        if (cVar != null) {
            cVar.A();
        }
    }

    static n.a a(String str) {
        if (ServerProtocol.DIALOG_PARAM_SDK_VERSION.equals(str)) {
            return n.a.SDK;
        }
        if ("rom".equals(str)) {
            return n.a.ROM;
        }
        return n.a.SERVICE;
    }

    static File x8(Context context) {
        com.subao.common.f.a.a(context, n.a.SDK);
        return com.subao.common.f.a.a();
    }

    static void x9(String str, final I1 i1) {
        com.subao.common.a.c cVar = a;
        if (cVar != null) {
            s.a j = cVar.j();
            com.subao.common.e.h.a(j.a, j.c, str, new h.a() {
                public void a(boolean z) {
                    i1.a(z);
                }
            });
            return;
        }
        i1.a(false);
    }

    static Object x10(Context context, I2 i2) {
        p pVar = new p(new g(i2));
        pVar.a(context);
        return pVar;
    }

    static void x11(Object obj) {
        ((o) obj).a();
    }

    static String x12(String str) {
        c cVar = new c();
        com.subao.common.j.d.a((Context) null, str, cVar, (Object) null, (ai) null);
        d.C0016d a2 = cVar.a();
        return a2 != null ? a2.toString() : "fail";
    }

    static int x13(int i2) {
        com.subao.common.a.c cVar = a;
        if (cVar == null) {
            return 1000;
        }
        return cVar.d(i2);
    }

    static void xy(Context context, String str) {
        aj.b().b((String) null);
        com.subao.common.f.a.a(context, a(str));
        File a2 = com.subao.common.f.a.a();
        File[] listFiles = a2.listFiles();
        if (listFiles != null) {
            for (File delete : listFiles) {
                delete.delete();
            }
        }
        a2.delete();
    }

    static class g implements o.a {
        private final I2 a;

        g(I2 i2) {
            this.a = i2;
        }

        public void a(int i) {
            this.a.a(i);
        }
    }

    static class f implements e {
        private static final int a = "com.subao.permission.USE_SDK".length();

        f() {
        }

        public boolean a(Context context) {
            String[] strArr;
            PackageManager packageManager = context.getPackageManager();
            if (packageManager == null) {
                return false;
            }
            String packageName = context.getPackageName();
            if (TextUtils.isEmpty(packageName)) {
                return false;
            }
            try {
                PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 4096);
                if (packageInfo == null || (strArr = packageInfo.requestedPermissions) == null) {
                    return false;
                }
                for (String str : strArr) {
                    if (str != null && str.length() >= a && str.startsWith("com.subao.permission.USE_SDK")) {
                        return true;
                    }
                }
                return false;
            } catch (Exception e) {
                return false;
            }
        }
    }

    private static class c extends ConditionVariable implements d.b {
        private d.C0016d a;

        private c() {
        }

        public void a(Object obj, d.C0016d dVar) {
            this.a = dVar;
            open();
        }

        public d.C0016d a() {
            block();
            return this.a;
        }
    }

    static class h implements e.a {
        h() {
        }

        public com.subao.common.a.e a() {
            return GameMasterVpnService.c();
        }

        public boolean a(Context context) {
            return GameMasterVpnService.b(context);
        }
    }

    static class i {
        @NonNull
        public final String a;
        @NonNull
        public final String b;

        private i(@NonNull String str, @NonNull String str2) {
            this.a = str;
            this.b = str2;
        }

        @Nullable
        static i a(String str, String str2) {
            if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
                return null;
            }
            return new i(str, str2);
        }

        public boolean equals(Object obj) {
            boolean z = true;
            if (obj == null) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof i)) {
                return false;
            }
            i iVar = (i) obj;
            if (!com.subao.common.e.a(iVar.a, this.a) || !com.subao.common.e.a(iVar.b, this.b)) {
                z = false;
            }
            return z;
        }

        public int hashCode() {
            return this.a.hashCode() ^ this.b.hashCode();
        }
    }

    static class d implements Runnable {
        @Nullable
        final Context a;
        final String b;
        final n.a c;
        final com.subao.common.g.a d;
        final String e;
        final int f;
        final byte[] g;
        final com.subao.common.a.c h;
        final boolean i;
        final InstalledApplicationsSupplier j;
        final e k;
        final a l;

        d(@Nullable Context context, String str, n.a aVar, com.subao.common.g.a aVar2, String str2, int i2, byte[] bArr, com.subao.common.a.c cVar, boolean z, InstalledApplicationsSupplier installedApplicationsSupplier, e eVar, @NonNull a aVar3) {
            this.a = context;
            this.b = str;
            this.c = aVar;
            this.d = aVar2;
            this.e = str2;
            this.f = i2;
            this.g = bArr;
            this.h = cVar;
            this.i = z;
            this.j = installedApplicationsSupplier;
            this.k = eVar;
            this.l = aVar3;
        }

        public void run() {
            this.l.onSDKInitCompleted(a());
        }

        private int a() {
            Context context = this.a;
            if (context == null && (context = this.l.a()) == null) {
                return -4;
            }
            return GameMaster.a(context, this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j, this.k);
        }
    }

    static class b implements a {
        @NonNull
        final i a;

        public b(@NonNull i iVar) {
            this.a = iVar;
        }

        public Context a() {
            return new a().a();
        }

        public void onSDKInitCompleted(int i) {
            boolean a2 = new a().a(this.a.a, this.a.b, String.format(n.b, "%d", new Object[]{Integer.valueOf(i)}));
            Log.d("SubaoGame", String.format(n.b, "Notify U3D observer: %s.%s(%d) result %b", new Object[]{this.a.a, this.a.b, Integer.valueOf(i), Boolean.valueOf(a2)}));
        }
    }
}
