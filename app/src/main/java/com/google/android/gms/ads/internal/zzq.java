package com.google.android.gms.ads.internal;

import android.content.Context;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.view.View;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.dynamic.zzn;
import com.google.android.gms.internal.zzaff;
import com.google.android.gms.internal.zzafg;
import com.google.android.gms.internal.zzafr;
import com.google.android.gms.internal.zzagz;
import com.google.android.gms.internal.zzaje;
import com.google.android.gms.internal.zzaka;
import com.google.android.gms.internal.zzir;
import com.google.android.gms.internal.zziv;
import com.google.android.gms.internal.zzks;
import com.google.android.gms.internal.zznb;
import com.google.android.gms.internal.zznh;
import com.google.android.gms.internal.zznn;
import com.google.android.gms.internal.zznq;
import com.google.android.gms.internal.zzns;
import com.google.android.gms.internal.zznw;
import com.google.android.gms.internal.zznx;
import com.google.android.gms.internal.zzny;
import com.google.android.gms.internal.zznz;
import com.google.android.gms.internal.zzoa;
import com.google.android.gms.internal.zzpj;
import com.google.android.gms.internal.zzpt;
import com.google.android.gms.internal.zzua;
import com.google.android.gms.internal.zzud;
import com.google.android.gms.internal.zzuq;
import com.google.android.gms.internal.zzut;
import com.google.android.gms.internal.zzvc;
import com.google.android.gms.internal.zzvf;
import com.google.android.gms.internal.zzxx;
import com.google.android.gms.internal.zzzn;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@zzzn
public final class zzq extends zzd implements zznz {
    private boolean zzsu;
    /* access modifiers changed from: private */
    public zzaff zztg;
    private boolean zzth = false;

    public zzq(Context context, zzv zzv, zziv zziv, String str, zzuq zzuq, zzaje zzaje) {
        super(context, zziv, str, zzuq, zzaje, zzv);
    }

    private static zzaff zza(zzafg zzafg, int i) {
        return new zzaff(zzafg.zzUj.zzSz, (zzaka) null, zzafg.zzXY.zzMa, i, zzafg.zzXY.zzMb, zzafg.zzXY.zzTq, zzafg.zzXY.orientation, zzafg.zzXY.zzMg, zzafg.zzUj.zzSC, zzafg.zzXY.zzTo, (zzua) null, (zzut) null, (String) null, zzafg.zzXN, (zzud) null, zzafg.zzXY.zzTp, zzafg.zzvX, zzafg.zzXY.zzTn, zzafg.zzXR, zzafg.zzXS, zzafg.zzXY.zzTt, zzafg.zzXL, (zzoa) null, zzafg.zzXY.zzTD, zzafg.zzXY.zzTE, zzafg.zzXY.zzTE, zzafg.zzXY.zzTG, zzafg.zzXY.zzTH, (String) null, zzafg.zzXY.zzMd, zzafg.zzXY.zzTK, zzafg.zzXX);
    }

    private final boolean zzb(zzaff zzaff, zzaff zzaff2) {
        zzc((List<String>) null);
        if (!this.zzsP.zzcc()) {
            zzafr.zzaT("Native ad does not have custom rendering mode.");
            zze(0);
            return false;
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
                        zzagz.zzZr.post(new zzt(this, zzns));
                    }
                }
                if (zzfv != null) {
                    if (!(this.zzsP.zzwi == null || this.zzsP.zzwi.get(zzfv.getCustomTemplateId()) == null)) {
                        zzagz.zzZr.post(new zzu(this, zzfv));
                    }
                }
                zzafr.zzaT("No matching mapper/listener for retrieved native ad template.");
                zze(0);
                return false;
            }
            zznq zznq = new zznq(zzfq.getHeadline(), zzfq.getImages(), zzfq.getBody(), zzfq.zzeh() != null ? zzfq.zzeh() : null, zzfq.getCallToAction(), zzfq.getStarRating(), zzfq.getStore(), zzfq.getPrice(), (zznn) null, zzfq.getExtras(), zzfq.getVideoController(), (View) null);
            zznq.zzb((zzny) new zznx(this.zzsP.zzqD, (zznz) this, this.zzsP.zzvS, zzfq, (zzoa) zznq));
            zzagz.zzZr.post(new zzs(this, zznq));
            return super.zza(zzaff, zzaff2);
        } catch (RemoteException e) {
            zzafr.zzc("Failed to get native ad mapper", e);
            zze(0);
            return false;
        }
    }

    private final boolean zzc(zzaff zzaff, zzaff zzaff2) {
        View zzd = zzar.zzd(zzaff2);
        if (zzd == null) {
            return false;
        }
        View nextView = this.zzsP.zzvU.getNextView();
        if (nextView != null) {
            if (nextView instanceof zzaka) {
                ((zzaka) nextView).destroy();
            }
            this.zzsP.zzvU.removeView(nextView);
        }
        if (!zzar.zze(zzaff2)) {
            try {
                zzb(zzd);
            } catch (Throwable th) {
                zzbs.zzbD().zza(th, "AdLoaderManager.swapBannerViews");
                zzafr.zzc("Could not add mediation view to view hierarchy.", th);
                return false;
            }
        }
        if (this.zzsP.zzvU.getChildCount() > 1) {
            this.zzsP.zzvU.showNext();
        }
        if (zzaff != null) {
            View nextView2 = this.zzsP.zzvU.getNextView();
            if (nextView2 != null) {
                this.zzsP.zzvU.removeView(nextView2);
            }
            this.zzsP.zzcb();
        }
        this.zzsP.zzvU.setMinimumWidth(zzam().widthPixels);
        this.zzsP.zzvU.setMinimumHeight(zzam().heightPixels);
        this.zzsP.zzvU.requestLayout();
        this.zzsP.zzvU.setVisibility(0);
        return true;
    }

    @Nullable
    public final zzks getVideoController() {
        return null;
    }

    public final void pause() {
        if (!this.zzth) {
            throw new IllegalStateException("Native Ad does not support pause().");
        }
        super.pause();
    }

    public final void resume() {
        if (!this.zzth) {
            throw new IllegalStateException("Native Ad does not support resume().");
        }
        super.resume();
    }

    public final void setManualImpressionsEnabled(boolean z) {
        zzbo.zzcz("setManualImpressionsEnabled must be called from the main thread.");
        this.zzsu = z;
    }

    public final void showInterstitial() {
        throw new IllegalStateException("Interstitial is not supported by AdLoaderManager.");
    }

    public final void zza(zzafg zzafg, zznb zznb) {
        this.zztg = null;
        if (zzafg.errorCode != -2) {
            this.zztg = zza(zzafg, zzafg.errorCode);
        } else if (!zzafg.zzXY.zzTo) {
            zzafr.zzaT("partialAdState is not mediation");
            this.zztg = zza(zzafg, 0);
        }
        if (this.zztg != null) {
            zzagz.zzZr.post(new zzr(this));
            return;
        }
        if (zzafg.zzvX != null) {
            this.zzsP.zzvX = zzafg.zzvX;
        }
        this.zzsP.zzwt = 0;
        zzbt zzbt = this.zzsP;
        zzbs.zzby();
        zzbt.zzvW = zzxx.zza(this.zzsP.zzqD, this, zzafg, this.zzsP.zzvS, (zzaka) null, this.zzsX, this, zznb);
    }

    public final void zza(zznh zznh) {
        throw new IllegalStateException("CustomRendering is not supported by AdLoaderManager.");
    }

    public final void zza(zznw zznw) {
        zzafr.zzaT("Unexpected call to AdLoaderManager method");
    }

    public final void zza(zzny zzny) {
        zzafr.zzaT("Unexpected call to AdLoaderManager method");
    }

    /* access modifiers changed from: protected */
    public final boolean zza(@Nullable zzaff zzaff, zzaff zzaff2) {
        boolean z;
        if (!this.zzsP.zzcc()) {
            throw new IllegalStateException("AdLoader API does not support custom rendering.");
        } else if (!zzaff2.zzTo) {
            zze(0);
            zzafr.zzaT("newState is not mediation.");
            return false;
        } else {
            if (zzaff2.zzMG != null && zzaff2.zzMG.zzfh()) {
                if (this.zzsP.zzcc() && this.zzsP.zzvU != null) {
                    this.zzsP.zzvU.zzcf().zzaP(zzaff2.zzTt);
                }
                if (!super.zza(zzaff, zzaff2)) {
                    z = false;
                } else if (!this.zzsP.zzcc() || zzc(zzaff, zzaff2)) {
                    if (!this.zzsP.zzcd()) {
                        super.zza(zzaff2, false);
                    }
                    z = true;
                } else {
                    zze(0);
                    z = false;
                }
                if (!z) {
                    return false;
                }
                this.zzth = true;
            } else if (zzaff2.zzMG == null || !zzaff2.zzMG.zzfi()) {
                zze(0);
                zzafr.zzaT("Response is neither banner nor native.");
                return false;
            } else if (!zzb(zzaff, zzaff2)) {
                return false;
            }
            zzd(new ArrayList(Arrays.asList(new Integer[]{2})));
            return true;
        }
    }

    public final boolean zza(zzir zzir) {
        if (this.zzsP.zzwn != null && this.zzsP.zzwn.size() == 1 && this.zzsP.zzwn.get(0).intValue() == 2) {
            zzafr.e("Requesting only banner Ad from AdLoader or calling loadAd on returned banner is not yet supported");
            zze(0);
            return false;
        } else if (this.zzsP.zzwm == null) {
            return super.zza(zzir);
        } else {
            if (zzir.zzzS != this.zzsu) {
                zzir = new zzir(zzir.versionCode, zzir.zzzN, zzir.extras, zzir.zzzO, zzir.zzzP, zzir.zzzQ, zzir.zzzR, zzir.zzzS || this.zzsu, zzir.zzzT, zzir.zzzU, zzir.zzzV, zzir.zzzW, zzir.zzzX, zzir.zzzY, zzir.zzzZ, zzir.zzAa, zzir.zzAb, zzir.zzAc);
            }
            return super.zza(zzir);
        }
    }

    /* access modifiers changed from: protected */
    public final boolean zza(zzir zzir, zzaff zzaff, boolean z) {
        return false;
    }

    public final void zzaC() {
        if (this.zzsP.zzvY == null || !"com.google.ads.mediation.admob.AdMobAdapter".equals(this.zzsP.zzvY.zzMI) || this.zzsP.zzvY.zzMG == null || !this.zzsP.zzvY.zzMG.zzfi()) {
            super.zzaC();
        } else {
            zzau();
        }
    }

    public final void zzaH() {
        if (this.zzsP.zzvY == null || !"com.google.ads.mediation.admob.AdMobAdapter".equals(this.zzsP.zzvY.zzMI) || this.zzsP.zzvY.zzMG == null || !this.zzsP.zzvY.zzMG.zzfi()) {
            super.zzaH();
        } else {
            zzat();
        }
    }

    public final void zzaO() {
        zzafr.zzaT("Unexpected call to AdLoaderManager method");
    }

    public final boolean zzaP() {
        return this.zzsP.zzvY != null && this.zzsP.zzvY.zzTo && this.zzsP.zzvY.zzXN != null && this.zzsP.zzvY.zzXN.zzMm;
    }

    /* access modifiers changed from: protected */
    public final void zzas() {
        super.zzas();
        zzaff zzaff = this.zzsP.zzvY;
        if (zzaff != null && zzaff.zzMG != null && zzaff.zzMG.zzfh() && this.zzsP.zzwm != null) {
            try {
                this.zzsP.zzwm.zza(this, zzn.zzw(this.zzsP.zzqD));
            } catch (RemoteException e) {
                zzafr.zzc("Could not call PublisherAdViewLoadedListener.onPublisherAdViewLoaded().", e);
            }
        }
    }

    public final void zzc(@Nullable List<String> list) {
        zzbo.zzcz("setNativeTemplates must be called on the main UI thread.");
        this.zzsP.zzwq = list;
    }

    public final void zzd(List<Integer> list) {
        zzbo.zzcz("setAllowedAdTypes must be called on the main UI thread.");
        this.zzsP.zzwn = list;
    }

    @Nullable
    public final zzpt zzs(String str) {
        zzbo.zzcz("getOnCustomClickListener must be called on the main UI thread.");
        return this.zzsP.zzwh.get(str);
    }
}
