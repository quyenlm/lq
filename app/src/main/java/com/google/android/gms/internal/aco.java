package com.google.android.gms.internal;

import java.io.IOException;

public final class aco extends adj<aco> {
    private String moduleId = "";
    public int version = 0;
    private long zzcrg = 0;
    private long zzcrh = 0;
    private String zzcri = "";

    public aco() {
        this.zzcso = null;
        this.zzcsx = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof aco)) {
            return false;
        }
        aco aco = (aco) obj;
        if (this.zzcrg != aco.zzcrg) {
            return false;
        }
        if (this.zzcrh != aco.zzcrh) {
            return false;
        }
        if (this.version != aco.version) {
            return false;
        }
        if (this.zzcri == null) {
            if (aco.zzcri != null) {
                return false;
            }
        } else if (!this.zzcri.equals(aco.zzcri)) {
            return false;
        }
        if (this.moduleId == null) {
            if (aco.moduleId != null) {
                return false;
            }
        } else if (!this.moduleId.equals(aco.moduleId)) {
            return false;
        }
        return (this.zzcso == null || this.zzcso.isEmpty()) ? aco.zzcso == null || aco.zzcso.isEmpty() : this.zzcso.equals(aco.zzcso);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((this.moduleId == null ? 0 : this.moduleId.hashCode()) + (((this.zzcri == null ? 0 : this.zzcri.hashCode()) + ((((((((getClass().getName().hashCode() + 527) * 31) + ((int) (this.zzcrg ^ (this.zzcrg >>> 32)))) * 31) + ((int) (this.zzcrh ^ (this.zzcrh >>> 32)))) * 31) + this.version) * 31)) * 31)) * 31;
        if (this.zzcso != null && !this.zzcso.isEmpty()) {
            i = this.zzcso.hashCode();
        }
        return hashCode + i;
    }

    public final /* synthetic */ adp zza(adg adg) throws IOException {
        while (true) {
            int zzLA = adg.zzLA();
            switch (zzLA) {
                case 0:
                    break;
                case 8:
                    this.zzcrg = adg.zzLG();
                    continue;
                case 16:
                    this.zzcrh = adg.zzLG();
                    continue;
                case 24:
                    this.version = adg.zzLF();
                    continue;
                case 34:
                    this.zzcri = adg.readString();
                    continue;
                case 42:
                    this.moduleId = adg.readString();
                    continue;
                default:
                    if (!super.zza(adg, zzLA)) {
                        break;
                    } else {
                        continue;
                    }
            }
        }
        return this;
    }

    public final void zza(adh adh) throws IOException {
        if (this.zzcrg != 0) {
            adh.zzb(1, this.zzcrg);
        }
        if (this.zzcrh != 0) {
            adh.zzb(2, this.zzcrh);
        }
        if (this.version != 0) {
            adh.zzr(3, this.version);
        }
        if (this.zzcri != null && !this.zzcri.equals("")) {
            adh.zzl(4, this.zzcri);
        }
        if (this.moduleId != null && !this.moduleId.equals("")) {
            adh.zzl(5, this.moduleId);
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int zzn = super.zzn();
        if (this.zzcrg != 0) {
            zzn += adh.zze(1, this.zzcrg);
        }
        if (this.zzcrh != 0) {
            zzn += adh.zze(2, this.zzcrh);
        }
        if (this.version != 0) {
            zzn += adh.zzs(3, this.version);
        }
        if (this.zzcri != null && !this.zzcri.equals("")) {
            zzn += adh.zzm(4, this.zzcri);
        }
        return (this.moduleId == null || this.moduleId.equals("")) ? zzn : zzn + adh.zzm(5, this.moduleId);
    }
}
