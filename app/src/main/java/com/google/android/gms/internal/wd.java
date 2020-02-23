package com.google.android.gms.internal;

public final class wd implements wf {
    private final xe zzcgV;

    public wd(xe xeVar) {
        this.zzcgV = xeVar;
    }

    public final wf zzID() {
        return this;
    }

    public final boolean zzIE() {
        return false;
    }

    public final xe zzIm() {
        return this.zzcgV;
    }

    public final xf zza(xf xfVar, wp wpVar, xm xmVar, qr qrVar, wg wgVar, wc wcVar) {
        xm zzFn = xfVar.zzFn();
        xm zzm = zzFn.zzm(wpVar);
        if (zzm.zzN(qrVar).equals(xmVar.zzN(qrVar)) && zzm.isEmpty() == xmVar.isEmpty()) {
            return xfVar;
        }
        if (wcVar != null) {
            if (xmVar.isEmpty()) {
                if (zzFn.zzk(wpVar)) {
                    wcVar.zza(vi.zzd(wpVar, zzm));
                }
            } else if (zzm.isEmpty()) {
                wcVar.zza(vi.zzc(wpVar, xmVar));
            } else {
                wcVar.zza(vi.zza(wpVar, xmVar, zzm));
            }
        }
        return (!zzFn.zzIQ() || !xmVar.isEmpty()) ? xfVar.zzg(wpVar, xmVar) : xfVar;
    }

    public final xf zza(xf xfVar, xf xfVar2, wc wcVar) {
        if (wcVar != null) {
            for (xl xlVar : xfVar.zzFn()) {
                if (!xfVar2.zzFn().zzk(xlVar.zzJk())) {
                    wcVar.zza(vi.zzd(xlVar.zzJk(), xlVar.zzFn()));
                }
            }
            if (!xfVar2.zzFn().zzIQ()) {
                for (xl xlVar2 : xfVar2.zzFn()) {
                    if (xfVar.zzFn().zzk(xlVar2.zzJk())) {
                        xm zzm = xfVar.zzFn().zzm(xlVar2.zzJk());
                        if (!zzm.equals(xlVar2.zzFn())) {
                            wcVar.zza(vi.zza(xlVar2.zzJk(), xlVar2.zzFn(), zzm));
                        }
                    } else {
                        wcVar.zza(vi.zzc(xlVar2.zzJk(), xlVar2.zzFn()));
                    }
                }
            }
        }
        return xfVar2;
    }

    public final xf zza(xf xfVar, xm xmVar) {
        return xfVar.zzFn().isEmpty() ? xfVar : xfVar.zzk(xmVar);
    }
}
