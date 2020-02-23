package com.google.android.gms.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.awareness.fence.FenceState;
import com.google.android.gms.awareness.fence.FenceStateMap;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.safeparcel.zze;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class zzbjf extends zza implements FenceStateMap {
    public static final Parcelable.Creator<zzbjf> CREATOR = new zzbjg();
    private Map<String, zzbjd> zzaLh = new HashMap();

    zzbjf(Bundle bundle) {
        if (bundle != null) {
            for (String str : bundle.keySet()) {
                this.zzaLh.put(str, (zzbjd) zze.zza(bundle.getByteArray(str), zzbjd.CREATOR));
            }
        }
    }

    public final Set<String> getFenceKeys() {
        return this.zzaLh.keySet();
    }

    public final /* synthetic */ FenceState getFenceState(String str) {
        if (this.zzaLh.containsKey(str)) {
            return this.zzaLh.get(str);
        }
        return null;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        Bundle bundle;
        int zze = zzd.zze(parcel);
        if (this.zzaLh == null) {
            bundle = null;
        } else {
            Bundle bundle2 = new Bundle();
            for (Map.Entry next : this.zzaLh.entrySet()) {
                bundle2.putByteArray((String) next.getKey(), zze.zza((zzbjd) next.getValue()));
            }
            bundle = bundle2;
        }
        zzd.zza(parcel, 2, bundle, false);
        zzd.zzI(parcel, zze);
    }
}
