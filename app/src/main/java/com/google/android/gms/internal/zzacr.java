package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.ads.internal.zzv;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.zzn;

@zzzn
public final class zzacr extends zzacz {
    private final Context mContext;
    private final Object mLock;
    private final zzacs zzWq;
    private final zzaje zztW;

    public zzacr(Context context, zzv zzv, zzuq zzuq, zzaje zzaje) {
        this(context, zzaje, new zzacs(context, zzv, zziv.zzdk(), zzuq, zzaje));
    }

    private zzacr(Context context, zzaje zzaje, zzacs zzacs) {
        this.mLock = new Object();
        this.mContext = context;
        this.zztW = zzaje;
        this.zzWq = zzacs;
    }

    public final void destroy() {
        zzh((IObjectWrapper) null);
    }

    public final String getMediationAdapterClassName() {
        String mediationAdapterClassName;
        synchronized (this.mLock) {
            mediationAdapterClassName = this.zzWq.getMediationAdapterClassName();
        }
        return mediationAdapterClassName;
    }

    public final boolean isLoaded() {
        boolean isLoaded;
        synchronized (this.mLock) {
            isLoaded = this.zzWq.isLoaded();
        }
        return isLoaded;
    }

    public final void pause() {
        zzf((IObjectWrapper) null);
    }

    public final void resume() {
        zzg((IObjectWrapper) null);
    }

    public final void setImmersiveMode(boolean z) {
        synchronized (this.mLock) {
            this.zzWq.setImmersiveMode(z);
        }
    }

    public final void setUserId(String str) {
        zzafr.zzaT("RewardedVideoAd.setUserId() is deprecated. Please do not call this method.");
    }

    public final void show() {
        synchronized (this.mLock) {
            this.zzWq.zzgP();
        }
    }

    public final void zza(zzadd zzadd) {
        synchronized (this.mLock) {
            this.zzWq.zza(zzadd);
        }
    }

    public final void zza(zzadj zzadj) {
        synchronized (this.mLock) {
            this.zzWq.zza(zzadj);
        }
    }

    public final void zzf(IObjectWrapper iObjectWrapper) {
        synchronized (this.mLock) {
            this.zzWq.pause();
        }
    }

    public final void zzg(IObjectWrapper iObjectWrapper) {
        synchronized (this.mLock) {
            Context context = iObjectWrapper == null ? null : (Context) zzn.zzE(iObjectWrapper);
            if (context != null) {
                try {
                    this.zzWq.onContextChanged(context);
                } catch (Exception e) {
                    zzafr.zzc("Unable to extract updated context.", e);
                }
            }
            this.zzWq.resume();
        }
    }

    public final void zzh(IObjectWrapper iObjectWrapper) {
        synchronized (this.mLock) {
            this.zzWq.destroy();
        }
    }
}
