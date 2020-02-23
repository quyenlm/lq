package com.google.android.gms.fitness.data;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.safeparcel.zze;
import com.google.android.gms.common.internal.zzbo;
import com.tencent.midas.oversea.comm.APDataReportManager;
import com.vk.sdk.api.VKApiConst;

public class DataSource extends zza {
    public static final Parcelable.Creator<DataSource> CREATOR = new zzk();
    public static final int DATA_QUALITY_BLOOD_GLUCOSE_ISO151972003 = 8;
    public static final int DATA_QUALITY_BLOOD_GLUCOSE_ISO151972013 = 9;
    public static final int DATA_QUALITY_BLOOD_PRESSURE_AAMI = 3;
    public static final int DATA_QUALITY_BLOOD_PRESSURE_BHS_A_A = 4;
    public static final int DATA_QUALITY_BLOOD_PRESSURE_BHS_A_B = 5;
    public static final int DATA_QUALITY_BLOOD_PRESSURE_BHS_B_A = 6;
    public static final int DATA_QUALITY_BLOOD_PRESSURE_BHS_B_B = 7;
    public static final int DATA_QUALITY_BLOOD_PRESSURE_ESH2002 = 1;
    public static final int DATA_QUALITY_BLOOD_PRESSURE_ESH2010 = 2;
    public static final String EXTRA_DATA_SOURCE = "vnd.google.fitness.data_source";
    public static final int TYPE_DERIVED = 1;
    public static final int TYPE_RAW = 0;
    private static final int[] zzaTE = new int[0];
    private final String name;
    private final int type;
    private final int versionCode;
    private final DataType zzaSZ;
    private final Device zzaTF;
    private final zzb zzaTG;
    private final String zzaTH;
    private final int[] zzaTI;
    private final String zzaTJ;

    public static final class Builder {
        /* access modifiers changed from: private */
        public String name;
        /* access modifiers changed from: private */
        public int type = -1;
        /* access modifiers changed from: private */
        public DataType zzaSZ;
        /* access modifiers changed from: private */
        public Device zzaTF;
        /* access modifiers changed from: private */
        public zzb zzaTG;
        /* access modifiers changed from: private */
        public String zzaTH = "";
        /* access modifiers changed from: private */
        public int[] zzaTI;

        public final DataSource build() {
            boolean z = true;
            zzbo.zza(this.zzaSZ != null, (Object) "Must set data type");
            if (this.type < 0) {
                z = false;
            }
            zzbo.zza(z, (Object) "Must set data source type");
            return new DataSource(this);
        }

        public final Builder setAppPackageName(Context context) {
            return setAppPackageName(context.getPackageName());
        }

        public final Builder setAppPackageName(String str) {
            this.zzaTG = zzb.zzcX(str);
            return this;
        }

        public final Builder setDataQualityStandards(int... iArr) {
            this.zzaTI = iArr;
            return this;
        }

        public final Builder setDataType(DataType dataType) {
            this.zzaSZ = dataType;
            return this;
        }

        public final Builder setDevice(Device device) {
            this.zzaTF = device;
            return this;
        }

        public final Builder setName(String str) {
            this.name = str;
            return this;
        }

        public final Builder setStreamName(String str) {
            zzbo.zzb(str != null, (Object) "Must specify a valid stream name");
            this.zzaTH = str;
            return this;
        }

        public final Builder setType(int i) {
            this.type = i;
            return this;
        }
    }

    DataSource(int i, DataType dataType, String str, int i2, Device device, zzb zzb, String str2, int[] iArr) {
        this.versionCode = i;
        this.zzaSZ = dataType;
        this.type = i2;
        this.name = str;
        this.zzaTF = device;
        this.zzaTG = zzb;
        this.zzaTH = str2;
        this.zzaTJ = zztN();
        this.zzaTI = iArr == null ? zzaTE : iArr;
    }

    private DataSource(Builder builder) {
        this.versionCode = 3;
        this.zzaSZ = builder.zzaSZ;
        this.type = builder.type;
        this.name = builder.name;
        this.zzaTF = builder.zzaTF;
        this.zzaTG = builder.zzaTG;
        this.zzaTH = builder.zzaTH;
        this.zzaTJ = zztN();
        this.zzaTI = builder.zzaTI;
    }

    public static DataSource extract(Intent intent) {
        if (intent == null) {
            return null;
        }
        return (DataSource) zze.zza(intent, EXTRA_DATA_SOURCE, CREATOR);
    }

    private final String getTypeString() {
        switch (this.type) {
            case 0:
                return "raw";
            case 1:
                return "derived";
            case 2:
                return "cleaned";
            case 3:
                return "converted";
            default:
                return "derived";
        }
    }

    public static String zzaV(int i) {
        switch (i) {
            case 1:
                return "blood_pressure_esh2002";
            case 2:
                return "blood_pressure_esh2010";
            case 3:
                return "blood_pressure_aami";
            case 4:
                return "blood_pressure_bhs_a_a";
            case 5:
                return "blood_pressure_bhs_a_b";
            case 6:
                return "blood_pressure_bhs_b_a";
            case 7:
                return "blood_pressure_bhs_b_b";
            case 8:
                return "blood_glucose_iso151972003";
            case 9:
                return "blood_glucose_iso151972013";
            default:
                return "unknown";
        }
    }

    private final String zztN() {
        StringBuilder sb = new StringBuilder();
        sb.append(getTypeString());
        sb.append(":").append(this.zzaSZ.getName());
        if (this.zzaTG != null) {
            sb.append(":").append(this.zzaTG.getPackageName());
        }
        if (this.zzaTF != null) {
            sb.append(":").append(this.zzaTF.getStreamIdentifier());
        }
        if (this.zzaTH != null) {
            sb.append(":").append(this.zzaTH);
        }
        return sb.toString();
    }

    public boolean equals(Object obj) {
        return this == obj || ((obj instanceof DataSource) && this.zzaTJ.equals(((DataSource) obj).zzaTJ));
    }

    public String getAppPackageName() {
        if (this.zzaTG == null) {
            return null;
        }
        return this.zzaTG.getPackageName();
    }

    public int[] getDataQualityStandards() {
        return this.zzaTI;
    }

    public DataType getDataType() {
        return this.zzaSZ;
    }

    public Device getDevice() {
        return this.zzaTF;
    }

    public String getName() {
        return this.name;
    }

    public String getStreamIdentifier() {
        return this.zzaTJ;
    }

    public String getStreamName() {
        return this.zzaTH;
    }

    public int getType() {
        return this.type;
    }

    public int hashCode() {
        return this.zzaTJ.hashCode();
    }

    public final String toDebugString() {
        String str;
        String concat;
        String str2;
        String str3;
        switch (this.type) {
            case 0:
                str = "r";
                break;
            case 1:
                str = APDataReportManager.GOODSANDMONTHSINPUT_PRE;
                break;
            case 2:
                str = "c";
                break;
            case 3:
                str = VKApiConst.VERSION;
                break;
            default:
                str = "?";
                break;
        }
        String valueOf = String.valueOf(str);
        String valueOf2 = String.valueOf(this.zzaSZ.zztO());
        if (this.zzaTG == null) {
            concat = "";
        } else if (this.zzaTG.equals(zzb.zzaTj)) {
            concat = ":gms";
        } else {
            String valueOf3 = String.valueOf(this.zzaTG.getPackageName());
            concat = valueOf3.length() != 0 ? ":".concat(valueOf3) : new String(":");
        }
        if (this.zzaTF != null) {
            String valueOf4 = String.valueOf(this.zzaTF.getModel());
            String valueOf5 = String.valueOf(this.zzaTF.getUid());
            str2 = new StringBuilder(String.valueOf(valueOf4).length() + 2 + String.valueOf(valueOf5).length()).append(":").append(valueOf4).append(":").append(valueOf5).toString();
        } else {
            str2 = "";
        }
        if (this.zzaTH != null) {
            String valueOf6 = String.valueOf(this.zzaTH);
            str3 = valueOf6.length() != 0 ? ":".concat(valueOf6) : new String(":");
        } else {
            str3 = "";
        }
        return new StringBuilder(String.valueOf(valueOf).length() + 1 + String.valueOf(valueOf2).length() + String.valueOf(concat).length() + String.valueOf(str2).length() + String.valueOf(str3).length()).append(valueOf).append(":").append(valueOf2).append(concat).append(str2).append(str3).toString();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("DataSource{");
        sb.append(getTypeString());
        if (this.name != null) {
            sb.append(":").append(this.name);
        }
        if (this.zzaTG != null) {
            sb.append(":").append(this.zzaTG);
        }
        if (this.zzaTF != null) {
            sb.append(":").append(this.zzaTF);
        }
        if (this.zzaTH != null) {
            sb.append(":").append(this.zzaTH);
        }
        sb.append(":").append(this.zzaSZ);
        return sb.append("}").toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 1, (Parcelable) getDataType(), i, false);
        zzd.zza(parcel, 2, getName(), false);
        zzd.zzc(parcel, 3, getType());
        zzd.zza(parcel, 4, (Parcelable) getDevice(), i, false);
        zzd.zza(parcel, 5, (Parcelable) this.zzaTG, i, false);
        zzd.zza(parcel, 6, getStreamName(), false);
        zzd.zzc(parcel, 1000, this.versionCode);
        zzd.zza(parcel, 8, getDataQualityStandards(), false);
        zzd.zzI(parcel, zze);
    }

    public final zzb zztM() {
        return this.zzaTG;
    }
}
