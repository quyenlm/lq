package com.google.android.gms.internal;

import android.support.annotation.NonNull;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.FirebaseApiNotAvailableException;

final class ms implements OnFailureListener {
    private /* synthetic */ pv zzbZu;

    ms(mr mrVar, pv pvVar) {
        this.zzbZu = pvVar;
    }

    public final void onFailure(@NonNull Exception exc) {
        if ((exc instanceof FirebaseApiNotAvailableException) || (exc instanceof aaf)) {
            this.zzbZu.zzgF((String) null);
        } else {
            this.zzbZu.onError(exc.getMessage());
        }
    }
}
