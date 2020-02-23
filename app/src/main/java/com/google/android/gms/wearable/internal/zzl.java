package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.tencent.smtt.sdk.TbsListener;

public final class zzl extends zza {
    public static final Parcelable.Creator<zzl> CREATOR = new zzm();
    private final String mAppId;
    private int mId;
    private final String mPackageName;
    private final String zzalP;
    private final String zzaoy;
    private final String zzapS;
    private final String zzbRP;
    private final String zzbRQ;
    private final byte zzbRR;
    private final byte zzbRS;
    private final byte zzbRT;
    private final byte zzbRU;

    public zzl(int i, String str, String str2, String str3, String str4, String str5, String str6, byte b, byte b2, byte b3, byte b4, String str7) {
        this.mId = i;
        this.mAppId = str;
        this.zzbRP = str2;
        this.zzapS = str3;
        this.zzaoy = str4;
        this.zzbRQ = str5;
        this.zzalP = str6;
        this.zzbRR = b;
        this.zzbRS = b2;
        this.zzbRT = b3;
        this.zzbRU = b4;
        this.mPackageName = str7;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        zzl zzl = (zzl) obj;
        if (this.mId != zzl.mId) {
            return false;
        }
        if (this.zzbRR != zzl.zzbRR) {
            return false;
        }
        if (this.zzbRS != zzl.zzbRS) {
            return false;
        }
        if (this.zzbRT != zzl.zzbRT) {
            return false;
        }
        if (this.zzbRU != zzl.zzbRU) {
            return false;
        }
        if (!this.mAppId.equals(zzl.mAppId)) {
            return false;
        }
        if (this.zzbRP == null ? zzl.zzbRP != null : !this.zzbRP.equals(zzl.zzbRP)) {
            return false;
        }
        if (!this.zzapS.equals(zzl.zzapS)) {
            return false;
        }
        if (!this.zzaoy.equals(zzl.zzaoy)) {
            return false;
        }
        if (!this.zzbRQ.equals(zzl.zzbRQ)) {
            return false;
        }
        if (this.zzalP == null ? zzl.zzalP != null : !this.zzalP.equals(zzl.zzalP)) {
            return false;
        }
        return this.mPackageName != null ? this.mPackageName.equals(zzl.mPackageName) : zzl.mPackageName == null;
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((((((((((this.zzalP != null ? this.zzalP.hashCode() : 0) + (((((((((this.zzbRP != null ? this.zzbRP.hashCode() : 0) + ((((this.mId + 31) * 31) + this.mAppId.hashCode()) * 31)) * 31) + this.zzapS.hashCode()) * 31) + this.zzaoy.hashCode()) * 31) + this.zzbRQ.hashCode()) * 31)) * 31) + this.zzbRR) * 31) + this.zzbRS) * 31) + this.zzbRT) * 31) + this.zzbRU) * 31;
        if (this.mPackageName != null) {
            i = this.mPackageName.hashCode();
        }
        return hashCode + i;
    }

    public final String toString() {
        int i = this.mId;
        String str = this.mAppId;
        String str2 = this.zzbRP;
        String str3 = this.zzapS;
        String str4 = this.zzaoy;
        String str5 = this.zzbRQ;
        String str6 = this.zzalP;
        byte b = this.zzbRR;
        byte b2 = this.zzbRS;
        byte b3 = this.zzbRT;
        byte b4 = this.zzbRU;
        String str7 = this.mPackageName;
        return new StringBuilder(String.valueOf(str).length() + TbsListener.ErrorCode.EXCEED_COPY_RETRY_NUM + String.valueOf(str2).length() + String.valueOf(str3).length() + String.valueOf(str4).length() + String.valueOf(str5).length() + String.valueOf(str6).length() + String.valueOf(str7).length()).append("AncsNotificationParcelable{, id=").append(i).append(", appId='").append(str).append("', dateTime='").append(str2).append("', notificationText='").append(str3).append("', title='").append(str4).append("', subtitle='").append(str5).append("', displayName='").append(str6).append("', eventId=").append(b).append(", eventFlags=").append(b2).append(", categoryId=").append(b3).append(", categoryCount=").append(b4).append(", packageName='").append(str7).append("'}").toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 2, this.mId);
        zzd.zza(parcel, 3, this.mAppId, false);
        zzd.zza(parcel, 4, this.zzbRP, false);
        zzd.zza(parcel, 5, this.zzapS, false);
        zzd.zza(parcel, 6, this.zzaoy, false);
        zzd.zza(parcel, 7, this.zzbRQ, false);
        zzd.zza(parcel, 8, this.zzalP == null ? this.mAppId : this.zzalP, false);
        zzd.zza(parcel, 9, this.zzbRR);
        zzd.zza(parcel, 10, this.zzbRS);
        zzd.zza(parcel, 11, this.zzbRT);
        zzd.zza(parcel, 12, this.zzbRU);
        zzd.zza(parcel, 13, this.mPackageName, false);
        zzd.zzI(parcel, zze);
    }
}
