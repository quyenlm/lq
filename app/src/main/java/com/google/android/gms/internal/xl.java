package com.google.android.gms.internal;

public final class xl {
    private static final xl zzciu = new xl(wp.zzIJ(), xd.zzJb());
    private static final xl zzciv = new xl(wp.zzIK(), xm.zzciw);
    private final wp zzcgy;
    private final xm zzcil;

    public xl(wp wpVar, xm xmVar) {
        this.zzcgy = wpVar;
        this.zzcil = xmVar;
    }

    public static xl zzJi() {
        return zzciu;
    }

    public static xl zzJj() {
        return zzciv;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        xl xlVar = (xl) obj;
        if (!this.zzcgy.equals(xlVar.zzcgy)) {
            return false;
        }
        return this.zzcil.equals(xlVar.zzcil);
    }

    public final int hashCode() {
        return (this.zzcgy.hashCode() * 31) + this.zzcil.hashCode();
    }

    public final String toString() {
        String valueOf = String.valueOf(this.zzcgy);
        String valueOf2 = String.valueOf(this.zzcil);
        return new StringBuilder(String.valueOf(valueOf).length() + 23 + String.valueOf(valueOf2).length()).append("NamedNode{name=").append(valueOf).append(", node=").append(valueOf2).append("}").toString();
    }

    public final xm zzFn() {
        return this.zzcil;
    }

    public final wp zzJk() {
        return this.zzcgy;
    }
}
