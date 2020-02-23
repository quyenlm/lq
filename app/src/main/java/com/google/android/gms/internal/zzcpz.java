package com.google.android.gms.internal;

import android.support.annotation.Nullable;
import android.support.v4.util.SimpleArrayMap;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.nearby.messages.MessageListener;
import com.google.android.gms.nearby.messages.StatusCallback;

public final class zzcpz {
    private final SimpleArrayMap<Object, zzcpn> zzbzJ = new SimpleArrayMap<>(1);

    private static boolean zza(zzcpn zzcpn) {
        return zzcpn != null && zzcpn.zzzX().zzoc();
    }

    @Nullable
    private final synchronized <C> C zzj(zzbdw<C> zzbdw) {
        C c;
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= this.zzbzJ.size()) {
                c = null;
                break;
            }
            C keyAt = this.zzbzJ.keyAt(i2);
            if (this.zzbzJ.get(keyAt).zzzX().equals(zzbdw)) {
                c = keyAt;
                break;
            }
            i = i2 + 1;
        }
        return c;
    }

    public final synchronized void clear() {
        this.zzbzJ.clear();
    }

    @Nullable
    public final synchronized <C> zzcpn<C> zzE(@Nullable C c) {
        zzcpn<C> zzcpn;
        if (c == null) {
            zzcpn = null;
        } else {
            zzcpn = this.zzbzJ.get(c);
            if (!zza(zzcpn)) {
                this.zzbzJ.remove(c);
                zzcpn = null;
            }
        }
        return zzcpn;
    }

    @Nullable
    public final synchronized <C> zzcpn<C> zzb(GoogleApiClient googleApiClient, @Nullable C c) {
        zzcpn<C> zzcpn;
        if (c == null) {
            zzcpn = null;
        } else {
            zzcpn = this.zzbzJ.get(c);
            if (!zza(zzcpn)) {
                zzbdw zzp = googleApiClient.zzp(c);
                if (c instanceof StatusCallback) {
                    zzcpn = new zzcpv(zzp);
                } else if (c instanceof MessageListener) {
                    zzcpn = new zzcpo(zzp);
                } else {
                    String valueOf = String.valueOf(c.getClass().getName());
                    throw new IllegalArgumentException(valueOf.length() != 0 ? "Unknown callback of type ".concat(valueOf) : new String("Unknown callback of type "));
                }
                this.zzbzJ.put(c, zzcpn);
            }
        }
        return zzcpn;
    }

    @Nullable
    public final synchronized <C> zzcpn<C> zzh(zzbdw<C> zzbdw) {
        return zzbdw == null ? null : zzE(zzj(zzbdw));
    }

    public final synchronized <C> void zzi(zzbdw<C> zzbdw) {
        zzbdw.clear();
        this.zzbzJ.remove(zzj(zzbdw));
    }
}
