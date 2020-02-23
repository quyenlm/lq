package com.google.android.gms.ads.internal;

import android.content.Context;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.util.SimpleArrayMap;
import android.util.Log;
import android.view.View;
import com.google.android.gms.ads.internal.js.zzai;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.internal.zzaff;
import com.google.android.gms.internal.zzafg;
import com.google.android.gms.internal.zzafr;
import com.google.android.gms.internal.zzagz;
import com.google.android.gms.internal.zzajc;
import com.google.android.gms.internal.zzaje;
import com.google.android.gms.internal.zzaka;
import com.google.android.gms.internal.zzfk;
import com.google.android.gms.internal.zzgs;
import com.google.android.gms.internal.zzir;
import com.google.android.gms.internal.zziv;
import com.google.android.gms.internal.zzmo;
import com.google.android.gms.internal.zznb;
import com.google.android.gms.internal.zznh;
import com.google.android.gms.internal.zznn;
import com.google.android.gms.internal.zznq;
import com.google.android.gms.internal.zzns;
import com.google.android.gms.internal.zznu;
import com.google.android.gms.internal.zznw;
import com.google.android.gms.internal.zznx;
import com.google.android.gms.internal.zzny;
import com.google.android.gms.internal.zznz;
import com.google.android.gms.internal.zzoa;
import com.google.android.gms.internal.zzpj;
import com.google.android.gms.internal.zzpt;
import com.google.android.gms.internal.zzpw;
import com.google.android.gms.internal.zzuq;
import com.google.android.gms.internal.zzvc;
import com.google.android.gms.internal.zzvf;
import com.google.android.gms.internal.zzxg;
import com.google.android.gms.internal.zzxx;
import com.google.android.gms.internal.zzyh;
import com.google.android.gms.internal.zzzn;
import java.util.List;

@zzzn
public final class zzbb extends zzd implements zznz {
    /* access modifiers changed from: private */
    public final Object mLock = new Object();
    private boolean zzta;
    private zzaka zzuO;
    private zzyh zzuP;

    public zzbb(Context context, zzv zzv, zziv zziv, String str, zzuq zzuq, zzaje zzaje) {
        super(context, zziv, str, zzuq, zzaje, zzv);
    }

    private final void zza(zznq zznq) {
        zzagz.zzZr.post(new zzbd(this, zznq));
    }

    private final void zza(zzns zzns) {
        zzagz.zzZr.post(new zzbe(this, zzns));
    }

    private final void zzbn() {
        zzagz.zzb(new zzbh(this));
    }

    public final String getAdUnitId() {
        return this.zzsP.zzvR;
    }

    public final void pause() {
        throw new IllegalStateException("Native Ad DOES NOT support pause().");
    }

    public final void resume() {
        throw new IllegalStateException("Native Ad DOES NOT support resume().");
    }

    public final void showInterstitial() {
        throw new IllegalStateException("Interstitial is NOT supported by NativeAdManager.");
    }

    public final void zza(zzafg zzafg, zznb zznb) {
        if (zzafg.zzvX != null) {
            this.zzsP.zzvX = zzafg.zzvX;
        }
        if (zzafg.errorCode != -2) {
            zzagz.zzZr.post(new zzbc(this, zzafg));
            return;
        }
        this.zzsP.zzwt = 0;
        zzbt zzbt = this.zzsP;
        zzbs.zzby();
        zzbt.zzvW = zzxx.zza(this.zzsP.zzqD, this, zzafg, this.zzsP.zzvS, (zzaka) null, this.zzsX, this, zznb);
        String valueOf = String.valueOf(this.zzsP.zzvW.getClass().getName());
        zzafr.zzaC(valueOf.length() != 0 ? "AdRenderer: ".concat(valueOf) : new String("AdRenderer: "));
    }

    public final void zza(zznh zznh) {
        throw new IllegalStateException("CustomRendering is NOT supported by NativeAdManager.");
    }

    public final void zza(zznw zznw) {
        if (this.zzuO != null) {
            this.zzuO.zzb(zznw);
        }
    }

    public final void zza(zzny zzny) {
        if (this.zzsP.zzvY.zzXL != null) {
            zzbs.zzbD().zzhG().zza(this.zzsP.zzvX, this.zzsP.zzvY, (zzgs) new zzfk(zzny), (zzai) null);
        }
    }

    public final void zza(zzxg zzxg) {
        throw new IllegalStateException("In App Purchase is NOT supported by NativeAdManager.");
    }

    /* access modifiers changed from: protected */
    public final boolean zza(zzaff zzaff, zzaff zzaff2) {
        zzc((List<String>) null);
        if (!this.zzsP.zzcc()) {
            throw new IllegalStateException("Native ad DOES NOT have custom rendering mode.");
        }
        if (zzaff2.zzTo) {
            if (((Boolean) zzbs.zzbL().zzd(zzmo.zzFu)).booleanValue()) {
                zzbn();
            }
            try {
                zzvc zzfq = zzaff2.zzMH != null ? zzaff2.zzMH.zzfq() : null;
                zzvf zzfr = zzaff2.zzMH != null ? zzaff2.zzMH.zzfr() : null;
                zzpj zzfv = zzaff2.zzMH != null ? zzaff2.zzMH.zzfv() : null;
                if (zzfq == null || this.zzsP.zzwf == null) {
                    if (zzfr != null) {
                        if (this.zzsP.zzwg != null) {
                            zzns zzns = new zzns(zzfr.getHeadline(), zzfr.getImages(), zzfr.getBody(), zzfr.zzem() != null ? zzfr.zzem() : null, zzfr.getCallToAction(), zzfr.getAdvertiser(), (zznn) null, zzfr.getExtras(), zzfr.getVideoController(), (View) null);
                            zzns.zzb((zzny) new zznx(this.zzsP.zzqD, (zznz) this, this.zzsP.zzvS, zzfr, (zzoa) zzns));
                            zza(zzns);
                        }
                    }
                    if (zzfv != null) {
                        if (!(this.zzsP.zzwi == null || this.zzsP.zzwi.get(zzfv.getCustomTemplateId()) == null)) {
                            zzagz.zzZr.post(new zzbg(this, zzfv));
                        }
                    }
                    zzafr.zzaT("No matching mapper/listener for retrieved native ad template.");
                    zze(0);
                    return false;
                }
                zznq zznq = new zznq(zzfq.getHeadline(), zzfq.getImages(), zzfq.getBody(), zzfq.zzeh() != null ? zzfq.zzeh() : null, zzfq.getCallToAction(), zzfq.getStarRating(), zzfq.getStore(), zzfq.getPrice(), (zznn) null, zzfq.getExtras(), zzfq.getVideoController(), (View) null);
                zznq.zzb((zzny) new zznx(this.zzsP.zzqD, (zznz) this, this.zzsP.zzvS, zzfq, (zzoa) zznq));
                zza(zznq);
            } catch (RemoteException e) {
                zzafr.zzc("Failed to get native ad mapper", e);
            }
        } else {
            zzoa zzoa = zzaff2.zzXT;
            if ((zzoa instanceof zzns) && this.zzsP.zzwg != null) {
                zza((zzns) zzaff2.zzXT);
            } else if ((zzoa instanceof zznq) && this.zzsP.zzwf != null) {
                zza((zznq) zzaff2.zzXT);
            } else if (!(zzoa instanceof zznu) || this.zzsP.zzwi == null || this.zzsP.zzwi.get(((zznu) zzoa).getCustomTemplateId()) == null) {
                zzafr.zzaT("No matching listener for retrieved native ad template.");
                zze(0);
                return false;
            } else {
                zzagz.zzZr.post(new zzbf(this, ((zznu) zzoa).getCustomTemplateId(), zzaff2));
            }
        }
        return super.zza(zzaff, zzaff2);
    }

    /* access modifiers changed from: protected */
    public final boolean zza(zzir zzir, zzaff zzaff, boolean z) {
        return this.zzsO.zzbo();
    }

    public final boolean zza(zzir zzir, zznb zznb) {
        try {
            if (((Boolean) zzbs.zzbL().zzd(zzmo.zzFu)).booleanValue()) {
                synchronized (this.mLock) {
                    this.zzuP = new zzyh(this.zzsP.zzqD, this, this.zzsP.zzvS, this.zzsP.zzvT);
                    this.zzuP.zzgs();
                    this.zzuP.zzgt();
                }
            }
            return super.zza(zzir, zznb);
        } catch (Exception e) {
            if (zzajc.zzz(4)) {
                Log.i("Ads", "Error initializing webview.", e);
            }
            return false;
        }
    }

    public final void zzaC() {
        if (this.zzsP.zzvY == null || !"com.google.ads.mediation.admob.AdMobAdapter".equals(this.zzsP.zzvY.zzMI)) {
            super.zzaC();
        } else {
            zzau();
        }
    }

    public final void zzaH() {
        if (this.zzsP.zzvY == null || !"com.google.ads.mediation.admob.AdMobAdapter".equals(this.zzsP.zzvY.zzMI)) {
            super.zzaH();
        } else {
            zzat();
        }
    }

    public final void zzaO() {
        if (this.zzuO != null) {
            this.zzuO.destroy();
            this.zzuO = null;
        }
    }

    public final boolean zzaP() {
        return this.zzsP.zzvY != null && this.zzsP.zzvY.zzTo && this.zzsP.zzvY.zzXN != null && this.zzsP.zzvY.zzXN.zzMm;
    }

    /* access modifiers changed from: protected */
    public final void zzas() {
        super.zzas();
        if (this.zzta) {
            if (((Boolean) zzbs.zzbL().zzd(zzmo.zzFC)).booleanValue()) {
                zzbj();
            }
        }
    }

    @Nullable
    public final zzyh zzbi() {
        zzyh zzyh;
        synchronized (this.mLock) {
            zzyh = this.zzuP;
        }
        return zzyh;
    }

    public final void zzbj() {
        if (this.zzsP.zzvY == null || this.zzuO == null) {
            this.zzta = true;
            zzafr.zzaT("Request to enable ActiveView before adState is available.");
            return;
        }
        zzbs.zzbD().zzhG().zza(this.zzsP.zzvX, this.zzsP.zzvY, this.zzuO.getView(), (zzai) this.zzuO);
        this.zzta = false;
    }

    public final void zzbk() {
        this.zzta = false;
        if (this.zzsP.zzvY == null || this.zzuO == null) {
            zzafr.zzaT("Request to enable ActiveView before adState is available.");
        } else {
            zzbs.zzbD().zzhG().zzg(this.zzsP.zzvY);
        }
    }

    public final SimpleArrayMap<String, zzpw> zzbl() {
        zzbo.zzcz("getOnCustomTemplateAdLoadedListeners must be called on the main UI thread.");
        return this.zzsP.zzwi;
    }

    public final void zzbm() {
        if (this.zzuO != null && this.zzuO.zziH() != null && this.zzsP.zzwj != null && this.zzsP.zzwj.zzIr != null) {
            this.zzuO.zziH().zzb(this.zzsP.zzwj.zzIr);
        }
    }

    public final void zzc(@Nullable List<String> list) {
        zzbo.zzcz("setNativeTemplates must be called on the main UI thread.");
        this.zzsP.zzwq = list;
    }

    public final void zzd(zzaka zzaka) {
        this.zzuO = zzaka;
    }

    /* access modifiers changed from: protected */
    public final void zze(int i) {
        if (((Boolean) zzbs.zzbL().zzd(zzmo.zzFu)).booleanValue()) {
            zzbn();
        }
        super.zze(i);
    }

    @Nullable
    public final zzpt zzs(String str) {
        zzbo.zzcz("getOnCustomClickListener must be called on the main UI thread.");
        return this.zzsP.zzwh.get(str);
    }
}
