package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.internal.zzcsa;
import java.util.ArrayList;

final class zzcsd extends zzcsa.zzf {
    private /* synthetic */ String zzbBP;
    private /* synthetic */ String zzbBR;
    private /* synthetic */ int[] zzbBS;
    private /* synthetic */ int zzbBT;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzcsd(GoogleApiClient googleApiClient, int[] iArr, int i, String str, String str2) {
        super(googleApiClient);
        this.zzbBS = iArr;
        this.zzbBT = i;
        this.zzbBR = str;
        this.zzbBP = str2;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        zzcsn zzcsn = (zzcsn) zzb;
        ArrayList arrayList = new ArrayList();
        for (int valueOf : this.zzbBS) {
            arrayList.add(Integer.valueOf(valueOf));
        }
        zzcsn.zza(this.zzbBW, arrayList, this.zzbBT, this.zzbBR, this.zzbBP);
    }
}
