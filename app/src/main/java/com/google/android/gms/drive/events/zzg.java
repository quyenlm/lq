package com.google.android.gms.drive.events;

import android.os.IBinder;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import java.util.ArrayList;

public final class zzg implements Parcelable.Creator<CompletionEvent> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        int i = 0;
        IBinder iBinder = null;
        ArrayList<String> arrayList = null;
        MetadataBundle metadataBundle = null;
        ParcelFileDescriptor parcelFileDescriptor = null;
        ParcelFileDescriptor parcelFileDescriptor2 = null;
        String str = null;
        DriveId driveId = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 2:
                    driveId = (DriveId) zzb.zza(parcel, readInt, DriveId.CREATOR);
                    break;
                case 3:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case 4:
                    parcelFileDescriptor2 = (ParcelFileDescriptor) zzb.zza(parcel, readInt, ParcelFileDescriptor.CREATOR);
                    break;
                case 5:
                    parcelFileDescriptor = (ParcelFileDescriptor) zzb.zza(parcel, readInt, ParcelFileDescriptor.CREATOR);
                    break;
                case 6:
                    metadataBundle = (MetadataBundle) zzb.zza(parcel, readInt, MetadataBundle.CREATOR);
                    break;
                case 7:
                    arrayList = zzb.zzC(parcel, readInt);
                    break;
                case 8:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case 9:
                    iBinder = zzb.zzr(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new CompletionEvent(driveId, str, parcelFileDescriptor2, parcelFileDescriptor, metadataBundle, arrayList, i, iBinder);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new CompletionEvent[i];
    }
}
