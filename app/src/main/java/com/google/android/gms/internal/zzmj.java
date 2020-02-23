package com.google.android.gms.internal;

import android.content.SharedPreferences;
import org.json.JSONObject;

final class zzmj extends zzme<String> {
    zzmj(int i, String str, String str2) {
        super(i, str, str2, (zzmf) null);
    }

    public final /* synthetic */ Object zza(SharedPreferences sharedPreferences) {
        return sharedPreferences.getString(getKey(), (String) zzdI());
    }

    public final /* synthetic */ void zza(SharedPreferences.Editor editor, Object obj) {
        editor.putString(getKey(), (String) obj);
    }

    public final /* synthetic */ Object zzb(JSONObject jSONObject) {
        return jSONObject.optString(getKey(), (String) zzdI());
    }
}
