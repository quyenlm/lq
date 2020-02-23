package com.google.android.gms.internal;

import com.google.android.gms.internal.adj;
import java.io.IOException;

public abstract class adj<M extends adj<M>> extends adp {
    protected adl zzcso;

    /* renamed from: zzLN */
    public M clone() throws CloneNotSupportedException {
        M m = (adj) super.clone();
        adn.zza(this, (adj) m);
        return m;
    }

    public /* synthetic */ adp zzLO() throws CloneNotSupportedException {
        return (adj) clone();
    }

    public final <T> T zza(adk<M, T> adk) {
        adm zzcx;
        if (this.zzcso == null || (zzcx = this.zzcso.zzcx(adk.tag >>> 3)) == null) {
            return null;
        }
        return zzcx.zzb(adk);
    }

    public void zza(adh adh) throws IOException {
        if (this.zzcso != null) {
            for (int i = 0; i < this.zzcso.size(); i++) {
                this.zzcso.zzcy(i).zza(adh);
            }
        }
    }

    /* access modifiers changed from: protected */
    public final boolean zza(adg adg, int i) throws IOException {
        int position = adg.getPosition();
        if (!adg.zzcm(i)) {
            return false;
        }
        int i2 = i >>> 3;
        adr adr = new adr(i, adg.zzp(position, adg.getPosition() - position));
        adm adm = null;
        if (this.zzcso == null) {
            this.zzcso = new adl();
        } else {
            adm = this.zzcso.zzcx(i2);
        }
        if (adm == null) {
            adm = new adm();
            this.zzcso.zza(i2, adm);
        }
        adm.zza(adr);
        return true;
    }

    /* access modifiers changed from: protected */
    public int zzn() {
        int i = 0;
        if (this.zzcso == null) {
            return 0;
        }
        int i2 = 0;
        while (true) {
            int i3 = i;
            if (i2 >= this.zzcso.size()) {
                return i3;
            }
            i = this.zzcso.zzcy(i2).zzn() + i3;
            i2++;
        }
    }
}
