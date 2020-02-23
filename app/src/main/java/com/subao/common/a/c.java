package com.subao.common.a;

import MTT.EFvrECode;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import com.banalytics.BATrackerConst;
import com.google.android.gms.nearby.connection.ConnectionsStatusCodes;
import com.subao.common.a.e;
import com.subao.common.b.c;
import com.subao.common.c.e;
import com.subao.common.e.ad;
import com.subao.common.e.ah;
import com.subao.common.e.ai;
import com.subao.common.e.aj;
import com.subao.common.e.ak;
import com.subao.common.e.al;
import com.subao.common.e.am;
import com.subao.common.e.b;
import com.subao.common.e.e;
import com.subao.common.e.n;
import com.subao.common.e.r;
import com.subao.common.e.s;
import com.subao.common.e.z;
import com.subao.common.f;
import com.subao.common.i.d;
import com.subao.common.i.m;
import com.subao.common.i.n;
import com.subao.common.intf.AccelSwitchListener;
import com.subao.common.intf.AccelerateGameCallback;
import com.subao.common.intf.ActivityExposureCallback;
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
import com.subao.common.intf.UserInfo;
import com.subao.common.intf.UserStateListener;
import com.subao.common.intf.VPNStateListener;
import com.subao.common.intf.XunyouTokenStateListener;
import com.subao.common.intf.XunyouUserStateCallback;
import com.subao.common.j.h;
import com.subao.common.j.j;
import com.subao.common.j.k;
import com.subao.common.j.o;
import com.subao.common.k.a;
import com.subao.common.k.b;
import com.subao.vpn.JniCallback;
import com.subao.vpn.VPNJni;
import com.tencent.qqgamemi.util.DeviceDetectUtil;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.HttpHost;

/* compiled from: EngineWrapper */
public class c implements com.subao.common.a, a, c.a {
    @Nullable
    private al A;
    private boolean B;
    private e.a C;
    private final com.subao.common.e.i<ai, ProductList> D = new com.subao.common.e.i<>(3600000);
    private final b E;
    private final m F = new m();
    private final i G = new i();
    private final com.subao.common.e.i<String, String> H = new com.subao.common.e.i<>(3600000);
    private final am<QueryActivitiesCallback> I = new am<>();
    private final am<ActivityExposureCallback> J = new am<>();
    @NonNull
    final String a;
    final String b;
    @NonNull
    final String c;
    @NonNull
    final com.subao.common.j.h d;
    final com.subao.common.b.k e = new com.subao.common.b.k();
    /* access modifiers changed from: private */
    public final Context f;
    private final n.a g;
    private final int h;
    @NonNull
    private final s.a i;
    /* access modifiers changed from: private */
    @NonNull
    public final com.subao.common.g.c j;
    @NonNull
    private final String k;
    /* access modifiers changed from: private */
    @NonNull
    public final ah l;
    /* access modifiers changed from: private */
    @NonNull
    public final com.subao.common.e.a m;
    /* access modifiers changed from: private */
    public g n;
    private volatile v o;
    private int p;
    @NonNull
    private final af q;
    @NonNull
    private final com.subao.common.i.f r;
    @NonNull
    private final com.subao.common.i.h s;
    private int t = -1;
    @Nullable
    private UserInfo u;
    @Nullable
    private com.subao.common.b.e v;
    private AccelSwitchListener w;
    private VPNStateListener x;
    @Nullable
    private volatile UserStateListener y;
    private ai z;

    /* compiled from: EngineWrapper */
    private interface ac extends com.subao.common.a {
        int a(Context context);
    }

    /* compiled from: EngineWrapper */
    private interface b {
        void a(String str, byte[] bArr, XunyouTokenStateListener xunyouTokenStateListener);

        byte[] a();
    }

    public c(@NonNull Context context, @NonNull n.a aVar, @NonNull String str, @NonNull String str2, @NonNull com.subao.common.j.h hVar, @NonNull com.subao.common.g.c cVar, @Nullable ah ahVar, boolean z2, @NonNull InstalledApplicationsSupplier installedApplicationsSupplier, @Nullable Map<String, String> map) {
        com.subao.common.f.b.a(context, str, cVar);
        com.subao.common.e.n.c = aVar;
        this.f = context.getApplicationContext();
        this.g = aVar;
        this.h = a(this.f);
        this.a = str;
        this.b = str2;
        this.c = a(map, "imsi");
        this.k = a(map, "imei");
        this.j = cVar;
        f.a.a(cVar);
        this.d = hVar;
        this.l = ahVar == null ? new ah() : ahVar;
        this.E = d(str);
        com.subao.common.f.a.a(context, aVar);
        b(context);
        if (ahVar == null) {
            this.l.a(context, aVar);
        }
        this.m = new com.subao.common.e.a(str, aVar, new r(l(), str2, this.l.c(), this.d), cVar, new s(cVar), installedApplicationsSupplier);
        this.q = new af(this, cVar);
        this.s = new com.subao.common.i.i(context, this.g, str2, str, this.c, this.k, this.d);
        this.z = this.l.j();
        if (this.z == null) {
            this.z = com.subao.common.e.f.a;
        }
        this.r = com.subao.common.i.g.a(this.z, this.s);
        this.i = new s.a(l(), str2, this.l.i(), this.d);
        if (z2) {
            a((JniCallback) new d(this, this.j, this.d, this.z, this.i));
        }
        com.subao.common.j.d.a(l(), this.z);
    }

    private static String a(@Nullable Map<String, String> map, @NonNull String str) {
        return map != null ? com.subao.common.n.g.a(map.get(str)) : "";
    }

    private b d(String str) {
        if ("D72C7B0F-B835-46BE-B0C6-5CA60CCED8AF".equals(str)) {
            return new d();
        }
        return new C0000c();
    }

    /* access modifiers changed from: package-private */
    public synchronized void a(boolean z2) {
        if (this.B != z2) {
            this.B = z2;
            n();
        }
    }

    public void a(@Nullable com.subao.common.b.e eVar) {
        this.v = eVar;
        if (eVar != null) {
            com.subao.common.i.j.a(eVar.a, eVar.d, eVar.e, eVar.f, new com.subao.common.i.b(eVar.j, eVar.k, eVar.l, eVar.m));
        } else {
            com.subao.common.i.j.b((String) null);
        }
    }

    @Nullable
    public UserStateListener e() {
        return this.y;
    }

    /* access modifiers changed from: private */
    public static long H() {
        return SystemClock.elapsedRealtime();
    }

    private static int a(Context context) {
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        if (applicationInfo == null) {
            return 0;
        }
        return applicationInfo.uid;
    }

    public static String a(String str, String str2) {
        return String.format("https://service.xunyou.mobi/?appid=%s&userid=%s", new Object[]{com.subao.common.e.a(str), com.subao.common.e.a(str2)});
    }

    private static String a(String str, String str2, String str3, n.a aVar) {
        int indexOf = str.indexOf(63);
        if (indexOf < 0) {
            return str2;
        }
        if (aVar == n.a.ROM) {
            return str2 + str3 + str.substring(indexOf);
        }
        return str2 + str.substring(indexOf);
    }

    /* access modifiers changed from: private */
    public static void b(com.subao.common.g.c cVar, int i2, int i3, int i4) {
        boolean a2 = com.subao.common.b.a(i3);
        if (com.subao.common.d.a("SubaoParallel")) {
            Log.d("SubaoParallel", String.format(com.subao.common.e.n.b, "requestMobileFD() return fd=%d, error=%d, canRetry=%b", new Object[]{Integer.valueOf(i4), Integer.valueOf(i3), Boolean.valueOf(a2)}));
        }
        cVar.a(i2, i4, i3, a2);
    }

    static com.subao.common.j.l a(com.subao.common.g.a aVar) {
        switch (aVar) {
            case TCP:
                return com.subao.common.j.l.TCP;
            case UDP:
                return com.subao.common.j.l.UDP;
            default:
                return com.subao.common.j.l.BOTH;
        }
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public com.subao.common.e.a f() {
        return this.m;
    }

    /* access modifiers changed from: package-private */
    public boolean g() {
        return this.o != null;
    }

    public al b(boolean z2) {
        if (z2) {
            this.A = this.m.a(this.f, com.subao.common.h.a.a(), this.j);
        }
        return this.A;
    }

    @Nullable
    public ak b(int i2) {
        al alVar = this.A;
        if (alVar != null) {
            Iterator<ak> it = alVar.iterator();
            while (it.hasNext()) {
                ak next = it.next();
                if (i2 == next.a) {
                    return next;
                }
            }
        }
        return null;
    }

    @NonNull
    public List<SupportGameLabel> h() {
        List<com.subao.common.e.b> a2 = com.subao.common.h.a.a();
        if (a2 == null || a2.isEmpty()) {
            return new ArrayList();
        }
        ArrayList arrayList = new ArrayList(a2.size());
        for (com.subao.common.e.b next : a2) {
            arrayList.add(new SupportGameLabel(next.a, next.b()));
        }
        return arrayList;
    }

    public void a(JniCallback jniCallback) {
        this.j.a(jniCallback);
    }

    /* access modifiers changed from: package-private */
    public Context i() {
        return this.f;
    }

    public s.a j() {
        return this.i;
    }

    @NonNull
    public com.subao.common.i.f k() {
        return this.r;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public String l() {
        return this.a;
    }

    private void b(Context context) {
        aj b2 = aj.b();
        b2.a(new aj.a() {
            public void a(String str) {
                com.subao.common.i.j.a(str);
                c.this.j.b(0, "key_subao_id", str);
            }
        });
        b2.a(context);
        com.subao.common.i.j.a(aj.b().c());
    }

    public void a() {
        synchronized (this) {
            v vVar = this.o;
            this.o = null;
            if (vVar != null) {
                vVar.a();
            }
            this.j.a();
            this.q.a();
        }
    }

    public void a(e.a aVar) {
        if (aVar == null) {
            throw new NullPointerException();
        }
        this.C = aVar;
    }

    public int m() {
        return this.j.b();
    }

    public int a(com.subao.common.g.a aVar, String str, String str2, int i2, @Nullable byte[] bArr) {
        long intValue;
        int a2 = l.a(this);
        if (a2 != 0) {
            return a2;
        }
        com.subao.common.b.a.a(this.l.i(), l(), com.subao.common.f.d.a(com.subao.common.f.a.a("ac5")));
        this.m.a(this.l.d());
        this.m.a(bArr);
        byte[] a3 = this.m.a(this.j.b());
        e.a a4 = this.m.a();
        this.p = a4 == null ? 0 : a4.a;
        boolean a5 = this.j.a(this.a, this.d.a().g, aVar, str, a3, a4 == null ? null : a4.b, this.m.b());
        if (a5) {
            l.a(this.j);
            l.a(aVar);
            this.j.a(0, "key_android_version_sdk_int", Build.VERSION.SDK_INT);
            l.a(this.j, this.l);
            l.a(this.f, this.j, this.g);
            if (aVar == com.subao.common.g.a.VPN) {
                this.A = this.m.a(this.f);
            } else {
                l.a(this.f, this.j, a(aVar), str2);
            }
            l.a(this.j, i2);
            this.j.b(0, "key_set_imsi", this.c);
            this.m.c();
            this.o = l.b(this.j);
            com.subao.common.i.a e2 = this.s.e();
            this.j.a(e2.a(), e2.b());
            if (aVar == com.subao.common.g.a.VPN) {
                Integer g2 = this.l.g();
                h hVar = new h(com.subao.common.m.b.a());
                if (g2 == null) {
                    intValue = -1;
                } else {
                    intValue = ((long) g2.intValue()) * 1000;
                }
                this.n = g.a(hVar, intValue);
            }
            this.d.a((h.a) new n());
            a(this.g);
            f.a(this.f, this.j);
            z.a(this.j.j());
            c(true);
        }
        if (!a5) {
            return -1;
        }
        return 0;
    }

    private void a(n.a aVar) {
        if (aVar != n.a.SDK) {
            this.j.b("C.Auth.RequestTimeout", Integer.toString(16));
            this.j.b("C.Auth.UserAuthRetryUpbound", "0");
        }
    }

    /* access modifiers changed from: package-private */
    public void n() {
        if (this.B) {
            o.a(this.f, this, this.r, this.s, this.p, this.w, this.k, this.c);
            return;
        }
        p.a(this.w);
    }

    public int o() {
        int i2;
        if (this.o == null) {
            return 1000;
        }
        synchronized (this) {
            if (this.B) {
                i2 = 1002;
            } else {
                this.B = this.j.e();
                if (this.B) {
                    i2 = 0;
                } else {
                    i2 = 1001;
                }
            }
        }
        if (i2 != 0) {
            return i2;
        }
        n();
        return i2;
    }

    public void p() {
        if (this.o != null) {
            this.q.a();
            synchronized (this) {
                if (this.B) {
                    this.j.f();
                    this.B = false;
                    n();
                }
            }
        }
    }

    public int a(int i2) {
        aa.a(this.x, true);
        if (this.o == null) {
            return 1000;
        }
        if (this.g == n.a.SDK) {
            return 1003;
        }
        if (!this.j.f(i2)) {
            return 1001;
        }
        o();
        return 0;
    }

    public void d() {
        aa.a(this.x, false);
        if (this.o != null && this.g != n.a.SDK) {
            p();
            this.j.o();
        }
    }

    public boolean c() {
        return this.j.d();
    }

    public void b() {
        B();
    }

    public synchronized void a(AccelSwitchListener accelSwitchListener) {
        this.w = accelSwitchListener;
    }

    public void a(VPNStateListener vPNStateListener) {
        this.x = vPNStateListener;
    }

    public void a(@Nullable UserStateListener userStateListener) {
        this.y = userStateListener;
    }

    public void a(@NonNull UserInfo userInfo, @Nullable XunyouUserStateCallback xunyouUserStateCallback, @Nullable Object obj, int i2) {
        this.u = userInfo;
        com.subao.common.i.j.b(userInfo.getUserId());
        this.v = null;
        int a2 = this.e.a(userInfo, new ag(xunyouUserStateCallback), obj);
        if (com.subao.common.d.a("SubaoGame")) {
            Log.d("SubaoGame", String.format("setUserToken(), callKey=%d, strategy=%d", new Object[]{Integer.valueOf(a2), Integer.valueOf(i2)}));
        }
        this.j.a(a2, userInfo.getUserId(), userInfo.getToken(), userInfo.getAppId(), i2);
    }

    public void a(String str, byte[] bArr, XunyouTokenStateListener xunyouTokenStateListener) {
        this.E.a(str, bArr, xunyouTokenStateListener);
    }

    public void a(long j2, XunyouUserStateCallback xunyouUserStateCallback, Object obj) {
        if (com.subao.common.d.a("SubaoGame")) {
            Log.d("SubaoGame", String.format(com.subao.common.e.n.b, "refreshXunyouUserState(%d)", new Object[]{Long.valueOf(j2)}));
        }
        UserInfo userInfo = this.u;
        if (userInfo == null) {
            new ag(xunyouUserStateCallback).onXunyouUserState((UserInfo) null, obj, 1004, 0, "");
        } else {
            a(userInfo, xunyouUserStateCallback, obj, 0);
        }
    }

    public void a(UserInfo userInfo, long j2, QueryOriginUserStateCallback queryOriginUserStateCallback, Object obj) {
        if (com.subao.common.d.a("SubaoGame")) {
            Log.d("SubaoGame", String.format(com.subao.common.e.n.b, "queryOriginUserState(%s, %d)", new Object[]{userInfo.toString(), Long.valueOf(j2)}));
        }
        com.subao.common.b.g.a(this.d, this.l.i(), l(), userInfo, 15000, queryOriginUserStateCallback, obj);
    }

    public byte[] q() {
        return this.E.a();
    }

    public void a(@NonNull UserInfo userInfo, int i2, @NonNull QueryThirdPartyAuthInfoCallback queryThirdPartyAuthInfoCallback) {
        int i3;
        if (i2 <= 0) {
            i3 = 8000;
        } else {
            i3 = i2;
        }
        com.subao.common.m.d.a(new com.subao.common.b.i(l(), this.l.i(), this.b, userInfo, i3, queryThirdPartyAuthInfoCallback));
    }

    public boolean a(@Nullable RequestTrialCallback requestTrialCallback) {
        if (this.u != null) {
            com.subao.common.b.e eVar = this.v;
            if (eVar != null) {
                return com.subao.common.b.a.a(this.a, this.l.i(), eVar, requestTrialCallback);
            }
            if (requestTrialCallback == null) {
                return false;
            }
            requestTrialCallback.onRequestTrialResult(1009);
            return false;
        } else if (requestTrialCallback == null) {
            return false;
        } else {
            requestTrialCallback.onRequestTrialResult(1004);
            return false;
        }
    }

    public void a(@NonNull QueryProductCallback queryProductCallback, boolean z2) {
        a(queryProductCallback, z2, this.l.h(), this.D);
    }

    /* access modifiers changed from: package-private */
    public void a(@NonNull QueryProductCallback queryProductCallback, boolean z2, @Nullable ai aiVar, @NonNull com.subao.common.e.i<ai, ProductList> iVar) {
        ProductList productList = null;
        if (z2) {
            iVar.a(aiVar, null);
        } else {
            productList = iVar.a(aiVar);
        }
        if (productList == null) {
            com.subao.common.m.d.a(new com.subao.common.c.e(l(), aiVar, new t(queryProductCallback, aiVar, iVar)));
        } else {
            queryProductCallback.onQueryProductResult(0, productList);
        }
    }

    public void a(@Nullable String str, @NonNull String str2, int i2, @NonNull RequestBuyCallback requestBuyCallback) {
        a(str, str2, i2, requestBuyCallback, this.l.h());
    }

    private void a(@Nullable String str, @NonNull String str2, int i2, @NonNull RequestBuyCallback requestBuyCallback, @Nullable ai aiVar) {
        if (this.u == null) {
            requestBuyCallback.onRequestBuyResult(1004, (RequestBuyResult) null);
            return;
        }
        com.subao.common.b.e eVar = this.v;
        if (eVar == null) {
            requestBuyCallback.onRequestBuyResult(1009, (RequestBuyResult) null);
        } else if (TextUtils.isEmpty(str) || str.equals(eVar.a)) {
            com.subao.common.m.d.a(new com.subao.common.c.a(l(), aiVar, eVar.b, str2, i2, requestBuyCallback));
        } else {
            requestBuyCallback.onRequestBuyResult(1009, (RequestBuyResult) null);
        }
    }

    private boolean a(@NonNull r.a aVar) {
        com.subao.common.b.e eVar = this.v;
        if (eVar == null) {
            return false;
        }
        com.subao.common.b.a.a(j(), eVar, aVar, true);
        return true;
    }

    public void a(@NonNull QuerySignCouponsCallback querySignCouponsCallback) {
        if (!a((r.a) new j(querySignCouponsCallback))) {
            querySignCouponsCallback.onQuerySignCouponsResult(1004, (List<String>) null);
        }
    }

    public void a(@NonNull QueryTwiceTrialStateCallback queryTwiceTrialStateCallback) {
        if (!a((r.a) new k(queryTwiceTrialStateCallback))) {
            queryTwiceTrialStateCallback.onQueryTwiceTrailStateResult(1004, (String) null, 0);
        }
    }

    public void a(@NonNull String str, @Nullable RequestTwiceTrialCallback requestTwiceTrialCallback) {
        int i2;
        String str2;
        if (!TextUtils.isEmpty(str)) {
            com.subao.common.b.e eVar = this.v;
            if (eVar != null) {
                new com.subao.common.e.t(j(), new s.d(eVar.a, eVar.b), str, requestTwiceTrialCallback).a(com.subao.common.m.d.a());
            } else if (requestTwiceTrialCallback != null) {
                UserInfo userInfo = this.u;
                if (userInfo != null) {
                    i2 = 1009;
                    str2 = userInfo.getUserId();
                } else {
                    i2 = 1004;
                    str2 = null;
                }
                requestTwiceTrialCallback.onRequestTwiceTrialResult(i2, str2);
            }
        } else if (requestTwiceTrialCallback != null) {
            requestTwiceTrialCallback.onRequestTwiceTrialResult(1012, str);
        }
    }

    public int r() {
        int a2 = this.q.a(this.f);
        if (this.g == n.a.SDK || d(a2) == 0) {
            return a2;
        }
        try {
            ParcelFileDescriptor.fromFd(a2).close();
        } catch (IOException e2) {
        }
        throw new b.d(EFvrECode._ERR_FVR_UNIID_EXCEPTION);
    }

    /* access modifiers changed from: package-private */
    public void c(int i2) {
        this.F.a(i2);
    }

    public int d(int i2) {
        e a2;
        e.a aVar = this.C;
        if (aVar == null || (a2 = aVar.a()) == null || !a2.b() || a2.protect(i2)) {
            return 0;
        }
        a2.a();
        return 8002;
    }

    public int s() {
        return this.t;
    }

    public void e(int i2) {
        this.j.b(i2);
    }

    public void f(int i2) {
        this.j.a(i2);
    }

    public int t() {
        int h2 = this.j.h();
        if (this.l.f() != null) {
            return this.l.f().intValue();
        }
        boolean c2 = c();
        j.a a2 = this.d.a();
        if (!d.a.a()) {
            return h2;
        }
        this.r.a(this.s.e().a(com.subao.common.i.j.a(), h2, a2.g, c2));
        return h2;
    }

    public void g(int i2) {
        this.t = i2;
        this.j.a(0, "key_free_flow_type", i2);
    }

    public void a(String str, int i2) {
        this.j.a(str, i2);
    }

    public String u() {
        return this.j.i();
    }

    public String h(int i2) {
        String appId;
        String str;
        this.j.a(0, "key_mobile_switch_state", com.subao.common.j.i.a(this.f).a());
        String c2 = this.j.c(i2);
        if (!TextUtils.isEmpty(c2)) {
            String e2 = this.l.e();
            if (!TextUtils.isEmpty(e2)) {
                c2 = a(c2, e2, this.a, com.subao.common.e.n.c);
            }
        }
        if (TextUtils.isEmpty(c2)) {
            UserInfo userInfo = this.u;
            if (userInfo == null) {
                appId = null;
                str = null;
            } else {
                String userId = userInfo.getUserId();
                appId = userInfo.getAppId();
                str = userId;
            }
            c2 = a(appId, str);
        }
        com.subao.common.d.a("SubaoGame", c2);
        return c2;
    }

    public void c(boolean z2) {
        this.j.a(0, "key_front_game_uid", z2 ? this.h : -1);
    }

    public String v() {
        return this.j.n();
    }

    public void a(String str) {
        this.j.b(0, "key_game_server_id", str);
    }

    public void i(int i2) {
        this.j.a(0, "key_sdk_player_level", i2);
    }

    public int w() {
        return this.j.k();
    }

    public long x() {
        return this.j.l();
    }

    public void b(String str, String str2) {
    }

    public boolean y() {
        return this.j.m();
    }

    public int z() {
        return this.d.a().g;
    }

    public void A() {
        this.j.c();
    }

    public void a(String str, String str2, int i2) {
        this.j.a(str, str2, i2);
    }

    public String j(int i2) {
        return this.j.d(i2);
    }

    public void a(int i2, boolean z2) {
        this.j.a(i2, z2);
    }

    public void c(String str, String str2) {
        this.j.b(0, "key_set_round_openid", str);
        this.j.b(0, "key_set_round_pvpid", str2);
    }

    public void b(String str) {
        this.j.b(0, "key_pay_type_white_list", str);
    }

    public void d(boolean z2) {
        UserInfo userInfo = this.u;
        a(z2, userInfo == null ? null : userInfo.getUserId());
    }

    /* access modifiers changed from: package-private */
    public void a(boolean z2, String str) {
        boolean z3 = true;
        this.j.a(0, "key_user_wifi_accel", z2 ? 1 : 0);
        if (TextUtils.isEmpty(str)) {
            z3 = false;
        }
        if (d.a.b()) {
            HashMap hashMap = new HashMap(2);
            if (!z3) {
                str = "(none)";
            }
            hashMap.put("userId", str);
            hashMap.put("switch", z2 ? "on" : "off");
            this.r.a(new m.a("set_wa_switch", hashMap));
        }
    }

    @SuppressLint({"ObsoleteSdkInt"})
    public synchronized int B() {
        List a2;
        int i2 = 0;
        synchronized (this) {
            boolean a3 = com.subao.common.d.a("SubaoGame");
            if (Build.VERSION.SDK_INT < 14) {
                i2 = ConnectionsStatusCodes.STATUS_BLUETOOTH_ERROR;
            } else {
                e.a aVar = this.C;
                if (aVar == null) {
                    i2 = ConnectionsStatusCodes.STATUS_ENDPOINT_UNKNOWN;
                } else {
                    e a4 = aVar.a();
                    if (a4 != null) {
                        if (a3) {
                            Log.d("SubaoGame", "Service already exists, call startProxy() ...");
                        }
                        al b2 = b(false);
                        if (b2 == null) {
                            a2 = null;
                        } else {
                            a2 = b2.a(new al.c(), false);
                        }
                        i2 = a4.a(a2);
                    } else if (!aVar.a(this.f)) {
                        i2 = ConnectionsStatusCodes.STATUS_ALREADY_HAVE_ACTIVE_STRATEGY;
                    }
                }
            }
            if (a3) {
                Log.d("SubaoGame", "openVPN() return " + i2);
            }
        }
        return i2;
    }

    private e I() {
        e.a aVar = this.C;
        if (aVar != null) {
            return aVar.a();
        }
        return null;
    }

    public synchronized void C() {
        e I2 = I();
        if (I2 != null) {
            I2.a();
        }
    }

    public boolean D() {
        e I2 = I();
        return I2 != null && I2.b();
    }

    public void a(@NonNull GameInformation gameInformation, long j2, AccelerateGameCallback accelerateGameCallback) {
        this.j.a(0, gameInformation.getUid(), b(gameInformation.getUid()), gameInformation.getNodeTag(), gameInformation.isForeign());
        if (accelerateGameCallback != null) {
            a.a(this, gameInformation, j2, accelerateGameCallback);
        }
    }

    public String E() {
        return com.subao.common.j.d.b(this.f, this.d.a());
    }

    public boolean k(int i2) {
        return this.j.e(i2);
    }

    public int F() {
        return this.j.p();
    }

    public String c(@NonNull String str) {
        return this.j.a("accel_effect", str);
    }

    public int a(String str, int i2, String str2, int i3, int i4, int i5, DetectTimeDelayCallback detectTimeDelayCallback) {
        int a2 = this.G.a(detectTimeDelayCallback);
        int a3 = this.j.a(a2, str, i2, str2, i3, i4, i5);
        if (a3 != 0) {
            this.G.a(a2);
        }
        return a3;
    }

    public int a(DetectTimeDelayCallback detectTimeDelayCallback) {
        int a2 = this.G.a(detectTimeDelayCallback);
        int g2 = this.j.g(a2);
        if (g2 != 0) {
            this.G.a(a2);
        }
        return g2;
    }

    public void a(int i2, final String str) {
        boolean a2 = com.subao.common.d.a("SubaoData");
        if (a2) {
            Log.d("SubaoData", String.format(com.subao.common.e.n.b, "onDetectTimeDelay, callKey = %d, result = %s", new Object[]{Integer.valueOf(i2), str}));
        }
        final DetectTimeDelayCallback a3 = this.G.a(i2);
        if (a3 == null) {
            com.subao.common.d.a("SubaoData", "onDetectTimeDelay, not found callback, return");
            return;
        }
        if (a2) {
            Log.d("SubaoData", String.format("onDetectTimeDelay, result = %s", new Object[]{str}));
        }
        com.subao.common.m.b.a().a(new Runnable() {
            public void run() {
                a3.onTimeDelay(str);
            }
        });
    }

    public void a(QueryActivitiesCallback queryActivitiesCallback, boolean z2) {
        a(queryActivitiesCallback, z2, this.H);
    }

    /* access modifiers changed from: package-private */
    public void a(QueryActivitiesCallback queryActivitiesCallback, boolean z2, @NonNull com.subao.common.e.i<String, String> iVar) {
        if (!z2) {
            String str = null;
            if (this.u != null) {
                str = iVar.a(this.u.getUserId());
            }
            if (!TextUtils.isEmpty(str)) {
                queryActivitiesCallback.onResult(0, true, str);
                return;
            }
        }
        this.j.a(0, this.I.a(queryActivitiesCallback));
    }

    public void a(int i2, int i3, boolean z2, String str) {
        QueryActivitiesCallback a2 = this.I.a(i2);
        if (a2 != null) {
            a2.onResult(i3, z2, str);
        }
        UserInfo userInfo = this.u;
        if (i3 == 0 && userInfo != null) {
            this.H.a(userInfo.getUserId(), str);
        }
    }

    public void a(String str, @NonNull ActivityExposureCallback activityExposureCallback) {
        this.j.a(0, this.J.a(activityExposureCallback), str);
    }

    public void a(int i2, int i3) {
        ActivityExposureCallback a2 = this.J.a(i2);
        if (a2 != null) {
            a2.onResult(i3);
        }
    }

    public int a(long j2) {
        return this.j.a(j2);
    }

    public void l(int i2) {
        this.j.h(i2);
    }

    public void e(boolean z2) {
        this.j.b("C.LifeLinkSwitch.Open", z2 ? "1" : "0");
    }

    /* compiled from: EngineWrapper */
    private static class v extends Thread {
        private com.subao.common.g.c a;
        private volatile boolean b;

        v(com.subao.common.g.c cVar) {
            this.a = cVar;
        }

        public void run() {
            while (!this.b) {
                this.a.g();
            }
            this.a = null;
        }

        /* access modifiers changed from: package-private */
        public void a() {
            this.b = true;
        }
    }

    /* compiled from: EngineWrapper */
    private static class ad implements ac {
        private int a;

        ad(int i) {
            this.a = i;
        }

        public int a(Context context) {
            throw new b.d(this.a);
        }

        public void a() {
        }

        public String toString() {
            return String.format(com.subao.common.e.n.b, "[WiFiAccelError #%d", new Object[]{Integer.valueOf(this.a)});
        }
    }

    /* compiled from: EngineWrapper */
    private static class af {
        private final c a;
        private final com.subao.common.g.c b;
        private ac c;
        private volatile boolean d;

        af(c cVar, com.subao.common.g.c cVar2) {
            this.a = cVar;
            this.b = cVar2;
            cVar.m.d();
        }

        public void a() {
            ac acVar;
            com.subao.common.d.a("SubaoParallel", "reset");
            synchronized (this) {
                acVar = this.c;
                this.c = null;
            }
            if (acVar != null) {
                acVar.a();
            }
        }

        public int a(Context context) {
            ac acVar;
            synchronized (this) {
                acVar = this.c;
            }
            if (acVar == null) {
                acVar = b();
                synchronized (this) {
                    if (this.c == null) {
                        this.c = acVar;
                    }
                }
            }
            try {
                int a2 = acVar.a(context);
                this.d = false;
                return a2;
            } catch (b.d e) {
                switch (e.a()) {
                    case 2007:
                        if (!this.d) {
                            this.d = true;
                            a();
                            break;
                        }
                        break;
                    case 2009:
                        a();
                        break;
                }
                throw e;
            }
        }

        private ac b() {
            ac adVar;
            try {
                adVar = new ae(this.a.f, this.b);
            } catch (b.d e) {
                adVar = new ad(e.a());
            }
            com.subao.common.d.a("SubaoParallel", String.format("Create '%s'", new Object[]{adVar.toString()}));
            return adVar;
        }
    }

    /* compiled from: EngineWrapper */
    private static class ae implements ac {
        private final com.subao.common.k.a a;

        ae(Context context, com.subao.common.g.c cVar) {
            this.a = com.subao.common.k.a.a(context, new e(context, cVar));
        }

        public void a() {
            this.a.a();
        }

        public int a(Context context) {
            if (com.subao.common.e.y.d()) {
                return this.a.a(context);
            }
            throw new b.d(2006);
        }

        public String toString() {
            return "[WiFiAccelImpl]";
        }
    }

    /* compiled from: EngineWrapper */
    private static class o implements Runnable {
        private final Context a;
        private final a b;
        private final com.subao.common.i.f c;
        private final com.subao.common.i.h d;
        private final int e;
        private final AccelSwitchListener f;
        private final String g;
        private final String h;

        private o(Context context, a aVar, com.subao.common.i.f fVar, com.subao.common.i.h hVar, int i, AccelSwitchListener accelSwitchListener, String str, String str2) {
            this.a = context;
            this.b = aVar;
            this.c = fVar;
            this.d = hVar;
            this.e = i;
            this.f = accelSwitchListener;
            this.g = str;
            this.h = str2;
        }

        static void a(Context context, a aVar, com.subao.common.i.f fVar, com.subao.common.i.h hVar, int i, AccelSwitchListener accelSwitchListener, String str, String str2) {
            o oVar = new o(context, aVar, fVar, hVar, i, accelSwitchListener, str, str2);
            if (com.subao.common.n.h.b()) {
                oVar.run();
            } else {
                com.subao.common.m.b.a().a(oVar);
            }
        }

        private String a() {
            String c2 = aj.b().c();
            if (aj.a(c2)) {
                com.subao.common.d.a("SubaoMessage", "SubaoId already exists, do not send INSTALLATION message.");
                return c2;
            }
            com.subao.common.d.a("SubaoMessage", "No SubaoId found, make INSTALLATION message.");
            this.c.a(this.d.e().a(System.currentTimeMillis() / 1000, n.a.a(this.a, this.g, this.h)));
            return null;
        }

        public void run() {
            if (this.f != null) {
                this.f.onAccelSwitch(true);
            }
            if (a() != null) {
                if (y.c()) {
                    y.a(this.b, this.c, this.e);
                } else {
                    y.a(this.c, this.e);
                }
            }
        }
    }

    /* compiled from: EngineWrapper */
    private static class p implements Runnable {
        private final AccelSwitchListener a;

        p(AccelSwitchListener accelSwitchListener) {
            this.a = accelSwitchListener;
        }

        static void a(AccelSwitchListener accelSwitchListener) {
            p pVar = new p(accelSwitchListener);
            if (com.subao.common.n.h.b()) {
                pVar.run();
            } else {
                com.subao.common.m.b.a().a(pVar);
            }
        }

        public void run() {
            if (this.a != null) {
                this.a.onAccelSwitch(false);
            }
            y.b();
        }
    }

    /* compiled from: EngineWrapper */
    private static class y {
        private static a a;

        static void a() {
            com.subao.common.m.b.a().a(a, BATrackerConst.TRACKER_WAKE_UP_INTERVAL);
        }

        static void a(a aVar, com.subao.common.i.f fVar, int i) {
            if (a == null) {
                a = new a(aVar, fVar, i);
                a();
            }
        }

        static void b() {
            if (a != null) {
                com.subao.common.m.b.a().b(a);
                a = null;
            }
        }

        static boolean c() {
            return com.subao.common.n.c.a() == com.subao.common.e.k.a().c();
        }

        static void a(com.subao.common.i.f fVar, int i) {
            fVar.a(i, 0, (List<com.subao.common.i.k>) null);
        }

        /* compiled from: EngineWrapper */
        private static class a implements Runnable {
            private final a a;
            private final com.subao.common.i.f b;
            private final int c;

            a(a aVar, com.subao.common.i.f fVar, int i) {
                this.a = aVar;
                this.b = fVar;
                this.c = i;
            }

            public void run() {
                if (this.a.c() && aj.a(com.subao.common.i.j.b()) && !y.c()) {
                    y.a(this.b, this.c);
                }
                y.a();
            }
        }
    }

    /* compiled from: EngineWrapper */
    private static class r extends z.a {
        r(String str, String str2, ai aiVar, com.subao.common.j.j jVar) {
            super(str, str2, aiVar, jVar);
        }

        @NonNull
        public com.subao.common.f.c a(String str) {
            return com.subao.common.f.d.a(com.subao.common.f.a.a(str));
        }
    }

    /* compiled from: EngineWrapper */
    private class n implements h.a {
        private n() {
        }

        public void a(Context context, j.a aVar) {
            if (aVar == j.a.UNKNOWN) {
                aVar = j.a.MOBILE_4G;
            }
            c.this.j.a(0, "key_net_state", aVar.g);
            switch (aVar) {
                case MOBILE_3G:
                case MOBILE_4G:
                    byte[] b = com.subao.common.j.k.b((k.a) null);
                    if (b != null) {
                        c.this.j.b(0, "key_mobile_private_ip", com.subao.common.j.e.a(b));
                        break;
                    }
                    break;
            }
            g d = c.this.n;
            if (d != null) {
                d.b(aVar);
            }
            if (aVar != j.a.DISCONNECT) {
                f.a(context, c.this.j);
            }
        }
    }

    /* compiled from: EngineWrapper */
    static class x {
        static void a(Context context, com.subao.common.m.a aVar, int i, com.subao.common.g.c cVar, int i2) {
            new a(i, cVar, i2).a(context, aVar);
        }

        /* compiled from: EngineWrapper */
        private static class a implements o.a, Runnable {
            static final /* synthetic */ boolean a = (!c.class.desiredAssertionStatus());
            private final int b;
            private final com.subao.common.g.c c;
            private final int d;
            private com.subao.common.j.o e;
            private int f = -1;

            a(int i, com.subao.common.g.c cVar, int i2) {
                this.b = i;
                this.c = cVar;
                this.d = i2;
            }

            public void a(Context context, com.subao.common.m.a aVar) {
                if (a || this.e == null) {
                    this.e = new com.subao.common.j.p(this);
                    this.e.a(context);
                    aVar.a(this, 1000);
                    return;
                }
                throw new AssertionError();
            }

            public void a(int i) {
                this.f = i;
            }

            public void run() {
                int i;
                this.e.a();
                if (this.f < 0) {
                    i = this.b;
                } else {
                    i = this.f + 2100;
                }
                c.b(this.c, this.d, i, -1);
            }
        }
    }

    /* compiled from: EngineWrapper */
    static class e implements a.C0017a {
        private final Context a;
        private final com.subao.common.g.c b;

        e(Context context, com.subao.common.g.c cVar) {
            this.a = context.getApplicationContext();
            this.b = cVar;
        }

        public void a(boolean z) {
            this.b.a(0, "key_cellular_state_change", z ? 1 : 0);
            if (com.subao.common.d.a("SubaoParallel")) {
                Log.d("SubaoParallel", z ? "Cellular available" : "Cellular lost");
                Log.d("SubaoParallel", "Mobile Switch State: " + com.subao.common.j.i.a(this.a).a());
            }
        }
    }

    /* compiled from: EngineWrapper */
    static class q extends com.subao.common.j.n {
        private int c;
        /* access modifiers changed from: private */
        public final String d;
        /* access modifiers changed from: private */
        public final int e;
        /* access modifiers changed from: private */
        public final String f;

        public q(d.b bVar, int i, String str, int i2, String str2) {
            super(bVar, i, 0);
            this.d = str;
            this.e = i2;
            this.f = str2;
        }

        /* access modifiers changed from: protected */
        public String a() {
            return null;
        }

        /* access modifiers changed from: protected */
        public void a(int i, byte[] bArr) {
            b(i);
        }

        /* access modifiers changed from: protected */
        public void b(int i, byte[] bArr) {
            c();
            b(i);
        }

        private void b(int i) {
            com.subao.common.d.a("SubaoNet", "OrdersResponseCallbackRetry code: " + i);
        }

        private void c() {
            if (this.c < 5) {
                com.subao.common.m.b.a().a(new Runnable() {
                    public void run() {
                        com.subao.common.b.a.a(q.this.d, q.this.e, q.this.f, (com.subao.common.j.n) q.this);
                    }
                }, Math.round(Math.pow(2.0d, (double) this.c) * 5000.0d));
                this.c++;
            }
        }
    }

    /* compiled from: EngineWrapper */
    static class w implements Runnable {
        @NonNull
        private final c a;
        @NonNull
        private final com.subao.common.g.c b;
        @NonNull
        private final com.subao.common.m.a c;
        private final int d;
        @NonNull
        private final a e;
        private int f;

        /* compiled from: EngineWrapper */
        interface a {
            void a();
        }

        w(@NonNull c cVar, @NonNull com.subao.common.g.c cVar2, int i, @NonNull com.subao.common.m.a aVar, @NonNull a aVar2) {
            this.a = cVar;
            this.b = cVar2;
            this.c = aVar;
            this.d = i;
            this.e = aVar2;
        }

        public void run() {
            if (a()) {
                this.e.a();
            }
        }

        private boolean a() {
            int i;
            int i2 = 0;
            this.f++;
            try {
                i = this.a.r();
            } catch (b.d e2) {
                if (this.f >= 4 || !com.subao.common.b.a(e2.a())) {
                    i2 = e2.a();
                    i = -1;
                } else {
                    if (com.subao.common.d.a("SubaoParallel")) {
                        Log.d("SubaoParallel", String.format(com.subao.common.e.n.b, "Request mobile fd error #%d, retry after 1.5 seconds", new Object[]{Integer.valueOf(e2.a())}));
                    }
                    this.c.a(this, DeviceDetectUtil.FIT_MEMORY);
                    return false;
                }
            }
            if (i2 == 2007 || i2 == 2008) {
                x.a(this.a.i(), com.subao.common.m.b.a(), i2, this.b, this.d);
            } else {
                c.b(this.b, this.d, i2, i);
            }
            return true;
        }
    }

    /* compiled from: EngineWrapper */
    class m {
        /* access modifiers changed from: private */
        public final Queue<Integer> b = new LinkedList();
        private final com.subao.common.m.a c = com.subao.common.m.b.a();

        m() {
        }

        public void a(int i) {
            boolean z = true;
            synchronized (this.b) {
                this.b.add(Integer.valueOf(i));
                if (this.b.size() != 1) {
                    z = false;
                }
            }
            if (z) {
                b(i);
            }
        }

        /* access modifiers changed from: private */
        public void b(int i) {
            this.c.a(new w(c.this, c.this.j, i, this.c, new w.a() {
                public void a() {
                    Integer num;
                    synchronized (m.this.b) {
                        m.this.b.poll();
                        num = (Integer) m.this.b.peek();
                    }
                    if (num != null) {
                        m.this.b(num.intValue());
                    }
                }
            }));
        }
    }

    /* compiled from: EngineWrapper */
    static class l {
        static int a(c cVar) {
            if (cVar.g()) {
                return 1;
            }
            if (cVar.l.b()) {
                return -1;
            }
            return 0;
        }

        static void a(com.subao.common.g.c cVar) {
            if (com.subao.common.f.b.b() || com.subao.common.d.a("SubaoProxy")) {
                cVar.c();
            }
        }

        static void a(com.subao.common.g.a aVar) {
            if (aVar == com.subao.common.g.a.VPN) {
                new u().start();
            } else {
                VPNJni.proxyLoop(0, true);
            }
        }

        static void a(com.subao.common.g.c cVar, ah ahVar) {
            ai i = ahVar.i();
            a(cVar, i, "C.AuthRequestParams");
            a(cVar, i, "C.UserStateRequestParams");
            a(cVar, i, "C.UserConfigRequestParams");
            a(cVar, ahVar.j(), "C.DroneRequestParams");
        }

        private static void a(com.subao.common.g.c cVar, ai aiVar, String str) {
            if (aiVar != null) {
                cVar.b(str + ".Protocol", aiVar.a);
                cVar.b(str + ".Host", aiVar.b);
                int i = aiVar.c;
                if (i <= 0) {
                    i = HttpHost.DEFAULT_SCHEME_NAME.equals(aiVar.b) ? 80 : 443;
                }
                cVar.b(str + ".Port", Integer.toString(i));
            }
        }

        static void a(Context context, com.subao.common.g.c cVar, n.a aVar) {
            com.subao.common.e.x xVar = new com.subao.common.e.x();
            if (xVar.a(context, aVar)) {
                cVar.a(0, "key_inject", xVar.b());
            }
        }

        static void a(com.subao.common.g.c cVar, int i) {
            if (i >= 0) {
                cVar.a(i);
            }
        }

        static void a(Context context, com.subao.common.g.c cVar, com.subao.common.j.l lVar, String str) {
            cVar.b(0, "key_hook_module", str);
            ApplicationInfo applicationInfo = context.getApplicationInfo();
            String a = com.subao.common.n.e.a(context, applicationInfo);
            int i = applicationInfo.uid;
            String packageName = context.getPackageName();
            if (a == null) {
                a = "";
            }
            cVar.a(new ak(i, false, packageName, a, 0, lVar, (Iterable<com.subao.common.e.p>) null, (String) null, (Iterable<b.c>) null));
        }

        static v b(com.subao.common.g.c cVar) {
            v vVar = new v(cVar);
            vVar.start();
            return vVar;
        }
    }

    /* compiled from: EngineWrapper */
    private static abstract class ab<C> implements Runnable {
        private final long a;
        private c b;
        private C c;

        /* access modifiers changed from: package-private */
        public abstract void a(C c2, boolean z);

        /* access modifiers changed from: package-private */
        public abstract boolean a(c cVar);

        ab(c cVar, long j, C c2) {
            this.b = cVar;
            this.a = Math.max(1000, j);
            this.c = c2;
        }

        public void run() {
            long G = this.a + c.H();
            boolean z = true;
            while (true) {
                SystemClock.sleep(500);
                if (!a(this.b)) {
                    if (c.H() >= G) {
                        break;
                    }
                } else {
                    z = false;
                    break;
                }
            }
            a(this.c, z);
            a();
        }

        private void a() {
            this.b = null;
            this.c = null;
        }
    }

    /* compiled from: EngineWrapper */
    private static class a extends ab<AccelerateGameCallback> {
        private final GameInformation a;
        private boolean b;

        static void a(c cVar, GameInformation gameInformation, long j, AccelerateGameCallback accelerateGameCallback) {
            com.subao.common.m.d.a().execute(new a(cVar, gameInformation, j, accelerateGameCallback));
        }

        private a(c cVar, GameInformation gameInformation, long j, AccelerateGameCallback accelerateGameCallback) {
            super(cVar, j, accelerateGameCallback);
            this.a = gameInformation;
        }

        /* access modifiers changed from: package-private */
        public boolean a(c cVar) {
            boolean k = cVar.k(this.a.getUid());
            this.b = k;
            return k;
        }

        /* access modifiers changed from: package-private */
        public void a(AccelerateGameCallback accelerateGameCallback, boolean z) {
            accelerateGameCallback.onAccelerateGameResult(this.a, this.b ? 0 : -1);
        }
    }

    /* compiled from: EngineWrapper */
    static class aa implements Runnable {
        private final VPNStateListener a;
        private final boolean b;

        aa(VPNStateListener vPNStateListener, boolean z) {
            this.a = vPNStateListener;
            this.b = z;
        }

        static void a(VPNStateListener vPNStateListener, boolean z) {
            if (vPNStateListener != null) {
                if (com.subao.common.n.h.b()) {
                    vPNStateListener.onVPNStateChanged(z);
                } else {
                    com.subao.common.m.b.a().a(new aa(vPNStateListener, z));
                }
            }
        }

        public void run() {
            this.a.onVPNStateChanged(this.b);
        }
    }

    /* compiled from: EngineWrapper */
    private static class ag implements XunyouUserStateCallback {
        private final XunyouUserStateCallback a;

        ag(XunyouUserStateCallback xunyouUserStateCallback) {
            this.a = xunyouUserStateCallback;
        }

        public void onXunyouUserState(UserInfo userInfo, Object obj, int i, int i2, String str) {
            if (com.subao.common.d.a("SubaoAuth")) {
                com.subao.common.d.a("SubaoAuth", String.format(com.subao.common.e.n.b, "onXunyouUserState(): error=%d, userState=%d, vipTime=%s", new Object[]{Integer.valueOf(i), Integer.valueOf(i2), str}));
            }
            if (this.a != null) {
                this.a.onXunyouUserState(userInfo, obj, i, i2, str);
            }
        }
    }

    /* compiled from: EngineWrapper */
    class h implements g.a {
        private final com.subao.common.m.a b;

        h(com.subao.common.m.a aVar) {
            this.b = aVar;
        }

        public boolean a(Runnable runnable) {
            return this.b.a(runnable);
        }

        public boolean a(Runnable runnable, long j) {
            return this.b.a(runnable, j);
        }

        public void b(Runnable runnable) {
            this.b.b(runnable);
        }

        public j.a a() {
            return c.this.d.a();
        }

        public void run() {
            c.this.f().b(c.this.f);
        }
    }

    /* compiled from: EngineWrapper */
    static class g {
        /* access modifiers changed from: private */
        public final a a;
        /* access modifiers changed from: private */
        public final long b;
        private final b c;
        private final boolean d;

        /* compiled from: EngineWrapper */
        interface a extends com.subao.common.m.a, Runnable {
            j.a a();
        }

        private g(a aVar, long j) {
            this.a = aVar;
            this.b = j <= 0 ? 18000000 : j;
            this.c = new b();
            this.d = a(aVar.a());
        }

        static g a(a aVar, long j) {
            g gVar = new g(aVar, j);
            gVar.a.a(gVar.c, gVar.b);
            return gVar;
        }

        static boolean a(j.a aVar) {
            switch (aVar) {
                case MOBILE_3G:
                case MOBILE_4G:
                case WIFI:
                case UNKNOWN:
                    return true;
                default:
                    return false;
            }
        }

        static long a() {
            return com.subao.common.e.z.f();
        }

        /* access modifiers changed from: package-private */
        public void b(j.a aVar) {
            if (!this.d && a(aVar)) {
                this.a.b(this.c);
                this.a.a(this.c);
            }
        }

        /* compiled from: EngineWrapper */
        class b implements Runnable {
            private long b = (g.a() - 18000000);

            b() {
            }

            public void run() {
                boolean a2 = com.subao.common.d.a("SubaoData");
                if (a2) {
                    Log.d("SubaoData", "[DataAutoRefresher] run");
                }
                long a3 = g.a();
                long j = a3 - this.b;
                if (j < g.this.b) {
                    if (a2) {
                        Log.d("SubaoData", "[DataAutoRefresher] Elapsed from last execute: " + j);
                    }
                    g.this.a.a(this, g.this.b - j);
                } else if (!g.a(g.this.a.a())) {
                    if (a2) {
                        Log.d("SubaoData", "[DataAutoRefresher] Network is bad");
                    }
                    g.this.a.a(this, g.this.b);
                } else {
                    long g = a3 - com.subao.common.e.z.g();
                    if (g < g.this.b) {
                        if (a2) {
                            Log.d("SubaoData", "[DataAutoRefresher] Elapsed from last download: " + g);
                        }
                        g.this.a.a(this, g.this.b - g);
                        return;
                    }
                    if (a2) {
                        Log.d("SubaoData", "[DataAutoRefresher] do it !!");
                    }
                    this.b = a3;
                    g.this.a.run();
                    g.this.a.a(this, g.this.b);
                }
            }
        }
    }

    /* compiled from: EngineWrapper */
    static class t implements e.a {
        @NonNull
        private final QueryProductCallback a;
        @Nullable
        private final ai b;
        @NonNull
        private final com.subao.common.e.i<ai, ProductList> c;

        t(@NonNull QueryProductCallback queryProductCallback, @Nullable ai aiVar, @NonNull com.subao.common.e.i<ai, ProductList> iVar) {
            this.a = queryProductCallback;
            this.b = aiVar;
            this.c = iVar;
        }

        public void a(int i, @Nullable ProductList productList) {
            int i2 = 0;
            if (com.subao.common.d.a("SubaoData")) {
                Locale locale = com.subao.common.e.n.b;
                Object[] objArr = new Object[2];
                objArr[0] = Integer.valueOf(i);
                objArr[1] = Integer.valueOf(productList == null ? 0 : productList.getCount());
                Log.d("SubaoData", String.format(locale, "QueryProductList result, responseCode is %d, product count is %d", objArr));
            }
            switch (i) {
                case -1:
                    i2 = 1006;
                    break;
                case 200:
                    break;
                default:
                    i2 = 1008;
                    break;
            }
            this.c.a(this.b, i2 == 0 ? productList : null);
            this.a.onQueryProductResult(i2, productList);
        }
    }

    /* renamed from: com.subao.common.a.c$c  reason: collision with other inner class name */
    /* compiled from: EngineWrapper */
    private static class C0000c implements b {
        private C0000c() {
        }

        public void a(String str, byte[] bArr, XunyouTokenStateListener xunyouTokenStateListener) {
        }

        public byte[] a() {
            return null;
        }
    }

    /* compiled from: EngineWrapper */
    private class d implements b {
        private d() {
        }

        public void a(String str, byte[] bArr, XunyouTokenStateListener xunyouTokenStateListener) {
            if (com.subao.common.d.a("SubaoGame")) {
                Log.d("SubaoGame", String.format("setUserToken_FromOtherAppCaller, userId = %s", new Object[]{str}));
            }
            if ((bArr == null || bArr.length == 0) && com.subao.common.d.a("SubaoGame")) {
                Log.d("SubaoGame", "setUserToken_FromOtherAppCaller, jwtToken is null");
            }
        }

        public byte[] a() {
            return com.subao.common.b.a.a();
        }
    }

    /* compiled from: EngineWrapper */
    private static class j implements r.a {
        @NonNull
        private final QuerySignCouponsCallback a;

        j(@NonNull QuerySignCouponsCallback querySignCouponsCallback) {
            this.a = querySignCouponsCallback;
        }

        private static void a(List<String> list, String str) {
            if (list.indexOf(str) < 0) {
                list.add(str);
            }
        }

        public void a(int i, @Nullable List<com.subao.common.e.l> list) {
            if (i != 0) {
                this.a.onQuerySignCouponsResult(i, (List<String>) null);
                return;
            }
            ArrayList arrayList = new ArrayList(3);
            if (list != null && !list.isEmpty()) {
                for (com.subao.common.e.l a2 : list) {
                    String a3 = a2.a("xiaomi");
                    if (a3 != null) {
                        char c = 65535;
                        switch (a3.hashCode()) {
                            case -948167249:
                                if (a3.equals("twoDaysfree")) {
                                    c = 1;
                                    break;
                                }
                                break;
                            case -647590143:
                                if (a3.equals("threeDaysfree")) {
                                    c = 2;
                                    break;
                                }
                                break;
                            case 1447980040:
                                if (a3.equals("dayfree")) {
                                    c = 0;
                                    break;
                                }
                                break;
                        }
                        switch (c) {
                            case 0:
                                a((List<String>) arrayList, "1");
                                break;
                            case 1:
                                a((List<String>) arrayList, "2");
                                break;
                            case 2:
                                a((List<String>) arrayList, "3");
                                break;
                        }
                    }
                }
            }
            this.a.onQuerySignCouponsResult(i, arrayList);
        }
    }

    /* compiled from: EngineWrapper */
    static class k implements r.a {
        @NonNull
        private final QueryTwiceTrialStateCallback a;

        k(@NonNull QueryTwiceTrialStateCallback queryTwiceTrialStateCallback) {
            this.a = queryTwiceTrialStateCallback;
        }

        public void a(int i, @Nullable List<com.subao.common.e.l> list) {
            int i2;
            String str = null;
            if (i != 0 || list == null || list.isEmpty()) {
                this.a.onQueryTwiceTrailStateResult(i, (String) null, 0);
                return;
            }
            Iterator<com.subao.common.e.l> it = list.iterator();
            while (true) {
                if (!it.hasNext()) {
                    i2 = 0;
                    break;
                }
                com.subao.common.e.l next = it.next();
                if ("true".equals(next.a("twicetrial"))) {
                    str = next.a();
                    try {
                        i2 = Integer.parseInt(next.a("accelDays"));
                        break;
                    } catch (NumberFormatException e) {
                        i2 = 0;
                    }
                }
            }
            this.a.onQueryTwiceTrailStateResult(0, str, i2);
        }
    }

    /* compiled from: EngineWrapper */
    private static class s implements ad.a {
        private final com.subao.common.g.c a;

        s(@NonNull com.subao.common.g.c cVar) {
            this.a = cVar;
        }

        public void a(@Nullable String str) {
            if (!TextUtils.isEmpty(str)) {
                this.a.b(0, n.b.a, str);
            }
        }

        public void a(ad.b bVar) {
        }
    }

    /* compiled from: EngineWrapper */
    private static class f implements Runnable {
        private static String c;
        private final Context a;
        private final com.subao.common.g.c b;

        private f(Context context, com.subao.common.g.c cVar) {
            this.a = context;
            this.b = cVar;
        }

        static void a(Context context, com.subao.common.g.c cVar) {
            if (Build.VERSION.SDK_INT >= 21) {
                com.subao.common.m.d.a(new f(context, cVar));
            }
        }

        public void run() {
            String a2 = a(this.a);
            if (!TextUtils.isEmpty(a2) && !a2.equals(c)) {
                c = a2;
                this.b.b(0, "key_local_dns", a2);
            }
        }

        @TargetApi(21)
        private static String a(Context context) {
            LinkProperties linkProperties;
            List<InetAddress> dnsServers;
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService("connectivity");
            if (connectivityManager == null) {
                return null;
            }
            StringBuilder sb = new StringBuilder();
            for (Network network : connectivityManager.getAllNetworks()) {
                NetworkInfo networkInfo = connectivityManager.getNetworkInfo(network);
                if (!(networkInfo == null || !networkInfo.isConnected() || (linkProperties = connectivityManager.getLinkProperties(network)) == null || (dnsServers = linkProperties.getDnsServers()) == null)) {
                    for (InetAddress hostAddress : dnsServers) {
                        String hostAddress2 = hostAddress.getHostAddress();
                        if (!TextUtils.isEmpty(hostAddress2)) {
                            if (sb.length() > 0) {
                                sb.append(',');
                            }
                            sb.append(com.subao.common.j.h.a(networkInfo).g).append("-").append(hostAddress2);
                        }
                    }
                }
            }
            return sb.toString();
        }
    }

    /* compiled from: EngineWrapper */
    private static class z {
        static void a(String str) {
            if (!TextUtils.isEmpty(str)) {
                Matcher matcher = Pattern.compile("(?:https?://)?([^:/]+)").matcher(str);
                if (matcher.find() && matcher.groupCount() >= 1) {
                    String group = matcher.group(1);
                    if (!TextUtils.isEmpty(group)) {
                        com.subao.common.m.d.a(new a(group));
                    }
                }
            }
        }

        /* compiled from: EngineWrapper */
        private static class a implements Runnable {
            private final String a;

            private a(String str) {
                this.a = str;
            }

            public void run() {
                try {
                    InetAddress[] allByName = InetAddress.getAllByName(this.a);
                    if (allByName != null && com.subao.common.d.a("SubaoGame")) {
                        for (InetAddress inetAddress : allByName) {
                            Log.d("SubaoGame", inetAddress.toString());
                        }
                    }
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /* compiled from: EngineWrapper */
    static class i {
        private int a = 0;
        private SparseArray<DetectTimeDelayCallback> b = new SparseArray<>();

        i() {
        }

        public synchronized int a(DetectTimeDelayCallback detectTimeDelayCallback) {
            this.a++;
            if (detectTimeDelayCallback != null) {
                this.b.put(this.a, detectTimeDelayCallback);
            }
            return this.a;
        }

        public synchronized DetectTimeDelayCallback a(int i) {
            DetectTimeDelayCallback detectTimeDelayCallback;
            detectTimeDelayCallback = this.b.get(i);
            this.b.remove(i);
            return detectTimeDelayCallback;
        }
    }

    public void m(int i2) {
        this.j.a(0, "key_main_loop_sleep_time", i2);
    }

    /* compiled from: EngineWrapper */
    private static class u extends Thread {
        u() {
            super("JNI-ProxyLoop");
        }

        public void run() {
            VPNJni.proxyLoop(0, false);
        }
    }
}
