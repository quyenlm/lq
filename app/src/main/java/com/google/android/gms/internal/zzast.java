package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.awareness.state.BeaconState;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import java.util.ArrayList;
import java.util.List;

public final class zzast extends zza implements BeaconState {
    public static final Parcelable.Creator<zzast> CREATOR = new zzasw();
    private final ArrayList<zzasu> zzanQ;

    public zzast(ArrayList<zzasu> arrayList) {
        this.zzanQ = arrayList;
    }

    public final List<BeaconState.BeaconInfo> getBeaconInfo() {
        return this.zzanQ;
    }

    public final String toString() {
        if (this.zzanQ == null || this.zzanQ.isEmpty()) {
            return "BeaconState: empty";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("BeaconState: ");
        ArrayList arrayList = this.zzanQ;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            sb.append((BeaconState.BeaconInfo) obj);
        }
        return sb.toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 2, this.zzanQ, false);
        zzd.zzI(parcel, zze);
    }
}
