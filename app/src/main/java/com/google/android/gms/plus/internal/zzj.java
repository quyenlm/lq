package com.google.android.gms.plus.internal;

import android.app.PendingIntent;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.internal.zzbaz;
import com.google.android.gms.plus.People;

final class zzj extends zza {
    private final zzbaz<People.LoadPeopleResult> zzbiT;

    public zzj(zzbaz<People.LoadPeopleResult> zzbaz) {
        this.zzbiT = zzbaz;
    }

    public final void zza(DataHolder dataHolder, String str) {
        Status status = new Status(dataHolder.getStatusCode(), (String) null, dataHolder.zzqN() != null ? (PendingIntent) dataHolder.zzqN().getParcelable("pendingIntent") : null);
        if (!status.isSuccess() && dataHolder != null) {
            if (!dataHolder.isClosed()) {
                dataHolder.close();
            }
            dataHolder = null;
        }
        this.zzbiT.setResult(new zzi(status, dataHolder, str));
    }
}
