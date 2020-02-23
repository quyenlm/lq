package com.google.android.gms.internal;

import android.content.Context;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.android.gms.ads.internal.zzbs;
import com.google.android.gms.ads.internal.zzbt;
import com.google.android.gms.ads.internal.zzd;
import com.google.android.gms.ads.internal.zzv;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.dynamic.zzn;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

@zzzn
public final class zzacs extends zzd implements zzadt {
    private static zzacs zzWr;
    private static final zzup zzWs = new zzup();
    private final Map<String, zzadz> zzWt = new HashMap();
    private boolean zzWu;
    private boolean zzuj;

    public zzacs(Context context, zzv zzv, zziv zziv, zzuq zzuq, zzaje zzaje) {
        super(context, zziv, (String) null, zzuq, zzaje, zzv);
        zzWr = this;
    }

    private static zzafg zzc(zzafg zzafg) {
        zzafr.v("Creating mediation ad response for non-mediated rewarded ad.");
        try {
            String jSONObject = zzabt.zzb(zzafg.zzXY).toString();
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("pubid", zzafg.zzUj.zzvR);
            return new zzafg(zzafg.zzUj, zzafg.zzXY, new zzub(Arrays.asList(new zzua[]{new zzua(jSONObject, (String) null, Arrays.asList(new String[]{"com.google.ads.mediation.admob.AdMobAdapter"}), (String) null, (String) null, Collections.emptyList(), Collections.emptyList(), jSONObject2.toString(), (String) null, Collections.emptyList(), Collections.emptyList(), (String) null, (String) null, (String) null, (List<String>) null, (String) null, Collections.emptyList(), (String) null)}), ((Long) zzbs.zzbL().zzd(zzmo.zzEL)).longValue(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), false, "", -1, 0, 1, (String) null, 0, -1, -1, false), zzafg.zzvX, zzafg.errorCode, zzafg.zzXR, zzafg.zzXS, zzafg.zzXL, zzafg.zzXX);
        } catch (JSONException e) {
            zzafr.zzb("Unable to generate ad state for non-mediated rewarded video.", e);
            return new zzafg(zzafg.zzUj, zzafg.zzXY, (zzub) null, zzafg.zzvX, 0, zzafg.zzXR, zzafg.zzXS, zzafg.zzXL, zzafg.zzXX);
        }
    }

    public static zzacs zzgO() {
        return zzWr;
    }

    public final void destroy() {
        zzbo.zzcz("destroy must be called on the main UI thread.");
        for (String next : this.zzWt.keySet()) {
            try {
                zzadz zzadz = this.zzWt.get(next);
                if (!(zzadz == null || zzadz.zzgW() == null)) {
                    zzadz.zzgW().destroy();
                }
            } catch (RemoteException e) {
                String valueOf = String.valueOf(next);
                zzafr.zzaT(valueOf.length() != 0 ? "Fail to destroy adapter: ".concat(valueOf) : new String("Fail to destroy adapter: "));
            }
        }
    }

    public final boolean isLoaded() {
        zzbo.zzcz("isLoaded must be called on the main UI thread.");
        return this.zzsP.zzvV == null && this.zzsP.zzvW == null && this.zzsP.zzvY != null && !this.zzWu;
    }

    public final void onContextChanged(@NonNull Context context) {
        for (zzadz zzgW : this.zzWt.values()) {
            try {
                zzgW.zzgW().zzk(zzn.zzw(context));
            } catch (RemoteException e) {
                zzafr.zzb("Unable to call Adapter.onContextChanged.", e);
            }
        }
    }

    public final void onRewardedVideoAdClosed() {
        zzap();
    }

    public final void onRewardedVideoAdLeftApplication() {
        zzaq();
    }

    public final void onRewardedVideoAdOpened() {
        zza(this.zzsP.zzvY, false);
        zzar();
    }

    public final void onRewardedVideoStarted() {
        if (!(this.zzsP.zzvY == null || this.zzsP.zzvY.zzMG == null)) {
            zzbs.zzbS();
            zzuj.zza(this.zzsP.zzqD, this.zzsP.zzvT.zzaP, this.zzsP.zzvY, this.zzsP.zzvR, false, this.zzsP.zzvY.zzMG.zzLQ);
        }
        zzav();
    }

    public final void pause() {
        zzbo.zzcz("pause must be called on the main UI thread.");
        for (String next : this.zzWt.keySet()) {
            try {
                zzadz zzadz = this.zzWt.get(next);
                if (!(zzadz == null || zzadz.zzgW() == null)) {
                    zzadz.zzgW().pause();
                }
            } catch (RemoteException e) {
                String valueOf = String.valueOf(next);
                zzafr.zzaT(valueOf.length() != 0 ? "Fail to pause adapter: ".concat(valueOf) : new String("Fail to pause adapter: "));
            }
        }
    }

    public final void resume() {
        zzbo.zzcz("resume must be called on the main UI thread.");
        for (String next : this.zzWt.keySet()) {
            try {
                zzadz zzadz = this.zzWt.get(next);
                if (!(zzadz == null || zzadz.zzgW() == null)) {
                    zzadz.zzgW().resume();
                }
            } catch (RemoteException e) {
                String valueOf = String.valueOf(next);
                zzafr.zzaT(valueOf.length() != 0 ? "Fail to resume adapter: ".concat(valueOf) : new String("Fail to resume adapter: "));
            }
        }
    }

    public final void setImmersiveMode(boolean z) {
        zzbo.zzcz("setImmersiveMode must be called on the main UI thread.");
        this.zzuj = z;
    }

    public final void zza(zzadj zzadj) {
        zzbo.zzcz("loadAd must be called on the main UI thread.");
        if (TextUtils.isEmpty(zzadj.zzvR)) {
            zzafr.zzaT("Invalid ad unit id. Aborting.");
            zzagz.zzZr.post(new zzact(this));
            return;
        }
        this.zzWu = false;
        this.zzsP.zzvR = zzadj.zzvR;
        super.zza(zzadj.zzSz);
    }

    public final void zza(zzafg zzafg, zznb zznb) {
        if (zzafg.errorCode != -2) {
            zzagz.zzZr.post(new zzacu(this, zzafg));
            return;
        }
        this.zzsP.zzvZ = zzafg;
        if (zzafg.zzXN == null) {
            this.zzsP.zzvZ = zzc(zzafg);
        }
        this.zzsP.zzwt = 0;
        zzbt zzbt = this.zzsP;
        zzbs.zzby();
        zzadw zzadw = new zzadw(this.zzsP.zzqD, this.zzsP.zzvZ, this);
        String valueOf = String.valueOf(zzadw.getClass().getName());
        zzafr.zzaC(valueOf.length() != 0 ? "AdRenderer: ".concat(valueOf) : new String("AdRenderer: "));
        zzadw.zzgp();
        zzbt.zzvW = zzadw;
    }

    public final boolean zza(zzaff zzaff, zzaff zzaff2) {
        return true;
    }

    /* access modifiers changed from: protected */
    public final boolean zza(zzir zzir, zzaff zzaff, boolean z) {
        return false;
    }

    /* access modifiers changed from: protected */
    public final void zzap() {
        this.zzsP.zzvY = null;
        super.zzap();
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x0035  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x003e  */
    @android.support.annotation.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.google.android.gms.internal.zzadz zzav(java.lang.String r6) {
        /*
            r5 = this;
            java.util.Map<java.lang.String, com.google.android.gms.internal.zzadz> r0 = r5.zzWt
            java.lang.Object r0 = r0.get(r6)
            com.google.android.gms.internal.zzadz r0 = (com.google.android.gms.internal.zzadz) r0
            if (r0 != 0) goto L_0x0026
            com.google.android.gms.internal.zzuq r1 = r5.zzsX     // Catch:{ Exception -> 0x0027 }
            java.lang.String r2 = "com.google.ads.mediation.admob.AdMobAdapter"
            boolean r2 = r2.equals(r6)     // Catch:{ Exception -> 0x0027 }
            if (r2 == 0) goto L_0x0047
            com.google.android.gms.internal.zzup r1 = zzWs     // Catch:{ Exception -> 0x0027 }
            r2 = r1
        L_0x0017:
            com.google.android.gms.internal.zzadz r1 = new com.google.android.gms.internal.zzadz     // Catch:{ Exception -> 0x0027 }
            com.google.android.gms.internal.zzut r2 = r2.zzah(r6)     // Catch:{ Exception -> 0x0027 }
            r1.<init>(r2, r5)     // Catch:{ Exception -> 0x0027 }
            java.util.Map<java.lang.String, com.google.android.gms.internal.zzadz> r0 = r5.zzWt     // Catch:{ Exception -> 0x0044 }
            r0.put(r6, r1)     // Catch:{ Exception -> 0x0044 }
            r0 = r1
        L_0x0026:
            return r0
        L_0x0027:
            r2 = move-exception
            r1 = r0
        L_0x0029:
            java.lang.String r3 = "Fail to instantiate adapter "
            java.lang.String r0 = java.lang.String.valueOf(r6)
            int r4 = r0.length()
            if (r4 == 0) goto L_0x003e
            java.lang.String r0 = r3.concat(r0)
        L_0x0039:
            com.google.android.gms.internal.zzafr.zzc(r0, r2)
            r0 = r1
            goto L_0x0026
        L_0x003e:
            java.lang.String r0 = new java.lang.String
            r0.<init>(r3)
            goto L_0x0039
        L_0x0044:
            r0 = move-exception
            r2 = r0
            goto L_0x0029
        L_0x0047:
            r2 = r1
            goto L_0x0017
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzacs.zzav(java.lang.String):com.google.android.gms.internal.zzadz");
    }

    public final void zzc(@Nullable zzaee zzaee) {
        if (!(this.zzsP.zzvY == null || this.zzsP.zzvY.zzMG == null)) {
            zzbs.zzbS();
            zzuj.zza(this.zzsP.zzqD, this.zzsP.zzvT.zzaP, this.zzsP.zzvY, this.zzsP.zzvR, false, this.zzsP.zzvY.zzMG.zzLR);
        }
        if (!(this.zzsP.zzvY == null || this.zzsP.zzvY.zzXN == null || TextUtils.isEmpty(this.zzsP.zzvY.zzXN.zzMh))) {
            zzaee = new zzaee(this.zzsP.zzvY.zzXN.zzMh, this.zzsP.zzvY.zzXN.zzMi);
        }
        zza(zzaee);
    }

    public final void zzgP() {
        zzbo.zzcz("showAd must be called on the main UI thread.");
        if (!isLoaded()) {
            zzafr.zzaT("The reward video has not loaded.");
            return;
        }
        this.zzWu = true;
        zzadz zzav = zzav(this.zzsP.zzvY.zzMI);
        if (zzav != null && zzav.zzgW() != null) {
            try {
                zzav.zzgW().setImmersiveMode(this.zzuj);
                zzav.zzgW().showVideo();
            } catch (RemoteException e) {
                zzafr.zzc("Could not call showVideo.", e);
            }
        }
    }

    public final void zzgQ() {
        onAdClicked();
    }
}
