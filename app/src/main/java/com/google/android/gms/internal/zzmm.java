package com.google.android.gms.internal;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.ConditionVariable;
import android.support.annotation.Nullable;
import com.google.android.gms.ads.internal.zzbs;
import com.google.android.gms.common.zzo;
import com.tencent.imsdk.expansion.downloader.Constants;

@zzzn
public final class zzmm {
    private final Object mLock = new Object();
    private final ConditionVariable zzBS = new ConditionVariable();
    /* access modifiers changed from: private */
    @Nullable
    public SharedPreferences zzBT = null;
    private volatile boolean zzuH = false;

    public final void initialize(Context context) {
        if (!this.zzuH) {
            synchronized (this.mLock) {
                if (!this.zzuH) {
                    try {
                        Context remoteContext = zzo.getRemoteContext(context);
                        if (remoteContext != null || context == null) {
                            context = remoteContext;
                        } else {
                            Context applicationContext = context.getApplicationContext();
                            if (applicationContext != null) {
                                context = applicationContext;
                            }
                        }
                        if (context != null) {
                            zzbs.zzbJ();
                            this.zzBT = context.getSharedPreferences("google_ads_flags", 0);
                            this.zzuH = true;
                            this.zzBS.open();
                        }
                    } finally {
                        this.zzBS.open();
                    }
                }
            }
        }
    }

    public final <T> T zzd(zzme<T> zzme) {
        if (!this.zzBS.block(Constants.ACTIVE_THREAD_WATCHDOG)) {
            throw new IllegalStateException("Flags.initialize() was not called!");
        }
        if (!this.zzuH || this.zzBT == null) {
            synchronized (this.mLock) {
                if (!this.zzuH || this.zzBT == null) {
                    T zzdI = zzme.zzdI();
                    return zzdI;
                }
            }
        }
        return zzait.zzb(new zzmn(this, zzme));
    }
}
