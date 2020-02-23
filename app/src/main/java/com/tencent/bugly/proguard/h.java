package com.tencent.bugly.proguard;

import com.amazonaws.services.s3.internal.Constants;
import java.util.List;
import java.util.Map;

/* compiled from: BUGLY */
public final class h {
    private StringBuilder a;
    private int b = 0;

    private void a(String str) {
        for (int i = 0; i < this.b; i++) {
            this.a.append(9);
        }
        if (str != null) {
            this.a.append(str).append(": ");
        }
    }

    public h(StringBuilder sb, int i) {
        this.a = sb;
        this.b = i;
    }

    public final h a(boolean z, String str) {
        a(str);
        this.a.append(z ? 'T' : 'F').append(10);
        return this;
    }

    public final h a(byte b2, String str) {
        a(str);
        this.a.append(b2).append(10);
        return this;
    }

    public final h a(short s, String str) {
        a(str);
        this.a.append(s).append(10);
        return this;
    }

    public final h a(int i, String str) {
        a(str);
        this.a.append(i).append(10);
        return this;
    }

    public final h a(long j, String str) {
        a(str);
        this.a.append(j).append(10);
        return this;
    }

    public final h a(String str, String str2) {
        a(str2);
        if (str == null) {
            this.a.append("null\n");
        } else {
            this.a.append(str).append(10);
        }
        return this;
    }

    public final h a(byte[] bArr, String str) {
        a(str);
        if (bArr == null) {
            this.a.append("null\n");
        } else if (bArr.length == 0) {
            this.a.append(bArr.length).append(", []\n");
        } else {
            this.a.append(bArr.length).append(", [\n");
            h hVar = new h(this.a, this.b + 1);
            for (byte append : bArr) {
                hVar.a((String) null);
                hVar.a.append(append).append(10);
            }
            a((String) null);
            this.a.append(']').append(10);
        }
        return this;
    }

    public final <K, V> h a(Map<K, V> map, String str) {
        a(str);
        if (map == null) {
            this.a.append("null\n");
        } else if (map.isEmpty()) {
            this.a.append(map.size()).append(", {}\n");
        } else {
            this.a.append(map.size()).append(", {\n");
            h hVar = new h(this.a, this.b + 1);
            h hVar2 = new h(this.a, this.b + 2);
            for (Map.Entry next : map.entrySet()) {
                hVar.a((String) null);
                hVar.a.append('(').append(10);
                hVar2.a(next.getKey(), (String) null);
                hVar2.a(next.getValue(), (String) null);
                hVar.a((String) null);
                hVar.a.append(')').append(10);
            }
            a((String) null);
            this.a.append('}').append(10);
        }
        return this;
    }

    private <T> h a(T[] tArr, String str) {
        a(str);
        if (tArr == null) {
            this.a.append("null\n");
        } else if (tArr.length == 0) {
            this.a.append(tArr.length).append(", []\n");
        } else {
            this.a.append(tArr.length).append(", [\n");
            h hVar = new h(this.a, this.b + 1);
            for (T a2 : tArr) {
                hVar.a(a2, (String) null);
            }
            a((String) null);
            this.a.append(']').append(10);
        }
        return this;
    }

    private <T> h a(T t, String str) {
        int i = 0;
        if (t == null) {
            this.a.append("null\n");
        } else if (t instanceof Byte) {
            byte byteValue = ((Byte) t).byteValue();
            a(str);
            this.a.append(byteValue).append(10);
        } else if (t instanceof Boolean) {
            boolean booleanValue = ((Boolean) t).booleanValue();
            a(str);
            this.a.append(booleanValue ? 'T' : 'F').append(10);
        } else if (t instanceof Short) {
            short shortValue = ((Short) t).shortValue();
            a(str);
            this.a.append(shortValue).append(10);
        } else if (t instanceof Integer) {
            int intValue = ((Integer) t).intValue();
            a(str);
            this.a.append(intValue).append(10);
        } else if (t instanceof Long) {
            long longValue = ((Long) t).longValue();
            a(str);
            this.a.append(longValue).append(10);
        } else if (t instanceof Float) {
            float floatValue = ((Float) t).floatValue();
            a(str);
            this.a.append(floatValue).append(10);
        } else if (t instanceof Double) {
            double doubleValue = ((Double) t).doubleValue();
            a(str);
            this.a.append(doubleValue).append(10);
        } else if (t instanceof String) {
            a((String) t, str);
        } else if (t instanceof Map) {
            a((Map) t, str);
        } else if (t instanceof List) {
            List list = (List) t;
            if (list == null) {
                a(str);
                this.a.append("null\t");
            } else {
                a((T[]) list.toArray(), str);
            }
        } else if (t instanceof k) {
            a((k) t, str);
        } else if (t instanceof byte[]) {
            a((byte[]) t, str);
        } else if (t instanceof boolean[]) {
            a((boolean[]) t, str);
        } else if (t instanceof short[]) {
            short[] sArr = (short[]) t;
            a(str);
            if (sArr == null) {
                this.a.append("null\n");
            } else if (sArr.length == 0) {
                this.a.append(sArr.length).append(", []\n");
            } else {
                this.a.append(sArr.length).append(", [\n");
                h hVar = new h(this.a, this.b + 1);
                int length = sArr.length;
                while (i < length) {
                    short s = sArr[i];
                    hVar.a((String) null);
                    hVar.a.append(s).append(10);
                    i++;
                }
                a((String) null);
                this.a.append(']').append(10);
            }
        } else if (t instanceof int[]) {
            int[] iArr = (int[]) t;
            a(str);
            if (iArr == null) {
                this.a.append("null\n");
            } else if (iArr.length == 0) {
                this.a.append(iArr.length).append(", []\n");
            } else {
                this.a.append(iArr.length).append(", [\n");
                h hVar2 = new h(this.a, this.b + 1);
                int length2 = iArr.length;
                while (i < length2) {
                    int i2 = iArr[i];
                    hVar2.a((String) null);
                    hVar2.a.append(i2).append(10);
                    i++;
                }
                a((String) null);
                this.a.append(']').append(10);
            }
        } else if (t instanceof long[]) {
            long[] jArr = (long[]) t;
            a(str);
            if (jArr == null) {
                this.a.append("null\n");
            } else if (jArr.length == 0) {
                this.a.append(jArr.length).append(", []\n");
            } else {
                this.a.append(jArr.length).append(", [\n");
                h hVar3 = new h(this.a, this.b + 1);
                int length3 = jArr.length;
                while (i < length3) {
                    long j = jArr[i];
                    hVar3.a((String) null);
                    hVar3.a.append(j).append(10);
                    i++;
                }
                a((String) null);
                this.a.append(']').append(10);
            }
        } else if (t instanceof float[]) {
            float[] fArr = (float[]) t;
            a(str);
            if (fArr == null) {
                this.a.append("null\n");
            } else if (fArr.length == 0) {
                this.a.append(fArr.length).append(", []\n");
            } else {
                this.a.append(fArr.length).append(", [\n");
                h hVar4 = new h(this.a, this.b + 1);
                int length4 = fArr.length;
                while (i < length4) {
                    float f = fArr[i];
                    hVar4.a((String) null);
                    hVar4.a.append(f).append(10);
                    i++;
                }
                a((String) null);
                this.a.append(']').append(10);
            }
        } else if (t instanceof double[]) {
            double[] dArr = (double[]) t;
            a(str);
            if (dArr == null) {
                this.a.append("null\n");
            } else if (dArr.length == 0) {
                this.a.append(dArr.length).append(", []\n");
            } else {
                this.a.append(dArr.length).append(", [\n");
                h hVar5 = new h(this.a, this.b + 1);
                int length5 = dArr.length;
                while (i < length5) {
                    double d = dArr[i];
                    hVar5.a((String) null);
                    hVar5.a.append(d).append(10);
                    i++;
                }
                a((String) null);
                this.a.append(']').append(10);
            }
        } else if (t.getClass().isArray()) {
            a((T[]) (Object[]) t, str);
        } else {
            throw new b("write object error: unsupport type.");
        }
        return this;
    }

    public final h a(k kVar, String str) {
        a(str);
        this.a.append('{').append(10);
        if (kVar == null) {
            this.a.append(9).append(Constants.NULL_VERSION_ID);
        } else {
            kVar.a(this.a, this.b + 1);
        }
        a((String) null);
        this.a.append('}').append(10);
        return this;
    }
}
