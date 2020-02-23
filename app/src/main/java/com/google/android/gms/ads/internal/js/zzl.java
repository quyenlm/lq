package com.google.android.gms.ads.internal.js;

import android.content.Context;
import android.support.annotation.Nullable;
import com.google.android.gms.ads.internal.zzbs;
import com.google.android.gms.internal.zzagz;
import com.google.android.gms.internal.zzahz;
import com.google.android.gms.internal.zzaje;
import com.google.android.gms.internal.zzcu;
import com.google.android.gms.internal.zzzn;

@zzzn
public final class zzl {
    /* access modifiers changed from: private */
    public final Context mContext;
    /* access modifiers changed from: private */
    public final Object mLock;
    /* access modifiers changed from: private */
    public final String zzLa;
    /* access modifiers changed from: private */
    public zzahz<zza> zzLb;
    private zzahz<zza> zzLc;
    /* access modifiers changed from: private */
    @Nullable
    public zzac zzLd;
    /* access modifiers changed from: private */
    public int zzLe;
    /* access modifiers changed from: private */
    public final zzaje zztW;

    public zzl(Context context, zzaje zzaje, String str) {
        this.mLock = new Object();
        this.zzLe = 1;
        this.zzLa = str;
        this.mContext = context.getApplicationContext();
        this.zztW = zzaje;
        this.zzLb = new zzx();
        this.zzLc = new zzx();
    }

    public zzl(Context context, zzaje zzaje, String str, zzahz<zza> zzahz, zzahz<zza> zzahz2) {
        this(context, zzaje, str);
        this.zzLb = zzahz;
        this.zzLc = zzahz2;
    }

    /* access modifiers changed from: protected */
    public final zzac zza(@Nullable zzcu zzcu) {
        zzac zzac = new zzac(this.zzLc);
        zzbs.zzbz();
        zzagz.runOnUiThread(new zzm(this, zzcu, zzac));
        zzac.zza(new zzu(this, zzac), new zzv(this, zzac));
        return zzac;
    }

    public final zzy zzb(@Nullable zzcu zzcu) {
        zzy zzy;
        synchronized (this.mLock) {
            if (this.zzLd == null || this.zzLd.getStatus() == -1) {
                this.zzLe = 2;
                this.zzLd = zza(zzcu);
                zzy = this.zzLd.zzfa();
            } else if (this.zzLe == 0) {
                zzy = this.zzLd.zzfa();
            } else if (this.zzLe == 1) {
                this.zzLe = 2;
                zza(zzcu);
                zzy = this.zzLd.zzfa();
            } else {
                zzy = this.zzLe == 2 ? this.zzLd.zzfa() : this.zzLd.zzfa();
            }
        }
        return zzy;
    }
}
