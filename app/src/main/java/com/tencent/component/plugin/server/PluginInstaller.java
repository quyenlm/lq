package com.tencent.component.plugin.server;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import com.appsflyer.share.Constants;
import com.tencent.component.plugin.PluginFileLock;
import com.tencent.component.plugin.PluginInfo;
import com.tencent.component.plugin.PluginNativeHelper;
import com.tencent.component.utils.FileUtil;
import com.tencent.component.utils.UniqueLock;
import com.tencent.component.utils.log.LogUtil;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;
import java.util.concurrent.locks.Lock;

class PluginInstaller {
    public static int INSTALL_FAILED_ALREADY_EXISTS = -5;
    public static int INSTALL_FAILED_COPY_FILE = -4;
    public static int INSTALL_FAILED_INTERNAL = -6;
    public static int INSTALL_FAILED_INVALID_DIR = -2;
    public static int INSTALL_FAILED_INVALID_FILE = -1;
    public static int INSTALL_FAILED_VERIFY = -3;
    public static int INSTALL_SUCCEED = 1;
    public static int INSTALL_SUCCEED_FAILED_COPY_LIB = 2;
    private static final String TAG = "PluginInstaller";
    private final Context mContext;
    private final UniqueLock<String> mInstallLock = new UniqueLock<>();
    private final UniqueLock<String> mPendingLock = new UniqueLock<>();
    private final PlatformServerContext mPlatformServerContext;
    private final File mPluginDir;
    private final File mPluginExternalPendingDir;
    private final PluginManagerServer mPluginManagerServer;
    private final File mPluginPendingDir;

    PluginInstaller(PlatformServerContext platformServerContext) {
        this.mPlatformServerContext = platformServerContext;
        this.mContext = platformServerContext.getContext();
        this.mPluginManagerServer = platformServerContext.getPluginManagerServer();
        this.mPluginDir = PluginConstant.getInstallDir(platformServerContext);
        this.mPluginPendingDir = PluginConstant.getInstallPendingDir(platformServerContext);
        this.mPluginExternalPendingDir = PluginConstant.getExternalInstallPendingDir(platformServerContext);
    }

    /* access modifiers changed from: package-private */
    public final void install() {
        installPendingPlugins(this.mPluginPendingDir);
        installPendingPlugins(this.mPluginExternalPendingDir);
    }

    private void installPendingPlugins(File pendingDir) {
        File[] files;
        if (isDirValid(pendingDir) && (files = pendingDir.listFiles()) != null) {
            for (File file : files) {
                int result = performInstall(file, false, false);
                LogUtil.i(TAG, "pending install result:" + result);
                this.mPlatformServerContext.broadcastPendingInstallFinish(result, file);
                PluginConstant.removePendingInstallInfo(this.mContext, file.getAbsolutePath());
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final int install(File file) {
        return performInstall(file, true, false);
    }

    /* access modifiers changed from: package-private */
    public final int install(File file, boolean installByUser) {
        return performInstall(file, true, installByUser);
    }

    /* access modifiers changed from: package-private */
    public final int installPending(File file) {
        return performPending(file);
    }

    /* access modifiers changed from: package-private */
    public final boolean uninstall(PluginInfo pluginInfo) {
        return performUninstall(pluginInfo, true);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0066, code lost:
        r7 = com.tencent.component.plugin.server.PluginParser.parse(r14.mPlatformServerContext, r14.mContext, r15.getAbsolutePath(), 1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
        com.tencent.component.plugin.server.PluginValidator.getInstance(r14.mContext).validate(r7, r14.mPlatformServerContext);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x007e, code lost:
        r4 = r14.mInstallLock.obtain(r7.pluginId);
        r4.lock();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        r3 = generateInstallFile(r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x008e, code lost:
        if (r3 != null) goto L_0x00fa;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0090, code lost:
        removeFile(r15);
        com.tencent.component.plugin.PluginReporter.report(com.tencent.component.plugin.PluginReporter.EVENT_INSTALL, false, "cannot generate install file", "plugin:" + r7, (java.lang.Throwable) null);
        com.tencent.component.utils.log.LogUtil.i(TAG, "cannot generate install file for plugin " + r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x00ce, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x00cf, code lost:
        removeFile(r15);
        com.tencent.component.plugin.PluginReporter.report(com.tencent.component.plugin.PluginReporter.EVENT_INSTALL, false, "verify error", "plugin:" + r7, r1);
        com.tencent.component.utils.log.LogUtil.i(TAG, r1.getMessage(), r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00fa, code lost:
        r6 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0103, code lost:
        if (r14.mPluginManagerServer.isPluginRegistered(r7.pluginId) == false) goto L_0x0113;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0105, code lost:
        r6 = r14.mPluginManagerServer.getPluginInfo(r7.pluginId);
        performUninstall(r6, true);
        r16 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0113, code lost:
        r2 = com.tencent.component.plugin.PluginFileLock.writeLock(r3.getAbsolutePath());
        r2.lock();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:?, code lost:
        copyFileSafely(r15, r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:?, code lost:
        r2.unlock();
        removeFile(r15);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x012b, code lost:
        if (isFileValid(r3) == false) goto L_0x01e0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x012d, code lost:
        r8 = INSTALL_SUCCEED;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0131, code lost:
        if (r8 != INSTALL_SUCCEED) goto L_0x0165;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x0133, code lost:
        r5 = r7.nativeLibraryDir;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0135, code lost:
        if (r5 == null) goto L_0x0165;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x013f, code lost:
        if (com.tencent.component.plugin.PluginNativeHelper.copyNativeBinariesIfNeeded(r3.getAbsolutePath(), r5) != false) goto L_0x0165;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x0141, code lost:
        r8 = INSTALL_SUCCEED_FAILED_COPY_LIB;
        com.tencent.component.utils.log.LogUtil.i(TAG, "cannot un-pack native libraries for plugin " + r7 + ", file " + r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x0165, code lost:
        if (r16 == false) goto L_0x0178;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x0169, code lost:
        if (r8 != INSTALL_SUCCEED) goto L_0x0178;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x016b, code lost:
        r7.installPath = r3.getAbsolutePath();
        r14.mPluginManagerServer.registerPlugin(r7.pluginId, r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x0178, code lost:
        if (r8 >= 0) goto L_0x01e4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x017a, code lost:
        com.tencent.component.utils.log.LogUtil.i(TAG, "fail to install plugin " + r7);
        com.tencent.component.plugin.PluginReporter.report(com.tencent.component.plugin.PluginReporter.EVENT_INSTALL, false, "cannot copy file", "srcFile:" + r15 + ", dstFile:" + r3, (java.lang.Throwable) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x01bb, code lost:
        if (r17 == false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x01c5, code lost:
        if (r14.mPlatformServerContext.getPlatformConfig().enbaleCorePlugin == false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x01c9, code lost:
        if (r7.corePlugin == false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x01cb, code lost:
        r14.mPlatformServerContext.getPluginLoader().load();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x01d6, code lost:
        r9 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x01da, code lost:
        throw r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x01db, code lost:
        r9 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x01dc, code lost:
        r4.unlock();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x01df, code lost:
        throw r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:?, code lost:
        r8 = INSTALL_FAILED_COPY_FILE;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x01e4, code lost:
        com.tencent.component.utils.log.LogUtil.i(TAG, "succeed to install plugin " + r7);
        com.tencent.component.plugin.PluginReporter.report(com.tencent.component.plugin.PluginReporter.EVENT_INSTALL, true, "succeed to install", "file:" + r15, (java.lang.Throwable) null);
        r10 = r7.pluginId;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x021a, code lost:
        if (r6 != null) goto L_0x0223;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x021c, code lost:
        r9 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x021d, code lost:
        broadcastPluginInstalled(r10, r9, r7.version);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x0223, code lost:
        r9 = r6.version;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:?, code lost:
        return INSTALL_FAILED_INTERNAL;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:?, code lost:
        return INSTALL_FAILED_VERIFY;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:?, code lost:
        return r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:?, code lost:
        return r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:?, code lost:
        return r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:?, code lost:
        return r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int performInstall(java.io.File r15, boolean r16, boolean r17) {
        /*
            r14 = this;
            boolean r9 = isFileValid(r15)
            if (r9 != 0) goto L_0x0046
            removeFile(r15)
            java.lang.String r9 = "install"
            r10 = 0
            java.lang.String r11 = "invalid file"
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            java.lang.String r13 = "file:"
            java.lang.StringBuilder r12 = r12.append(r13)
            java.lang.StringBuilder r12 = r12.append(r15)
            java.lang.String r12 = r12.toString()
            r13 = 0
            com.tencent.component.plugin.PluginReporter.report(r9, r10, r11, r12, r13)
            java.lang.String r9 = "PluginInstaller"
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r11 = "file "
            java.lang.StringBuilder r10 = r10.append(r11)
            java.lang.StringBuilder r10 = r10.append(r15)
            java.lang.String r11 = " is not valid"
            java.lang.StringBuilder r10 = r10.append(r11)
            java.lang.String r10 = r10.toString()
            com.tencent.component.utils.log.LogUtil.i(r9, r10)
            int r8 = INSTALL_FAILED_INVALID_FILE
        L_0x0045:
            return r8
        L_0x0046:
            monitor-enter(r14)
            boolean r9 = r14.ensureInstallDir()     // Catch:{ all -> 0x0062 }
            if (r9 != 0) goto L_0x0065
            java.lang.String r9 = "install"
            r10 = 0
            java.lang.String r11 = "invalid install dir"
            r12 = 0
            r13 = 0
            com.tencent.component.plugin.PluginReporter.report(r9, r10, r11, r12, r13)     // Catch:{ all -> 0x0062 }
            java.lang.String r9 = "PluginInstaller"
            java.lang.String r10 = "cannot create install dir"
            com.tencent.component.utils.log.LogUtil.i(r9, r10)     // Catch:{ all -> 0x0062 }
            int r8 = INSTALL_FAILED_INVALID_DIR     // Catch:{ all -> 0x0062 }
            monitor-exit(r14)     // Catch:{ all -> 0x0062 }
            goto L_0x0045
        L_0x0062:
            r9 = move-exception
            monitor-exit(r14)     // Catch:{ all -> 0x0062 }
            throw r9
        L_0x0065:
            monitor-exit(r14)     // Catch:{ all -> 0x0062 }
            com.tencent.component.plugin.server.PlatformServerContext r9 = r14.mPlatformServerContext
            android.content.Context r10 = r14.mContext
            java.lang.String r11 = r15.getAbsolutePath()
            r12 = 1
            com.tencent.component.plugin.PluginInfo r7 = com.tencent.component.plugin.server.PluginParser.parse(r9, r10, r11, r12)
            android.content.Context r9 = r14.mContext     // Catch:{ ValidateException -> 0x00ce }
            com.tencent.component.plugin.server.PluginValidator r9 = com.tencent.component.plugin.server.PluginValidator.getInstance(r9)     // Catch:{ ValidateException -> 0x00ce }
            com.tencent.component.plugin.server.PlatformServerContext r10 = r14.mPlatformServerContext     // Catch:{ ValidateException -> 0x00ce }
            r9.validate(r7, r10)     // Catch:{ ValidateException -> 0x00ce }
            r8 = -7
            com.tencent.component.utils.UniqueLock<java.lang.String> r9 = r14.mInstallLock
            java.lang.String r10 = r7.pluginId
            java.util.concurrent.locks.Lock r4 = r9.obtain(r10)
            r4.lock()
            java.io.File r3 = r14.generateInstallFile(r7)     // Catch:{ all -> 0x01db }
            if (r3 != 0) goto L_0x00fa
            removeFile(r15)     // Catch:{ all -> 0x01db }
            java.lang.String r9 = "install"
            r10 = 0
            java.lang.String r11 = "cannot generate install file"
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ all -> 0x01db }
            r12.<init>()     // Catch:{ all -> 0x01db }
            java.lang.String r13 = "plugin:"
            java.lang.StringBuilder r12 = r12.append(r13)     // Catch:{ all -> 0x01db }
            java.lang.StringBuilder r12 = r12.append(r7)     // Catch:{ all -> 0x01db }
            java.lang.String r12 = r12.toString()     // Catch:{ all -> 0x01db }
            r13 = 0
            com.tencent.component.plugin.PluginReporter.report(r9, r10, r11, r12, r13)     // Catch:{ all -> 0x01db }
            java.lang.String r9 = "PluginInstaller"
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ all -> 0x01db }
            r10.<init>()     // Catch:{ all -> 0x01db }
            java.lang.String r11 = "cannot generate install file for plugin "
            java.lang.StringBuilder r10 = r10.append(r11)     // Catch:{ all -> 0x01db }
            java.lang.StringBuilder r10 = r10.append(r7)     // Catch:{ all -> 0x01db }
            java.lang.String r10 = r10.toString()     // Catch:{ all -> 0x01db }
            com.tencent.component.utils.log.LogUtil.i(r9, r10)     // Catch:{ all -> 0x01db }
            int r8 = INSTALL_FAILED_INTERNAL     // Catch:{ all -> 0x01db }
            r4.unlock()
            goto L_0x0045
        L_0x00ce:
            r1 = move-exception
            removeFile(r15)
            java.lang.String r9 = "install"
            r10 = 0
            java.lang.String r11 = "verify error"
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            java.lang.String r13 = "plugin:"
            java.lang.StringBuilder r12 = r12.append(r13)
            java.lang.StringBuilder r12 = r12.append(r7)
            java.lang.String r12 = r12.toString()
            com.tencent.component.plugin.PluginReporter.report(r9, r10, r11, r12, r1)
            java.lang.String r9 = "PluginInstaller"
            java.lang.String r10 = r1.getMessage()
            com.tencent.component.utils.log.LogUtil.i(r9, r10, r1)
            int r8 = INSTALL_FAILED_VERIFY
            goto L_0x0045
        L_0x00fa:
            r6 = 0
            com.tencent.component.plugin.server.PluginManagerServer r9 = r14.mPluginManagerServer     // Catch:{ all -> 0x01db }
            java.lang.String r10 = r7.pluginId     // Catch:{ all -> 0x01db }
            boolean r9 = r9.isPluginRegistered(r10)     // Catch:{ all -> 0x01db }
            if (r9 == 0) goto L_0x0113
            com.tencent.component.plugin.server.PluginManagerServer r9 = r14.mPluginManagerServer     // Catch:{ all -> 0x01db }
            java.lang.String r10 = r7.pluginId     // Catch:{ all -> 0x01db }
            com.tencent.component.plugin.PluginInfo r6 = r9.getPluginInfo(r10)     // Catch:{ all -> 0x01db }
            r9 = 1
            r14.performUninstall(r6, r9)     // Catch:{ all -> 0x01db }
            r16 = 1
        L_0x0113:
            java.lang.String r9 = r3.getAbsolutePath()     // Catch:{ all -> 0x01db }
            java.util.concurrent.locks.Lock r2 = com.tencent.component.plugin.PluginFileLock.writeLock(r9)     // Catch:{ all -> 0x01db }
            r2.lock()     // Catch:{ all -> 0x01db }
            r14.copyFileSafely(r15, r3)     // Catch:{ all -> 0x01d6 }
            r2.unlock()     // Catch:{ all -> 0x01db }
            removeFile(r15)     // Catch:{ all -> 0x01db }
            boolean r9 = isFileValid(r3)     // Catch:{ all -> 0x01db }
            if (r9 == 0) goto L_0x01e0
            int r8 = INSTALL_SUCCEED     // Catch:{ all -> 0x01db }
        L_0x012f:
            int r9 = INSTALL_SUCCEED     // Catch:{ all -> 0x01db }
            if (r8 != r9) goto L_0x0165
            java.lang.String r5 = r7.nativeLibraryDir     // Catch:{ all -> 0x01db }
            if (r5 == 0) goto L_0x0165
            java.lang.String r9 = r3.getAbsolutePath()     // Catch:{ all -> 0x01db }
            boolean r0 = com.tencent.component.plugin.PluginNativeHelper.copyNativeBinariesIfNeeded((java.lang.String) r9, (java.lang.String) r5)     // Catch:{ all -> 0x01db }
            if (r0 != 0) goto L_0x0165
            int r8 = INSTALL_SUCCEED_FAILED_COPY_LIB     // Catch:{ all -> 0x01db }
            java.lang.String r9 = "PluginInstaller"
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ all -> 0x01db }
            r10.<init>()     // Catch:{ all -> 0x01db }
            java.lang.String r11 = "cannot un-pack native libraries for plugin "
            java.lang.StringBuilder r10 = r10.append(r11)     // Catch:{ all -> 0x01db }
            java.lang.StringBuilder r10 = r10.append(r7)     // Catch:{ all -> 0x01db }
            java.lang.String r11 = ", file "
            java.lang.StringBuilder r10 = r10.append(r11)     // Catch:{ all -> 0x01db }
            java.lang.StringBuilder r10 = r10.append(r3)     // Catch:{ all -> 0x01db }
            java.lang.String r10 = r10.toString()     // Catch:{ all -> 0x01db }
            com.tencent.component.utils.log.LogUtil.i(r9, r10)     // Catch:{ all -> 0x01db }
        L_0x0165:
            if (r16 == 0) goto L_0x0178
            int r9 = INSTALL_SUCCEED     // Catch:{ all -> 0x01db }
            if (r8 != r9) goto L_0x0178
            java.lang.String r9 = r3.getAbsolutePath()     // Catch:{ all -> 0x01db }
            r7.installPath = r9     // Catch:{ all -> 0x01db }
            com.tencent.component.plugin.server.PluginManagerServer r9 = r14.mPluginManagerServer     // Catch:{ all -> 0x01db }
            java.lang.String r10 = r7.pluginId     // Catch:{ all -> 0x01db }
            r9.registerPlugin(r10, r7)     // Catch:{ all -> 0x01db }
        L_0x0178:
            if (r8 >= 0) goto L_0x01e4
            java.lang.String r9 = "PluginInstaller"
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ all -> 0x01db }
            r10.<init>()     // Catch:{ all -> 0x01db }
            java.lang.String r11 = "fail to install plugin "
            java.lang.StringBuilder r10 = r10.append(r11)     // Catch:{ all -> 0x01db }
            java.lang.StringBuilder r10 = r10.append(r7)     // Catch:{ all -> 0x01db }
            java.lang.String r10 = r10.toString()     // Catch:{ all -> 0x01db }
            com.tencent.component.utils.log.LogUtil.i(r9, r10)     // Catch:{ all -> 0x01db }
            java.lang.String r9 = "install"
            r10 = 0
            java.lang.String r11 = "cannot copy file"
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ all -> 0x01db }
            r12.<init>()     // Catch:{ all -> 0x01db }
            java.lang.String r13 = "srcFile:"
            java.lang.StringBuilder r12 = r12.append(r13)     // Catch:{ all -> 0x01db }
            java.lang.StringBuilder r12 = r12.append(r15)     // Catch:{ all -> 0x01db }
            java.lang.String r13 = ", dstFile:"
            java.lang.StringBuilder r12 = r12.append(r13)     // Catch:{ all -> 0x01db }
            java.lang.StringBuilder r12 = r12.append(r3)     // Catch:{ all -> 0x01db }
            java.lang.String r12 = r12.toString()     // Catch:{ all -> 0x01db }
            r13 = 0
            com.tencent.component.plugin.PluginReporter.report(r9, r10, r11, r12, r13)     // Catch:{ all -> 0x01db }
        L_0x01b8:
            r4.unlock()
            if (r17 == 0) goto L_0x0045
            com.tencent.component.plugin.server.PlatformServerContext r9 = r14.mPlatformServerContext
            com.tencent.component.plugin.PluginPlatformConfig r9 = r9.getPlatformConfig()
            boolean r9 = r9.enbaleCorePlugin
            if (r9 == 0) goto L_0x0045
            boolean r9 = r7.corePlugin
            if (r9 == 0) goto L_0x0045
            com.tencent.component.plugin.server.PlatformServerContext r9 = r14.mPlatformServerContext
            com.tencent.component.plugin.server.PluginLoader r9 = r9.getPluginLoader()
            r9.load()
            goto L_0x0045
        L_0x01d6:
            r9 = move-exception
            r2.unlock()     // Catch:{ all -> 0x01db }
            throw r9     // Catch:{ all -> 0x01db }
        L_0x01db:
            r9 = move-exception
            r4.unlock()
            throw r9
        L_0x01e0:
            int r8 = INSTALL_FAILED_COPY_FILE     // Catch:{ all -> 0x01db }
            goto L_0x012f
        L_0x01e4:
            java.lang.String r9 = "PluginInstaller"
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ all -> 0x01db }
            r10.<init>()     // Catch:{ all -> 0x01db }
            java.lang.String r11 = "succeed to install plugin "
            java.lang.StringBuilder r10 = r10.append(r11)     // Catch:{ all -> 0x01db }
            java.lang.StringBuilder r10 = r10.append(r7)     // Catch:{ all -> 0x01db }
            java.lang.String r10 = r10.toString()     // Catch:{ all -> 0x01db }
            com.tencent.component.utils.log.LogUtil.i(r9, r10)     // Catch:{ all -> 0x01db }
            java.lang.String r9 = "install"
            r10 = 1
            java.lang.String r11 = "succeed to install"
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ all -> 0x01db }
            r12.<init>()     // Catch:{ all -> 0x01db }
            java.lang.String r13 = "file:"
            java.lang.StringBuilder r12 = r12.append(r13)     // Catch:{ all -> 0x01db }
            java.lang.StringBuilder r12 = r12.append(r15)     // Catch:{ all -> 0x01db }
            java.lang.String r12 = r12.toString()     // Catch:{ all -> 0x01db }
            r13 = 0
            com.tencent.component.plugin.PluginReporter.report(r9, r10, r11, r12, r13)     // Catch:{ all -> 0x01db }
            java.lang.String r10 = r7.pluginId     // Catch:{ all -> 0x01db }
            if (r6 != 0) goto L_0x0223
            r9 = 0
        L_0x021d:
            int r11 = r7.version     // Catch:{ all -> 0x01db }
            r14.broadcastPluginInstalled(r10, r9, r11)     // Catch:{ all -> 0x01db }
            goto L_0x01b8
        L_0x0223:
            int r9 = r6.version     // Catch:{ all -> 0x01db }
            goto L_0x021d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.component.plugin.server.PluginInstaller.performInstall(java.io.File, boolean, boolean):int");
    }

    private void broadcastPluginInstalled(String id, int oldVersion, int version) {
        this.mPlatformServerContext.broadcastPluginInstalled(id, oldVersion, version);
    }

    private void broadcastPluginUninstalled(String id) {
        this.mPlatformServerContext.broadcastPluginUninstalled(id);
    }

    private boolean performUninstall(PluginInfo pluginInfo, boolean unregister) {
        if (pluginInfo == null) {
            return false;
        }
        Lock fileLock = this.mInstallLock.obtain(pluginInfo.pluginId);
        fileLock.lock();
        try {
            LogUtil.i(TAG, "performUninstall id:" + pluginInfo.pluginId + ",pluginInfo.isInternal:" + pluginInfo.isInternal() + ",pluginInfo.corePlugin:" + pluginInfo.corePlugin);
            if (!pluginInfo.isInternal()) {
                String pluginPath = pluginInfo.installPath;
                if (pluginPath != null) {
                    fileLock = PluginFileLock.writeLock(pluginPath);
                    fileLock.lock();
                    removeFile(new File(pluginPath));
                    fileLock.unlock();
                    String dexDir = pluginInfo.dexOptimizeDir;
                    if (dexDir != null) {
                        String dexName = PluginConstant.getPluginDexOptimizeName(pluginInfo);
                        LogUtil.i(TAG, "remove " + pluginInfo.pluginId + " dexOpt :" + dexDir + Constants.URL_PATH_DELIMITER + dexName);
                        if (dexName != null) {
                            removeFile(new File(dexDir, dexName));
                        }
                    }
                }
                String nativeLibDir = pluginInfo.nativeLibraryDir;
                if (nativeLibDir != null && !PluginNativeHelper.removeNativeBinaries(nativeLibDir)) {
                    LogUtil.i(TAG, "cannot remove native libraries for plugin " + pluginInfo + ", file " + pluginPath);
                }
            }
            if (unregister && !TextUtils.isEmpty(pluginInfo.pluginId)) {
                this.mPluginManagerServer.unregisterPlugin(pluginInfo.pluginId);
            }
            broadcastPluginUninstalled(pluginInfo.pluginId);
            return true;
        } catch (Throwable th) {
            throw th;
        } finally {
            fileLock.unlock();
        }
    }

    private void clearResourceCachesWithReflect(PluginInfo pluginInfo) {
        if (pluginInfo == null) {
            LogUtil.e(TAG, "clearResourceCachesWithReflect plugin info is null");
            return;
        }
        String installPath = pluginInfo.installPath;
        if (TextUtils.isEmpty(installPath)) {
            LogUtil.e(TAG, "clearResourceCachesWithReflect plugin install path is empty");
            return;
        }
        LogUtil.d(TAG, "clearResourceCachesWithReflect plugin path: " + installPath);
        boolean cleared = false;
        try {
            Class<?> resourcesManagerCls = Class.forName("android.app.ResourcesManager");
            Method getInstanceMethod = resourcesManagerCls.getMethod("getInstance", new Class[0]);
            Method invalidatePathMethod = resourcesManagerCls.getMethod("invalidatePath", new Class[]{String.class});
            Object resourcesManager = getInstanceMethod.invoke(resourcesManagerCls, new Object[0]);
            if (resourcesManager != null) {
                invalidatePathMethod.invoke(resourcesManager, new Object[]{installPath});
                cleared = true;
                LogUtil.i(TAG, "clearResourceCachesWithReflect success");
            }
        } catch (ClassNotFoundException e) {
            LogUtil.e(TAG, "clearResourceCachesWithReflect failed, ClassNotFoundException : " + (e != null ? e.getMessage() : ""), e);
            e.printStackTrace();
        } catch (NoSuchMethodException e2) {
            LogUtil.e(TAG, "clearResourceCachesWithReflect failed, NoSuchMethodException : " + (e2 != null ? e2.getMessage() : ""), e2);
            e2.printStackTrace();
        } catch (IllegalAccessException e3) {
            LogUtil.e(TAG, "clearResourceCachesWithReflect failed, IllegalAccessException : " + (e3 != null ? e3.getMessage() : ""), e3);
            e3.printStackTrace();
        } catch (InvocationTargetException e4) {
            LogUtil.e(TAG, "clearResourceCachesWithReflect failed, InvocationTargetException : " + (e4 != null ? e4.getMessage() : ""), e4);
            e4.printStackTrace();
        } catch (Throwable tr) {
            LogUtil.e(TAG, "clearResourceCachesWithReflect failed, error : " + (tr != null ? tr.getMessage() : ""), tr);
            if (tr != null) {
                tr.printStackTrace();
            }
        }
        if (!cleared) {
            LogUtil.i(TAG, "clearResourceCachesWithReflect failed.");
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x005f, code lost:
        r1 = generatePendingFile(r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0063, code lost:
        if (r1 != null) goto L_0x009a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0065, code lost:
        com.tencent.component.plugin.PluginReporter.report(com.tencent.component.plugin.PluginReporter.EVENT_INSTALL, false, "cannot generate pending path", "file:" + r10, (java.lang.Throwable) null);
        com.tencent.component.utils.log.LogUtil.i(TAG, "cannot generate pending file for file " + r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x009a, code lost:
        r0 = r9.mPendingLock.obtain(r1.getAbsolutePath());
        r0.lock();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x00ab, code lost:
        if (isAlreadyPending(r10) == false) goto L_0x00b3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
        copyFileSafely(r10, r1);
        removeFile(r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00bd, code lost:
        if (isFileValid(r1) == false) goto L_0x00ee;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00bf, code lost:
        r2 = INSTALL_SUCCEED;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00c1, code lost:
        if (r2 >= 0) goto L_0x00f1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00c3, code lost:
        com.tencent.component.plugin.PluginReporter.report(com.tencent.component.plugin.PluginReporter.EVENT_INSTALL, false, "cannot copy file", "srcFile:" + r10 + ", dstFile:" + r1, (java.lang.Throwable) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00e9, code lost:
        r0.unlock();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:?, code lost:
        r2 = INSTALL_FAILED_COPY_FILE;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00f1, code lost:
        com.tencent.component.plugin.PluginReporter.report(com.tencent.component.plugin.PluginReporter.EVENT_INSTALL, true, "succeed to install", "file:" + r10, (java.lang.Throwable) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x010e, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x010f, code lost:
        r0.unlock();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0112, code lost:
        throw r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:?, code lost:
        return INSTALL_FAILED_INTERNAL;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:?, code lost:
        return INSTALL_FAILED_ALREADY_EXISTS;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:?, code lost:
        return r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int performPending(java.io.File r10) {
        /*
            r9 = this;
            r8 = 0
            r7 = 0
            boolean r3 = isFileValid(r10)
            if (r3 != 0) goto L_0x0046
            removeFile(r10)
            java.lang.String r3 = "install"
            java.lang.String r4 = "invalid file"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "file:"
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.StringBuilder r5 = r5.append(r10)
            java.lang.String r5 = r5.toString()
            com.tencent.component.plugin.PluginReporter.report(r3, r8, r4, r5, r7)
            java.lang.String r3 = "PluginInstaller"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "file "
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.StringBuilder r4 = r4.append(r10)
            java.lang.String r5 = " is not valid"
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r4 = r4.toString()
            com.tencent.component.utils.log.LogUtil.i(r3, r4)
            int r2 = INSTALL_FAILED_INVALID_FILE
        L_0x0045:
            return r2
        L_0x0046:
            monitor-enter(r9)
            boolean r3 = r9.ensurePendingDir()     // Catch:{ all -> 0x005b }
            if (r3 != 0) goto L_0x005e
            java.lang.String r3 = "install"
            r4 = 0
            java.lang.String r5 = "invalid pending dir"
            r6 = 0
            r7 = 0
            com.tencent.component.plugin.PluginReporter.report(r3, r4, r5, r6, r7)     // Catch:{ all -> 0x005b }
            int r2 = INSTALL_FAILED_INVALID_DIR     // Catch:{ all -> 0x005b }
            monitor-exit(r9)     // Catch:{ all -> 0x005b }
            goto L_0x0045
        L_0x005b:
            r3 = move-exception
            monitor-exit(r9)     // Catch:{ all -> 0x005b }
            throw r3
        L_0x005e:
            monitor-exit(r9)     // Catch:{ all -> 0x005b }
            java.io.File r1 = r9.generatePendingFile(r10)
            if (r1 != 0) goto L_0x009a
            java.lang.String r3 = "install"
            java.lang.String r4 = "cannot generate pending path"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "file:"
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.StringBuilder r5 = r5.append(r10)
            java.lang.String r5 = r5.toString()
            com.tencent.component.plugin.PluginReporter.report(r3, r8, r4, r5, r7)
            java.lang.String r3 = "PluginInstaller"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "cannot generate pending file for file "
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.StringBuilder r4 = r4.append(r10)
            java.lang.String r4 = r4.toString()
            com.tencent.component.utils.log.LogUtil.i(r3, r4)
            int r2 = INSTALL_FAILED_INTERNAL
            goto L_0x0045
        L_0x009a:
            com.tencent.component.utils.UniqueLock<java.lang.String> r3 = r9.mPendingLock
            java.lang.String r4 = r1.getAbsolutePath()
            java.util.concurrent.locks.Lock r0 = r3.obtain(r4)
            r0.lock()
            boolean r3 = r9.isAlreadyPending(r10)     // Catch:{ all -> 0x010e }
            if (r3 == 0) goto L_0x00b3
            int r2 = INSTALL_FAILED_ALREADY_EXISTS     // Catch:{ all -> 0x010e }
            r0.unlock()
            goto L_0x0045
        L_0x00b3:
            r9.copyFileSafely(r10, r1)     // Catch:{ all -> 0x010e }
            removeFile(r10)     // Catch:{ all -> 0x010e }
            boolean r3 = isFileValid(r1)     // Catch:{ all -> 0x010e }
            if (r3 == 0) goto L_0x00ee
            int r2 = INSTALL_SUCCEED     // Catch:{ all -> 0x010e }
        L_0x00c1:
            if (r2 >= 0) goto L_0x00f1
            java.lang.String r3 = "install"
            r4 = 0
            java.lang.String r5 = "cannot copy file"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x010e }
            r6.<init>()     // Catch:{ all -> 0x010e }
            java.lang.String r7 = "srcFile:"
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ all -> 0x010e }
            java.lang.StringBuilder r6 = r6.append(r10)     // Catch:{ all -> 0x010e }
            java.lang.String r7 = ", dstFile:"
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ all -> 0x010e }
            java.lang.StringBuilder r6 = r6.append(r1)     // Catch:{ all -> 0x010e }
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x010e }
            r7 = 0
            com.tencent.component.plugin.PluginReporter.report(r3, r4, r5, r6, r7)     // Catch:{ all -> 0x010e }
        L_0x00e9:
            r0.unlock()
            goto L_0x0045
        L_0x00ee:
            int r2 = INSTALL_FAILED_COPY_FILE     // Catch:{ all -> 0x010e }
            goto L_0x00c1
        L_0x00f1:
            java.lang.String r3 = "install"
            r4 = 1
            java.lang.String r5 = "succeed to install"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x010e }
            r6.<init>()     // Catch:{ all -> 0x010e }
            java.lang.String r7 = "file:"
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ all -> 0x010e }
            java.lang.StringBuilder r6 = r6.append(r10)     // Catch:{ all -> 0x010e }
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x010e }
            r7 = 0
            com.tencent.component.plugin.PluginReporter.report(r3, r4, r5, r6, r7)     // Catch:{ all -> 0x010e }
            goto L_0x00e9
        L_0x010e:
            r3 = move-exception
            r0.unlock()
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.component.plugin.server.PluginInstaller.performPending(java.io.File):int");
    }

    private void copyFileSafely(File src, File dst) {
        if (src != null && dst != null && isFileValid(src)) {
            copyFile(src, dst);
            if (isFileValid(dst)) {
                return;
            }
            if ((isInternal(src) && isInternal(dst)) || (!isInternal(src) && !isInternal(dst))) {
                File tmpFile = generateTmpFile(!isInternal(dst));
                if (tmpFile != null) {
                    copyFile(src, tmpFile);
                    removeFile(src);
                    copyFile(tmpFile, dst);
                    removeFile(tmpFile);
                }
            }
        }
    }

    private boolean ensureInstallDir() {
        return ensureDir(this.mPluginDir);
    }

    private File generateInstallFile(PluginInfo pluginInfo) {
        String installName = PluginConstant.getPluginInstallName(pluginInfo);
        if (TextUtils.isEmpty(installName)) {
            return null;
        }
        return new File(this.mPluginDir, installName);
    }

    private boolean ensurePendingDir() {
        return ensureDir(this.mPluginPendingDir);
    }

    private File generatePendingFile(File file) {
        if (file == null) {
            return null;
        }
        return new File(this.mPluginPendingDir, file.getName());
    }

    private boolean isAlreadyPending(File file) {
        return file.getAbsolutePath().startsWith(this.mPluginPendingDir.getAbsolutePath());
    }

    private File generateTmpFile(boolean external) {
        String path = PluginConstant.getPluginTmpDir(this.mContext, UUID.randomUUID().toString(), external, true);
        if (path != null) {
            return new File(path);
        }
        return null;
    }

    private static void copyFile(File src, File dst) {
        FileUtil.copyFiles(src, dst);
    }

    private static void removeFile(File file) {
        FileUtil.delete(file);
    }

    private static boolean isDirValid(File dir) {
        return dir != null && dir.isDirectory() && dir.exists();
    }

    private static boolean isFileValid(File file) {
        return file != null && file.isFile() && file.length() > 0;
    }

    private static boolean ensureDir(File dir) {
        if (isDirValid(dir)) {
            return true;
        }
        FileUtil.delete(dir);
        return dir.mkdirs();
    }

    private static boolean isInternal(File file) {
        String path = file == null ? null : file.getAbsolutePath();
        return path != null && path.startsWith(Environment.getDataDirectory().getAbsolutePath());
    }
}
