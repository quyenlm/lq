package com.google.android.gms.internal;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

final class zzaij<T> extends zzp<InputStream> {
    /* access modifiers changed from: private */
    public final zzv<T> zzaD;
    /* access modifiers changed from: private */
    public final zzaii<T> zzaaj;

    public zzaij(String str, zzaii<T> zzaii, zzv<T> zzv) {
        super(0, str, new zzaik(zzv, zzaii));
        this.zzaaj = zzaii;
        this.zzaD = zzv;
    }

    /* access modifiers changed from: protected */
    public final zzt<InputStream> zza(zzn zzn) {
        return zzt.zza(new ByteArrayInputStream(zzn.data), zzam.zzb(zzn));
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Object obj) {
        zzajm zza = zzagt.zza(new zzail(this, (InputStream) obj));
        zza.zzd(new zzaim(this, zza));
    }
}
