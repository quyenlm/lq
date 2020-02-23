package com.google.android.gms.internal;

import com.google.firebase.database.DatabaseError;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class sn {
    private final Map<vq, vu> zzceE = new HashMap();
    private final uh zzceF;

    public sn(uh uhVar) {
        this.zzceF = uhVar;
    }

    private final List<vj> zza(vu vuVar, tx txVar, ts tsVar, xm xmVar) {
        vv zzb = vuVar.zzb(txVar, tsVar, xmVar);
        if (!vuVar.zzIv().zzIq()) {
            HashSet hashSet = new HashSet();
            HashSet hashSet2 = new HashSet();
            for (vi next : zzb.zzchq) {
                vl zzHZ = next.zzHZ();
                if (zzHZ == vl.CHILD_ADDED) {
                    hashSet2.add(next.zzHY());
                } else if (zzHZ == vl.CHILD_REMOVED) {
                    hashSet.add(next.zzHY());
                }
            }
            if (!hashSet2.isEmpty() || !hashSet.isEmpty()) {
                this.zzceF.zza(vuVar.zzIv(), (Set<wp>) hashSet2, (Set<wp>) hashSet);
            }
        }
        return zzb.zzchp;
    }

    public final boolean isEmpty() {
        return this.zzceE.isEmpty();
    }

    public final List<vu> zzHn() {
        ArrayList arrayList = new ArrayList();
        for (Map.Entry<vq, vu> value : this.zzceE.entrySet()) {
            vu vuVar = (vu) value.getValue();
            if (!vuVar.zzIv().zzIq()) {
                arrayList.add(vuVar);
            }
        }
        return arrayList;
    }

    public final boolean zzHo() {
        return zzHp() != null;
    }

    public final vu zzHp() {
        for (Map.Entry<vq, vu> value : this.zzceE.entrySet()) {
            vu vuVar = (vu) value.getValue();
            if (vuVar.zzIv().zzIq()) {
                return vuVar;
            }
        }
        return null;
    }

    public final za<List<vt>, List<vk>> zza(vt vtVar, qi qiVar, DatabaseError databaseError) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        boolean zzHo = zzHo();
        if (vtVar.isDefault()) {
            Iterator<Map.Entry<vq, vu>> it = this.zzceE.entrySet().iterator();
            while (it.hasNext()) {
                vu vuVar = (vu) it.next().getValue();
                arrayList2.addAll(vuVar.zza(qiVar, databaseError));
                if (vuVar.isEmpty()) {
                    it.remove();
                    if (!vuVar.zzIv().zzIq()) {
                        arrayList.add(vuVar.zzIv());
                    }
                }
            }
        } else {
            vu vuVar2 = this.zzceE.get(vtVar.zzIu());
            if (vuVar2 != null) {
                arrayList2.addAll(vuVar2.zza(qiVar, databaseError));
                if (vuVar2.isEmpty()) {
                    this.zzceE.remove(vtVar.zzIu());
                    if (!vuVar2.zzIv().zzIq()) {
                        arrayList.add(vuVar2.zzIv());
                    }
                }
            }
        }
        if (zzHo && !zzHo()) {
            arrayList.add(vt.zzM(vtVar.zzFq()));
        }
        return new za<>(arrayList, arrayList2);
    }

    public final List<vj> zza(qi qiVar, ts tsVar, vg vgVar) {
        boolean z;
        vt zzGH = qiVar.zzGH();
        vu vuVar = this.zzceE.get(zzGH.zzIu());
        if (vuVar == null) {
            xm zzc = tsVar.zzc(vgVar.zzHU() ? vgVar.zzFn() : null);
            if (zzc != null) {
                z = true;
            } else {
                zzc = tsVar.zzd(vgVar.zzFn());
                z = false;
            }
            vu vuVar2 = new vu(zzGH, new vw(new vg(xf.zza(zzc, zzGH.zzIm()), z, false), vgVar));
            if (!zzGH.zzIq()) {
                HashSet hashSet = new HashSet();
                for (xl zzJk : vuVar2.zzIx()) {
                    hashSet.add(zzJk.zzJk());
                }
                this.zzceF.zza(zzGH, (Set<wp>) hashSet);
            }
            this.zzceE.put(zzGH.zzIu(), vuVar2);
            vuVar = vuVar2;
        }
        vuVar.zzb(qiVar);
        return vuVar.zzk(qiVar);
    }

    public final List<vj> zza(tx txVar, ts tsVar, xm xmVar) {
        vq zzHI = txVar.zzHE().zzHI();
        if (zzHI != null) {
            return zza(this.zzceE.get(zzHI), txVar, tsVar, xmVar);
        }
        ArrayList arrayList = new ArrayList();
        for (Map.Entry<vq, vu> value : this.zzceE.entrySet()) {
            arrayList.addAll(zza((vu) value.getValue(), txVar, tsVar, xmVar));
        }
        return arrayList;
    }

    public final vu zzb(vt vtVar) {
        return vtVar.zzIq() ? zzHp() : this.zzceE.get(vtVar.zzIu());
    }

    public final boolean zzc(vt vtVar) {
        return zzb(vtVar) != null;
    }

    public final xm zzr(qr qrVar) {
        for (vu next : this.zzceE.values()) {
            if (next.zzr(qrVar) != null) {
                return next.zzr(qrVar);
            }
        }
        return null;
    }
}
