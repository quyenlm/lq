package com.tencent.component.plugin;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.text.TextUtils;
import com.tencent.component.plugin.PluginManager;
import com.tencent.component.plugin.service.ILeafServiceConnection;
import com.tencent.component.plugin.service.ILeafServiceManager;
import com.tencent.component.utils.log.LogUtil;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TreeService extends Service {
    private static final String TAG = "TreeService";
    /* access modifiers changed from: private */
    public Handler mHandler = new Handler(Looper.getMainLooper());
    private ConcurrentHashMap<String, ConcurrentHashMap<String, Intent>> mIntentMap = new ConcurrentHashMap<>();
    private ILeafServiceManager.Stub mLeafServiceManager = new ILeafServiceManager.Stub() {
        public void bindService(String platformId, String pluginId, String leafServiceClassName, ILeafServiceConnection connection, Bundle args) throws RemoteException {
            TreeService.this.registerPluginListener(platformId);
            final String str = platformId;
            final String str2 = pluginId;
            final String str3 = leafServiceClassName;
            final Bundle bundle = args;
            final ILeafServiceConnection iLeafServiceConnection = connection;
            TreeService.this.runOnUIThread(new Runnable() {
                public void run() {
                    LeafService leafService = TreeService.this.getLeafService(str, str2, str3);
                    if (leafService != null) {
                        Intent intent = new Intent();
                        if (bundle != null) {
                            intent.putExtras(bundle);
                        }
                        IBinder binder = leafService.onBind(intent);
                        TreeService.this.putLeafServiceIntentToCache(str, str2, str3, intent);
                        try {
                            iLeafServiceConnection.connected(str3, binder);
                        } catch (RemoteException e) {
                        }
                    }
                }
            });
        }

        public void unbindService(String platformId, String pluginId, String leafServiceClassName, ILeafServiceConnection connection) throws RemoteException {
            final LeafService leafService = TreeService.this.getLeafServiceFromCache(platformId, pluginId, leafServiceClassName);
            if (leafService != null) {
                TreeService.this.removeLeafServiceFromCache(platformId, pluginId, leafServiceClassName);
                final String str = platformId;
                final String str2 = pluginId;
                final String str3 = leafServiceClassName;
                TreeService.this.runOnUIThread(new Runnable() {
                    public void run() {
                        leafService.onUnbind(TreeService.this.getLeafServiceIntentFromCache(str, str2, str3));
                        TreeService.this.removeLeafServiceFromCache(str, str2, str3);
                    }
                });
            }
        }

        public void startService(String platformId, String pluginId, String leafServiceClassName, Bundle args) throws RemoteException {
            TreeService.this.registerPluginListener(platformId);
            final String str = platformId;
            final String str2 = pluginId;
            final String str3 = leafServiceClassName;
            final Bundle bundle = args;
            TreeService.this.runOnUIThread(new Runnable() {
                public void run() {
                    LeafService leafService = TreeService.this.getLeafService(str, str2, str3);
                    if (leafService != null) {
                        Intent intent = new Intent();
                        if (bundle != null) {
                            intent.putExtras(bundle);
                        }
                        leafService.onStartCommand(intent, 0, 0);
                    }
                }
            });
        }

        public void stopService(String platformId, String pluginId, String leafServiceClassName) throws RemoteException {
            final LeafService leafService = TreeService.this.getLeafServiceFromCache(platformId, pluginId, leafServiceClassName);
            if (leafService != null) {
                TreeService.this.removeLeafServiceFromCache(platformId, pluginId, leafServiceClassName);
                TreeService.this.runOnUIThread(new Runnable() {
                    public void run() {
                        leafService.onDestroy();
                    }
                });
            }
        }
    };
    /* access modifiers changed from: private */
    public ConcurrentHashMap<String, ConcurrentHashMap<String, LeafService>> mLeafServiceMap = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, LeafPluginListener> mPluginListeners = new ConcurrentHashMap<>();
    private volatile TreeServiceHelper mTreeServiceHelper;

    /* access modifiers changed from: private */
    public void runOnUIThread(Runnable runnable) {
        if (runnable != null) {
            try {
                if (Looper.myLooper() == Looper.getMainLooper()) {
                    runnable.run();
                } else {
                    this.mHandler.post(runnable);
                }
            } catch (Throwable e) {
                LogUtil.w(TAG, e.getMessage(), e);
            }
        }
    }

    public IBinder onBind(Intent intent) {
        return this.mLeafServiceManager;
    }

    public void onCreate() {
        super.onCreate();
        LogUtil.i(TAG, "onCreate");
    }

    public void onDestroy() {
        super.onDestroy();
        unregisterPluginListener();
        for (Map.Entry<String, ConcurrentHashMap<String, LeafService>> entry : this.mLeafServiceMap.entrySet()) {
            ConcurrentHashMap<String, LeafService> map = entry.getValue();
            if (map != null) {
                for (Map.Entry<String, LeafService> leafServiceEntry : map.entrySet()) {
                    try {
                        leafServiceEntry.getValue().onDestroy();
                    } catch (Throwable th) {
                    }
                }
            }
        }
        this.mLeafServiceMap.clear();
    }

    /* access modifiers changed from: private */
    public void putLeafServiceIntentToCache(String platformId, String pluginId, String leafServiceName, Intent intent) {
        if (!TextUtils.isEmpty(platformId) && !TextUtils.isEmpty(pluginId) && !TextUtils.isEmpty(leafServiceName) && intent != null) {
            String key = platformId + "_" + pluginId;
            ConcurrentHashMap<String, Intent> map = this.mIntentMap.get(key);
            if (map == null) {
                map = new ConcurrentHashMap<>();
                this.mIntentMap.put(key, map);
            }
            map.put(leafServiceName, intent);
        }
    }

    /* access modifiers changed from: private */
    public Intent getLeafServiceIntentFromCache(String platformId, String pluginId, String leafServiceName) {
        ConcurrentHashMap<String, Intent> map;
        if (TextUtils.isEmpty(platformId) || TextUtils.isEmpty(pluginId) || TextUtils.isEmpty(leafServiceName) || (map = this.mIntentMap.get(platformId + "_" + pluginId)) == null) {
            return null;
        }
        return map.get(leafServiceName);
    }

    /* access modifiers changed from: private */
    public void registerPluginListener(String platformId) {
        if (!TextUtils.isEmpty(platformId)) {
            synchronized (this.mPluginListeners) {
                if (this.mPluginListeners.get(platformId) == null) {
                    LeafPluginListener listener = new LeafPluginListener(platformId);
                    this.mPluginListeners.put(platformId, listener);
                    PluginManager.getInstance((Context) this, platformId).addPluginListener(listener);
                }
            }
        }
    }

    private void unregisterPluginListener() {
        synchronized (this.mPluginListeners) {
            for (Map.Entry<String, LeafPluginListener> entry : this.mPluginListeners.entrySet()) {
                String platformId = entry.getKey();
                if (!TextUtils.isEmpty(platformId)) {
                    PluginManager.getInstance((Context) this, platformId).removePluginListener(entry.getValue());
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public LeafService getLeafServiceFromCache(String platformId, String pluginId, String leafServiceName) {
        ConcurrentHashMap<String, LeafService> map = this.mLeafServiceMap.get(platformId + "_" + pluginId);
        if (map != null) {
            return map.get(leafServiceName);
        }
        return null;
    }

    private void putLeafServiceToCache(String platformId, String pluginId, String leafServiceName, LeafService leafService) {
        String key = platformId + "_" + pluginId;
        ConcurrentHashMap<String, LeafService> map = this.mLeafServiceMap.get(key);
        if (map == null) {
            map = new ConcurrentHashMap<>();
            this.mLeafServiceMap.put(key, map);
        }
        map.put(leafServiceName, leafService);
    }

    /* access modifiers changed from: private */
    public void removeLeafServiceFromCache(String platformId, String pluginId, String leafServiceName) {
        ConcurrentHashMap<String, LeafService> map = this.mLeafServiceMap.get(platformId + "_" + pluginId);
        if (map != null) {
            map.remove(leafServiceName);
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v0, resolved type: com.tencent.component.plugin.PluginInfo} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v1, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v2, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v3, resolved type: java.lang.String} */
    /* access modifiers changed from: private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.tencent.component.plugin.LeafService getLeafService(java.lang.String r11, java.lang.String r12, java.lang.String r13) {
        /*
            r10 = this;
            r7 = 0
            boolean r6 = android.text.TextUtils.isEmpty(r11)
            if (r6 != 0) goto L_0x0013
            boolean r6 = android.text.TextUtils.isEmpty(r12)
            if (r6 != 0) goto L_0x0013
            boolean r6 = android.text.TextUtils.isEmpty(r13)
            if (r6 == 0) goto L_0x0015
        L_0x0013:
            r2 = r7
        L_0x0014:
            return r2
        L_0x0015:
            com.tencent.component.plugin.LeafService r2 = r10.getLeafServiceFromCache(r11, r12, r13)
            if (r2 != 0) goto L_0x0014
            com.tencent.component.plugin.PluginInfo r5 = r10.loadPluginInfoFromIntent(r11, r12)
            if (r5 != 0) goto L_0x0042
            r4 = r7
        L_0x0022:
            if (r5 == 0) goto L_0x0026
            if (r4 != 0) goto L_0x004d
        L_0x0026:
            java.lang.String r6 = "TreeService"
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r9 = "fail to init plugin for "
            java.lang.StringBuilder r8 = r8.append(r9)
            if (r5 == 0) goto L_0x004b
        L_0x0035:
            java.lang.StringBuilder r8 = r8.append(r5)
            java.lang.String r8 = r8.toString()
            com.tencent.component.utils.log.LogUtil.i(r6, r8)
            r2 = r7
            goto L_0x0014
        L_0x0042:
            com.tencent.component.plugin.PluginManager r6 = com.tencent.component.plugin.PluginManager.getInstance((android.content.Context) r10, (java.lang.String) r11)
            com.tencent.component.plugin.Plugin r4 = r6.getPlugin(r5)
            goto L_0x0022
        L_0x004b:
            r5 = r12
            goto L_0x0035
        L_0x004d:
            java.lang.Class r6 = r4.getClass()     // Catch:{ Exception -> 0x0068 }
            java.lang.ClassLoader r6 = r6.getClassLoader()     // Catch:{ Exception -> 0x0068 }
            java.lang.Class r3 = r6.loadClass(r13)     // Catch:{ Exception -> 0x0068 }
            java.lang.Object r6 = r3.newInstance()     // Catch:{ Exception -> 0x0068 }
            r0 = r6
            com.tencent.component.plugin.LeafService r0 = (com.tencent.component.plugin.LeafService) r0     // Catch:{ Exception -> 0x0068 }
            r2 = r0
            r2.init(r4, r10)     // Catch:{ Exception -> 0x0068 }
            r10.putLeafServiceToCache(r11, r12, r13, r2)     // Catch:{ Exception -> 0x0068 }
            goto L_0x0014
        L_0x0068:
            r1 = move-exception
            java.lang.String r6 = "TreeService"
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r9 = "fail to init leaf service "
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.StringBuilder r8 = r8.append(r13)
            java.lang.String r9 = " |"
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.StringBuilder r8 = r8.append(r5)
            java.lang.String r8 = r8.toString()
            com.tencent.component.utils.log.LogUtil.i(r6, r8, r1)
            r2 = r7
            goto L_0x0014
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.component.plugin.TreeService.getLeafService(java.lang.String, java.lang.String, java.lang.String):com.tencent.component.plugin.LeafService");
    }

    private PluginInfo loadPluginInfoFromIntent(String platformId, String pluginId) {
        if (TextUtils.isEmpty(platformId) || TextUtils.isEmpty(pluginId)) {
            return null;
        }
        return PluginManager.getInstance((Context) this, platformId).loadPluginInfoSync(pluginId);
    }

    /* access modifiers changed from: protected */
    public TreeServiceHelper getTreeServiceHelper() {
        if (this.mTreeServiceHelper == null) {
            this.mTreeServiceHelper = newTreeServiceHelper();
        }
        return this.mTreeServiceHelper;
    }

    /* access modifiers changed from: protected */
    public TreeServiceHelper newTreeServiceHelper() {
        return new TreeServiceHelper(this, 0, 0, 0, getClass());
    }

    /* access modifiers changed from: package-private */
    public void setTreeServiceForeground() {
        try {
            getTreeServiceHelper().setForeground();
        } catch (Exception e) {
        }
    }

    /* access modifiers changed from: package-private */
    public void setTreeServiceBackground() {
        try {
            getTreeServiceHelper().setBackground();
        } catch (Exception e) {
        }
    }

    private class LeafPluginListener implements PluginManager.PluginListener {
        /* access modifiers changed from: private */
        public String platformId;

        public LeafPluginListener(String platformId2) {
            this.platformId = platformId2;
        }

        public void onPluginChanged(final String pluginId, int changeFlags, int statusFlags) {
            if ((changeFlags & 1) != 0 && (statusFlags & 1) == 0 && !TextUtils.isEmpty(this.platformId)) {
                TreeService.this.mHandler.post(new Runnable() {
                    public void run() {
                        String key = LeafPluginListener.this.platformId + "_" + pluginId;
                        ConcurrentHashMap<String, LeafService> map = (ConcurrentHashMap) TreeService.this.mLeafServiceMap.get(key);
                        TreeService.this.mLeafServiceMap.remove(key);
                        if (map != null && map.size() > 0) {
                            for (Map.Entry<String, LeafService> entry : map.entrySet()) {
                                try {
                                    LeafService leafService = entry.getValue();
                                    if (leafService != null) {
                                        leafService.onDestroy();
                                    }
                                } catch (Exception e) {
                                }
                            }
                        }
                    }
                });
            }
        }

        public void onStartCheckPluginSurvive(List<PluginInfo> list) {
        }

        public void onPlatformInitialStart() {
        }

        public void onPlatformInitialFinish() {
        }

        public void onPluginInstalled(String id, int lastVersion, int version) {
        }

        public void onPluginUninstall(String id) {
        }

        public void onPendingInstallFinish(boolean success, boolean corePlugin, String extraInfo, String errorMsg) {
        }
    }
}
