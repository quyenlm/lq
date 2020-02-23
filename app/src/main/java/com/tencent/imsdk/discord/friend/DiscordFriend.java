package com.tencent.imsdk.discord.friend;

import android.content.Context;
import android.content.Intent;
import com.tencent.imsdk.intent.friend.IntentFriend;
import com.tencent.imsdk.tool.etc.DeviceUtils;

public class DiscordFriend extends IntentFriend {
    private static final String DISCORD_APP_PACKAGE = "com.discord";

    public boolean initialize(Context context) {
        return super.initialize(context);
    }

    /* access modifiers changed from: protected */
    public Intent initIntent() {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setPackage(DISCORD_APP_PACKAGE);
        return intent;
    }

    /* access modifiers changed from: protected */
    public boolean isAppInstalled() {
        return DeviceUtils.isAppInstalled(this.currentContext, DISCORD_APP_PACKAGE);
    }
}
