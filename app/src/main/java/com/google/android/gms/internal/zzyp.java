package com.google.android.gms.internal;

import android.text.TextUtils;
import com.google.android.gms.ads.internal.js.zzai;
import com.google.android.gms.common.internal.zzbo;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

final class zzyp implements zzrd {
    private /* synthetic */ zzai zzIj;
    private /* synthetic */ zzyo zzRG;

    zzyp(zzyo zzyo, zzai zzai) {
        this.zzRG = zzyo;
        this.zzIj = zzai;
    }

    public final void zza(zzaka zzaka, Map<String, String> map) {
        JSONObject jSONObject;
        boolean z;
        try {
            String str = map.get("success");
            String str2 = map.get("failure");
            if (!TextUtils.isEmpty(str2)) {
                z = false;
                jSONObject = new JSONObject(str2);
            } else {
                jSONObject = new JSONObject(str);
                z = true;
            }
            if (this.zzRG.zzRC.equals(jSONObject.optString("ads_id", ""))) {
                this.zzIj.zzb("/nativeAdPreProcess", this.zzRG.zzRD.zzRW);
                if (z) {
                    JSONArray optJSONArray = jSONObject.optJSONArray("ads");
                    if (optJSONArray == null || optJSONArray.length() <= 0) {
                        this.zzRG.zzRF.zzt(3);
                        this.zzRG.zzRE.zzg(null);
                        return;
                    }
                    this.zzRG.zzRE.zzg(optJSONArray.getJSONObject(0));
                    return;
                }
                this.zzRG.zzRF.zzt(0);
                zzbo.zza(this.zzRG.zzRF.zzgx(), (Object) "Unable to set the ad state error!");
                this.zzRG.zzRE.zzg(null);
            }
        } catch (JSONException e) {
            zzafr.zzb("Malformed native JSON response.", e);
        }
    }
}
