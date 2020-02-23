package com.garena.android.gpns;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Process;
import android.os.RemoteException;
import com.garena.android.gpns.logic.BroadcastManager;
import com.garena.android.gpns.notification.NotificationBus;
import com.garena.android.gpns.utility.AppLogger;

public abstract class BaseService extends Service {
    public static final int MSG_REGISTER_CLIENT = 9991;
    public static final int MSG_SELFDESTRUCT = 9993;
    public static final int MSG_UNREGISTER_CLIENT = 9992;
    protected static BroadcastManager mBroadcastManager;
    protected static NotificationBus mBus;
    protected static Context mContext;
    final Messenger mMessenger = new Messenger(new IncomingHandler());

    public abstract void onReceiveMessage(Message message);

    public abstract void onStartService();

    /* access modifiers changed from: protected */
    public abstract void registerReceivers();

    /* access modifiers changed from: protected */
    public abstract void selfDestruct();

    /* access modifiers changed from: protected */
    public abstract void unregisterReceivers();

    public void onCreate() {
        super.onCreate();
        AppLogger.d("======== OnCreated " + getPackageName() + " ============");
        mContext = this;
        mBus = new NotificationBus();
        mBroadcastManager = new BroadcastManager(mContext);
        onStartService();
        registerReceivers();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        return 1;
    }

    public IBinder onBind(Intent intent) {
        return this.mMessenger.getBinder();
    }

    public void onDestroy() {
        super.onDestroy();
        unregisterReceivers();
        AppLogger.d("======== OnDestroy " + getPackageName() + " ============");
        Process.killProcess(Process.myPid());
    }

    /* access modifiers changed from: protected */
    public void send(Messenger client, Message msg) {
        try {
            client.send(msg);
        } catch (RemoteException e) {
            AppLogger.e((Throwable) e);
        }
    }

    public static Context getContext() {
        return mContext;
    }

    public static NotificationBus getBus() {
        return mBus;
    }

    public static BroadcastManager getBroadcastManager() {
        return mBroadcastManager;
    }

    private class IncomingHandler extends Handler {
        private IncomingHandler() {
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case BaseService.MSG_REGISTER_CLIENT /*9991*/:
                    BaseService.this.send(msg.replyTo, Message.obtain((Handler) null, 2));
                    return;
                case BaseService.MSG_UNREGISTER_CLIENT /*9992*/:
                    return;
                case BaseService.MSG_SELFDESTRUCT /*9993*/:
                    AppLogger.d("MSG_SELFDESTRUCT");
                    BaseService.this.selfDestruct();
                    BaseService.this.send(msg.replyTo, Message.obtain((Handler) null, 3));
                    return;
                default:
                    BaseService.this.onReceiveMessage(msg);
                    return;
            }
        }
    }
}
