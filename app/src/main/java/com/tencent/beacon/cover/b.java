package com.tencent.beacon.cover;

import android.content.Context;
import com.tencent.component.debug.TraceFormat;
import com.tencent.component.plugin.PluginReporter;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* compiled from: CompLoad */
public final class b implements Runnable {
    private static b c = null;
    private Context a;
    private List<a> b = null;
    private ClassLoader d;

    private b(Context context) {
        this.a = context;
        this.b = new ArrayList();
    }

    public static b a(Context context, List<a> list) {
        if (c == null) {
            c = new b(context);
        }
        c.a(list);
        return c;
    }

    private synchronized b a(List<a> list) {
        this.b.clear();
        this.b.addAll(list);
        return this;
    }

    /* JADX WARNING: Removed duplicated region for block: B:50:0x012c  */
    @android.annotation.TargetApi(3)
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized boolean b() {
        /*
            r10 = this;
            r9 = 3
            r1 = 1
            r2 = 0
            monitor-enter(r10)
            java.lang.ClassLoader r0 = r10.d     // Catch:{ all -> 0x00b5 }
            if (r0 == 0) goto L_0x000a
        L_0x0008:
            monitor-exit(r10)
            return r1
        L_0x000a:
            java.util.List<com.tencent.beacon.cover.a> r0 = r10.b     // Catch:{ all -> 0x00b5 }
            if (r0 == 0) goto L_0x0016
            java.util.List<com.tencent.beacon.cover.a> r0 = r10.b     // Catch:{ all -> 0x00b5 }
            int r0 = r0.size()     // Catch:{ all -> 0x00b5 }
            if (r0 > 0) goto L_0x0029
        L_0x0016:
            android.content.Context r0 = r10.a     // Catch:{ all -> 0x00b5 }
            java.lang.ClassLoader r0 = r0.getClassLoader()     // Catch:{ all -> 0x00b5 }
            r10.d = r0     // Catch:{ all -> 0x00b5 }
            android.content.Context r0 = r10.a     // Catch:{ all -> 0x00b5 }
            java.lang.ClassLoader r0 = r0.getClassLoader()     // Catch:{ all -> 0x00b5 }
            com.tencent.beacon.event.UserAction.onCompLoaded(r0)     // Catch:{ all -> 0x00b5 }
            r1 = r2
            goto L_0x0008
        L_0x0029:
            android.content.Context r0 = r10.a     // Catch:{ all -> 0x00b5 }
            java.io.File r0 = r0.getFilesDir()     // Catch:{ all -> 0x00b5 }
            if (r0 != 0) goto L_0x0033
            r1 = r2
            goto L_0x0008
        L_0x0033:
            java.lang.String r0 = "D"
            java.lang.String r3 = "start to load comps to classLoader."
            r4 = 0
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ all -> 0x00b5 }
            com.tencent.beacon.cover.g.a((java.lang.String) r0, (java.lang.String) r3, (java.lang.Object[]) r4)     // Catch:{ all -> 0x00b5 }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x00b5 }
            r0.<init>()     // Catch:{ all -> 0x00b5 }
            android.content.Context r3 = r10.a     // Catch:{ all -> 0x00b5 }
            java.io.File r3 = r3.getFilesDir()     // Catch:{ all -> 0x00b5 }
            java.lang.String r3 = r3.getAbsolutePath()     // Catch:{ all -> 0x00b5 }
            java.lang.StringBuilder r0 = r0.append(r3)     // Catch:{ all -> 0x00b5 }
            java.lang.String r3 = java.io.File.separator     // Catch:{ all -> 0x00b5 }
            java.lang.StringBuilder r0 = r0.append(r3)     // Catch:{ all -> 0x00b5 }
            java.lang.String r3 = "beacon/comp"
            java.lang.StringBuilder r0 = r0.append(r3)     // Catch:{ all -> 0x00b5 }
            java.lang.String r3 = r0.toString()     // Catch:{ all -> 0x00b5 }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x00b5 }
            r0.<init>()     // Catch:{ all -> 0x00b5 }
            android.content.Context r4 = r10.a     // Catch:{ all -> 0x00b5 }
            java.io.File r4 = r4.getFilesDir()     // Catch:{ all -> 0x00b5 }
            java.lang.String r4 = r4.getAbsolutePath()     // Catch:{ all -> 0x00b5 }
            java.lang.StringBuilder r0 = r0.append(r4)     // Catch:{ all -> 0x00b5 }
            java.lang.String r4 = java.io.File.separator     // Catch:{ all -> 0x00b5 }
            java.lang.StringBuilder r0 = r0.append(r4)     // Catch:{ all -> 0x00b5 }
            java.lang.String r4 = "beacon/odex"
            java.lang.StringBuilder r0 = r0.append(r4)     // Catch:{ all -> 0x00b5 }
            java.lang.String r4 = r0.toString()     // Catch:{ all -> 0x00b5 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x00b5 }
            r5.<init>()     // Catch:{ all -> 0x00b5 }
            java.util.List<com.tencent.beacon.cover.a> r0 = r10.b     // Catch:{ all -> 0x00b5 }
            java.util.Iterator r6 = r0.iterator()     // Catch:{ all -> 0x00b5 }
        L_0x008e:
            boolean r0 = r6.hasNext()     // Catch:{ all -> 0x00b5 }
            if (r0 == 0) goto L_0x00b8
            java.lang.Object r0 = r6.next()     // Catch:{ all -> 0x00b5 }
            com.tencent.beacon.cover.a r0 = (com.tencent.beacon.cover.a) r0     // Catch:{ all -> 0x00b5 }
            if (r0 == 0) goto L_0x008e
            int r7 = r0.c     // Catch:{ all -> 0x00b5 }
            int r8 = com.tencent.beacon.cover.g.b     // Catch:{ all -> 0x00b5 }
            if (r7 != r8) goto L_0x008e
            r5.append(r3)     // Catch:{ all -> 0x00b5 }
            java.lang.String r7 = java.io.File.separator     // Catch:{ all -> 0x00b5 }
            r5.append(r7)     // Catch:{ all -> 0x00b5 }
            java.lang.String r0 = r0.d     // Catch:{ all -> 0x00b5 }
            r5.append(r0)     // Catch:{ all -> 0x00b5 }
            java.lang.String r0 = java.io.File.pathSeparator     // Catch:{ all -> 0x00b5 }
            r5.append(r0)     // Catch:{ all -> 0x00b5 }
            goto L_0x008e
        L_0x00b5:
            r0 = move-exception
            monitor-exit(r10)
            throw r0
        L_0x00b8:
            int r0 = r10.c()     // Catch:{ all -> 0x00b5 }
            if (r0 < r9) goto L_0x00cb
            java.lang.String r0 = "E"
            java.lang.String r1 = "load comps failed for three times, don't load again."
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ all -> 0x00b5 }
            com.tencent.beacon.cover.g.a((java.lang.String) r0, (java.lang.String) r1, (java.lang.Object[]) r3)     // Catch:{ all -> 0x00b5 }
            r1 = r2
            goto L_0x0008
        L_0x00cb:
            int r6 = r0 + 1
            r10.a((int) r6)     // Catch:{ Throwable -> 0x011a }
            java.lang.String r0 = "D"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x011a }
            java.lang.String r8 = "dex file path -> "
            r7.<init>(r8)     // Catch:{ Throwable -> 0x011a }
            java.lang.String r8 = r5.toString()     // Catch:{ Throwable -> 0x011a }
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ Throwable -> 0x011a }
            java.lang.String r7 = r7.toString()     // Catch:{ Throwable -> 0x011a }
            r8 = 0
            java.lang.Object[] r8 = new java.lang.Object[r8]     // Catch:{ Throwable -> 0x011a }
            com.tencent.beacon.cover.g.a((java.lang.String) r0, (java.lang.String) r7, (java.lang.Object[]) r8)     // Catch:{ Throwable -> 0x011a }
            int r0 = android.os.Build.VERSION.SDK_INT     // Catch:{ Throwable -> 0x011a }
            r7 = 21
            if (r0 == r7) goto L_0x00f7
            int r0 = android.os.Build.VERSION.SDK_INT     // Catch:{ Throwable -> 0x011a }
            r7 = 22
            if (r0 != r7) goto L_0x00fa
        L_0x00f7:
            a((java.lang.String) r4)     // Catch:{ Throwable -> 0x011a }
        L_0x00fa:
            dalvik.system.DexClassLoader r0 = new dalvik.system.DexClassLoader     // Catch:{ Throwable -> 0x011a }
            java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x011a }
            java.lang.Class r7 = r10.getClass()     // Catch:{ Throwable -> 0x011a }
            java.lang.ClassLoader r7 = r7.getClassLoader()     // Catch:{ Throwable -> 0x011a }
            r0.<init>(r5, r4, r3, r7)     // Catch:{ Throwable -> 0x011a }
            r10.d = r0     // Catch:{ Throwable -> 0x011a }
            java.lang.ClassLoader r0 = r10.d     // Catch:{ Throwable -> 0x011a }
            com.tencent.beacon.event.UserAction.onCompLoaded(r0)     // Catch:{ Throwable -> 0x011a }
            r0 = 0
            r10.a((int) r0)     // Catch:{ Throwable -> 0x013a }
            r0 = r1
        L_0x0117:
            r1 = r0
            goto L_0x0008
        L_0x011a:
            r1 = move-exception
            r3 = r1
            r0 = r2
        L_0x011d:
            android.content.Context r1 = r10.a     // Catch:{ all -> 0x00b5 }
            com.tencent.beacon.cover.e r1 = com.tencent.beacon.cover.e.a((android.content.Context) r1)     // Catch:{ all -> 0x00b5 }
            java.lang.String r2 = r3.toString()     // Catch:{ all -> 0x00b5 }
            r1.a((java.lang.String) r2)     // Catch:{ all -> 0x00b5 }
            if (r6 < r9) goto L_0x0136
            android.content.Context r1 = r10.a     // Catch:{ all -> 0x00b5 }
            com.tencent.beacon.cover.e r1 = com.tencent.beacon.cover.e.a((android.content.Context) r1)     // Catch:{ all -> 0x00b5 }
            r2 = 0
            r1.a((boolean) r2)     // Catch:{ all -> 0x00b5 }
        L_0x0136:
            r3.printStackTrace()     // Catch:{ all -> 0x00b5 }
            goto L_0x0117
        L_0x013a:
            r2 = move-exception
            r3 = r2
            r0 = r1
            goto L_0x011d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.beacon.cover.b.b():boolean");
    }

    private static void a(String str) {
        File file = new File(str);
        if (file.exists()) {
            for (File file2 : file.listFiles()) {
                f fVar = null;
                try {
                    if (f.a(file2) == 1) {
                        fVar = new f(file2);
                    }
                    g.a(TraceFormat.STR_INFO, "good oat file: " + file2.getPath(), new Object[0]);
                } catch (IOException e) {
                    g.a(TraceFormat.STR_ERROR, " oat file error , try to delete: " + file2.getPath(), new Object[0]);
                    g.b(file2);
                } finally {
                    g.a((Closeable) fVar);
                }
            }
        }
    }

    public final synchronized void run() {
        if (d.a(this.a).a(PluginReporter.EVENT_LOAD)) {
            b();
            d.a(this.a).b(PluginReporter.EVENT_LOAD);
        }
    }

    public final void a() {
        if (this.d == null) {
            a(0);
            b();
        }
    }

    private int c() {
        try {
            return Integer.parseInt(g.b(this.a, "LOAD_RETRIES_TIMES", "0"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private void a(int i) {
        g.a(this.a, "LOAD_RETRIES_TIMES", String.valueOf(i));
    }
}
