package com.google.android.gms.internal;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;

public final class zzcbf extends zzcbh {
    private Activity mActivity;
    private final int zzaBk;

    public zzcbf(int i, Activity activity) {
        this.zzaBk = i;
        this.mActivity = activity;
    }

    /* access modifiers changed from: private */
    public final void setActivity(Activity activity) {
        this.mActivity = null;
    }

    public final void zze(int i, Bundle bundle) {
        if (i == 1) {
            Intent intent = new Intent();
            intent.putExtras(bundle);
            PendingIntent createPendingResult = this.mActivity.createPendingResult(this.zzaBk, intent, 1073741824);
            if (createPendingResult != null) {
                try {
                    createPendingResult.send(1);
                } catch (PendingIntent.CanceledException e) {
                    Log.w("AddressClientImpl", "Exception settng pending result", e);
                }
            }
        } else {
            PendingIntent pendingIntent = null;
            if (bundle != null) {
                pendingIntent = (PendingIntent) bundle.getParcelable("com.google.android.gms.identity.intents.EXTRA_PENDING_INTENT");
            }
            ConnectionResult connectionResult = new ConnectionResult(i, pendingIntent);
            if (connectionResult.hasResolution()) {
                try {
                    connectionResult.startResolutionForResult(this.mActivity, this.zzaBk);
                } catch (IntentSender.SendIntentException e2) {
                    Log.w("AddressClientImpl", "Exception starting pending intent", e2);
                }
            } else {
                try {
                    PendingIntent createPendingResult2 = this.mActivity.createPendingResult(this.zzaBk, new Intent(), 1073741824);
                    if (createPendingResult2 != null) {
                        createPendingResult2.send(1);
                    }
                } catch (PendingIntent.CanceledException e3) {
                    Log.w("AddressClientImpl", "Exception setting pending result", e3);
                }
            }
        }
    }
}
