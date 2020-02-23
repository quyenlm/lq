package com.google.android.gms.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.zzn;
import com.google.android.gms.dynamic.zzp;
import com.google.android.gms.dynamic.zzq;

@zzzn
public final class zzex extends zzp<zzfb> {
    private static final zzex zzsm = new zzex();

    private zzex() {
        super("com.google.android.gms.ads.adshield.AdShieldCreatorImpl");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x000b, code lost:
        r0 = zzsm.zzc(r2, r3, false);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.google.android.gms.internal.zzey zzb(java.lang.String r2, android.content.Context r3, boolean r4) {
        /*
            r1 = 0
            com.google.android.gms.common.zze r0 = com.google.android.gms.common.zze.zzoW()
            int r0 = r0.isGooglePlayServicesAvailable(r3)
            if (r0 != 0) goto L_0x0013
            com.google.android.gms.internal.zzex r0 = zzsm
            com.google.android.gms.internal.zzey r0 = r0.zzc(r2, r3, r1)
            if (r0 != 0) goto L_0x0018
        L_0x0013:
            com.google.android.gms.internal.zzew r0 = new com.google.android.gms.internal.zzew
            r0.<init>(r2, r3, r1)
        L_0x0018:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzex.zzb(java.lang.String, android.content.Context, boolean):com.google.android.gms.internal.zzey");
    }

    private final zzey zzc(String str, Context context, boolean z) {
        IBinder zzb;
        IObjectWrapper zzw = zzn.zzw(context);
        if (z) {
            try {
                zzb = ((zzfb) zzaS(context)).zza(str, zzw);
            } catch (RemoteException | zzq e) {
                return null;
            }
        } else {
            zzb = ((zzfb) zzaS(context)).zzb(str, zzw);
        }
        if (zzb == null) {
            return null;
        }
        IInterface queryLocalInterface = zzb.queryLocalInterface("com.google.android.gms.ads.adshield.internal.IAdShieldClient");
        return queryLocalInterface instanceof zzey ? (zzey) queryLocalInterface : new zzfa(zzb);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Object zzb(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.ads.adshield.internal.IAdShieldCreator");
        return queryLocalInterface instanceof zzfb ? (zzfb) queryLocalInterface : new zzfc(iBinder);
    }
}
