package com.google.android.gms.awareness.fence;

import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.internal.zzbiy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public abstract class AwarenessFence extends zza {
    protected AwarenessFence() {
    }

    public static AwarenessFence and(Collection<AwarenessFence> collection) {
        zzbo.zzaf(collection != null && !collection.isEmpty());
        return zzbiy.zze(zzd(collection));
    }

    public static AwarenessFence and(AwarenessFence... awarenessFenceArr) {
        zzbo.zzaf(awarenessFenceArr != null && awarenessFenceArr.length > 0);
        return zzbiy.zze(zza(awarenessFenceArr));
    }

    public static AwarenessFence not(AwarenessFence awarenessFence) {
        zzbo.zzu(awarenessFence);
        return zzbiy.zza((zzbiy) awarenessFence);
    }

    public static AwarenessFence or(Collection<AwarenessFence> collection) {
        zzbo.zzaf(collection != null && !collection.isEmpty());
        return zzbiy.zzf(zzd(collection));
    }

    public static AwarenessFence or(AwarenessFence... awarenessFenceArr) {
        zzbo.zzaf(awarenessFenceArr != null && awarenessFenceArr.length > 0);
        return zzbiy.zzf(zza(awarenessFenceArr));
    }

    private static ArrayList<zzbiy> zza(AwarenessFence[] awarenessFenceArr) {
        ArrayList<zzbiy> arrayList = new ArrayList<>(awarenessFenceArr.length);
        for (zzbiy add : awarenessFenceArr) {
            arrayList.add(add);
        }
        return arrayList;
    }

    private static ArrayList<zzbiy> zzd(Collection<AwarenessFence> collection) {
        ArrayList<zzbiy> arrayList = new ArrayList<>(collection.size());
        Iterator<AwarenessFence> it = collection.iterator();
        while (it.hasNext()) {
            arrayList.add((zzbiy) it.next());
        }
        return arrayList;
    }
}
