package com.tencent.imsdk.whatsapp.share;

import android.content.Intent;
import com.tencent.imsdk.intent.share.IntentShare;
import com.tencent.imsdk.sns.base.IMShareContent;
import com.tencent.imsdk.tool.etc.DeviceUtils;

public class WhatsAppShare extends IntentShare {
    private static final String WHATSAPP_PACKAGE_NAME = "com.whatsapp";

    /* access modifiers changed from: protected */
    public Intent initIntent() {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setPackage(WHATSAPP_PACKAGE_NAME);
        return intent;
    }

    /* access modifiers changed from: protected */
    public boolean isAppInstalled() {
        return DeviceUtils.isAppInstalled(this.currentContext, WHATSAPP_PACKAGE_NAME);
    }

    /* access modifiers changed from: protected */
    public Intent prepareImageIntent(Intent intent, IMShareContent info) {
        if (!(info == null || info.content == null)) {
            intent.putExtra("android.intent.extra.TEXT", info.content);
        }
        return intent;
    }
}
