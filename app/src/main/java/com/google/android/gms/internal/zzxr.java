package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.ads.internal.zzbs;
import com.google.android.gms.common.internal.zzbo;
import java.util.concurrent.atomic.AtomicBoolean;

@zzzn
public abstract class zzxr implements zzahp<Void>, zzakf {
    protected final Context mContext;
    protected final zzaka zzJH;
    private zzxy zzQP;
    private zzafg zzQQ;
    protected zzaai zzQR;
    private Runnable zzQS;
    private Object zzQT = new Object();
    /* access modifiers changed from: private */
    public AtomicBoolean zzQU = new AtomicBoolean(true);

    protected zzxr(Context context, zzafg zzafg, zzaka zzaka, zzxy zzxy) {
        this.mContext = context;
        this.zzQQ = zzafg;
        this.zzQR = this.zzQQ.zzXY;
        this.zzJH = zzaka;
        this.zzQP = zzxy;
    }

    public void cancel() {
        if (this.zzQU.getAndSet(false)) {
            this.zzJH.stopLoading();
            zzbs.zzbB();
            zzahe.zzk(this.zzJH);
            zzr(-1);
            zzagz.zzZr.removeCallbacks(this.zzQS);
        }
    }

    public final void zza(zzaka zzaka, boolean z) {
        int i = 0;
        zzafr.zzaC("WebView finished loading.");
        if (this.zzQU.getAndSet(false)) {
            if (z) {
                i = -2;
            }
            zzr(i);
            zzagz.zzZr.removeCallbacks(this.zzQS);
        }
    }

    /* access modifiers changed from: protected */
    public abstract void zzgo();

    public final /* synthetic */ Object zzgp() {
        zzbo.zzcz("Webview render task needs to be called on UI thread.");
        this.zzQS = new zzxs(this);
        zzagz.zzZr.postDelayed(this.zzQS, ((Long) zzbs.zzbL().zzd(zzmo.zzEL)).longValue());
        zzgo();
        return null;
    }

    /* access modifiers changed from: protected */
    public void zzr(int i) {
        if (i != -2) {
            this.zzQR = new zzaai(i, this.zzQR.zzMg);
        }
        this.zzJH.zzir();
        zzxy zzxy = this.zzQP;
        zzaae zzaae = this.zzQQ.zzUj;
        zzxy zzxy2 = zzxy;
        zzxy2.zzb(new zzaff(zzaae.zzSz, this.zzJH, this.zzQR.zzMa, i, this.zzQR.zzMb, this.zzQR.zzTq, this.zzQR.orientation, this.zzQR.zzMg, zzaae.zzSC, this.zzQR.zzTo, (zzua) null, (zzut) null, (String) null, (zzub) null, (zzud) null, this.zzQR.zzTp, this.zzQQ.zzvX, this.zzQR.zzTn, this.zzQQ.zzXR, this.zzQR.zzTs, this.zzQR.zzTt, this.zzQQ.zzXL, (zzoa) null, this.zzQR.zzTD, this.zzQR.zzTE, this.zzQR.zzTF, this.zzQR.zzTG, this.zzQR.zzTH, (String) null, this.zzQR.zzMd, this.zzQR.zzTK, this.zzQQ.zzXX));
    }
}
