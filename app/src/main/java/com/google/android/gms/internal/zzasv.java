package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.amazonaws.services.s3.internal.Constants;
import com.google.android.gms.awareness.state.BeaconState;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbo;
import com.tencent.tp.a.h;
import java.util.Arrays;

public final class zzasv extends BeaconState.TypeFilter {
    public static final Parcelable.Creator<zzasv> CREATOR = new zzaug();
    private final ack zzanT;

    public zzasv(String str, String str2) {
        this.zzanT = new ack();
        this.zzanT.zzbxU = zzbo.zzcF(str);
        this.zzanT.type = zzbo.zzcF(str2);
    }

    public zzasv(String str, String str2, byte[] bArr) {
        this.zzanT = new ack();
        this.zzanT.zzbxU = zzbo.zzcF(str);
        this.zzanT.type = zzbo.zzcF(str2);
        this.zzanT.content = (byte[]) zzbo.zzu(bArr);
    }

    zzasv(byte[] bArr) {
        ack ack;
        try {
            ack = (ack) adp.zza(new ack(), bArr);
        } catch (ado e) {
            zzeq.zzd("BeaconStateImpl", "Could not deserialize BeaconFence.BeaconTypeFilter");
            ack = null;
        }
        this.zzanT = ack;
    }

    private final byte[] getContent() {
        if (this.zzanT == null || this.zzanT.content == null || this.zzanT.content.length == 0) {
            return null;
        }
        return this.zzanT.content;
    }

    private final String getNamespace() {
        if (this.zzanT == null) {
            return null;
        }
        return this.zzanT.zzbxU;
    }

    private final String getType() {
        if (this.zzanT == null) {
            return null;
        }
        return this.zzanT.type;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzasv)) {
            return false;
        }
        zzasv zzasv = (zzasv) obj;
        return TextUtils.equals(getNamespace(), zzasv.getNamespace()) && TextUtils.equals(getType(), zzasv.getType()) && Arrays.equals(getContent(), zzasv.getContent());
    }

    public final int hashCode() {
        int i = 0;
        Object[] objArr = new Object[3];
        objArr[0] = getNamespace();
        objArr[1] = getType();
        if (getContent() != null) {
            i = Arrays.hashCode(getContent());
        }
        objArr[2] = Integer.valueOf(i);
        return Arrays.hashCode(objArr);
    }

    public final String toString() {
        String valueOf = String.valueOf(getNamespace());
        String valueOf2 = String.valueOf(getType());
        String str = getContent() == null ? Constants.NULL_VERSION_ID : new String(getContent());
        return new StringBuilder(String.valueOf(valueOf).length() + 4 + String.valueOf(valueOf2).length() + String.valueOf(str).length()).append(h.a).append(valueOf).append(",").append(valueOf2).append(",").append(str).append(h.b).toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 2, adp.zzc(this.zzanT), false);
        zzd.zzI(parcel, zze);
    }

    public final ack zzmV() {
        return this.zzanT;
    }
}
