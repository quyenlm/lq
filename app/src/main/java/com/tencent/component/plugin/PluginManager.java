package com.tencent.component.plugin;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.net.Uri;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.text.TextUtils;
import com.tencent.component.annotation.PluginApi;
import com.tencent.component.plugin.IPluginManager;
import com.tencent.component.plugin.InstallPluginListener;
import com.tencent.component.plugin.PluginCommander;
import com.tencent.component.plugin.annotation.CorePluginApi;
import com.tencent.component.plugin.server.PluginConstant;
import com.tencent.component.plugin.server.PluginServerBroadcast;
import com.tencent.component.plugin.server.PluginService;
import com.tencent.component.utils.ApkUtil;
import com.tencent.component.utils.FileUtil;
import com.tencent.component.utils.UniqueLock;
import com.tencent.component.utils.log.LogUtil;
import com.tencent.component.utils.thread.ThreadPool;
import com.tencent.tp.a.h;
import java.io.File;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;

public class PluginManager {
    private static final String ACTION_PREFIX = "plugin_manager";
    public static final int FLAG_ENABLE = 2;
    public static final int FLAG_REGISTER = 1;
    private static final String TAG = "PluginManager";
    private static ConcurrentHashMap<String, PluginManager> sInstanceMap = new ConcurrentHashMap<>();
    /* access modifiers changed from: private */
    public Context mContext;
    /* access modifiers changed from: private */
    public volatile boolean mInitialed;
    private final HashSet<PluginListener> mListeners = new HashSet<>();
    private PluginMonitor mMonitor;
    /* access modifiers changed from: private */
    public final String mPlatformId;
    /* access modifiers changed from: private */
    public volatile boolean mPlatformInitialFinish;
    private final HashMap<String, PluginRecord> mPluginRecords = new HashMap<>();
    /* access modifiers changed from: private */
    public PluginServerBroadcast mPluginServerBroadcast = new PluginServerBroadcast.Stub() {
        public void onPlatformInitialStart() throws RemoteException {
            PluginManager.this.runOnUIThread(new Runnable() {
                public void run() {
                    PluginManager.this.notifyPlatformInitialStart();
                }
            });
        }

        public void onPlatformInitialFinish() throws RemoteException {
            PluginManager.this.runOnUIThread(new Runnable() {
                public void run() {
                    PluginManager.this.onHelloFinish();
                }
            });
        }

        public void onPluginInstalled(final String pluginId, final int oldVersion, final int version) throws RemoteException {
            if (!TextUtils.isEmpty(pluginId)) {
                PluginManager.this.removePluginRecord(pluginId);
                PluginManager.this.runOnUIThread(new Runnable() {
                    public void run() {
                        PluginManager.this.notifyPluginInstalled(pluginId, oldVersion, version);
                    }
                });
            }
        }

        public void onPluginUninstalled(final String pluginId) throws RemoteException {
            if (!TextUtils.isEmpty(pluginId)) {
                PluginManager.this.removePluginRecord(pluginId);
                PluginManager.this.runOnUIThread(new Runnable() {
                    public void run() {
                        PluginManager.this.notifyPluginUninstalled(pluginId);
                    }
                });
            }
        }

        public void onPendingInstallFinish(boolean success, boolean corePlugin, String extraInfo, String errorMsg) throws RemoteException {
            final boolean z = success;
            final boolean z2 = corePlugin;
            final String str = extraInfo;
            final String str2 = errorMsg;
            PluginManager.this.runOnUIThread(new Runnable() {
                public void run() {
                    PluginManager.this.notifyPendingInstallFinish(z, z2, str, str2);
                }
            });
        }

        public void onPluginStateChange(final String pluginId, final int changeFlags, final int statusFlags) throws RemoteException {
            if (!TextUtils.isEmpty(pluginId)) {
                if ((changeFlags & 1) != 0 && (statusFlags & 1) == 0) {
                    PluginManager.this.removePluginRecord(pluginId);
                }
                PluginManager.this.runOnUIThread(new Runnable() {
                    public void run() {
                        PluginManager.this.notifyPluginChanged(pluginId, changeFlags, statusFlags);
                    }
                });
            }
        }
    };
    private ThreadPool mPluginThreadPool;
    /* access modifiers changed from: private */
    public IPluginManager mService;
    private volatile ServiceConnection mServiceConnection;
    /* access modifiers changed from: private */
    public final Object mServiceLock = new Object();
    private Handler mUIHandler;
    private final UniqueLock<String> mUniqueRecordLock = new UniqueLock<>();
    public final PluginPlatformConfig pluginPlatformConfig;

    public interface GetPluginInfoCallback {
        @CorePluginApi(since = 400)
        void onGetPluginInfo(PluginInfo pluginInfo);
    }

    public interface GetPluginListCallback {
        @CorePluginApi(since = 400)
        void onGetPluginList(List<PluginInfo> list);
    }

    public interface LoadPluginInfoCallback {
        @CorePluginApi(since = 400)
        void onLoadPluginInfo(PluginInfo pluginInfo);
    }

    @CorePluginApi(since = 400)
    public interface PluginListener {
        @CorePluginApi(since = 400)
        void onPendingInstallFinish(boolean z, boolean z2, String str, String str2);

        @CorePluginApi(since = 400)
        void onPlatformInitialFinish();

        @CorePluginApi(since = 400)
        void onPlatformInitialStart();

        @CorePluginApi(since = 400)
        void onPluginChanged(String str, int i, int i2);

        @CorePluginApi(since = 400)
        void onPluginInstalled(String str, int i, int i2);

        @CorePluginApi(since = 400)
        void onPluginUninstall(String str);

        @CorePluginApi(since = 400)
        void onStartCheckPluginSurvive(List<PluginInfo> list);
    }

    @CorePluginApi(since = 400)
    @Deprecated
    public interface PluginMonitor {
        @CorePluginApi(since = 400)
        void onPluginChanged(String str, int i, int i2);

        @CorePluginApi(since = 400)
        void onPluginInstalled(String str, int i, int i2);

        @CorePluginApi(since = 400)
        void onPluginUninstall(String str);
    }

    private PluginManager(Context context, PluginPlatformConfig pluginPlatformConfig2) {
        this.mContext = context.getApplicationContext();
        this.pluginPlatformConfig = pluginPlatformConfig2;
        this.mPlatformId = pluginPlatformConfig2.platformId;
        this.mUIHandler = new Handler(Looper.getMainLooper());
        this.mPluginThreadPool = new ThreadPool("plugin-thread-pool", 1, 2);
        bindService();
    }

    private void bindService() {
        if (this.mServiceConnection == null) {
            synchronized (PluginManager.class) {
                if (this.mServiceConnection == null) {
                    this.mServiceConnection = new ServiceConnection() {
                        public void onServiceConnected(ComponentName name, IBinder service) {
                            LogUtil.i(PluginManager.TAG, "pluginService connected.");
                            synchronized (PluginManager.this.mServiceLock) {
                                IPluginManager unused = PluginManager.this.mService = IPluginManager.Stub.asInterface(service);
                                PluginManager.this.mServiceLock.notifyAll();
                            }
                            if (!PluginManager.this.mInitialed) {
                                boolean unused2 = PluginManager.this.mInitialed = true;
                                PluginManager.this.sayHello();
                            }
                        }

                        public void onServiceDisconnected(ComponentName name) {
                            boolean unused = PluginManager.this.mInitialed = false;
                            LogUtil.i(PluginManager.TAG, "pluginService disconnected.");
                        }
                    };
                }
            }
        }
        LogUtil.i(TAG, "try to bind service (platformId:" + this.mPlatformId + h.b);
        PluginService.bindPluginService(this.mContext, this.mServiceConnection, this.mPlatformId);
    }

    /* access modifiers changed from: private */
    public void stopService() {
        this.mContext.unbindService(this.mServiceConnection);
        this.mContext.stopService(new Intent(this.mContext, PluginService.class));
        synchronized (this.mServiceLock) {
            this.mService = null;
        }
    }

    /* access modifiers changed from: private */
    public IPluginManager getService() {
        if (!isServiceManagerAlive()) {
            int count = 0;
            if (this.mService != null) {
                bindService();
            }
            while (!isServiceManagerAlive() && (count = count + 1) <= 10) {
                try {
                    synchronized (this.mServiceLock) {
                        try {
                            this.mServiceLock.wait(300);
                        } catch (InterruptedException e) {
                        }
                    }
                } catch (Exception e2) {
                    LogUtil.e(TAG, "startService(Reason.Restart) exception  :" + e2.getMessage());
                }
            }
        }
        return this.mService;
    }

    private boolean isServiceManagerAlive() {
        return this.mService != null && this.mService.asBinder().isBinderAlive() && this.mService.asBinder().pingBinder();
    }

    @CorePluginApi(since = 400)
    public void init() {
    }

    /* access modifiers changed from: private */
    public void sayHello() {
        async(new Code() {
            public void code() throws RemoteException {
                IPluginManager pm = PluginManager.this.getService();
                if (pm != null) {
                    try {
                        pm.hello(PluginManager.this.pluginPlatformConfig, PluginManager.this.mPluginServerBroadcast);
                        PluginPlatform.initialize(PluginManager.this.mContext);
                    } catch (Throwable e) {
                        boolean unused = PluginManager.this.mInitialed = false;
                        LogUtil.e(PluginManager.TAG, "say hello failed by exception (platformId:" + PluginManager.this.mPlatformId + h.b, e);
                    }
                } else {
                    boolean unused2 = PluginManager.this.mInitialed = false;
                    LogUtil.i(PluginManager.TAG, "say hello failed as pluginManager binder is null (platformId:" + PluginManager.this.mPlatformId + ").");
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void onHelloFinish() {
        async(new Code() {
            public void code() throws RemoteException {
                IPluginManager pm = PluginManager.this.getService();
                if (pm != null) {
                    List<PluginInfo> pluginInfos = null;
                    try {
                        pluginInfos = pm.getAllPluginInfos(PluginManager.this.mPlatformId);
                    } catch (RemoteException e) {
                        LogUtil.e(PluginManager.TAG, e.getMessage(), e);
                    }
                    if (PluginManager.this.pluginPlatformConfig.enbaleCorePlugin) {
                        PluginManager.this.launchAllCorePlugins(pluginInfos);
                    }
                    PluginManager.this.launchSurviveDetector(pluginInfos);
                    PluginManager.this.notifyBootComplete(pluginInfos);
                    PluginManager.this.launchAutoStartPlugins(pluginInfos);
                    boolean unused = PluginManager.this.mInitialed = true;
                    boolean unused2 = PluginManager.this.mPlatformInitialFinish = true;
                    PluginManager.this.notifyPlatformInitialFinish();
                    LogUtil.i(PluginManager.TAG, "say hello finished (platformId:" + PluginManager.this.mPlatformId + ").");
                    return;
                }
                boolean unused3 = PluginManager.this.mInitialed = false;
                LogUtil.i(PluginManager.TAG, "say hello failed as pluginManager binder is null (platformId:" + PluginManager.this.mPlatformId + ").");
            }
        });
    }

    /* access modifiers changed from: private */
    public void launchAllCorePlugins(List<PluginInfo> pluginInfos) {
        if (pluginInfos != null) {
            ArrayList<PluginInfo> corePluginList = new ArrayList<>();
            for (PluginInfo pluginInfo : pluginInfos) {
                if (pluginInfo.corePlugin) {
                    corePluginList.add(pluginInfo);
                }
            }
            if (corePluginList.size() > 0) {
                final CountDownLatch latch = new CountDownLatch(corePluginList.size());
                Iterator<PluginInfo> it = corePluginList.iterator();
                while (it.hasNext()) {
                    final PluginInfo pluginInfo2 = it.next();
                    LogUtil.i(TAG, "start launch core plugin:" + pluginInfo2.pluginId);
                    runOnUIThread(new Runnable() {
                        public void run() {
                            String str;
                            String str2;
                            try {
                                if (PluginManager.this.getPlugin(pluginInfo2) == null) {
                                    LogUtil.e(PluginManager.TAG, "auto start core plugin:" + pluginInfo2 + " failed.");
                                }
                            } catch (Exception e) {
                                LogUtil.e(PluginManager.TAG, e.getMessage(), e);
                            } finally {
                                latch.countDown();
                                String str3 = PluginManager.TAG;
                                str = "start core plugin:";
                                StringBuilder append = new StringBuilder().append(str).append(pluginInfo2.pluginId);
                                str2 = " countDown.";
                                LogUtil.i(str3, append.append(str2).toString());
                            }
                        }
                    });
                }
                try {
                    LogUtil.i(TAG, "start to wait launch core plugin result.");
                    latch.await();
                    LogUtil.i(TAG, "finish to wait launch core plugin.");
                } catch (InterruptedException e) {
                    LogUtil.e(TAG, e.getMessage(), e);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void launchSurviveDetector(List<PluginInfo> pluginInfos) {
        if (pluginInfos != null) {
            ArrayList<PluginInfo> filteredPluginList = new ArrayList<>();
            ArrayList<PluginInfo> otherPlugins = new ArrayList<>();
            for (PluginInfo pluginInfo : pluginInfos) {
                if (pluginInfo == null || !pluginInfo.enabled || TextUtils.isEmpty(pluginInfo.surviveDetector)) {
                    otherPlugins.add(pluginInfo);
                } else {
                    filteredPluginList.add(pluginInfo);
                }
            }
            notifyStartCheckPluginSurvive(otherPlugins);
            if (filteredPluginList.size() > 0) {
                final CountDownLatch latch = new CountDownLatch(filteredPluginList.size());
                Iterator<PluginInfo> it = filteredPluginList.iterator();
                while (it.hasNext()) {
                    final PluginInfo pluginInfo2 = it.next();
                    runOnUIThread(new Runnable() {
                        public void run() {
                            String str;
                            String str2;
                            PluginSurviveDetector detector;
                            try {
                                LogUtil.i(PluginManager.TAG, "start to check plugin:" + pluginInfo2.pluginId + " is surviveable");
                                Plugin plugin = PluginManager.this.getPlugin(pluginInfo2);
                                if (!(plugin == null || (detector = PluginSurviveDetector.instantiate(plugin.getContext(), pluginInfo2)) == null)) {
                                    if (!detector.isSurvivable()) {
                                        LogUtil.i(PluginManager.TAG, "pluginId:" + pluginInfo2.pluginId + " not surviveable.");
                                        PluginManager.this.markPluginSurviveable(pluginInfo2.pluginId, false);
                                        pluginInfo2.bootCompleteReceiver = null;
                                        pluginInfo2.extraInfo.autoLoad = false;
                                        PluginManager.this.removePluginRecord(pluginInfo2.pluginId);
                                    } else {
                                        LogUtil.i(PluginManager.TAG, "pluginId:" + pluginInfo2.pluginId + " can survive.");
                                    }
                                }
                            } catch (Exception e) {
                                LogUtil.e(PluginManager.TAG, e.getMessage(), e);
                            } finally {
                                latch.countDown();
                                String str3 = PluginManager.TAG;
                                str = "plugin:";
                                StringBuilder append = new StringBuilder().append(str).append(pluginInfo2.pluginId);
                                str2 = " check survive countDown.";
                                LogUtil.i(str3, append.append(str2).toString());
                            }
                        }
                    });
                }
                try {
                    LogUtil.i(TAG, "start to wait check plugin survive result.");
                    latch.await();
                    LogUtil.i(TAG, "finish to wait check plugin survive.");
                } catch (InterruptedException e) {
                    LogUtil.e(TAG, e.getMessage(), e);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void notifyBootComplete(List<PluginInfo> pluginInfos) {
        if (pluginInfos != null) {
            for (PluginInfo pluginInfo : pluginInfos) {
                if (pluginInfo != null && pluginInfo.enabled && !TextUtils.isEmpty(pluginInfo.bootCompleteReceiver)) {
                    notifyBootCompleteInner(pluginInfo);
                }
            }
        }
    }

    private void notifyBootCompleteInner(final PluginInfo pluginInfo) {
        if (pluginInfo != null && !TextUtils.isEmpty(pluginInfo.bootCompleteReceiver)) {
            runOnUIThread(new Runnable() {
                public void run() {
                    Plugin plugin = PluginManager.this.getPlugin(pluginInfo);
                    if (plugin != null) {
                        BootCompleteReceiver receiver = BootCompleteReceiver.instantiate(plugin.getContext(), pluginInfo);
                        if (receiver != null) {
                            LogUtil.i(PluginManager.TAG, "notify plugin:" + pluginInfo.pluginId + " boot complete.");
                            receiver.onBootComplete(plugin.getContext());
                            return;
                        }
                        LogUtil.i(PluginManager.TAG, "notify plugin:" + pluginInfo.pluginId + " boot complete failed.");
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void launchAutoStartPlugins(List<PluginInfo> pluginInfos) {
        if (pluginInfos != null) {
            for (PluginInfo pluginInfo : pluginInfos) {
                if (pluginInfo != null && pluginInfo.enabled && pluginInfo.extraInfo.autoLoad) {
                    startPluginInner(pluginInfo, (Intent) null);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public boolean registerPlugin(String id, PluginInfo pluginInfo) {
        IPluginManager pm;
        if (!PluginHelper.checkPluginId(id) || !PluginHelper.checkPluginInfo(pluginInfo) || (pm = getService()) == null) {
            return false;
        }
        try {
            return pm.registerPlugin(this.mPlatformId, id, pluginInfo);
        } catch (RemoteException e) {
            LogUtil.e(TAG, "registerPlugin", e);
            return false;
        }
    }

    /* access modifiers changed from: package-private */
    public void markPluginSurviveable(String id, boolean surviveable) {
        IPluginManager pm;
        if (PluginHelper.checkPluginId(id) && (pm = getService()) != null) {
            try {
                pm.markPluginSurviveable(this.mPlatformId, id, surviveable);
            } catch (RemoteException e) {
                LogUtil.e(TAG, "markPluginSurviveable", e);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public boolean unregisterPlugin(String id) {
        IPluginManager pm;
        if (!PluginHelper.checkPluginId(id) || (pm = getService()) == null) {
            return false;
        }
        try {
            return pm.unregisterPlugin(this.mPlatformId, id);
        } catch (RemoteException e) {
            LogUtil.e(TAG, "unregisterPlugin", e);
            return false;
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isPluginRegistered(String id) {
        IPluginManager pm = getService();
        if (pm != null) {
            try {
                return pm.isPluginRegistered(this.mPlatformId, id);
            } catch (RemoteException e) {
                LogUtil.e(TAG, "isPluginRegistered", e);
            }
        }
        return false;
    }

    @CorePluginApi(since = 400)
    public void enablePlugin(final String id) {
        async(new Code() {
            public void code() throws RemoteException {
                IPluginManager pm = PluginManager.this.getService();
                if (pm != null) {
                    pm.enablePlugin(PluginManager.this.mPlatformId, id);
                }
            }
        });
    }

    @CorePluginApi(since = 400)
    public void disablePlugin(final String id) {
        async(new Code() {
            public void code() throws RemoteException {
                IPluginManager pm = PluginManager.this.getService();
                if (pm != null) {
                    pm.disablePlugin(PluginManager.this.mPlatformId, id);
                }
            }
        });
    }

    @CorePluginApi(since = 400)
    public void addPendingInstallPlugin(String pluginLocation) {
        addPendingInstallPlugin(pluginLocation, false, "");
    }

    @CorePluginApi(since = 400)
    public void addPendingInstallPlugin(final String pluginLocation, final boolean corePlugin, final String extraInfo) {
        if (!TextUtils.isEmpty(pluginLocation)) {
            async(new Code() {
                public void code() throws RemoteException {
                    File downLoadPluginFile = new File(pluginLocation);
                    if (downLoadPluginFile.isFile()) {
                        File destFile = new File(PluginManager.this.getInstallPendingDir(PluginManager.this.mContext), downLoadPluginFile.getName());
                        FileUtil.copyFiles(downLoadPluginFile, new File(PluginManager.this.getInstallPendingDir(PluginManager.this.mContext), downLoadPluginFile.getName()));
                        PluginConstant.setPendingInstallInfo(PluginManager.this.mContext, destFile.getAbsolutePath(), corePlugin, extraInfo);
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public File getInstallPendingDir(Context context) {
        return context.getDir("plugins_pending_" + this.mPlatformId, 0);
    }

    @CorePluginApi(since = 400)
    public void install(final String pluginLocation, final InstallPluginListener listener) {
        async(new Code() {
            public void code() throws RemoteException {
                final IPluginManager pm = PluginManager.this.getService();
                if (pm != null) {
                    pm.install(PluginManager.this.mPlatformId, pluginLocation, new InstallPluginListener.Stub() {
                        public void onInstallSuccess() throws RemoteException {
                            PluginManager.this.launchAllCorePlugins(pm.getAllPluginInfos(PluginManager.this.mPlatformId));
                            if (listener != null) {
                                listener.onInstallSuccess();
                            }
                        }

                        public void onInstallFailed(String failMsg) throws RemoteException {
                            if (listener != null) {
                                listener.onInstallFailed(failMsg);
                            }
                        }
                    });
                }
            }
        });
    }

    @CorePluginApi(since = 400)
    public void uninstall(final PluginInfo pluginInfo, final UninstallPluginListener listener) {
        if (pluginInfo != null && !TextUtils.isEmpty(pluginInfo.pluginId)) {
            async(new Code() {
                public void code() throws RemoteException {
                    IPluginManager pm = PluginManager.this.getService();
                    if (pm != null) {
                        pm.uninstall(PluginManager.this.mPlatformId, pluginInfo, listener);
                    }
                }
            });
        } else if (listener != null) {
            try {
                listener.onUninstallFailed("pluginInfo/pluginId is empty");
            } catch (RemoteException e) {
                LogUtil.e(TAG, e.getMessage(), e);
            }
        }
    }

    @CorePluginApi(since = 400)
    public void uninstall(String pluginId, final UninstallPluginListener listener) {
        if (!TextUtils.isEmpty(pluginId)) {
            getPluginInfo(pluginId, new GetPluginInfoCallback() {
                public void onGetPluginInfo(PluginInfo pluginInfo) {
                    PluginManager.this.uninstall(pluginInfo, listener);
                }
            });
        } else if (listener != null) {
            try {
                listener.onUninstallFailed("pluginId is empty");
            } catch (RemoteException e) {
                LogUtil.e(TAG, e.getMessage(), e);
            }
        }
    }

    @CorePluginApi(since = 400)
    public void loadPluginInfo(final String id, final LoadPluginInfoCallback callback) {
        if (callback != null && !TextUtils.isEmpty(id)) {
            async(new Code() {
                public void code() throws RemoteException {
                    IPluginManager pm = PluginManager.this.getService();
                    if (pm != null) {
                        callback.onLoadPluginInfo(pm.getPluginInfo(PluginManager.this.mPlatformId, id));
                    }
                }
            });
        }
    }

    /* access modifiers changed from: package-private */
    public PluginInfo loadPluginInfoSync(String id) {
        IPluginManager pm = getService();
        if (pm != null) {
            try {
                return pm.loadPluginInfo(this.mPlatformId, id);
            } catch (RemoteException e) {
                LogUtil.e(TAG, "getPluginInfo", e);
            }
        }
        return null;
    }

    @CorePluginApi(since = 400)
    public void getPluginInfo(final String id, final GetPluginInfoCallback getPluginInfoCallback) {
        if (getPluginInfoCallback != null && !TextUtils.isEmpty(id)) {
            async(new Code() {
                public void code() throws RemoteException {
                    IPluginManager pm = PluginManager.this.getService();
                    if (pm != null) {
                        getPluginInfoCallback.onGetPluginInfo(pm.getPluginInfo(PluginManager.this.mPlatformId, id));
                    }
                }
            });
        }
    }

    /* access modifiers changed from: package-private */
    public PluginInfo getPluginInfoSync(String id) {
        IPluginManager pm = getService();
        if (pm != null) {
            try {
                return pm.getPluginInfo(this.mPlatformId, id);
            } catch (RemoteException e) {
                LogUtil.e(TAG, "getPluginInfo", e);
            }
        }
        return null;
    }

    @CorePluginApi(since = 400)
    public void getPluginList(GetPluginListCallback callback) {
        getPluginList(callback, true);
    }

    @CorePluginApi(since = 400)
    public void getPluginList(final GetPluginListCallback callback, final boolean execludeCorePlugin) {
        if (callback != null) {
            async(new Code() {
                public void code() throws RemoteException {
                    IPluginManager pm = PluginManager.this.getService();
                    if (pm != null) {
                        List<PluginInfo> pluginInfos = pm.getAllPluginInfos(PluginManager.this.mPlatformId);
                        if (execludeCorePlugin && pluginInfos != null && pluginInfos.size() > 0) {
                            List<PluginInfo> nonCorePluginList = new ArrayList<>();
                            for (PluginInfo pluginInfo : pluginInfos) {
                                if (!pluginInfo.corePlugin) {
                                    nonCorePluginList.add(pluginInfo);
                                }
                            }
                            pluginInfos = nonCorePluginList;
                        }
                        callback.onGetPluginList(pluginInfos);
                    }
                }
            });
        }
    }

    @CorePluginApi(since = 400)
    public List<Plugin> getActiviedPlugins() {
        ArrayList<Plugin> pluginList = new ArrayList<>();
        synchronized (this.mPluginRecords) {
            for (Map.Entry<String, PluginRecord> entry : this.mPluginRecords.entrySet()) {
                PluginRecord pluginRecord = entry.getValue();
                if (!(pluginRecord == null || pluginRecord.plugin == null)) {
                    pluginList.add(pluginRecord.plugin);
                }
            }
        }
        return pluginList;
    }

    @PluginApi(since = 400)
    public int getCorePluginVersionCode(String pluginId) {
        PluginInfo pluginInfo;
        int versionCode = 0;
        if (!TextUtils.isEmpty(pluginId)) {
            synchronized (this.mPluginRecords) {
                PluginRecord pluginRecord = this.mPluginRecords.get(pluginId);
                if (!(pluginRecord == null || pluginRecord.plugin == null || (pluginInfo = pluginRecord.plugin.getPluginInfo()) == null)) {
                    versionCode = pluginInfo.version;
                }
            }
        }
        return versionCode;
    }

    @PluginApi(since = 400)
    public String getCorePluginVersionName(String pluginId) {
        PluginInfo pluginInfo;
        String versionName = "";
        if (!TextUtils.isEmpty(pluginId)) {
            synchronized (this.mPluginRecords) {
                PluginRecord pluginRecord = this.mPluginRecords.get(pluginId);
                if (!(pluginRecord == null || pluginRecord.plugin == null || (pluginInfo = pluginRecord.plugin.getPluginInfo()) == null)) {
                    versionName = pluginInfo.versionName;
                }
            }
        }
        return versionName;
    }

    public void setPluginHandler(final PluginManageHandler handler) {
        async(new Code() {
            public void code() throws RemoteException {
                IPluginManager pm = PluginManager.this.getService();
                if (pm != null) {
                    pm.setPluginHandler(PluginManager.this.mPlatformId, handler);
                }
            }
        });
    }

    @CorePluginApi(since = 400)
    public void stopPlugin(String id) {
        if (!TextUtils.isEmpty(id) && isServiceManagerAlive()) {
            IPluginManager pm = getService();
            if (pm != null) {
                try {
                    Plugin plugin = getPlugin(pm.getPluginInfo(this.mPlatformId, id), false);
                    if (plugin != null) {
                        plugin.stop();
                    } else {
                        LogUtil.w(TAG, "fail to stop plugin:" + id + " (no record)");
                    }
                } catch (RemoteException e) {
                    LogUtil.w(TAG, "fail to stop plugin:" + id + "(remote exception)", e);
                }
            } else {
                LogUtil.w(TAG, "cannot get remote service, stop plugin:" + id + " failed!");
            }
        }
    }

    @CorePluginApi(since = 400)
    public void stopAllPlugin() {
        if (isServiceManagerAlive()) {
            IPluginManager pm = getService();
            if (pm != null) {
                try {
                    List<PluginInfo> pluginInfos = pm.getAllPluginInfos(this.mPlatformId);
                    if (pluginInfos != null) {
                        for (PluginInfo pluginInfo : pluginInfos) {
                            Plugin plugin = getPlugin(pluginInfo, false);
                            if (plugin != null) {
                                plugin.stop();
                            } else {
                                LogUtil.w(TAG, "fail to stop plugin:" + pluginInfo + " (no record)");
                            }
                        }
                    }
                } catch (RemoteException e) {
                    LogUtil.w(TAG, "fail to stop all plugin (remote exception)", e);
                }
            } else {
                LogUtil.w(TAG, "cannot get remote service, stop all plugin failed!");
            }
        }
    }

    @CorePluginApi(since = 400)
    public void startPlugin(String id) {
        startPlugin(id, (Intent) null);
    }

    @CorePluginApi(since = 400)
    public void startPlugin(String id, final Intent args) {
        loadPluginInfo(id, new LoadPluginInfoCallback() {
            public void onLoadPluginInfo(PluginInfo pluginInfo) {
                if (pluginInfo != null) {
                    PluginManager.this.startPluginInner(pluginInfo, args);
                }
            }
        });
    }

    @CorePluginApi(since = 400)
    public void writeCommandToPlugin(String id, final String cmd, final Object args) {
        if (TextUtils.isEmpty(id) || TextUtils.isEmpty(cmd) || !isServiceManagerAlive()) {
            LogUtil.w(TAG, "writeCommandToPlugin failed [illegal params --> id:" + id + " |cmd:" + cmd + " |args:" + args);
            return;
        }
        IPluginManager pm = getService();
        if (pm != null) {
            try {
                Plugin plugin = getPlugin(pm.getPluginInfo(this.mPlatformId, id), false);
                if (plugin != null) {
                    PluginCommander pluginDAO = plugin.getPluginCommander();
                    if (pluginDAO != null) {
                        pluginDAO.write(cmd, args);
                    } else {
                        LogUtil.w(TAG, "fail to write data to plugin:" + id + " (pluginDAO is null)");
                    }
                } else {
                    loadPluginInfo(id, new LoadPluginInfoCallback() {
                        public void onLoadPluginInfo(final PluginInfo pluginInfo) {
                            PluginManager.this.runOnUIThread(new Runnable() {
                                public void run() {
                                    if (pluginInfo != null) {
                                        Plugin plugin = PluginManager.this.getPlugin(pluginInfo);
                                        if (plugin != null) {
                                            PluginCommander pluginDAO = plugin.getPluginCommander();
                                            if (pluginDAO != null) {
                                                pluginDAO.write(cmd, args);
                                            } else {
                                                LogUtil.w(PluginManager.TAG, "fail to put data to plugin:" + pluginInfo.pluginId + ", pluginDAO is null.");
                                            }
                                        } else {
                                            LogUtil.w(PluginManager.TAG, "fail to put data to plugin:" + pluginInfo.pluginId + ", plugin is null.");
                                        }
                                    } else {
                                        LogUtil.w(PluginManager.TAG, "fail to put data to plugin, pluginInfo is null.");
                                    }
                                }
                            });
                        }
                    });
                }
            } catch (Throwable e) {
                LogUtil.w(TAG, "fail to write data to plugin:" + id + "(remote exception)", e);
            }
        } else {
            LogUtil.w(TAG, "cannot get remote service, write data to plugin:" + id + " failed!");
        }
    }

    @CorePluginApi(since = 400)
    public Object readDataFromPlugin(String id, String cmd, Object args, Object defaultValue, PluginCommander.ReadDataCallback callback) {
        if (TextUtils.isEmpty(id) || TextUtils.isEmpty(cmd) || !isServiceManagerAlive()) {
            LogUtil.w(TAG, "readDataFromPlugin failed [illegal params --> id:" + id + " |cmd:" + cmd + " |args:" + args);
            return defaultValue;
        }
        IPluginManager pm = getService();
        if (pm != null) {
            try {
                Plugin plugin = getPlugin(pm.getPluginInfo(this.mPlatformId, id), false);
                if (plugin != null) {
                    PluginCommander pluginDAO = plugin.getPluginCommander();
                    if (pluginDAO != null) {
                        return pluginDAO.read(cmd, args, defaultValue, callback);
                    }
                    LogUtil.w(TAG, "fail to get data from plugin:" + id + " (pluginDAO is null)");
                    return defaultValue;
                } else if (callback != null) {
                    final String str = cmd;
                    final Object obj = args;
                    final Object obj2 = defaultValue;
                    final PluginCommander.ReadDataCallback readDataCallback = callback;
                    final String str2 = id;
                    loadPluginInfo(id, new LoadPluginInfoCallback() {
                        public void onLoadPluginInfo(final PluginInfo pluginInfo) {
                            PluginManager.this.runOnUIThread(new Runnable() {
                                public void run() {
                                    if (pluginInfo != null) {
                                        Plugin plugin = PluginManager.this.getPlugin(pluginInfo, false);
                                        if (plugin != null) {
                                            PluginCommander pluginDAO = plugin.getPluginCommander();
                                            if (pluginDAO != null) {
                                                Object result = pluginDAO.read(str, obj, obj2, readDataCallback);
                                                if (result != null) {
                                                    readDataCallback.onReadDataFinish(str, result);
                                                    return;
                                                }
                                                return;
                                            }
                                            LogUtil.w(PluginManager.TAG, "fail to get data from plugin:" + str2 + " (pluginDAO is null,onLoadPluginInfo)");
                                            return;
                                        }
                                        LogUtil.w(PluginManager.TAG, "fail to get data from plugin:" + str2 + " (plugin is null,onLoadPluginInfo)");
                                        return;
                                    }
                                    LogUtil.w(PluginManager.TAG, "fail to get data from plugin:" + str2 + " (pluginInfo is null,onLoadPluginInfo)");
                                }
                            });
                        }
                    });
                    return defaultValue;
                } else {
                    LogUtil.w(TAG, "fail to get data from plugin:" + id + " (plugin is null)");
                    return defaultValue;
                }
            } catch (RemoteException e) {
                LogUtil.w(TAG, "fail to get data from plugin:" + id + "(remote exception)", e);
                return defaultValue;
            }
        } else {
            LogUtil.w(TAG, "cannot get remote service, get data from plugin:" + id + " failed!");
            return defaultValue;
        }
    }

    @CorePluginApi(since = 400)
    public void transparentLifeCycle(int type, Object data) {
        if (isServiceManagerAlive()) {
            IPluginManager pm = getService();
            if (pm != null) {
                try {
                    List<PluginInfo> pluginInfos = pm.getAllPluginInfos(this.mPlatformId);
                    if (pluginInfos != null) {
                        for (PluginInfo pluginInfo : pluginInfos) {
                            Plugin plugin = getPlugin(pluginInfo, false);
                            if (plugin != null) {
                                plugin.onBusinessLifeCycle(type, data);
                            } else {
                                LogUtil.w(TAG, "fail to transparent lifecycle :" + pluginInfo + " (no record)");
                            }
                        }
                    }
                } catch (RemoteException e) {
                    LogUtil.w(TAG, "fail to transparent lifecycle (remote exception)", e);
                }
            } else {
                LogUtil.w(TAG, "cannot get remote service, fail to transparent lifecycle ");
            }
        }
    }

    @CorePluginApi(since = 400)
    public void movePluginToBack(String id) {
        if (!TextUtils.isEmpty(id) && isServiceManagerAlive()) {
            IPluginManager pm = getService();
            if (pm != null) {
                try {
                    Plugin plugin = getPlugin(pm.getPluginInfo(this.mPlatformId, id), false);
                    if (plugin != null) {
                        plugin.enterBackground();
                    } else {
                        LogUtil.w(TAG, "fail to move plugin to background :" + id + " (no record)");
                    }
                } catch (RemoteException e) {
                    LogUtil.w(TAG, "fail to move plugin to background:" + id + "(remote exception)", e);
                }
            } else {
                LogUtil.w(TAG, "cannot get remote service, fail to move plugin to background:" + id);
            }
        }
    }

    @CorePluginApi(since = 400)
    public void moveAllPluginToBack() {
        if (isServiceManagerAlive()) {
            IPluginManager pm = getService();
            if (pm != null) {
                try {
                    List<PluginInfo> pluginInfos = pm.getAllPluginInfos(this.mPlatformId);
                    if (pluginInfos != null) {
                        for (PluginInfo pluginInfo : pluginInfos) {
                            Plugin plugin = getPlugin(pluginInfo, false);
                            if (plugin != null) {
                                plugin.enterBackground();
                            } else {
                                LogUtil.w(TAG, "fail to move plugin:" + pluginInfo + " to background(no record)");
                            }
                        }
                    }
                } catch (RemoteException e) {
                    LogUtil.w(TAG, "fail to move all plugin to background (remote exception)", e);
                }
            } else {
                LogUtil.w(TAG, "cannot get remote service, move all plugin to background failed!");
            }
        }
    }

    /* access modifiers changed from: private */
    public void startPluginInner(final PluginInfo pluginInfo, final Intent args) {
        if (pluginInfo != null) {
            runOnUIThread(new Runnable() {
                public void run() {
                    Plugin plugin = PluginManager.this.getPlugin(pluginInfo);
                    if (plugin != null) {
                        plugin.start(plugin.getContext(), args);
                    } else {
                        LogUtil.w(PluginManager.TAG, "fail to start plugin:" + pluginInfo.pluginId);
                    }
                }
            });
        } else {
            LogUtil.w(TAG, "fail to start plugin (pluginInfo is null)");
        }
    }

    @CorePluginApi(since = 400)
    public Plugin getPlugin(PluginInfo pluginInfo) {
        return getPlugin(pluginInfo, true);
    }

    @CorePluginApi(since = 400)
    public Plugin getPlugin(PluginInfo pluginInfo, boolean initPlugin) {
        if (pluginInfo == null) {
            return null;
        }
        PluginRecord record = getPluginRecord(pluginInfo.pluginId, true);
        Plugin plugin = record.plugin;
        if (plugin != null) {
            return plugin;
        }
        boolean create = false;
        Lock lock = this.mUniqueRecordLock.obtain(pluginInfo.pluginId);
        lock.lock();
        try {
            if (record.plugin == null && initPlugin) {
                record.plugin = generatePlugin(pluginInfo);
                if (record.plugin != null) {
                    record.plugin.attach(this.mContext, this, PluginHelper.getInstance(this.mPlatformId, this), pluginInfo);
                }
                create = record.plugin != null;
            }
            Plugin plugin2 = record.plugin;
            if (!create || plugin2 == null) {
                return plugin2;
            }
            final Plugin tempPlugin = plugin2;
            runOnUIThread(new Runnable() {
                public void run() {
                    tempPlugin.performCreate();
                }
            });
            return plugin2;
        } finally {
            lock.unlock();
        }
    }

    @CorePluginApi(since = 400)
    public PluginHelper getPluginHelper() {
        return PluginHelper.getInstance(this.mPlatformId, this);
    }

    /* access modifiers changed from: private */
    public void removePluginRecord(String id) {
        PluginRecord pluginRecord;
        synchronized (this.mPluginRecords) {
            pluginRecord = this.mPluginRecords.remove(id);
        }
        if (pluginRecord == null || pluginRecord.plugin == null) {
            LogUtil.i(TAG, "there's no pluginrecord :" + id + " to remove");
            return;
        }
        PluginInfo pluginInfo = pluginRecord.plugin.getPluginInfo();
        LogUtil.i(TAG, "remove pluginrecord :" + id);
        if (pluginInfo != null) {
            PluginClassLoader.removeClassLoader(pluginInfo);
        }
    }

    private Plugin generatePlugin(PluginInfo pluginInfo) {
        if (!PluginHelper.checkPluginInfo(pluginInfo)) {
            return null;
        }
        if (pluginInfo.isInternal()) {
            try {
                return Plugin.instantiate(this.mContext, pluginInfo);
            } catch (Throwable e) {
                LogUtil.e(TAG, "fail to generate plugin for " + pluginInfo, e);
                return null;
            }
        } else {
            Lock fileLock = PluginFileLock.readLock(pluginInfo.installPath);
            fileLock.lock();
            try {
                return Plugin.instantiate(this.mContext, pluginInfo);
            } catch (Throwable e2) {
                LogUtil.e(TAG, "fail to generate plugin for " + pluginInfo, e2);
                return null;
            } finally {
                fileLock.unlock();
            }
        }
    }

    @CorePluginApi(since = 400)
    public void addPluginListener(PluginListener listener) {
        if (listener != null) {
            synchronized (this.mListeners) {
                this.mListeners.add(listener);
            }
        }
    }

    @CorePluginApi(since = 400)
    public void removePluginListener(PluginListener listener) {
        if (listener != null) {
            synchronized (this.mListeners) {
                this.mListeners.remove(listener);
            }
        }
    }

    @CorePluginApi(since = 400)
    @Deprecated
    public void setPluginMonitor(PluginMonitor monitor) {
        this.mMonitor = monitor;
    }

    /* access modifiers changed from: private */
    public void notifyPlatformInitialStart() {
        PluginListener[] listenerArray = collectionPluginListeners();
        if (listenerArray != null) {
            for (PluginListener listener : listenerArray) {
                if (listener != null) {
                    listener.onPlatformInitialStart();
                }
            }
        }
    }

    private void notifyStartCheckPluginSurvive(List<PluginInfo> pluginInfos) {
        PluginListener[] listenerArray = collectionPluginListeners();
        if (listenerArray != null) {
            for (PluginListener listener : listenerArray) {
                if (listener != null) {
                    listener.onStartCheckPluginSurvive(pluginInfos);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void notifyPlatformInitialFinish() {
        PluginListener[] listenerArray = collectionPluginListeners();
        if (listenerArray != null) {
            for (PluginListener listener : listenerArray) {
                if (listener != null) {
                    listener.onPlatformInitialFinish();
                }
            }
        }
    }

    private PluginListener[] collectionPluginListeners() {
        PluginListener[] listenerArray;
        synchronized (this.mListeners) {
            PluginListener[] listeners = this.mListeners.isEmpty() ? null : new PluginListener[this.mListeners.size()];
            if (listeners != null) {
                listeners = (PluginListener[]) this.mListeners.toArray(listeners);
            }
            listenerArray = listeners;
        }
        return listenerArray;
    }

    /* access modifiers changed from: private */
    public void notifyPendingInstallFinish(boolean success, boolean corePlugin, String extraInfo, String errorMsg) {
        PluginListener[] listenerArray = collectionPluginListeners();
        if (listenerArray != null) {
            for (PluginListener listener : listenerArray) {
                if (listener != null) {
                    try {
                        listener.onPendingInstallFinish(success, corePlugin, extraInfo, errorMsg);
                    } catch (Throwable e) {
                        LogUtil.e(TAG, e.getMessage(), e);
                    }
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void notifyPluginChanged(String id, int changeFlags, int statusFlags) {
        PluginMonitor monitor = this.mMonitor;
        if (monitor != null) {
            monitor.onPluginChanged(id, changeFlags, statusFlags);
        }
        PluginListener[] listenerArray = collectionPluginListeners();
        if (listenerArray != null) {
            for (PluginListener listener : listenerArray) {
                if (listener != null) {
                    try {
                        listener.onPluginChanged(id, changeFlags, statusFlags);
                    } catch (Throwable e) {
                        LogUtil.e(TAG, e.getMessage(), e);
                    }
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void notifyPluginInstalled(String id, int oldVersion, int version) {
        PluginMonitor monitor = this.mMonitor;
        if (monitor != null) {
            monitor.onPluginInstalled(id, oldVersion, version);
        }
        PluginListener[] listenerArray = collectionPluginListeners();
        if (listenerArray != null) {
            for (PluginListener listener : listenerArray) {
                if (listener != null) {
                    try {
                        listener.onPluginInstalled(id, oldVersion, version);
                    } catch (Throwable e) {
                        LogUtil.e(TAG, e.getMessage(), e);
                    }
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void notifyPluginUninstalled(String id) {
        PluginMonitor monitor = this.mMonitor;
        if (monitor != null) {
            monitor.onPluginUninstall(id);
        }
        PluginListener[] listenerArray = collectionPluginListeners();
        if (listenerArray != null) {
            for (PluginListener listener : listenerArray) {
                if (listener != null) {
                    try {
                        listener.onPluginUninstall(id);
                    } catch (Throwable e) {
                        LogUtil.e(TAG, e.getMessage(), e);
                    }
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public Intent handlePluginUri(String id, Uri uri) {
        IPluginManager pm = getService();
        if (pm != null) {
            try {
                return pm.handlePluginUri(this.mPlatformId, id, uri);
            } catch (RemoteException e) {
                LogUtil.e(TAG, "handlePluginUri", e);
            }
        }
        return null;
    }

    private static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /* access modifiers changed from: package-private */
    public Resources getPluginResources(PluginInfo pluginInfo) {
        if (pluginInfo == null) {
            return null;
        }
        PluginRecord record = getPluginRecord(pluginInfo.pluginId, true);
        ResourcesEntry entry = record == null ? null : record.resources;
        Resources resources = entry == null ? null : (Resources) entry.get();
        if (resources != null) {
            return resources;
        }
        Lock lock = this.mUniqueRecordLock.obtain(pluginInfo.pluginId);
        lock.lock();
        ResourcesEntry entry2 = record == null ? null : record.resources;
        Resources resources2 = entry2 == null ? null : (Resources) entry2.get();
        if (resources2 == null) {
            try {
                resources2 = generateResources(pluginInfo);
                if (!(resources2 == null || record == null)) {
                    record.resources = new ResourcesEntry(resources2);
                }
            } catch (Throwable th) {
                lock.unlock();
                throw th;
            }
        }
        lock.unlock();
        return resources2;
    }

    @PluginApi(since = 4)
    public Resources getGlobalResources() {
        return this.mContext.getResources();
    }

    public Context getPlatformContext() {
        return this.mContext;
    }

    @CorePluginApi(since = 400)
    public PluginPlatformConfig getPluginPlatformConfig() {
        return this.pluginPlatformConfig;
    }

    public int getPlatformVersion() {
        return this.pluginPlatformConfig.platformVersion;
    }

    @PluginApi(since = 400)
    public static int getBasePlatformVersion() {
        return 600;
    }

    @PluginApi(since = 400)
    public static String getBasePlatformVersionName() {
        return PluginConstant.PLUGIN_PLATFROM_VERSION_NAME;
    }

    private Resources generateResources(PluginInfo pluginInfo) {
        if (!PluginHelper.checkPluginInfo(pluginInfo)) {
            return null;
        }
        String pluginPath = pluginInfo.installPath;
        if (pluginInfo.isInternal() || isEmpty(pluginPath)) {
            return getGlobalResources();
        }
        Lock fileLock = PluginFileLock.readLock(pluginInfo.pluginId);
        fileLock.lock();
        try {
            return ApkUtil.getResources(this.mContext, pluginPath);
        } finally {
            fileLock.unlock();
        }
    }

    private PluginRecord getPluginRecord(String id, boolean autoCreate) {
        PluginRecord record;
        if (TextUtils.isEmpty(id)) {
            return null;
        }
        synchronized (this.mPluginRecords) {
            record = this.mPluginRecords.get(id);
            if (autoCreate && record == null) {
                record = new PluginRecord();
                this.mPluginRecords.put(id, record);
            }
        }
        return record;
    }

    static final class PluginRecord {
        Plugin plugin;
        ResourcesEntry resources;

        PluginRecord() {
        }
    }

    static final class ResourcesEntry extends SoftReference<Resources> {
        public ResourcesEntry(Resources r) {
            super(r);
        }
    }

    @CorePluginApi(since = 400)
    public boolean isPlatformInitialFinish() {
        return this.mPlatformInitialFinish;
    }

    @CorePluginApi(since = 400)
    public static PluginManager getInstance(Context context, String platformId) {
        PluginPlatformConfig pluginPlatformConfig2 = new PluginPlatformConfig();
        pluginPlatformConfig2.platformId = platformId;
        return getInstance(context, pluginPlatformConfig2);
    }

    @CorePluginApi(since = 400)
    public static PluginManager getInstance(Context context, PluginPlatformConfig platformConfig) {
        String platformId = platformConfig.platformId;
        PluginManager pluginManager = sInstanceMap.get(platformId);
        if (pluginManager == null) {
            synchronized (PluginManager.class) {
                try {
                    pluginManager = sInstanceMap.get(platformId);
                    if (pluginManager == null) {
                        PluginManager pluginManager2 = new PluginManager(context, platformConfig);
                        try {
                            sInstanceMap.put(platformId, pluginManager2);
                            pluginManager = pluginManager2;
                        } catch (Throwable th) {
                            th = th;
                            PluginManager pluginManager3 = pluginManager2;
                            throw th;
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    throw th;
                }
            }
        }
        return pluginManager;
    }

    private void async(Code code) {
        this.mPluginThreadPool.submit(code);
    }

    /* access modifiers changed from: private */
    public void runOnUIThread(Runnable runnable) {
        try {
            if (Looper.myLooper() != Looper.getMainLooper()) {
                this.mUIHandler.post(runnable);
            } else {
                runnable.run();
            }
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage(), e);
        }
    }

    protected abstract class Code implements ThreadPool.Job<Object> {
        public abstract void code() throws RemoteException;

        protected Code() {
        }

        public Object run(ThreadPool.JobContext jc) {
            try {
                code();
                return null;
            } catch (DeadObjectException e) {
                LogUtil.e(PluginManager.TAG, "occure DeadObjectException,try to stopService ", e);
                PluginManager.this.stopService();
                return null;
            } catch (RemoteException e2) {
                LogUtil.e(PluginManager.TAG, "Remote Code Exception : ", e2);
                return null;
            }
        }
    }
}
