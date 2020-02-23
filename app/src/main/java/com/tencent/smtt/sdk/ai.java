package com.tencent.smtt.sdk;

import android.content.Context;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

class ai {
    private static ai a = null;
    private static Context b = null;

    private ai() {
    }

    static ai a(Context context) {
        if (a == null) {
            synchronized (ai.class) {
                if (a == null) {
                    a = new ai();
                }
            }
        }
        b = context.getApplicationContext();
        return a;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:25:0x003a, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x003b, code lost:
        r2 = r0;
        r4 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x004f, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0050, code lost:
        r3 = r0;
        r4 = null;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0030 A[SYNTHETIC, Splitter:B:20:0x0030] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x003a A[ExcHandler: all (r0v1 'th' java.lang.Throwable A[CUSTOM_DECLARE]), Splitter:B:1:0x0001] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x003f A[SYNTHETIC, Splitter:B:28:0x003f] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.util.Properties e() {
        /*
            r5 = this;
            r1 = 0
            java.io.File r0 = r5.a()     // Catch:{ Exception -> 0x0027, all -> 0x003a }
            java.util.Properties r2 = new java.util.Properties     // Catch:{ Exception -> 0x0027, all -> 0x003a }
            r2.<init>()     // Catch:{ Exception -> 0x0027, all -> 0x003a }
            if (r0 == 0) goto L_0x0057
            if (r2 == 0) goto L_0x0057
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ Exception -> 0x004f, all -> 0x003a }
            r3.<init>(r0)     // Catch:{ Exception -> 0x004f, all -> 0x003a }
            java.io.BufferedInputStream r0 = new java.io.BufferedInputStream     // Catch:{ Exception -> 0x004f, all -> 0x003a }
            r0.<init>(r3)     // Catch:{ Exception -> 0x004f, all -> 0x003a }
            r2.load(r0)     // Catch:{ Exception -> 0x0053, all -> 0x0048 }
        L_0x001b:
            if (r0 == 0) goto L_0x0020
            r0.close()     // Catch:{ IOException -> 0x0022 }
        L_0x0020:
            r0 = r2
        L_0x0021:
            return r0
        L_0x0022:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0020
        L_0x0027:
            r0 = move-exception
            r3 = r0
            r2 = r1
            r4 = r1
        L_0x002b:
            r3.printStackTrace()     // Catch:{ all -> 0x004c }
            if (r4 == 0) goto L_0x0033
            r4.close()     // Catch:{ IOException -> 0x0035 }
        L_0x0033:
            r0 = r2
            goto L_0x0021
        L_0x0035:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0033
        L_0x003a:
            r0 = move-exception
            r2 = r0
            r4 = r1
        L_0x003d:
            if (r4 == 0) goto L_0x0042
            r4.close()     // Catch:{ IOException -> 0x0043 }
        L_0x0042:
            throw r2
        L_0x0043:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0042
        L_0x0048:
            r1 = move-exception
            r2 = r1
            r4 = r0
            goto L_0x003d
        L_0x004c:
            r0 = move-exception
            r2 = r0
            goto L_0x003d
        L_0x004f:
            r0 = move-exception
            r3 = r0
            r4 = r1
            goto L_0x002b
        L_0x0053:
            r1 = move-exception
            r3 = r1
            r4 = r0
            goto L_0x002b
        L_0x0057:
            r0 = r1
            goto L_0x001b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.ai.e():java.util.Properties");
    }

    /* access modifiers changed from: package-private */
    public File a() {
        am.a();
        File file = new File(am.s(b), "tbscoreinstall.txt");
        if (file == null || file.exists()) {
            return file;
        }
        try {
            file.createNewFile();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    public void a(int i) {
        a("dexopt_retry_num", i);
    }

    /* access modifiers changed from: package-private */
    public void a(int i, int i2) {
        a("copy_core_ver", i);
        a("copy_status", i2);
    }

    /* access modifiers changed from: package-private */
    public void a(String str) {
        a("install_apk_path", str);
    }

    /* access modifiers changed from: package-private */
    public void a(String str, int i) {
        a(str, String.valueOf(i));
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0044 A[SYNTHETIC, Splitter:B:20:0x0044] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0051 A[SYNTHETIC, Splitter:B:27:0x0051] */
    /* JADX WARNING: Removed duplicated region for block: B:37:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(java.lang.String r5, java.lang.String r6) {
        /*
            r4 = this;
            r2 = 0
            java.util.Properties r0 = r4.e()     // Catch:{ Exception -> 0x003d, all -> 0x004d }
            if (r0 == 0) goto L_0x0032
            r0.setProperty(r5, r6)     // Catch:{ Exception -> 0x003d, all -> 0x004d }
            java.io.File r3 = r4.a()     // Catch:{ Exception -> 0x003d, all -> 0x004d }
            if (r3 == 0) goto L_0x0032
            java.io.FileOutputStream r1 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x003d, all -> 0x004d }
            r1.<init>(r3)     // Catch:{ Exception -> 0x003d, all -> 0x004d }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x005c }
            r2.<init>()     // Catch:{ Exception -> 0x005c }
            java.lang.String r3 = "update "
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Exception -> 0x005c }
            java.lang.StringBuilder r2 = r2.append(r5)     // Catch:{ Exception -> 0x005c }
            java.lang.String r3 = " and status!"
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Exception -> 0x005c }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x005c }
            r0.store(r1, r2)     // Catch:{ Exception -> 0x005c }
            r2 = r1
        L_0x0032:
            if (r2 == 0) goto L_0x0037
            r2.close()     // Catch:{ IOException -> 0x0038 }
        L_0x0037:
            return
        L_0x0038:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0037
        L_0x003d:
            r0 = move-exception
            r1 = r2
        L_0x003f:
            r0.printStackTrace()     // Catch:{ all -> 0x005a }
            if (r1 == 0) goto L_0x0037
            r1.close()     // Catch:{ IOException -> 0x0048 }
            goto L_0x0037
        L_0x0048:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0037
        L_0x004d:
            r0 = move-exception
            r1 = r2
        L_0x004f:
            if (r1 == 0) goto L_0x0054
            r1.close()     // Catch:{ IOException -> 0x0055 }
        L_0x0054:
            throw r0
        L_0x0055:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x0054
        L_0x005a:
            r0 = move-exception
            goto L_0x004f
        L_0x005c:
            r0 = move-exception
            goto L_0x003f
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.ai.a(java.lang.String, java.lang.String):void");
    }

    /* access modifiers changed from: package-private */
    public int b() {
        return c("install_core_ver");
    }

    /* access modifiers changed from: package-private */
    public int b(String str) {
        Properties e = e();
        if (e == null || e.getProperty(str) == null) {
            return -1;
        }
        return Integer.parseInt(e.getProperty(str));
    }

    /* access modifiers changed from: package-private */
    public void b(int i) {
        a("unzip_retry_num", i);
    }

    /* access modifiers changed from: package-private */
    public void b(int i, int i2) {
        a("install_core_ver", i);
        a("install_status", i2);
    }

    /* access modifiers changed from: package-private */
    public int c() {
        return b("install_status");
    }

    /* access modifiers changed from: package-private */
    public int c(String str) {
        Properties e = e();
        if (e == null || e.getProperty(str) == null) {
            return 0;
        }
        return Integer.parseInt(e.getProperty(str));
    }

    /* access modifiers changed from: package-private */
    public void c(int i) {
        a("incrupdate_status", i);
    }

    /* access modifiers changed from: package-private */
    public int d() {
        return b("incrupdate_status");
    }

    /* access modifiers changed from: package-private */
    public String d(String str) {
        Properties e = e();
        if (e == null || e.getProperty(str) == null) {
            return null;
        }
        return e.getProperty(str);
    }

    /* access modifiers changed from: package-private */
    public void d(int i) {
        a("unlzma_status", i);
    }
}
