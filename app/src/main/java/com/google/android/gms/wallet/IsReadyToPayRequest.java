package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import java.util.ArrayList;
import java.util.List;

public final class IsReadyToPayRequest extends zza {
    public static final Parcelable.Creator<IsReadyToPayRequest> CREATOR = new zzl();
    ArrayList<Integer> zzbON;
    private String zzbOO;
    private String zzbOP;

    public final class Builder {
        private Builder() {
        }

        public final Builder addAllowedCardNetwork(int i) {
            if (IsReadyToPayRequest.this.zzbON == null) {
                IsReadyToPayRequest.this.zzbON = new ArrayList<>();
            }
            IsReadyToPayRequest.this.zzbON.add(Integer.valueOf(i));
            return this;
        }

        public final IsReadyToPayRequest build() {
            return IsReadyToPayRequest.this;
        }
    }

    IsReadyToPayRequest() {
    }

    IsReadyToPayRequest(ArrayList<Integer> arrayList, String str, String str2) {
        this.zzbON = arrayList;
        this.zzbOO = str;
        this.zzbOP = str2;
    }

    public static Builder newBuilder() {
        IsReadyToPayRequest isReadyToPayRequest = new IsReadyToPayRequest();
        isReadyToPayRequest.getClass();
        return new Builder();
    }

    public final ArrayList<Integer> getAllowedCardNetworks() {
        return this.zzbON;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 2, (List<Integer>) this.zzbON, false);
        zzd.zza(parcel, 4, this.zzbOO, false);
        zzd.zza(parcel, 5, this.zzbOP, false);
        zzd.zzI(parcel, zze);
    }
}
