package com.tencent.smtt.sdk;

import android.content.Context;
import android.os.Build;
import com.tencent.smtt.sdk.TbsListener;
import com.tencent.smtt.utils.TbsLog;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

class o {
    static boolean a = false;
    private static o c = null;
    private static int f = 0;
    private static int g = 0;
    private static int h = 3;
    private static String j = null;
    private bh b = null;
    private boolean d = false;
    private boolean e = false;
    private File i = null;

    private o() {
    }

    public static o a(boolean z) {
        if (c == null && z) {
            synchronized (o.class) {
                if (c == null) {
                    c = new o();
                }
            }
        }
        return c;
    }

    static void a(int i2) {
        f = i2;
    }

    private void b(int i2) {
        String valueOf = String.valueOf(i2);
        Properties properties = new Properties();
        properties.setProperty(j, valueOf);
        try {
            properties.store(new FileOutputStream(new File(this.i, "count.prop")), (String) null);
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
        } catch (IOException e3) {
            e3.printStackTrace();
        }
    }

    public static int d() {
        return f;
    }

    /* JADX WARNING: Removed duplicated region for block: B:31:0x005d A[SYNTHETIC, Splitter:B:31:0x005d] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int i() {
        /*
            r5 = this;
            r0 = 0
            r3 = 0
            java.io.File r1 = new java.io.File     // Catch:{ Exception -> 0x0049, all -> 0x0059 }
            java.io.File r2 = r5.i     // Catch:{ Exception -> 0x0049, all -> 0x0059 }
            java.lang.String r4 = "count.prop"
            r1.<init>(r2, r4)     // Catch:{ Exception -> 0x0049, all -> 0x0059 }
            boolean r2 = r1.exists()     // Catch:{ Exception -> 0x0049, all -> 0x0059 }
            if (r2 != 0) goto L_0x001c
            if (r3 == 0) goto L_0x0016
            r3.close()     // Catch:{ IOException -> 0x0017 }
        L_0x0016:
            return r0
        L_0x0017:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x0016
        L_0x001c:
            java.io.FileInputStream r4 = new java.io.FileInputStream     // Catch:{ Exception -> 0x0049, all -> 0x0059 }
            r4.<init>(r1)     // Catch:{ Exception -> 0x0049, all -> 0x0059 }
            java.io.BufferedInputStream r2 = new java.io.BufferedInputStream     // Catch:{ Exception -> 0x0049, all -> 0x0059 }
            r2.<init>(r4)     // Catch:{ Exception -> 0x0049, all -> 0x0059 }
            java.util.Properties r1 = new java.util.Properties     // Catch:{ Exception -> 0x0068 }
            r1.<init>()     // Catch:{ Exception -> 0x0068 }
            r1.load(r2)     // Catch:{ Exception -> 0x0068 }
            java.lang.String r3 = j     // Catch:{ Exception -> 0x0068 }
            java.lang.String r4 = "1"
            java.lang.String r1 = r1.getProperty(r3, r4)     // Catch:{ Exception -> 0x0068 }
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)     // Catch:{ Exception -> 0x0068 }
            int r0 = r1.intValue()     // Catch:{ Exception -> 0x0068 }
            if (r2 == 0) goto L_0x0016
            r2.close()     // Catch:{ IOException -> 0x0044 }
            goto L_0x0016
        L_0x0044:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x0016
        L_0x0049:
            r1 = move-exception
            r2 = r3
        L_0x004b:
            r1.printStackTrace()     // Catch:{ all -> 0x0066 }
            if (r2 == 0) goto L_0x0016
            r2.close()     // Catch:{ IOException -> 0x0054 }
            goto L_0x0016
        L_0x0054:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x0016
        L_0x0059:
            r0 = move-exception
            r2 = r3
        L_0x005b:
            if (r2 == 0) goto L_0x0060
            r2.close()     // Catch:{ IOException -> 0x0061 }
        L_0x0060:
            throw r0
        L_0x0061:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x0060
        L_0x0066:
            r0 = move-exception
            goto L_0x005b
        L_0x0068:
            r1 = move-exception
            goto L_0x004b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.o.i():int");
    }

    public bh a() {
        if (this.d) {
            return this.b;
        }
        return null;
    }

    public synchronized void a(Context context, boolean z, boolean z2) {
        Context applicationContext;
        File file;
        File file2;
        boolean z3 = true;
        synchronized (this) {
            TbsLog.addLog(999, (String) null, new Object[0]);
            TbsLog.initIfNeed(context);
            TbsLog.i("SDKEngine", "init -- context: " + context + ", isPreIniting: " + z2);
            g++;
            TbsCoreLoadStat.getInstance().a();
            am.a().b(context, g == 1);
            am.a().k(context);
            TbsShareManager.forceToLoadX5ForThirdApp(context, true);
            boolean a2 = QbSdk.a(context, z, z2);
            boolean z4 = Build.VERSION.SDK_INT >= 7;
            if (!(a2 && z4)) {
                String str = "can_load_x5=" + a2 + "; is_compatible=" + z4;
                TbsLog.e("SDKEngine", "SDKEngine.init canLoadTbs=false; failure: " + str);
                if (!QbSdk.a || !this.d) {
                    this.d = false;
                    TbsCoreLoadStat.getInstance().a(context, 405, new Throwable(str));
                }
                this.i = am.s(context);
                this.e = true;
            } else if (!this.d) {
                try {
                    if (TbsShareManager.isThirdPartyApp(context)) {
                        TbsLog.addLog(TbsLog.TBSLOG_CODE_SDK_THIRD_MODE, (String) null, new Object[0]);
                        if (TbsShareManager.j(context)) {
                            File file3 = new File(TbsShareManager.c(context));
                            file = am.a().q(context);
                            applicationContext = TbsShareManager.e(context);
                            if (file == null) {
                                this.d = false;
                                QbSdk.a(context, "SDKEngine::useSystemWebView by error_tbs_core_dexopt_dir null!");
                            } else {
                                file2 = file3;
                            }
                        } else {
                            this.d = false;
                            QbSdk.a(context, "SDKEngine::useSystemWebView by error_host_unavailable");
                        }
                    } else {
                        TbsLog.addLog(TbsLog.TBSLOG_CODE_SDK_SELF_MODE, (String) null, new Object[0]);
                        File q = am.a().q(context);
                        if (!(f == 25436 || f == 25437)) {
                            z3 = false;
                        }
                        applicationContext = z3 ? context.getApplicationContext() : context;
                        if (q == null) {
                            this.d = false;
                            QbSdk.a(context, "SDKEngine::useSystemWebView by tbs_core_share_dir null!");
                        } else {
                            file = q;
                            file2 = q;
                        }
                    }
                    String[] dexLoaderFileList = QbSdk.getDexLoaderFileList(context, applicationContext, file2.getAbsolutePath());
                    for (int i2 = 0; i2 < dexLoaderFileList.length; i2++) {
                    }
                    String hostCorePathAppDefined = TbsShareManager.getHostCorePathAppDefined() != null ? TbsShareManager.getHostCorePathAppDefined() : file.getAbsolutePath();
                    TbsLog.i("SDKEngine", "SDKEngine init optDir is " + hostCorePathAppDefined);
                    this.b = new bh(context, applicationContext, file2.getAbsolutePath(), hostCorePathAppDefined, dexLoaderFileList, QbSdk.d);
                    this.d = true;
                } catch (Throwable th) {
                    TbsLog.e("SDKEngine", "useSystemWebView by exception: " + th);
                    if (th == null) {
                        TbsCoreLoadStat.getInstance().a(context, TbsListener.ErrorCode.TEST_THROWABLE_IS_NULL);
                    } else {
                        TbsCoreLoadStat.getInstance().a(context, TbsListener.ErrorCode.TEST_THROWABLE_ISNOT_NULL, th);
                    }
                    this.d = false;
                    QbSdk.a(context, "SDKEngine::useSystemWebView by exception: " + th);
                }
                this.i = am.s(context);
                this.e = true;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void a(String str) {
        j = str;
    }

    public boolean b() {
        return this.d;
    }

    /* access modifiers changed from: package-private */
    public boolean b(boolean z) {
        a = z;
        return z;
    }

    /* access modifiers changed from: package-private */
    public bh c() {
        return this.b;
    }

    public String e() {
        return (this.b == null || QbSdk.a) ? "system webview get nothing..." : this.b.a();
    }

    /* access modifiers changed from: package-private */
    public boolean f() {
        if (a) {
            if (j == null) {
                return false;
            }
            int i2 = i();
            if (i2 == 0) {
                b(1);
            } else if (i2 + 1 > h) {
                return false;
            } else {
                b(i2 + 1);
            }
        }
        return a;
    }

    /* access modifiers changed from: package-private */
    public boolean g() {
        return this.e;
    }

    public boolean h() {
        return QbSdk.useSoftWare();
    }
}
