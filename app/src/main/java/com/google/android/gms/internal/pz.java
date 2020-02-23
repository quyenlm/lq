package com.google.android.gms.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class pz implements Iterable<Map.Entry<qr, xm>> {
    private static final pz zzccF = new pz(new uv(null));
    private final uv<xm> zzccG;

    private pz(uv<xm> uvVar) {
        this.zzccG = uvVar;
    }

    public static pz zzD(Map<String, Object> map) {
        uv zzHR = uv.zzHR();
        Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
        while (true) {
            uv uvVar = zzHR;
            if (!it.hasNext()) {
                return new pz(uvVar);
            }
            Map.Entry next = it.next();
            zzHR = uvVar.zza(new qr((String) next.getKey()), new uv(xp.zza(next.getValue(), xd.zzJb())));
        }
    }

    public static pz zzE(Map<qr, xm> map) {
        uv zzHR = uv.zzHR();
        Iterator<Map.Entry<qr, xm>> it = map.entrySet().iterator();
        while (true) {
            uv uvVar = zzHR;
            if (!it.hasNext()) {
                return new pz(uvVar);
            }
            Map.Entry next = it.next();
            zzHR = uvVar.zza((qr) next.getKey(), new uv((xm) next.getValue()));
        }
    }

    public static pz zzGI() {
        return zzccF;
    }

    private final xm zza(qr qrVar, uv<xm> uvVar, xm xmVar) {
        if (uvVar.getValue() != null) {
            return xmVar.zzl(qrVar, uvVar.getValue());
        }
        Iterator<Map.Entry<wp, uv<xm>>> it = uvVar.zzHS().iterator();
        xm xmVar2 = null;
        while (it.hasNext()) {
            Map.Entry next = it.next();
            uv uvVar2 = (uv) next.getValue();
            wp wpVar = (wp) next.getKey();
            if (wpVar.zzIN()) {
                xmVar2 = (xm) uvVar2.getValue();
            } else {
                xmVar = zza(qrVar.zza(wpVar), uvVar2, xmVar);
            }
        }
        return (xmVar.zzN(qrVar).isEmpty() || xmVar2 == null) ? xmVar : xmVar.zzl(qrVar.zza(wp.zzIL()), xmVar2);
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        return ((pz) obj).zzaD(true).equals(zzaD(true));
    }

    public final int hashCode() {
        return zzaD(true).hashCode();
    }

    public final boolean isEmpty() {
        return this.zzccG.isEmpty();
    }

    public final Iterator<Map.Entry<qr, xm>> iterator() {
        return this.zzccG.iterator();
    }

    public final String toString() {
        String valueOf = String.valueOf(zzaD(true).toString());
        return new StringBuilder(String.valueOf(valueOf).length() + 15).append("CompoundWrite{").append(valueOf).append("}").toString();
    }

    public final xm zzGJ() {
        return this.zzccG.getValue();
    }

    public final List<xl> zzGK() {
        ArrayList arrayList = new ArrayList();
        if (this.zzccG.getValue() != null) {
            for (xl xlVar : this.zzccG.getValue()) {
                arrayList.add(new xl(xlVar.zzJk(), xlVar.zzFn()));
            }
        } else {
            Iterator<Map.Entry<wp, uv<xm>>> it = this.zzccG.zzHS().iterator();
            while (it.hasNext()) {
                Map.Entry next = it.next();
                uv uvVar = (uv) next.getValue();
                if (uvVar.getValue() != null) {
                    arrayList.add(new xl((wp) next.getKey(), (xm) uvVar.getValue()));
                }
            }
        }
        return arrayList;
    }

    public final Map<wp, pz> zzGL() {
        HashMap hashMap = new HashMap();
        Iterator<Map.Entry<wp, uv<xm>>> it = this.zzccG.zzHS().iterator();
        while (it.hasNext()) {
            Map.Entry next = it.next();
            hashMap.put((wp) next.getKey(), new pz((uv) next.getValue()));
        }
        return hashMap;
    }

    public final pz zza(wp wpVar, xm xmVar) {
        return zze(new qr(wpVar), xmVar);
    }

    public final Map<String, Object> zzaD(boolean z) {
        HashMap hashMap = new HashMap();
        this.zzccG.zza(new qb(this, hashMap, true));
        return hashMap;
    }

    public final pz zzb(qr qrVar, pz pzVar) {
        return (pz) pzVar.zzccG.zzb(this, new qa(this, qrVar));
    }

    public final xm zzb(xm xmVar) {
        return zza(qr.zzGZ(), this.zzccG, xmVar);
    }

    public final pz zzd(qr qrVar) {
        return qrVar.isEmpty() ? zzccF : new pz(this.zzccG.zza(qrVar, uv.zzHR()));
    }

    public final pz zze(qr qrVar, xm xmVar) {
        if (qrVar.isEmpty()) {
            return new pz(new uv(xmVar));
        }
        qr zzF = this.zzccG.zzF(qrVar);
        if (zzF != null) {
            qr zza = qr.zza(zzF, qrVar);
            xm zzJ = this.zzccG.zzJ(zzF);
            wp zzHf = zza.zzHf();
            if (zzHf != null && zzHf.zzIN() && zzJ.zzN(zza.zzHe()).isEmpty()) {
                return this;
            }
            return new pz(this.zzccG.zzb(zzF, zzJ.zzl(zza, xmVar)));
        }
        return new pz(this.zzccG.zza(qrVar, new uv(xmVar)));
    }

    public final boolean zze(qr qrVar) {
        return zzf(qrVar) != null;
    }

    public final xm zzf(qr qrVar) {
        qr zzF = this.zzccG.zzF(qrVar);
        if (zzF != null) {
            return this.zzccG.zzJ(zzF).zzN(qr.zza(zzF, qrVar));
        }
        return null;
    }

    public final pz zzg(qr qrVar) {
        if (qrVar.isEmpty()) {
            return this;
        }
        xm zzf = zzf(qrVar);
        return zzf != null ? new pz(new uv(zzf)) : new pz(this.zzccG.zzH(qrVar));
    }
}
