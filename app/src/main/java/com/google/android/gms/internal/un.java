package com.google.android.gms.internal;

public final class un {
    public final boolean complete;
    public final long id;
    public final boolean zzbpf;
    public final vt zzcgi;
    public final long zzcgj;

    public un(long j, vt vtVar, long j2, boolean z, boolean z2) {
        this.id = j;
        if (!vtVar.zzIq() || vtVar.isDefault()) {
            this.zzcgi = vtVar;
            this.zzcgj = j2;
            this.complete = z;
            this.zzbpf = z2;
            return;
        }
        throw new IllegalArgumentException("Can't create TrackedQuery for a non-default query that loads all data");
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        un unVar = (un) obj;
        return this.id == unVar.id && this.zzcgi.equals(unVar.zzcgi) && this.zzcgj == unVar.zzcgj && this.complete == unVar.complete && this.zzbpf == unVar.zzbpf;
    }

    public final int hashCode() {
        return (((((((Long.valueOf(this.id).hashCode() * 31) + this.zzcgi.hashCode()) * 31) + Long.valueOf(this.zzcgj).hashCode()) * 31) + Boolean.valueOf(this.complete).hashCode()) * 31) + Boolean.valueOf(this.zzbpf).hashCode();
    }

    public final String toString() {
        long j = this.id;
        String valueOf = String.valueOf(this.zzcgi);
        long j2 = this.zzcgj;
        boolean z = this.complete;
        return new StringBuilder(String.valueOf(valueOf).length() + 109).append("TrackedQuery{id=").append(j).append(", querySpec=").append(valueOf).append(", lastUse=").append(j2).append(", complete=").append(z).append(", active=").append(this.zzbpf).append("}").toString();
    }

    public final un zzHO() {
        return new un(this.id, this.zzcgi, this.zzcgj, true, this.zzbpf);
    }
}
