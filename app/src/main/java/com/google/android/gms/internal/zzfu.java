package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.ads.internal.js.zzai;
import com.google.android.gms.ads.internal.js.zzl;
import com.google.android.gms.ads.internal.js.zzy;
import com.google.android.gms.ads.internal.zzbs;
import org.json.JSONObject;

@zzzn
public final class zzfu implements zzgd {
    private final Context mContext;
    /* access modifiers changed from: private */
    public final zzfi zzxn;
    private final zzrd zzxp = new zzfz(this);
    private final zzrd zzxq = new zzga(this);
    private final zzrd zzxr = new zzgb(this);
    private zzy zzxt;
    /* access modifiers changed from: private */
    public boolean zzxu;
    private final zzrd zzxv = new zzgc(this);

    public zzfu(zzfi zzfi, zzl zzl, Context context) {
        this.zzxn = zzfi;
        this.mContext = context;
        this.zzxt = zzl.zzb((zzcu) null);
        this.zzxt.zza(new zzfv(this), new zzfw(this));
        String valueOf = String.valueOf(this.zzxn.zzwQ.zzcm());
        zzafr.zzaC(valueOf.length() != 0 ? "Core JS tracking ad unit: ".concat(valueOf) : new String("Core JS tracking ad unit: "));
    }

    /* access modifiers changed from: package-private */
    public final void zzb(zzai zzai) {
        zzai.zza("/updateActiveView", this.zzxp);
        zzai.zza("/untrackActiveViewUnit", this.zzxq);
        zzai.zza("/visibilityChanged", this.zzxr);
        if (zzbs.zzbY().zzp(this.mContext)) {
            zzai.zza("/logScionEvent", this.zzxv);
        }
    }

    public final void zzb(JSONObject jSONObject, boolean z) {
        this.zzxt.zza(new zzfx(this, jSONObject), new zzajr());
    }

    /* access modifiers changed from: package-private */
    public final void zzc(zzai zzai) {
        zzai.zzb("/visibilityChanged", this.zzxr);
        zzai.zzb("/untrackActiveViewUnit", this.zzxq);
        zzai.zzb("/updateActiveView", this.zzxp);
        if (zzbs.zzbY().zzp(this.mContext)) {
            zzai.zzb("/logScionEvent", this.zzxv);
        }
    }

    public final boolean zzcy() {
        return this.zzxu;
    }

    public final void zzcz() {
        this.zzxt.zza(new zzfy(this), new zzajr());
        this.zzxt.release();
    }
}
