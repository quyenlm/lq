package com.garena.android.gpns.logic;

import android.content.Context;
import com.garena.android.gpns.GNotificationService;
import com.garena.android.gpns.ServiceHandler;
import com.garena.android.gpns.network.NetworkRequestHandler;
import com.garena.android.gpns.network.NetworkResponseListener;
import com.garena.android.gpns.network.NetworkThread;
import com.garena.android.gpns.network.PacketRouter;
import com.garena.android.gpns.network.tcp.TCPPacket;
import com.garena.android.gpns.notification.NotifyItem;
import com.garena.android.gpns.notification.event.NotifyEvent;
import com.garena.android.gpns.storage.LocalStorage;
import com.garena.android.gpns.utility.AlarmUtil;
import com.garena.android.gpns.utility.AppLogger;
import com.garena.android.gpns.utility.CONSTANT;
import com.garena.android.gpns.utility.DeviceUtil;
import com.garena.android.gpns.utility.TCPPacketFactory;
import com.tencent.qqgamemi.util.TimeUtils;

public class ServiceController implements NetworkResponseListener {
    private final int RECONNECT_TIME_WHEN_INVALID_GIP_RECEIVED = 600000;
    private final NotifyItem mAckPushMessage = new NotifyItem() {
        public void onNotify(NotifyEvent event) {
            if (event.notifyData instanceof TCPPacket) {
                ServiceController.this.mNetworkRequestHandler.sendPacket((TCPPacket) event.notifyData);
            }
        }
    };
    private final NotifyItem mConnectToAuthServer = new NotifyItem() {
        public void onNotify(NotifyEvent event) {
            ServiceController.this.connectToAuthServer();
        }
    };
    private final NotifyItem mConnectToLiveServer = new NotifyItem() {
        public void onNotify(NotifyEvent event) {
            ServiceController.this.connectToNotificationServer();
        }
    };
    /* access modifiers changed from: private */
    public final Context mContext;
    private AppLogger.NetworkThreadExceptionHandler mExceptionHandler = new AppLogger.NetworkThreadExceptionHandler() {
        public void uncaughtException(Thread thread, Throwable ex) {
            super.uncaughtException(thread, ex);
            ServiceController.this.scheduleWakeConnect(ServiceController.this.getTimeout());
        }
    };
    /* access modifiers changed from: private */
    public NetworkRequestHandler mNetworkRequestHandler;
    /* access modifiers changed from: private */
    public NetworkThread mNetworkThread;
    private NotifyItem mOnLongPing = new NotifyItem() {
        public void onNotify(NotifyEvent event) {
            if (ServiceController.this.isStateValid()) {
                ServiceController.this.mNetworkRequestHandler.sendPacket(TCPPacketFactory.newPingRequestPacket());
            }
        }
    };
    private NotifyItem mOnShortPing = new NotifyItem() {
        public void onNotify(NotifyEvent event) {
            if (ServiceController.this.isStateValid()) {
                if (!DeviceUtil.isConnectedToNetwork(ServiceController.this.mContext)) {
                    ServiceController.this.notifyInternetDisconnected();
                } else if (ServiceController.this.mNetworkThread.isTCPDisconnected()) {
                    ServiceController.this.scheduleWakeConnect(30000);
                }
            }
        }
    };
    private NotifyItem mOnWakeConnect = new NotifyItem() {
        public void onNotify(NotifyEvent event) {
            if (System.currentTimeMillis() - LocalStorage.getRegionRequestTime() >= TimeUtils.MILLIS_IN_DAY) {
                ServiceController.this.connectToRegionServer();
            } else {
                ServiceController.this.connectToAuthServer();
            }
        }
    };
    private NotifyItem mPerformPing = new NotifyItem() {
        public void onNotify(NotifyEvent event) {
            if (event.notifyData instanceof TCPPacket) {
                ServiceController.this.mNetworkRequestHandler.sendPacket((TCPPacket) event.notifyData);
            }
        }
    };
    private final NotifyItem mScheduleReconnectWhenInvalidGIPReceived = new NotifyItem() {
        public void onNotify(NotifyEvent event) {
            AppLogger.d("received gip == 0, reconnect to auth server");
            if (ServiceController.this.mContext != null) {
                AlarmUtil.scheduleWakeConnect(ServiceController.this.mContext, 600000);
            }
        }
    };
    private ServiceHandler mServiceHandler;
    private int mTimeout = 30000;

    public ServiceController(Context context) {
        this.mContext = context;
        this.mServiceHandler = new ServiceHandler(this);
        registerNotifications();
    }

    /* access modifiers changed from: private */
    public void connectToRegionServer() {
        if (this.mNetworkThread == null || !this.mNetworkThread.isAlive()) {
            this.mNetworkThread = new NetworkThread(this.mServiceHandler);
            this.mNetworkThread.start();
            this.mNetworkThread.setUncaughtExceptionHandler(this.mExceptionHandler);
            this.mNetworkRequestHandler = this.mNetworkThread.getHandler();
        }
        this.mNetworkRequestHandler.connectRegionServer();
    }

    /* access modifiers changed from: private */
    public void connectToAuthServer() {
        if (this.mNetworkThread == null || !this.mNetworkThread.isAlive()) {
            this.mNetworkThread = new NetworkThread(this.mServiceHandler);
            this.mNetworkThread.start();
            this.mNetworkThread.setUncaughtExceptionHandler(this.mExceptionHandler);
            this.mNetworkRequestHandler = this.mNetworkThread.getHandler();
        }
        this.mNetworkRequestHandler.connectAuthenticationServer();
    }

    /* access modifiers changed from: private */
    public void connectToNotificationServer() {
        if (this.mNetworkThread == null || !this.mNetworkThread.isAlive()) {
            this.mNetworkThread = new NetworkThread(this.mServiceHandler);
            this.mNetworkThread.start();
            this.mNetworkThread.setUncaughtExceptionHandler(this.mExceptionHandler);
            this.mNetworkRequestHandler = this.mNetworkThread.getHandler();
        }
        this.mNetworkRequestHandler.connectNotificationServer();
    }

    /* access modifiers changed from: private */
    public int getTimeout() {
        int timeout = this.mTimeout;
        if (timeout > 600000) {
            timeout = 600000;
        }
        this.mTimeout *= 2;
        return timeout;
    }

    private void resetTimeout() {
        this.mTimeout = 30000;
    }

    public void onConnectionOK(int connectionId) {
        switch (connectionId) {
            case 0:
                AppLogger.d("onConnectionOK, sendAuthPacket()");
                sendAuthPacket();
                return;
            case 1:
                AppLogger.d("onConnectionOK, requestNotification()");
                requestNotification();
                schedulePingAlarms();
                resetTimeout();
                return;
            case 2:
                AppLogger.d("onConnectionOK, sendRegionPacket()");
                sendRegionPacket();
                return;
            default:
                return;
        }
    }

    public void onUnableToConnect(int connectionId) {
        switch (connectionId) {
            case 0:
            case 1:
            case 2:
                if (DeviceUtil.isConnectedToNetwork(this.mContext)) {
                    scheduleWakeConnect(getTimeout());
                    return;
                } else {
                    notifyInternetDisconnected();
                    return;
                }
            default:
                return;
        }
    }

    public void onPacketFailed(TCPPacket packet) {
        if (DeviceUtil.isConnectedToNetwork(this.mContext)) {
            scheduleWakeConnect(getTimeout());
        } else {
            notifyInternetDisconnected();
        }
    }

    public void onResponseArrived(TCPPacket packet) {
        PacketRouter.route(packet);
    }

    public void onConnectionDropped(int connectionId) {
        switch (connectionId) {
            case 0:
            case 1:
            case 2:
                if (DeviceUtil.isConnectedToNetwork(this.mContext)) {
                    scheduleWakeConnect(getTimeout());
                    return;
                } else {
                    notifyInternetDisconnected();
                    return;
                }
            default:
                return;
        }
    }

    private void sendRegionPacket() {
        this.mNetworkRequestHandler.sendPacket(TCPPacketFactory.newRegionRequestPacket());
    }

    private void sendAuthPacket() {
        this.mNetworkRequestHandler.sendPacket(TCPPacketFactory.newAuthRequestPacket());
    }

    private void requestNotification() {
        this.mNetworkRequestHandler.sendPacket(TCPPacketFactory.newConnectionRequestPacket(LocalStorage.getConnectionId()));
    }

    /* access modifiers changed from: private */
    public boolean isStateValid() {
        return (this.mNetworkThread == null || this.mNetworkRequestHandler == null) ? false : true;
    }

    private void registerNotifications() {
        GNotificationService.getBus().register(CONSTANT.BUS.WAKE_CONNECT, this.mOnWakeConnect);
        GNotificationService.getBus().register(CONSTANT.BUS.SHORT_PING, this.mOnShortPing);
        GNotificationService.getBus().register(CONSTANT.BUS.LONG_PING, this.mOnLongPing);
        GNotificationService.getBus().register(CONSTANT.BUS.CONNECT_NOTIFICATION_SERVER, this.mConnectToLiveServer);
        GNotificationService.getBus().register(CONSTANT.BUS.ACK_PUSH_MSG, this.mAckPushMessage);
        GNotificationService.getBus().register(CONSTANT.BUS.PERFORM_PING, this.mPerformPing);
        GNotificationService.getBus().register(CONSTANT.BUS.CONNECT_AUTHENTICATION_SERVER, this.mConnectToAuthServer);
        GNotificationService.getBus().register(CONSTANT.BUS.RECONNECT_WHEN_INVALID_GIP_RECEIVED, this.mScheduleReconnectWhenInvalidGIPReceived);
    }

    private void schedulePingAlarms() {
        AppLogger.i("SCHEDULE_PING_ALARM");
        AlarmUtil.scheduleShortPing(this.mContext);
        AlarmUtil.scheduleLongPing(this.mContext);
    }

    /* access modifiers changed from: private */
    public void scheduleWakeConnect(int delayMs) {
        AppLogger.i("SCHEDULE_WAKE_CONNECT : " + delayMs);
        AppLogger.f("SCHEDULE_WAKE_CONNECT : " + delayMs);
        AlarmUtil.cancelShortPing(this.mContext);
        AlarmUtil.cancelLongPing(this.mContext);
        AlarmUtil.scheduleWakeConnect(this.mContext, delayMs);
    }

    /* access modifiers changed from: private */
    public void notifyInternetDisconnected() {
        AppLogger.i("NOTIFY_INTERNET_DISCONNECTED");
        AppLogger.f("NOTIFY_INTERNET_DISCONNECTED");
        AlarmUtil.cancelShortPing(this.mContext);
        AlarmUtil.cancelLongPing(this.mContext);
        GNotificationService.getBroadcastManager().registerNetworkChangeReceiver();
    }

    public void setupConnection() {
        scheduleWakeConnect(10000);
    }

    public void cancelPendingAlarms() {
        AlarmUtil.cancelLongPing(this.mContext);
        AlarmUtil.cancelShortPing(this.mContext);
        AlarmUtil.cancelWakeConnect(this.mContext);
    }

    public void destroy() {
        GNotificationService.getBus().clearAll();
        this.mServiceHandler = null;
        if (this.mNetworkThread != null) {
            this.mNetworkThread.quit();
        }
    }
}
