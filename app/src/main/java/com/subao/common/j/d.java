package com.subao.common.j;

import android.content.Context;
import android.os.ConditionVariable;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.JsonReader;
import android.util.Log;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.subao.common.e.ai;
import com.subao.common.e.n;
import com.subao.common.j.a;
import com.subao.common.j.j;
import com.tencent.midas.oversea.network.http.APBaseHttpParam;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* compiled from: IPInfoQuery */
public final class d {
    private static boolean a;
    @Nullable
    private static ai b;
    /* access modifiers changed from: private */
    public static final a c = new a();
    private static final com.subao.common.m.c d = new com.subao.common.m.c();

    /* compiled from: IPInfoQuery */
    public interface b {
        void a(Object obj, C0016d dVar);
    }

    /* compiled from: IPInfoQuery */
    private interface f {
        @Nullable
        C0016d a(String str);

        boolean a();
    }

    private d() {
    }

    @Nullable
    private static synchronized ai b() {
        ai aiVar;
        synchronized (d.class) {
            aiVar = b;
        }
        return aiVar;
    }

    public static void a(@NonNull String str, @NonNull ai aiVar) {
        synchronized (d.class) {
            b = aiVar;
            a = SystemMediaRouteProvider.PACKAGE_NAME.equals(str);
        }
        c.a();
    }

    @Nullable
    private static C0016d c(@NonNull Context context, @NonNull j.a aVar) {
        String a2 = a(context, aVar);
        C0016d a3 = c.a(a2);
        if (com.subao.common.d.a("SubaoNet")) {
            Log.d("SubaoNet", String.format(n.b, "IPInfoQuery getMyInfo(%d, %s) return: %s", new Object[]{Integer.valueOf(aVar.g), a2, a3}));
        }
        return a3;
    }

    public static void a(Context context, @Nullable String str, int i, @NonNull b bVar, @Nullable Object obj, @NonNull j.a aVar) {
        C0016d c2;
        if (!TextUtils.isEmpty(str) || (c2 = c(context, aVar)) == null) {
            a(context, str, bVar, obj, (a || i == 2) ? new h(b()) : new g(), aVar);
        } else {
            bVar.a(obj, c2);
        }
    }

    public static void a(Context context, @Nullable String str, @NonNull b bVar, Object obj, @Nullable ai aiVar) {
        a(context, str, bVar, obj, (f) new h(aiVar), j.a.UNKNOWN);
    }

    private static void a(Context context, String str, b bVar, Object obj, @NonNull f fVar, @NonNull j.a aVar) {
        c cVar = new c(bVar, obj);
        String str2 = "";
        if (TextUtils.isEmpty(str)) {
            str2 = a(context, aVar);
        }
        d.execute(new e(fVar, str, cVar, str2));
    }

    @NonNull
    static String a(Context context, @NonNull j.a aVar) {
        String a2 = k.a(context, aVar);
        Locale locale = n.b;
        Object[] objArr = new Object[2];
        objArr[0] = Integer.valueOf(aVar.g);
        if (a2 == null) {
            a2 = "";
        }
        objArr[1] = a2;
        return String.format(locale, "%d_%s", objArr);
    }

    /* compiled from: IPInfoQuery */
    static class c {
        public final b a;
        final Object b;

        c(@NonNull b bVar, @Nullable Object obj) {
            this.a = bVar;
            this.b = obj;
        }

        /* access modifiers changed from: package-private */
        public void a(@Nullable C0016d dVar) {
            this.a.a(this.b, dVar);
        }
    }

    /* compiled from: IPInfoQuery */
    private static class e implements Runnable {
        @NonNull
        private final f a;
        @Nullable
        private final String b;
        @NonNull
        private final c c;
        @NonNull
        private final String d;

        e(@NonNull f fVar, @Nullable String str, @NonNull c cVar, @NonNull String str2) {
            this.a = fVar;
            this.b = str;
            this.c = cVar;
            this.d = str2;
        }

        public void run() {
            C0016d a2 = this.a.a(this.b);
            boolean a3 = com.subao.common.d.a("SubaoNet");
            if (a3) {
                Log.d("SubaoNet", String.format("IPInfoQuery (ip=%s, worker=%s) result: %s", new Object[]{this.b, this.a, a2}));
            }
            boolean isEmpty = TextUtils.isEmpty(this.b);
            if (a2 != null) {
                if (isEmpty && this.a.a()) {
                    d.c.a(this.d, a2);
                }
            } else if (isEmpty) {
                a2 = d.c.a(this.d);
                if (a3) {
                    Log.d("SubaoNet", "IPInfoQuery query failed, find cache data: " + com.subao.common.n.g.a((Object) a2));
                }
            }
            this.c.a(a2);
        }
    }

    @NonNull
    public static String b(@NonNull Context context, @NonNull j.a aVar) {
        C0016d a2 = c.a(a(context, aVar));
        return (a2 != null && !TextUtils.isEmpty(a2.a)) ? a2.a : "";
    }

    /* renamed from: com.subao.common.j.d$d  reason: collision with other inner class name */
    /* compiled from: IPInfoQuery */
    public static class C0016d {
        public final String a;
        public final int b;
        public final int c;
        public final String d;

        public C0016d(String str, int i, int i2, String str2) {
            this.a = str;
            this.b = i;
            this.c = i2;
            this.d = str2;
        }

        public boolean equals(Object obj) {
            boolean z = true;
            if (obj == null) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof C0016d)) {
                return false;
            }
            C0016d dVar = (C0016d) obj;
            if (this.b != dVar.b || this.c != dVar.c || !com.subao.common.e.a(this.a, dVar.a) || !com.subao.common.e.a(this.d, dVar.d)) {
                z = false;
            }
            return z;
        }

        public String toString() {
            String num;
            com.subao.common.e.j a2 = a();
            Locale locale = n.b;
            Object[] objArr = new Object[5];
            objArr[0] = this.a;
            objArr[1] = Integer.valueOf(this.b);
            objArr[2] = Integer.valueOf(this.c);
            if (a2 == null) {
                num = "unknown";
            } else {
                num = Integer.toString(a2.d);
            }
            objArr[3] = num;
            objArr[4] = this.d;
            return String.format(locale, "[%s, (%d.%d (%s)) (%s)]", objArr);
        }

        public com.subao.common.e.j a() {
            switch (this.c) {
                case 2:
                    return com.subao.common.e.j.CHINA_MOBILE;
                case 4:
                    return com.subao.common.e.j.CHINA_UNICOM;
                case 8:
                    return com.subao.common.e.j.CHINA_TELECOM;
                default:
                    return null;
            }
        }

        /* renamed from: com.subao.common.j.d$d$a */
        /* compiled from: IPInfoQuery */
        public static class a {
            public String a;
            public int b = -1;
            public int c;
            public String d;

            /* access modifiers changed from: package-private */
            public C0016d a() {
                return new C0016d(this.a, this.b, this.c, this.d);
            }
        }
    }

    /* compiled from: IPInfoQuery */
    static class g implements f {
        g() {
        }

        public boolean a() {
            return false;
        }

        @Nullable
        public C0016d a(String str) {
            InetAddress inetAddress;
            int i = 4;
            if (str != null && str.length() > 0) {
                return null;
            }
            try {
                inetAddress = a.a("isp-map.wsds.cn");
            } catch (IOException e) {
                inetAddress = null;
            }
            if (inetAddress == null) {
                return null;
            }
            if (com.subao.common.d.a("SubaoNet")) {
                Log.d("SubaoNet", "IPInfoQuery DNS: " + inetAddress.toString());
            }
            byte[] address = inetAddress.getAddress();
            if (address == null || address.length < 4) {
                return null;
            }
            if (address[0] != -84 || address[1] != 16) {
                return null;
            }
            switch (address[3]) {
                case 10:
                    i = 8;
                    break;
                case 11:
                    break;
                case 12:
                    i = 2;
                    break;
                default:
                    i = 0;
                    break;
            }
            return new C0016d((String) null, address[2], i, (String) null);
        }

        public String toString() {
            return "ByDNS@" + Integer.toHexString(hashCode());
        }

        /* compiled from: IPInfoQuery */
        static class a {
            static InetAddress a(String str) {
                return InetAddress.getByName(str);
            }
        }
    }

    /* compiled from: IPInfoQuery */
    private static class h implements f {
        @NonNull
        private final ai a;

        h(@Nullable ai aiVar) {
            this.a = aiVar == null ? com.subao.common.e.f.a : aiVar;
        }

        public boolean a() {
            return true;
        }

        @Nullable
        public C0016d a(String str) {
            a aVar;
            C0016d dVar = null;
            if (TextUtils.isEmpty(str)) {
                aVar = new a();
                com.subao.common.m.d.a(aVar);
            } else {
                aVar = null;
            }
            long elapsedRealtime = SystemClock.elapsedRealtime();
            try {
                dVar = b(str);
            } catch (IOException | RuntimeException e) {
            }
            if (dVar != null || aVar == null) {
                return dVar;
            }
            C0016d a2 = aVar.a(4000 - (SystemClock.elapsedRealtime() - elapsedRealtime));
            if (!com.subao.common.d.a("SubaoNet")) {
                return a2;
            }
            Log.d("SubaoNet", String.format("IPInfoQuery (subao) failed, wait dns result: %s", new Object[]{a2}));
            return a2;
        }

        public String toString() {
            return "BySubao@" + Integer.toHexString(hashCode());
        }

        @Nullable
        private C0016d b(String str) {
            URL c = c(str);
            a.c a2 = new a(2000, 2000).a(c, (String) null);
            com.subao.common.d.a("SubaoNet", String.format(n.b, "WorkerBySubao query IP info, ip = %s, url = %s, response code = %d", new Object[]{str, c.toString(), Integer.valueOf(a2.a)}));
            if (a2.a != 200) {
                return null;
            }
            if (a2.b == null || a2.b.length == 0) {
                Log.w("SubaoNet", "Response Code is 200, but body is null");
                return null;
            }
            if (com.subao.common.d.a("SubaoNet")) {
                Log.d("SubaoNet", "IPInfoQuery resolve result: " + new String(a2.b));
            }
            return a((InputStream) new ByteArrayInputStream(a2.b));
        }

        private URL c(String str) {
            StringBuilder sb = new StringBuilder(128);
            sb.append("/resolve");
            if (!TextUtils.isEmpty(str)) {
                sb.append("?ip=").append(str);
            }
            return new URL(this.a.a, this.a.b, this.a.c, sb.toString());
        }

        /* JADX INFO: finally extract failed */
        @Nullable
        private static C0016d a(InputStream inputStream) {
            C0016d.a aVar = new C0016d.a();
            JsonReader jsonReader = new JsonReader(new InputStreamReader(inputStream));
            try {
                jsonReader.beginObject();
                while (jsonReader.hasNext()) {
                    String nextName = jsonReader.nextName();
                    if (APBaseHttpParam.IP_ACCESS.equals(nextName)) {
                        aVar.a = com.subao.common.n.f.a(jsonReader);
                    } else if ("ipLib".equals(nextName)) {
                        a(jsonReader, aVar);
                    } else {
                        jsonReader.skipValue();
                    }
                }
                jsonReader.endObject();
                com.subao.common.e.a((Closeable) jsonReader);
                if (aVar.b < 0 || aVar.c < 0) {
                    return null;
                }
                return aVar.a();
            } catch (Throwable th) {
                com.subao.common.e.a((Closeable) jsonReader);
                throw th;
            }
        }

        private static void a(JsonReader jsonReader, C0016d.a aVar) {
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                String nextName = jsonReader.nextName();
                if ("province".equals(nextName)) {
                    aVar.b = jsonReader.nextInt();
                } else if ("operators".equals(nextName)) {
                    aVar.c = jsonReader.nextInt();
                } else if (ProductAction.ACTION_DETAIL.equals(nextName)) {
                    aVar.d = com.subao.common.n.f.a(jsonReader);
                } else {
                    jsonReader.skipValue();
                }
            }
            jsonReader.endObject();
        }

        /* compiled from: IPInfoQuery */
        private static class a implements Runnable {
            private final ConditionVariable a;
            private volatile C0016d b;

            private a() {
                this.a = new ConditionVariable();
            }

            /* access modifiers changed from: package-private */
            public C0016d a(long j) {
                C0016d dVar;
                this.a.block(j);
                synchronized (this) {
                    dVar = this.b;
                }
                return dVar;
            }

            public void run() {
                try {
                    C0016d a2 = new g().a((String) null);
                    synchronized (this) {
                        this.b = a2;
                    }
                } finally {
                    this.a.open();
                }
            }
        }
    }

    /* compiled from: IPInfoQuery */
    static class a {
        private final List<C0015a> a = new ArrayList(4);

        a() {
        }

        @Nullable
        private C0015a b(@NonNull String str) {
            for (C0015a next : this.a) {
                if (next.a.equals(str)) {
                    return next;
                }
            }
            return null;
        }

        @Nullable
        public synchronized C0016d a(@NonNull String str) {
            C0015a b;
            b = b(str);
            return b != null ? b.b : null;
        }

        public synchronized void a(@NonNull String str, @NonNull C0016d dVar) {
            C0015a b = b(str);
            if (b != null) {
                b.b = dVar;
            } else {
                this.a.add(new C0015a(str, dVar));
            }
        }

        public synchronized void a() {
            this.a.clear();
        }

        /* renamed from: com.subao.common.j.d$a$a  reason: collision with other inner class name */
        /* compiled from: IPInfoQuery */
        private static class C0015a {
            public String a;
            public C0016d b;

            public C0015a(String str, C0016d dVar) {
                this.a = str;
                this.b = dVar;
            }
        }
    }
}
