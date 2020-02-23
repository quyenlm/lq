package com.google.android.gms.tagmanager;

import com.amazonaws.services.s3.internal.Constants;
import com.google.android.gms.internal.zzbr;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class zzgk {
    private static final Object zzbHe = null;
    private static Long zzbHf = new Long(0);
    private static Double zzbHg = new Double(FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE);
    private static zzgj zzbHh = zzgj.zzai(0);
    private static String zzbHi = new String("");
    private static Boolean zzbHj = new Boolean(false);
    private static List<Object> zzbHk = new ArrayList(0);
    private static Map<Object, Object> zzbHl = new HashMap();
    private static zzbr zzbHm = zzI(zzbHi);

    private static double getDouble(Object obj) {
        if (obj instanceof Number) {
            return ((Number) obj).doubleValue();
        }
        zzdj.e("getDouble received non-Number");
        return FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
    }

    public static Object zzCb() {
        return null;
    }

    public static Long zzCc() {
        return zzbHf;
    }

    public static Double zzCd() {
        return zzbHg;
    }

    public static Boolean zzCe() {
        return zzbHj;
    }

    public static zzgj zzCf() {
        return zzbHh;
    }

    public static String zzCg() {
        return zzbHi;
    }

    public static zzbr zzCh() {
        return zzbHm;
    }

    private static String zzH(Object obj) {
        return obj == null ? zzbHi : obj.toString();
    }

    public static zzbr zzI(Object obj) {
        boolean z = false;
        zzbr zzbr = new zzbr();
        if (obj instanceof zzbr) {
            return (zzbr) obj;
        }
        if (obj instanceof String) {
            zzbr.type = 1;
            zzbr.string = (String) obj;
        } else if (obj instanceof List) {
            zzbr.type = 2;
            List<Object> list = (List) obj;
            ArrayList arrayList = new ArrayList(list.size());
            boolean z2 = false;
            for (Object zzI : list) {
                zzbr zzI2 = zzI(zzI);
                if (zzI2 == zzbHm) {
                    return zzbHm;
                }
                boolean z3 = z2 || zzI2.zzlN;
                arrayList.add(zzI2);
                z2 = z3;
            }
            zzbr.zzlE = (zzbr[]) arrayList.toArray(new zzbr[0]);
            z = z2;
        } else if (obj instanceof Map) {
            zzbr.type = 3;
            Set<Map.Entry> entrySet = ((Map) obj).entrySet();
            ArrayList arrayList2 = new ArrayList(entrySet.size());
            ArrayList arrayList3 = new ArrayList(entrySet.size());
            boolean z4 = false;
            for (Map.Entry entry : entrySet) {
                zzbr zzI3 = zzI(entry.getKey());
                zzbr zzI4 = zzI(entry.getValue());
                if (zzI3 == zzbHm || zzI4 == zzbHm) {
                    return zzbHm;
                }
                boolean z5 = z4 || zzI3.zzlN || zzI4.zzlN;
                arrayList2.add(zzI3);
                arrayList3.add(zzI4);
                z4 = z5;
            }
            zzbr.zzlF = (zzbr[]) arrayList2.toArray(new zzbr[0]);
            zzbr.zzlG = (zzbr[]) arrayList3.toArray(new zzbr[0]);
            z = z4;
        } else if (zzJ(obj)) {
            zzbr.type = 1;
            zzbr.string = obj.toString();
        } else if (zzK(obj)) {
            zzbr.type = 6;
            zzbr.zzlJ = zzL(obj);
        } else if (obj instanceof Boolean) {
            zzbr.type = 8;
            zzbr.zzlK = ((Boolean) obj).booleanValue();
        } else {
            String valueOf = String.valueOf(obj == null ? Constants.NULL_VERSION_ID : obj.getClass().toString());
            zzdj.e(valueOf.length() != 0 ? "Converting to Value from unknown object type: ".concat(valueOf) : new String("Converting to Value from unknown object type: "));
            return zzbHm;
        }
        zzbr.zzlN = z;
        return zzbr;
    }

    private static boolean zzJ(Object obj) {
        return (obj instanceof Double) || (obj instanceof Float) || ((obj instanceof zzgj) && ((zzgj) obj).zzBZ());
    }

    private static boolean zzK(Object obj) {
        return (obj instanceof Byte) || (obj instanceof Short) || (obj instanceof Integer) || (obj instanceof Long) || ((obj instanceof zzgj) && ((zzgj) obj).zzCa());
    }

    private static long zzL(Object obj) {
        if (obj instanceof Number) {
            return ((Number) obj).longValue();
        }
        zzdj.e("getInt64 received non-Number");
        return 0;
    }

    public static String zzb(zzbr zzbr) {
        return zzH(zzg(zzbr));
    }

    public static zzgj zzc(zzbr zzbr) {
        Object zzg = zzg(zzbr);
        return zzg instanceof zzgj ? (zzgj) zzg : zzK(zzg) ? zzgj.zzai(zzL(zzg)) : zzJ(zzg) ? zzgj.zza(Double.valueOf(getDouble(zzg))) : zzfz(zzH(zzg));
    }

    public static Long zzd(zzbr zzbr) {
        Object zzg = zzg(zzbr);
        if (zzK(zzg)) {
            return Long.valueOf(zzL(zzg));
        }
        zzgj zzfz = zzfz(zzH(zzg));
        return zzfz == zzbHh ? zzbHf : Long.valueOf(zzfz.longValue());
    }

    public static Double zze(zzbr zzbr) {
        Object zzg = zzg(zzbr);
        if (zzJ(zzg)) {
            return Double.valueOf(getDouble(zzg));
        }
        zzgj zzfz = zzfz(zzH(zzg));
        return zzfz == zzbHh ? zzbHg : Double.valueOf(zzfz.doubleValue());
    }

    public static Boolean zzf(zzbr zzbr) {
        Object zzg = zzg(zzbr);
        if (zzg instanceof Boolean) {
            return (Boolean) zzg;
        }
        String zzH = zzH(zzg);
        return "true".equalsIgnoreCase(zzH) ? Boolean.TRUE : "false".equalsIgnoreCase(zzH) ? Boolean.FALSE : zzbHj;
    }

    public static zzbr zzfy(String str) {
        zzbr zzbr = new zzbr();
        zzbr.type = 5;
        zzbr.zzlI = str;
        return zzbr;
    }

    private static zzgj zzfz(String str) {
        try {
            return zzgj.zzfx(str);
        } catch (NumberFormatException e) {
            zzdj.e(new StringBuilder(String.valueOf(str).length() + 33).append("Failed to convert '").append(str).append("' to a number.").toString());
            return zzbHh;
        }
    }

    public static Object zzg(zzbr zzbr) {
        int i = 0;
        if (zzbr == null) {
            return null;
        }
        switch (zzbr.type) {
            case 1:
                return zzbr.string;
            case 2:
                ArrayList arrayList = new ArrayList(zzbr.zzlE.length);
                zzbr[] zzbrArr = zzbr.zzlE;
                int length = zzbrArr.length;
                while (i < length) {
                    Object zzg = zzg(zzbrArr[i]);
                    if (zzg == null) {
                        return null;
                    }
                    arrayList.add(zzg);
                    i++;
                }
                return arrayList;
            case 3:
                if (zzbr.zzlF.length != zzbr.zzlG.length) {
                    String valueOf = String.valueOf(zzbr.toString());
                    zzdj.e(valueOf.length() != 0 ? "Converting an invalid value to object: ".concat(valueOf) : new String("Converting an invalid value to object: "));
                    return null;
                }
                HashMap hashMap = new HashMap(zzbr.zzlG.length);
                while (i < zzbr.zzlF.length) {
                    Object zzg2 = zzg(zzbr.zzlF[i]);
                    Object zzg3 = zzg(zzbr.zzlG[i]);
                    if (zzg2 == null || zzg3 == null) {
                        return null;
                    }
                    hashMap.put(zzg2, zzg3);
                    i++;
                }
                return hashMap;
            case 4:
                zzdj.e("Trying to convert a macro reference to object");
                return null;
            case 5:
                zzdj.e("Trying to convert a function id to object");
                return null;
            case 6:
                return Long.valueOf(zzbr.zzlJ);
            case 7:
                StringBuffer stringBuffer = new StringBuffer();
                zzbr[] zzbrArr2 = zzbr.zzlL;
                int length2 = zzbrArr2.length;
                while (i < length2) {
                    String zzb = zzb(zzbrArr2[i]);
                    if (zzb == zzbHi) {
                        return null;
                    }
                    stringBuffer.append(zzb);
                    i++;
                }
                return stringBuffer.toString();
            case 8:
                return Boolean.valueOf(zzbr.zzlK);
            default:
                zzdj.e(new StringBuilder(46).append("Failed to convert a value of type: ").append(zzbr.type).toString());
                return null;
        }
    }
}
