package com.garena.android.msdk.push.gcm;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.beetalk.sdk.helper.BBLogger;
import com.garena.android.BaseTokenUpdateReceiver;
import com.garena.android.DefaultNotificationReceiver;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class FCMNotificationReceiver extends FirebaseMessagingService {
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String title;
        String message;
        Map<String, String> data = remoteMessage.getData();
        RemoteMessage.Notification notif = remoteMessage.getNotification();
        if (data.containsKey("content") || data.containsKey(DefaultNotificationReceiver.KEY_PARAMETER)) {
            BBLogger.i("received FCM message, contents data JSON: %s", new JSONObject(data).toString());
            title = data.get(DefaultNotificationReceiver.KEY_PARAMETER);
            message = data.get("content");
        } else if (notif == null) {
            return;
        } else {
            if (!TextUtils.isEmpty(notif.getTitle()) || !TextUtils.isEmpty(notif.getBody())) {
                BBLogger.i("received FCM message, notification title: %s, body: %s", notif.getTitle(), notif.getBody());
                title = notif.getTitle();
                message = notif.getBody();
            } else {
                return;
            }
        }
        BBLogger.i("received FCM message, relaying to " + DefaultNotificationReceiver.class.getSimpleName(), new Object[0]);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("content", message);
            jsonObject.put(DefaultNotificationReceiver.KEY_PARAMETER, title);
        } catch (JSONException e) {
            BBLogger.e(e);
        }
        Bundle extra = new Bundle();
        extra.putString("DATA", jsonObject.toString());
        Intent intent = new Intent(this, DefaultNotificationReceiver.class);
        intent.putExtras(extra);
        sendBroadcast(intent);
    }

    public void onNewToken(String s) {
        super.onNewToken(s);
        BBLogger.d("FCM received new token: " + s, new Object[0]);
        Intent intent = new Intent(BaseTokenUpdateReceiver.ACTION_TOKEN_UPDATE);
        int resId = getResources().getIdentifier("is_using_fcm", "bool", getPackageName());
        intent.putExtra(BaseTokenUpdateReceiver.EXTRA_TOKEN_TYPE, (resId == 0 || !getResources().getBoolean(resId)) ? 3 : 4);
        intent.putExtra("token", s);
        sendBroadcast(intent, getPackageName() + BaseTokenUpdateReceiver.PERMISSION_SUFFIX);
    }
}
