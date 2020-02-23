package com.tencent.component.plugin.server;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import com.tencent.component.plugin.IPluginManager;
import com.tencent.component.plugin.InstallPluginListener;
import com.tencent.component.plugin.PluginInfo;
import com.tencent.component.plugin.PluginManageHandler;
import com.tencent.component.plugin.PluginPlatformConfig;
import com.tencent.component.plugin.UninstallPluginListener;
import com.tencent.component.utils.log.LogUtil;
import com.tencent.component.utils.thread.ThreadPool;
import java.util.List;

public final class PluginServiceLogic {
    private static final String ARGS_PLATFORM_ID = "platformId";
    private static final String PLUGIN_SERVICE_ACTION = "com.tencent.component.plugin.server.PluginService";
    private static final String TAG = "PlguinService";
    private Context mContext;
    private ThreadPool mThreadPool = new ThreadPool("plugin-server-pool", 1, 1);

    public PluginServiceLogic(Context context) {
        this.mContext = context;
    }

    public void onCreate() {
    }

    public IBinder onBind(Intent intent) {
        String platformId = intent.getStringExtra(ARGS_PLATFORM_ID);
        if (!TextUtils.isEmpty(platformId)) {
            return new PluginServiceBinder(this.mContext, platformId, this.mThreadPool);
        }
        LogUtil.e(TAG, "Illeagal bind request as platformId is empty!");
        return null;
    }

    public static void bindPluginService(Context context, ServiceConnection connection, String platformId) {
        Intent intent = new Intent(PLUGIN_SERVICE_ACTION);
        intent.putExtra(ARGS_PLATFORM_ID, platformId);
        context.bindService(intent, connection, 1);
    }

    private static class PluginServiceBinder extends IPluginManager.Stub {
        /* access modifiers changed from: private */
        public Context mContext;
        private ThreadPool mThreadPool;

        private PluginManagerServer getPluginManagerServer(String platformId) {
            return PlatformServerContext.getInstance(this.mContext, platformId).getPluginManagerServer();
        }

        public PluginServiceBinder(Context context, String platformId, ThreadPool threadPool) {
            this.mContext = context.getApplicationContext();
            this.mThreadPool = threadPool;
        }

        public boolean registerPlugin(String platfromId, String id, PluginInfo pluginInfo) throws RemoteException {
            return getPluginManagerServer(platfromId).registerPlugin(id, pluginInfo);
        }

        public boolean unregisterPlugin(String platfromId, String id) throws RemoteException {
            return getPluginManagerServer(platfromId).unregisterPlugin(id);
        }

        public boolean isPluginRegistered(String platfromId, String id) throws RemoteException {
            return getPluginManagerServer(platfromId).isPluginRegistered(id);
        }

        public boolean enablePlugin(String platfromId, String id) throws RemoteException {
            return getPluginManagerServer(platfromId).enablePlugin(id);
        }

        public boolean disablePlugin(String platfromId, String id) throws RemoteException {
            return getPluginManagerServer(platfromId).disablePlugin(id);
        }

        public boolean isPluginEnabled(String platfromId, String id) throws RemoteException {
            return getPluginManagerServer(platfromId).isPluginEnabled(id);
        }

        public PluginInfo loadPluginInfo(String platfromId, String id) throws RemoteException {
            return getPluginManagerServer(platfromId).loadPluginInfo(id);
        }

        public PluginInfo getPluginInfo(String platfromId, String id) throws RemoteException {
            return getPluginManagerServer(platfromId).getPluginInfo(id);
        }

        public List<PluginInfo> getAllPluginInfos(String platfromId) throws RemoteException {
            return getPluginManagerServer(platfromId).getAllPluginInfos();
        }

        public void setPluginHandler(String platfromId, PluginManageHandler handler) throws RemoteException {
            getPluginManagerServer(platfromId).setPluginHandler(handler);
        }

        public Intent handlePluginUri(String platfromId, String id, Uri uri) throws RemoteException {
            return getPluginManagerServer(platfromId).handlePluginUri(id, uri);
        }

        public void install(String platfromId, String pluginLocation, InstallPluginListener listener) throws RemoteException {
            getPluginManagerServer(platfromId).install(pluginLocation, listener);
        }

        public void uninstall(String platfromId, PluginInfo pluginInfo, UninstallPluginListener listener) throws RemoteException {
            getPluginManagerServer(platfromId).uninstall(pluginInfo, listener);
        }

        public void markPluginSurviveable(String platformId, String pluginId, boolean surviveable) throws RemoteException {
            getPluginManagerServer(platformId).markPluginSurviveable(pluginId, surviveable);
        }

        public void hello(final PluginPlatformConfig platformConfig, final PluginServerBroadcast broadcast) throws RemoteException {
            this.mThreadPool.submit(new ThreadPool.Job<Void>() {
                public Void run(ThreadPool.JobContext jc) {
                    String platformId = platformConfig.platformId;
                    LogUtil.i(PluginServiceLogic.TAG, "receive hello from " + platformId);
                    PlatformServerContext platformServerContext = PlatformServerContext.getInstance(PluginServiceBinder.this.mContext, platformId);
                    platformServerContext.setPlatformConfig(platformConfig);
                    platformServerContext.setPluginServerBroadcast(broadcast);
                    platformServerContext.getPluginManagerServer().init();
                    return null;
                }
            });
        }
    }
}
