package com.google.android.gms.internal;

import android.support.v4.util.SimpleArrayMap;
import android.view.View;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@zzzn
public final class zzzh implements zzyv<zznu> {
    private final boolean zzSe;

    public zzzh(boolean z) {
        this.zzSe = z;
    }

    private static <K, V> SimpleArrayMap<K, V> zza(SimpleArrayMap<K, Future<V>> simpleArrayMap) throws InterruptedException, ExecutionException {
        SimpleArrayMap<K, V> simpleArrayMap2 = new SimpleArrayMap<>();
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= simpleArrayMap.size()) {
                return simpleArrayMap2;
            }
            simpleArrayMap2.put(simpleArrayMap.keyAt(i2), simpleArrayMap.valueAt(i2).get());
            i = i2 + 1;
        }
    }

    public final /* synthetic */ zzoa zza(zzyn zzyn, JSONObject jSONObject) throws JSONException, InterruptedException, ExecutionException {
        View view = null;
        SimpleArrayMap simpleArrayMap = new SimpleArrayMap();
        SimpleArrayMap simpleArrayMap2 = new SimpleArrayMap();
        zzajm<zznn> zzd = zzyn.zzd(jSONObject);
        zzajm<zzaka> zzc = zzyn.zzc(jSONObject, "video");
        JSONArray jSONArray = jSONObject.getJSONArray("custom_assets");
        for (int i = 0; i < jSONArray.length(); i++) {
            JSONObject jSONObject2 = jSONArray.getJSONObject(i);
            String string = jSONObject2.getString("type");
            if ("string".equals(string)) {
                simpleArrayMap2.put(jSONObject2.getString("name"), jSONObject2.getString("string_value"));
            } else if ("image".equals(string)) {
                simpleArrayMap.put(jSONObject2.getString("name"), zzyn.zza(jSONObject2, "image_value", this.zzSe));
            } else {
                String valueOf = String.valueOf(string);
                zzafr.zzaT(valueOf.length() != 0 ? "Unknown custom asset type: ".concat(valueOf) : new String("Unknown custom asset type: "));
            }
        }
        zzaka zzb = zzyn.zzb(zzc);
        String string2 = jSONObject.getString("custom_template_id");
        SimpleArrayMap zza = zza(simpleArrayMap);
        zznn zznn = (zznn) zzd.get();
        zzaks zziH = zzb != null ? zzb.zziH() : null;
        if (zzb != null) {
            view = zzb.getView();
        }
        return new zznu(string2, zza, simpleArrayMap2, zznn, zziH, view);
    }
}
