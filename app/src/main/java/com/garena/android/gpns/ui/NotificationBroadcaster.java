package com.garena.android.gpns.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.garena.android.gpns.external.ApplicationManager;
import com.garena.android.gpns.utility.CONSTANT;
import okio.ByteString;

public final class NotificationBroadcaster {
    public static void broadcast(Context context, Integer appId, ByteString data) {
        String packageName = ApplicationManager.getPackageForId(appId.intValue(), context);
        if (!TextUtils.isEmpty(packageName)) {
            Intent intent = new Intent();
            intent.setAction(CONSTANT.ACTION.ACTION_NOTIFICATION);
            intent.addCategory(packageName);
            Bundle bundle = new Bundle();
            bundle.putString("DATA", new String(data.toByteArray()));
            intent.putExtras(bundle);
            context.sendBroadcast(intent);
        }
    }

    private NotificationBroadcaster() {
    }
}
