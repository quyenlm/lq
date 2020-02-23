package com.google.android.gms.internal;

import android.graphics.PointF;

public final class hy implements hx {
    public final PointF zza(float f, PointF[] pointFArr, PointF pointF) {
        float f2 = 1.0f - f;
        float f3 = f2 * f2;
        float f4 = f3 * f2;
        float f5 = f * f;
        float f6 = f5 * f;
        pointF.set((pointFArr[0].x * f4) + (3.0f * f3 * f * pointFArr[1].x) + (3.0f * f2 * f5 * pointFArr[2].x) + (pointFArr[3].x * f6), (f2 * 3.0f * f5 * pointFArr[2].y) + (f3 * 3.0f * f * pointFArr[1].y) + (f4 * pointFArr[0].y) + (pointFArr[3].y * f6));
        return pointF;
    }
}
