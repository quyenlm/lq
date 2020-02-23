package com.google.android.gms.fitness.data;

import android.content.Context;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.Settings;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbe;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.internal.zzbui;
import com.google.android.gms.internal.zzbzn;
import java.util.Arrays;

public final class Device extends zza {
    public static final Parcelable.Creator<Device> CREATOR = new zzo();
    public static final int TYPE_CHEST_STRAP = 4;
    public static final int TYPE_HEAD_MOUNTED = 6;
    public static final int TYPE_PHONE = 1;
    public static final int TYPE_SCALE = 5;
    public static final int TYPE_TABLET = 2;
    public static final int TYPE_UNKNOWN = 0;
    public static final int TYPE_WATCH = 3;
    private final int type;
    private final String version;
    private final int versionCode;
    private final String zzaUf;
    private final String zzaUg;
    private final String zzaUh;
    private final int zzaUi;

    Device(int i, String str, String str2, String str3, String str4, int i2, int i3) {
        this.versionCode = i;
        this.zzaUf = (String) zzbo.zzu(str);
        this.zzaUg = (String) zzbo.zzu(str2);
        this.version = "";
        if (str4 != null) {
            this.zzaUh = str4;
            this.type = i2;
            this.zzaUi = i3;
            return;
        }
        throw new IllegalStateException("Device UID is null.");
    }

    public Device(String str, String str2, String str3, int i) {
        this(str, str2, "", str3, i, 0);
    }

    private Device(String str, String str2, String str3, String str4, int i, int i2) {
        this(1, str, str2, "", str4, i, i2);
    }

    public static Device getLocalDevice(Context context) {
        int zzaU = zzbui.zzaU(context);
        return new Device(Build.MANUFACTURER, Build.MODEL, Build.VERSION.RELEASE, Settings.Secure.getString(context.getContentResolver(), "android_id"), zzaU, 2);
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (!(obj instanceof Device)) {
                return false;
            }
            Device device = (Device) obj;
            if (!(zzbe.equal(this.zzaUf, device.zzaUf) && zzbe.equal(this.zzaUg, device.zzaUg) && zzbe.equal(this.version, device.version) && zzbe.equal(this.zzaUh, device.zzaUh) && this.type == device.type && this.zzaUi == device.zzaUi)) {
                return false;
            }
        }
        return true;
    }

    public final String getManufacturer() {
        return this.zzaUf;
    }

    public final String getModel() {
        return this.zzaUg;
    }

    /* access modifiers changed from: package-private */
    public final String getStreamIdentifier() {
        return String.format("%s:%s:%s", new Object[]{this.zzaUf, this.zzaUg, this.zzaUh});
    }

    public final int getType() {
        return this.type;
    }

    public final String getUid() {
        return this.zzaUh;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzaUf, this.zzaUg, this.version, this.zzaUh, Integer.valueOf(this.type)});
    }

    public final String toString() {
        return String.format("Device{%s:%s:%s:%s}", new Object[]{getStreamIdentifier(), this.version, Integer.valueOf(this.type), Integer.valueOf(this.zzaUi)});
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 1, getManufacturer(), false);
        zzd.zza(parcel, 2, getModel(), false);
        zzd.zza(parcel, 3, this.version, false);
        zzd.zza(parcel, 4, this.zzaUi == 1 ? this.zzaUh : zzbzn.zzdh(this.zzaUh), false);
        zzd.zzc(parcel, 5, getType());
        zzd.zzc(parcel, 6, this.zzaUi);
        zzd.zzc(parcel, 1000, this.versionCode);
        zzd.zzI(parcel, zze);
    }
}
