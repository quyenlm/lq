package com.garena.android.gpns.processor;

import android.content.Context;
import android.content.Intent;
import com.garena.android.gpnprotocol.gpush.GetGPidResponse;
import com.garena.android.gpns.GNotificationService;
import com.garena.android.gpns.notification.event.NotifyEvent;
import com.garena.android.gpns.storage.LocalStorage;
import com.garena.android.gpns.utility.AppLogger;
import com.garena.android.gpns.utility.CONSTANT;
import com.garena.android.gpns.utility.WireUtil;

public class PushIdResponseProcessor extends AbstractProcessor {
    public void process(byte[] data, int offset, int length) throws Exception {
        GetGPidResponse response = WireUtil.parseGetGPidResponse(data, offset, length);
        long newGPID = response.GPid.longValue();
        AppLogger.i("GetGPidResponse gPid: " + newGPID);
        if (newGPID != 0) {
            if (newGPID != -1) {
                broadcastGPIDChange(newGPID);
            }
            LocalStorage.saveConnectionId(newGPID);
            LocalStorage.saveConnectionAddress(response.ConnServerAddr);
            GNotificationService.getBus().fire(CONSTANT.BUS.CONNECT_NOTIFICATION_SERVER, (NotifyEvent) null);
            return;
        }
        GNotificationService.getBus().fire(CONSTANT.BUS.RECONNECT_WHEN_INVALID_GIP_RECEIVED, (NotifyEvent) null);
    }

    public int getCommand() {
        return 17;
    }

    private void broadcastGPIDChange(long gpid) {
        Context context = GNotificationService.getContext();
        if (context != null) {
            Intent intent = new Intent(CONSTANT.ACTION.ACTION_GPID_UPDATE);
            intent.putExtra(CONSTANT.INTENT_EXTRA.GPID_UPDATE_INTENT_EXTRA_GPID, gpid);
            context.sendBroadcast(intent);
        }
    }
}
