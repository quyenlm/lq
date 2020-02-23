package com.google.android.gms.ads.internal;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.view.Window;
import com.google.android.gms.ads.internal.js.zzai;
import com.google.android.gms.ads.internal.overlay.AdOverlayInfoParcel;
import com.google.android.gms.ads.internal.overlay.zzu;
import com.google.android.gms.ads.internal.overlay.zzw;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.internal.zzaai;
import com.google.android.gms.internal.zzabt;
import com.google.android.gms.internal.zzaee;
import com.google.android.gms.internal.zzaeq;
import com.google.android.gms.internal.zzaet;
import com.google.android.gms.internal.zzaev;
import com.google.android.gms.internal.zzaff;
import com.google.android.gms.internal.zzafg;
import com.google.android.gms.internal.zzafr;
import com.google.android.gms.internal.zzagz;
import com.google.android.gms.internal.zzahe;
import com.google.android.gms.internal.zzaje;
import com.google.android.gms.internal.zzaka;
import com.google.android.gms.internal.zzakb;
import com.google.android.gms.internal.zzakh;
import com.google.android.gms.internal.zzakm;
import com.google.android.gms.internal.zzge;
import com.google.android.gms.internal.zzgi;
import com.google.android.gms.internal.zzir;
import com.google.android.gms.internal.zziv;
import com.google.android.gms.internal.zzmo;
import com.google.android.gms.internal.zznb;
import com.google.android.gms.internal.zzrd;
import com.google.android.gms.internal.zzrm;
import com.google.android.gms.internal.zzru;
import com.google.android.gms.internal.zzrv;
import com.google.android.gms.internal.zzua;
import com.google.android.gms.internal.zzub;
import com.google.android.gms.internal.zzuq;
import com.google.android.gms.internal.zzwv;
import com.google.android.gms.internal.zzzn;
import java.util.Collections;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

@zzzn
public final class zzal extends zzi implements zzrm, zzrv {
    private transient boolean zzuf = false;
    private int zzug = -1;
    /* access modifiers changed from: private */
    public boolean zzuh;
    /* access modifiers changed from: private */
    public float zzui;
    /* access modifiers changed from: private */
    public boolean zzuj;
    private final zzaev zzuk;
    private String zzul;
    private final String zzum;

    public zzal(Context context, zziv zziv, String str, zzuq zzuq, zzaje zzaje, zzv zzv) {
        super(context, zziv, str, zzuq, zzaje, zzv);
        this.zzuk = zzbs.zzbY().zzs(context) ? new zzaev(context, str) : null;
        this.zzum = (zziv == null || !"reward_mb".equals(zziv.zzAs)) ? "/Interstitial" : "/Rewarded";
    }

    private static zzafg zzb(zzafg zzafg) {
        try {
            String jSONObject = zzabt.zzb(zzafg.zzXY).toString();
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("pubid", zzafg.zzUj.zzvR);
            zzua zzua = new zzua(jSONObject, (String) null, Collections.singletonList("com.google.ads.mediation.admob.AdMobAdapter"), (String) null, (String) null, Collections.emptyList(), Collections.emptyList(), jSONObject2.toString(), (String) null, Collections.emptyList(), Collections.emptyList(), (String) null, (String) null, (String) null, (List<String>) null, (String) null, Collections.emptyList(), (String) null);
            zzaai zzaai = zzafg.zzXY;
            zzub zzub = new zzub(Collections.singletonList(zzua), ((Long) zzbs.zzbL().zzd(zzmo.zzEL)).longValue(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), zzaai.zzMd, zzaai.zzMe, "", -1, 0, 1, (String) null, 0, -1, -1, false);
            return new zzafg(zzafg.zzUj, new zzaai(zzafg.zzUj, zzaai.zzPi, zzaai.body, Collections.emptyList(), Collections.emptyList(), zzaai.zzTn, true, zzaai.zzTp, Collections.emptyList(), zzaai.zzMg, zzaai.orientation, zzaai.zzTr, zzaai.zzTs, zzaai.zzTt, zzaai.zzTu, zzaai.zzTv, (String) null, zzaai.zzTx, zzaai.zzAv, zzaai.zzSH, zzaai.zzTy, zzaai.zzTz, zzaai.zzTC, zzaai.zzAw, zzaai.zzAx, (zzaee) null, Collections.emptyList(), Collections.emptyList(), zzaai.zzTG, zzaai.zzTH, zzaai.zzSV, zzaai.zzSW, zzaai.zzMd, zzaai.zzMe, zzaai.zzTI, (zzaeq) null, zzaai.zzTK, zzaai.zzTL, zzaai.zzTh), zzub, zzafg.zzvX, zzafg.errorCode, zzafg.zzXR, zzafg.zzXS, (JSONObject) null, zzafg.zzXX);
        } catch (JSONException e) {
            zzafr.zzb("Unable to generate ad state for an interstitial ad with pooling.", e);
            return zzafg;
        }
    }

    private final void zzb(Bundle bundle) {
        zzbs.zzbz().zzb(this.zzsP.zzqD, this.zzsP.zzvT.zzaP, "gmob-apps", bundle, false);
    }

    public final void setImmersiveMode(boolean z) {
        zzbo.zzcz("setImmersiveMode must be called on the main UI thread.");
        this.zzuj = z;
    }

    public final void showInterstitial() {
        Bitmap bitmap;
        zzbo.zzcz("showInterstitial must be called on the main UI thread.");
        if (zzbs.zzbY().zzs(this.zzsP.zzqD)) {
            this.zzul = zzbs.zzbY().zzt(this.zzsP.zzqD);
            String valueOf = String.valueOf(this.zzul);
            String valueOf2 = String.valueOf(this.zzum);
            this.zzul = valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
        }
        if (this.zzsP.zzvY == null) {
            zzafr.zzaT("The interstitial has not loaded.");
            return;
        }
        if (((Boolean) zzbs.zzbL().zzd(zzmo.zzEv)).booleanValue()) {
            String packageName = this.zzsP.zzqD.getApplicationContext() != null ? this.zzsP.zzqD.getApplicationContext().getPackageName() : this.zzsP.zzqD.getPackageName();
            if (!this.zzuf) {
                zzafr.zzaT("It is not recommended to show an interstitial before onAdLoaded completes.");
                Bundle bundle = new Bundle();
                bundle.putString("appid", packageName);
                bundle.putString("action", "show_interstitial_before_load_finish");
                zzb(bundle);
            }
            zzbs.zzbz();
            if (!zzagz.zzJ(this.zzsP.zzqD)) {
                zzafr.zzaT("It is not recommended to show an interstitial when app is not in foreground.");
                Bundle bundle2 = new Bundle();
                bundle2.putString("appid", packageName);
                bundle2.putString("action", "show_interstitial_app_not_in_foreground");
                zzb(bundle2);
            }
        }
        if (this.zzsP.zzcd()) {
            return;
        }
        if (this.zzsP.zzvY.zzTo && this.zzsP.zzvY.zzMH != null) {
            try {
                if (((Boolean) zzbs.zzbL().zzd(zzmo.zzDT)).booleanValue()) {
                    this.zzsP.zzvY.zzMH.setImmersiveMode(this.zzuj);
                }
                this.zzsP.zzvY.zzMH.showInterstitial();
            } catch (RemoteException e) {
                zzafr.zzc("Could not show interstitial.", e);
                zzbb();
            }
        } else if (this.zzsP.zzvY.zzPg == null) {
            zzafr.zzaT("The interstitial failed to load.");
        } else if (this.zzsP.zzvY.zzPg.zziA()) {
            zzafr.zzaT("The interstitial is already showing.");
        } else {
            this.zzsP.zzvY.zzPg.zzA(true);
            if (this.zzsP.zzvY.zzXL != null) {
                this.zzsR.zza(this.zzsP.zzvX, this.zzsP.zzvY);
            }
            zzaff zzaff = this.zzsP.zzvY;
            if (zzaff.zzcn()) {
                new zzge(this.zzsP.zzqD, zzaff.zzPg.getView()).zza((zzgi) zzaff.zzPg);
            } else {
                zzaff.zzPg.zziw().zza((zzakh) new zzam(this, zzaff));
            }
            if (this.zzsP.zzur) {
                zzbs.zzbz();
                bitmap = zzagz.zzK(this.zzsP.zzqD);
            } else {
                bitmap = null;
            }
            this.zzug = zzbs.zzbU().zzb(bitmap);
            if (!((Boolean) zzbs.zzbL().zzd(zzmo.zzFh)).booleanValue() || bitmap == null) {
                zzap zzap = new zzap(this.zzsP.zzur, zzba(), false, 0.0f, -1, this.zzuj);
                int requestedOrientation = this.zzsP.zzvY.zzPg.getRequestedOrientation();
                if (requestedOrientation == -1) {
                    requestedOrientation = this.zzsP.zzvY.orientation;
                }
                AdOverlayInfoParcel adOverlayInfoParcel = new AdOverlayInfoParcel(this, this, this, this.zzsP.zzvY.zzPg, requestedOrientation, this.zzsP.zzvT, this.zzsP.zzvY.zzTt, zzap);
                zzbs.zzbx();
                zzu.zza(this.zzsP.zzqD, adOverlayInfoParcel, true);
                return;
            }
            new zzan(this, this.zzug).zzhL();
        }
    }

    /* access modifiers changed from: protected */
    public final zzaka zza(zzafg zzafg, @Nullable zzw zzw, @Nullable zzaet zzaet) throws zzakm {
        zzaka zza = zzbs.zzbA().zza(this.zzsP.zzqD, this.zzsP.zzvX, false, false, this.zzsP.zzvS, this.zzsP.zzvT, this.zzsK, this, this.zzsS, zzafg.zzXX);
        zza.zziw().zza(this, (zzw) null, this, this, ((Boolean) zzbs.zzbL().zzd(zzmo.zzDn)).booleanValue(), this, zzw, (zzwv) null, zzaet);
        zza((zzai) zza);
        zza.zzaV(zzafg.zzUj.zzSM);
        zza.zziw().zza("/reward", (zzrd) new zzru(this));
        return zza;
    }

    public final void zza(zzafg zzafg, zznb zznb) {
        boolean z = true;
        if (!((Boolean) zzbs.zzbL().zzd(zzmo.zzDY)).booleanValue()) {
            super.zza(zzafg, zznb);
        } else if (zzafg.errorCode != -2) {
            super.zza(zzafg, zznb);
        } else {
            Bundle bundle = zzafg.zzUj.zzSz.zzzX.getBundle("com.google.ads.mediation.admob.AdMobAdapter");
            boolean z2 = bundle == null || !bundle.containsKey("gw");
            if (zzafg.zzXY.zzTo) {
                z = false;
            }
            if (z2 && z) {
                this.zzsP.zzvZ = zzb(zzafg);
            }
            super.zza(this.zzsP.zzvZ, zznb);
        }
    }

    public final void zza(boolean z, float f) {
        this.zzuh = z;
        this.zzui = f;
    }

    public final boolean zza(@Nullable zzaff zzaff, zzaff zzaff2) {
        if (!super.zza(zzaff, zzaff2)) {
            return false;
        }
        if (!(this.zzsP.zzcc() || this.zzsP.zzws == null || zzaff2.zzXL == null)) {
            this.zzsR.zza(this.zzsP.zzvX, zzaff2, this.zzsP.zzws);
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public final boolean zza(zzir zzir, zzaff zzaff, boolean z) {
        if (this.zzsP.zzcc() && zzaff.zzPg != null) {
            zzbs.zzbB();
            zzahe.zzk(zzaff.zzPg);
        }
        return this.zzsO.zzbo();
    }

    public final boolean zza(zzir zzir, zznb zznb) {
        if (this.zzsP.zzvY == null) {
            return super.zza(zzir, zznb);
        }
        zzafr.zzaT("An interstitial is already loading. Aborting.");
        return false;
    }

    public final void zzaA() {
        super.zzaA();
        this.zzsR.zzg(this.zzsP.zzvY);
        if (zzbs.zzbY().zzs(this.zzsP.zzqD)) {
            this.zzuk.zzu(false);
        }
    }

    public final void zzaB() {
        zzakb zziw;
        recordImpression();
        super.zzaB();
        if (!(this.zzsP.zzvY == null || this.zzsP.zzvY.zzPg == null || (zziw = this.zzsP.zzvY.zzPg.zziw()) == null)) {
            zziw.zziV();
        }
        if (zzbs.zzbY().zzs(this.zzsP.zzqD)) {
            if (!(this.zzsP.zzvY == null || this.zzsP.zzvY.zzPg == null)) {
                zzbs.zzbY().zze(this.zzsP.zzvY.zzPg.getContext(), this.zzul);
            }
            this.zzuk.zzu(true);
        }
    }

    /* access modifiers changed from: protected */
    public final void zzap() {
        zzbb();
        super.zzap();
    }

    /* access modifiers changed from: protected */
    public final void zzas() {
        super.zzas();
        this.zzuf = true;
    }

    public final void zzb(zzaee zzaee) {
        if (this.zzsP.zzvY != null) {
            if (this.zzsP.zzvY.zzTF != null) {
                zzbs.zzbz();
                zzagz.zza(this.zzsP.zzqD, this.zzsP.zzvT.zzaP, this.zzsP.zzvY.zzTF);
            }
            if (this.zzsP.zzvY.zzTD != null) {
                zzaee = this.zzsP.zzvY.zzTD;
            }
        }
        zza(zzaee);
    }

    /* access modifiers changed from: protected */
    public final boolean zzba() {
        if (!(this.zzsP.zzqD instanceof Activity)) {
            return false;
        }
        Window window = ((Activity) this.zzsP.zzqD).getWindow();
        if (window == null || window.getDecorView() == null) {
            return false;
        }
        Rect rect = new Rect();
        Rect rect2 = new Rect();
        window.getDecorView().getGlobalVisibleRect(rect, (Point) null);
        window.getDecorView().getWindowVisibleDisplayFrame(rect2);
        return (rect.bottom == 0 || rect2.bottom == 0 || rect.top != rect2.top) ? false : true;
    }

    public final void zzbb() {
        zzbs.zzbU().zzb(Integer.valueOf(this.zzug));
        if (this.zzsP.zzcc()) {
            this.zzsP.zzca();
            this.zzsP.zzvY = null;
            this.zzsP.zzur = false;
            this.zzuf = false;
        }
    }

    public final void zzbc() {
        if (!(this.zzsP.zzvY == null || this.zzsP.zzvY.zzXQ == null)) {
            zzbs.zzbz();
            zzagz.zza(this.zzsP.zzqD, this.zzsP.zzvT.zzaP, this.zzsP.zzvY.zzXQ);
        }
        zzav();
    }

    public final void zzc(boolean z) {
        this.zzsP.zzur = z;
    }
}
