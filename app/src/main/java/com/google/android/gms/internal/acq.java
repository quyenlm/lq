package com.google.android.gms.internal;

import java.io.IOException;

public final class acq extends adj<acq> {
    private String packageName = "";
    private int zzcqq = 0;

    public acq() {
        this.zzcsx = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof acq)) {
            return false;
        }
        acq acq = (acq) obj;
        if (this.zzcqq != acq.zzcqq) {
            return false;
        }
        if (this.packageName == null) {
            if (acq.packageName != null) {
                return false;
            }
        } else if (!this.packageName.equals(acq.packageName)) {
            return false;
        }
        return (this.zzcso == null || this.zzcso.isEmpty()) ? acq.zzcso == null || acq.zzcso.isEmpty() : this.zzcso.equals(acq.zzcso);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((this.packageName == null ? 0 : this.packageName.hashCode()) + ((((getClass().getName().hashCode() + 527) * 31) + this.zzcqq) * 31)) * 31;
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
                    int position = adg.getPosition();
                    int zzLF = adg.zzLF();
                    switch (zzLF) {
                        case 0:
                        case 1:
                        case 2:
                            this.zzcqq = zzLF;
                            break;
                        default:
                            adg.zzcp(position);
                            zza(adg, zzLA);
                            continue;
                    }
                case 18:
                    this.packageName = adg.readString();
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
        if (this.zzcqq != 0) {
            adh.zzr(1, this.zzcqq);
        }
        if (this.packageName != null && !this.packageName.equals("")) {
            adh.zzl(2, this.packageName);
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int zzn = super.zzn();
        if (this.zzcqq != 0) {
            zzn += adh.zzs(1, this.zzcqq);
        }
        return (this.packageName == null || this.packageName.equals("")) ? zzn : zzn + adh.zzm(2, this.packageName);
    }
}
