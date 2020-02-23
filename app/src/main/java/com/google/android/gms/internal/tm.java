package com.google.android.gms.internal;

public final class tm {
    private final qr zzbZf;
    private final long zzcfl;
    private final xm zzcfm;
    private final pz zzcfn;
    private final boolean zzcfo;

    public tm(long j, qr qrVar, pz pzVar) {
        this.zzcfl = j;
        this.zzbZf = qrVar;
        this.zzcfm = null;
        this.zzcfn = pzVar;
        this.zzcfo = true;
    }

    public tm(long j, qr qrVar, xm xmVar, boolean z) {
        this.zzcfl = j;
        this.zzbZf = qrVar;
        this.zzcfm = xmVar;
        this.zzcfn = null;
        this.zzcfo = z;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        tm tmVar = (tm) obj;
        if (this.zzcfl != tmVar.zzcfl) {
            return false;
        }
        if (!this.zzbZf.equals(tmVar.zzbZf)) {
            return false;
        }
        if (this.zzcfo != tmVar.zzcfo) {
            return false;
        }
        if (this.zzcfm == null ? tmVar.zzcfm != null : !this.zzcfm.equals(tmVar.zzcfm)) {
            return false;
        }
        if (this.zzcfn != null) {
            if (this.zzcfn.equals(tmVar.zzcfn)) {
                return true;
            }
        } else if (tmVar.zzcfn == null) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((this.zzcfm != null ? this.zzcfm.hashCode() : 0) + (((((Long.valueOf(this.zzcfl).hashCode() * 31) + Boolean.valueOf(this.zzcfo).hashCode()) * 31) + this.zzbZf.hashCode()) * 31)) * 31;
        if (this.zzcfn != null) {
            i = this.zzcfn.hashCode();
        }
        return hashCode + i;
    }

    public final boolean isVisible() {
        return this.zzcfo;
    }

    public final String toString() {
        long j = this.zzcfl;
        String valueOf = String.valueOf(this.zzbZf);
        boolean z = this.zzcfo;
        String valueOf2 = String.valueOf(this.zzcfm);
        String valueOf3 = String.valueOf(this.zzcfn);
        return new StringBuilder(String.valueOf(valueOf).length() + 78 + String.valueOf(valueOf2).length() + String.valueOf(valueOf3).length()).append("UserWriteRecord{id=").append(j).append(" path=").append(valueOf).append(" visible=").append(z).append(" overwrite=").append(valueOf2).append(" merge=").append(valueOf3).append("}").toString();
    }

    public final qr zzFq() {
        return this.zzbZf;
    }

    public final long zzHt() {
        return this.zzcfl;
    }

    public final xm zzHu() {
        if (this.zzcfm != null) {
            return this.zzcfm;
        }
        throw new IllegalArgumentException("Can't access overwrite when write is a merge!");
    }

    public final pz zzHv() {
        if (this.zzcfn != null) {
            return this.zzcfn;
        }
        throw new IllegalArgumentException("Can't access merge when write is an overwrite!");
    }

    public final boolean zzHw() {
        return this.zzcfm != null;
    }
}
