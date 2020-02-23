package com.tencent.imsdk.line.friend;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import com.tencent.imsdk.intent.friend.IntentFriend;
import com.tencent.imsdk.tool.etc.DeviceUtils;
import com.tencent.imsdk.tool.etc.IMLogger;

public class LineFriend extends IntentFriend {
    private static final String LINE_ACTIVITY_NAME = "jp.naver.line.android.activity.selectchat.SelectChatActivity";
    private static final String LINE_ACTIVITY_NAME_LAUNCH = "jp.naver.line.android.activity.selectchat.SelectChatActivityLaunchActivity";
    private static final String LINE_PACKAGE_NAME = "jp.naver.line.android";

    public boolean initialize(Context context) {
        return super.initialize(context);
    }

    /* access modifiers changed from: protected */
    public boolean isAppInstalled() {
        return DeviceUtils.isAppInstalled(this.currentContext, "jp.naver.line.android");
    }

    private Intent createLineIntent(String activityName) {
        ComponentName cn = new ComponentName("jp.naver.line.android", activityName);
        Intent intent = new Intent();
        intent.setAction("android.intent.action.SEND");
        intent.setComponent(cn);
        return intent;
    }

    /* access modifiers changed from: protected */
    public Intent initIntent() {
        Intent intent = createLineIntent(LINE_ACTIVITY_NAME);
        if (checkLineActivityExist(intent)) {
            return intent;
        }
        IMLogger.w("no SelectChatActivity found, find SelectChatActivityLaunchActivity !!!");
        return createLineIntent(LINE_ACTIVITY_NAME_LAUNCH);
    }

    private boolean checkLineActivityExist(Intent intent) {
        if (this.currentContext.getPackageManager().queryIntentActivities(intent, 0).size() == 0) {
            return false;
        }
        return true;
    }
}
