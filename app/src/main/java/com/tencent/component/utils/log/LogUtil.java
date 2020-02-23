package com.tencent.component.utils.log;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.tencent.component.ComponentContext;
import com.tencent.component.annotation.PluginApi;
import com.tencent.component.debug.FileTracerConfig;
import com.tencent.component.debug.TraceLevel;
import com.tencent.component.utils.ProcessUtils;
import java.io.File;

@PluginApi(since = 6)
public class LogUtil {
    private static DefaultLogProxy DEFAULT_PROXY = null;
    private static volatile boolean logcatEnable = true;
    private static volatile LogProxy sProxy = DEFAULT_PROXY;
    private static volatile int traceLevel = 63;

    public interface LogProxy extends TraceLevel {
        void d(String str, String str2);

        void e(String str, String str2);

        void flush();

        File getWorkerFolder();

        File getWorkerFolder(long j);

        void i(String str, String str2);

        void setFileLogEnable(boolean z);

        void setLogcatEnable(boolean z);

        void setTraceLevel(int i);

        void v(String str, String str2);

        void w(String str, String str2);
    }

    private static class DefaultLogProxy implements LogProxy {
        private volatile boolean isInit;
        private volatile AppTracer mTracer;

        private DefaultLogProxy() {
            this.isInit = false;
            init();
        }

        /* access modifiers changed from: private */
        public boolean isInit() {
            return this.isInit;
        }

        /* access modifiers changed from: private */
        public void init() {
            if (ComponentContext.getContext() == null) {
                this.isInit = false;
                return;
            }
            try {
                File rootPath = AppTracer.getLogFilePath();
                if (rootPath != null) {
                    int blockCount = LogConfig.getInstance().getMaxFolderSize();
                    long keepPeriod = LogConfig.getInstance().getMaxKeepPeriod();
                    Context context = ComponentContext.getContext();
                    String processName = null;
                    if (!(context == null || (processName = ProcessUtils.myProcessName(context)) == null)) {
                        processName = processName.toLowerCase().replace(':', '.');
                    }
                    if (TextUtils.isEmpty(processName)) {
                        processName = "main";
                    }
                    this.mTracer = new AppTracer(new FileTracerConfig(rootPath, blockCount, 262144, 8192, "file.tracer." + processName, 10000, 10, "." + processName + FileTracerConfig.DEF_TRACE_FILEEXT, keepPeriod));
                    this.isInit = true;
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        public void v(String tag, String msg) {
            if (this.mTracer != null) {
                this.mTracer.trace(1, tag, msg, (Throwable) null);
            }
        }

        public void d(String tag, String msg) {
            if (this.mTracer != null) {
                this.mTracer.trace(2, tag, msg, (Throwable) null);
            }
        }

        public void i(String tag, String msg) {
            if (this.mTracer != null) {
                this.mTracer.trace(4, tag, msg, (Throwable) null);
            }
        }

        public void w(String tag, String msg) {
            if (this.mTracer != null) {
                this.mTracer.trace(8, tag, msg, (Throwable) null);
            }
        }

        public void e(String tag, String msg) {
            if (this.mTracer != null) {
                this.mTracer.trace(16, tag, msg, (Throwable) null);
            }
        }

        public void flush() {
            if (this.mTracer != null) {
                this.mTracer.flush();
            }
        }

        public void setTraceLevel(int level) {
            if (this.mTracer != null) {
                this.mTracer.setFileTracerLevel(level);
            }
        }

        public void setLogcatEnable(boolean enable) {
            if (this.mTracer != null) {
                this.mTracer.setLogcatTracerEnabled(enable);
            }
        }

        public void setFileLogEnable(boolean enable) {
            if (this.mTracer != null) {
                this.mTracer.setFileTracerEnabled(enable);
            }
        }

        public File getWorkerFolder() {
            if (this.mTracer != null) {
                return this.mTracer.getWorkerFolder();
            }
            return null;
        }

        public File getWorkerFolder(long time) {
            if (this.mTracer != null) {
                return this.mTracer.getWorkerFolder(time);
            }
            return null;
        }
    }

    @PluginApi(since = 6)
    public static void v(String tag, String msg) {
        getProxy().v(tag, msg);
    }

    @PluginApi(since = 6)
    public static void v(String tag, String msg, Throwable tr) {
        getProxy().v(tag, msg + 10 + getStackTraceString(tr));
    }

    @PluginApi(since = 6)
    public static void d(String tag, String msg) {
        getProxy().d(tag, msg);
    }

    @PluginApi(since = 6)
    public static void d(String tag, String msg, Throwable tr) {
        getProxy().d(tag, msg + 10 + getStackTraceString(tr));
    }

    @PluginApi(since = 6)
    public static void i(String tag, String msg) {
        getProxy().i(tag, msg);
    }

    @PluginApi(since = 6)
    public static void i(String tag, String msg, Throwable tr) {
        getProxy().i(tag, msg + 10 + getStackTraceString(tr));
    }

    @PluginApi(since = 6)
    public static void w(String tag, String msg) {
        getProxy().w(tag, msg);
    }

    @PluginApi(since = 6)
    public static void w(String tag, String msg, Throwable tr) {
        getProxy().w(tag, msg + 10 + getStackTraceString(tr));
    }

    @PluginApi(since = 6)
    public static void w(String tag, Throwable tr) {
        getProxy().w(tag, getStackTraceString(tr));
    }

    @PluginApi(since = 6)
    public static void e(String tag, String msg) {
        getProxy().e(tag, msg);
    }

    @PluginApi(since = 6)
    public static void e(String tag, String msg, Throwable tr) {
        getProxy().e(tag, msg + 10 + getStackTraceString(tr));
    }

    public static void flush() {
        getProxy().flush();
    }

    public static void setTraceLevel(int level) {
        traceLevel = level;
        getProxy().setTraceLevel(level);
    }

    public static void setLogcatEnable(boolean enable) {
        getProxy().setLogcatEnable(enable);
        logcatEnable = enable;
    }

    public static void setFileLogEnable(boolean enable) {
        getProxy().setFileLogEnable(enable);
    }

    public static void setProxy(LogProxy proxy) {
        synchronized (LogUtil.class) {
            sProxy = proxy;
        }
    }

    private static LogProxy getProxy() {
        if (sProxy == null && DEFAULT_PROXY == null) {
            DEFAULT_PROXY = new DefaultLogProxy();
        }
        LogProxy proxy = sProxy != null ? sProxy : DEFAULT_PROXY;
        if (proxy instanceof DefaultLogProxy) {
            DefaultLogProxy defaultLogProxy = (DefaultLogProxy) proxy;
            if (!defaultLogProxy.isInit()) {
                defaultLogProxy.init();
            }
        }
        return proxy;
    }

    private static String getStackTraceString(Throwable tr) {
        return Log.getStackTraceString(tr);
    }

    @PluginApi(since = 6)
    public static File getWorkerFolder() {
        return getProxy().getWorkerFolder();
    }

    @PluginApi(since = 600)
    public static File getWorkerFolder(long time) {
        return getProxy().getWorkerFolder(time);
    }
}
