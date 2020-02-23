package com.google.android.gms.ads.internal;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import com.google.android.gms.internal.zzafr;
import com.google.android.gms.internal.zzagt;
import com.google.android.gms.internal.zzaiy;
import com.google.android.gms.internal.zzcp;
import com.google.android.gms.internal.zzct;
import com.google.android.gms.internal.zzji;
import com.google.android.gms.internal.zzmo;
import com.google.android.gms.internal.zzzn;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

@zzzn
final class zzah implements zzcp, Runnable {
    private zzbt zzsP;
    private final List<Object[]> zztH = new Vector();
    private final AtomicReference<zzcp> zztI = new AtomicReference<>();
    private CountDownLatch zztJ = new CountDownLatch(1);

    public zzah(zzbt zzbt) {
        this.zzsP = zzbt;
        zzji.zzds();
        if (zzaiy.zzil()) {
            zzagt.zza((Runnable) this);
        } else {
            run();
        }
    }

    private final boolean zzaU() {
        try {
            this.zztJ.await();
            return true;
        } catch (InterruptedException e) {
            zzafr.zzc("Interrupted during GADSignals creation.", e);
            return false;
        }
    }

    private final void zzaV() {
        if (!this.zztH.isEmpty()) {
            for (Object[] next : this.zztH) {
                if (next.length == 1) {
                    this.zztI.get().zza((MotionEvent) next[0]);
                } else if (next.length == 3) {
                    this.zztI.get().zza(((Integer) next[0]).intValue(), ((Integer) next[1]).intValue(), ((Integer) next[2]).intValue());
                }
            }
            this.zztH.clear();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0013, code lost:
        r0 = r2.getApplicationContext();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static android.content.Context zze(android.content.Context r2) {
        /*
            com.google.android.gms.internal.zzme<java.lang.Boolean> r0 = com.google.android.gms.internal.zzmo.zzCi
            com.google.android.gms.internal.zzmm r1 = com.google.android.gms.ads.internal.zzbs.zzbL()
            java.lang.Object r0 = r1.zzd(r0)
            java.lang.Boolean r0 = (java.lang.Boolean) r0
            boolean r0 = r0.booleanValue()
            if (r0 != 0) goto L_0x0013
        L_0x0012:
            return r2
        L_0x0013:
            android.content.Context r0 = r2.getApplicationContext()
            if (r0 == 0) goto L_0x0012
            r2 = r0
            goto L_0x0012
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.ads.internal.zzah.zze(android.content.Context):android.content.Context");
    }

    public final void run() {
        try {
            this.zztI.set(zzct.zza(this.zzsP.zzvT.zzaP, zze(this.zzsP.zzqD), !((Boolean) zzbs.zzbL().zzd(zzmo.zzDO)).booleanValue() && (this.zzsP.zzvT.zzaaQ)));
        } finally {
            this.zztJ.countDown();
            this.zzsP = null;
        }
    }

    public final String zza(Context context) {
        zzcp zzcp;
        if (!zzaU() || (zzcp = this.zztI.get()) == null) {
            return "";
        }
        zzaV();
        return zzcp.zza(zze(context));
    }

    public final String zza(Context context, String str, View view) {
        zzcp zzcp;
        if (!zzaU() || (zzcp = this.zztI.get()) == null) {
            return "";
        }
        zzaV();
        return zzcp.zza(zze(context), str, view);
    }

    public final void zza(int i, int i2, int i3) {
        zzcp zzcp = this.zztI.get();
        if (zzcp != null) {
            zzaV();
            zzcp.zza(i, i2, i3);
            return;
        }
        this.zztH.add(new Object[]{Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3)});
    }

    public final void zza(MotionEvent motionEvent) {
        zzcp zzcp = this.zztI.get();
        if (zzcp != null) {
            zzaV();
            zzcp.zza(motionEvent);
            return;
        }
        this.zztH.add(new Object[]{motionEvent});
    }
}
