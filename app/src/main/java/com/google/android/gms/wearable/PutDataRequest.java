package com.google.android.gms.wearable;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import com.appsflyer.share.Constants;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.wearable.internal.DataItemAssetParcelable;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class PutDataRequest extends zza {
    public static final Parcelable.Creator<PutDataRequest> CREATOR = new zzh();
    public static final String WEAR_URI_SCHEME = "wear";
    private static final long zzbRh = TimeUnit.MINUTES.toMillis(30);
    private static final Random zzbRi = new SecureRandom();
    private final Uri mUri;
    private final Bundle zzbRj;
    private long zzbRk;
    private byte[] zzbdY;

    private PutDataRequest(Uri uri) {
        this(uri, new Bundle(), (byte[]) null, zzbRh);
    }

    PutDataRequest(Uri uri, Bundle bundle, byte[] bArr, long j) {
        this.mUri = uri;
        this.zzbRj = bundle;
        this.zzbRj.setClassLoader(DataItemAssetParcelable.class.getClassLoader());
        this.zzbdY = bArr;
        this.zzbRk = j;
    }

    public static PutDataRequest create(String str) {
        return zzt(zzgj(str));
    }

    public static PutDataRequest createFromDataItem(DataItem dataItem) {
        PutDataRequest zzt = zzt(dataItem.getUri());
        for (Map.Entry next : dataItem.getAssets().entrySet()) {
            if (((DataItemAsset) next.getValue()).getId() == null) {
                String valueOf = String.valueOf((String) next.getKey());
                throw new IllegalStateException(valueOf.length() != 0 ? "Cannot create an asset for a put request without a digest: ".concat(valueOf) : new String("Cannot create an asset for a put request without a digest: "));
            }
            zzt.putAsset((String) next.getKey(), Asset.createFromRef(((DataItemAsset) next.getValue()).getId()));
        }
        zzt.setData(dataItem.getData());
        return zzt;
    }

    public static PutDataRequest createWithAutoAppendedId(String str) {
        StringBuilder sb = new StringBuilder(str);
        if (!str.endsWith(Constants.URL_PATH_DELIMITER)) {
            sb.append(Constants.URL_PATH_DELIMITER);
        }
        sb.append("PN").append(zzbRi.nextLong());
        return new PutDataRequest(zzgj(sb.toString()));
    }

    private static Uri zzgj(String str) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("An empty path was supplied.");
        } else if (!str.startsWith(Constants.URL_PATH_DELIMITER)) {
            throw new IllegalArgumentException("A path must start with a single / .");
        } else if (!str.startsWith("//")) {
            return new Uri.Builder().scheme(WEAR_URI_SCHEME).path(str).build();
        } else {
            throw new IllegalArgumentException("A path must start with a single / .");
        }
    }

    public static PutDataRequest zzt(Uri uri) {
        return new PutDataRequest(uri);
    }

    public Asset getAsset(String str) {
        return (Asset) this.zzbRj.getParcelable(str);
    }

    public Map<String, Asset> getAssets() {
        HashMap hashMap = new HashMap();
        for (String str : this.zzbRj.keySet()) {
            hashMap.put(str, (Asset) this.zzbRj.getParcelable(str));
        }
        return Collections.unmodifiableMap(hashMap);
    }

    public byte[] getData() {
        return this.zzbdY;
    }

    public Uri getUri() {
        return this.mUri;
    }

    public boolean hasAsset(String str) {
        return this.zzbRj.containsKey(str);
    }

    public boolean isUrgent() {
        return this.zzbRk == 0;
    }

    public PutDataRequest putAsset(String str, Asset asset) {
        zzbo.zzu(str);
        zzbo.zzu(asset);
        this.zzbRj.putParcelable(str, asset);
        return this;
    }

    public PutDataRequest removeAsset(String str) {
        this.zzbRj.remove(str);
        return this;
    }

    public PutDataRequest setData(byte[] bArr) {
        this.zzbdY = bArr;
        return this;
    }

    public PutDataRequest setUrgent() {
        this.zzbRk = 0;
        return this;
    }

    public String toString() {
        return toString(Log.isLoggable(DataMap.TAG, 3));
    }

    public String toString(boolean z) {
        StringBuilder sb = new StringBuilder("PutDataRequest[");
        String valueOf = String.valueOf(this.zzbdY == null ? com.amazonaws.services.s3.internal.Constants.NULL_VERSION_ID : Integer.valueOf(this.zzbdY.length));
        sb.append(new StringBuilder(String.valueOf(valueOf).length() + 7).append("dataSz=").append(valueOf).toString());
        sb.append(new StringBuilder(23).append(", numAssets=").append(this.zzbRj.size()).toString());
        String valueOf2 = String.valueOf(this.mUri);
        sb.append(new StringBuilder(String.valueOf(valueOf2).length() + 6).append(", uri=").append(valueOf2).toString());
        sb.append(new StringBuilder(35).append(", syncDeadline=").append(this.zzbRk).toString());
        if (!z) {
            sb.append("]");
            return sb.toString();
        }
        sb.append("]\n  assets: ");
        for (String str : this.zzbRj.keySet()) {
            String valueOf3 = String.valueOf(this.zzbRj.getParcelable(str));
            sb.append(new StringBuilder(String.valueOf(str).length() + 7 + String.valueOf(valueOf3).length()).append("\n    ").append(str).append(": ").append(valueOf3).toString());
        }
        sb.append("\n  ]");
        return sb.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 2, (Parcelable) getUri(), i, false);
        zzd.zza(parcel, 4, this.zzbRj, false);
        zzd.zza(parcel, 5, getData(), false);
        zzd.zza(parcel, 6, this.zzbRk);
        zzd.zzI(parcel, zze);
    }
}
