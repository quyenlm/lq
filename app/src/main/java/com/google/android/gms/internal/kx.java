package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Log;
import com.garena.android.BaseTokenUpdateReceiver;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.common.util.zzi;
import org.json.JSONException;
import org.json.JSONObject;

public final class kx extends zza {
    public static final Parcelable.Creator<kx> CREATOR = new ky();
    private String zzbCx;
    private String zzbWx;
    private Long zzbXo;
    private String zzbXp;
    private Long zzbXq;

    public kx() {
        this.zzbXq = Long.valueOf(System.currentTimeMillis());
    }

    kx(String str, String str2, Long l, String str3, Long l2) {
        this.zzbWx = str;
        this.zzbCx = str2;
        this.zzbXo = l;
        this.zzbXp = str3;
        this.zzbXq = l2;
    }

    public static kx zzgu(@NonNull String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            kx kxVar = new kx();
            kxVar.zzbWx = jSONObject.optString("refresh_token", (String) null);
            kxVar.zzbCx = jSONObject.optString("access_token", (String) null);
            kxVar.zzbXo = Long.valueOf(jSONObject.optLong("expires_in"));
            kxVar.zzbXp = jSONObject.optString(BaseTokenUpdateReceiver.EXTRA_TOKEN_TYPE, (String) null);
            kxVar.zzbXq = Long.valueOf(jSONObject.optLong("issued_at"));
            return kxVar;
        } catch (JSONException e) {
            Log.d("GetTokenResponse", "Failed to read GetTokenResponse from JSONObject");
            throw new ip(e);
        }
    }

    public final String getAccessToken() {
        return this.zzbCx;
    }

    public final boolean isValid() {
        return zzi.zzrY().currentTimeMillis() + 300000 < this.zzbXq.longValue() + (this.zzbXo.longValue() * 1000);
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 2, this.zzbWx, false);
        zzd.zza(parcel, 3, this.zzbCx, false);
        zzd.zza(parcel, 4, Long.valueOf(this.zzbXo == null ? 0 : this.zzbXo.longValue()), false);
        zzd.zza(parcel, 5, this.zzbXp, false);
        zzd.zza(parcel, 6, Long.valueOf(this.zzbXq.longValue()), false);
        zzd.zzI(parcel, zze);
    }

    public final String zzES() {
        return this.zzbWx;
    }

    public final void zzgt(@NonNull String str) {
        this.zzbWx = zzbo.zzcF(str);
    }

    public final String zzmC() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("refresh_token", this.zzbWx);
            jSONObject.put("access_token", this.zzbCx);
            jSONObject.put("expires_in", this.zzbXo);
            jSONObject.put(BaseTokenUpdateReceiver.EXTRA_TOKEN_TYPE, this.zzbXp);
            jSONObject.put("issued_at", this.zzbXq);
            return jSONObject.toString();
        } catch (JSONException e) {
            Log.d("GetTokenResponse", "Failed to convert GetTokenResponse to JSON");
            throw new ip(e);
        }
    }
}
