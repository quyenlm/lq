package com.google.android.gms.internal;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import android.text.TextUtils;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.zzq;
import com.google.android.gms.common.internal.zzz;
import java.util.List;

public final class zzcsn extends zzz<zzcry> {
    private final Context mContext;

    public zzcsn(Context context, Looper looper, zzq zzq, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 45, zzq, connectionCallbacks, onConnectionFailedListener);
        this.mContext = context;
    }

    private final String zzeH(String str) {
        try {
            PackageManager packageManager = this.mContext.getPackageManager();
            if (packageManager == null) {
                return null;
            }
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(this.mContext.getPackageName(), 128);
            if (applicationInfo == null) {
                return null;
            }
            Bundle bundle = applicationInfo.metaData;
            if (bundle == null) {
                return null;
            }
            return (String) bundle.get(str);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    public final void zza(zzcrw zzcrw, List<Integer> list, int i, String str, String str2) throws RemoteException {
        String zzeH = str2 == null ? zzeH("com.google.android.safetynet.API_KEY") : str2;
        int[] iArr = new int[list.size()];
        int i2 = 0;
        while (true) {
            int i3 = i2;
            if (i3 < list.size()) {
                iArr[i3] = list.get(i3).intValue();
                i2 = i3 + 1;
            } else {
                ((zzcry) zzrf()).zza(zzcrw, zzeH, iArr, i, str);
                return;
            }
        }
    }

    public final void zzb(zzcrw zzcrw, byte[] bArr, String str) throws RemoteException {
        if (TextUtils.isEmpty(str)) {
            str = zzeH("com.google.android.safetynet.ATTEST_API_KEY");
        }
        ((zzcry) zzrf()).zza(zzcrw, bArr, str);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ IInterface zzd(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.safetynet.internal.ISafetyNetService");
        return queryLocalInterface instanceof zzcry ? (zzcry) queryLocalInterface : new zzcrz(iBinder);
    }

    /* access modifiers changed from: protected */
    public final String zzdb() {
        return "com.google.android.gms.safetynet.service.START";
    }

    /* access modifiers changed from: protected */
    public final String zzdc() {
        return "com.google.android.gms.safetynet.internal.ISafetyNetService";
    }
}
