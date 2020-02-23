package com.google.android.gms.internal;

import com.google.android.gms.ads.internal.js.zzai;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

final class zzol implements zzrd {
    private /* synthetic */ zzai zzIj;
    private /* synthetic */ zzog zzIk;

    zzol(zzog zzog, zzai zzai) {
        this.zzIk = zzog;
        this.zzIj = zzai;
    }

    public final void zza(zzaka zzaka, Map<String, String> map) {
        JSONObject jSONObject = new JSONObject();
        try {
            for (String next : map.keySet()) {
                jSONObject.put(next, map.get(next));
            }
            jSONObject.put("id", this.zzIk.zzIi);
            this.zzIj.zzb("sendMessageToNativeJs", jSONObject);
        } catch (JSONException e) {
            zzafr.zzb("Unable to dispatch sendMessageToNativeJs event", e);
        }
    }
}
