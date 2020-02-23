package com.google.android.gms.internal;

import com.google.android.gms.internal.adj;
import java.io.IOException;
import java.util.List;

public final class adk<M extends adj<M>, T> {
    public final int tag;
    private int type = 11;
    protected final Class<T> zzcjG;
    protected final boolean zzcsp;

    private adk(int i, Class<T> cls, int i2, boolean z) {
        this.zzcjG = cls;
        this.tag = i2;
        this.zzcsp = false;
    }

    public static <M extends adj<M>, T extends adp> adk<M, T> zza(int i, Class<T> cls, long j) {
        return new adk<>(11, cls, (int) j, false);
    }

    private final Object zzb(adg adg) {
        Class<T> cls = this.zzcjG;
        try {
            switch (this.type) {
                case 10:
                    adp adp = (adp) cls.newInstance();
                    adg.zza(adp, this.tag >>> 3);
                    return adp;
                case 11:
                    adp adp2 = (adp) cls.newInstance();
                    adg.zza(adp2);
                    return adp2;
                default:
                    throw new IllegalArgumentException(new StringBuilder(24).append("Unknown type ").append(this.type).toString());
            }
        } catch (InstantiationException e) {
            String valueOf = String.valueOf(cls);
            throw new IllegalArgumentException(new StringBuilder(String.valueOf(valueOf).length() + 33).append("Error creating instance of class ").append(valueOf).toString(), e);
        } catch (IllegalAccessException e2) {
            String valueOf2 = String.valueOf(cls);
            throw new IllegalArgumentException(new StringBuilder(String.valueOf(valueOf2).length() + 33).append("Error creating instance of class ").append(valueOf2).toString(), e2);
        } catch (IOException e3) {
            throw new IllegalArgumentException("Error reading extension field", e3);
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof adk)) {
            return false;
        }
        adk adk = (adk) obj;
        return this.type == adk.type && this.zzcjG == adk.zzcjG && this.tag == adk.tag;
    }

    public final int hashCode() {
        return (((((this.type + 1147) * 31) + this.zzcjG.hashCode()) * 31) + this.tag) * 31;
    }

    /* access modifiers changed from: package-private */
    public final T zzX(List<adr> list) {
        if (list != null && !list.isEmpty()) {
            return this.zzcjG.cast(zzb(adg.zzH(list.get(list.size() - 1).zzbws)));
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public final void zza(Object obj, adh adh) {
        try {
            adh.zzcu(this.tag);
            switch (this.type) {
                case 10:
                    ((adp) obj).zza(adh);
                    adh.zzt(this.tag >>> 3, 4);
                    return;
                case 11:
                    adh.zzb((adp) obj);
                    return;
                default:
                    throw new IllegalArgumentException(new StringBuilder(24).append("Unknown type ").append(this.type).toString());
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        throw new IllegalStateException(e);
    }

    /* access modifiers changed from: protected */
    public final int zzav(Object obj) {
        int i = this.tag >>> 3;
        switch (this.type) {
            case 10:
                return (adh.zzct(i) << 1) + ((adp) obj).zzLV();
            case 11:
                return adh.zzb(i, (adp) obj);
            default:
                throw new IllegalArgumentException(new StringBuilder(24).append("Unknown type ").append(this.type).toString());
        }
    }
}
