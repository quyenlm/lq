package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.places.model.PlaceFields;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.plus.PlusShare;
import com.google.android.gms.plus.model.people.Person;
import com.tencent.ieg.ntv.ctrl.chat.ChatMsg;
import com.vk.sdk.api.VKApiConst;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class zzcri extends zzbgl implements Person {
    public static final Parcelable.Creator<zzcri> CREATOR = new zzcrj();
    private static final HashMap<String, zzbgj<?, ?>> zzbAS;
    private int zzAe;
    private String zzD;
    private String zzIi;
    private String zzaeT;
    private int zzaku;
    private String zzalP;
    private Set<Integer> zzbAT;
    private String zzbAU;
    private zza zzbAV;
    private String zzbAW;
    private String zzbAX;
    private int zzbAY;
    private zzb zzbAZ;
    private String zzbBa;
    private zzc zzbBb;
    private boolean zzbBc;
    private zzd zzbBd;
    private String zzbBe;
    private int zzbBf;
    private List<zze> zzbBg;
    private List<zzf> zzbBh;
    private int zzbBi;
    private int zzbBj;
    private String zzbBk;
    private List<zzg> zzbBl;
    private boolean zzbBm;

    public static final class zza extends zzbgl implements Person.AgeRange {
        public static final Parcelable.Creator<zza> CREATOR = new zzcrk();
        private static final HashMap<String, zzbgj<?, ?>> zzbAS;
        private int zzaku;
        private Set<Integer> zzbAT;
        private int zzbBn;
        private int zzbBo;

        static {
            HashMap<String, zzbgj<?, ?>> hashMap = new HashMap<>();
            zzbAS = hashMap;
            hashMap.put("max", zzbgj.zzj("max", 2));
            zzbAS.put("min", zzbgj.zzj("min", 3));
        }

        public zza() {
            this.zzaku = 1;
            this.zzbAT = new HashSet();
        }

        zza(Set<Integer> set, int i, int i2, int i3) {
            this.zzbAT = set;
            this.zzaku = i;
            this.zzbBn = i2;
            this.zzbBo = i3;
        }

        public final boolean equals(Object obj) {
            if (!(obj instanceof zza)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            zza zza = (zza) obj;
            for (zzbgj next : zzbAS.values()) {
                if (zza(next)) {
                    if (!zza.zza(next)) {
                        return false;
                    }
                    if (!zzb(next).equals(zza.zzb(next))) {
                        return false;
                    }
                } else if (zza.zza(next)) {
                    return false;
                }
            }
            return true;
        }

        public final /* bridge */ /* synthetic */ Object freeze() {
            return this;
        }

        public final int getMax() {
            return this.zzbBn;
        }

        public final int getMin() {
            return this.zzbBo;
        }

        public final boolean hasMax() {
            return this.zzbAT.contains(2);
        }

        public final boolean hasMin() {
            return this.zzbAT.contains(3);
        }

        public final int hashCode() {
            int i = 0;
            Iterator<zzbgj<?, ?>> it = zzbAS.values().iterator();
            while (true) {
                int i2 = i;
                if (!it.hasNext()) {
                    return i2;
                }
                zzbgj next = it.next();
                if (zza(next)) {
                    i = zzb(next).hashCode() + i2 + next.zzrM();
                } else {
                    i = i2;
                }
            }
        }

        public final boolean isDataValid() {
            return true;
        }

        public final void writeToParcel(Parcel parcel, int i) {
            int zze = com.google.android.gms.common.internal.safeparcel.zzd.zze(parcel);
            Set<Integer> set = this.zzbAT;
            if (set.contains(1)) {
                com.google.android.gms.common.internal.safeparcel.zzd.zzc(parcel, 1, this.zzaku);
            }
            if (set.contains(2)) {
                com.google.android.gms.common.internal.safeparcel.zzd.zzc(parcel, 2, this.zzbBn);
            }
            if (set.contains(3)) {
                com.google.android.gms.common.internal.safeparcel.zzd.zzc(parcel, 3, this.zzbBo);
            }
            com.google.android.gms.common.internal.safeparcel.zzd.zzI(parcel, zze);
        }

        /* access modifiers changed from: protected */
        public final boolean zza(zzbgj zzbgj) {
            return this.zzbAT.contains(Integer.valueOf(zzbgj.zzrM()));
        }

        /* access modifiers changed from: protected */
        public final Object zzb(zzbgj zzbgj) {
            switch (zzbgj.zzrM()) {
                case 2:
                    return Integer.valueOf(this.zzbBn);
                case 3:
                    return Integer.valueOf(this.zzbBo);
                default:
                    throw new IllegalStateException(new StringBuilder(38).append("Unknown safe parcelable id=").append(zzbgj.zzrM()).toString());
            }
        }

        public final /* synthetic */ Map zzrL() {
            return zzbAS;
        }
    }

    public static final class zzb extends zzbgl implements Person.Cover {
        public static final Parcelable.Creator<zzb> CREATOR = new zzcrl();
        private static final HashMap<String, zzbgj<?, ?>> zzbAS;
        private int zzaku;
        private Set<Integer> zzbAT;
        private zza zzbBp;
        private C0036zzb zzbBq;
        private int zzbBr;

        public static final class zza extends zzbgl implements Person.Cover.CoverInfo {
            public static final Parcelable.Creator<zza> CREATOR = new zzcrm();
            private static final HashMap<String, zzbgj<?, ?>> zzbAS;
            private int zzaku;
            private Set<Integer> zzbAT;
            private int zzbBs;
            private int zzbBt;

            static {
                HashMap<String, zzbgj<?, ?>> hashMap = new HashMap<>();
                zzbAS = hashMap;
                hashMap.put("leftImageOffset", zzbgj.zzj("leftImageOffset", 2));
                zzbAS.put("topImageOffset", zzbgj.zzj("topImageOffset", 3));
            }

            public zza() {
                this.zzaku = 1;
                this.zzbAT = new HashSet();
            }

            zza(Set<Integer> set, int i, int i2, int i3) {
                this.zzbAT = set;
                this.zzaku = i;
                this.zzbBs = i2;
                this.zzbBt = i3;
            }

            public final boolean equals(Object obj) {
                if (!(obj instanceof zza)) {
                    return false;
                }
                if (this == obj) {
                    return true;
                }
                zza zza = (zza) obj;
                for (zzbgj next : zzbAS.values()) {
                    if (zza(next)) {
                        if (!zza.zza(next)) {
                            return false;
                        }
                        if (!zzb(next).equals(zza.zzb(next))) {
                            return false;
                        }
                    } else if (zza.zza(next)) {
                        return false;
                    }
                }
                return true;
            }

            public final /* bridge */ /* synthetic */ Object freeze() {
                return this;
            }

            public final int getLeftImageOffset() {
                return this.zzbBs;
            }

            public final int getTopImageOffset() {
                return this.zzbBt;
            }

            public final boolean hasLeftImageOffset() {
                return this.zzbAT.contains(2);
            }

            public final boolean hasTopImageOffset() {
                return this.zzbAT.contains(3);
            }

            public final int hashCode() {
                int i = 0;
                Iterator<zzbgj<?, ?>> it = zzbAS.values().iterator();
                while (true) {
                    int i2 = i;
                    if (!it.hasNext()) {
                        return i2;
                    }
                    zzbgj next = it.next();
                    if (zza(next)) {
                        i = zzb(next).hashCode() + i2 + next.zzrM();
                    } else {
                        i = i2;
                    }
                }
            }

            public final boolean isDataValid() {
                return true;
            }

            public final void writeToParcel(Parcel parcel, int i) {
                int zze = com.google.android.gms.common.internal.safeparcel.zzd.zze(parcel);
                Set<Integer> set = this.zzbAT;
                if (set.contains(1)) {
                    com.google.android.gms.common.internal.safeparcel.zzd.zzc(parcel, 1, this.zzaku);
                }
                if (set.contains(2)) {
                    com.google.android.gms.common.internal.safeparcel.zzd.zzc(parcel, 2, this.zzbBs);
                }
                if (set.contains(3)) {
                    com.google.android.gms.common.internal.safeparcel.zzd.zzc(parcel, 3, this.zzbBt);
                }
                com.google.android.gms.common.internal.safeparcel.zzd.zzI(parcel, zze);
            }

            /* access modifiers changed from: protected */
            public final boolean zza(zzbgj zzbgj) {
                return this.zzbAT.contains(Integer.valueOf(zzbgj.zzrM()));
            }

            /* access modifiers changed from: protected */
            public final Object zzb(zzbgj zzbgj) {
                switch (zzbgj.zzrM()) {
                    case 2:
                        return Integer.valueOf(this.zzbBs);
                    case 3:
                        return Integer.valueOf(this.zzbBt);
                    default:
                        throw new IllegalStateException(new StringBuilder(38).append("Unknown safe parcelable id=").append(zzbgj.zzrM()).toString());
                }
            }

            public final /* synthetic */ Map zzrL() {
                return zzbAS;
            }
        }

        /* renamed from: com.google.android.gms.internal.zzcri$zzb$zzb  reason: collision with other inner class name */
        public static final class C0036zzb extends zzbgl implements Person.Cover.CoverPhoto {
            public static final Parcelable.Creator<C0036zzb> CREATOR = new zzcrn();
            private static final HashMap<String, zzbgj<?, ?>> zzbAS;
            private String zzD;
            private int zzaku;
            private Set<Integer> zzbAT;
            private int zzrW;
            private int zzrX;

            static {
                HashMap<String, zzbgj<?, ?>> hashMap = new HashMap<>();
                zzbAS = hashMap;
                hashMap.put("height", zzbgj.zzj("height", 2));
                zzbAS.put("url", zzbgj.zzl("url", 3));
                zzbAS.put("width", zzbgj.zzj("width", 4));
            }

            public C0036zzb() {
                this.zzaku = 1;
                this.zzbAT = new HashSet();
            }

            C0036zzb(Set<Integer> set, int i, int i2, String str, int i3) {
                this.zzbAT = set;
                this.zzaku = i;
                this.zzrX = i2;
                this.zzD = str;
                this.zzrW = i3;
            }

            public final boolean equals(Object obj) {
                if (!(obj instanceof C0036zzb)) {
                    return false;
                }
                if (this == obj) {
                    return true;
                }
                C0036zzb zzb = (C0036zzb) obj;
                for (zzbgj next : zzbAS.values()) {
                    if (zza(next)) {
                        if (!zzb.zza(next)) {
                            return false;
                        }
                        if (!zzb(next).equals(zzb.zzb(next))) {
                            return false;
                        }
                    } else if (zzb.zza(next)) {
                        return false;
                    }
                }
                return true;
            }

            public final /* bridge */ /* synthetic */ Object freeze() {
                return this;
            }

            public final int getHeight() {
                return this.zzrX;
            }

            public final String getUrl() {
                return this.zzD;
            }

            public final int getWidth() {
                return this.zzrW;
            }

            public final boolean hasHeight() {
                return this.zzbAT.contains(2);
            }

            public final boolean hasUrl() {
                return this.zzbAT.contains(3);
            }

            public final boolean hasWidth() {
                return this.zzbAT.contains(4);
            }

            public final int hashCode() {
                int i = 0;
                Iterator<zzbgj<?, ?>> it = zzbAS.values().iterator();
                while (true) {
                    int i2 = i;
                    if (!it.hasNext()) {
                        return i2;
                    }
                    zzbgj next = it.next();
                    if (zza(next)) {
                        i = zzb(next).hashCode() + i2 + next.zzrM();
                    } else {
                        i = i2;
                    }
                }
            }

            public final boolean isDataValid() {
                return true;
            }

            public final void writeToParcel(Parcel parcel, int i) {
                int zze = com.google.android.gms.common.internal.safeparcel.zzd.zze(parcel);
                Set<Integer> set = this.zzbAT;
                if (set.contains(1)) {
                    com.google.android.gms.common.internal.safeparcel.zzd.zzc(parcel, 1, this.zzaku);
                }
                if (set.contains(2)) {
                    com.google.android.gms.common.internal.safeparcel.zzd.zzc(parcel, 2, this.zzrX);
                }
                if (set.contains(3)) {
                    com.google.android.gms.common.internal.safeparcel.zzd.zza(parcel, 3, this.zzD, true);
                }
                if (set.contains(4)) {
                    com.google.android.gms.common.internal.safeparcel.zzd.zzc(parcel, 4, this.zzrW);
                }
                com.google.android.gms.common.internal.safeparcel.zzd.zzI(parcel, zze);
            }

            /* access modifiers changed from: protected */
            public final boolean zza(zzbgj zzbgj) {
                return this.zzbAT.contains(Integer.valueOf(zzbgj.zzrM()));
            }

            /* access modifiers changed from: protected */
            public final Object zzb(zzbgj zzbgj) {
                switch (zzbgj.zzrM()) {
                    case 2:
                        return Integer.valueOf(this.zzrX);
                    case 3:
                        return this.zzD;
                    case 4:
                        return Integer.valueOf(this.zzrW);
                    default:
                        throw new IllegalStateException(new StringBuilder(38).append("Unknown safe parcelable id=").append(zzbgj.zzrM()).toString());
                }
            }

            public final /* synthetic */ Map zzrL() {
                return zzbAS;
            }
        }

        static {
            HashMap<String, zzbgj<?, ?>> hashMap = new HashMap<>();
            zzbAS = hashMap;
            hashMap.put("coverInfo", zzbgj.zza("coverInfo", 2, zza.class));
            zzbAS.put("coverPhoto", zzbgj.zza("coverPhoto", 3, C0036zzb.class));
            zzbAS.put("layout", zzbgj.zza("layout", 4, new zzbge().zzi("banner", 0), false));
        }

        public zzb() {
            this.zzaku = 1;
            this.zzbAT = new HashSet();
        }

        zzb(Set<Integer> set, int i, zza zza2, C0036zzb zzb, int i2) {
            this.zzbAT = set;
            this.zzaku = i;
            this.zzbBp = zza2;
            this.zzbBq = zzb;
            this.zzbBr = i2;
        }

        public final boolean equals(Object obj) {
            if (!(obj instanceof zzb)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            zzb zzb = (zzb) obj;
            for (zzbgj next : zzbAS.values()) {
                if (zza(next)) {
                    if (!zzb.zza(next)) {
                        return false;
                    }
                    if (!zzb(next).equals(zzb.zzb(next))) {
                        return false;
                    }
                } else if (zzb.zza(next)) {
                    return false;
                }
            }
            return true;
        }

        public final /* bridge */ /* synthetic */ Object freeze() {
            return this;
        }

        public final Person.Cover.CoverInfo getCoverInfo() {
            return this.zzbBp;
        }

        public final Person.Cover.CoverPhoto getCoverPhoto() {
            return this.zzbBq;
        }

        public final int getLayout() {
            return this.zzbBr;
        }

        public final boolean hasCoverInfo() {
            return this.zzbAT.contains(2);
        }

        public final boolean hasCoverPhoto() {
            return this.zzbAT.contains(3);
        }

        public final boolean hasLayout() {
            return this.zzbAT.contains(4);
        }

        public final int hashCode() {
            int i = 0;
            Iterator<zzbgj<?, ?>> it = zzbAS.values().iterator();
            while (true) {
                int i2 = i;
                if (!it.hasNext()) {
                    return i2;
                }
                zzbgj next = it.next();
                if (zza(next)) {
                    i = zzb(next).hashCode() + i2 + next.zzrM();
                } else {
                    i = i2;
                }
            }
        }

        public final boolean isDataValid() {
            return true;
        }

        public final void writeToParcel(Parcel parcel, int i) {
            int zze = com.google.android.gms.common.internal.safeparcel.zzd.zze(parcel);
            Set<Integer> set = this.zzbAT;
            if (set.contains(1)) {
                com.google.android.gms.common.internal.safeparcel.zzd.zzc(parcel, 1, this.zzaku);
            }
            if (set.contains(2)) {
                com.google.android.gms.common.internal.safeparcel.zzd.zza(parcel, 2, (Parcelable) this.zzbBp, i, true);
            }
            if (set.contains(3)) {
                com.google.android.gms.common.internal.safeparcel.zzd.zza(parcel, 3, (Parcelable) this.zzbBq, i, true);
            }
            if (set.contains(4)) {
                com.google.android.gms.common.internal.safeparcel.zzd.zzc(parcel, 4, this.zzbBr);
            }
            com.google.android.gms.common.internal.safeparcel.zzd.zzI(parcel, zze);
        }

        /* access modifiers changed from: protected */
        public final boolean zza(zzbgj zzbgj) {
            return this.zzbAT.contains(Integer.valueOf(zzbgj.zzrM()));
        }

        /* access modifiers changed from: protected */
        public final Object zzb(zzbgj zzbgj) {
            switch (zzbgj.zzrM()) {
                case 2:
                    return this.zzbBp;
                case 3:
                    return this.zzbBq;
                case 4:
                    return Integer.valueOf(this.zzbBr);
                default:
                    throw new IllegalStateException(new StringBuilder(38).append("Unknown safe parcelable id=").append(zzbgj.zzrM()).toString());
            }
        }

        public final /* synthetic */ Map zzrL() {
            return zzbAS;
        }
    }

    public static final class zzc extends zzbgl implements Person.Image {
        public static final Parcelable.Creator<zzc> CREATOR = new zzcro();
        private static final HashMap<String, zzbgj<?, ?>> zzbAS;
        private String zzD;
        private int zzaku;
        private Set<Integer> zzbAT;

        static {
            HashMap<String, zzbgj<?, ?>> hashMap = new HashMap<>();
            zzbAS = hashMap;
            hashMap.put("url", zzbgj.zzl("url", 2));
        }

        public zzc() {
            this.zzaku = 1;
            this.zzbAT = new HashSet();
        }

        public zzc(String str) {
            this.zzbAT = new HashSet();
            this.zzaku = 1;
            this.zzD = str;
            this.zzbAT.add(2);
        }

        zzc(Set<Integer> set, int i, String str) {
            this.zzbAT = set;
            this.zzaku = i;
            this.zzD = str;
        }

        public final boolean equals(Object obj) {
            if (!(obj instanceof zzc)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            zzc zzc = (zzc) obj;
            for (zzbgj next : zzbAS.values()) {
                if (zza(next)) {
                    if (!zzc.zza(next)) {
                        return false;
                    }
                    if (!zzb(next).equals(zzc.zzb(next))) {
                        return false;
                    }
                } else if (zzc.zza(next)) {
                    return false;
                }
            }
            return true;
        }

        public final /* bridge */ /* synthetic */ Object freeze() {
            return this;
        }

        public final String getUrl() {
            return this.zzD;
        }

        public final boolean hasUrl() {
            return this.zzbAT.contains(2);
        }

        public final int hashCode() {
            int i = 0;
            Iterator<zzbgj<?, ?>> it = zzbAS.values().iterator();
            while (true) {
                int i2 = i;
                if (!it.hasNext()) {
                    return i2;
                }
                zzbgj next = it.next();
                if (zza(next)) {
                    i = zzb(next).hashCode() + i2 + next.zzrM();
                } else {
                    i = i2;
                }
            }
        }

        public final boolean isDataValid() {
            return true;
        }

        public final void writeToParcel(Parcel parcel, int i) {
            int zze = com.google.android.gms.common.internal.safeparcel.zzd.zze(parcel);
            Set<Integer> set = this.zzbAT;
            if (set.contains(1)) {
                com.google.android.gms.common.internal.safeparcel.zzd.zzc(parcel, 1, this.zzaku);
            }
            if (set.contains(2)) {
                com.google.android.gms.common.internal.safeparcel.zzd.zza(parcel, 2, this.zzD, true);
            }
            com.google.android.gms.common.internal.safeparcel.zzd.zzI(parcel, zze);
        }

        /* access modifiers changed from: protected */
        public final boolean zza(zzbgj zzbgj) {
            return this.zzbAT.contains(Integer.valueOf(zzbgj.zzrM()));
        }

        /* access modifiers changed from: protected */
        public final Object zzb(zzbgj zzbgj) {
            switch (zzbgj.zzrM()) {
                case 2:
                    return this.zzD;
                default:
                    throw new IllegalStateException(new StringBuilder(38).append("Unknown safe parcelable id=").append(zzbgj.zzrM()).toString());
            }
        }

        public final /* synthetic */ Map zzrL() {
            return zzbAS;
        }
    }

    public static final class zzd extends zzbgl implements Person.Name {
        public static final Parcelable.Creator<zzd> CREATOR = new zzcrp();
        private static final HashMap<String, zzbgj<?, ?>> zzbAS;
        private String zzakX;
        private String zzakY;
        private int zzaku;
        private Set<Integer> zzbAT;
        private String zzbBu;
        private String zzbBv;
        private String zzbBw;
        private String zzbBx;

        static {
            HashMap<String, zzbgj<?, ?>> hashMap = new HashMap<>();
            zzbAS = hashMap;
            hashMap.put("familyName", zzbgj.zzl("familyName", 2));
            zzbAS.put("formatted", zzbgj.zzl("formatted", 3));
            zzbAS.put("givenName", zzbgj.zzl("givenName", 4));
            zzbAS.put("honorificPrefix", zzbgj.zzl("honorificPrefix", 5));
            zzbAS.put("honorificSuffix", zzbgj.zzl("honorificSuffix", 6));
            zzbAS.put("middleName", zzbgj.zzl("middleName", 7));
        }

        public zzd() {
            this.zzaku = 1;
            this.zzbAT = new HashSet();
        }

        zzd(Set<Integer> set, int i, String str, String str2, String str3, String str4, String str5, String str6) {
            this.zzbAT = set;
            this.zzaku = i;
            this.zzakY = str;
            this.zzbBu = str2;
            this.zzakX = str3;
            this.zzbBv = str4;
            this.zzbBw = str5;
            this.zzbBx = str6;
        }

        public final boolean equals(Object obj) {
            if (!(obj instanceof zzd)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            zzd zzd = (zzd) obj;
            for (zzbgj next : zzbAS.values()) {
                if (zza(next)) {
                    if (!zzd.zza(next)) {
                        return false;
                    }
                    if (!zzb(next).equals(zzd.zzb(next))) {
                        return false;
                    }
                } else if (zzd.zza(next)) {
                    return false;
                }
            }
            return true;
        }

        public final /* bridge */ /* synthetic */ Object freeze() {
            return this;
        }

        public final String getFamilyName() {
            return this.zzakY;
        }

        public final String getFormatted() {
            return this.zzbBu;
        }

        public final String getGivenName() {
            return this.zzakX;
        }

        public final String getHonorificPrefix() {
            return this.zzbBv;
        }

        public final String getHonorificSuffix() {
            return this.zzbBw;
        }

        public final String getMiddleName() {
            return this.zzbBx;
        }

        public final boolean hasFamilyName() {
            return this.zzbAT.contains(2);
        }

        public final boolean hasFormatted() {
            return this.zzbAT.contains(3);
        }

        public final boolean hasGivenName() {
            return this.zzbAT.contains(4);
        }

        public final boolean hasHonorificPrefix() {
            return this.zzbAT.contains(5);
        }

        public final boolean hasHonorificSuffix() {
            return this.zzbAT.contains(6);
        }

        public final boolean hasMiddleName() {
            return this.zzbAT.contains(7);
        }

        public final int hashCode() {
            int i = 0;
            Iterator<zzbgj<?, ?>> it = zzbAS.values().iterator();
            while (true) {
                int i2 = i;
                if (!it.hasNext()) {
                    return i2;
                }
                zzbgj next = it.next();
                if (zza(next)) {
                    i = zzb(next).hashCode() + i2 + next.zzrM();
                } else {
                    i = i2;
                }
            }
        }

        public final boolean isDataValid() {
            return true;
        }

        public final void writeToParcel(Parcel parcel, int i) {
            int zze = com.google.android.gms.common.internal.safeparcel.zzd.zze(parcel);
            Set<Integer> set = this.zzbAT;
            if (set.contains(1)) {
                com.google.android.gms.common.internal.safeparcel.zzd.zzc(parcel, 1, this.zzaku);
            }
            if (set.contains(2)) {
                com.google.android.gms.common.internal.safeparcel.zzd.zza(parcel, 2, this.zzakY, true);
            }
            if (set.contains(3)) {
                com.google.android.gms.common.internal.safeparcel.zzd.zza(parcel, 3, this.zzbBu, true);
            }
            if (set.contains(4)) {
                com.google.android.gms.common.internal.safeparcel.zzd.zza(parcel, 4, this.zzakX, true);
            }
            if (set.contains(5)) {
                com.google.android.gms.common.internal.safeparcel.zzd.zza(parcel, 5, this.zzbBv, true);
            }
            if (set.contains(6)) {
                com.google.android.gms.common.internal.safeparcel.zzd.zza(parcel, 6, this.zzbBw, true);
            }
            if (set.contains(7)) {
                com.google.android.gms.common.internal.safeparcel.zzd.zza(parcel, 7, this.zzbBx, true);
            }
            com.google.android.gms.common.internal.safeparcel.zzd.zzI(parcel, zze);
        }

        /* access modifiers changed from: protected */
        public final boolean zza(zzbgj zzbgj) {
            return this.zzbAT.contains(Integer.valueOf(zzbgj.zzrM()));
        }

        /* access modifiers changed from: protected */
        public final Object zzb(zzbgj zzbgj) {
            switch (zzbgj.zzrM()) {
                case 2:
                    return this.zzakY;
                case 3:
                    return this.zzbBu;
                case 4:
                    return this.zzakX;
                case 5:
                    return this.zzbBv;
                case 6:
                    return this.zzbBw;
                case 7:
                    return this.zzbBx;
                default:
                    throw new IllegalStateException(new StringBuilder(38).append("Unknown safe parcelable id=").append(zzbgj.zzrM()).toString());
            }
        }

        public final /* synthetic */ Map zzrL() {
            return zzbAS;
        }
    }

    public static final class zze extends zzbgl implements Person.Organizations {
        public static final Parcelable.Creator<zze> CREATOR = new zzcrq();
        private static final HashMap<String, zzbgj<?, ?>> zzbAS;
        private String mName;
        private String zzafa;
        private int zzaku;
        private int zzamr;
        private String zzaoy;
        private Set<Integer> zzbAT;
        private String zzbBA;
        private boolean zzbBB;
        private String zzbBC;
        private String zzbBy;
        private String zzbBz;

        static {
            HashMap<String, zzbgj<?, ?>> hashMap = new HashMap<>();
            zzbAS = hashMap;
            hashMap.put("department", zzbgj.zzl("department", 2));
            zzbAS.put("description", zzbgj.zzl("description", 3));
            zzbAS.put("endDate", zzbgj.zzl("endDate", 4));
            zzbAS.put("location", zzbgj.zzl("location", 5));
            zzbAS.put("name", zzbgj.zzl("name", 6));
            zzbAS.put("primary", zzbgj.zzk("primary", 7));
            zzbAS.put("startDate", zzbgj.zzl("startDate", 8));
            zzbAS.put("title", zzbgj.zzl("title", 9));
            zzbAS.put("type", zzbgj.zza("type", 10, new zzbge().zzi("work", 0).zzi(VKApiConst.SCHOOL, 1), false));
        }

        public zze() {
            this.zzaku = 1;
            this.zzbAT = new HashSet();
        }

        zze(Set<Integer> set, int i, String str, String str2, String str3, String str4, String str5, boolean z, String str6, String str7, int i2) {
            this.zzbAT = set;
            this.zzaku = i;
            this.zzbBy = str;
            this.zzafa = str2;
            this.zzbBz = str3;
            this.zzbBA = str4;
            this.mName = str5;
            this.zzbBB = z;
            this.zzbBC = str6;
            this.zzaoy = str7;
            this.zzamr = i2;
        }

        public final boolean equals(Object obj) {
            if (!(obj instanceof zze)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            zze zze = (zze) obj;
            for (zzbgj next : zzbAS.values()) {
                if (zza(next)) {
                    if (!zze.zza(next)) {
                        return false;
                    }
                    if (!zzb(next).equals(zze.zzb(next))) {
                        return false;
                    }
                } else if (zze.zza(next)) {
                    return false;
                }
            }
            return true;
        }

        public final /* bridge */ /* synthetic */ Object freeze() {
            return this;
        }

        public final String getDepartment() {
            return this.zzbBy;
        }

        public final String getDescription() {
            return this.zzafa;
        }

        public final String getEndDate() {
            return this.zzbBz;
        }

        public final String getLocation() {
            return this.zzbBA;
        }

        public final String getName() {
            return this.mName;
        }

        public final String getStartDate() {
            return this.zzbBC;
        }

        public final String getTitle() {
            return this.zzaoy;
        }

        public final int getType() {
            return this.zzamr;
        }

        public final boolean hasDepartment() {
            return this.zzbAT.contains(2);
        }

        public final boolean hasDescription() {
            return this.zzbAT.contains(3);
        }

        public final boolean hasEndDate() {
            return this.zzbAT.contains(4);
        }

        public final boolean hasLocation() {
            return this.zzbAT.contains(5);
        }

        public final boolean hasName() {
            return this.zzbAT.contains(6);
        }

        public final boolean hasPrimary() {
            return this.zzbAT.contains(7);
        }

        public final boolean hasStartDate() {
            return this.zzbAT.contains(8);
        }

        public final boolean hasTitle() {
            return this.zzbAT.contains(9);
        }

        public final boolean hasType() {
            return this.zzbAT.contains(10);
        }

        public final int hashCode() {
            int i = 0;
            Iterator<zzbgj<?, ?>> it = zzbAS.values().iterator();
            while (true) {
                int i2 = i;
                if (!it.hasNext()) {
                    return i2;
                }
                zzbgj next = it.next();
                if (zza(next)) {
                    i = zzb(next).hashCode() + i2 + next.zzrM();
                } else {
                    i = i2;
                }
            }
        }

        public final boolean isDataValid() {
            return true;
        }

        public final boolean isPrimary() {
            return this.zzbBB;
        }

        public final void writeToParcel(Parcel parcel, int i) {
            int zze = com.google.android.gms.common.internal.safeparcel.zzd.zze(parcel);
            Set<Integer> set = this.zzbAT;
            if (set.contains(1)) {
                com.google.android.gms.common.internal.safeparcel.zzd.zzc(parcel, 1, this.zzaku);
            }
            if (set.contains(2)) {
                com.google.android.gms.common.internal.safeparcel.zzd.zza(parcel, 2, this.zzbBy, true);
            }
            if (set.contains(3)) {
                com.google.android.gms.common.internal.safeparcel.zzd.zza(parcel, 3, this.zzafa, true);
            }
            if (set.contains(4)) {
                com.google.android.gms.common.internal.safeparcel.zzd.zza(parcel, 4, this.zzbBz, true);
            }
            if (set.contains(5)) {
                com.google.android.gms.common.internal.safeparcel.zzd.zza(parcel, 5, this.zzbBA, true);
            }
            if (set.contains(6)) {
                com.google.android.gms.common.internal.safeparcel.zzd.zza(parcel, 6, this.mName, true);
            }
            if (set.contains(7)) {
                com.google.android.gms.common.internal.safeparcel.zzd.zza(parcel, 7, this.zzbBB);
            }
            if (set.contains(8)) {
                com.google.android.gms.common.internal.safeparcel.zzd.zza(parcel, 8, this.zzbBC, true);
            }
            if (set.contains(9)) {
                com.google.android.gms.common.internal.safeparcel.zzd.zza(parcel, 9, this.zzaoy, true);
            }
            if (set.contains(10)) {
                com.google.android.gms.common.internal.safeparcel.zzd.zzc(parcel, 10, this.zzamr);
            }
            com.google.android.gms.common.internal.safeparcel.zzd.zzI(parcel, zze);
        }

        /* access modifiers changed from: protected */
        public final boolean zza(zzbgj zzbgj) {
            return this.zzbAT.contains(Integer.valueOf(zzbgj.zzrM()));
        }

        /* access modifiers changed from: protected */
        public final Object zzb(zzbgj zzbgj) {
            switch (zzbgj.zzrM()) {
                case 2:
                    return this.zzbBy;
                case 3:
                    return this.zzafa;
                case 4:
                    return this.zzbBz;
                case 5:
                    return this.zzbBA;
                case 6:
                    return this.mName;
                case 7:
                    return Boolean.valueOf(this.zzbBB);
                case 8:
                    return this.zzbBC;
                case 9:
                    return this.zzaoy;
                case 10:
                    return Integer.valueOf(this.zzamr);
                default:
                    throw new IllegalStateException(new StringBuilder(38).append("Unknown safe parcelable id=").append(zzbgj.zzrM()).toString());
            }
        }

        public final /* synthetic */ Map zzrL() {
            return zzbAS;
        }
    }

    public static final class zzf extends zzbgl implements Person.PlacesLived {
        public static final Parcelable.Creator<zzf> CREATOR = new zzcrr();
        private static final HashMap<String, zzbgj<?, ?>> zzbAS;
        private String mValue;
        private int zzaku;
        private Set<Integer> zzbAT;
        private boolean zzbBB;

        static {
            HashMap<String, zzbgj<?, ?>> hashMap = new HashMap<>();
            zzbAS = hashMap;
            hashMap.put("primary", zzbgj.zzk("primary", 2));
            zzbAS.put("value", zzbgj.zzl("value", 3));
        }

        public zzf() {
            this.zzaku = 1;
            this.zzbAT = new HashSet();
        }

        zzf(Set<Integer> set, int i, boolean z, String str) {
            this.zzbAT = set;
            this.zzaku = i;
            this.zzbBB = z;
            this.mValue = str;
        }

        public final boolean equals(Object obj) {
            if (!(obj instanceof zzf)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            zzf zzf = (zzf) obj;
            for (zzbgj next : zzbAS.values()) {
                if (zza(next)) {
                    if (!zzf.zza(next)) {
                        return false;
                    }
                    if (!zzb(next).equals(zzf.zzb(next))) {
                        return false;
                    }
                } else if (zzf.zza(next)) {
                    return false;
                }
            }
            return true;
        }

        public final /* bridge */ /* synthetic */ Object freeze() {
            return this;
        }

        public final String getValue() {
            return this.mValue;
        }

        public final boolean hasPrimary() {
            return this.zzbAT.contains(2);
        }

        public final boolean hasValue() {
            return this.zzbAT.contains(3);
        }

        public final int hashCode() {
            int i = 0;
            Iterator<zzbgj<?, ?>> it = zzbAS.values().iterator();
            while (true) {
                int i2 = i;
                if (!it.hasNext()) {
                    return i2;
                }
                zzbgj next = it.next();
                if (zza(next)) {
                    i = zzb(next).hashCode() + i2 + next.zzrM();
                } else {
                    i = i2;
                }
            }
        }

        public final boolean isDataValid() {
            return true;
        }

        public final boolean isPrimary() {
            return this.zzbBB;
        }

        public final void writeToParcel(Parcel parcel, int i) {
            int zze = com.google.android.gms.common.internal.safeparcel.zzd.zze(parcel);
            Set<Integer> set = this.zzbAT;
            if (set.contains(1)) {
                com.google.android.gms.common.internal.safeparcel.zzd.zzc(parcel, 1, this.zzaku);
            }
            if (set.contains(2)) {
                com.google.android.gms.common.internal.safeparcel.zzd.zza(parcel, 2, this.zzbBB);
            }
            if (set.contains(3)) {
                com.google.android.gms.common.internal.safeparcel.zzd.zza(parcel, 3, this.mValue, true);
            }
            com.google.android.gms.common.internal.safeparcel.zzd.zzI(parcel, zze);
        }

        /* access modifiers changed from: protected */
        public final boolean zza(zzbgj zzbgj) {
            return this.zzbAT.contains(Integer.valueOf(zzbgj.zzrM()));
        }

        /* access modifiers changed from: protected */
        public final Object zzb(zzbgj zzbgj) {
            switch (zzbgj.zzrM()) {
                case 2:
                    return Boolean.valueOf(this.zzbBB);
                case 3:
                    return this.mValue;
                default:
                    throw new IllegalStateException(new StringBuilder(38).append("Unknown safe parcelable id=").append(zzbgj.zzrM()).toString());
            }
        }

        public final /* synthetic */ Map zzrL() {
            return zzbAS;
        }
    }

    public static final class zzg extends zzbgl implements Person.Urls {
        public static final Parcelable.Creator<zzg> CREATOR = new zzcrs();
        private static final HashMap<String, zzbgj<?, ?>> zzbAS;
        private String mValue;
        private String zzaeY;
        private int zzaku;
        private int zzamr;
        private Set<Integer> zzbAT;
        private final int zzbBD;

        static {
            HashMap<String, zzbgj<?, ?>> hashMap = new HashMap<>();
            zzbAS = hashMap;
            hashMap.put(PlusShare.KEY_CALL_TO_ACTION_LABEL, zzbgj.zzl(PlusShare.KEY_CALL_TO_ACTION_LABEL, 5));
            zzbAS.put("type", zzbgj.zza("type", 6, new zzbge().zzi("home", 0).zzi("work", 1).zzi("blog", 2).zzi(Scopes.PROFILE, 3).zzi("other", 4).zzi("otherProfile", 5).zzi("contributor", 6).zzi(PlaceFields.WEBSITE, 7), false));
            zzbAS.put("value", zzbgj.zzl("value", 4));
        }

        public zzg() {
            this.zzbBD = 4;
            this.zzaku = 1;
            this.zzbAT = new HashSet();
        }

        zzg(Set<Integer> set, int i, String str, int i2, String str2, int i3) {
            this.zzbBD = 4;
            this.zzbAT = set;
            this.zzaku = i;
            this.zzaeY = str;
            this.zzamr = i2;
            this.mValue = str2;
        }

        public final boolean equals(Object obj) {
            if (!(obj instanceof zzg)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            zzg zzg = (zzg) obj;
            for (zzbgj next : zzbAS.values()) {
                if (zza(next)) {
                    if (!zzg.zza(next)) {
                        return false;
                    }
                    if (!zzb(next).equals(zzg.zzb(next))) {
                        return false;
                    }
                } else if (zzg.zza(next)) {
                    return false;
                }
            }
            return true;
        }

        public final /* bridge */ /* synthetic */ Object freeze() {
            return this;
        }

        public final String getLabel() {
            return this.zzaeY;
        }

        public final int getType() {
            return this.zzamr;
        }

        public final String getValue() {
            return this.mValue;
        }

        public final boolean hasLabel() {
            return this.zzbAT.contains(5);
        }

        public final boolean hasType() {
            return this.zzbAT.contains(6);
        }

        public final boolean hasValue() {
            return this.zzbAT.contains(4);
        }

        public final int hashCode() {
            int i = 0;
            Iterator<zzbgj<?, ?>> it = zzbAS.values().iterator();
            while (true) {
                int i2 = i;
                if (!it.hasNext()) {
                    return i2;
                }
                zzbgj next = it.next();
                if (zza(next)) {
                    i = zzb(next).hashCode() + i2 + next.zzrM();
                } else {
                    i = i2;
                }
            }
        }

        public final boolean isDataValid() {
            return true;
        }

        public final void writeToParcel(Parcel parcel, int i) {
            int zze = com.google.android.gms.common.internal.safeparcel.zzd.zze(parcel);
            Set<Integer> set = this.zzbAT;
            if (set.contains(1)) {
                com.google.android.gms.common.internal.safeparcel.zzd.zzc(parcel, 1, this.zzaku);
            }
            if (set.contains(3)) {
                com.google.android.gms.common.internal.safeparcel.zzd.zzc(parcel, 3, 4);
            }
            if (set.contains(4)) {
                com.google.android.gms.common.internal.safeparcel.zzd.zza(parcel, 4, this.mValue, true);
            }
            if (set.contains(5)) {
                com.google.android.gms.common.internal.safeparcel.zzd.zza(parcel, 5, this.zzaeY, true);
            }
            if (set.contains(6)) {
                com.google.android.gms.common.internal.safeparcel.zzd.zzc(parcel, 6, this.zzamr);
            }
            com.google.android.gms.common.internal.safeparcel.zzd.zzI(parcel, zze);
        }

        /* access modifiers changed from: protected */
        public final boolean zza(zzbgj zzbgj) {
            return this.zzbAT.contains(Integer.valueOf(zzbgj.zzrM()));
        }

        /* access modifiers changed from: protected */
        public final Object zzb(zzbgj zzbgj) {
            switch (zzbgj.zzrM()) {
                case 4:
                    return this.mValue;
                case 5:
                    return this.zzaeY;
                case 6:
                    return Integer.valueOf(this.zzamr);
                default:
                    throw new IllegalStateException(new StringBuilder(38).append("Unknown safe parcelable id=").append(zzbgj.zzrM()).toString());
            }
        }

        public final /* synthetic */ Map zzrL() {
            return zzbAS;
        }
    }

    static {
        HashMap<String, zzbgj<?, ?>> hashMap = new HashMap<>();
        zzbAS = hashMap;
        hashMap.put("aboutMe", zzbgj.zzl("aboutMe", 2));
        zzbAS.put("ageRange", zzbgj.zza("ageRange", 3, zza.class));
        zzbAS.put("birthday", zzbgj.zzl("birthday", 4));
        zzbAS.put("braggingRights", zzbgj.zzl("braggingRights", 5));
        zzbAS.put("circledByCount", zzbgj.zzj("circledByCount", 6));
        zzbAS.put(PlaceFields.COVER, zzbgj.zza(PlaceFields.COVER, 7, zzb.class));
        zzbAS.put("currentLocation", zzbgj.zzl("currentLocation", 8));
        zzbAS.put("displayName", zzbgj.zzl("displayName", 9));
        zzbAS.put("gender", zzbgj.zza("gender", 12, new zzbge().zzi("male", 0).zzi("female", 1).zzi("other", 2), false));
        zzbAS.put("id", zzbgj.zzl("id", 14));
        zzbAS.put("image", zzbgj.zza("image", 15, zzc.class));
        zzbAS.put("isPlusUser", zzbgj.zzk("isPlusUser", 16));
        zzbAS.put("language", zzbgj.zzl("language", 18));
        zzbAS.put("name", zzbgj.zza("name", 19, zzd.class));
        zzbAS.put(ChatMsg.KEY_NICK_NAME, zzbgj.zzl(ChatMsg.KEY_NICK_NAME, 20));
        zzbAS.put("objectType", zzbgj.zza("objectType", 21, new zzbge().zzi("person", 0).zzi("page", 1), false));
        zzbAS.put("organizations", zzbgj.zzb("organizations", 22, zze.class));
        zzbAS.put("placesLived", zzbgj.zzb("placesLived", 23, zzf.class));
        zzbAS.put("plusOneCount", zzbgj.zzj("plusOneCount", 24));
        zzbAS.put("relationshipStatus", zzbgj.zza("relationshipStatus", 25, new zzbge().zzi("single", 0).zzi("in_a_relationship", 1).zzi("engaged", 2).zzi("married", 3).zzi("its_complicated", 4).zzi("open_relationship", 5).zzi("widowed", 6).zzi("in_domestic_partnership", 7).zzi("in_civil_union", 8), false));
        zzbAS.put("tagline", zzbgj.zzl("tagline", 26));
        zzbAS.put("url", zzbgj.zzl("url", 27));
        zzbAS.put("urls", zzbgj.zzb("urls", 28, zzg.class));
        zzbAS.put("verified", zzbgj.zzk("verified", 29));
    }

    public zzcri() {
        this.zzaku = 1;
        this.zzbAT = new HashSet();
    }

    public zzcri(String str, String str2, zzc zzc2, int i, String str3) {
        this.zzaku = 1;
        this.zzbAT = new HashSet();
        this.zzalP = str;
        this.zzbAT.add(9);
        this.zzIi = str2;
        this.zzbAT.add(14);
        this.zzbBb = zzc2;
        this.zzbAT.add(15);
        this.zzbBf = i;
        this.zzbAT.add(21);
        this.zzD = str3;
        this.zzbAT.add(27);
    }

    zzcri(Set<Integer> set, int i, String str, zza zza2, String str2, String str3, int i2, zzb zzb2, String str4, String str5, int i3, String str6, zzc zzc2, boolean z, String str7, zzd zzd2, String str8, int i4, List<zze> list, List<zzf> list2, int i5, int i6, String str9, String str10, List<zzg> list3, boolean z2) {
        this.zzbAT = set;
        this.zzaku = i;
        this.zzbAU = str;
        this.zzbAV = zza2;
        this.zzbAW = str2;
        this.zzbAX = str3;
        this.zzbAY = i2;
        this.zzbAZ = zzb2;
        this.zzbBa = str4;
        this.zzalP = str5;
        this.zzAe = i3;
        this.zzIi = str6;
        this.zzbBb = zzc2;
        this.zzbBc = z;
        this.zzaeT = str7;
        this.zzbBd = zzd2;
        this.zzbBe = str8;
        this.zzbBf = i4;
        this.zzbBg = list;
        this.zzbBh = list2;
        this.zzbBi = i5;
        this.zzbBj = i6;
        this.zzbBk = str9;
        this.zzD = str10;
        this.zzbBl = list3;
        this.zzbBm = z2;
    }

    public static zzcri zzp(byte[] bArr) {
        Parcel obtain = Parcel.obtain();
        obtain.unmarshall(bArr, 0, bArr.length);
        obtain.setDataPosition(0);
        zzcri createFromParcel = CREATOR.createFromParcel(obtain);
        obtain.recycle();
        return createFromParcel;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof zzcri)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        zzcri zzcri = (zzcri) obj;
        for (zzbgj next : zzbAS.values()) {
            if (zza(next)) {
                if (!zzcri.zza(next)) {
                    return false;
                }
                if (!zzb(next).equals(zzcri.zzb(next))) {
                    return false;
                }
            } else if (zzcri.zza(next)) {
                return false;
            }
        }
        return true;
    }

    public final /* bridge */ /* synthetic */ Object freeze() {
        return this;
    }

    public final String getAboutMe() {
        return this.zzbAU;
    }

    public final Person.AgeRange getAgeRange() {
        return this.zzbAV;
    }

    public final String getBirthday() {
        return this.zzbAW;
    }

    public final String getBraggingRights() {
        return this.zzbAX;
    }

    public final int getCircledByCount() {
        return this.zzbAY;
    }

    public final Person.Cover getCover() {
        return this.zzbAZ;
    }

    public final String getCurrentLocation() {
        return this.zzbBa;
    }

    public final String getDisplayName() {
        return this.zzalP;
    }

    public final int getGender() {
        return this.zzAe;
    }

    public final String getId() {
        return this.zzIi;
    }

    public final Person.Image getImage() {
        return this.zzbBb;
    }

    public final String getLanguage() {
        return this.zzaeT;
    }

    public final Person.Name getName() {
        return this.zzbBd;
    }

    public final String getNickname() {
        return this.zzbBe;
    }

    public final int getObjectType() {
        return this.zzbBf;
    }

    public final List<Person.Organizations> getOrganizations() {
        return (ArrayList) this.zzbBg;
    }

    public final List<Person.PlacesLived> getPlacesLived() {
        return (ArrayList) this.zzbBh;
    }

    public final int getPlusOneCount() {
        return this.zzbBi;
    }

    public final int getRelationshipStatus() {
        return this.zzbBj;
    }

    public final String getTagline() {
        return this.zzbBk;
    }

    public final String getUrl() {
        return this.zzD;
    }

    public final List<Person.Urls> getUrls() {
        return (ArrayList) this.zzbBl;
    }

    public final boolean hasAboutMe() {
        return this.zzbAT.contains(2);
    }

    public final boolean hasAgeRange() {
        return this.zzbAT.contains(3);
    }

    public final boolean hasBirthday() {
        return this.zzbAT.contains(4);
    }

    public final boolean hasBraggingRights() {
        return this.zzbAT.contains(5);
    }

    public final boolean hasCircledByCount() {
        return this.zzbAT.contains(6);
    }

    public final boolean hasCover() {
        return this.zzbAT.contains(7);
    }

    public final boolean hasCurrentLocation() {
        return this.zzbAT.contains(8);
    }

    public final boolean hasDisplayName() {
        return this.zzbAT.contains(9);
    }

    public final boolean hasGender() {
        return this.zzbAT.contains(12);
    }

    public final boolean hasId() {
        return this.zzbAT.contains(14);
    }

    public final boolean hasImage() {
        return this.zzbAT.contains(15);
    }

    public final boolean hasIsPlusUser() {
        return this.zzbAT.contains(16);
    }

    public final boolean hasLanguage() {
        return this.zzbAT.contains(18);
    }

    public final boolean hasName() {
        return this.zzbAT.contains(19);
    }

    public final boolean hasNickname() {
        return this.zzbAT.contains(20);
    }

    public final boolean hasObjectType() {
        return this.zzbAT.contains(21);
    }

    public final boolean hasOrganizations() {
        return this.zzbAT.contains(22);
    }

    public final boolean hasPlacesLived() {
        return this.zzbAT.contains(23);
    }

    public final boolean hasPlusOneCount() {
        return this.zzbAT.contains(24);
    }

    public final boolean hasRelationshipStatus() {
        return this.zzbAT.contains(25);
    }

    public final boolean hasTagline() {
        return this.zzbAT.contains(26);
    }

    public final boolean hasUrl() {
        return this.zzbAT.contains(27);
    }

    public final boolean hasUrls() {
        return this.zzbAT.contains(28);
    }

    public final boolean hasVerified() {
        return this.zzbAT.contains(29);
    }

    public final int hashCode() {
        int i = 0;
        Iterator<zzbgj<?, ?>> it = zzbAS.values().iterator();
        while (true) {
            int i2 = i;
            if (!it.hasNext()) {
                return i2;
            }
            zzbgj next = it.next();
            if (zza(next)) {
                i = zzb(next).hashCode() + i2 + next.zzrM();
            } else {
                i = i2;
            }
        }
    }

    public final boolean isDataValid() {
        return true;
    }

    public final boolean isPlusUser() {
        return this.zzbBc;
    }

    public final boolean isVerified() {
        return this.zzbBm;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze2 = com.google.android.gms.common.internal.safeparcel.zzd.zze(parcel);
        Set<Integer> set = this.zzbAT;
        if (set.contains(1)) {
            com.google.android.gms.common.internal.safeparcel.zzd.zzc(parcel, 1, this.zzaku);
        }
        if (set.contains(2)) {
            com.google.android.gms.common.internal.safeparcel.zzd.zza(parcel, 2, this.zzbAU, true);
        }
        if (set.contains(3)) {
            com.google.android.gms.common.internal.safeparcel.zzd.zza(parcel, 3, (Parcelable) this.zzbAV, i, true);
        }
        if (set.contains(4)) {
            com.google.android.gms.common.internal.safeparcel.zzd.zza(parcel, 4, this.zzbAW, true);
        }
        if (set.contains(5)) {
            com.google.android.gms.common.internal.safeparcel.zzd.zza(parcel, 5, this.zzbAX, true);
        }
        if (set.contains(6)) {
            com.google.android.gms.common.internal.safeparcel.zzd.zzc(parcel, 6, this.zzbAY);
        }
        if (set.contains(7)) {
            com.google.android.gms.common.internal.safeparcel.zzd.zza(parcel, 7, (Parcelable) this.zzbAZ, i, true);
        }
        if (set.contains(8)) {
            com.google.android.gms.common.internal.safeparcel.zzd.zza(parcel, 8, this.zzbBa, true);
        }
        if (set.contains(9)) {
            com.google.android.gms.common.internal.safeparcel.zzd.zza(parcel, 9, this.zzalP, true);
        }
        if (set.contains(12)) {
            com.google.android.gms.common.internal.safeparcel.zzd.zzc(parcel, 12, this.zzAe);
        }
        if (set.contains(14)) {
            com.google.android.gms.common.internal.safeparcel.zzd.zza(parcel, 14, this.zzIi, true);
        }
        if (set.contains(15)) {
            com.google.android.gms.common.internal.safeparcel.zzd.zza(parcel, 15, (Parcelable) this.zzbBb, i, true);
        }
        if (set.contains(16)) {
            com.google.android.gms.common.internal.safeparcel.zzd.zza(parcel, 16, this.zzbBc);
        }
        if (set.contains(18)) {
            com.google.android.gms.common.internal.safeparcel.zzd.zza(parcel, 18, this.zzaeT, true);
        }
        if (set.contains(19)) {
            com.google.android.gms.common.internal.safeparcel.zzd.zza(parcel, 19, (Parcelable) this.zzbBd, i, true);
        }
        if (set.contains(20)) {
            com.google.android.gms.common.internal.safeparcel.zzd.zza(parcel, 20, this.zzbBe, true);
        }
        if (set.contains(21)) {
            com.google.android.gms.common.internal.safeparcel.zzd.zzc(parcel, 21, this.zzbBf);
        }
        if (set.contains(22)) {
            com.google.android.gms.common.internal.safeparcel.zzd.zzc(parcel, 22, this.zzbBg, true);
        }
        if (set.contains(23)) {
            com.google.android.gms.common.internal.safeparcel.zzd.zzc(parcel, 23, this.zzbBh, true);
        }
        if (set.contains(24)) {
            com.google.android.gms.common.internal.safeparcel.zzd.zzc(parcel, 24, this.zzbBi);
        }
        if (set.contains(25)) {
            com.google.android.gms.common.internal.safeparcel.zzd.zzc(parcel, 25, this.zzbBj);
        }
        if (set.contains(26)) {
            com.google.android.gms.common.internal.safeparcel.zzd.zza(parcel, 26, this.zzbBk, true);
        }
        if (set.contains(27)) {
            com.google.android.gms.common.internal.safeparcel.zzd.zza(parcel, 27, this.zzD, true);
        }
        if (set.contains(28)) {
            com.google.android.gms.common.internal.safeparcel.zzd.zzc(parcel, 28, this.zzbBl, true);
        }
        if (set.contains(29)) {
            com.google.android.gms.common.internal.safeparcel.zzd.zza(parcel, 29, this.zzbBm);
        }
        com.google.android.gms.common.internal.safeparcel.zzd.zzI(parcel, zze2);
    }

    /* access modifiers changed from: protected */
    public final boolean zza(zzbgj zzbgj) {
        return this.zzbAT.contains(Integer.valueOf(zzbgj.zzrM()));
    }

    /* access modifiers changed from: protected */
    public final Object zzb(zzbgj zzbgj) {
        switch (zzbgj.zzrM()) {
            case 2:
                return this.zzbAU;
            case 3:
                return this.zzbAV;
            case 4:
                return this.zzbAW;
            case 5:
                return this.zzbAX;
            case 6:
                return Integer.valueOf(this.zzbAY);
            case 7:
                return this.zzbAZ;
            case 8:
                return this.zzbBa;
            case 9:
                return this.zzalP;
            case 12:
                return Integer.valueOf(this.zzAe);
            case 14:
                return this.zzIi;
            case 15:
                return this.zzbBb;
            case 16:
                return Boolean.valueOf(this.zzbBc);
            case 18:
                return this.zzaeT;
            case 19:
                return this.zzbBd;
            case 20:
                return this.zzbBe;
            case 21:
                return Integer.valueOf(this.zzbBf);
            case 22:
                return this.zzbBg;
            case 23:
                return this.zzbBh;
            case 24:
                return Integer.valueOf(this.zzbBi);
            case 25:
                return Integer.valueOf(this.zzbBj);
            case 26:
                return this.zzbBk;
            case 27:
                return this.zzD;
            case 28:
                return this.zzbBl;
            case 29:
                return Boolean.valueOf(this.zzbBm);
            default:
                throw new IllegalStateException(new StringBuilder(38).append("Unknown safe parcelable id=").append(zzbgj.zzrM()).toString());
        }
    }

    public final /* synthetic */ Map zzrL() {
        return zzbAS;
    }
}
