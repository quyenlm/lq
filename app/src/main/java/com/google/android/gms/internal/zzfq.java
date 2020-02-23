package com.google.android.gms.internal;

import com.google.android.gms.ads.internal.js.zzai;
import org.json.JSONObject;

@zzzn
public final class zzfq implements zzgd {
    /* access modifiers changed from: private */
    public final zzfi zzxn;
    private final zzai zzxo;
    private final zzrd zzxp = new zzfr(this);
    private final zzrd zzxq = new zzfs(this);
    private final zzrd zzxr = new zzft(this);

    public zzfq(zzfi zzfi, zzai zzai) {
        this.zzxn = zzfi;
        this.zzxo = zzai;
        zzai zzai2 = this.zzxo;
        zzai2.zza("/updateActiveView", this.zzxp);
        zzai2.zza("/untrackActiveViewUnit", this.zzxq);
        zzai2.zza("/visibilityChanged", this.zzxr);
        String valueOf = String.valueOf(this.zzxn.zzwQ.zzcm());
        zzafr.zzaC(valueOf.length() != 0 ? "Custom JS tracking ad unit: ".concat(valueOf) : new String("Custom JS tracking ad unit: "));
    }

    public final void zzb(JSONObject jSONObject, boolean z) {
        if (!z) {
            this.zzxo.zza("AFMA_updateActiveView", jSONObject);
        } else {
            this.zzxn.zzb((zzgd) this);
        }
    }

    public final boolean zzcy() {
        return true;
    }

    public final void zzcz() {
        zzai zzai = this.zzxo;
        zzai.zzb("/visibilityChanged", this.zzxr);
        zzai.zzb("/untrackActiveViewUnit", this.zzxq);
        zzai.zzb("/updateActiveView", this.zzxp);
    }
}
