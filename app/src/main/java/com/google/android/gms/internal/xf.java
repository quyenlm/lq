package com.google.android.gms.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public final class xf implements Iterable<xl> {
    private static final nm<xl> zzcik = new nm<>(Collections.emptyList(), (Comparator) null);
    private final xe zzcgV;
    private final xm zzcil;
    private nm<xl> zzcim;

    private xf(xm xmVar, xe xeVar) {
        this.zzcgV = xeVar;
        this.zzcil = xmVar;
        this.zzcim = null;
    }

    private xf(xm xmVar, xe xeVar, nm<xl> nmVar) {
        this.zzcgV = xeVar;
        this.zzcil = xmVar;
        this.zzcim = nmVar;
    }

    private final void zzJe() {
        if (this.zzcim == null) {
            if (!this.zzcgV.equals(xg.zzJh())) {
                ArrayList arrayList = new ArrayList();
                boolean z = false;
                for (xl xlVar : this.zzcil) {
                    z = z || this.zzcgV.zzi(xlVar.zzFn());
                    arrayList.add(new xl(xlVar.zzJk(), xlVar.zzFn()));
                }
                if (z) {
                    this.zzcim = new nm<>(arrayList, this.zzcgV);
                    return;
                }
            }
            this.zzcim = zzcik;
        }
    }

    public static xf zza(xm xmVar, xe xeVar) {
        return new xf(xmVar, xeVar);
    }

    public static xf zzj(xm xmVar) {
        return new xf(xmVar, xr.zzJl());
    }

    public final Iterator<xl> iterator() {
        zzJe();
        return this.zzcim == zzcik ? this.zzcil.iterator() : this.zzcim.iterator();
    }

    public final xm zzFn() {
        return this.zzcil;
    }

    public final Iterator<xl> zzFz() {
        zzJe();
        return this.zzcim == zzcik ? this.zzcil.zzFz() : this.zzcim.zzFz();
    }

    public final xl zzJf() {
        if (!(this.zzcil instanceof wr)) {
            return null;
        }
        zzJe();
        if (this.zzcim != zzcik) {
            return this.zzcim.zzFB();
        }
        wp zzIS = ((wr) this.zzcil).zzIS();
        return new xl(zzIS, this.zzcil.zzm(zzIS));
    }

    public final xl zzJg() {
        if (!(this.zzcil instanceof wr)) {
            return null;
        }
        zzJe();
        if (this.zzcim != zzcik) {
            return this.zzcim.zzFC();
        }
        wp zzIT = ((wr) this.zzcil).zzIT();
        return new xl(zzIT, this.zzcil.zzm(zzIT));
    }

    public final wp zza(wp wpVar, xm xmVar, xe xeVar) {
        if (this.zzcgV.equals(xg.zzJh()) || this.zzcgV.equals(xeVar)) {
            zzJe();
            if (this.zzcim == zzcik) {
                return this.zzcil.zzl(wpVar);
            }
            xl zzae = this.zzcim.zzae(new xl(wpVar, xmVar));
            if (zzae != null) {
                return zzae.zzJk();
            }
            return null;
        }
        throw new IllegalArgumentException("Index not available in IndexedNode!");
    }

    public final xf zzg(wp wpVar, xm xmVar) {
        xm zze = this.zzcil.zze(wpVar, xmVar);
        if (this.zzcim == zzcik && !this.zzcgV.zzi(xmVar)) {
            return new xf(zze, this.zzcgV, zzcik);
        }
        if (this.zzcim == null || this.zzcim == zzcik) {
            return new xf(zze, this.zzcgV, (nm<xl>) null);
        }
        nm<xl> zzac = this.zzcim.zzac(new xl(wpVar, this.zzcil.zzm(wpVar)));
        if (!xmVar.isEmpty()) {
            zzac = zzac.zzad(new xl(wpVar, xmVar));
        }
        return new xf(zze, this.zzcgV, zzac);
    }

    public final xf zzk(xm xmVar) {
        return new xf(this.zzcil.zzf(xmVar), this.zzcgV, this.zzcim);
    }
}
