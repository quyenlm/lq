package com.subao.common.e;

import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import com.amazonaws.services.s3.Headers;
import com.google.android.gms.games.GamesStatusCodes;
import com.subao.common.j.a;
import com.subao.common.j.j;
import com.tencent.qqgamemi.util.TimeUtils;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/* compiled from: PortalDataDownloader */
public abstract class z {
    private static final List<String> a = new ArrayList(8);
    private static volatile long b = (f() - TimeUtils.MILLIS_IN_DAY);
    /* access modifiers changed from: private */
    @NonNull
    public final a c;
    /* access modifiers changed from: private */
    public int d;
    @NonNull
    private final Object e = new Object();

    /* access modifiers changed from: protected */
    @NonNull
    public abstract String a();

    /* access modifiers changed from: protected */
    @NonNull
    public abstract String b();

    static /* synthetic */ int b(z zVar) {
        int i = zVar.d + 1;
        zVar.d = i;
        return i;
    }

    protected z(@NonNull a aVar) {
        this.c = aVar;
    }

    public static long f() {
        return SystemClock.elapsedRealtime();
    }

    public static synchronized long g() {
        long j;
        synchronized (z.class) {
            j = b;
        }
        return j;
    }

    protected static boolean h() {
        return com.subao.common.d.a("SubaoData");
    }

    protected static long a(HttpURLConnection httpURLConnection) {
        long j = 3600000;
        String headerField = httpURLConnection.getHeaderField("Cache-Control");
        if (TextUtils.isEmpty(headerField)) {
            return 0;
        }
        if (headerField.length() <= "max-age=".length()) {
            return 0;
        }
        if (!headerField.startsWith("max-age=")) {
            return 0;
        }
        try {
            long parseLong = Long.parseLong(headerField.substring("max-age=".length()));
            if (parseLong <= 0) {
                return 0;
            }
            long j2 = 1000 * parseLong;
            if (j2 <= 3600000) {
                j = j2;
            }
            return j + System.currentTimeMillis();
        } catch (NumberFormatException e2) {
            return 0;
        }
    }

    /* access modifiers changed from: protected */
    public final void a(String str) {
        if (str != null) {
            Log.d("SubaoData", c(str));
        }
    }

    private void b(String str) {
        if (str != null) {
            Log.w("SubaoData", c(str));
        }
    }

    @NonNull
    private String c(String str) {
        return "Portal." + b() + ": " + str;
    }

    /* access modifiers changed from: protected */
    @NonNull
    public URL i() {
        return new URL(this.c.c.a, this.c.c.b, this.c.c.c, String.format("/api/%s/%s/%s", new Object[]{c(), this.c.a, a()}));
    }

    /* access modifiers changed from: protected */
    @NonNull
    public String c() {
        return "v1";
    }

    @NonNull
    private String d() {
        return b() + ".portal2";
    }

    @NonNull
    private com.subao.common.f.c e() {
        return this.c.a(d());
    }

    /* access modifiers changed from: protected */
    @NonNull
    public String j() {
        return a.C0013a.JSON.e;
    }

    /* access modifiers changed from: protected */
    public boolean c(aa aaVar) {
        return aaVar != null;
    }

    /* access modifiers changed from: protected */
    public int k() {
        return GamesStatusCodes.STATUS_REAL_TIME_CONNECTION_FAILED;
    }

    /* access modifiers changed from: protected */
    @Nullable
    public aa l() {
        String str;
        aa aaVar;
        InputStream inputStream;
        com.subao.common.f.c e2 = e();
        synchronized (this.e) {
            if (e2.b()) {
                try {
                    inputStream = e2.c();
                    try {
                        aa a2 = aa.a(inputStream);
                        com.subao.common.e.a((Closeable) inputStream);
                        str = null;
                        aaVar = a2;
                    } catch (IOException e3) {
                        e = e3;
                    }
                } catch (IOException e4) {
                    e = e4;
                    inputStream = null;
                } catch (Throwable th) {
                    th = th;
                    inputStream = null;
                    com.subao.common.e.a((Closeable) inputStream);
                    throw th;
                }
            } else {
                str = null;
                aaVar = null;
            }
        }
        b(str);
        return aaVar;
        try {
            str = e.getMessage();
            com.subao.common.e.a((Closeable) inputStream);
            aaVar = null;
            b(str);
            return aaVar;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    /* access modifiers changed from: package-private */
    public final void m() {
        com.subao.common.f.c e2 = e();
        synchronized (this.e) {
            e2.f();
        }
    }

    /* access modifiers changed from: protected */
    public boolean d(@Nullable aa aaVar) {
        return aaVar != null && com.subao.common.e.a(this.c.b, aaVar.d());
    }

    @NonNull
    public final a n() {
        return this.c;
    }

    /* access modifiers changed from: protected */
    public boolean e(@Nullable aa aaVar) {
        return a(aaVar, false);
    }

    /* access modifiers changed from: protected */
    public boolean a(@Nullable aa aaVar, boolean z) {
        boolean o = o();
        if (o) {
            com.subao.common.m.d.a(new g(this, aaVar, z));
        }
        if (h()) {
            a("execute() return: " + o);
        }
        return o;
    }

    private boolean o() {
        boolean z;
        String b2 = b();
        synchronized (a) {
            if (a.contains(b2)) {
                z = false;
            } else {
                z = true;
                a.add(b2);
            }
        }
        return z;
    }

    /* access modifiers changed from: private */
    public void p() {
        synchronized (a) {
            a.remove(b());
        }
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public aa b(@Nullable aa aaVar, boolean z) {
        aa aaVar2;
        boolean z2;
        aa aaVar3 = null;
        boolean h = h();
        if (z) {
            aaVar = l();
            if (h) {
                a("Load from file: " + com.subao.common.n.g.a((Object) aaVar));
                aaVar2 = aaVar;
            }
            aaVar2 = aaVar;
        } else {
            if (h) {
                a("Use init data: " + com.subao.common.n.g.a((Object) aaVar));
            }
            aaVar2 = aaVar;
        }
        if (aaVar2 == null || !d(aaVar2)) {
            z2 = false;
        } else {
            z2 = true;
        }
        if (z2) {
            long currentTimeMillis = System.currentTimeMillis() - aaVar2.e();
            if (currentTimeMillis < 0) {
                if (currentTimeMillis > -3600000) {
                    if (!h) {
                        return aaVar2;
                    }
                    a("Data not expired: " + (currentTimeMillis / 1000));
                    return aaVar2;
                } else if (h) {
                    a("Too large cache alive time: " + (currentTimeMillis / 1000));
                }
            }
        }
        if (h) {
            a("Try download from network ...");
        }
        c a2 = new e(z2 ? aaVar2.c() : null, h).a();
        if (a2 == null) {
            return aaVar2;
        }
        synchronized (z.class) {
            b = f();
        }
        d dVar = new d();
        if (z2) {
            aaVar3 = aaVar2;
        }
        return dVar.a(a2, aaVar3, h);
    }

    /* access modifiers changed from: protected */
    public void a(@Nullable aa aaVar) {
    }

    /* access modifiers changed from: private */
    public void b(@NonNull aa aaVar) {
        OutputStream outputStream;
        String str = null;
        if (h()) {
            a("Save data, expire time: " + com.subao.common.n.c.a(com.subao.common.n.c.b(aaVar.e()), 7));
        }
        com.subao.common.f.c e2 = e();
        synchronized (this.e) {
            try {
                outputStream = e2.d();
                try {
                    aaVar.a(outputStream);
                    com.subao.common.e.a((Closeable) outputStream);
                } catch (IOException e3) {
                    e = e3;
                    try {
                        str = e.getMessage();
                        com.subao.common.e.a((Closeable) outputStream);
                        b(str);
                    } catch (Throwable th) {
                        th = th;
                    }
                }
            } catch (IOException e4) {
                e = e4;
                outputStream = null;
            } catch (Throwable th2) {
                th = th2;
                outputStream = null;
                com.subao.common.e.a((Closeable) outputStream);
                throw th;
            }
        }
        b(str);
    }

    /* compiled from: PortalDataDownloader */
    public static abstract class a extends u {
        @NonNull
        public abstract com.subao.common.f.c a(String str);

        public a(String str, String str2, ai aiVar, j jVar) {
            super(str, str2, a(aiVar), jVar);
        }

        @NonNull
        private static ai a(ai aiVar) {
            return aiVar == null ? f.c : aiVar;
        }
    }

    /* compiled from: PortalDataDownloader */
    public static class b extends a {
        private final a e;

        public b(a aVar) {
            super("common", aVar.b, aVar.c, aVar.d);
            this.e = aVar;
        }

        @NonNull
        public com.subao.common.f.c a(String str) {
            return this.e.a(str);
        }
    }

    /* compiled from: PortalDataDownloader */
    static class g implements Runnable {
        @Nullable
        private z a;
        @Nullable
        private aa b;
        private final boolean c;

        g(@NonNull z zVar, @Nullable aa aaVar, boolean z) {
            this.a = zVar;
            this.b = aaVar;
            this.c = z;
        }

        public void run() {
            z zVar = this.a;
            if (zVar != null) {
                try {
                    zVar.a(zVar.b(this.b, this.c));
                } finally {
                    zVar.p();
                    this.b = null;
                    this.a = null;
                }
            }
        }
    }

    /* compiled from: PortalDataDownloader */
    static class c {
        @NonNull
        public final a.c a;
        @Nullable
        public final String b;
        public final long c;

        c(@NonNull a.c cVar, @Nullable String str, long j) {
            this.a = cVar;
            this.b = str;
            this.c = j;
        }
    }

    /* compiled from: PortalDataDownloader */
    private class e {
        @Nullable
        private final String b;
        private final boolean c;

        e(String str, @Nullable boolean z) {
            this.b = str;
            this.c = z;
        }

        /* access modifiers changed from: package-private */
        @Nullable
        public c a() {
            int max = Math.max(f.a(), 0) + 1;
            int unused = z.this.d = 0;
            int i = 0;
            while (i < max) {
                long a2 = f.a(i);
                if (a2 > 0) {
                    SystemClock.sleep(a2);
                }
                z.b(z.this);
                try {
                    c b2 = b();
                    if (b2.a.a != 500) {
                        return b2;
                    }
                    i++;
                } catch (IOException e) {
                    if (this.c) {
                        z.this.a(e.getMessage());
                    }
                } catch (RuntimeException e2) {
                    if (this.c) {
                        z.this.a(e2.getMessage());
                    }
                }
            }
            return null;
        }

        @NonNull
        private c b() {
            int k = z.this.k();
            HttpURLConnection a2 = new com.subao.common.j.a(k, k).a(z.this.i(), a.b.GET, a.C0013a.JSON.e);
            com.subao.common.j.a.b(a2, z.this.j());
            if (this.b != null) {
                a2.setRequestProperty(Headers.GET_OBJECT_IF_NONE_MATCH, this.b);
                if (this.c) {
                    z.this.a("Cache TAG: " + this.b);
                }
            }
            return new c(com.subao.common.j.a.b(a2), a2.getHeaderField(Headers.ETAG), z.a(a2));
        }
    }

    /* compiled from: PortalDataDownloader */
    private class d {
        private d() {
        }

        /* access modifiers changed from: package-private */
        @Nullable
        public aa a(@NonNull c cVar, @Nullable aa aaVar, boolean z) {
            switch (cVar.a.a) {
                case 200:
                    return c(cVar, aaVar, z);
                case 304:
                    return b(cVar, aaVar, z);
                case 404:
                    return a(z);
                default:
                    if (!z) {
                        return aaVar;
                    }
                    z.this.a("Server response: " + cVar.a.a);
                    return aaVar;
            }
        }

        @Nullable
        private aa a(boolean z) {
            if (z) {
                z.this.a("Response 404 not found, remove local cache.");
            }
            z.this.m();
            return null;
        }

        @Nullable
        private aa b(@NonNull c cVar, @Nullable aa aaVar, boolean z) {
            if (z) {
                z.this.a("Portal data not modified.");
            }
            if (aaVar != null) {
                aaVar.a(cVar.c);
                z.this.b(aaVar);
            }
            return aaVar;
        }

        @Nullable
        private aa c(@NonNull c cVar, @Nullable aa aaVar, boolean z) {
            aa aaVar2 = new aa(cVar.b, cVar.c, z.this.c.b, cVar.a.b, true);
            if (z.this.c(aaVar2)) {
                if (z) {
                    z.this.a("Serialize download data " + aaVar2);
                }
                z.this.b(aaVar2);
                return aaVar2;
            }
            z.this.a("Invalid download data " + aaVar2);
            return aaVar;
        }
    }

    /* compiled from: PortalDataDownloader */
    public static class f {
        public static int a() {
            return 3;
        }

        public static long a(int i) {
            if (i <= 0) {
                return 0;
            }
            return (((long) (Math.random() * 4000.0d)) + 3000) * ((long) i);
        }
    }
}
