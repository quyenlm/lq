package com.google.android.gms.internal;

import com.google.android.gms.ads.internal.js.zzai;
import org.json.JSONObject;

final class zzaci implements zzajs<zzai> {
    private /* synthetic */ zzabu zzUA;
    private /* synthetic */ zzacg zzWj;
    private /* synthetic */ zzrd zzWk;
    private /* synthetic */ JSONObject zzWl;

    zzaci(zzacg zzacg, zzrd zzrd, JSONObject jSONObject, zzabu zzabu) {
        this.zzWj = zzacg;
        this.zzWk = zzrd;
        this.zzWl = jSONObject;
        this.zzUA = zzabu;
    }

    public final /* synthetic */ void zzc(Object obj) {
        zzai zzai = (zzai) obj;
        zzai.zza("/loadSdkConstants", this.zzWk);
        try {
            zzai.zza("AFMA_getSdkConstants", this.zzWl);
        } catch (Exception e) {
            zzafr.zzb("Error requesting an ad url", e);
            zzacg.zze(this.zzUA);
        }
    }
}
