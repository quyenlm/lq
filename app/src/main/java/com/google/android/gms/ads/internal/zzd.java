package com.google.android.gms.ads.internal;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import com.google.android.gms.ads.internal.overlay.zzw;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.internal.zzaaf;
import com.google.android.gms.internal.zzaaz;
import com.google.android.gms.internal.zzaff;
import com.google.android.gms.internal.zzafh;
import com.google.android.gms.internal.zzafj;
import com.google.android.gms.internal.zzafp;
import com.google.android.gms.internal.zzafr;
import com.google.android.gms.internal.zzagt;
import com.google.android.gms.internal.zzagz;
import com.google.android.gms.internal.zzahe;
import com.google.android.gms.internal.zzaje;
import com.google.android.gms.internal.zzajm;
import com.google.android.gms.internal.zzaka;
import com.google.android.gms.internal.zzbha;
import com.google.android.gms.internal.zzgz;
import com.google.android.gms.internal.zzij;
import com.google.android.gms.internal.zzir;
import com.google.android.gms.internal.zziv;
import com.google.android.gms.internal.zzky;
import com.google.android.gms.internal.zzmo;
import com.google.android.gms.internal.zznb;
import com.google.android.gms.internal.zzon;
import com.google.android.gms.internal.zzpj;
import com.google.android.gms.internal.zzpt;
import com.google.android.gms.internal.zztp;
import com.google.android.gms.internal.zzuc;
import com.google.android.gms.internal.zzuj;
import com.google.android.gms.internal.zzuq;
import com.google.android.gms.internal.zzzn;
import com.google.android.gms.internal.zzzq;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@zzzn
public abstract class zzd extends zza implements zzw, zzbl, zzuc {
    protected final zzuq zzsX;
    private transient boolean zzsY;

    public zzd(Context context, zziv zziv, String str, zzuq zzuq, zzaje zzaje, zzv zzv) {
        this(new zzbt(context, zziv, str, zzaje), zzuq, (zzbi) null, zzv);
    }

    private zzd(zzbt zzbt, zzuq zzuq, @Nullable zzbi zzbi, zzv zzv) {
        super(zzbt, (zzbi) null, zzv);
        this.zzsX = zzuq;
        this.zzsY = false;
    }

    private final zzaaf zza(zzir zzir, Bundle bundle, zzafj zzafj) {
        PackageInfo packageInfo;
        JSONArray optJSONArray;
        ApplicationInfo applicationInfo = this.zzsP.zzqD.getApplicationInfo();
        try {
            packageInfo = zzbha.zzaP(this.zzsP.zzqD).getPackageInfo(applicationInfo.packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
        }
        DisplayMetrics displayMetrics = this.zzsP.zzqD.getResources().getDisplayMetrics();
        Bundle bundle2 = null;
        if (!(this.zzsP.zzvU == null || this.zzsP.zzvU.getParent() == null)) {
            int[] iArr = new int[2];
            this.zzsP.zzvU.getLocationOnScreen(iArr);
            int i = iArr[0];
            int i2 = iArr[1];
            int width = this.zzsP.zzvU.getWidth();
            int height = this.zzsP.zzvU.getHeight();
            int i3 = 0;
            if (this.zzsP.zzvU.isShown() && i + width > 0 && i2 + height > 0 && i <= displayMetrics.widthPixels && i2 <= displayMetrics.heightPixels) {
                i3 = 1;
            }
            bundle2 = new Bundle(5);
            bundle2.putInt("x", i);
            bundle2.putInt("y", i2);
            bundle2.putInt("width", width);
            bundle2.putInt("height", height);
            bundle2.putInt("visible", i3);
        }
        String zzhp = zzbs.zzbD().zzhp();
        this.zzsP.zzwa = new zzafh(zzhp, this.zzsP.zzvR);
        this.zzsP.zzwa.zzo(zzir);
        zzbs.zzbz();
        String zza = zzagz.zza(this.zzsP.zzqD, (View) this.zzsP.zzvU, this.zzsP.zzvX);
        long j = 0;
        if (this.zzsP.zzwe != null) {
            try {
                j = this.zzsP.zzwe.getValue();
            } catch (RemoteException e2) {
                zzafr.zzaT("Cannot get correlation id, default to 0.");
            }
        }
        String uuid = UUID.randomUUID().toString();
        Bundle zza2 = zzbs.zzbD().zza(this.zzsP.zzqD, this, zzhp);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        int i4 = 0;
        while (true) {
            int i5 = i4;
            if (i5 >= this.zzsP.zzwi.size()) {
                break;
            }
            String keyAt = this.zzsP.zzwi.keyAt(i5);
            arrayList.add(keyAt);
            if (this.zzsP.zzwh.containsKey(keyAt) && this.zzsP.zzwh.get(keyAt) != null) {
                arrayList2.add(keyAt);
            }
            i4 = i5 + 1;
        }
        zzajm zza3 = zzagt.zza(new zze(this));
        zzajm zza4 = zzagt.zza(new zzf(this));
        String str = null;
        if (zzafj != null) {
            str = zzafj.zzhk();
        }
        String str2 = null;
        if (this.zzsP.zzwq != null && this.zzsP.zzwq.size() > 0) {
            int i6 = 0;
            if (packageInfo != null) {
                i6 = packageInfo.versionCode;
            }
            if (i6 > zzbs.zzbD().zzhA()) {
                zzbs.zzbD().zzhF();
                zzbs.zzbD().zzx(i6);
            } else {
                JSONObject zzhE = zzbs.zzbD().zzhE();
                if (!(zzhE == null || (optJSONArray = zzhE.optJSONArray(this.zzsP.zzvR)) == null)) {
                    str2 = optJSONArray.toString();
                }
            }
        }
        zziv zziv = this.zzsP.zzvX;
        String str3 = this.zzsP.zzvR;
        String sessionId = zzbs.zzbD().getSessionId();
        zzaje zzaje = this.zzsP.zzvT;
        List<String> list = this.zzsP.zzwq;
        boolean zzhs = zzbs.zzbD().zzhs();
        int i7 = displayMetrics.widthPixels;
        int i8 = displayMetrics.heightPixels;
        float f = displayMetrics.density;
        List<String> zzdJ = zzmo.zzdJ();
        String str4 = this.zzsP.zzvQ;
        zzon zzon = this.zzsP.zzwj;
        String zzce = this.zzsP.zzce();
        zzbs.zzbz();
        float zzbf = zzagz.zzbf();
        zzbs.zzbz();
        boolean zzbh = zzagz.zzbh();
        zzbs.zzbz();
        int zzN = zzagz.zzN(this.zzsP.zzqD);
        zzbs.zzbz();
        int zzp = zzagz.zzp(this.zzsP.zzvU);
        boolean z = this.zzsP.zzqD instanceof Activity;
        boolean zzhx = zzbs.zzbD().zzhx();
        boolean zzhC = zzbs.zzbD().zzhC();
        int zzeE = zzbs.zzbW().zzeE();
        zzbs.zzbz();
        Bundle zzhS = zzagz.zzhS();
        String zzib = zzbs.zzbH().zzib();
        zzky zzky = this.zzsP.zzwl;
        boolean zzic = zzbs.zzbH().zzic();
        Bundle asBundle = zztp.zzeN().asBundle();
        zzbs.zzbD();
        Context context = this.zzsP.zzqD;
        return new zzaaf(bundle2, zzir, zziv, str3, applicationInfo, packageInfo, zzhp, sessionId, zzaje, zza2, list, arrayList, bundle, zzhs, i7, i8, f, zza, j, uuid, zzdJ, str4, zzon, zzce, zzbf, zzbh, zzN, zzp, z, zzhx, zza3, str, zzhC, zzeE, zzhS, zzib, zzky, zzic, asBundle, context.getSharedPreferences("admob", 0).getStringSet("never_pool_slots", Collections.emptySet()).contains(this.zzsP.zzvR), zza4, this.zzsP.zzwn, str2, arrayList2);
    }

    public final String getMediationAdapterClassName() {
        if (this.zzsP.zzvY == null) {
            return null;
        }
        return this.zzsP.zzvY.zzMI;
    }

    public void onAdClicked() {
        if (this.zzsP.zzvY == null) {
            zzafr.zzaT("Ad state was null when trying to ping click URLs.");
            return;
        }
        if (!(this.zzsP.zzvY.zzXN == null || this.zzsP.zzvY.zzXN.zzMa == null)) {
            zzbs.zzbS();
            zzuj.zza(this.zzsP.zzqD, this.zzsP.zzvT.zzaP, this.zzsP.zzvY, this.zzsP.zzvR, false, zzb(this.zzsP.zzvY.zzXN.zzMa));
        }
        if (!(this.zzsP.zzvY.zzMG == null || this.zzsP.zzvY.zzMG.zzLM == null)) {
            zzbs.zzbS();
            zzuj.zza(this.zzsP.zzqD, this.zzsP.zzvT.zzaP, this.zzsP.zzvY, this.zzsP.zzvR, false, this.zzsP.zzvY.zzMG.zzLM);
        }
        super.onAdClicked();
    }

    public final void onPause() {
        this.zzsR.zzi(this.zzsP.zzvY);
    }

    public final void onResume() {
        this.zzsR.zzj(this.zzsP.zzvY);
    }

    public void pause() {
        zzbo.zzcz("pause must be called on the main UI thread.");
        if (!(this.zzsP.zzvY == null || this.zzsP.zzvY.zzPg == null || !this.zzsP.zzcc())) {
            zzbs.zzbB();
            zzahe.zzk(this.zzsP.zzvY.zzPg);
        }
        if (!(this.zzsP.zzvY == null || this.zzsP.zzvY.zzMH == null)) {
            try {
                this.zzsP.zzvY.zzMH.pause();
            } catch (RemoteException e) {
                zzafr.zzaT("Could not pause mediation adapter.");
            }
        }
        this.zzsR.zzi(this.zzsP.zzvY);
        this.zzsO.pause();
    }

    public final void recordImpression() {
        zza(this.zzsP.zzvY, false);
    }

    public void resume() {
        zzbo.zzcz("resume must be called on the main UI thread.");
        zzaka zzaka = null;
        if (!(this.zzsP.zzvY == null || this.zzsP.zzvY.zzPg == null)) {
            zzaka = this.zzsP.zzvY.zzPg;
        }
        if (zzaka != null && this.zzsP.zzcc()) {
            zzbs.zzbB();
            zzahe.zzl(this.zzsP.zzvY.zzPg);
        }
        if (!(this.zzsP.zzvY == null || this.zzsP.zzvY.zzMH == null)) {
            try {
                this.zzsP.zzvY.zzMH.resume();
            } catch (RemoteException e) {
                zzafr.zzaT("Could not resume mediation adapter.");
            }
        }
        if (zzaka == null || !zzaka.zziD()) {
            this.zzsO.resume();
        }
        this.zzsR.zzj(this.zzsP.zzvY);
    }

    public void showInterstitial() {
        throw new IllegalStateException("showInterstitial is not supported for current ad type");
    }

    /* access modifiers changed from: protected */
    public void zza(@Nullable zzaff zzaff, boolean z) {
        if (zzaff == null) {
            zzafr.zzaT("Ad state was null when trying to ping impression URLs.");
            return;
        }
        if (zzaff == null) {
            zzafr.zzaT("Ad state was null when trying to ping impression URLs.");
        } else {
            zzafr.zzaC("Pinging Impression URLs.");
            if (this.zzsP.zzwa != null) {
                this.zzsP.zzwa.zzhc();
            }
            if (zzaff.zzMb != null && !zzaff.zzXU) {
                zzbs.zzbz();
                zzagz.zza(this.zzsP.zzqD, this.zzsP.zzvT.zzaP, zzb(zzaff.zzMb));
                zzaff.zzXU = true;
            }
        }
        if (!(zzaff.zzXN == null || zzaff.zzXN.zzMb == null)) {
            zzbs.zzbS();
            zzuj.zza(this.zzsP.zzqD, this.zzsP.zzvT.zzaP, zzaff, this.zzsP.zzvR, z, zzb(zzaff.zzXN.zzMb));
        }
        if (zzaff.zzMG != null && zzaff.zzMG.zzLN != null) {
            zzbs.zzbS();
            zzuj.zza(this.zzsP.zzqD, this.zzsP.zzvT.zzaP, zzaff, this.zzsP.zzvR, z, zzaff.zzMG.zzLN);
        }
    }

    public final void zza(zzpj zzpj, String str) {
        String str2;
        zzpt zzpt = null;
        if (zzpj != null) {
            try {
                str2 = zzpj.getCustomTemplateId();
            } catch (RemoteException e) {
                zzafr.zzc("Unable to call onCustomClick.", e);
                return;
            }
        } else {
            str2 = null;
        }
        if (!(this.zzsP.zzwh == null || str2 == null)) {
            zzpt = this.zzsP.zzwh.get(str2);
        }
        if (zzpt == null) {
            zzafr.zzaT("Mediation adapter invoked onCustomClick but no listeners were set.");
        } else {
            zzpt.zzb(zzpj, str);
        }
    }

    /* access modifiers changed from: package-private */
    public final boolean zza(zzaff zzaff) {
        zzir zzir;
        boolean z = false;
        if (this.zzsQ != null) {
            zzir = this.zzsQ;
            this.zzsQ = null;
        } else {
            zzir = zzaff.zzSz;
            if (zzir.extras != null) {
                z = zzir.extras.getBoolean("_noRefresh", false);
            }
        }
        return zza(zzir, zzaff, z);
    }

    /* access modifiers changed from: protected */
    public boolean zza(@Nullable zzaff zzaff, zzaff zzaff2) {
        int i;
        int i2;
        if (!(zzaff == null || zzaff.zzMJ == null)) {
            zzaff.zzMJ.zza((zzuc) null);
        }
        if (zzaff2.zzMJ != null) {
            zzaff2.zzMJ.zza((zzuc) this);
        }
        if (zzaff2.zzXN != null) {
            int i3 = zzaff2.zzXN.zzMn;
            i = zzaff2.zzXN.zzMo;
            i2 = i3;
        } else {
            i = 0;
            i2 = 0;
        }
        this.zzsP.zzwr.zzg(i2, i);
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean zza(zzir zzir, zzaff zzaff, boolean z) {
        if (!z && this.zzsP.zzcc()) {
            if (zzaff.zzMg > 0) {
                this.zzsO.zza(zzir, zzaff.zzMg);
            } else if (zzaff.zzXN != null && zzaff.zzXN.zzMg > 0) {
                this.zzsO.zza(zzir, zzaff.zzXN.zzMg);
            } else if (!zzaff.zzTo && zzaff.errorCode == 2) {
                this.zzsO.zzg(zzir);
            }
        }
        return this.zzsO.zzbo();
    }

    public boolean zza(zzir zzir, zznb zznb) {
        zzafj zzafj;
        if (!zzaz()) {
            return false;
        }
        zzbs.zzbz();
        zzgz zzA = zzbs.zzbD().zzA(this.zzsP.zzqD);
        Bundle zza = zzA == null ? null : zzagz.zza(zzA);
        this.zzsO.cancel();
        this.zzsP.zzwt = 0;
        if (((Boolean) zzbs.zzbL().zzd(zzmo.zzFO)).booleanValue()) {
            zzafj = zzbs.zzbD().zzhD();
            zzbs.zzbV().zza(this.zzsP.zzqD, this.zzsP.zzvT, false, zzafj, zzafj != null ? zzafj.zzhl() : null, this.zzsP.zzvR, (Runnable) null);
        } else {
            zzafj = null;
        }
        zzaaf zza2 = zza(zzir, zza, zzafj);
        zznb.zzh("seq_num", zza2.zzSC);
        zznb.zzh("request_id", zza2.zzSM);
        zznb.zzh("session_id", zza2.zzSD);
        if (zza2.zzSA != null) {
            zznb.zzh("app_version", String.valueOf(zza2.zzSA.versionCode));
        }
        zzbt zzbt = this.zzsP;
        zzbs.zzbv();
        Context context = this.zzsP.zzqD;
        zzij zzij = this.zzsS.zztp;
        zzafp zzaaz = zza2.zzSz.extras.getBundle("sdk_less_server_data") != null ? new zzaaz(context, zza2, this, zzij) : new zzzq(context, zza2, this, zzij);
        zzaaz.zzhL();
        zzbt.zzvV = zzaaz;
        return true;
    }

    public void zzaA() {
        this.zzsY = false;
        zzap();
        this.zzsP.zzwa.zzhe();
    }

    public void zzaB() {
        this.zzsY = true;
        zzar();
    }

    public void zzaC() {
        onAdClicked();
    }

    public final void zzaD() {
        zzaA();
    }

    public final void zzaE() {
        zzaq();
    }

    public final void zzaF() {
        zzaB();
    }

    public final void zzaG() {
        if (this.zzsP.zzvY != null) {
            String str = this.zzsP.zzvY.zzMI;
            zzafr.zzaT(new StringBuilder(String.valueOf(str).length() + 74).append("Mediation adapter ").append(str).append(" refreshed, but mediation adapters should never refresh.").toString());
        }
        zza(this.zzsP.zzvY, true);
        zzas();
    }

    public void zzaH() {
        recordImpression();
    }

    public final String zzaI() {
        if (this.zzsP.zzvY == null) {
            return null;
        }
        String str = this.zzsP.zzvY.zzMI;
        if (!("com.google.android.gms.ads.mediation.customevent.CustomEventAdapter".equals(str) || "com.google.ads.mediation.customevent.CustomEventAdapter".equals(str)) || this.zzsP.zzvY.zzMG == null) {
            return str;
        }
        try {
            return new JSONObject(this.zzsP.zzvY.zzMG.zzLP).getString("class_name");
        } catch (NullPointerException | JSONException e) {
            return str;
        }
    }

    public final void zzaJ() {
        zzbs.zzbz();
        zzagz.runOnUiThread(new zzg(this));
    }

    public final void zzaK() {
        zzbs.zzbz();
        zzagz.runOnUiThread(new zzh(this));
    }

    /* access modifiers changed from: protected */
    public boolean zzaz() {
        zzbs.zzbz();
        if (zzagz.zzc(this.zzsP.zzqD, this.zzsP.zzqD.getPackageName(), "android.permission.INTERNET")) {
            zzbs.zzbz();
            return zzagz.zzD(this.zzsP.zzqD);
        }
    }

    public final void zzb(zzaff zzaff) {
        super.zzb(zzaff);
        if (zzaff.zzMG != null) {
            zzafr.zzaC("Disable the debug gesture detector on the mediation ad frame.");
            if (this.zzsP.zzvU != null) {
                this.zzsP.zzvU.zzci();
            }
            zzafr.zzaC("Pinging network fill URLs.");
            zzbs.zzbS();
            zzuj.zza(this.zzsP.zzqD, this.zzsP.zzvT.zzaP, zzaff, this.zzsP.zzvR, false, zzaff.zzMG.zzLO);
            if (!(zzaff.zzXN == null || zzaff.zzXN.zzMd == null || zzaff.zzXN.zzMd.size() <= 0)) {
                zzafr.zzaC("Pinging urls remotely");
                zzbs.zzbz().zza(this.zzsP.zzqD, zzaff.zzXN.zzMd);
            }
        } else {
            zzafr.zzaC("Enable the debug gesture detector on the admob ad frame.");
            if (this.zzsP.zzvU != null) {
                this.zzsP.zzvU.zzch();
            }
        }
        if (zzaff.errorCode == 3 && zzaff.zzXN != null && zzaff.zzXN.zzMc != null) {
            zzafr.zzaC("Pinging no fill URLs.");
            zzbs.zzbS();
            zzuj.zza(this.zzsP.zzqD, this.zzsP.zzvT.zzaP, zzaff, this.zzsP.zzvR, false, zzaff.zzXN.zzMc);
        }
    }

    /* access modifiers changed from: protected */
    public final boolean zzb(zzir zzir) {
        return super.zzb(zzir) && !this.zzsY;
    }

    public final void zze(String str, String str2) {
        onAppEvent(str, str2);
    }
}
