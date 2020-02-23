package com.google.android.gms.nearby.messages.internal;

import android.app.PendingIntent;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.nearby.messages.MessageFilter;
import com.google.android.gms.nearby.messages.Strategy;

public final class zzbb implements Parcelable.Creator<SubscribeRequest> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        int i = 0;
        IBinder iBinder = null;
        Strategy strategy = null;
        IBinder iBinder2 = null;
        MessageFilter messageFilter = null;
        PendingIntent pendingIntent = null;
        int i2 = 0;
        String str = null;
        String str2 = null;
        byte[] bArr = null;
        boolean z = false;
        IBinder iBinder3 = null;
        boolean z2 = false;
        ClientAppContext clientAppContext = null;
        boolean z3 = false;
        int i3 = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case 2:
                    iBinder = zzb.zzr(parcel, readInt);
                    break;
                case 3:
                    strategy = (Strategy) zzb.zza(parcel, readInt, Strategy.CREATOR);
                    break;
                case 4:
                    iBinder2 = zzb.zzr(parcel, readInt);
                    break;
                case 5:
                    messageFilter = (MessageFilter) zzb.zza(parcel, readInt, MessageFilter.CREATOR);
                    break;
                case 6:
                    pendingIntent = (PendingIntent) zzb.zza(parcel, readInt, PendingIntent.CREATOR);
                    break;
                case 7:
                    i2 = zzb.zzg(parcel, readInt);
                    break;
                case 8:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case 9:
                    str2 = zzb.zzq(parcel, readInt);
                    break;
                case 10:
                    bArr = zzb.zzt(parcel, readInt);
                    break;
                case 11:
                    z = zzb.zzc(parcel, readInt);
                    break;
                case 12:
                    iBinder3 = zzb.zzr(parcel, readInt);
                    break;
                case 13:
                    z2 = zzb.zzc(parcel, readInt);
                    break;
                case 14:
                    clientAppContext = (ClientAppContext) zzb.zza(parcel, readInt, ClientAppContext.CREATOR);
                    break;
                case 15:
                    z3 = zzb.zzc(parcel, readInt);
                    break;
                case 16:
                    i3 = zzb.zzg(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new SubscribeRequest(i, iBinder, strategy, iBinder2, messageFilter, pendingIntent, i2, str, str2, bArr, z, iBinder3, z2, clientAppContext, z3, i3);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new SubscribeRequest[i];
    }
}
