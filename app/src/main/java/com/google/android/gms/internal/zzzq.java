package com.google.android.gms.internal;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.webkit.CookieManager;
import com.google.android.gms.ads.internal.zzbs;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

@zzzn
public final class zzzq extends zzafp implements zzzw {
    private final Context mContext;
    private zzaae zzMM;
    private zzub zzMu;
    private zzaai zzQR;
    /* access modifiers changed from: private */
    public Runnable zzQS;
    /* access modifiers changed from: private */
    public final Object zzQT = new Object();
    private final zzzp zzSm;
    /* access modifiers changed from: private */
    public final zzaaf zzSn;
    private final zzij zzSo;
    zzahp zzSp;

    public zzzq(Context context, zzaaf zzaaf, zzzp zzzp, zzij zzij) {
        this.zzSm = zzzp;
        this.mContext = context;
        this.zzSn = zzaaf;
        this.zzSo = zzij;
    }

    private final zziv zzb(zzaae zzaae) throws zzzt {
        boolean z = true;
        if (this.zzMM == null || this.zzMM.zzwn == null || this.zzMM.zzwn.size() <= 1) {
            z = false;
        }
        if (z && this.zzMu != null && !this.zzMu.zzMp) {
            return null;
        }
        if (this.zzQR.zzAw) {
            for (zziv zziv : zzaae.zzvX.zzAu) {
                if (zziv.zzAw) {
                    return new zziv(zziv, zzaae.zzvX.zzAu);
                }
            }
        }
        if (this.zzQR.zzTr == null) {
            throw new zzzt("The ad response must specify one of the supported ad sizes.", 0);
        }
        String[] split = this.zzQR.zzTr.split("x");
        if (split.length != 2) {
            String valueOf = String.valueOf(this.zzQR.zzTr);
            throw new zzzt(valueOf.length() != 0 ? "Invalid ad size format from the ad response: ".concat(valueOf) : new String("Invalid ad size format from the ad response: "), 0);
        }
        try {
            int parseInt = Integer.parseInt(split[0]);
            int parseInt2 = Integer.parseInt(split[1]);
            for (zziv zziv2 : zzaae.zzvX.zzAu) {
                float f = this.mContext.getResources().getDisplayMetrics().density;
                int i = zziv2.width == -1 ? (int) (((float) zziv2.widthPixels) / f) : zziv2.width;
                int i2 = zziv2.height == -2 ? (int) (((float) zziv2.heightPixels) / f) : zziv2.height;
                if (parseInt == i && parseInt2 == i2 && !zziv2.zzAw) {
                    return new zziv(zziv2, zzaae.zzvX.zzAu);
                }
            }
            String valueOf2 = String.valueOf(this.zzQR.zzTr);
            throw new zzzt(valueOf2.length() != 0 ? "The ad size from the ad response was not one of the requested sizes: ".concat(valueOf2) : new String("The ad size from the ad response was not one of the requested sizes: "), 0);
        } catch (NumberFormatException e) {
            String valueOf3 = String.valueOf(this.zzQR.zzTr);
            throw new zzzt(valueOf3.length() != 0 ? "Invalid ad size number from the ad response: ".concat(valueOf3) : new String("Invalid ad size number from the ad response: "), 0);
        }
    }

    /* access modifiers changed from: private */
    public final void zzd(int i, String str) {
        if (i == 3 || i == -1) {
            zzafr.zzaS(str);
        } else {
            zzafr.zzaT(str);
        }
        if (this.zzQR == null) {
            this.zzQR = new zzaai(i);
        } else {
            this.zzQR = new zzaai(i, this.zzQR.zzMg);
        }
        this.zzSm.zza(new zzafg(this.zzMM != null ? this.zzMM : new zzaae(this.zzSn, -1, (String) null, (String) null, (String) null), this.zzQR, this.zzMu, (zziv) null, i, -1, this.zzQR.zzTs, (JSONObject) null, this.zzSo));
    }

    public final void onStop() {
        synchronized (this.zzQT) {
            if (this.zzSp != null) {
                this.zzSp.cancel();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final zzahp zza(zzaje zzaje, zzajp<zzaae> zzajp) {
        Context context = this.mContext;
        if (new zzzv(context).zza(zzaje)) {
            zzafr.zzaC("Fetching ad response from local ad request service.");
            zzaab zzaab = new zzaab(context, zzajp, this);
            zzaab.zzgp();
            return zzaab;
        }
        zzafr.zzaC("Fetching ad response from remote ad request service.");
        zzji.zzds();
        if (zzaiy.zzX(context)) {
            return new zzaac(context, zzaje, zzajp, this);
        }
        zzafr.zzaT("Failed to connect to remote ad request service.");
        return null;
    }

    public final void zza(@NonNull zzaai zzaai) {
        JSONObject jSONObject;
        zzafr.zzaC("Received ad response.");
        this.zzQR = zzaai;
        long elapsedRealtime = zzbs.zzbF().elapsedRealtime();
        synchronized (this.zzQT) {
            this.zzSp = null;
        }
        zzbs.zzbD().zzf(this.mContext, this.zzQR.zzSV);
        if (((Boolean) zzbs.zzbL().zzd(zzmo.zzDY)).booleanValue()) {
            if (this.zzQR.zzTh) {
                zzbs.zzbD();
                Context context = this.mContext;
                String str = this.zzMM.zzvR;
                SharedPreferences sharedPreferences = context.getSharedPreferences("admob", 0);
                Set<String> stringSet = sharedPreferences.getStringSet("never_pool_slots", Collections.emptySet());
                if (!stringSet.contains(str)) {
                    HashSet hashSet = new HashSet(stringSet);
                    hashSet.add(str);
                    SharedPreferences.Editor edit = sharedPreferences.edit();
                    edit.putStringSet("never_pool_slots", hashSet);
                    edit.apply();
                }
            } else {
                zzbs.zzbD();
                Context context2 = this.mContext;
                String str2 = this.zzMM.zzvR;
                SharedPreferences sharedPreferences2 = context2.getSharedPreferences("admob", 0);
                Set<String> stringSet2 = sharedPreferences2.getStringSet("never_pool_slots", Collections.emptySet());
                if (stringSet2.contains(str2)) {
                    HashSet hashSet2 = new HashSet(stringSet2);
                    hashSet2.remove(str2);
                    SharedPreferences.Editor edit2 = sharedPreferences2.edit();
                    edit2.putStringSet("never_pool_slots", hashSet2);
                    edit2.apply();
                }
            }
        }
        try {
            if (this.zzQR.errorCode == -2 || this.zzQR.errorCode == -3) {
                if (this.zzQR.errorCode != -3) {
                    if (TextUtils.isEmpty(this.zzQR.body)) {
                        throw new zzzt("No fill from ad server.", 3);
                    }
                    zzbs.zzbD().zze(this.mContext, this.zzQR.zzSH);
                    if (this.zzQR.zzTo) {
                        this.zzMu = new zzub(this.zzQR.body);
                        zzbs.zzbD().zzz(this.zzMu.zzMe);
                    } else {
                        zzbs.zzbD().zzz(this.zzQR.zzMe);
                    }
                    if (!TextUtils.isEmpty(this.zzQR.zzSW)) {
                        if (((Boolean) zzbs.zzbL().zzd(zzmo.zzGf)).booleanValue()) {
                            zzafr.zzaC("Received cookie from server. Setting webview cookie in CookieManager.");
                            CookieManager zzS = zzbs.zzbB().zzS(this.mContext);
                            if (zzS != null) {
                                zzS.setCookie("googleads.g.doubleclick.net", this.zzQR.zzSW);
                            }
                        }
                    }
                }
                zziv zzb = this.zzMM.zzvX.zzAu != null ? zzb(this.zzMM) : null;
                zzbs.zzbD().zzx(this.zzQR.zzTy);
                zzbs.zzbD().zzy(this.zzQR.zzTL);
                if (!TextUtils.isEmpty(this.zzQR.zzTw)) {
                    try {
                        jSONObject = new JSONObject(this.zzQR.zzTw);
                    } catch (Exception e) {
                        zzafr.zzb("Error parsing the JSON for Active View.", e);
                    }
                    this.zzSm.zza(new zzafg(this.zzMM, this.zzQR, this.zzMu, zzb, -2, elapsedRealtime, this.zzQR.zzTs, jSONObject, this.zzSo));
                    zzagz.zzZr.removeCallbacks(this.zzQS);
                    return;
                }
                jSONObject = null;
                this.zzSm.zza(new zzafg(this.zzMM, this.zzQR, this.zzMu, zzb, -2, elapsedRealtime, this.zzQR.zzTs, jSONObject, this.zzSo));
                zzagz.zzZr.removeCallbacks(this.zzQS);
                return;
            }
            throw new zzzt(new StringBuilder(66).append("There was a problem getting an ad response. ErrorCode: ").append(this.zzQR.errorCode).toString(), this.zzQR.errorCode);
        } catch (JSONException e2) {
            zzafr.zzb("Could not parse mediation config.", e2);
            String valueOf = String.valueOf(this.zzQR.body);
            throw new zzzt(valueOf.length() != 0 ? "Could not parse mediation config: ".concat(valueOf) : new String("Could not parse mediation config: "), 0);
        } catch (zzzt e3) {
            zzd(e3.getErrorCode(), e3.getMessage());
            zzagz.zzZr.removeCallbacks(this.zzQS);
        }
    }

    public final void zzbd() {
        String string;
        zzafr.zzaC("AdLoaderBackgroundTask started.");
        this.zzQS = new zzzr(this);
        zzagz.zzZr.postDelayed(this.zzQS, ((Long) zzbs.zzbL().zzd(zzmo.zzEK)).longValue());
        long elapsedRealtime = zzbs.zzbF().elapsedRealtime();
        if (!((Boolean) zzbs.zzbL().zzd(zzmo.zzEI)).booleanValue() || this.zzSn.zzSz.extras == null || (string = this.zzSn.zzSz.extras.getString("_ad")) == null) {
            zzajt zzajt = new zzajt();
            zzagt.zza((Runnable) new zzzs(this, zzajt));
            String zzu = zzbs.zzbY().zzu(this.mContext);
            String zzv = zzbs.zzbY().zzv(this.mContext);
            String zzw = zzbs.zzbY().zzw(this.mContext);
            zzbs.zzbY().zzh(this.mContext, zzw);
            this.zzMM = new zzaae(this.zzSn, elapsedRealtime, zzu, zzv, zzw);
            zzajt.zzf(this.zzMM);
            return;
        }
        this.zzMM = new zzaae(this.zzSn, elapsedRealtime, (String) null, (String) null, (String) null);
        zza(zzabt.zza(this.mContext, this.zzMM, string));
    }
}
