package com.garena.android.gpns.processor;

import android.text.TextUtils;
import com.garena.android.gpns.GNotificationService;
import com.garena.android.gpns.notification.event.NotifyEvent;
import com.garena.android.gpns.storage.LocalStorage;
import com.garena.android.gpns.utility.CONSTANT;
import com.garena.android.gpns.utility.WireUtil;

public class RegionResponseProcessor extends AbstractProcessor {
    public void process(byte[] data, int offset, int length) throws Exception {
        String region = WireUtil.parseGetRegionResponse(data, offset, length).Region;
        if (!TextUtils.isEmpty(region)) {
            LocalStorage.updateRegionRequestTime(System.currentTimeMillis());
            LocalStorage.setAuthServerAddress(region);
        }
        GNotificationService.getBus().fire(CONSTANT.BUS.CONNECT_AUTHENTICATION_SERVER, (NotifyEvent) null);
    }

    public int getCommand() {
        return 20;
    }
}
