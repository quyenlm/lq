package com.tencent.component.plugin.server;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.text.TextUtils;
import com.tencent.component.cache.sp.PreferenceUtil;
import com.tencent.component.plugin.InstallPluginListener;
import com.tencent.component.plugin.PluginHelper;
import com.tencent.component.plugin.PluginInfo;
import com.tencent.component.plugin.PluginManageHandler;
import com.tencent.component.plugin.PluginManageInternalHandler;
import com.tencent.component.plugin.UninstallPluginListener;
import com.tencent.component.utils.UniqueLock;
import com.tencent.component.utils.log.LogUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.Lock;

class PluginManagerServer {
    private static final String PREFENCE_ENABLE_KEY = "plugin_enabled_";
    private static final String PREFENCE_PLUGIN_FILE = "plugin_enable_records";
    private static final String TAG = "PluginManagerServer";
    private final Context mContext;
    private PluginManageHandler mHandler;
    private volatile boolean mInitialed;
    private PluginManageInternalHandler mInternalHandler;
    /* access modifiers changed from: private */
    public final PlatformServerContext mPlatformServerContext;
    private final HashMap<String, PluginRecord> mPluginRecords = new HashMap<>();
    private final ArrayList<PluginInfo> mPlugins = new ArrayList<>();
    private final UniqueLock<String> mUniqueRecordLock = new UniqueLock<>();

    PluginManagerServer(PlatformServerContext platformServerContext) {
        this.mPlatformServerContext = platformServerContext;
        this.mContext = platformServerContext.getContext();
    }

    /* access modifiers changed from: package-private */
    public void init() {
        if (!this.mInitialed) {
            this.mInitialed = true;
            try {
                notifyInitializeStart(this.mPlatformServerContext);
                initPluginServiceHandler();
                this.mPlatformServerContext.getPluginLoader().load();
                this.mPlatformServerContext.getBuiltinPluginLoader().load();
                this.mPlatformServerContext.getPluginInstaller().install();
                notifyInitializeFinish(this.mPlatformServerContext);
            } catch (Exception e) {
                this.mInitialed = false;
                LogUtil.e(TAG, e.getMessage(), e);
            }
        } else {
            LogUtil.i(TAG, "ignore init request..");
        }
    }

    public boolean registerPlugin(String id, PluginInfo pluginInfo) {
        if (!PluginHelper.checkPluginId(id) || !PluginHelper.checkPluginInfo(pluginInfo)) {
            return false;
        }
        synchronized (this.mPluginRecords) {
            if (this.mPluginRecords.containsKey(id)) {
                return false;
            }
            PluginInfo newPluginInfo = new PluginInfo(pluginInfo);
            newPluginInfo.pluginId = id;
            PluginRecord record = new PluginRecord();
            record.pluginInfo = newPluginInfo;
            newPluginInfo.enabled = getEnableState(id, true);
            this.mPluginRecords.put(id, record);
            this.mPlugins.add(newPluginInfo);
            broadcastPluginChanged(id, 1, 1);
            return true;
        }
    }

    public boolean unregisterPlugin(String id) {
        if (!PluginHelper.checkPluginId(id)) {
            return false;
        }
        synchronized (this.mPluginRecords) {
            PluginRecord record = this.mPluginRecords.remove(id);
            if (record == null) {
                return false;
            }
            this.mPlugins.remove(record.pluginInfo);
            broadcastPluginChanged(id, 1, 0);
            return true;
        }
    }

    public boolean isPluginRegistered(String id) {
        return getPluginRecord(id) != null;
    }

    public boolean enablePlugin(String id) {
        PluginRecord record = getPluginRecord(id);
        if (record == null) {
            return false;
        }
        Lock lock = this.mUniqueRecordLock.obtain(id);
        lock.lock();
        try {
            if (record.isEnabled()) {
                return false;
            }
            record.setEnabled(true);
            saveEnableState(id, true);
            lock.unlock();
            broadcastPluginChanged(id, 2, 2);
            return true;
        } finally {
            lock.unlock();
        }
    }

    public boolean disablePlugin(String id) {
        PluginRecord record = getPluginRecord(id);
        if (record == null) {
            return false;
        }
        Lock lock = this.mUniqueRecordLock.obtain(id);
        lock.lock();
        try {
            if (!record.isEnabled()) {
                return false;
            }
            record.setEnabled(false);
            saveEnableState(id, false);
            lock.unlock();
            broadcastPluginChanged(id, 2, 0);
            return true;
        } finally {
            lock.unlock();
        }
    }

    private void saveEnableState(String id, boolean enable) {
        if (!TextUtils.isEmpty(id)) {
            PreferenceUtil.getGlobalPreference(this.mContext, PREFENCE_PLUGIN_FILE).edit().putBoolean(PREFENCE_ENABLE_KEY + id, enable).commit();
        }
    }

    private void removeEnableState(String id) {
        if (!TextUtils.isEmpty(id)) {
            PreferenceUtil.getGlobalPreference(this.mContext, PREFENCE_PLUGIN_FILE).edit().remove(PREFENCE_ENABLE_KEY + id).commit();
        }
    }

    private boolean getEnableState(String id, boolean defaultValue) {
        if (!TextUtils.isEmpty(id)) {
            return PreferenceUtil.getGlobalPreference(this.mContext, PREFENCE_PLUGIN_FILE).getBoolean(PREFENCE_ENABLE_KEY + id, defaultValue);
        }
        return defaultValue;
    }

    public boolean isPluginEnabled(String id) {
        PluginRecord record = getPluginRecord(id);
        return record != null && record.isEnabled();
    }

    public PluginInfo loadPluginInfo(String id) {
        PluginManageInternalHandler handler;
        PluginInfo pluginInfo = getPluginInfo(id);
        if (pluginInfo != null) {
            return pluginInfo;
        }
        if (PluginHelper.checkPluginId(id) && (handler = this.mInternalHandler) != null) {
            try {
                if (handler.onPluginNotFound(id)) {
                    LogUtil.i(TAG, "plugin " + id + " not found, try to perform load on demand");
                    return getPluginInfo(id);
                }
            } catch (RemoteException e) {
                LogUtil.e(TAG, e.getMessage(), e);
            }
        }
        return null;
    }

    public PluginInfo getPluginInfo(String id) {
        PluginRecord record = getPluginRecord(id);
        if (record == null) {
            return null;
        }
        return record.pluginInfo;
    }

    public List<PluginInfo> getAllPluginInfos() {
        ArrayList arrayList;
        synchronized (this.mPluginRecords) {
            arrayList = this.mPlugins.isEmpty() ? null : new ArrayList(this.mPlugins);
        }
        return arrayList;
    }

    public List<PluginInfo> getAllCorePluginInfos() {
        ArrayList<PluginInfo> corePluginList;
        synchronized (this.mPluginRecords) {
            if (this.mPlugins.isEmpty()) {
                corePluginList = null;
            } else {
                corePluginList = new ArrayList<>();
                Iterator<PluginInfo> it = this.mPlugins.iterator();
                while (it.hasNext()) {
                    PluginInfo pluginInfo = it.next();
                    if (pluginInfo != null && pluginInfo.corePlugin) {
                        corePluginList.add(pluginInfo);
                    }
                }
            }
        }
        return corePluginList;
    }

    public void install(String pluginLocation, InstallPluginListener listener) throws RemoteException {
        if (!TextUtils.isEmpty(pluginLocation)) {
            int result = this.mPlatformServerContext.getPluginInstaller().install(new File(pluginLocation), true);
            if (listener == null) {
                return;
            }
            if (result > 0) {
                try {
                    listener.onInstallSuccess();
                } catch (Exception e) {
                    LogUtil.e(TAG, e.getMessage(), e);
                }
            } else {
                listener.onInstallFailed("errorCode:" + result);
            }
        } else if (listener != null) {
            listener.onInstallFailed("pluginLocation is empty");
        }
    }

    public void markPluginSurviveable(String pluginId, boolean surviveable) {
        PluginInfo pluginInfo = getPluginInfo(pluginId);
        if (pluginInfo != null) {
            pluginInfo.surviveable = surviveable;
            LogUtil.i(TAG, "mark plugin:" + pluginId + " surviveable:" + surviveable);
        }
    }

    public void uninstall(PluginInfo pluginInfo, UninstallPluginListener listener) throws RemoteException {
        if (pluginInfo != null) {
            boolean success = this.mPlatformServerContext.getPluginInstaller().uninstall(pluginInfo);
            if (listener == null) {
                return;
            }
            if (success) {
                try {
                    listener.onUninstallSuccess();
                    removeEnableState(pluginInfo.pluginId);
                } catch (Exception e) {
                    LogUtil.e(TAG, e.getMessage(), e);
                }
            } else {
                listener.onUninstallFailed("uninstall failed.");
            }
        } else if (listener != null) {
            listener.onUninstallFailed("pluginInfo is null");
        }
    }

    public void setPluginInternalHandler(PluginManageInternalHandler handler) {
        throwIfRemoteCall("cannot set plugin internal handler from remote process");
        if (handler != null) {
            throwIfRemoteBinder(handler.asBinder(), "only support local process handler");
        }
        this.mInternalHandler = handler;
    }

    public void setPluginHandler(PluginManageHandler handler) {
        throwIfRemoteCall("cannot set plugin handler from remote process");
        if (handler != null) {
            throwIfRemoteBinder(handler.asBinder(), "only support local process handler");
        }
        this.mHandler = handler;
    }

    public Intent handlePluginUri(String id, Uri uri) throws RemoteException {
        PluginManageHandler handler = this.mHandler;
        Intent intent = null;
        if (handler != null && handler.asBinder().isBinderAlive()) {
            intent = handler.onInterceptPluginUri(id, uri);
        }
        if (intent != null || uri == null) {
            return intent;
        }
        Intent intent2 = new Intent();
        intent2.setAction("android.intent.action.VIEW");
        intent2.setData(uri);
        return intent2;
    }

    private PluginRecord getPluginRecord(String id) {
        PluginRecord pluginRecord;
        if (!PluginHelper.checkPluginId(id)) {
            return null;
        }
        synchronized (this.mPluginRecords) {
            pluginRecord = this.mPluginRecords.get(id);
        }
        return pluginRecord;
    }

    private void broadcastPluginChanged(String id, int changeFlags, int statusFlags) {
        this.mPlatformServerContext.broadcastPluginChanged(id, changeFlags, statusFlags);
    }

    private static void throwIfRemoteCall(String msg) {
        if (Binder.getCallingPid() != Process.myPid()) {
            throw new RuntimeException(msg);
        }
    }

    private static void throwIfRemoteBinder(IBinder binder, String msg) {
        if (!(binder instanceof Binder)) {
            throw new RuntimeException(msg);
        }
    }

    private static void notifyInitializeStart(PlatformServerContext platformServerContext) {
        platformServerContext.notifyInitializeStart();
    }

    private static void notifyInitializeFinish(PlatformServerContext platformServerContext) {
        platformServerContext.notifyInitializeFinish();
    }

    private boolean initPluginServiceHandler() {
        try {
            setPluginInternalHandler(new PluginManageInternalHandler.Stub() {
                public boolean onPluginNotFound(String id) throws RemoteException {
                    PluginManagerServer.this.mPlatformServerContext.getPluginLoader().load(id);
                    PluginManagerServer.this.mPlatformServerContext.getBuiltinPluginLoader().load(id);
                    return true;
                }
            });
            return true;
        } catch (Throwable e) {
            LogUtil.d(TAG, "fail to init plugin service handler", e);
            return false;
        }
    }

    static final class PluginRecord {
        PluginInfo pluginInfo;

        PluginRecord() {
        }

        /* access modifiers changed from: package-private */
        public boolean isEnabled() {
            return this.pluginInfo != null && this.pluginInfo.enabled;
        }

        /* access modifiers changed from: package-private */
        public void setEnabled(boolean enabled) {
            if (this.pluginInfo != null) {
                this.pluginInfo.enabled = enabled;
            }
        }
    }
}
