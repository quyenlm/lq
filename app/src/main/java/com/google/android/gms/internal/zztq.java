package com.google.android.gms.internal;

import android.content.Context;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import com.google.android.gms.ads.internal.zzal;
import com.google.android.gms.ads.internal.zzbs;
import com.google.android.gms.ads.internal.zzv;
import com.google.android.gms.dynamic.IObjectWrapper;

@zzzn
public final class zztq extends zzka {
    private final zzti zzKI;
    private final zzsi zzKq;
    @Nullable
    private zzal zzKv;
    private final String zztV;
    private boolean zzuj;

    public zztq(Context context, String str, zzuq zzuq, zzaje zzaje, zzv zzv) {
        this(str, new zzsi(context, zzuq, zzaje, zzv));
    }

    private zztq(String str, zzsi zzsi) {
        this.zztV = str;
        this.zzKq = zzsi;
        this.zzKI = new zzti();
        zzbs.zzbN().zza(zzsi);
    }

    private final void abort() {
        if (this.zzKv == null) {
            this.zzKv = this.zzKq.zzW(this.zztV);
            this.zzKI.zzd(this.zzKv);
        }
    }

    public final void destroy() throws RemoteException {
        if (this.zzKv != null) {
            this.zzKv.destroy();
        }
    }

    public final String getAdUnitId() {
        throw new IllegalStateException("getAdUnitId not implemented");
    }

    @Nullable
    public final String getMediationAdapterClassName() throws RemoteException {
        if (this.zzKv != null) {
            return this.zzKv.getMediationAdapterClassName();
        }
        return null;
    }

    public final zzks getVideoController() {
        throw new IllegalStateException("getVideoController not implemented for interstitials");
    }

    public final boolean isLoading() throws RemoteException {
        return this.zzKv != null && this.zzKv.isLoading();
    }

    public final boolean isReady() throws RemoteException {
        return this.zzKv != null && this.zzKv.isReady();
    }

    public final void pause() throws RemoteException {
        if (this.zzKv != null) {
            this.zzKv.pause();
        }
    }

    public final void resume() throws RemoteException {
        if (this.zzKv != null) {
            this.zzKv.resume();
        }
    }

    public final void setImmersiveMode(boolean z) {
        this.zzuj = z;
    }

    public final void setManualImpressionsEnabled(boolean z) throws RemoteException {
        abort();
        if (this.zzKv != null) {
            this.zzKv.setManualImpressionsEnabled(z);
        }
    }

    public final void setUserId(String str) {
    }

    public final void showInterstitial() throws RemoteException {
        if (this.zzKv != null) {
            this.zzKv.setImmersiveMode(this.zzuj);
            this.zzKv.showInterstitial();
            return;
        }
        zzafr.zzaT("Interstitial ad must be loaded before showInterstitial().");
    }

    public final void stopLoading() throws RemoteException {
        if (this.zzKv != null) {
            this.zzKv.stopLoading();
        }
    }

    public final void zza(zzadd zzadd) {
        this.zzKI.zzKl = zzadd;
        if (this.zzKv != null) {
            this.zzKI.zzd(this.zzKv);
        }
    }

    public final void zza(zziv zziv) throws RemoteException {
        if (this.zzKv != null) {
            this.zzKv.zza(zziv);
        }
    }

    public final void zza(zzjl zzjl) throws RemoteException {
        this.zzKI.zzKk = zzjl;
        if (this.zzKv != null) {
            this.zzKI.zzd(this.zzKv);
        }
    }

    public final void zza(zzjo zzjo) throws RemoteException {
        this.zzKI.zztK = zzjo;
        if (this.zzKv != null) {
            this.zzKI.zzd(this.zzKv);
        }
    }

    public final void zza(zzke zzke) throws RemoteException {
        this.zzKI.zzKi = zzke;
        if (this.zzKv != null) {
            this.zzKI.zzd(this.zzKv);
        }
    }

    public final void zza(zzkk zzkk) throws RemoteException {
        abort();
        if (this.zzKv != null) {
            this.zzKv.zza(zzkk);
        }
    }

    public final void zza(zzky zzky) {
        throw new IllegalStateException("Unused method");
    }

    public final void zza(zzlx zzlx) {
        throw new IllegalStateException("getVideoController not implemented for interstitials");
    }

    public final void zza(zznh zznh) throws RemoteException {
        this.zzKI.zzKj = zznh;
        if (this.zzKv != null) {
            this.zzKI.zzd(this.zzKv);
        }
    }

    public final void zza(zzxg zzxg) throws RemoteException {
        zzafr.zzaT("setInAppPurchaseListener is deprecated and should not be called.");
    }

    public final void zza(zzxo zzxo, String str) throws RemoteException {
        zzafr.zzaT("setPlayStorePurchaseParams is deprecated and should not be called.");
    }

    public final boolean zza(zzir zzir) throws RemoteException {
        if (!zztl.zzi(zzir).contains("gw")) {
            abort();
        }
        if (zztl.zzi(zzir).contains("_skipMediation")) {
            abort();
        }
        if (zzir.zzzU != null) {
            abort();
        }
        if (this.zzKv != null) {
            return this.zzKv.zza(zzir);
        }
        zztl zzbN = zzbs.zzbN();
        if (zztl.zzi(zzir).contains("_ad")) {
            zzbN.zzb(zzir, this.zztV);
        }
        zzto zza = zzbN.zza(zzir, this.zztV);
        if (zza != null) {
            if (!zza.zzKz) {
                zza.load();
                zztp.zzeN().zzeR();
            } else {
                zztp.zzeN().zzeQ();
            }
            this.zzKv = zza.zzKv;
            zza.zzKx.zza(this.zzKI);
            this.zzKI.zzd(this.zzKv);
            return zza.zzKA;
        }
        abort();
        zztp.zzeN().zzeR();
        return this.zzKv.zza(zzir);
    }

    @Nullable
    public final String zzaI() throws RemoteException {
        if (this.zzKv != null) {
            return this.zzKv.zzaI();
        }
        return null;
    }

    @Nullable
    public final IObjectWrapper zzal() throws RemoteException {
        if (this.zzKv != null) {
            return this.zzKv.zzal();
        }
        return null;
    }

    @Nullable
    public final zziv zzam() throws RemoteException {
        if (this.zzKv != null) {
            return this.zzKv.zzam();
        }
        return null;
    }

    public final void zzao() throws RemoteException {
        if (this.zzKv != null) {
            this.zzKv.zzao();
        } else {
            zzafr.zzaT("Interstitial ad must be loaded before pingManualTrackingUrl().");
        }
    }

    public final zzke zzax() {
        throw new IllegalStateException("getIAppEventListener not implemented");
    }

    public final zzjo zzay() {
        throw new IllegalStateException("getIAdListener not implemented");
    }
}
