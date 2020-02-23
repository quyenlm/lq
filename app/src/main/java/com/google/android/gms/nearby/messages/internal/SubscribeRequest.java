package com.google.android.gms.nearby.messages.internal;

import android.app.PendingIntent;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.media.MediaRouter;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.nearby.messages.MessageFilter;
import com.google.android.gms.nearby.messages.Strategy;

public final class SubscribeRequest extends zza implements ReflectedParcelable {
    public static final Parcelable.Creator<SubscribeRequest> CREATOR = new zzbb();
    private PendingIntent zzaLr;
    private int zzaku;
    private boolean zzbyA;
    private int zzbyB;
    @Deprecated
    private String zzbyW;
    @Deprecated
    private String zzbye;
    @Deprecated
    private boolean zzbyf;
    private zzp zzbza;
    @Deprecated
    private ClientAppContext zzbzb;
    private Strategy zzbzq;
    @Deprecated
    private boolean zzbzr;
    private zzm zzbzv;
    private MessageFilter zzbzw;
    @Deprecated
    private int zzbzx;
    private byte[] zzbzy;
    private zzaa zzbzz;

    public SubscribeRequest(int i, IBinder iBinder, Strategy strategy, IBinder iBinder2, MessageFilter messageFilter, PendingIntent pendingIntent, int i2, String str, String str2, byte[] bArr, boolean z, IBinder iBinder3, boolean z2, ClientAppContext clientAppContext, boolean z3, int i3) {
        zzm zzo;
        zzp zzr;
        zzaa zzac;
        this.zzaku = i;
        if (iBinder == null) {
            zzo = null;
        } else {
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.nearby.messages.internal.IMessageListener");
            zzo = queryLocalInterface instanceof zzm ? (zzm) queryLocalInterface : new zzo(iBinder);
        }
        this.zzbzv = zzo;
        this.zzbzq = strategy;
        if (iBinder2 == null) {
            zzr = null;
        } else {
            IInterface queryLocalInterface2 = iBinder2.queryLocalInterface("com.google.android.gms.nearby.messages.internal.INearbyMessagesCallback");
            zzr = queryLocalInterface2 instanceof zzp ? (zzp) queryLocalInterface2 : new zzr(iBinder2);
        }
        this.zzbza = zzr;
        this.zzbzw = messageFilter;
        this.zzaLr = pendingIntent;
        this.zzbzx = i2;
        this.zzbye = str;
        this.zzbyW = str2;
        this.zzbzy = bArr;
        this.zzbzr = z;
        if (iBinder3 == null) {
            zzac = null;
        } else if (iBinder3 == null) {
            zzac = null;
        } else {
            IInterface queryLocalInterface3 = iBinder3.queryLocalInterface("com.google.android.gms.nearby.messages.internal.ISubscribeCallback");
            zzac = queryLocalInterface3 instanceof zzaa ? (zzaa) queryLocalInterface3 : new zzac(iBinder3);
        }
        this.zzbzz = zzac;
        this.zzbyf = z2;
        this.zzbzb = ClientAppContext.zza(clientAppContext, str2, str, z2);
        this.zzbyA = z3;
        this.zzbyB = i3;
    }

    public SubscribeRequest(IBinder iBinder, Strategy strategy, IBinder iBinder2, MessageFilter messageFilter, PendingIntent pendingIntent, byte[] bArr, IBinder iBinder3, boolean z) {
        this(iBinder, strategy, iBinder2, messageFilter, (PendingIntent) null, (byte[]) null, iBinder3, z, 0);
    }

    public SubscribeRequest(IBinder iBinder, Strategy strategy, IBinder iBinder2, MessageFilter messageFilter, PendingIntent pendingIntent, byte[] bArr, IBinder iBinder3, boolean z, int i) {
        this(3, iBinder, strategy, iBinder2, messageFilter, pendingIntent, 0, (String) null, (String) null, bArr, false, iBinder3, false, (ClientAppContext) null, z, i);
    }

    public final String toString() {
        String sb;
        String valueOf = String.valueOf(this.zzbzv);
        String valueOf2 = String.valueOf(this.zzbzq);
        String valueOf3 = String.valueOf(this.zzbza);
        String valueOf4 = String.valueOf(this.zzbzw);
        String valueOf5 = String.valueOf(this.zzaLr);
        if (this.zzbzy == null) {
            sb = null;
        } else {
            sb = new StringBuilder(19).append("<").append(this.zzbzy.length).append(" bytes>").toString();
        }
        String valueOf6 = String.valueOf(this.zzbzz);
        boolean z = this.zzbyf;
        String valueOf7 = String.valueOf(this.zzbzb);
        boolean z2 = this.zzbyA;
        String str = this.zzbye;
        String str2 = this.zzbyW;
        return new StringBuilder(String.valueOf(valueOf).length() + MediaRouter.GlobalMediaRouter.CallbackHandler.MSG_ROUTE_UNSELECTED + String.valueOf(valueOf2).length() + String.valueOf(valueOf3).length() + String.valueOf(valueOf4).length() + String.valueOf(valueOf5).length() + String.valueOf(sb).length() + String.valueOf(valueOf6).length() + String.valueOf(valueOf7).length() + String.valueOf(str).length() + String.valueOf(str2).length()).append("SubscribeRequest{messageListener=").append(valueOf).append(", strategy=").append(valueOf2).append(", callback=").append(valueOf3).append(", filter=").append(valueOf4).append(", pendingIntent=").append(valueOf5).append(", hint=").append(sb).append(", subscribeCallback=").append(valueOf6).append(", useRealClientApiKey=").append(z).append(", clientAppContext=").append(valueOf7).append(", isDiscardPendingIntent=").append(z2).append(", zeroPartyPackageName=").append(str).append(", realClientPackageName=").append(str2).append(", isIgnoreNearbyPermission=").append(this.zzbzr).append("}").toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        IBinder iBinder = null;
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, this.zzaku);
        zzd.zza(parcel, 2, this.zzbzv == null ? null : this.zzbzv.asBinder(), false);
        zzd.zza(parcel, 3, (Parcelable) this.zzbzq, i, false);
        zzd.zza(parcel, 4, this.zzbza == null ? null : this.zzbza.asBinder(), false);
        zzd.zza(parcel, 5, (Parcelable) this.zzbzw, i, false);
        zzd.zza(parcel, 6, (Parcelable) this.zzaLr, i, false);
        zzd.zzc(parcel, 7, this.zzbzx);
        zzd.zza(parcel, 8, this.zzbye, false);
        zzd.zza(parcel, 9, this.zzbyW, false);
        zzd.zza(parcel, 10, this.zzbzy, false);
        zzd.zza(parcel, 11, this.zzbzr);
        if (this.zzbzz != null) {
            iBinder = this.zzbzz.asBinder();
        }
        zzd.zza(parcel, 12, iBinder, false);
        zzd.zza(parcel, 13, this.zzbyf);
        zzd.zza(parcel, 14, (Parcelable) this.zzbzb, i, false);
        zzd.zza(parcel, 15, this.zzbyA);
        zzd.zzc(parcel, 16, this.zzbyB);
        zzd.zzI(parcel, zze);
    }
}
