package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.common.internal.zzq;
import com.google.android.gms.common.internal.zzz;
import com.google.android.gms.dynamite.DynamiteModule;

public final class jy extends zzz<kc> implements jx {
    private static zzbgb zzakn = new zzbgb("FirebaseAuth", "FirebaseAuth:");
    private final Context mContext;
    private final kg zzbWF;

    public jy(Context context, Looper looper, zzq zzq, kg kgVar, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 112, zzq, connectionCallbacks, onConnectionFailedListener);
        this.mContext = (Context) zzbo.zzu(context);
        this.zzbWF = kgVar;
    }

    public final /* synthetic */ kc zzEM() throws DeadObjectException {
        return (kc) super.zzrf();
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ IInterface zzd(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.firebase.auth.api.internal.IFirebaseAuthService");
        return queryLocalInterface instanceof kc ? (kc) queryLocalInterface : new kd(iBinder);
    }

    /* access modifiers changed from: protected */
    public final String zzdb() {
        return "com.google.firebase.auth.api.gms.service.START";
    }

    /* access modifiers changed from: protected */
    public final String zzdc() {
        return "com.google.firebase.auth.api.internal.IFirebaseAuthService";
    }

    /* access modifiers changed from: protected */
    public final Bundle zzmo() {
        Bundle zzmo = super.zzmo();
        if (zzmo == null) {
            zzmo = new Bundle();
        }
        if (this.zzbWF != null) {
            zzmo.putString("com.google.firebase.auth.API_KEY", this.zzbWF.getApiKey());
        }
        return zzmo;
    }

    public final boolean zzpe() {
        return DynamiteModule.zzE(this.mContext, "com.google.firebase.auth") == 0;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.String zzqZ() {
        /*
            r4 = this;
            r2 = -1
            r1 = 0
            java.lang.String r0 = "firebear.preference"
            java.lang.String r0 = com.google.android.gms.internal.ks.getProperty(r0)
            boolean r3 = android.text.TextUtils.isEmpty(r0)
            if (r3 == 0) goto L_0x0010
            java.lang.String r0 = "default"
        L_0x0010:
            int r3 = r0.hashCode()
            switch(r3) {
                case 103145323: goto L_0x004f;
                case 1544803905: goto L_0x0059;
                default: goto L_0x0017;
            }
        L_0x0017:
            r3 = r2
        L_0x0018:
            switch(r3) {
                case 0: goto L_0x001d;
                case 1: goto L_0x001d;
                default: goto L_0x001b;
            }
        L_0x001b:
            java.lang.String r0 = "default"
        L_0x001d:
            int r3 = r0.hashCode()
            switch(r3) {
                case 103145323: goto L_0x0063;
                default: goto L_0x0024;
            }
        L_0x0024:
            r0 = r2
        L_0x0025:
            switch(r0) {
                case 0: goto L_0x006d;
                default: goto L_0x0028;
            }
        L_0x0028:
            com.google.android.gms.internal.zzbgb r0 = zzakn
            java.lang.String r2 = "Loading module via default loading order."
            java.lang.Object[] r3 = new java.lang.Object[r1]
            r0.zza(r2, r3)
            android.content.Context r0 = r4.mContext
            java.lang.String r2 = "com.google.firebase.auth"
            int r0 = com.google.android.gms.dynamite.DynamiteModule.zzE(r0, r2)
            android.content.Context r2 = r4.mContext
            java.lang.String r3 = "com.google.android.gms.firebase_auth"
            int r2 = com.google.android.gms.dynamite.DynamiteModule.zzF(r2, r3)
            if (r2 < r0) goto L_0x007d
            com.google.android.gms.internal.zzbgb r0 = zzakn
            java.lang.String r2 = "Loading remote module."
            java.lang.Object[] r1 = new java.lang.Object[r1]
            r0.zza(r2, r1)
            java.lang.String r0 = "com.google.android.gms"
        L_0x004e:
            return r0
        L_0x004f:
            java.lang.String r3 = "local"
            boolean r3 = r0.equals(r3)
            if (r3 == 0) goto L_0x0017
            r3 = r1
            goto L_0x0018
        L_0x0059:
            java.lang.String r3 = "default"
            boolean r3 = r0.equals(r3)
            if (r3 == 0) goto L_0x0017
            r3 = 1
            goto L_0x0018
        L_0x0063:
            java.lang.String r3 = "local"
            boolean r0 = r0.equals(r3)
            if (r0 == 0) goto L_0x0024
            r0 = r1
            goto L_0x0025
        L_0x006d:
            com.google.android.gms.internal.zzbgb r0 = zzakn
            java.lang.String r2 = "Loading fallback module override."
            java.lang.Object[] r1 = new java.lang.Object[r1]
            r0.zza(r2, r1)
            android.content.Context r0 = r4.mContext
            java.lang.String r0 = r0.getPackageName()
            goto L_0x004e
        L_0x007d:
            com.google.android.gms.internal.zzbgb r0 = zzakn
            java.lang.String r2 = "Loading fallback module."
            java.lang.Object[] r1 = new java.lang.Object[r1]
            r0.zza(r2, r1)
            android.content.Context r0 = r4.mContext
            java.lang.String r0 = r0.getPackageName()
            goto L_0x004e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.jy.zzqZ():java.lang.String");
    }
}
