package com.google.android.gms.gcm;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.RequiresPermission;
import com.google.android.gms.iid.InstanceID;
import java.io.IOException;
import java.util.regex.Pattern;

public class GcmPubSub {
    private static GcmPubSub zzbfx;
    private static final Pattern zzbfz = Pattern.compile("/topics/[a-zA-Z0-9-_.~%]{1,900}");
    private InstanceID zzbfy;

    private GcmPubSub(Context context) {
        this.zzbfy = InstanceID.getInstance(context);
    }

    public static synchronized GcmPubSub getInstance(Context context) {
        GcmPubSub gcmPubSub;
        synchronized (GcmPubSub.class) {
            if (zzbfx == null) {
                zzbfx = new GcmPubSub(context);
            }
            gcmPubSub = zzbfx;
        }
        return gcmPubSub;
    }

    @RequiresPermission("com.google.android.c2dm.permission.RECEIVE")
    public void subscribe(String str, String str2, Bundle bundle) throws IOException {
        if (str == null || str.isEmpty()) {
            String valueOf = String.valueOf(str);
            throw new IllegalArgumentException(valueOf.length() != 0 ? "Invalid appInstanceToken: ".concat(valueOf) : new String("Invalid appInstanceToken: "));
        } else if (str2 == null || !zzbfz.matcher(str2).matches()) {
            String valueOf2 = String.valueOf(str2);
            throw new IllegalArgumentException(valueOf2.length() != 0 ? "Invalid topic name: ".concat(valueOf2) : new String("Invalid topic name: "));
        } else {
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putString("gcm.topic", str2);
            this.zzbfy.getToken(str, str2, bundle);
        }
    }

    @RequiresPermission("com.google.android.c2dm.permission.RECEIVE")
    public void unsubscribe(String str, String str2) throws IOException {
        Bundle bundle = new Bundle();
        bundle.putString("gcm.topic", str2);
        this.zzbfy.zzb(str, str2, bundle);
    }
}
