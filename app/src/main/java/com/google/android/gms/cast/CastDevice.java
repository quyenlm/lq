package com.google.android.gms.cast;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.images.WebImage;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.internal.zzaye;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CastDevice extends zza implements ReflectedParcelable {
    public static final int CAPABILITY_AUDIO_IN = 8;
    public static final int CAPABILITY_AUDIO_OUT = 4;
    public static final int CAPABILITY_MULTIZONE_GROUP = 32;
    public static final int CAPABILITY_VIDEO_IN = 2;
    public static final int CAPABILITY_VIDEO_OUT = 1;
    public static final Parcelable.Creator<CastDevice> CREATOR = new zzn();
    private int zzLe;
    private String zzapa;
    private String zzapb;
    private Inet4Address zzapc;
    private String zzapd;
    private String zzape;
    private String zzapf;
    private int zzapg;
    private List<WebImage> zzaph;
    private int zzapi;
    private String zzapj;
    private String zzapk;
    private int zzapl;

    CastDevice(String str, String str2, String str3, String str4, String str5, int i, List<WebImage> list, int i2, int i3, String str6, String str7, int i4) {
        this.zzapa = zzbX(str);
        this.zzapb = zzbX(str2);
        if (!TextUtils.isEmpty(this.zzapb)) {
            try {
                InetAddress byName = InetAddress.getByName(this.zzapb);
                if (byName instanceof Inet4Address) {
                    this.zzapc = (Inet4Address) byName;
                }
            } catch (UnknownHostException e) {
                String str8 = this.zzapb;
                String valueOf = String.valueOf(e.getMessage());
                Log.i("CastDevice", new StringBuilder(String.valueOf(str8).length() + 48 + String.valueOf(valueOf).length()).append("Unable to convert host address (").append(str8).append(") to ipaddress: ").append(valueOf).toString());
            }
        }
        this.zzapd = zzbX(str3);
        this.zzape = zzbX(str4);
        this.zzapf = zzbX(str5);
        this.zzapg = i;
        this.zzaph = list == null ? new ArrayList<>() : list;
        this.zzapi = i2;
        this.zzLe = i3;
        this.zzapj = zzbX(str6);
        this.zzapk = str7;
        this.zzapl = i4;
    }

    public static CastDevice getFromBundle(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        bundle.setClassLoader(CastDevice.class.getClassLoader());
        return (CastDevice) bundle.getParcelable("com.google.android.gms.cast.EXTRA_CAST_DEVICE");
    }

    private static String zzbX(String str) {
        return str == null ? "" : str;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof CastDevice)) {
            return false;
        }
        CastDevice castDevice = (CastDevice) obj;
        return this.zzapa == null ? castDevice.zzapa == null : zzaye.zza(this.zzapa, castDevice.zzapa) && zzaye.zza(this.zzapc, castDevice.zzapc) && zzaye.zza(this.zzape, castDevice.zzape) && zzaye.zza(this.zzapd, castDevice.zzapd) && zzaye.zza(this.zzapf, castDevice.zzapf) && this.zzapg == castDevice.zzapg && zzaye.zza(this.zzaph, castDevice.zzaph) && this.zzapi == castDevice.zzapi && this.zzLe == castDevice.zzLe && zzaye.zza(this.zzapj, castDevice.zzapj) && zzaye.zza(Integer.valueOf(this.zzapl), Integer.valueOf(castDevice.zzapl));
    }

    public String getDeviceId() {
        return this.zzapa.startsWith("__cast_nearby__") ? this.zzapa.substring(16) : this.zzapa;
    }

    public String getDeviceVersion() {
        return this.zzapf;
    }

    public String getFriendlyName() {
        return this.zzapd;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0058, code lost:
        if (r1.getHeight() < r5) goto L_0x005a;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.google.android.gms.common.images.WebImage getIcon(int r9, int r10) {
        /*
            r8 = this;
            r7 = 0
            r0 = 0
            java.util.List<com.google.android.gms.common.images.WebImage> r1 = r8.zzaph
            boolean r1 = r1.isEmpty()
            if (r1 == 0) goto L_0x000b
        L_0x000a:
            return r0
        L_0x000b:
            if (r9 <= 0) goto L_0x000f
            if (r10 > 0) goto L_0x0018
        L_0x000f:
            java.util.List<com.google.android.gms.common.images.WebImage> r0 = r8.zzaph
            java.lang.Object r0 = r0.get(r7)
            com.google.android.gms.common.images.WebImage r0 = (com.google.android.gms.common.images.WebImage) r0
            goto L_0x000a
        L_0x0018:
            java.util.List<com.google.android.gms.common.images.WebImage> r1 = r8.zzaph
            java.util.Iterator r3 = r1.iterator()
            r1 = r0
            r2 = r0
        L_0x0020:
            boolean r0 = r3.hasNext()
            if (r0 == 0) goto L_0x005c
            java.lang.Object r0 = r3.next()
            com.google.android.gms.common.images.WebImage r0 = (com.google.android.gms.common.images.WebImage) r0
            int r4 = r0.getWidth()
            int r5 = r0.getHeight()
            if (r4 < r9) goto L_0x0048
            if (r5 < r10) goto L_0x0048
            if (r2 == 0) goto L_0x0046
            int r6 = r2.getWidth()
            if (r6 <= r4) goto L_0x006e
            int r4 = r2.getHeight()
            if (r4 <= r5) goto L_0x006e
        L_0x0046:
            r2 = r0
            goto L_0x0020
        L_0x0048:
            if (r4 >= r9) goto L_0x006e
            if (r5 >= r10) goto L_0x006e
            if (r1 == 0) goto L_0x005a
            int r6 = r1.getWidth()
            if (r6 >= r4) goto L_0x006e
            int r4 = r1.getHeight()
            if (r4 >= r5) goto L_0x006e
        L_0x005a:
            r1 = r0
            goto L_0x0020
        L_0x005c:
            if (r2 == 0) goto L_0x0060
        L_0x005e:
            r0 = r2
            goto L_0x000a
        L_0x0060:
            if (r1 == 0) goto L_0x0064
            r2 = r1
            goto L_0x005e
        L_0x0064:
            java.util.List<com.google.android.gms.common.images.WebImage> r0 = r8.zzaph
            java.lang.Object r0 = r0.get(r7)
            com.google.android.gms.common.images.WebImage r0 = (com.google.android.gms.common.images.WebImage) r0
            r2 = r0
            goto L_0x005e
        L_0x006e:
            r0 = r1
            goto L_0x005a
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.CastDevice.getIcon(int, int):com.google.android.gms.common.images.WebImage");
    }

    public List<WebImage> getIcons() {
        return Collections.unmodifiableList(this.zzaph);
    }

    public Inet4Address getIpAddress() {
        return this.zzapc;
    }

    public String getModelName() {
        return this.zzape;
    }

    public int getServicePort() {
        return this.zzapg;
    }

    public boolean hasCapabilities(int[] iArr) {
        if (iArr == null) {
            return false;
        }
        for (int hasCapability : iArr) {
            if (!hasCapability(hasCapability)) {
                return false;
            }
        }
        return true;
    }

    public boolean hasCapability(int i) {
        return (this.zzapi & i) == i;
    }

    public boolean hasIcons() {
        return !this.zzaph.isEmpty();
    }

    public int hashCode() {
        if (this.zzapa == null) {
            return 0;
        }
        return this.zzapa.hashCode();
    }

    public boolean isOnLocalNetwork() {
        return !this.zzapa.startsWith("__cast_nearby__");
    }

    public boolean isSameDevice(CastDevice castDevice) {
        if (castDevice == null) {
            return false;
        }
        return this.zzapa == null ? castDevice.zzapa == null : zzaye.zza(this.zzapa, castDevice.zzapa);
    }

    public void putInBundle(Bundle bundle) {
        if (bundle != null) {
            bundle.putParcelable("com.google.android.gms.cast.EXTRA_CAST_DEVICE", this);
        }
    }

    public String toString() {
        return String.format("\"%s\" (%s)", new Object[]{this.zzapd, this.zzapa});
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 2, this.zzapa, false);
        zzd.zza(parcel, 3, this.zzapb, false);
        zzd.zza(parcel, 4, getFriendlyName(), false);
        zzd.zza(parcel, 5, getModelName(), false);
        zzd.zza(parcel, 6, getDeviceVersion(), false);
        zzd.zzc(parcel, 7, getServicePort());
        zzd.zzc(parcel, 8, getIcons(), false);
        zzd.zzc(parcel, 9, this.zzapi);
        zzd.zzc(parcel, 10, this.zzLe);
        zzd.zza(parcel, 11, this.zzapj, false);
        zzd.zza(parcel, 12, this.zzapk, false);
        zzd.zzc(parcel, 13, this.zzapl);
        zzd.zzI(parcel, zze);
    }
}
