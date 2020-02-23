package com.google.android.gms.drive;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.facebook.share.internal.ShareConstants;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.common.util.zzf;
import java.util.Set;
import java.util.regex.Pattern;

public class DriveSpace extends zza implements ReflectedParcelable {
    public static final Parcelable.Creator<DriveSpace> CREATOR = new zzl();
    public static final DriveSpace zzaMl = new DriveSpace("DRIVE");
    public static final DriveSpace zzaMm = new DriveSpace("APP_DATA_FOLDER");
    public static final DriveSpace zzaMn = new DriveSpace(ShareConstants.PHOTOS);
    private static Set<DriveSpace> zzaMo = zzf.zza(zzaMl, zzaMm, zzaMn);
    private static String zzaMp = TextUtils.join(",", zzaMo.toArray());
    private static final Pattern zzaMq = Pattern.compile("[A-Z0-9_]*");
    private final String mName;

    DriveSpace(String str) {
        this.mName = (String) zzbo.zzu(str);
    }

    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != DriveSpace.class) {
            return false;
        }
        return this.mName.equals(((DriveSpace) obj).mName);
    }

    public int hashCode() {
        return 1247068382 ^ this.mName.hashCode();
    }

    public String toString() {
        return this.mName;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 2, this.mName, false);
        zzd.zzI(parcel, zze);
    }
}
