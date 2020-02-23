package com.google.android.gms.internal;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

final class rt implements pf {
    private /* synthetic */ qr zzccH;
    private /* synthetic */ qu zzcdA;
    private /* synthetic */ DatabaseReference.CompletionListener zzcdD;
    private /* synthetic */ long zzcdX;

    rt(qu quVar, qr qrVar, long j, DatabaseReference.CompletionListener completionListener) {
        this.zzcdA = quVar;
        this.zzccH = qrVar;
        this.zzcdX = j;
        this.zzcdD = completionListener;
    }

    public final void zzaa(String str, String str2) {
        DatabaseError zzac = qu.zzab(str, str2);
        this.zzcdA.zza("updateChildren", this.zzccH, zzac);
        this.zzcdA.zza(this.zzcdX, this.zzccH, zzac);
        this.zzcdA.zza(this.zzcdD, zzac, this.zzccH);
    }
}
