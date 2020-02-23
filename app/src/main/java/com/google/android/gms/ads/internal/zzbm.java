package com.google.android.gms.ads.internal;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.webkit.WebView;
import com.google.android.gms.actions.SearchIntents;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.zzn;
import com.google.android.gms.internal.zzadd;
import com.google.android.gms.internal.zzafr;
import com.google.android.gms.internal.zzagt;
import com.google.android.gms.internal.zzaiy;
import com.google.android.gms.internal.zzaje;
import com.google.android.gms.internal.zzeu;
import com.google.android.gms.internal.zzev;
import com.google.android.gms.internal.zzir;
import com.google.android.gms.internal.zziv;
import com.google.android.gms.internal.zzji;
import com.google.android.gms.internal.zzjl;
import com.google.android.gms.internal.zzjo;
import com.google.android.gms.internal.zzka;
import com.google.android.gms.internal.zzke;
import com.google.android.gms.internal.zzkk;
import com.google.android.gms.internal.zzks;
import com.google.android.gms.internal.zzky;
import com.google.android.gms.internal.zzlx;
import com.google.android.gms.internal.zzmo;
import com.google.android.gms.internal.zznh;
import com.google.android.gms.internal.zzxg;
import com.google.android.gms.internal.zzxo;
import com.google.android.gms.internal.zzzn;
import java.util.Map;
import java.util.concurrent.Future;

@zzzn
public final class zzbm extends zzka {
    /* access modifiers changed from: private */
    public final Context mContext;
    /* access modifiers changed from: private */
    @Nullable
    public zzjo zztK;
    /* access modifiers changed from: private */
    public final zzaje zztW;
    private final zziv zzuZ;
    /* access modifiers changed from: private */
    public final Future<zzeu> zzva = zzagt.zza(new zzbp(this));
    private final zzbr zzvb;
    /* access modifiers changed from: private */
    @Nullable
    public WebView zzvc = new WebView(this.mContext);
    /* access modifiers changed from: private */
    @Nullable
    public zzeu zzvd;
    private AsyncTask<Void, Void, String> zzve;

    public zzbm(Context context, zziv zziv, String str, zzaje zzaje) {
        this.mContext = context;
        this.zztW = zzaje;
        this.zzuZ = zziv;
        this.zzvb = new zzbr(str);
        zzf(0);
        this.zzvc.setVerticalScrollBarEnabled(false);
        this.zzvc.getSettings().setJavaScriptEnabled(true);
        this.zzvc.setWebViewClient(new zzbn(this));
        this.zzvc.setOnTouchListener(new zzbo(this));
    }

    /* access modifiers changed from: private */
    public final String zzw(String str) {
        if (this.zzvd == null) {
            return str;
        }
        Uri parse = Uri.parse(str);
        try {
            parse = this.zzvd.zzc(parse, this.mContext);
        } catch (RemoteException e) {
            zzafr.zzc("Unable to process ad data", e);
        } catch (zzev e2) {
            zzafr.zzc("Unable to parse ad click url", e2);
        }
        return parse.toString();
    }

    /* access modifiers changed from: private */
    public final void zzx(String str) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse(str));
        this.mContext.startActivity(intent);
    }

    public final void destroy() throws RemoteException {
        zzbo.zzcz("destroy must be called on the main UI thread.");
        this.zzve.cancel(true);
        this.zzva.cancel(true);
        this.zzvc.destroy();
        this.zzvc = null;
    }

    public final String getAdUnitId() {
        throw new IllegalStateException("getAdUnitId not implemented");
    }

    @Nullable
    public final String getMediationAdapterClassName() throws RemoteException {
        return null;
    }

    @Nullable
    public final zzks getVideoController() {
        return null;
    }

    public final boolean isLoading() throws RemoteException {
        return false;
    }

    public final boolean isReady() throws RemoteException {
        return false;
    }

    public final void pause() throws RemoteException {
        zzbo.zzcz("pause must be called on the main UI thread.");
    }

    public final void resume() throws RemoteException {
        zzbo.zzcz("resume must be called on the main UI thread.");
    }

    public final void setImmersiveMode(boolean z) {
        throw new IllegalStateException("Unused method");
    }

    public final void setManualImpressionsEnabled(boolean z) throws RemoteException {
    }

    public final void setUserId(String str) throws RemoteException {
        throw new IllegalStateException("Unused method");
    }

    public final void showInterstitial() throws RemoteException {
        throw new IllegalStateException("Unused method");
    }

    public final void stopLoading() throws RemoteException {
    }

    public final void zza(zzadd zzadd) throws RemoteException {
        throw new IllegalStateException("Unused method");
    }

    public final void zza(zziv zziv) throws RemoteException {
        throw new IllegalStateException("AdSize must be set before initialization");
    }

    public final void zza(zzjl zzjl) throws RemoteException {
        throw new IllegalStateException("Unused method");
    }

    public final void zza(zzjo zzjo) throws RemoteException {
        this.zztK = zzjo;
    }

    public final void zza(zzke zzke) throws RemoteException {
        throw new IllegalStateException("Unused method");
    }

    public final void zza(zzkk zzkk) throws RemoteException {
        throw new IllegalStateException("Unused method");
    }

    public final void zza(zzky zzky) {
        throw new IllegalStateException("Unused method");
    }

    public final void zza(zzlx zzlx) {
        throw new IllegalStateException("Unused method");
    }

    public final void zza(zznh zznh) throws RemoteException {
        throw new IllegalStateException("Unused method");
    }

    public final void zza(zzxg zzxg) throws RemoteException {
        throw new IllegalStateException("Unused method");
    }

    public final void zza(zzxo zzxo, String str) throws RemoteException {
        throw new IllegalStateException("Unused method");
    }

    public final boolean zza(zzir zzir) throws RemoteException {
        zzbo.zzb(this.zzvc, (Object) "This Search Ad has already been torn down");
        this.zzvb.zza(zzir, this.zztW);
        this.zzve = new zzbq(this, (zzbn) null).execute(new Void[0]);
        return true;
    }

    @Nullable
    public final String zzaI() throws RemoteException {
        return null;
    }

    public final IObjectWrapper zzal() throws RemoteException {
        zzbo.zzcz("getAdFrame must be called on the main UI thread.");
        return zzn.zzw(this.zzvc);
    }

    public final zziv zzam() throws RemoteException {
        return this.zzuZ;
    }

    public final void zzao() throws RemoteException {
        throw new IllegalStateException("Unused method");
    }

    public final zzke zzax() {
        throw new IllegalStateException("getIAppEventListener not implemented");
    }

    public final zzjo zzay() {
        throw new IllegalStateException("getIAdListener not implemented");
    }

    /* access modifiers changed from: package-private */
    public final String zzbp() {
        Uri uri;
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https://").appendEncodedPath((String) zzbs.zzbL().zzd(zzmo.zzFV));
        builder.appendQueryParameter(SearchIntents.EXTRA_QUERY, this.zzvb.getQuery());
        builder.appendQueryParameter("pubId", this.zzvb.zzbs());
        Map<String, String> zzbt = this.zzvb.zzbt();
        for (String next : zzbt.keySet()) {
            builder.appendQueryParameter(next, zzbt.get(next));
        }
        Uri build = builder.build();
        if (this.zzvd != null) {
            try {
                uri = this.zzvd.zzb(build, this.mContext);
            } catch (RemoteException | zzev e) {
                zzafr.zzc("Unable to process ad data", e);
            }
            String valueOf = String.valueOf(zzbq());
            String valueOf2 = String.valueOf(uri.getEncodedQuery());
            return new StringBuilder(String.valueOf(valueOf).length() + 1 + String.valueOf(valueOf2).length()).append(valueOf).append("#").append(valueOf2).toString();
        }
        uri = build;
        String valueOf3 = String.valueOf(zzbq());
        String valueOf22 = String.valueOf(uri.getEncodedQuery());
        return new StringBuilder(String.valueOf(valueOf3).length() + 1 + String.valueOf(valueOf22).length()).append(valueOf3).append("#").append(valueOf22).toString();
    }

    /* access modifiers changed from: package-private */
    public final String zzbq() {
        String zzbr = this.zzvb.zzbr();
        String str = TextUtils.isEmpty(zzbr) ? "www.google.com" : zzbr;
        String valueOf = String.valueOf("https://");
        String str2 = (String) zzbs.zzbL().zzd(zzmo.zzFV);
        return new StringBuilder(String.valueOf(valueOf).length() + String.valueOf(str).length() + String.valueOf(str2).length()).append(valueOf).append(str).append(str2).toString();
    }

    /* access modifiers changed from: package-private */
    public final void zzf(int i) {
        if (this.zzvc != null) {
            this.zzvc.setLayoutParams(new ViewGroup.LayoutParams(-1, i));
        }
    }

    /* access modifiers changed from: package-private */
    public final int zzv(String str) {
        String queryParameter = Uri.parse(str).getQueryParameter("height");
        if (TextUtils.isEmpty(queryParameter)) {
            return 0;
        }
        try {
            zzji.zzds();
            return zzaiy.zzc(this.mContext, Integer.parseInt(queryParameter));
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
