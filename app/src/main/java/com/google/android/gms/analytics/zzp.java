package com.google.android.gms.analytics;

import android.text.TextUtils;
import com.appsflyer.AppsFlyerLib;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveConstants;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.internal.zzalk;
import com.google.android.gms.internal.zzalx;
import com.google.android.gms.internal.zzami;
import com.google.android.gms.internal.zzamm;
import com.google.android.gms.internal.zzanx;
import com.google.android.gms.internal.zzaos;
import com.tencent.imsdk.framework.request.HttpRequestParams;
import com.vk.sdk.api.VKApiConst;
import java.util.HashMap;
import java.util.Map;

final class zzp implements Runnable {
    private /* synthetic */ String zzaeA;
    private /* synthetic */ Tracker zzaeB;
    private /* synthetic */ Map zzaeu;
    private /* synthetic */ boolean zzaev;
    private /* synthetic */ String zzaew;
    private /* synthetic */ long zzaex;
    private /* synthetic */ boolean zzaey;
    private /* synthetic */ boolean zzaez;

    zzp(Tracker tracker, Map map, boolean z, String str, long j, boolean z2, boolean z3, String str2) {
        this.zzaeB = tracker;
        this.zzaeu = map;
        this.zzaev = z;
        this.zzaew = str;
        this.zzaex = j;
        this.zzaey = z2;
        this.zzaez = z3;
        this.zzaeA = str2;
    }

    public final void run() {
        boolean z = true;
        if (this.zzaeB.zzaer.zzjE()) {
            this.zzaeu.put("sc", "start");
        }
        Map map = this.zzaeu;
        GoogleAnalytics zzku = this.zzaeB.zzku();
        zzbo.zzcG("getClientId can not be called from the main thread");
        zzaos.zzc(map, "cid", zzku.zzji().zzkJ().zzli());
        String str = (String) this.zzaeu.get("sf");
        if (str != null) {
            double zza = zzaos.zza(str, 100.0d);
            if (zzaos.zza(zza, (String) this.zzaeu.get("cid"))) {
                this.zzaeB.zzb("Sampling enabled. Hit sampled out. sample rate", Double.valueOf(zza));
                return;
            }
        }
        zzalx zzb = this.zzaeB.zzkA();
        if (this.zzaev) {
            zzaos.zzb((Map<String, String>) this.zzaeu, "ate", zzb.zzjZ());
            zzaos.zzb((Map<String, String>) this.zzaeu, HttpRequestParams.AD_ID, zzb.zzkg());
        } else {
            this.zzaeu.remove("ate");
            this.zzaeu.remove(HttpRequestParams.AD_ID);
        }
        zzalk zzkW = this.zzaeB.zzkB().zzkW();
        zzaos.zzb((Map<String, String>) this.zzaeu, "an", zzkW.zzjG());
        zzaos.zzb((Map<String, String>) this.zzaeu, "av", zzkW.zzjH());
        zzaos.zzb((Map<String, String>) this.zzaeu, AppsFlyerLib.ATTRIBUTION_ID_COLUMN_NAME, zzkW.zzhl());
        zzaos.zzb((Map<String, String>) this.zzaeu, "aiid", zzkW.zzjI());
        this.zzaeu.put(VKApiConst.VERSION, "1");
        this.zzaeu.put("_v", zzami.zzafL);
        zzaos.zzb((Map<String, String>) this.zzaeu, "ul", this.zzaeB.zzkC().zzlA().getLanguage());
        zzaos.zzb((Map<String, String>) this.zzaeu, "sr", this.zzaeB.zzkC().zzlB());
        if ((this.zzaew.equals("transaction") || this.zzaew.equals("item")) || this.zzaeB.zzaeq.zzlL()) {
            long zzbC = zzaos.zzbC((String) this.zzaeu.get("ht"));
            if (zzbC == 0) {
                zzbC = this.zzaex;
            }
            if (this.zzaey) {
                this.zzaeB.zzkr().zzc("Dry run enabled. Would have sent hit", new zzanx(this.zzaeB, this.zzaeu, zzbC, this.zzaez));
                return;
            }
            String str2 = (String) this.zzaeu.get("cid");
            HashMap hashMap = new HashMap();
            zzaos.zza((Map<String, String>) hashMap, GGLiveConstants.PARAM.UID, (Map<String, String>) this.zzaeu);
            zzaos.zza((Map<String, String>) hashMap, "an", (Map<String, String>) this.zzaeu);
            zzaos.zza((Map<String, String>) hashMap, AppsFlyerLib.ATTRIBUTION_ID_COLUMN_NAME, (Map<String, String>) this.zzaeu);
            zzaos.zza((Map<String, String>) hashMap, "av", (Map<String, String>) this.zzaeu);
            zzaos.zza((Map<String, String>) hashMap, "aiid", (Map<String, String>) this.zzaeu);
            String str3 = this.zzaeA;
            if (TextUtils.isEmpty((CharSequence) this.zzaeu.get(HttpRequestParams.AD_ID))) {
                z = false;
            }
            this.zzaeu.put("_s", String.valueOf(this.zzaeB.zzkv().zza(new zzamm(0, str2, str3, z, 0, hashMap))));
            this.zzaeB.zzkv().zza(new zzanx(this.zzaeB, this.zzaeu, zzbC, this.zzaez));
            return;
        }
        this.zzaeB.zzkr().zze(this.zzaeu, "Too many hits sent too quickly, rate limiting invoked");
    }
}
