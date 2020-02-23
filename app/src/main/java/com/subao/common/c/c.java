package com.subao.common.c;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.JsonWriter;
import com.subao.common.e;
import com.subao.common.e.ai;
import com.subao.common.j.a;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.OutputStreamWriter;

/* compiled from: OrderRequester */
public class c extends g {
    private final b a;
    private int b = -1;
    @Nullable
    private String c;

    public c(@NonNull String str, @Nullable ai aiVar, @NonNull String str2, @NonNull b bVar) {
        super(str, aiVar, str2);
        this.a = bVar;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        r2.endObject();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x003f, code lost:
        com.subao.common.e.a((java.io.Closeable) r2);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static java.lang.String a(@android.support.annotation.Nullable byte[] r4) {
        /*
            r0 = 0
            if (r4 != 0) goto L_0x0004
        L_0x0003:
            return r0
        L_0x0004:
            android.util.JsonReader r2 = new android.util.JsonReader     // Catch:{ IOException -> 0x0051, RuntimeException -> 0x004c, all -> 0x0043 }
            java.io.InputStreamReader r1 = new java.io.InputStreamReader     // Catch:{ IOException -> 0x0051, RuntimeException -> 0x004c, all -> 0x0043 }
            java.io.ByteArrayInputStream r3 = new java.io.ByteArrayInputStream     // Catch:{ IOException -> 0x0051, RuntimeException -> 0x004c, all -> 0x0043 }
            r3.<init>(r4)     // Catch:{ IOException -> 0x0051, RuntimeException -> 0x004c, all -> 0x0043 }
            r1.<init>(r3)     // Catch:{ IOException -> 0x0051, RuntimeException -> 0x004c, all -> 0x0043 }
            r2.<init>(r1)     // Catch:{ IOException -> 0x0051, RuntimeException -> 0x004c, all -> 0x0043 }
            r2.beginObject()     // Catch:{ IOException -> 0x0034, RuntimeException -> 0x004f }
        L_0x0016:
            boolean r1 = r2.hasNext()     // Catch:{ IOException -> 0x0034, RuntimeException -> 0x004f }
            if (r1 == 0) goto L_0x003c
            java.lang.String r1 = "orderId"
            java.lang.String r3 = r2.nextName()     // Catch:{ IOException -> 0x0034, RuntimeException -> 0x004f }
            boolean r1 = r1.equals(r3)     // Catch:{ IOException -> 0x0034, RuntimeException -> 0x004f }
            if (r1 == 0) goto L_0x0030
            java.lang.String r0 = com.subao.common.n.f.a((android.util.JsonReader) r2)     // Catch:{ IOException -> 0x0034, RuntimeException -> 0x004f }
            com.subao.common.e.a((java.io.Closeable) r2)
            goto L_0x0003
        L_0x0030:
            r2.skipValue()     // Catch:{ IOException -> 0x0034, RuntimeException -> 0x004f }
            goto L_0x0016
        L_0x0034:
            r1 = move-exception
        L_0x0035:
            r1.printStackTrace()     // Catch:{ all -> 0x0049 }
            com.subao.common.e.a((java.io.Closeable) r2)
            goto L_0x0003
        L_0x003c:
            r2.endObject()     // Catch:{ IOException -> 0x0034, RuntimeException -> 0x004f }
            com.subao.common.e.a((java.io.Closeable) r2)
            goto L_0x0003
        L_0x0043:
            r1 = move-exception
            r2 = r0
        L_0x0045:
            com.subao.common.e.a((java.io.Closeable) r2)
            throw r1
        L_0x0049:
            r0 = move-exception
            r1 = r0
            goto L_0x0045
        L_0x004c:
            r1 = move-exception
            r2 = r0
            goto L_0x0035
        L_0x004f:
            r1 = move-exception
            goto L_0x0035
        L_0x0051:
            r1 = move-exception
            r2 = r0
            goto L_0x0035
        */
        throw new UnsupportedOperationException("Method not decompiled: com.subao.common.c.c.a(byte[]):java.lang.String");
    }

    /* access modifiers changed from: protected */
    @NonNull
    public a.b a() {
        return a.b.POST;
    }

    /* access modifiers changed from: protected */
    @Nullable
    public byte[] b() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(256);
        JsonWriter jsonWriter = new JsonWriter(new OutputStreamWriter(byteArrayOutputStream));
        this.a.serialize(jsonWriter);
        e.a((Closeable) jsonWriter);
        return byteArrayOutputStream.toByteArray();
    }

    /* access modifiers changed from: protected */
    @NonNull
    public String c() {
        return "/api/v1/" + f() + "/orders";
    }

    /* access modifiers changed from: protected */
    public void a(@Nullable a.c cVar) {
        String str = null;
        if (cVar == null) {
            this.b = -1;
            this.c = null;
            return;
        }
        this.b = cVar.a;
        if (cVar.a == 201) {
            str = a(cVar.b);
        }
        this.c = str;
    }

    public int d() {
        return this.b;
    }

    @Nullable
    public String e() {
        return this.c;
    }
}
