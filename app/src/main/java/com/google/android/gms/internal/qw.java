package com.google.android.gms.internal;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import java.util.Map;

final class qw implements pf {
    private /* synthetic */ qr zzccH;
    private /* synthetic */ qu zzcdA;
    private /* synthetic */ Map zzcdB;
    private /* synthetic */ DatabaseReference.CompletionListener zzcdC;

    qw(qu quVar, qr qrVar, Map map, DatabaseReference.CompletionListener completionListener) {
        this.zzcdA = quVar;
        this.zzccH = qrVar;
        this.zzcdB = map;
        this.zzcdC = completionListener;
    }

    public final void zzaa(String str, String str2) {
        DatabaseError zzac = qu.zzab(str, str2);
        this.zzcdA.zza("onDisconnect().updateChildren", this.zzccH, zzac);
        if (zzac == null) {
            for (Map.Entry entry : this.zzcdB.entrySet()) {
                this.zzcdA.zzcdl.zzh(this.zzccH.zzh((qr) entry.getKey()), (xm) entry.getValue());
            }
        }
        this.zzcdA.zza(this.zzcdC, zzac, this.zzccH);
    }
}
