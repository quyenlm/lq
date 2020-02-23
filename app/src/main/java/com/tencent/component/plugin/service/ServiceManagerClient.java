package com.tencent.component.plugin.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.RemoteException;
import com.tencent.component.plugin.TreeService;
import com.tencent.component.plugin.service.ILeafServiceManager;
import com.tencent.component.utils.log.LogUtil;
import java.util.HashMap;

final class ServiceManagerClient {
    private static final String TAG = "ServiceManagerClient";
    private static volatile Handler sHandler;
    /* access modifiers changed from: private */
    public final Object SERVICE_LOCK = new Object();
    private Context mContext;
    /* access modifiers changed from: private */
    public volatile ILeafServiceManager mService;
    private final HashMap<String, IBinder> mServiceCache = new HashMap<>();
    private Class mTreeServiceClass = TreeService.class;

    private static Handler getHandler() {
        if (sHandler == null) {
            synchronized (ServiceManagerClient.class) {
                if (sHandler == null) {
                    HandlerThread thread = new HandlerThread("service_mgr", 10);
                    thread.start();
                    sHandler = new Handler(thread.getLooper());
                }
            }
        }
        return sHandler;
    }

    ServiceManagerClient(Context context, Class treeServiceClass) {
        this.mTreeServiceClass = treeServiceClass;
        if (this.mTreeServiceClass == null) {
            this.mTreeServiceClass = TreeService.class;
        }
        this.mContext = context.getApplicationContext();
        bindService();
    }

    private boolean bindService() {
        return this.mContext.bindService(new Intent(this.mContext, this.mTreeServiceClass), new ServiceConnection() {
            public void onServiceDisconnected(ComponentName name) {
            }

            public void onServiceConnected(ComponentName name, IBinder service) {
                ILeafServiceManager unused = ServiceManagerClient.this.mService = ILeafServiceManager.Stub.asInterface(service);
                synchronized (ServiceManagerClient.this.SERVICE_LOCK) {
                    ServiceManagerClient.this.SERVICE_LOCK.notifyAll();
                }
            }
        }, 1);
    }

    /* access modifiers changed from: private */
    public ILeafServiceManager obtainServiceManager() {
        if (!isServiceManagerAlive()) {
            int count = 0;
            bindService();
            while (!isServiceManagerAlive() && (count = count + 1) <= 10) {
                try {
                    synchronized (this.SERVICE_LOCK) {
                        try {
                            this.SERVICE_LOCK.wait(300);
                        } catch (InterruptedException e) {
                        }
                    }
                } catch (Exception e2) {
                    LogUtil.e(TAG, "bindService(Reason.Restart) exception  :" + e2.getMessage());
                }
            }
        }
        return this.mService;
    }

    private boolean isServiceManagerAlive() {
        return this.mService != null && this.mService.asBinder().isBinderAlive();
    }

    public void bindService(String platformId, String pluginId, String leafServiceClassName, ILeafServiceConnection connection, Bundle args) throws RemoteException {
        IBinder service;
        synchronized (this.mServiceCache) {
            service = this.mServiceCache.get(leafServiceClassName);
        }
        if (service != null && isServiceAlive(service)) {
            try {
                connection.connected(leafServiceClassName, service);
            } catch (RemoteException e) {
            }
        } else if (isServiceManagerAlive()) {
            ILeafServiceManager leafServiceManager = obtainServiceManager();
            if (leafServiceManager != null) {
                leafServiceManager.bindService(platformId, pluginId, leafServiceClassName, connection, args);
            }
        } else {
            final String str = platformId;
            final String str2 = pluginId;
            final String str3 = leafServiceClassName;
            final ILeafServiceConnection iLeafServiceConnection = connection;
            final Bundle bundle = args;
            getHandler().post(new Runnable() {
                public void run() {
                    try {
                        ILeafServiceManager leafServiceManager = ServiceManagerClient.this.obtainServiceManager();
                        if (leafServiceManager != null) {
                            leafServiceManager.bindService(str, str2, str3, iLeafServiceConnection, bundle);
                        }
                    } catch (RemoteException e) {
                    }
                }
            });
        }
    }

    public void unbindService(String platformId, String pluginId, String leafServiceClassName, ILeafServiceConnection connection) throws RemoteException {
        synchronized (this.mServiceCache) {
            this.mServiceCache.remove(leafServiceClassName);
        }
        if (isServiceManagerAlive()) {
            ILeafServiceManager leafServiceManager = obtainServiceManager();
            if (leafServiceManager != null) {
                leafServiceManager.unbindService(platformId, pluginId, leafServiceClassName, connection);
                return;
            }
            return;
        }
        final String str = platformId;
        final String str2 = pluginId;
        final String str3 = leafServiceClassName;
        final ILeafServiceConnection iLeafServiceConnection = connection;
        getHandler().post(new Runnable() {
            public void run() {
                try {
                    ILeafServiceManager leafServiceManager = ServiceManagerClient.this.obtainServiceManager();
                    if (leafServiceManager != null) {
                        leafServiceManager.unbindService(str, str2, str3, iLeafServiceConnection);
                    }
                } catch (RemoteException e) {
                }
            }
        });
    }

    public void startService(String platformId, String pluginId, String leafServiceClassName, Bundle args) throws RemoteException {
        if (isServiceManagerAlive()) {
            ILeafServiceManager leafServiceManager = obtainServiceManager();
            if (leafServiceManager != null) {
                leafServiceManager.startService(platformId, pluginId, leafServiceClassName, args);
                return;
            }
            return;
        }
        final String str = platformId;
        final String str2 = pluginId;
        final String str3 = leafServiceClassName;
        final Bundle bundle = args;
        getHandler().post(new Runnable() {
            public void run() {
                try {
                    ILeafServiceManager leafServiceManager = ServiceManagerClient.this.obtainServiceManager();
                    if (leafServiceManager != null) {
                        leafServiceManager.startService(str, str2, str3, bundle);
                    }
                } catch (RemoteException e) {
                }
            }
        });
    }

    public void stopService(final String platformId, final String pluginId, final String leafServiceClassName) throws RemoteException {
        if (isServiceManagerAlive()) {
            ILeafServiceManager leafServiceManager = obtainServiceManager();
            if (leafServiceManager != null) {
                leafServiceManager.stopService(platformId, pluginId, leafServiceClassName);
                return;
            }
            return;
        }
        getHandler().post(new Runnable() {
            public void run() {
                try {
                    ILeafServiceManager leafServiceManager = ServiceManagerClient.this.obtainServiceManager();
                    if (leafServiceManager != null) {
                        leafServiceManager.stopService(platformId, pluginId, leafServiceClassName);
                    }
                } catch (RemoteException e) {
                }
            }
        });
    }

    private static boolean isServiceAlive(IBinder binder) {
        return binder != null && binder.isBinderAlive() && binder.pingBinder();
    }
}
