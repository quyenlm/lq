package com.tencent.imsdk.system.friend;

import android.content.Context;
import android.content.Intent;
import com.tencent.imsdk.intent.friend.IntentFriend;
import com.tencent.imsdk.sns.base.IMFriendContent;

public class SystemFriend extends IntentFriend {
    public boolean initialize(Context context) {
        return super.initialize(context);
    }

    /* access modifiers changed from: protected */
    public Intent initIntent() {
        return new Intent("android.intent.action.SEND");
    }

    /* access modifiers changed from: protected */
    public Intent prepareTextIntent(Intent intent, IMFriendContent info) {
        return Intent.createChooser(intent, info.title == null ? "" : info.title);
    }

    /* access modifiers changed from: protected */
    public Intent prepareImageIntent(Intent intent, IMFriendContent info) {
        return Intent.createChooser(intent, info.title == null ? "" : info.title);
    }
}
