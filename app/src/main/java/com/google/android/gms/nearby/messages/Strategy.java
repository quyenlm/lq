package com.google.android.gms.nearby.messages;

import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.share.internal.MessengerShareContentUtility;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbo;
import java.util.ArrayList;

public class Strategy extends zza {
    public static final Strategy BLE_ONLY;
    public static final Parcelable.Creator<Strategy> CREATOR = new zzg();
    public static final Strategy DEFAULT = new Builder().build();
    public static final int DISCOVERY_MODE_BROADCAST = 1;
    public static final int DISCOVERY_MODE_DEFAULT = 3;
    public static final int DISCOVERY_MODE_SCAN = 2;
    public static final int DISTANCE_TYPE_DEFAULT = 0;
    public static final int DISTANCE_TYPE_EARSHOT = 1;
    public static final int TTL_SECONDS_DEFAULT = 300;
    public static final int TTL_SECONDS_INFINITE = Integer.MAX_VALUE;
    public static final int TTL_SECONDS_MAX = 86400;
    @Deprecated
    private static Strategy zzbyl;
    private int zzaku;
    @Deprecated
    private int zzbym;
    private int zzbyn;
    private int zzbyo;
    @Deprecated
    private boolean zzbyp;
    private int zzbyq;
    private int zzbyr;
    private final int zzbys;

    public static class Builder {
        private int zzbyt = 3;
        private int zzbyu = 300;
        private int zzbyv = 0;
        private int zzbyw = -1;
        private int zzbyx = 0;

        public Strategy build() {
            if (this.zzbyw != 2 || this.zzbyv != 1) {
                return new Strategy(2, 0, this.zzbyu, this.zzbyv, false, this.zzbyw, this.zzbyt, 0);
            }
            throw new IllegalStateException("Cannot set EARSHOT with BLE only mode.");
        }

        public Builder setDiscoveryMode(int i) {
            this.zzbyt = i;
            return this;
        }

        public Builder setDistanceType(int i) {
            this.zzbyv = i;
            return this;
        }

        public Builder setTtlSeconds(int i) {
            zzbo.zzb(i == Integer.MAX_VALUE || (i > 0 && i <= 86400), "mTtlSeconds(%d) must either be TTL_SECONDS_INFINITE, or it must be between 1 and TTL_SECONDS_MAX(%d) inclusive", Integer.valueOf(i), 86400);
            this.zzbyu = i;
            return this;
        }

        public final Builder zzbr(int i) {
            this.zzbyw = 2;
            return this;
        }
    }

    static {
        Strategy build = new Builder().zzbr(2).setTtlSeconds(Integer.MAX_VALUE).build();
        BLE_ONLY = build;
        zzbyl = build;
    }

    Strategy(int i, int i2, int i3, int i4, boolean z, int i5, int i6, int i7) {
        this.zzaku = i;
        this.zzbym = i2;
        if (i2 != 0) {
            switch (i2) {
                case 2:
                    this.zzbyr = 1;
                    break;
                case 3:
                    this.zzbyr = 2;
                    break;
                default:
                    this.zzbyr = 3;
                    break;
            }
        } else {
            this.zzbyr = i6;
        }
        this.zzbyo = i4;
        this.zzbyp = z;
        if (!z) {
            this.zzbyn = i3;
            switch (i5) {
                case -1:
                case 0:
                case 1:
                case 6:
                    this.zzbyq = -1;
                    break;
                default:
                    this.zzbyq = i5;
                    break;
            }
        } else {
            this.zzbyq = 2;
            this.zzbyn = Integer.MAX_VALUE;
        }
        this.zzbys = i7;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Strategy)) {
            return false;
        }
        Strategy strategy = (Strategy) obj;
        return this.zzaku == strategy.zzaku && this.zzbyr == strategy.zzbyr && this.zzbyn == strategy.zzbyn && this.zzbyo == strategy.zzbyo && this.zzbyq == strategy.zzbyq;
    }

    public int hashCode() {
        return (((((((this.zzaku * 31) + this.zzbyr) * 31) + this.zzbyn) * 31) + this.zzbyo) * 31) + this.zzbyq;
    }

    public String toString() {
        String str;
        String sb;
        String sb2;
        String str2;
        int i = this.zzbyn;
        int i2 = this.zzbyo;
        switch (i2) {
            case 0:
                str = MessengerShareContentUtility.PREVIEW_DEFAULT;
                break;
            case 1:
                str = "EARSHOT";
                break;
            default:
                str = new StringBuilder(19).append("UNKNOWN:").append(i2).toString();
                break;
        }
        String valueOf = String.valueOf(str);
        int i3 = this.zzbyq;
        if (i3 == -1) {
            sb = MessengerShareContentUtility.PREVIEW_DEFAULT;
        } else {
            ArrayList arrayList = new ArrayList();
            if ((i3 & 4) > 0) {
                arrayList.add("ULTRASOUND");
            }
            if ((i3 & 2) > 0) {
                arrayList.add("BLE");
            }
            sb = arrayList.isEmpty() ? new StringBuilder(19).append("UNKNOWN:").append(i3).toString() : arrayList.toString();
        }
        String valueOf2 = String.valueOf(sb);
        int i4 = this.zzbyr;
        if (i4 == 3) {
            sb2 = MessengerShareContentUtility.PREVIEW_DEFAULT;
        } else {
            ArrayList arrayList2 = new ArrayList();
            if ((i4 & 1) > 0) {
                arrayList2.add("BROADCAST");
            }
            if ((i4 & 2) > 0) {
                arrayList2.add("SCAN");
            }
            sb2 = arrayList2.isEmpty() ? new StringBuilder(19).append("UNKNOWN:").append(i4).toString() : arrayList2.toString();
        }
        String valueOf3 = String.valueOf(sb2);
        int i5 = this.zzbys;
        switch (i5) {
            case 0:
                str2 = MessengerShareContentUtility.PREVIEW_DEFAULT;
                break;
            case 1:
                str2 = "ALWAYS_ON";
                break;
            default:
                str2 = new StringBuilder(20).append("UNKNOWN: ").append(i5).toString();
                break;
        }
        String valueOf4 = String.valueOf(str2);
        return new StringBuilder(String.valueOf(valueOf).length() + 102 + String.valueOf(valueOf2).length() + String.valueOf(valueOf3).length() + String.valueOf(valueOf4).length()).append("Strategy{ttlSeconds=").append(i).append(", distanceType=").append(valueOf).append(", discoveryMedium=").append(valueOf2).append(", discoveryMode=").append(valueOf3).append(", backgroundScanMode=").append(valueOf4).append("}").toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, this.zzbym);
        zzd.zzc(parcel, 2, this.zzbyn);
        zzd.zzc(parcel, 3, this.zzbyo);
        zzd.zza(parcel, 4, this.zzbyp);
        zzd.zzc(parcel, 5, this.zzbyq);
        zzd.zzc(parcel, 6, this.zzbyr);
        zzd.zzc(parcel, 7, this.zzbys);
        zzd.zzc(parcel, 1000, this.zzaku);
        zzd.zzI(parcel, zze);
    }

    public final int zzzU() {
        return this.zzbys;
    }
}
