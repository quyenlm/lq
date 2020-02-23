package com.tencent.component.utils.log;

import android.content.SharedPreferences;
import com.tencent.component.debug.FileTracer;
import com.tencent.component.debug.FileTracerConfig;
import com.tencent.component.debug.LogcatTracer;
import com.tencent.component.debug.TraceLevel;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class AppTracer implements TraceLevel, SharedPreferences.OnSharedPreferenceChangeListener {
    private volatile boolean enabled = true;
    private FileTracer fileTracer;
    private volatile boolean fileTracerEnabled = true;
    private volatile boolean logcatTracerEnabled = true;

    /* JADX WARNING: Removed duplicated region for block: B:14:0x0043 A[SYNTHETIC, Splitter:B:14:0x0043] */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0061  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.io.File getLogFilePath() {
        /*
            boolean r7 = com.tencent.component.utils.FileUtil.isExternalAvailable()
            java.lang.String r0 = "logs"
            java.lang.String r5 = "GameJoyRecorder"
            r2 = 0
            if (r7 == 0) goto L_0x0063
            r4 = 0
            int r8 = android.os.Build.VERSION.SDK_INT
            r9 = 23
            if (r8 < r9) goto L_0x0051
            boolean r8 = android.text.TextUtils.isEmpty(r5)
            if (r8 != 0) goto L_0x0051
            android.content.Context r8 = com.tencent.component.ComponentContext.getContext()
            java.lang.String r9 = ""
            java.lang.String r1 = com.tencent.component.utils.FileUtil.getExternalCacheDir((android.content.Context) r8, (java.lang.String) r9)
            boolean r8 = android.text.TextUtils.isEmpty(r1)
            if (r8 != 0) goto L_0x0036
            java.io.File r6 = new java.io.File
            r6.<init>(r1, r5)
            java.io.File r8 = new java.io.File
            r8.<init>(r6, r0)
            java.lang.String r4 = r8.getAbsolutePath()
        L_0x0036:
            boolean r8 = android.text.TextUtils.isEmpty(r4)
            if (r8 != 0) goto L_0x0063
            java.io.File r3 = new java.io.File     // Catch:{ Exception -> 0x005b }
            r3.<init>(r4)     // Catch:{ Exception -> 0x005b }
        L_0x0041:
            if (r3 != 0) goto L_0x0061
            java.io.File r2 = new java.io.File     // Catch:{ Exception -> 0x005e }
            android.content.Context r8 = com.tencent.component.ComponentContext.getContext()     // Catch:{ Exception -> 0x005e }
            java.io.File r8 = r8.getFilesDir()     // Catch:{ Exception -> 0x005e }
            r2.<init>(r8, r0)     // Catch:{ Exception -> 0x005e }
        L_0x0050:
            return r2
        L_0x0051:
            android.content.Context r8 = com.tencent.component.ComponentContext.getContext()
            r9 = 1
            java.lang.String r4 = com.tencent.component.utils.FileUtil.getExternalCacheDirExt(r8, r0, r9)
            goto L_0x0036
        L_0x005b:
            r8 = move-exception
            r3 = r2
            goto L_0x0041
        L_0x005e:
            r8 = move-exception
            r2 = r3
            goto L_0x0050
        L_0x0061:
            r2 = r3
            goto L_0x0050
        L_0x0063:
            r3 = r2
            goto L_0x0041
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.component.utils.log.AppTracer.getLogFilePath():java.io.File");
    }

    public AppTracer(FileTracerConfig tracerConfig) {
        this.fileTracer = new FileTracer(tracerConfig);
        LogConfig.getInstance().startListen(this);
    }

    public void stop() {
        if (this.fileTracer != null) {
            this.fileTracer.flush();
            this.fileTracer.quit();
        }
    }

    public void flush() {
        if (this.fileTracer != null) {
            this.fileTracer.flush();
        }
    }

    public void trace(int level, String tag, String msg, Throwable tr) {
        if (isEnabled()) {
            if (isFileTracerEnabled() && this.fileTracer != null) {
                this.fileTracer.trace(level, Thread.currentThread(), System.currentTimeMillis(), tag, msg, tr);
            }
            if (isLogcatTracerEnabled()) {
                LogcatTracer.Instance.trace(level, Thread.currentThread(), System.currentTimeMillis(), tag, msg, tr);
            }
        }
    }

    public final void setEnabled(boolean enabled2) {
        this.enabled = enabled2;
    }

    public final boolean isEnabled() {
        return this.enabled;
    }

    public final void setFileTracerLevel(int traceLevel) {
        this.fileTracer.setTraceLevel(traceLevel);
    }

    public final void setFileTracerEnabled(boolean fileTracerEnabled2) {
        this.fileTracer.flush();
        this.fileTracerEnabled = fileTracerEnabled2;
    }

    public final boolean isFileTracerEnabled() {
        return this.fileTracerEnabled;
    }

    public final void setLogcatTracerEnabled(boolean logcatTracerEnabled2) {
        this.logcatTracerEnabled = logcatTracerEnabled2;
    }

    public final boolean isLogcatTracerEnabled() {
        return this.logcatTracerEnabled;
    }

    public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
        if (LogConfig.FileTraceLevel.equals(key) || key == null) {
            int traceLevel = LogConfig.getInstance().getFileTraceLevel();
            trace(16, "WnsTracer", "File Trace Level Changed = " + traceLevel, (Throwable) null);
            this.fileTracer.setTraceLevel(traceLevel);
        }
    }

    public BufferedReader myLogReader(int pageIndex) {
        File folder = this.fileTracer.getConfig().getWorkFolder(System.currentTimeMillis());
        if (folder == null || !folder.isDirectory()) {
            return null;
        }
        File[] files = this.fileTracer.getConfig().sortBlocksByIndex(this.fileTracer.getConfig().getAllBlocksInFolder(folder));
        if (pageIndex < 0 || pageIndex >= files.length) {
            return null;
        }
        try {
            FileReader fr = new FileReader(files[(files.length - pageIndex) - 1]);
            try {
                return new BufferedReader(fr);
            } catch (FileNotFoundException e) {
                FileReader fileReader = fr;
                return null;
            }
        } catch (FileNotFoundException e2) {
            return null;
        }
    }

    public File getWorkerFolder() {
        return this.fileTracer.getConfig().getWorkFolder(System.currentTimeMillis());
    }

    public File getWorkerFolder(long time) {
        return this.fileTracer.getConfig().getWorkFolder(time);
    }

    public void cleanLog() {
        File[] files = this.fileTracer.getConfig().getAllBlocksInFolder(this.fileTracer.getConfig().getWorkFolder(System.currentTimeMillis()));
        if (files != null) {
            for (File deleteFile : files) {
                deleteFile(deleteFile);
            }
        }
    }

    public static void deleteFile(File file) {
        if (file != null && file.exists()) {
            if (file.isFile()) {
                file.delete();
                return;
            }
            for (File f : file.listFiles()) {
                deleteFile(f);
            }
        }
    }
}
