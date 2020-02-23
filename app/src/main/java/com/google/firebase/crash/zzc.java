package com.google.firebase.crash;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.util.Log;
import com.google.android.gms.common.util.zzg;
import com.google.android.gms.internal.mj;
import com.google.android.gms.internal.ml;
import com.google.android.gms.internal.mn;
import com.google.android.gms.internal.mq;
import com.google.android.gms.internal.zzcaf;
import com.google.firebase.FirebaseApp;

public final class zzc {
    /* access modifiers changed from: private */
    public final Context mContext;
    /* access modifiers changed from: private */
    public final FirebaseApp zzbVZ;
    /* access modifiers changed from: private */
    public String zzbYc = null;

    zzc(@NonNull FirebaseApp firebaseApp, @Nullable String str) {
        this.mContext = firebaseApp.getApplicationContext();
        this.zzbVZ = firebaseApp;
    }

    @VisibleForTesting
    public final mj zzFi() {
        mj mjVar;
        mq.initialize(this.mContext);
        if (((Boolean) zzcaf.zzuc().zzb(mq.zzbYp)).booleanValue()) {
            try {
                ml.zzFj().zzav(this.mContext);
                mjVar = ml.zzFj().zzFk();
                try {
                    String valueOf = String.valueOf(ml.zzFj());
                    Log.i("FirebaseCrash", new StringBuilder(String.valueOf(valueOf).length() + 33).append("FirebaseCrash reporting loaded - ").append(valueOf).toString());
                    return mjVar;
                } catch (mn e) {
                    e = e;
                }
            } catch (mn e2) {
                e = e2;
                mjVar = null;
                Log.e("FirebaseCrash", "Failed to load crash reporting", e);
                zzg.zza(this.mContext, e);
                return mjVar;
            }
        } else {
            Log.w("FirebaseCrash", "Crash reporting is disabled");
            return null;
        }
    }
}
