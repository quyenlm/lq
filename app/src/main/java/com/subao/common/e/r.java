package com.subao.common.e;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.JsonReader;
import com.subao.common.e;
import com.subao.common.e.s;
import com.subao.common.j.a;
import java.util.ArrayList;
import java.util.List;

/* compiled from: HRCouponListRequester */
public class r extends s {
    @NonNull
    private final a c;
    private final boolean d;

    /* compiled from: HRCouponListRequester */
    public interface a {
        void a(int i, @Nullable List<l> list);
    }

    public r(@NonNull s.a aVar, @NonNull s.d dVar, @NonNull a aVar2, boolean z) {
        super(aVar, dVar, a.b.GET, (byte[]) null);
        this.c = aVar2;
        this.d = z;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    @android.support.annotation.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.util.List<com.subao.common.e.l> a(@android.support.annotation.NonNull byte[] r7) {
        /*
            r5 = -1
            r2 = 0
            android.util.JsonReader r3 = new android.util.JsonReader     // Catch:{ IOException -> 0x007f, RuntimeException -> 0x007a, all -> 0x0072 }
            java.io.InputStreamReader r0 = new java.io.InputStreamReader     // Catch:{ IOException -> 0x007f, RuntimeException -> 0x007a, all -> 0x0072 }
            java.io.ByteArrayInputStream r1 = new java.io.ByteArrayInputStream     // Catch:{ IOException -> 0x007f, RuntimeException -> 0x007a, all -> 0x0072 }
            r1.<init>(r7)     // Catch:{ IOException -> 0x007f, RuntimeException -> 0x007a, all -> 0x0072 }
            java.lang.String r4 = "UTF-8"
            r0.<init>(r1, r4)     // Catch:{ IOException -> 0x007f, RuntimeException -> 0x007a, all -> 0x0072 }
            r3.<init>(r0)     // Catch:{ IOException -> 0x007f, RuntimeException -> 0x007a, all -> 0x0072 }
            r3.beginObject()     // Catch:{ IOException -> 0x0031, RuntimeException -> 0x007d }
            r0 = r5
            r1 = r2
        L_0x0018:
            boolean r4 = r3.hasNext()     // Catch:{ IOException -> 0x0031, RuntimeException -> 0x007d }
            if (r4 == 0) goto L_0x006b
            java.lang.String r4 = r3.nextName()     // Catch:{ IOException -> 0x0031, RuntimeException -> 0x007d }
            int r6 = r4.hashCode()     // Catch:{ IOException -> 0x0031, RuntimeException -> 0x007d }
            switch(r6) {
                case -572353622: goto L_0x003c;
                case 609384932: goto L_0x0046;
                default: goto L_0x0029;
            }     // Catch:{ IOException -> 0x0031, RuntimeException -> 0x007d }
        L_0x0029:
            r4 = r5
        L_0x002a:
            switch(r4) {
                case 0: goto L_0x0050;
                case 1: goto L_0x0061;
                default: goto L_0x002d;
            }     // Catch:{ IOException -> 0x0031, RuntimeException -> 0x007d }
        L_0x002d:
            r3.skipValue()     // Catch:{ IOException -> 0x0031, RuntimeException -> 0x007d }
            goto L_0x0018
        L_0x0031:
            r0 = move-exception
            r1 = r3
        L_0x0033:
            r3 = r1
        L_0x0034:
            r0.printStackTrace()     // Catch:{ all -> 0x0078 }
            com.subao.common.e.a((java.io.Closeable) r3)
            r1 = r2
        L_0x003b:
            return r1
        L_0x003c:
            java.lang.String r6 = "resultCode"
            boolean r4 = r4.equals(r6)     // Catch:{ IOException -> 0x0031, RuntimeException -> 0x007d }
            if (r4 == 0) goto L_0x0029
            r4 = 0
            goto L_0x002a
        L_0x0046:
            java.lang.String r6 = "couponList"
            boolean r4 = r4.equals(r6)     // Catch:{ IOException -> 0x0031, RuntimeException -> 0x007d }
            if (r4 == 0) goto L_0x0029
            r4 = 1
            goto L_0x002a
        L_0x0050:
            int r0 = r3.nextInt()     // Catch:{ IOException -> 0x0031, RuntimeException -> 0x007d }
            if (r0 != 0) goto L_0x005c
            if (r1 == 0) goto L_0x0018
            com.subao.common.e.a((java.io.Closeable) r3)
            goto L_0x003b
        L_0x005c:
            com.subao.common.e.a((java.io.Closeable) r3)
            r1 = r2
            goto L_0x003b
        L_0x0061:
            java.util.List r1 = a((android.util.JsonReader) r3)     // Catch:{ IOException -> 0x0031, RuntimeException -> 0x007d }
            if (r0 != 0) goto L_0x0018
            com.subao.common.e.a((java.io.Closeable) r3)
            goto L_0x003b
        L_0x006b:
            r3.endObject()     // Catch:{ IOException -> 0x0031, RuntimeException -> 0x007d }
            com.subao.common.e.a((java.io.Closeable) r3)
            goto L_0x003b
        L_0x0072:
            r0 = move-exception
            r3 = r2
        L_0x0074:
            com.subao.common.e.a((java.io.Closeable) r3)
            throw r0
        L_0x0078:
            r0 = move-exception
            goto L_0x0074
        L_0x007a:
            r0 = move-exception
            r3 = r2
            goto L_0x0034
        L_0x007d:
            r0 = move-exception
            goto L_0x0034
        L_0x007f:
            r0 = move-exception
            r1 = r2
            goto L_0x0033
        */
        throw new UnsupportedOperationException("Method not decompiled: com.subao.common.e.r.a(byte[]):java.util.List");
    }

    @NonNull
    private static List<l> a(JsonReader jsonReader) {
        ArrayList arrayList = new ArrayList(32);
        jsonReader.beginArray();
        while (jsonReader.hasNext()) {
            arrayList.add(l.a(jsonReader));
        }
        jsonReader.endArray();
        return arrayList;
    }

    /* access modifiers changed from: protected */
    public int a() {
        return 0;
    }

    /* access modifiers changed from: protected */
    public String b() {
        StringBuilder sb = new StringBuilder(512);
        sb.append("/api/v2/").append(this.a.a).append("/coupons");
        if (this.d) {
            sb.append("?user=").append(e.a(this.b.a));
        }
        return sb.toString();
    }

    /* access modifiers changed from: protected */
    public void a(@Nullable s.b bVar) {
        byte[] bArr;
        int i = 1008;
        super.a(bVar);
        List<l> list = null;
        if (bVar == null || bVar.b == null) {
            i = 1006;
        } else if (200 == bVar.b.a && (bArr = bVar.b.b) != null && bArr.length > 2 && (list = a(bArr)) != null) {
            i = 0;
        }
        this.c.a(i, list);
    }
}
