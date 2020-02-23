package com.google.android.gms.internal;

public final class wo extends xh<wo> {
    private final boolean value;

    public wo(Boolean bool, xm xmVar) {
        super(xmVar);
        this.value = bool.booleanValue();
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof wo)) {
            return false;
        }
        wo woVar = (wo) obj;
        return this.value == woVar.value && this.zzchS.equals(woVar.zzchS);
    }

    public final Object getValue() {
        return Boolean.valueOf(this.value);
    }

    public final int hashCode() {
        return (this.value ? 1 : 0) + this.zzchS.hashCode();
    }

    /* access modifiers changed from: protected */
    public final xj zzII() {
        return xj.Boolean;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ int zza(xh xhVar) {
        if (this.value == ((wo) xhVar).value) {
            return 0;
        }
        return this.value ? 1 : -1;
    }

    public final String zza(xo xoVar) {
        String valueOf = String.valueOf(zzb(xoVar));
        return new StringBuilder(String.valueOf(valueOf).length() + 13).append(valueOf).append("boolean:").append(this.value).toString();
    }

    public final /* synthetic */ xm zzf(xm xmVar) {
        return new wo(Boolean.valueOf(this.value), xmVar);
    }
}
