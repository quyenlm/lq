package com.tencent.qqgamemi.protocol.pbproxy;

import android.content.Context;
import com.tencent.component.event.Event;
import com.tencent.component.event.EventCenter;
import com.tencent.component.event.EventSource;
import com.tencent.component.event.Observer;
import com.tencent.component.utils.log.LogUtil;
import com.tencent.qqgamemi.mgc.connection.IPManager;
import com.tencent.qqgamemi.protocol.pbproxy.BaseProxy;
import java.util.HashSet;
import java.util.Iterator;

public class PBProxyManager {
    private static PBProxyManager mVideoRecordManager;
    /* access modifiers changed from: private */
    public String TAG;
    private ConnectionMonitorObserver observer;
    /* access modifiers changed from: private */
    public HashSet<BaseProxy> proxyCaches;

    public PBProxyManager() {
        this.TAG = "PBProxyManager";
        this.observer = null;
        this.proxyCaches = new HashSet<>();
        this.observer = new ConnectionMonitorObserver();
        this.observer.register();
    }

    public static PBProxyManager getInstance() {
        if (mVideoRecordManager == null) {
            synchronized (PBProxyManager.class) {
                if (mVideoRecordManager == null) {
                    mVideoRecordManager = new PBProxyManager();
                }
            }
        }
        return mVideoRecordManager;
    }

    public void init(Context context) {
        IPManager.getInstance().init(context);
    }

    public void requestWhiteListInfo(int plugin_version, BaseProxy.MessageListener listener) {
        WhiteListInfoProxyEx mWhiteListInfoProxyEx = new WhiteListInfoProxyEx(listener, Integer.valueOf(plugin_version));
        if (mWhiteListInfoProxyEx == null) {
            return;
        }
        if (!this.observer.isInitConnect()) {
            this.proxyCaches.add(mWhiteListInfoProxyEx);
            return;
        }
        mWhiteListInfoProxyEx.sendRequest();
        LogUtil.i(this.TAG, "requestWhiteListInfo is send!");
    }

    public void requestPluginUploadInfo(int plugin_version, BaseProxy.MessageListener listener) {
        PluginUploadProxyEx mPluginUploadProxyEx = new PluginUploadProxyEx(listener, Integer.valueOf(plugin_version));
        if (mPluginUploadProxyEx == null) {
            return;
        }
        if (!this.observer.isInitConnect()) {
            this.proxyCaches.add(mPluginUploadProxyEx);
        } else {
            mPluginUploadProxyEx.sendRequest();
        }
    }

    public void connectionSuccessNotify() {
        this.observer.notifySuccess();
    }

    public class ConnectionMonitorObserver {
        public static final String EVENT_SOURCE_NAME = "connectionMoitor";
        public static final int WHAT_CONNECTION_SUCCESS = 0;
        /* access modifiers changed from: private */
        public boolean isInitConnect = false;
        private Observer mObserver = new Observer() {
            public void onNotify(Event event) {
                LogUtil.i(PBProxyManager.this.TAG, "ConnectionMonitorObserver event: " + event + ",isInitConnect:" + ConnectionMonitorObserver.this.isInitConnect);
                if (event == null || ConnectionMonitorObserver.this.isInitConnect) {
                    return;
                }
                if (event.what == 0) {
                    boolean unused = ConnectionMonitorObserver.this.isInitConnect = true;
                    Iterator it = PBProxyManager.this.proxyCaches.iterator();
                    while (it.hasNext()) {
                        BaseProxy proxy = (BaseProxy) it.next();
                        LogUtil.i(PBProxyManager.this.TAG, "proxy:" + proxy.getClass().getSimpleName() + "is send!");
                        proxy.sendRequest();
                    }
                    PBProxyManager.this.proxyCaches.clear();
                    return;
                }
                boolean unused2 = ConnectionMonitorObserver.this.isInitConnect = false;
            }
        };

        public ConnectionMonitorObserver() {
        }

        public void notifySuccess() {
            LogUtil.i(PBProxyManager.this.TAG, "notifySuccess is called:");
            EventCenter.getInstance().notify(new EventSource(EVENT_SOURCE_NAME), 0, Event.EventRank.NORMAL, new Object[0]);
        }

        public boolean isInitConnect() {
            return this.isInitConnect;
        }

        public void register() {
            EventCenter.getInstance().addObserver(this.mObserver, EVENT_SOURCE_NAME, 0);
        }
    }
}
