package com.google.android.gms.maps.model;

import android.support.annotation.NonNull;

public final class CustomCap extends Cap {
    public final BitmapDescriptor bitmapDescriptor;
    public final float refWidth;

    public CustomCap(@NonNull BitmapDescriptor bitmapDescriptor2) {
        this(bitmapDescriptor2, 10.0f);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public CustomCap(@android.support.annotation.NonNull com.google.android.gms.maps.model.BitmapDescriptor r4, float r5) {
        /*
            r3 = this;
            java.lang.String r0 = "bitmapDescriptor must not be null"
            java.lang.Object r0 = com.google.android.gms.common.internal.zzbo.zzb(r4, (java.lang.Object) r0)
            com.google.android.gms.maps.model.BitmapDescriptor r0 = (com.google.android.gms.maps.model.BitmapDescriptor) r0
            java.lang.String r1 = "refWidth must be positive"
            r2 = 0
            int r2 = (r5 > r2 ? 1 : (r5 == r2 ? 0 : -1))
            if (r2 > 0) goto L_0x0015
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            r0.<init>(r1)
            throw r0
        L_0x0015:
            r3.<init>(r0, r5)
            r3.bitmapDescriptor = r4
            r3.refWidth = r5
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.maps.model.CustomCap.<init>(com.google.android.gms.maps.model.BitmapDescriptor, float):void");
    }

    public final String toString() {
        String valueOf = String.valueOf(this.bitmapDescriptor);
        return new StringBuilder(String.valueOf(valueOf).length() + 55).append("[CustomCap: bitmapDescriptor=").append(valueOf).append(" refWidth=").append(this.refWidth).append("]").toString();
    }
}
