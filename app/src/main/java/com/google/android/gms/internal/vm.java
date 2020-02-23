package com.google.android.gms.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class vm {
    private final vt zzcgU;
    /* access modifiers changed from: private */
    public final xe zzcgV;

    public vm(vt vtVar) {
        this.zzcgU = vtVar;
        this.zzcgV = vtVar.zzIm();
    }

    private final void zza(List<vj> list, vl vlVar, List<vi> list2, List<qi> list3, xf xfVar) {
        ArrayList arrayList = new ArrayList();
        for (vi next : list2) {
            if (next.zzHZ().equals(vlVar)) {
                arrayList.add(next);
            }
        }
        Collections.sort(arrayList, new vn(this));
        ArrayList arrayList2 = arrayList;
        int size = arrayList2.size();
        int i = 0;
        while (i < size) {
            int i2 = i + 1;
            vi viVar = (vi) arrayList2.get(i);
            for (qi next2 : list3) {
                if (next2.zza(vlVar)) {
                    list.add(next2.zza((viVar.zzHZ().equals(vl.VALUE) || viVar.zzHZ().equals(vl.CHILD_REMOVED)) ? viVar : viVar.zzg(xfVar.zza(viVar.zzHY(), viVar.zzHW().zzFn(), this.zzcgV)), this.zzcgU));
                }
            }
            i = i2;
        }
    }

    public final List<vj> zza(List<vi> list, xf xfVar, List<qi> list2) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (vi next : list) {
            if (next.zzHZ().equals(vl.CHILD_CHANGED)) {
                if (this.zzcgV.compare(new xl(wp.zzIJ(), next.zzIb().zzFn()), new xl(wp.zzIJ(), next.zzHW().zzFn())) != 0) {
                    arrayList2.add(vi.zzc(next.zzHY(), next.zzHW()));
                }
            }
        }
        zza(arrayList, vl.CHILD_REMOVED, list, list2, xfVar);
        zza(arrayList, vl.CHILD_ADDED, list, list2, xfVar);
        zza(arrayList, vl.CHILD_MOVED, arrayList2, list2, xfVar);
        zza(arrayList, vl.CHILD_CHANGED, list, list2, xfVar);
        zza(arrayList, vl.VALUE, list, list2, xfVar);
        return arrayList;
    }
}
