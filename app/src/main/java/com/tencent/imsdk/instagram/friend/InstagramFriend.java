package com.tencent.imsdk.instagram.friend;

import android.content.Intent;
import com.tencent.imsdk.intent.friend.IntentFriend;
import com.tencent.imsdk.tool.etc.DeviceUtils;

public class InstagramFriend extends IntentFriend {
    private static final String INSTAGRAM_PACKAGE_NAME = "com.instagram.android";

    /* access modifiers changed from: protected */
    public Intent initIntent() {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setPackage(INSTAGRAM_PACKAGE_NAME);
        return intent;
    }

    /* access modifiers changed from: protected */
    public boolean isAppInstalled() {
        return DeviceUtils.isAppInstalled(this.currentContext, INSTAGRAM_PACKAGE_NAME);
    }
}
