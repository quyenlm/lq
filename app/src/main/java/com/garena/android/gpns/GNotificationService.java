package com.garena.android.gpns;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.garena.android.gpns.external.CLIENT_CONST;
import com.garena.android.gpns.logic.ServiceController;
import com.garena.android.gpns.network.PacketRouter;
import com.garena.android.gpns.storage.LocalStorage;
import com.garena.android.gpns.utility.AppLogger;
import com.garena.android.gpns.utility.DeviceUtil;

public class GNotificationService extends BaseService {
    public static final String INTENT_LOCAL_ACTION = "com.garena.android.gpns.local";
    public static final String INTENT_LOCAL_ACTION_COMMAND = "command";
    public static boolean IS_RUNNING = false;
    public static final int LOCAL_ACTION_KILL = 1;
    private static ServiceController mServiceController;
    private long deviceId = -1;

    public void onStartService() {
        PacketRouter.registerProcessors();
        this.deviceId = DeviceUtil.generateDeviceId(mContext);
        AppLogger.d("DEVICE_ID : " + this.deviceId);
        if (mServiceController != null) {
            mServiceController.destroy();
        }
        mServiceController = new ServiceController(mContext);
        if (DeviceUtil.isConnectedToNetwork(mContext)) {
            mServiceController.setupConnection();
        } else {
            mBroadcastManager.registerNetworkChangeReceiver();
        }
        mBroadcastManager.registerLocalHandler(new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                if (intent.getIntExtra("command", 0) == 1) {
                    AppLogger.d("local action to kill:" + context.getPackageName());
                    GNotificationService.this.selfDestruct();
                }
            }
        });
        AppLogger.d("Current Service Version: 4");
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        IS_RUNNING = true;
        return super.onStartCommand(intent, flags, startId);
    }

    /* access modifiers changed from: protected */
    public void registerReceivers() {
    }

    /* access modifiers changed from: protected */
    public void unregisterReceivers() {
        mBroadcastManager.unregisterNetworkChangeReceiver();
        mBroadcastManager.unregisterLocalHandler();
    }

    public void onDestroy() {
        super.onDestroy();
        onDestroyService();
    }

    private void onDestroyService() {
        mServiceController.destroy();
        mServiceController = null;
        IS_RUNNING = false;
    }

    public void onReceiveMessage(Message msg) {
        switch (msg.what) {
            case 0:
                Bundle data = new Bundle();
                data.putString(CLIENT_CONST.PROTOCOL.KEY_REGISTRATION_ID, String.valueOf(LocalStorage.getConnectionId()));
                data.putInt(CLIENT_CONST.PROTOCOL.KEY_SERVICE_VERSION, 4);
                data.putBoolean(CLIENT_CONST.PROTOCOL.KEY_IS_RUNNING, IS_RUNNING);
                Message reply = Message.obtain((Handler) null, 1, data);
                AppLogger.d("generate the reply " + msg.replyTo + "  device:" + String.valueOf(LocalStorage.getConnectionId()));
                send(msg.replyTo, reply);
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: protected */
    public void selfDestruct() {
        mServiceController.cancelPendingAlarms();
        stopSelf();
    }
}
