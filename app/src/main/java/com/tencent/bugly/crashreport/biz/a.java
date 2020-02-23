package com.tencent.bugly.crashreport.biz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Parcelable;
import com.banalytics.BATrackerConst;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import com.tencent.bugly.proguard.ap;
import com.tencent.bugly.proguard.au;
import com.tencent.bugly.proguard.k;
import com.tencent.bugly.proguard.o;
import com.tencent.bugly.proguard.p;
import com.tencent.bugly.proguard.t;
import com.tencent.bugly.proguard.u;
import com.tencent.bugly.proguard.w;
import com.tencent.bugly.proguard.x;
import com.tencent.bugly.proguard.z;
import com.tencent.imsdk.expansion.downloader.Constants;
import com.tencent.qqgamemi.util.TimeUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: BUGLY */
public final class a {
    private Context a;
    /* access modifiers changed from: private */
    public long b;
    private int c;
    private boolean d = true;

    static /* synthetic */ void a(a aVar, UserInfoBean userInfoBean, boolean z) {
        List<UserInfoBean> a2;
        if (userInfoBean == null) {
            return;
        }
        if (z || userInfoBean.b == 1 || (a2 = aVar.a(com.tencent.bugly.crashreport.common.info.a.a(aVar.a).d)) == null || a2.size() < 20) {
            long a3 = p.a().a("t_ui", a(userInfoBean), (o) null, true);
            if (a3 >= 0) {
                x.c("[Database] insert %s success with ID: %d", "t_ui", Long.valueOf(a3));
                userInfoBean.a = a3;
                return;
            }
            return;
        }
        x.a("[UserInfo] There are too many user info in local: %d", Integer.valueOf(a2.size()));
    }

    public a(Context context, boolean z) {
        this.a = context;
        this.d = z;
    }

    public final void a(int i, boolean z, long j) {
        int i2 = 1;
        com.tencent.bugly.crashreport.common.strategy.a a2 = com.tencent.bugly.crashreport.common.strategy.a.a();
        if (a2 == null || a2.c().h || i == 1 || i == 3) {
            if (i == 1 || i == 3) {
                this.c++;
            }
            com.tencent.bugly.crashreport.common.info.a a3 = com.tencent.bugly.crashreport.common.info.a.a(this.a);
            UserInfoBean userInfoBean = new UserInfoBean();
            userInfoBean.b = i;
            userInfoBean.c = a3.d;
            userInfoBean.d = a3.g();
            userInfoBean.e = System.currentTimeMillis();
            userInfoBean.f = -1;
            userInfoBean.n = a3.j;
            if (i != 1) {
                i2 = 0;
            }
            userInfoBean.o = i2;
            userInfoBean.l = a3.a();
            userInfoBean.m = a3.p;
            userInfoBean.g = a3.q;
            userInfoBean.h = a3.r;
            userInfoBean.i = a3.s;
            userInfoBean.k = a3.t;
            userInfoBean.r = a3.B();
            userInfoBean.s = a3.G();
            userInfoBean.p = a3.H();
            userInfoBean.q = a3.I();
            w.a().a(new C0020a(userInfoBean, z), 0);
            return;
        }
        x.e("UserInfo is disable", new Object[0]);
    }

    public final void a() {
        this.b = z.b() + TimeUtils.MILLIS_IN_DAY;
        w.a().a(new b(), (this.b - System.currentTimeMillis()) + Constants.ACTIVE_THREAD_WATCHDOG);
    }

    /* renamed from: com.tencent.bugly.crashreport.biz.a$a  reason: collision with other inner class name */
    /* compiled from: BUGLY */
    class C0020a implements Runnable {
        private boolean a;
        private UserInfoBean b;

        public C0020a(UserInfoBean userInfoBean, boolean z) {
            this.b = userInfoBean;
            this.a = z;
        }

        public final void run() {
            com.tencent.bugly.crashreport.common.info.a b2;
            try {
                if (this.b != null) {
                    UserInfoBean userInfoBean = this.b;
                    if (!(userInfoBean == null || (b2 = com.tencent.bugly.crashreport.common.info.a.b()) == null)) {
                        userInfoBean.j = b2.e();
                    }
                    x.c("[UserInfo] Record user info.", new Object[0]);
                    a.a(a.this, this.b, false);
                }
                if (this.a) {
                    a aVar = a.this;
                    w a2 = w.a();
                    if (a2 != null) {
                        a2.a(new Runnable() {
                            public final void run() {
                                try {
                                    a.this.c();
                                } catch (Throwable th) {
                                    x.a(th);
                                }
                            }
                        });
                    }
                }
            } catch (Throwable th) {
                if (!x.a(th)) {
                    th.printStackTrace();
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public synchronized void c() {
        com.tencent.bugly.crashreport.common.strategy.a a2;
        final ArrayList arrayList;
        boolean z;
        boolean z2;
        int i;
        boolean z3 = false;
        synchronized (this) {
            if (this.d) {
                u a3 = u.a();
                if (!(a3 == null || (a2 = com.tencent.bugly.crashreport.common.strategy.a.a()) == null || (a2.b() && !a3.b(1001)))) {
                    String str = com.tencent.bugly.crashreport.common.info.a.a(this.a).d;
                    ArrayList arrayList2 = new ArrayList();
                    List<UserInfoBean> a4 = a(str);
                    if (a4 != null) {
                        int size = a4.size() - 20;
                        if (size > 0) {
                            for (int i2 = 0; i2 < a4.size() - 1; i2++) {
                                for (int i3 = i2 + 1; i3 < a4.size(); i3++) {
                                    if (a4.get(i2).e > a4.get(i3).e) {
                                        a4.set(i2, a4.get(i3));
                                        a4.set(i3, a4.get(i2));
                                    }
                                }
                            }
                            for (int i4 = 0; i4 < size; i4++) {
                                arrayList2.add(a4.get(i4));
                            }
                        }
                        Iterator<UserInfoBean> it = a4.iterator();
                        int i5 = 0;
                        while (it.hasNext()) {
                            UserInfoBean next = it.next();
                            if (next.f != -1) {
                                it.remove();
                                if (next.e < z.b()) {
                                    arrayList2.add(next);
                                }
                            }
                            if (next.e <= System.currentTimeMillis() - BATrackerConst.TRACKER_WAKE_UP_INTERVAL || !(next.b == 1 || next.b == 4 || next.b == 3)) {
                                i = i5;
                            } else {
                                i = i5 + 1;
                            }
                            i5 = i;
                        }
                        if (i5 > 15) {
                            x.d("[UserInfo] Upload user info too many times in 10 min: %d", Integer.valueOf(i5));
                            z2 = false;
                        } else {
                            z2 = true;
                        }
                        arrayList = a4;
                        z = z2;
                    } else {
                        arrayList = new ArrayList();
                        z = true;
                    }
                    if (arrayList2.size() > 0) {
                        a((List<UserInfoBean>) arrayList2);
                    }
                    if (!z || arrayList.size() == 0) {
                        x.c("[UserInfo] There is no user info in local database.", new Object[0]);
                    } else {
                        x.c("[UserInfo] Upload user info(size: %d)", Integer.valueOf(arrayList.size()));
                        au a5 = com.tencent.bugly.proguard.a.a(arrayList, this.c == 1 ? 1 : 2);
                        if (a5 == null) {
                            x.d("[UserInfo] Failed to create UserInfoPackage.", new Object[0]);
                        } else {
                            byte[] a6 = com.tencent.bugly.proguard.a.a((k) a5);
                            if (a6 == null) {
                                x.d("[UserInfo] Failed to encode data.", new Object[0]);
                            } else {
                                ap a7 = com.tencent.bugly.proguard.a.a(this.a, a3.a ? 840 : 640, a6);
                                if (a7 == null) {
                                    x.d("[UserInfo] Request package is null.", new Object[0]);
                                } else {
                                    AnonymousClass1 r5 = new t() {
                                        public final void a(boolean z) {
                                            if (z) {
                                                x.c("[UserInfo] Successfully uploaded user info.", new Object[0]);
                                                long currentTimeMillis = System.currentTimeMillis();
                                                for (UserInfoBean userInfoBean : arrayList) {
                                                    userInfoBean.f = currentTimeMillis;
                                                    a.a(a.this, userInfoBean, true);
                                                }
                                            }
                                        }
                                    };
                                    StrategyBean c2 = com.tencent.bugly.crashreport.common.strategy.a.a().c();
                                    String str2 = a3.a ? c2.r : c2.t;
                                    String str3 = a3.a ? StrategyBean.b : StrategyBean.a;
                                    u a8 = u.a();
                                    if (this.c == 1) {
                                        z3 = true;
                                    }
                                    a8.a(1001, a7, str2, str3, r5, z3);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public final void b() {
        w a2 = w.a();
        if (a2 != null) {
            a2.a(new Runnable() {
                public final void run() {
                    try {
                        a.this.c();
                    } catch (Throwable th) {
                        x.a(th);
                    }
                }
            });
        }
    }

    /* compiled from: BUGLY */
    class b implements Runnable {
        b() {
        }

        public final void run() {
            long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis < a.this.b) {
                w.a().a(new b(), (a.this.b - currentTimeMillis) + Constants.ACTIVE_THREAD_WATCHDOG);
                return;
            }
            a.this.a(3, false, 0);
            a.this.a();
        }
    }

    /* compiled from: BUGLY */
    class c implements Runnable {
        private long a = 21600000;

        public c(long j) {
            this.a = j;
        }

        public final void run() {
            a aVar = a.this;
            w a2 = w.a();
            if (a2 != null) {
                a2.a(new Runnable() {
                    public final void run() {
                        try {
                            a.this.c();
                        } catch (Throwable th) {
                            x.a(th);
                        }
                    }
                });
            }
            a aVar2 = a.this;
            long j = this.a;
            w.a().a(new c(j), j);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x004f, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0050, code lost:
        r1 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0085, code lost:
        r0 = th;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0085 A[ExcHandler: all (th java.lang.Throwable), Splitter:B:12:0x0035] */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0088  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.util.List<com.tencent.bugly.crashreport.biz.UserInfoBean> a(java.lang.String r10) {
        /*
            r9 = this;
            r7 = 0
            boolean r0 = com.tencent.bugly.proguard.z.a((java.lang.String) r10)     // Catch:{ Throwable -> 0x00ca, all -> 0x00c4 }
            if (r0 == 0) goto L_0x001f
            r3 = r7
        L_0x0008:
            com.tencent.bugly.proguard.p r0 = com.tencent.bugly.proguard.p.a()     // Catch:{ Throwable -> 0x00ca, all -> 0x00c4 }
            java.lang.String r1 = "t_ui"
            r2 = 0
            r4 = 0
            r5 = 0
            r6 = 1
            android.database.Cursor r8 = r0.a(r1, r2, r3, r4, r5, r6)     // Catch:{ Throwable -> 0x00ca, all -> 0x00c4 }
            if (r8 != 0) goto L_0x0035
            if (r8 == 0) goto L_0x001d
            r8.close()
        L_0x001d:
            r0 = r7
        L_0x001e:
            return r0
        L_0x001f:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00ca, all -> 0x00c4 }
            java.lang.String r1 = "_pc = '"
            r0.<init>(r1)     // Catch:{ Throwable -> 0x00ca, all -> 0x00c4 }
            java.lang.StringBuilder r0 = r0.append(r10)     // Catch:{ Throwable -> 0x00ca, all -> 0x00c4 }
            java.lang.String r1 = "'"
            java.lang.StringBuilder r0 = r0.append(r1)     // Catch:{ Throwable -> 0x00ca, all -> 0x00c4 }
            java.lang.String r3 = r0.toString()     // Catch:{ Throwable -> 0x00ca, all -> 0x00c4 }
            goto L_0x0008
        L_0x0035:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x004f, all -> 0x0085 }
            r0.<init>()     // Catch:{ Throwable -> 0x004f, all -> 0x0085 }
            java.util.ArrayList r6 = new java.util.ArrayList     // Catch:{ Throwable -> 0x004f, all -> 0x0085 }
            r6.<init>()     // Catch:{ Throwable -> 0x004f, all -> 0x0085 }
        L_0x003f:
            boolean r1 = r8.moveToNext()     // Catch:{ Throwable -> 0x004f, all -> 0x0085 }
            if (r1 == 0) goto L_0x008c
            com.tencent.bugly.crashreport.biz.UserInfoBean r1 = a((android.database.Cursor) r8)     // Catch:{ Throwable -> 0x004f, all -> 0x0085 }
            if (r1 == 0) goto L_0x0061
            r6.add(r1)     // Catch:{ Throwable -> 0x004f, all -> 0x0085 }
            goto L_0x003f
        L_0x004f:
            r0 = move-exception
            r1 = r8
        L_0x0051:
            boolean r2 = com.tencent.bugly.proguard.x.a(r0)     // Catch:{ all -> 0x00c7 }
            if (r2 != 0) goto L_0x005a
            r0.printStackTrace()     // Catch:{ all -> 0x00c7 }
        L_0x005a:
            if (r1 == 0) goto L_0x005f
            r1.close()
        L_0x005f:
            r0 = r7
            goto L_0x001e
        L_0x0061:
            java.lang.String r1 = "_id"
            int r1 = r8.getColumnIndex(r1)     // Catch:{ Throwable -> 0x007b, all -> 0x0085 }
            long r2 = r8.getLong(r1)     // Catch:{ Throwable -> 0x007b, all -> 0x0085 }
            java.lang.String r1 = " or _id"
            java.lang.StringBuilder r1 = r0.append(r1)     // Catch:{ Throwable -> 0x007b, all -> 0x0085 }
            java.lang.String r4 = " = "
            java.lang.StringBuilder r1 = r1.append(r4)     // Catch:{ Throwable -> 0x007b, all -> 0x0085 }
            r1.append(r2)     // Catch:{ Throwable -> 0x007b, all -> 0x0085 }
            goto L_0x003f
        L_0x007b:
            r1 = move-exception
            java.lang.String r1 = "[Database] unknown id."
            r2 = 0
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x004f, all -> 0x0085 }
            com.tencent.bugly.proguard.x.d(r1, r2)     // Catch:{ Throwable -> 0x004f, all -> 0x0085 }
            goto L_0x003f
        L_0x0085:
            r0 = move-exception
        L_0x0086:
            if (r8 == 0) goto L_0x008b
            r8.close()
        L_0x008b:
            throw r0
        L_0x008c:
            java.lang.String r0 = r0.toString()     // Catch:{ Throwable -> 0x004f, all -> 0x0085 }
            int r1 = r0.length()     // Catch:{ Throwable -> 0x004f, all -> 0x0085 }
            if (r1 <= 0) goto L_0x00bc
            r1 = 4
            java.lang.String r2 = r0.substring(r1)     // Catch:{ Throwable -> 0x004f, all -> 0x0085 }
            com.tencent.bugly.proguard.p r0 = com.tencent.bugly.proguard.p.a()     // Catch:{ Throwable -> 0x004f, all -> 0x0085 }
            java.lang.String r1 = "t_ui"
            r3 = 0
            r4 = 0
            r5 = 1
            int r0 = r0.a((java.lang.String) r1, (java.lang.String) r2, (java.lang.String[]) r3, (com.tencent.bugly.proguard.o) r4, (boolean) r5)     // Catch:{ Throwable -> 0x004f, all -> 0x0085 }
            java.lang.String r1 = "[Database] deleted %s error data %d"
            r2 = 2
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x004f, all -> 0x0085 }
            r3 = 0
            java.lang.String r4 = "t_ui"
            r2[r3] = r4     // Catch:{ Throwable -> 0x004f, all -> 0x0085 }
            r3 = 1
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch:{ Throwable -> 0x004f, all -> 0x0085 }
            r2[r3] = r0     // Catch:{ Throwable -> 0x004f, all -> 0x0085 }
            com.tencent.bugly.proguard.x.d(r1, r2)     // Catch:{ Throwable -> 0x004f, all -> 0x0085 }
        L_0x00bc:
            if (r8 == 0) goto L_0x00c1
            r8.close()
        L_0x00c1:
            r0 = r6
            goto L_0x001e
        L_0x00c4:
            r0 = move-exception
            r8 = r7
            goto L_0x0086
        L_0x00c7:
            r0 = move-exception
            r8 = r1
            goto L_0x0086
        L_0x00ca:
            r0 = move-exception
            r1 = r7
            goto L_0x0051
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.biz.a.a(java.lang.String):java.util.List");
    }

    private static void a(List<UserInfoBean> list) {
        if (list != null && list.size() != 0) {
            StringBuilder sb = new StringBuilder();
            int i = 0;
            while (i < list.size() && i < 50) {
                sb.append(" or _id").append(" = ").append(list.get(i).a);
                i++;
            }
            String sb2 = sb.toString();
            if (sb2.length() > 0) {
                sb2 = sb2.substring(4);
            }
            sb.setLength(0);
            try {
                x.c("[Database] deleted %s data %d", "t_ui", Integer.valueOf(p.a().a("t_ui", sb2, (String[]) null, (o) null, true)));
            } catch (Throwable th) {
                if (!x.a(th)) {
                    th.printStackTrace();
                }
            }
        }
    }

    private static ContentValues a(UserInfoBean userInfoBean) {
        if (userInfoBean == null) {
            return null;
        }
        try {
            ContentValues contentValues = new ContentValues();
            if (userInfoBean.a > 0) {
                contentValues.put("_id", Long.valueOf(userInfoBean.a));
            }
            contentValues.put("_tm", Long.valueOf(userInfoBean.e));
            contentValues.put("_ut", Long.valueOf(userInfoBean.f));
            contentValues.put("_tp", Integer.valueOf(userInfoBean.b));
            contentValues.put("_pc", userInfoBean.c);
            contentValues.put("_dt", z.a((Parcelable) userInfoBean));
            return contentValues;
        } catch (Throwable th) {
            if (x.a(th)) {
                return null;
            }
            th.printStackTrace();
            return null;
        }
    }

    private static UserInfoBean a(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        try {
            byte[] blob = cursor.getBlob(cursor.getColumnIndex("_dt"));
            if (blob == null) {
                return null;
            }
            long j = cursor.getLong(cursor.getColumnIndex("_id"));
            UserInfoBean userInfoBean = (UserInfoBean) z.a(blob, UserInfoBean.CREATOR);
            if (userInfoBean == null) {
                return userInfoBean;
            }
            userInfoBean.a = j;
            return userInfoBean;
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }
}
