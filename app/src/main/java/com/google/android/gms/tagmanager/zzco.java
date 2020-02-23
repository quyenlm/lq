package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzee;

public abstract class zzco extends zzee implements zzcn {
    public zzco() {
        attachInterface(this, "com.google.android.gms.tagmanager.IMeasurementProxy");
    }

    /* JADX WARNING: type inference failed for: r0v0 */
    /* JADX WARNING: type inference failed for: r0v5, types: [com.google.android.gms.tagmanager.zzch] */
    /* JADX WARNING: type inference failed for: r0v10, types: [com.google.android.gms.tagmanager.zzck] */
    /* JADX WARNING: type inference failed for: r0v15 */
    /* JADX WARNING: type inference failed for: r0v16 */
    /* JADX WARNING: type inference failed for: r0v17 */
    /* JADX WARNING: type inference failed for: r0v18 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onTransact(int r8, android.os.Parcel r9, android.os.Parcel r10, int r11) throws android.os.RemoteException {
        /*
            r7 = this;
            r0 = 0
            r6 = 1
            boolean r1 = r7.zza(r8, r9, r10, r11)
            if (r1 == 0) goto L_0x000a
            r0 = r6
        L_0x0009:
            return r0
        L_0x000a:
            switch(r8) {
                case 2: goto L_0x000f;
                case 11: goto L_0x002c;
                case 21: goto L_0x0037;
                case 22: goto L_0x0057;
                default: goto L_0x000d;
            }
        L_0x000d:
            r0 = 0
            goto L_0x0009
        L_0x000f:
            java.lang.String r1 = r9.readString()
            java.lang.String r2 = r9.readString()
            android.os.Parcelable$Creator r0 = android.os.Bundle.CREATOR
            android.os.Parcelable r3 = com.google.android.gms.internal.zzef.zza((android.os.Parcel) r9, r0)
            android.os.Bundle r3 = (android.os.Bundle) r3
            long r4 = r9.readLong()
            r0 = r7
            r0.logEventInternalNoInterceptor(r1, r2, r3, r4)
            r10.writeNoException()
        L_0x002a:
            r0 = r6
            goto L_0x0009
        L_0x002c:
            java.util.Map r0 = r7.zzBh()
            r10.writeNoException()
            r10.writeMap(r0)
            goto L_0x002a
        L_0x0037:
            android.os.IBinder r1 = r9.readStrongBinder()
            if (r1 != 0) goto L_0x0044
        L_0x003d:
            r7.zza((com.google.android.gms.tagmanager.zzck) r0)
            r10.writeNoException()
            goto L_0x002a
        L_0x0044:
            java.lang.String r0 = "com.google.android.gms.tagmanager.IMeasurementInterceptor"
            android.os.IInterface r0 = r1.queryLocalInterface(r0)
            boolean r2 = r0 instanceof com.google.android.gms.tagmanager.zzck
            if (r2 == 0) goto L_0x0051
            com.google.android.gms.tagmanager.zzck r0 = (com.google.android.gms.tagmanager.zzck) r0
            goto L_0x003d
        L_0x0051:
            com.google.android.gms.tagmanager.zzcm r0 = new com.google.android.gms.tagmanager.zzcm
            r0.<init>(r1)
            goto L_0x003d
        L_0x0057:
            android.os.IBinder r1 = r9.readStrongBinder()
            if (r1 != 0) goto L_0x0064
        L_0x005d:
            r7.zza((com.google.android.gms.tagmanager.zzch) r0)
            r10.writeNoException()
            goto L_0x002a
        L_0x0064:
            java.lang.String r0 = "com.google.android.gms.tagmanager.IMeasurementEventListener"
            android.os.IInterface r0 = r1.queryLocalInterface(r0)
            boolean r2 = r0 instanceof com.google.android.gms.tagmanager.zzch
            if (r2 == 0) goto L_0x0071
            com.google.android.gms.tagmanager.zzch r0 = (com.google.android.gms.tagmanager.zzch) r0
            goto L_0x005d
        L_0x0071:
            com.google.android.gms.tagmanager.zzcj r0 = new com.google.android.gms.tagmanager.zzcj
            r0.<init>(r1)
            goto L_0x005d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.zzco.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
    }
}
