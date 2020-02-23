package com.google.android.gms.internal;

import com.google.firebase.database.DataSnapshot;

public final class vj implements vk {
    private final qi zzcgG;
    private final vl zzcgI;
    private final DataSnapshot zzcgM;
    private final String zzcgN;

    public vj(vl vlVar, qi qiVar, DataSnapshot dataSnapshot, String str) {
        this.zzcgI = vlVar;
        this.zzcgG = qiVar;
        this.zzcgM = dataSnapshot;
        this.zzcgN = str;
    }

    private final qr zzFq() {
        qr zzFq = this.zzcgM.getRef().zzFq();
        return this.zzcgI == vl.VALUE ? zzFq : zzFq.zzHe();
    }

    public final String toString() {
        if (this.zzcgI == vl.VALUE) {
            String valueOf = String.valueOf(zzFq());
            String valueOf2 = String.valueOf(this.zzcgI);
            String valueOf3 = String.valueOf(this.zzcgM.getValue(true));
            return new StringBuilder(String.valueOf(valueOf).length() + 4 + String.valueOf(valueOf2).length() + String.valueOf(valueOf3).length()).append(valueOf).append(": ").append(valueOf2).append(": ").append(valueOf3).toString();
        }
        String valueOf4 = String.valueOf(zzFq());
        String valueOf5 = String.valueOf(this.zzcgI);
        String valueOf6 = String.valueOf(this.zzcgM.getKey());
        String valueOf7 = String.valueOf(this.zzcgM.getValue(true));
        return new StringBuilder(String.valueOf(valueOf4).length() + 10 + String.valueOf(valueOf5).length() + String.valueOf(valueOf6).length() + String.valueOf(valueOf7).length()).append(valueOf4).append(": ").append(valueOf5).append(": { ").append(valueOf6).append(": ").append(valueOf7).append(" }").toString();
    }

    public final void zzHX() {
        this.zzcgG.zza(this);
    }

    public final vl zzHZ() {
        return this.zzcgI;
    }

    public final DataSnapshot zzIc() {
        return this.zzcgM;
    }

    public final String zzId() {
        return this.zzcgN;
    }
}
