package com.google.android.gms.internal;

import android.graphics.PointF;
import java.util.ArrayList;

public final class ib {
    private static final hz zzbUC = new hz();
    /* access modifiers changed from: private */
    public static final hy zzbUD = new hy();
    private ArrayList<Cif> zzaLj = new ArrayList<>();

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x005d, code lost:
        r10 = r23;
        r11 = r24;
        r2 = zza(r10, r11, r22.zzbUF, r22.zzbUG, r8[0], r9, new float[1], new android.graphics.PointF(), r28);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void zza(android.graphics.PointF[] r23, com.google.android.gms.internal.hx r24, boolean r25, java.util.ArrayList<android.graphics.PointF> r26, java.util.ArrayList<java.lang.Float> r27, float r28, android.graphics.PointF r29) {
        /*
            com.google.android.gms.internal.ic r2 = new com.google.android.gms.internal.ic
            r3 = 0
            r4 = 1065353216(0x3f800000, float:1.0)
            r5 = 1065353216(0x3f800000, float:1.0)
            android.graphics.PointF r6 = new android.graphics.PointF
            r6.<init>()
            r0 = r24
            r1 = r23
            android.graphics.PointF r5 = r0.zza(r5, r1, r6)
            r2.<init>(r3, r4, r5)
            com.google.android.gms.internal.ic r21 = new com.google.android.gms.internal.ic
            r3 = 0
            r4 = 0
            android.graphics.PointF r5 = new android.graphics.PointF
            r5.<init>()
            r0 = r24
            r1 = r23
            android.graphics.PointF r4 = r0.zza(r4, r1, r5)
            r0 = r21
            r0.<init>(r2, r3, r4)
            r3 = 1
            float[] r8 = new float[r3]
            r22 = r21
            r4 = r25
        L_0x0034:
            if (r2 == 0) goto L_0x009a
            r19 = r2
            r20 = r4
        L_0x003a:
            android.graphics.PointF r9 = new android.graphics.PointF
            r9.<init>()
            r0 = r22
            float r4 = r0.zzbUF
            r0 = r22
            android.graphics.PointF r5 = r0.zzbUG
            r0 = r19
            float r6 = r0.zzbUF
            r0 = r19
            android.graphics.PointF r7 = r0.zzbUG
            r2 = r23
            r3 = r24
            r10 = r28
            boolean r2 = zza(r2, r3, r4, r5, r6, r7, r8, r9, r10)
            if (r2 != 0) goto L_0x0117
            if (r20 == 0) goto L_0x0117
            android.graphics.PointF r17 = new android.graphics.PointF
            r17.<init>()
            r2 = 1
            float[] r0 = new float[r2]
            r16 = r0
            r0 = r22
            float r12 = r0.zzbUF
            r0 = r22
            android.graphics.PointF r13 = r0.zzbUG
            r2 = 0
            r14 = r8[r2]
            r10 = r23
            r11 = r24
            r15 = r9
            r18 = r28
            boolean r2 = zza(r10, r11, r12, r13, r14, r15, r16, r17, r18)
            if (r2 == 0) goto L_0x0117
            r20 = 0
            r4 = r20
        L_0x0083:
            if (r2 == 0) goto L_0x0113
            com.google.android.gms.internal.ic r3 = new com.google.android.gms.internal.ic
            r5 = 0
            r5 = r8[r5]
            r0 = r19
            r3.<init>(r0, r5, r9)
            r0 = r22
            r0.zzbUE = r3
        L_0x0093:
            if (r2 != 0) goto L_0x010d
            com.google.android.gms.internal.ic r2 = r3.zzbUE
            r22 = r3
            goto L_0x0034
        L_0x009a:
            boolean r2 = r26.isEmpty()
            if (r2 == 0) goto L_0x00bd
            android.graphics.PointF r2 = new android.graphics.PointF
            r3 = 0
            r4 = 0
            r2.<init>(r3, r4)
            r0 = r26
            r0.add(r2)
            r2 = 0
            java.lang.Float r2 = java.lang.Float.valueOf(r2)
            r0 = r27
            r0.add(r2)
            r2 = 0
            r3 = 0
            r0 = r29
            r0.set(r2, r3)
        L_0x00bd:
            int r2 = r26.size()
            int r2 = r2 + -1
            r0 = r26
            java.lang.Object r2 = r0.get(r2)
            android.graphics.PointF r2 = (android.graphics.PointF) r2
            int r3 = r27.size()
            int r3 = r3 + -1
            r0 = r27
            java.lang.Object r3 = r0.get(r3)
            java.lang.Float r3 = (java.lang.Float) r3
            float r3 = r3.floatValue()
            r4 = r2
            r5 = r21
        L_0x00e0:
            if (r5 == 0) goto L_0x010c
            android.graphics.PointF r2 = r5.zzbUG
            r0 = r26
            r0.add(r2)
            android.graphics.PointF r2 = r5.zzbUG
            float r2 = r2.x
            float r6 = r4.x
            float r2 = r2 - r6
            android.graphics.PointF r6 = r5.zzbUG
            float r6 = r6.y
            float r4 = r4.y
            float r4 = r6 - r4
            float r2 = android.graphics.PointF.length(r2, r4)
            float r2 = r2 + r3
            java.lang.Float r3 = java.lang.Float.valueOf(r2)
            r0 = r27
            r0.add(r3)
            android.graphics.PointF r4 = r5.zzbUG
            com.google.android.gms.internal.ic r5 = r5.zzbUE
            r3 = r2
            goto L_0x00e0
        L_0x010c:
            return
        L_0x010d:
            r19 = r3
            r20 = r4
            goto L_0x003a
        L_0x0113:
            r3 = r19
            goto L_0x0093
        L_0x0117:
            r4 = r20
            goto L_0x0083
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.ib.zza(android.graphics.PointF[], com.google.android.gms.internal.hx, boolean, java.util.ArrayList, java.util.ArrayList, float, android.graphics.PointF):void");
    }

    private static boolean zza(PointF[] pointFArr, hx hxVar, float f, PointF pointF, float f2, PointF pointF2, float[] fArr, PointF pointF3, float f3) {
        fArr[0] = (f2 + f) / 2.0f;
        hxVar.zza(fArr[0], pointFArr, pointF3);
        float f4 = pointF3.x - ((pointF2.x + pointF.x) / 2.0f);
        float f5 = pointF3.y - ((pointF2.y + pointF.y) / 2.0f);
        return (f4 * f4) + (f5 * f5) > f3;
    }

    public final void cubicTo(float f, float f2, float f3, float f4, float f5, float f6) {
        this.zzaLj.add(new id(f, 0.0f, f3, 1.0f, 1.0f, 1.0f, false));
    }

    public final void moveTo(float f, float f2) {
        this.zzaLj.add(new ie(0.0f, 0.0f, false));
    }

    /* access modifiers changed from: package-private */
    public final float[] zzf(float f) {
        float f2;
        int size = this.zzaLj.size();
        ArrayList arrayList = new ArrayList(size + 1);
        ArrayList arrayList2 = new ArrayList(size + 1);
        PointF pointF = new PointF();
        for (int i = 0; i < size; i++) {
            this.zzaLj.get(i).zza(arrayList, arrayList2, 4.0000004E-6f, pointF);
        }
        if (arrayList.isEmpty()) {
            arrayList.add(new PointF(0.0f, 0.0f));
            arrayList2.add(Float.valueOf(0.0f));
        }
        float floatValue = ((Float) arrayList2.get(arrayList2.size() - 1)).floatValue();
        if (floatValue == 0.0f) {
            arrayList.add((PointF) arrayList.get(arrayList.size() - 1));
            arrayList2.add(Float.valueOf(1.0f));
            f2 = 1.0f;
        } else {
            f2 = floatValue;
        }
        int size2 = arrayList.size();
        float[] fArr = new float[(size2 * 3)];
        int i2 = 0;
        int i3 = 0;
        while (i2 < size2) {
            PointF pointF2 = (PointF) arrayList.get(i2);
            int i4 = i3 + 1;
            fArr[i3] = ((Float) arrayList2.get(i2)).floatValue() / f2;
            int i5 = i4 + 1;
            fArr[i4] = pointF2.x;
            fArr[i5] = pointF2.y;
            i2++;
            i3 = i5 + 1;
        }
        return fArr;
    }
}
