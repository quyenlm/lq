package com.tencent.component.plugin.server;

import android.content.Context;
import android.os.RemoteException;
import com.tencent.component.plugin.PluginPlatformConfig;
import com.tencent.component.utils.log.LogUtil;
import java.io.File;
import java.util.concurrent.ConcurrentHashMap;

class PlatformServerContext {
    private static final String TAG = "PlatformServerContext";
    private static ConcurrentHashMap<String, PlatformServerContext> sInstanceMap = new ConcurrentHashMap<>();
    private volatile BuiltinPluginLoader mBuiltinPluginLoader;
    private Context mContext;
    private volatile PluginPlatformConfig mPlatformConfig;
    private final String mPlatformId;
    private volatile PluginInstaller mPluginInstaller;
    private volatile PluginLoader mPluginLoader;
    private volatile PluginManagerServer mPluginManagerServer;
    private volatile PluginServerBroadcast mPluginServerBroadcast;

    private PlatformServerContext(Context context, String platformId) {
        this.mPlatformId = platformId;
        this.mContext = context.getApplicationContext();
    }

    public Context getContext() {
        return this.mContext;
    }

    public String getPlatformId() {
        return this.mPlatformId;
    }

    public BuiltinPluginLoader getBuiltinPluginLoader() {
        if (this.mBuiltinPluginLoader == null) {
            synchronized (BuiltinPluginLoader.class) {
                if (this.mBuiltinPluginLoader == null) {
                    this.mBuiltinPluginLoader = new BuiltinPluginLoader(this);
                }
            }
        }
        return this.mBuiltinPluginLoader;
    }

    public PluginLoader getPluginLoader() {
        if (this.mPluginLoader == null) {
            synchronized (PluginLoader.class) {
                if (this.mPluginLoader == null) {
                    this.mPluginLoader = new PluginLoader(this);
                }
            }
        }
        return this.mPluginLoader;
    }

    public PluginInstaller getPluginInstaller() {
        if (this.mPluginInstaller == null) {
            synchronized (PluginInstaller.class) {
                if (this.mPluginInstaller == null) {
                    this.mPluginInstaller = new PluginInstaller(this);
                }
            }
        }
        return this.mPluginInstaller;
    }

    public PluginManagerServer getPluginManagerServer() {
        if (this.mPluginManagerServer == null) {
            synchronized (PluginManagerServer.class) {
                if (this.mPluginManagerServer == null) {
                    this.mPluginManagerServer = new PluginManagerServer(this);
                }
            }
        }
        return this.mPluginManagerServer;
    }

    public void setPlatformConfig(PluginPlatformConfig platformConfig) {
        this.mPlatformConfig = platformConfig;
    }

    public PluginPlatformConfig getPlatformConfig() {
        return this.mPlatformConfig;
    }

    public void setPluginServerBroadcast(PluginServerBroadcast broadcast) {
        this.mPluginServerBroadcast = broadcast;
    }

    public void broadcastPluginInstalled(String id, int oldVersion, int version) {
        if (this.mPluginServerBroadcast != null) {
            try {
                this.mPluginServerBroadcast.onPluginInstalled(id, oldVersion, version);
            } catch (RemoteException e) {
                LogUtil.e(TAG, e.getMessage(), e);
            }
        }
    }

    public void broadcastPluginUninstalled(String id) {
        if (this.mPluginServerBroadcast != null) {
            try {
                this.mPluginServerBroadcast.onPluginUninstalled(id);
            } catch (RemoteException e) {
                LogUtil.e(TAG, e.getMessage(), e);
            }
        }
    }

    public void broadcastPendingInstallFinish(int result, File file) {
        if (this.mPluginServerBroadcast != null) {
            try {
                String installLocation = file.getAbsolutePath();
                boolean corePlugin = PluginConstant.getCorePluginPendingInstallInfo(this.mContext, installLocation);
                String extraInfo = PluginConstant.getPendingInstallExtraInfo(this.mContext, installLocation);
                boolean success = result == PluginInstaller.INSTALL_SUCCEED;
                this.mPluginServerBroadcast.onPendingInstallFinish(success, corePlugin, extraInfo, success ? "" : "errorCode:" + result);
            } catch (RemoteException e) {
                LogUtil.e(TAG, e.getMessage(), e);
            }
        }
    }

    public void broadcastPluginChanged(String id, int changeFlags, int statusFlags) {
        if (this.mPluginServerBroadcast != null) {
            try {
                this.mPluginServerBroadcast.onPluginStateChange(id, changeFlags, statusFlags);
            } catch (RemoteException e) {
                LogUtil.e(TAG, e.getMessage(), e);
            }
        }
    }

    public void notifyInitializeStart() {
        if (this.mPluginServerBroadcast != null) {
            try {
                this.mPluginServerBroadcast.onPlatformInitialStart();
            } catch (RemoteException e) {
                LogUtil.e(TAG, e.getMessage(), e);
            }
        }
    }

    public void notifyInitializeFinish() {
        if (this.mPluginServerBroadcast != null) {
            try {
                this.mPluginServerBroadcast.onPlatformInitialFinish();
            } catch (RemoteException e) {
                LogUtil.e(TAG, e.getMessage(), e);
            }
        }
    }

    public static PlatformServerContext getInstance(Context context, String platformId) {
        PlatformServerContext platformServerContext = sInstanceMap.get(platformId);
        if (platformServerContext == null) {
            synchronized (PlatformServerContext.class) {
                try {
                    platformServerContext = sInstanceMap.get(platformId);
                    if (platformServerContext == null) {
                        PlatformServerContext platformServerContext2 = new PlatformServerContext(context, platformId);
                        try {
                            sInstanceMap.put(platformId, platformServerContext2);
                            platformServerContext = platformServerContext2;
                        } catch (Throwable th) {
                            th = th;
                            PlatformServerContext platformServerContext3 = platformServerContext2;
                            throw th;
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    throw th;
                }
            }
        }
        return platformServerContext;
    }
}
