package com.google.android.gms.internal;

import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.zzbo;
import java.util.Iterator;
import java.util.LinkedList;

@zzzn
final class zztn {
    private final LinkedList<zzto> zzKr = new LinkedList<>();
    /* access modifiers changed from: private */
    public zzir zzKs;
    private final int zzKt;
    private boolean zzKu;
    /* access modifiers changed from: private */
    public final String zztV;

    zztn(zzir zzir, String str, int i) {
        zzbo.zzu(zzir);
        zzbo.zzu(str);
        this.zzKs = zzir;
        this.zztV = str;
        this.zzKt = i;
    }

    /* access modifiers changed from: package-private */
    public final String getAdUnitId() {
        return this.zztV;
    }

    /* access modifiers changed from: package-private */
    public final int getNetworkType() {
        return this.zzKt;
    }

    /* access modifiers changed from: package-private */
    public final int size() {
        return this.zzKr.size();
    }

    /* access modifiers changed from: package-private */
    public final void zza(zzsi zzsi, zzir zzir) {
        this.zzKr.add(new zzto(this, zzsi, zzir));
    }

    /* access modifiers changed from: package-private */
    public final boolean zzb(zzsi zzsi) {
        zzto zzto = new zzto(this, zzsi);
        this.zzKr.add(zzto);
        return zzto.load();
    }

    /* access modifiers changed from: package-private */
    public final zzir zzeI() {
        return this.zzKs;
    }

    /* access modifiers changed from: package-private */
    public final int zzeJ() {
        int i = 0;
        Iterator it = this.zzKr.iterator();
        while (true) {
            int i2 = i;
            if (!it.hasNext()) {
                return i2;
            }
            i = ((zzto) it.next()).zzKz ? i2 + 1 : i2;
        }
    }

    /* access modifiers changed from: package-private */
    public final int zzeK() {
        int i = 0;
        Iterator it = this.zzKr.iterator();
        while (true) {
            int i2 = i;
            if (!it.hasNext()) {
                return i2;
            }
            i = ((zzto) it.next()).load() ? i2 + 1 : i2;
        }
    }

    /* access modifiers changed from: package-private */
    public final void zzeL() {
        this.zzKu = true;
    }

    /* access modifiers changed from: package-private */
    public final boolean zzeM() {
        return this.zzKu;
    }

    /* access modifiers changed from: package-private */
    public final zzto zzm(@Nullable zzir zzir) {
        if (zzir != null) {
            this.zzKs = zzir;
        }
        return this.zzKr.remove();
    }
}
