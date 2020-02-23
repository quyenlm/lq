package com.google.android.gms.ads.internal;

import android.text.TextUtils;
import com.google.android.gms.ads.internal.js.zzai;
import com.google.android.gms.internal.zzafr;
import com.google.android.gms.internal.zzajs;
import org.json.JSONObject;

final class zzaf implements zzajs<zzai> {
    private /* synthetic */ zzae zztG;

    zzaf(zzae zzae) {
        this.zztG = zzae;
    }

    public final /* synthetic */ void zzc(Object obj) {
        zzai zzai = (zzai) obj;
        zzai.zza("/appSettingsFetched", this.zztG.zztB);
        try {
            JSONObject jSONObject = new JSONObject();
            if (!TextUtils.isEmpty(this.zztG.zztC)) {
                jSONObject.put("app_id", this.zztG.zztC);
            } else if (!TextUtils.isEmpty(this.zztG.zztD)) {
                jSONObject.put("ad_unit_id", this.zztG.zztD);
            }
            jSONObject.put("is_init", this.zztG.zztE);
            jSONObject.put("pn", this.zztG.zztF.getPackageName());
            zzai.zza("AFMA_fetchAppSettings", jSONObject);
        } catch (Exception e) {
            zzai.zzb("/appSettingsFetched", this.zztG.zztB);
            zzafr.zzb("Error requesting application settings", e);
        }
    }
}
