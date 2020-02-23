package com.google.android.gms.internal;

public final class xu extends xh<xu> {
    private final String value;

    public xu(String str, xm xmVar) {
        super(xmVar);
        this.value = str;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof xu)) {
            return false;
        }
        xu xuVar = (xu) obj;
        return this.value.equals(xuVar.value) && this.zzchS.equals(xuVar.zzchS);
    }

    public final Object getValue() {
        return this.value;
    }

    public final int hashCode() {
        return this.value.hashCode() + this.zzchS.hashCode();
    }

    /* access modifiers changed from: protected */
    public final xj zzII() {
        return xj.String;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ int zza(xh xhVar) {
        return this.value.compareTo(((xu) xhVar).value);
    }

    public final String zza(xo xoVar) {
        switch (xv.zzcio[xoVar.ordinal()]) {
            case 1:
                String valueOf = String.valueOf(zzb(xoVar));
                String str = this.value;
                return new StringBuilder(String.valueOf(valueOf).length() + 7 + String.valueOf(str).length()).append(valueOf).append("string:").append(str).toString();
            case 2:
                String valueOf2 = String.valueOf(zzb(xoVar));
                String valueOf3 = String.valueOf(zd.zzgZ(this.value));
                return new StringBuilder(String.valueOf(valueOf2).length() + 7 + String.valueOf(valueOf3).length()).append(valueOf2).append("string:").append(valueOf3).toString();
            default:
                String valueOf4 = String.valueOf(xoVar);
                throw new IllegalArgumentException(new StringBuilder(String.valueOf(valueOf4).length() + 38).append("Invalid hash version for string node: ").append(valueOf4).toString());
        }
    }

    public final /* synthetic */ xm zzf(xm xmVar) {
        return new xu(this.value, xmVar);
    }
}
