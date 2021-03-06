package com.google.android.gms.internal;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import com.google.android.gms.ads.internal.zzbs;
import com.tencent.midas.oversea.comm.APGlobalInfo;
import org.apache.http.client.methods.HttpHead;
import org.json.JSONException;
import org.json.JSONObject;

@zzzn
public final class zzacg extends zzack {
    private final Object mLock = new Object();
    @Nullable
    private SharedPreferences zzWi;

    /* access modifiers changed from: private */
    public final void zzgN() {
        this.zzWi.edit().putLong("js_last_update", zzbs.zzbF().currentTimeMillis()).apply();
    }

    public final void zza(Context context, zzabu zzabu, zzaje zzaje) {
        synchronized (this.mLock) {
            if (this.zzWi == null) {
                this.zzWi = context.getSharedPreferences("google_ads_flags_meta", 0);
            }
        }
        if (zzbs.zzbF().currentTimeMillis() - this.zzWi.getLong("js_last_update", 0) < ((Long) zzbs.zzbL().zzd(zzmo.zzFm)).longValue()) {
            zze(zzabu);
            return;
        }
        zzach zzach = new zzach(this, context, zzabu);
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("js", zzaje.zzaP);
            jSONObject.put("mf", zzbs.zzbL().zzd(zzmo.zzFn));
            jSONObject.put("cl", "162978962");
            jSONObject.put("rapid_rc", APGlobalInfo.DevEnv);
            jSONObject.put("rapid_rollup", HttpHead.METHOD_NAME);
            zzabu.zzUN.zza(new zzaci(this, zzach, jSONObject, zzabu), new zzacj(this, zzabu));
        } catch (JSONException e) {
            zzafr.zzb("Unable to populate SDK Core Constants parameters.", e);
            zze(zzabu);
        }
    }
}
