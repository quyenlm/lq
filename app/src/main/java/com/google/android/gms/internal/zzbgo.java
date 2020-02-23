package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class zzbgo extends zza {
    public static final Parcelable.Creator<zzbgo> CREATOR = new zzbgr();
    private final HashMap<String, Map<String, zzbgj<?, ?>>> zzaIR;
    private final ArrayList<zzbgp> zzaIS = null;
    private final String zzaIT;
    private int zzaku;

    zzbgo(int i, ArrayList<zzbgp> arrayList, String str) {
        this.zzaku = i;
        HashMap<String, Map<String, zzbgj<?, ?>>> hashMap = new HashMap<>();
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            zzbgp zzbgp = arrayList.get(i2);
            hashMap.put(zzbgp.className, zzbgp.zzrS());
        }
        this.zzaIR = hashMap;
        this.zzaIT = (String) zzbo.zzu(str);
        zzrQ();
    }

    private final void zzrQ() {
        for (String str : this.zzaIR.keySet()) {
            Map map = this.zzaIR.get(str);
            for (String str2 : map.keySet()) {
                ((zzbgj) map.get(str2)).zza(this);
            }
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        for (String next : this.zzaIR.keySet()) {
            sb.append(next).append(":\n");
            Map map = this.zzaIR.get(next);
            for (String str : map.keySet()) {
                sb.append("  ").append(str).append(": ");
                sb.append(map.get(str));
            }
        }
        return sb.toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, this.zzaku);
        ArrayList arrayList = new ArrayList();
        for (String next : this.zzaIR.keySet()) {
            arrayList.add(new zzbgp(next, this.zzaIR.get(next)));
        }
        zzd.zzc(parcel, 2, arrayList, false);
        zzd.zza(parcel, 3, this.zzaIT, false);
        zzd.zzI(parcel, zze);
    }

    public final Map<String, zzbgj<?, ?>> zzcJ(String str) {
        return this.zzaIR.get(str);
    }

    public final String zzrR() {
        return this.zzaIT;
    }
}
