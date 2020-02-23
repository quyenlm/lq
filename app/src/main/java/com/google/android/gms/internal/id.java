package com.google.android.gms.internal;

import android.graphics.PointF;
import java.util.ArrayList;

public final class id implements Cif {
    private float zzbUH;
    private float zzbUI;
    private float zzbUJ;
    private float zzbUK;
    private float zzbUL = 1.0f;
    private float zzbUM = 1.0f;
    private final boolean zzbUN = false;

    public id(float f, float f2, float f3, float f4, float f5, float f6, boolean z) {
        this.zzbUH = f;
        this.zzbUI = f2;
        this.zzbUJ = f3;
        this.zzbUK = f4;
    }

    public final void zza(ArrayList<PointF> arrayList, ArrayList<Float> arrayList2, float f, PointF pointF) {
        ib.zza(new PointF[]{arrayList.isEmpty() ? new PointF(0.0f, 0.0f) : arrayList.get(arrayList.size() - 1), new PointF(this.zzbUH, this.zzbUI), new PointF(this.zzbUJ, this.zzbUK), new PointF(this.zzbUL, this.zzbUM)}, ib.zzbUD, true, arrayList, arrayList2, 4.0000004E-6f, pointF);
    }
}
