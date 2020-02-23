package com.google.android.gms.internal;

import android.content.SharedPreferences;
import org.json.JSONObject;

final class zzmf extends zzme<Boolean> {
    zzmf(int i, String str, Boolean bool) {
        super(i, str, bool, (zzmf) null);
    }

    public final /* synthetic */ Object zza(SharedPreferences sharedPreferences) {
        return Boolean.valueOf(sharedPreferences.getBoolean(getKey(), ((Boolean) zzdI()).booleanValue()));
    }

    public final /* synthetic */ void zza(SharedPreferences.Editor editor, Object obj) {
        editor.putBoolean(getKey(), ((Boolean) obj).booleanValue());
    }

    public final /* synthetic */ Object zzb(JSONObject jSONObject) {
        return Boolean.valueOf(jSONObject.optBoolean(getKey(), ((Boolean) zzdI()).booleanValue()));
    }
}
