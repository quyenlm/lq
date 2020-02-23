package com.google.android.gms.internal;

import com.google.firebase.database.DatabaseError;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;

final class ss implements Callable<List<vk>> {
    private /* synthetic */ vt zzcdP;
    private /* synthetic */ so zzceR;
    private /* synthetic */ qi zzceU;
    private /* synthetic */ DatabaseError zzceV;

    ss(so soVar, vt vtVar, qi qiVar, DatabaseError databaseError) {
        this.zzceR = soVar;
        this.zzcdP = vtVar;
        this.zzceU = qiVar;
        this.zzceV = databaseError;
    }

    public final /* synthetic */ Object call() throws Exception {
        boolean z;
        qr zzFq = this.zzcdP.zzFq();
        sn snVar = (sn) this.zzceR.zzceG.zzJ(zzFq);
        Object arrayList = new ArrayList();
        if (snVar != null && (this.zzcdP.isDefault() || snVar.zzc(this.zzcdP))) {
            za<List<vt>, List<vk>> zza = snVar.zza(this.zzcdP, this.zzceU, this.zzceV);
            if (snVar.isEmpty()) {
                uv unused = this.zzceR.zzceG = this.zzceR.zzceG.zzI(zzFq);
            }
            List<vt> first = zza.getFirst();
            arrayList = zza.zzJG();
            boolean z2 = false;
            for (vt vtVar : first) {
                this.zzceR.zzceF.zzh(this.zzcdP);
                z2 = z2 || vtVar.zzIq();
            }
            uv zzd = this.zzceR.zzceG;
            boolean z3 = zzd.getValue() != null && ((sn) zzd.getValue()).zzHo();
            Iterator<wp> it = zzFq.iterator();
            while (true) {
                z = z3;
                if (!it.hasNext()) {
                    break;
                }
                zzd = zzd.zze(it.next());
                z3 = z || (zzd.getValue() != null && ((sn) zzd.getValue()).zzHo());
                if (z3 || zzd.isEmpty()) {
                    z = z3;
                }
            }
            z = z3;
            if (z2 && !z) {
                uv zzH = this.zzceR.zzceG.zzH(zzFq);
                if (!zzH.isEmpty()) {
                    for (vu vuVar : this.zzceR.zza((uv<sn>) zzH)) {
                        tf tfVar = new tf(this.zzceR, vuVar);
                        this.zzceR.zzceL.zza(so.zzd(vuVar.zzIv()), tfVar.zzcff, tfVar, tfVar);
                    }
                }
            }
            if (!z && !first.isEmpty() && this.zzceV == null) {
                if (z2) {
                    this.zzceR.zzceL.zza(so.zzd(this.zzcdP), (th) null);
                } else {
                    for (vt vtVar2 : first) {
                        this.zzceR.zzceL.zza(so.zzd(vtVar2), this.zzceR.zze(vtVar2));
                    }
                }
            }
            this.zzceR.zzU(first);
        }
        return arrayList;
    }
}
