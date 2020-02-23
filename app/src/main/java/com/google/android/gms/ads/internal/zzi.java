package com.google.android.gms.ads.internal;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import com.google.android.gms.ads.internal.js.zzai;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.internal.zzaet;
import com.google.android.gms.internal.zzaff;
import com.google.android.gms.internal.zzafg;
import com.google.android.gms.internal.zzafr;
import com.google.android.gms.internal.zzagz;
import com.google.android.gms.internal.zzaje;
import com.google.android.gms.internal.zzaka;
import com.google.android.gms.internal.zzakm;
import com.google.android.gms.internal.zziv;
import com.google.android.gms.internal.zzmo;
import com.google.android.gms.internal.zznb;
import com.google.android.gms.internal.zznh;
import com.google.android.gms.internal.zzoa;
import com.google.android.gms.internal.zzrd;
import com.google.android.gms.internal.zzrm;
import com.google.android.gms.internal.zzua;
import com.google.android.gms.internal.zzud;
import com.google.android.gms.internal.zzuq;
import com.google.android.gms.internal.zzut;
import com.google.android.gms.internal.zzwv;
import com.google.android.gms.internal.zzxx;
import com.google.android.gms.internal.zzzn;

@zzzn
public class zzi extends zzd implements zzag, zzwv {
    private boolean zzta;

    public zzi(Context context, zziv zziv, String str, zzuq zzuq, zzaje zzaje, zzv zzv) {
        super(context, zziv, str, zzuq, zzaje, zzv);
    }

    /* access modifiers changed from: protected */
    public zzaka zza(zzafg zzafg, @Nullable zzw zzw, @Nullable zzaet zzaet) throws zzakm {
        zzaka zzaka = null;
        View nextView = this.zzsP.zzvU.getNextView();
        if (nextView instanceof zzaka) {
            zzaka = (zzaka) nextView;
            if (((Boolean) zzbs.zzbL().zzd(zzmo.zzDE)).booleanValue()) {
                zzafr.zzaC("Reusing webview...");
                zzaka.zza(this.zzsP.zzqD, this.zzsP.zzvX, this.zzsK);
            } else {
                zzaka.destroy();
                zzaka = null;
            }
        }
        if (zzaka == null) {
            if (nextView != null) {
                this.zzsP.zzvU.removeView(nextView);
            }
            zzaka = zzbs.zzbA().zza(this.zzsP.zzqD, this.zzsP.zzvX, false, false, this.zzsP.zzvS, this.zzsP.zzvT, this.zzsK, this, this.zzsS, zzafg.zzXX);
            if (this.zzsP.zzvX.zzAu == null) {
                zzb(zzaka.getView());
            }
        }
        zzaka zzaka2 = zzaka;
        zzaka2.zziw().zza(this, this, this, this, false, (zzrm) null, zzw, this, zzaet);
        zza((zzai) zzaka2);
        zzaka2.zzaV(zzafg.zzUj.zzSM);
        return zzaka2;
    }

    public final void zza(int i, int i2, int i3, int i4) {
        zzar();
    }

    /* access modifiers changed from: protected */
    public final void zza(zzai zzai) {
        zzai.zza("/trackActiveViewUnit", (zzrd) new zzj(this));
    }

    /* access modifiers changed from: protected */
    public void zza(zzafg zzafg, zznb zznb) {
        if (zzafg.errorCode != -2) {
            zzagz.zzZr.post(new zzk(this, zzafg));
            return;
        }
        if (zzafg.zzvX != null) {
            this.zzsP.zzvX = zzafg.zzvX;
        }
        if (!zzafg.zzXY.zzTo || zzafg.zzXY.zzAx) {
            zzagz.zzZr.post(new zzl(this, zzafg, this.zzsS.zzto.zza(this.zzsP.zzqD, this.zzsP.zzvT, zzafg.zzXY), zznb));
            return;
        }
        this.zzsP.zzwt = 0;
        zzbt zzbt = this.zzsP;
        zzbs.zzby();
        zzbt.zzvW = zzxx.zza(this.zzsP.zzqD, this, zzafg, this.zzsP.zzvS, (zzaka) null, this.zzsX, this, zznb);
    }

    /* access modifiers changed from: package-private */
    public final void zza(zzaka zzaka) {
        if (this.zzsP.zzvY != null) {
            this.zzsR.zza(this.zzsP.zzvX, this.zzsP.zzvY, zzaka.getView(), (zzai) zzaka);
            this.zzta = false;
            return;
        }
        this.zzta = true;
        zzafr.zzaT("Request to enable ActiveView before adState is available.");
    }

    public final void zza(zznh zznh) {
        zzbo.zzcz("setOnCustomRenderedAdLoadedListener must be called on the main UI thread.");
        this.zzsP.zzwo = zznh;
    }

    /* access modifiers changed from: protected */
    public boolean zza(@Nullable zzaff zzaff, zzaff zzaff2) {
        if (this.zzsP.zzcc() && this.zzsP.zzvU != null) {
            this.zzsP.zzvU.zzcf().zzaP(zzaff2.zzTt);
        }
        return super.zza(zzaff, zzaff2);
    }

    public final void zzaL() {
        onAdClicked();
    }

    public final void zzaM() {
        recordImpression();
        zzao();
    }

    public final void zzaN() {
        zzap();
    }

    /* access modifiers changed from: protected */
    public void zzas() {
        super.zzas();
        if (this.zzta) {
            if (((Boolean) zzbs.zzbL().zzd(zzmo.zzFC)).booleanValue()) {
                zza(this.zzsP.zzvY.zzPg);
            }
        }
    }

    public final void zzc(View view) {
        this.zzsP.zzws = view;
        zzb(new zzaff(this.zzsP.zzvZ, (zzaka) null, (zzua) null, (zzut) null, (String) null, (zzud) null, (zzoa) null, (String) null));
    }
}
