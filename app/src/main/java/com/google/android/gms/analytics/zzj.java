package com.google.android.gms.analytics;

import android.text.TextUtils;
import com.google.android.gms.analytics.zzj;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public abstract class zzj<T extends zzj> {
    private static String zza(Object obj, int i) {
        if (i > 10) {
            return "ERROR: Recursive toString calls";
        }
        if (obj == null) {
            return "";
        }
        if (obj instanceof String) {
            return TextUtils.isEmpty((String) obj) ? "" : obj.toString();
        }
        if (obj instanceof Integer) {
            return ((Integer) obj).intValue() == 0 ? "" : obj.toString();
        }
        if (obj instanceof Long) {
            return ((Long) obj).longValue() == 0 ? "" : obj.toString();
        }
        if (obj instanceof Double) {
            return ((Double) obj).doubleValue() == FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE ? "" : obj.toString();
        }
        if (obj instanceof Boolean) {
            return !((Boolean) obj).booleanValue() ? "" : obj.toString();
        }
        if (obj instanceof List) {
            StringBuffer stringBuffer = new StringBuffer();
            if (i > 0) {
                stringBuffer.append("[");
            }
            int length = stringBuffer.length();
            for (Object next : (List) obj) {
                if (stringBuffer.length() > length) {
                    stringBuffer.append(", ");
                }
                stringBuffer.append(zza(next, i + 1));
            }
            if (i > 0) {
                stringBuffer.append("]");
            }
            return stringBuffer.toString();
        } else if (!(obj instanceof Map)) {
            return obj.toString();
        } else {
            StringBuffer stringBuffer2 = new StringBuffer();
            int i2 = 0;
            boolean z = false;
            for (Map.Entry entry : new TreeMap((Map) obj).entrySet()) {
                String zza = zza(entry.getValue(), i + 1);
                if (!TextUtils.isEmpty(zza)) {
                    if (i > 0 && !z) {
                        stringBuffer2.append("{");
                        z = true;
                        i2 = stringBuffer2.length();
                    }
                    if (stringBuffer2.length() > i2) {
                        stringBuffer2.append(", ");
                    }
                    stringBuffer2.append((String) entry.getKey());
                    stringBuffer2.append('=');
                    stringBuffer2.append(zza);
                }
            }
            if (z) {
                stringBuffer2.append("}");
            }
            return stringBuffer2.toString();
        }
    }

    public static String zzh(Object obj) {
        return zza(obj, 0);
    }

    public static String zzk(Map map) {
        return zza(map, 1);
    }

    public abstract void zzb(T t);
}
