package com.tencent.bugly.proguard;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/* compiled from: BUGLY */
public final class i {
    private ByteBuffer a;
    private String b = "GBK";

    /* compiled from: BUGLY */
    public static class a {
        public byte a;
        public int b;
    }

    public i() {
    }

    public i(byte[] bArr) {
        this.a = ByteBuffer.wrap(bArr);
    }

    public i(byte[] bArr, int i) {
        this.a = ByteBuffer.wrap(bArr);
        this.a.position(4);
    }

    public final void a(byte[] bArr) {
        if (this.a != null) {
            this.a.clear();
        }
        this.a = ByteBuffer.wrap(bArr);
    }

    private static int a(a aVar, ByteBuffer byteBuffer) {
        byte b2 = byteBuffer.get();
        aVar.a = (byte) (b2 & 15);
        aVar.b = (b2 & 240) >> 4;
        if (aVar.b != 15) {
            return 1;
        }
        aVar.b = byteBuffer.get();
        return 2;
    }

    private boolean a(int i) {
        try {
            a aVar = new a();
            while (true) {
                int a2 = a(aVar, this.a.duplicate());
                if (i > aVar.b && aVar.a != 11) {
                    this.a.position(a2 + this.a.position());
                    a(aVar.a);
                }
            }
            if (i == aVar.b) {
                return true;
            }
            return false;
        } catch (g | BufferUnderflowException e) {
            return false;
        }
    }

    private void a() {
        a aVar = new a();
        do {
            a(aVar, this.a);
            a(aVar.a);
        } while (aVar.a != 11);
    }

    private void a(byte b2) {
        int i = 0;
        switch (b2) {
            case 0:
                this.a.position(this.a.position() + 1);
                return;
            case 1:
                this.a.position(2 + this.a.position());
                return;
            case 2:
                this.a.position(this.a.position() + 4);
                return;
            case 3:
                this.a.position(this.a.position() + 8);
                return;
            case 4:
                this.a.position(this.a.position() + 4);
                return;
            case 5:
                this.a.position(this.a.position() + 8);
                return;
            case 6:
                int i2 = this.a.get();
                if (i2 < 0) {
                    i2 += 256;
                }
                this.a.position(i2 + this.a.position());
                return;
            case 7:
                this.a.position(this.a.getInt() + this.a.position());
                return;
            case 8:
                int a2 = a(0, 0, true);
                while (i < (a2 << 1)) {
                    a aVar = new a();
                    a(aVar, this.a);
                    a(aVar.a);
                    i++;
                }
                return;
            case 9:
                int a3 = a(0, 0, true);
                while (i < a3) {
                    a aVar2 = new a();
                    a(aVar2, this.a);
                    a(aVar2.a);
                    i++;
                }
                return;
            case 10:
                a();
                return;
            case 11:
            case 12:
                return;
            case 13:
                a aVar3 = new a();
                a(aVar3, this.a);
                if (aVar3.a != 0) {
                    throw new g("skipField with invalid type, type value: " + b2 + ", " + aVar3.a);
                }
                this.a.position(a(0, 0, true) + this.a.position());
                return;
            default:
                throw new g("invalid type.");
        }
    }

    public final boolean a(int i, boolean z) {
        if (a((byte) 0, i, z) != 0) {
            return true;
        }
        return false;
    }

    public final byte a(byte b2, int i, boolean z) {
        if (a(i)) {
            a aVar = new a();
            a(aVar, this.a);
            switch (aVar.a) {
                case 0:
                    return this.a.get();
                case 12:
                    return 0;
                default:
                    throw new g("type mismatch.");
            }
        } else if (!z) {
            return b2;
        } else {
            throw new g("require field not exist.");
        }
    }

    public final short a(short s, int i, boolean z) {
        if (a(i)) {
            a aVar = new a();
            a(aVar, this.a);
            switch (aVar.a) {
                case 0:
                    return (short) this.a.get();
                case 1:
                    return this.a.getShort();
                case 12:
                    return 0;
                default:
                    throw new g("type mismatch.");
            }
        } else if (!z) {
            return s;
        } else {
            throw new g("require field not exist.");
        }
    }

    public final int a(int i, int i2, boolean z) {
        if (a(i2)) {
            a aVar = new a();
            a(aVar, this.a);
            switch (aVar.a) {
                case 0:
                    return this.a.get();
                case 1:
                    return this.a.getShort();
                case 2:
                    return this.a.getInt();
                case 12:
                    return 0;
                default:
                    throw new g("type mismatch.");
            }
        } else if (!z) {
            return i;
        } else {
            throw new g("require field not exist.");
        }
    }

    public final long a(long j, int i, boolean z) {
        if (a(i)) {
            a aVar = new a();
            a(aVar, this.a);
            switch (aVar.a) {
                case 0:
                    return (long) this.a.get();
                case 1:
                    return (long) this.a.getShort();
                case 2:
                    return (long) this.a.getInt();
                case 3:
                    return this.a.getLong();
                case 12:
                    return 0;
                default:
                    throw new g("type mismatch.");
            }
        } else if (!z) {
            return j;
        } else {
            throw new g("require field not exist.");
        }
    }

    private float a(float f, int i, boolean z) {
        if (a(i)) {
            a aVar = new a();
            a(aVar, this.a);
            switch (aVar.a) {
                case 4:
                    return this.a.getFloat();
                case 12:
                    return 0.0f;
                default:
                    throw new g("type mismatch.");
            }
        } else if (!z) {
            return f;
        } else {
            throw new g("require field not exist.");
        }
    }

    private double a(double d, int i, boolean z) {
        if (a(i)) {
            a aVar = new a();
            a(aVar, this.a);
            switch (aVar.a) {
                case 4:
                    return (double) this.a.getFloat();
                case 5:
                    return this.a.getDouble();
                case 12:
                    return FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
                default:
                    throw new g("type mismatch.");
            }
        } else if (!z) {
            return d;
        } else {
            throw new g("require field not exist.");
        }
    }

    public final String b(int i, boolean z) {
        if (a(i)) {
            a aVar = new a();
            a(aVar, this.a);
            switch (aVar.a) {
                case 6:
                    int i2 = this.a.get();
                    if (i2 < 0) {
                        i2 += 256;
                    }
                    byte[] bArr = new byte[i2];
                    this.a.get(bArr);
                    try {
                        return new String(bArr, this.b);
                    } catch (UnsupportedEncodingException e) {
                        return new String(bArr);
                    }
                case 7:
                    int i3 = this.a.getInt();
                    if (i3 > 104857600 || i3 < 0) {
                        throw new g("String too long: " + i3);
                    }
                    byte[] bArr2 = new byte[i3];
                    this.a.get(bArr2);
                    try {
                        return new String(bArr2, this.b);
                    } catch (UnsupportedEncodingException e2) {
                        return new String(bArr2);
                    }
                default:
                    throw new g("type mismatch.");
            }
        } else if (!z) {
            return null;
        } else {
            throw new g("require field not exist.");
        }
    }

    public final <K, V> HashMap<K, V> a(Map<K, V> map, int i, boolean z) {
        return (HashMap) a(new HashMap(), map, i, z);
    }

    private <K, V> Map<K, V> a(Map<K, V> map, Map<K, V> map2, int i, boolean z) {
        if (map2 == null || map2.isEmpty()) {
            return new HashMap();
        }
        Map.Entry next = map2.entrySet().iterator().next();
        Object key = next.getKey();
        Object value = next.getValue();
        if (a(i)) {
            a aVar = new a();
            a(aVar, this.a);
            switch (aVar.a) {
                case 8:
                    int a2 = a(0, 0, true);
                    if (a2 < 0) {
                        throw new g("size invalid: " + a2);
                    }
                    for (int i2 = 0; i2 < a2; i2++) {
                        map.put(a(key, 0, true), a(value, 1, true));
                    }
                    return map;
                default:
                    throw new g("type mismatch.");
            }
        } else if (!z) {
            return map;
        } else {
            throw new g("require field not exist.");
        }
    }

    private boolean[] d(int i, boolean z) {
        boolean z2;
        if (a(i)) {
            a aVar = new a();
            a(aVar, this.a);
            switch (aVar.a) {
                case 9:
                    int a2 = a(0, 0, true);
                    if (a2 < 0) {
                        throw new g("size invalid: " + a2);
                    }
                    boolean[] zArr = new boolean[a2];
                    for (int i2 = 0; i2 < a2; i2++) {
                        if (a((byte) 0, 0, true) != 0) {
                            z2 = true;
                        } else {
                            z2 = false;
                        }
                        zArr[i2] = z2;
                    }
                    return zArr;
                default:
                    throw new g("type mismatch.");
            }
        } else if (!z) {
            return null;
        } else {
            throw new g("require field not exist.");
        }
    }

    public final byte[] c(int i, boolean z) {
        if (a(i)) {
            a aVar = new a();
            a(aVar, this.a);
            switch (aVar.a) {
                case 9:
                    int a2 = a(0, 0, true);
                    if (a2 < 0) {
                        throw new g("size invalid: " + a2);
                    }
                    byte[] bArr = new byte[a2];
                    for (int i2 = 0; i2 < a2; i2++) {
                        bArr[i2] = a(bArr[0], 0, true);
                    }
                    return bArr;
                case 13:
                    a aVar2 = new a();
                    a(aVar2, this.a);
                    if (aVar2.a != 0) {
                        throw new g("type mismatch, tag: " + i + ", type: " + aVar.a + ", " + aVar2.a);
                    }
                    int a3 = a(0, 0, true);
                    if (a3 < 0) {
                        throw new g("invalid size, tag: " + i + ", type: " + aVar.a + ", " + aVar2.a + ", size: " + a3);
                    }
                    byte[] bArr2 = new byte[a3];
                    this.a.get(bArr2);
                    return bArr2;
                default:
                    throw new g("type mismatch.");
            }
        } else if (!z) {
            return null;
        } else {
            throw new g("require field not exist.");
        }
    }

    private short[] e(int i, boolean z) {
        short[] sArr = null;
        if (a(i)) {
            a aVar = new a();
            a(aVar, this.a);
            switch (aVar.a) {
                case 9:
                    int a2 = a(0, 0, true);
                    if (a2 >= 0) {
                        sArr = new short[a2];
                        for (int i2 = 0; i2 < a2; i2++) {
                            sArr[i2] = a(sArr[0], 0, true);
                        }
                        break;
                    } else {
                        throw new g("size invalid: " + a2);
                    }
                default:
                    throw new g("type mismatch.");
            }
        } else if (z) {
            throw new g("require field not exist.");
        }
        return sArr;
    }

    private int[] f(int i, boolean z) {
        int[] iArr = null;
        if (a(i)) {
            a aVar = new a();
            a(aVar, this.a);
            switch (aVar.a) {
                case 9:
                    int a2 = a(0, 0, true);
                    if (a2 >= 0) {
                        iArr = new int[a2];
                        for (int i2 = 0; i2 < a2; i2++) {
                            iArr[i2] = a(iArr[0], 0, true);
                        }
                        break;
                    } else {
                        throw new g("size invalid: " + a2);
                    }
                default:
                    throw new g("type mismatch.");
            }
        } else if (z) {
            throw new g("require field not exist.");
        }
        return iArr;
    }

    private long[] g(int i, boolean z) {
        long[] jArr = null;
        if (a(i)) {
            a aVar = new a();
            a(aVar, this.a);
            switch (aVar.a) {
                case 9:
                    int a2 = a(0, 0, true);
                    if (a2 >= 0) {
                        jArr = new long[a2];
                        for (int i2 = 0; i2 < a2; i2++) {
                            jArr[i2] = a(jArr[0], 0, true);
                        }
                        break;
                    } else {
                        throw new g("size invalid: " + a2);
                    }
                default:
                    throw new g("type mismatch.");
            }
        } else if (z) {
            throw new g("require field not exist.");
        }
        return jArr;
    }

    private float[] h(int i, boolean z) {
        float[] fArr = null;
        if (a(i)) {
            a aVar = new a();
            a(aVar, this.a);
            switch (aVar.a) {
                case 9:
                    int a2 = a(0, 0, true);
                    if (a2 >= 0) {
                        fArr = new float[a2];
                        for (int i2 = 0; i2 < a2; i2++) {
                            fArr[i2] = a(fArr[0], 0, true);
                        }
                        break;
                    } else {
                        throw new g("size invalid: " + a2);
                    }
                default:
                    throw new g("type mismatch.");
            }
        } else if (z) {
            throw new g("require field not exist.");
        }
        return fArr;
    }

    private double[] i(int i, boolean z) {
        double[] dArr = null;
        if (a(i)) {
            a aVar = new a();
            a(aVar, this.a);
            switch (aVar.a) {
                case 9:
                    int a2 = a(0, 0, true);
                    if (a2 >= 0) {
                        dArr = new double[a2];
                        for (int i2 = 0; i2 < a2; i2++) {
                            dArr[i2] = a(dArr[0], 0, true);
                        }
                        break;
                    } else {
                        throw new g("size invalid: " + a2);
                    }
                default:
                    throw new g("type mismatch.");
            }
        } else if (z) {
            throw new g("require field not exist.");
        }
        return dArr;
    }

    private <T> T[] a(T[] tArr, int i, boolean z) {
        if (tArr != null && tArr.length != 0) {
            return b(tArr[0], i, z);
        }
        throw new g("unable to get type of key and value.");
    }

    private <T> T[] b(T t, int i, boolean z) {
        if (a(i)) {
            a aVar = new a();
            a(aVar, this.a);
            switch (aVar.a) {
                case 9:
                    int a2 = a(0, 0, true);
                    if (a2 < 0) {
                        throw new g("size invalid: " + a2);
                    }
                    T[] tArr = (Object[]) Array.newInstance(t.getClass(), a2);
                    for (int i2 = 0; i2 < a2; i2++) {
                        tArr[i2] = a(t, 0, true);
                    }
                    return tArr;
                default:
                    throw new g("type mismatch.");
            }
        } else if (!z) {
            return null;
        } else {
            throw new g("require field not exist.");
        }
    }

    public final k a(k kVar, int i, boolean z) {
        k kVar2 = null;
        if (a(i)) {
            try {
                kVar2 = (k) kVar.getClass().newInstance();
                a aVar = new a();
                a(aVar, this.a);
                if (aVar.a != 10) {
                    throw new g("type mismatch.");
                }
                kVar2.a(this);
                a();
            } catch (Exception e) {
                throw new g(e.getMessage());
            }
        } else if (z) {
            throw new g("require field not exist.");
        }
        return kVar2;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v45, resolved type: boolean} */
    /* JADX WARNING: type inference failed for: r0v0 */
    /* JADX WARNING: type inference failed for: r0v23, types: [int] */
    /* JADX WARNING: type inference failed for: r0v47 */
    /* JADX WARNING: type inference failed for: r0v50 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final <T> java.lang.Object a(T r5, int r6, boolean r7) {
        /*
            r4 = this;
            r0 = 0
            boolean r1 = r5 instanceof java.lang.Byte
            if (r1 == 0) goto L_0x000e
            byte r0 = r4.a((byte) r0, (int) r6, (boolean) r7)
            java.lang.Byte r0 = java.lang.Byte.valueOf(r0)
        L_0x000d:
            return r0
        L_0x000e:
            boolean r1 = r5 instanceof java.lang.Boolean
            if (r1 == 0) goto L_0x001e
            byte r1 = r4.a((byte) r0, (int) r6, (boolean) r7)
            if (r1 == 0) goto L_0x0019
            r0 = 1
        L_0x0019:
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)
            goto L_0x000d
        L_0x001e:
            boolean r1 = r5 instanceof java.lang.Short
            if (r1 == 0) goto L_0x002b
            short r0 = r4.a((short) r0, (int) r6, (boolean) r7)
            java.lang.Short r0 = java.lang.Short.valueOf(r0)
            goto L_0x000d
        L_0x002b:
            boolean r1 = r5 instanceof java.lang.Integer
            if (r1 == 0) goto L_0x0038
            int r0 = r4.a((int) r0, (int) r6, (boolean) r7)
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            goto L_0x000d
        L_0x0038:
            boolean r1 = r5 instanceof java.lang.Long
            if (r1 == 0) goto L_0x0047
            r0 = 0
            long r0 = r4.a((long) r0, (int) r6, (boolean) r7)
            java.lang.Long r0 = java.lang.Long.valueOf(r0)
            goto L_0x000d
        L_0x0047:
            boolean r1 = r5 instanceof java.lang.Float
            if (r1 == 0) goto L_0x0055
            r0 = 0
            float r0 = r4.a((float) r0, (int) r6, (boolean) r7)
            java.lang.Float r0 = java.lang.Float.valueOf(r0)
            goto L_0x000d
        L_0x0055:
            boolean r1 = r5 instanceof java.lang.Double
            if (r1 == 0) goto L_0x0064
            r0 = 0
            double r0 = r4.a((double) r0, (int) r6, (boolean) r7)
            java.lang.Double r0 = java.lang.Double.valueOf(r0)
            goto L_0x000d
        L_0x0064:
            boolean r1 = r5 instanceof java.lang.String
            if (r1 == 0) goto L_0x0071
            java.lang.String r0 = r4.b(r6, r7)
            java.lang.String r0 = java.lang.String.valueOf(r0)
            goto L_0x000d
        L_0x0071:
            boolean r1 = r5 instanceof java.util.Map
            if (r1 == 0) goto L_0x0083
            java.util.Map r5 = (java.util.Map) r5
            java.util.HashMap r0 = new java.util.HashMap
            r0.<init>()
            java.util.Map r0 = r4.a(r0, r5, r6, r7)
            java.util.HashMap r0 = (java.util.HashMap) r0
            goto L_0x000d
        L_0x0083:
            boolean r1 = r5 instanceof java.util.List
            if (r1 == 0) goto L_0x00b8
            java.util.List r5 = (java.util.List) r5
            if (r5 == 0) goto L_0x0091
            boolean r1 = r5.isEmpty()
            if (r1 == 0) goto L_0x0098
        L_0x0091:
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            goto L_0x000d
        L_0x0098:
            java.lang.Object r1 = r5.get(r0)
            java.lang.Object[] r2 = r4.b(r1, r6, r7)
            if (r2 != 0) goto L_0x00a5
            r0 = 0
            goto L_0x000d
        L_0x00a5:
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
        L_0x00aa:
            int r3 = r2.length
            if (r0 >= r3) goto L_0x00b5
            r3 = r2[r0]
            r1.add(r3)
            int r0 = r0 + 1
            goto L_0x00aa
        L_0x00b5:
            r0 = r1
            goto L_0x000d
        L_0x00b8:
            boolean r0 = r5 instanceof com.tencent.bugly.proguard.k
            if (r0 == 0) goto L_0x00c4
            com.tencent.bugly.proguard.k r5 = (com.tencent.bugly.proguard.k) r5
            com.tencent.bugly.proguard.k r0 = r4.a((com.tencent.bugly.proguard.k) r5, (int) r6, (boolean) r7)
            goto L_0x000d
        L_0x00c4:
            java.lang.Class r0 = r5.getClass()
            boolean r0 = r0.isArray()
            if (r0 == 0) goto L_0x0120
            boolean r0 = r5 instanceof byte[]
            if (r0 != 0) goto L_0x00d6
            boolean r0 = r5 instanceof java.lang.Byte[]
            if (r0 == 0) goto L_0x00dc
        L_0x00d6:
            byte[] r0 = r4.c(r6, r7)
            goto L_0x000d
        L_0x00dc:
            boolean r0 = r5 instanceof boolean[]
            if (r0 == 0) goto L_0x00e6
            boolean[] r0 = r4.d(r6, r7)
            goto L_0x000d
        L_0x00e6:
            boolean r0 = r5 instanceof short[]
            if (r0 == 0) goto L_0x00f0
            short[] r0 = r4.e(r6, r7)
            goto L_0x000d
        L_0x00f0:
            boolean r0 = r5 instanceof int[]
            if (r0 == 0) goto L_0x00fa
            int[] r0 = r4.f(r6, r7)
            goto L_0x000d
        L_0x00fa:
            boolean r0 = r5 instanceof long[]
            if (r0 == 0) goto L_0x0104
            long[] r0 = r4.g(r6, r7)
            goto L_0x000d
        L_0x0104:
            boolean r0 = r5 instanceof float[]
            if (r0 == 0) goto L_0x010e
            float[] r0 = r4.h(r6, r7)
            goto L_0x000d
        L_0x010e:
            boolean r0 = r5 instanceof double[]
            if (r0 == 0) goto L_0x0118
            double[] r0 = r4.i(r6, r7)
            goto L_0x000d
        L_0x0118:
            java.lang.Object[] r5 = (java.lang.Object[]) r5
            java.lang.Object[] r0 = r4.a((T[]) r5, (int) r6, (boolean) r7)
            goto L_0x000d
        L_0x0120:
            com.tencent.bugly.proguard.g r0 = new com.tencent.bugly.proguard.g
            java.lang.String r1 = "read object error: unsupport type."
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.i.a(java.lang.Object, int, boolean):java.lang.Object");
    }

    public final int a(String str) {
        this.b = str;
        return 0;
    }
}
