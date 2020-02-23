package com.google.firebase.appindexing.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.firebase.appindexing.Indexable;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Set;

public final class Thing extends com.google.android.gms.common.internal.safeparcel.zza implements ReflectedParcelable, Indexable {
    public static final Parcelable.Creator<Thing> CREATOR = new zzz();
    private final String zzIi;
    private final String zzVB;
    private final Bundle zzajQ;
    private int zzaku;
    private final zza zzbVH;

    public static class zza extends com.google.android.gms.common.internal.safeparcel.zza {
        public static final Parcelable.Creator<zza> CREATOR = new zzx();
        private final Bundle zzajQ;
        private final boolean zzbVF;
        private String zzbVG;
        private int zzyh;

        public zza(boolean z, int i, String str, Bundle bundle) {
            this.zzbVF = z;
            this.zzyh = i;
            this.zzbVG = str;
            this.zzajQ = bundle;
        }

        public final String toString() {
            StringBuilder append = new StringBuilder("worksOffline: ").append(this.zzbVF).append(", score: ").append(this.zzyh);
            if (!this.zzbVG.isEmpty()) {
                append.append(", accountEmail: ").append(this.zzbVG);
            }
            if (this.zzajQ != null && !this.zzajQ.isEmpty()) {
                append.append(", Properties { ");
                Thing.zza(this.zzajQ, append);
                append.append("}");
            }
            return append.toString();
        }

        public final void writeToParcel(Parcel parcel, int i) {
            int zze = zzd.zze(parcel);
            zzd.zza(parcel, 1, this.zzbVF);
            zzd.zzc(parcel, 2, this.zzyh);
            zzd.zza(parcel, 3, this.zzbVG, false);
            zzd.zza(parcel, 4, this.zzajQ, false);
            zzd.zzI(parcel, zze);
        }
    }

    public Thing(int i, Bundle bundle, zza zza2, String str, String str2) {
        this.zzaku = i;
        this.zzajQ = bundle;
        this.zzbVH = zza2;
        this.zzIi = str;
        this.zzVB = str2;
        this.zzajQ.setClassLoader(getClass().getClassLoader());
    }

    public Thing(@NonNull Bundle bundle, @NonNull zza zza2, String str, @NonNull String str2) {
        this.zzaku = 10;
        this.zzajQ = bundle;
        this.zzbVH = zza2;
        this.zzIi = str;
        this.zzVB = str2;
    }

    /* access modifiers changed from: private */
    public static void zza(@NonNull Bundle bundle, @NonNull StringBuilder sb) {
        Set keySet = bundle.keySet();
        String[] strArr = (String[]) keySet.toArray(new String[keySet.size()]);
        Arrays.sort(strArr);
        for (String str : strArr) {
            sb.append("{ key: '").append(str).append("' value: ");
            Object obj = bundle.get(str);
            if (obj == null) {
                sb.append("<null>");
            } else if (obj.getClass().isArray()) {
                sb.append("[ ");
                for (int i = 0; i < Array.getLength(obj); i++) {
                    sb.append("'").append(Array.get(obj, i)).append("' ");
                }
                sb.append("]");
            } else {
                sb.append(obj.toString());
            }
            sb.append(" } ");
        }
    }

    public final String toString() {
        StringBuilder append = new StringBuilder().append(this.zzVB.equals("Thing") ? "Indexable" : this.zzVB).append(" { { id: ");
        if (this.zzIi == null) {
            append.append("<null>");
        } else {
            append.append("'").append(this.zzIi).append("'");
        }
        append.append(" } Properties { ");
        zza(this.zzajQ, append);
        append.append("} ");
        append.append("Metadata { ");
        append.append(this.zzbVH.toString());
        append.append(" } ");
        append.append("}");
        return append.toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 1, this.zzajQ, false);
        zzd.zza(parcel, 2, (Parcelable) this.zzbVH, i, false);
        zzd.zza(parcel, 3, this.zzIi, false);
        zzd.zza(parcel, 4, this.zzVB, false);
        zzd.zzc(parcel, 1000, this.zzaku);
        zzd.zzI(parcel, zze);
    }
}
