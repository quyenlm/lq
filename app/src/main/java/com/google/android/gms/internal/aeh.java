package com.google.android.gms.internal;

import java.io.IOException;

public final class aeh extends adj<aeh> implements Cloneable {
    private String version = "";
    private int zzbpb = 0;
    private String zzctL = "";

    public aeh() {
        this.zzcso = null;
        this.zzcsx = -1;
    }

    /* access modifiers changed from: private */
    /* renamed from: zzMb */
    public aeh clone() {
        try {
            return (aeh) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof aeh)) {
            return false;
        }
        aeh aeh = (aeh) obj;
        if (this.zzbpb != aeh.zzbpb) {
            return false;
        }
        if (this.zzctL == null) {
            if (aeh.zzctL != null) {
                return false;
            }
        } else if (!this.zzctL.equals(aeh.zzctL)) {
            return false;
        }
        if (this.version == null) {
            if (aeh.version != null) {
                return false;
            }
        } else if (!this.version.equals(aeh.version)) {
            return false;
        }
        return (this.zzcso == null || this.zzcso.isEmpty()) ? aeh.zzcso == null || aeh.zzcso.isEmpty() : this.zzcso.equals(aeh.zzcso);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((this.version == null ? 0 : this.version.hashCode()) + (((this.zzctL == null ? 0 : this.zzctL.hashCode()) + ((((getClass().getName().hashCode() + 527) * 31) + this.zzbpb) * 31)) * 31)) * 31;
        if (this.zzcso != null && !this.zzcso.isEmpty()) {
            i = this.zzcso.hashCode();
        }
        return hashCode + i;
    }

    public final /* synthetic */ adj zzLN() throws CloneNotSupportedException {
        return (aeh) clone();
    }

    public final /* synthetic */ adp zzLO() throws CloneNotSupportedException {
        return (aeh) clone();
    }

    public final /* synthetic */ adp zza(adg adg) throws IOException {
        while (true) {
            int zzLA = adg.zzLA();
            switch (zzLA) {
                case 0:
                    break;
                case 8:
                    this.zzbpb = adg.zzLC();
                    continue;
                case 18:
                    this.zzctL = adg.readString();
                    continue;
                case 26:
                    this.version = adg.readString();
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
        if (this.zzbpb != 0) {
            adh.zzr(1, this.zzbpb);
        }
        if (this.zzctL != null && !this.zzctL.equals("")) {
            adh.zzl(2, this.zzctL);
        }
        if (this.version != null && !this.version.equals("")) {
            adh.zzl(3, this.version);
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int zzn = super.zzn();
        if (this.zzbpb != 0) {
            zzn += adh.zzs(1, this.zzbpb);
        }
        if (this.zzctL != null && !this.zzctL.equals("")) {
            zzn += adh.zzm(2, this.zzctL);
        }
        return (this.version == null || this.version.equals("")) ? zzn : zzn + adh.zzm(3, this.version);
    }
}
