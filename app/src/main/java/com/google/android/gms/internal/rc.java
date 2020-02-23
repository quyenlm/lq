package com.google.android.gms.internal;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.zzh;
import java.util.ArrayList;
import java.util.List;

final class rc implements pf {
    private /* synthetic */ qr zzccH;
    private /* synthetic */ qu zzcdA;
    private /* synthetic */ List zzcdH;
    private /* synthetic */ qu zzcdI;

    rc(qu quVar, qr qrVar, List list, qu quVar2) {
        this.zzcdA = quVar;
        this.zzccH = qrVar;
        this.zzcdH = list;
        this.zzcdI = quVar2;
    }

    public final void zzaa(String str, String str2) {
        DatabaseError zzac = qu.zzab(str, str2);
        this.zzcdA.zza("Transaction", this.zzccH, zzac);
        ArrayList arrayList = new ArrayList();
        if (zzac == null) {
            ArrayList arrayList2 = new ArrayList();
            for (rv rvVar : this.zzcdH) {
                int unused = rvVar.zzceb = rw.zzcem;
                arrayList.addAll(this.zzcdA.zzcdw.zza(rvVar.zzcef, false, false, (ys) this.zzcdA.zzcdj));
                arrayList2.add(new rd(this, rvVar, zzh.zza(zzh.zza(this.zzcdI, rvVar.zzbZf), xf.zzj(rvVar.zzcei))));
                this.zzcdA.zze((qi) new to(this.zzcdA, rvVar.zzcea, vt.zzM(rvVar.zzbZf)));
            }
            this.zzcdA.zzb((vb<List<rv>>) this.zzcdA.zzcdm.zzK(this.zzccH));
            this.zzcdA.zzHl();
            this.zzcdI.zzT(arrayList);
            for (int i = 0; i < arrayList2.size(); i++) {
                this.zzcdA.zzo((Runnable) arrayList2.get(i));
            }
            return;
        }
        if (zzac.getCode() == -1) {
            for (rv rvVar2 : this.zzcdH) {
                if (rvVar2.zzceb == rw.zzcen) {
                    int unused2 = rvVar2.zzceb = rw.zzceo;
                } else {
                    int unused3 = rvVar2.zzceb = rw.zzcek;
                }
            }
        } else {
            for (rv rvVar3 : this.zzcdH) {
                int unused4 = rvVar3.zzceb = rw.zzceo;
                DatabaseError unused5 = rvVar3.zzcee = zzac;
            }
        }
        qr unused6 = this.zzcdA.zzn(this.zzccH);
    }
}
