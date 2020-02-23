package com.google.android.gms.internal;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

public final class ue implements uh {
    private final wl zzbZE;
    private final ui zzcfX;
    private final uo zzcfY;
    private final uc zzcfZ;
    private long zzcga;

    public ue(qd qdVar, ui uiVar, uc ucVar) {
        this(qdVar, uiVar, ucVar, new yt());
    }

    private ue(qd qdVar, ui uiVar, uc ucVar, ys ysVar) {
        this.zzcga = 0;
        this.zzcfX = uiVar;
        this.zzbZE = qdVar.zzgP("Persistence");
        this.zzcfY = new uo(this.zzcfX, this.zzbZE, ysVar);
        this.zzcfZ = ucVar;
    }

    private final void zzHM() {
        this.zzcga++;
        if (this.zzcfZ.zzax(this.zzcga)) {
            if (this.zzbZE.zzIH()) {
                this.zzbZE.zzb("Reached prune check threshold.", (Throwable) null, new Object[0]);
            }
            this.zzcga = 0;
            boolean z = true;
            long zzFt = this.zzcfX.zzFt();
            if (this.zzbZE.zzIH()) {
                this.zzbZE.zzb(new StringBuilder(32).append("Cache size: ").append(zzFt).toString(), (Throwable) null, new Object[0]);
            }
            while (z && this.zzcfZ.zzi(zzFt, this.zzcfY.zzHP())) {
                uj zza = this.zzcfY.zza(this.zzcfZ);
                if (zza.zzHN()) {
                    this.zzcfX.zza(qr.zzGZ(), zza);
                } else {
                    z = false;
                }
                zzFt = this.zzcfX.zzFt();
                if (this.zzbZE.zzIH()) {
                    this.zzbZE.zzb(new StringBuilder(44).append("Cache size after prune: ").append(zzFt).toString(), (Throwable) null, new Object[0]);
                }
            }
        }
    }

    public final List<tm> zzFs() {
        return this.zzcfX.zzFs();
    }

    public final void zzFv() {
        this.zzcfX.zzFv();
    }

    public final void zza(qr qrVar, pz pzVar, long j) {
        this.zzcfX.zza(qrVar, pzVar, j);
    }

    public final void zza(qr qrVar, xm xmVar, long j) {
        this.zzcfX.zza(qrVar, xmVar, j);
    }

    public final void zza(vt vtVar, xm xmVar) {
        if (vtVar.zzIq()) {
            this.zzcfX.zza(vtVar.zzFq(), xmVar);
        } else {
            this.zzcfX.zzb(vtVar.zzFq(), xmVar);
        }
        zzi(vtVar);
        zzHM();
    }

    public final void zza(vt vtVar, Set<wp> set) {
        this.zzcfX.zza(this.zzcfY.zzk(vtVar).id, set);
    }

    public final void zza(vt vtVar, Set<wp> set, Set<wp> set2) {
        this.zzcfX.zza(this.zzcfY.zzk(vtVar).id, set, set2);
    }

    public final void zzal(long j) {
        this.zzcfX.zzal(j);
    }

    public final void zzc(qr qrVar, pz pzVar) {
        Iterator<Map.Entry<qr, xm>> it = pzVar.iterator();
        while (it.hasNext()) {
            Map.Entry next = it.next();
            zzk(qrVar.zzh((qr) next.getKey()), (xm) next.getValue());
        }
    }

    public final void zzd(qr qrVar, pz pzVar) {
        this.zzcfX.zza(qrVar, pzVar);
        zzHM();
    }

    public final vg zzf(vt vtVar) {
        Set<wp> zzA;
        boolean z;
        if (this.zzcfY.zzm(vtVar)) {
            un zzk = this.zzcfY.zzk(vtVar);
            if (vtVar.zzIq() || zzk == null || !zzk.complete) {
                zzA = null;
                z = true;
            } else {
                zzA = this.zzcfX.zzao(zzk.id);
                z = true;
            }
        } else {
            zzA = this.zzcfY.zzA(vtVar.zzFq());
            z = false;
        }
        xm zza = this.zzcfX.zza(vtVar.zzFq());
        if (zzA == null) {
            return new vg(xf.zza(zza, vtVar.zzIm()), true, false);
        }
        xm zzJb = xd.zzJb();
        Iterator<wp> it = zzA.iterator();
        while (true) {
            xm xmVar = zzJb;
            if (!it.hasNext()) {
                return new vg(xf.zza(xmVar, vtVar.zzIm()), z, true);
            }
            wp next = it.next();
            zzJb = xmVar.zze(next, zza.zzm(next));
        }
    }

    public final <T> T zzg(Callable<T> callable) {
        this.zzcfX.beginTransaction();
        try {
            T call = callable.call();
            this.zzcfX.setTransactionSuccessful();
            this.zzcfX.endTransaction();
            return call;
        } catch (Throwable th) {
            this.zzcfX.endTransaction();
            throw th;
        }
    }

    public final void zzg(vt vtVar) {
        this.zzcfY.zzg(vtVar);
    }

    public final void zzh(vt vtVar) {
        this.zzcfY.zzh(vtVar);
    }

    public final void zzi(vt vtVar) {
        if (vtVar.zzIq()) {
            this.zzcfY.zzz(vtVar.zzFq());
        } else {
            this.zzcfY.zzl(vtVar);
        }
    }

    public final void zzk(qr qrVar, xm xmVar) {
        if (!this.zzcfY.zzC(qrVar)) {
            this.zzcfX.zza(qrVar, xmVar);
            this.zzcfY.zzB(qrVar);
        }
    }
}
