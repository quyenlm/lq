package com.subao.common.g;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import com.amazonaws.services.s3.internal.Constants;
import com.subao.common.a;
import com.subao.common.d;
import com.subao.common.e.ak;
import com.subao.common.e.n;
import com.subao.common.f;
import com.subao.common.i.l;
import com.subao.common.i.p;
import com.subao.common.n.g;
import com.subao.vpn.JniCallback;
import com.subao.vpn.VPNJni;
import java.io.IOException;

/* compiled from: JniWrapper */
public class c implements a, f {
    private static final byte[] a = new byte[0];

    @NonNull
    private static String c(@Nullable String str) {
        return str == null ? "" : str;
    }

    public c(String str) {
        VPNJni.loadLibrary(new b(), str);
    }

    static byte[] a(String str) {
        if (str == null || str.length() == 0) {
            return a;
        }
        return str.getBytes();
    }

    public void a() {
        VPNJni.setCallback(new b());
    }

    public JniCallback a(JniCallback jniCallback) {
        return VPNJni.setCallback(jniCallback);
    }

    private static String a(Object obj) {
        if (obj == null) {
            return Constants.NULL_VERSION_ID;
        }
        String obj2 = obj.toString();
        if (obj2.length() <= 64) {
            return obj2;
        }
        return String.format(n.b, "\"%s\" ... (%d chars)", new Object[]{obj2.substring(0, 60), Integer.valueOf(obj2.length())});
    }

    public int b() {
        int scriptBit = VPNJni.getScriptBit(0);
        if (scriptBit == 32) {
            return scriptBit;
        }
        return 64;
    }

    public void a(int i, int i2, @Nullable String str, @Nullable String str2, @Nullable String str3) {
        VPNJni.httpResponse(i, i2, c(str), c(str2), c(str3));
    }

    public void a(int i, @NonNull String str, @NonNull String str2) {
        VPNJni.qosPrepareResult(i, str, str2);
    }

    public void a(int i, @Nullable String str) {
        if (str == null) {
            str = "";
        }
        VPNJni.domainNameResolveResult(i, str);
    }

    public void b(int i, String str) {
        VPNJni.requestIPRegionResult(i, str);
    }

    public boolean a(String str, int i, a aVar, String str2, byte[] bArr, String str3, String str4) {
        if (d.a("SubaoData")) {
            Log.d("SubaoData", "Init with PCode: " + g.a(bArr));
            Log.d("SubaoData", "NodeList: " + a((Object) str3));
            Log.d("SubaoData", "GIP: " + a((Object) str4));
        }
        boolean init = VPNJni.init(0, str, i, aVar.e, a(str2), (bArr == null || bArr.length == 0) ? a : bArr, a(str3), a(str4));
        if (init && d.a("SubaoProxy")) {
            c();
        }
        return init;
    }

    public final void c() {
        a(0, "key_log_level", 1);
        b("log_test = function(str) log_debug(str) end; log_test2 = function(s) log_debug(s) end");
    }

    /* access modifiers changed from: package-private */
    public final void b(String str) {
        b(0, "key_inject", str);
    }

    public boolean d() {
        return VPNJni.getProxyIsStart(0);
    }

    public boolean e() {
        return VPNJni.startProxy(0);
    }

    public void f() {
        VPNJni.stopProxy(0);
    }

    public void g() {
        VPNJni.processEvent();
    }

    public void b(int i, String str, String str2) {
        String format;
        if (d.a("SubaoData")) {
            if (str2 == null) {
                format = Constants.NULL_VERSION_ID;
            } else if (str2.length() > 64) {
                format = String.format(n.b, "\"%s ...\" (%d chars)", new Object[]{str2.substring(0, 60), Integer.valueOf(str2.length())});
            } else {
                format = String.format("\"%s\"", new Object[]{str2});
            }
            Log.d("SubaoData", String.format(n.b, "setString(%d, \"%s\", %s)", new Object[]{Integer.valueOf(i), str, format}));
        }
        VPNJni.setString(i, str.getBytes(), a(str2));
    }

    public void a(int i, String str, byte[] bArr) {
        if (d.a("SubaoData")) {
            Log.d("SubaoData", String.format(n.b, "setString(%d, \"%s\", %d bytes)", new Object[]{Integer.valueOf(i), str, Integer.valueOf(bArr == null ? 0 : bArr.length)}));
        }
        byte[] bytes = str.getBytes();
        if (bArr == null) {
            bArr = a;
        }
        VPNJni.setString(i, bytes, bArr);
    }

    public String a(@NonNull String str, @Nullable String str2) {
        String string = VPNJni.getString(0, str, str2);
        if (d.a("SubaoData")) {
            Log.d("SubaoData", String.format("getString(\"%s\", \"%s\") return: %s", new Object[]{str, str2, string}));
        }
        return string;
    }

    public void a(int i, String str, int i2) {
        if (d.a("SubaoData")) {
            Log.d("SubaoData", String.format(n.b, "setInt(%d, \"%s\", %d)", new Object[]{Integer.valueOf(i), str, Integer.valueOf(i2)}));
        }
        VPNJni.setInt(i, str.getBytes(), i2);
    }

    public void a(int i) {
        VPNJni.setUDPEchoPort(0, i);
    }

    public void a(int i, String str, String str2, String str3, int i2) {
        Log.d("SubaoAuth", String.format(n.b, "setUserToken(..., %d)", new Object[]{Integer.valueOf(i2)}));
        VPNJni.setUserToken(0, i, a(str), a(str2), a(str3), i2);
    }

    public void a(int i, int i2, int i3, boolean z) {
        VPNJni.requestMobileFDResult(i, i2, i3, z);
    }

    public void b(int i) {
        VPNJni.onUDPDelay(0, i);
    }

    public int h() {
        return VPNJni.getAccelRecommendation(0);
    }

    public void a(String str, int i) {
        VPNJni.setRecommendationGameIP(0, a(str), i);
    }

    public void a(@NonNull String str, @NonNull String str2, int i) {
        VPNJni.addAccelAddress(0, "tcp".compareToIgnoreCase(str) == 0 ? 2 : 1, str2, i);
    }

    public String c(int i) {
        return VPNJni.getWebUIUrl(0, i);
    }

    public String i() {
        return VPNJni.getWebUIUrl(0, 1000);
    }

    public String j() {
        return a("key_base_url", (String) null);
    }

    public int k() {
        return VPNJni.getAccelerationStatus(0);
    }

    public long l() {
        String lastAuthServerTime = VPNJni.getLastAuthServerTime(0);
        if (!TextUtils.isEmpty(lastAuthServerTime)) {
            try {
                return Long.parseLong(lastAuthServerTime);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public boolean m() {
        return VPNJni.getSDKUDPIsProxy(0);
    }

    public String n() {
        return VPNJni.getVIPValidTime(0);
    }

    public void b(String str, String str2) {
        VPNJni.defineConst(0, a(str), a(str2));
    }

    public void a(p pVar, l lVar) {
        b(0, "key_version", pVar.a);
        b(0, "key_channel", pVar.b);
        b(0, "key_os_version", pVar.c);
        b(0, "key_android_version", pVar.d);
        b(0, "key_phone_model", lVar.a());
        b(0, "key_rom", lVar.e());
        a(0, "key_cpu_speed", lVar.b());
        a(0, "key_cpu_core", lVar.c());
        a(0, "key_memory", lVar.d());
    }

    public void c(int i, @Nullable String str) {
        VPNJni.onLoadDataResult(i, c(str));
    }

    public void d(int i, @Nullable String str) {
        VPNJni.onListDataResult(i, c(str));
    }

    public String d(int i) {
        return VPNJni.getAccelRecommendationData(0, i);
    }

    public void a(int i, boolean z) {
        VPNJni.onAccelRecommendationResult(0, i, z);
    }

    public void a(int i, int i2, ak akVar, String str, boolean z) {
        a(akVar, z);
        VPNJni.startNodeDetect(0, i, i2, str);
    }

    private void a(ak akVar, boolean z) {
        int i;
        if (akVar != null && "com.ztgame.bob".equals(akVar.b)) {
            if (z) {
                i = akVar.d | 1;
            } else {
                i = akVar.d & -2;
            }
            a(new ak(akVar.a, akVar.b(), akVar.b, akVar.c, i, akVar.e, akVar.d(), akVar.f, akVar.c()));
        }
    }

    public boolean e(int i) {
        return VPNJni.isNodeDetected(0, i);
    }

    public void a(@NonNull ak akVar) {
        try {
            b(0, "key_add_game_with_json", akVar.e());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean f(int i) {
        return VPNJni.doStartVPN(i);
    }

    public void o() {
        VPNJni.doStopVPN();
    }

    public int p() {
        return VPNJni.getXunYouServiceRemindType(0);
    }

    public int a(int i, String str, int i2, String str2, int i3, int i4, int i5) {
        return VPNJni.detectTimeDelay(i, str, i2, str2, i3, i4, i5);
    }

    public int g(int i) {
        return VPNJni.detectAccessDelay(i);
    }

    public void a(int i, int i2) {
        VPNJni.queryActivities(i, i2);
    }

    public void a(int i, int i2, String str) {
        VPNJni.setActivityExposure(i, i2, str);
    }

    public int a(long j) {
        return VPNJni.queryTrialNotice(0, (int) (j / 1000));
    }

    public void h(int i) {
        VPNJni.replyTrialNotice(0, i);
    }
}
