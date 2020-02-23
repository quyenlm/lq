package com.google.android.gms.internal;

import android.app.Activity;
import android.content.ComponentName;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.instantapps.ActivityCompat;

public final class zzcbk implements ActivityCompat {
    private final Activity zzbhp;

    public zzcbk(Activity activity) {
        this.zzbhp = activity;
    }

    public final ComponentName getCallingActivity() {
        zzcbp zzbf;
        ComponentName callingActivity = this.zzbhp.getCallingActivity();
        if (!(callingActivity == null || callingActivity.getPackageName() == null || !callingActivity.getPackageName().equals("com.google.android.instantapps.supervisor") || (zzbf = zzcbp.zzbf(this.zzbhp)) == null)) {
            try {
                ComponentName zzdu = zzbf.zzdu(callingActivity.getClassName());
                if (zzdu != null) {
                    return zzdu;
                }
            } catch (RemoteException e) {
                Log.e("IAActivityCompat", "Error getting calling activity", e);
            }
        }
        return callingActivity;
    }

    public final String getCallingPackage() {
        ComponentName callingActivity = getCallingActivity();
        if (callingActivity != null) {
            return callingActivity.getPackageName();
        }
        return null;
    }
}
