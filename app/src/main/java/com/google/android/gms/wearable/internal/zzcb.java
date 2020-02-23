package com.google.android.gms.wearable.internal;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.amazonaws.services.s3.internal.Constants;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataItemAsset;
import java.util.HashMap;
import java.util.Map;

public final class zzcb extends zza implements DataItem {
    public static final Parcelable.Creator<zzcb> CREATOR = new zzcc();
    private final Uri mUri;
    private final Map<String, DataItemAsset> zzbSE;
    private byte[] zzbdY;

    zzcb(Uri uri, Bundle bundle, byte[] bArr) {
        this.mUri = uri;
        HashMap hashMap = new HashMap();
        bundle.setClassLoader(DataItemAssetParcelable.class.getClassLoader());
        for (String str : bundle.keySet()) {
            hashMap.put(str, (DataItemAssetParcelable) bundle.getParcelable(str));
        }
        this.zzbSE = hashMap;
        this.zzbdY = bArr;
    }

    public final /* bridge */ /* synthetic */ Object freeze() {
        return this;
    }

    public final Map<String, DataItemAsset> getAssets() {
        return this.zzbSE;
    }

    public final byte[] getData() {
        return this.zzbdY;
    }

    public final Uri getUri() {
        return this.mUri;
    }

    public final boolean isDataValid() {
        return true;
    }

    public final /* synthetic */ DataItem setData(byte[] bArr) {
        this.zzbdY = bArr;
        return this;
    }

    public final String toString() {
        boolean isLoggable = Log.isLoggable("DataItem", 3);
        StringBuilder sb = new StringBuilder("DataItemParcelable[");
        sb.append("@");
        sb.append(Integer.toHexString(hashCode()));
        String valueOf = String.valueOf(this.zzbdY == null ? Constants.NULL_VERSION_ID : Integer.valueOf(this.zzbdY.length));
        sb.append(new StringBuilder(String.valueOf(valueOf).length() + 8).append(",dataSz=").append(valueOf).toString());
        sb.append(new StringBuilder(23).append(", numAssets=").append(this.zzbSE.size()).toString());
        String valueOf2 = String.valueOf(this.mUri);
        sb.append(new StringBuilder(String.valueOf(valueOf2).length() + 6).append(", uri=").append(valueOf2).toString());
        if (!isLoggable) {
            sb.append("]");
            return sb.toString();
        }
        sb.append("]\n  assets: ");
        for (String next : this.zzbSE.keySet()) {
            String valueOf3 = String.valueOf(this.zzbSE.get(next));
            sb.append(new StringBuilder(String.valueOf(next).length() + 7 + String.valueOf(valueOf3).length()).append("\n    ").append(next).append(": ").append(valueOf3).toString());
        }
        sb.append("\n  ]");
        return sb.toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 2, (Parcelable) getUri(), i, false);
        Bundle bundle = new Bundle();
        bundle.setClassLoader(DataItemAssetParcelable.class.getClassLoader());
        for (Map.Entry next : this.zzbSE.entrySet()) {
            bundle.putParcelable((String) next.getKey(), new DataItemAssetParcelable((DataItemAsset) next.getValue()));
        }
        zzd.zza(parcel, 4, bundle, false);
        zzd.zza(parcel, 5, getData(), false);
        zzd.zzI(parcel, zze);
    }
}
