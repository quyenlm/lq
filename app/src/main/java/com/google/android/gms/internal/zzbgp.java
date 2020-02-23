package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class zzbgp extends zza {
    public static final Parcelable.Creator<zzbgp> CREATOR = new zzbgs();
    final String className;
    private int versionCode;
    private ArrayList<zzbgq> zzaIU;

    zzbgp(int i, String str, ArrayList<zzbgq> arrayList) {
        this.versionCode = i;
        this.className = str;
        this.zzaIU = arrayList;
    }

    zzbgp(String str, Map<String, zzbgj<?, ?>> map) {
        ArrayList<zzbgq> arrayList;
        this.versionCode = 1;
        this.className = str;
        if (map == null) {
            arrayList = null;
        } else {
            ArrayList<zzbgq> arrayList2 = new ArrayList<>();
            for (String next : map.keySet()) {
                arrayList2.add(new zzbgq(next, map.get(next)));
            }
            arrayList = arrayList2;
        }
        this.zzaIU = arrayList;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, this.versionCode);
        zzd.zza(parcel, 2, this.className, false);
        zzd.zzc(parcel, 3, this.zzaIU, false);
        zzd.zzI(parcel, zze);
    }

    /* access modifiers changed from: package-private */
    public final HashMap<String, zzbgj<?, ?>> zzrS() {
        HashMap<String, zzbgj<?, ?>> hashMap = new HashMap<>();
        int size = this.zzaIU.size();
        for (int i = 0; i < size; i++) {
            zzbgq zzbgq = this.zzaIU.get(i);
            hashMap.put(zzbgq.key, zzbgq.zzaIV);
        }
        return hashMap;
    }
}
