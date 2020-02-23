package com.google.android.gms.internal;

import java.util.Map;

public final class vb<T> {
    private vf<T> zzcgA;
    private wp zzcgy;
    private vb<T> zzcgz;

    public vb() {
        this((wp) null, (vb) null, new vf());
    }

    private vb(wp wpVar, vb<T> vbVar, vf<T> vfVar) {
        this.zzcgy = wpVar;
        this.zzcgz = vbVar;
        this.zzcgA = vfVar;
    }

    private final void zzHT() {
        vb<T> vbVar;
        while (true) {
            vb<T> vbVar2 = vbVar;
            if (vbVar2.zzcgz != null) {
                vbVar = vbVar2.zzcgz;
                wp wpVar = vbVar2.zzcgy;
                boolean z = vbVar2.zzcgA.value == null && vbVar2.zzcgA.zzceA.isEmpty();
                boolean containsKey = vbVar.zzcgA.zzceA.containsKey(wpVar);
                if (z && containsKey) {
                    vbVar.zzcgA.zzceA.remove(wpVar);
                } else if (!z && !containsKey) {
                    vbVar.zzcgA.zzceA.put(wpVar, vbVar2.zzcgA);
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }

    public final T getValue() {
        return this.zzcgA.value;
    }

    public final boolean hasChildren() {
        return !this.zzcgA.zzceA.isEmpty();
    }

    public final void setValue(T t) {
        this.zzcgA.value = t;
        zzHT();
    }

    public final String toString() {
        String asString = this.zzcgy == null ? "<anon>" : this.zzcgy.asString();
        String valueOf = String.valueOf(this.zzcgA.toString(String.valueOf("").concat("\t")));
        return new StringBuilder(String.valueOf("").length() + 1 + String.valueOf(asString).length() + String.valueOf(valueOf).length()).append("").append(asString).append("\n").append(valueOf).toString();
    }

    public final qr zzFq() {
        if (this.zzcgz != null) {
            return this.zzcgz.zzFq().zza(this.zzcgy);
        }
        if (this.zzcgy == null) {
            return qr.zzGZ();
        }
        return new qr(this.zzcgy);
    }

    public final vb<T> zzK(qr qrVar) {
        wp zzHc = qrVar.zzHc();
        while (zzHc != null) {
            vb<T> vbVar = new vb<>(zzHc, this, this.zzcgA.zzceA.containsKey(zzHc) ? this.zzcgA.zzceA.get(zzHc) : new vf());
            qrVar = qrVar.zzHd();
            zzHc = qrVar.zzHc();
            this = vbVar;
        }
        return this;
    }

    public final void zza(ve<T> veVar) {
        Object[] array = this.zzcgA.zzceA.entrySet().toArray();
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 < array.length) {
                Map.Entry entry = (Map.Entry) array[i2];
                veVar.zzd(new vb((wp) entry.getKey(), this, (vf) entry.getValue()));
                i = i2 + 1;
            } else {
                return;
            }
        }
    }

    public final void zza(ve<T> veVar, boolean z, boolean z2) {
        if (z && !z2) {
            veVar.zzd(this);
        }
        zza(new vc(this, veVar, z2));
        if (z && z2) {
            veVar.zzd(this);
        }
    }

    public final boolean zza(vd<T> vdVar, boolean z) {
        for (vb<T> vbVar = this.zzcgz; vbVar != null; vbVar = vbVar.zzcgz) {
            vdVar.zze(vbVar);
        }
        return false;
    }
}
