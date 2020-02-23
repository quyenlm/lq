package com.subao.common.e;

import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.subao.common.e;
import com.subao.common.j.a;
import com.subao.common.j.j;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Executor;

/* compiled from: HRDataTrans */
public abstract class s {
    @NonNull
    protected final a a;
    @NonNull
    protected final d b;
    @NonNull
    private final a.b c;
    @Nullable
    private final byte[] d;

    /* access modifiers changed from: protected */
    public abstract int a();

    /* access modifiers changed from: protected */
    public abstract String b();

    protected s(@NonNull a aVar, @NonNull d dVar, @NonNull a.b bVar, @Nullable byte[] bArr) {
        this.a = aVar;
        this.b = dVar;
        this.c = bVar;
        this.d = bArr;
    }

    private static void a(URLConnection uRLConnection, String str, String str2) {
        if (!TextUtils.isEmpty(str2)) {
            uRLConnection.setRequestProperty(str, str2);
        }
    }

    public void a(@NonNull Executor executor) {
        executor.execute(new c());
    }

    /* access modifiers changed from: protected */
    public void a(@Nullable b bVar) {
    }

    /* access modifiers changed from: private */
    @NonNull
    public b a(URL url) {
        HttpURLConnection httpURLConnection;
        HttpURLConnection httpURLConnection2;
        a.c b2;
        a.c cVar = null;
        try {
            httpURLConnection2 = new com.subao.common.j.a(15000, 15000).a(url, this.c, a.C0013a.JSON.e);
            try {
                if (d() && !TextUtils.isEmpty(this.b.b)) {
                    httpURLConnection2.addRequestProperty("Authorization", "Bearer " + this.b.b);
                }
                a(httpURLConnection2, "userId", this.b.a);
                a(httpURLConnection2, "accessToken", this.b.c);
                switch (this.c) {
                    case GET:
                    case DELETE:
                        b2 = com.subao.common.j.a.b(httpURLConnection2);
                        break;
                    default:
                        b2 = com.subao.common.j.a.a(httpURLConnection2, this.d);
                        break;
                }
                cVar = b2;
            } catch (IOException e) {
                httpURLConnection = httpURLConnection2;
            } catch (RuntimeException e2) {
                httpURLConnection = httpURLConnection2;
            }
        } catch (IOException e3) {
            httpURLConnection = null;
        } catch (RuntimeException e4) {
            httpURLConnection = null;
        }
        return new b(httpURLConnection2, cVar);
        httpURLConnection2 = httpURLConnection;
        return new b(httpURLConnection2, cVar);
    }

    /* access modifiers changed from: private */
    public URL e() {
        return new URL(c(), this.a.c.b, this.a.c.c, b());
    }

    /* access modifiers changed from: protected */
    public String c() {
        return this.a.c.a;
    }

    /* access modifiers changed from: protected */
    public boolean d() {
        return true;
    }

    /* compiled from: HRDataTrans */
    public static class a extends u {
        public a(@NonNull String str, @NonNull String str2, @Nullable ai aiVar, @Nullable j jVar) {
            super(str, str2, a(aiVar), jVar);
        }

        @NonNull
        private static ai a(@Nullable ai aiVar) {
            if (aiVar == null) {
                return f.b;
            }
            return aiVar;
        }
    }

    /* compiled from: HRDataTrans */
    public static class d {
        public final String a;
        public final String b;
        public final String c;

        public d(String str, String str2) {
            this(str, str2, (String) null);
        }

        public d(String str, String str2, String str3) {
            this.a = str;
            this.b = str2;
            this.c = str3;
        }

        public boolean equals(Object obj) {
            boolean z = true;
            if (obj == null) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof d)) {
                return false;
            }
            d dVar = (d) obj;
            if (!e.a(this.a, dVar.a) || !e.a(this.b, dVar.b) || !e.a(this.c, dVar.c)) {
                z = false;
            }
            return z;
        }
    }

    /* compiled from: HRDataTrans */
    public static class b {
        @Nullable
        public final HttpURLConnection a;
        @Nullable
        public final a.c b;

        public b(@Nullable HttpURLConnection httpURLConnection, @Nullable a.c cVar) {
            this.a = httpURLConnection;
            this.b = cVar;
        }
    }

    /* compiled from: HRDataTrans */
    private class c implements Runnable {
        private c() {
        }

        @Nullable
        private b a() {
            try {
                URL a2 = s.this.e();
                int a3 = s.this.a();
                int i = 10000;
                while (true) {
                    b a4 = s.this.a(a2);
                    if (a3 <= 0) {
                        return a4;
                    }
                    if (a4.b != null && a4.b.a != 500) {
                        return a4;
                    }
                    SystemClock.sleep((long) i);
                    a3--;
                    i *= 2;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        public void run() {
            s.this.a(a());
        }
    }
}
