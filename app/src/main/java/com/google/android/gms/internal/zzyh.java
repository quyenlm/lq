package com.google.android.gms.internal;

import android.content.Context;
import android.support.annotation.Nullable;
import com.google.android.gms.ads.internal.js.JavascriptEngineFactory;
import com.google.android.gms.ads.internal.js.zza;
import com.google.android.gms.ads.internal.js.zzah;
import com.google.android.gms.ads.internal.js.zzl;
import com.google.android.gms.ads.internal.js.zzx;
import com.google.android.gms.ads.internal.zzbb;
import com.google.android.gms.ads.internal.zzbs;
import com.google.android.gms.ads.internal.zzw;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@zzzn
public final class zzyh {
    private static final long zzRj = TimeUnit.SECONDS.toMillis(60);
    private static boolean zzRk = false;
    private static zzl zzRl = null;
    private static final Object zzuF = new Object();
    private final Context mContext;
    private final Object mLock;
    private final zzcu zzIc;
    /* access modifiers changed from: private */
    public final zzbb zzRm;
    private JavascriptEngineFactory zzRn;
    private zzah zzRo;
    private zzajm<zza> zzRp;
    private boolean zzRq;
    private boolean zzRr;
    private final zzaje zzuK;

    public zzyh(Context context, zzbb zzbb, zzcu zzcu, zzaje zzaje) {
        this.mLock = new Object();
        this.zzRq = false;
        this.zzRr = false;
        this.mContext = context;
        this.zzRm = zzbb;
        this.zzIc = zzcu;
        this.zzuK = zzaje;
        this.zzRq = ((Boolean) zzbs.zzbL().zzd(zzmo.zzFt)).booleanValue();
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public zzyh(Context context, zzafg zzafg, zzbb zzbb, zzcu zzcu) {
        this(context, zzbb, zzcu, (zzafg == null || zzafg.zzUj == null) ? null : zzafg.zzUj.zzvT);
    }

    @Nullable
    private final zza zzgu() throws CancellationException, ExecutionException, InterruptedException, TimeoutException {
        zza zza = null;
        if (this.zzRp != null) {
            zza = (zza) this.zzRp.get(zzRj, TimeUnit.MILLISECONDS);
            synchronized (this.mLock) {
                if (!this.zzRr) {
                    zza.zza(this.zzRm, this.zzRm, this.zzRm, this.zzRm, false, (zzrm) null, (zzw) null, (zzwv) null);
                    this.zzRr = true;
                }
            }
        }
        return zza;
    }

    public final void zza(zzym zzym) {
        if (this.zzRq) {
            zzah zzah = this.zzRo;
            if (zzah == null) {
                zzafr.zzaT("SharedJavascriptEngine not initialized");
            } else {
                zzah.zza(new zzyi(this, zzym), new zzyj(this, zzym));
            }
        } else {
            try {
                zza zzgu = zzgu();
                if (zzgu == null) {
                    zzafr.zzaT("JavascriptEngine not initialized");
                } else {
                    zzym.zzd(zzgu);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                zzafr.zzc("Exception occurred during execution", e);
            } catch (CancellationException | ExecutionException | TimeoutException e2) {
                zzafr.zzc("Exception occurred during execution", e2);
            }
        }
    }

    public final void zzfd() {
        if (!this.zzRq) {
            try {
                zza zzgu = zzgu();
                if (zzgu != null) {
                    zzbs.zzbz();
                    zzagz.runOnUiThread(new zzyl(this, zzgu));
                }
            } catch (InterruptedException | CancellationException | ExecutionException | TimeoutException e) {
                zzafr.zzc("Exception occurred while destroying engine", e);
            }
        }
    }

    public final void zzgs() {
        if (this.zzRq) {
            synchronized (zzuF) {
                if (!zzRk) {
                    zzRl = new zzl(this.mContext.getApplicationContext() != null ? this.mContext.getApplicationContext() : this.mContext, this.zzuK, (String) zzbs.zzbL().zzd(zzmo.zzFq), new zzyk(this), new zzx());
                    zzRk = true;
                }
            }
            return;
        }
        this.zzRn = new JavascriptEngineFactory();
    }

    public final void zzgt() throws zzakm {
        if (this.zzRq) {
            this.zzRo = new zzah(zzRl.zzb(this.zzIc));
            return;
        }
        this.zzRp = this.zzRn.zza(this.mContext, this.zzuK, (String) zzbs.zzbL().zzd(zzmo.zzFq), this.zzIc, this.zzRm.zzak());
    }
}
