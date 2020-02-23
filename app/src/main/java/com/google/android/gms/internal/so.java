package com.google.android.gms.internal;

import com.google.firebase.database.DatabaseError;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class so {
    /* access modifiers changed from: private */
    public final wl zzbZE;
    /* access modifiers changed from: private */
    public final uh zzceF;
    /* access modifiers changed from: private */
    public uv<sn> zzceG = uv.zzHR();
    /* access modifiers changed from: private */
    public final tp zzceH = new tp();
    /* access modifiers changed from: private */
    public final Map<th, vt> zzceI = new HashMap();
    /* access modifiers changed from: private */
    public final Map<vt, th> zzceJ = new HashMap();
    private final Set<vt> zzceK = new HashSet();
    /* access modifiers changed from: private */
    public final tg zzceL;
    private long zzceM = 1;

    public so(qd qdVar, uh uhVar, tg tgVar) {
        this.zzceL = tgVar;
        this.zzceF = uhVar;
        this.zzbZE = qdVar.zzgP("SyncTree");
    }

    /* access modifiers changed from: private */
    public final th zzHr() {
        long j = this.zzceM;
        this.zzceM = 1 + j;
        return new th(j);
    }

    /* access modifiers changed from: private */
    public final void zzU(List<vt> list) {
        for (vt next : list) {
            if (!next.zzIq()) {
                th zze = zze(next);
                this.zzceJ.remove(next);
                this.zzceI.remove(zze);
            }
        }
    }

    /* access modifiers changed from: private */
    public final List<vk> zza(tx txVar) {
        return zza(txVar, this.zzceG, (xm) null, this.zzceH.zzt(qr.zzGZ()));
    }

    private final List<vk> zza(tx txVar, uv<sn> uvVar, xm xmVar, ts tsVar) {
        if (txVar.zzFq().isEmpty()) {
            return zzb(txVar, uvVar, xmVar, tsVar);
        }
        sn value = uvVar.getValue();
        if (xmVar == null && value != null) {
            xmVar = value.zzr(qr.zzGZ());
        }
        ArrayList arrayList = new ArrayList();
        wp zzHc = txVar.zzFq().zzHc();
        tx zzc = txVar.zzc(zzHc);
        uv uvVar2 = uvVar.zzHS().get(zzHc);
        if (!(uvVar2 == null || zzc == null)) {
            arrayList.addAll(zza(zzc, (uv<sn>) uvVar2, xmVar != null ? xmVar.zzm(zzHc) : null, tsVar.zzb(zzHc)));
        }
        if (value != null) {
            arrayList.addAll(value.zza(txVar, tsVar, xmVar));
        }
        return arrayList;
    }

    /* access modifiers changed from: private */
    public final List<vu> zza(uv<sn> uvVar) {
        ArrayList arrayList = new ArrayList();
        zza(uvVar, (List<vu>) arrayList);
        return arrayList;
    }

    /* access modifiers changed from: private */
    public final List<? extends vk> zza(vt vtVar, tx txVar) {
        qr zzFq = vtVar.zzFq();
        return this.zzceG.zzJ(zzFq).zza(txVar, this.zzceH.zzt(zzFq), (xm) null);
    }

    private final void zza(uv<sn> uvVar, List<vu> list) {
        sn value = uvVar.getValue();
        if (value == null || !value.zzHo()) {
            if (value != null) {
                list.addAll(value.zzHn());
            }
            Iterator<Map.Entry<wp, uv<sn>>> it = uvVar.zzHS().iterator();
            while (it.hasNext()) {
                zza((uv<sn>) (uv) it.next().getValue(), list);
            }
            return;
        }
        list.add(value.zzHp());
    }

    /* access modifiers changed from: private */
    public final void zza(vt vtVar, vu vuVar) {
        qr zzFq = vtVar.zzFq();
        th zze = zze(vtVar);
        tf tfVar = new tf(this, vuVar);
        this.zzceL.zza(zzd(vtVar), zze, tfVar, tfVar);
        uv<sn> zzH = this.zzceG.zzH(zzFq);
        if (zze == null) {
            zzH.zza(new st(this));
        }
    }

    /* access modifiers changed from: private */
    public final vt zzb(th thVar) {
        return this.zzceI.get(thVar);
    }

    /* access modifiers changed from: private */
    public final List<vk> zzb(tx txVar, uv<sn> uvVar, xm xmVar, ts tsVar) {
        sn value = uvVar.getValue();
        xm zzr = (xmVar != null || value == null) ? xmVar : value.zzr(qr.zzGZ());
        ArrayList arrayList = new ArrayList();
        uvVar.zzHS().zza(new su(this, zzr, tsVar, txVar, arrayList));
        if (value != null) {
            arrayList.addAll(value.zza(txVar, tsVar, zzr));
        }
        return arrayList;
    }

    private final List<vk> zzb(vt vtVar, qi qiVar, DatabaseError databaseError) {
        return (List) this.zzceF.zzg(new ss(this, vtVar, qiVar, databaseError));
    }

    /* access modifiers changed from: private */
    public static vt zzd(vt vtVar) {
        return (!vtVar.zzIq() || vtVar.isDefault()) ? vtVar : vt.zzM(vtVar.zzFq());
    }

    /* access modifiers changed from: private */
    public final th zze(vt vtVar) {
        return this.zzceJ.get(vtVar);
    }

    public final boolean isEmpty() {
        return this.zzceG.isEmpty();
    }

    public final List<? extends vk> zzHq() {
        return (List) this.zzceF.zzg(new sx(this));
    }

    public final List<? extends vk> zza(long j, boolean z, boolean z2, ys ysVar) {
        return (List) this.zzceF.zzg(new sw(this, z2, j, z, ysVar));
    }

    public final List<? extends vk> zza(qr qrVar, pz pzVar, pz pzVar2, long j, boolean z) {
        return (List) this.zzceF.zzg(new sv(this, z, qrVar, pzVar, j, pzVar2));
    }

    public final List<? extends vk> zza(qr qrVar, xm xmVar, th thVar) {
        return (List) this.zzceF.zzg(new tc(this, thVar, qrVar, xmVar));
    }

    public final List<? extends vk> zza(qr qrVar, xm xmVar, xm xmVar2, long j, boolean z, boolean z2) {
        zd.zzb(z || !z2, "We shouldn't be persisting non-visible writes.");
        return (List) this.zzceF.zzg(new sp(this, z2, qrVar, xmVar, j, xmVar2, z));
    }

    public final List<? extends vk> zza(qr qrVar, List<xt> list, th thVar) {
        vt zzb = zzb(thVar);
        if (zzb == null) {
            return Collections.emptyList();
        }
        xm zzIw = this.zzceG.zzJ(zzb.zzFq()).zzb(zzb).zzIw();
        Iterator<xt> it = list.iterator();
        while (true) {
            xm xmVar = zzIw;
            if (!it.hasNext()) {
                return zza(qrVar, xmVar, thVar);
            }
            zzIw = it.next().zzm(xmVar);
        }
    }

    public final List<? extends vk> zza(qr qrVar, Map<qr, xm> map) {
        return (List) this.zzceF.zzg(new sz(this, map, qrVar));
    }

    public final List<? extends vk> zza(qr qrVar, Map<qr, xm> map, th thVar) {
        return (List) this.zzceF.zzg(new sq(this, thVar, qrVar, map));
    }

    public final List<? extends vk> zza(th thVar) {
        return (List) this.zzceF.zzg(new tb(this, thVar));
    }

    public final List<vk> zza(vt vtVar, DatabaseError databaseError) {
        return zzb(vtVar, (qi) null, databaseError);
    }

    public final void zza(vt vtVar, boolean z) {
        if (z && !this.zzceK.contains(vtVar)) {
            zzg((qi) new te(vtVar));
            this.zzceK.add(vtVar);
        } else if (!z && this.zzceK.contains(vtVar)) {
            zzh((qi) new te(vtVar));
            this.zzceK.remove(vtVar);
        }
    }

    public final List<? extends vk> zzb(qr qrVar, List<xt> list) {
        sn zzJ = this.zzceG.zzJ(qrVar);
        if (zzJ == null) {
            return Collections.emptyList();
        }
        vu zzHp = zzJ.zzHp();
        if (zzHp == null) {
            return Collections.emptyList();
        }
        xm zzIw = zzHp.zzIw();
        Iterator<xt> it = list.iterator();
        while (true) {
            xm xmVar = zzIw;
            if (!it.hasNext()) {
                return zzi(qrVar, xmVar);
            }
            zzIw = it.next().zzm(xmVar);
        }
    }

    public final xm zzc(qr qrVar, List<Long> list) {
        xm zzr;
        uv<sn> uvVar = this.zzceG;
        uvVar.getValue();
        xm xmVar = null;
        qr zzGZ = qr.zzGZ();
        qr qrVar2 = qrVar;
        while (true) {
            wp zzHc = qrVar2.zzHc();
            qrVar2 = qrVar2.zzHd();
            zzGZ = zzGZ.zza(zzHc);
            qr zza = qr.zza(zzGZ, qrVar);
            uvVar = zzHc != null ? uvVar.zze(zzHc) : uv.zzHR();
            sn value = uvVar.getValue();
            zzr = value != null ? value.zzr(zza) : xmVar;
            if (!qrVar2.isEmpty() && zzr == null) {
                xmVar = zzr;
            }
        }
        return this.zzceH.zza(qrVar, zzr, list, true);
    }

    public final List<? extends vk> zzg(qi qiVar) {
        return (List) this.zzceF.zzg(new sr(this, qiVar));
    }

    public final List<vk> zzh(qi qiVar) {
        return zzb(qiVar.zzGH(), qiVar, (DatabaseError) null);
    }

    public final List<? extends vk> zzi(qr qrVar, xm xmVar) {
        return (List) this.zzceF.zzg(new sy(this, qrVar, xmVar));
    }

    public final List<? extends vk> zzs(qr qrVar) {
        return (List) this.zzceF.zzg(new ta(this, qrVar));
    }
}
