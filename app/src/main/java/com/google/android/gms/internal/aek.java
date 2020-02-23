package com.google.android.gms.internal;

import java.io.IOException;

public final class aek extends adj<aek> implements Cloneable {
    private static volatile aek[] zzcuj;
    private String key = "";
    private String value = "";

    public aek() {
        this.zzcso = null;
        this.zzcsx = -1;
    }

    public static aek[] zzMe() {
        if (zzcuj == null) {
            synchronized (adn.zzcsw) {
                if (zzcuj == null) {
                    zzcuj = new aek[0];
                }
            }
        }
        return zzcuj;
    }

    /* access modifiers changed from: private */
    /* renamed from: zzMf */
    public aek clone() {
        try {
            return (aek) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof aek)) {
            return false;
        }
        aek aek = (aek) obj;
        if (this.key == null) {
            if (aek.key != null) {
                return false;
            }
        } else if (!this.key.equals(aek.key)) {
            return false;
        }
        if (this.value == null) {
            if (aek.value != null) {
                return false;
            }
        } else if (!this.value.equals(aek.value)) {
            return false;
        }
        return (this.zzcso == null || this.zzcso.isEmpty()) ? aek.zzcso == null || aek.zzcso.isEmpty() : this.zzcso.equals(aek.zzcso);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((this.value == null ? 0 : this.value.hashCode()) + (((this.key == null ? 0 : this.key.hashCode()) + ((getClass().getName().hashCode() + 527) * 31)) * 31)) * 31;
        if (this.zzcso != null && !this.zzcso.isEmpty()) {
            i = this.zzcso.hashCode();
        }
        return hashCode + i;
    }

    public final /* synthetic */ adj zzLN() throws CloneNotSupportedException {
        return (aek) clone();
    }

    public final /* synthetic */ adp zzLO() throws CloneNotSupportedException {
        return (aek) clone();
    }

    public final /* synthetic */ adp zza(adg adg) throws IOException {
        while (true) {
            int zzLA = adg.zzLA();
            switch (zzLA) {
                case 0:
                    break;
                case 10:
                    this.key = adg.readString();
                    continue;
                case 18:
                    this.value = adg.readString();
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
        if (this.key != null && !this.key.equals("")) {
            adh.zzl(1, this.key);
        }
        if (this.value != null && !this.value.equals("")) {
            adh.zzl(2, this.value);
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int zzn = super.zzn();
        if (this.key != null && !this.key.equals("")) {
            zzn += adh.zzm(1, this.key);
        }
        return (this.value == null || this.value.equals("")) ? zzn : zzn + adh.zzm(2, this.value);
    }
}
