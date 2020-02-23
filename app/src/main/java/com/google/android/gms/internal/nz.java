package com.google.android.gms.internal;

import java.util.Iterator;

final class nz implements Iterator<oa> {
    private int zzcaj = (this.zzcak.length - 1);
    private /* synthetic */ ny zzcak;

    nz(ny nyVar) {
        this.zzcak = nyVar;
    }

    public final boolean hasNext() {
        return this.zzcaj >= 0;
    }

    public final /* synthetic */ Object next() {
        boolean z = true;
        oa oaVar = new oa();
        if ((this.zzcak.value & ((long) (1 << this.zzcaj))) != 0) {
            z = false;
        }
        oaVar.zzcal = z;
        oaVar.zzcam = (int) Math.pow(2.0d, (double) this.zzcaj);
        this.zzcaj--;
        return oaVar;
    }

    public final void remove() {
    }
}
