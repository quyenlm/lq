package com.google.android.gms.internal;

public final class th {
    private final long zzcfg;

    public th(long j) {
        this.zzcfg = j;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.zzcfg == ((th) obj).zzcfg;
    }

    public final int hashCode() {
        return (int) (this.zzcfg ^ (this.zzcfg >>> 32));
    }

    public final String toString() {
        return new StringBuilder(35).append("Tag{tagNumber=").append(this.zzcfg).append("}").toString();
    }

    public final long zzHs() {
        return this.zzcfg;
    }
}
