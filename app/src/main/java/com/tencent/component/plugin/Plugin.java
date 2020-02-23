package com.tencent.component.plugin;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.tencent.component.annotation.PluginApi;
import com.tencent.component.plugin.annotation.CorePluginApi;
import com.tencent.component.utils.DebugUtil;
import com.tencent.component.utils.log.LogUtil;

public abstract class Plugin {
    private static final String TAG = "Plugin";
    private static PluginClassLoaderInterceptor sDefaultClassLoaderInterceptor = new PluginClassLoaderInterceptor();
    private volatile boolean mCalled;
    private Context mContext;
    private PluginHelper mPluginHelper;
    private PluginInfo mPluginInfo;
    private PluginManager mPluginManager;
    private volatile boolean mStarted;
    private Handler mUIHandler = new Handler(Looper.getMainLooper());

    /* access modifiers changed from: package-private */
    public final void attach(Context appContext, PluginManager pluginManager, PluginHelper pluginHelper, PluginInfo pluginInfo) {
        this.mPluginInfo = pluginInfo;
        this.mPluginManager = pluginManager;
        this.mPluginHelper = pluginHelper;
        this.mContext = new PluginContextWrapper(appContext.getApplicationContext(), pluginInfo, this);
        onAttach();
    }

    /* access modifiers changed from: protected */
    public void onAttach() {
    }

    @CorePluginApi(since = 400)
    public final PluginInfo getPluginInfo() {
        return this.mPluginInfo;
    }

    /* access modifiers changed from: protected */
    @PluginApi(since = 6)
    public final PluginInfo myPluginInfo() {
        return this.mPluginInfo;
    }

    @PluginApi(since = 4)
    public final PluginManager getPluginManager() {
        return this.mPluginManager;
    }

    @PluginApi(since = 4)
    public final PluginHelper getPluginHelper() {
        return this.mPluginHelper;
    }

    @PluginApi(since = 4)
    public final Context getContext() {
        return this.mContext;
    }

    @PluginApi(since = 4)
    public final Resources getResources() {
        return this.mContext.getResources();
    }

    public final void start(Context context, Intent args) {
        if (context == null) {
            throw new IllegalArgumentException("context cannot be null.");
        }
        PluginInfo pluginInfo = this.mPluginInfo;
        if (pluginInfo != null && !TextUtils.isEmpty(pluginInfo.launchFragment)) {
            this.mPluginHelper.startActivity(context, getPluginInfo(), (String) null, args);
        }
        notifyStartIfNeeded(true, args);
    }

    /* access modifiers changed from: package-private */
    public final void notifyStartIfNeeded() {
        notifyStartIfNeeded(false, (Intent) null);
    }

    private void notifyStartIfNeeded(boolean force, final Intent intent) {
        if (force || !this.mStarted) {
            this.mStarted = true;
            runOnUIThread(new Runnable() {
                public void run() {
                    Plugin.this.onStartInner(intent);
                }
            });
        }
    }

    /* access modifiers changed from: package-private */
    public final void stop() {
        runOnUIThread(new Runnable() {
            public void run() {
                Plugin.this.onStopInner();
            }
        });
    }

    /* access modifiers changed from: package-private */
    public final void enterBackground() {
        runOnUIThread(new Runnable() {
            public void run() {
                Plugin.this.onEnterBackgroundInner();
            }
        });
    }

    /* access modifiers changed from: package-private */
    public void performCreate() {
        runOnUIThread(new Runnable() {
            public void run() {
                Plugin.this.onCreateInner();
            }
        });
    }

    @PluginApi(since = 4)
    public void onCreate() {
        this.mCalled = true;
    }

    /* access modifiers changed from: private */
    public void onCreateInner() {
        this.mCalled = false;
        onCreate();
        checkSuperMethodCalled("onCreate()");
    }

    private void checkSuperMethodCalled(String method) {
    }

    @PluginApi(since = 7)
    public void onBusinessLifeCycle(int type, Object datas) {
    }

    private boolean isNeedCheckSuperHasCalled() {
        return DebugUtil.isDebuggable();
    }

    @PluginApi(since = 4)
    @Deprecated
    public void onStart() {
        this.mCalled = true;
    }

    @PluginApi(since = 300)
    public void onStart(Intent intent) {
        this.mCalled = true;
    }

    /* access modifiers changed from: private */
    public void onStartInner(Intent intent) {
        this.mCalled = false;
        if (intent == null) {
            onStart();
        }
        onStart(intent);
        checkSuperMethodCalled("onStart()");
    }

    @PluginApi(since = 4)
    public void onStop() {
        this.mCalled = true;
    }

    /* access modifiers changed from: private */
    public void onStopInner() {
        this.mCalled = false;
        onStop();
        checkSuperMethodCalled("onStop()");
    }

    @PluginApi(since = 4)
    public void onEnterBackground() {
        this.mCalled = true;
    }

    @PluginApi(since = 300)
    public PluginCommander getPluginCommander() {
        return null;
    }

    @PluginApi(since = 300)
    public PluginReceiverHandler getPluginReceiverHandler() {
        return null;
    }

    @PluginApi(since = 400)
    public PluginClassLoaderInterceptor getClassLoaderInterceptor() {
        return sDefaultClassLoaderInterceptor;
    }

    /* access modifiers changed from: private */
    public void onEnterBackgroundInner() {
        this.mCalled = false;
        onEnterBackground();
        checkSuperMethodCalled("onEnterBackground()");
    }

    static Plugin instantiate(Context context, PluginInfo pluginInfo) {
        Plugin plugin = null;
        if (pluginInfo != null) {
            String pluginClass = pluginInfo.pluginClass;
            if (!TextUtils.isEmpty(pluginClass)) {
                try {
                    PluginClassLoader classLoader = PluginClassLoader.obtainClassLoader(context, pluginInfo);
                    Class<?> clazz = classLoader.loadClass(pluginClass);
                    LogUtil.d(TAG, "new plugin for " + pluginInfo.pluginId + " " + pluginInfo.installPath);
                    if (clazz != null) {
                        plugin = (Plugin) clazz.newInstance();
                    }
                    classLoader.setPlugin(plugin);
                } catch (ClassNotFoundException e) {
                    throw new InstantiationException("Unable to instantiate plugin " + pluginClass + ": make sure class name exists, is public, and has an empty constructor that is public", e);
                } catch (InstantiationException e2) {
                    throw new InstantiationException("Unable to instantiate plugin " + pluginClass + ": make sure class name exists, is public, and has an empty constructor that is public", e2);
                } catch (IllegalAccessException e3) {
                    throw new InstantiationException("Unable to instantiate plugin " + pluginClass + ": make sure class name exists, is public, and has an empty constructor that is public", e3);
                }
            }
        }
        return plugin;
    }

    public static final class InstantiationException extends RuntimeException {
        private static final long serialVersionUID = 7119757964464278300L;

        public InstantiationException(String msg, Exception cause) {
            super(msg, cause);
        }
    }

    private void runOnUIThread(Runnable action) {
        if (action != null) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                action.run();
            } else {
                this.mUIHandler.post(action);
            }
        }
    }
}
