package com.google.android.gms.internal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.zzbo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public final class ed {
    public static Map<String, Object> zzC(Bundle bundle) {
        HashMap hashMap = new HashMap();
        for (String str : bundle.keySet()) {
            Object obj = bundle.get(str);
            if (obj instanceof Bundle) {
                hashMap.put(str, zzC((Bundle) obj));
            } else if (obj instanceof List) {
                hashMap.put(str, zzM((List) obj));
            } else {
                hashMap.put(str, obj);
            }
        }
        return hashMap;
    }

    private static List<Object> zzM(List<Object> list) {
        ArrayList arrayList = new ArrayList();
        for (Object next : list) {
            if (next instanceof Bundle) {
                arrayList.add(zzC((Bundle) next));
            } else if (next instanceof List) {
                arrayList.add(zzM((List) next));
            } else {
                arrayList.add(next);
            }
        }
        return arrayList;
    }

    public static dp<?> zzQ(@Nullable Object obj) {
        if (obj == null) {
            return dv.zzbLt;
        }
        if (obj instanceof dp) {
            return (dp) obj;
        }
        if (obj instanceof Boolean) {
            return new ds((Boolean) obj);
        }
        if (obj instanceof Short) {
            return new dt(Double.valueOf(((Short) obj).doubleValue()));
        }
        if (obj instanceof Integer) {
            return new dt(Double.valueOf(((Integer) obj).doubleValue()));
        }
        if (obj instanceof Long) {
            return new dt(Double.valueOf(((Long) obj).doubleValue()));
        }
        if (obj instanceof Float) {
            return new dt(Double.valueOf(((Float) obj).doubleValue()));
        }
        if (obj instanceof Double) {
            return new dt((Double) obj);
        }
        if (obj instanceof Byte) {
            return new eb(obj.toString());
        }
        if (obj instanceof Character) {
            return new eb(obj.toString());
        }
        if (obj instanceof String) {
            return new eb((String) obj);
        }
        if (obj instanceof List) {
            ArrayList arrayList = new ArrayList();
            for (Object zzQ : (List) obj) {
                arrayList.add(zzQ(zzQ));
            }
            return new dw(arrayList);
        } else if (obj instanceof Map) {
            HashMap hashMap = new HashMap();
            for (Map.Entry entry : ((Map) obj).entrySet()) {
                zzbo.zzaf(entry.getKey() instanceof String);
                hashMap.put((String) entry.getKey(), zzQ(entry.getValue()));
            }
            return new dz(hashMap);
        } else if (obj instanceof Bundle) {
            HashMap hashMap2 = new HashMap();
            for (String str : ((Bundle) obj).keySet()) {
                hashMap2.put(str, zzQ(((Bundle) obj).get(str)));
            }
            return new dz(hashMap2);
        } else {
            String valueOf = String.valueOf(obj.getClass());
            throw new UnsupportedOperationException(new StringBuilder(String.valueOf(valueOf).length() + 20).append("Type not supported: ").append(valueOf).toString());
        }
    }

    public static dp zza(zzcwa zzcwa, dp dpVar) {
        zzbo.zzu(dpVar);
        if (!zzl(dpVar) && !(dpVar instanceof du) && !(dpVar instanceof dw) && !(dpVar instanceof dz)) {
            if (dpVar instanceof ea) {
                dpVar = zza(zzcwa, (ea) dpVar);
            } else {
                throw new UnsupportedOperationException("Attempting to evaluate unknown type");
            }
        }
        if (dpVar == null) {
            throw new IllegalArgumentException("AbstractType evaluated to Java null");
        } else if (!(dpVar instanceof ea)) {
            return dpVar;
        } else {
            throw new IllegalArgumentException("AbstractType evaluated to illegal type Statement.");
        }
    }

    public static dp zza(zzcwa zzcwa, ea eaVar) {
        String zzDv = eaVar.zzDv();
        List<dp<?>> zzDw = eaVar.zzDw();
        dp<?> zzfK = zzcwa.zzfK(zzDv);
        if (zzfK == null) {
            throw new UnsupportedOperationException(new StringBuilder(String.valueOf(zzDv).length() + 28).append("Function '").append(zzDv).append("' is not supported").toString());
        } else if (zzfK instanceof du) {
            return ((zzcxo) ((du) zzfK).zzDl()).zzb(zzcwa, (dp[]) zzDw.toArray(new dp[zzDw.size()]));
        } else {
            throw new UnsupportedOperationException(new StringBuilder(String.valueOf(zzDv).length() + 29).append("Function '").append(zzDv).append("' is not a function").toString());
        }
    }

    public static dv zza(zzcwa zzcwa, List<dp<?>> list) {
        for (dp next : list) {
            zzbo.zzaf(next instanceof ea);
            dp zza = zza(zzcwa, next);
            if (zzm(zza)) {
                return (dv) zza;
            }
        }
        return dv.zzbLu;
    }

    public static Object zzj(dp<?> dpVar) {
        if (dpVar == null) {
            return null;
        }
        if (dpVar == dv.zzbLt) {
            return null;
        }
        if (dpVar instanceof ds) {
            return (Boolean) ((ds) dpVar).zzDl();
        }
        if (dpVar instanceof dt) {
            double doubleValue = ((Double) ((dt) dpVar).zzDl()).doubleValue();
            return (Double.isInfinite(doubleValue) || doubleValue - Math.floor(doubleValue) >= 1.0E-5d) ? ((Double) ((dt) dpVar).zzDl()).toString() : Integer.valueOf((int) doubleValue);
        } else if (dpVar instanceof eb) {
            return (String) ((eb) dpVar).zzDl();
        } else {
            if (dpVar instanceof dw) {
                ArrayList arrayList = new ArrayList();
                for (dp dpVar2 : (List) ((dw) dpVar).zzDl()) {
                    Object zzj = zzj(dpVar2);
                    if (zzj == null) {
                        zzcvk.e(String.format("Failure to convert a list element to object: %s (%s)", new Object[]{dpVar2, dpVar2.getClass().getCanonicalName()}));
                        return null;
                    }
                    arrayList.add(zzj);
                }
                return arrayList;
            } else if (dpVar instanceof dz) {
                HashMap hashMap = new HashMap();
                for (Map.Entry entry : ((Map) ((dz) dpVar).zzDl()).entrySet()) {
                    Object zzj2 = zzj((dp) entry.getValue());
                    if (zzj2 == null) {
                        zzcvk.e(String.format("Failure to convert a map's value to object: %s (%s)", new Object[]{entry.getValue(), ((dp) entry.getValue()).getClass().getCanonicalName()}));
                        return null;
                    }
                    hashMap.put((String) entry.getKey(), zzj2);
                }
                return hashMap;
            } else {
                String valueOf = String.valueOf(dpVar.getClass());
                zzcvk.e(new StringBuilder(String.valueOf(valueOf).length() + 49).append("Converting to Object from unknown abstract type: ").append(valueOf).toString());
                return null;
            }
        }
    }

    public static dp zzk(dp<?> dpVar) {
        if (dpVar instanceof dz) {
            HashSet<String> hashSet = new HashSet<>();
            Map map = (Map) ((dz) dpVar).zzDl();
            for (Map.Entry entry : map.entrySet()) {
                if (entry.getValue() == dv.zzbLu) {
                    hashSet.add((String) entry.getKey());
                }
            }
            for (String remove : hashSet) {
                map.remove(remove);
            }
        }
        return dpVar;
    }

    public static boolean zzl(dp dpVar) {
        return (dpVar instanceof ds) || (dpVar instanceof dt) || (dpVar instanceof eb) || dpVar == dv.zzbLt || dpVar == dv.zzbLu;
    }

    public static boolean zzm(dp dpVar) {
        return dpVar == dv.zzbLs || dpVar == dv.zzbLr || ((dpVar instanceof dv) && ((dv) dpVar).zzDr());
    }

    public static Bundle zzy(Map<String, dp<?>> map) {
        if (map == null) {
            return null;
        }
        Bundle bundle = new Bundle(map.size());
        for (Map.Entry next : map.entrySet()) {
            if (next.getValue() instanceof eb) {
                bundle.putString((String) next.getKey(), (String) ((eb) next.getValue()).zzDl());
            } else if (next.getValue() instanceof ds) {
                bundle.putBoolean((String) next.getKey(), ((Boolean) ((ds) next.getValue()).zzDl()).booleanValue());
            } else if (next.getValue() instanceof dt) {
                bundle.putDouble((String) next.getKey(), ((Double) ((dt) next.getValue()).zzDl()).doubleValue());
            } else if (next.getValue() instanceof dz) {
                bundle.putBundle((String) next.getKey(), zzy((Map) ((dz) next.getValue()).zzDl()));
            } else {
                throw new IllegalArgumentException(String.format("Invalid param type for key '%s'. Only boolean, double and string types and maps of thereof are supported.", new Object[]{next.getKey()}));
            }
        }
        return bundle;
    }
}
