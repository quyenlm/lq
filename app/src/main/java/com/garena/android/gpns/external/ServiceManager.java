package com.garena.android.gpns.external;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.text.TextUtils;
import com.garena.android.gpns.BaseService;
import com.garena.android.gpns.GNotificationService;
import com.garena.android.gpns.external.CLIENT_CONST;
import com.garena.android.gpns.strategy.ServiceLoader;
import com.garena.android.gpns.utility.AlarmUtil;
import com.garena.android.gpns.utility.AppLogger;

public class ServiceManager {
    /* access modifiers changed from: private */
    public Context mApplicationContext;
    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            AppLogger.d("Service Connected");
            Messenger unused = ServiceManager.this.mService = new Messenger(service);
            try {
                Message msg = Message.obtain((Handler) null, BaseService.MSG_REGISTER_CLIENT);
                msg.replyTo = ServiceManager.this.mMessenger;
                ServiceManager.this.mService.send(msg);
            } catch (RemoteException e) {
                AppLogger.e((Throwable) e);
            }
        }

        public void onServiceDisconnected(ComponentName className) {
            AppLogger.d("Service disconnected");
            Messenger unused = ServiceManager.this.mService = null;
        }
    };
    private boolean mIsBound;
    /* access modifiers changed from: private */
    public final Messenger mMessenger;
    /* access modifiers changed from: private */
    public String mPushNotificationToken = "";
    /* access modifiers changed from: private */
    public Messenger mService = null;
    private final Class<? extends BaseService> mServiceClass = GNotificationService.class;

    public ServiceManager(Context context) {
        this.mApplicationContext = context.getApplicationContext();
        this.mMessenger = new Messenger(new IncomingHandler(Looper.getMainLooper()));
    }

    public String getToken() {
        AppLogger.d("Starting service");
        new ServiceLoader(this.mApplicationContext, new ServiceLoader.ServiceStatusListener() {
            public void onServiceStarted() {
                ServiceManager.this.doStartService();
                ServiceManager.this.doBindService(new ComponentName(ServiceManager.this.mApplicationContext, GNotificationService.class));
            }

            public void onOtherServiceRunning(ComponentName componentName) {
                ServiceManager.this.doBindService(componentName);
            }
        }).loadService();
        if (TextUtils.isEmpty(this.mPushNotificationToken) || this.mPushNotificationToken.equals(String.valueOf(-1))) {
            synchronized (this) {
                try {
                    AppLogger.d("lock the worker thread");
                    wait();
                } catch (InterruptedException e) {
                    AppLogger.e((Throwable) e);
                }
            }
            AppLogger.d("worker thread freed");
        }
        return this.mPushNotificationToken;
    }

    public void stop() {
        doStopService();
    }

    /* access modifiers changed from: private */
    public void unbind() {
        doUnbindService();
    }

    /* access modifiers changed from: private */
    public void send(Message msg) throws RemoteException {
        AppLogger.d("intend to send message out " + this.mIsBound);
        if (this.mIsBound && this.mService != null) {
            msg.replyTo = this.mMessenger;
            this.mService.send(msg);
            AppLogger.d("message delivered");
        }
    }

    /* access modifiers changed from: private */
    public void doStartService() {
        this.mApplicationContext.startService(new Intent(this.mApplicationContext, this.mServiceClass));
    }

    private void doStopService() {
        this.mApplicationContext.stopService(new Intent(this.mApplicationContext, this.mServiceClass));
        AlarmUtil.cancelLongPing(this.mApplicationContext);
        AlarmUtil.cancelShortPing(this.mApplicationContext);
        AlarmUtil.cancelWakeConnect(this.mApplicationContext);
    }

    /* access modifiers changed from: private */
    public void doBindService(ComponentName componentName) {
        AppLogger.d("doBindService");
        if (!this.mIsBound) {
            this.mIsBound = true;
            Intent bindIntent = new Intent();
            bindIntent.setComponent(componentName);
            this.mApplicationContext.bindService(bindIntent, this.mConnection, 1);
        } else if (this.mService != null) {
            try {
                Message msg = Message.obtain((Handler) null, BaseService.MSG_REGISTER_CLIENT);
                msg.replyTo = this.mMessenger;
                this.mService.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private void doUnbindService() {
        if (this.mIsBound) {
            AppLogger.d("doUnbindService");
            if (this.mService != null) {
                try {
                    Message msg = Message.obtain((Handler) null, BaseService.MSG_UNREGISTER_CLIENT);
                    msg.replyTo = this.mMessenger;
                    this.mService.send(msg);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            this.mApplicationContext.unbindService(this.mConnection);
            this.mIsBound = false;
        }
    }

    private class IncomingHandler extends Handler {
        public IncomingHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    String unused = ServiceManager.this.mPushNotificationToken = ((Bundle) msg.obj).getString(CLIENT_CONST.PROTOCOL.KEY_REGISTRATION_ID);
                    AppLogger.d("Receive Notification Token " + ServiceManager.this.mPushNotificationToken);
                    if (ServiceManager.this.mPushNotificationToken.equals(String.valueOf(-1))) {
                        postDelayed(new Runnable() {
                            public void run() {
                                try {
                                    ServiceManager.this.send(Message.obtain((Handler) null, 0));
                                } catch (RemoteException e) {
                                    AppLogger.e((Throwable) e);
                                }
                            }
                        }, 3000);
                        return;
                    }
                    synchronized (ServiceManager.this) {
                        ServiceManager.this.notify();
                    }
                    ServiceManager.this.unbind();
                    return;
                case 2:
                    AppLogger.d("Receive connection ack, request service info");
                    try {
                        ServiceManager.this.send(Message.obtain((Handler) null, 0));
                        return;
                    } catch (RemoteException e) {
                        return;
                    }
                default:
                    return;
            }
        }
    }
}
