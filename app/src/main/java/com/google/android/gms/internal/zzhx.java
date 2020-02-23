package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import java.io.InputStream;

@zzzn
public final class zzhx extends zza {
    public static final Parcelable.Creator<zzhx> CREATOR = new zzhy();
    @Nullable
    private ParcelFileDescriptor zzzt;

    public zzhx() {
        this((ParcelFileDescriptor) null);
    }

    public zzhx(@Nullable ParcelFileDescriptor parcelFileDescriptor) {
        this.zzzt = parcelFileDescriptor;
    }

    private synchronized ParcelFileDescriptor zzda() {
        return this.zzzt;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 2, (Parcelable) zzda(), i, false);
        zzd.zzI(parcel, zze);
    }

    public final synchronized boolean zzcY() {
        return this.zzzt != null;
    }

    @Nullable
    public final synchronized InputStream zzcZ() {
        ParcelFileDescriptor.AutoCloseInputStream autoCloseInputStream = null;
        synchronized (this) {
            if (this.zzzt != null) {
                autoCloseInputStream = new ParcelFileDescriptor.AutoCloseInputStream(this.zzzt);
                this.zzzt = null;
            }
        }
        return autoCloseInputStream;
    }
}
