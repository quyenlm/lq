package com.tencent.bugly.crashreport.crash;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Parcelable;
import android.text.TextUtils;
import com.amazonaws.services.s3.internal.Constants;
import com.tencent.bugly.BuglyStrategy;
import com.tencent.bugly.crashreport.common.info.PlugInBean;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import com.tencent.bugly.crashreport.common.strategy.a;
import com.tencent.bugly.proguard.ak;
import com.tencent.bugly.proguard.am;
import com.tencent.bugly.proguard.an;
import com.tencent.bugly.proguard.ao;
import com.tencent.bugly.proguard.ap;
import com.tencent.bugly.proguard.k;
import com.tencent.bugly.proguard.o;
import com.tencent.bugly.proguard.p;
import com.tencent.bugly.proguard.r;
import com.tencent.bugly.proguard.t;
import com.tencent.bugly.proguard.u;
import com.tencent.bugly.proguard.x;
import com.tencent.bugly.proguard.z;
import com.tencent.qqgamemi.util.TimeUtils;
import com.tencent.tp.a.h;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/* compiled from: BUGLY */
public final class b {
    private static int a = 0;
    private Context b;
    private u c;
    private p d;
    private a e;
    private o f;
    private BuglyStrategy.a g;

    public b(int i, Context context, u uVar, p pVar, a aVar, BuglyStrategy.a aVar2, o oVar) {
        a = i;
        this.b = context;
        this.c = uVar;
        this.d = pVar;
        this.e = aVar;
        this.g = aVar2;
        this.f = oVar;
    }

    private static List<a> a(List<a> list) {
        if (list == null || list.size() == 0) {
            return null;
        }
        long currentTimeMillis = System.currentTimeMillis();
        ArrayList arrayList = new ArrayList();
        for (a next : list) {
            if (next.d && next.b <= currentTimeMillis - TimeUtils.MILLIS_IN_DAY) {
                arrayList.add(next);
            }
        }
        return arrayList;
    }

    private CrashDetailBean a(List<a> list, CrashDetailBean crashDetailBean) {
        CrashDetailBean crashDetailBean2;
        CrashDetailBean crashDetailBean3;
        List<CrashDetailBean> b2;
        String[] split;
        if (list == null || list.size() == 0) {
            return crashDetailBean;
        }
        CrashDetailBean crashDetailBean4 = null;
        ArrayList arrayList = new ArrayList(10);
        for (a next : list) {
            if (next.e) {
                arrayList.add(next);
            }
        }
        if (arrayList.size() <= 0 || (b2 = b((List<a>) arrayList)) == null || b2.size() <= 0) {
            crashDetailBean2 = null;
        } else {
            Collections.sort(b2);
            int i = 0;
            while (i < b2.size()) {
                CrashDetailBean crashDetailBean5 = b2.get(i);
                if (i != 0) {
                    if (!(crashDetailBean5.s == null || (split = crashDetailBean5.s.split("\n")) == null)) {
                        for (String str : split) {
                            if (!crashDetailBean4.s.contains(str)) {
                                crashDetailBean4.t++;
                                crashDetailBean4.s += str + "\n";
                            }
                        }
                    }
                    crashDetailBean5 = crashDetailBean4;
                }
                i++;
                crashDetailBean4 = crashDetailBean5;
            }
            crashDetailBean2 = crashDetailBean4;
        }
        if (crashDetailBean2 == null) {
            crashDetailBean.j = true;
            crashDetailBean.t = 0;
            crashDetailBean.s = "";
            crashDetailBean3 = crashDetailBean;
        } else {
            crashDetailBean3 = crashDetailBean2;
        }
        for (a next2 : list) {
            if (!next2.e && !next2.d && !crashDetailBean3.s.contains(new StringBuilder().append(next2.b).toString())) {
                crashDetailBean3.t++;
                crashDetailBean3.s += next2.b + "\n";
            }
        }
        if (crashDetailBean3.r == crashDetailBean.r || crashDetailBean3.s.contains(new StringBuilder().append(crashDetailBean.r).toString())) {
            return crashDetailBean3;
        }
        crashDetailBean3.t++;
        crashDetailBean3.s += crashDetailBean.r + "\n";
        return crashDetailBean3;
    }

    public final boolean a(CrashDetailBean crashDetailBean) {
        return a(crashDetailBean, -123456789);
    }

    public final boolean a(CrashDetailBean crashDetailBean, int i) {
        if (crashDetailBean == null) {
            return true;
        }
        if (c.n != null && !c.n.isEmpty()) {
            x.c("Crash filter for crash stack is: %s", c.n);
            if (crashDetailBean.q.contains(c.n)) {
                x.d("This crash contains the filter string set. It will not be record and upload.", new Object[0]);
                return true;
            }
        }
        if (c.o != null && !c.o.isEmpty()) {
            x.c("Crash regular filter for crash stack is: %s", c.o);
            if (Pattern.compile(c.o).matcher(crashDetailBean.q).find()) {
                x.d("This crash matches the regular filter string set. It will not be record and upload.", new Object[0]);
                return true;
            }
        }
        int i2 = crashDetailBean.b;
        String str = crashDetailBean.n;
        String str2 = crashDetailBean.o;
        String str3 = crashDetailBean.p;
        String str4 = crashDetailBean.q;
        long j = crashDetailBean.r;
        String str5 = crashDetailBean.m;
        String str6 = crashDetailBean.e;
        String str7 = crashDetailBean.c;
        String str8 = crashDetailBean.A;
        String str9 = crashDetailBean.B;
        if (this.f != null) {
            x.c("Calling 'onCrashSaving' of RQD crash listener.", new Object[0]);
            if (!this.f.c()) {
                x.d("Crash listener 'onCrashSaving' return 'false' thus will not handle this crash.", new Object[0]);
                return true;
            }
        }
        if (crashDetailBean.b != 2) {
            r rVar = new r();
            rVar.b = 1;
            rVar.c = crashDetailBean.A;
            rVar.d = crashDetailBean.B;
            rVar.e = crashDetailBean.r;
            this.d.b(1);
            this.d.a(rVar);
            x.b("[crash] a crash occur, handling...", new Object[0]);
        } else {
            x.b("[crash] a caught exception occur, handling...", new Object[0]);
        }
        List<a> b2 = b();
        ArrayList arrayList = null;
        if (b2 != null && b2.size() > 0) {
            ArrayList arrayList2 = new ArrayList(10);
            ArrayList<a> arrayList3 = new ArrayList<>(10);
            arrayList2.addAll(a(b2));
            b2.removeAll(arrayList2);
            if (!com.tencent.bugly.b.c && c.d) {
                boolean z = false;
                for (a next : b2) {
                    if (crashDetailBean.u.equals(next.c)) {
                        if (next.e) {
                            z = true;
                        }
                        arrayList3.add(next);
                    }
                    z = z;
                }
                if (z || arrayList3.size() >= c.c) {
                    x.a("same crash occur too much do merged!", new Object[0]);
                    CrashDetailBean a2 = a((List<a>) arrayList3, crashDetailBean);
                    for (a aVar : arrayList3) {
                        if (aVar.a != a2.a) {
                            arrayList2.add(aVar);
                        }
                    }
                    d(a2);
                    c((List<a>) arrayList2);
                    x.b("[crash] save crash success. For this device crash many times, it will not upload crashes immediately", new Object[0]);
                    return true;
                }
            }
            arrayList = arrayList2;
        }
        d(crashDetailBean);
        if (arrayList != null && !arrayList.isEmpty()) {
            c((List<a>) arrayList);
        }
        x.b("[crash] save crash success", new Object[0]);
        return false;
    }

    public final List<CrashDetailBean> a() {
        StrategyBean c2 = a.a().c();
        if (c2 == null) {
            x.d("have not synced remote!", new Object[0]);
            return null;
        } else if (!c2.g) {
            x.d("Crashreport remote closed, please check your APP ID correct and Version available, then uninstall and reinstall your app.", new Object[0]);
            x.b("[init] WARNING! Crashreport closed by server, please check your APP ID correct and Version available, then uninstall and reinstall your app.", new Object[0]);
            return null;
        } else {
            long currentTimeMillis = System.currentTimeMillis();
            long b2 = z.b();
            List<a> b3 = b();
            x.c("Size of crash list loaded from DB: %s", Integer.valueOf(b3.size()));
            if (b3 == null || b3.size() <= 0) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            arrayList.addAll(a(b3));
            b3.removeAll(arrayList);
            Iterator<a> it = b3.iterator();
            while (it.hasNext()) {
                a next = it.next();
                if (next.b < b2 - c.g) {
                    it.remove();
                    arrayList.add(next);
                } else if (next.d) {
                    if (next.b >= currentTimeMillis - TimeUtils.MILLIS_IN_DAY) {
                        it.remove();
                    } else if (!next.e) {
                        it.remove();
                        arrayList.add(next);
                    }
                } else if (((long) next.f) >= 3 && next.b < currentTimeMillis - TimeUtils.MILLIS_IN_DAY) {
                    it.remove();
                    arrayList.add(next);
                }
            }
            if (arrayList.size() > 0) {
                c((List<a>) arrayList);
            }
            ArrayList arrayList2 = new ArrayList();
            List<CrashDetailBean> b4 = b(b3);
            if (b4 != null && b4.size() > 0) {
                String str = com.tencent.bugly.crashreport.common.info.a.b().j;
                Iterator<CrashDetailBean> it2 = b4.iterator();
                while (it2.hasNext()) {
                    CrashDetailBean next2 = it2.next();
                    if (!str.equals(next2.f)) {
                        it2.remove();
                        arrayList2.add(next2);
                    }
                }
            }
            if (arrayList2.size() > 0) {
                d((List<CrashDetailBean>) arrayList2);
            }
            return b4;
        }
    }

    public final void b(CrashDetailBean crashDetailBean) {
        if (this.f != null) {
            x.c("Calling 'onCrashHandleEnd' of RQD crash listener.", new Object[0]);
            o oVar = this.f;
            int i = crashDetailBean.b;
        }
    }

    public final void a(CrashDetailBean crashDetailBean, long j, boolean z) {
        boolean z2 = false;
        if (c.l) {
            x.a("try to upload right now", new Object[0]);
            ArrayList arrayList = new ArrayList();
            arrayList.add(crashDetailBean);
            if (crashDetailBean.b == 7) {
                z2 = true;
            }
            a(arrayList, 3000, z, z2, z);
        }
    }

    public final void a(final List<CrashDetailBean> list, long j, boolean z, boolean z2, boolean z3) {
        ao aoVar;
        if (!com.tencent.bugly.crashreport.common.info.a.a(this.b).e || this.c == null) {
            return;
        }
        if (z3 || this.c.b(c.a)) {
            StrategyBean c2 = this.e.c();
            if (!c2.g) {
                x.d("remote report is disable!", new Object[0]);
                x.b("[crash] server closed bugly in this app. please check your appid if is correct, and re-install it", new Object[0]);
            } else if (list != null && list.size() != 0) {
                try {
                    String str = this.c.a ? c2.s : c2.t;
                    String str2 = this.c.a ? StrategyBean.c : StrategyBean.a;
                    int i = this.c.a ? 830 : 630;
                    Context context = this.b;
                    com.tencent.bugly.crashreport.common.info.a b2 = com.tencent.bugly.crashreport.common.info.a.b();
                    if (context == null || list == null || list.size() == 0 || b2 == null) {
                        x.d("enEXPPkg args == null!", new Object[0]);
                        aoVar = null;
                    } else {
                        ao aoVar2 = new ao();
                        aoVar2.a = new ArrayList<>();
                        for (CrashDetailBean a2 : list) {
                            aoVar2.a.add(a(context, a2, b2));
                        }
                        aoVar = aoVar2;
                    }
                    if (aoVar == null) {
                        x.d("create eupPkg fail!", new Object[0]);
                        return;
                    }
                    byte[] a3 = com.tencent.bugly.proguard.a.a((k) aoVar);
                    if (a3 == null) {
                        x.d("send encode fail!", new Object[0]);
                        return;
                    }
                    ap a4 = com.tencent.bugly.proguard.a.a(this.b, i, a3);
                    if (a4 == null) {
                        x.d("request package is null.", new Object[0]);
                        return;
                    }
                    AnonymousClass1 r5 = new t() {
                        public final void a(boolean z) {
                            b bVar = b.this;
                            b.a(z, (List<CrashDetailBean>) list);
                        }
                    };
                    if (z) {
                        this.c.a(a, a4, str, str2, r5, j, z2);
                    } else {
                        this.c.a(a, a4, str, str2, r5, false);
                    }
                } catch (Throwable th) {
                    x.e("req cr error %s", th.toString());
                    if (!x.b(th)) {
                        th.printStackTrace();
                    }
                }
            }
        }
    }

    public static void a(boolean z, List<CrashDetailBean> list) {
        if (list != null && list.size() > 0) {
            x.c("up finish update state %b", Boolean.valueOf(z));
            for (CrashDetailBean next : list) {
                x.c("pre uid:%s uc:%d re:%b me:%b", next.c, Integer.valueOf(next.l), Boolean.valueOf(next.d), Boolean.valueOf(next.j));
                next.l++;
                next.d = z;
                x.c("set uid:%s uc:%d re:%b me:%b", next.c, Integer.valueOf(next.l), Boolean.valueOf(next.d), Boolean.valueOf(next.j));
            }
            for (CrashDetailBean a2 : list) {
                c.a().a(a2);
            }
            x.c("update state size %d", Integer.valueOf(list.size()));
        }
        if (!z) {
            x.b("[crash] upload fail.", new Object[0]);
        }
    }

    public final void c(CrashDetailBean crashDetailBean) {
        int i;
        String str;
        if (crashDetailBean != null) {
            if (this.g != null || this.f != null) {
                try {
                    x.a("[crash callback] start user's callback:onCrashHandleStart()", new Object[0]);
                    switch (crashDetailBean.b) {
                        case 0:
                            i = 0;
                            break;
                        case 1:
                            i = 2;
                            break;
                        case 2:
                            i = 1;
                            break;
                        case 3:
                            i = 4;
                            break;
                        case 4:
                            i = 3;
                            break;
                        case 5:
                            i = 5;
                            break;
                        case 6:
                            i = 6;
                            break;
                        case 7:
                            i = 7;
                            break;
                        default:
                            return;
                    }
                    int i2 = crashDetailBean.b;
                    String str2 = crashDetailBean.n;
                    String str3 = crashDetailBean.p;
                    String str4 = crashDetailBean.q;
                    long j = crashDetailBean.r;
                    Map map = null;
                    if (this.f != null) {
                        x.c("Calling 'onCrashHandleStart' of RQD crash listener.", new Object[0]);
                        o oVar = this.f;
                        x.c("Calling 'getCrashExtraMessage' of RQD crash listener.", new Object[0]);
                        String b2 = this.f.b();
                        if (b2 != null) {
                            map = new HashMap(1);
                            map.put("userData", b2);
                        }
                    } else if (this.g != null) {
                        x.c("Calling 'onCrashHandleStart' of Bugly crash listener.", new Object[0]);
                        map = this.g.onCrashHandleStart(i, crashDetailBean.n, crashDetailBean.o, crashDetailBean.q);
                    }
                    if (map != null && map.size() > 0) {
                        crashDetailBean.O = new LinkedHashMap(map.size());
                        for (Map.Entry entry : map.entrySet()) {
                            if (!z.a((String) entry.getKey())) {
                                String str5 = (String) entry.getKey();
                                if (str5.length() > 100) {
                                    str5 = str5.substring(0, 100);
                                    x.d("setted key length is over limit %d substring to %s", 100, str5);
                                }
                                String str6 = str5;
                                if (z.a((String) entry.getValue()) || ((String) entry.getValue()).length() <= 30000) {
                                    str = ((String) entry.getValue());
                                } else {
                                    str = ((String) entry.getValue()).substring(((String) entry.getValue()).length() - 30000);
                                    x.d("setted %s value length is over limit %d substring", str6, 30000);
                                }
                                crashDetailBean.O.put(str6, str);
                                x.a("add setted key %s value size:%d", str6, Integer.valueOf(str.length()));
                            }
                        }
                    }
                    x.a("[crash callback] start user's callback:onCrashHandleStart2GetExtraDatas()", new Object[0]);
                    byte[] bArr = null;
                    if (this.f != null) {
                        x.c("Calling 'getCrashExtraData' of RQD crash listener.", new Object[0]);
                        bArr = this.f.a();
                    } else if (this.g != null) {
                        x.c("Calling 'onCrashHandleStart2GetExtraDatas' of Bugly crash listener.", new Object[0]);
                        bArr = this.g.onCrashHandleStart2GetExtraDatas(i, crashDetailBean.n, crashDetailBean.o, crashDetailBean.q);
                    }
                    crashDetailBean.T = bArr;
                    if (bArr != null) {
                        if (bArr.length > 30000) {
                            x.d("extra bytes size %d is over limit %d will drop over part", Integer.valueOf(bArr.length), 30000);
                            crashDetailBean.T = Arrays.copyOf(bArr, 30000);
                        }
                        x.a("add extra bytes %d ", Integer.valueOf(bArr.length));
                    }
                } catch (Throwable th) {
                    x.d("crash handle callback something wrong! %s", th.getClass().getName());
                    if (!x.a(th)) {
                        th.printStackTrace();
                    }
                }
            }
        }
    }

    private static ContentValues e(CrashDetailBean crashDetailBean) {
        int i;
        int i2 = 1;
        if (crashDetailBean == null) {
            return null;
        }
        try {
            ContentValues contentValues = new ContentValues();
            if (crashDetailBean.a > 0) {
                contentValues.put("_id", Long.valueOf(crashDetailBean.a));
            }
            contentValues.put("_tm", Long.valueOf(crashDetailBean.r));
            contentValues.put("_s1", crashDetailBean.u);
            if (crashDetailBean.d) {
                i = 1;
            } else {
                i = 0;
            }
            contentValues.put("_up", Integer.valueOf(i));
            if (!crashDetailBean.j) {
                i2 = 0;
            }
            contentValues.put("_me", Integer.valueOf(i2));
            contentValues.put("_uc", Integer.valueOf(crashDetailBean.l));
            contentValues.put("_dt", z.a((Parcelable) crashDetailBean));
            return contentValues;
        } catch (Throwable th) {
            if (x.a(th)) {
                return null;
            }
            th.printStackTrace();
            return null;
        }
    }

    private static CrashDetailBean a(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        try {
            byte[] blob = cursor.getBlob(cursor.getColumnIndex("_dt"));
            if (blob == null) {
                return null;
            }
            long j = cursor.getLong(cursor.getColumnIndex("_id"));
            CrashDetailBean crashDetailBean = (CrashDetailBean) z.a(blob, CrashDetailBean.CREATOR);
            if (crashDetailBean == null) {
                return crashDetailBean;
            }
            crashDetailBean.a = j;
            return crashDetailBean;
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    public final void d(CrashDetailBean crashDetailBean) {
        ContentValues e2;
        if (crashDetailBean != null && (e2 = e(crashDetailBean)) != null) {
            long a2 = p.a().a("t_cr", e2, (o) null, true);
            if (a2 >= 0) {
                x.c("insert %s success!", "t_cr");
                crashDetailBean.a = a2;
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0098, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0099, code lost:
        r1 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00cc, code lost:
        r0 = th;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00cc A[ExcHandler: all (th java.lang.Throwable), Splitter:B:19:0x0077] */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00cf  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.util.List<com.tencent.bugly.crashreport.crash.CrashDetailBean> b(java.util.List<com.tencent.bugly.crashreport.crash.a> r12) {
        /*
            r11 = this;
            r10 = 0
            r8 = 0
            if (r12 == 0) goto L_0x000a
            int r0 = r12.size()
            if (r0 != 0) goto L_0x000c
        L_0x000a:
            r0 = r8
        L_0x000b:
            return r0
        L_0x000c:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r0 = "_id in "
            java.lang.StringBuilder r0 = r1.append(r0)
            java.lang.String r2 = "("
            r0.append(r2)
            java.util.Iterator r2 = r12.iterator()
        L_0x0020:
            boolean r0 = r2.hasNext()
            if (r0 == 0) goto L_0x0038
            java.lang.Object r0 = r2.next()
            com.tencent.bugly.crashreport.crash.a r0 = (com.tencent.bugly.crashreport.crash.a) r0
            long r4 = r0.a
            java.lang.StringBuilder r0 = r1.append(r4)
            java.lang.String r3 = ","
            r0.append(r3)
            goto L_0x0020
        L_0x0038:
            java.lang.String r0 = r1.toString()
            java.lang.String r2 = ","
            boolean r0 = r0.contains(r2)
            if (r0 == 0) goto L_0x012e
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r2 = ","
            int r2 = r1.lastIndexOf(r2)
            java.lang.String r1 = r1.substring(r10, r2)
            r0.<init>(r1)
            r7 = r0
        L_0x0054:
            java.lang.String r0 = ")"
            r7.append(r0)
            java.lang.String r3 = r7.toString()
            r7.setLength(r10)
            com.tencent.bugly.proguard.p r0 = com.tencent.bugly.proguard.p.a()     // Catch:{ Throwable -> 0x012a, all -> 0x0124 }
            java.lang.String r1 = "t_cr"
            r2 = 0
            r4 = 0
            r5 = 0
            r6 = 1
            android.database.Cursor r9 = r0.a(r1, r2, r3, r4, r5, r6)     // Catch:{ Throwable -> 0x012a, all -> 0x0124 }
            if (r9 != 0) goto L_0x0077
            if (r9 == 0) goto L_0x0075
            r9.close()
        L_0x0075:
            r0 = r8
            goto L_0x000b
        L_0x0077:
            java.util.ArrayList r6 = new java.util.ArrayList     // Catch:{ Throwable -> 0x0098, all -> 0x00cc }
            r6.<init>()     // Catch:{ Throwable -> 0x0098, all -> 0x00cc }
            java.lang.String r0 = "_id in "
            java.lang.StringBuilder r0 = r7.append(r0)     // Catch:{ Throwable -> 0x0098, all -> 0x00cc }
            java.lang.String r1 = "("
            r0.append(r1)     // Catch:{ Throwable -> 0x0098, all -> 0x00cc }
            r1 = r10
        L_0x0088:
            boolean r0 = r9.moveToNext()     // Catch:{ Throwable -> 0x0098, all -> 0x00cc }
            if (r0 == 0) goto L_0x00d3
            com.tencent.bugly.crashreport.crash.CrashDetailBean r0 = a((android.database.Cursor) r9)     // Catch:{ Throwable -> 0x0098, all -> 0x00cc }
            if (r0 == 0) goto L_0x00ab
            r6.add(r0)     // Catch:{ Throwable -> 0x0098, all -> 0x00cc }
            goto L_0x0088
        L_0x0098:
            r0 = move-exception
            r1 = r9
        L_0x009a:
            boolean r2 = com.tencent.bugly.proguard.x.a(r0)     // Catch:{ all -> 0x0127 }
            if (r2 != 0) goto L_0x00a3
            r0.printStackTrace()     // Catch:{ all -> 0x0127 }
        L_0x00a3:
            if (r1 == 0) goto L_0x00a8
            r1.close()
        L_0x00a8:
            r0 = r8
            goto L_0x000b
        L_0x00ab:
            java.lang.String r0 = "_id"
            int r0 = r9.getColumnIndex(r0)     // Catch:{ Throwable -> 0x00c2, all -> 0x00cc }
            long r2 = r9.getLong(r0)     // Catch:{ Throwable -> 0x00c2, all -> 0x00cc }
            java.lang.StringBuilder r0 = r7.append(r2)     // Catch:{ Throwable -> 0x00c2, all -> 0x00cc }
            java.lang.String r2 = ","
            r0.append(r2)     // Catch:{ Throwable -> 0x00c2, all -> 0x00cc }
            int r0 = r1 + 1
            r1 = r0
            goto L_0x0088
        L_0x00c2:
            r0 = move-exception
            java.lang.String r0 = "unknown id!"
            r2 = 0
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x0098, all -> 0x00cc }
            com.tencent.bugly.proguard.x.d(r0, r2)     // Catch:{ Throwable -> 0x0098, all -> 0x00cc }
            goto L_0x0088
        L_0x00cc:
            r0 = move-exception
        L_0x00cd:
            if (r9 == 0) goto L_0x00d2
            r9.close()
        L_0x00d2:
            throw r0
        L_0x00d3:
            java.lang.String r0 = r7.toString()     // Catch:{ Throwable -> 0x0098, all -> 0x00cc }
            java.lang.String r2 = ","
            boolean r0 = r0.contains(r2)     // Catch:{ Throwable -> 0x0098, all -> 0x00cc }
            if (r0 == 0) goto L_0x00f0
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0098, all -> 0x00cc }
            r2 = 0
            java.lang.String r3 = ","
            int r3 = r7.lastIndexOf(r3)     // Catch:{ Throwable -> 0x0098, all -> 0x00cc }
            java.lang.String r2 = r7.substring(r2, r3)     // Catch:{ Throwable -> 0x0098, all -> 0x00cc }
            r0.<init>(r2)     // Catch:{ Throwable -> 0x0098, all -> 0x00cc }
            r7 = r0
        L_0x00f0:
            java.lang.String r0 = ")"
            r7.append(r0)     // Catch:{ Throwable -> 0x0098, all -> 0x00cc }
            java.lang.String r2 = r7.toString()     // Catch:{ Throwable -> 0x0098, all -> 0x00cc }
            if (r1 <= 0) goto L_0x011c
            com.tencent.bugly.proguard.p r0 = com.tencent.bugly.proguard.p.a()     // Catch:{ Throwable -> 0x0098, all -> 0x00cc }
            java.lang.String r1 = "t_cr"
            r3 = 0
            r4 = 0
            r5 = 1
            int r0 = r0.a((java.lang.String) r1, (java.lang.String) r2, (java.lang.String[]) r3, (com.tencent.bugly.proguard.o) r4, (boolean) r5)     // Catch:{ Throwable -> 0x0098, all -> 0x00cc }
            java.lang.String r1 = "deleted %s illegal data %d"
            r2 = 2
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x0098, all -> 0x00cc }
            r3 = 0
            java.lang.String r4 = "t_cr"
            r2[r3] = r4     // Catch:{ Throwable -> 0x0098, all -> 0x00cc }
            r3 = 1
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch:{ Throwable -> 0x0098, all -> 0x00cc }
            r2[r3] = r0     // Catch:{ Throwable -> 0x0098, all -> 0x00cc }
            com.tencent.bugly.proguard.x.d(r1, r2)     // Catch:{ Throwable -> 0x0098, all -> 0x00cc }
        L_0x011c:
            if (r9 == 0) goto L_0x0121
            r9.close()
        L_0x0121:
            r0 = r6
            goto L_0x000b
        L_0x0124:
            r0 = move-exception
            r9 = r8
            goto L_0x00cd
        L_0x0127:
            r0 = move-exception
            r9 = r1
            goto L_0x00cd
        L_0x012a:
            r0 = move-exception
            r1 = r8
            goto L_0x009a
        L_0x012e:
            r7 = r1
            goto L_0x0054
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.crash.b.b(java.util.List):java.util.List");
    }

    private static a b(Cursor cursor) {
        boolean z = true;
        if (cursor == null) {
            return null;
        }
        try {
            a aVar = new a();
            aVar.a = cursor.getLong(cursor.getColumnIndex("_id"));
            aVar.b = cursor.getLong(cursor.getColumnIndex("_tm"));
            aVar.c = cursor.getString(cursor.getColumnIndex("_s1"));
            aVar.d = cursor.getInt(cursor.getColumnIndex("_up")) == 1;
            if (cursor.getInt(cursor.getColumnIndex("_me")) != 1) {
                z = false;
            }
            aVar.e = z;
            aVar.f = cursor.getInt(cursor.getColumnIndex("_uc"));
            return aVar;
        } catch (Throwable th) {
            if (x.a(th)) {
                return null;
            }
            th.printStackTrace();
            return null;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0060, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0061, code lost:
        r7 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0093, code lost:
        r0 = th;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0093 A[ExcHandler: all (th java.lang.Throwable), Splitter:B:7:0x003f] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0096  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.util.List<com.tencent.bugly.crashreport.crash.a> b() {
        /*
            r10 = this;
            r9 = 0
            r7 = 0
            java.util.ArrayList r8 = new java.util.ArrayList
            r8.<init>()
            r0 = 6
            java.lang.String[] r2 = new java.lang.String[r0]     // Catch:{ Throwable -> 0x00f4, all -> 0x00ee }
            r0 = 0
            java.lang.String r1 = "_id"
            r2[r0] = r1     // Catch:{ Throwable -> 0x00f4, all -> 0x00ee }
            r0 = 1
            java.lang.String r1 = "_tm"
            r2[r0] = r1     // Catch:{ Throwable -> 0x00f4, all -> 0x00ee }
            r0 = 2
            java.lang.String r1 = "_s1"
            r2[r0] = r1     // Catch:{ Throwable -> 0x00f4, all -> 0x00ee }
            r0 = 3
            java.lang.String r1 = "_up"
            r2[r0] = r1     // Catch:{ Throwable -> 0x00f4, all -> 0x00ee }
            r0 = 4
            java.lang.String r1 = "_me"
            r2[r0] = r1     // Catch:{ Throwable -> 0x00f4, all -> 0x00ee }
            r0 = 5
            java.lang.String r1 = "_uc"
            r2[r0] = r1     // Catch:{ Throwable -> 0x00f4, all -> 0x00ee }
            com.tencent.bugly.proguard.p r0 = com.tencent.bugly.proguard.p.a()     // Catch:{ Throwable -> 0x00f4, all -> 0x00ee }
            java.lang.String r1 = "t_cr"
            r3 = 0
            r4 = 0
            r5 = 0
            r6 = 1
            android.database.Cursor r6 = r0.a(r1, r2, r3, r4, r5, r6)     // Catch:{ Throwable -> 0x00f4, all -> 0x00ee }
            if (r6 != 0) goto L_0x003f
            if (r6 == 0) goto L_0x003d
            r6.close()
        L_0x003d:
            r0 = r7
        L_0x003e:
            return r0
        L_0x003f:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0060, all -> 0x0093 }
            r1.<init>()     // Catch:{ Throwable -> 0x0060, all -> 0x0093 }
            java.lang.String r0 = "_id in "
            java.lang.StringBuilder r0 = r1.append(r0)     // Catch:{ Throwable -> 0x0060, all -> 0x0093 }
            java.lang.String r2 = "("
            r0.append(r2)     // Catch:{ Throwable -> 0x0060, all -> 0x0093 }
            r3 = r9
        L_0x0050:
            boolean r0 = r6.moveToNext()     // Catch:{ Throwable -> 0x0060, all -> 0x0093 }
            if (r0 == 0) goto L_0x009a
            com.tencent.bugly.crashreport.crash.a r0 = b((android.database.Cursor) r6)     // Catch:{ Throwable -> 0x0060, all -> 0x0093 }
            if (r0 == 0) goto L_0x0072
            r8.add(r0)     // Catch:{ Throwable -> 0x0060, all -> 0x0093 }
            goto L_0x0050
        L_0x0060:
            r0 = move-exception
            r7 = r6
        L_0x0062:
            boolean r1 = com.tencent.bugly.proguard.x.a(r0)     // Catch:{ all -> 0x00f1 }
            if (r1 != 0) goto L_0x006b
            r0.printStackTrace()     // Catch:{ all -> 0x00f1 }
        L_0x006b:
            if (r7 == 0) goto L_0x0070
            r7.close()
        L_0x0070:
            r0 = r8
            goto L_0x003e
        L_0x0072:
            java.lang.String r0 = "_id"
            int r0 = r6.getColumnIndex(r0)     // Catch:{ Throwable -> 0x0089, all -> 0x0093 }
            long r4 = r6.getLong(r0)     // Catch:{ Throwable -> 0x0089, all -> 0x0093 }
            java.lang.StringBuilder r0 = r1.append(r4)     // Catch:{ Throwable -> 0x0089, all -> 0x0093 }
            java.lang.String r2 = ","
            r0.append(r2)     // Catch:{ Throwable -> 0x0089, all -> 0x0093 }
            int r0 = r3 + 1
            r3 = r0
            goto L_0x0050
        L_0x0089:
            r0 = move-exception
            java.lang.String r0 = "unknown id!"
            r2 = 0
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x0060, all -> 0x0093 }
            com.tencent.bugly.proguard.x.d(r0, r2)     // Catch:{ Throwable -> 0x0060, all -> 0x0093 }
            goto L_0x0050
        L_0x0093:
            r0 = move-exception
        L_0x0094:
            if (r6 == 0) goto L_0x0099
            r6.close()
        L_0x0099:
            throw r0
        L_0x009a:
            java.lang.String r0 = r1.toString()     // Catch:{ Throwable -> 0x0060, all -> 0x0093 }
            java.lang.String r2 = ","
            boolean r0 = r0.contains(r2)     // Catch:{ Throwable -> 0x0060, all -> 0x0093 }
            if (r0 == 0) goto L_0x00f7
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0060, all -> 0x0093 }
            r2 = 0
            java.lang.String r4 = ","
            int r4 = r1.lastIndexOf(r4)     // Catch:{ Throwable -> 0x0060, all -> 0x0093 }
            java.lang.String r1 = r1.substring(r2, r4)     // Catch:{ Throwable -> 0x0060, all -> 0x0093 }
            r0.<init>(r1)     // Catch:{ Throwable -> 0x0060, all -> 0x0093 }
        L_0x00b6:
            java.lang.String r1 = ")"
            r0.append(r1)     // Catch:{ Throwable -> 0x0060, all -> 0x0093 }
            java.lang.String r2 = r0.toString()     // Catch:{ Throwable -> 0x0060, all -> 0x0093 }
            r1 = 0
            r0.setLength(r1)     // Catch:{ Throwable -> 0x0060, all -> 0x0093 }
            if (r3 <= 0) goto L_0x00e6
            com.tencent.bugly.proguard.p r0 = com.tencent.bugly.proguard.p.a()     // Catch:{ Throwable -> 0x0060, all -> 0x0093 }
            java.lang.String r1 = "t_cr"
            r3 = 0
            r4 = 0
            r5 = 1
            int r0 = r0.a((java.lang.String) r1, (java.lang.String) r2, (java.lang.String[]) r3, (com.tencent.bugly.proguard.o) r4, (boolean) r5)     // Catch:{ Throwable -> 0x0060, all -> 0x0093 }
            java.lang.String r1 = "deleted %s illegal data %d"
            r2 = 2
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x0060, all -> 0x0093 }
            r3 = 0
            java.lang.String r4 = "t_cr"
            r2[r3] = r4     // Catch:{ Throwable -> 0x0060, all -> 0x0093 }
            r3 = 1
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch:{ Throwable -> 0x0060, all -> 0x0093 }
            r2[r3] = r0     // Catch:{ Throwable -> 0x0060, all -> 0x0093 }
            com.tencent.bugly.proguard.x.d(r1, r2)     // Catch:{ Throwable -> 0x0060, all -> 0x0093 }
        L_0x00e6:
            if (r6 == 0) goto L_0x00eb
            r6.close()
        L_0x00eb:
            r0 = r8
            goto L_0x003e
        L_0x00ee:
            r0 = move-exception
            r6 = r7
            goto L_0x0094
        L_0x00f1:
            r0 = move-exception
            r6 = r7
            goto L_0x0094
        L_0x00f4:
            r0 = move-exception
            goto L_0x0062
        L_0x00f7:
            r0 = r1
            goto L_0x00b6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.crash.b.b():java.util.List");
    }

    private static void c(List<a> list) {
        if (list != null && list.size() != 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("_id in ").append(h.a);
            for (a aVar : list) {
                sb.append(aVar.a).append(",");
            }
            StringBuilder sb2 = new StringBuilder(sb.substring(0, sb.lastIndexOf(",")));
            sb2.append(h.b);
            String sb3 = sb2.toString();
            sb2.setLength(0);
            try {
                x.c("deleted %s data %d", "t_cr", Integer.valueOf(p.a().a("t_cr", sb3, (String[]) null, (o) null, true)));
            } catch (Throwable th) {
                if (!x.a(th)) {
                    th.printStackTrace();
                }
            }
        }
    }

    private static void d(List<CrashDetailBean> list) {
        if (list != null) {
            try {
                if (list.size() != 0) {
                    StringBuilder sb = new StringBuilder();
                    for (CrashDetailBean crashDetailBean : list) {
                        sb.append(" or _id").append(" = ").append(crashDetailBean.a);
                    }
                    String sb2 = sb.toString();
                    if (sb2.length() > 0) {
                        sb2 = sb2.substring(4);
                    }
                    sb.setLength(0);
                    x.c("deleted %s data %d", "t_cr", Integer.valueOf(p.a().a("t_cr", sb2, (String[]) null, (o) null, true)));
                }
            } catch (Throwable th) {
                if (!x.a(th)) {
                    th.printStackTrace();
                }
            }
        }
    }

    private static an a(Context context, CrashDetailBean crashDetailBean, com.tencent.bugly.crashreport.common.info.a aVar) {
        am a2;
        am a3;
        am amVar;
        int i;
        boolean z = true;
        if (context == null || crashDetailBean == null || aVar == null) {
            x.d("enExp args == null", new Object[0]);
            return null;
        }
        an anVar = new an();
        switch (crashDetailBean.b) {
            case 0:
                anVar.a = crashDetailBean.j ? "200" : "100";
                break;
            case 1:
                anVar.a = crashDetailBean.j ? "201" : "101";
                break;
            case 2:
                anVar.a = crashDetailBean.j ? "202" : "102";
                break;
            case 3:
                anVar.a = crashDetailBean.j ? "203" : "103";
                break;
            case 4:
                anVar.a = crashDetailBean.j ? "204" : "104";
                break;
            case 5:
                anVar.a = crashDetailBean.j ? "207" : "107";
                break;
            case 6:
                anVar.a = crashDetailBean.j ? "206" : "106";
                break;
            case 7:
                anVar.a = crashDetailBean.j ? "208" : "108";
                break;
            default:
                x.e("crash type error! %d", Integer.valueOf(crashDetailBean.b));
                break;
        }
        anVar.b = crashDetailBean.r;
        anVar.c = crashDetailBean.n;
        anVar.d = crashDetailBean.o;
        anVar.e = crashDetailBean.p;
        anVar.g = crashDetailBean.q;
        anVar.h = crashDetailBean.z;
        anVar.i = crashDetailBean.c;
        anVar.j = null;
        anVar.l = crashDetailBean.m;
        anVar.m = crashDetailBean.e;
        anVar.f = crashDetailBean.B;
        anVar.t = com.tencent.bugly.crashreport.common.info.a.b().i();
        anVar.n = null;
        if (crashDetailBean.i != null && crashDetailBean.i.size() > 0) {
            anVar.o = new ArrayList<>();
            for (Map.Entry next : crashDetailBean.i.entrySet()) {
                ak akVar = new ak();
                akVar.a = ((PlugInBean) next.getValue()).a;
                akVar.c = ((PlugInBean) next.getValue()).c;
                akVar.d = ((PlugInBean) next.getValue()).b;
                akVar.b = aVar.r();
                anVar.o.add(akVar);
            }
        }
        if (crashDetailBean.h != null && crashDetailBean.h.size() > 0) {
            anVar.p = new ArrayList<>();
            for (Map.Entry next2 : crashDetailBean.h.entrySet()) {
                ak akVar2 = new ak();
                akVar2.a = ((PlugInBean) next2.getValue()).a;
                akVar2.c = ((PlugInBean) next2.getValue()).c;
                akVar2.d = ((PlugInBean) next2.getValue()).b;
                anVar.p.add(akVar2);
            }
        }
        if (crashDetailBean.j) {
            anVar.k = crashDetailBean.t;
            if (crashDetailBean.s != null && crashDetailBean.s.length() > 0) {
                if (anVar.q == null) {
                    anVar.q = new ArrayList<>();
                }
                try {
                    anVar.q.add(new am((byte) 1, "alltimes.txt", crashDetailBean.s.getBytes("utf-8")));
                } catch (UnsupportedEncodingException e2) {
                    e2.printStackTrace();
                    anVar.q = null;
                }
            }
            Object[] objArr = new Object[2];
            objArr[0] = Integer.valueOf(anVar.k);
            if (anVar.q != null) {
                i = anVar.q.size();
            } else {
                i = 0;
            }
            objArr[1] = Integer.valueOf(i);
            x.c("crashcount:%d sz:%d", objArr);
        }
        if (crashDetailBean.w != null) {
            if (anVar.q == null) {
                anVar.q = new ArrayList<>();
            }
            try {
                anVar.q.add(new am((byte) 1, "log.txt", crashDetailBean.w.getBytes("utf-8")));
            } catch (UnsupportedEncodingException e3) {
                e3.printStackTrace();
                anVar.q = null;
            }
        }
        if (crashDetailBean.x != null) {
            if (anVar.q == null) {
                anVar.q = new ArrayList<>();
            }
            try {
                anVar.q.add(new am((byte) 1, "jniLog.txt", crashDetailBean.x.getBytes("utf-8")));
            } catch (UnsupportedEncodingException e4) {
                e4.printStackTrace();
                anVar.q = null;
            }
        }
        if (!z.a(crashDetailBean.U)) {
            if (anVar.q == null) {
                anVar.q = new ArrayList<>();
            }
            try {
                amVar = new am((byte) 1, "crashInfos.txt", crashDetailBean.U.getBytes("utf-8"));
            } catch (UnsupportedEncodingException e5) {
                e5.printStackTrace();
                amVar = null;
            }
            if (amVar != null) {
                x.c("attach crash infos", new Object[0]);
                anVar.q.add(amVar);
            }
        }
        if (crashDetailBean.V != null) {
            if (anVar.q == null) {
                anVar.q = new ArrayList<>();
            }
            am a4 = a("backupRecord.zip", context, crashDetailBean.V);
            if (a4 != null) {
                x.c("attach backup record", new Object[0]);
                anVar.q.add(a4);
            }
        }
        if (crashDetailBean.y != null && crashDetailBean.y.length > 0) {
            am amVar2 = new am((byte) 2, "buglylog.zip", crashDetailBean.y);
            x.c("attach user log", new Object[0]);
            if (anVar.q == null) {
                anVar.q = new ArrayList<>();
            }
            anVar.q.add(amVar2);
        }
        if (crashDetailBean.b == 3) {
            if (anVar.q == null) {
                anVar.q = new ArrayList<>();
            }
            x.c("crashBean.userDatas:%s", crashDetailBean.O);
            if (crashDetailBean.O != null && crashDetailBean.O.containsKey("BUGLY_CR_01")) {
                try {
                    if (!TextUtils.isEmpty(crashDetailBean.O.get("BUGLY_CR_01"))) {
                        anVar.q.add(new am((byte) 1, "anrMessage.txt", crashDetailBean.O.get("BUGLY_CR_01").getBytes("utf-8")));
                        x.c("attach anr message", new Object[0]);
                    }
                } catch (UnsupportedEncodingException e6) {
                    e6.printStackTrace();
                    anVar.q = null;
                }
                crashDetailBean.O.remove("BUGLY_CR_01");
            }
            if (!(crashDetailBean.v == null || (a3 = a("trace.zip", context, crashDetailBean.v)) == null)) {
                x.c("attach traces", new Object[0]);
                anVar.q.add(a3);
            }
        }
        if (crashDetailBean.b == 1) {
            if (anVar.q == null) {
                anVar.q = new ArrayList<>();
            }
            if (!(crashDetailBean.v == null || (a2 = a("tomb.zip", context, crashDetailBean.v)) == null)) {
                x.c("attach tombs", new Object[0]);
                anVar.q.add(a2);
            }
        }
        if (aVar.C != null && !aVar.C.isEmpty()) {
            if (anVar.q == null) {
                anVar.q = new ArrayList<>();
            }
            StringBuilder sb = new StringBuilder();
            for (String append : aVar.C) {
                sb.append(append);
            }
            try {
                anVar.q.add(new am((byte) 1, "martianlog.txt", sb.toString().getBytes("utf-8")));
                x.c("attach pageTracingList", new Object[0]);
            } catch (UnsupportedEncodingException e7) {
                e7.printStackTrace();
            }
        }
        if (crashDetailBean.T != null && crashDetailBean.T.length > 0) {
            if (anVar.q == null) {
                anVar.q = new ArrayList<>();
            }
            anVar.q.add(new am((byte) 1, "userExtraByteData", crashDetailBean.T));
            x.c("attach extraData", new Object[0]);
        }
        anVar.r = new HashMap();
        anVar.r.put("A9", new StringBuilder().append(crashDetailBean.C).toString());
        anVar.r.put("A11", new StringBuilder().append(crashDetailBean.D).toString());
        anVar.r.put("A10", new StringBuilder().append(crashDetailBean.E).toString());
        anVar.r.put("A23", crashDetailBean.f);
        anVar.r.put("A7", aVar.f);
        anVar.r.put("A6", aVar.s());
        anVar.r.put("A5", aVar.r());
        anVar.r.put("A22", aVar.h());
        anVar.r.put("A2", new StringBuilder().append(crashDetailBean.G).toString());
        anVar.r.put("A1", new StringBuilder().append(crashDetailBean.F).toString());
        anVar.r.put("A24", aVar.h);
        anVar.r.put("A17", new StringBuilder().append(crashDetailBean.H).toString());
        anVar.r.put("A3", aVar.k());
        anVar.r.put("A16", aVar.m());
        anVar.r.put("A25", aVar.n());
        anVar.r.put("A14", aVar.l());
        anVar.r.put("A15", aVar.w());
        anVar.r.put("A13", new StringBuilder().append(aVar.x()).toString());
        anVar.r.put("A34", crashDetailBean.A);
        if (aVar.x != null) {
            anVar.r.put("productIdentify", aVar.x);
        }
        try {
            anVar.r.put("A26", URLEncoder.encode(crashDetailBean.I, "utf-8"));
        } catch (UnsupportedEncodingException e8) {
            e8.printStackTrace();
        }
        if (crashDetailBean.b == 1) {
            anVar.r.put("A27", crashDetailBean.K);
            anVar.r.put("A28", crashDetailBean.J);
            anVar.r.put("A29", new StringBuilder().append(crashDetailBean.k).toString());
        }
        anVar.r.put("A30", crashDetailBean.L);
        anVar.r.put("A18", new StringBuilder().append(crashDetailBean.M).toString());
        anVar.r.put("A36", new StringBuilder().append(!crashDetailBean.N).toString());
        anVar.r.put("F02", new StringBuilder().append(aVar.q).toString());
        anVar.r.put("F03", new StringBuilder().append(aVar.r).toString());
        anVar.r.put("F04", aVar.e());
        anVar.r.put("F05", new StringBuilder().append(aVar.s).toString());
        anVar.r.put("F06", aVar.p);
        anVar.r.put("F08", aVar.v);
        anVar.r.put("F09", aVar.w);
        anVar.r.put("F10", new StringBuilder().append(aVar.t).toString());
        if (crashDetailBean.P >= 0) {
            anVar.r.put("C01", new StringBuilder().append(crashDetailBean.P).toString());
        }
        if (crashDetailBean.Q >= 0) {
            anVar.r.put("C02", new StringBuilder().append(crashDetailBean.Q).toString());
        }
        if (crashDetailBean.R != null && crashDetailBean.R.size() > 0) {
            for (Map.Entry next3 : crashDetailBean.R.entrySet()) {
                anVar.r.put("C03_" + ((String) next3.getKey()), next3.getValue());
            }
        }
        if (crashDetailBean.S != null && crashDetailBean.S.size() > 0) {
            for (Map.Entry next4 : crashDetailBean.S.entrySet()) {
                anVar.r.put("C04_" + ((String) next4.getKey()), next4.getValue());
            }
        }
        anVar.s = null;
        if (crashDetailBean.O != null && crashDetailBean.O.size() > 0) {
            anVar.s = crashDetailBean.O;
            x.a("setted message size %d", Integer.valueOf(anVar.s.size()));
        }
        Object[] objArr2 = new Object[12];
        objArr2[0] = crashDetailBean.n;
        objArr2[1] = crashDetailBean.c;
        objArr2[2] = aVar.e();
        objArr2[3] = Long.valueOf((crashDetailBean.r - crashDetailBean.M) / 1000);
        objArr2[4] = Boolean.valueOf(crashDetailBean.k);
        objArr2[5] = Boolean.valueOf(crashDetailBean.N);
        objArr2[6] = Boolean.valueOf(crashDetailBean.j);
        if (crashDetailBean.b != 1) {
            z = false;
        }
        objArr2[7] = Boolean.valueOf(z);
        objArr2[8] = Integer.valueOf(crashDetailBean.t);
        objArr2[9] = crashDetailBean.s;
        objArr2[10] = Boolean.valueOf(crashDetailBean.d);
        objArr2[11] = Integer.valueOf(anVar.r.size());
        x.c("%s rid:%s sess:%s ls:%ds isR:%b isF:%b isM:%b isN:%b mc:%d ,%s ,isUp:%b ,vm:%d", objArr2);
        return anVar;
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0059 A[Catch:{ all -> 0x00df }] */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x005e A[SYNTHETIC, Splitter:B:22:0x005e] */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0067  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00c0 A[SYNTHETIC, Splitter:B:46:0x00c0] */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00c9  */
    /* JADX WARNING: Removed duplicated region for block: B:62:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.tencent.bugly.proguard.am a(java.lang.String r9, android.content.Context r10, java.lang.String r11) {
        /*
            r2 = 1
            r0 = 0
            r8 = 0
            if (r11 == 0) goto L_0x0007
            if (r10 != 0) goto L_0x000f
        L_0x0007:
            java.lang.String r1 = "rqdp{  createZipAttachment sourcePath == null || context == null ,pls check}"
            java.lang.Object[] r2 = new java.lang.Object[r8]
            com.tencent.bugly.proguard.x.d(r1, r2)
        L_0x000e:
            return r0
        L_0x000f:
            java.lang.String r1 = "zip %s"
            java.lang.Object[] r2 = new java.lang.Object[r2]
            r2[r8] = r11
            com.tencent.bugly.proguard.x.c(r1, r2)
            java.io.File r1 = new java.io.File
            r1.<init>(r11)
            java.io.File r3 = new java.io.File
            java.io.File r2 = r10.getCacheDir()
            r3.<init>(r2, r9)
            r2 = 5000(0x1388, float:7.006E-42)
            boolean r1 = com.tencent.bugly.proguard.z.a((java.io.File) r1, (java.io.File) r3, (int) r2)
            if (r1 != 0) goto L_0x0036
            java.lang.String r1 = "zip fail!"
            java.lang.Object[] r2 = new java.lang.Object[r8]
            com.tencent.bugly.proguard.x.d(r1, r2)
            goto L_0x000e
        L_0x0036:
            java.io.ByteArrayOutputStream r1 = new java.io.ByteArrayOutputStream
            r1.<init>()
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x00e2, all -> 0x00bc }
            r2.<init>(r3)     // Catch:{ Throwable -> 0x00e2, all -> 0x00bc }
            r4 = 4096(0x1000, float:5.74E-42)
            byte[] r4 = new byte[r4]     // Catch:{ Throwable -> 0x0052 }
        L_0x0044:
            int r5 = r2.read(r4)     // Catch:{ Throwable -> 0x0052 }
            if (r5 <= 0) goto L_0x0072
            r6 = 0
            r1.write(r4, r6, r5)     // Catch:{ Throwable -> 0x0052 }
            r1.flush()     // Catch:{ Throwable -> 0x0052 }
            goto L_0x0044
        L_0x0052:
            r1 = move-exception
        L_0x0053:
            boolean r4 = com.tencent.bugly.proguard.x.a(r1)     // Catch:{ all -> 0x00df }
            if (r4 != 0) goto L_0x005c
            r1.printStackTrace()     // Catch:{ all -> 0x00df }
        L_0x005c:
            if (r2 == 0) goto L_0x0061
            r2.close()     // Catch:{ IOException -> 0x00b1 }
        L_0x0061:
            boolean r1 = r3.exists()
            if (r1 == 0) goto L_0x000e
            java.lang.String r1 = "del tmp"
            java.lang.Object[] r2 = new java.lang.Object[r8]
            com.tencent.bugly.proguard.x.c(r1, r2)
            r3.delete()
            goto L_0x000e
        L_0x0072:
            byte[] r4 = r1.toByteArray()     // Catch:{ Throwable -> 0x0052 }
            java.lang.String r1 = "read bytes :%d"
            r5 = 1
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ Throwable -> 0x0052 }
            r6 = 0
            int r7 = r4.length     // Catch:{ Throwable -> 0x0052 }
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)     // Catch:{ Throwable -> 0x0052 }
            r5[r6] = r7     // Catch:{ Throwable -> 0x0052 }
            com.tencent.bugly.proguard.x.c(r1, r5)     // Catch:{ Throwable -> 0x0052 }
            com.tencent.bugly.proguard.am r1 = new com.tencent.bugly.proguard.am     // Catch:{ Throwable -> 0x0052 }
            r5 = 2
            java.lang.String r6 = r3.getName()     // Catch:{ Throwable -> 0x0052 }
            r1.<init>(r5, r6, r4)     // Catch:{ Throwable -> 0x0052 }
            r2.close()     // Catch:{ IOException -> 0x00a6 }
        L_0x0093:
            boolean r0 = r3.exists()
            if (r0 == 0) goto L_0x00a3
            java.lang.String r0 = "del tmp"
            java.lang.Object[] r2 = new java.lang.Object[r8]
            com.tencent.bugly.proguard.x.c(r0, r2)
            r3.delete()
        L_0x00a3:
            r0 = r1
            goto L_0x000e
        L_0x00a6:
            r0 = move-exception
            boolean r2 = com.tencent.bugly.proguard.x.a(r0)
            if (r2 != 0) goto L_0x0093
            r0.printStackTrace()
            goto L_0x0093
        L_0x00b1:
            r1 = move-exception
            boolean r2 = com.tencent.bugly.proguard.x.a(r1)
            if (r2 != 0) goto L_0x0061
            r1.printStackTrace()
            goto L_0x0061
        L_0x00bc:
            r1 = move-exception
            r2 = r0
        L_0x00be:
            if (r2 == 0) goto L_0x00c3
            r2.close()     // Catch:{ IOException -> 0x00d4 }
        L_0x00c3:
            boolean r0 = r3.exists()
            if (r0 == 0) goto L_0x00d3
            java.lang.String r0 = "del tmp"
            java.lang.Object[] r2 = new java.lang.Object[r8]
            com.tencent.bugly.proguard.x.c(r0, r2)
            r3.delete()
        L_0x00d3:
            throw r1
        L_0x00d4:
            r0 = move-exception
            boolean r2 = com.tencent.bugly.proguard.x.a(r0)
            if (r2 != 0) goto L_0x00c3
            r0.printStackTrace()
            goto L_0x00c3
        L_0x00df:
            r0 = move-exception
            r1 = r0
            goto L_0x00be
        L_0x00e2:
            r1 = move-exception
            r2 = r0
            goto L_0x0053
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.crash.b.a(java.lang.String, android.content.Context, java.lang.String):com.tencent.bugly.proguard.am");
    }

    public static void a(String str, String str2, String str3, String str4, String str5, CrashDetailBean crashDetailBean) {
        com.tencent.bugly.crashreport.common.info.a b2 = com.tencent.bugly.crashreport.common.info.a.b();
        if (b2 != null) {
            x.e("#++++++++++Record By Bugly++++++++++#", new Object[0]);
            x.e("# You can use Bugly(http:\\\\bugly.qq.com) to get more Crash Detail!", new Object[0]);
            x.e("# PKG NAME: %s", b2.c);
            x.e("# APP VER: %s", b2.j);
            x.e("# LAUNCH TIME: %s", z.a(new Date(com.tencent.bugly.crashreport.common.info.a.b().a)));
            x.e("# CRASH TYPE: %s", str);
            x.e("# CRASH TIME: %s", str2);
            x.e("# CRASH PROCESS: %s", str3);
            x.e("# CRASH THREAD: %s", str4);
            if (crashDetailBean != null) {
                x.e("# REPORT ID: %s", crashDetailBean.c);
                Object[] objArr = new Object[2];
                objArr[0] = b2.g;
                objArr[1] = b2.x().booleanValue() ? "ROOTED" : "UNROOT";
                x.e("# CRASH DEVICE: %s %s", objArr);
                x.e("# RUNTIME AVAIL RAM:%d ROM:%d SD:%d", Long.valueOf(crashDetailBean.C), Long.valueOf(crashDetailBean.D), Long.valueOf(crashDetailBean.E));
                x.e("# RUNTIME TOTAL RAM:%d ROM:%d SD:%d", Long.valueOf(crashDetailBean.F), Long.valueOf(crashDetailBean.G), Long.valueOf(crashDetailBean.H));
                if (!z.a(crashDetailBean.K)) {
                    x.e("# EXCEPTION FIRED BY %s %s", crashDetailBean.K, crashDetailBean.J);
                } else if (crashDetailBean.b == 3) {
                    Object[] objArr2 = new Object[1];
                    objArr2[0] = crashDetailBean.O == null ? Constants.NULL_VERSION_ID : crashDetailBean.O.get("BUGLY_CR_01");
                    x.e("# EXCEPTION ANR MESSAGE:\n %s", objArr2);
                }
            }
            if (!z.a(str5)) {
                x.e("# CRASH STACK: ", new Object[0]);
                x.e(str5, new Object[0]);
            }
            x.e("#++++++++++++++++++++++++++++++++++++++++++#", new Object[0]);
        }
    }
}
