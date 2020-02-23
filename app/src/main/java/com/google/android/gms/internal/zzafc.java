package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;

@zzzn
public final class zzafc implements zzafe {
    public final zzajm<String> zze(zzaae zzaae) {
        return new zzajh(zzaae.zzSB);
    }

    public final zzajm<AdvertisingIdClient.Info> zzz(Context context) {
        zzajg zzajg = new zzajg();
        zzji.zzds();
        if (zzaiy.zzY(context)) {
            zzagt.zza((Runnable) new zzafd(this, context, zzajg));
        }
        return zzajg;
    }
}
