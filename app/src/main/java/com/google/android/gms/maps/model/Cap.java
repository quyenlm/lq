package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbe;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.dynamic.IObjectWrapper;
import java.util.Arrays;

public class Cap extends zza {
    public static final Parcelable.Creator<Cap> CREATOR = new zzb();
    private static final String TAG = Cap.class.getSimpleName();
    @Nullable
    private final BitmapDescriptor bitmapDescriptor;
    private final int type;
    @Nullable
    private final Float zzbng;

    protected Cap(int i) {
        this(i, (BitmapDescriptor) null, (Float) null);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    Cap(int i, @Nullable IBinder iBinder, @Nullable Float f) {
        this(i, iBinder == null ? null : new BitmapDescriptor(IObjectWrapper.zza.zzM(iBinder)), f);
    }

    private Cap(int i, @Nullable BitmapDescriptor bitmapDescriptor2, @Nullable Float f) {
        boolean z = false;
        boolean z2 = f != null && f.floatValue() > 0.0f;
        if (i != 3 || (bitmapDescriptor2 != null && z2)) {
            z = true;
        }
        String valueOf = String.valueOf(bitmapDescriptor2);
        String valueOf2 = String.valueOf(f);
        zzbo.zzb(z, (Object) new StringBuilder(String.valueOf(valueOf).length() + 63 + String.valueOf(valueOf2).length()).append("Invalid Cap: type=").append(i).append(" bitmapDescriptor=").append(valueOf).append(" bitmapRefWidth=").append(valueOf2).toString());
        this.type = i;
        this.bitmapDescriptor = bitmapDescriptor2;
        this.zzbng = f;
    }

    protected Cap(@NonNull BitmapDescriptor bitmapDescriptor2, float f) {
        this(3, bitmapDescriptor2, Float.valueOf(f));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Cap)) {
            return false;
        }
        Cap cap = (Cap) obj;
        return this.type == cap.type && zzbe.equal(this.bitmapDescriptor, cap.bitmapDescriptor) && zzbe.equal(this.zzbng, cap.zzbng);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Integer.valueOf(this.type), this.bitmapDescriptor, this.zzbng});
    }

    public String toString() {
        return new StringBuilder(23).append("[Cap: type=").append(this.type).append("]").toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 2, this.type);
        zzd.zza(parcel, 3, this.bitmapDescriptor == null ? null : this.bitmapDescriptor.zzwe().asBinder(), false);
        zzd.zza(parcel, 4, this.zzbng, false);
        zzd.zzI(parcel, zze);
    }

    /* access modifiers changed from: package-private */
    public final Cap zzwk() {
        switch (this.type) {
            case 0:
                return new ButtCap();
            case 1:
                return new SquareCap();
            case 2:
                return new RoundCap();
            case 3:
                return new CustomCap(this.bitmapDescriptor, this.zzbng.floatValue());
            default:
                Log.w(TAG, new StringBuilder(29).append("Unknown Cap type: ").append(this.type).toString());
                return this;
        }
    }
}
