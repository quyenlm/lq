package com.google.android.gms.internal;

import java.util.Iterator;

public final class we implements wf {
    private final int limit;
    private final xe zzcgV;
    private final boolean zzchA;
    private final wh zzchz;

    public we(vq vqVar) {
        this.zzchz = new wh(vqVar);
        this.zzcgV = vqVar.zzIm();
        this.limit = vqVar.getLimit();
        this.zzchA = !vqVar.zzIo();
    }

    public final wf zzID() {
        return this.zzchz.zzID();
    }

    public final boolean zzIE() {
        return true;
    }

    public final xe zzIm() {
        return this.zzcgV;
    }

    public final xf zza(xf xfVar, wp wpVar, xm xmVar, qr qrVar, wg wgVar, wc wcVar) {
        xm zzJb = !this.zzchz.zza(new xl(wpVar, xmVar)) ? xd.zzJb() : xmVar;
        if (xfVar.zzFn().zzm(wpVar).equals(zzJb)) {
            return xfVar;
        }
        if (xfVar.zzFn().getChildCount() < this.limit) {
            return this.zzchz.zzID().zza(xfVar, wpVar, zzJb, qrVar, wgVar, wcVar);
        }
        xl xlVar = new xl(wpVar, zzJb);
        xl zzJf = this.zzchA ? xfVar.zzJf() : xfVar.zzJg();
        boolean zza = this.zzchz.zza(xlVar);
        if (xfVar.zzFn().zzk(wpVar)) {
            xm zzm = xfVar.zzFn().zzm(wpVar);
            xl zza2 = wgVar.zza(this.zzcgV, zzJf, this.zzchA);
            while (zza2 != null && (zza2.zzJk().equals(wpVar) || xfVar.zzFn().zzk(zza2.zzJk()))) {
                zza2 = wgVar.zza(this.zzcgV, zza2, this.zzchA);
            }
            if (zza && !zzJb.isEmpty() && (zza2 == null ? 1 : this.zzcgV.zza(zza2, xlVar, this.zzchA)) >= 0) {
                if (wcVar != null) {
                    wcVar.zza(vi.zza(wpVar, zzJb, zzm));
                }
                return xfVar.zzg(wpVar, zzJb);
            }
            if (wcVar != null) {
                wcVar.zza(vi.zzd(wpVar, zzm));
            }
            xf zzg = xfVar.zzg(wpVar, xd.zzJb());
            if (!(zza2 != null && this.zzchz.zza(zza2))) {
                return zzg;
            }
            if (wcVar != null) {
                wcVar.zza(vi.zzc(zza2.zzJk(), zza2.zzFn()));
            }
            return zzg.zzg(zza2.zzJk(), zza2.zzFn());
        } else if (zzJb.isEmpty() || !zza || this.zzcgV.zza(zzJf, xlVar, this.zzchA) < 0) {
            return xfVar;
        } else {
            if (wcVar != null) {
                wcVar.zza(vi.zzd(zzJf.zzJk(), zzJf.zzFn()));
                wcVar.zza(vi.zzc(wpVar, zzJb));
            }
            return xfVar.zzg(wpVar, zzJb).zzg(zzJf.zzJk(), xd.zzJb());
        }
    }

    public final xf zza(xf xfVar, xf xfVar2, wc wcVar) {
        xf xfVar3;
        xl zzIF;
        xl zzIG;
        int i;
        Iterator<xl> it;
        if (xfVar2.zzFn().zzIQ() || xfVar2.zzFn().isEmpty()) {
            xfVar3 = xf.zza(xd.zzJb(), this.zzcgV);
        } else {
            xfVar3 = xfVar2.zzk(xd.zzJb());
            if (this.zzchA) {
                it = xfVar2.zzFz();
                zzIF = this.zzchz.zzIG();
                zzIG = this.zzchz.zzIF();
                i = -1;
            } else {
                Iterator<xl> it2 = xfVar2.iterator();
                zzIF = this.zzchz.zzIF();
                zzIG = this.zzchz.zzIG();
                i = 1;
                it = it2;
            }
            boolean z = false;
            int i2 = 0;
            while (it.hasNext()) {
                xl next = it.next();
                if (!z && this.zzcgV.compare(zzIF, next) * i <= 0) {
                    z = true;
                }
                if (z && i2 < this.limit && this.zzcgV.compare(next, zzIG) * i <= 0) {
                    i2++;
                } else {
                    xfVar3 = xfVar3.zzg(next.zzJk(), xd.zzJb());
                }
            }
        }
        return this.zzchz.zzID().zza(xfVar, xfVar3, wcVar);
    }

    public final xf zza(xf xfVar, xm xmVar) {
        return xfVar;
    }
}
