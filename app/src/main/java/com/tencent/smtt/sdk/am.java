package com.tencent.smtt.sdk;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import com.tencent.component.plugin.server.PluginConstant;
import com.tencent.imsdk.expansion.downloader.Constants;
import com.tencent.mna.KartinRet;
import com.tencent.smtt.sdk.TbsDownloadConfig;
import com.tencent.smtt.sdk.TbsListener;
import com.tencent.smtt.sdk.TbsLogReport;
import com.tencent.smtt.utils.TbsLog;
import com.tencent.smtt.utils.aa;
import com.tencent.smtt.utils.k;
import dalvik.system.DexClassLoader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.nio.channels.FileLock;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class am {
    public static ThreadLocal<Integer> a = new an();
    static boolean b = false;
    static final FileFilter c = new ao();
    private static am d = null;
    private static final Lock i = new ReentrantLock();
    private static final Lock j = new ReentrantLock();
    private static FileLock l = null;
    private static Handler m = null;
    private static final Long[][] n = {new Long[]{25413L, 11460320L}, new Long[]{25436L, 12009376L}, new Long[]{25437L, 11489180L}, new Long[]{25438L, 11489180L}, new Long[]{25439L, 12013472L}, new Long[]{25440L, 11489180L}, new Long[]{25442L, 11489180L}};
    private static int o = 0;
    private static boolean p = false;
    private int e = 0;
    private FileLock f;
    private FileOutputStream g;
    private boolean h = false;
    private boolean k = false;

    private am() {
        if (m == null) {
            m = new ap(this, al.a().getLooper());
        }
    }

    private void A(Context context) {
        TbsLog.i("TbsInstaller", "TbsInstaller--generateNewTbsCoreFromUnzip");
        if (!z(context)) {
            TbsLog.i("TbsInstaller", "get rename fileLock#4 ## failed!");
            return;
        }
        try {
            C(context);
            D(context);
            if (!TbsShareManager.isThirdPartyApp(context)) {
                TbsShareManager.a(context);
            }
            ai.a(context).a(0);
            ai.a(context).b(0);
            ai.a(context).d(0);
            ai.a(context).a("incrupdate_retry_num", 0);
            ai.a(context).b(0, 3);
            ai.a(context).a("");
            ai.a(context).c(-1);
            if (!TbsShareManager.isThirdPartyApp(context)) {
                int i2 = TbsDownloadConfig.getInstance(context).mPreferences.getInt(TbsDownloadConfig.TbsConfigKey.KEY_DECOUPLECOREVERSION, 0);
                if (i2 <= 0 || i2 == a().h(context) || i2 != a().i(context)) {
                    TbsLog.i("TbsInstaller", "TbsInstaller--generateNewTbsCoreFromUnzip #1 deCoupleCoreVersion is " + i2 + " getTbsCoreShareDecoupleCoreVersion is " + a().h(context) + " getTbsCoreInstalledVerInNolock is " + a().i(context));
                } else {
                    n(context);
                }
            }
            if (TbsShareManager.isThirdPartyApp(context)) {
                TbsShareManager.writeCoreInfoForThirdPartyApp(context, m(context), true);
            }
            a.set(0);
            o = 0;
        } catch (Throwable th) {
            th.printStackTrace();
            TbsLogReport.a(context).a((int) TbsListener.ErrorCode.RENAME_EXCEPTION, "exception when renameing from unzip:" + th.toString());
            TbsLog.e("TbsInstaller", "TbsInstaller--generateNewTbsCoreFromUnzip Exception", true);
        }
        y(context);
    }

    private void B(Context context) {
        TbsLog.i("TbsInstaller", "TbsInstaller--generateNewTbsCoreFromCopy");
        if (!z(context)) {
            TbsLog.i("TbsInstaller", "get rename fileLock#4 ## failed!");
            return;
        }
        try {
            C(context);
            E(context);
            TbsShareManager.a(context);
            ai.a(context).a(0, 3);
            if (!TbsShareManager.isThirdPartyApp(context)) {
                int i2 = TbsDownloadConfig.getInstance(context).mPreferences.getInt(TbsDownloadConfig.TbsConfigKey.KEY_DECOUPLECOREVERSION, 0);
                if (i2 <= 0 || i2 == a().h(context) || i2 != a().i(context)) {
                    TbsLog.i("TbsInstaller", "TbsInstaller--generateNewTbsCoreFromCopy #1 deCoupleCoreVersion is " + i2 + " getTbsCoreShareDecoupleCoreVersion is " + a().h(context) + " getTbsCoreInstalledVerInNolock is " + a().i(context));
                } else {
                    n(context);
                }
            }
            a.set(0);
        } catch (Exception e2) {
            e2.printStackTrace();
            TbsLogReport.a(context).a((int) TbsListener.ErrorCode.RENAME_EXCEPTION, "exception when renameing from copy:" + e2.toString());
        }
        y(context);
    }

    private void C(Context context) {
        TbsLog.i("TbsInstaller", "TbsInstaller--deleteOldCore");
        k.a(q(context), false);
    }

    private void D(Context context) {
        TbsLog.i("TbsInstaller", "TbsInstaller--renameShareDir");
        File t = t(context);
        File q = q(context);
        if (t != null && q != null) {
            t.renameTo(q);
            f(context, false);
        }
    }

    private void E(Context context) {
        TbsLog.i("TbsInstaller", "TbsInstaller--renameTbsCoreCopyDir");
        File v = v(context);
        File q = q(context);
        if (v != null && q != null) {
            v.renameTo(q);
            f(context, false);
        }
    }

    private void F(Context context) {
        TbsLog.i("TbsInstaller", "TbsInstaller--clearNewTbsCore");
        File t = t(context);
        if (t != null) {
            k.a(t, false);
        }
        ai.a(context).b(0, 5);
        ai.a(context).c(-1);
        QbSdk.a(context, "TbsInstaller::clearNewTbsCore forceSysWebViewInner!");
    }

    static synchronized am a() {
        am amVar;
        synchronized (am.class) {
            if (d == null) {
                synchronized (am.class) {
                    if (d == null) {
                        d = new am();
                    }
                }
            }
            amVar = d;
        }
        return amVar;
    }

    /* JADX WARNING: Removed duplicated region for block: B:27:0x0091 A[SYNTHETIC, Splitter:B:27:0x0091] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0096 A[SYNTHETIC, Splitter:B:30:0x0096] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00a9 A[SYNTHETIC, Splitter:B:39:0x00a9] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00ae A[SYNTHETIC, Splitter:B:42:0x00ae] */
    /* JADX WARNING: Removed duplicated region for block: B:60:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(int r7, java.lang.String r8, android.content.Context r9) {
        /*
            r6 = this;
            r4 = 1
            r2 = 0
            java.io.File r0 = new java.io.File
            r0.<init>(r8)
            r0.delete()
            java.lang.String r0 = "TbsInstaller"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r3 = "Local tbs apk("
            java.lang.StringBuilder r1 = r1.append(r3)
            java.lang.StringBuilder r1 = r1.append(r8)
            java.lang.String r3 = ") is deleted!"
            java.lang.StringBuilder r1 = r1.append(r3)
            java.lang.String r1 = r1.toString()
            com.tencent.smtt.utils.TbsLog.i(r0, r1, r4)
            java.lang.String r0 = "tbs"
            r1 = 0
            java.io.File r0 = r9.getDir(r0, r1)
            java.io.File r1 = new java.io.File
            java.lang.String r3 = "core_unzip_tmp"
            r1.<init>(r0, r3)
            if (r1 == 0) goto L_0x007e
            boolean r0 = r1.canRead()
            if (r0 == 0) goto L_0x007e
            java.io.File r0 = new java.io.File
            java.lang.String r3 = "tbs.conf"
            r0.<init>(r1, r3)
            java.util.Properties r4 = new java.util.Properties
            r4.<init>()
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x0089, all -> 0x00a4 }
            r1.<init>(r0)     // Catch:{ Throwable -> 0x0089, all -> 0x00a4 }
            java.io.BufferedInputStream r3 = new java.io.BufferedInputStream     // Catch:{ Throwable -> 0x0089, all -> 0x00a4 }
            r3.<init>(r1)     // Catch:{ Throwable -> 0x0089, all -> 0x00a4 }
            r4.load(r3)     // Catch:{ Throwable -> 0x00c1, all -> 0x00bc }
            java.io.FileOutputStream r5 = new java.io.FileOutputStream     // Catch:{ Throwable -> 0x00c1, all -> 0x00bc }
            r5.<init>(r0)     // Catch:{ Throwable -> 0x00c1, all -> 0x00bc }
            java.io.BufferedOutputStream r1 = new java.io.BufferedOutputStream     // Catch:{ Throwable -> 0x00c1, all -> 0x00bc }
            r1.<init>(r5)     // Catch:{ Throwable -> 0x00c1, all -> 0x00bc }
            java.lang.String r0 = "tbs_local_installation"
            java.lang.String r2 = "true"
            r4.setProperty(r0, r2)     // Catch:{ Throwable -> 0x00c4 }
            r0 = 0
            r4.store(r1, r0)     // Catch:{ Throwable -> 0x00c4 }
            java.lang.String r0 = "TbsInstaller"
            java.lang.String r2 = "TBS_LOCAL_INSTALLATION is set!"
            r4 = 1
            com.tencent.smtt.utils.TbsLog.i(r0, r2, r4)     // Catch:{ Throwable -> 0x00c4 }
            if (r1 == 0) goto L_0x0079
            r1.close()     // Catch:{ IOException -> 0x007f }
        L_0x0079:
            if (r3 == 0) goto L_0x007e
            r3.close()     // Catch:{ IOException -> 0x0084 }
        L_0x007e:
            return
        L_0x007f:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0079
        L_0x0084:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x007e
        L_0x0089:
            r0 = move-exception
            r1 = r2
            r3 = r2
        L_0x008c:
            r0.printStackTrace()     // Catch:{ all -> 0x00bf }
            if (r1 == 0) goto L_0x0094
            r1.close()     // Catch:{ IOException -> 0x009f }
        L_0x0094:
            if (r3 == 0) goto L_0x007e
            r3.close()     // Catch:{ IOException -> 0x009a }
            goto L_0x007e
        L_0x009a:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x007e
        L_0x009f:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0094
        L_0x00a4:
            r0 = move-exception
            r1 = r2
            r3 = r2
        L_0x00a7:
            if (r1 == 0) goto L_0x00ac
            r1.close()     // Catch:{ IOException -> 0x00b2 }
        L_0x00ac:
            if (r3 == 0) goto L_0x00b1
            r3.close()     // Catch:{ IOException -> 0x00b7 }
        L_0x00b1:
            throw r0
        L_0x00b2:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x00ac
        L_0x00b7:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x00b1
        L_0x00bc:
            r0 = move-exception
            r1 = r2
            goto L_0x00a7
        L_0x00bf:
            r0 = move-exception
            goto L_0x00a7
        L_0x00c1:
            r0 = move-exception
            r1 = r2
            goto L_0x008c
        L_0x00c4:
            r0 = move-exception
            goto L_0x008c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.am.a(int, java.lang.String, android.content.Context):void");
    }

    public static void a(Context context) {
        if (x(context)) {
            return;
        }
        if (d(context, "core_unzip_tmp")) {
            TbsCoreLoadStat.getInstance().a(context, 417, new Throwable("TMP_TBS_UNZIP_FOLDER_NAME"));
            TbsLog.e("TbsInstaller", "TbsInstaller-UploadIfTempCoreExistConfError INFO_TEMP_CORE_EXIST_CONF_ERROR TMP_TBS_UNZIP_FOLDER_NAME");
        } else if (d(context, "core_share_backup_tmp")) {
            TbsCoreLoadStat.getInstance().a(context, 417, new Throwable("TMP_BACKUP_TBSCORE_FOLDER_NAME"));
            TbsLog.e("TbsInstaller", "TbsInstaller-UploadIfTempCoreExistConfError INFO_TEMP_CORE_EXIST_CONF_ERROR TMP_BACKUP_TBSCORE_FOLDER_NAME");
        } else if (d(context, "core_copy_tmp")) {
            TbsCoreLoadStat.getInstance().a(context, 417, new Throwable("TMP_TBS_COPY_FOLDER_NAME"));
            TbsLog.e("TbsInstaller", "TbsInstaller-UploadIfTempCoreExistConfError INFO_TEMP_CORE_EXIST_CONF_ERROR TMP_TBS_COPY_FOLDER_NAME");
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:100:0x0306 A[SYNTHETIC, Splitter:B:100:0x0306] */
    /* JADX WARNING: Removed duplicated region for block: B:121:0x035c A[SYNTHETIC, Splitter:B:121:0x035c] */
    /* JADX WARNING: Removed duplicated region for block: B:126:0x0364 A[SYNTHETIC, Splitter:B:126:0x0364] */
    /* JADX WARNING: Removed duplicated region for block: B:152:0x0439 A[ADDED_TO_REGION, Catch:{ Exception -> 0x0368 }] */
    /* JADX WARNING: Removed duplicated region for block: B:163:0x0485 A[Catch:{ Exception -> 0x0368 }] */
    /* JADX WARNING: Removed duplicated region for block: B:167:0x0499 A[Catch:{ Exception -> 0x0368 }] */
    /* JADX WARNING: Removed duplicated region for block: B:170:0x04cb A[Catch:{ Exception -> 0x0368 }] */
    /* JADX WARNING: Removed duplicated region for block: B:176:0x04f9  */
    /* JADX WARNING: Removed duplicated region for block: B:177:0x04fc A[SYNTHETIC, Splitter:B:177:0x04fc] */
    /* JADX WARNING: Removed duplicated region for block: B:179:0x0508 A[Catch:{ Exception -> 0x0368 }] */
    /* JADX WARNING: Removed duplicated region for block: B:96:0x0300 A[SYNTHETIC, Splitter:B:96:0x0300] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:171:0x04d2=Splitter:B:171:0x04d2, B:128:0x0367=Splitter:B:128:0x0367, B:173:0x04ea=Splitter:B:173:0x04ea} */
    @android.annotation.TargetApi(11)
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(android.content.Context r13, android.content.Context r14, int r15) {
        /*
            r12 = this;
            r2 = -1
            r5 = 1
            r6 = 0
            com.tencent.smtt.sdk.TbsDownloadConfig r0 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r13)
            r1 = -524(0xfffffffffffffdf4, float:NaN)
            r0.setInstallInterruptCode(r1)
            boolean r0 = r12.c(r14)
            if (r0 == 0) goto L_0x0013
        L_0x0012:
            return
        L_0x0013:
            java.lang.String r0 = "TbsInstaller"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r3 = "TbsInstaller-copyTbsCoreInThread start!  tbsCoreTargetVer is "
            java.lang.StringBuilder r1 = r1.append(r3)
            java.lang.StringBuilder r1 = r1.append(r15)
            java.lang.String r1 = r1.toString()
            com.tencent.smtt.utils.TbsLog.i(r0, r1)
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 11
            if (r0 < r1) goto L_0x0068
            java.lang.String r0 = "tbs_preloadx5_check_cfg_file"
            r1 = 4
            android.content.SharedPreferences r0 = r14.getSharedPreferences(r0, r1)
        L_0x0038:
            java.lang.String r1 = "tbs_precheck_disable_version"
            int r0 = r0.getInt(r1, r2)
            if (r0 != r15) goto L_0x006f
            java.lang.String r0 = "TbsInstaller"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "TbsInstaller-copyTbsCoreInThread -- version:"
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r15)
            java.lang.String r2 = " is disabled by preload_x5_check!"
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r1 = r1.toString()
            com.tencent.smtt.utils.TbsLog.e(r0, r1)
            com.tencent.smtt.sdk.TbsDownloadConfig r0 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r13)
            r1 = -525(0xfffffffffffffdf3, float:NaN)
            r0.setInstallInterruptCode(r1)
            goto L_0x0012
        L_0x0068:
            java.lang.String r0 = "tbs_preloadx5_check_cfg_file"
            android.content.SharedPreferences r0 = r14.getSharedPreferences(r0, r6)
            goto L_0x0038
        L_0x006f:
            boolean r0 = r12.w(r14)
            if (r0 != 0) goto L_0x007f
            com.tencent.smtt.sdk.TbsDownloadConfig r0 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r13)
            r1 = -526(0xfffffffffffffdf2, float:NaN)
            r0.setInstallInterruptCode(r1)
            goto L_0x0012
        L_0x007f:
            java.util.concurrent.locks.Lock r0 = j
            boolean r0 = r0.tryLock()
            java.lang.String r1 = "TbsInstaller"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "TbsInstaller-copyTbsCoreInThread #1 locked is "
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.StringBuilder r3 = r3.append(r0)
            java.lang.String r3 = r3.toString()
            com.tencent.smtt.utils.TbsLog.i(r1, r3)
            if (r0 == 0) goto L_0x05b1
            java.util.concurrent.locks.Lock r0 = i
            r0.lock()
            r1 = 0
            com.tencent.smtt.sdk.ai r0 = com.tencent.smtt.sdk.ai.a((android.content.Context) r14)     // Catch:{ Exception -> 0x05d1 }
            java.lang.String r3 = "copy_core_ver"
            int r3 = r0.c((java.lang.String) r3)     // Catch:{ Exception -> 0x05d1 }
            com.tencent.smtt.sdk.ai r0 = com.tencent.smtt.sdk.ai.a((android.content.Context) r14)     // Catch:{ Exception -> 0x05d1 }
            java.lang.String r4 = "copy_status"
            int r0 = r0.b((java.lang.String) r4)     // Catch:{ Exception -> 0x05d1 }
            if (r3 != r15) goto L_0x00da
            com.tencent.smtt.sdk.TbsListener r0 = com.tencent.smtt.sdk.QbSdk.l     // Catch:{ Exception -> 0x05d1 }
            r2 = 220(0xdc, float:3.08E-43)
            r0.onInstallFinish(r2)     // Catch:{ Exception -> 0x05d1 }
            com.tencent.smtt.sdk.TbsDownloadConfig r0 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r13)     // Catch:{ Exception -> 0x05d1 }
            r2 = -528(0xfffffffffffffdf0, float:NaN)
            r0.setInstallInterruptCode(r2)     // Catch:{ Exception -> 0x05d1 }
            java.util.concurrent.locks.Lock r0 = i
            r0.unlock()
            java.util.concurrent.locks.Lock r0 = j
            r0.unlock()
        L_0x00d5:
            r12.b()
            goto L_0x0012
        L_0x00da:
            int r4 = r12.i(r14)     // Catch:{ Exception -> 0x05d1 }
            java.lang.String r7 = "TbsInstaller"
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x05d1 }
            r8.<init>()     // Catch:{ Exception -> 0x05d1 }
            java.lang.String r9 = "TbsInstaller-copyTbsCoreInThread tbsCoreInstalledVer="
            java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ Exception -> 0x05d1 }
            java.lang.StringBuilder r8 = r8.append(r4)     // Catch:{ Exception -> 0x05d1 }
            java.lang.String r8 = r8.toString()     // Catch:{ Exception -> 0x05d1 }
            com.tencent.smtt.utils.TbsLog.i(r7, r8)     // Catch:{ Exception -> 0x05d1 }
            if (r4 != r15) goto L_0x012b
            com.tencent.smtt.sdk.TbsListener r0 = com.tencent.smtt.sdk.QbSdk.l     // Catch:{ Exception -> 0x05d1 }
            r2 = 220(0xdc, float:3.08E-43)
            r0.onInstallFinish(r2)     // Catch:{ Exception -> 0x05d1 }
            com.tencent.smtt.sdk.TbsDownloadConfig r0 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r13)     // Catch:{ Exception -> 0x05d1 }
            r2 = -528(0xfffffffffffffdf0, float:NaN)
            r0.setInstallInterruptCode(r2)     // Catch:{ Exception -> 0x05d1 }
            java.lang.String r0 = "TbsInstaller"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x05d1 }
            r2.<init>()     // Catch:{ Exception -> 0x05d1 }
            java.lang.String r3 = "TbsInstaller-copyTbsCoreInThread return have same version is "
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Exception -> 0x05d1 }
            java.lang.StringBuilder r2 = r2.append(r4)     // Catch:{ Exception -> 0x05d1 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x05d1 }
            com.tencent.smtt.utils.TbsLog.i(r0, r2)     // Catch:{ Exception -> 0x05d1 }
            java.util.concurrent.locks.Lock r0 = i
            r0.unlock()
            java.util.concurrent.locks.Lock r0 = j
            r0.unlock()
            goto L_0x00d5
        L_0x012b:
            com.tencent.smtt.sdk.ai r7 = com.tencent.smtt.sdk.ai.a((android.content.Context) r14)     // Catch:{ Exception -> 0x05d1 }
            int r7 = r7.b()     // Catch:{ Exception -> 0x05d1 }
            if (r7 <= 0) goto L_0x0137
            if (r15 > r7) goto L_0x013b
        L_0x0137:
            if (r3 <= 0) goto L_0x013e
            if (r15 <= r3) goto L_0x013e
        L_0x013b:
            r12.o(r14)     // Catch:{ Exception -> 0x05d1 }
        L_0x013e:
            r3 = 3
            if (r0 != r3) goto L_0x0156
            if (r4 <= 0) goto L_0x0156
            if (r15 > r4) goto L_0x014a
            r3 = 88888888(0x54c5638, float:9.60787E-36)
            if (r15 != r3) goto L_0x0156
        L_0x014a:
            r12.o(r14)     // Catch:{ Exception -> 0x05d1 }
            java.lang.String r0 = "TbsInstaller"
            java.lang.String r3 = "TbsInstaller-copyTbsCoreInThread -- update TBS....."
            r4 = 1
            com.tencent.smtt.utils.TbsLog.i(r0, r3, r4)     // Catch:{ Exception -> 0x05d1 }
            r0 = r2
        L_0x0156:
            boolean r2 = com.tencent.smtt.utils.k.b((android.content.Context) r14)     // Catch:{ Exception -> 0x05d1 }
            if (r2 != 0) goto L_0x01a3
            long r2 = com.tencent.smtt.utils.aa.a()     // Catch:{ Exception -> 0x05d1 }
            com.tencent.smtt.sdk.TbsDownloadConfig r0 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r14)     // Catch:{ Exception -> 0x05d1 }
            long r4 = r0.getDownloadMinFreeSpace()     // Catch:{ Exception -> 0x05d1 }
            com.tencent.smtt.sdk.TbsDownloadConfig r0 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r13)     // Catch:{ Exception -> 0x05d1 }
            r6 = -529(0xfffffffffffffdef, float:NaN)
            r0.setInstallInterruptCode(r6)     // Catch:{ Exception -> 0x05d1 }
            com.tencent.smtt.sdk.TbsLogReport r0 = com.tencent.smtt.sdk.TbsLogReport.a((android.content.Context) r14)     // Catch:{ Exception -> 0x05d1 }
            r6 = 210(0xd2, float:2.94E-43)
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x05d1 }
            r7.<init>()     // Catch:{ Exception -> 0x05d1 }
            java.lang.String r8 = "rom is not enough when copying tbs core! curAvailROM="
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ Exception -> 0x05d1 }
            java.lang.StringBuilder r2 = r7.append(r2)     // Catch:{ Exception -> 0x05d1 }
            java.lang.String r3 = ",minReqRom="
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Exception -> 0x05d1 }
            java.lang.StringBuilder r2 = r2.append(r4)     // Catch:{ Exception -> 0x05d1 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x05d1 }
            r0.a((int) r6, (java.lang.String) r2)     // Catch:{ Exception -> 0x05d1 }
            java.util.concurrent.locks.Lock r0 = i
            r0.unlock()
            java.util.concurrent.locks.Lock r0 = j
            r0.unlock()
            goto L_0x00d5
        L_0x01a3:
            if (r0 <= 0) goto L_0x01c0
            boolean r2 = com.tencent.smtt.sdk.TbsShareManager.isThirdPartyApp(r14)     // Catch:{ Exception -> 0x05d1 }
            if (r2 != 0) goto L_0x01ef
            com.tencent.smtt.sdk.TbsDownloadConfig r2 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r14)     // Catch:{ Exception -> 0x05d1 }
            android.content.SharedPreferences r2 = r2.mPreferences     // Catch:{ Exception -> 0x05d1 }
            java.lang.String r3 = "tbs_downloaddecouplecore"
            r4 = 0
            int r2 = r2.getInt(r3, r4)     // Catch:{ Exception -> 0x05d1 }
            if (r2 != r5) goto L_0x01ef
            int r2 = r12.h(r14)     // Catch:{ Exception -> 0x05d1 }
            if (r15 == r2) goto L_0x01ef
        L_0x01c0:
            if (r0 != 0) goto L_0x0222
            com.tencent.smtt.sdk.ai r0 = com.tencent.smtt.sdk.ai.a((android.content.Context) r14)     // Catch:{ Exception -> 0x05d1 }
            java.lang.String r2 = "copy_retry_num"
            int r0 = r0.c((java.lang.String) r2)     // Catch:{ Exception -> 0x05d1 }
            r2 = 2
            if (r0 <= r2) goto L_0x0217
            com.tencent.smtt.sdk.TbsLogReport r0 = com.tencent.smtt.sdk.TbsLogReport.a((android.content.Context) r14)     // Catch:{ Exception -> 0x05d1 }
            r2 = 211(0xd3, float:2.96E-43)
            java.lang.String r3 = "exceed copy retry num!"
            r0.a((int) r2, (java.lang.String) r3)     // Catch:{ Exception -> 0x05d1 }
            com.tencent.smtt.sdk.TbsDownloadConfig r0 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r13)     // Catch:{ Exception -> 0x05d1 }
            r2 = -530(0xfffffffffffffdee, float:NaN)
            r0.setInstallInterruptCode(r2)     // Catch:{ Exception -> 0x05d1 }
            java.util.concurrent.locks.Lock r0 = i
            r0.unlock()
            java.util.concurrent.locks.Lock r0 = j
            r0.unlock()
            goto L_0x00d5
        L_0x01ef:
            java.lang.String r0 = "TbsInstaller"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x05d1 }
            r2.<init>()     // Catch:{ Exception -> 0x05d1 }
            java.lang.String r3 = "TbsInstaller-copyTbsCoreInThread return have copied is "
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Exception -> 0x05d1 }
            int r3 = r12.h(r14)     // Catch:{ Exception -> 0x05d1 }
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Exception -> 0x05d1 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x05d1 }
            com.tencent.smtt.utils.TbsLog.i(r0, r2)     // Catch:{ Exception -> 0x05d1 }
            java.util.concurrent.locks.Lock r0 = i
            r0.unlock()
            java.util.concurrent.locks.Lock r0 = j
            r0.unlock()
            goto L_0x00d5
        L_0x0217:
            com.tencent.smtt.sdk.ai r2 = com.tencent.smtt.sdk.ai.a((android.content.Context) r14)     // Catch:{ Exception -> 0x05d1 }
            java.lang.String r3 = "copy_retry_num"
            int r0 = r0 + 1
            r2.a((java.lang.String) r3, (int) r0)     // Catch:{ Exception -> 0x05d1 }
        L_0x0222:
            java.io.File r0 = r12.q(r13)     // Catch:{ Exception -> 0x05d1 }
            boolean r2 = com.tencent.smtt.sdk.TbsShareManager.isThirdPartyApp(r14)     // Catch:{ Exception -> 0x05d1 }
            if (r2 != 0) goto L_0x02d2
            com.tencent.smtt.sdk.TbsDownloadConfig r2 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r14)     // Catch:{ Exception -> 0x05d1 }
            android.content.SharedPreferences r2 = r2.mPreferences     // Catch:{ Exception -> 0x05d1 }
            java.lang.String r3 = "tbs_downloaddecouplecore"
            r4 = 0
            int r2 = r2.getInt(r3, r4)     // Catch:{ Exception -> 0x05d1 }
            if (r2 != r5) goto L_0x02cc
            java.io.File r4 = r12.p(r14)     // Catch:{ Exception -> 0x05d1 }
        L_0x023f:
            if (r0 == 0) goto L_0x0556
            if (r4 == 0) goto L_0x0556
            com.tencent.smtt.sdk.ai r1 = com.tencent.smtt.sdk.ai.a((android.content.Context) r14)     // Catch:{ Exception -> 0x0368 }
            r2 = 0
            r1.a((int) r15, (int) r2)     // Catch:{ Exception -> 0x0368 }
            com.tencent.smtt.utils.z r1 = new com.tencent.smtt.utils.z     // Catch:{ Exception -> 0x0368 }
            r1.<init>()     // Catch:{ Exception -> 0x0368 }
            r1.a(r0)     // Catch:{ Exception -> 0x0368 }
            long r2 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0368 }
            com.tencent.smtt.sdk.TbsDownloadConfig r7 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r13)     // Catch:{ Exception -> 0x0368 }
            r8 = -551(0xfffffffffffffdd9, float:NaN)
            r7.setInstallInterruptCode(r8)     // Catch:{ Exception -> 0x0368 }
            java.io.FileFilter r7 = c     // Catch:{ Exception -> 0x0368 }
            boolean r7 = com.tencent.smtt.utils.k.a((java.io.File) r0, (java.io.File) r4, (java.io.FileFilter) r7)     // Catch:{ Exception -> 0x0368 }
            com.tencent.smtt.sdk.TbsDownloadConfig r8 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r14)     // Catch:{ Exception -> 0x0368 }
            android.content.SharedPreferences r8 = r8.mPreferences     // Catch:{ Exception -> 0x0368 }
            java.lang.String r9 = "tbs_downloaddecouplecore"
            r10 = 0
            int r8 = r8.getInt(r9, r10)     // Catch:{ Exception -> 0x0368 }
            if (r8 != r5) goto L_0x0278
            com.tencent.smtt.sdk.TbsShareManager.b(r14)     // Catch:{ Exception -> 0x0368 }
        L_0x0278:
            java.lang.String r8 = "TbsInstaller"
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0368 }
            r9.<init>()     // Catch:{ Exception -> 0x0368 }
            java.lang.String r10 = "TbsInstaller-copyTbsCoreInThread time="
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ Exception -> 0x0368 }
            long r10 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0368 }
            long r2 = r10 - r2
            java.lang.StringBuilder r2 = r9.append(r2)     // Catch:{ Exception -> 0x0368 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x0368 }
            com.tencent.smtt.utils.TbsLog.i(r8, r2)     // Catch:{ Exception -> 0x0368 }
            if (r7 == 0) goto L_0x052e
            r1.b(r0)     // Catch:{ Exception -> 0x0368 }
            boolean r0 = r1.a()     // Catch:{ Exception -> 0x0368 }
            if (r0 != 0) goto L_0x02d8
            java.lang.String r0 = "TbsInstaller"
            java.lang.String r1 = "TbsInstaller-copyTbsCoreInThread copy-verify fail!"
            com.tencent.smtt.utils.TbsLog.i(r0, r1)     // Catch:{ Exception -> 0x0368 }
            r0 = 1
            com.tencent.smtt.utils.k.a((java.io.File) r4, (boolean) r0)     // Catch:{ Exception -> 0x0368 }
            com.tencent.smtt.sdk.TbsLogReport r0 = com.tencent.smtt.sdk.TbsLogReport.a((android.content.Context) r14)     // Catch:{ Exception -> 0x0368 }
            r1 = 213(0xd5, float:2.98E-43)
            java.lang.String r2 = "TbsCopy-Verify fail after copying tbs core!"
            r0.a((int) r1, (java.lang.String) r2)     // Catch:{ Exception -> 0x0368 }
            com.tencent.smtt.sdk.TbsDownloadConfig r0 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r13)     // Catch:{ Exception -> 0x0368 }
            r1 = -531(0xfffffffffffffded, float:NaN)
            r0.setInstallInterruptCode(r1)     // Catch:{ Exception -> 0x0368 }
            java.util.concurrent.locks.Lock r0 = i
            r0.unlock()
            java.util.concurrent.locks.Lock r0 = j
            r0.unlock()
            goto L_0x00d5
        L_0x02cc:
            java.io.File r4 = r12.v(r14)     // Catch:{ Exception -> 0x05d1 }
            goto L_0x023f
        L_0x02d2:
            java.io.File r4 = r12.v(r14)     // Catch:{ Exception -> 0x05d1 }
            goto L_0x023f
        L_0x02d8:
            r3 = 0
            r2 = 0
            java.io.File r0 = new java.io.File     // Catch:{ Exception -> 0x0355 }
            java.lang.String r1 = "1"
            r0.<init>(r4, r1)     // Catch:{ Exception -> 0x0355 }
            java.util.Properties r1 = new java.util.Properties     // Catch:{ Exception -> 0x0355 }
            r1.<init>()     // Catch:{ Exception -> 0x0355 }
            if (r0 == 0) goto L_0x0352
            boolean r2 = r0.exists()     // Catch:{ Exception -> 0x05d8 }
            if (r2 == 0) goto L_0x0352
            if (r1 == 0) goto L_0x0352
            java.io.FileInputStream r7 = new java.io.FileInputStream     // Catch:{ Exception -> 0x05d8 }
            r7.<init>(r0)     // Catch:{ Exception -> 0x05d8 }
            java.io.BufferedInputStream r2 = new java.io.BufferedInputStream     // Catch:{ Exception -> 0x05d8 }
            r2.<init>(r7)     // Catch:{ Exception -> 0x05d8 }
            r1.load(r2)     // Catch:{ Exception -> 0x05db, all -> 0x05d4 }
            r0 = r5
        L_0x02fe:
            if (r2 == 0) goto L_0x0303
            r2.close()     // Catch:{ IOException -> 0x05cb }
        L_0x0303:
            r2 = r0
        L_0x0304:
            if (r2 == 0) goto L_0x041f
            java.io.File[] r3 = r4.listFiles()     // Catch:{ Exception -> 0x0368 }
            com.tencent.smtt.sdk.TbsDownloadConfig r0 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r13)     // Catch:{ Exception -> 0x0368 }
            r7 = -552(0xfffffffffffffdd8, float:NaN)
            r0.setInstallInterruptCode(r7)     // Catch:{ Exception -> 0x0368 }
            r0 = r6
        L_0x0314:
            int r7 = r3.length     // Catch:{ Exception -> 0x0368 }
            if (r0 >= r7) goto L_0x041f
            r7 = r3[r0]     // Catch:{ Exception -> 0x0368 }
            java.lang.String r8 = "1"
            java.lang.String r9 = r7.getName()     // Catch:{ Exception -> 0x0368 }
            boolean r8 = r8.equals(r9)     // Catch:{ Exception -> 0x0368 }
            if (r8 != 0) goto L_0x034f
            java.lang.String r8 = r7.getName()     // Catch:{ Exception -> 0x0368 }
            java.lang.String r9 = ".dex"
            boolean r8 = r8.endsWith(r9)     // Catch:{ Exception -> 0x0368 }
            if (r8 != 0) goto L_0x034f
            java.lang.String r8 = "tbs.conf"
            java.lang.String r9 = r7.getName()     // Catch:{ Exception -> 0x0368 }
            boolean r8 = r8.equals(r9)     // Catch:{ Exception -> 0x0368 }
            if (r8 != 0) goto L_0x034f
            boolean r8 = r7.isDirectory()     // Catch:{ Exception -> 0x0368 }
            if (r8 != 0) goto L_0x034f
            java.lang.String r8 = r7.getName()     // Catch:{ Exception -> 0x0368 }
            java.lang.String r9 = ".prof"
            boolean r8 = r8.endsWith(r9)     // Catch:{ Exception -> 0x0368 }
            if (r8 == 0) goto L_0x0399
        L_0x034f:
            int r0 = r0 + 1
            goto L_0x0314
        L_0x0352:
            r2 = r3
            r0 = r6
            goto L_0x02fe
        L_0x0355:
            r0 = move-exception
            r1 = r2
        L_0x0357:
            r0.printStackTrace()     // Catch:{ all -> 0x0361 }
            if (r3 == 0) goto L_0x035f
            r3.close()     // Catch:{ IOException -> 0x05c5 }
        L_0x035f:
            r2 = r5
            goto L_0x0304
        L_0x0361:
            r0 = move-exception
        L_0x0362:
            if (r3 == 0) goto L_0x0367
            r3.close()     // Catch:{ IOException -> 0x05bf }
        L_0x0367:
            throw r0     // Catch:{ Exception -> 0x0368 }
        L_0x0368:
            r0 = move-exception
            r1 = r4
        L_0x036a:
            com.tencent.smtt.sdk.TbsLogReport r2 = com.tencent.smtt.sdk.TbsLogReport.a((android.content.Context) r14)     // Catch:{ all -> 0x03d9 }
            r3 = 215(0xd7, float:3.01E-43)
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x03d9 }
            r2.a((int) r3, (java.lang.String) r0)     // Catch:{ all -> 0x03d9 }
            com.tencent.smtt.sdk.TbsDownloadConfig r0 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r13)     // Catch:{ all -> 0x03d9 }
            r2 = -537(0xfffffffffffffde7, float:NaN)
            r0.setInstallInterruptCode(r2)     // Catch:{ all -> 0x03d9 }
            r0 = 0
            com.tencent.smtt.utils.k.a((java.io.File) r1, (boolean) r0)     // Catch:{ Exception -> 0x0584 }
            com.tencent.smtt.sdk.ai r0 = com.tencent.smtt.sdk.ai.a((android.content.Context) r14)     // Catch:{ Exception -> 0x0584 }
            r1 = 0
            r2 = -1
            r0.a((int) r1, (int) r2)     // Catch:{ Exception -> 0x0584 }
        L_0x038d:
            java.util.concurrent.locks.Lock r0 = i
            r0.unlock()
            java.util.concurrent.locks.Lock r0 = j
            r0.unlock()
            goto L_0x00d5
        L_0x0399:
            java.lang.String r8 = com.tencent.smtt.utils.a.a(r7)     // Catch:{ Exception -> 0x0368 }
            java.lang.String r9 = r7.getName()     // Catch:{ Exception -> 0x0368 }
            java.lang.String r10 = ""
            java.lang.String r9 = r1.getProperty(r9, r10)     // Catch:{ Exception -> 0x0368 }
            java.lang.String r10 = ""
            boolean r10 = r9.equals(r10)     // Catch:{ Exception -> 0x0368 }
            if (r10 != 0) goto L_0x03e8
            boolean r10 = r8.equals(r9)     // Catch:{ Exception -> 0x0368 }
            if (r10 == 0) goto L_0x03e8
            java.lang.String r8 = "TbsInstaller"
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0368 }
            r9.<init>()     // Catch:{ Exception -> 0x0368 }
            java.lang.String r10 = "md5_check_success for ("
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ Exception -> 0x0368 }
            java.lang.String r7 = r7.getName()     // Catch:{ Exception -> 0x0368 }
            java.lang.StringBuilder r7 = r9.append(r7)     // Catch:{ Exception -> 0x0368 }
            java.lang.String r9 = ")"
            java.lang.StringBuilder r7 = r7.append(r9)     // Catch:{ Exception -> 0x0368 }
            java.lang.String r7 = r7.toString()     // Catch:{ Exception -> 0x0368 }
            com.tencent.smtt.utils.TbsLog.i(r8, r7)     // Catch:{ Exception -> 0x0368 }
            goto L_0x034f
        L_0x03d9:
            r0 = move-exception
            java.util.concurrent.locks.Lock r1 = i
            r1.unlock()
            java.util.concurrent.locks.Lock r1 = j
            r1.unlock()
            r12.b()
            throw r0
        L_0x03e8:
            java.lang.String r0 = "TbsInstaller"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0368 }
            r1.<init>()     // Catch:{ Exception -> 0x0368 }
            java.lang.String r3 = "md5_check_failure for ("
            java.lang.StringBuilder r1 = r1.append(r3)     // Catch:{ Exception -> 0x0368 }
            java.lang.String r3 = r7.getName()     // Catch:{ Exception -> 0x0368 }
            java.lang.StringBuilder r1 = r1.append(r3)     // Catch:{ Exception -> 0x0368 }
            java.lang.String r3 = ")"
            java.lang.StringBuilder r1 = r1.append(r3)     // Catch:{ Exception -> 0x0368 }
            java.lang.String r3 = " targetMd5:"
            java.lang.StringBuilder r1 = r1.append(r3)     // Catch:{ Exception -> 0x0368 }
            java.lang.StringBuilder r1 = r1.append(r9)     // Catch:{ Exception -> 0x0368 }
            java.lang.String r3 = ", realMd5:"
            java.lang.StringBuilder r1 = r1.append(r3)     // Catch:{ Exception -> 0x0368 }
            java.lang.StringBuilder r1 = r1.append(r8)     // Catch:{ Exception -> 0x0368 }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x0368 }
            com.tencent.smtt.utils.TbsLog.e(r0, r1)     // Catch:{ Exception -> 0x0368 }
            r5 = r6
        L_0x041f:
            java.lang.String r0 = "TbsInstaller"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0368 }
            r1.<init>()     // Catch:{ Exception -> 0x0368 }
            java.lang.String r3 = "copyTbsCoreInThread - md5_check_success:"
            java.lang.StringBuilder r1 = r1.append(r3)     // Catch:{ Exception -> 0x0368 }
            java.lang.StringBuilder r1 = r1.append(r5)     // Catch:{ Exception -> 0x0368 }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x0368 }
            com.tencent.smtt.utils.TbsLog.i(r0, r1)     // Catch:{ Exception -> 0x0368 }
            if (r2 == 0) goto L_0x0466
            if (r5 != 0) goto L_0x0466
            java.lang.String r0 = "TbsInstaller"
            java.lang.String r1 = "copyTbsCoreInThread - md5 incorrect -> delete destTmpDir!"
            com.tencent.smtt.utils.TbsLog.e(r0, r1)     // Catch:{ Exception -> 0x0368 }
            r0 = 1
            com.tencent.smtt.utils.k.a((java.io.File) r4, (boolean) r0)     // Catch:{ Exception -> 0x0368 }
            com.tencent.smtt.sdk.TbsLogReport r0 = com.tencent.smtt.sdk.TbsLogReport.a((android.content.Context) r14)     // Catch:{ Exception -> 0x0368 }
            r1 = 213(0xd5, float:2.98E-43)
            java.lang.String r2 = "TbsCopy-Verify md5 fail after copying tbs core!"
            r0.a((int) r1, (java.lang.String) r2)     // Catch:{ Exception -> 0x0368 }
            com.tencent.smtt.sdk.TbsDownloadConfig r0 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r13)     // Catch:{ Exception -> 0x0368 }
            r1 = -532(0xfffffffffffffdec, float:NaN)
            r0.setInstallInterruptCode(r1)     // Catch:{ Exception -> 0x0368 }
            java.util.concurrent.locks.Lock r0 = i
            r0.unlock()
            java.util.concurrent.locks.Lock r0 = j
            r0.unlock()
            goto L_0x00d5
        L_0x0466:
            java.lang.String r0 = "TbsInstaller"
            java.lang.String r1 = "TbsInstaller-copyTbsCoreInThread success!"
            com.tencent.smtt.utils.TbsLog.i(r0, r1)     // Catch:{ Exception -> 0x0368 }
            r0 = 1
            r12.f(r14, r0)     // Catch:{ Exception -> 0x0368 }
            java.io.File r1 = com.tencent.smtt.sdk.ag.b((android.content.Context) r13)     // Catch:{ Exception -> 0x0368 }
            if (r1 == 0) goto L_0x048d
            boolean r0 = r1.exists()     // Catch:{ Exception -> 0x0368 }
            if (r0 == 0) goto L_0x048d
            java.io.File r2 = new java.io.File     // Catch:{ Exception -> 0x0368 }
            boolean r0 = com.tencent.smtt.sdk.TbsDownloader.getOverSea(r14)     // Catch:{ Exception -> 0x0368 }
            if (r0 == 0) goto L_0x04f9
            java.lang.String r0 = "x5.oversea.tbs.org"
        L_0x0487:
            r2.<init>(r1, r0)     // Catch:{ Exception -> 0x0368 }
            com.tencent.smtt.sdk.ag.a((java.io.File) r2, (android.content.Context) r14)     // Catch:{ Exception -> 0x0368 }
        L_0x048d:
            com.tencent.smtt.sdk.ai r0 = com.tencent.smtt.sdk.ai.a((android.content.Context) r14)     // Catch:{ Exception -> 0x0368 }
            r1 = 1
            r0.a((int) r15, (int) r1)     // Catch:{ Exception -> 0x0368 }
            boolean r0 = r12.k     // Catch:{ Exception -> 0x0368 }
            if (r0 == 0) goto L_0x04fc
            com.tencent.smtt.sdk.TbsLogReport r0 = com.tencent.smtt.sdk.TbsLogReport.a((android.content.Context) r14)     // Catch:{ Exception -> 0x0368 }
            r1 = 220(0xdc, float:3.08E-43)
            java.lang.String r2 = "continueInstallWithout core success"
            r0.a((int) r1, (java.lang.String) r2)     // Catch:{ Exception -> 0x0368 }
        L_0x04a4:
            com.tencent.smtt.sdk.TbsDownloadConfig r0 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r13)     // Catch:{ Exception -> 0x0368 }
            r1 = -533(0xfffffffffffffdeb, float:NaN)
            r0.setInstallInterruptCode(r1)     // Catch:{ Exception -> 0x0368 }
            java.lang.String r0 = "TbsInstaller"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0368 }
            r1.<init>()     // Catch:{ Exception -> 0x0368 }
            java.lang.String r2 = "TbsInstaller-copyTbsCoreInThread success -- version:"
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ Exception -> 0x0368 }
            java.lang.StringBuilder r1 = r1.append(r15)     // Catch:{ Exception -> 0x0368 }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x0368 }
            com.tencent.smtt.utils.TbsLog.i(r0, r1)     // Catch:{ Exception -> 0x0368 }
            int r0 = android.os.Build.VERSION.SDK_INT     // Catch:{ Exception -> 0x0368 }
            r1 = 11
            if (r0 < r1) goto L_0x0508
            java.lang.String r0 = "tbs_preloadx5_check_cfg_file"
            r1 = 4
            android.content.SharedPreferences r0 = r14.getSharedPreferences(r0, r1)     // Catch:{ Exception -> 0x0368 }
        L_0x04d2:
            android.content.SharedPreferences$Editor r0 = r0.edit()     // Catch:{ Throwable -> 0x0510 }
            java.lang.String r1 = "tbs_preload_x5_counter"
            r2 = 0
            r0.putInt(r1, r2)     // Catch:{ Throwable -> 0x0510 }
            java.lang.String r1 = "tbs_preload_x5_recorder"
            r2 = 0
            r0.putInt(r1, r2)     // Catch:{ Throwable -> 0x0510 }
            java.lang.String r1 = "tbs_preload_x5_version"
            r0.putInt(r1, r15)     // Catch:{ Throwable -> 0x0510 }
            r0.commit()     // Catch:{ Throwable -> 0x0510 }
        L_0x04ea:
            com.tencent.smtt.utils.aa.a(r14)     // Catch:{ Exception -> 0x0368 }
        L_0x04ed:
            java.util.concurrent.locks.Lock r0 = i
            r0.unlock()
            java.util.concurrent.locks.Lock r0 = j
            r0.unlock()
            goto L_0x00d5
        L_0x04f9:
            java.lang.String r0 = "x5.tbs.org"
            goto L_0x0487
        L_0x04fc:
            com.tencent.smtt.sdk.TbsLogReport r0 = com.tencent.smtt.sdk.TbsLogReport.a((android.content.Context) r14)     // Catch:{ Exception -> 0x0368 }
            r1 = 220(0xdc, float:3.08E-43)
            java.lang.String r2 = "success"
            r0.a((int) r1, (java.lang.String) r2)     // Catch:{ Exception -> 0x0368 }
            goto L_0x04a4
        L_0x0508:
            java.lang.String r0 = "tbs_preloadx5_check_cfg_file"
            r1 = 0
            android.content.SharedPreferences r0 = r14.getSharedPreferences(r0, r1)     // Catch:{ Exception -> 0x0368 }
            goto L_0x04d2
        L_0x0510:
            r0 = move-exception
            java.lang.String r1 = "TbsInstaller"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0368 }
            r2.<init>()     // Catch:{ Exception -> 0x0368 }
            java.lang.String r3 = "Init tbs_preload_x5_counter#2 exception:"
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Exception -> 0x0368 }
            java.lang.String r0 = android.util.Log.getStackTraceString(r0)     // Catch:{ Exception -> 0x0368 }
            java.lang.StringBuilder r0 = r2.append(r0)     // Catch:{ Exception -> 0x0368 }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x0368 }
            com.tencent.smtt.utils.TbsLog.e(r1, r0)     // Catch:{ Exception -> 0x0368 }
            goto L_0x04ea
        L_0x052e:
            java.lang.String r0 = "TbsInstaller"
            java.lang.String r1 = "TbsInstaller-copyTbsCoreInThread fail!"
            com.tencent.smtt.utils.TbsLog.i(r0, r1)     // Catch:{ Exception -> 0x0368 }
            com.tencent.smtt.sdk.ai r0 = com.tencent.smtt.sdk.ai.a((android.content.Context) r14)     // Catch:{ Exception -> 0x0368 }
            r1 = 2
            r0.a((int) r15, (int) r1)     // Catch:{ Exception -> 0x0368 }
            r0 = 0
            com.tencent.smtt.utils.k.a((java.io.File) r4, (boolean) r0)     // Catch:{ Exception -> 0x0368 }
            com.tencent.smtt.sdk.TbsDownloadConfig r0 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r13)     // Catch:{ Exception -> 0x0368 }
            r1 = -534(0xfffffffffffffdea, float:NaN)
            r0.setInstallInterruptCode(r1)     // Catch:{ Exception -> 0x0368 }
            com.tencent.smtt.sdk.TbsLogReport r0 = com.tencent.smtt.sdk.TbsLogReport.a((android.content.Context) r14)     // Catch:{ Exception -> 0x0368 }
            r1 = 212(0xd4, float:2.97E-43)
            java.lang.String r2 = "copy fail!"
            r0.a((int) r1, (java.lang.String) r2)     // Catch:{ Exception -> 0x0368 }
            goto L_0x04ed
        L_0x0556:
            if (r0 != 0) goto L_0x056c
            com.tencent.smtt.sdk.TbsLogReport r0 = com.tencent.smtt.sdk.TbsLogReport.a((android.content.Context) r14)     // Catch:{ Exception -> 0x0368 }
            r1 = 213(0xd5, float:2.98E-43)
            java.lang.String r2 = "src-dir is null when copying tbs core!"
            r0.a((int) r1, (java.lang.String) r2)     // Catch:{ Exception -> 0x0368 }
            com.tencent.smtt.sdk.TbsDownloadConfig r0 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r13)     // Catch:{ Exception -> 0x0368 }
            r1 = -535(0xfffffffffffffde9, float:NaN)
            r0.setInstallInterruptCode(r1)     // Catch:{ Exception -> 0x0368 }
        L_0x056c:
            if (r4 != 0) goto L_0x04ed
            com.tencent.smtt.sdk.TbsLogReport r0 = com.tencent.smtt.sdk.TbsLogReport.a((android.content.Context) r14)     // Catch:{ Exception -> 0x0368 }
            r1 = 214(0xd6, float:3.0E-43)
            java.lang.String r2 = "dst-dir is null when copying tbs core!"
            r0.a((int) r1, (java.lang.String) r2)     // Catch:{ Exception -> 0x0368 }
            com.tencent.smtt.sdk.TbsDownloadConfig r0 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r13)     // Catch:{ Exception -> 0x0368 }
            r1 = -536(0xfffffffffffffde8, float:NaN)
            r0.setInstallInterruptCode(r1)     // Catch:{ Exception -> 0x0368 }
            goto L_0x04ed
        L_0x0584:
            r0 = move-exception
            java.lang.String r1 = "TbsInstaller"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x03d9 }
            r2.<init>()     // Catch:{ all -> 0x03d9 }
            java.lang.String r3 = "[TbsInstaller-copyTbsCoreInThread] delete dstTmpDir throws exception:"
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ all -> 0x03d9 }
            java.lang.String r3 = r0.getMessage()     // Catch:{ all -> 0x03d9 }
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ all -> 0x03d9 }
            java.lang.String r3 = ","
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ all -> 0x03d9 }
            java.lang.Throwable r0 = r0.getCause()     // Catch:{ all -> 0x03d9 }
            java.lang.StringBuilder r0 = r2.append(r0)     // Catch:{ all -> 0x03d9 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x03d9 }
            com.tencent.smtt.utils.TbsLog.e(r1, r0)     // Catch:{ all -> 0x03d9 }
            goto L_0x038d
        L_0x05b1:
            r12.b()
            com.tencent.smtt.sdk.TbsDownloadConfig r0 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r13)
            r1 = -538(0xfffffffffffffde6, float:NaN)
            r0.setInstallInterruptCode(r1)
            goto L_0x0012
        L_0x05bf:
            r1 = move-exception
            r1.printStackTrace()     // Catch:{ Exception -> 0x0368 }
            goto L_0x0367
        L_0x05c5:
            r0 = move-exception
            r0.printStackTrace()     // Catch:{ Exception -> 0x0368 }
            goto L_0x035f
        L_0x05cb:
            r2 = move-exception
            r2.printStackTrace()     // Catch:{ Exception -> 0x0368 }
            goto L_0x0303
        L_0x05d1:
            r0 = move-exception
            goto L_0x036a
        L_0x05d4:
            r0 = move-exception
            r3 = r2
            goto L_0x0362
        L_0x05d8:
            r0 = move-exception
            goto L_0x0357
        L_0x05db:
            r0 = move-exception
            r3 = r2
            goto L_0x0357
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.am.a(android.content.Context, android.content.Context, int):void");
    }

    private boolean a(Context context, File file) {
        return a(context, file, false);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:49:0x0125, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:?, code lost:
        com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r10).setInstallInterruptCode(-523);
        com.tencent.smtt.sdk.TbsLogReport.a(r10).a(206, (java.lang.Throwable) r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:?, code lost:
        com.tencent.smtt.utils.k.b(r4);
        com.tencent.smtt.utils.TbsLog.e("TbsInstaller", "copyFileIfChanged -- delete tmpTbsCoreUnzipDir#2! exist:" + r4.exists());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x01af, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x01b0, code lost:
        com.tencent.smtt.utils.TbsLog.i("TbsInstaller", "TbsInstaller-unzipTbs done");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x01b7, code lost:
        throw r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x01b8, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x01b9, code lost:
        com.tencent.smtt.utils.TbsLog.e("TbsInstaller", "copyFileIfChanged -- delete tmpTbsCoreUnzipDir#2! exception:" + android.util.Log.getStackTraceString(r0));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x01f9, code lost:
        r0 = false;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x0125 A[ExcHandler: IOException (r2v18 'e' java.io.IOException A[CUSTOM_DECLARE]), Splitter:B:23:0x0098] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean a(android.content.Context r10, java.io.File r11, boolean r12) {
        /*
            r9 = this;
            r0 = 1
            r1 = 0
            java.lang.String r2 = "TbsInstaller"
            java.lang.String r3 = "TbsInstaller-unzipTbs start"
            com.tencent.smtt.utils.TbsLog.i(r2, r3)
            boolean r2 = com.tencent.smtt.utils.k.c((java.io.File) r11)
            if (r2 != 0) goto L_0x0024
            com.tencent.smtt.sdk.TbsLogReport r0 = com.tencent.smtt.sdk.TbsLogReport.a((android.content.Context) r10)
            r2 = 204(0xcc, float:2.86E-43)
            java.lang.String r3 = "apk is invalid!"
            r0.a((int) r2, (java.lang.String) r3)
            com.tencent.smtt.sdk.TbsDownloadConfig r0 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r10)
            r2 = -520(0xfffffffffffffdf8, float:NaN)
            r0.setInstallInterruptCode(r2)
        L_0x0023:
            return r1
        L_0x0024:
            java.lang.String r2 = "tbs"
            r3 = 0
            java.io.File r3 = r10.getDir(r2, r3)     // Catch:{ Throwable -> 0x0074 }
            if (r12 == 0) goto L_0x006c
            java.io.File r2 = new java.io.File     // Catch:{ Throwable -> 0x0074 }
            java.lang.String r4 = "core_share_decouple"
            r2.<init>(r3, r4)     // Catch:{ Throwable -> 0x0074 }
        L_0x0034:
            if (r2 == 0) goto L_0x004e
            boolean r3 = r2.exists()     // Catch:{ Throwable -> 0x0074 }
            if (r3 == 0) goto L_0x004e
            com.tencent.smtt.sdk.TbsDownloadConfig r3 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r10)     // Catch:{ Throwable -> 0x0074 }
            android.content.SharedPreferences r3 = r3.mPreferences     // Catch:{ Throwable -> 0x0074 }
            java.lang.String r4 = "tbs_downloaddecouplecore"
            r5 = 0
            int r3 = r3.getInt(r4, r5)     // Catch:{ Throwable -> 0x0074 }
            if (r3 == r0) goto L_0x004e
            com.tencent.smtt.utils.k.b((java.io.File) r2)     // Catch:{ Throwable -> 0x0074 }
        L_0x004e:
            if (r12 == 0) goto L_0x0092
            java.io.File r2 = r9.u(r10)
            r4 = r2
        L_0x0055:
            if (r4 != 0) goto L_0x0098
            com.tencent.smtt.sdk.TbsLogReport r0 = com.tencent.smtt.sdk.TbsLogReport.a((android.content.Context) r10)
            r2 = 205(0xcd, float:2.87E-43)
            java.lang.String r3 = "tmp unzip dir is null!"
            r0.a((int) r2, (java.lang.String) r3)
            com.tencent.smtt.sdk.TbsDownloadConfig r0 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r10)
            r2 = -521(0xfffffffffffffdf7, float:NaN)
            r0.setInstallInterruptCode(r2)
            goto L_0x0023
        L_0x006c:
            java.io.File r2 = new java.io.File     // Catch:{ Throwable -> 0x0074 }
            java.lang.String r4 = "core_unzip_tmp"
            r2.<init>(r3, r4)     // Catch:{ Throwable -> 0x0074 }
            goto L_0x0034
        L_0x0074:
            r2 = move-exception
            java.lang.String r3 = "TbsInstaller"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "TbsInstaller-unzipTbs -- delete unzip folder if exists exception"
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r2 = android.util.Log.getStackTraceString(r2)
            java.lang.StringBuilder r2 = r4.append(r2)
            java.lang.String r2 = r2.toString()
            com.tencent.smtt.utils.TbsLog.e(r3, r2)
            goto L_0x004e
        L_0x0092:
            java.io.File r2 = r9.t(r10)
            r4 = r2
            goto L_0x0055
        L_0x0098:
            com.tencent.smtt.utils.k.a((java.io.File) r4)     // Catch:{ IOException -> 0x0125, Exception -> 0x016c }
            if (r12 == 0) goto L_0x00a1
            r2 = 1
            com.tencent.smtt.utils.k.a((java.io.File) r4, (boolean) r2)     // Catch:{ IOException -> 0x0125, Exception -> 0x016c }
        L_0x00a1:
            boolean r2 = com.tencent.smtt.utils.k.a((java.io.File) r11, (java.io.File) r4)     // Catch:{ IOException -> 0x0125, Exception -> 0x016c }
            if (r2 == 0) goto L_0x00ab
            boolean r2 = r9.a((java.io.File) r4, (android.content.Context) r10)     // Catch:{ IOException -> 0x0125, Exception -> 0x016c }
        L_0x00ab:
            if (r12 == 0) goto L_0x00dc
            java.lang.String[] r5 = r4.list()     // Catch:{ IOException -> 0x0125, Exception -> 0x016c }
            r3 = r1
        L_0x00b2:
            int r6 = r5.length     // Catch:{ IOException -> 0x0125, Exception -> 0x016c }
            if (r3 >= r6) goto L_0x00ce
            java.io.File r6 = new java.io.File     // Catch:{ IOException -> 0x0125, Exception -> 0x016c }
            r7 = r5[r3]     // Catch:{ IOException -> 0x0125, Exception -> 0x016c }
            r6.<init>(r4, r7)     // Catch:{ IOException -> 0x0125, Exception -> 0x016c }
            java.lang.String r7 = r6.getName()     // Catch:{ IOException -> 0x0125, Exception -> 0x016c }
            java.lang.String r8 = ".dex"
            boolean r7 = r7.endsWith(r8)     // Catch:{ IOException -> 0x0125, Exception -> 0x016c }
            if (r7 == 0) goto L_0x00cb
            r6.delete()     // Catch:{ IOException -> 0x0125, Exception -> 0x016c }
        L_0x00cb:
            int r3 = r3 + 1
            goto L_0x00b2
        L_0x00ce:
            java.io.File r3 = s(r10)     // Catch:{ Exception -> 0x01f4, IOException -> 0x0125 }
            java.io.File r5 = new java.io.File     // Catch:{ Exception -> 0x01f4, IOException -> 0x0125 }
            java.lang.String r6 = "x5.tbs"
            r5.<init>(r3, r6)     // Catch:{ Exception -> 0x01f4, IOException -> 0x0125 }
            r5.delete()     // Catch:{ Exception -> 0x01f4, IOException -> 0x0125 }
        L_0x00dc:
            if (r2 != 0) goto L_0x0110
            com.tencent.smtt.utils.k.b((java.io.File) r4)     // Catch:{ IOException -> 0x0125, Exception -> 0x016c }
            com.tencent.smtt.sdk.TbsDownloadConfig r3 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r10)     // Catch:{ IOException -> 0x0125, Exception -> 0x016c }
            r5 = -522(0xfffffffffffffdf6, float:NaN)
            r3.setInstallInterruptCode(r5)     // Catch:{ IOException -> 0x0125, Exception -> 0x016c }
            java.lang.String r3 = "TbsInstaller"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0125, Exception -> 0x016c }
            r5.<init>()     // Catch:{ IOException -> 0x0125, Exception -> 0x016c }
            java.lang.String r6 = "copyFileIfChanged -- delete tmpTbsCoreUnzipDir#1! exist:"
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ IOException -> 0x0125, Exception -> 0x016c }
            boolean r6 = r4.exists()     // Catch:{ IOException -> 0x0125, Exception -> 0x016c }
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ IOException -> 0x0125, Exception -> 0x016c }
            java.lang.String r5 = r5.toString()     // Catch:{ IOException -> 0x0125, Exception -> 0x016c }
            com.tencent.smtt.utils.TbsLog.e(r3, r5)     // Catch:{ IOException -> 0x0125, Exception -> 0x016c }
        L_0x0106:
            java.lang.String r0 = "TbsInstaller"
            java.lang.String r1 = "TbsInstaller-unzipTbs done"
            com.tencent.smtt.utils.TbsLog.i(r0, r1)
            r1 = r2
            goto L_0x0023
        L_0x0110:
            r3 = 1
            r9.f(r10, r3)     // Catch:{ IOException -> 0x0125, Exception -> 0x016c }
            if (r12 == 0) goto L_0x0106
            java.io.File r3 = r9.p(r10)     // Catch:{ IOException -> 0x0125, Exception -> 0x016c }
            r5 = 1
            com.tencent.smtt.utils.k.a((java.io.File) r3, (boolean) r5)     // Catch:{ IOException -> 0x0125, Exception -> 0x016c }
            r4.renameTo(r3)     // Catch:{ IOException -> 0x0125, Exception -> 0x016c }
            com.tencent.smtt.sdk.TbsShareManager.b(r10)     // Catch:{ IOException -> 0x0125, Exception -> 0x016c }
            goto L_0x0106
        L_0x0125:
            r2 = move-exception
            com.tencent.smtt.sdk.TbsDownloadConfig r3 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r10)     // Catch:{ all -> 0x01af }
            r5 = -523(0xfffffffffffffdf5, float:NaN)
            r3.setInstallInterruptCode(r5)     // Catch:{ all -> 0x01af }
            com.tencent.smtt.sdk.TbsLogReport r3 = com.tencent.smtt.sdk.TbsLogReport.a((android.content.Context) r10)     // Catch:{ all -> 0x01af }
            r5 = 206(0xce, float:2.89E-43)
            r3.a((int) r5, (java.lang.Throwable) r2)     // Catch:{ all -> 0x01af }
            if (r4 == 0) goto L_0x01f9
            boolean r2 = r4.exists()     // Catch:{ all -> 0x01af }
            if (r2 == 0) goto L_0x01f9
        L_0x0140:
            if (r0 == 0) goto L_0x0163
            if (r4 == 0) goto L_0x0163
            com.tencent.smtt.utils.k.b((java.io.File) r4)     // Catch:{ Throwable -> 0x01b8 }
            java.lang.String r0 = "TbsInstaller"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x01b8 }
            r2.<init>()     // Catch:{ Throwable -> 0x01b8 }
            java.lang.String r3 = "copyFileIfChanged -- delete tmpTbsCoreUnzipDir#2! exist:"
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Throwable -> 0x01b8 }
            boolean r3 = r4.exists()     // Catch:{ Throwable -> 0x01b8 }
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Throwable -> 0x01b8 }
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x01b8 }
            com.tencent.smtt.utils.TbsLog.e(r0, r2)     // Catch:{ Throwable -> 0x01b8 }
        L_0x0163:
            java.lang.String r0 = "TbsInstaller"
            java.lang.String r2 = "TbsInstaller-unzipTbs done"
        L_0x0167:
            com.tencent.smtt.utils.TbsLog.i(r0, r2)
            goto L_0x0023
        L_0x016c:
            r2 = move-exception
            com.tencent.smtt.sdk.TbsDownloadConfig r3 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r10)     // Catch:{ all -> 0x01af }
            r5 = -523(0xfffffffffffffdf5, float:NaN)
            r3.setInstallInterruptCode(r5)     // Catch:{ all -> 0x01af }
            com.tencent.smtt.sdk.TbsLogReport r3 = com.tencent.smtt.sdk.TbsLogReport.a((android.content.Context) r10)     // Catch:{ all -> 0x01af }
            r5 = 207(0xcf, float:2.9E-43)
            r3.a((int) r5, (java.lang.Throwable) r2)     // Catch:{ all -> 0x01af }
            if (r4 == 0) goto L_0x01f7
            boolean r2 = r4.exists()     // Catch:{ all -> 0x01af }
            if (r2 == 0) goto L_0x01f7
        L_0x0187:
            if (r0 == 0) goto L_0x01aa
            if (r4 == 0) goto L_0x01aa
            com.tencent.smtt.utils.k.b((java.io.File) r4)     // Catch:{ Throwable -> 0x01d6 }
            java.lang.String r0 = "TbsInstaller"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x01d6 }
            r2.<init>()     // Catch:{ Throwable -> 0x01d6 }
            java.lang.String r3 = "copyFileIfChanged -- delete tmpTbsCoreUnzipDir#2! exist:"
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Throwable -> 0x01d6 }
            boolean r3 = r4.exists()     // Catch:{ Throwable -> 0x01d6 }
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Throwable -> 0x01d6 }
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x01d6 }
            com.tencent.smtt.utils.TbsLog.e(r0, r2)     // Catch:{ Throwable -> 0x01d6 }
        L_0x01aa:
            java.lang.String r0 = "TbsInstaller"
            java.lang.String r2 = "TbsInstaller-unzipTbs done"
            goto L_0x0167
        L_0x01af:
            r0 = move-exception
            java.lang.String r1 = "TbsInstaller"
            java.lang.String r2 = "TbsInstaller-unzipTbs done"
            com.tencent.smtt.utils.TbsLog.i(r1, r2)
            throw r0
        L_0x01b8:
            r0 = move-exception
            java.lang.String r2 = "TbsInstaller"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "copyFileIfChanged -- delete tmpTbsCoreUnzipDir#2! exception:"
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r0 = android.util.Log.getStackTraceString(r0)
            java.lang.StringBuilder r0 = r3.append(r0)
            java.lang.String r0 = r0.toString()
            com.tencent.smtt.utils.TbsLog.e(r2, r0)
            goto L_0x0163
        L_0x01d6:
            r0 = move-exception
            java.lang.String r2 = "TbsInstaller"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "copyFileIfChanged -- delete tmpTbsCoreUnzipDir#2! exception:"
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r0 = android.util.Log.getStackTraceString(r0)
            java.lang.StringBuilder r0 = r3.append(r0)
            java.lang.String r0 = r0.toString()
            com.tencent.smtt.utils.TbsLog.e(r2, r0)
            goto L_0x01aa
        L_0x01f4:
            r3 = move-exception
            goto L_0x00dc
        L_0x01f7:
            r0 = r1
            goto L_0x0187
        L_0x01f9:
            r0 = r1
            goto L_0x0140
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.am.a(android.content.Context, java.io.File, boolean):boolean");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00ae, code lost:
        r1 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00af, code lost:
        r0 = null;
        r5 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00bb, code lost:
        r0 = th;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x004b A[SYNTHETIC, Splitter:B:13:0x004b] */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0068  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00b6 A[SYNTHETIC, Splitter:B:37:0x00b6] */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00bb A[ExcHandler: all (th java.lang.Throwable), Splitter:B:1:0x0025] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00be A[SYNTHETIC, Splitter:B:42:0x00be] */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x0152 A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean a(java.io.File r11, android.content.Context r12) {
        /*
            r10 = this;
            r3 = 0
            r2 = 1
            r4 = 0
            java.lang.String r0 = "TbsInstaller"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r5 = "finalCheckForTbsCoreValidity - "
            java.lang.StringBuilder r1 = r1.append(r5)
            java.lang.StringBuilder r1 = r1.append(r11)
            java.lang.String r5 = ", "
            java.lang.StringBuilder r1 = r1.append(r5)
            java.lang.StringBuilder r1 = r1.append(r12)
            java.lang.String r1 = r1.toString()
            com.tencent.smtt.utils.TbsLog.i(r0, r1)
            java.io.File r1 = new java.io.File     // Catch:{ Exception -> 0x00ae, all -> 0x00bb }
            java.lang.String r0 = "1"
            r1.<init>(r11, r0)     // Catch:{ Exception -> 0x00ae, all -> 0x00bb }
            java.util.Properties r0 = new java.util.Properties     // Catch:{ Exception -> 0x00ae, all -> 0x00bb }
            r0.<init>()     // Catch:{ Exception -> 0x00ae, all -> 0x00bb }
            if (r1 == 0) goto L_0x00ab
            boolean r5 = r1.exists()     // Catch:{ Exception -> 0x017b, all -> 0x00bb }
            if (r5 == 0) goto L_0x00ab
            if (r0 == 0) goto L_0x00ab
            java.io.FileInputStream r6 = new java.io.FileInputStream     // Catch:{ Exception -> 0x017b, all -> 0x00bb }
            r6.<init>(r1)     // Catch:{ Exception -> 0x017b, all -> 0x00bb }
            java.io.BufferedInputStream r5 = new java.io.BufferedInputStream     // Catch:{ Exception -> 0x017b, all -> 0x00bb }
            r5.<init>(r6)     // Catch:{ Exception -> 0x017b, all -> 0x00bb }
            r0.load(r5)     // Catch:{ Exception -> 0x017f }
            r1 = r2
        L_0x0049:
            if (r5 == 0) goto L_0x004e
            r5.close()     // Catch:{ IOException -> 0x0171 }
        L_0x004e:
            java.lang.String r3 = "TbsInstaller"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "finalCheckForTbsCoreValidity - need_check:"
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.StringBuilder r5 = r5.append(r1)
            java.lang.String r5 = r5.toString()
            com.tencent.smtt.utils.TbsLog.i(r3, r5)
            if (r1 == 0) goto L_0x0182
            java.io.File[] r5 = r11.listFiles()
            r3 = r4
        L_0x006d:
            int r6 = r5.length
            if (r3 >= r6) goto L_0x0182
            r6 = r5[r3]
            java.lang.String r7 = "1"
            java.lang.String r8 = r6.getName()
            boolean r7 = r7.equals(r8)
            if (r7 != 0) goto L_0x00a8
            java.lang.String r7 = r6.getName()
            java.lang.String r8 = ".dex"
            boolean r7 = r7.endsWith(r8)
            if (r7 != 0) goto L_0x00a8
            java.lang.String r7 = "tbs.conf"
            java.lang.String r8 = r6.getName()
            boolean r7 = r7.equals(r8)
            if (r7 != 0) goto L_0x00a8
            boolean r7 = r6.isDirectory()
            if (r7 != 0) goto L_0x00a8
            java.lang.String r7 = r6.getName()
            java.lang.String r8 = ".prof"
            boolean r7 = r7.endsWith(r8)
            if (r7 == 0) goto L_0x00c2
        L_0x00a8:
            int r3 = r3 + 1
            goto L_0x006d
        L_0x00ab:
            r5 = r3
            r1 = r4
            goto L_0x0049
        L_0x00ae:
            r1 = move-exception
            r0 = r3
            r5 = r3
        L_0x00b1:
            r1.printStackTrace()     // Catch:{ all -> 0x0177 }
            if (r5 == 0) goto L_0x00b9
            r5.close()     // Catch:{ IOException -> 0x016b }
        L_0x00b9:
            r1 = r2
            goto L_0x004e
        L_0x00bb:
            r0 = move-exception
        L_0x00bc:
            if (r3 == 0) goto L_0x00c1
            r3.close()     // Catch:{ IOException -> 0x0165 }
        L_0x00c1:
            throw r0
        L_0x00c2:
            java.lang.String r7 = com.tencent.smtt.utils.a.a(r6)
            java.lang.String r8 = r6.getName()
            java.lang.String r9 = ""
            java.lang.String r8 = r0.getProperty(r8, r9)
            java.lang.String r9 = ""
            boolean r9 = r8.equals(r9)
            if (r9 != 0) goto L_0x0101
            boolean r9 = r8.equals(r7)
            if (r9 == 0) goto L_0x0101
            java.lang.String r7 = "TbsInstaller"
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r9 = "md5_check_success for ("
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.String r6 = r6.getName()
            java.lang.StringBuilder r6 = r8.append(r6)
            java.lang.String r8 = ")"
            java.lang.StringBuilder r6 = r6.append(r8)
            java.lang.String r6 = r6.toString()
            com.tencent.smtt.utils.TbsLog.i(r7, r6)
            goto L_0x00a8
        L_0x0101:
            java.lang.String r0 = "TbsInstaller"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r5 = "md5_check_failure for ("
            java.lang.StringBuilder r3 = r3.append(r5)
            java.lang.String r5 = r6.getName()
            java.lang.StringBuilder r3 = r3.append(r5)
            java.lang.String r5 = ")"
            java.lang.StringBuilder r3 = r3.append(r5)
            java.lang.String r5 = " targetMd5:"
            java.lang.StringBuilder r3 = r3.append(r5)
            java.lang.StringBuilder r3 = r3.append(r8)
            java.lang.String r5 = ", realMd5:"
            java.lang.StringBuilder r3 = r3.append(r5)
            java.lang.StringBuilder r3 = r3.append(r7)
            java.lang.String r3 = r3.toString()
            com.tencent.smtt.utils.TbsLog.e(r0, r3)
            r0 = r4
        L_0x0138:
            java.lang.String r3 = "TbsInstaller"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "finalCheckForTbsCoreValidity - md5_check_success:"
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.StringBuilder r5 = r5.append(r0)
            java.lang.String r5 = r5.toString()
            com.tencent.smtt.utils.TbsLog.i(r3, r5)
            if (r1 == 0) goto L_0x015c
            if (r0 != 0) goto L_0x015c
            java.lang.String r0 = "TbsInstaller"
            java.lang.String r1 = "finalCheckForTbsCoreValidity - Verify failed after unzipping!"
            com.tencent.smtt.utils.TbsLog.e(r0, r1)
        L_0x015b:
            return r4
        L_0x015c:
            java.lang.String r0 = "TbsInstaller"
            java.lang.String r1 = "finalCheckForTbsCoreValidity success!"
            com.tencent.smtt.utils.TbsLog.i(r0, r1)
            r4 = r2
            goto L_0x015b
        L_0x0165:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x00c1
        L_0x016b:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x00b9
        L_0x0171:
            r3 = move-exception
            r3.printStackTrace()
            goto L_0x004e
        L_0x0177:
            r0 = move-exception
            r3 = r5
            goto L_0x00bc
        L_0x017b:
            r1 = move-exception
            r5 = r3
            goto L_0x00b1
        L_0x017f:
            r1 = move-exception
            goto L_0x00b1
        L_0x0182:
            r0 = r2
            goto L_0x0138
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.am.a(java.io.File, android.content.Context):boolean");
    }

    /* access modifiers changed from: private */
    @TargetApi(11)
    public void b(Context context, String str, int i2) {
        int i3;
        boolean z;
        String str2;
        String str3;
        int i4;
        int i5 = 200;
        int i6 = 0;
        TbsDownloadConfig.getInstance(context).setInstallInterruptCode(KartinRet.KARTIN_FLAG_GET_DGN_SPEED_TESTER_FAILED);
        if (c(context)) {
            TbsLog.i("TbsInstaller", "isTbsLocalInstalled --> no installation!", true);
            TbsDownloadConfig.getInstance(context).setInstallInterruptCode(-502);
            return;
        }
        TbsLog.i("TbsInstaller", "TbsInstaller-installTbsCoreInThread tbsApkPath=" + str);
        TbsLog.i("TbsInstaller", "TbsInstaller-installTbsCoreInThread tbsCoreTargetVer=" + i2);
        TbsLog.i("TbsInstaller", "TbsInstaller-continueInstallTbsCore currentProcessName=" + context.getApplicationInfo().processName);
        TbsLog.i("TbsInstaller", "TbsInstaller-installTbsCoreInThread currentProcessId=" + Process.myPid());
        TbsLog.i("TbsInstaller", "TbsInstaller-installTbsCoreInThread currentThreadName=" + Thread.currentThread().getName());
        if ((Build.VERSION.SDK_INT >= 11 ? context.getSharedPreferences("tbs_preloadx5_check_cfg_file", 4) : context.getSharedPreferences("tbs_preloadx5_check_cfg_file", 0)).getInt("tbs_precheck_disable_version", -1) == i2) {
            TbsLog.e("TbsInstaller", "TbsInstaller-installTbsCoreInThread -- version:" + i2 + " is disabled by preload_x5_check!");
            TbsDownloadConfig.getInstance(context).setInstallInterruptCode(-503);
        } else if (!k.b(context)) {
            long a2 = aa.a();
            long downloadMinFreeSpace = TbsDownloadConfig.getInstance(context).getDownloadMinFreeSpace();
            TbsDownloadConfig.getInstance(context).setInstallInterruptCode(-504);
            TbsLogReport.a(context).a((int) TbsListener.ErrorCode.ROM_NOT_ENOUGH, "rom is not enough when installing tbs core! curAvailROM=" + a2 + ",minReqRom=" + downloadMinFreeSpace);
        } else if (!w(context)) {
            TbsDownloadConfig.getInstance(context).setInstallInterruptCode(-505);
        } else {
            boolean tryLock = j.tryLock();
            TbsLog.i("TbsInstaller", "TbsInstaller-installTbsCoreInThread locked =" + tryLock);
            if (tryLock) {
                TbsDownloadConfig.getInstance(context).setInstallInterruptCode(-507);
                i.lock();
                int c2 = ai.a(context).c("copy_core_ver");
                int b2 = ai.a(context).b();
                TbsLog.i("TbsInstaller", "TbsInstaller-installTbsCoreInThread tbsCoreCopyVer =" + c2);
                TbsLog.i("TbsInstaller", "TbsInstaller-installTbsCoreInThread tbsCoreInstallVer =" + b2);
                TbsLog.i("TbsInstaller", "TbsInstaller-installTbsCoreInThread tbsCoreTargetVer =" + i2);
                if ((b2 > 0 && i2 > b2) || (c2 > 0 && i2 > c2)) {
                    o(context);
                }
                int c3 = ai.a(context).c();
                int i7 = i(context);
                TbsLog.i("TbsInstaller", "TbsInstaller-installTbsCoreInThread installStatus1=" + c3);
                TbsLog.i("TbsInstaller", "TbsInstaller-installTbsCoreInThread tbsCoreInstalledVer=" + i7);
                if (c3 < 0 || c3 >= 2) {
                    if (c3 == 3 && i7 > 0 && (i2 > i7 || i2 == 88888888)) {
                        c3 = -1;
                        o(context);
                        TbsLog.i("TbsInstaller", "TbsInstaller-installTbsCoreInThread -- update TBS.....", true);
                    }
                    i3 = c3;
                    z = false;
                } else {
                    TbsLog.i("TbsInstaller", "TbsInstaller-installTbsCoreInThread -- retry.....", true);
                    i3 = c3;
                    z = true;
                }
                TbsDownloadConfig.getInstance(context).setInstallInterruptCode(-508);
                TbsLog.i("TbsInstaller", "TbsInstaller-installTbsCoreInThread installStatus2=" + i3);
                if (i3 < 1) {
                    TbsLog.i("TbsInstaller", "STEP 2/2 begin installation.....", true);
                    TbsDownloadConfig.getInstance(context).setInstallInterruptCode(-509);
                    if (z) {
                        int c4 = ai.a(context).c("unzip_retry_num");
                        if (c4 > 10) {
                            TbsLogReport.a(context).a(201, "exceed unzip retry num!");
                            F(context);
                            TbsDownloadConfig.getInstance(context).setInstallInterruptCode(-510);
                            i.unlock();
                            j.unlock();
                            b();
                            return;
                        }
                        ai.a(context).b(c4 + 1);
                    }
                    if (str == null) {
                        str3 = ai.a(context).d("install_apk_path");
                        if (str3 == null) {
                            TbsLogReport.a(context).a(202, "apk path is null!");
                            TbsDownloadConfig.getInstance(context).setInstallInterruptCode(-511);
                            i.unlock();
                            j.unlock();
                            b();
                            return;
                        }
                    } else {
                        str3 = str;
                    }
                    TbsLog.i("TbsInstaller", "TbsInstaller-installTbsCoreInThread apkPath =" + str3);
                    int b3 = b(context, str3);
                    if (b3 == 0) {
                        TbsDownloadConfig.getInstance(context).setInstallInterruptCode(-512);
                        TbsLogReport.a(context).a(203, "apk version is 0!");
                        i.unlock();
                        j.unlock();
                        b();
                        return;
                    }
                    ai.a(context).a("install_apk_path", str3);
                    ai.a(context).b(b3, 0);
                    TbsDownloadConfig.getInstance(context).setInstallInterruptCode(-548);
                    if (TbsDownloadConfig.getInstance(context).mPreferences.getInt(TbsDownloadConfig.TbsConfigKey.KEY_DOWNLOADDECOUPLECORE, 0) == 1) {
                        if (!a(context, new File(str3), true)) {
                            TbsLogReport.a(context).a(207, "unzipTbsApk failed", TbsLogReport.EventType.TYPE_INSTALL_DECOUPLE);
                            i.unlock();
                            j.unlock();
                            b();
                            return;
                        }
                    } else if (!a(context, new File(str3))) {
                        TbsLogReport.a(context).a(207, "unzipTbsApk failed");
                        i.unlock();
                        j.unlock();
                        b();
                        return;
                    }
                    if (z) {
                        int b4 = ai.a(context).b("unlzma_status");
                        if (b4 > 5) {
                            TbsLogReport.a(context).a((int) TbsListener.ErrorCode.EXCEED_LZMA_RETRY_NUM, "exceed unlzma retry num!");
                            TbsDownloadConfig.getInstance(context).setInstallInterruptCode(-553);
                            F(context);
                            ag.c(context);
                            TbsDownloadConfig.getInstance(context).a.put(TbsDownloadConfig.TbsConfigKey.KEY_NEEDDOWNLOAD, true);
                            TbsDownloadConfig.getInstance(context).a.put(TbsDownloadConfig.TbsConfigKey.KEY_FULL_PACKAGE, true);
                            TbsDownloadConfig.getInstance(context).commit();
                            i.unlock();
                            j.unlock();
                            b();
                            return;
                        }
                        ai.a(context).d(b4 + 1);
                    }
                    TbsLog.i("TbsInstaller", "unlzma begin");
                    int i8 = TbsDownloadConfig.getInstance().mPreferences.getInt(TbsDownloadConfig.TbsConfigKey.KEY_RESPONSECODE, 0);
                    if (i(context) != 0) {
                        Object a3 = QbSdk.a(context, "can_unlzma", (Bundle) null);
                        if ((a3 == null || !(a3 instanceof Boolean)) ? false : ((Boolean) a3).booleanValue()) {
                            Bundle bundle = new Bundle();
                            bundle.putInt("responseCode", i8);
                            if (TbsDownloadConfig.getInstance(context).mPreferences.getInt(TbsDownloadConfig.TbsConfigKey.KEY_DOWNLOADDECOUPLECORE, 0) == 1) {
                                bundle.putString("unzip_temp_path", p(context).getAbsolutePath());
                            } else {
                                bundle.putString("unzip_temp_path", t(context).getAbsolutePath());
                            }
                            Object a4 = QbSdk.a(context, "unlzma", bundle);
                            if (a4 == null) {
                                TbsLog.i("TbsInstaller", "unlzma return null");
                                TbsLogReport.a(context).a(222, "unlzma is null");
                            } else if (a4 instanceof Boolean) {
                                if (((Boolean) a4).booleanValue()) {
                                    TbsLog.i("TbsInstaller", "unlzma success");
                                    i4 = 1;
                                } else {
                                    TbsLog.i("TbsInstaller", "unlzma return false");
                                    TbsLogReport.a(context).a(222, "unlzma return false");
                                    i4 = 0;
                                }
                                i6 = i4;
                            } else if (a4 instanceof Bundle) {
                                i6 = 1;
                            } else if (a4 instanceof Throwable) {
                                TbsLog.i("TbsInstaller", "unlzma failure because Throwable" + Log.getStackTraceString((Throwable) a4));
                                TbsLogReport.a(context).a(222, (Throwable) a4);
                            }
                            if (i6 == 0) {
                                i.unlock();
                                j.unlock();
                                b();
                                return;
                            }
                        }
                    }
                    TbsLog.i("TbsInstaller", "unlzma finished");
                    ai.a(context).b(b3, 1);
                    i6 = b3;
                } else if (TbsDownloadConfig.getInstance(context).mPreferences.getInt(TbsDownloadConfig.TbsConfigKey.KEY_DOWNLOADDECOUPLECORE, 0) == 1) {
                    if (str == null) {
                        str2 = ai.a(context).d("install_apk_path");
                        if (str2 == null) {
                            TbsLogReport.a(context).a(202, "apk path is null!");
                            TbsDownloadConfig.getInstance(context).setInstallInterruptCode(-511);
                            i.unlock();
                            j.unlock();
                            b();
                            return;
                        }
                    } else {
                        str2 = str;
                    }
                    try {
                        if (!a(context, new File(str2), true)) {
                        }
                    } catch (Throwable th) {
                        i.unlock();
                        j.unlock();
                        b();
                        throw th;
                    }
                }
                if (i3 < 2) {
                    if (z) {
                        int c5 = ai.a(context).c("dexopt_retry_num");
                        if (c5 > 10) {
                            TbsLogReport.a(context).a((int) TbsListener.ErrorCode.EXCEED_DEXOPT_RETRY_NUM, "exceed dexopt retry num!");
                            TbsDownloadConfig.getInstance(context).setInstallInterruptCode(-514);
                            F(context);
                            i.unlock();
                            j.unlock();
                            b();
                            return;
                        }
                        ai.a(context).a(c5 + 1);
                    }
                    TbsDownloadConfig.getInstance(context).setInstallInterruptCode(-549);
                    if (!c(context, 0)) {
                        TbsDownloadConfig.getInstance(context).setInstallInterruptCode(-515);
                        i.unlock();
                        j.unlock();
                        b();
                        return;
                    }
                    ai.a(context).b(i6, 2);
                    TbsLog.i("TbsInstaller", "STEP 2/2 installation completed! you can restart!", true);
                    TbsLog.i("TbsInstaller", "STEP 2/2 installation completed! you can restart! version:" + i2);
                    TbsDownloadConfig.getInstance(context).setInstallInterruptCode(-516);
                    SharedPreferences.Editor edit = (Build.VERSION.SDK_INT >= 11 ? context.getSharedPreferences("tbs_preloadx5_check_cfg_file", 4) : context.getSharedPreferences("tbs_preloadx5_check_cfg_file", 0)).edit();
                    edit.putInt("tbs_preload_x5_counter", 0);
                    edit.putInt("tbs_preload_x5_recorder", 0);
                    edit.putInt("tbs_preload_x5_version", i2);
                    edit.commit();
                    TbsDownloadConfig.getInstance(context).setInstallInterruptCode(-517);
                    if (i2 == 88888888) {
                        a(i2, str, context);
                    }
                    if (this.k) {
                        TbsLogReport a5 = TbsLogReport.a(context);
                        if (ai.a(context).d() == 1) {
                            i5 = TbsListener.ErrorCode.INCRUPDATE_INSTALL_SUCCESS;
                        }
                        a5.a(i5, "continueInstallWithout core success");
                    } else {
                        TbsLogReport.a(context).a(ai.a(context).d() == 1 ? TbsListener.ErrorCode.INCRUPDATE_INSTALL_SUCCESS : 200, "success");
                    }
                } else if (i3 == 2) {
                    QbSdk.l.onInstallFinish(200);
                }
                i.unlock();
                j.unlock();
                b();
                return;
            }
            TbsDownloadConfig.getInstance(context).setInstallInterruptCode(-519);
            b();
        }
    }

    private boolean b(Context context, File file) {
        try {
            File[] listFiles = file.listFiles(new au(this));
            int length = listFiles.length;
            if (Build.VERSION.SDK_INT < 16 && context.getPackageName() != null && context.getPackageName().equalsIgnoreCase(TbsConfig.APP_DEMO)) {
                try {
                    Thread.sleep(Constants.ACTIVE_THREAD_WATCHDOG);
                } catch (Exception e2) {
                }
            }
            ClassLoader classLoader = context.getClassLoader();
            for (int i2 = 0; i2 < length; i2++) {
                TbsLog.i("TbsInstaller", "jarFile: " + listFiles[i2].getAbsolutePath());
                new DexClassLoader(listFiles[i2].getAbsolutePath(), file.getAbsolutePath(), (String) null, classLoader);
            }
            return true;
        } catch (Exception e3) {
            e3.printStackTrace();
            TbsLogReport.a(context).a((int) TbsListener.ErrorCode.DEXOPT_EXCEPTION, e3.toString());
            TbsLog.i("TbsInstaller", "TbsInstaller-doTbsDexOpt done");
            return false;
        }
    }

    private boolean c(Context context, int i2) {
        File q;
        boolean z;
        boolean z2 = false;
        TbsLog.i("TbsInstaller", "TbsInstaller-doTbsDexOpt start - dirMode: " + i2);
        switch (i2) {
            case 0:
                if (TbsDownloadConfig.getInstance(context).mPreferences.getInt(TbsDownloadConfig.TbsConfigKey.KEY_DOWNLOADDECOUPLECORE, 0) != 1) {
                    q = t(context);
                    break;
                } else {
                    return true;
                }
            case 1:
                q = v(context);
                break;
            case 2:
                q = q(context);
                break;
            default:
                try {
                    TbsLog.e("TbsInstaller", "doDexoptOrDexoat mode error: " + i2);
                    return false;
                } catch (Exception e2) {
                    e2.printStackTrace();
                    TbsLogReport.a(context).a((int) TbsListener.ErrorCode.DEXOPT_EXCEPTION, e2.toString());
                    TbsLog.i("TbsInstaller", "TbsInstaller-doTbsDexOpt done");
                    return true;
                }
        }
        try {
            String property = System.getProperty("java.vm.version");
            z = property != null && property.startsWith("2");
        } catch (Throwable th) {
            TbsLogReport.a(context).a((int) TbsListener.ErrorCode.DEXOAT_EXCEPTION, th);
            z = false;
        }
        boolean z3 = Build.VERSION.SDK_INT == 23;
        boolean z4 = TbsDownloadConfig.getInstance(context).mPreferences.getBoolean(TbsDownloadConfig.TbsConfigKey.KEY_STOP_PRE_OAT, false);
        if (z && z3 && !z4) {
            z2 = true;
        }
        if (!z2 || !c(context, q)) {
            return b(context, q);
        }
        return true;
    }

    private boolean c(Context context, File file) {
        try {
            File file2 = new File(file, "tbs_sdk_extension_dex.jar");
            File file3 = new File(file, "tbs_sdk_extension_dex.dex");
            new DexClassLoader(file2.getAbsolutePath(), file.getAbsolutePath(), (String) null, context.getClassLoader());
            String a2 = g.a(context, file3.getAbsolutePath());
            if (TextUtils.isEmpty(a2)) {
                TbsLogReport.a(context).a((int) TbsListener.ErrorCode.DEXOAT_EXCEPTION, "can not find oat command");
                return false;
            }
            for (File file4 : file.listFiles(new av(this))) {
                String substring = file4.getName().substring(0, file4.getName().length() - 4);
                Runtime.getRuntime().exec("/system/bin/dex2oat " + a2.replaceAll("tbs_sdk_extension_dex", substring) + " --dex-location=" + a().q(context) + File.separator + substring + ".jar").waitFor();
            }
            return true;
        } catch (Exception e2) {
            e2.printStackTrace();
            TbsLogReport.a(context).a((int) TbsListener.ErrorCode.DEXOAT_EXCEPTION, (Throwable) e2);
            return false;
        }
    }

    private synchronized boolean c(Context context, boolean z) {
        boolean z2 = false;
        synchronized (this) {
            TbsLog.i("TbsInstaller", "TbsInstaller-enableTbsCoreFromCopy");
            try {
                if (w(context)) {
                    boolean tryLock = i.tryLock();
                    TbsLog.i("TbsInstaller", "TbsInstaller-enableTbsCoreFromCopy Locked =" + tryLock);
                    if (tryLock) {
                        int b2 = ai.a(context).b("copy_status");
                        int a2 = a(false, context);
                        TbsLog.i("TbsInstaller", "TbsInstaller-enableTbsCoreFromCopy copyStatus =" + b2);
                        TbsLog.i("TbsInstaller", "TbsInstaller-enableTbsCoreFromCopy tbsCoreInstalledVer =" + a2);
                        if (b2 == 1) {
                            if (a2 == 0) {
                                TbsLog.i("TbsInstaller", "TbsInstaller-enableTbsCoreFromCopy tbsCoreInstalledVer = 0", true);
                                B(context);
                                z2 = true;
                            } else if (z) {
                                TbsLog.i("TbsInstaller", "TbsInstaller-enableTbsCoreFromCopy tbsCoreInstalledVer != 0", true);
                                B(context);
                                z2 = true;
                            }
                        }
                        i.unlock();
                    }
                    b();
                }
            } catch (Throwable th) {
                TbsLogReport.a(context).a((int) TbsListener.ErrorCode.COPY_EXCEPTION, th.toString());
                QbSdk.a(context, "TbsInstaller::enableTbsCoreFromCopy exception:" + Log.getStackTraceString(th));
            }
        }
        return z2;
    }

    private Context d(Context context, int i2) {
        Context a2;
        TbsLog.i("TbsInstaller", "TbsInstaller--getTbsCoreHostContext tbsCoreTargetVer=" + i2);
        if (i2 <= 0) {
            return null;
        }
        String[] coreProviderAppList = TbsShareManager.getCoreProviderAppList();
        for (int i3 = 0; i3 < coreProviderAppList.length; i3++) {
            if (!context.getPackageName().equalsIgnoreCase(coreProviderAppList[i3]) && e(context, coreProviderAppList[i3]) && (a2 = a(context, coreProviderAppList[i3])) != null) {
                if (!f(a2)) {
                    TbsLog.e("TbsInstaller", "TbsInstaller--getTbsCoreHostContext " + coreProviderAppList[i3] + " illegal signature go on next");
                } else {
                    int i4 = i(a2);
                    TbsLog.i("TbsInstaller", "TbsInstaller-getTbsCoreHostContext hostTbsCoreVer=" + i4);
                    if (i4 != 0 && i4 == i2) {
                        TbsLog.i("TbsInstaller", "TbsInstaller-getTbsCoreHostContext targetApp=" + coreProviderAppList[i3]);
                        return a2;
                    }
                }
            }
        }
        return null;
    }

    private static boolean d(Context context, String str) {
        File file = new File(context.getDir("tbs", 0), str);
        if (file == null || !file.exists()) {
            TbsLog.i("TbsInstaller", "TbsInstaller-isPrepareTbsCore, #1");
            return false;
        }
        File file2 = new File(file, "tbs.conf");
        if (file2 == null || !file2.exists()) {
            TbsLog.i("TbsInstaller", "TbsInstaller-isPrepareTbsCore, #2");
            return false;
        }
        TbsLog.i("TbsInstaller", "TbsInstaller-isPrepareTbsCore, #3");
        return true;
    }

    private synchronized boolean d(Context context, boolean z) {
        boolean z2 = false;
        synchronized (this) {
            TbsLog.i("TbsInstaller", "TbsInstaller-enableTbsCoreFromUnzip canRenameTmpDir =" + z);
            try {
                if (w(context)) {
                    boolean tryLock = i.tryLock();
                    TbsLog.i("TbsInstaller", "TbsInstaller-enableTbsCoreFromUnzip locked=" + tryLock);
                    if (tryLock) {
                        int c2 = ai.a(context).c();
                        TbsLog.i("TbsInstaller", "TbsInstaller-enableTbsCoreFromUnzip installStatus=" + c2);
                        int a2 = a(false, context);
                        if (c2 == 2) {
                            if (a2 == 0) {
                                TbsLog.i("TbsInstaller", "TbsInstaller-enableTbsCoreFromUnzip tbsCoreInstalledVer = 0", false);
                                A(context);
                                z2 = true;
                            } else if (z) {
                                TbsLog.i("TbsInstaller", "TbsInstaller-enableTbsCoreFromUnzip tbsCoreInstalledVer != 0", false);
                                A(context);
                                z2 = true;
                            }
                        }
                        i.unlock();
                    }
                    b();
                }
            } catch (Exception e2) {
                QbSdk.a(context, "TbsInstaller::enableTbsCoreFromUnzip Exception: " + e2);
                e2.printStackTrace();
            } catch (Throwable th) {
                i.unlock();
                throw th;
            }
        }
        return z2;
    }

    private boolean e(Context context, String str) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(str, 0);
        } catch (PackageManager.NameNotFoundException e2) {
            packageInfo = null;
        }
        return packageInfo != null;
    }

    private synchronized boolean e(Context context, boolean z) {
        return false;
    }

    private void f(Context context, boolean z) {
        if (context == null) {
            TbsLogReport.a(context).a((int) TbsListener.ErrorCode.CREATE_TEMP_CONF_ERROR, "setTmpFolderCoreToRead context is null");
            return;
        }
        try {
            File file = new File(context.getDir("tbs", 0), "tmp_folder_core_to_read.conf");
            if (!z) {
                k.b(file);
            } else if (file == null || !file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e2) {
            TbsLogReport.a(context).a((int) TbsListener.ErrorCode.CREATE_TEMP_CONF_ERROR, "setTmpFolderCoreToRead Exception message is " + e2.getMessage() + " Exception cause is " + e2.getCause());
        }
    }

    static File s(Context context) {
        File file = new File(context.getDir("tbs", 0), "core_private");
        if (file == null) {
            return null;
        }
        if (file.isDirectory() || file.mkdir()) {
            return file;
        }
        return null;
    }

    private static boolean x(Context context) {
        if (context == null) {
            TbsLog.i("TbsInstaller", "TbsInstaller-getTmpFolderCoreToRead, #1");
            return true;
        }
        try {
            if (new File(context.getDir("tbs", 0), "tmp_folder_core_to_read.conf").exists()) {
                TbsLog.i("TbsInstaller", "TbsInstaller-getTmpFolderCoreToRead, #2");
                return true;
            }
            TbsLog.i("TbsInstaller", "TbsInstaller-getTmpFolderCoreToRead, #3");
            return false;
        } catch (Exception e2) {
            TbsLog.i("TbsInstaller", "TbsInstaller-getTmpFolderCoreToRead, #4");
            return true;
        }
    }

    private void y(Context context) {
        boolean z = true;
        try {
            z = TbsDownloadConfig.getInstance().getTbsCoreLoadRenameFileLockEnable();
        } catch (Throwable th) {
        }
        if (z && l != null) {
            k.a(context, l);
        }
    }

    private boolean z(Context context) {
        boolean z;
        try {
            z = TbsDownloadConfig.getInstance().getTbsCoreLoadRenameFileLockEnable();
        } catch (Throwable th) {
            z = true;
        }
        if (!z) {
            l = br.a().b(context);
        } else {
            l = k.f(context);
        }
        return l != null;
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0051 A[SYNTHETIC, Splitter:B:28:0x0051] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int a(java.lang.String r7) {
        /*
            r6 = this;
            r0 = 0
            if (r7 != 0) goto L_0x0004
        L_0x0003:
            return r0
        L_0x0004:
            r2 = 0
            java.io.File r1 = new java.io.File     // Catch:{ Exception -> 0x0055, all -> 0x004e }
            r1.<init>(r7)     // Catch:{ Exception -> 0x0055, all -> 0x004e }
            java.io.File r3 = new java.io.File     // Catch:{ Exception -> 0x0055, all -> 0x004e }
            java.lang.String r4 = "tbs.conf"
            r3.<init>(r1, r4)     // Catch:{ Exception -> 0x0055, all -> 0x004e }
            if (r3 == 0) goto L_0x0019
            boolean r1 = r3.exists()     // Catch:{ Exception -> 0x0055, all -> 0x004e }
            if (r1 != 0) goto L_0x0021
        L_0x0019:
            if (r2 == 0) goto L_0x0003
            r2.close()     // Catch:{ IOException -> 0x001f }
            goto L_0x0003
        L_0x001f:
            r1 = move-exception
            goto L_0x0003
        L_0x0021:
            java.util.Properties r4 = new java.util.Properties     // Catch:{ Exception -> 0x0055, all -> 0x004e }
            r4.<init>()     // Catch:{ Exception -> 0x0055, all -> 0x004e }
            java.io.FileInputStream r5 = new java.io.FileInputStream     // Catch:{ Exception -> 0x0055, all -> 0x004e }
            r5.<init>(r3)     // Catch:{ Exception -> 0x0055, all -> 0x004e }
            java.io.BufferedInputStream r1 = new java.io.BufferedInputStream     // Catch:{ Exception -> 0x0055, all -> 0x004e }
            r1.<init>(r5)     // Catch:{ Exception -> 0x0055, all -> 0x004e }
            r4.load(r1)     // Catch:{ Exception -> 0x0062, all -> 0x005f }
            r1.close()     // Catch:{ Exception -> 0x0062, all -> 0x005f }
            java.lang.String r2 = "tbs_core_version"
            java.lang.String r2 = r4.getProperty(r2)     // Catch:{ Exception -> 0x0062, all -> 0x005f }
            if (r2 != 0) goto L_0x0044
            if (r1 == 0) goto L_0x0003
            r1.close()     // Catch:{ IOException -> 0x001f }
            goto L_0x0003
        L_0x0044:
            int r0 = java.lang.Integer.parseInt(r2)     // Catch:{ Exception -> 0x0062, all -> 0x005f }
            if (r1 == 0) goto L_0x0003
            r1.close()     // Catch:{ IOException -> 0x001f }
            goto L_0x0003
        L_0x004e:
            r0 = move-exception
        L_0x004f:
            if (r2 == 0) goto L_0x0054
            r2.close()     // Catch:{ IOException -> 0x005d }
        L_0x0054:
            throw r0
        L_0x0055:
            r1 = move-exception
            r1 = r2
        L_0x0057:
            if (r1 == 0) goto L_0x0003
            r1.close()     // Catch:{ IOException -> 0x001f }
            goto L_0x0003
        L_0x005d:
            r1 = move-exception
            goto L_0x0054
        L_0x005f:
            r0 = move-exception
            r2 = r1
            goto L_0x004f
        L_0x0062:
            r2 = move-exception
            goto L_0x0057
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.am.a(java.lang.String):int");
    }

    public int a(boolean z, Context context) {
        if (z || a.get().intValue() <= 0) {
            a.set(Integer.valueOf(i(context)));
        }
        return a.get().intValue();
    }

    /* access modifiers changed from: package-private */
    public Context a(Context context, String str) {
        try {
            return context.createPackageContext(str, 2);
        } catch (Exception e2) {
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    public void a(Context context, Bundle bundle) {
        if (bundle != null && context != null) {
            Message message = new Message();
            message.what = 3;
            message.obj = new Object[]{context, bundle};
            m.sendMessage(message);
        }
    }

    /* access modifiers changed from: package-private */
    public void a(Context context, String str, int i2) {
        TbsLog.i("TbsInstaller", "TbsInstaller-installTbsCore tbsApkPath=" + str);
        TbsLog.i("TbsInstaller", "TbsInstaller-installTbsCore tbsCoreTargetVer=" + i2);
        TbsLog.i("TbsInstaller", "TbsInstaller-continueInstallTbsCore currentProcessName=" + context.getApplicationInfo().processName);
        TbsLog.i("TbsInstaller", "TbsInstaller-installTbsCore currentProcessId=" + Process.myPid());
        TbsLog.i("TbsInstaller", "TbsInstaller-installTbsCore currentThreadName=" + Thread.currentThread().getName());
        Object[] objArr = {context, str, Integer.valueOf(i2)};
        Message message = new Message();
        message.what = 1;
        message.obj = objArr;
        m.sendMessage(message);
    }

    /* access modifiers changed from: package-private */
    public void a(Context context, boolean z) {
        int i2;
        int i3;
        int i4;
        int i5;
        boolean z2 = false;
        if (z) {
            this.k = true;
        }
        TbsLog.i("TbsInstaller", "TbsInstaller-continueInstallTbsCore currentProcessName=" + context.getApplicationInfo().processName);
        TbsLog.i("TbsInstaller", "TbsInstaller-continueInstallTbsCore currentProcessId=" + Process.myPid());
        TbsLog.i("TbsInstaller", "TbsInstaller-continueInstallTbsCore currentThreadName=" + Thread.currentThread().getName());
        if (w(context)) {
            String str = null;
            if (i.tryLock()) {
                try {
                    i5 = ai.a(context).c();
                    i4 = ai.a(context).b();
                    str = ai.a(context).d("install_apk_path");
                    i2 = ai.a(context).c("copy_core_ver");
                    i3 = ai.a(context).b("copy_status");
                } finally {
                    i.unlock();
                }
            } else {
                i2 = 0;
                i3 = -1;
                i4 = 0;
                i5 = -1;
            }
            b();
            TbsLog.i("TbsInstaller", "TbsInstaller-continueInstallTbsCore installStatus=" + i5);
            TbsLog.i("TbsInstaller", "TbsInstaller-continueInstallTbsCore tbsCoreInstallVer=" + i4);
            TbsLog.i("TbsInstaller", "TbsInstaller-continueInstallTbsCore tbsApkPath=" + str);
            TbsLog.i("TbsInstaller", "TbsInstaller-continueInstallTbsCore tbsCoreCopyVer=" + i2);
            TbsLog.i("TbsInstaller", "TbsInstaller-continueInstallTbsCore copyStatus=" + i3);
            if (TbsShareManager.isThirdPartyApp(context)) {
                b(context, TbsShareManager.a(context, false));
                return;
            }
            int i6 = TbsDownloadConfig.getInstance(context).mPreferences.getInt(TbsDownloadConfig.TbsConfigKey.KEY_RESPONSECODE, 0);
            if (i6 == 1 || i6 == 2 || i6 == 4) {
                z2 = true;
            }
            if (!z2 && i6 != 0) {
                Bundle bundle = new Bundle();
                bundle.putInt("operation", 10001);
                a(context, bundle);
            }
            if (i5 > -1 && i5 < 2) {
                a(context, str, i4);
            }
            if (i3 == 0) {
                a(context, i2);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public boolean a(Context context, int i2) {
        if (TbsDownloader.getOverSea(context)) {
            return false;
        }
        TbsLog.i("TbsInstaller", "TbsInstaller-installLocalTbsCore targetTbsCoreVer=" + i2);
        TbsLog.i("TbsInstaller", "TbsInstaller-continueInstallTbsCore currentProcessName=" + context.getApplicationInfo().processName);
        TbsLog.i("TbsInstaller", "TbsInstaller-installLocalTbsCore currentProcessId=" + Process.myPid());
        TbsLog.i("TbsInstaller", "TbsInstaller-installLocalTbsCore currentThreadName=" + Thread.currentThread().getName());
        Context d2 = d(context, i2);
        if (d2 != null) {
            Object[] objArr = {d2, context, Integer.valueOf(i2)};
            Message message = new Message();
            message.what = 2;
            message.obj = objArr;
            m.sendMessage(message);
            return true;
        }
        TbsLog.i("TbsInstaller", "TbsInstaller--installLocalTbsCore copy from null");
        return false;
    }

    public synchronized boolean a(Context context, Context context2) {
        TbsLog.i("TbsInstaller", "TbsInstaller--quickDexOptForThirdPartyApp");
        if (!p) {
            p = true;
            new aq(this, context2, context).start();
        }
        return true;
    }

    public boolean a(Context context, File[] fileArr) {
        return false;
    }

    /* access modifiers changed from: package-private */
    public int b(Context context, String str) {
        PackageInfo packageArchiveInfo = context.getPackageManager().getPackageArchiveInfo(str, 0);
        if (packageArchiveInfo != null) {
            return packageArchiveInfo.versionCode;
        }
        return 0;
    }

    /* access modifiers changed from: package-private */
    public File b(Context context, Context context2) {
        File file = new File(context2.getDir("tbs", 0), "core_share");
        if (file == null) {
            return null;
        }
        if (file.isDirectory() || ((context != null && TbsShareManager.isThirdPartyApp(context)) || file.mkdir())) {
            return file;
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public synchronized void b() {
        int i2 = this.e;
        this.e = i2 - 1;
        if (i2 > 1 || !this.h) {
            TbsLog.i("TbsInstaller", "releaseTbsInstallingFileLock with skip");
        } else {
            TbsLog.i("TbsInstaller", "releaseTbsInstallingFileLock without skip");
            k.a(this.f, this.g);
            this.h = false;
        }
    }

    public void b(Context context) {
        f(context, true);
        ai.a(context).b(h(context), 2);
    }

    /* access modifiers changed from: package-private */
    public void b(Context context, int i2) {
        int i3;
        TbsLog.i("TbsInstaller", "TbsInstaller-installTbsCoreForThirdPartyApp");
        if (i2 > 0 && (i3 = i(context)) != i2) {
            Context e2 = TbsShareManager.e(context);
            if (e2 != null || TbsShareManager.getHostCorePathAppDefined() != null) {
                TbsLog.i("TbsInstaller", "TbsInstaller--quickDexOptForThirdPartyApp hostContext != null");
                a(context, e2);
            } else if (i3 <= 0) {
                TbsLog.i("TbsInstaller", "TbsInstaller--installTbsCoreForThirdPartyApp hostContext == null");
                QbSdk.a(context, "TbsInstaller::installTbsCoreForThirdPartyApp forceSysWebViewInner #2");
            }
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x019a  */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x026c  */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x02bd  */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x0306  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void b(android.content.Context r11, android.os.Bundle r12) {
        /*
            r10 = this;
            r9 = 217(0xd9, float:3.04E-43)
            r8 = -546(0xfffffffffffffdde, float:NaN)
            r3 = 2
            r4 = 1
            r5 = 0
            boolean r0 = r10.c(r11)
            if (r0 == 0) goto L_0x0017
            com.tencent.smtt.sdk.TbsDownloadConfig r0 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r11)
            r1 = -539(0xfffffffffffffde5, float:NaN)
            r0.setInstallInterruptCode(r1)
        L_0x0016:
            return
        L_0x0017:
            java.lang.String r0 = "TbsInstaller"
            java.lang.String r1 = "TbsInstaller-installLocalTesCoreExInThread"
            com.tencent.smtt.utils.TbsLog.i(r0, r1)
            if (r12 == 0) goto L_0x0016
            if (r11 == 0) goto L_0x0016
            boolean r0 = com.tencent.smtt.utils.k.b((android.content.Context) r11)
            if (r0 != 0) goto L_0x0064
            long r0 = com.tencent.smtt.utils.aa.a()
            com.tencent.smtt.sdk.TbsDownloadConfig r2 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r11)
            long r2 = r2.getDownloadMinFreeSpace()
            com.tencent.smtt.sdk.TbsLogReport r4 = com.tencent.smtt.sdk.TbsLogReport.a((android.content.Context) r11)
            r5 = 210(0xd2, float:2.94E-43)
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "rom is not enough when patching tbs core! curAvailROM="
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.StringBuilder r0 = r6.append(r0)
            java.lang.String r1 = ",minReqRom="
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.StringBuilder r0 = r0.append(r2)
            java.lang.String r0 = r0.toString()
            r4.a((int) r5, (java.lang.String) r0)
            com.tencent.smtt.sdk.TbsDownloadConfig r0 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r11)
            r1 = -540(0xfffffffffffffde4, float:NaN)
            r0.setInstallInterruptCode(r1)
            goto L_0x0016
        L_0x0064:
            boolean r0 = r10.w(r11)
            if (r0 != 0) goto L_0x0074
            com.tencent.smtt.sdk.TbsDownloadConfig r0 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r11)
            r1 = -541(0xfffffffffffffde3, float:NaN)
            r0.setInstallInterruptCode(r1)
            goto L_0x0016
        L_0x0074:
            java.util.concurrent.locks.Lock r0 = j
            boolean r0 = r0.tryLock()
            java.lang.String r1 = "TbsInstaller"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r6 = "TbsInstaller-installLocalTesCoreExInThread locked="
            java.lang.StringBuilder r2 = r2.append(r6)
            java.lang.StringBuilder r2 = r2.append(r0)
            java.lang.String r2 = r2.toString()
            com.tencent.smtt.utils.TbsLog.i(r1, r2)
            if (r0 == 0) goto L_0x02af
            r1 = 0
            r0 = 1
            com.tencent.smtt.sdk.QbSdk.setTBSInstallingStatus(r0)     // Catch:{ Exception -> 0x01e2 }
            int r0 = r10.i(r11)     // Catch:{ Exception -> 0x01e2 }
            if (r0 <= 0) goto L_0x00a9
            com.tencent.smtt.sdk.ai r0 = com.tencent.smtt.sdk.ai.a((android.content.Context) r11)     // Catch:{ Exception -> 0x01e2 }
            int r0 = r0.d()     // Catch:{ Exception -> 0x01e2 }
            if (r0 != r4) goto L_0x00c1
        L_0x00a9:
            r0 = 0
            com.tencent.smtt.sdk.QbSdk.setTBSInstallingStatus(r0)     // Catch:{ Exception -> 0x01e2 }
            java.util.concurrent.locks.Lock r0 = j
            r0.unlock()
            r10.b()
            java.lang.String r0 = "TbsInstaller"
            java.lang.String r1 = "TbsInstaller-installLocalTesCoreExInThread PATCH_NONEEDPATCH"
            com.tencent.smtt.utils.TbsLog.i(r0, r1)
        L_0x00bc:
            com.tencent.smtt.sdk.QbSdk.setTBSInstallingStatus(r5)
            goto L_0x0016
        L_0x00c1:
            com.tencent.smtt.sdk.TbsDownloadConfig r0 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r11)     // Catch:{ Exception -> 0x01e2 }
            android.content.SharedPreferences r0 = r0.mPreferences     // Catch:{ Exception -> 0x01e2 }
            java.lang.String r2 = "tbs_responsecode"
            r6 = 0
            int r2 = r0.getInt(r2, r6)     // Catch:{ Exception -> 0x01e2 }
            if (r2 == r4) goto L_0x00d5
            if (r2 == r3) goto L_0x00d5
            r0 = 4
            if (r2 != r0) goto L_0x015d
        L_0x00d5:
            r0 = r4
        L_0x00d6:
            if (r0 != 0) goto L_0x0355
            if (r2 == 0) goto L_0x0355
            com.tencent.smtt.sdk.ai r0 = com.tencent.smtt.sdk.ai.a((android.content.Context) r11)     // Catch:{ Exception -> 0x01e2 }
            java.lang.String r2 = "incrupdate_retry_num"
            int r0 = r0.c((java.lang.String) r2)     // Catch:{ Exception -> 0x01e2 }
            r2 = 5
            if (r0 <= r2) goto L_0x0160
            java.lang.String r0 = "TbsInstaller"
            java.lang.String r2 = "TbsInstaller-installLocalTesCoreExInThread exceed incrupdate num"
            com.tencent.smtt.utils.TbsLog.i(r0, r2)     // Catch:{ Exception -> 0x01e2 }
            java.lang.String r0 = "old_apk_location"
            java.lang.String r0 = r12.getString(r0)     // Catch:{ Exception -> 0x01e2 }
            java.lang.String r2 = "new_apk_location"
            java.lang.String r2 = r12.getString(r2)     // Catch:{ Exception -> 0x01e2 }
            java.lang.String r6 = "diff_file_location"
            java.lang.String r6 = r12.getString(r6)     // Catch:{ Exception -> 0x01e2 }
            boolean r7 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Exception -> 0x01e2 }
            if (r7 != 0) goto L_0x010e
            java.io.File r7 = new java.io.File     // Catch:{ Exception -> 0x01e2 }
            r7.<init>(r0)     // Catch:{ Exception -> 0x01e2 }
            com.tencent.smtt.utils.k.b((java.io.File) r7)     // Catch:{ Exception -> 0x01e2 }
        L_0x010e:
            boolean r0 = android.text.TextUtils.isEmpty(r2)     // Catch:{ Exception -> 0x01e2 }
            if (r0 != 0) goto L_0x011c
            java.io.File r0 = new java.io.File     // Catch:{ Exception -> 0x01e2 }
            r0.<init>(r2)     // Catch:{ Exception -> 0x01e2 }
            com.tencent.smtt.utils.k.b((java.io.File) r0)     // Catch:{ Exception -> 0x01e2 }
        L_0x011c:
            boolean r0 = android.text.TextUtils.isEmpty(r6)     // Catch:{ Exception -> 0x01e2 }
            if (r0 != 0) goto L_0x012a
            java.io.File r0 = new java.io.File     // Catch:{ Exception -> 0x01e2 }
            r0.<init>(r6)     // Catch:{ Exception -> 0x01e2 }
            com.tencent.smtt.utils.k.b((java.io.File) r0)     // Catch:{ Exception -> 0x01e2 }
        L_0x012a:
            com.tencent.smtt.sdk.TbsDownloadConfig r0 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r11)     // Catch:{ Exception -> 0x01e2 }
            java.util.Map<java.lang.String, java.lang.Object> r0 = r0.a     // Catch:{ Exception -> 0x01e2 }
            java.lang.String r2 = "tbs_needdownload"
            r6 = 1
            java.lang.Boolean r6 = java.lang.Boolean.valueOf(r6)     // Catch:{ Exception -> 0x01e2 }
            r0.put(r2, r6)     // Catch:{ Exception -> 0x01e2 }
            com.tencent.smtt.sdk.TbsDownloadConfig r0 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r11)     // Catch:{ Exception -> 0x01e2 }
            r0.commit()     // Catch:{ Exception -> 0x01e2 }
            com.tencent.smtt.sdk.TbsLogReport r0 = com.tencent.smtt.sdk.TbsLogReport.a((android.content.Context) r11)     // Catch:{ Exception -> 0x01e2 }
            r2 = 224(0xe0, float:3.14E-43)
            java.lang.String r6 = "incrUpdate exceed retry max num"
            r0.a((int) r2, (java.lang.String) r6)     // Catch:{ Exception -> 0x01e2 }
            java.util.concurrent.locks.Lock r0 = j
            r0.unlock()
            r10.b()
            java.lang.String r0 = "TbsInstaller"
            java.lang.String r1 = "TbsInstaller-installLocalTesCoreExInThread PATCH_NONEEDPATCH"
            com.tencent.smtt.utils.TbsLog.i(r0, r1)
            goto L_0x00bc
        L_0x015d:
            r0 = r5
            goto L_0x00d6
        L_0x0160:
            com.tencent.smtt.sdk.ai r2 = com.tencent.smtt.sdk.ai.a((android.content.Context) r11)     // Catch:{ Exception -> 0x01e2 }
            java.lang.String r6 = "incrupdate_retry_num"
            int r0 = r0 + 1
            r2.a((java.lang.String) r6, (int) r0)     // Catch:{ Exception -> 0x01e2 }
            java.io.File r0 = s(r11)     // Catch:{ Exception -> 0x01e2 }
            if (r0 == 0) goto L_0x0355
            java.io.File r2 = new java.io.File     // Catch:{ Exception -> 0x01e2 }
            java.lang.String r6 = "x5.tbs"
            r2.<init>(r0, r6)     // Catch:{ Exception -> 0x01e2 }
            if (r2 == 0) goto L_0x0355
            boolean r0 = r2.exists()     // Catch:{ Exception -> 0x01e2 }
            if (r0 == 0) goto L_0x0355
            com.tencent.smtt.sdk.TbsDownloadConfig r0 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r11)     // Catch:{ Exception -> 0x01e2 }
            r2 = -550(0xfffffffffffffdda, float:NaN)
            r0.setInstallInterruptCode(r2)     // Catch:{ Exception -> 0x01e2 }
            android.os.Bundle r1 = com.tencent.smtt.sdk.QbSdk.a((android.content.Context) r11, (android.os.Bundle) r12)     // Catch:{ Exception -> 0x01e2 }
            if (r1 != 0) goto L_0x01db
            r0 = r4
        L_0x0190:
            java.util.concurrent.locks.Lock r2 = j
            r2.unlock()
            r10.b()
            if (r0 != 0) goto L_0x0306
            java.lang.String r0 = "TbsInstaller"
            java.lang.String r2 = "TbsInstaller-installLocalTesCoreExInThread PATCH_SUCCESS"
            com.tencent.smtt.utils.TbsLog.i(r0, r2)
            com.tencent.smtt.sdk.ai r0 = com.tencent.smtt.sdk.ai.a((android.content.Context) r11)
            java.lang.String r2 = "incrupdate_retry_num"
            r0.a((java.lang.String) r2, (int) r5)
            com.tencent.smtt.sdk.TbsDownloadConfig r0 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r11)
            r2 = -544(0xfffffffffffffde0, float:NaN)
            r0.setInstallInterruptCode(r2)
            com.tencent.smtt.sdk.ai r0 = com.tencent.smtt.sdk.ai.a((android.content.Context) r11)
            r2 = -1
            r0.b(r5, r2)
            com.tencent.smtt.sdk.ai r0 = com.tencent.smtt.sdk.ai.a((android.content.Context) r11)
            r0.c((int) r4)
            java.lang.String r0 = "apk_path"
            java.lang.String r0 = r1.getString(r0)
            java.io.File r2 = new java.io.File
            r2.<init>(r0)
            com.tencent.smtt.sdk.ag.a((java.io.File) r2, (android.content.Context) r11)
            java.lang.String r2 = "tbs_core_ver"
            int r1 = r1.getInt(r2)
            r10.b(r11, r0, r1)
            goto L_0x00bc
        L_0x01db:
            java.lang.String r0 = "patch_result"
            int r0 = r1.getInt(r0)     // Catch:{ Exception -> 0x01e2 }
            goto L_0x0190
        L_0x01e2:
            r0 = move-exception
            java.lang.String r2 = "TbsInstaller"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x0260 }
            r6.<init>()     // Catch:{ all -> 0x0260 }
            java.lang.String r7 = "installLocalTbsCoreExInThread exception:"
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ all -> 0x0260 }
            java.lang.String r7 = android.util.Log.getStackTraceString(r0)     // Catch:{ all -> 0x0260 }
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ all -> 0x0260 }
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x0260 }
            com.tencent.smtt.utils.TbsLog.i(r2, r6)     // Catch:{ all -> 0x0260 }
            r0.printStackTrace()     // Catch:{ all -> 0x0260 }
            com.tencent.smtt.sdk.TbsDownloadConfig r2 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r11)     // Catch:{ all -> 0x0351 }
            r6 = -543(0xfffffffffffffde1, float:NaN)
            r2.setInstallInterruptCode(r6)     // Catch:{ all -> 0x0351 }
            com.tencent.smtt.sdk.TbsLogReport r2 = com.tencent.smtt.sdk.TbsLogReport.a((android.content.Context) r11)     // Catch:{ all -> 0x0351 }
            r6 = 218(0xda, float:3.05E-43)
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0351 }
            r2.a((int) r6, (java.lang.String) r0)     // Catch:{ all -> 0x0351 }
            java.util.concurrent.locks.Lock r0 = j
            r0.unlock()
            r10.b()
            com.tencent.smtt.sdk.TbsDownloadConfig r0 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r11)
            r0.setInstallInterruptCode(r8)
            java.lang.String r0 = "TbsInstaller"
            java.lang.String r1 = "TbsInstaller-installLocalTesCoreExInThread PATCH_FAIL"
            com.tencent.smtt.utils.TbsLog.i(r0, r1)
            com.tencent.smtt.sdk.TbsDownloadConfig r0 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r11)
            java.util.Map<java.lang.String, java.lang.Object> r0 = r0.a
            java.lang.String r1 = "tbs_needdownload"
            java.lang.Boolean r2 = java.lang.Boolean.valueOf(r4)
            r0.put(r1, r2)
            com.tencent.smtt.sdk.TbsDownloadConfig r0 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r11)
            r0.commit()
            com.tencent.smtt.sdk.TbsLogReport r0 = com.tencent.smtt.sdk.TbsLogReport.a((android.content.Context) r11)
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "incrUpdate fail! patch ret="
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r4)
            java.lang.String r1 = r1.toString()
            r0.a((int) r9, (java.lang.String) r1)
            goto L_0x00bc
        L_0x0260:
            r0 = move-exception
            r2 = r3
        L_0x0262:
            java.util.concurrent.locks.Lock r6 = j
            r6.unlock()
            r10.b()
            if (r2 != 0) goto L_0x02bd
            java.lang.String r2 = "TbsInstaller"
            java.lang.String r3 = "TbsInstaller-installLocalTesCoreExInThread PATCH_SUCCESS"
            com.tencent.smtt.utils.TbsLog.i(r2, r3)
            com.tencent.smtt.sdk.ai r2 = com.tencent.smtt.sdk.ai.a((android.content.Context) r11)
            java.lang.String r3 = "incrupdate_retry_num"
            r2.a((java.lang.String) r3, (int) r5)
            com.tencent.smtt.sdk.TbsDownloadConfig r2 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r11)
            r3 = -544(0xfffffffffffffde0, float:NaN)
            r2.setInstallInterruptCode(r3)
            com.tencent.smtt.sdk.ai r2 = com.tencent.smtt.sdk.ai.a((android.content.Context) r11)
            r3 = -1
            r2.b(r5, r3)
            com.tencent.smtt.sdk.ai r2 = com.tencent.smtt.sdk.ai.a((android.content.Context) r11)
            r2.c((int) r4)
            java.lang.String r2 = "apk_path"
            java.lang.String r2 = r1.getString(r2)
            java.io.File r3 = new java.io.File
            r3.<init>(r2)
            com.tencent.smtt.sdk.ag.a((java.io.File) r3, (android.content.Context) r11)
            java.lang.String r3 = "tbs_core_ver"
            int r1 = r1.getInt(r3)
            r10.b(r11, r2, r1)
        L_0x02ab:
            com.tencent.smtt.sdk.QbSdk.setTBSInstallingStatus(r5)
            throw r0
        L_0x02af:
            com.tencent.smtt.sdk.TbsDownloadConfig r0 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r11)
            r1 = -547(0xfffffffffffffddd, float:NaN)
            r0.setInstallInterruptCode(r1)
            r10.b()
            goto L_0x0016
        L_0x02bd:
            if (r2 != r3) goto L_0x02c7
            java.lang.String r1 = "TbsInstaller"
            java.lang.String r2 = "TbsInstaller-installLocalTesCoreExInThread PATCH_NONEEDPATCH"
            com.tencent.smtt.utils.TbsLog.i(r1, r2)
            goto L_0x02ab
        L_0x02c7:
            com.tencent.smtt.sdk.TbsDownloadConfig r1 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r11)
            r1.setInstallInterruptCode(r8)
            java.lang.String r1 = "TbsInstaller"
            java.lang.String r3 = "TbsInstaller-installLocalTesCoreExInThread PATCH_FAIL"
            com.tencent.smtt.utils.TbsLog.i(r1, r3)
            com.tencent.smtt.sdk.TbsDownloadConfig r1 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r11)
            java.util.Map<java.lang.String, java.lang.Object> r1 = r1.a
            java.lang.String r3 = "tbs_needdownload"
            java.lang.Boolean r4 = java.lang.Boolean.valueOf(r4)
            r1.put(r3, r4)
            com.tencent.smtt.sdk.TbsDownloadConfig r1 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r11)
            r1.commit()
            com.tencent.smtt.sdk.TbsLogReport r1 = com.tencent.smtt.sdk.TbsLogReport.a((android.content.Context) r11)
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "incrUpdate fail! patch ret="
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.StringBuilder r2 = r3.append(r2)
            java.lang.String r2 = r2.toString()
            r1.a((int) r9, (java.lang.String) r2)
            goto L_0x02ab
        L_0x0306:
            if (r0 != r3) goto L_0x0311
            java.lang.String r0 = "TbsInstaller"
            java.lang.String r1 = "TbsInstaller-installLocalTesCoreExInThread PATCH_NONEEDPATCH"
            com.tencent.smtt.utils.TbsLog.i(r0, r1)
            goto L_0x00bc
        L_0x0311:
            com.tencent.smtt.sdk.TbsDownloadConfig r1 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r11)
            r1.setInstallInterruptCode(r8)
            java.lang.String r1 = "TbsInstaller"
            java.lang.String r2 = "TbsInstaller-installLocalTesCoreExInThread PATCH_FAIL"
            com.tencent.smtt.utils.TbsLog.i(r1, r2)
            com.tencent.smtt.sdk.TbsDownloadConfig r1 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r11)
            java.util.Map<java.lang.String, java.lang.Object> r1 = r1.a
            java.lang.String r2 = "tbs_needdownload"
            java.lang.Boolean r3 = java.lang.Boolean.valueOf(r4)
            r1.put(r2, r3)
            com.tencent.smtt.sdk.TbsDownloadConfig r1 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r11)
            r1.commit()
            com.tencent.smtt.sdk.TbsLogReport r1 = com.tencent.smtt.sdk.TbsLogReport.a((android.content.Context) r11)
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "incrUpdate fail! patch ret="
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.StringBuilder r0 = r2.append(r0)
            java.lang.String r0 = r0.toString()
            r1.a((int) r9, (java.lang.String) r0)
            goto L_0x00bc
        L_0x0351:
            r0 = move-exception
            r2 = r4
            goto L_0x0262
        L_0x0355:
            r0 = r3
            goto L_0x0190
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.am.b(android.content.Context, android.os.Bundle):void");
    }

    /* access modifiers changed from: package-private */
    public void b(Context context, boolean z) {
        File u;
        if (!QbSdk.b) {
            if (Build.VERSION.SDK_INT < 8) {
                TbsLog.e("TbsInstaller", "android version < 2.1 no need install X5 core", true);
                return;
            }
            try {
                if (!TbsShareManager.isThirdPartyApp(context) && (u = u(context)) != null && u.exists()) {
                    k.a(u, false);
                    new File(s(context), "x5.tbs").delete();
                }
            } catch (Throwable th) {
            }
            if (!x(context)) {
                return;
            }
            if (d(context, "core_unzip_tmp") && d(context, z)) {
                TbsLog.i("TbsInstaller", "TbsInstaller-installTbsCoreIfNeeded, enableTbsCoreFromUnzip!!", true);
            } else if (d(context, "core_share_backup_tmp") && e(context, z)) {
                TbsLog.i("TbsInstaller", "TbsInstaller-installTbsCoreIfNeeded, enableTbsCoreFromBackup!!", true);
            } else if (d(context, "core_copy_tmp") && c(context, z)) {
                TbsLog.i("TbsInstaller", "TbsInstaller-installTbsCoreIfNeeded, enableTbsCoreFromCopy!!", true);
            } else {
                TbsLog.i("TbsInstaller", "TbsInstaller-installTbsCoreIfNeeded, error !!", true);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public File c(Context context, Context context2) {
        File file = new File(context2.getDir("tbs", 0), "core_share_decouple");
        if (file == null) {
            return null;
        }
        if (file.isDirectory() || ((context != null && TbsShareManager.isThirdPartyApp(context)) || file.mkdir())) {
            return file;
        }
        return null;
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x0048 A[SYNTHETIC, Splitter:B:23:0x0048] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String c(android.content.Context r6, java.lang.String r7) {
        /*
            r5 = this;
            r0 = 0
            boolean r1 = android.text.TextUtils.isEmpty(r7)
            if (r1 == 0) goto L_0x0008
        L_0x0007:
            return r0
        L_0x0008:
            r1 = 0
            java.io.File r2 = r5.q(r6)     // Catch:{ Exception -> 0x004c, all -> 0x0043 }
            java.io.File r3 = new java.io.File     // Catch:{ Exception -> 0x004c, all -> 0x0043 }
            java.lang.String r4 = "tbs.conf"
            r3.<init>(r2, r4)     // Catch:{ Exception -> 0x004c, all -> 0x0043 }
            if (r3 == 0) goto L_0x001c
            boolean r2 = r3.exists()     // Catch:{ Exception -> 0x004c, all -> 0x0043 }
            if (r2 != 0) goto L_0x0024
        L_0x001c:
            if (r0 == 0) goto L_0x0007
            r1.close()     // Catch:{ IOException -> 0x0022 }
            goto L_0x0007
        L_0x0022:
            r1 = move-exception
            goto L_0x0007
        L_0x0024:
            java.util.Properties r2 = new java.util.Properties     // Catch:{ Exception -> 0x004c, all -> 0x0043 }
            r2.<init>()     // Catch:{ Exception -> 0x004c, all -> 0x0043 }
            java.io.FileInputStream r4 = new java.io.FileInputStream     // Catch:{ Exception -> 0x004c, all -> 0x0043 }
            r4.<init>(r3)     // Catch:{ Exception -> 0x004c, all -> 0x0043 }
            java.io.BufferedInputStream r1 = new java.io.BufferedInputStream     // Catch:{ Exception -> 0x004c, all -> 0x0043 }
            r1.<init>(r4)     // Catch:{ Exception -> 0x004c, all -> 0x0043 }
            r2.load(r1)     // Catch:{ Exception -> 0x005a, all -> 0x0056 }
            r1.close()     // Catch:{ Exception -> 0x005a, all -> 0x0056 }
            java.lang.String r0 = r2.getProperty(r7)     // Catch:{ Exception -> 0x005a, all -> 0x0056 }
            if (r1 == 0) goto L_0x0007
            r1.close()     // Catch:{ IOException -> 0x0022 }
            goto L_0x0007
        L_0x0043:
            r1 = move-exception
            r2 = r1
            r3 = r0
        L_0x0046:
            if (r3 == 0) goto L_0x004b
            r3.close()     // Catch:{ IOException -> 0x0054 }
        L_0x004b:
            throw r2
        L_0x004c:
            r1 = move-exception
            r1 = r0
        L_0x004e:
            if (r1 == 0) goto L_0x0007
            r1.close()     // Catch:{ IOException -> 0x0022 }
            goto L_0x0007
        L_0x0054:
            r0 = move-exception
            goto L_0x004b
        L_0x0056:
            r0 = move-exception
            r2 = r0
            r3 = r1
            goto L_0x0046
        L_0x005a:
            r2 = move-exception
            goto L_0x004e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.am.c(android.content.Context, java.lang.String):java.lang.String");
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x008a A[SYNTHETIC, Splitter:B:30:0x008a] */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0097 A[SYNTHETIC, Splitter:B:37:0x0097] */
    /* JADX WARNING: Removed duplicated region for block: B:51:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean c(android.content.Context r11) {
        /*
            r10 = this;
            r1 = 1
            r2 = 0
            java.io.File r0 = r10.q(r11)
            java.io.File r5 = new java.io.File
            java.lang.String r3 = "tbs.conf"
            r5.<init>(r0, r3)
            if (r5 == 0) goto L_0x0015
            boolean r0 = r5.exists()
            if (r0 != 0) goto L_0x0017
        L_0x0015:
            r0 = r2
        L_0x0016:
            return r0
        L_0x0017:
            java.util.Properties r0 = new java.util.Properties
            r0.<init>()
            r4 = 0
            java.io.FileInputStream r6 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x0082, all -> 0x0093 }
            r6.<init>(r5)     // Catch:{ Throwable -> 0x0082, all -> 0x0093 }
            java.io.BufferedInputStream r3 = new java.io.BufferedInputStream     // Catch:{ Throwable -> 0x0082, all -> 0x0093 }
            r3.<init>(r6)     // Catch:{ Throwable -> 0x0082, all -> 0x0093 }
            r0.load(r3)     // Catch:{ Throwable -> 0x00a2 }
            java.lang.String r4 = "tbs_local_installation"
            java.lang.String r6 = "false"
            java.lang.String r0 = r0.getProperty(r4, r6)     // Catch:{ Throwable -> 0x00a2 }
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)     // Catch:{ Throwable -> 0x00a2 }
            boolean r4 = r0.booleanValue()     // Catch:{ Throwable -> 0x00a2 }
            if (r4 == 0) goto L_0x00a8
            long r6 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x00a5 }
            long r8 = r5.lastModified()     // Catch:{ Throwable -> 0x00a5 }
            long r6 = r6 - r8
            r8 = 259200000(0xf731400, double:1.280618154E-315)
            int r0 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r0 <= 0) goto L_0x007e
            r0 = r1
        L_0x004d:
            java.lang.String r5 = "TbsInstaller"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00a5 }
            r6.<init>()     // Catch:{ Throwable -> 0x00a5 }
            java.lang.String r7 = "TBS_LOCAL_INSTALLATION is:"
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Throwable -> 0x00a5 }
            java.lang.StringBuilder r6 = r6.append(r4)     // Catch:{ Throwable -> 0x00a5 }
            java.lang.String r7 = " expired="
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Throwable -> 0x00a5 }
            java.lang.StringBuilder r6 = r6.append(r0)     // Catch:{ Throwable -> 0x00a5 }
            java.lang.String r6 = r6.toString()     // Catch:{ Throwable -> 0x00a5 }
            com.tencent.smtt.utils.TbsLog.i(r5, r6)     // Catch:{ Throwable -> 0x00a5 }
            if (r0 != 0) goto L_0x0080
        L_0x0071:
            r0 = r4 & r1
            if (r3 == 0) goto L_0x0016
            r3.close()     // Catch:{ IOException -> 0x0079 }
            goto L_0x0016
        L_0x0079:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x0016
        L_0x007e:
            r0 = r2
            goto L_0x004d
        L_0x0080:
            r1 = r2
            goto L_0x0071
        L_0x0082:
            r1 = move-exception
            r3 = r4
            r0 = r2
        L_0x0085:
            r1.printStackTrace()     // Catch:{ all -> 0x00a0 }
            if (r3 == 0) goto L_0x0016
            r3.close()     // Catch:{ IOException -> 0x008e }
            goto L_0x0016
        L_0x008e:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x0016
        L_0x0093:
            r0 = move-exception
            r3 = r4
        L_0x0095:
            if (r3 == 0) goto L_0x009a
            r3.close()     // Catch:{ IOException -> 0x009b }
        L_0x009a:
            throw r0
        L_0x009b:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x009a
        L_0x00a0:
            r0 = move-exception
            goto L_0x0095
        L_0x00a2:
            r1 = move-exception
            r0 = r2
            goto L_0x0085
        L_0x00a5:
            r1 = move-exception
            r0 = r4
            goto L_0x0085
        L_0x00a8:
            r0 = r2
            goto L_0x004d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.am.c(android.content.Context):boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0043 A[SYNTHETIC, Splitter:B:19:0x0043] */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0048 A[SYNTHETIC, Splitter:B:22:0x0048] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0054 A[SYNTHETIC, Splitter:B:28:0x0054] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0059 A[SYNTHETIC, Splitter:B:31:0x0059] */
    /* JADX WARNING: Removed duplicated region for block: B:50:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void d(android.content.Context r7) {
        /*
            r6 = this;
            r1 = 0
            java.io.File r0 = r6.q(r7)     // Catch:{ Throwable -> 0x005d }
            java.io.File r3 = new java.io.File     // Catch:{ Throwable -> 0x005d }
            java.lang.String r2 = "tbs.conf"
            r3.<init>(r0, r2)     // Catch:{ Throwable -> 0x005d }
            java.util.Properties r4 = new java.util.Properties     // Catch:{ Throwable -> 0x005d }
            r4.<init>()     // Catch:{ Throwable -> 0x005d }
            java.io.FileInputStream r0 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x003e, all -> 0x004e }
            r0.<init>(r3)     // Catch:{ Throwable -> 0x003e, all -> 0x004e }
            java.io.BufferedInputStream r2 = new java.io.BufferedInputStream     // Catch:{ Throwable -> 0x003e, all -> 0x004e }
            r2.<init>(r0)     // Catch:{ Throwable -> 0x003e, all -> 0x004e }
            r4.load(r2)     // Catch:{ Throwable -> 0x0071, all -> 0x0069 }
            java.io.FileOutputStream r5 = new java.io.FileOutputStream     // Catch:{ Throwable -> 0x0071, all -> 0x0069 }
            r5.<init>(r3)     // Catch:{ Throwable -> 0x0071, all -> 0x0069 }
            java.io.BufferedOutputStream r0 = new java.io.BufferedOutputStream     // Catch:{ Throwable -> 0x0071, all -> 0x0069 }
            r0.<init>(r5)     // Catch:{ Throwable -> 0x0071, all -> 0x0069 }
            java.lang.String r1 = "tbs_local_installation"
            java.lang.String r3 = "false"
            r4.setProperty(r1, r3)     // Catch:{ Throwable -> 0x0074, all -> 0x006d }
            r1 = 0
            r4.store(r0, r1)     // Catch:{ Throwable -> 0x0074, all -> 0x006d }
            if (r0 == 0) goto L_0x0038
            r0.close()     // Catch:{ IOException -> 0x005f }
        L_0x0038:
            if (r2 == 0) goto L_0x003d
            r2.close()     // Catch:{ IOException -> 0x0061 }
        L_0x003d:
            return
        L_0x003e:
            r0 = move-exception
            r0 = r1
            r2 = r1
        L_0x0041:
            if (r0 == 0) goto L_0x0046
            r0.close()     // Catch:{ IOException -> 0x0063 }
        L_0x0046:
            if (r2 == 0) goto L_0x003d
            r2.close()     // Catch:{ IOException -> 0x004c }
            goto L_0x003d
        L_0x004c:
            r0 = move-exception
            goto L_0x003d
        L_0x004e:
            r0 = move-exception
            r3 = r0
            r4 = r1
            r2 = r1
        L_0x0052:
            if (r4 == 0) goto L_0x0057
            r4.close()     // Catch:{ IOException -> 0x0065 }
        L_0x0057:
            if (r2 == 0) goto L_0x005c
            r2.close()     // Catch:{ IOException -> 0x0067 }
        L_0x005c:
            throw r3     // Catch:{ Throwable -> 0x005d }
        L_0x005d:
            r0 = move-exception
            goto L_0x003d
        L_0x005f:
            r0 = move-exception
            goto L_0x0038
        L_0x0061:
            r0 = move-exception
            goto L_0x003d
        L_0x0063:
            r0 = move-exception
            goto L_0x0046
        L_0x0065:
            r0 = move-exception
            goto L_0x0057
        L_0x0067:
            r0 = move-exception
            goto L_0x005c
        L_0x0069:
            r0 = move-exception
            r3 = r0
            r4 = r1
            goto L_0x0052
        L_0x006d:
            r1 = move-exception
            r3 = r1
            r4 = r0
            goto L_0x0052
        L_0x0071:
            r0 = move-exception
            r0 = r1
            goto L_0x0041
        L_0x0074:
            r1 = move-exception
            goto L_0x0041
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.am.d(android.content.Context):void");
    }

    public boolean e(Context context) {
        try {
            File file = new File(k.a(context, 4), "x5.tbs.decouple");
            File u = a().u(context);
            k.a(u);
            k.a(u, true);
            k.a(file, u);
            String[] list = u.list();
            for (String file2 : list) {
                File file3 = new File(u, file2);
                if (file3.getName().endsWith(PluginConstant.PLUGIN_INSTALL_DEX_OPT_SUFFIX)) {
                    file3.delete();
                }
            }
            a().f(context, true);
            File p2 = p(context);
            k.a(p2, true);
            u.renameTo(p2);
            TbsShareManager.b(context);
            return true;
        } catch (Exception e2) {
            return false;
        }
    }

    /* access modifiers changed from: package-private */
    public boolean f(Context context) {
        if (TbsShareManager.getHostCorePathAppDefined() != null) {
            return true;
        }
        try {
            Signature signature = context.getPackageManager().getPackageInfo(context.getPackageName(), 64).signatures[0];
            return context.getPackageName().equals(TbsConfig.APP_QB) ? signature.toCharsString().equals("3082023f308201a8a00302010202044c46914a300d06092a864886f70d01010505003064310b30090603550406130238363110300e060355040813074265696a696e673110300e060355040713074265696a696e673110300e060355040a130754656e63656e74310c300a060355040b13035753443111300f0603550403130873616d75656c6d6f301e170d3130303732313036313835305a170d3430303731333036313835305a3064310b30090603550406130238363110300e060355040813074265696a696e673110300e060355040713074265696a696e673110300e060355040a130754656e63656e74310c300a060355040b13035753443111300f0603550403130873616d75656c6d6f30819f300d06092a864886f70d010101050003818d0030818902818100c209077044bd0d63ea00ede5b839914cabcc912a87f0f8b390877e0f7a2583f0d5933443c40431c35a4433bc4c965800141961adc44c9625b1d321385221fd097e5bdc2f44a1840d643ab59dc070cf6c4b4b4d98bed5cbb8046e0a7078ae134da107cdf2bfc9b440fe5cb2f7549b44b73202cc6f7c2c55b8cfb0d333a021f01f0203010001300d06092a864886f70d010105050003818100b007db9922774ef4ccfee81ba514a8d57c410257e7a2eba64bfa17c9e690da08106d32f637ac41fbc9f205176c71bde238c872c3ee2f8313502bee44c80288ea4ef377a6f2cdfe4d3653c145c4acfedbfbadea23b559d41980cc3cdd35d79a68240693739aabf5c5ed26148756cf88264226de394c8a24ac35b712b120d4d23a") : context.getPackageName().equals(TbsConfig.APP_WX) ? signature.toCharsString().equals("308202eb30820254a00302010202044d36f7a4300d06092a864886f70d01010505003081b9310b300906035504061302383631123010060355040813094775616e67646f6e673111300f060355040713085368656e7a68656e31353033060355040a132c54656e63656e7420546563686e6f6c6f6779285368656e7a68656e2920436f6d70616e79204c696d69746564313a3038060355040b133154656e63656e74204775616e677a686f7520526573656172636820616e6420446576656c6f706d656e742043656e7465723110300e0603550403130754656e63656e74301e170d3131303131393134333933325a170d3431303131313134333933325a3081b9310b300906035504061302383631123010060355040813094775616e67646f6e673111300f060355040713085368656e7a68656e31353033060355040a132c54656e63656e7420546563686e6f6c6f6779285368656e7a68656e2920436f6d70616e79204c696d69746564313a3038060355040b133154656e63656e74204775616e677a686f7520526573656172636820616e6420446576656c6f706d656e742043656e7465723110300e0603550403130754656e63656e7430819f300d06092a864886f70d010101050003818d0030818902818100c05f34b231b083fb1323670bfbe7bdab40c0c0a6efc87ef2072a1ff0d60cc67c8edb0d0847f210bea6cbfaa241be70c86daf56be08b723c859e52428a064555d80db448cdcacc1aea2501eba06f8bad12a4fa49d85cacd7abeb68945a5cb5e061629b52e3254c373550ee4e40cb7c8ae6f7a8151ccd8df582d446f39ae0c5e930203010001300d06092a864886f70d0101050500038181009c8d9d7f2f908c42081b4c764c377109a8b2c70582422125ce545842d5f520aea69550b6bd8bfd94e987b75a3077eb04ad341f481aac266e89d3864456e69fba13df018acdc168b9a19dfd7ad9d9cc6f6ace57c746515f71234df3a053e33ba93ece5cd0fc15f3e389a3f365588a9fcb439e069d3629cd7732a13fff7b891499") : context.getPackageName().equals(TbsConfig.APP_QQ) ? signature.toCharsString().equals("30820253308201bca00302010202044bbb0361300d06092a864886f70d0101050500306d310e300c060355040613054368696e61310f300d06035504080c06e58c97e4baac310f300d06035504070c06e58c97e4baac310f300d060355040a0c06e885bee8aeaf311b3019060355040b0c12e697a0e7babfe4b89ae58aa1e7b3bbe7bb9f310b30090603550403130251513020170d3130303430363039343831375a180f32323834303132303039343831375a306d310e300c060355040613054368696e61310f300d06035504080c06e58c97e4baac310f300d06035504070c06e58c97e4baac310f300d060355040a0c06e885bee8aeaf311b3019060355040b0c12e697a0e7babfe4b89ae58aa1e7b3bbe7bb9f310b300906035504031302515130819f300d06092a864886f70d010101050003818d0030818902818100a15e9756216f694c5915e0b529095254367c4e64faeff07ae13488d946615a58ddc31a415f717d019edc6d30b9603d3e2a7b3de0ab7e0cf52dfee39373bc472fa997027d798d59f81d525a69ecf156e885fd1e2790924386b2230cc90e3b7adc95603ddcf4c40bdc72f22db0f216a99c371d3bf89cba6578c60699e8a0d536950203010001300d06092a864886f70d01010505000381810094a9b80e80691645dd42d6611775a855f71bcd4d77cb60a8e29404035a5e00b21bcc5d4a562482126bd91b6b0e50709377ceb9ef8c2efd12cc8b16afd9a159f350bb270b14204ff065d843832720702e28b41491fbc3a205f5f2f42526d67f17614d8a974de6487b2c866efede3b4e49a0f916baa3c1336fd2ee1b1629652049") : context.getPackageName().equals(TbsConfig.APP_DEMO) ? signature.toCharsString().equals("3082023f308201a8a00302010202044c46914a300d06092a864886f70d01010505003064310b30090603550406130238363110300e060355040813074265696a696e673110300e060355040713074265696a696e673110300e060355040a130754656e63656e74310c300a060355040b13035753443111300f0603550403130873616d75656c6d6f301e170d3130303732313036313835305a170d3430303731333036313835305a3064310b30090603550406130238363110300e060355040813074265696a696e673110300e060355040713074265696a696e673110300e060355040a130754656e63656e74310c300a060355040b13035753443111300f0603550403130873616d75656c6d6f30819f300d06092a864886f70d010101050003818d0030818902818100c209077044bd0d63ea00ede5b839914cabcc912a87f0f8b390877e0f7a2583f0d5933443c40431c35a4433bc4c965800141961adc44c9625b1d321385221fd097e5bdc2f44a1840d643ab59dc070cf6c4b4b4d98bed5cbb8046e0a7078ae134da107cdf2bfc9b440fe5cb2f7549b44b73202cc6f7c2c55b8cfb0d333a021f01f0203010001300d06092a864886f70d010105050003818100b007db9922774ef4ccfee81ba514a8d57c410257e7a2eba64bfa17c9e690da08106d32f637ac41fbc9f205176c71bde238c872c3ee2f8313502bee44c80288ea4ef377a6f2cdfe4d3653c145c4acfedbfbadea23b559d41980cc3cdd35d79a68240693739aabf5c5ed26148756cf88264226de394c8a24ac35b712b120d4d23a") : context.getPackageName().equals(TbsConfig.APP_QZONE) ? signature.toCharsString().equals("308202ad30820216a00302010202044c26cea2300d06092a864886f70d010105050030819a310b3009060355040613023836311530130603550408130c4265696a696e672043697479311530130603550407130c4265696a696e67204369747931263024060355040a131d515a6f6e65205465616d206f662054656e63656e7420436f6d70616e7931183016060355040b130f54656e63656e7420436f6d70616e79311b301906035504031312416e64726f696420515a6f6e65205465616d301e170d3130303632373034303830325a170d3335303632313034303830325a30819a310b3009060355040613023836311530130603550408130c4265696a696e672043697479311530130603550407130c4265696a696e67204369747931263024060355040a131d515a6f6e65205465616d206f662054656e63656e7420436f6d70616e7931183016060355040b130f54656e63656e7420436f6d70616e79311b301906035504031312416e64726f696420515a6f6e65205465616d30819f300d06092a864886f70d010101050003818d003081890281810082d6aca037a9843fbbe88b6dd19f36e9c24ce174c1b398f3a529e2a7fe02de99c27539602c026edf96ad8d43df32a85458bca1e6fbf11958658a7d6751a1d9b782bf43a8c19bd1c06bdbfd94c0516326ae3cf638ac42bb470580e340c46e6f306a772c1ef98f10a559edf867f3f31fe492808776b7bd953b2cba2d2b2d66a44f0203010001300d06092a864886f70d0101050500038181006003b04a8a8c5be9650f350cda6896e57dd13e6e83e7f891fc70f6a3c2eaf75cfa4fc998365deabbd1b9092159edf4b90df5702a0d101f8840b5d4586eb92a1c3cd19d95fbc1c2ac956309eda8eef3944baf08c4a49d3b9b3ffb06bc13dab94ecb5b8eb74e8789aa0ba21cb567f538bbc59c2a11e6919924a24272eb79251677") : !context.getPackageName().equals("com.tencent.qqpimsecure") || signature.toCharsString().equals("30820239308201a2a00302010202044c96f48f300d06092a864886f70d01010505003060310b300906035504061302434e310b300906035504081302474431123010060355040713094775616e677a686f753110300e060355040a130754656e63656e74310b3009060355040b130233473111300f0603550403130857696c736f6e57753020170d3130303932303035343334335a180f32303635303632333035343334335a3060310b300906035504061302434e310b300906035504081302474431123010060355040713094775616e677a686f753110300e060355040a130754656e63656e74310b3009060355040b130233473111300f0603550403130857696c736f6e577530819f300d06092a864886f70d010101050003818d0030818902818100b56e79dbb1185a79e52d792bb3d0bb3da8010d9b87da92ec69f7dc5ad66ab6bfdff2a6a1ed285dd2358f28b72a468be7c10a2ce30c4c27323ed4edcc936080e5bedc2cbbca0b7e879c08a631182793f44bb3ea284179b263410c298e5f6831032c9702ba4a74e2ccfc9ef857f12201451602fc8e774ac59d6398511586c83d1d0203010001300d06092a864886f70d0101050500038181002475615bb65b8d8786b890535802948840387d06b1692ff3ea47ef4c435719ba1865b81e6bfa6293ce31747c3cd6b34595b485cc1563fd90107ba5845c28b95c79138f0dec288940395bc10f92f2b69d8dc410999deb38900974ce9984b678030edfba8816582f56160d87e38641288d8588d2a31e20b89f223d788dd35cc9c8");
        } catch (Exception e2) {
            TbsLog.i("TbsInstaller", "TbsInstaller-installLocalTbsCore getPackageInfo fail");
            return false;
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0065 A[SYNTHETIC, Splitter:B:26:0x0065] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int g(android.content.Context r7) {
        /*
            r6 = this;
            r0 = 0
            r2 = 0
            java.io.File r1 = r6.t(r7)     // Catch:{ Exception -> 0x0069, all -> 0x0062 }
            java.lang.String r3 = "TbsInstaller"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0069, all -> 0x0062 }
            r4.<init>()     // Catch:{ Exception -> 0x0069, all -> 0x0062 }
            java.lang.String r5 = "TbsInstaller--getTmpTbsCoreVersionUnzipDir  tbsShareDir is "
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ Exception -> 0x0069, all -> 0x0062 }
            java.lang.StringBuilder r4 = r4.append(r1)     // Catch:{ Exception -> 0x0069, all -> 0x0062 }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x0069, all -> 0x0062 }
            com.tencent.smtt.utils.TbsLog.i(r3, r4)     // Catch:{ Exception -> 0x0069, all -> 0x0062 }
            java.io.File r3 = new java.io.File     // Catch:{ Exception -> 0x0069, all -> 0x0062 }
            java.lang.String r4 = "tbs.conf"
            r3.<init>(r1, r4)     // Catch:{ Exception -> 0x0069, all -> 0x0062 }
            if (r3 == 0) goto L_0x002d
            boolean r1 = r3.exists()     // Catch:{ Exception -> 0x0069, all -> 0x0062 }
            if (r1 != 0) goto L_0x0033
        L_0x002d:
            if (r2 == 0) goto L_0x0032
            r2.close()     // Catch:{ IOException -> 0x0056 }
        L_0x0032:
            return r0
        L_0x0033:
            java.util.Properties r4 = new java.util.Properties     // Catch:{ Exception -> 0x0069, all -> 0x0062 }
            r4.<init>()     // Catch:{ Exception -> 0x0069, all -> 0x0062 }
            java.io.FileInputStream r5 = new java.io.FileInputStream     // Catch:{ Exception -> 0x0069, all -> 0x0062 }
            r5.<init>(r3)     // Catch:{ Exception -> 0x0069, all -> 0x0062 }
            java.io.BufferedInputStream r1 = new java.io.BufferedInputStream     // Catch:{ Exception -> 0x0069, all -> 0x0062 }
            r1.<init>(r5)     // Catch:{ Exception -> 0x0069, all -> 0x0062 }
            r4.load(r1)     // Catch:{ Exception -> 0x0076, all -> 0x0073 }
            r1.close()     // Catch:{ Exception -> 0x0076, all -> 0x0073 }
            java.lang.String r2 = "tbs_core_version"
            java.lang.String r2 = r4.getProperty(r2)     // Catch:{ Exception -> 0x0076, all -> 0x0073 }
            if (r2 != 0) goto L_0x0058
            if (r1 == 0) goto L_0x0032
            r1.close()     // Catch:{ IOException -> 0x0056 }
            goto L_0x0032
        L_0x0056:
            r1 = move-exception
            goto L_0x0032
        L_0x0058:
            int r0 = java.lang.Integer.parseInt(r2)     // Catch:{ Exception -> 0x0076, all -> 0x0073 }
            if (r1 == 0) goto L_0x0032
            r1.close()     // Catch:{ IOException -> 0x0056 }
            goto L_0x0032
        L_0x0062:
            r0 = move-exception
        L_0x0063:
            if (r2 == 0) goto L_0x0068
            r2.close()     // Catch:{ IOException -> 0x0071 }
        L_0x0068:
            throw r0
        L_0x0069:
            r1 = move-exception
            r1 = r2
        L_0x006b:
            if (r1 == 0) goto L_0x0032
            r1.close()     // Catch:{ IOException -> 0x0056 }
            goto L_0x0032
        L_0x0071:
            r1 = move-exception
            goto L_0x0068
        L_0x0073:
            r0 = move-exception
            r2 = r1
            goto L_0x0063
        L_0x0076:
            r2 = move-exception
            goto L_0x006b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.am.g(android.content.Context):int");
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x004d A[SYNTHETIC, Splitter:B:26:0x004d] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int h(android.content.Context r7) {
        /*
            r6 = this;
            r0 = 0
            r2 = 0
            java.io.File r1 = r6.p(r7)     // Catch:{ Exception -> 0x0051, all -> 0x004a }
            java.io.File r3 = new java.io.File     // Catch:{ Exception -> 0x0051, all -> 0x004a }
            java.lang.String r4 = "tbs.conf"
            r3.<init>(r1, r4)     // Catch:{ Exception -> 0x0051, all -> 0x004a }
            if (r3 == 0) goto L_0x0015
            boolean r1 = r3.exists()     // Catch:{ Exception -> 0x0051, all -> 0x004a }
            if (r1 != 0) goto L_0x001b
        L_0x0015:
            if (r2 == 0) goto L_0x001a
            r2.close()     // Catch:{ IOException -> 0x003e }
        L_0x001a:
            return r0
        L_0x001b:
            java.util.Properties r4 = new java.util.Properties     // Catch:{ Exception -> 0x0051, all -> 0x004a }
            r4.<init>()     // Catch:{ Exception -> 0x0051, all -> 0x004a }
            java.io.FileInputStream r5 = new java.io.FileInputStream     // Catch:{ Exception -> 0x0051, all -> 0x004a }
            r5.<init>(r3)     // Catch:{ Exception -> 0x0051, all -> 0x004a }
            java.io.BufferedInputStream r1 = new java.io.BufferedInputStream     // Catch:{ Exception -> 0x0051, all -> 0x004a }
            r1.<init>(r5)     // Catch:{ Exception -> 0x0051, all -> 0x004a }
            r4.load(r1)     // Catch:{ Exception -> 0x005e, all -> 0x005b }
            r1.close()     // Catch:{ Exception -> 0x005e, all -> 0x005b }
            java.lang.String r2 = "tbs_core_version"
            java.lang.String r2 = r4.getProperty(r2)     // Catch:{ Exception -> 0x005e, all -> 0x005b }
            if (r2 != 0) goto L_0x0040
            if (r1 == 0) goto L_0x001a
            r1.close()     // Catch:{ IOException -> 0x003e }
            goto L_0x001a
        L_0x003e:
            r1 = move-exception
            goto L_0x001a
        L_0x0040:
            int r0 = java.lang.Integer.parseInt(r2)     // Catch:{ Exception -> 0x005e, all -> 0x005b }
            if (r1 == 0) goto L_0x001a
            r1.close()     // Catch:{ IOException -> 0x003e }
            goto L_0x001a
        L_0x004a:
            r0 = move-exception
        L_0x004b:
            if (r2 == 0) goto L_0x0050
            r2.close()     // Catch:{ IOException -> 0x0059 }
        L_0x0050:
            throw r0
        L_0x0051:
            r1 = move-exception
            r1 = r2
        L_0x0053:
            if (r1 == 0) goto L_0x001a
            r1.close()     // Catch:{ IOException -> 0x003e }
            goto L_0x001a
        L_0x0059:
            r1 = move-exception
            goto L_0x0050
        L_0x005b:
            r0 = move-exception
            r2 = r1
            goto L_0x004b
        L_0x005e:
            r2 = move-exception
            goto L_0x0053
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.am.h(android.content.Context):int");
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x00b0 A[SYNTHETIC, Splitter:B:41:0x00b0] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int i(android.content.Context r7) {
        /*
            r6 = this;
            r0 = 0
            r3 = 0
            java.io.File r1 = r6.q(r7)     // Catch:{ Exception -> 0x006d, all -> 0x00ac }
            java.io.File r2 = new java.io.File     // Catch:{ Exception -> 0x006d, all -> 0x00ac }
            java.lang.String r4 = "tbs.conf"
            r2.<init>(r1, r4)     // Catch:{ Exception -> 0x006d, all -> 0x00ac }
            if (r2 == 0) goto L_0x0015
            boolean r1 = r2.exists()     // Catch:{ Exception -> 0x006d, all -> 0x00ac }
            if (r1 != 0) goto L_0x001b
        L_0x0015:
            if (r3 == 0) goto L_0x001a
            r3.close()     // Catch:{ IOException -> 0x00d2 }
        L_0x001a:
            return r0
        L_0x001b:
            java.util.Properties r1 = new java.util.Properties     // Catch:{ Exception -> 0x006d, all -> 0x00ac }
            r1.<init>()     // Catch:{ Exception -> 0x006d, all -> 0x00ac }
            java.io.FileInputStream r4 = new java.io.FileInputStream     // Catch:{ Exception -> 0x006d, all -> 0x00ac }
            r4.<init>(r2)     // Catch:{ Exception -> 0x006d, all -> 0x00ac }
            java.io.BufferedInputStream r2 = new java.io.BufferedInputStream     // Catch:{ Exception -> 0x006d, all -> 0x00ac }
            r2.<init>(r4)     // Catch:{ Exception -> 0x006d, all -> 0x00ac }
            r1.load(r2)     // Catch:{ Exception -> 0x010f }
            r2.close()     // Catch:{ Exception -> 0x010f }
            java.lang.String r3 = "tbs_core_version"
            java.lang.String r1 = r1.getProperty(r3)     // Catch:{ Exception -> 0x010f }
            if (r1 != 0) goto L_0x005c
            if (r2 == 0) goto L_0x001a
            r2.close()     // Catch:{ IOException -> 0x003e }
            goto L_0x001a
        L_0x003e:
            r1 = move-exception
            java.lang.String r2 = "TbsInstaller"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "TbsInstaller--getTbsCoreInstalledVerInNolock IOException="
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r1 = r1.toString()
            java.lang.StringBuilder r1 = r3.append(r1)
            java.lang.String r1 = r1.toString()
        L_0x0058:
            com.tencent.smtt.utils.TbsLog.i(r2, r1)
            goto L_0x001a
        L_0x005c:
            int r1 = java.lang.Integer.parseInt(r1)     // Catch:{ Exception -> 0x010f }
            int r3 = o     // Catch:{ Exception -> 0x010f }
            if (r3 != 0) goto L_0x0066
            o = r1     // Catch:{ Exception -> 0x010f }
        L_0x0066:
            if (r2 == 0) goto L_0x006b
            r2.close()     // Catch:{ IOException -> 0x00ee }
        L_0x006b:
            r0 = r1
            goto L_0x001a
        L_0x006d:
            r1 = move-exception
            r2 = r3
        L_0x006f:
            java.lang.String r3 = "TbsInstaller"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x010d }
            r4.<init>()     // Catch:{ all -> 0x010d }
            java.lang.String r5 = "TbsInstaller--getTbsCoreInstalledVerInNolock Exception="
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x010d }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x010d }
            java.lang.StringBuilder r1 = r4.append(r1)     // Catch:{ all -> 0x010d }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x010d }
            com.tencent.smtt.utils.TbsLog.i(r3, r1)     // Catch:{ all -> 0x010d }
            if (r2 == 0) goto L_0x001a
            r2.close()     // Catch:{ IOException -> 0x0091 }
            goto L_0x001a
        L_0x0091:
            r1 = move-exception
            java.lang.String r2 = "TbsInstaller"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "TbsInstaller--getTbsCoreInstalledVerInNolock IOException="
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r1 = r1.toString()
            java.lang.StringBuilder r1 = r3.append(r1)
            java.lang.String r1 = r1.toString()
            goto L_0x0058
        L_0x00ac:
            r0 = move-exception
            r2 = r3
        L_0x00ae:
            if (r2 == 0) goto L_0x00b3
            r2.close()     // Catch:{ IOException -> 0x00b4 }
        L_0x00b3:
            throw r0
        L_0x00b4:
            r1 = move-exception
            java.lang.String r2 = "TbsInstaller"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "TbsInstaller--getTbsCoreInstalledVerInNolock IOException="
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r1 = r1.toString()
            java.lang.StringBuilder r1 = r3.append(r1)
            java.lang.String r1 = r1.toString()
            com.tencent.smtt.utils.TbsLog.i(r2, r1)
            goto L_0x00b3
        L_0x00d2:
            r1 = move-exception
            java.lang.String r2 = "TbsInstaller"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "TbsInstaller--getTbsCoreInstalledVerInNolock IOException="
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r1 = r1.toString()
            java.lang.StringBuilder r1 = r3.append(r1)
            java.lang.String r1 = r1.toString()
            goto L_0x0058
        L_0x00ee:
            r0 = move-exception
            java.lang.String r2 = "TbsInstaller"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "TbsInstaller--getTbsCoreInstalledVerInNolock IOException="
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r0 = r0.toString()
            java.lang.StringBuilder r0 = r3.append(r0)
            java.lang.String r0 = r0.toString()
            com.tencent.smtt.utils.TbsLog.i(r2, r0)
            goto L_0x006b
        L_0x010d:
            r0 = move-exception
            goto L_0x00ae
        L_0x010f:
            r1 = move-exception
            goto L_0x006f
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.am.i(android.content.Context):int");
    }

    /* access modifiers changed from: package-private */
    public int j(Context context) {
        return o != 0 ? o : i(context);
    }

    /* access modifiers changed from: package-private */
    public void k(Context context) {
        if (o == 0) {
            o = i(context);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean l(Context context) {
        File file = new File(q(context), "tbs.conf");
        return file != null && file.exists();
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00d2 A[SYNTHETIC, Splitter:B:42:0x00d2] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int m(android.content.Context r7) {
        /*
            r6 = this;
            r1 = 0
            boolean r0 = r6.w(r7)
            if (r0 != 0) goto L_0x0009
            r0 = -1
        L_0x0008:
            return r0
        L_0x0009:
            java.util.concurrent.locks.Lock r0 = i
            boolean r0 = r0.tryLock()
            java.lang.String r2 = "TbsInstaller"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "TbsInstaller--getTbsCoreInstalledVer locked="
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.StringBuilder r3 = r3.append(r0)
            java.lang.String r3 = r3.toString()
            com.tencent.smtt.utils.TbsLog.i(r2, r3)
            if (r0 == 0) goto L_0x00de
            r3 = 0
            java.io.File r0 = r6.q(r7)     // Catch:{ Exception -> 0x00a0, all -> 0x00ce }
            java.io.File r2 = new java.io.File     // Catch:{ Exception -> 0x00a0, all -> 0x00ce }
            java.lang.String r4 = "tbs.conf"
            r2.<init>(r0, r4)     // Catch:{ Exception -> 0x00a0, all -> 0x00ce }
            if (r2 == 0) goto L_0x003d
            boolean r0 = r2.exists()     // Catch:{ Exception -> 0x00a0, all -> 0x00ce }
            if (r0 != 0) goto L_0x004c
        L_0x003d:
            if (r3 == 0) goto L_0x0042
            r3.close()     // Catch:{ IOException -> 0x0120 }
        L_0x0042:
            java.util.concurrent.locks.Lock r0 = i
            r0.unlock()
            r6.b()
            r0 = r1
            goto L_0x0008
        L_0x004c:
            java.util.Properties r0 = new java.util.Properties     // Catch:{ Exception -> 0x00a0, all -> 0x00ce }
            r0.<init>()     // Catch:{ Exception -> 0x00a0, all -> 0x00ce }
            java.io.FileInputStream r4 = new java.io.FileInputStream     // Catch:{ Exception -> 0x00a0, all -> 0x00ce }
            r4.<init>(r2)     // Catch:{ Exception -> 0x00a0, all -> 0x00ce }
            java.io.BufferedInputStream r2 = new java.io.BufferedInputStream     // Catch:{ Exception -> 0x00a0, all -> 0x00ce }
            r2.<init>(r4)     // Catch:{ Exception -> 0x00a0, all -> 0x00ce }
            r0.load(r2)     // Catch:{ Exception -> 0x0180 }
            r2.close()     // Catch:{ Exception -> 0x0180 }
            java.lang.String r3 = "tbs_core_version"
            java.lang.String r0 = r0.getProperty(r3)     // Catch:{ Exception -> 0x0180 }
            if (r0 != 0) goto L_0x0078
            if (r2 == 0) goto L_0x006e
            r2.close()     // Catch:{ IOException -> 0x013f }
        L_0x006e:
            java.util.concurrent.locks.Lock r0 = i
            r0.unlock()
            r6.b()
            r0 = r1
            goto L_0x0008
        L_0x0078:
            java.lang.ThreadLocal<java.lang.Integer> r3 = a     // Catch:{ Exception -> 0x0180 }
            int r0 = java.lang.Integer.parseInt(r0)     // Catch:{ Exception -> 0x0180 }
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch:{ Exception -> 0x0180 }
            r3.set(r0)     // Catch:{ Exception -> 0x0180 }
            java.lang.ThreadLocal<java.lang.Integer> r0 = a     // Catch:{ Exception -> 0x0180 }
            java.lang.Object r0 = r0.get()     // Catch:{ Exception -> 0x0180 }
            java.lang.Integer r0 = (java.lang.Integer) r0     // Catch:{ Exception -> 0x0180 }
            int r0 = r0.intValue()     // Catch:{ Exception -> 0x0180 }
            if (r2 == 0) goto L_0x0096
            r2.close()     // Catch:{ IOException -> 0x015e }
        L_0x0096:
            java.util.concurrent.locks.Lock r1 = i
            r1.unlock()
            r6.b()
            goto L_0x0008
        L_0x00a0:
            r0 = move-exception
            r2 = r3
        L_0x00a2:
            java.lang.String r3 = "TbsInstaller"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x017d }
            r4.<init>()     // Catch:{ all -> 0x017d }
            java.lang.String r5 = "TbsInstaller--getTbsCoreInstalledVer Exception="
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x017d }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x017d }
            java.lang.StringBuilder r0 = r4.append(r0)     // Catch:{ all -> 0x017d }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x017d }
            com.tencent.smtt.utils.TbsLog.i(r3, r0)     // Catch:{ all -> 0x017d }
            if (r2 == 0) goto L_0x00c3
            r2.close()     // Catch:{ IOException -> 0x0102 }
        L_0x00c3:
            java.util.concurrent.locks.Lock r0 = i
            r0.unlock()
            r6.b()
            r0 = r1
            goto L_0x0008
        L_0x00ce:
            r0 = move-exception
            r2 = r3
        L_0x00d0:
            if (r2 == 0) goto L_0x00d5
            r2.close()     // Catch:{ IOException -> 0x00e4 }
        L_0x00d5:
            java.util.concurrent.locks.Lock r1 = i
            r1.unlock()
            r6.b()
            throw r0
        L_0x00de:
            r6.b()
            r0 = r1
            goto L_0x0008
        L_0x00e4:
            r1 = move-exception
            java.lang.String r2 = "TbsInstaller"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "TbsInstaller--getTbsCoreInstalledVer IOException="
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r1 = r1.toString()
            java.lang.StringBuilder r1 = r3.append(r1)
            java.lang.String r1 = r1.toString()
            com.tencent.smtt.utils.TbsLog.i(r2, r1)
            goto L_0x00d5
        L_0x0102:
            r0 = move-exception
            java.lang.String r2 = "TbsInstaller"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "TbsInstaller--getTbsCoreInstalledVer IOException="
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r0 = r0.toString()
            java.lang.StringBuilder r0 = r3.append(r0)
            java.lang.String r0 = r0.toString()
            com.tencent.smtt.utils.TbsLog.i(r2, r0)
            goto L_0x00c3
        L_0x0120:
            r0 = move-exception
            java.lang.String r2 = "TbsInstaller"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "TbsInstaller--getTbsCoreInstalledVer IOException="
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r0 = r0.toString()
            java.lang.StringBuilder r0 = r3.append(r0)
            java.lang.String r0 = r0.toString()
            com.tencent.smtt.utils.TbsLog.i(r2, r0)
            goto L_0x0042
        L_0x013f:
            r0 = move-exception
            java.lang.String r2 = "TbsInstaller"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "TbsInstaller--getTbsCoreInstalledVer IOException="
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r0 = r0.toString()
            java.lang.StringBuilder r0 = r3.append(r0)
            java.lang.String r0 = r0.toString()
            com.tencent.smtt.utils.TbsLog.i(r2, r0)
            goto L_0x006e
        L_0x015e:
            r1 = move-exception
            java.lang.String r2 = "TbsInstaller"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "TbsInstaller--getTbsCoreInstalledVer IOException="
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r1 = r1.toString()
            java.lang.StringBuilder r1 = r3.append(r1)
            java.lang.String r1 = r1.toString()
            com.tencent.smtt.utils.TbsLog.i(r2, r1)
            goto L_0x0096
        L_0x017d:
            r0 = move-exception
            goto L_0x00d0
        L_0x0180:
            r0 = move-exception
            goto L_0x00a2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.am.m(android.content.Context):int");
    }

    public boolean n(Context context) {
        TbsLog.i("TbsInstaller", "TbsInstaller--coreShareCopyToDecouple #0");
        File q = q(context);
        File p2 = p(context);
        try {
            k.a(p2, true);
            k.a(q, p2, (FileFilter) new at(this));
            TbsShareManager.b(context);
            TbsLog.i("TbsInstaller", "TbsInstaller--coreShareCopyToDecouple success!!!");
            return true;
        } catch (Exception e2) {
            return false;
        }
    }

    /* access modifiers changed from: package-private */
    public void o(Context context) {
        TbsLog.i("TbsInstaller", "TbsInstaller--cleanStatusAndTmpDir");
        ai.a(context).a(0);
        ai.a(context).b(0);
        ai.a(context).d(0);
        ai.a(context).a("incrupdate_retry_num", 0);
        if (TbsDownloadConfig.getInstance(context).mPreferences.getInt(TbsDownloadConfig.TbsConfigKey.KEY_DOWNLOADDECOUPLECORE, 0) != 1) {
            ai.a(context).b(0, -1);
            ai.a(context).a("");
            ai.a(context).a("copy_retry_num", 0);
            ai.a(context).c(-1);
            ai.a(context).a(0, -1);
            k.a(t(context), true);
            k.a(v(context), true);
        }
    }

    /* access modifiers changed from: package-private */
    public File p(Context context) {
        File file = new File(context.getDir("tbs", 0), "core_share_decouple");
        if (file == null) {
            return null;
        }
        if (file.isDirectory() || file.mkdir()) {
            return file;
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public File q(Context context) {
        return b((Context) null, context);
    }

    /* access modifiers changed from: package-private */
    public File r(Context context) {
        File file = new File(context.getDir("tbs", 0), "share");
        if (file == null) {
            return null;
        }
        if (file.isDirectory() || file.mkdir()) {
            return file;
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public File t(Context context) {
        File file = new File(context.getDir("tbs", 0), "core_unzip_tmp");
        if (file == null) {
            return null;
        }
        if (file.isDirectory() || file.mkdir()) {
            return file;
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public File u(Context context) {
        File file = new File(context.getDir("tbs", 0), "core_unzip_tmp_decouple");
        if (file == null) {
            return null;
        }
        if (file.isDirectory() || file.mkdir()) {
            return file;
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public File v(Context context) {
        File file = new File(context.getDir("tbs", 0), "core_copy_tmp");
        if (file == null) {
            return null;
        }
        if (file.isDirectory() || file.mkdir()) {
            return file;
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public synchronized boolean w(Context context) {
        boolean z = true;
        synchronized (this) {
            this.e++;
            if (this.h) {
                TbsLog.i("TbsInstaller", "getTbsInstallingFileLock success,is cached= true");
            } else {
                this.g = k.b(context, true, "tbslock.txt");
                if (this.g != null) {
                    this.f = k.a(context, this.g);
                    if (this.f == null) {
                        z = false;
                    } else {
                        TbsLog.i("TbsInstaller", "getTbsInstallingFileLock success,is cached= false");
                        this.h = true;
                    }
                } else {
                    z = false;
                }
            }
        }
        return z;
    }
}
