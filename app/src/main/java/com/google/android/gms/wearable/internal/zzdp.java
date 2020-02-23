package com.google.android.gms.wearable.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.util.Log;
import com.appsflyer.share.Constants;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzbaz;
import java.util.HashMap;
import java.util.Map;

final class zzdp<T> {
    private final Map<T, zzga<T>> zzaWU = new HashMap();

    zzdp() {
    }

    public final void zza(zzfw zzfw, zzbaz<Status> zzbaz, T t) throws RemoteException {
        synchronized (this.zzaWU) {
            zzga remove = this.zzaWU.remove(t);
            if (remove == null) {
                zzbaz.setResult(new Status(4002));
                return;
            }
            remove.clear();
            ((zzdn) zzfw.zzrf()).zza((zzdi) new zzdr(this.zzaWU, t, zzbaz), new zzeo(remove));
        }
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zza(com.google.android.gms.wearable.internal.zzfw r5, com.google.android.gms.internal.zzbaz<com.google.android.gms.common.api.Status> r6, T r7, com.google.android.gms.wearable.internal.zzga<T> r8) throws android.os.RemoteException {
        /*
            r4 = this;
            java.util.Map<T, com.google.android.gms.wearable.internal.zzga<T>> r1 = r4.zzaWU
            monitor-enter(r1)
            java.util.Map<T, com.google.android.gms.wearable.internal.zzga<T>> r0 = r4.zzaWU     // Catch:{ all -> 0x0033 }
            java.lang.Object r0 = r0.get(r7)     // Catch:{ all -> 0x0033 }
            if (r0 == 0) goto L_0x0017
            com.google.android.gms.common.api.Status r0 = new com.google.android.gms.common.api.Status     // Catch:{ all -> 0x0033 }
            r2 = 4001(0xfa1, float:5.607E-42)
            r0.<init>(r2)     // Catch:{ all -> 0x0033 }
            r6.setResult(r0)     // Catch:{ all -> 0x0033 }
            monitor-exit(r1)     // Catch:{ all -> 0x0033 }
        L_0x0016:
            return
        L_0x0017:
            java.util.Map<T, com.google.android.gms.wearable.internal.zzga<T>> r0 = r4.zzaWU     // Catch:{ all -> 0x0033 }
            r0.put(r7, r8)     // Catch:{ all -> 0x0033 }
            android.os.IInterface r0 = r5.zzrf()     // Catch:{ RemoteException -> 0x0036 }
            com.google.android.gms.wearable.internal.zzdn r0 = (com.google.android.gms.wearable.internal.zzdn) r0     // Catch:{ RemoteException -> 0x0036 }
            com.google.android.gms.wearable.internal.zzdq r2 = new com.google.android.gms.wearable.internal.zzdq     // Catch:{ RemoteException -> 0x0036 }
            java.util.Map<T, com.google.android.gms.wearable.internal.zzga<T>> r3 = r4.zzaWU     // Catch:{ RemoteException -> 0x0036 }
            r2.<init>(r3, r7, r6)     // Catch:{ RemoteException -> 0x0036 }
            com.google.android.gms.wearable.internal.zzd r3 = new com.google.android.gms.wearable.internal.zzd     // Catch:{ RemoteException -> 0x0036 }
            r3.<init>(r8)     // Catch:{ RemoteException -> 0x0036 }
            r0.zza((com.google.android.gms.wearable.internal.zzdi) r2, (com.google.android.gms.wearable.internal.zzd) r3)     // Catch:{ RemoteException -> 0x0036 }
            monitor-exit(r1)     // Catch:{ all -> 0x0033 }
            goto L_0x0016
        L_0x0033:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0033 }
            throw r0
        L_0x0036:
            r0 = move-exception
            java.util.Map<T, com.google.android.gms.wearable.internal.zzga<T>> r2 = r4.zzaWU     // Catch:{ all -> 0x0033 }
            r2.remove(r7)     // Catch:{ all -> 0x0033 }
            throw r0     // Catch:{ all -> 0x0033 }
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.wearable.internal.zzdp.zza(com.google.android.gms.wearable.internal.zzfw, com.google.android.gms.internal.zzbaz, java.lang.Object, com.google.android.gms.wearable.internal.zzga):void");
    }

    public final void zzam(IBinder iBinder) {
        zzdn zzdo;
        synchronized (this.zzaWU) {
            if (iBinder == null) {
                zzdo = null;
            } else {
                IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.wearable.internal.IWearableService");
                zzdo = queryLocalInterface instanceof zzdn ? (zzdn) queryLocalInterface : new zzdo(iBinder);
            }
            zzfp zzfp = new zzfp();
            for (Map.Entry next : this.zzaWU.entrySet()) {
                zzga zzga = (zzga) next.getValue();
                try {
                    zzdo.zza((zzdi) zzfp, new zzd(zzga));
                    if (Log.isLoggable("WearableClient", 2)) {
                        String valueOf = String.valueOf(next.getKey());
                        String valueOf2 = String.valueOf(zzga);
                        Log.d("WearableClient", new StringBuilder(String.valueOf(valueOf).length() + 27 + String.valueOf(valueOf2).length()).append("onPostInitHandler: added: ").append(valueOf).append(Constants.URL_PATH_DELIMITER).append(valueOf2).toString());
                    }
                } catch (RemoteException e) {
                    String valueOf3 = String.valueOf(next.getKey());
                    String valueOf4 = String.valueOf(zzga);
                    Log.d("WearableClient", new StringBuilder(String.valueOf(valueOf3).length() + 32 + String.valueOf(valueOf4).length()).append("onPostInitHandler: Didn't add: ").append(valueOf3).append(Constants.URL_PATH_DELIMITER).append(valueOf4).toString());
                }
            }
        }
    }
}
