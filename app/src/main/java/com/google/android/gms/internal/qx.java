package com.google.android.gms.internal;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

final class qx implements pf {
    private /* synthetic */ qr zzccH;
    private /* synthetic */ qu zzcdA;
    private /* synthetic */ DatabaseReference.CompletionListener zzcdD;

    qx(qu quVar, qr qrVar, DatabaseReference.CompletionListener completionListener) {
        this.zzcdA = quVar;
        this.zzccH = qrVar;
        this.zzcdD = completionListener;
    }

    public final void zzaa(String str, String str2) {
        DatabaseError zzac = qu.zzab(str, str2);
        if (zzac == null) {
            this.zzcdA.zzcdl.zzq(this.zzccH);
        }
        this.zzcdA.zza(this.zzcdD, zzac, this.zzccH);
    }
}
