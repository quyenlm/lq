package com.google.firebase.database;

import android.support.annotation.Nullable;
import com.google.android.gms.internal.qr;
import com.google.android.gms.internal.sh;
import com.google.android.gms.internal.tn;
import com.google.android.gms.internal.wp;
import com.google.android.gms.internal.xd;
import com.google.android.gms.internal.xf;
import com.google.android.gms.internal.xm;
import com.google.android.gms.internal.xp;
import com.google.android.gms.internal.xs;
import com.google.android.gms.internal.zf;
import com.google.android.gms.internal.zg;

public class MutableData {
    /* access modifiers changed from: private */
    public final sh zzbZb;
    /* access modifiers changed from: private */
    public final qr zzbZc;

    private MutableData(sh shVar, qr qrVar) {
        this.zzbZb = shVar;
        this.zzbZc = qrVar;
        tn.zza(this.zzbZc, getValue());
    }

    /* synthetic */ MutableData(sh shVar, qr qrVar, zzi zzi) {
        this(shVar, qrVar);
    }

    MutableData(xm xmVar) {
        this(new sh(xmVar), new qr(""));
    }

    public MutableData child(String str) {
        zf.zzhb(str);
        return new MutableData(this.zzbZb, this.zzbZc.zzh(new qr(str)));
    }

    public boolean equals(Object obj) {
        return (obj instanceof MutableData) && this.zzbZb.equals(((MutableData) obj).zzbZb) && this.zzbZc.equals(((MutableData) obj).zzbZc);
    }

    public Iterable<MutableData> getChildren() {
        xm zzFn = zzFn();
        return (zzFn.isEmpty() || zzFn.zzIQ()) ? new zzi(this) : new zzk(this, xf.zzj(zzFn).iterator());
    }

    public long getChildrenCount() {
        return (long) zzFn().getChildCount();
    }

    public String getKey() {
        if (this.zzbZc.zzHf() != null) {
            return this.zzbZc.zzHf().asString();
        }
        return null;
    }

    public Object getPriority() {
        return zzFn().zzIR().getValue();
    }

    @Nullable
    public Object getValue() {
        return zzFn().getValue();
    }

    @Nullable
    public <T> T getValue(GenericTypeIndicator<T> genericTypeIndicator) {
        return zg.zza(zzFn().getValue(), genericTypeIndicator);
    }

    @Nullable
    public <T> T getValue(Class<T> cls) {
        return zg.zza(zzFn().getValue(), cls);
    }

    public boolean hasChild(String str) {
        return !zzFn().zzN(new qr(str)).isEmpty();
    }

    public boolean hasChildren() {
        xm zzFn = zzFn();
        return !zzFn.zzIQ() && !zzFn.isEmpty();
    }

    public void setPriority(Object obj) {
        this.zzbZb.zzg(this.zzbZc, zzFn().zzf(xs.zzc(this.zzbZc, obj)));
    }

    public void setValue(Object obj) throws DatabaseException {
        tn.zza(this.zzbZc, obj);
        Object zzan = zg.zzan(obj);
        zf.zzam(zzan);
        this.zzbZb.zzg(this.zzbZc, xp.zza(zzan, xd.zzJb()));
    }

    public String toString() {
        wp zzHc = this.zzbZc.zzHc();
        String asString = zzHc != null ? zzHc.asString() : "<none>";
        String valueOf = String.valueOf(this.zzbZb.zzHm().getValue(true));
        return new StringBuilder(String.valueOf(asString).length() + 32 + String.valueOf(valueOf).length()).append("MutableData { key = ").append(asString).append(", value = ").append(valueOf).append(" }").toString();
    }

    /* access modifiers changed from: package-private */
    public final xm zzFn() {
        return this.zzbZb.zzp(this.zzbZc);
    }
}
