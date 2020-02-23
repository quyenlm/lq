package com.google.android.gms.internal;

import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.zzbo;
import java.util.HashMap;
import java.util.Map;

public final class zzcwa {
    private zzcwa zzbIR;
    private Map<String, dp> zzbIS;

    public zzcwa() {
        this((zzcwa) null);
    }

    private zzcwa(@Nullable zzcwa zzcwa) {
        this.zzbIS = null;
        this.zzbIR = zzcwa;
    }

    public final boolean has(String str) {
        while (true) {
            if (this.zzbIS != null && this.zzbIS.containsKey(str)) {
                return true;
            }
            if (this.zzbIR == null) {
                return false;
            }
            this = this.zzbIR;
        }
    }

    public final void remove(String str) {
        while (true) {
            zzbo.zzae(this.has(str));
            if (this.zzbIS == null || !this.zzbIS.containsKey(str)) {
                this = this.zzbIR;
            } else {
                this.zzbIS.remove(str);
                return;
            }
        }
    }

    public final zzcwa zzCz() {
        return new zzcwa(this);
    }

    public final void zza(String str, dp<?> dpVar) {
        if (this.zzbIS == null) {
            this.zzbIS = new HashMap();
        }
        this.zzbIS.put(str, dpVar);
    }

    public final void zzb(String str, dp<?> dpVar) {
        while (true) {
            if (this.zzbIS != null && this.zzbIS.containsKey(str)) {
                this.zzbIS.put(str, dpVar);
                return;
            } else if (this.zzbIR == null) {
                String valueOf = String.valueOf(str);
                throw new IllegalStateException(valueOf.length() != 0 ? "Trying to modify a non existent symbol: ".concat(valueOf) : new String("Trying to modify a non existent symbol: "));
            } else {
                this = this.zzbIR;
            }
        }
    }

    public final dp<?> zzfK(String str) {
        while (true) {
            if (this.zzbIS != null && this.zzbIS.containsKey(str)) {
                return this.zzbIS.get(str);
            }
            if (this.zzbIR != null) {
                this = this.zzbIR;
            } else {
                String valueOf = String.valueOf(str);
                throw new IllegalStateException(valueOf.length() != 0 ? "Trying to get a non existent symbol: ".concat(valueOf) : new String("Trying to get a non existent symbol: "));
            }
        }
    }
}
