package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.internal.zzcdr;
import java.util.ArrayList;
import java.util.List;

public class GeofencingRequest extends zza {
    public static final Parcelable.Creator<GeofencingRequest> CREATOR = new zzi();
    public static final int INITIAL_TRIGGER_DWELL = 4;
    public static final int INITIAL_TRIGGER_ENTER = 1;
    public static final int INITIAL_TRIGGER_EXIT = 2;
    private final String mTag;
    private final List<zzcdr> zzbhQ;
    private final int zzbhR;

    public static final class Builder {
        private String mTag = "";
        private final List<zzcdr> zzbhQ = new ArrayList();
        private int zzbhR = 5;

        public final Builder addGeofence(Geofence geofence) {
            zzbo.zzb(geofence, (Object) "geofence can't be null.");
            zzbo.zzb(geofence instanceof zzcdr, (Object) "Geofence must be created using Geofence.Builder.");
            this.zzbhQ.add((zzcdr) geofence);
            return this;
        }

        public final Builder addGeofences(List<Geofence> list) {
            if (list != null && !list.isEmpty()) {
                for (Geofence next : list) {
                    if (next != null) {
                        addGeofence(next);
                    }
                }
            }
            return this;
        }

        public final GeofencingRequest build() {
            zzbo.zzb(!this.zzbhQ.isEmpty(), (Object) "No geofence has been added to this request.");
            return new GeofencingRequest(this.zzbhQ, this.zzbhR, this.mTag);
        }

        public final Builder setInitialTrigger(int i) {
            this.zzbhR = i & 7;
            return this;
        }
    }

    GeofencingRequest(List<zzcdr> list, int i, String str) {
        this.zzbhQ = list;
        this.zzbhR = i;
        this.mTag = str;
    }

    public List<Geofence> getGeofences() {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(this.zzbhQ);
        return arrayList;
    }

    public int getInitialTrigger() {
        return this.zzbhR;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, this.zzbhQ, false);
        zzd.zzc(parcel, 2, getInitialTrigger());
        zzd.zza(parcel, 3, this.mTag, false);
        zzd.zzI(parcel, zze);
    }
}
