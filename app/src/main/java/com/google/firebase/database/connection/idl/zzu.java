package com.google.firebase.database.connection.idl;

import android.os.IBinder;
import android.os.IInterface;
import com.google.android.gms.internal.zzee;

public abstract class zzu extends zzee implements zzt {
    public zzu() {
        attachInterface(this, "com.google.firebase.database.connection.idl.IPersistentConnection");
    }

    public static zzt asInterface(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.firebase.database.connection.idl.IPersistentConnection");
        return queryLocalInterface instanceof zzt ? (zzt) queryLocalInterface : new zzv(iBinder);
    }

    /* JADX WARNING: type inference failed for: r6v0 */
    /* JADX WARNING: type inference failed for: r6v2 */
    /* JADX WARNING: type inference failed for: r6v3, types: [com.google.firebase.database.connection.idl.zzah] */
    /* JADX WARNING: type inference failed for: r6v5 */
    /* JADX WARNING: type inference failed for: r6v6, types: [com.google.firebase.database.connection.idl.zzah] */
    /* JADX WARNING: type inference failed for: r6v8 */
    /* JADX WARNING: type inference failed for: r6v9, types: [com.google.firebase.database.connection.idl.zzah] */
    /* JADX WARNING: type inference failed for: r6v11 */
    /* JADX WARNING: type inference failed for: r6v12, types: [com.google.firebase.database.connection.idl.zzah] */
    /* JADX WARNING: type inference failed for: r6v14 */
    /* JADX WARNING: type inference failed for: r6v15, types: [com.google.firebase.database.connection.idl.zzah] */
    /* JADX WARNING: type inference failed for: r6v17 */
    /* JADX WARNING: type inference failed for: r6v18, types: [com.google.firebase.database.connection.idl.zzah] */
    /* JADX WARNING: type inference failed for: r6v21 */
    /* JADX WARNING: type inference failed for: r6v22, types: [com.google.firebase.database.connection.idl.zzah] */
    /* JADX WARNING: type inference failed for: r6v24 */
    /* JADX WARNING: type inference failed for: r6v25, types: [com.google.firebase.database.connection.idl.zzw] */
    /* JADX WARNING: type inference failed for: r6v26 */
    /* JADX WARNING: type inference failed for: r6v27 */
    /* JADX WARNING: type inference failed for: r6v28 */
    /* JADX WARNING: type inference failed for: r6v29 */
    /* JADX WARNING: type inference failed for: r6v30 */
    /* JADX WARNING: type inference failed for: r6v31 */
    /* JADX WARNING: type inference failed for: r6v32 */
    /* JADX WARNING: type inference failed for: r6v33 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onTransact(int r10, android.os.Parcel r11, android.os.Parcel r12, int r13) throws android.os.RemoteException {
        /*
            r9 = this;
            r7 = 1
            r6 = 0
            boolean r0 = r9.zza(r10, r11, r12, r13)
            if (r0 == 0) goto L_0x000a
            r0 = r7
        L_0x0009:
            return r0
        L_0x000a:
            switch(r10) {
                case 1: goto L_0x000f;
                case 2: goto L_0x005d;
                case 3: goto L_0x0064;
                case 4: goto L_0x006b;
                case 5: goto L_0x007d;
                case 6: goto L_0x00ca;
                case 7: goto L_0x00de;
                case 8: goto L_0x00e6;
                case 9: goto L_0x0114;
                case 10: goto L_0x0146;
                case 11: goto L_0x0174;
                case 12: goto L_0x01a2;
                case 13: goto L_0x01d0;
                case 14: goto L_0x01f6;
                case 15: goto L_0x0202;
                case 16: goto L_0x020e;
                case 17: goto L_0x0072;
                default: goto L_0x000d;
            }
        L_0x000d:
            r0 = 0
            goto L_0x0009
        L_0x000f:
            android.os.Parcelable$Creator<com.google.firebase.database.connection.idl.zzc> r0 = com.google.firebase.database.connection.idl.zzc.CREATOR
            android.os.Parcelable r0 = com.google.android.gms.internal.zzef.zza((android.os.Parcel) r11, r0)
            com.google.firebase.database.connection.idl.zzc r0 = (com.google.firebase.database.connection.idl.zzc) r0
            android.os.IBinder r2 = r11.readStrongBinder()
            if (r2 != 0) goto L_0x0034
            r2 = r6
        L_0x001e:
            android.os.IBinder r1 = r11.readStrongBinder()
            com.google.android.gms.dynamic.IObjectWrapper r3 = com.google.android.gms.dynamic.IObjectWrapper.zza.zzM(r1)
            android.os.IBinder r4 = r11.readStrongBinder()
            if (r4 != 0) goto L_0x0049
        L_0x002c:
            r9.setup(r0, r2, r3, r6)
            r12.writeNoException()
        L_0x0032:
            r0 = r7
            goto L_0x0009
        L_0x0034:
            java.lang.String r1 = "com.google.firebase.database.connection.idl.IConnectionAuthTokenProvider"
            android.os.IInterface r1 = r2.queryLocalInterface(r1)
            boolean r3 = r1 instanceof com.google.firebase.database.connection.idl.zzk
            if (r3 == 0) goto L_0x0042
            com.google.firebase.database.connection.idl.zzk r1 = (com.google.firebase.database.connection.idl.zzk) r1
            r2 = r1
            goto L_0x001e
        L_0x0042:
            com.google.firebase.database.connection.idl.zzm r1 = new com.google.firebase.database.connection.idl.zzm
            r1.<init>(r2)
            r2 = r1
            goto L_0x001e
        L_0x0049:
            java.lang.String r1 = "com.google.firebase.database.connection.idl.IPersistentConnectionDelegate"
            android.os.IInterface r1 = r4.queryLocalInterface(r1)
            boolean r5 = r1 instanceof com.google.firebase.database.connection.idl.zzw
            if (r5 == 0) goto L_0x0057
            com.google.firebase.database.connection.idl.zzw r1 = (com.google.firebase.database.connection.idl.zzw) r1
            r6 = r1
            goto L_0x002c
        L_0x0057:
            com.google.firebase.database.connection.idl.zzy r6 = new com.google.firebase.database.connection.idl.zzy
            r6.<init>(r4)
            goto L_0x002c
        L_0x005d:
            r9.initialize()
            r12.writeNoException()
            goto L_0x0032
        L_0x0064:
            r9.shutdown()
            r12.writeNoException()
            goto L_0x0032
        L_0x006b:
            r9.refreshAuthToken()
            r12.writeNoException()
            goto L_0x0032
        L_0x0072:
            java.lang.String r0 = r11.readString()
            r9.refreshAuthToken2(r0)
            r12.writeNoException()
            goto L_0x0032
        L_0x007d:
            java.util.ArrayList r1 = r11.createStringArrayList()
            android.os.IBinder r0 = r11.readStrongBinder()
            com.google.android.gms.dynamic.IObjectWrapper r2 = com.google.android.gms.dynamic.IObjectWrapper.zza.zzM(r0)
            android.os.IBinder r4 = r11.readStrongBinder()
            if (r4 != 0) goto L_0x00a2
            r3 = r6
        L_0x0090:
            long r4 = r11.readLong()
            android.os.IBinder r8 = r11.readStrongBinder()
            if (r8 != 0) goto L_0x00b6
        L_0x009a:
            r0 = r9
            r0.listen(r1, r2, r3, r4, r6)
            r12.writeNoException()
            goto L_0x0032
        L_0x00a2:
            java.lang.String r0 = "com.google.firebase.database.connection.idl.IListenHashProvider"
            android.os.IInterface r0 = r4.queryLocalInterface(r0)
            boolean r3 = r0 instanceof com.google.firebase.database.connection.idl.zzq
            if (r3 == 0) goto L_0x00b0
            com.google.firebase.database.connection.idl.zzq r0 = (com.google.firebase.database.connection.idl.zzq) r0
            r3 = r0
            goto L_0x0090
        L_0x00b0:
            com.google.firebase.database.connection.idl.zzs r3 = new com.google.firebase.database.connection.idl.zzs
            r3.<init>(r4)
            goto L_0x0090
        L_0x00b6:
            java.lang.String r0 = "com.google.firebase.database.connection.idl.IRequestResultCallback"
            android.os.IInterface r0 = r8.queryLocalInterface(r0)
            boolean r6 = r0 instanceof com.google.firebase.database.connection.idl.zzah
            if (r6 == 0) goto L_0x00c4
            com.google.firebase.database.connection.idl.zzah r0 = (com.google.firebase.database.connection.idl.zzah) r0
            r6 = r0
            goto L_0x009a
        L_0x00c4:
            com.google.firebase.database.connection.idl.zzaj r6 = new com.google.firebase.database.connection.idl.zzaj
            r6.<init>(r8)
            goto L_0x009a
        L_0x00ca:
            java.util.ArrayList r0 = r11.createStringArrayList()
            android.os.IBinder r1 = r11.readStrongBinder()
            com.google.android.gms.dynamic.IObjectWrapper r1 = com.google.android.gms.dynamic.IObjectWrapper.zza.zzM(r1)
            r9.unlisten(r0, r1)
            r12.writeNoException()
            goto L_0x0032
        L_0x00de:
            r9.purgeOutstandingWrites()
            r12.writeNoException()
            goto L_0x0032
        L_0x00e6:
            java.util.ArrayList r1 = r11.createStringArrayList()
            android.os.IBinder r0 = r11.readStrongBinder()
            com.google.android.gms.dynamic.IObjectWrapper r2 = com.google.android.gms.dynamic.IObjectWrapper.zza.zzM(r0)
            android.os.IBinder r3 = r11.readStrongBinder()
            if (r3 != 0) goto L_0x0100
        L_0x00f8:
            r9.put(r1, r2, r6)
            r12.writeNoException()
            goto L_0x0032
        L_0x0100:
            java.lang.String r0 = "com.google.firebase.database.connection.idl.IRequestResultCallback"
            android.os.IInterface r0 = r3.queryLocalInterface(r0)
            boolean r4 = r0 instanceof com.google.firebase.database.connection.idl.zzah
            if (r4 == 0) goto L_0x010e
            com.google.firebase.database.connection.idl.zzah r0 = (com.google.firebase.database.connection.idl.zzah) r0
            r6 = r0
            goto L_0x00f8
        L_0x010e:
            com.google.firebase.database.connection.idl.zzaj r6 = new com.google.firebase.database.connection.idl.zzaj
            r6.<init>(r3)
            goto L_0x00f8
        L_0x0114:
            java.util.ArrayList r1 = r11.createStringArrayList()
            android.os.IBinder r0 = r11.readStrongBinder()
            com.google.android.gms.dynamic.IObjectWrapper r2 = com.google.android.gms.dynamic.IObjectWrapper.zza.zzM(r0)
            java.lang.String r3 = r11.readString()
            android.os.IBinder r4 = r11.readStrongBinder()
            if (r4 != 0) goto L_0x0132
        L_0x012a:
            r9.compareAndPut(r1, r2, r3, r6)
            r12.writeNoException()
            goto L_0x0032
        L_0x0132:
            java.lang.String r0 = "com.google.firebase.database.connection.idl.IRequestResultCallback"
            android.os.IInterface r0 = r4.queryLocalInterface(r0)
            boolean r5 = r0 instanceof com.google.firebase.database.connection.idl.zzah
            if (r5 == 0) goto L_0x0140
            com.google.firebase.database.connection.idl.zzah r0 = (com.google.firebase.database.connection.idl.zzah) r0
            r6 = r0
            goto L_0x012a
        L_0x0140:
            com.google.firebase.database.connection.idl.zzaj r6 = new com.google.firebase.database.connection.idl.zzaj
            r6.<init>(r4)
            goto L_0x012a
        L_0x0146:
            java.util.ArrayList r1 = r11.createStringArrayList()
            android.os.IBinder r0 = r11.readStrongBinder()
            com.google.android.gms.dynamic.IObjectWrapper r2 = com.google.android.gms.dynamic.IObjectWrapper.zza.zzM(r0)
            android.os.IBinder r3 = r11.readStrongBinder()
            if (r3 != 0) goto L_0x0160
        L_0x0158:
            r9.merge(r1, r2, r6)
            r12.writeNoException()
            goto L_0x0032
        L_0x0160:
            java.lang.String r0 = "com.google.firebase.database.connection.idl.IRequestResultCallback"
            android.os.IInterface r0 = r3.queryLocalInterface(r0)
            boolean r4 = r0 instanceof com.google.firebase.database.connection.idl.zzah
            if (r4 == 0) goto L_0x016e
            com.google.firebase.database.connection.idl.zzah r0 = (com.google.firebase.database.connection.idl.zzah) r0
            r6 = r0
            goto L_0x0158
        L_0x016e:
            com.google.firebase.database.connection.idl.zzaj r6 = new com.google.firebase.database.connection.idl.zzaj
            r6.<init>(r3)
            goto L_0x0158
        L_0x0174:
            java.util.ArrayList r1 = r11.createStringArrayList()
            android.os.IBinder r0 = r11.readStrongBinder()
            com.google.android.gms.dynamic.IObjectWrapper r2 = com.google.android.gms.dynamic.IObjectWrapper.zza.zzM(r0)
            android.os.IBinder r3 = r11.readStrongBinder()
            if (r3 != 0) goto L_0x018e
        L_0x0186:
            r9.onDisconnectPut(r1, r2, r6)
            r12.writeNoException()
            goto L_0x0032
        L_0x018e:
            java.lang.String r0 = "com.google.firebase.database.connection.idl.IRequestResultCallback"
            android.os.IInterface r0 = r3.queryLocalInterface(r0)
            boolean r4 = r0 instanceof com.google.firebase.database.connection.idl.zzah
            if (r4 == 0) goto L_0x019c
            com.google.firebase.database.connection.idl.zzah r0 = (com.google.firebase.database.connection.idl.zzah) r0
            r6 = r0
            goto L_0x0186
        L_0x019c:
            com.google.firebase.database.connection.idl.zzaj r6 = new com.google.firebase.database.connection.idl.zzaj
            r6.<init>(r3)
            goto L_0x0186
        L_0x01a2:
            java.util.ArrayList r1 = r11.createStringArrayList()
            android.os.IBinder r0 = r11.readStrongBinder()
            com.google.android.gms.dynamic.IObjectWrapper r2 = com.google.android.gms.dynamic.IObjectWrapper.zza.zzM(r0)
            android.os.IBinder r3 = r11.readStrongBinder()
            if (r3 != 0) goto L_0x01bc
        L_0x01b4:
            r9.onDisconnectMerge(r1, r2, r6)
            r12.writeNoException()
            goto L_0x0032
        L_0x01bc:
            java.lang.String r0 = "com.google.firebase.database.connection.idl.IRequestResultCallback"
            android.os.IInterface r0 = r3.queryLocalInterface(r0)
            boolean r4 = r0 instanceof com.google.firebase.database.connection.idl.zzah
            if (r4 == 0) goto L_0x01ca
            com.google.firebase.database.connection.idl.zzah r0 = (com.google.firebase.database.connection.idl.zzah) r0
            r6 = r0
            goto L_0x01b4
        L_0x01ca:
            com.google.firebase.database.connection.idl.zzaj r6 = new com.google.firebase.database.connection.idl.zzaj
            r6.<init>(r3)
            goto L_0x01b4
        L_0x01d0:
            java.util.ArrayList r1 = r11.createStringArrayList()
            android.os.IBinder r2 = r11.readStrongBinder()
            if (r2 != 0) goto L_0x01e2
        L_0x01da:
            r9.onDisconnectCancel(r1, r6)
            r12.writeNoException()
            goto L_0x0032
        L_0x01e2:
            java.lang.String r0 = "com.google.firebase.database.connection.idl.IRequestResultCallback"
            android.os.IInterface r0 = r2.queryLocalInterface(r0)
            boolean r3 = r0 instanceof com.google.firebase.database.connection.idl.zzah
            if (r3 == 0) goto L_0x01f0
            com.google.firebase.database.connection.idl.zzah r0 = (com.google.firebase.database.connection.idl.zzah) r0
            r6 = r0
            goto L_0x01da
        L_0x01f0:
            com.google.firebase.database.connection.idl.zzaj r6 = new com.google.firebase.database.connection.idl.zzaj
            r6.<init>(r2)
            goto L_0x01da
        L_0x01f6:
            java.lang.String r0 = r11.readString()
            r9.interrupt(r0)
            r12.writeNoException()
            goto L_0x0032
        L_0x0202:
            java.lang.String r0 = r11.readString()
            r9.resume(r0)
            r12.writeNoException()
            goto L_0x0032
        L_0x020e:
            java.lang.String r0 = r11.readString()
            boolean r0 = r9.isInterrupted(r0)
            r12.writeNoException()
            com.google.android.gms.internal.zzef.zza((android.os.Parcel) r12, (boolean) r0)
            goto L_0x0032
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.database.connection.idl.zzu.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
    }
}
