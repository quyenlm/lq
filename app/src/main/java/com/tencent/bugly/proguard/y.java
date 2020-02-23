package com.tencent.bugly.proguard;

import android.content.Context;
import android.os.Process;
import com.google.android.gms.gcm.Task;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/* compiled from: BUGLY */
public final class y {
    public static boolean a = true;
    private static SimpleDateFormat b;
    private static int c = 5120;
    private static StringBuilder d;
    /* access modifiers changed from: private */
    public static StringBuilder e;
    /* access modifiers changed from: private */
    public static boolean f;
    /* access modifiers changed from: private */
    public static a g;
    private static String h;
    private static String i;
    private static Context j;
    /* access modifiers changed from: private */
    public static String k;
    private static boolean l;
    private static boolean m = false;
    private static int n;
    /* access modifiers changed from: private */
    public static final Object o = new Object();

    static {
        b = null;
        try {
            b = new SimpleDateFormat("MM-dd HH:mm:ss");
        } catch (Throwable th) {
        }
    }

    private static boolean b(String str, String str2, String str3) {
        try {
            com.tencent.bugly.crashreport.common.info.a b2 = com.tencent.bugly.crashreport.common.info.a.b();
            if (!(b2 == null || b2.D == null)) {
                return b2.D.appendLogToNative(str, str2, str3);
            }
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
        }
        return false;
    }

    private static String f() {
        try {
            com.tencent.bugly.crashreport.common.info.a b2 = com.tencent.bugly.crashreport.common.info.a.b();
            if (!(b2 == null || b2.D == null)) {
                return b2.D.getLogFromNative();
            }
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
        }
        return null;
    }

    public static synchronized void a(Context context) {
        synchronized (y.class) {
            if (!l && context != null && a) {
                try {
                    e = new StringBuilder(0);
                    d = new StringBuilder(0);
                    j = context;
                    com.tencent.bugly.crashreport.common.info.a a2 = com.tencent.bugly.crashreport.common.info.a.a(context);
                    h = a2.d;
                    a2.getClass();
                    i = "";
                    k = j.getFilesDir().getPath() + "/buglylog_" + h + "_" + i + ".txt";
                    n = Process.myPid();
                } catch (Throwable th) {
                }
                l = true;
            }
        }
    }

    public static void a(int i2) {
        synchronized (o) {
            c = i2;
            if (i2 < 0) {
                c = 0;
            } else if (i2 > 10240) {
                c = Task.EXTRAS_LIMIT_BYTES;
            }
        }
    }

    public static void a(boolean z) {
        x.a("[LogUtil] Whether can record user log into native: " + z, new Object[0]);
        m = z;
    }

    public static void a(String str, String str2, Throwable th) {
        if (th != null) {
            String message = th.getMessage();
            if (message == null) {
                message = "";
            }
            a(str, str2, message + 10 + z.b(th));
        }
    }

    public static synchronized void a(String str, String str2, String str3) {
        synchronized (y.class) {
            if (l && a) {
                if (!m || !b(str, str2, str3)) {
                    long myTid = (long) Process.myTid();
                    d.setLength(0);
                    if (str3.length() > 30720) {
                        str3 = str3.substring(str3.length() - 30720, str3.length() - 1);
                    }
                    Date date = new Date();
                    d.append(b != null ? b.format(date) : date.toString()).append(" ").append(n).append(" ").append(myTid).append(" ").append(str).append(" ").append(str2).append(": ").append(str3).append("\u0001\r\n");
                    String sb = d.toString();
                    synchronized (o) {
                        e.append(sb);
                        if (e.length() > c) {
                            if (!f) {
                                f = true;
                                w.a().a(new Runnable() {
                                    public final void run() {
                                        synchronized (y.o) {
                                            try {
                                                if (y.g == null) {
                                                    a unused = y.g = new a(y.k);
                                                } else if (y.g.b == null || y.g.b.length() + ((long) y.e.length()) > y.g.e) {
                                                    boolean unused2 = y.g.a();
                                                }
                                                if (y.g.a(y.e.toString())) {
                                                    y.e.setLength(0);
                                                    boolean unused3 = y.f = false;
                                                }
                                            } catch (Throwable th) {
                                            }
                                        }
                                    }
                                });
                            }
                        }
                    }
                }
            }
        }
    }

    public static byte[] a() {
        if (!a) {
            return null;
        }
        if (m) {
            x.a("[LogUtil] Get user log from native.", new Object[0]);
            String f2 = f();
            if (f2 != null) {
                x.a("[LogUtil] Got user log from native: %d bytes", Integer.valueOf(f2.length()));
                return z.a((File) null, f2, "BuglyNativeLog.txt");
            }
        }
        StringBuilder sb = new StringBuilder();
        synchronized (o) {
            if (g != null && g.a && g.b != null && g.b.length() > 0) {
                sb.append(z.a(g.b, 30720, true));
            }
            if (e != null && e.length() > 0) {
                sb.append(e.toString());
            }
        }
        return z.a((File) null, sb.toString(), "BuglyLog.txt");
    }

    /* compiled from: BUGLY */
    public static class a {
        /* access modifiers changed from: private */
        public boolean a;
        /* access modifiers changed from: private */
        public File b;
        private String c;
        private long d;
        /* access modifiers changed from: private */
        public long e = 30720;

        public a(String str) {
            if (str != null && !str.equals("")) {
                this.c = str;
                this.a = a();
            }
        }

        /* access modifiers changed from: private */
        public boolean a() {
            try {
                this.b = new File(this.c);
                if (this.b.exists() && !this.b.delete()) {
                    this.a = false;
                    return false;
                } else if (this.b.createNewFile()) {
                    return true;
                } else {
                    this.a = false;
                    return false;
                }
            } catch (Throwable th) {
                x.a(th);
                this.a = false;
                return false;
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:16:0x0038 A[SYNTHETIC, Splitter:B:16:0x0038] */
        /* JADX WARNING: Removed duplicated region for block: B:22:0x0042 A[SYNTHETIC, Splitter:B:22:0x0042] */
        /* JADX WARNING: Removed duplicated region for block: B:31:? A[RETURN, SYNTHETIC] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final boolean a(java.lang.String r9) {
            /*
                r8 = this;
                r1 = 1
                r0 = 0
                boolean r2 = r8.a
                if (r2 != 0) goto L_0x0007
            L_0x0006:
                return r0
            L_0x0007:
                r3 = 0
                java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch:{ Throwable -> 0x002e, all -> 0x003e }
                java.io.File r4 = r8.b     // Catch:{ Throwable -> 0x002e, all -> 0x003e }
                r5 = 1
                r2.<init>(r4, r5)     // Catch:{ Throwable -> 0x002e, all -> 0x003e }
                java.lang.String r3 = "UTF-8"
                byte[] r3 = r9.getBytes(r3)     // Catch:{ Throwable -> 0x004c }
                r2.write(r3)     // Catch:{ Throwable -> 0x004c }
                r2.flush()     // Catch:{ Throwable -> 0x004c }
                r2.close()     // Catch:{ Throwable -> 0x004c }
                long r4 = r8.d     // Catch:{ Throwable -> 0x004c }
                int r3 = r3.length     // Catch:{ Throwable -> 0x004c }
                long r6 = (long) r3     // Catch:{ Throwable -> 0x004c }
                long r4 = r4 + r6
                r8.d = r4     // Catch:{ Throwable -> 0x004c }
                r3 = 1
                r8.a = r3     // Catch:{ Throwable -> 0x004c }
                r2.close()     // Catch:{ IOException -> 0x0046 }
            L_0x002c:
                r0 = r1
                goto L_0x0006
            L_0x002e:
                r1 = move-exception
                r2 = r3
            L_0x0030:
                com.tencent.bugly.proguard.x.a(r1)     // Catch:{ all -> 0x004a }
                r1 = 0
                r8.a = r1     // Catch:{ all -> 0x004a }
                if (r2 == 0) goto L_0x0006
                r2.close()     // Catch:{ IOException -> 0x003c }
                goto L_0x0006
            L_0x003c:
                r1 = move-exception
                goto L_0x0006
            L_0x003e:
                r0 = move-exception
                r2 = r3
            L_0x0040:
                if (r2 == 0) goto L_0x0045
                r2.close()     // Catch:{ IOException -> 0x0048 }
            L_0x0045:
                throw r0
            L_0x0046:
                r0 = move-exception
                goto L_0x002c
            L_0x0048:
                r1 = move-exception
                goto L_0x0045
            L_0x004a:
                r0 = move-exception
                goto L_0x0040
            L_0x004c:
                r1 = move-exception
                goto L_0x0030
            */
            throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.y.a.a(java.lang.String):boolean");
        }
    }
}
