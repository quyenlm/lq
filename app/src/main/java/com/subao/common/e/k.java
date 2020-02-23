package com.subao.common.e;

import android.os.AsyncTask;
import android.util.JsonWriter;
import com.subao.common.e;
import com.subao.common.f.a;
import com.subao.common.f.c;
import com.subao.common.f.d;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStreamWriter;

/* compiled from: Config */
public class k {
    private static final k a = new k();
    /* access modifiers changed from: private */
    public final c b = d.a(a.a("config.subao"));
    /* access modifiers changed from: private */
    public int c;

    private k() {
        b();
    }

    public static k a() {
        return a;
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:19:0x004b=Splitter:B:19:0x004b, B:13:0x003f=Splitter:B:13:0x003f} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean b() {
        /*
            r6 = this;
            r0 = 0
            com.subao.common.f.c r1 = r6.b
            boolean r1 = r1.b()
            if (r1 != 0) goto L_0x000a
        L_0x0009:
            return r0
        L_0x000a:
            r3 = 0
            android.util.JsonReader r2 = new android.util.JsonReader     // Catch:{ RuntimeException -> 0x0065, IOException -> 0x0062, all -> 0x005a }
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch:{ RuntimeException -> 0x0065, IOException -> 0x0062, all -> 0x005a }
            java.io.InputStreamReader r4 = new java.io.InputStreamReader     // Catch:{ RuntimeException -> 0x0065, IOException -> 0x0062, all -> 0x005a }
            com.subao.common.f.c r5 = r6.b     // Catch:{ RuntimeException -> 0x0065, IOException -> 0x0062, all -> 0x005a }
            java.io.InputStream r5 = r5.c()     // Catch:{ RuntimeException -> 0x0065, IOException -> 0x0062, all -> 0x005a }
            r4.<init>(r5)     // Catch:{ RuntimeException -> 0x0065, IOException -> 0x0062, all -> 0x005a }
            r5 = 1024(0x400, float:1.435E-42)
            r1.<init>(r4, r5)     // Catch:{ RuntimeException -> 0x0065, IOException -> 0x0062, all -> 0x005a }
            r2.<init>(r1)     // Catch:{ RuntimeException -> 0x0065, IOException -> 0x0062, all -> 0x005a }
            r2.beginObject()     // Catch:{ RuntimeException -> 0x003e, IOException -> 0x004a }
        L_0x0025:
            boolean r1 = r2.hasNext()     // Catch:{ RuntimeException -> 0x003e, IOException -> 0x004a }
            if (r1 == 0) goto L_0x0052
            java.lang.String r1 = r2.nextName()     // Catch:{ RuntimeException -> 0x003e, IOException -> 0x004a }
            java.lang.String r3 = "drsm"
            boolean r1 = r3.equals(r1)     // Catch:{ RuntimeException -> 0x003e, IOException -> 0x004a }
            if (r1 == 0) goto L_0x0046
            int r1 = r2.nextInt()     // Catch:{ RuntimeException -> 0x003e, IOException -> 0x004a }
            r6.c = r1     // Catch:{ RuntimeException -> 0x003e, IOException -> 0x004a }
            goto L_0x0025
        L_0x003e:
            r1 = move-exception
        L_0x003f:
            r1.printStackTrace()     // Catch:{ all -> 0x0060 }
            com.subao.common.e.a((java.io.Closeable) r2)
            goto L_0x0009
        L_0x0046:
            r2.skipValue()     // Catch:{ RuntimeException -> 0x003e, IOException -> 0x004a }
            goto L_0x0025
        L_0x004a:
            r1 = move-exception
        L_0x004b:
            r1.printStackTrace()     // Catch:{ all -> 0x0060 }
            com.subao.common.e.a((java.io.Closeable) r2)
            goto L_0x0009
        L_0x0052:
            r2.endObject()     // Catch:{ RuntimeException -> 0x003e, IOException -> 0x004a }
            r0 = 1
            com.subao.common.e.a((java.io.Closeable) r2)
            goto L_0x0009
        L_0x005a:
            r0 = move-exception
            r2 = r3
        L_0x005c:
            com.subao.common.e.a((java.io.Closeable) r2)
            throw r0
        L_0x0060:
            r0 = move-exception
            goto L_0x005c
        L_0x0062:
            r1 = move-exception
            r2 = r3
            goto L_0x004b
        L_0x0065:
            r1 = move-exception
            r2 = r3
            goto L_0x003f
        */
        throw new UnsupportedOperationException("Method not decompiled: com.subao.common.e.k.b():boolean");
    }

    public int c() {
        return this.c;
    }

    public void a(int i) {
        if (this.c != i) {
            this.c = i;
            d();
        }
    }

    private void d() {
        AsyncTask.SERIAL_EXECUTOR.execute(new Runnable() {
            public void run() {
                JsonWriter jsonWriter;
                JsonWriter jsonWriter2 = null;
                try {
                    jsonWriter = new JsonWriter(new BufferedWriter(new OutputStreamWriter(k.this.b.d()), 1024));
                    try {
                        jsonWriter.beginObject();
                        jsonWriter.name("drsm").value((long) k.this.c);
                        jsonWriter.endObject();
                        e.a((Closeable) jsonWriter);
                    } catch (IOException e) {
                        e = e;
                        try {
                            e.printStackTrace();
                            e.a((Closeable) jsonWriter);
                        } catch (Throwable th) {
                            th = th;
                            jsonWriter2 = jsonWriter;
                            e.a((Closeable) jsonWriter2);
                            throw th;
                        }
                    } catch (RuntimeException e2) {
                        e = e2;
                        jsonWriter2 = jsonWriter;
                        try {
                            e.printStackTrace();
                            e.a((Closeable) jsonWriter2);
                        } catch (Throwable th2) {
                            th = th2;
                            e.a((Closeable) jsonWriter2);
                            throw th;
                        }
                    }
                } catch (IOException e3) {
                    e = e3;
                    jsonWriter = null;
                    e.printStackTrace();
                    e.a((Closeable) jsonWriter);
                } catch (RuntimeException e4) {
                    e = e4;
                    e.printStackTrace();
                    e.a((Closeable) jsonWriter2);
                }
            }
        });
    }
}
