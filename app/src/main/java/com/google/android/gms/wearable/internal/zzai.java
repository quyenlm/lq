package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.wearable.ChannelApi;

public final class zzai extends zza {
    public static final Parcelable.Creator<zzai> CREATOR = new zzaj();
    private int type;
    private int zzbSh;
    private int zzbSi;
    private zzak zzbSj;

    public zzai(zzak zzak, int i, int i2, int i3) {
        this.zzbSj = zzak;
        this.type = i;
        this.zzbSh = i2;
        this.zzbSi = i3;
    }

    public final String toString() {
        String str;
        String str2;
        String valueOf = String.valueOf(this.zzbSj);
        int i = this.type;
        switch (i) {
            case 1:
                str = "CHANNEL_OPENED";
                break;
            case 2:
                str = "CHANNEL_CLOSED";
                break;
            case 3:
                str = "INPUT_CLOSED";
                break;
            case 4:
                str = "OUTPUT_CLOSED";
                break;
            default:
                str = Integer.toString(i);
                break;
        }
        String valueOf2 = String.valueOf(str);
        int i2 = this.zzbSh;
        switch (i2) {
            case 0:
                str2 = "CLOSE_REASON_NORMAL";
                break;
            case 1:
                str2 = "CLOSE_REASON_DISCONNECTED";
                break;
            case 2:
                str2 = "CLOSE_REASON_REMOTE_CLOSE";
                break;
            case 3:
                str2 = "CLOSE_REASON_LOCAL_CLOSE";
                break;
            default:
                str2 = Integer.toString(i2);
                break;
        }
        String valueOf3 = String.valueOf(str2);
        return new StringBuilder(String.valueOf(valueOf).length() + 81 + String.valueOf(valueOf2).length() + String.valueOf(valueOf3).length()).append("ChannelEventParcelable[, channel=").append(valueOf).append(", type=").append(valueOf2).append(", closeReason=").append(valueOf3).append(", appErrorCode=").append(this.zzbSi).append("]").toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 2, (Parcelable) this.zzbSj, i, false);
        zzd.zzc(parcel, 3, this.type);
        zzd.zzc(parcel, 4, this.zzbSh);
        zzd.zzc(parcel, 5, this.zzbSi);
        zzd.zzI(parcel, zze);
    }

    public final void zza(ChannelApi.ChannelListener channelListener) {
        switch (this.type) {
            case 1:
                channelListener.onChannelOpened(this.zzbSj);
                return;
            case 2:
                channelListener.onChannelClosed(this.zzbSj, this.zzbSh, this.zzbSi);
                return;
            case 3:
                channelListener.onInputClosed(this.zzbSj, this.zzbSh, this.zzbSi);
                return;
            case 4:
                channelListener.onOutputClosed(this.zzbSj, this.zzbSh, this.zzbSi);
                return;
            default:
                Log.w("ChannelEventParcelable", new StringBuilder(25).append("Unknown type: ").append(this.type).toString());
                return;
        }
    }
}
