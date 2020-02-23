package com.google.android.gms.maps.model;

import android.content.Context;
import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.util.zzn;
import java.io.IOException;

public final class MapStyleOptions extends zza {
    public static final Parcelable.Creator<MapStyleOptions> CREATOR = new zzg();
    private static final String TAG = MapStyleOptions.class.getSimpleName();
    private String zzbnC;

    public MapStyleOptions(String str) {
        this.zzbnC = str;
    }

    public static MapStyleOptions loadRawResourceStyle(Context context, int i) throws Resources.NotFoundException {
        try {
            return new MapStyleOptions(new String(zzn.zza(context.getResources().openRawResource(i), true), "UTF-8"));
        } catch (IOException e) {
            String valueOf = String.valueOf(e);
            throw new Resources.NotFoundException(new StringBuilder(String.valueOf(valueOf).length() + 37).append("Failed to read resource ").append(i).append(": ").append(valueOf).toString());
        }
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 2, this.zzbnC, false);
        zzd.zzI(parcel, zze);
    }
}
