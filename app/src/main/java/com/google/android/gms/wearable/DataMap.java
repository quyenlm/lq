package com.google.android.gms.wearable;

import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.internal.ado;
import com.google.android.gms.internal.adp;
import com.google.android.gms.internal.hc;
import com.google.android.gms.internal.hd;
import com.google.android.gms.internal.he;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.vk.sdk.api.VKApiConst;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class DataMap {
    public static final String TAG = "DataMap";
    private final HashMap<String, Object> zzrO = new HashMap<>();

    public static ArrayList<DataMap> arrayListFromBundleArrayList(ArrayList<Bundle> arrayList) {
        ArrayList<DataMap> arrayList2 = new ArrayList<>();
        ArrayList arrayList3 = arrayList;
        int size = arrayList3.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList3.get(i);
            i++;
            arrayList2.add(fromBundle((Bundle) obj));
        }
        return arrayList2;
    }

    public static DataMap fromBundle(Bundle bundle) {
        bundle.setClassLoader(Asset.class.getClassLoader());
        DataMap dataMap = new DataMap();
        for (String str : bundle.keySet()) {
            Object obj = bundle.get(str);
            if (obj instanceof String) {
                dataMap.putString(str, (String) obj);
            } else if (obj instanceof Integer) {
                dataMap.putInt(str, ((Integer) obj).intValue());
            } else if (obj instanceof Long) {
                dataMap.putLong(str, ((Long) obj).longValue());
            } else if (obj instanceof Double) {
                dataMap.putDouble(str, ((Double) obj).doubleValue());
            } else if (obj instanceof Float) {
                dataMap.putFloat(str, ((Float) obj).floatValue());
            } else if (obj instanceof Boolean) {
                dataMap.putBoolean(str, ((Boolean) obj).booleanValue());
            } else if (obj instanceof Byte) {
                dataMap.putByte(str, ((Byte) obj).byteValue());
            } else if (obj instanceof byte[]) {
                dataMap.putByteArray(str, (byte[]) obj);
            } else if (obj instanceof String[]) {
                dataMap.putStringArray(str, (String[]) obj);
            } else if (obj instanceof long[]) {
                dataMap.putLongArray(str, (long[]) obj);
            } else if (obj instanceof float[]) {
                dataMap.putFloatArray(str, (float[]) obj);
            } else if (obj instanceof Asset) {
                dataMap.putAsset(str, (Asset) obj);
            } else if (obj instanceof Bundle) {
                dataMap.putDataMap(str, fromBundle((Bundle) obj));
            } else if (obj instanceof ArrayList) {
                switch (zze((ArrayList) obj)) {
                    case 0:
                        dataMap.putStringArrayList(str, (ArrayList) obj);
                        break;
                    case 1:
                        dataMap.putStringArrayList(str, (ArrayList) obj);
                        break;
                    case 2:
                        dataMap.putIntegerArrayList(str, (ArrayList) obj);
                        break;
                    case 3:
                        dataMap.putStringArrayList(str, (ArrayList) obj);
                        break;
                    case 5:
                        dataMap.putDataMapArrayList(str, arrayListFromBundleArrayList((ArrayList) obj));
                        break;
                }
            }
        }
        return dataMap;
    }

    public static DataMap fromByteArray(byte[] bArr) {
        try {
            return hc.zza(new hd(he.zzy(bArr), new ArrayList()));
        } catch (ado e) {
            throw new IllegalArgumentException("Unable to convert data", e);
        }
    }

    private static void zza(String str, Object obj, String str2, ClassCastException classCastException) {
        zza(str, obj, str2, "<null>", classCastException);
    }

    private static void zza(String str, Object obj, String str2, Object obj2, ClassCastException classCastException) {
        Log.w(TAG, "Key " + str + " expected " + str2 + " but value was a " + obj.getClass().getName() + ".  The default value " + obj2 + " was returned.");
        Log.w(TAG, "Attempt to cast generated internal exception:", classCastException);
    }

    private static void zzb(Bundle bundle, String str, Object obj) {
        if (obj instanceof String) {
            bundle.putString(str, (String) obj);
        } else if (obj instanceof Integer) {
            bundle.putInt(str, ((Integer) obj).intValue());
        } else if (obj instanceof Long) {
            bundle.putLong(str, ((Long) obj).longValue());
        } else if (obj instanceof Double) {
            bundle.putDouble(str, ((Double) obj).doubleValue());
        } else if (obj instanceof Float) {
            bundle.putFloat(str, ((Float) obj).floatValue());
        } else if (obj instanceof Boolean) {
            bundle.putBoolean(str, ((Boolean) obj).booleanValue());
        } else if (obj instanceof Byte) {
            bundle.putByte(str, ((Byte) obj).byteValue());
        } else if (obj instanceof byte[]) {
            bundle.putByteArray(str, (byte[]) obj);
        } else if (obj instanceof String[]) {
            bundle.putStringArray(str, (String[]) obj);
        } else if (obj instanceof long[]) {
            bundle.putLongArray(str, (long[]) obj);
        } else if (obj instanceof float[]) {
            bundle.putFloatArray(str, (float[]) obj);
        } else if (obj instanceof Asset) {
            bundle.putParcelable(str, (Asset) obj);
        } else if (obj instanceof DataMap) {
            bundle.putParcelable(str, ((DataMap) obj).toBundle());
        } else if (obj instanceof ArrayList) {
            switch (zze((ArrayList) obj)) {
                case 0:
                    bundle.putStringArrayList(str, (ArrayList) obj);
                    return;
                case 1:
                    bundle.putStringArrayList(str, (ArrayList) obj);
                    return;
                case 2:
                    bundle.putIntegerArrayList(str, (ArrayList) obj);
                    return;
                case 3:
                    bundle.putStringArrayList(str, (ArrayList) obj);
                    return;
                case 4:
                    ArrayList arrayList = new ArrayList();
                    ArrayList arrayList2 = (ArrayList) obj;
                    int size = arrayList2.size();
                    int i = 0;
                    while (i < size) {
                        Object obj2 = arrayList2.get(i);
                        i++;
                        arrayList.add(((DataMap) obj2).toBundle());
                    }
                    bundle.putParcelableArrayList(str, arrayList);
                    return;
                default:
                    return;
            }
        }
    }

    private static int zze(ArrayList<?> arrayList) {
        int i = 0;
        if (arrayList.isEmpty()) {
            return 0;
        }
        ArrayList arrayList2 = arrayList;
        int size = arrayList2.size();
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            if (obj != null) {
                if (obj instanceof Integer) {
                    return 2;
                }
                if (obj instanceof String) {
                    return 3;
                }
                if (obj instanceof DataMap) {
                    return 4;
                }
                if (obj instanceof Bundle) {
                    return 5;
                }
            }
        }
        return 1;
    }

    public void clear() {
        this.zzrO.clear();
    }

    public boolean containsKey(String str) {
        return this.zzrO.containsKey(str);
    }

    /* JADX WARNING: Removed duplicated region for block: B:61:0x00d8  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean equals(java.lang.Object r7) {
        /*
            r6 = this;
            r4 = 1
            r3 = 0
            boolean r0 = r7 instanceof com.google.android.gms.wearable.DataMap
            if (r0 != 0) goto L_0x0008
            r0 = r3
        L_0x0007:
            return r0
        L_0x0008:
            com.google.android.gms.wearable.DataMap r7 = (com.google.android.gms.wearable.DataMap) r7
            int r0 = r6.size()
            int r1 = r7.size()
            if (r0 == r1) goto L_0x0016
            r0 = r3
            goto L_0x0007
        L_0x0016:
            java.util.Set r0 = r6.keySet()
            java.util.Iterator r5 = r0.iterator()
        L_0x001e:
            boolean r0 = r5.hasNext()
            if (r0 == 0) goto L_0x00e4
            java.lang.Object r0 = r5.next()
            java.lang.String r0 = (java.lang.String) r0
            java.lang.Object r1 = r6.get(r0)
            java.lang.Object r2 = r7.get(r0)
            boolean r0 = r1 instanceof com.google.android.gms.wearable.Asset
            if (r0 == 0) goto L_0x0073
            boolean r0 = r2 instanceof com.google.android.gms.wearable.Asset
            if (r0 != 0) goto L_0x003c
            r0 = r3
            goto L_0x0007
        L_0x003c:
            r0 = r1
            com.google.android.gms.wearable.Asset r0 = (com.google.android.gms.wearable.Asset) r0
            r1 = r2
            com.google.android.gms.wearable.Asset r1 = (com.google.android.gms.wearable.Asset) r1
            if (r0 == 0) goto L_0x0046
            if (r1 != 0) goto L_0x004f
        L_0x0046:
            if (r0 != r1) goto L_0x004d
            r0 = r4
        L_0x0049:
            if (r0 != 0) goto L_0x001e
            r0 = r3
            goto L_0x0007
        L_0x004d:
            r0 = r3
            goto L_0x0049
        L_0x004f:
            java.lang.String r2 = r0.getDigest()
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 != 0) goto L_0x0066
            java.lang.String r0 = r0.getDigest()
            java.lang.String r1 = r1.getDigest()
            boolean r0 = r0.equals(r1)
            goto L_0x0049
        L_0x0066:
            byte[] r0 = r0.getData()
            byte[] r1 = r1.getData()
            boolean r0 = java.util.Arrays.equals(r0, r1)
            goto L_0x0049
        L_0x0073:
            boolean r0 = r1 instanceof java.lang.String[]
            if (r0 == 0) goto L_0x008a
            boolean r0 = r2 instanceof java.lang.String[]
            if (r0 != 0) goto L_0x007d
            r0 = r3
            goto L_0x0007
        L_0x007d:
            java.lang.String[] r1 = (java.lang.String[]) r1
            java.lang.String[] r2 = (java.lang.String[]) r2
            boolean r0 = java.util.Arrays.equals(r1, r2)
            if (r0 != 0) goto L_0x001e
            r0 = r3
            goto L_0x0007
        L_0x008a:
            boolean r0 = r1 instanceof long[]
            if (r0 == 0) goto L_0x00a2
            boolean r0 = r2 instanceof long[]
            if (r0 != 0) goto L_0x0095
            r0 = r3
            goto L_0x0007
        L_0x0095:
            long[] r1 = (long[]) r1
            long[] r2 = (long[]) r2
            boolean r0 = java.util.Arrays.equals(r1, r2)
            if (r0 != 0) goto L_0x001e
            r0 = r3
            goto L_0x0007
        L_0x00a2:
            boolean r0 = r1 instanceof float[]
            if (r0 == 0) goto L_0x00ba
            boolean r0 = r2 instanceof float[]
            if (r0 != 0) goto L_0x00ad
            r0 = r3
            goto L_0x0007
        L_0x00ad:
            float[] r1 = (float[]) r1
            float[] r2 = (float[]) r2
            boolean r0 = java.util.Arrays.equals(r1, r2)
            if (r0 != 0) goto L_0x001e
            r0 = r3
            goto L_0x0007
        L_0x00ba:
            boolean r0 = r1 instanceof byte[]
            if (r0 == 0) goto L_0x00d2
            boolean r0 = r2 instanceof byte[]
            if (r0 != 0) goto L_0x00c5
            r0 = r3
            goto L_0x0007
        L_0x00c5:
            byte[] r1 = (byte[]) r1
            byte[] r2 = (byte[]) r2
            boolean r0 = java.util.Arrays.equals(r1, r2)
            if (r0 != 0) goto L_0x001e
            r0 = r3
            goto L_0x0007
        L_0x00d2:
            if (r1 == 0) goto L_0x00d6
            if (r2 != 0) goto L_0x00db
        L_0x00d6:
            if (r1 == r2) goto L_0x00e4
            r0 = r3
            goto L_0x0007
        L_0x00db:
            boolean r0 = r1.equals(r2)
            if (r0 != 0) goto L_0x001e
            r0 = r3
            goto L_0x0007
        L_0x00e4:
            r0 = r4
            goto L_0x0007
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.wearable.DataMap.equals(java.lang.Object):boolean");
    }

    public <T> T get(String str) {
        return this.zzrO.get(str);
    }

    public Asset getAsset(String str) {
        Object obj = this.zzrO.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (Asset) obj;
        } catch (ClassCastException e) {
            zza(str, obj, "Asset", e);
            return null;
        }
    }

    public boolean getBoolean(String str) {
        return getBoolean(str, false);
    }

    public boolean getBoolean(String str, boolean z) {
        Object obj = this.zzrO.get(str);
        if (obj == null) {
            return z;
        }
        try {
            return ((Boolean) obj).booleanValue();
        } catch (ClassCastException e) {
            zza(str, obj, "Boolean", Boolean.valueOf(z), e);
            return z;
        }
    }

    public byte getByte(String str) {
        return getByte(str, (byte) 0);
    }

    public byte getByte(String str, byte b) {
        Object obj = this.zzrO.get(str);
        if (obj == null) {
            return b;
        }
        try {
            return ((Byte) obj).byteValue();
        } catch (ClassCastException e) {
            zza(str, obj, "Byte", Byte.valueOf(b), e);
            return b;
        }
    }

    public byte[] getByteArray(String str) {
        Object obj = this.zzrO.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (byte[]) obj;
        } catch (ClassCastException e) {
            zza(str, obj, "byte[]", e);
            return null;
        }
    }

    public DataMap getDataMap(String str) {
        Object obj = this.zzrO.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (DataMap) obj;
        } catch (ClassCastException e) {
            zza(str, obj, TAG, e);
            return null;
        }
    }

    public ArrayList<DataMap> getDataMapArrayList(String str) {
        Object obj = this.zzrO.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (ArrayList) obj;
        } catch (ClassCastException e) {
            zza(str, obj, "ArrayList<DataMap>", e);
            return null;
        }
    }

    public double getDouble(String str) {
        return getDouble(str, FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE);
    }

    public double getDouble(String str, double d) {
        Object obj = this.zzrO.get(str);
        if (obj == null) {
            return d;
        }
        try {
            return ((Double) obj).doubleValue();
        } catch (ClassCastException e) {
            zza(str, obj, "Double", Double.valueOf(d), e);
            return d;
        }
    }

    public float getFloat(String str) {
        return getFloat(str, 0.0f);
    }

    public float getFloat(String str, float f) {
        Object obj = this.zzrO.get(str);
        if (obj == null) {
            return f;
        }
        try {
            return ((Float) obj).floatValue();
        } catch (ClassCastException e) {
            zza(str, obj, "Float", Float.valueOf(f), e);
            return f;
        }
    }

    public float[] getFloatArray(String str) {
        Object obj = this.zzrO.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (float[]) obj;
        } catch (ClassCastException e) {
            zza(str, obj, "float[]", e);
            return null;
        }
    }

    public int getInt(String str) {
        return getInt(str, 0);
    }

    public int getInt(String str, int i) {
        Object obj = this.zzrO.get(str);
        if (obj == null) {
            return i;
        }
        try {
            return ((Integer) obj).intValue();
        } catch (ClassCastException e) {
            zza(str, obj, "Integer", e);
            return i;
        }
    }

    public ArrayList<Integer> getIntegerArrayList(String str) {
        Object obj = this.zzrO.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (ArrayList) obj;
        } catch (ClassCastException e) {
            zza(str, obj, "ArrayList<Integer>", e);
            return null;
        }
    }

    public long getLong(String str) {
        return getLong(str, 0);
    }

    public long getLong(String str, long j) {
        Object obj = this.zzrO.get(str);
        if (obj == null) {
            return j;
        }
        try {
            return ((Long) obj).longValue();
        } catch (ClassCastException e) {
            zza(str, obj, VKApiConst.LONG, e);
            return j;
        }
    }

    public long[] getLongArray(String str) {
        Object obj = this.zzrO.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (long[]) obj;
        } catch (ClassCastException e) {
            zza(str, obj, "long[]", e);
            return null;
        }
    }

    public String getString(String str) {
        Object obj = this.zzrO.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (String) obj;
        } catch (ClassCastException e) {
            zza(str, obj, "String", e);
            return null;
        }
    }

    public String getString(String str, String str2) {
        String string = getString(str);
        return string == null ? str2 : string;
    }

    public String[] getStringArray(String str) {
        Object obj = this.zzrO.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (String[]) obj;
        } catch (ClassCastException e) {
            zza(str, obj, "String[]", e);
            return null;
        }
    }

    public ArrayList<String> getStringArrayList(String str) {
        Object obj = this.zzrO.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (ArrayList) obj;
        } catch (ClassCastException e) {
            zza(str, obj, "ArrayList<String>", e);
            return null;
        }
    }

    public int hashCode() {
        return this.zzrO.hashCode() * 29;
    }

    public boolean isEmpty() {
        return this.zzrO.isEmpty();
    }

    public Set<String> keySet() {
        return this.zzrO.keySet();
    }

    public void putAll(DataMap dataMap) {
        for (String next : dataMap.keySet()) {
            this.zzrO.put(next, dataMap.get(next));
        }
    }

    public void putAsset(String str, Asset asset) {
        this.zzrO.put(str, asset);
    }

    public void putBoolean(String str, boolean z) {
        this.zzrO.put(str, Boolean.valueOf(z));
    }

    public void putByte(String str, byte b) {
        this.zzrO.put(str, Byte.valueOf(b));
    }

    public void putByteArray(String str, byte[] bArr) {
        this.zzrO.put(str, bArr);
    }

    public void putDataMap(String str, DataMap dataMap) {
        this.zzrO.put(str, dataMap);
    }

    public void putDataMapArrayList(String str, ArrayList<DataMap> arrayList) {
        this.zzrO.put(str, arrayList);
    }

    public void putDouble(String str, double d) {
        this.zzrO.put(str, Double.valueOf(d));
    }

    public void putFloat(String str, float f) {
        this.zzrO.put(str, Float.valueOf(f));
    }

    public void putFloatArray(String str, float[] fArr) {
        this.zzrO.put(str, fArr);
    }

    public void putInt(String str, int i) {
        this.zzrO.put(str, Integer.valueOf(i));
    }

    public void putIntegerArrayList(String str, ArrayList<Integer> arrayList) {
        this.zzrO.put(str, arrayList);
    }

    public void putLong(String str, long j) {
        this.zzrO.put(str, Long.valueOf(j));
    }

    public void putLongArray(String str, long[] jArr) {
        this.zzrO.put(str, jArr);
    }

    public void putString(String str, String str2) {
        this.zzrO.put(str, str2);
    }

    public void putStringArray(String str, String[] strArr) {
        this.zzrO.put(str, strArr);
    }

    public void putStringArrayList(String str, ArrayList<String> arrayList) {
        this.zzrO.put(str, arrayList);
    }

    public Object remove(String str) {
        return this.zzrO.remove(str);
    }

    public int size() {
        return this.zzrO.size();
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        for (String next : this.zzrO.keySet()) {
            zzb(bundle, next, this.zzrO.get(next));
        }
        return bundle;
    }

    public byte[] toByteArray() {
        return adp.zzc(hc.zza(this).zzbTF);
    }

    public String toString() {
        return this.zzrO.toString();
    }
}
