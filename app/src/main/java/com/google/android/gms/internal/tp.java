package com.google.android.gms.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class tp {
    private static final uz<tm> zzcfv = new tr();
    private pz zzcfs = pz.zzGI();
    private List<tm> zzcft = new ArrayList();
    private Long zzcfu = -1L;

    private static pz zza(List<tm> list, uz<tm> uzVar, qr qrVar) {
        pz zzGI = pz.zzGI();
        Iterator<tm> it = list.iterator();
        while (true) {
            pz pzVar = zzGI;
            if (!it.hasNext()) {
                return pzVar;
            }
            tm next = it.next();
            if (uzVar.zzaj(next)) {
                qr zzFq = next.zzFq();
                if (next.zzHw()) {
                    if (qrVar.zzi(zzFq)) {
                        zzGI = pzVar.zze(qr.zza(qrVar, zzFq), next.zzHu());
                    } else if (zzFq.zzi(qrVar)) {
                        zzGI = pzVar.zze(qr.zzGZ(), next.zzHu().zzN(qr.zza(zzFq, qrVar)));
                    }
                } else if (qrVar.zzi(zzFq)) {
                    zzGI = pzVar.zzb(qr.zza(qrVar, zzFq), next.zzHv());
                } else if (zzFq.zzi(qrVar)) {
                    qr zza = qr.zza(zzFq, qrVar);
                    if (zza.isEmpty()) {
                        zzGI = pzVar.zzb(qr.zzGZ(), next.zzHv());
                    } else {
                        xm zzf = next.zzHv().zzf(zza);
                        if (zzf != null) {
                            pzVar = pzVar.zze(qr.zzGZ(), zzf);
                        }
                    }
                }
            }
            zzGI = pzVar;
        }
    }

    public final List<tm> zzHz() {
        ArrayList arrayList = new ArrayList(this.zzcft);
        this.zzcfs = pz.zzGI();
        this.zzcft = new ArrayList();
        return arrayList;
    }

    public final xl zza(qr qrVar, xm xmVar, xl xlVar, boolean z, xe xeVar) {
        xl xlVar2 = null;
        pz zzg = this.zzcfs.zzg(qrVar);
        xm<xl> zzf = zzg.zzf(qr.zzGZ());
        if (zzf == null) {
            if (xmVar != null) {
                zzf = zzg.zzb(xmVar);
            }
            return xlVar2;
        }
        for (xl xlVar3 : zzf) {
            if (xeVar.zza(xlVar3, xlVar, z) <= 0 || (xlVar2 != null && xeVar.zza(xlVar3, xlVar2, z) >= 0)) {
                xlVar3 = xlVar2;
            }
            xlVar2 = xlVar3;
        }
        return xlVar2;
    }

    public final xm zza(qr qrVar, qr qrVar2, xm xmVar, xm xmVar2) {
        qr zzh = qrVar.zzh(qrVar2);
        if (this.zzcfs.zze(zzh)) {
            return null;
        }
        pz zzg = this.zzcfs.zzg(zzh);
        return zzg.isEmpty() ? xmVar2.zzN(qrVar2) : zzg.zzb(xmVar2.zzN(qrVar2));
    }

    public final xm zza(qr qrVar, wp wpVar, vg vgVar) {
        qr zza = qrVar.zza(wpVar);
        xm zzf = this.zzcfs.zzf(zza);
        if (zzf != null) {
            return zzf;
        }
        if (vgVar.zzf(wpVar)) {
            return this.zzcfs.zzg(zza).zzb(vgVar.zzFn().zzm(wpVar));
        }
        return null;
    }

    public final xm zza(qr qrVar, xm xmVar, List<Long> list, boolean z) {
        if (!list.isEmpty() || z) {
            pz zzg = this.zzcfs.zzg(qrVar);
            if (!z && zzg.isEmpty()) {
                return xmVar;
            }
            if (!z && xmVar == null && !zzg.zze(qr.zzGZ())) {
                return null;
            }
            pz zza = zza(this.zzcft, (uz<tm>) new tq(this, z, list, qrVar), qrVar);
            if (xmVar == null) {
                xmVar = xd.zzJb();
            }
            return zza.zzb(xmVar);
        }
        xm zzf = this.zzcfs.zzf(qrVar);
        if (zzf != null) {
            return zzf;
        }
        pz zzg2 = this.zzcfs.zzg(qrVar);
        if (zzg2.isEmpty()) {
            return xmVar;
        }
        if (xmVar == null && !zzg2.zze(qr.zzGZ())) {
            return null;
        }
        if (xmVar == null) {
            xmVar = xd.zzJb();
        }
        return zzg2.zzb(xmVar);
    }

    public final void zza(qr qrVar, pz pzVar, Long l) {
        this.zzcft.add(new tm(l.longValue(), qrVar, pzVar));
        this.zzcfs = this.zzcfs.zzb(qrVar, pzVar);
        this.zzcfu = l;
    }

    public final void zza(qr qrVar, xm xmVar, Long l, boolean z) {
        this.zzcft.add(new tm(l.longValue(), qrVar, xmVar, z));
        if (z) {
            this.zzcfs = this.zzcfs.zze(qrVar, xmVar);
        }
        this.zzcfu = l;
    }

    public final tm zzav(long j) {
        for (tm next : this.zzcft) {
            if (next.zzHt() == j) {
                return next;
            }
        }
        return null;
    }

    public final boolean zzaw(long j) {
        tm tmVar;
        boolean z;
        boolean z2;
        boolean z3;
        Iterator<tm> it = this.zzcft.iterator();
        int i = 0;
        while (true) {
            if (!it.hasNext()) {
                tmVar = null;
                break;
            }
            tm next = it.next();
            if (next.zzHt() == j) {
                tmVar = next;
                break;
            }
            i++;
        }
        this.zzcft.remove(tmVar);
        boolean isVisible = tmVar.isVisible();
        int size = this.zzcft.size() - 1;
        boolean z4 = false;
        while (isVisible && size >= 0) {
            tm tmVar2 = this.zzcft.get(size);
            if (tmVar2.isVisible()) {
                if (size >= i) {
                    qr zzFq = tmVar.zzFq();
                    if (!tmVar2.zzHw()) {
                        Iterator<Map.Entry<qr, xm>> it2 = tmVar2.zzHv().iterator();
                        while (true) {
                            if (it2.hasNext()) {
                                if (tmVar2.zzFq().zzh((qr) it2.next().getKey()).zzi(zzFq)) {
                                    z3 = true;
                                    break;
                                }
                            } else {
                                z3 = false;
                                break;
                            }
                        }
                    } else {
                        z3 = tmVar2.zzFq().zzi(zzFq);
                    }
                    if (z3) {
                        z = z4;
                        z2 = false;
                        size--;
                        z4 = z;
                        isVisible = z2;
                    }
                }
                if (tmVar.zzFq().zzi(tmVar2.zzFq())) {
                    z = true;
                    z2 = isVisible;
                    size--;
                    z4 = z;
                    isVisible = z2;
                }
            }
            z = z4;
            z2 = isVisible;
            size--;
            z4 = z;
            isVisible = z2;
        }
        if (!isVisible) {
            return false;
        }
        if (z4) {
            this.zzcfs = zza(this.zzcft, zzcfv, qr.zzGZ());
            if (this.zzcft.size() > 0) {
                this.zzcfu = Long.valueOf(this.zzcft.get(this.zzcft.size() - 1).zzHt());
                return true;
            }
            this.zzcfu = -1L;
            return true;
        } else if (tmVar.zzHw()) {
            this.zzcfs = this.zzcfs.zzd(tmVar.zzFq());
            return true;
        } else {
            Iterator<Map.Entry<qr, xm>> it3 = tmVar.zzHv().iterator();
            while (it3.hasNext()) {
                this.zzcfs = this.zzcfs.zzd(tmVar.zzFq().zzh((qr) it3.next().getKey()));
            }
            return true;
        }
    }

    public final xm zzj(qr qrVar, xm xmVar) {
        xm xmVar2;
        xm zzJb = xd.zzJb();
        xm zzf = this.zzcfs.zzf(qrVar);
        if (zzf == null) {
            pz zzg = this.zzcfs.zzg(qrVar);
            Iterator it = xmVar.iterator();
            while (true) {
                xmVar2 = zzJb;
                if (!it.hasNext()) {
                    break;
                }
                xl xlVar = (xl) it.next();
                zzJb = xmVar2.zze(xlVar.zzJk(), zzg.zzg(new qr(xlVar.zzJk())).zzb(xlVar.zzFn()));
            }
            for (xl next : zzg.zzGK()) {
                xmVar2 = xmVar2.zze(next.zzJk(), next.zzFn());
            }
            return xmVar2;
        } else if (zzf.zzIQ()) {
            return zzJb;
        } else {
            Iterator it2 = zzf.iterator();
            while (true) {
                xm xmVar3 = zzJb;
                if (!it2.hasNext()) {
                    return xmVar3;
                }
                xl xlVar2 = (xl) it2.next();
                zzJb = xmVar3.zze(xlVar2.zzJk(), xlVar2.zzFn());
            }
        }
    }

    public final ts zzt(qr qrVar) {
        return new ts(qrVar, this);
    }

    public final xm zzu(qr qrVar) {
        return this.zzcfs.zzf(qrVar);
    }
}
