package com.subao.common.i;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.JsonWriter;
import android.util.Log;
import com.subao.common.c;
import com.subao.common.d;
import com.subao.common.e;
import com.subao.common.e.ai;
import com.subao.common.e.aj;
import com.subao.common.e.n;
import com.subao.common.i.m;
import com.subao.common.i.n;
import com.subao.common.j.a;
import com.subao.common.j.j;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/* compiled from: MessageSenderImpl */
public class g implements f {
    final a a;

    private g(ai aiVar, h hVar) {
        this.a = new a(aiVar, hVar);
    }

    @NonNull
    public static f a(ai aiVar, h hVar) {
        return new g(aiVar, hVar);
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: private */
    public static byte[] b(c cVar) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(4096);
        JsonWriter jsonWriter = new JsonWriter(new OutputStreamWriter(byteArrayOutputStream));
        try {
            cVar.serialize(jsonWriter);
            e.a((Closeable) jsonWriter);
            if (d.a("SubaoMessage")) {
                Log.d("SubaoMessage", byteArrayOutputStream.toString());
            }
            return byteArrayOutputStream.toByteArray();
        } catch (Throwable th) {
            e.a((Closeable) jsonWriter);
            throw th;
        }
    }

    public void a(n nVar) {
        a aVar = this.a;
        a aVar2 = this.a;
        aVar2.getClass();
        aVar.post(new a.h(nVar));
    }

    public void a(int i, int i2, List<k> list) {
        a aVar = this.a;
        a aVar2 = this.a;
        aVar2.getClass();
        aVar.post(new a.j(i, i2, list));
    }

    public void a(String str, String str2) {
        a aVar = this.a;
        a aVar2 = this.a;
        aVar2.getClass();
        aVar.post(new a.e(str, str2));
    }

    public void a(m mVar) {
        a aVar = this.a;
        a aVar2 = this.a;
        aVar2.getClass();
        aVar.post(new a.f(mVar));
    }

    public void a(m.a aVar) {
        if (aVar != null) {
            a aVar2 = this.a;
            a aVar3 = this.a;
            aVar3.getClass();
            aVar2.post(new a.d(aVar));
        }
    }

    public void a(String str) {
        a aVar = this.a;
        a aVar2 = this.a;
        aVar2.getClass();
        aVar.post(new a.C0012g(str));
    }

    /* compiled from: MessageSenderImpl */
    static class a extends Handler {
        final h a;
        final ai b;
        final l c;
        private final k d;
        /* access modifiers changed from: private */
        public int e = 15000;

        a(ai aiVar, h hVar) {
            super(a());
            this.b = aiVar;
            this.a = hVar;
            this.d = a.a(hVar.a());
            this.c = new l(hVar.a());
        }

        private static Looper a() {
            HandlerThread handlerThread = new HandlerThread("subao_mu");
            handlerThread.start();
            return handlerThread.getLooper();
        }

        /* compiled from: MessageSenderImpl */
        static class k implements Runnable {
            private final String a;

            k(String str) {
                this.a = str;
            }

            public void run() {
                aj.b().b(this.a);
            }
        }

        /* renamed from: com.subao.common.i.g$a$a  reason: collision with other inner class name */
        /* compiled from: MessageSenderImpl */
        abstract class C0011a implements Runnable {
            public final String a;
            private byte[] c;
            private URL d;

            /* access modifiers changed from: protected */
            public abstract void a(a.c cVar);

            /* access modifiers changed from: protected */
            public abstract String b();

            /* access modifiers changed from: protected */
            public abstract byte[] c();

            /* access modifiers changed from: protected */
            public abstract void e();

            C0011a(String str) {
                this.a = str;
            }

            public void run() {
                HttpURLConnection a2;
                a.c b2;
                byte[] c2;
                a.b a3 = a();
                if (a3 == null) {
                    throw new NullPointerException("Null HTTP method");
                }
                try {
                    a2 = new com.subao.common.j.a(a.this.e, a.this.e).a(f(), a3, a.C0013a.JSON.e);
                    com.subao.common.j.a.a(a2, a.C0013a.JSON.e);
                    switch (a3) {
                        case DELETE:
                        case GET:
                            b2 = com.subao.common.j.a.b(a2);
                            break;
                        default:
                            if (d()) {
                                if (this.c == null) {
                                    this.c = c();
                                }
                                c2 = this.c;
                            } else {
                                c2 = c();
                            }
                            b2 = com.subao.common.j.a.a(a2, c2);
                            break;
                    }
                    a(b2);
                    a2.disconnect();
                } catch (IOException | RuntimeException e) {
                    e();
                } catch (Throwable th) {
                    a2.disconnect();
                    throw th;
                }
            }

            private URL f() {
                if (this.d == null) {
                    String b2 = b();
                    String str = a.this.b.a;
                    String str2 = a.this.b.b;
                    int i = a.this.b.c;
                    if (b2 == null) {
                        b2 = "";
                    }
                    this.d = new URL(str, str2, i, b2);
                }
                return this.d;
            }

            /* access modifiers changed from: package-private */
            public final void a(long j) {
                a.this.postDelayed(this, j);
            }

            /* access modifiers changed from: protected */
            public a.b a() {
                return a.b.POST;
            }

            /* access modifiers changed from: package-private */
            public boolean d() {
                return true;
            }
        }

        /* compiled from: MessageSenderImpl */
        class h extends C0011a {
            private final n d;
            private int e = 10;

            h(n nVar) {
                super("Installation");
                this.d = nVar;
            }

            /* access modifiers changed from: protected */
            public String b() {
                return "/v3/report/client/installation/android";
            }

            /* access modifiers changed from: protected */
            public byte[] c() {
                return g.b(this.d);
            }

            /* access modifiers changed from: protected */
            public void e() {
                f();
            }

            /* access modifiers changed from: protected */
            public void a(a.c cVar) {
                switch (cVar.a) {
                    case 200:
                    case 201:
                        String a = e.a(cVar.b);
                        if (a != null) {
                            a.this.a.a(new k(a));
                            return;
                        }
                        return;
                    case 500:
                        f();
                        return;
                    default:
                        return;
                }
            }

            private void f() {
                if (this.e <= 320) {
                    if (com.subao.common.d.a("SubaoMessage")) {
                        Log.d("SubaoMessage", String.format(n.b, "Installation message post failed, retry after %d seconds", new Object[]{Integer.valueOf(this.e)}));
                    }
                    a((long) (this.e * 1000));
                    this.e *= 2;
                    return;
                }
                com.subao.common.d.a("SubaoMessage", "Retry stopped");
            }
        }

        /* compiled from: MessageSenderImpl */
        private abstract class b extends C0011a {
            private final int d;
            private final boolean e;
            private long f;
            private int g;

            b(a aVar, String str, int i) {
                this(aVar, str, i, 10000);
            }

            b(a aVar, String str, int i, long j) {
                this(str, i, j, false);
            }

            b(String str, int i, long j, boolean z) {
                super(str);
                this.d = i;
                this.f = j;
                this.e = z;
            }

            /* access modifiers changed from: package-private */
            public final void f() {
                this.g++;
                if (this.g <= this.d) {
                    a(this.f);
                    if (this.e) {
                        this.f *= 2;
                    }
                    if (com.subao.common.d.a("SubaoMessage")) {
                        Log.d("SubaoMessage", String.format(n.b, "[%s] retry after %d milliseconds (%d/%d)", new Object[]{this.a, Long.valueOf(this.f), Integer.valueOf(this.g), Integer.valueOf(this.d)}));
                    }
                }
            }

            /* access modifiers changed from: protected */
            public void e() {
                f();
            }

            /* access modifiers changed from: protected */
            public void a(a.c cVar) {
                if (cVar.a == 500) {
                    f();
                }
            }
        }

        /* compiled from: MessageSenderImpl */
        private abstract class i extends b {
            i(String str) {
                super(a.this, str, 1, 10000);
            }
        }

        /* compiled from: MessageSenderImpl */
        class j extends i {
            private final int f;
            private final int g;

            j(int i, int i2, List<k> list) {
                super("Start");
                this.f = i;
                this.g = i2;
            }

            /* access modifiers changed from: protected */
            public String b() {
                return "/v3/report/client/start/android";
            }

            /* access modifiers changed from: protected */
            public byte[] c() {
                return g.b(a.this.a.e().a(j.a(), this.f, this.g));
            }

            private void a(byte[] bArr) {
                a.this.a.b();
                String a = e.a(bArr);
                if (aj.a(a)) {
                    if (com.subao.common.d.a("SubaoMessage")) {
                        Log.d("SubaoMessage", "Response of 'start': subaoId=" + a);
                    }
                    a.this.a.a(new k(a));
                    return;
                }
                com.subao.common.d.a("SubaoMessage", "Response of 'start', subaoId is invalid");
                a.this.a.a(new Runnable() {
                    public void run() {
                        aj.b().b((String) null);
                        a.this.post(new h(a.this.a.e().a(System.currentTimeMillis() / 1000, n.a.a(a.this.a.a(), a.this.a.d(), a.this.a.c()))));
                    }
                });
            }

            /* access modifiers changed from: protected */
            public void a(a.c cVar) {
                switch (cVar.a) {
                    case 201:
                        a(cVar.b);
                        return;
                    default:
                        super.a(cVar);
                        return;
                }
            }
        }

        /* compiled from: MessageSenderImpl */
        abstract class c extends b {
            protected c() {
                super(a.this, "Event", 10);
            }

            /* access modifiers changed from: protected */
            public String b() {
                return "/v3/report/client/event";
            }
        }

        /* compiled from: MessageSenderImpl */
        class f extends c {
            private m f;

            f(m mVar) {
                super();
                this.f = mVar;
            }

            /* access modifiers changed from: protected */
            public byte[] c() {
                if (this.f == null) {
                    return null;
                }
                byte[] a = g.b(this.f);
                this.f = null;
                if (!com.subao.common.d.a("SubaoMessage")) {
                    return a;
                }
                Log.d("SubaoMessage", "MessageEvent: " + new String(a));
                return a;
            }
        }

        /* compiled from: MessageSenderImpl */
        class e extends c {
            private final String f;
            private final String g;
            private boolean h;

            e(String str, String str2) {
                super();
                this.f = str;
                this.g = str2;
            }

            /* access modifiers changed from: protected */
            public byte[] c() {
                if (this.h || this.f == null || this.g == null) {
                    return null;
                }
                this.h = true;
                byte[] a = g.b(a.this.a.e().a(j.a(), this.f, this.g));
                if (!com.subao.common.d.a("SubaoMessage")) {
                    return a;
                }
                Log.d("SubaoMessage", "MessageEvent: " + new String(a));
                return a;
            }
        }

        /* renamed from: com.subao.common.i.g$a$g  reason: collision with other inner class name */
        /* compiled from: MessageSenderImpl */
        class C0012g extends c {
            private final byte[] f;

            C0012g(String str) {
                super();
                this.f = TextUtils.isEmpty(str) ? null : str.getBytes();
                if (com.subao.common.d.a("SubaoMessage")) {
                    Log.d("SubaoMessage", "MessageEvent: " + str);
                }
            }

            /* access modifiers changed from: protected */
            public byte[] c() {
                return this.f;
            }
        }

        /* compiled from: MessageSenderImpl */
        class d extends c {
            private m.a f;

            d(m.a aVar) {
                super();
                this.f = aVar;
            }

            /* access modifiers changed from: protected */
            public byte[] c() {
                if (this.f == null) {
                    return null;
                }
                ArrayList arrayList = new ArrayList(1);
                arrayList.add(this.f);
                m a = a.this.a.e().a(j.a(), (List<m.a>) arrayList);
                this.f = null;
                return g.b(a);
            }
        }
    }

    /* renamed from: com.subao.common.i.g$1  reason: invalid class name */
    /* compiled from: MessageSenderImpl */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] a = new int[j.a.values().length];

        static {
            b = new int[a.b.values().length];
            try {
                b[a.b.DELETE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                b[a.b.GET.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[j.a.MOBILE_2G.ordinal()] = 1;
            } catch (NoSuchFieldError e3) {
            }
            try {
                a[j.a.MOBILE_3G.ordinal()] = 2;
            } catch (NoSuchFieldError e4) {
            }
            try {
                a[j.a.MOBILE_4G.ordinal()] = 3;
            } catch (NoSuchFieldError e5) {
            }
            try {
                a[j.a.WIFI.ordinal()] = 4;
            } catch (NoSuchFieldError e6) {
            }
        }
    }
}
