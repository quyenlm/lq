package com.garena.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.beetalk.sdk.helper.BBLogger;
import com.beetalk.sdk.helper.Helper;
import com.garena.android.push.NotificationData;
import java.util.Random;
import org.json.JSONException;
import org.json.JSONObject;

public class DefaultNotificationReceiver extends BroadcastReceiver {
    public static final String KEY_CONTENT = "content";
    public static final String KEY_DATA = "DATA";
    public static final String KEY_PARAMETER = "parameter";
    public static final String NOTIFY_ID = "NOTIFY_ID";

    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        String data = extras.getString("DATA");
        BBLogger.d("Notification received, data: %s", data);
        String title = "";
        String text = "";
        try {
            JSONObject object = new JSONObject(data);
            if (object.has(KEY_PARAMETER)) {
                title = object.getString(KEY_PARAMETER);
            }
            if (object.has("content")) {
                text = object.getString("content");
            }
        } catch (JSONException e) {
            try {
                JSONObject object2 = new JSONObject(data.replace("': u'", "': '").replace("', u'", "', '").replace("{u'", "{'"));
                if (object2.has(KEY_PARAMETER)) {
                    title = object2.getString(KEY_PARAMETER);
                }
                if (object2.has("content")) {
                    text = object2.getString("content");
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
        NotificationData.NotificationBuilder builder = new NotificationData.NotificationBuilder();
        builder.setMessage(text).setTitle(title).setPackageName(context.getApplicationContext().getPackageName());
        if (extras.containsKey(NOTIFY_ID)) {
            builder.setNotificationId(extras.getInt(NOTIFY_ID));
        } else {
            builder.setNotificationId((Math.abs(new Random().nextInt() * 1000) % 10000) + Integer.valueOf(Helper.getPushAppId(context)).intValue());
        }
        builder.build().queueNotification(context);
        BBLogger.d("Notification queued: %s", intent);
    }
}
