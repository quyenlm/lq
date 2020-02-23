package com.google.android.gms.auth.api.signin;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import com.google.android.gms.auth.api.signin.internal.zzv;

public final class RevocationBoundService extends Service {
    public final IBinder onBind(Intent intent) {
        if ("com.google.android.gms.auth.api.signin.RevocationBoundService.disconnect".equals(intent.getAction())) {
            if (Log.isLoggable("RevocationService", 2)) {
                Log.v("RevocationService", "RevocationBoundService handling disconnect.");
            }
            return new zzv(this);
        }
        String valueOf = String.valueOf(intent.getAction());
        Log.w("RevocationService", valueOf.length() != 0 ? "Unknown action sent to RevocationBoundService: ".concat(valueOf) : new String("Unknown action sent to RevocationBoundService: "));
        return null;
    }
}
