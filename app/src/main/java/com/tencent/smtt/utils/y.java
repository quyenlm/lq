package com.tencent.smtt.utils;

import android.content.Context;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class y {
    private static y e = null;
    public boolean a = false;
    private Context b = null;
    private File c = null;
    private boolean d = false;
    private File f = null;

    private y(Context context) {
        this.b = context.getApplicationContext();
        b();
    }

    public static synchronized y a() {
        y yVar;
        synchronized (y.class) {
            yVar = e;
        }
        return yVar;
    }

    public static synchronized y a(Context context) {
        y yVar;
        synchronized (y.class) {
            if (e == null) {
                e = new y(context);
            }
            yVar = e;
        }
        return yVar;
    }

    private File d() {
        File file;
        try {
            if (this.c == null) {
                this.c = new File(this.b.getDir("tbs", 0), "core_private");
                if (this.c == null || !this.c.isDirectory()) {
                    return null;
                }
            }
            file = new File(this.c, "debug.conf");
            if (file != null && !file.exists()) {
                file.createNewFile();
            }
        } catch (Throwable th) {
            th.printStackTrace();
            file = null;
        }
        return file;
    }

    public void a(boolean z) {
        this.d = z;
        c();
    }

    /* JADX WARNING: Removed duplicated region for block: B:35:0x005a A[SYNTHETIC, Splitter:B:35:0x005a] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x0066 A[SYNTHETIC, Splitter:B:42:0x0066] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void b() {
        /*
            r4 = this;
            monitor-enter(r4)
            r1 = 0
            java.io.File r0 = r4.f     // Catch:{ Throwable -> 0x0054 }
            if (r0 != 0) goto L_0x000c
            java.io.File r0 = r4.d()     // Catch:{ Throwable -> 0x0054 }
            r4.f = r0     // Catch:{ Throwable -> 0x0054 }
        L_0x000c:
            java.io.File r0 = r4.f     // Catch:{ Throwable -> 0x0054 }
            if (r0 != 0) goto L_0x001f
            if (r1 == 0) goto L_0x0015
            r1.close()     // Catch:{ Exception -> 0x0017 }
        L_0x0015:
            monitor-exit(r4)
            return
        L_0x0017:
            r0 = move-exception
            r0.printStackTrace()     // Catch:{ all -> 0x001c }
            goto L_0x0015
        L_0x001c:
            r0 = move-exception
            monitor-exit(r4)
            throw r0
        L_0x001f:
            java.io.FileInputStream r0 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x0054 }
            java.io.File r2 = r4.f     // Catch:{ Throwable -> 0x0054 }
            r0.<init>(r2)     // Catch:{ Throwable -> 0x0054 }
            java.io.BufferedInputStream r2 = new java.io.BufferedInputStream     // Catch:{ Throwable -> 0x0054 }
            r2.<init>(r0)     // Catch:{ Throwable -> 0x0054 }
            java.util.Properties r0 = new java.util.Properties     // Catch:{ Throwable -> 0x0072, all -> 0x006f }
            r0.<init>()     // Catch:{ Throwable -> 0x0072, all -> 0x006f }
            r0.load(r2)     // Catch:{ Throwable -> 0x0072, all -> 0x006f }
            java.lang.String r1 = "setting_forceUseSystemWebview"
            java.lang.String r3 = ""
            java.lang.String r0 = r0.getProperty(r1, r3)     // Catch:{ Throwable -> 0x0072, all -> 0x006f }
            java.lang.String r1 = ""
            boolean r1 = r1.equals(r0)     // Catch:{ Throwable -> 0x0072, all -> 0x006f }
            if (r1 != 0) goto L_0x0049
            boolean r0 = java.lang.Boolean.parseBoolean(r0)     // Catch:{ Throwable -> 0x0072, all -> 0x006f }
            r4.a = r0     // Catch:{ Throwable -> 0x0072, all -> 0x006f }
        L_0x0049:
            if (r2 == 0) goto L_0x0015
            r2.close()     // Catch:{ Exception -> 0x004f }
            goto L_0x0015
        L_0x004f:
            r0 = move-exception
            r0.printStackTrace()     // Catch:{ all -> 0x001c }
            goto L_0x0015
        L_0x0054:
            r0 = move-exception
        L_0x0055:
            r0.printStackTrace()     // Catch:{ all -> 0x0063 }
            if (r1 == 0) goto L_0x0015
            r1.close()     // Catch:{ Exception -> 0x005e }
            goto L_0x0015
        L_0x005e:
            r0 = move-exception
            r0.printStackTrace()     // Catch:{ all -> 0x001c }
            goto L_0x0015
        L_0x0063:
            r0 = move-exception
        L_0x0064:
            if (r1 == 0) goto L_0x0069
            r1.close()     // Catch:{ Exception -> 0x006a }
        L_0x0069:
            throw r0     // Catch:{ all -> 0x001c }
        L_0x006a:
            r1 = move-exception
            r1.printStackTrace()     // Catch:{ all -> 0x001c }
            goto L_0x0069
        L_0x006f:
            r0 = move-exception
            r1 = r2
            goto L_0x0064
        L_0x0072:
            r0 = move-exception
            r1 = r2
            goto L_0x0055
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.utils.y.b():void");
    }

    public void c() {
        BufferedInputStream bufferedInputStream;
        BufferedOutputStream bufferedOutputStream;
        BufferedInputStream bufferedInputStream2 = null;
        BufferedOutputStream bufferedOutputStream2 = null;
        try {
            File d2 = d();
            if (d2 == null) {
                try {
                    bufferedInputStream2.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                try {
                    bufferedOutputStream2.close();
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            } else {
                bufferedInputStream = new BufferedInputStream(new FileInputStream(d2));
                try {
                    Properties properties = new Properties();
                    properties.load(bufferedInputStream);
                    properties.setProperty("setting_forceUseSystemWebview", Boolean.toString(this.a));
                    properties.setProperty("result_systemWebviewForceUsed", Boolean.toString(this.d));
                    bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(d2));
                    try {
                        properties.store(bufferedOutputStream, (String) null);
                        try {
                            bufferedInputStream.close();
                        } catch (Exception e4) {
                            e4.printStackTrace();
                        }
                        try {
                            bufferedOutputStream.close();
                        } catch (Exception e5) {
                            e5.printStackTrace();
                        }
                    } catch (Throwable th) {
                        th = th;
                        try {
                            th.printStackTrace();
                            try {
                                bufferedInputStream.close();
                            } catch (Exception e6) {
                                e6.printStackTrace();
                            }
                            try {
                                bufferedOutputStream.close();
                            } catch (Exception e7) {
                                e7.printStackTrace();
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            try {
                                bufferedInputStream.close();
                            } catch (Exception e8) {
                                e8.printStackTrace();
                            }
                            try {
                                bufferedOutputStream.close();
                            } catch (Exception e9) {
                                e9.printStackTrace();
                            }
                            throw th;
                        }
                    }
                } catch (Throwable th3) {
                    th = th3;
                    bufferedOutputStream = null;
                    bufferedInputStream.close();
                    bufferedOutputStream.close();
                    throw th;
                }
            }
        } catch (Throwable th4) {
            th = th4;
            bufferedOutputStream = null;
            bufferedInputStream = null;
            bufferedInputStream.close();
            bufferedOutputStream.close();
            throw th;
        }
    }
}
