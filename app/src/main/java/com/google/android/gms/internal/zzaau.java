package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import com.google.android.gms.ads.internal.zzbs;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.util.zzn;
import java.io.DataInputStream;
import java.io.IOException;

@zzzn
public final class zzaau extends zza {
    public static final Parcelable.Creator<zzaau> CREATOR = new zzaaw();
    private ParcelFileDescriptor zzTP;
    private Parcelable zzTQ;
    private boolean zzTR;

    zzaau(ParcelFileDescriptor parcelFileDescriptor) {
        this.zzTP = parcelFileDescriptor;
        this.zzTQ = null;
        this.zzTR = true;
    }

    public zzaau(SafeParcelable safeParcelable) {
        this.zzTP = null;
        this.zzTQ = safeParcelable;
        this.zzTR = false;
    }

    private final <T> ParcelFileDescriptor zzc(byte[] bArr) {
        ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream;
        try {
            ParcelFileDescriptor[] createPipe = ParcelFileDescriptor.createPipe();
            autoCloseOutputStream = new ParcelFileDescriptor.AutoCloseOutputStream(createPipe[1]);
            try {
                new Thread(new zzaav(this, autoCloseOutputStream, bArr)).start();
                return createPipe[0];
            } catch (IOException e) {
                e = e;
            }
        } catch (IOException e2) {
            e = e2;
            autoCloseOutputStream = null;
            zzafr.zzb("Error transporting the ad response", e);
            zzbs.zzbD().zza((Throwable) e, "LargeParcelTeleporter.pipeData.2");
            zzn.closeQuietly(autoCloseOutputStream);
            return null;
        }
    }

    /* JADX INFO: finally extract failed */
    public final void writeToParcel(Parcel parcel, int i) {
        if (this.zzTP == null) {
            Parcel obtain = Parcel.obtain();
            try {
                this.zzTQ.writeToParcel(obtain, 0);
                byte[] marshall = obtain.marshall();
                obtain.recycle();
                this.zzTP = zzc(marshall);
            } catch (Throwable th) {
                obtain.recycle();
                throw th;
            }
        }
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 2, (Parcelable) this.zzTP, i, false);
        zzd.zzI(parcel, zze);
    }

    /* JADX INFO: finally extract failed */
    public final <T extends SafeParcelable> T zza(Parcelable.Creator<T> creator) {
        if (this.zzTR) {
            if (this.zzTP == null) {
                zzafr.e("File descriptor is empty, returning null.");
                return null;
            }
            DataInputStream dataInputStream = new DataInputStream(new ParcelFileDescriptor.AutoCloseInputStream(this.zzTP));
            try {
                byte[] bArr = new byte[dataInputStream.readInt()];
                dataInputStream.readFully(bArr, 0, bArr.length);
                zzn.closeQuietly(dataInputStream);
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.unmarshall(bArr, 0, bArr.length);
                    obtain.setDataPosition(0);
                    this.zzTQ = (SafeParcelable) creator.createFromParcel(obtain);
                    obtain.recycle();
                    this.zzTR = false;
                } catch (Throwable th) {
                    obtain.recycle();
                    throw th;
                }
            } catch (IOException e) {
                throw new IllegalStateException("Could not read from parcel file descriptor", e);
            } catch (Throwable th2) {
                zzn.closeQuietly(dataInputStream);
                throw th2;
            }
        }
        return (SafeParcelable) this.zzTQ;
    }
}
