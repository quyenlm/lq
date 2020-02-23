package com.tencent.bugly.crashreport.crash.jni;

import android.annotation.SuppressLint;
import android.content.Context;
import com.tencent.bugly.crashreport.a;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import com.tencent.bugly.crashreport.crash.CrashDetailBean;
import com.tencent.bugly.crashreport.crash.b;
import com.tencent.bugly.crashreport.crash.c;
import com.tencent.bugly.proguard.w;
import com.tencent.bugly.proguard.x;
import com.tencent.bugly.proguard.y;
import com.tencent.bugly.proguard.z;
import com.tencent.qqgamemi.util.TimeUtils;
import com.tencent.smtt.utils.TbsLog;
import java.io.File;

/* compiled from: BUGLY */
public class NativeCrashHandler implements a {
    private static NativeCrashHandler a;
    private static boolean l = false;
    private static boolean m = false;
    /* access modifiers changed from: private */
    public static boolean o = true;
    /* access modifiers changed from: private */
    public final Context b;
    private final com.tencent.bugly.crashreport.common.info.a c;
    private final w d;
    /* access modifiers changed from: private */
    public NativeExceptionHandler e;
    /* access modifiers changed from: private */
    public String f;
    private final boolean g;
    private boolean h = false;
    private boolean i = false;
    private boolean j = false;
    private boolean k = false;
    /* access modifiers changed from: private */
    public b n;

    /* access modifiers changed from: protected */
    public native boolean appendNativeLog(String str, String str2, String str3);

    /* access modifiers changed from: protected */
    public native boolean appendWholeNativeLog(String str);

    /* access modifiers changed from: protected */
    public native String getNativeKeyValueList();

    /* access modifiers changed from: protected */
    public native String getNativeLog();

    /* access modifiers changed from: protected */
    public native boolean putNativeKeyValue(String str, String str2);

    /* access modifiers changed from: protected */
    public native String regist(String str, boolean z, int i2);

    /* access modifiers changed from: protected */
    public native String removeNativeKeyValue(String str);

    /* access modifiers changed from: protected */
    public native void setNativeInfo(int i2, String str);

    /* access modifiers changed from: protected */
    public native void testCrash();

    /* access modifiers changed from: protected */
    public native String unregist();

    @SuppressLint({"SdCardPath"})
    private NativeCrashHandler(Context context, com.tencent.bugly.crashreport.common.info.a aVar, b bVar, w wVar, boolean z, String str) {
        this.b = z.a(context);
        try {
            if (z.a(str)) {
                str = context.getDir("bugly", 0).getAbsolutePath();
            }
        } catch (Throwable th) {
            str = "/data/data/" + com.tencent.bugly.crashreport.common.info.a.a(context).c + "/app_bugly";
        }
        this.n = bVar;
        this.f = str;
        this.c = aVar;
        this.d = wVar;
        this.g = z;
        this.e = new a(context, aVar, bVar, com.tencent.bugly.crashreport.common.strategy.a.a());
    }

    public static synchronized NativeCrashHandler getInstance(Context context, com.tencent.bugly.crashreport.common.info.a aVar, b bVar, com.tencent.bugly.crashreport.common.strategy.a aVar2, w wVar, boolean z, String str) {
        NativeCrashHandler nativeCrashHandler;
        synchronized (NativeCrashHandler.class) {
            if (a == null) {
                a = new NativeCrashHandler(context, aVar, bVar, wVar, z, str);
            }
            nativeCrashHandler = a;
        }
        return nativeCrashHandler;
    }

    public static synchronized NativeCrashHandler getInstance() {
        NativeCrashHandler nativeCrashHandler;
        synchronized (NativeCrashHandler.class) {
            nativeCrashHandler = a;
        }
        return nativeCrashHandler;
    }

    public synchronized String getDumpFilePath() {
        return this.f;
    }

    public synchronized void setDumpFilePath(String str) {
        this.f = str;
    }

    public static void setShouldHandleInJava(boolean z) {
        o = z;
        if (a != null) {
            a.a(999, new StringBuilder().append(z).toString());
        }
    }

    public static boolean isShouldHandleInJava() {
        return o;
    }

    private synchronized void a(boolean z) {
        if (this.j) {
            x.d("[Native] Native crash report has already registered.", new Object[0]);
        } else {
            if (this.i) {
                try {
                    String regist = regist(this.f, z, 1);
                    if (regist != null) {
                        x.a("[Native] Native Crash Report enable.", new Object[0]);
                        x.c("[Native] Check extra jni for Bugly NDK v%s", regist);
                        String replace = "2.1.1".replace(".", "");
                        String replace2 = "2.3.0".replace(".", "");
                        String replace3 = regist.replace(".", "");
                        if (replace3.length() == 2) {
                            replace3 = replace3 + "0";
                        } else if (replace3.length() == 1) {
                            replace3 = replace3 + "00";
                        }
                        try {
                            if (Integer.parseInt(replace3) >= Integer.parseInt(replace)) {
                                l = true;
                            }
                            if (Integer.parseInt(replace3) >= Integer.parseInt(replace2)) {
                                m = true;
                            }
                        } catch (Throwable th) {
                        }
                        if (m) {
                            x.a("[Native] Info setting jni can be accessed.", new Object[0]);
                        } else {
                            x.d("[Native] Info setting jni can not be accessed.", new Object[0]);
                        }
                        if (l) {
                            x.a("[Native] Extra jni can be accessed.", new Object[0]);
                        } else {
                            x.d("[Native] Extra jni can not be accessed.", new Object[0]);
                        }
                        this.c.n = regist;
                        y.a(l);
                        this.j = true;
                    }
                } catch (Throwable th2) {
                    x.c("[Native] Failed to load Bugly SO file.", new Object[0]);
                }
            } else if (this.h) {
                try {
                    Class[] clsArr = {String.class, String.class, Integer.TYPE, Integer.TYPE};
                    Object[] objArr = new Object[4];
                    objArr[0] = this.f;
                    objArr[1] = com.tencent.bugly.crashreport.common.info.b.a(this.b, false);
                    objArr[2] = Integer.valueOf(z ? 1 : 5);
                    objArr[3] = 1;
                    String str = (String) z.a("com.tencent.feedback.eup.jni.NativeExceptionUpload", "registNativeExceptionHandler2", (Object) null, clsArr, objArr);
                    if (str == null) {
                        Class[] clsArr2 = {String.class, String.class, Integer.TYPE};
                        com.tencent.bugly.crashreport.common.info.a.b();
                        str = (String) z.a("com.tencent.feedback.eup.jni.NativeExceptionUpload", "registNativeExceptionHandler", (Object) null, clsArr2, new Object[]{this.f, com.tencent.bugly.crashreport.common.info.b.a(this.b, false), Integer.valueOf(com.tencent.bugly.crashreport.common.info.a.K())});
                    }
                    if (str != null) {
                        this.j = true;
                        com.tencent.bugly.crashreport.common.info.a.b().n = str;
                        Boolean bool = (Boolean) z.a("com.tencent.feedback.eup.jni.NativeExceptionUpload", "checkExtraJni", (Object) null, new Class[]{String.class}, new Object[]{str});
                        if (bool != null) {
                            boolean booleanValue = bool.booleanValue();
                            l = booleanValue;
                            y.a(booleanValue);
                        }
                        z.a("com.tencent.feedback.eup.jni.NativeExceptionUpload", "enableHandler", (Object) null, new Class[]{Boolean.TYPE}, new Object[]{true});
                        z.a("com.tencent.feedback.eup.jni.NativeExceptionUpload", "setLogMode", (Object) null, new Class[]{Integer.TYPE}, new Object[]{Integer.valueOf(z ? 1 : 5)});
                    }
                } catch (Throwable th3) {
                }
            }
            this.i = false;
            this.h = false;
        }
    }

    public synchronized void startNativeMonitor() {
        if (this.i || this.h) {
            a(this.g);
        } else {
            String str = "Bugly";
            boolean z = !z.a(this.c.m);
            String str2 = this.c.m;
            if (!z) {
                this.c.getClass();
            } else {
                str = str2;
            }
            this.i = a(str, z);
            if (this.i || this.h) {
                a(this.g);
                if (l) {
                    setNativeAppVersion(this.c.j);
                    setNativeAppChannel(this.c.l);
                    setNativeAppPackage(this.c.c);
                    setNativeUserId(this.c.g());
                    setNativeIsAppForeground(this.c.a());
                    setNativeLaunchTime(this.c.a);
                }
            }
        }
    }

    public void checkUploadRecordCrash() {
        this.d.a(new Runnable() {
            public final void run() {
                if (!z.a(NativeCrashHandler.this.b, "native_record_lock", 10000)) {
                    x.a("[Native] Failed to lock file for handling native crash record.", new Object[0]);
                    return;
                }
                if (!NativeCrashHandler.o) {
                    boolean unused = NativeCrashHandler.this.a(999, "false");
                }
                CrashDetailBean a2 = b.a(NativeCrashHandler.this.b, NativeCrashHandler.this.f, NativeCrashHandler.this.e);
                if (a2 != null) {
                    x.a("[Native] Get crash from native record.", new Object[0]);
                    if (!NativeCrashHandler.this.n.a(a2)) {
                        NativeCrashHandler.this.n.a(a2, 3000, false);
                    }
                    b.a(false, NativeCrashHandler.this.f);
                }
                NativeCrashHandler.this.a();
                z.b(NativeCrashHandler.this.b, "native_record_lock");
            }
        });
    }

    private static boolean a(String str, boolean z) {
        boolean z2;
        try {
            x.a("[Native] Trying to load so: %s", str);
            if (z) {
                System.load(str);
            } else {
                System.loadLibrary(str);
            }
            try {
                x.a("[Native] Successfully loaded SO: %s", str);
                return true;
            } catch (Throwable th) {
                th = th;
                z2 = true;
            }
        } catch (Throwable th2) {
            th = th2;
            z2 = false;
        }
        x.d(th.getMessage(), new Object[0]);
        x.d("[Native] Failed to load so: %s", str);
        return z2;
    }

    private synchronized void c() {
        if (!this.j) {
            x.d("[Native] Native crash report has already unregistered.", new Object[0]);
        } else {
            try {
                if (unregist() != null) {
                    x.a("[Native] Successfully closed native crash report.", new Object[0]);
                    this.j = false;
                }
            } catch (Throwable th) {
                x.c("[Native] Failed to close native crash report.", new Object[0]);
            }
            try {
                z.a("com.tencent.feedback.eup.jni.NativeExceptionUpload", "enableHandler", (Object) null, new Class[]{Boolean.TYPE}, new Object[]{false});
                this.j = false;
                x.a("[Native] Successfully closed native crash report.", new Object[0]);
            } catch (Throwable th2) {
                x.c("[Native] Failed to close native crash report.", new Object[0]);
                this.i = false;
                this.h = false;
            }
        }
        return;
    }

    public void testNativeCrash() {
        if (!this.i) {
            x.d("[Native] Bugly SO file has not been load.", new Object[0]);
        } else {
            testCrash();
        }
    }

    public void testNativeCrash(boolean z, boolean z2, boolean z3) {
        a(16, new StringBuilder().append(z).toString());
        a(17, new StringBuilder().append(z2).toString());
        a(18, new StringBuilder().append(z3).toString());
        testNativeCrash();
    }

    public NativeExceptionHandler getNativeExceptionHandler() {
        return this.e;
    }

    /* access modifiers changed from: protected */
    public final void a() {
        long b2 = z.b() - c.g;
        long b3 = TimeUtils.MILLIS_IN_DAY + z.b();
        File file = new File(this.f);
        if (file.exists() && file.isDirectory()) {
            try {
                File[] listFiles = file.listFiles();
                if (listFiles != null && listFiles.length != 0) {
                    int i2 = 0;
                    int i3 = 0;
                    for (File file2 : listFiles) {
                        long lastModified = file2.lastModified();
                        if (lastModified < b2 || lastModified >= b3) {
                            x.a("[Native] Delete record file: %s", file2.getAbsolutePath());
                            i3++;
                            if (file2.delete()) {
                                i2++;
                            }
                        }
                    }
                    x.c("[Native] Number of record files overdue: %d, has deleted: %d", Integer.valueOf(i3), Integer.valueOf(i2));
                }
            } catch (Throwable th) {
                x.a(th);
            }
        }
    }

    public void removeEmptyNativeRecordFiles() {
        b.c(this.f);
    }

    private synchronized void b(boolean z) {
        if (z) {
            startNativeMonitor();
        } else {
            c();
        }
    }

    public synchronized boolean isUserOpened() {
        return this.k;
    }

    private synchronized void c(boolean z) {
        if (this.k != z) {
            x.a("user change native %b", Boolean.valueOf(z));
            this.k = z;
        }
    }

    public synchronized void setUserOpened(boolean z) {
        boolean z2 = true;
        synchronized (this) {
            c(z);
            boolean isUserOpened = isUserOpened();
            com.tencent.bugly.crashreport.common.strategy.a a2 = com.tencent.bugly.crashreport.common.strategy.a.a();
            if (a2 == null) {
                z2 = isUserOpened;
            } else if (!isUserOpened || !a2.c().g) {
                z2 = false;
            }
            if (z2 != this.j) {
                x.a("native changed to %b", Boolean.valueOf(z2));
                b(z2);
            }
        }
    }

    public synchronized void onStrategyChanged(StrategyBean strategyBean) {
        boolean z = true;
        synchronized (this) {
            if (strategyBean != null) {
                if (strategyBean.g != this.j) {
                    x.d("server native changed to %b", Boolean.valueOf(strategyBean.g));
                }
            }
            if (!com.tencent.bugly.crashreport.common.strategy.a.a().c().g || !this.k) {
                z = false;
            }
            if (z != this.j) {
                x.a("native changed to %b", Boolean.valueOf(z));
                b(z);
            }
        }
    }

    public boolean appendLogToNative(String str, String str2, String str3) {
        if (!this.h && !this.i) {
            return false;
        }
        if (!l) {
            return false;
        }
        if (str == null || str2 == null || str3 == null) {
            return false;
        }
        try {
            if (this.i) {
                return appendNativeLog(str, str2, str3);
            }
            Boolean bool = (Boolean) z.a("com.tencent.feedback.eup.jni.NativeExceptionUpload", "appendNativeLog", (Object) null, new Class[]{String.class, String.class, String.class}, new Object[]{str, str2, str3});
            if (bool != null) {
                return bool.booleanValue();
            }
            return false;
        } catch (UnsatisfiedLinkError e2) {
            l = false;
            return false;
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return false;
        }
    }

    public String getLogFromNative() {
        if (!this.h && !this.i) {
            return null;
        }
        if (!l) {
            return null;
        }
        try {
            if (this.i) {
                return getNativeLog();
            }
            return (String) z.a("com.tencent.feedback.eup.jni.NativeExceptionUpload", "getNativeLog", (Object) null, (Class<?>[]) null, (Object[]) null);
        } catch (UnsatisfiedLinkError e2) {
            l = false;
            return null;
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    public boolean putKeyValueToNative(String str, String str2) {
        if (!this.h && !this.i) {
            return false;
        }
        if (!l) {
            return false;
        }
        if (str == null || str2 == null) {
            return false;
        }
        try {
            if (this.i) {
                return putNativeKeyValue(str, str2);
            }
            Boolean bool = (Boolean) z.a("com.tencent.feedback.eup.jni.NativeExceptionUpload", "putNativeKeyValue", (Object) null, new Class[]{String.class, String.class}, new Object[]{str, str2});
            if (bool != null) {
                return bool.booleanValue();
            }
            return false;
        } catch (UnsatisfiedLinkError e2) {
            l = false;
            return false;
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return false;
        }
    }

    /* access modifiers changed from: private */
    public boolean a(int i2, String str) {
        if (!this.i || !m) {
            return false;
        }
        try {
            setNativeInfo(i2, str);
            return true;
        } catch (UnsatisfiedLinkError e2) {
            m = false;
            return false;
        } catch (Throwable th) {
            if (x.a(th)) {
                return false;
            }
            th.printStackTrace();
            return false;
        }
    }

    public boolean filterSigabrtSysLog() {
        return a((int) TbsLog.TBSLOG_CODE_SDK_LOAD_ERROR, "true");
    }

    public boolean setNativeAppVersion(String str) {
        return a(10, str);
    }

    public boolean setNativeAppChannel(String str) {
        return a(12, str);
    }

    public boolean setNativeAppPackage(String str) {
        return a(13, str);
    }

    public boolean setNativeUserId(String str) {
        return a(11, str);
    }

    public boolean setNativeIsAppForeground(boolean z) {
        return a(14, z ? "true" : "false");
    }

    public boolean setNativeLaunchTime(long j2) {
        try {
            return a(15, String.valueOf(j2));
        } catch (NumberFormatException e2) {
            if (!x.a(e2)) {
                e2.printStackTrace();
            }
            return false;
        }
    }
}
