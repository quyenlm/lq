package com.tencent.bugly.crashreport.crash.jni;

import android.content.Context;
import com.tencent.bugly.crashreport.common.info.AppInfo;
import com.tencent.bugly.crashreport.crash.CrashDetailBean;
import com.tencent.bugly.crashreport.crash.b;
import com.tencent.bugly.crashreport.crash.c;
import com.tencent.bugly.proguard.x;
import com.tencent.bugly.proguard.y;
import com.tencent.bugly.proguard.z;
import com.tencent.imsdk.framework.request.HttpRequest;
import com.tencent.tp.a.h;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* compiled from: BUGLY */
public final class a implements NativeExceptionHandler {
    private final Context a;
    private final b b;
    private final com.tencent.bugly.crashreport.common.info.a c;
    private final com.tencent.bugly.crashreport.common.strategy.a d;

    public a(Context context, com.tencent.bugly.crashreport.common.info.a aVar, b bVar, com.tencent.bugly.crashreport.common.strategy.a aVar2) {
        this.a = context;
        this.b = bVar;
        this.c = aVar;
        this.d = aVar2;
    }

    public final CrashDetailBean packageCrashDatas(String str, String str2, long j, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, byte[] bArr, Map<String, String> map, boolean z, boolean z2) {
        int length;
        String str12;
        int indexOf;
        boolean k = c.a().k();
        if (k) {
            x.e("This Crash Caused By ANR , PLS To Fix ANR , This Trace May Be Not Useful!", new Object[0]);
        }
        CrashDetailBean crashDetailBean = new CrashDetailBean();
        crashDetailBean.b = 1;
        crashDetailBean.e = this.c.h();
        crashDetailBean.f = this.c.j;
        crashDetailBean.g = this.c.w();
        crashDetailBean.m = this.c.g();
        crashDetailBean.n = str3;
        crashDetailBean.o = k ? " This Crash Caused By ANR , PLS To Fix ANR , This Trace May Be Not Useful![Bugly]" : "";
        crashDetailBean.p = str4;
        if (str5 == null) {
            str5 = "";
        }
        crashDetailBean.q = str5;
        crashDetailBean.r = j;
        crashDetailBean.u = z.b(crashDetailBean.q.getBytes());
        crashDetailBean.A = str;
        crashDetailBean.B = str2;
        crashDetailBean.I = this.c.y();
        crashDetailBean.h = this.c.v();
        crashDetailBean.i = this.c.J();
        crashDetailBean.v = str8;
        String str13 = null;
        NativeCrashHandler instance = NativeCrashHandler.getInstance();
        if (instance != null) {
            str13 = instance.getDumpFilePath();
        }
        String a2 = b.a(str13, str8);
        if (!z.a(a2)) {
            crashDetailBean.U = a2;
        }
        crashDetailBean.V = b.b(str13);
        crashDetailBean.w = b.a(str9, c.e, (String) null, false);
        crashDetailBean.x = b.a(str10, c.e, (String) null, true);
        crashDetailBean.J = str7;
        crashDetailBean.K = str6;
        crashDetailBean.L = str11;
        crashDetailBean.F = this.c.p();
        crashDetailBean.G = this.c.o();
        crashDetailBean.H = this.c.q();
        if (z) {
            crashDetailBean.C = com.tencent.bugly.crashreport.common.info.b.k();
            crashDetailBean.D = com.tencent.bugly.crashreport.common.info.b.i();
            crashDetailBean.E = com.tencent.bugly.crashreport.common.info.b.m();
            if (crashDetailBean.w == null) {
                crashDetailBean.w = z.a(this.a, c.e, (String) null);
            }
            crashDetailBean.y = y.a();
            crashDetailBean.M = this.c.a;
            crashDetailBean.N = this.c.a();
            crashDetailBean.P = this.c.H();
            crashDetailBean.Q = this.c.I();
            crashDetailBean.R = this.c.B();
            crashDetailBean.S = this.c.G();
            crashDetailBean.z = z.a(c.f, false);
            int indexOf2 = crashDetailBean.q.indexOf("java:\n");
            if (indexOf2 > 0 && (length = indexOf2 + "java:\n".length()) < crashDetailBean.q.length()) {
                String substring = crashDetailBean.q.substring(length, crashDetailBean.q.length() - 1);
                if (substring.length() > 0 && crashDetailBean.z.containsKey(crashDetailBean.B) && (indexOf = str12.indexOf(substring)) > 0) {
                    String substring2 = (str12 = crashDetailBean.z.get(crashDetailBean.B)).substring(indexOf);
                    crashDetailBean.z.put(crashDetailBean.B, substring2);
                    crashDetailBean.q = crashDetailBean.q.substring(0, length);
                    crashDetailBean.q += substring2;
                }
            }
            if (str == null) {
                crashDetailBean.A = this.c.d;
            }
            this.b.c(crashDetailBean);
        } else {
            crashDetailBean.C = -1;
            crashDetailBean.D = -1;
            crashDetailBean.E = -1;
            if (crashDetailBean.w == null) {
                crashDetailBean.w = "this crash is occurred at last process! Log is miss, when get an terrible ABRT Native Exception etc.";
            }
            crashDetailBean.M = -1;
            crashDetailBean.P = -1;
            crashDetailBean.Q = -1;
            crashDetailBean.R = map;
            crashDetailBean.S = this.c.G();
            crashDetailBean.z = null;
            if (str == null) {
                crashDetailBean.A = "unknown(record)";
            }
            if (bArr != null) {
                crashDetailBean.y = bArr;
            }
        }
        return crashDetailBean;
    }

    public final void handleNativeException(int i, int i2, long j, long j2, String str, String str2, String str3, String str4, int i3, String str5, int i4, int i5, int i6, String str6, String str7) {
        x.a("Native Crash Happen v1", new Object[0]);
        handleNativeException2(i, i2, j, j2, str, str2, str3, str4, i3, str5, i4, i5, i6, str6, str7, (String[]) null);
    }

    public final void handleNativeException2(int i, int i2, long j, long j2, String str, String str2, String str3, String str4, int i3, String str5, int i4, int i5, int i6, String str6, String str7, String[] strArr) {
        String str8;
        String str9;
        String str10;
        String str11;
        boolean z;
        x.a("Native Crash Happen v2", new Object[0]);
        try {
            String a2 = b.a(str3);
            String str12 = "UNKNOWN";
            if (i3 > 0) {
                str8 = "KERNEL";
                str9 = str + h.a + str5 + h.b;
            } else {
                if (i4 > 0) {
                    Context context = this.a;
                    str12 = AppInfo.a(i4);
                }
                if (!str12.equals(String.valueOf(i4))) {
                    str12 = str12 + h.a + i4 + h.b;
                    str8 = str5;
                    str9 = str;
                } else {
                    str8 = str5;
                    str9 = str;
                }
            }
            HashMap hashMap = new HashMap();
            if (strArr != null) {
                for (int i7 = 0; i7 < strArr.length; i7++) {
                    String str13 = strArr[i7];
                    if (str13 != null) {
                        x.a("Extra message[%d]: %s", Integer.valueOf(i7), str13);
                        String[] split = str13.split(HttpRequest.HTTP_REQ_ENTITY_MERGE);
                        if (split.length == 2) {
                            hashMap.put(split[0], split[1]);
                        } else {
                            x.d("bad extraMsg %s", str13);
                        }
                    }
                }
            } else {
                x.c("not found extraMsg", new Object[0]);
            }
            boolean z2 = false;
            String str14 = (String) hashMap.get("HasPendingException");
            if (str14 != null && str14.equals("true")) {
                x.a("Native crash happened with a Java pending exception.", new Object[0]);
                z2 = true;
            }
            String str15 = (String) hashMap.get("ExceptionProcessName");
            if (str15 == null || str15.length() == 0) {
                str10 = this.c.d;
            } else {
                x.c("Name of crash process: %s", str15);
                str10 = str15;
            }
            String str16 = (String) hashMap.get("ExceptionThreadName");
            if (str16 == null || str16.length() == 0) {
                Thread currentThread = Thread.currentThread();
                str11 = currentThread.getName() + h.a + currentThread.getId() + h.b;
            } else {
                x.c("Name of crash thread: %s", str16);
                Iterator<Thread> it = Thread.getAllStackTraces().keySet().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        z = false;
                        str11 = str16;
                        break;
                    }
                    Thread next = it.next();
                    if (next.getName().equals(str16)) {
                        str11 = str16 + h.a + next.getId() + h.b;
                        z = true;
                        break;
                    }
                }
                if (!z) {
                    str11 = str11 + h.a + i2 + h.b;
                }
            }
            long j3 = (j2 / 1000) + (1000 * j);
            String str17 = (String) hashMap.get("SysLogPath");
            String str18 = (String) hashMap.get("JniLogPath");
            if (!this.d.b()) {
                x.d("no remote but still store!", new Object[0]);
            }
            if (this.d.c().g || !this.d.b()) {
                CrashDetailBean packageCrashDatas = packageCrashDatas(str10, str11, j3, str9, str2, a2, str8, str12, str4, str17, str18, str7, (byte[]) null, (Map<String, String>) null, true, z2);
                if (packageCrashDatas == null) {
                    x.e("pkg crash datas fail!", new Object[0]);
                    return;
                }
                b.a("NATIVE_CRASH", z.a(), str10, str11, str9 + "\n" + str2 + "\n" + a2, packageCrashDatas);
                boolean z3 = !this.b.a(packageCrashDatas, i3);
                String str19 = null;
                NativeCrashHandler instance = NativeCrashHandler.getInstance();
                if (instance != null) {
                    str19 = instance.getDumpFilePath();
                }
                b.a(true, str19);
                if (z3) {
                    this.b.a(packageCrashDatas, 3000, true);
                }
                this.b.b(packageCrashDatas);
                return;
            }
            x.e("crash report was closed by remote , will not upload to Bugly , print local for helpful!", new Object[0]);
            b.a("NATIVE_CRASH", z.a(), str10, str11, str9 + "\n" + str2 + "\n" + a2, (CrashDetailBean) null);
            z.b(str4);
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
        }
    }
}
