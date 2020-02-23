package com.subao.common.e;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.subao.common.d;
import com.subao.common.f.c;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/* compiled from: JniFileOperator */
public class w {
    @NonNull
    private final Executor a;
    /* access modifiers changed from: private */
    @NonNull
    public final c b;

    public w(@NonNull c cVar) {
        this(cVar, Executors.newSingleThreadExecutor());
    }

    w(@NonNull c cVar, @NonNull Executor executor) {
        this.b = cVar;
        this.a = executor;
    }

    /* access modifiers changed from: private */
    @NonNull
    public c b(@NonNull String str, @NonNull String str2) {
        return this.b.a(str).a(str2);
    }

    public void a(@NonNull String str, @NonNull String str2, @Nullable String str3, boolean z) {
        final String str4 = str;
        final String str5 = str2;
        final String str6 = str3;
        final boolean z2 = z;
        this.a.execute(new Runnable() {
            /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void run() {
                /*
                    r6 = this;
                    com.subao.common.e.w r0 = com.subao.common.e.w.this     // Catch:{ IOException -> 0x006f }
                    java.lang.String r1 = r2     // Catch:{ IOException -> 0x006f }
                    java.lang.String r2 = r3     // Catch:{ IOException -> 0x006f }
                    com.subao.common.f.c r0 = r0.b(r1, r2)     // Catch:{ IOException -> 0x006f }
                    java.lang.String r1 = "SubaoData"
                    boolean r1 = com.subao.common.d.a(r1)     // Catch:{ IOException -> 0x006f }
                    java.lang.String r2 = r4     // Catch:{ IOException -> 0x006f }
                    if (r2 != 0) goto L_0x0036
                    boolean r2 = r5     // Catch:{ IOException -> 0x006f }
                    if (r2 != 0) goto L_0x0035
                    r0.f()     // Catch:{ IOException -> 0x006f }
                    if (r1 == 0) goto L_0x0035
                    java.lang.String r0 = "SubaoData"
                    java.lang.String r1 = "(JNI) '%s/%s' dropped"
                    r2 = 2
                    java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ IOException -> 0x006f }
                    r3 = 0
                    java.lang.String r4 = r2     // Catch:{ IOException -> 0x006f }
                    r2[r3] = r4     // Catch:{ IOException -> 0x006f }
                    r3 = 1
                    java.lang.String r4 = r3     // Catch:{ IOException -> 0x006f }
                    r2[r3] = r4     // Catch:{ IOException -> 0x006f }
                    java.lang.String r1 = java.lang.String.format(r1, r2)     // Catch:{ IOException -> 0x006f }
                    android.util.Log.d(r0, r1)     // Catch:{ IOException -> 0x006f }
                L_0x0035:
                    return
                L_0x0036:
                    boolean r2 = r5     // Catch:{ IOException -> 0x006f }
                    if (r2 == 0) goto L_0x0074
                    java.io.OutputStream r0 = r0.e()     // Catch:{ IOException -> 0x006f }
                L_0x003e:
                    java.lang.String r2 = r4     // Catch:{ IOException -> 0x006f }
                    java.lang.String r3 = "UTF-8"
                    byte[] r2 = r2.getBytes(r3)     // Catch:{ IOException -> 0x006f }
                    r0.write(r2)     // Catch:{ all -> 0x0079 }
                    com.subao.common.e.a((java.io.Closeable) r0)     // Catch:{ IOException -> 0x006f }
                    if (r1 == 0) goto L_0x0035
                    java.lang.String r0 = "SubaoData"
                    java.lang.String r1 = "(JNI) write to '%s/%s' %d bytes"
                    r3 = 3
                    java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ IOException -> 0x006f }
                    r4 = 0
                    java.lang.String r5 = r2     // Catch:{ IOException -> 0x006f }
                    r3[r4] = r5     // Catch:{ IOException -> 0x006f }
                    r4 = 1
                    java.lang.String r5 = r3     // Catch:{ IOException -> 0x006f }
                    r3[r4] = r5     // Catch:{ IOException -> 0x006f }
                    r4 = 2
                    int r2 = r2.length     // Catch:{ IOException -> 0x006f }
                    java.lang.Integer r2 = java.lang.Integer.valueOf(r2)     // Catch:{ IOException -> 0x006f }
                    r3[r4] = r2     // Catch:{ IOException -> 0x006f }
                    java.lang.String r1 = java.lang.String.format(r1, r3)     // Catch:{ IOException -> 0x006f }
                    android.util.Log.d(r0, r1)     // Catch:{ IOException -> 0x006f }
                    goto L_0x0035
                L_0x006f:
                    r0 = move-exception
                    r0.printStackTrace()
                    goto L_0x0035
                L_0x0074:
                    java.io.OutputStream r0 = r0.d()     // Catch:{ IOException -> 0x006f }
                    goto L_0x003e
                L_0x0079:
                    r1 = move-exception
                    com.subao.common.e.a((java.io.Closeable) r0)     // Catch:{ IOException -> 0x006f }
                    throw r1     // Catch:{ IOException -> 0x006f }
                */
                throw new UnsupportedOperationException("Method not decompiled: com.subao.common.e.w.AnonymousClass1.run():void");
            }
        });
    }

    public void a(@NonNull String str, @NonNull String str2, @NonNull com.subao.common.g.c cVar, int i) {
        final String str3 = str;
        final String str4 = str2;
        final com.subao.common.g.c cVar2 = cVar;
        final int i2 = i;
        this.a.execute(new Runnable() {
            public void run() {
                String str;
                int i = 0;
                try {
                    byte[] g = w.this.b(str3, str4).g();
                    str = (g == null || g.length == 0) ? null : new String(g, "UTF-8");
                    if (d.a("SubaoData")) {
                        Object[] objArr = new Object[3];
                        objArr[0] = str3;
                        objArr[1] = str4;
                        if (g != null) {
                            i = g.length;
                        }
                        objArr[2] = Integer.valueOf(i);
                        Log.d("SubaoData", String.format("(JNI) read from '%s/%s' %d bytes", objArr));
                    }
                } catch (IOException e2) {
                    str = null;
                }
                cVar2.c(i2, str);
            }
        });
    }

    public void a(@NonNull final String str, @NonNull final String str2) {
        this.a.execute(new Runnable() {
            public void run() {
                for (String str : str2.split("\\|")) {
                    w.this.b(str, str).f();
                    if (d.a("SubaoData")) {
                        Log.d("SubaoData", String.format("(JNI) '%s/%s' deleted", new Object[]{str, str}));
                    }
                }
            }
        });
    }

    public void a(@NonNull final String str, @NonNull final com.subao.common.g.c cVar, final int i) {
        this.a.execute(new Runnable() {
            public void run() {
                String str;
                Iterable<c> h = w.this.b.a(str).h();
                if (h != null) {
                    StringBuilder sb = new StringBuilder(1024);
                    for (c a2 : h) {
                        sb.append(a2.a()).append(',');
                    }
                    str = sb.toString();
                } else {
                    str = "";
                }
                if (d.a("SubaoData")) {
                    Log.d("SubaoData", String.format("(JNI) '%s' list: '%s'", new Object[]{str, str}));
                }
                cVar.d(i, str);
            }
        });
    }
}
