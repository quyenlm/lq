package com.google.android.gms.internal;

import android.os.Bundle;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.json.JSONException;
import org.json.JSONObject;

@zzzn
public final class zzzf implements zzyv<zznq> {
    private final boolean zzSe;
    private final boolean zzSf;

    public zzzf(boolean z, boolean z2) {
        this.zzSe = z;
        this.zzSf = z2;
    }

    public final /* synthetic */ zzoa zza(zzyn zzyn, JSONObject jSONObject) throws JSONException, InterruptedException, ExecutionException {
        List<zzajm<zznp>> zza = zzyn.zza(jSONObject, "images", false, this.zzSe, this.zzSf);
        zzajm<zznp> zza2 = zzyn.zza(jSONObject, "app_icon", true, this.zzSe);
        zzajm<zzaka> zzc = zzyn.zzc(jSONObject, "video");
        zzajm<zznn> zzd = zzyn.zzd(jSONObject);
        ArrayList arrayList = new ArrayList();
        for (zzajm<zznp> zzajm : zza) {
            arrayList.add((zznp) zzajm.get());
        }
        zzaka zzb = zzyn.zzb(zzc);
        return new zznq(jSONObject.getString("headline"), arrayList, jSONObject.getString("body"), (zzos) zza2.get(), jSONObject.getString("call_to_action"), jSONObject.optDouble("rating", -1.0d), jSONObject.optString("store"), jSONObject.optString(FirebaseAnalytics.Param.PRICE), (zznn) zzd.get(), new Bundle(), zzb != null ? zzb.zziH() : null, zzb != null ? zzb.getView() : null);
    }
}
