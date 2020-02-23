package com.google.android.gms.fitness.data;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbe;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.common.util.zzl;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public final class Value extends zza {
    public static final Parcelable.Creator<Value> CREATOR = new zzai();
    private final int format;
    private float value;
    private final int versionCode;
    private String zzaIF;
    private boolean zzaVl;
    private Map<String, MapValue> zzaVm;
    private int[] zzaVn;
    private float[] zzaVo;
    private byte[] zzaVp;

    public Value(int i) {
        this(3, i, false, 0.0f, (String) null, (Bundle) null, (int[]) null, (float[]) null, (byte[]) null);
    }

    Value(int i, int i2, boolean z, float f, String str, Bundle bundle, int[] iArr, float[] fArr, byte[] bArr) {
        ArrayMap arrayMap;
        this.versionCode = i;
        this.format = i2;
        this.zzaVl = z;
        this.value = f;
        this.zzaIF = str;
        if (bundle == null) {
            arrayMap = null;
        } else {
            bundle.setClassLoader(MapValue.class.getClassLoader());
            ArrayMap arrayMap2 = new ArrayMap(bundle.size());
            for (String str2 : bundle.keySet()) {
                arrayMap2.put(str2, (MapValue) bundle.getParcelable(str2));
            }
            arrayMap = arrayMap2;
        }
        this.zzaVm = arrayMap;
        this.zzaVn = iArr;
        this.zzaVo = fArr;
        this.zzaVp = bArr;
    }

    public final String asActivity() {
        return com.google.android.gms.fitness.zza.getName(asInt());
    }

    public final float asFloat() {
        zzbo.zza(this.format == 2, (Object) "Value is not in float format");
        return this.value;
    }

    public final int asInt() {
        boolean z = true;
        if (this.format != 1) {
            z = false;
        }
        zzbo.zza(z, (Object) "Value is not in int format");
        return Float.floatToRawIntBits(this.value);
    }

    public final String asString() {
        zzbo.zza(this.format == 3, (Object) "Value is not in string format");
        return this.zzaIF;
    }

    public final void clearKey(String str) {
        zzbo.zza(this.format == 4, (Object) "Attempting to set a key's value to a field that is not in FLOAT_MAP format.  Please check the data type definition and use the right format.");
        if (this.zzaVm != null) {
            this.zzaVm.remove(str);
        }
    }

    public final boolean equals(Object obj) {
        boolean z;
        if (this != obj) {
            if (!(obj instanceof Value)) {
                return false;
            }
            Value value2 = (Value) obj;
            if (this.format == value2.format && this.zzaVl == value2.zzaVl) {
                switch (this.format) {
                    case 1:
                        if (asInt() != value2.asInt()) {
                            z = false;
                            break;
                        } else {
                            z = true;
                            break;
                        }
                    case 2:
                        if (this.value != value2.value) {
                            z = false;
                            break;
                        } else {
                            z = true;
                            break;
                        }
                    case 3:
                        z = zzbe.equal(this.zzaIF, value2.zzaIF);
                        break;
                    case 4:
                        z = zzbe.equal(this.zzaVm, value2.zzaVm);
                        break;
                    case 5:
                        z = Arrays.equals(this.zzaVn, value2.zzaVn);
                        break;
                    case 6:
                        z = Arrays.equals(this.zzaVo, value2.zzaVo);
                        break;
                    case 7:
                        z = Arrays.equals(this.zzaVp, value2.zzaVp);
                        break;
                    default:
                        if (this.value != value2.value) {
                            z = false;
                            break;
                        } else {
                            z = true;
                            break;
                        }
                }
            } else {
                z = false;
            }
            if (!z) {
                return false;
            }
        }
        return true;
    }

    public final int getFormat() {
        return this.format;
    }

    @Nullable
    public final Float getKeyValue(String str) {
        zzbo.zza(this.format == 4, (Object) "Value is not in float map format");
        if (this.zzaVm == null || !this.zzaVm.containsKey(str)) {
            return null;
        }
        return Float.valueOf(this.zzaVm.get(str).asFloat());
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{Float.valueOf(this.value), this.zzaIF, this.zzaVm, this.zzaVn, this.zzaVo, this.zzaVp});
    }

    public final boolean isSet() {
        return this.zzaVl;
    }

    public final void setActivity(String str) {
        setInt(com.google.android.gms.fitness.zza.zzcW(str));
    }

    public final void setFloat(float f) {
        zzbo.zza(this.format == 2, (Object) "Attempting to set an float value to a field that is not in FLOAT format.  Please check the data type definition and use the right format.");
        this.zzaVl = true;
        this.value = f;
    }

    public final void setInt(int i) {
        zzbo.zza(this.format == 1, (Object) "Attempting to set an int value to a field that is not in INT32 format.  Please check the data type definition and use the right format.");
        this.zzaVl = true;
        this.value = Float.intBitsToFloat(i);
    }

    public final void setKeyValue(String str, float f) {
        zzbo.zza(this.format == 4, (Object) "Attempting to set a key's value to a field that is not in FLOAT_MAP format.  Please check the data type definition and use the right format.");
        this.zzaVl = true;
        if (this.zzaVm == null) {
            this.zzaVm = new HashMap();
        }
        this.zzaVm.put(str, new MapValue(2, f));
    }

    public final void setString(String str) {
        zzbo.zza(this.format == 3, (Object) "Attempting to set a string value to a field that is not in STRING format.  Please check the data type definition and use the right format.");
        this.zzaVl = true;
        this.zzaIF = str;
    }

    public final String toString() {
        if (!this.zzaVl) {
            return "unset";
        }
        switch (this.format) {
            case 1:
                return Integer.toString(asInt());
            case 2:
                return Float.toString(this.value);
            case 3:
                return this.zzaIF;
            case 4:
                return new TreeMap(this.zzaVm).toString();
            case 5:
                return Arrays.toString(this.zzaVn);
            case 6:
                return Arrays.toString(this.zzaVo);
            case 7:
                return zzl.zza(this.zzaVp, 0, this.zzaVp.length, false);
            default:
                return "unknown";
        }
    }

    public final void writeToParcel(Parcel parcel, int i) {
        Bundle bundle;
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, getFormat());
        zzd.zza(parcel, 2, isSet());
        zzd.zza(parcel, 3, this.value);
        zzd.zza(parcel, 4, this.zzaIF, false);
        if (this.zzaVm == null) {
            bundle = null;
        } else {
            Bundle bundle2 = new Bundle(this.zzaVm.size());
            for (Map.Entry next : this.zzaVm.entrySet()) {
                bundle2.putParcelable((String) next.getKey(), (Parcelable) next.getValue());
            }
            bundle = bundle2;
        }
        zzd.zza(parcel, 5, bundle, false);
        zzd.zza(parcel, 6, this.zzaVn, false);
        zzd.zza(parcel, 7, this.zzaVo, false);
        zzd.zzc(parcel, 1000, this.versionCode);
        zzd.zza(parcel, 8, this.zzaVp, false);
        zzd.zzI(parcel, zze);
    }
}
