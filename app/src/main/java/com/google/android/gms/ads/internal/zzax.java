package com.google.android.gms.ads.internal;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.zzn;
import com.google.android.gms.internal.zzafr;
import com.google.android.gms.internal.zzahq;
import com.google.android.gms.internal.zzaje;
import com.google.android.gms.internal.zzko;
import com.google.android.gms.internal.zzmo;
import com.google.android.gms.internal.zzzn;

@zzzn
public final class zzax extends zzko {
    private static final Object zzuF = new Object();
    @Nullable
    private static zzax zzuG;
    /* access modifiers changed from: private */
    public final Context mContext;
    private final Object mLock = new Object();
    private boolean zzuH;
    private boolean zzuI;
    private float zzuJ = -1.0f;
    private zzaje zzuK;

    private zzax(Context context, zzaje zzaje) {
        this.mContext = context;
        this.zzuK = zzaje;
        this.zzuH = false;
    }

    public static zzax zza(Context context, zzaje zzaje) {
        zzax zzax;
        synchronized (zzuF) {
            if (zzuG == null) {
                zzuG = new zzax(context.getApplicationContext(), zzaje);
            }
            zzax = zzuG;
        }
        return zzax;
    }

    @Nullable
    public static zzax zzbe() {
        zzax zzax;
        synchronized (zzuF) {
            zzax = zzuG;
        }
        return zzax;
    }

    public final void initialize() {
        synchronized (zzuF) {
            if (this.zzuH) {
                zzafr.zzaT("Mobile ads is initialized already.");
                return;
            }
            this.zzuH = true;
            zzmo.initialize(this.mContext);
            zzbs.zzbD().zzd(this.mContext, this.zzuK);
            zzbs.zzbE().initialize(this.mContext);
        }
    }

    public final void setAppMuted(boolean z) {
        synchronized (this.mLock) {
            this.zzuI = z;
        }
    }

    public final void setAppVolume(float f) {
        synchronized (this.mLock) {
            this.zzuJ = f;
        }
    }

    public final void zzb(IObjectWrapper iObjectWrapper, String str) {
        if (iObjectWrapper == null) {
            zzafr.e("Wrapped context is null. Failed to open debug menu.");
            return;
        }
        Context context = (Context) zzn.zzE(iObjectWrapper);
        if (context == null) {
            zzafr.e("Context is null. Failed to open debug menu.");
            return;
        }
        zzahq zzahq = new zzahq(context);
        zzahq.setAdUnitId(str);
        zzahq.zzaO(this.zzuK.zzaP);
        zzahq.showDialog();
    }

    public final float zzbf() {
        float f;
        synchronized (this.mLock) {
            f = this.zzuJ;
        }
        return f;
    }

    public final boolean zzbg() {
        boolean z;
        synchronized (this.mLock) {
            z = this.zzuJ >= 0.0f;
        }
        return z;
    }

    public final boolean zzbh() {
        boolean z;
        synchronized (this.mLock) {
            z = this.zzuI;
        }
        return z;
    }

    public final void zzc(String str, IObjectWrapper iObjectWrapper) {
        zzay zzay;
        if (!TextUtils.isEmpty(str)) {
            zzmo.initialize(this.mContext);
            boolean booleanValue = ((Boolean) zzbs.zzbL().zzd(zzmo.zzFO)).booleanValue() | ((Boolean) zzbs.zzbL().zzd(zzmo.zzDH)).booleanValue();
            if (((Boolean) zzbs.zzbL().zzd(zzmo.zzDH)).booleanValue()) {
                booleanValue = true;
                zzay = new zzay(this, (Runnable) zzn.zzE(iObjectWrapper));
            } else {
                zzay = null;
            }
            if (booleanValue) {
                zzbs.zzbV().zza(this.mContext, this.zzuK, str, zzay);
            }
        }
    }

    public final void zzu(String str) {
        zzmo.initialize(this.mContext);
        if (!TextUtils.isEmpty(str)) {
            if (((Boolean) zzbs.zzbL().zzd(zzmo.zzFO)).booleanValue()) {
                zzbs.zzbV().zza(this.mContext, this.zzuK, str, (Runnable) null);
            }
        }
    }
}
