package com.tencent.imsdk.system.share;

import android.content.Context;
import android.content.Intent;
import com.tencent.imsdk.intent.share.IntentShare;
import com.tencent.imsdk.sns.base.IMShareContent;

public class SystemShare extends IntentShare {
    public boolean initialize(Context context) {
        return super.initialize(context);
    }

    /* access modifiers changed from: protected */
    public Intent initIntent() {
        return new Intent("android.intent.action.SEND");
    }

    /* access modifiers changed from: protected */
    public Intent prepareTextIntent(Intent intent, IMShareContent info) {
        return Intent.createChooser(intent, info.title == null ? "" : info.title);
    }

    /* access modifiers changed from: protected */
    public Intent prepareImageIntent(Intent intent, IMShareContent info) {
        return Intent.createChooser(intent, info.title == null ? "" : info.title);
    }
}
