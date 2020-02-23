package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@zzzn
public final class zzaak extends zza {
    public static final Parcelable.Creator<zzaak> CREATOR = new zzaal();
    private boolean zzTN;
    @Nullable
    private List<String> zzTO;

    public zzaak() {
        this(false, Collections.emptyList());
    }

    public zzaak(boolean z, List<String> list) {
        this.zzTN = z;
        this.zzTO = list;
    }

    @Nullable
    public static zzaak zze(JSONObject jSONObject) {
        if (jSONObject == null) {
            return new zzaak();
        }
        JSONArray optJSONArray = jSONObject.optJSONArray("reporting_urls");
        ArrayList arrayList = new ArrayList();
        if (optJSONArray != null) {
            for (int i = 0; i < optJSONArray.length(); i++) {
                try {
                    arrayList.add(optJSONArray.getString(i));
                } catch (JSONException e) {
                    zzafr.zzc("Error grabbing url from json.", e);
                }
            }
        }
        return new zzaak(jSONObject.optBoolean("enable_protection"), arrayList);
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 2, this.zzTN);
        zzd.zzb(parcel, 3, this.zzTO, false);
        zzd.zzI(parcel, zze);
    }
}
