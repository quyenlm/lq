package com.google.android.gms.drive.metadata.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.data.BitmapTeleporter;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbe;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.internal.zzbng;
import com.google.android.gms.internal.zzbrc;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public final class MetadataBundle extends zza implements ReflectedParcelable {
    public static final Parcelable.Creator<MetadataBundle> CREATOR = new zzj();
    private Bundle zzaPM;

    MetadataBundle(Bundle bundle) {
        this.zzaPM = (Bundle) zzbo.zzu(bundle);
        this.zzaPM.setClassLoader(getClass().getClassLoader());
        ArrayList arrayList = new ArrayList();
        for (String str : this.zzaPM.keySet()) {
            if (zzf.zzcR(str) == null) {
                arrayList.add(str);
                String valueOf = String.valueOf(str);
                zzbng.zzy("MetadataBundle", valueOf.length() != 0 ? "Ignored unknown metadata field in bundle: ".concat(valueOf) : new String("Ignored unknown metadata field in bundle: "));
            }
        }
        ArrayList arrayList2 = arrayList;
        int size = arrayList2.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            this.zzaPM.remove((String) obj);
        }
    }

    public static <T> MetadataBundle zzb(MetadataField<T> metadataField, T t) {
        MetadataBundle zztp = zztp();
        zztp.zzc(metadataField, t);
        return zztp;
    }

    public static MetadataBundle zztp() {
        return new MetadataBundle(new Bundle());
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MetadataBundle)) {
            return false;
        }
        MetadataBundle metadataBundle = (MetadataBundle) obj;
        Set<String> keySet = this.zzaPM.keySet();
        if (!keySet.equals(metadataBundle.zzaPM.keySet())) {
            return false;
        }
        for (String str : keySet) {
            if (!zzbe.equal(this.zzaPM.get(str), metadataBundle.zzaPM.get(str))) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        int i = 1;
        Iterator it = this.zzaPM.keySet().iterator();
        while (true) {
            int i2 = i;
            if (!it.hasNext()) {
                return i2;
            }
            i = this.zzaPM.get((String) it.next()).hashCode() + (i2 * 31);
        }
    }

    public final void setContext(Context context) {
        BitmapTeleporter bitmapTeleporter = (BitmapTeleporter) zza(zzbrc.zzaQv);
        if (bitmapTeleporter != null) {
            bitmapTeleporter.zzc(context.getCacheDir());
        }
    }

    public final String toString() {
        String valueOf = String.valueOf(this.zzaPM);
        return new StringBuilder(String.valueOf(valueOf).length() + 24).append("MetadataBundle [values=").append(valueOf).append("]").toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 2, this.zzaPM, false);
        zzd.zzI(parcel, zze);
    }

    public final <T> T zza(MetadataField<T> metadataField) {
        return metadataField.zzp(this.zzaPM);
    }

    public final <T> void zzc(MetadataField<T> metadataField, T t) {
        if (zzf.zzcR(metadataField.getName()) == null) {
            String valueOf = String.valueOf(metadataField.getName());
            throw new IllegalArgumentException(valueOf.length() != 0 ? "Unregistered field: ".concat(valueOf) : new String("Unregistered field: "));
        } else {
            metadataField.zza(t, this.zzaPM);
        }
    }

    public final boolean zzc(MetadataField<?> metadataField) {
        return this.zzaPM.containsKey(metadataField.getName());
    }

    public final MetadataBundle zztq() {
        return new MetadataBundle(new Bundle(this.zzaPM));
    }

    public final Set<MetadataField<?>> zztr() {
        HashSet hashSet = new HashSet();
        for (String zzcR : this.zzaPM.keySet()) {
            hashSet.add(zzf.zzcR(zzcR));
        }
        return hashSet;
    }
}
