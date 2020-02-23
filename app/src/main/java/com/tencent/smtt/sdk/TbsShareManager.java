package com.tencent.smtt.sdk;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;
import com.tencent.smtt.sdk.TbsListener;
import com.tencent.smtt.utils.TbsLog;
import com.tencent.smtt.utils.b;
import java.io.File;
import java.io.IOException;

public class TbsShareManager {
    private static Context a;
    private static boolean b;
    private static String c = null;
    private static String d = null;
    private static int e = 0;
    private static String f = null;
    private static boolean g = false;
    private static boolean h = false;
    private static String i = null;
    private static boolean j = false;
    private static boolean k = false;
    public static boolean mHasQueryed = false;

    static int a(Context context, boolean z) {
        b(context, z);
        return e;
    }

    static Context a(Context context, String str) {
        try {
            return context.createPackageContext(str, 2);
        } catch (PackageManager.NameNotFoundException e2) {
            return null;
        } catch (Exception e3) {
            e3.printStackTrace();
            return null;
        }
    }

    static void a(Context context) {
        try {
            TbsLinuxToolsJni tbsLinuxToolsJni = new TbsLinuxToolsJni(context);
            a(context, tbsLinuxToolsJni, am.a().q(context));
            tbsLinuxToolsJni.a(am.a().r(context).getAbsolutePath(), "755");
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private static void a(Context context, TbsLinuxToolsJni tbsLinuxToolsJni, File file) {
        if (file != null && file.exists() && file.isDirectory()) {
            tbsLinuxToolsJni.a(file.getAbsolutePath(), "755");
            for (File file2 : file.listFiles()) {
                if (file2.isFile()) {
                    if (file2.getAbsolutePath().indexOf(".so") > 0) {
                        tbsLinuxToolsJni.a(file2.getAbsolutePath(), "755");
                    } else {
                        tbsLinuxToolsJni.a(file2.getAbsolutePath(), "644");
                    }
                } else if (file2.isDirectory()) {
                    a(context, tbsLinuxToolsJni, file2);
                } else {
                    TbsLog.e("TbsShareManager", "unknown file type.", true);
                }
            }
        }
    }

    private static File b(Context context, String str) {
        File r = am.a().r(context);
        if (r == null) {
            return null;
        }
        File file = new File(r, str);
        if (file != null && file.exists()) {
            return file;
        }
        try {
            file.createNewFile();
            return file;
        } catch (IOException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    static void b(Context context) {
        try {
            a(context, new TbsLinuxToolsJni(context), am.a().p(context));
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    static boolean b(Context context, boolean z) {
        if (i(context)) {
            return true;
        }
        if (z) {
            QbSdk.a(context, "TbsShareManager::isShareTbsCoreAvailable forceSysWebViewInner!");
        }
        return false;
    }

    static String c(Context context) {
        j(context);
        return d;
    }

    /* JADX WARNING: Removed duplicated region for block: B:32:0x0082 A[SYNTHETIC, Splitter:B:32:0x0082] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0087 A[SYNTHETIC, Splitter:B:35:0x0087] */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x0090 A[SYNTHETIC, Splitter:B:40:0x0090] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x0095 A[SYNTHETIC, Splitter:B:43:0x0095] */
    /* JADX WARNING: Removed duplicated region for block: B:60:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void c(android.content.Context r8, boolean r9) {
        /*
            r2 = 0
            r0 = 0
            r1 = 0
            java.lang.String r3 = "core_info"
            java.io.File r4 = b((android.content.Context) r8, (java.lang.String) r3)     // Catch:{ Throwable -> 0x007a, all -> 0x008b }
            if (r4 != 0) goto L_0x0016
            if (r2 == 0) goto L_0x0010
            r0.close()     // Catch:{ Exception -> 0x009f }
        L_0x0010:
            if (r2 == 0) goto L_0x0015
            r1.close()     // Catch:{ Exception -> 0x0078 }
        L_0x0015:
            return
        L_0x0016:
            java.io.FileInputStream r0 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x007a, all -> 0x008b }
            r0.<init>(r4)     // Catch:{ Throwable -> 0x007a, all -> 0x008b }
            java.io.BufferedInputStream r3 = new java.io.BufferedInputStream     // Catch:{ Throwable -> 0x007a, all -> 0x008b }
            r3.<init>(r0)     // Catch:{ Throwable -> 0x007a, all -> 0x008b }
            java.util.Properties r0 = new java.util.Properties     // Catch:{ Throwable -> 0x00a9, all -> 0x00a4 }
            r0.<init>()     // Catch:{ Throwable -> 0x00a9, all -> 0x00a4 }
            r0.load(r3)     // Catch:{ Throwable -> 0x00a9, all -> 0x00a4 }
            java.lang.String r1 = "core_disabled"
            r5 = 0
            java.lang.String r5 = java.lang.String.valueOf(r5)     // Catch:{ Throwable -> 0x00a9, all -> 0x00a4 }
            r0.setProperty(r1, r5)     // Catch:{ Throwable -> 0x00a9, all -> 0x00a4 }
            if (r9 == 0) goto L_0x005f
            com.tencent.smtt.sdk.am r1 = com.tencent.smtt.sdk.am.a()     // Catch:{ Throwable -> 0x00a9, all -> 0x00a4 }
            java.io.File r1 = r1.q(r8)     // Catch:{ Throwable -> 0x00a9, all -> 0x00a4 }
            java.lang.String r1 = r1.getAbsolutePath()     // Catch:{ Throwable -> 0x00a9, all -> 0x00a4 }
            android.content.Context r5 = r8.getApplicationContext()     // Catch:{ Throwable -> 0x00a9, all -> 0x00a4 }
            java.lang.String r5 = r5.getPackageName()     // Catch:{ Throwable -> 0x00a9, all -> 0x00a4 }
            int r6 = com.tencent.smtt.utils.b.b(r8)     // Catch:{ Throwable -> 0x00a9, all -> 0x00a4 }
            java.lang.String r7 = "core_packagename"
            r0.setProperty(r7, r5)     // Catch:{ Throwable -> 0x00a9, all -> 0x00a4 }
            java.lang.String r5 = "core_path"
            r0.setProperty(r5, r1)     // Catch:{ Throwable -> 0x00a9, all -> 0x00a4 }
            java.lang.String r1 = "app_version"
            java.lang.String r5 = java.lang.String.valueOf(r6)     // Catch:{ Throwable -> 0x00a9, all -> 0x00a4 }
            r0.setProperty(r1, r5)     // Catch:{ Throwable -> 0x00a9, all -> 0x00a4 }
        L_0x005f:
            java.io.FileOutputStream r5 = new java.io.FileOutputStream     // Catch:{ Throwable -> 0x00a9, all -> 0x00a4 }
            r5.<init>(r4)     // Catch:{ Throwable -> 0x00a9, all -> 0x00a4 }
            java.io.BufferedOutputStream r1 = new java.io.BufferedOutputStream     // Catch:{ Throwable -> 0x00a9, all -> 0x00a4 }
            r1.<init>(r5)     // Catch:{ Throwable -> 0x00a9, all -> 0x00a4 }
            r2 = 0
            r0.store(r1, r2)     // Catch:{ Throwable -> 0x00ac }
            if (r3 == 0) goto L_0x0072
            r3.close()     // Catch:{ Exception -> 0x00a2 }
        L_0x0072:
            if (r1 == 0) goto L_0x0015
            r1.close()     // Catch:{ Exception -> 0x0078 }
            goto L_0x0015
        L_0x0078:
            r0 = move-exception
            goto L_0x0015
        L_0x007a:
            r0 = move-exception
            r1 = r2
            r3 = r2
        L_0x007d:
            r0.printStackTrace()     // Catch:{ all -> 0x00a7 }
            if (r3 == 0) goto L_0x0085
            r3.close()     // Catch:{ Exception -> 0x009d }
        L_0x0085:
            if (r1 == 0) goto L_0x0015
            r1.close()     // Catch:{ Exception -> 0x0078 }
            goto L_0x0015
        L_0x008b:
            r0 = move-exception
            r1 = r2
            r3 = r2
        L_0x008e:
            if (r3 == 0) goto L_0x0093
            r3.close()     // Catch:{ Exception -> 0x0099 }
        L_0x0093:
            if (r1 == 0) goto L_0x0098
            r1.close()     // Catch:{ Exception -> 0x009b }
        L_0x0098:
            throw r0
        L_0x0099:
            r2 = move-exception
            goto L_0x0093
        L_0x009b:
            r1 = move-exception
            goto L_0x0098
        L_0x009d:
            r0 = move-exception
            goto L_0x0085
        L_0x009f:
            r0 = move-exception
            goto L_0x0010
        L_0x00a2:
            r0 = move-exception
            goto L_0x0072
        L_0x00a4:
            r0 = move-exception
            r1 = r2
            goto L_0x008e
        L_0x00a7:
            r0 = move-exception
            goto L_0x008e
        L_0x00a9:
            r0 = move-exception
            r1 = r2
            goto L_0x007d
        L_0x00ac:
            r0 = move-exception
            goto L_0x007d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.TbsShareManager.c(android.content.Context, boolean):void");
    }

    static int d(Context context) {
        return a(context, true);
    }

    static Context e(Context context) {
        j(context);
        if (f == null) {
            return null;
        }
        Context a2 = a(context, f);
        if (!am.a().f(a2)) {
            return null;
        }
        return a2;
    }

    /* JADX WARNING: Removed duplicated region for block: B:31:0x004e A[SYNTHETIC, Splitter:B:31:0x004e] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static synchronized java.lang.String f(android.content.Context r6) {
        /*
            r0 = 0
            java.lang.Class<com.tencent.smtt.sdk.TbsShareManager> r3 = com.tencent.smtt.sdk.TbsShareManager.class
            monitor-enter(r3)
            r1 = 0
            java.lang.String r2 = "core_info"
            java.io.File r2 = b((android.content.Context) r6, (java.lang.String) r2)     // Catch:{ Throwable -> 0x003d, all -> 0x004a }
            if (r2 != 0) goto L_0x0014
            if (r0 == 0) goto L_0x0012
            r1.close()     // Catch:{ Exception -> 0x0048 }
        L_0x0012:
            monitor-exit(r3)
            return r0
        L_0x0014:
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x003d, all -> 0x004a }
            r1.<init>(r2)     // Catch:{ Throwable -> 0x003d, all -> 0x004a }
            java.io.BufferedInputStream r2 = new java.io.BufferedInputStream     // Catch:{ Throwable -> 0x003d, all -> 0x004a }
            r2.<init>(r1)     // Catch:{ Throwable -> 0x003d, all -> 0x004a }
            java.util.Properties r1 = new java.util.Properties     // Catch:{ Throwable -> 0x0062 }
            r1.<init>()     // Catch:{ Throwable -> 0x0062 }
            r1.load(r2)     // Catch:{ Throwable -> 0x0062 }
            java.lang.String r4 = "core_packagename"
            java.lang.String r5 = ""
            java.lang.String r1 = r1.getProperty(r4, r5)     // Catch:{ Throwable -> 0x0062 }
            java.lang.String r4 = ""
            boolean r4 = r4.equals(r1)     // Catch:{ Throwable -> 0x0062 }
            if (r4 != 0) goto L_0x0059
            if (r2 == 0) goto L_0x003b
            r2.close()     // Catch:{ Exception -> 0x0057 }
        L_0x003b:
            r0 = r1
            goto L_0x0012
        L_0x003d:
            r1 = move-exception
            r2 = r0
        L_0x003f:
            r1.printStackTrace()     // Catch:{ all -> 0x005f }
            if (r2 == 0) goto L_0x0012
            r2.close()     // Catch:{ Exception -> 0x0048 }
            goto L_0x0012
        L_0x0048:
            r1 = move-exception
            goto L_0x0012
        L_0x004a:
            r1 = move-exception
            r2 = r0
        L_0x004c:
            if (r2 == 0) goto L_0x0051
            r2.close()     // Catch:{ Exception -> 0x0055 }
        L_0x0051:
            throw r1     // Catch:{ all -> 0x0052 }
        L_0x0052:
            r0 = move-exception
            monitor-exit(r3)
            throw r0
        L_0x0055:
            r0 = move-exception
            goto L_0x0051
        L_0x0057:
            r0 = move-exception
            goto L_0x003b
        L_0x0059:
            if (r2 == 0) goto L_0x0012
            r2.close()     // Catch:{ Exception -> 0x0048 }
            goto L_0x0012
        L_0x005f:
            r0 = move-exception
            r1 = r0
            goto L_0x004c
        L_0x0062:
            r1 = move-exception
            goto L_0x003f
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.TbsShareManager.f(android.content.Context):java.lang.String");
    }

    public static int findCoreForThirdPartyApp(Context context) {
        n(context);
        TbsLog.i("TbsShareManager", "core_info mAvailableCoreVersion is " + e + " mAvailableCorePath is " + d + " mSrcPackageName is " + f);
        if (f == null) {
            TbsLog.e("TbsShareManager", "mSrcPackageName is null !!!");
        }
        if (f == null || !f.equals("AppDefined")) {
            if (!k(context) && !l(context)) {
                e = 0;
                d = null;
                f = null;
                TbsLog.i("TbsShareManager", "core_info error checkCoreInfo is false and checkCoreInOthers is false ");
            }
        } else if (e != am.a().a(c)) {
            e = 0;
            d = null;
            f = null;
            TbsLog.i("TbsShareManager", "check AppDefined core is error src is " + e + " dest is " + am.a().a(c));
        }
        if (e > 0) {
            ApplicationInfo applicationInfo = context.getApplicationInfo();
            if ((!("com.tencent.android.qqdownloader".equals(applicationInfo.packageName) || "com.jd.jrapp".equals(applicationInfo.packageName)) ? QbSdk.a(context, e) : false) || g) {
                e = 0;
                d = null;
                f = null;
                TbsLog.i("TbsShareManager", "core_info error QbSdk.isX5Disabled ");
            }
        }
        return e;
    }

    public static boolean forceLoadX5FromTBSDemo(Context context) {
        int sharedTbsCoreVersion;
        if (context == null || am.a().a(context, (File[]) null) || (sharedTbsCoreVersion = getSharedTbsCoreVersion(context, TbsConfig.APP_DEMO)) <= 0) {
            return false;
        }
        writeProperties(context, Integer.toString(sharedTbsCoreVersion), TbsConfig.APP_DEMO, am.a().q(a(context, TbsConfig.APP_DEMO)).getAbsolutePath(), "1");
        return true;
    }

    public static void forceToLoadX5ForThirdApp(Context context, boolean z) {
        File r;
        int a2;
        File file;
        try {
            if (!isThirdPartyApp(context) || QbSdk.getOnlyDownload() || (r = am.a().r(context)) == null) {
                return;
            }
            if (z && (file = new File(r, "core_info")) != null && file.exists()) {
                return;
            }
            if (c == null || (a2 = am.a().a(c)) <= 0) {
                TbsLog.i("TbsShareManager", "forceToLoadX5ForThirdApp #1");
                int h2 = h(context);
                int i2 = am.a().i(context);
                TbsLog.i("TbsShareManager", "forceToLoadX5ForThirdApp coreVersionFromConfig is " + h2);
                TbsLog.i("TbsShareManager", "forceToLoadX5ForThirdApp coreVersionFromCoreShare is " + i2);
                String[] coreProviderAppList = getCoreProviderAppList();
                for (String str : coreProviderAppList) {
                    int coreShareDecoupleCoreVersion = getCoreShareDecoupleCoreVersion(context, str);
                    if (coreShareDecoupleCoreVersion >= h2 && coreShareDecoupleCoreVersion >= i2 && coreShareDecoupleCoreVersion > 0) {
                        d = am.a().c(context, a(context, str)).getAbsolutePath();
                        f = str;
                        e = coreShareDecoupleCoreVersion;
                        if (QbSdk.canLoadX5FirstTimeThirdApp(context)) {
                            int b2 = b.b(context);
                            TbsLog.i("TbsShareManager", "forceToLoadX5ForThirdApp #2");
                            writeProperties(context, Integer.toString(e), f, d, Integer.toString(b2));
                            return;
                        }
                        e = 0;
                        d = null;
                        f = null;
                    }
                }
                for (String str2 : coreProviderAppList) {
                    int sharedTbsCoreVersion = getSharedTbsCoreVersion(context, str2);
                    if (sharedTbsCoreVersion >= h2 && sharedTbsCoreVersion >= i2 && sharedTbsCoreVersion > 0) {
                        d = am.a().b(context, a(context, str2)).getAbsolutePath();
                        f = str2;
                        e = sharedTbsCoreVersion;
                        if (QbSdk.canLoadX5FirstTimeThirdApp(context)) {
                            writeProperties(context, Integer.toString(e), f, d, Integer.toString(b.b(context)));
                            return;
                        }
                        e = 0;
                        d = null;
                        f = null;
                    }
                }
                return;
            }
            d = c;
            f = "AppDefined";
            e = a2;
            writeProperties(context, Integer.toString(e), f, d, Integer.toString(1));
        } catch (Exception e2) {
        }
    }

    static String g(Context context) {
        try {
            n(context);
            if (d == null || TextUtils.isEmpty(d)) {
                return null;
            }
            return d + File.separator + "res.apk";
        } catch (Throwable th) {
            Log.e("", "getTbsResourcesPath exception: " + Log.getStackTraceString(th));
            return null;
        }
    }

    public static boolean getCoreDisabled() {
        return g;
    }

    public static boolean getCoreFormOwn() {
        return j;
    }

    public static String[] getCoreProviderAppList() {
        return new String[]{TbsConfig.APP_DEMO, TbsConfig.APP_WX, TbsConfig.APP_QQ, TbsConfig.APP_QZONE};
    }

    public static int getCoreShareDecoupleCoreVersion(Context context, String str) {
        Context a2 = a(context, str);
        if (a2 != null) {
            return am.a().h(a2);
        }
        return 0;
    }

    public static String getHostCorePathAppDefined() {
        return c;
    }

    public static long getHostCoreVersions(Context context) {
        long j2 = 0;
        for (String str : getCoreProviderAppList()) {
            if (str.equalsIgnoreCase(TbsConfig.APP_WX)) {
                j2 += ((long) getSharedTbsCoreVersion(context, str)) * 10000000000L;
            } else if (str.equalsIgnoreCase(TbsConfig.APP_QQ)) {
                j2 += ((long) getSharedTbsCoreVersion(context, str)) * 100000;
            } else if (str.equalsIgnoreCase(TbsConfig.APP_QZONE)) {
                j2 += (long) getSharedTbsCoreVersion(context, str);
            }
        }
        return j2;
    }

    public static int getSharedTbsCoreVersion(Context context, String str) {
        Context a2 = a(context, str);
        if (a2 != null) {
            return am.a().i(a2);
        }
        return 0;
    }

    /* JADX WARNING: Removed duplicated region for block: B:43:0x007a A[SYNTHETIC, Splitter:B:43:0x007a] */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x008a A[SYNTHETIC, Splitter:B:52:0x008a] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static synchronized int h(android.content.Context r6) {
        /*
            r0 = 0
            java.lang.Class<com.tencent.smtt.sdk.TbsShareManager> r3 = com.tencent.smtt.sdk.TbsShareManager.class
            monitor-enter(r3)
            java.lang.String r1 = "TbsShareManager"
            java.lang.String r2 = "readCoreVersionFromConfig #1"
            com.tencent.smtt.utils.TbsLog.i(r1, r2)     // Catch:{ all -> 0x005f }
            r1 = 0
            java.lang.String r2 = "core_info"
            java.io.File r2 = b((android.content.Context) r6, (java.lang.String) r2)     // Catch:{ Throwable -> 0x0074, all -> 0x0086 }
            if (r2 != 0) goto L_0x0022
            java.lang.String r2 = "TbsShareManager"
            java.lang.String r4 = "readCoreVersionFromConfig #2"
            com.tencent.smtt.utils.TbsLog.i(r2, r4)     // Catch:{ Throwable -> 0x0074, all -> 0x0086 }
            if (r1 == 0) goto L_0x0020
            r1.close()     // Catch:{ Exception -> 0x0098 }
        L_0x0020:
            monitor-exit(r3)
            return r0
        L_0x0022:
            java.io.FileInputStream r4 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x0074, all -> 0x0086 }
            r4.<init>(r2)     // Catch:{ Throwable -> 0x0074, all -> 0x0086 }
            java.io.BufferedInputStream r2 = new java.io.BufferedInputStream     // Catch:{ Throwable -> 0x0074, all -> 0x0086 }
            r2.<init>(r4)     // Catch:{ Throwable -> 0x0074, all -> 0x0086 }
            java.util.Properties r1 = new java.util.Properties     // Catch:{ Throwable -> 0x00a2, all -> 0x009d }
            r1.<init>()     // Catch:{ Throwable -> 0x00a2, all -> 0x009d }
            r1.load(r2)     // Catch:{ Throwable -> 0x00a2, all -> 0x009d }
            java.lang.String r4 = "core_version"
            java.lang.String r5 = ""
            java.lang.String r1 = r1.getProperty(r4, r5)     // Catch:{ Throwable -> 0x00a2, all -> 0x009d }
            java.lang.String r4 = ""
            boolean r4 = r4.equals(r1)     // Catch:{ Throwable -> 0x00a2, all -> 0x009d }
            if (r4 != 0) goto L_0x0062
            java.lang.String r0 = "TbsShareManager"
            java.lang.String r4 = "readCoreVersionFromConfig #3"
            com.tencent.smtt.utils.TbsLog.i(r0, r4)     // Catch:{ Throwable -> 0x00a2, all -> 0x009d }
            int r0 = java.lang.Integer.parseInt(r1)     // Catch:{ Throwable -> 0x00a2, all -> 0x009d }
            r1 = 0
            int r0 = java.lang.Math.max(r0, r1)     // Catch:{ Throwable -> 0x00a2, all -> 0x009d }
            if (r2 == 0) goto L_0x0020
            r2.close()     // Catch:{ Exception -> 0x005a }
            goto L_0x0020
        L_0x005a:
            r1 = move-exception
            r1.printStackTrace()     // Catch:{ all -> 0x005f }
            goto L_0x0020
        L_0x005f:
            r0 = move-exception
            monitor-exit(r3)
            throw r0
        L_0x0062:
            java.lang.String r1 = "TbsShareManager"
            java.lang.String r4 = "readCoreVersionFromConfig #4"
            com.tencent.smtt.utils.TbsLog.i(r1, r4)     // Catch:{ Throwable -> 0x00a2, all -> 0x009d }
            if (r2 == 0) goto L_0x0020
            r2.close()     // Catch:{ Exception -> 0x006f }
            goto L_0x0020
        L_0x006f:
            r1 = move-exception
            r1.printStackTrace()     // Catch:{ all -> 0x005f }
            goto L_0x0020
        L_0x0074:
            r0 = move-exception
        L_0x0075:
            r0.printStackTrace()     // Catch:{ all -> 0x009f }
            if (r1 == 0) goto L_0x007d
            r1.close()     // Catch:{ Exception -> 0x0093 }
        L_0x007d:
            java.lang.String r0 = "TbsShareManager"
            java.lang.String r1 = "readCoreVersionFromConfig #5"
            com.tencent.smtt.utils.TbsLog.i(r0, r1)     // Catch:{ all -> 0x005f }
            r0 = -2
            goto L_0x0020
        L_0x0086:
            r0 = move-exception
            r2 = r1
        L_0x0088:
            if (r2 == 0) goto L_0x008d
            r2.close()     // Catch:{ Exception -> 0x008e }
        L_0x008d:
            throw r0     // Catch:{ all -> 0x005f }
        L_0x008e:
            r1 = move-exception
            r1.printStackTrace()     // Catch:{ all -> 0x005f }
            goto L_0x008d
        L_0x0093:
            r0 = move-exception
            r0.printStackTrace()     // Catch:{ all -> 0x005f }
            goto L_0x007d
        L_0x0098:
            r1 = move-exception
            r1.printStackTrace()     // Catch:{ all -> 0x005f }
            goto L_0x0020
        L_0x009d:
            r0 = move-exception
            goto L_0x0088
        L_0x009f:
            r0 = move-exception
            r2 = r1
            goto L_0x0088
        L_0x00a2:
            r0 = move-exception
            r1 = r2
            goto L_0x0075
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.TbsShareManager.h(android.content.Context):int");
    }

    static boolean i(Context context) {
        try {
            if (e == 0) {
                findCoreForThirdPartyApp(context);
            }
            if (e == 0) {
                TbsLog.addLog(TbsLog.TBSLOG_CODE_SDK_NO_SHARE_X5CORE, (String) null, new Object[0]);
                return false;
            }
            if (c == null) {
                if (e != 0 && getSharedTbsCoreVersion(context, f) == e) {
                    return true;
                }
            } else if (e != 0 && am.a().a(c) == e) {
                return true;
            }
            if (l(context)) {
                return true;
            }
            TbsCoreLoadStat.getInstance().a(context, TbsListener.ErrorCode.INFO_CORE_EXIST_NOT_LOAD, new Throwable("mAvailableCoreVersion=" + e + "; mSrcPackageName=" + f + "; getSharedTbsCoreVersion(ctx, mSrcPackageName) is " + getSharedTbsCoreVersion(context, f) + "; getHostCoreVersions is " + getHostCoreVersions(context)));
            d = null;
            e = 0;
            TbsLog.addLog(TbsLog.TBSLOG_CODE_SDK_CONFLICT_X5CORE, (String) null, new Object[0]);
            QbSdk.a(context, "TbsShareManager::isShareTbsCoreAvailableInner forceSysWebViewInner!");
            return false;
        } catch (Throwable th) {
            th.printStackTrace();
            TbsLog.addLog(TbsLog.TBSLOG_CODE_SDK_UNAVAIL_X5CORE, (String) null, new Object[0]);
            return false;
        }
    }

    public static boolean isThirdPartyApp(Context context) {
        try {
            if (a != null && a.equals(context.getApplicationContext())) {
                return b;
            }
            a = context.getApplicationContext();
            String packageName = a.getPackageName();
            for (String equals : getCoreProviderAppList()) {
                if (packageName.equals(equals)) {
                    b = false;
                    return false;
                }
            }
            b = true;
            return true;
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    static boolean j(Context context) {
        return b(context, true);
    }

    private static boolean k(Context context) {
        if (f == null) {
            return false;
        }
        if (e == getSharedTbsCoreVersion(context, f)) {
            return true;
        }
        return e == getCoreShareDecoupleCoreVersion(context, f);
    }

    private static boolean l(Context context) {
        if (QbSdk.getOnlyDownload()) {
            return false;
        }
        String[] coreProviderAppList = getCoreProviderAppList();
        for (String str : coreProviderAppList) {
            if (e > 0 && e == getSharedTbsCoreVersion(context, str)) {
                Context a2 = a(context, str);
                if (am.a().f(context)) {
                    d = am.a().b(context, a2).getAbsolutePath();
                    f = str;
                    return true;
                }
            }
        }
        for (String str2 : coreProviderAppList) {
            if (e > 0 && e == getCoreShareDecoupleCoreVersion(context, str2)) {
                Context a3 = a(context, str2);
                if (am.a().f(context)) {
                    d = am.a().c(context, a3).getAbsolutePath();
                    f = str2;
                    writeProperties(context, Integer.toString(e), f, d, Integer.toString(0));
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean m(Context context) {
        if (context == null) {
            return false;
        }
        writeProperties(context, Integer.toString(0), "", "", Integer.toString(0));
        return true;
    }

    /* JADX WARNING: Removed duplicated region for block: B:64:0x00d3 A[SYNTHETIC, Splitter:B:64:0x00d3] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static synchronized void n(android.content.Context r5) {
        /*
            java.lang.Class<com.tencent.smtt.sdk.TbsShareManager> r3 = com.tencent.smtt.sdk.TbsShareManager.class
            monitor-enter(r3)
            boolean r0 = k     // Catch:{ all -> 0x001d }
            if (r0 == 0) goto L_0x0009
        L_0x0007:
            monitor-exit(r3)
            return
        L_0x0009:
            r2 = 0
            java.lang.String r0 = "core_info"
            java.io.File r0 = b((android.content.Context) r5, (java.lang.String) r0)     // Catch:{ Throwable -> 0x00de, all -> 0x00cf }
            if (r0 != 0) goto L_0x0020
            if (r2 == 0) goto L_0x0007
            r2.close()     // Catch:{ Exception -> 0x0018 }
            goto L_0x0007
        L_0x0018:
            r0 = move-exception
            r0.printStackTrace()     // Catch:{ all -> 0x001d }
            goto L_0x0007
        L_0x001d:
            r0 = move-exception
            monitor-exit(r3)
            throw r0
        L_0x0020:
            java.io.FileInputStream r4 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x00de, all -> 0x00cf }
            r4.<init>(r0)     // Catch:{ Throwable -> 0x00de, all -> 0x00cf }
            java.io.BufferedInputStream r1 = new java.io.BufferedInputStream     // Catch:{ Throwable -> 0x00de, all -> 0x00cf }
            r1.<init>(r4)     // Catch:{ Throwable -> 0x00de, all -> 0x00cf }
            java.util.Properties r0 = new java.util.Properties     // Catch:{ Throwable -> 0x00be }
            r0.<init>()     // Catch:{ Throwable -> 0x00be }
            r0.load(r1)     // Catch:{ Throwable -> 0x00be }
            java.lang.String r2 = "core_version"
            java.lang.String r4 = ""
            java.lang.String r2 = r0.getProperty(r2, r4)     // Catch:{ Throwable -> 0x00be }
            java.lang.String r4 = ""
            boolean r4 = r4.equals(r2)     // Catch:{ Throwable -> 0x00be }
            if (r4 != 0) goto L_0x004d
            int r2 = java.lang.Integer.parseInt(r2)     // Catch:{ Throwable -> 0x00be }
            r4 = 0
            int r2 = java.lang.Math.max(r2, r4)     // Catch:{ Throwable -> 0x00be }
            e = r2     // Catch:{ Throwable -> 0x00be }
        L_0x004d:
            java.lang.String r2 = "core_packagename"
            java.lang.String r4 = ""
            java.lang.String r2 = r0.getProperty(r2, r4)     // Catch:{ Throwable -> 0x00be }
            java.lang.String r4 = ""
            boolean r4 = r4.equals(r2)     // Catch:{ Throwable -> 0x00be }
            if (r4 != 0) goto L_0x005f
            f = r2     // Catch:{ Throwable -> 0x00be }
        L_0x005f:
            java.lang.String r2 = f     // Catch:{ Throwable -> 0x00be }
            if (r2 == 0) goto L_0x0078
            android.content.Context r2 = a     // Catch:{ Throwable -> 0x00be }
            if (r2 == 0) goto L_0x0078
            java.lang.String r2 = f     // Catch:{ Throwable -> 0x00be }
            android.content.Context r4 = a     // Catch:{ Throwable -> 0x00be }
            java.lang.String r4 = r4.getPackageName()     // Catch:{ Throwable -> 0x00be }
            boolean r2 = r2.equals(r4)     // Catch:{ Throwable -> 0x00be }
            if (r2 == 0) goto L_0x00ba
            r2 = 1
            j = r2     // Catch:{ Throwable -> 0x00be }
        L_0x0078:
            java.lang.String r2 = "core_path"
            java.lang.String r4 = ""
            java.lang.String r2 = r0.getProperty(r2, r4)     // Catch:{ Throwable -> 0x00be }
            java.lang.String r4 = ""
            boolean r4 = r4.equals(r2)     // Catch:{ Throwable -> 0x00be }
            if (r4 != 0) goto L_0x008a
            d = r2     // Catch:{ Throwable -> 0x00be }
        L_0x008a:
            java.lang.String r2 = "app_version"
            java.lang.String r4 = ""
            java.lang.String r2 = r0.getProperty(r2, r4)     // Catch:{ Throwable -> 0x00be }
            java.lang.String r4 = ""
            boolean r4 = r4.equals(r2)     // Catch:{ Throwable -> 0x00be }
            if (r4 != 0) goto L_0x009c
            i = r2     // Catch:{ Throwable -> 0x00be }
        L_0x009c:
            java.lang.String r2 = "core_disabled"
            java.lang.String r4 = "false"
            java.lang.String r0 = r0.getProperty(r2, r4)     // Catch:{ Throwable -> 0x00be }
            boolean r0 = java.lang.Boolean.parseBoolean(r0)     // Catch:{ Throwable -> 0x00be }
            g = r0     // Catch:{ Throwable -> 0x00be }
            r0 = 1
            k = r0     // Catch:{ Throwable -> 0x00be }
            if (r1 == 0) goto L_0x0007
            r1.close()     // Catch:{ Exception -> 0x00b4 }
            goto L_0x0007
        L_0x00b4:
            r0 = move-exception
            r0.printStackTrace()     // Catch:{ all -> 0x001d }
            goto L_0x0007
        L_0x00ba:
            r2 = 0
            j = r2     // Catch:{ Throwable -> 0x00be }
            goto L_0x0078
        L_0x00be:
            r0 = move-exception
        L_0x00bf:
            r0.printStackTrace()     // Catch:{ all -> 0x00dc }
            if (r1 == 0) goto L_0x0007
            r1.close()     // Catch:{ Exception -> 0x00c9 }
            goto L_0x0007
        L_0x00c9:
            r0 = move-exception
            r0.printStackTrace()     // Catch:{ all -> 0x001d }
            goto L_0x0007
        L_0x00cf:
            r0 = move-exception
            r1 = r2
        L_0x00d1:
            if (r1 == 0) goto L_0x00d6
            r1.close()     // Catch:{ Exception -> 0x00d7 }
        L_0x00d6:
            throw r0     // Catch:{ all -> 0x001d }
        L_0x00d7:
            r1 = move-exception
            r1.printStackTrace()     // Catch:{ all -> 0x001d }
            goto L_0x00d6
        L_0x00dc:
            r0 = move-exception
            goto L_0x00d1
        L_0x00de:
            r0 = move-exception
            r1 = r2
            goto L_0x00bf
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.TbsShareManager.n(android.content.Context):void");
    }

    public static void setHostCorePathAppDefined(String str) {
        c = str;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:107:0x0311, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:108:0x0312, code lost:
        r0.printStackTrace();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x01fe, code lost:
        if (r4.equals(r9.getApplicationContext().getPackageName()) != false) goto L_0x022a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x0200, code lost:
        com.tencent.smtt.utils.TbsLog.i("TbsShareManager", "thirdAPP pre--> delete old core_share Directory:" + r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:?, code lost:
        com.tencent.smtt.utils.k.b(com.tencent.smtt.sdk.am.a().q(r9));
        com.tencent.smtt.utils.TbsLog.i("TbsShareManager", "thirdAPP success--> delete old core_share Directory");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:85:0x026d, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:0x026e, code lost:
        r0.printStackTrace();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:92:0x02a2, code lost:
        if (r4.equals(r9.getApplicationContext().getPackageName()) != false) goto L_0x02ce;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:93:0x02a4, code lost:
        com.tencent.smtt.utils.TbsLog.i("TbsShareManager", "thirdAPP pre--> delete old core_share Directory:" + r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:95:?, code lost:
        com.tencent.smtt.utils.k.b(com.tencent.smtt.sdk.am.a().q(r9));
        com.tencent.smtt.utils.TbsLog.i("TbsShareManager", "thirdAPP success--> delete old core_share Directory");
     */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:74:0x022a=Splitter:B:74:0x022a, B:96:0x02ce=Splitter:B:96:0x02ce} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized void writeCoreInfoForThirdPartyApp(android.content.Context r9, int r10, boolean r11) {
        /*
            r0 = 0
            java.lang.Class<com.tencent.smtt.sdk.TbsShareManager> r3 = com.tencent.smtt.sdk.TbsShareManager.class
            monitor-enter(r3)
            java.lang.String r1 = "TbsShareManager"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0058 }
            r2.<init>()     // Catch:{ all -> 0x0058 }
            java.lang.String r4 = "writeCoreInfoForThirdPartyApp coreVersion is "
            java.lang.StringBuilder r2 = r2.append(r4)     // Catch:{ all -> 0x0058 }
            java.lang.StringBuilder r2 = r2.append(r10)     // Catch:{ all -> 0x0058 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x0058 }
            com.tencent.smtt.utils.TbsLog.i(r1, r2)     // Catch:{ all -> 0x0058 }
            if (r10 != 0) goto L_0x002e
            m(r9)     // Catch:{ all -> 0x0058 }
            android.content.Context r0 = a     // Catch:{ all -> 0x0058 }
            com.tencent.smtt.sdk.TbsDownloadConfig r0 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r0)     // Catch:{ all -> 0x0058 }
            r1 = -401(0xfffffffffffffe6f, float:NaN)
            r0.setDownloadInterruptCode(r1)     // Catch:{ all -> 0x0058 }
        L_0x002c:
            monitor-exit(r3)
            return
        L_0x002e:
            int r1 = h(r9)     // Catch:{ all -> 0x0058 }
            java.lang.String r2 = "TbsShareManager"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0058 }
            r4.<init>()     // Catch:{ all -> 0x0058 }
            java.lang.String r5 = "writeCoreInfoForThirdPartyApp coreVersionFromConfig is "
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x0058 }
            java.lang.StringBuilder r4 = r4.append(r1)     // Catch:{ all -> 0x0058 }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x0058 }
            com.tencent.smtt.utils.TbsLog.i(r2, r4)     // Catch:{ all -> 0x0058 }
            if (r1 >= 0) goto L_0x005b
            android.content.Context r0 = a     // Catch:{ all -> 0x0058 }
            com.tencent.smtt.sdk.TbsDownloadConfig r0 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r0)     // Catch:{ all -> 0x0058 }
            r1 = -402(0xfffffffffffffe6e, float:NaN)
            r0.setDownloadInterruptCode(r1)     // Catch:{ all -> 0x0058 }
            goto L_0x002c
        L_0x0058:
            r0 = move-exception
            monitor-exit(r3)
            throw r0
        L_0x005b:
            if (r10 != r1) goto L_0x006c
            c(r9, r11)     // Catch:{ all -> 0x0058 }
            android.content.Context r0 = a     // Catch:{ all -> 0x0058 }
            com.tencent.smtt.sdk.TbsDownloadConfig r0 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r0)     // Catch:{ all -> 0x0058 }
            r1 = -403(0xfffffffffffffe6d, float:NaN)
            r0.setDownloadInterruptCode(r1)     // Catch:{ all -> 0x0058 }
            goto L_0x002c
        L_0x006c:
            if (r10 >= r1) goto L_0x007d
            m(r9)     // Catch:{ all -> 0x0058 }
            android.content.Context r0 = a     // Catch:{ all -> 0x0058 }
            com.tencent.smtt.sdk.TbsDownloadConfig r0 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r0)     // Catch:{ all -> 0x0058 }
            r1 = -404(0xfffffffffffffe6c, float:NaN)
            r0.setDownloadInterruptCode(r1)     // Catch:{ all -> 0x0058 }
            goto L_0x002c
        L_0x007d:
            com.tencent.smtt.sdk.am r1 = com.tencent.smtt.sdk.am.a()     // Catch:{ all -> 0x0058 }
            int r1 = r1.i(r9)     // Catch:{ all -> 0x0058 }
            java.lang.String r2 = "TbsShareManager"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0058 }
            r4.<init>()     // Catch:{ all -> 0x0058 }
            java.lang.String r5 = "writeCoreInfoForThirdPartyApp coreVersionFromCoreShare is "
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x0058 }
            java.lang.StringBuilder r4 = r4.append(r1)     // Catch:{ all -> 0x0058 }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x0058 }
            com.tencent.smtt.utils.TbsLog.i(r2, r4)     // Catch:{ all -> 0x0058 }
            if (r10 >= r1) goto L_0x00af
            m(r9)     // Catch:{ all -> 0x0058 }
            android.content.Context r0 = a     // Catch:{ all -> 0x0058 }
            com.tencent.smtt.sdk.TbsDownloadConfig r0 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r0)     // Catch:{ all -> 0x0058 }
            r1 = -404(0xfffffffffffffe6c, float:NaN)
            r0.setDownloadInterruptCode(r1)     // Catch:{ all -> 0x0058 }
            goto L_0x002c
        L_0x00af:
            boolean r1 = com.tencent.smtt.sdk.QbSdk.getOnlyDownload()     // Catch:{ all -> 0x0058 }
            if (r1 == 0) goto L_0x011c
            r1 = 1
            java.lang.String[] r1 = new java.lang.String[r1]     // Catch:{ all -> 0x0058 }
            r2 = 0
            android.content.Context r4 = r9.getApplicationContext()     // Catch:{ all -> 0x0058 }
            java.lang.String r4 = r4.getPackageName()     // Catch:{ all -> 0x0058 }
            r1[r2] = r4     // Catch:{ all -> 0x0058 }
            r2 = r1
        L_0x00c4:
            java.lang.String r1 = c     // Catch:{ all -> 0x0058 }
            if (r1 == 0) goto L_0x01c6
            com.tencent.smtt.sdk.am r1 = com.tencent.smtt.sdk.am.a()     // Catch:{ all -> 0x0058 }
            java.lang.String r4 = c     // Catch:{ all -> 0x0058 }
            int r1 = r1.a((java.lang.String) r4)     // Catch:{ all -> 0x0058 }
            if (r10 != r1) goto L_0x0132
            java.lang.String r0 = java.lang.Integer.toString(r10)     // Catch:{ all -> 0x0058 }
            java.lang.String r1 = "AppDefined"
            java.lang.String r2 = c     // Catch:{ all -> 0x0058 }
            r4 = 1
            java.lang.String r4 = java.lang.Integer.toString(r4)     // Catch:{ all -> 0x0058 }
            writeProperties(r9, r0, r1, r2, r4)     // Catch:{ all -> 0x0058 }
            java.lang.String r0 = "core_info"
            java.io.File r0 = b((android.content.Context) r9, (java.lang.String) r0)     // Catch:{ Throwable -> 0x0116 }
            boolean r1 = h     // Catch:{ Throwable -> 0x0116 }
            if (r1 != 0) goto L_0x002c
            if (r0 == 0) goto L_0x002c
            com.tencent.smtt.sdk.TbsLinuxToolsJni r1 = new com.tencent.smtt.sdk.TbsLinuxToolsJni     // Catch:{ Throwable -> 0x0116 }
            android.content.Context r2 = a     // Catch:{ Throwable -> 0x0116 }
            r1.<init>(r2)     // Catch:{ Throwable -> 0x0116 }
            java.lang.String r0 = r0.getAbsolutePath()     // Catch:{ Throwable -> 0x0116 }
            java.lang.String r2 = "644"
            r1.a(r0, r2)     // Catch:{ Throwable -> 0x0116 }
            com.tencent.smtt.sdk.am r0 = com.tencent.smtt.sdk.am.a()     // Catch:{ Throwable -> 0x0116 }
            java.io.File r0 = r0.r(r9)     // Catch:{ Throwable -> 0x0116 }
            java.lang.String r0 = r0.getAbsolutePath()     // Catch:{ Throwable -> 0x0116 }
            java.lang.String r2 = "755"
            r1.a(r0, r2)     // Catch:{ Throwable -> 0x0116 }
            r0 = 1
            h = r0     // Catch:{ Throwable -> 0x0116 }
            goto L_0x002c
        L_0x0116:
            r0 = move-exception
            r0.printStackTrace()     // Catch:{ all -> 0x0058 }
            goto L_0x002c
        L_0x011c:
            java.lang.String[] r1 = getCoreProviderAppList()     // Catch:{ all -> 0x0058 }
            if (r11 == 0) goto L_0x0316
            r1 = 1
            java.lang.String[] r1 = new java.lang.String[r1]     // Catch:{ all -> 0x0058 }
            r2 = 0
            android.content.Context r4 = r9.getApplicationContext()     // Catch:{ all -> 0x0058 }
            java.lang.String r4 = r4.getPackageName()     // Catch:{ all -> 0x0058 }
            r1[r2] = r4     // Catch:{ all -> 0x0058 }
            r2 = r1
            goto L_0x00c4
        L_0x0132:
            com.tencent.smtt.sdk.am r1 = com.tencent.smtt.sdk.am.a()     // Catch:{ all -> 0x0058 }
            java.lang.String r4 = c     // Catch:{ all -> 0x0058 }
            int r1 = r1.a((java.lang.String) r4)     // Catch:{ all -> 0x0058 }
            if (r10 <= r1) goto L_0x01c6
            int r4 = r2.length     // Catch:{ all -> 0x0058 }
            r1 = r0
        L_0x0140:
            if (r1 >= r4) goto L_0x01c6
            r5 = r2[r1]     // Catch:{ all -> 0x0058 }
            int r6 = getSharedTbsCoreVersion(r9, r5)     // Catch:{ all -> 0x0058 }
            if (r10 != r6) goto L_0x0167
            android.content.Context r5 = a((android.content.Context) r9, (java.lang.String) r5)     // Catch:{ all -> 0x0058 }
            com.tencent.smtt.sdk.am r6 = com.tencent.smtt.sdk.am.a()     // Catch:{ all -> 0x0058 }
            java.io.File r6 = r6.q(r5)     // Catch:{ all -> 0x0058 }
            java.lang.String r6 = r6.getAbsolutePath()     // Catch:{ all -> 0x0058 }
            com.tencent.smtt.utils.b.b(r9)     // Catch:{ all -> 0x0058 }
            com.tencent.smtt.sdk.am r7 = com.tencent.smtt.sdk.am.a()     // Catch:{ all -> 0x0058 }
            boolean r5 = r7.f(r5)     // Catch:{ all -> 0x0058 }
            if (r5 != 0) goto L_0x016a
        L_0x0167:
            int r1 = r1 + 1
            goto L_0x0140
        L_0x016a:
            java.io.File r0 = new java.io.File     // Catch:{ all -> 0x0058 }
            java.lang.String r1 = c     // Catch:{ all -> 0x0058 }
            r0.<init>(r1)     // Catch:{ all -> 0x0058 }
            java.io.File r1 = new java.io.File     // Catch:{ all -> 0x0058 }
            r1.<init>(r6)     // Catch:{ all -> 0x0058 }
            com.tencent.smtt.sdk.bb r2 = new com.tencent.smtt.sdk.bb     // Catch:{ all -> 0x0058 }
            r2.<init>()     // Catch:{ all -> 0x0058 }
            com.tencent.smtt.utils.k.a((java.io.File) r1, (java.io.File) r0, (java.io.FileFilter) r2)     // Catch:{ Throwable -> 0x01c0 }
            java.lang.String r0 = java.lang.Integer.toString(r10)     // Catch:{ Throwable -> 0x01c0 }
            java.lang.String r1 = "AppDefined"
            java.lang.String r2 = c     // Catch:{ Throwable -> 0x01c0 }
            r4 = 1
            java.lang.String r4 = java.lang.Integer.toString(r4)     // Catch:{ Throwable -> 0x01c0 }
            writeProperties(r9, r0, r1, r2, r4)     // Catch:{ Throwable -> 0x01c0 }
            java.lang.String r0 = "core_info"
            java.io.File r0 = b((android.content.Context) r9, (java.lang.String) r0)     // Catch:{ Throwable -> 0x01c0 }
            boolean r1 = h     // Catch:{ Throwable -> 0x01c0 }
            if (r1 != 0) goto L_0x002c
            if (r0 == 0) goto L_0x002c
            com.tencent.smtt.sdk.TbsLinuxToolsJni r1 = new com.tencent.smtt.sdk.TbsLinuxToolsJni     // Catch:{ Throwable -> 0x01c0 }
            android.content.Context r2 = a     // Catch:{ Throwable -> 0x01c0 }
            r1.<init>(r2)     // Catch:{ Throwable -> 0x01c0 }
            java.lang.String r0 = r0.getAbsolutePath()     // Catch:{ Throwable -> 0x01c0 }
            java.lang.String r2 = "644"
            r1.a(r0, r2)     // Catch:{ Throwable -> 0x01c0 }
            com.tencent.smtt.sdk.am r0 = com.tencent.smtt.sdk.am.a()     // Catch:{ Throwable -> 0x01c0 }
            java.io.File r0 = r0.r(r9)     // Catch:{ Throwable -> 0x01c0 }
            java.lang.String r0 = r0.getAbsolutePath()     // Catch:{ Throwable -> 0x01c0 }
            java.lang.String r2 = "755"
            r1.a(r0, r2)     // Catch:{ Throwable -> 0x01c0 }
            r0 = 1
            h = r0     // Catch:{ Throwable -> 0x01c0 }
            goto L_0x002c
        L_0x01c0:
            r0 = move-exception
            r0.printStackTrace()     // Catch:{ all -> 0x0058 }
            goto L_0x002c
        L_0x01c6:
            int r1 = r2.length     // Catch:{ all -> 0x0058 }
        L_0x01c7:
            if (r0 >= r1) goto L_0x002c
            r4 = r2[r0]     // Catch:{ all -> 0x0058 }
            int r5 = getSharedTbsCoreVersion(r9, r4)     // Catch:{ all -> 0x0058 }
            if (r10 != r5) goto L_0x0272
            android.content.Context r5 = a((android.content.Context) r9, (java.lang.String) r4)     // Catch:{ all -> 0x0058 }
            com.tencent.smtt.sdk.am r6 = com.tencent.smtt.sdk.am.a()     // Catch:{ all -> 0x0058 }
            java.io.File r6 = r6.q(r5)     // Catch:{ all -> 0x0058 }
            java.lang.String r6 = r6.getAbsolutePath()     // Catch:{ all -> 0x0058 }
            int r7 = com.tencent.smtt.utils.b.b(r9)     // Catch:{ all -> 0x0058 }
            com.tencent.smtt.sdk.am r8 = com.tencent.smtt.sdk.am.a()     // Catch:{ all -> 0x0058 }
            boolean r5 = r8.f(r5)     // Catch:{ all -> 0x0058 }
            if (r5 != 0) goto L_0x01f2
        L_0x01ef:
            int r0 = r0 + 1
            goto L_0x01c7
        L_0x01f2:
            android.content.Context r0 = r9.getApplicationContext()     // Catch:{ all -> 0x0058 }
            java.lang.String r0 = r0.getPackageName()     // Catch:{ all -> 0x0058 }
            boolean r0 = r4.equals(r0)     // Catch:{ all -> 0x0058 }
            if (r0 != 0) goto L_0x022a
            java.lang.String r0 = "TbsShareManager"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0058 }
            r1.<init>()     // Catch:{ all -> 0x0058 }
            java.lang.String r2 = "thirdAPP pre--> delete old core_share Directory:"
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ all -> 0x0058 }
            java.lang.StringBuilder r1 = r1.append(r10)     // Catch:{ all -> 0x0058 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0058 }
            com.tencent.smtt.utils.TbsLog.i(r0, r1)     // Catch:{ all -> 0x0058 }
            com.tencent.smtt.sdk.am r0 = com.tencent.smtt.sdk.am.a()     // Catch:{ all -> 0x0058 }
            java.io.File r0 = r0.q(r9)     // Catch:{ all -> 0x0058 }
            com.tencent.smtt.utils.k.b((java.io.File) r0)     // Catch:{ Throwable -> 0x026d }
            java.lang.String r0 = "TbsShareManager"
            java.lang.String r1 = "thirdAPP success--> delete old core_share Directory"
            com.tencent.smtt.utils.TbsLog.i(r0, r1)     // Catch:{ Throwable -> 0x026d }
        L_0x022a:
            java.lang.String r0 = java.lang.Integer.toString(r10)     // Catch:{ all -> 0x0058 }
            java.lang.String r1 = java.lang.Integer.toString(r7)     // Catch:{ all -> 0x0058 }
            writeProperties(r9, r0, r4, r6, r1)     // Catch:{ all -> 0x0058 }
            java.lang.String r0 = "core_info"
            java.io.File r0 = b((android.content.Context) r9, (java.lang.String) r0)     // Catch:{ Throwable -> 0x0267 }
            boolean r1 = h     // Catch:{ Throwable -> 0x0267 }
            if (r1 != 0) goto L_0x002c
            if (r0 == 0) goto L_0x002c
            com.tencent.smtt.sdk.TbsLinuxToolsJni r1 = new com.tencent.smtt.sdk.TbsLinuxToolsJni     // Catch:{ Throwable -> 0x0267 }
            android.content.Context r2 = a     // Catch:{ Throwable -> 0x0267 }
            r1.<init>(r2)     // Catch:{ Throwable -> 0x0267 }
            java.lang.String r0 = r0.getAbsolutePath()     // Catch:{ Throwable -> 0x0267 }
            java.lang.String r2 = "644"
            r1.a(r0, r2)     // Catch:{ Throwable -> 0x0267 }
            com.tencent.smtt.sdk.am r0 = com.tencent.smtt.sdk.am.a()     // Catch:{ Throwable -> 0x0267 }
            java.io.File r0 = r0.r(r9)     // Catch:{ Throwable -> 0x0267 }
            java.lang.String r0 = r0.getAbsolutePath()     // Catch:{ Throwable -> 0x0267 }
            java.lang.String r2 = "755"
            r1.a(r0, r2)     // Catch:{ Throwable -> 0x0267 }
            r0 = 1
            h = r0     // Catch:{ Throwable -> 0x0267 }
            goto L_0x002c
        L_0x0267:
            r0 = move-exception
            r0.printStackTrace()     // Catch:{ all -> 0x0058 }
            goto L_0x002c
        L_0x026d:
            r0 = move-exception
            r0.printStackTrace()     // Catch:{ all -> 0x0058 }
            goto L_0x022a
        L_0x0272:
            int r5 = getCoreShareDecoupleCoreVersion(r9, r4)     // Catch:{ all -> 0x0058 }
            if (r10 != r5) goto L_0x01ef
            android.content.Context r5 = a((android.content.Context) r9, (java.lang.String) r4)     // Catch:{ all -> 0x0058 }
            com.tencent.smtt.sdk.am r6 = com.tencent.smtt.sdk.am.a()     // Catch:{ all -> 0x0058 }
            java.io.File r6 = r6.p(r5)     // Catch:{ all -> 0x0058 }
            java.lang.String r6 = r6.getAbsolutePath()     // Catch:{ all -> 0x0058 }
            int r7 = com.tencent.smtt.utils.b.b(r9)     // Catch:{ all -> 0x0058 }
            com.tencent.smtt.sdk.am r8 = com.tencent.smtt.sdk.am.a()     // Catch:{ all -> 0x0058 }
            boolean r5 = r8.f(r5)     // Catch:{ all -> 0x0058 }
            if (r5 == 0) goto L_0x01ef
            android.content.Context r0 = r9.getApplicationContext()     // Catch:{ all -> 0x0058 }
            java.lang.String r0 = r0.getPackageName()     // Catch:{ all -> 0x0058 }
            boolean r0 = r4.equals(r0)     // Catch:{ all -> 0x0058 }
            if (r0 != 0) goto L_0x02ce
            java.lang.String r0 = "TbsShareManager"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0058 }
            r1.<init>()     // Catch:{ all -> 0x0058 }
            java.lang.String r2 = "thirdAPP pre--> delete old core_share Directory:"
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ all -> 0x0058 }
            java.lang.StringBuilder r1 = r1.append(r10)     // Catch:{ all -> 0x0058 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0058 }
            com.tencent.smtt.utils.TbsLog.i(r0, r1)     // Catch:{ all -> 0x0058 }
            com.tencent.smtt.sdk.am r0 = com.tencent.smtt.sdk.am.a()     // Catch:{ all -> 0x0058 }
            java.io.File r0 = r0.q(r9)     // Catch:{ all -> 0x0058 }
            com.tencent.smtt.utils.k.b((java.io.File) r0)     // Catch:{ Throwable -> 0x0311 }
            java.lang.String r0 = "TbsShareManager"
            java.lang.String r1 = "thirdAPP success--> delete old core_share Directory"
            com.tencent.smtt.utils.TbsLog.i(r0, r1)     // Catch:{ Throwable -> 0x0311 }
        L_0x02ce:
            java.lang.String r0 = java.lang.Integer.toString(r10)     // Catch:{ all -> 0x0058 }
            java.lang.String r1 = java.lang.Integer.toString(r7)     // Catch:{ all -> 0x0058 }
            writeProperties(r9, r0, r4, r6, r1)     // Catch:{ all -> 0x0058 }
            java.lang.String r0 = "core_info"
            java.io.File r0 = b((android.content.Context) r9, (java.lang.String) r0)     // Catch:{ Throwable -> 0x030b }
            boolean r1 = h     // Catch:{ Throwable -> 0x030b }
            if (r1 != 0) goto L_0x002c
            if (r0 == 0) goto L_0x002c
            com.tencent.smtt.sdk.TbsLinuxToolsJni r1 = new com.tencent.smtt.sdk.TbsLinuxToolsJni     // Catch:{ Throwable -> 0x030b }
            android.content.Context r2 = a     // Catch:{ Throwable -> 0x030b }
            r1.<init>(r2)     // Catch:{ Throwable -> 0x030b }
            java.lang.String r0 = r0.getAbsolutePath()     // Catch:{ Throwable -> 0x030b }
            java.lang.String r2 = "644"
            r1.a(r0, r2)     // Catch:{ Throwable -> 0x030b }
            com.tencent.smtt.sdk.am r0 = com.tencent.smtt.sdk.am.a()     // Catch:{ Throwable -> 0x030b }
            java.io.File r0 = r0.r(r9)     // Catch:{ Throwable -> 0x030b }
            java.lang.String r0 = r0.getAbsolutePath()     // Catch:{ Throwable -> 0x030b }
            java.lang.String r2 = "755"
            r1.a(r0, r2)     // Catch:{ Throwable -> 0x030b }
            r0 = 1
            h = r0     // Catch:{ Throwable -> 0x030b }
            goto L_0x002c
        L_0x030b:
            r0 = move-exception
            r0.printStackTrace()     // Catch:{ all -> 0x0058 }
            goto L_0x002c
        L_0x0311:
            r0 = move-exception
            r0.printStackTrace()     // Catch:{ all -> 0x0058 }
            goto L_0x02ce
        L_0x0316:
            r2 = r1
            goto L_0x00c4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.TbsShareManager.writeCoreInfoForThirdPartyApp(android.content.Context, int, boolean):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:41:0x00c2 A[SYNTHETIC, Splitter:B:41:0x00c2] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x00c7 A[SYNTHETIC, Splitter:B:44:0x00c7] */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00d2 A[SYNTHETIC, Splitter:B:50:0x00d2] */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00d7 A[SYNTHETIC, Splitter:B:53:0x00d7] */
    /* JADX WARNING: Removed duplicated region for block: B:78:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void writeProperties(android.content.Context r6, java.lang.String r7, java.lang.String r8, java.lang.String r9, java.lang.String r10) {
        /*
            r2 = 0
            r0 = 0
            java.lang.String r1 = "TbsShareManager"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "writeProperties coreVersion is "
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.StringBuilder r3 = r3.append(r7)
            java.lang.String r4 = " corePackageName is "
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.StringBuilder r3 = r3.append(r8)
            java.lang.String r4 = " corePath is "
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.StringBuilder r3 = r3.append(r9)
            java.lang.String r3 = r3.toString()
            com.tencent.smtt.utils.TbsLog.i(r1, r3)
            r1 = 0
            r3 = 0
            java.lang.String r4 = "core_info"
            java.io.File r4 = b((android.content.Context) r6, (java.lang.String) r4)     // Catch:{ Throwable -> 0x00fe, all -> 0x00cd }
            if (r4 != 0) goto L_0x004e
            android.content.Context r0 = a     // Catch:{ Throwable -> 0x00fe, all -> 0x00cd }
            com.tencent.smtt.sdk.TbsDownloadConfig r0 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r0)     // Catch:{ Throwable -> 0x00fe, all -> 0x00cd }
            r4 = -405(0xfffffffffffffe6b, float:NaN)
            r0.setDownloadInterruptCode(r4)     // Catch:{ Throwable -> 0x00fe, all -> 0x00cd }
            if (r2 == 0) goto L_0x0048
            r1.close()     // Catch:{ Exception -> 0x00ec }
        L_0x0048:
            if (r2 == 0) goto L_0x004d
            r3.close()     // Catch:{ Exception -> 0x00f2 }
        L_0x004d:
            return
        L_0x004e:
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x00fe, all -> 0x00cd }
            r1.<init>(r4)     // Catch:{ Throwable -> 0x00fe, all -> 0x00cd }
            java.io.BufferedInputStream r3 = new java.io.BufferedInputStream     // Catch:{ Throwable -> 0x00fe, all -> 0x00cd }
            r3.<init>(r1)     // Catch:{ Throwable -> 0x00fe, all -> 0x00cd }
            java.util.Properties r5 = new java.util.Properties     // Catch:{ Throwable -> 0x00bb, all -> 0x00f9 }
            r5.<init>()     // Catch:{ Throwable -> 0x00bb, all -> 0x00f9 }
            r5.load(r3)     // Catch:{ Throwable -> 0x00bb, all -> 0x00f9 }
            int r0 = java.lang.Integer.parseInt(r7)     // Catch:{ Exception -> 0x00db }
        L_0x0064:
            if (r0 == 0) goto L_0x00b0
            java.lang.String r0 = "core_version"
            r5.setProperty(r0, r7)     // Catch:{ Throwable -> 0x00bb, all -> 0x00f9 }
            java.lang.String r0 = "core_disabled"
            r1 = 0
            java.lang.String r1 = java.lang.String.valueOf(r1)     // Catch:{ Throwable -> 0x00bb, all -> 0x00f9 }
            r5.setProperty(r0, r1)     // Catch:{ Throwable -> 0x00bb, all -> 0x00f9 }
            java.lang.String r0 = "core_packagename"
            r5.setProperty(r0, r8)     // Catch:{ Throwable -> 0x00bb, all -> 0x00f9 }
            java.lang.String r0 = "core_path"
            r5.setProperty(r0, r9)     // Catch:{ Throwable -> 0x00bb, all -> 0x00f9 }
            java.lang.String r0 = "app_version"
            r5.setProperty(r0, r10)     // Catch:{ Throwable -> 0x00bb, all -> 0x00f9 }
        L_0x0084:
            java.io.FileOutputStream r0 = new java.io.FileOutputStream     // Catch:{ Throwable -> 0x00bb, all -> 0x00f9 }
            r0.<init>(r4)     // Catch:{ Throwable -> 0x00bb, all -> 0x00f9 }
            java.io.BufferedOutputStream r1 = new java.io.BufferedOutputStream     // Catch:{ Throwable -> 0x00bb, all -> 0x00f9 }
            r1.<init>(r0)     // Catch:{ Throwable -> 0x00bb, all -> 0x00f9 }
            r0 = 0
            r5.store(r1, r0)     // Catch:{ Throwable -> 0x0102 }
            r0 = 0
            k = r0     // Catch:{ Throwable -> 0x0102 }
            android.content.Context r0 = a     // Catch:{ Throwable -> 0x0102 }
            com.tencent.smtt.sdk.TbsDownloadConfig r0 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r0)     // Catch:{ Throwable -> 0x0102 }
            r2 = -406(0xfffffffffffffe6a, float:NaN)
            r0.setDownloadInterruptCode(r2)     // Catch:{ Throwable -> 0x0102 }
            if (r3 == 0) goto L_0x00a5
            r3.close()     // Catch:{ Exception -> 0x00f4 }
        L_0x00a5:
            if (r1 == 0) goto L_0x004d
            r1.close()     // Catch:{ Exception -> 0x00ab }
            goto L_0x004d
        L_0x00ab:
            r0 = move-exception
        L_0x00ac:
            r0.printStackTrace()
            goto L_0x004d
        L_0x00b0:
            java.lang.String r0 = "core_disabled"
            r1 = 1
            java.lang.String r1 = java.lang.String.valueOf(r1)     // Catch:{ Throwable -> 0x00bb, all -> 0x00f9 }
            r5.setProperty(r0, r1)     // Catch:{ Throwable -> 0x00bb, all -> 0x00f9 }
            goto L_0x0084
        L_0x00bb:
            r0 = move-exception
            r1 = r2
        L_0x00bd:
            r0.printStackTrace()     // Catch:{ all -> 0x00fc }
            if (r3 == 0) goto L_0x00c5
            r3.close()     // Catch:{ Exception -> 0x00e7 }
        L_0x00c5:
            if (r1 == 0) goto L_0x004d
            r1.close()     // Catch:{ Exception -> 0x00cb }
            goto L_0x004d
        L_0x00cb:
            r0 = move-exception
            goto L_0x00ac
        L_0x00cd:
            r0 = move-exception
            r1 = r2
            r3 = r2
        L_0x00d0:
            if (r3 == 0) goto L_0x00d5
            r3.close()     // Catch:{ Exception -> 0x00dd }
        L_0x00d5:
            if (r1 == 0) goto L_0x00da
            r1.close()     // Catch:{ Exception -> 0x00e2 }
        L_0x00da:
            throw r0
        L_0x00db:
            r1 = move-exception
            goto L_0x0064
        L_0x00dd:
            r2 = move-exception
            r2.printStackTrace()
            goto L_0x00d5
        L_0x00e2:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x00da
        L_0x00e7:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x00c5
        L_0x00ec:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0048
        L_0x00f2:
            r0 = move-exception
            goto L_0x00ac
        L_0x00f4:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x00a5
        L_0x00f9:
            r0 = move-exception
            r1 = r2
            goto L_0x00d0
        L_0x00fc:
            r0 = move-exception
            goto L_0x00d0
        L_0x00fe:
            r0 = move-exception
            r1 = r2
            r3 = r2
            goto L_0x00bd
        L_0x0102:
            r0 = move-exception
            goto L_0x00bd
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.TbsShareManager.writeProperties(android.content.Context, java.lang.String, java.lang.String, java.lang.String, java.lang.String):void");
    }
}
