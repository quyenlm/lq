package com.google.android.gms.internal;

import android.support.annotation.WorkerThread;
import com.google.android.gms.common.api.Api;
import java.util.ArrayList;

final class zzbcj extends zzbcn {
    private /* synthetic */ zzbcd zzaDp;
    private final ArrayList<Api.zze> zzaDv;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public zzbcj(zzbcd zzbcd, ArrayList<Api.zze> arrayList) {
        super(zzbcd, (zzbce) null);
        this.zzaDp = zzbcd;
        this.zzaDv = arrayList;
    }

    @WorkerThread
    public final void zzpV() {
        this.zzaDp.zzaCZ.zzaCl.zzaDG = this.zzaDp.zzqb();
        ArrayList arrayList = this.zzaDv;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((Api.zze) obj).zza(this.zzaDp.zzaDl, this.zzaDp.zzaCZ.zzaCl.zzaDG);
        }
    }
}
