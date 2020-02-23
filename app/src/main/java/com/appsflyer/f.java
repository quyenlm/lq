package com.appsflyer;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class f implements SensorEventListener {

    /* renamed from: ʻ  reason: contains not printable characters */
    private double f84;

    /* renamed from: ʼ  reason: contains not printable characters */
    private final int f85;
    @NonNull

    /* renamed from: ˊ  reason: contains not printable characters */
    private final String f86;

    /* renamed from: ˋ  reason: contains not printable characters */
    private final long[] f87 = new long[2];
    @NonNull

    /* renamed from: ˎ  reason: contains not printable characters */
    private final String f88;

    /* renamed from: ˏ  reason: contains not printable characters */
    private final float[][] f89 = new float[2][];

    /* renamed from: ॱ  reason: contains not printable characters */
    private final int f90;

    /* renamed from: ॱॱ  reason: contains not printable characters */
    private long f91;

    private f(int i, @Nullable String str, @Nullable String str2) {
        this.f90 = i;
        this.f88 = str == null ? "" : str;
        this.f86 = str2 == null ? "" : str2;
        this.f85 = ((this.f88.hashCode() + ((i + 31) * 31)) * 31) + this.f86.hashCode();
    }

    /* renamed from: ˎ  reason: contains not printable characters */
    static f m65(Sensor sensor) {
        return new f(sensor.getType(), sensor.getName(), sensor.getVendor());
    }

    /* renamed from: ˊ  reason: contains not printable characters */
    private static double m61(@NonNull float[] fArr, @NonNull float[] fArr2) {
        int min = Math.min(fArr.length, fArr2.length);
        double d = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
        for (int i = 0; i < min; i++) {
            d += StrictMath.pow((double) (fArr[i] - fArr2[i]), 2.0d);
        }
        return Math.sqrt(d);
    }

    @NonNull
    /* renamed from: ॱ  reason: contains not printable characters */
    private static List<Float> m66(@NonNull float[] fArr) {
        ArrayList arrayList = new ArrayList(fArr.length);
        for (float valueOf : fArr) {
            arrayList.add(Float.valueOf(valueOf));
        }
        return arrayList;
    }

    public final void onSensorChanged(SensorEvent sensorEvent) {
        boolean z;
        if (sensorEvent != null && sensorEvent.values != null) {
            Sensor sensor = sensorEvent.sensor;
            if (sensor == null || sensor.getName() == null || sensor.getVendor() == null) {
                z = false;
            } else {
                z = true;
            }
            if (z) {
                int type = sensorEvent.sensor.getType();
                String name = sensorEvent.sensor.getName();
                String vendor = sensorEvent.sensor.getVendor();
                long j = sensorEvent.timestamp;
                float[] fArr = sensorEvent.values;
                if (m64(type, name, vendor)) {
                    long currentTimeMillis = System.currentTimeMillis();
                    float[] fArr2 = this.f89[0];
                    if (fArr2 == null) {
                        this.f89[0] = Arrays.copyOf(fArr, fArr.length);
                        this.f87[0] = currentTimeMillis;
                        return;
                    }
                    float[] fArr3 = this.f89[1];
                    if (fArr3 == null) {
                        float[] copyOf = Arrays.copyOf(fArr, fArr.length);
                        this.f89[1] = copyOf;
                        this.f87[1] = currentTimeMillis;
                        this.f84 = m61(fArr2, copyOf);
                    } else if (50000000 <= j - this.f91) {
                        this.f91 = j;
                        if (Arrays.equals(fArr3, fArr)) {
                            this.f87[1] = currentTimeMillis;
                            return;
                        }
                        double r2 = m61(fArr2, fArr);
                        if (r2 > this.f84) {
                            this.f89[1] = Arrays.copyOf(fArr, fArr.length);
                            this.f87[1] = currentTimeMillis;
                            this.f84 = r2;
                        }
                    }
                }
            }
        }
    }

    public final void onAccuracyChanged(Sensor sensor, int i) {
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ॱ  reason: contains not printable characters */
    public final void m69(@NonNull Map<f, Map<String, Object>> map) {
        m63(map, true);
    }

    /* renamed from: ˊ  reason: contains not printable characters */
    public final void m68(Map<f, Map<String, Object>> map) {
        m63(map, false);
    }

    /* renamed from: ˊ  reason: contains not printable characters */
    private void m63(@NonNull Map<f, Map<String, Object>> map, boolean z) {
        boolean z2 = false;
        if (this.f89[0] != null) {
            z2 = true;
        }
        if (z2) {
            map.put(this, m62());
            if (z) {
                m67();
            }
        } else if (!map.containsKey(this)) {
            map.put(this, m62());
        }
    }

    /* renamed from: ˊ  reason: contains not printable characters */
    private boolean m64(int i, @NonNull String str, @NonNull String str2) {
        return this.f90 == i && this.f88.equals(str) && this.f86.equals(str2);
    }

    @NonNull
    /* renamed from: ˊ  reason: contains not printable characters */
    private Map<String, Object> m62() {
        HashMap hashMap = new HashMap(7);
        hashMap.put("sT", Integer.valueOf(this.f90));
        hashMap.put("sN", this.f88);
        hashMap.put("sV", this.f86);
        float[] fArr = this.f89[0];
        if (fArr != null) {
            hashMap.put("sVS", m66(fArr));
        }
        float[] fArr2 = this.f89[1];
        if (fArr2 != null) {
            hashMap.put("sVE", m66(fArr2));
        }
        return hashMap;
    }

    /* renamed from: ॱ  reason: contains not printable characters */
    private void m67() {
        for (int i = 0; i < 2; i++) {
            this.f89[i] = null;
        }
        for (int i2 = 0; i2 < 2; i2++) {
            this.f87[i2] = 0;
        }
        this.f84 = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
        this.f91 = 0;
    }

    public final int hashCode() {
        return this.f85;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof f)) {
            return false;
        }
        f fVar = (f) obj;
        return m64(fVar.f90, fVar.f88, fVar.f86);
    }
}
