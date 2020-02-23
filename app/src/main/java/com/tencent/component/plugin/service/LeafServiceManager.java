package com.tencent.component.plugin.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.text.TextUtils;
import com.tencent.component.plugin.LeafService;
import com.tencent.component.plugin.PluginManager;
import com.tencent.component.plugin.service.ILeafServiceConnection;
import com.tencent.component.utils.log.LogUtil;
import com.tencent.tp.a.h;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class LeafServiceManager {
    private static final String TAG = "LeafServiceManager";
    private static volatile ConcurrentHashMap<String, LeafServiceManager> sInstanceMap = new ConcurrentHashMap<>();
    private final ServiceManagerClient mService;
    private final HashMap<ServiceConnection, ServiceDispatcher> mServiceDispatchers = new HashMap<>();
    private Class mTreeServiceClass;

    protected LeafServiceManager(Context context, String platformId) {
        this.mTreeServiceClass = PluginManager.getInstance(context, platformId).getPluginPlatformConfig().pluginTreeServiceClass;
        this.mService = new ServiceManagerClient(context, this.mTreeServiceClass);
    }

    public static LeafServiceManager getInstance(Context context, String platformId) {
        LeafServiceManager leafServiceManager = sInstanceMap.get(platformId);
        if (leafServiceManager == null) {
            synchronized (LeafServiceManager.class) {
                try {
                    leafServiceManager = sInstanceMap.get(platformId);
                    if (leafServiceManager == null) {
                        LeafServiceManager leafServiceManager2 = new LeafServiceManager(context, platformId);
                        try {
                            sInstanceMap.put(platformId, leafServiceManager2);
                            leafServiceManager = leafServiceManager2;
                        } catch (Throwable th) {
                            th = th;
                            LeafServiceManager leafServiceManager3 = leafServiceManager2;
                            throw th;
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    throw th;
                }
            }
        }
        return leafServiceManager;
    }

    public void startService(String platformId, String pluginId, String leafServiceClassName, Bundle args) {
        try {
            this.mService.startService(platformId, pluginId, leafServiceClassName, args);
        } catch (RemoteException e) {
            LogUtil.w(TAG, "fail to start service " + leafServiceClassName, e);
        }
    }

    public void stopService(String platformId, String pluginId, String leafServiceClassName) {
        try {
            this.mService.stopService(platformId, pluginId, leafServiceClassName);
        } catch (RemoteException e) {
            LogUtil.w(TAG, "fail to stop service " + leafServiceClassName, e);
        }
    }

    public boolean bindService(String platformId, String pluginId, Class<? extends LeafService> clazz, ServiceConnection conn, Bundle args) {
        if (clazz == null) {
            return false;
        }
        return bindService(platformId, pluginId, clazz.getName(), conn, args, (Looper) null);
    }

    public boolean bindService(String platformId, String pluginId, String leafServiceName, ServiceConnection conn, Bundle args, Looper looper) {
        if (TextUtils.isEmpty(platformId) || TextUtils.isEmpty(pluginId) || TextUtils.isEmpty(leafServiceName) || conn == null) {
            LogUtil.i(TAG, "bindService failed [pluginId:" + pluginId + " | platformId:" + platformId + " | clazz:" + leafServiceName + " | conn:" + conn + "]");
            return false;
        }
        ILeafServiceConnection sc = getServiceDispatcher(platformId, pluginId, leafServiceName, conn, looper).getILeafServiceConnection();
        if (sc == null) {
            return false;
        }
        try {
            this.mService.bindService(platformId, pluginId, leafServiceName, sc, args);
            return true;
        } catch (RemoteException e) {
            LogUtil.w(TAG, "fail to bind service " + leafServiceName, e);
            return false;
        }
    }

    public void unbindService(ServiceConnection conn) {
        ServiceDispatcher sd;
        ILeafServiceConnection sc;
        synchronized (this.mServiceDispatchers) {
            sd = this.mServiceDispatchers.remove(conn);
        }
        if (sd != null && (sc = sd.getILeafServiceConnection()) != null && sd.mClazz != null) {
            try {
                this.mService.unbindService(sd.mPlatformId, sd.mPluginId, sd.mClazz, sc);
            } catch (RemoteException e) {
                LogUtil.w(TAG, "fail to unbind service " + sd.mClazz, e);
            }
        }
    }

    private ServiceDispatcher getServiceDispatcher(String platformId, String pluginId, String clazz, ServiceConnection conn, Looper looper) {
        ServiceDispatcher sd;
        synchronized (this.mServiceDispatchers) {
            sd = this.mServiceDispatchers.get(conn);
            if (sd == null) {
                sd = new ServiceDispatcher(platformId, pluginId, clazz, conn, looper);
                this.mServiceDispatchers.put(conn, sd);
            } else {
                sd.validate(looper);
            }
        }
        return sd;
    }

    private static class InnerConnection extends ILeafServiceConnection.Stub {
        private final WeakReference<ServiceDispatcher> mDispatcher;

        InnerConnection(ServiceDispatcher sd) {
            this.mDispatcher = new WeakReference<>(sd);
        }

        public void connected(String name, IBinder service) throws RemoteException {
            ServiceDispatcher sd = (ServiceDispatcher) this.mDispatcher.get();
            if (sd != null) {
                sd.connected(name, service);
            }
        }

        public void disconnected(String name) throws RemoteException {
            ServiceDispatcher sd = (ServiceDispatcher) this.mDispatcher.get();
            if (sd != null) {
                sd.forget();
            }
        }
    }

    private static class ConnectionInfo {
        IBinder binder;
        IBinder.DeathRecipient deathMonitor;

        private ConnectionInfo() {
        }
    }

    static final class ServiceDispatcher {
        private final HashMap<String, ConnectionInfo> mActiveConnections = new HashMap<>();
        final String mClazz;
        private final ServiceConnection mConnection;
        private boolean mDied;
        private boolean mForgotten;
        private final Handler mHandler;
        private final ILeafServiceConnection mILeafServiceConnection = new InnerConnection(this);
        private final Looper mLooper;
        final String mPlatformId;
        final String mPluginId;

        ServiceDispatcher(String platformId, String pluginId, String clazz, ServiceConnection conn, Looper looper) {
            this.mConnection = conn;
            this.mLooper = looper;
            this.mHandler = looper != null ? new Handler(looper) : null;
            this.mClazz = clazz;
            this.mPluginId = pluginId;
            this.mPlatformId = platformId;
        }

        /* access modifiers changed from: package-private */
        public ILeafServiceConnection getILeafServiceConnection() {
            return this.mILeafServiceConnection;
        }

        /* access modifiers changed from: package-private */
        public ServiceConnection getServiceConnection() {
            return this.mConnection;
        }

        /* access modifiers changed from: package-private */
        public void forget() {
            synchronized (this) {
                for (ConnectionInfo ci : this.mActiveConnections.values()) {
                    try {
                        ci.binder.unlinkToDeath(ci.deathMonitor, 0);
                    } catch (Throwable th) {
                    }
                }
                this.mActiveConnections.clear();
                this.mForgotten = true;
            }
        }

        /* access modifiers changed from: package-private */
        public void connected(String name, IBinder service) {
            if (this.mHandler != null) {
                this.mHandler.post(new RunConnection(name, service, 0));
            } else {
                doConnected(name, service);
            }
        }

        /* access modifiers changed from: package-private */
        /* JADX WARNING: Code restructure failed: missing block: B:24:0x0044, code lost:
            r0 = new android.content.ComponentName(r7.mPluginId, r8);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:0x004b, code lost:
            if (r3 == null) goto L_0x0052;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:0x004d, code lost:
            r7.mConnection.onServiceDisconnected(r0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:0x0052, code lost:
            if (r9 == null) goto L_?;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:28:0x0054, code lost:
            r7.mConnection.onServiceConnected(r0, r9);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:37:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:38:?, code lost:
            return;
         */
        /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void doConnected(java.lang.String r8, android.os.IBinder r9) {
            /*
                r7 = this;
                monitor-enter(r7)
                boolean r4 = r7.mForgotten     // Catch:{ all -> 0x0017 }
                if (r4 == 0) goto L_0x0007
                monitor-exit(r7)     // Catch:{ all -> 0x0017 }
            L_0x0006:
                return
            L_0x0007:
                java.util.HashMap<java.lang.String, com.tencent.component.plugin.service.LeafServiceManager$ConnectionInfo> r4 = r7.mActiveConnections     // Catch:{ all -> 0x0017 }
                java.lang.Object r3 = r4.get(r8)     // Catch:{ all -> 0x0017 }
                com.tencent.component.plugin.service.LeafServiceManager$ConnectionInfo r3 = (com.tencent.component.plugin.service.LeafServiceManager.ConnectionInfo) r3     // Catch:{ all -> 0x0017 }
                if (r3 == 0) goto L_0x001a
                android.os.IBinder r4 = r3.binder     // Catch:{ all -> 0x0017 }
                if (r4 != r9) goto L_0x001a
                monitor-exit(r7)     // Catch:{ all -> 0x0017 }
                goto L_0x0006
            L_0x0017:
                r4 = move-exception
                monitor-exit(r7)     // Catch:{ all -> 0x0017 }
                throw r4
            L_0x001a:
                if (r9 == 0) goto L_0x0062
                r4 = 0
                r7.mDied = r4     // Catch:{ all -> 0x0017 }
                com.tencent.component.plugin.service.LeafServiceManager$ConnectionInfo r2 = new com.tencent.component.plugin.service.LeafServiceManager$ConnectionInfo     // Catch:{ all -> 0x0017 }
                r4 = 0
                r2.<init>()     // Catch:{ all -> 0x0017 }
                r2.binder = r9     // Catch:{ all -> 0x0017 }
                com.tencent.component.plugin.service.LeafServiceManager$ServiceDispatcher$DeathMonitor r4 = new com.tencent.component.plugin.service.LeafServiceManager$ServiceDispatcher$DeathMonitor     // Catch:{ all -> 0x0017 }
                r4.<init>(r8, r9)     // Catch:{ all -> 0x0017 }
                r2.deathMonitor = r4     // Catch:{ all -> 0x0017 }
                android.os.IBinder$DeathRecipient r4 = r2.deathMonitor     // Catch:{ RemoteException -> 0x005a }
                r5 = 0
                r9.linkToDeath(r4, r5)     // Catch:{ RemoteException -> 0x005a }
                java.util.HashMap<java.lang.String, com.tencent.component.plugin.service.LeafServiceManager$ConnectionInfo> r4 = r7.mActiveConnections     // Catch:{ RemoteException -> 0x005a }
                r4.put(r8, r2)     // Catch:{ RemoteException -> 0x005a }
            L_0x0039:
                if (r3 == 0) goto L_0x0043
                android.os.IBinder r4 = r3.binder     // Catch:{ all -> 0x0017 }
                android.os.IBinder$DeathRecipient r5 = r3.deathMonitor     // Catch:{ all -> 0x0017 }
                r6 = 0
                r4.unlinkToDeath(r5, r6)     // Catch:{ all -> 0x0017 }
            L_0x0043:
                monitor-exit(r7)     // Catch:{ all -> 0x0017 }
                android.content.ComponentName r0 = new android.content.ComponentName
                java.lang.String r4 = r7.mPluginId
                r0.<init>(r4, r8)
                if (r3 == 0) goto L_0x0052
                android.content.ServiceConnection r4 = r7.mConnection
                r4.onServiceDisconnected(r0)
            L_0x0052:
                if (r9 == 0) goto L_0x0006
                android.content.ServiceConnection r4 = r7.mConnection
                r4.onServiceConnected(r0, r9)
                goto L_0x0006
            L_0x005a:
                r1 = move-exception
                java.util.HashMap<java.lang.String, com.tencent.component.plugin.service.LeafServiceManager$ConnectionInfo> r4 = r7.mActiveConnections     // Catch:{ all -> 0x0017 }
                r4.remove(r8)     // Catch:{ all -> 0x0017 }
                monitor-exit(r7)     // Catch:{ all -> 0x0017 }
                goto L_0x0006
            L_0x0062:
                java.util.HashMap<java.lang.String, com.tencent.component.plugin.service.LeafServiceManager$ConnectionInfo> r4 = r7.mActiveConnections     // Catch:{ all -> 0x0017 }
                r4.remove(r8)     // Catch:{ all -> 0x0017 }
                goto L_0x0039
            */
            throw new UnsupportedOperationException("Method not decompiled: com.tencent.component.plugin.service.LeafServiceManager.ServiceDispatcher.doConnected(java.lang.String, android.os.IBinder):void");
        }

        /* access modifiers changed from: package-private */
        /* JADX WARNING: Code restructure failed: missing block: B:12:0x0020, code lost:
            if (r5.mHandler == null) goto L_0x0030;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:0x0022, code lost:
            r5.mHandler.post(new com.tencent.component.plugin.service.LeafServiceManager.ServiceDispatcher.RunConnection(r5, r6, r7, 1));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x0030, code lost:
            doDeath(r6, r7);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:24:?, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void death(java.lang.String r6, android.os.IBinder r7) {
            /*
                r5 = this;
                r4 = 1
                monitor-enter(r5)
                r1 = 1
                r5.mDied = r1     // Catch:{ all -> 0x002d }
                java.util.HashMap<java.lang.String, com.tencent.component.plugin.service.LeafServiceManager$ConnectionInfo> r1 = r5.mActiveConnections     // Catch:{ all -> 0x002d }
                java.lang.Object r0 = r1.remove(r6)     // Catch:{ all -> 0x002d }
                com.tencent.component.plugin.service.LeafServiceManager$ConnectionInfo r0 = (com.tencent.component.plugin.service.LeafServiceManager.ConnectionInfo) r0     // Catch:{ all -> 0x002d }
                if (r0 == 0) goto L_0x0013
                android.os.IBinder r1 = r0.binder     // Catch:{ all -> 0x002d }
                if (r1 == r7) goto L_0x0015
            L_0x0013:
                monitor-exit(r5)     // Catch:{ all -> 0x002d }
            L_0x0014:
                return
            L_0x0015:
                android.os.IBinder r1 = r0.binder     // Catch:{ all -> 0x002d }
                android.os.IBinder$DeathRecipient r2 = r0.deathMonitor     // Catch:{ all -> 0x002d }
                r3 = 0
                r1.unlinkToDeath(r2, r3)     // Catch:{ all -> 0x002d }
                monitor-exit(r5)     // Catch:{ all -> 0x002d }
                android.os.Handler r1 = r5.mHandler
                if (r1 == 0) goto L_0x0030
                android.os.Handler r1 = r5.mHandler
                com.tencent.component.plugin.service.LeafServiceManager$ServiceDispatcher$RunConnection r2 = new com.tencent.component.plugin.service.LeafServiceManager$ServiceDispatcher$RunConnection
                r2.<init>(r6, r7, r4)
                r1.post(r2)
                goto L_0x0014
            L_0x002d:
                r1 = move-exception
                monitor-exit(r5)     // Catch:{ all -> 0x002d }
                throw r1
            L_0x0030:
                r5.doDeath(r6, r7)
                goto L_0x0014
            */
            throw new UnsupportedOperationException("Method not decompiled: com.tencent.component.plugin.service.LeafServiceManager.ServiceDispatcher.death(java.lang.String, android.os.IBinder):void");
        }

        /* access modifiers changed from: package-private */
        public void doDeath(String name, IBinder service) {
            this.mConnection.onServiceDisconnected(new ComponentName(this.mPluginId, name));
        }

        /* access modifiers changed from: package-private */
        public void validate(Looper looper) {
            if (this.mLooper != looper) {
                throw new RuntimeException("ServiceConnection " + this.mConnection + " registered with differing looper (was " + this.mLooper + " now " + looper + h.b);
            }
        }

        private final class RunConnection implements Runnable {
            final int mCommand;
            final String mName;
            final IBinder mService;

            RunConnection(String name, IBinder service, int command) {
                this.mName = name;
                this.mService = service;
                this.mCommand = command;
            }

            public void run() {
                if (this.mCommand == 0) {
                    ServiceDispatcher.this.doConnected(this.mName, this.mService);
                } else if (this.mCommand == 1) {
                    ServiceDispatcher.this.doDeath(this.mName, this.mService);
                }
            }
        }

        private final class DeathMonitor implements IBinder.DeathRecipient {
            final String mName;
            final IBinder mService;

            DeathMonitor(String name, IBinder service) {
                this.mName = name;
                this.mService = service;
            }

            public void binderDied() {
                ServiceDispatcher.this.death(this.mName, this.mService);
            }
        }
    }
}
