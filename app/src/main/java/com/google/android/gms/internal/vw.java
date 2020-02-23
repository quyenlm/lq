package com.google.android.gms.internal;

public final class vw {
    private final vg zzchr;
    private final vg zzchs;

    public vw(vg vgVar, vg vgVar2) {
        this.zzchr = vgVar;
        this.zzchs = vgVar2;
    }

    public final vg zzIA() {
        return this.zzchs;
    }

    public final xm zzIB() {
        if (this.zzchs.zzHU()) {
            return this.zzchs.zzFn();
        }
        return null;
    }

    public final vg zzIy() {
        return this.zzchr;
    }

    public final xm zzIz() {
        if (this.zzchr.zzHU()) {
            return this.zzchr.zzFn();
        }
        return null;
    }

    public final vw zza(xf xfVar, boolean z, boolean z2) {
        return new vw(new vg(xfVar, z, z2), this.zzchs);
    }

    public final vw zzb(xf xfVar, boolean z, boolean z2) {
        return new vw(this.zzchr, new vg(xfVar, z, z2));
    }
}
