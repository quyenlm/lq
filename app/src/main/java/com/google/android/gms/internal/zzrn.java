package com.google.android.gms.internal;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;
import org.json.JSONException;
import org.json.JSONObject;

@zzzn
public final class zzrn implements zzrd {
    private HashMap<String, zzajg<JSONObject>> zzJD = new HashMap<>();

    public final Future<JSONObject> zzS(String str) {
        zzajg zzajg = new zzajg();
        this.zzJD.put(str, zzajg);
        return zzajg;
    }

    public final void zzT(String str) {
        zzajg zzajg = this.zzJD.get(str);
        if (zzajg == null) {
            zzafr.e("Could not find the ad request for the corresponding ad response.");
            return;
        }
        if (!zzajg.isDone()) {
            zzajg.cancel(true);
        }
        this.zzJD.remove(str);
    }

    public final void zza(zzaka zzaka, Map<String, String> map) {
        String str = map.get("request_id");
        String str2 = map.get("fetched_ad");
        zzafr.zzaC("Received ad from the cache.");
        zzajg zzajg = this.zzJD.get(str);
        if (zzajg == null) {
            zzafr.e("Could not find the ad request for the corresponding ad response.");
            return;
        }
        try {
            zzajg.zzg(new JSONObject(str2));
        } catch (JSONException e) {
            zzafr.zzb("Failed constructing JSON object from value passed from javascript", e);
            zzajg.zzg(null);
        } finally {
            this.zzJD.remove(str);
        }
    }
}
