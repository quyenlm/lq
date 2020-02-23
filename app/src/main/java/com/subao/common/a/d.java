package com.subao.common.a;

import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Base64;
import android.util.JsonReader;
import android.util.Log;
import com.subao.common.a.c;
import com.subao.common.b.e;
import com.subao.common.e.ai;
import com.subao.common.e.an;
import com.subao.common.e.h;
import com.subao.common.e.n;
import com.subao.common.e.q;
import com.subao.common.e.s;
import com.subao.common.e.w;
import com.subao.common.i.d;
import com.subao.common.j.d;
import com.subao.common.j.j;
import com.subao.common.j.k;
import com.subao.common.n.g;
import com.subao.vpn.JniCallback;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Locale;

/* compiled from: JniCallbackImpl */
public class d implements JniCallback {
    private final c a;
    /* access modifiers changed from: private */
    public final com.subao.common.g.c b;
    private final j c;
    private final ai d;
    private final s.a e;
    @NonNull
    private final w f = new w(com.subao.common.f.d.a(new File(com.subao.common.f.a.a(), "proxy_data")));
    @NonNull
    private final w g = new w(com.subao.common.f.d.a(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "cn.wsds.game.data")));
    @NonNull
    private final com.subao.common.b.b h = new com.subao.common.b.c(this.a, this.d, this.a.e);

    /* compiled from: JniCallbackImpl */
    static class b {
        final w a;
        final String b;

        b(@NonNull w wVar, String str) {
            this.a = wVar;
            this.b = str;
        }

        /* access modifiers changed from: package-private */
        public void a(int i, com.subao.common.g.c cVar) {
            this.a.a(this.b, cVar, i);
        }

        /* access modifiers changed from: package-private */
        public void a(String str) {
            this.a.a(this.b, str);
        }

        /* access modifiers changed from: package-private */
        public void a(String str, String str2, boolean z) {
            this.a.a(this.b, str, str2, z);
        }

        /* access modifiers changed from: package-private */
        public void a(int i, com.subao.common.g.c cVar, String str) {
            this.a.a(this.b, str, cVar, i);
        }
    }

    public d(@NonNull c cVar, @NonNull com.subao.common.g.c cVar2, j jVar, ai aiVar, s.a aVar) {
        this.a = cVar;
        this.b = cVar2;
        this.c = jVar;
        this.d = aiVar;
        this.e = aVar;
    }

    public void a(boolean z) {
        this.a.a(z);
    }

    public void a(int i) {
        com.subao.common.d.a("SubaoParallel", "Proxy request mobile fd ...");
        this.a.c(i);
    }

    public void a(int i, int i2) {
        com.subao.common.d.a("SubaoData", "Proxy request region and isp ...");
        com.subao.common.j.d.a(this.a.i(), (String) null, i2, (d.b) new d.b() {
            public void a(Object obj, d.C0016d dVar) {
                if (dVar != null) {
                    d.this.b.b(((Integer) obj).intValue(), "key_isp", d.a(dVar));
                }
            }
        }, (Object) Integer.valueOf(i), this.c.a());
    }

    static String a(d.C0016d dVar) {
        if (dVar == null) {
            com.subao.common.d.a("SubaoNet", "getISPResultFormat, result = null, return -1.-1");
            return "-1.-1";
        }
        com.subao.common.e.j a2 = dVar.a();
        Locale locale = n.b;
        Object[] objArr = new Object[2];
        objArr[0] = Integer.valueOf(dVar.b);
        objArr[1] = Integer.valueOf(a2 == null ? 1 : a2.d);
        return String.format(locale, "%d.%d", objArr);
    }

    public void a(String str) {
        this.a.k().a(str);
    }

    public void b(String str) {
        this.a.k().a("lua_error", str);
    }

    public void a(String str, String str2, String str3) {
        if (com.subao.common.d.a("SubaoData")) {
            Log.d("SubaoData", "Accel-Info: " + str);
        }
        an.a(this.e, new s.d(str2, str3), str.getBytes());
    }

    public void a(int i, String str) {
        c(str).a(i, this.b);
    }

    public void a(int i, String str, String str2) {
        c(str).a(str2);
    }

    public void a(int i, String str, String str2, String str3) {
        a(str, str2, str3, false);
    }

    public void b(int i, String str, String str2, String str3) {
        a(str, str2, str3, true);
    }

    private void a(String str, String str2, String str3, boolean z) {
        c(str).a(str2, str3, z);
    }

    public void b(int i, String str, String str2) {
        c(str).a(i, this.b, str2);
    }

    @NonNull
    private b c(@NonNull String str) {
        if (str.startsWith("*")) {
            return new b(this.g, str.substring(1));
        }
        return new b(this.f, str);
    }

    public void b(final int i, String str) {
        h.a(this.e.a, this.e.c, str, new h.a() {
            public void a(boolean z) {
                d.this.b.a(i, "key_beacon_counter_result", z ? 1 : 0);
            }
        });
    }

    public int b(int i) {
        return this.a.d(i);
    }

    public void b(String str, String str2, String str3) {
        q.a(this.e, new s.d(str, str2), str3);
    }

    public void a(int i, String str, int i2, String str2) {
        com.subao.common.b.a.a(str, i2, str2, (com.subao.common.j.n) new c.q((d.b) null, i, str, i2, str2));
    }

    public void a(int i, int i2, String str, String str2, @Nullable String str3, @Nullable String str4) {
        new com.subao.common.j.b(this.b, i).a(i2, str, str2, TextUtils.isEmpty(str3) ? null : str3.getBytes(), str4);
    }

    public void a(int i, String str, String str2, String str3, int i2) {
        com.subao.common.m.b.a().a(new c(this.b, i, str, str2, str3, i2));
    }

    public void c(int i, String str) {
        com.subao.common.m.d.a(new a(this.b, i, str));
    }

    public void a(int i, int i2, @Nullable String str) {
        com.subao.common.m.b.a().a(new C0001d(this.h, i, i2, str));
    }

    public void d(int i, String str) {
        com.subao.common.j.d.a(this.a.i(), str, new d.b() {
            public void a(Object obj, d.C0016d dVar) {
                com.subao.common.d.a("SubaoNet", "requestIPRegion, result = " + d.a(dVar));
                d.this.b.b(((Integer) obj).intValue(), d.a(dVar));
            }
        }, Integer.valueOf(i), this.d);
    }

    public void e(int i, String str) {
        this.a.a(i, str);
    }

    public void a(int i, int i2, int i3, boolean z, String str) {
        this.a.a(i2, i3, z, str);
    }

    public void a(int i, int i2, int i3) {
        this.a.a(i2, i3);
    }

    /* compiled from: JniCallbackImpl */
    static class c implements Runnable {
        private final com.subao.common.g.c a;
        private final int b;
        private final String c;
        private final String d;
        private final String e;
        private final int f;

        private c(com.subao.common.g.c cVar, int i, String str, String str2, String str3, int i2) {
            this.a = cVar;
            this.b = i;
            this.c = str;
            this.d = str2;
            this.e = str3;
            this.f = i2;
        }

        @NonNull
        static String a(String str, String str2, String str3, int i) {
            try {
                byte[] a2 = com.subao.common.n.b.a(str2, str.getBytes());
                if ("BASE64".compareToIgnoreCase(str3) == 0) {
                    return Base64.encodeToString(a2, i);
                }
                return g.a(a2, i != 0);
            } catch (NoSuchAlgorithmException e2) {
                e2.printStackTrace();
                return "";
            }
        }

        public void run() {
            this.a.a(this.b, a(this.c, this.d, this.e, this.f), com.subao.common.l.a.a((k.a) null));
        }
    }

    /* compiled from: JniCallbackImpl */
    private static class a implements Runnable {
        @NonNull
        private final com.subao.common.g.c a;
        private final int b;
        @NonNull
        private final String c;

        private a(@NonNull com.subao.common.g.c cVar, int i, @NonNull String str) {
            this.b = i;
            this.c = str;
            this.a = cVar;
        }

        public void run() {
            this.a.a(this.b, a());
        }

        @Nullable
        private String a() {
            try {
                InetAddress[] allByName = InetAddress.getAllByName(this.c);
                if (allByName == null || allByName.length == 0) {
                    return null;
                }
                ArrayList<Inet4Address> arrayList = new ArrayList<>(allByName.length);
                for (InetAddress inetAddress : allByName) {
                    if (!inetAddress.isLinkLocalAddress() && !inetAddress.isSiteLocalAddress() && !inetAddress.isMulticastAddress() && !inetAddress.isAnyLocalAddress() && !inetAddress.isLoopbackAddress() && (inetAddress instanceof Inet4Address)) {
                        arrayList.add((Inet4Address) inetAddress);
                    }
                }
                if (arrayList.isEmpty()) {
                    return null;
                }
                StringBuilder sb = new StringBuilder(256);
                for (Inet4Address hostAddress : arrayList) {
                    sb.append(hostAddress.getHostAddress()).append(',');
                }
                return sb.toString();
            } catch (IOException e) {
                return null;
            }
        }
    }

    /* renamed from: com.subao.common.a.d$d  reason: collision with other inner class name */
    /* compiled from: JniCallbackImpl */
    private static class C0001d implements Runnable {
        private final com.subao.common.b.b a;
        private final int b;
        private final int c;
        private final String d;

        private C0001d(com.subao.common.b.b bVar, int i, int i2, String str) {
            this.a = bVar;
            this.b = i;
            this.c = i2;
            this.d = str;
        }

        public void run() {
            int i = this.c;
            if (i == 0 && !TextUtils.isEmpty(this.d)) {
                e a2 = a(this.d);
                if (a2 != null) {
                    this.a.a(this.b, a2);
                    return;
                }
                i = -1000;
            }
            this.a.a(this.b, i);
        }

        @Nullable
        private static e a(@NonNull String str) {
            JsonReader jsonReader;
            e eVar = null;
            try {
                jsonReader = new JsonReader(new InputStreamReader(new ByteArrayInputStream(str.getBytes("UTF-8"))));
                try {
                    eVar = e.a(jsonReader);
                    com.subao.common.e.a((Closeable) jsonReader);
                } catch (IOException e) {
                    e = e;
                    try {
                        e.printStackTrace();
                        com.subao.common.e.a((Closeable) jsonReader);
                        return eVar;
                    } catch (Throwable th) {
                        th = th;
                        com.subao.common.e.a((Closeable) jsonReader);
                        throw th;
                    }
                }
            } catch (IOException e2) {
                e = e2;
                jsonReader = null;
                e.printStackTrace();
                com.subao.common.e.a((Closeable) jsonReader);
                return eVar;
            } catch (Throwable th2) {
                th = th2;
                jsonReader = null;
                com.subao.common.e.a((Closeable) jsonReader);
                throw th;
            }
            return eVar;
        }
    }
}
