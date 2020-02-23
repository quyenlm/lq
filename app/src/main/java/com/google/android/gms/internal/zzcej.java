package com.google.android.gms.internal;

import android.content.ContentValues;
import android.database.sqlite.SQLiteException;
import android.support.annotation.WorkerThread;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import com.amazonaws.services.s3.internal.Constants;
import com.google.android.gms.common.internal.zzbo;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

final class zzcej extends zzchj {
    zzcej(zzcgl zzcgl) {
        super(zzcgl);
    }

    private final Boolean zza(double d, zzcjp zzcjp) {
        try {
            return zza(new BigDecimal(d), zzcjp, Math.ulp(d));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private final Boolean zza(long j, zzcjp zzcjp) {
        try {
            return zza(new BigDecimal(j), zzcjp, (double) FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private final Boolean zza(zzcjn zzcjn, zzcjw zzcjw, long j) {
        Boolean zza;
        if (zzcjn.zzbuQ != null) {
            Boolean zza2 = zza(j, zzcjn.zzbuQ);
            if (zza2 == null) {
                return null;
            }
            if (!zza2.booleanValue()) {
                return false;
            }
        }
        HashSet hashSet = new HashSet();
        for (zzcjo zzcjo : zzcjn.zzbuO) {
            if (TextUtils.isEmpty(zzcjo.zzbuV)) {
                zzwF().zzyz().zzj("null or empty param name in filter. event", zzwA().zzdW(zzcjw.name));
                return null;
            }
            hashSet.add(zzcjo.zzbuV);
        }
        ArrayMap arrayMap = new ArrayMap();
        for (zzcjx zzcjx : zzcjw.zzbvw) {
            if (hashSet.contains(zzcjx.name)) {
                if (zzcjx.zzbvA != null) {
                    arrayMap.put(zzcjx.name, zzcjx.zzbvA);
                } else if (zzcjx.zzbuB != null) {
                    arrayMap.put(zzcjx.name, zzcjx.zzbuB);
                } else if (zzcjx.zzaIF != null) {
                    arrayMap.put(zzcjx.name, zzcjx.zzaIF);
                } else {
                    zzwF().zzyz().zze("Unknown value for param. event, param", zzwA().zzdW(zzcjw.name), zzwA().zzdX(zzcjx.name));
                    return null;
                }
            }
        }
        for (zzcjo zzcjo2 : zzcjn.zzbuO) {
            boolean equals = Boolean.TRUE.equals(zzcjo2.zzbuU);
            String str = zzcjo2.zzbuV;
            if (TextUtils.isEmpty(str)) {
                zzwF().zzyz().zzj("Event has empty param name. event", zzwA().zzdW(zzcjw.name));
                return null;
            }
            Object obj = arrayMap.get(str);
            if (obj instanceof Long) {
                if (zzcjo2.zzbuT == null) {
                    zzwF().zzyz().zze("No number filter for long param. event, param", zzwA().zzdW(zzcjw.name), zzwA().zzdX(str));
                    return null;
                }
                Boolean zza3 = zza(((Long) obj).longValue(), zzcjo2.zzbuT);
                if (zza3 == null) {
                    return null;
                }
                if ((!zza3.booleanValue()) ^ equals) {
                    return false;
                }
            } else if (obj instanceof Double) {
                if (zzcjo2.zzbuT == null) {
                    zzwF().zzyz().zze("No number filter for double param. event, param", zzwA().zzdW(zzcjw.name), zzwA().zzdX(str));
                    return null;
                }
                Boolean zza4 = zza(((Double) obj).doubleValue(), zzcjo2.zzbuT);
                if (zza4 == null) {
                    return null;
                }
                if ((!zza4.booleanValue()) ^ equals) {
                    return false;
                }
            } else if (obj instanceof String) {
                if (zzcjo2.zzbuS != null) {
                    zza = zza((String) obj, zzcjo2.zzbuS);
                } else if (zzcjo2.zzbuT == null) {
                    zzwF().zzyz().zze("No filter for String param. event, param", zzwA().zzdW(zzcjw.name), zzwA().zzdX(str));
                    return null;
                } else if (zzcjl.zzez((String) obj)) {
                    zza = zza((String) obj, zzcjo2.zzbuT);
                } else {
                    zzwF().zzyz().zze("Invalid param value for number filter. event, param", zzwA().zzdW(zzcjw.name), zzwA().zzdX(str));
                    return null;
                }
                if (zza == null) {
                    return null;
                }
                if ((!zza.booleanValue()) ^ equals) {
                    return false;
                }
            } else if (obj == null) {
                zzwF().zzyD().zze("Missing param for filter. event, param", zzwA().zzdW(zzcjw.name), zzwA().zzdX(str));
                return false;
            } else {
                zzwF().zzyz().zze("Unknown param type. event, param", zzwA().zzdW(zzcjw.name), zzwA().zzdX(str));
                return null;
            }
        }
        return true;
    }

    private static Boolean zza(Boolean bool, boolean z) {
        if (bool == null) {
            return null;
        }
        return Boolean.valueOf(bool.booleanValue() ^ z);
    }

    private final Boolean zza(String str, int i, boolean z, String str2, List<String> list, String str3) {
        if (str == null) {
            return null;
        }
        if (i == 6) {
            if (list == null || list.size() == 0) {
                return null;
            }
        } else if (str2 == null) {
            return null;
        }
        if (!z && i != 1) {
            str = str.toUpperCase(Locale.ENGLISH);
        }
        switch (i) {
            case 1:
                try {
                    return Boolean.valueOf(Pattern.compile(str3, z ? 0 : 66).matcher(str).matches());
                } catch (PatternSyntaxException e) {
                    zzwF().zzyz().zzj("Invalid regular expression in REGEXP audience filter. expression", str3);
                    return null;
                }
            case 2:
                return Boolean.valueOf(str.startsWith(str2));
            case 3:
                return Boolean.valueOf(str.endsWith(str2));
            case 4:
                return Boolean.valueOf(str.contains(str2));
            case 5:
                return Boolean.valueOf(str.equals(str2));
            case 6:
                return Boolean.valueOf(list.contains(str));
            default:
                return null;
        }
    }

    private final Boolean zza(String str, zzcjp zzcjp) {
        if (!zzcjl.zzez(str)) {
            return null;
        }
        try {
            return zza(new BigDecimal(str), zzcjp, (double) FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private final Boolean zza(String str, zzcjr zzcjr) {
        List arrayList;
        String str2 = null;
        zzbo.zzu(zzcjr);
        if (str == null || zzcjr.zzbve == null || zzcjr.zzbve.intValue() == 0) {
            return null;
        }
        if (zzcjr.zzbve.intValue() == 6) {
            if (zzcjr.zzbvh == null || zzcjr.zzbvh.length == 0) {
                return null;
            }
        } else if (zzcjr.zzbvf == null) {
            return null;
        }
        int intValue = zzcjr.zzbve.intValue();
        boolean z = zzcjr.zzbvg != null && zzcjr.zzbvg.booleanValue();
        String upperCase = (z || intValue == 1 || intValue == 6) ? zzcjr.zzbvf : zzcjr.zzbvf.toUpperCase(Locale.ENGLISH);
        if (zzcjr.zzbvh == null) {
            arrayList = null;
        } else {
            String[] strArr = zzcjr.zzbvh;
            if (z) {
                arrayList = Arrays.asList(strArr);
            } else {
                arrayList = new ArrayList();
                for (String upperCase2 : strArr) {
                    arrayList.add(upperCase2.toUpperCase(Locale.ENGLISH));
                }
            }
        }
        if (intValue == 1) {
            str2 = upperCase;
        }
        return zza(str, intValue, z, upperCase, arrayList, str2);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:38:0x007d, code lost:
        if (r5 != null) goto L_0x007f;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.Boolean zza(java.math.BigDecimal r10, com.google.android.gms.internal.zzcjp r11, double r12) {
        /*
            r8 = 4
            r7 = -1
            r1 = 0
            r0 = 1
            r2 = 0
            com.google.android.gms.common.internal.zzbo.zzu(r11)
            java.lang.Integer r3 = r11.zzbuW
            if (r3 == 0) goto L_0x0014
            java.lang.Integer r3 = r11.zzbuW
            int r3 = r3.intValue()
            if (r3 != 0) goto L_0x0016
        L_0x0014:
            r0 = r2
        L_0x0015:
            return r0
        L_0x0016:
            java.lang.Integer r3 = r11.zzbuW
            int r3 = r3.intValue()
            if (r3 != r8) goto L_0x0028
            java.lang.String r3 = r11.zzbuZ
            if (r3 == 0) goto L_0x0026
            java.lang.String r3 = r11.zzbva
            if (r3 != 0) goto L_0x002e
        L_0x0026:
            r0 = r2
            goto L_0x0015
        L_0x0028:
            java.lang.String r3 = r11.zzbuY
            if (r3 != 0) goto L_0x002e
            r0 = r2
            goto L_0x0015
        L_0x002e:
            java.lang.Integer r3 = r11.zzbuW
            int r6 = r3.intValue()
            java.lang.Integer r3 = r11.zzbuW
            int r3 = r3.intValue()
            if (r3 != r8) goto L_0x0066
            java.lang.String r3 = r11.zzbuZ
            boolean r3 = com.google.android.gms.internal.zzcjl.zzez(r3)
            if (r3 == 0) goto L_0x004c
            java.lang.String r3 = r11.zzbva
            boolean r3 = com.google.android.gms.internal.zzcjl.zzez(r3)
            if (r3 != 0) goto L_0x004e
        L_0x004c:
            r0 = r2
            goto L_0x0015
        L_0x004e:
            java.math.BigDecimal r4 = new java.math.BigDecimal     // Catch:{ NumberFormatException -> 0x0063 }
            java.lang.String r3 = r11.zzbuZ     // Catch:{ NumberFormatException -> 0x0063 }
            r4.<init>(r3)     // Catch:{ NumberFormatException -> 0x0063 }
            java.math.BigDecimal r3 = new java.math.BigDecimal     // Catch:{ NumberFormatException -> 0x0063 }
            java.lang.String r5 = r11.zzbva     // Catch:{ NumberFormatException -> 0x0063 }
            r3.<init>(r5)     // Catch:{ NumberFormatException -> 0x0063 }
            r5 = r2
        L_0x005d:
            if (r6 != r8) goto L_0x007d
            if (r4 != 0) goto L_0x007f
            r0 = r2
            goto L_0x0015
        L_0x0063:
            r0 = move-exception
            r0 = r2
            goto L_0x0015
        L_0x0066:
            java.lang.String r3 = r11.zzbuY
            boolean r3 = com.google.android.gms.internal.zzcjl.zzez(r3)
            if (r3 != 0) goto L_0x0070
            r0 = r2
            goto L_0x0015
        L_0x0070:
            java.math.BigDecimal r5 = new java.math.BigDecimal     // Catch:{ NumberFormatException -> 0x007a }
            java.lang.String r3 = r11.zzbuY     // Catch:{ NumberFormatException -> 0x007a }
            r5.<init>(r3)     // Catch:{ NumberFormatException -> 0x007a }
            r3 = r2
            r4 = r2
            goto L_0x005d
        L_0x007a:
            r0 = move-exception
            r0 = r2
            goto L_0x0015
        L_0x007d:
            if (r5 == 0) goto L_0x0082
        L_0x007f:
            switch(r6) {
                case 1: goto L_0x0084;
                case 2: goto L_0x0091;
                case 3: goto L_0x009f;
                case 4: goto L_0x00ed;
                default: goto L_0x0082;
            }
        L_0x0082:
            r0 = r2
            goto L_0x0015
        L_0x0084:
            int r2 = r10.compareTo(r5)
            if (r2 != r7) goto L_0x008f
        L_0x008a:
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)
            goto L_0x0015
        L_0x008f:
            r0 = r1
            goto L_0x008a
        L_0x0091:
            int r2 = r10.compareTo(r5)
            if (r2 != r0) goto L_0x009d
        L_0x0097:
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)
            goto L_0x0015
        L_0x009d:
            r0 = r1
            goto L_0x0097
        L_0x009f:
            r2 = 0
            int r2 = (r12 > r2 ? 1 : (r12 == r2 ? 0 : -1))
            if (r2 == 0) goto L_0x00df
            java.math.BigDecimal r2 = new java.math.BigDecimal
            r2.<init>(r12)
            java.math.BigDecimal r3 = new java.math.BigDecimal
            r4 = 2
            r3.<init>(r4)
            java.math.BigDecimal r2 = r2.multiply(r3)
            java.math.BigDecimal r2 = r5.subtract(r2)
            int r2 = r10.compareTo(r2)
            if (r2 != r0) goto L_0x00dd
            java.math.BigDecimal r2 = new java.math.BigDecimal
            r2.<init>(r12)
            java.math.BigDecimal r3 = new java.math.BigDecimal
            r4 = 2
            r3.<init>(r4)
            java.math.BigDecimal r2 = r2.multiply(r3)
            java.math.BigDecimal r2 = r5.add(r2)
            int r2 = r10.compareTo(r2)
            if (r2 != r7) goto L_0x00dd
        L_0x00d7:
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)
            goto L_0x0015
        L_0x00dd:
            r0 = r1
            goto L_0x00d7
        L_0x00df:
            int r2 = r10.compareTo(r5)
            if (r2 != 0) goto L_0x00eb
        L_0x00e5:
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)
            goto L_0x0015
        L_0x00eb:
            r0 = r1
            goto L_0x00e5
        L_0x00ed:
            int r2 = r10.compareTo(r4)
            if (r2 == r7) goto L_0x00ff
            int r2 = r10.compareTo(r3)
            if (r2 == r0) goto L_0x00ff
        L_0x00f9:
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)
            goto L_0x0015
        L_0x00ff:
            r0 = r1
            goto L_0x00f9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcej.zza(java.math.BigDecimal, com.google.android.gms.internal.zzcjp, double):java.lang.Boolean");
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final zzcjv[] zza(String str, zzcjw[] zzcjwArr, zzckb[] zzckbArr) {
        Map map;
        Boolean bool;
        zzcev zzys;
        Map map2;
        zzbo.zzcF(str);
        HashSet hashSet = new HashSet();
        ArrayMap arrayMap = new ArrayMap();
        ArrayMap arrayMap2 = new ArrayMap();
        ArrayMap arrayMap3 = new ArrayMap();
        Map<Integer, zzcka> zzdT = zzwz().zzdT(str);
        if (zzdT != null) {
            for (Integer intValue : zzdT.keySet()) {
                int intValue2 = intValue.intValue();
                zzcka zzcka = zzdT.get(Integer.valueOf(intValue2));
                BitSet bitSet = (BitSet) arrayMap2.get(Integer.valueOf(intValue2));
                BitSet bitSet2 = (BitSet) arrayMap3.get(Integer.valueOf(intValue2));
                if (bitSet == null) {
                    bitSet = new BitSet();
                    arrayMap2.put(Integer.valueOf(intValue2), bitSet);
                    bitSet2 = new BitSet();
                    arrayMap3.put(Integer.valueOf(intValue2), bitSet2);
                }
                for (int i = 0; i < (zzcka.zzbwe.length << 6); i++) {
                    if (zzcjl.zza(zzcka.zzbwe, i)) {
                        zzwF().zzyD().zze("Filter already evaluated. audience ID, filter ID", Integer.valueOf(intValue2), Integer.valueOf(i));
                        bitSet2.set(i);
                        if (zzcjl.zza(zzcka.zzbwf, i)) {
                            bitSet.set(i);
                        }
                    }
                }
                zzcjv zzcjv = new zzcjv();
                arrayMap.put(Integer.valueOf(intValue2), zzcjv);
                zzcjv.zzbvu = false;
                zzcjv.zzbvt = zzcka;
                zzcjv.zzbvs = new zzcka();
                zzcjv.zzbvs.zzbwf = zzcjl.zza(bitSet);
                zzcjv.zzbvs.zzbwe = zzcjl.zza(bitSet2);
            }
        }
        if (zzcjwArr != null) {
            ArrayMap arrayMap4 = new ArrayMap();
            int length = zzcjwArr.length;
            int i2 = 0;
            while (true) {
                int i3 = i2;
                if (i3 >= length) {
                    break;
                }
                zzcjw zzcjw = zzcjwArr[i3];
                zzcev zzE = zzwz().zzE(str, zzcjw.name);
                if (zzE == null) {
                    zzwF().zzyz().zze("Event aggregate wasn't created during raw event logging. appId, event", zzcfl.zzdZ(str), zzwA().zzdW(zzcjw.name));
                    zzys = new zzcev(str, zzcjw.name, 1, 1, zzcjw.zzbvx.longValue());
                } else {
                    zzys = zzE.zzys();
                }
                zzwz().zza(zzys);
                long j = zzys.zzbpG;
                Map map3 = (Map) arrayMap4.get(zzcjw.name);
                if (map3 == null) {
                    Map zzJ = zzwz().zzJ(str, zzcjw.name);
                    if (zzJ == null) {
                        zzJ = new ArrayMap();
                    }
                    arrayMap4.put(zzcjw.name, zzJ);
                    map2 = zzJ;
                } else {
                    map2 = map3;
                }
                for (Integer intValue3 : map2.keySet()) {
                    int intValue4 = intValue3.intValue();
                    if (hashSet.contains(Integer.valueOf(intValue4))) {
                        zzwF().zzyD().zzj("Skipping failed audience ID", Integer.valueOf(intValue4));
                    } else {
                        zzcjv zzcjv2 = (zzcjv) arrayMap.get(Integer.valueOf(intValue4));
                        BitSet bitSet3 = (BitSet) arrayMap2.get(Integer.valueOf(intValue4));
                        BitSet bitSet4 = (BitSet) arrayMap3.get(Integer.valueOf(intValue4));
                        if (zzcjv2 == null) {
                            zzcjv zzcjv3 = new zzcjv();
                            arrayMap.put(Integer.valueOf(intValue4), zzcjv3);
                            zzcjv3.zzbvu = true;
                            bitSet3 = new BitSet();
                            arrayMap2.put(Integer.valueOf(intValue4), bitSet3);
                            bitSet4 = new BitSet();
                            arrayMap3.put(Integer.valueOf(intValue4), bitSet4);
                        }
                        for (zzcjn zzcjn : (List) map2.get(Integer.valueOf(intValue4))) {
                            if (zzwF().zzz(2)) {
                                zzwF().zzyD().zzd("Evaluating filter. audience, filter, event", Integer.valueOf(intValue4), zzcjn.zzbuM, zzwA().zzdW(zzcjn.zzbuN));
                                zzwF().zzyD().zzj("Filter definition", zzwA().zza(zzcjn));
                            }
                            if (zzcjn.zzbuM == null || zzcjn.zzbuM.intValue() > 256) {
                                zzwF().zzyz().zze("Invalid event filter ID. appId, id", zzcfl.zzdZ(str), String.valueOf(zzcjn.zzbuM));
                            } else if (bitSet3.get(zzcjn.zzbuM.intValue())) {
                                zzwF().zzyD().zze("Event filter already evaluated true. audience ID, filter ID", Integer.valueOf(intValue4), zzcjn.zzbuM);
                            } else {
                                Boolean zza = zza(zzcjn, zzcjw, j);
                                zzwF().zzyD().zzj("Event filter result", zza == null ? Constants.NULL_VERSION_ID : zza);
                                if (zza == null) {
                                    hashSet.add(Integer.valueOf(intValue4));
                                } else {
                                    bitSet4.set(zzcjn.zzbuM.intValue());
                                    if (zza.booleanValue()) {
                                        bitSet3.set(zzcjn.zzbuM.intValue());
                                    }
                                }
                            }
                        }
                    }
                }
                i2 = i3 + 1;
            }
        }
        if (zzckbArr != null) {
            ArrayMap arrayMap5 = new ArrayMap();
            for (zzckb zzckb : zzckbArr) {
                Map map4 = (Map) arrayMap5.get(zzckb.name);
                if (map4 == null) {
                    Map zzK = zzwz().zzK(str, zzckb.name);
                    if (zzK == null) {
                        zzK = new ArrayMap();
                    }
                    arrayMap5.put(zzckb.name, zzK);
                    map = zzK;
                } else {
                    map = map4;
                }
                for (Integer intValue5 : map.keySet()) {
                    int intValue6 = intValue5.intValue();
                    if (!hashSet.contains(Integer.valueOf(intValue6))) {
                        zzcjv zzcjv4 = (zzcjv) arrayMap.get(Integer.valueOf(intValue6));
                        BitSet bitSet5 = (BitSet) arrayMap2.get(Integer.valueOf(intValue6));
                        BitSet bitSet6 = (BitSet) arrayMap3.get(Integer.valueOf(intValue6));
                        if (zzcjv4 == null) {
                            zzcjv zzcjv5 = new zzcjv();
                            arrayMap.put(Integer.valueOf(intValue6), zzcjv5);
                            zzcjv5.zzbvu = true;
                            bitSet5 = new BitSet();
                            arrayMap2.put(Integer.valueOf(intValue6), bitSet5);
                            bitSet6 = new BitSet();
                            arrayMap3.put(Integer.valueOf(intValue6), bitSet6);
                        }
                        Iterator it = ((List) map.get(Integer.valueOf(intValue6))).iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            zzcjq zzcjq = (zzcjq) it.next();
                            if (zzwF().zzz(2)) {
                                zzwF().zzyD().zzd("Evaluating filter. audience, filter, property", Integer.valueOf(intValue6), zzcjq.zzbuM, zzwA().zzdY(zzcjq.zzbvc));
                                zzwF().zzyD().zzj("Filter definition", zzwA().zza(zzcjq));
                            }
                            if (zzcjq.zzbuM == null || zzcjq.zzbuM.intValue() > 256) {
                                zzwF().zzyz().zze("Invalid property filter ID. appId, id", zzcfl.zzdZ(str), String.valueOf(zzcjq.zzbuM));
                                hashSet.add(Integer.valueOf(intValue6));
                            } else if (bitSet5.get(zzcjq.zzbuM.intValue())) {
                                zzwF().zzyD().zze("Property filter already evaluated true. audience ID, filter ID", Integer.valueOf(intValue6), zzcjq.zzbuM);
                            } else {
                                zzcjo zzcjo = zzcjq.zzbvd;
                                if (zzcjo == null) {
                                    zzwF().zzyz().zzj("Missing property filter. property", zzwA().zzdY(zzckb.name));
                                    bool = null;
                                } else {
                                    boolean equals = Boolean.TRUE.equals(zzcjo.zzbuU);
                                    if (zzckb.zzbvA != null) {
                                        if (zzcjo.zzbuT == null) {
                                            zzwF().zzyz().zzj("No number filter for long property. property", zzwA().zzdY(zzckb.name));
                                            bool = null;
                                        } else {
                                            bool = zza(zza(zzckb.zzbvA.longValue(), zzcjo.zzbuT), equals);
                                        }
                                    } else if (zzckb.zzbuB != null) {
                                        if (zzcjo.zzbuT == null) {
                                            zzwF().zzyz().zzj("No number filter for double property. property", zzwA().zzdY(zzckb.name));
                                            bool = null;
                                        } else {
                                            bool = zza(zza(zzckb.zzbuB.doubleValue(), zzcjo.zzbuT), equals);
                                        }
                                    } else if (zzckb.zzaIF == null) {
                                        zzwF().zzyz().zzj("User property has no value, property", zzwA().zzdY(zzckb.name));
                                        bool = null;
                                    } else if (zzcjo.zzbuS == null) {
                                        if (zzcjo.zzbuT == null) {
                                            zzwF().zzyz().zzj("No string or number filter defined. property", zzwA().zzdY(zzckb.name));
                                        } else if (zzcjl.zzez(zzckb.zzaIF)) {
                                            bool = zza(zza(zzckb.zzaIF, zzcjo.zzbuT), equals);
                                        } else {
                                            zzwF().zzyz().zze("Invalid user property value for Numeric number filter. property, value", zzwA().zzdY(zzckb.name), zzckb.zzaIF);
                                        }
                                        bool = null;
                                    } else {
                                        bool = zza(zza(zzckb.zzaIF, zzcjo.zzbuS), equals);
                                    }
                                }
                                zzwF().zzyD().zzj("Property filter result", bool == null ? Constants.NULL_VERSION_ID : bool);
                                if (bool == null) {
                                    hashSet.add(Integer.valueOf(intValue6));
                                } else {
                                    bitSet6.set(zzcjq.zzbuM.intValue());
                                    if (bool.booleanValue()) {
                                        bitSet5.set(zzcjq.zzbuM.intValue());
                                    }
                                }
                            }
                        }
                    } else {
                        zzwF().zzyD().zzj("Skipping failed audience ID", Integer.valueOf(intValue6));
                    }
                }
            }
        }
        zzcjv[] zzcjvArr = new zzcjv[arrayMap2.size()];
        int i4 = 0;
        for (Integer intValue7 : arrayMap2.keySet()) {
            int intValue8 = intValue7.intValue();
            if (!hashSet.contains(Integer.valueOf(intValue8))) {
                zzcjv zzcjv6 = (zzcjv) arrayMap.get(Integer.valueOf(intValue8));
                zzcjv zzcjv7 = zzcjv6 == null ? new zzcjv() : zzcjv6;
                int i5 = i4 + 1;
                zzcjvArr[i4] = zzcjv7;
                zzcjv7.zzbuI = Integer.valueOf(intValue8);
                zzcjv7.zzbvs = new zzcka();
                zzcjv7.zzbvs.zzbwf = zzcjl.zza((BitSet) arrayMap2.get(Integer.valueOf(intValue8)));
                zzcjv7.zzbvs.zzbwe = zzcjl.zza((BitSet) arrayMap3.get(Integer.valueOf(intValue8)));
                zzcen zzwz = zzwz();
                zzcka zzcka2 = zzcjv7.zzbvs;
                zzwz.zzkD();
                zzwz.zzjC();
                zzbo.zzcF(str);
                zzbo.zzu(zzcka2);
                try {
                    byte[] bArr = new byte[zzcka2.zzLV()];
                    adh zzc = adh.zzc(bArr, 0, bArr.length);
                    zzcka2.zza(zzc);
                    zzc.zzLM();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("app_id", str);
                    contentValues.put("audience_id", Integer.valueOf(intValue8));
                    contentValues.put("current_results", bArr);
                    try {
                        if (zzwz.getWritableDatabase().insertWithOnConflict("audience_filter_values", (String) null, contentValues, 5) == -1) {
                            zzwz.zzwF().zzyx().zzj("Failed to insert filter results (got -1). appId", zzcfl.zzdZ(str));
                        }
                        i4 = i5;
                    } catch (SQLiteException e) {
                        zzwz.zzwF().zzyx().zze("Error storing filter results. appId", zzcfl.zzdZ(str), e);
                        i4 = i5;
                    }
                } catch (IOException e2) {
                    zzwz.zzwF().zzyx().zze("Configuration loss. Failed to serialize filter results. appId", zzcfl.zzdZ(str), e2);
                    i4 = i5;
                }
            }
        }
        return (zzcjv[]) Arrays.copyOf(zzcjvArr, i4);
    }

    /* access modifiers changed from: protected */
    public final void zzjD() {
    }
}
