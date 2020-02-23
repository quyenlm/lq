package com.google.android.gms.internal;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

final class sr implements Callable<List<? extends vk>> {
    private /* synthetic */ so zzceR;
    private /* synthetic */ qi zzceU;

    sr(so soVar, qi qiVar) {
        this.zzceR = soVar;
        this.zzceU = qiVar;
    }

    public final /* synthetic */ Object call() throws Exception {
        boolean z;
        sn snVar;
        vg vgVar;
        xm zzr;
        boolean z2;
        vt zzGH = this.zzceU.zzGH();
        qr zzFq = zzGH.zzFq();
        xm xmVar = null;
        qr qrVar = zzFq;
        uv zzd = this.zzceR.zzceG;
        boolean z3 = false;
        while (!zzd.isEmpty()) {
            sn snVar2 = (sn) zzd.getValue();
            if (snVar2 != null) {
                if (xmVar == null) {
                    xmVar = snVar2.zzr(qrVar);
                }
                z2 = z || snVar2.zzHo();
            } else {
                z2 = z;
            }
            zzd = zzd.zze(qrVar.isEmpty() ? wp.zzgT("") : qrVar.zzHc());
            qrVar = qrVar.zzHd();
            z3 = z2;
        }
        sn snVar3 = (sn) this.zzceR.zzceG.zzJ(zzFq);
        if (snVar3 == null) {
            sn snVar4 = new sn(this.zzceR.zzceF);
            uv unused = this.zzceR.zzceG = this.zzceR.zzceG.zzb(zzFq, snVar4);
            snVar = snVar4;
        } else {
            z = z || snVar3.zzHo();
            if (xmVar == null) {
                xmVar = snVar3.zzr(qr.zzGZ());
            }
            snVar = snVar3;
        }
        this.zzceR.zzceF.zzg(zzGH);
        if (xmVar != null) {
            vgVar = new vg(xf.zza(xmVar, zzGH.zzIm()), true, false);
        } else {
            vg zzf = this.zzceR.zzceF.zzf(zzGH);
            if (zzf.zzHU()) {
                vgVar = zzf;
            } else {
                xm zzJb = xd.zzJb();
                Iterator it = this.zzceR.zzceG.zzH(zzFq).zzHS().iterator();
                while (it.hasNext()) {
                    Map.Entry entry = (Map.Entry) it.next();
                    sn snVar5 = (sn) ((uv) entry.getValue()).getValue();
                    zzJb = (snVar5 == null || (zzr = snVar5.zzr(qr.zzGZ())) == null) ? zzJb : zzJb.zze((wp) entry.getKey(), zzr);
                }
                for (xl xlVar : zzf.zzFn()) {
                    if (!zzJb.zzk(xlVar.zzJk())) {
                        zzJb = zzJb.zze(xlVar.zzJk(), xlVar.zzFn());
                    }
                }
                vgVar = new vg(xf.zza(zzJb, zzGH.zzIm()), false, false);
            }
        }
        boolean zzc = snVar.zzc(zzGH);
        if (!zzc && !zzGH.zzIq()) {
            th zzf2 = this.zzceR.zzHr();
            this.zzceR.zzceJ.put(zzGH, zzf2);
            this.zzceR.zzceI.put(zzf2, zzGH);
        }
        List<vj> zza = snVar.zza(this.zzceU, this.zzceR.zzceH.zzt(zzFq), vgVar);
        if (!zzc && !z) {
            this.zzceR.zza(zzGH, snVar.zzb(zzGH));
        }
        return zza;
    }
}
