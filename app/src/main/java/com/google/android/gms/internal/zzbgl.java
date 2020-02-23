package com.google.android.gms.internal;

import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.Iterator;

public abstract class zzbgl extends zzbgi implements SafeParcelable {
    public final int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!getClass().isInstance(obj)) {
            return false;
        }
        zzbgi zzbgi = (zzbgi) obj;
        for (zzbgj next : zzrL().values()) {
            if (zza(next)) {
                if (!zzbgi.zza(next)) {
                    return false;
                }
                if (!zzb(next).equals(zzbgi.zzb(next))) {
                    return false;
                }
            } else if (zzbgi.zza(next)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int i = 0;
        Iterator<zzbgj<?, ?>> it = zzrL().values().iterator();
        while (true) {
            int i2 = i;
            if (!it.hasNext()) {
                return i2;
            }
            zzbgj next = it.next();
            if (zza(next)) {
                i = zzb(next).hashCode() + (i2 * 31);
            } else {
                i = i2;
            }
        }
    }

    public Object zzcH(String str) {
        return null;
    }

    public boolean zzcI(String str) {
        return false;
    }
}
