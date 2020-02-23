package com.google.firebase.messaging;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.zzl;
import java.util.regex.Pattern;

public class FirebaseMessaging {
    public static final String INSTANCE_ID_SCOPE = "FCM";
    private static final Pattern zzckW = Pattern.compile("[a-zA-Z0-9-_.~%]{1,900}");
    private static FirebaseMessaging zzckX;
    private final FirebaseInstanceId zzcku;

    private FirebaseMessaging(FirebaseInstanceId firebaseInstanceId) {
        this.zzcku = firebaseInstanceId;
    }

    public static synchronized FirebaseMessaging getInstance() {
        FirebaseMessaging firebaseMessaging;
        synchronized (FirebaseMessaging.class) {
            if (zzckX == null) {
                zzckX = new FirebaseMessaging(FirebaseInstanceId.getInstance());
            }
            firebaseMessaging = zzckX;
        }
        return firebaseMessaging;
    }

    public void send(RemoteMessage remoteMessage) {
        if (TextUtils.isEmpty(remoteMessage.getTo())) {
            throw new IllegalArgumentException("Missing 'to'");
        }
        Context applicationContext = FirebaseApp.getInstance().getApplicationContext();
        String zzbd = zzl.zzbd(applicationContext);
        if (zzbd == null) {
            Log.e("FirebaseMessaging", "Google Play services package is missing. Impossible to send message");
            return;
        }
        Intent intent = new Intent("com.google.android.gcm.intent.SEND");
        zzl.zzd(applicationContext, intent);
        intent.setPackage(zzbd);
        intent.putExtras(remoteMessage.mBundle);
        applicationContext.sendOrderedBroadcast(intent, "com.google.android.gtalkservice.permission.GTALK_SERVICE");
    }

    public void subscribeToTopic(String str) {
        if (str != null && str.startsWith("/topics/")) {
            Log.w("FirebaseMessaging", "Format /topics/topic-name is deprecated. Only 'topic-name' should be used in subscribeToTopic.");
            str = str.substring(8);
        }
        if (str == null || !zzckW.matcher(str).matches()) {
            String valueOf = String.valueOf("[a-zA-Z0-9-_.~%]{1,900}");
            throw new IllegalArgumentException(new StringBuilder(String.valueOf(str).length() + 55 + String.valueOf(valueOf).length()).append("Invalid topic name: ").append(str).append(" does not match the allowed format ").append(valueOf).toString());
        }
        FirebaseInstanceId firebaseInstanceId = this.zzcku;
        String valueOf2 = String.valueOf("S!");
        String valueOf3 = String.valueOf(str);
        firebaseInstanceId.zzhf(valueOf3.length() != 0 ? valueOf2.concat(valueOf3) : new String(valueOf2));
    }

    public void unsubscribeFromTopic(String str) {
        if (str != null && str.startsWith("/topics/")) {
            Log.w("FirebaseMessaging", "Format /topics/topic-name is deprecated. Only 'topic-name' should be used in unsubscribeFromTopic.");
            str = str.substring(8);
        }
        if (str == null || !zzckW.matcher(str).matches()) {
            String valueOf = String.valueOf("[a-zA-Z0-9-_.~%]{1,900}");
            throw new IllegalArgumentException(new StringBuilder(String.valueOf(str).length() + 55 + String.valueOf(valueOf).length()).append("Invalid topic name: ").append(str).append(" does not match the allowed format ").append(valueOf).toString());
        }
        FirebaseInstanceId firebaseInstanceId = this.zzcku;
        String valueOf2 = String.valueOf("U!");
        String valueOf3 = String.valueOf(str);
        firebaseInstanceId.zzhf(valueOf3.length() != 0 ? valueOf2.concat(valueOf3) : new String(valueOf2));
    }
}
