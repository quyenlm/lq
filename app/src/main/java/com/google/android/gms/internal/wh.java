package com.google.android.gms.internal;

import java.util.Iterator;

public final class wh implements wf {
    private final xe zzcgV;
    private final wd zzchB;
    private final xl zzchC;
    private final xl zzchD;

    public wh(vq vqVar) {
        xl zzJi;
        xl zzJc;
        this.zzchB = new wd(vqVar.zzIm());
        this.zzcgV = vqVar.zzIm();
        if (vqVar.zzIe()) {
            zzJi = vqVar.zzIm().zzf(vqVar.zzIg(), vqVar.zzIf());
        } else {
            vqVar.zzIm();
            zzJi = xl.zzJi();
        }
        this.zzchC = zzJi;
        if (vqVar.zzIh()) {
            zzJc = vqVar.zzIm().zzf(vqVar.zzIj(), vqVar.zzIi());
        } else {
            zzJc = vqVar.zzIm().zzJc();
        }
        this.zzchD = zzJc;
    }

    public final wf zzID() {
        return this.zzchB;
    }

    public final boolean zzIE() {
        return true;
    }

    public final xl zzIF() {
        return this.zzchC;
    }

    public final xl zzIG() {
        return this.zzchD;
    }

    public final xe zzIm() {
        return this.zzcgV;
    }

    public final xf zza(xf xfVar, wp wpVar, xm xmVar, qr qrVar, wg wgVar, wc wcVar) {
        return this.zzchB.zza(xfVar, wpVar, !zza(new xl(wpVar, xmVar)) ? xd.zzJb() : xmVar, qrVar, wgVar, wcVar);
    }

    public final xf zza(xf xfVar, xf xfVar2, wc wcVar) {
        xf xfVar3;
        if (!xfVar2.zzFn().zzIQ()) {
            xf zzk = xfVar2.zzk(xd.zzJb());
            Iterator<xl> it = xfVar2.iterator();
            while (true) {
                xfVar3 = zzk;
                if (!it.hasNext()) {
                    break;
                }
                xl next = it.next();
                zzk = !zza(next) ? xfVar3.zzg(next.zzJk(), xd.zzJb()) : xfVar3;
            }
        } else {
            xfVar3 = xf.zza(xd.zzJb(), this.zzcgV);
        }
        return this.zzchB.zza(xfVar, xfVar3, wcVar);
    }

    public final xf zza(xf xfVar, xm xmVar) {
        return xfVar;
    }

    public final boolean zza(xl xlVar) {
        return this.zzcgV.compare(this.zzchC, xlVar) <= 0 && this.zzcgV.compare(xlVar, this.zzchD) <= 0;
    }
}
