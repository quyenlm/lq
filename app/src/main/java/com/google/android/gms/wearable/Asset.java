package com.google.android.gms.wearable;

import android.net.Uri;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbe;
import java.util.Arrays;

public class Asset extends zza implements ReflectedParcelable {
    public static final Parcelable.Creator<Asset> CREATOR = new zze();
    private Uri uri;
    private String zzbQX;
    private ParcelFileDescriptor zzbQY;
    private byte[] zzbdY;

    Asset(byte[] bArr, String str, ParcelFileDescriptor parcelFileDescriptor, Uri uri2) {
        this.zzbdY = bArr;
        this.zzbQX = str;
        this.zzbQY = parcelFileDescriptor;
        this.uri = uri2;
    }

    public static Asset createFromBytes(byte[] bArr) {
        if (bArr != null) {
            return new Asset(bArr, (String) null, (ParcelFileDescriptor) null, (Uri) null);
        }
        throw new IllegalArgumentException("Asset data cannot be null");
    }

    public static Asset createFromFd(ParcelFileDescriptor parcelFileDescriptor) {
        if (parcelFileDescriptor != null) {
            return new Asset((byte[]) null, (String) null, parcelFileDescriptor, (Uri) null);
        }
        throw new IllegalArgumentException("Asset fd cannot be null");
    }

    public static Asset createFromRef(String str) {
        if (str != null) {
            return new Asset((byte[]) null, str, (ParcelFileDescriptor) null, (Uri) null);
        }
        throw new IllegalArgumentException("Asset digest cannot be null");
    }

    public static Asset createFromUri(Uri uri2) {
        if (uri2 != null) {
            return new Asset((byte[]) null, (String) null, (ParcelFileDescriptor) null, uri2);
        }
        throw new IllegalArgumentException("Asset uri cannot be null");
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Asset)) {
            return false;
        }
        Asset asset = (Asset) obj;
        return Arrays.equals(this.zzbdY, asset.zzbdY) && zzbe.equal(this.zzbQX, asset.zzbQX) && zzbe.equal(this.zzbQY, asset.zzbQY) && zzbe.equal(this.uri, asset.uri);
    }

    public final byte[] getData() {
        return this.zzbdY;
    }

    public String getDigest() {
        return this.zzbQX;
    }

    public ParcelFileDescriptor getFd() {
        return this.zzbQY;
    }

    public Uri getUri() {
        return this.uri;
    }

    public int hashCode() {
        return Arrays.deepHashCode(new Object[]{this.zzbdY, this.zzbQX, this.zzbQY, this.uri});
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Asset[@");
        sb.append(Integer.toHexString(hashCode()));
        if (this.zzbQX == null) {
            sb.append(", nodigest");
        } else {
            sb.append(", ");
            sb.append(this.zzbQX);
        }
        if (this.zzbdY != null) {
            sb.append(", size=");
            sb.append(this.zzbdY.length);
        }
        if (this.zzbQY != null) {
            sb.append(", fd=");
            sb.append(this.zzbQY);
        }
        if (this.uri != null) {
            sb.append(", uri=");
            sb.append(this.uri);
        }
        sb.append("]");
        return sb.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        int i2 = i | 1;
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 2, this.zzbdY, false);
        zzd.zza(parcel, 3, getDigest(), false);
        zzd.zza(parcel, 4, (Parcelable) this.zzbQY, i2, false);
        zzd.zza(parcel, 5, (Parcelable) this.uri, i2, false);
        zzd.zzI(parcel, zze);
    }
}
