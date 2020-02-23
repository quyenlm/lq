package com.google.android.gms.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class uo {
    private static final uz<Map<vq, un>> zzcgk = new up();
    private static final uz<Map<vq, un>> zzcgl = new uq();
    /* access modifiers changed from: private */
    public static final uz<un> zzcgm = new ur();
    private static final uz<un> zzcgn = new us();
    private final wl zzbZE;
    private final ui zzcfX;
    private uv<Map<vq, un>> zzcgo;
    private final ys zzcgp;
    private long zzcgq = 0;

    /* JADX INFO: finally extract failed */
    public uo(ui uiVar, wl wlVar, ys ysVar) {
        this.zzcfX = uiVar;
        this.zzbZE = wlVar;
        this.zzcgp = ysVar;
        this.zzcgo = new uv<>(null);
        try {
            this.zzcfX.beginTransaction();
            this.zzcfX.zzan(this.zzcgp.zzJF());
            this.zzcfX.setTransactionSuccessful();
            this.zzcfX.endTransaction();
            for (un next : this.zzcfX.zzFu()) {
                this.zzcgq = Math.max(next.id + 1, this.zzcgq);
                zzb(next);
            }
        } catch (Throwable th) {
            this.zzcfX.endTransaction();
            throw th;
        }
    }

    private final boolean zzD(qr qrVar) {
        return this.zzcgo.zza(qrVar, zzcgk) != null;
    }

    private final Set<Long> zzE(qr qrVar) {
        HashSet hashSet = new HashSet();
        Map zzJ = this.zzcgo.zzJ(qrVar);
        if (zzJ != null) {
            for (un unVar : zzJ.values()) {
                if (!unVar.zzcgi.zzIq()) {
                    hashSet.add(Long.valueOf(unVar.id));
                }
            }
        }
        return hashSet;
    }

    private final List<un> zza(uz<un> uzVar) {
        ArrayList arrayList = new ArrayList();
        Iterator<Map.Entry<qr, Map<vq, un>>> it = this.zzcgo.iterator();
        while (it.hasNext()) {
            for (un unVar : ((Map) it.next().getValue()).values()) {
                if (uzVar.zzaj(unVar)) {
                    arrayList.add(unVar);
                }
            }
        }
        return arrayList;
    }

    /* access modifiers changed from: private */
    public final void zza(un unVar) {
        zzb(unVar);
        this.zzcfX.zza(unVar);
    }

    private final void zzb(un unVar) {
        HashMap hashMap;
        boolean z = false;
        vt vtVar = unVar.zzcgi;
        zd.zzb(!vtVar.zzIq() || vtVar.isDefault(), "Can't have tracked non-default query that loads all data");
        Map zzJ = this.zzcgo.zzJ(unVar.zzcgi.zzFq());
        if (zzJ == null) {
            HashMap hashMap2 = new HashMap();
            this.zzcgo = this.zzcgo.zzb(unVar.zzcgi.zzFq(), hashMap2);
            hashMap = hashMap2;
        } else {
            hashMap = zzJ;
        }
        un unVar2 = (un) hashMap.get(unVar.zzcgi.zzIu());
        if (unVar2 == null || unVar2.id == unVar.id) {
            z = true;
        }
        zd.zzaF(z);
        hashMap.put(unVar.zzcgi.zzIu(), unVar);
    }

    private final void zzb(vt vtVar, boolean z) {
        un unVar;
        vt zzj = zzj(vtVar);
        un zzk = zzk(zzj);
        long zzJF = this.zzcgp.zzJF();
        if (zzk != null) {
            un unVar2 = new un(zzk.id, zzk.zzcgi, zzJF, zzk.complete, zzk.zzbpf);
            unVar = new un(unVar2.id, unVar2.zzcgi, unVar2.zzcgj, unVar2.complete, z);
        } else {
            long j = this.zzcgq;
            this.zzcgq = 1 + j;
            unVar = new un(j, zzj, zzJF, false, z);
        }
        zza(unVar);
    }

    private static vt zzj(vt vtVar) {
        return vtVar.zzIq() ? vt.zzM(vtVar.zzFq()) : vtVar;
    }

    public final Set<wp> zzA(qr qrVar) {
        HashSet hashSet = new HashSet();
        Set<Long> zzE = zzE(qrVar);
        if (!zzE.isEmpty()) {
            hashSet.addAll(this.zzcfX.zze(zzE));
        }
        Iterator<Map.Entry<wp, uv<Map<vq, un>>>> it = this.zzcgo.zzH(qrVar).zzHS().iterator();
        while (it.hasNext()) {
            Map.Entry next = it.next();
            wp wpVar = (wp) next.getKey();
            uv uvVar = (uv) next.getValue();
            if (uvVar.getValue() != null && zzcgk.zzaj((Map) uvVar.getValue())) {
                hashSet.add(wpVar);
            }
        }
        return hashSet;
    }

    public final void zzB(qr qrVar) {
        un zzHO;
        if (!zzD(qrVar)) {
            vt zzM = vt.zzM(qrVar);
            un zzk = zzk(zzM);
            if (zzk == null) {
                long j = this.zzcgq;
                this.zzcgq = 1 + j;
                zzHO = new un(j, zzM, this.zzcgp.zzJF(), true, false);
            } else {
                zzHO = zzk.zzHO();
            }
            zza(zzHO);
        }
    }

    public final boolean zzC(qr qrVar) {
        return this.zzcgo.zzb(qrVar, zzcgl) != null;
    }

    public final long zzHP() {
        return (long) zza(zzcgm).size();
    }

    public final uj zza(uc ucVar) {
        List<un> zza = zza(zzcgm);
        long size = (long) zza.size();
        long min = size - Math.min((long) Math.floor((double) ((1.0f - ucVar.zzHK()) * ((float) size))), ucVar.zzHL());
        uj ujVar = new uj();
        if (this.zzbZE.zzIH()) {
            this.zzbZE.zzb(new StringBuilder(80).append("Pruning old queries.  Prunable: ").append(zza.size()).append(" Count to prune: ").append(min).toString(), (Throwable) null, new Object[0]);
        }
        Collections.sort(zza, new uu(this));
        for (int i = 0; ((long) i) < min; i++) {
            un unVar = zza.get(i);
            ujVar = ujVar.zzx(unVar.zzcgi.zzFq());
            vt zzj = zzj(unVar.zzcgi);
            this.zzcfX.zzam(zzk(zzj).id);
            Map zzJ = this.zzcgo.zzJ(zzj.zzFq());
            zzJ.remove(zzj.zzIu());
            if (zzJ.isEmpty()) {
                this.zzcgo = this.zzcgo.zzI(zzj.zzFq());
            }
        }
        int i2 = (int) min;
        while (true) {
            int i3 = i2;
            if (i3 >= zza.size()) {
                break;
            }
            ujVar = ujVar.zzy(zza.get(i3).zzcgi.zzFq());
            i2 = i3 + 1;
        }
        List<un> zza2 = zza(zzcgn);
        if (this.zzbZE.zzIH()) {
            this.zzbZE.zzb(new StringBuilder(31).append("Unprunable queries: ").append(zza2.size()).toString(), (Throwable) null, new Object[0]);
        }
        for (un unVar2 : zza2) {
            ujVar = ujVar.zzy(unVar2.zzcgi.zzFq());
        }
        return ujVar;
    }

    public final void zzg(vt vtVar) {
        zzb(vtVar, true);
    }

    public final void zzh(vt vtVar) {
        zzb(vtVar, false);
    }

    public final un zzk(vt vtVar) {
        vt zzj = zzj(vtVar);
        Map zzJ = this.zzcgo.zzJ(zzj.zzFq());
        if (zzJ != null) {
            return (un) zzJ.get(zzj.zzIu());
        }
        return null;
    }

    public final void zzl(vt vtVar) {
        un zzk = zzk(zzj(vtVar));
        if (zzk != null && !zzk.complete) {
            zza(zzk.zzHO());
        }
    }

    public final boolean zzm(vt vtVar) {
        if (zzD(vtVar.zzFq())) {
            return true;
        }
        if (vtVar.zzIq()) {
            return false;
        }
        Map zzJ = this.zzcgo.zzJ(vtVar.zzFq());
        return zzJ != null && zzJ.containsKey(vtVar.zzIu()) && ((un) zzJ.get(vtVar.zzIu())).complete;
    }

    public final void zzz(qr qrVar) {
        this.zzcgo.zzH(qrVar).zza(new ut(this));
    }
}
