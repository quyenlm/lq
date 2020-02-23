package com.google.android.gms.internal;

import com.google.android.gms.fitness.data.Field;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class zzbuk {
    public static final Set<String> zzaVr = Collections.unmodifiableSet(new HashSet(Arrays.asList(new String[]{"altitude", "duration", "food_item", "meal_type", "repetitions", "resistance", "resistance_type", "debug_session", "google.android.fitness.SessionV2"})));
    private static final zzbuk zzaVu = new zzbuk();
    private final Map<String, Map<String, zzbum>> zzaVs;
    private final Map<String, zzbum> zzaVt;

    private zzbuk() {
        HashMap hashMap = new HashMap();
        hashMap.put("latitude", new zzbum(-90.0d, 90.0d));
        hashMap.put("longitude", new zzbum(-180.0d, 180.0d));
        hashMap.put("accuracy", new zzbum(FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE, 10000.0d));
        hashMap.put("bpm", new zzbum(FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE, 1000.0d));
        hashMap.put("altitude", new zzbum(-100000.0d, 100000.0d));
        hashMap.put("percentage", new zzbum(FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE, 100.0d));
        hashMap.put("confidence", new zzbum(FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE, 100.0d));
        hashMap.put("duration", new zzbum(FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE, 9.223372036854776E18d));
        hashMap.put("height", new zzbum(FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE, 3.0d));
        hashMap.put("weight", new zzbum(FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE, 1000.0d));
        hashMap.put(TransferTable.COLUMN_SPEED, new zzbum(FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE, 11000.0d));
        this.zzaVt = Collections.unmodifiableMap(hashMap);
        HashMap hashMap2 = new HashMap();
        hashMap2.put("com.google.step_count.delta", zzd("steps", new zzbum(FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE, 1.0E-8d)));
        hashMap2.put("com.google.calories.consumed", zzd(Field.NUTRIENT_CALORIES, new zzbum(FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE, 1.0E-6d)));
        hashMap2.put("com.google.calories.expended", zzd(Field.NUTRIENT_CALORIES, new zzbum(FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE, 5.555555555555555E-10d)));
        hashMap2.put("com.google.distance.delta", zzd("distance", new zzbum(FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE, 1.0E-7d)));
        this.zzaVs = Collections.unmodifiableMap(hashMap2);
    }

    private static <K, V> Map<K, V> zzd(K k, V v) {
        HashMap hashMap = new HashMap();
        hashMap.put(k, v);
        return hashMap;
    }

    public static zzbuk zztR() {
        return zzaVu;
    }

    /* access modifiers changed from: package-private */
    public final zzbum zzC(String str, String str2) {
        Map map = this.zzaVs.get(str);
        if (map != null) {
            return (zzbum) map.get(str2);
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public final zzbum zzdg(String str) {
        return this.zzaVt.get(str);
    }
}
