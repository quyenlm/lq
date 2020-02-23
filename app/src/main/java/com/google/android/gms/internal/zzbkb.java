package com.google.android.gms.internal;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Process;
import android.text.TextUtils;
import com.google.android.gms.awareness.AwarenessOptions;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.util.zzd;
import com.tencent.tp.a.h;
import java.util.Arrays;

public final class zzbkb extends zza {
    public static final Parcelable.Creator<zzbkb> CREATOR = new zzbkc();
    public final String moduleId;
    public final String packageName;
    private int pid;
    private int uid;
    private String zzaLA;
    private String zzaLB;
    private int zzaLC;
    private zzej zzaLD;
    public final String zzaLx;
    private int zzaLy;
    private int zzaLz;

    public zzbkb(String str, String str2, int i, String str3, int i2, int i3, String str4, String str5, int i4, int i5) {
        this.zzaLx = str;
        this.packageName = str2;
        this.uid = i;
        this.moduleId = str3;
        this.zzaLy = i2;
        this.zzaLz = i3;
        this.zzaLA = str4;
        this.zzaLB = str5;
        this.zzaLC = i4;
        this.pid = i5;
    }

    public static zzbkb zza(Context context, String str, AwarenessOptions awarenessOptions) {
        return new zzbkb(awarenessOptions.getAccount() != null ? awarenessOptions.getAccount().name : str, context.getPackageName(), Process.myUid(), awarenessOptions.zzmQ(), zzd.zzA(context, context.getPackageName()), awarenessOptions.zzmR(), awarenessOptions.zzmS(), awarenessOptions.zzmT(), awarenessOptions.zzmU(), Process.myPid());
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzbkb)) {
            return false;
        }
        zzbkb zzbkb = (zzbkb) obj;
        return this.uid == zzbkb.uid && this.zzaLy == zzbkb.zzaLy && this.zzaLz == zzbkb.zzaLz && this.zzaLC == zzbkb.zzaLC && TextUtils.equals(this.zzaLx, zzbkb.zzaLx) && TextUtils.equals(this.packageName, zzbkb.packageName) && TextUtils.equals(this.moduleId, zzbkb.moduleId) && TextUtils.equals(this.zzaLA, zzbkb.zzaLA) && TextUtils.equals(this.zzaLB, zzbkb.zzaLB);
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzaLx, this.packageName, Integer.valueOf(this.uid), this.moduleId, Integer.valueOf(this.zzaLy), Integer.valueOf(this.zzaLz), this.zzaLA, this.zzaLB, Integer.valueOf(this.zzaLC)});
    }

    public final String toString() {
        zzej zzej;
        if (this.zzaLx == null) {
            zzej = null;
        } else {
            if (this.zzaLD == null) {
                this.zzaLD = new zzej(this.zzaLx);
            }
            zzej = this.zzaLD;
        }
        String valueOf = String.valueOf(zzej);
        String str = this.packageName;
        int i = this.uid;
        String str2 = this.moduleId;
        int i2 = this.zzaLy;
        String valueOf2 = String.valueOf(Integer.toString(this.zzaLz));
        String str3 = this.zzaLA;
        String str4 = this.zzaLB;
        return new StringBuilder(String.valueOf(valueOf).length() + 89 + String.valueOf(str).length() + String.valueOf(str2).length() + String.valueOf(valueOf2).length() + String.valueOf(str3).length() + String.valueOf(str4).length()).append("(accnt=").append(valueOf).append(", ").append(str).append(h.a).append(i).append("):").append(str2).append(", vrsn=").append(i2).append(", ").append(valueOf2).append(", 3pPkg = ").append(str3).append(" ,  3pMdlId = ").append(str4).append(" ,  pid = ").append(this.pid).append(h.b).toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = com.google.android.gms.common.internal.safeparcel.zzd.zze(parcel);
        com.google.android.gms.common.internal.safeparcel.zzd.zza(parcel, 2, this.zzaLx, false);
        com.google.android.gms.common.internal.safeparcel.zzd.zza(parcel, 3, this.packageName, false);
        com.google.android.gms.common.internal.safeparcel.zzd.zzc(parcel, 4, this.uid);
        com.google.android.gms.common.internal.safeparcel.zzd.zza(parcel, 5, this.moduleId, false);
        com.google.android.gms.common.internal.safeparcel.zzd.zzc(parcel, 6, this.zzaLy);
        com.google.android.gms.common.internal.safeparcel.zzd.zzc(parcel, 7, this.zzaLz);
        com.google.android.gms.common.internal.safeparcel.zzd.zza(parcel, 8, this.zzaLA, false);
        com.google.android.gms.common.internal.safeparcel.zzd.zza(parcel, 9, this.zzaLB, false);
        com.google.android.gms.common.internal.safeparcel.zzd.zzc(parcel, 10, this.zzaLC);
        com.google.android.gms.common.internal.safeparcel.zzd.zzc(parcel, 11, this.pid);
        com.google.android.gms.common.internal.safeparcel.zzd.zzI(parcel, zze);
    }
}
