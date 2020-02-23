package com.google.android.gms.internal;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.WorkerThread;
import android.text.TextUtils;
import com.facebook.internal.AnalyticsEvents;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.util.zze;
import com.google.firebase.iid.FirebaseInstanceId;
import java.math.BigInteger;
import java.util.Locale;

public final class zzcfg extends zzchj {
    private String mAppId;
    private String zzXB;
    private String zzaeH;
    private String zzaeI;
    private String zzboB;
    private long zzboF;
    private int zzbqC;
    private long zzbqD;
    private int zzbqE;

    zzcfg(zzcgl zzcgl) {
        super(zzcgl);
    }

    @WorkerThread
    private final String zzwK() {
        super.zzjC();
        try {
            return FirebaseInstanceId.getInstance().getId();
        } catch (IllegalStateException e) {
            super.zzwF().zzyz().log("Failed to retrieve Firebase Instance Id");
            return null;
        }
    }

    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    /* access modifiers changed from: package-private */
    public final String getGmpAppId() {
        zzkD();
        return this.zzXB;
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final zzceh zzdV(String str) {
        super.zzjC();
        String zzhl = zzhl();
        String gmpAppId = getGmpAppId();
        zzkD();
        String str2 = this.zzaeI;
        long zzyv = (long) zzyv();
        zzkD();
        String str3 = this.zzboB;
        long zzwP = zzcem.zzwP();
        zzkD();
        super.zzjC();
        if (this.zzbqD == 0) {
            this.zzbqD = this.zzboe.zzwB().zzI(super.getContext(), super.getContext().getPackageName());
        }
        long j = this.zzbqD;
        boolean isEnabled = this.zzboe.isEnabled();
        boolean z = !super.zzwG().zzbrC;
        String zzwK = zzwK();
        zzkD();
        long zzyY = this.zzboe.zzyY();
        zzkD();
        return new zzceh(zzhl, gmpAppId, str2, zzyv, str3, zzwP, j, str, isEnabled, z, zzwK, 0, zzyY, this.zzbqE);
    }

    /* access modifiers changed from: package-private */
    public final String zzhl() {
        zzkD();
        return this.mAppId;
    }

    public final /* bridge */ /* synthetic */ void zzjC() {
        super.zzjC();
    }

    /* access modifiers changed from: protected */
    public final void zzjD() {
        boolean z;
        int i = 1;
        String str = "unknown";
        String str2 = AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
        int i2 = Integer.MIN_VALUE;
        String str3 = AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
        String packageName = super.getContext().getPackageName();
        PackageManager packageManager = super.getContext().getPackageManager();
        if (packageManager == null) {
            super.zzwF().zzyx().zzj("PackageManager is null, app identity information might be inaccurate. appId", zzcfl.zzdZ(packageName));
        } else {
            try {
                str = packageManager.getInstallerPackageName(packageName);
            } catch (IllegalArgumentException e) {
                super.zzwF().zzyx().zzj("Error retrieving app installer package name. appId", zzcfl.zzdZ(packageName));
            }
            if (str == null) {
                str = "manual_install";
            } else if ("com.android.vending".equals(str)) {
                str = "";
            }
            try {
                PackageInfo packageInfo = packageManager.getPackageInfo(super.getContext().getPackageName(), 0);
                if (packageInfo != null) {
                    CharSequence applicationLabel = packageManager.getApplicationLabel(packageInfo.applicationInfo);
                    if (!TextUtils.isEmpty(applicationLabel)) {
                        str3 = applicationLabel.toString();
                    }
                    str2 = packageInfo.versionName;
                    i2 = packageInfo.versionCode;
                }
            } catch (PackageManager.NameNotFoundException e2) {
                super.zzwF().zzyx().zze("Error retrieving package info. appId, appName", zzcfl.zzdZ(packageName), str3);
            }
        }
        this.mAppId = packageName;
        this.zzboB = str;
        this.zzaeI = str2;
        this.zzbqC = i2;
        this.zzaeH = str3;
        this.zzbqD = 0;
        zzcem.zzxE();
        Status zzaz = zzbdm.zzaz(super.getContext());
        boolean z2 = zzaz != null && zzaz.isSuccess();
        if (!z2) {
            if (zzaz == null) {
                super.zzwF().zzyx().log("GoogleService failed to initialize (no status)");
            } else {
                super.zzwF().zzyx().zze("GoogleService failed to initialize, status", Integer.valueOf(zzaz.getStatusCode()), zzaz.getStatusMessage());
            }
        }
        if (z2) {
            Boolean zzdN = super.zzwH().zzdN("firebase_analytics_collection_enabled");
            if (super.zzwH().zzxF()) {
                super.zzwF().zzyB().log("Collection disabled with firebase_analytics_collection_deactivated=1");
                z = false;
            } else if (zzdN != null && !zzdN.booleanValue()) {
                super.zzwF().zzyB().log("Collection disabled with firebase_analytics_collection_enabled=0");
                z = false;
            } else if (zzdN != null || !zzcem.zzqB()) {
                super.zzwF().zzyD().log("Collection enabled");
                z = true;
            } else {
                super.zzwF().zzyB().log("Collection disabled with google_app_measurement_enable=0");
                z = false;
            }
        } else {
            z = false;
        }
        this.zzXB = "";
        this.zzboF = 0;
        zzcem.zzxE();
        try {
            String zzqA = zzbdm.zzqA();
            if (TextUtils.isEmpty(zzqA)) {
                zzqA = "";
            }
            this.zzXB = zzqA;
            if (z) {
                super.zzwF().zzyD().zze("App package, google app id", this.mAppId, this.zzXB);
            }
        } catch (IllegalStateException e3) {
            super.zzwF().zzyx().zze("getGoogleAppId or isMeasurementEnabled failed with exception. appId", zzcfl.zzdZ(packageName), e3);
        }
        if (Build.VERSION.SDK_INT >= 16) {
            if (!zzbgy.zzaN(super.getContext())) {
                i = 0;
            }
            this.zzbqE = i;
            return;
        }
        this.zzbqE = 0;
    }

    public final /* bridge */ /* synthetic */ zze zzkq() {
        return super.zzkq();
    }

    public final /* bridge */ /* synthetic */ zzcfj zzwA() {
        return super.zzwA();
    }

    public final /* bridge */ /* synthetic */ zzcjl zzwB() {
        return super.zzwB();
    }

    public final /* bridge */ /* synthetic */ zzcgf zzwC() {
        return super.zzwC();
    }

    public final /* bridge */ /* synthetic */ zzcja zzwD() {
        return super.zzwD();
    }

    public final /* bridge */ /* synthetic */ zzcgg zzwE() {
        return super.zzwE();
    }

    public final /* bridge */ /* synthetic */ zzcfl zzwF() {
        return super.zzwF();
    }

    public final /* bridge */ /* synthetic */ zzcfw zzwG() {
        return super.zzwG();
    }

    public final /* bridge */ /* synthetic */ zzcem zzwH() {
        return super.zzwH();
    }

    public final /* bridge */ /* synthetic */ void zzwo() {
        super.zzwo();
    }

    public final /* bridge */ /* synthetic */ void zzwp() {
        super.zzwp();
    }

    public final /* bridge */ /* synthetic */ void zzwq() {
        super.zzwq();
    }

    public final /* bridge */ /* synthetic */ zzcec zzwr() {
        return super.zzwr();
    }

    public final /* bridge */ /* synthetic */ zzcej zzws() {
        return super.zzws();
    }

    public final /* bridge */ /* synthetic */ zzchl zzwt() {
        return super.zzwt();
    }

    public final /* bridge */ /* synthetic */ zzcfg zzwu() {
        return super.zzwu();
    }

    public final /* bridge */ /* synthetic */ zzcet zzwv() {
        return super.zzwv();
    }

    public final /* bridge */ /* synthetic */ zzcid zzww() {
        return super.zzww();
    }

    public final /* bridge */ /* synthetic */ zzchz zzwx() {
        return super.zzwx();
    }

    public final /* bridge */ /* synthetic */ zzcfh zzwy() {
        return super.zzwy();
    }

    public final /* bridge */ /* synthetic */ zzcen zzwz() {
        return super.zzwz();
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final String zzyu() {
        byte[] bArr = new byte[16];
        super.zzwB().zzzt().nextBytes(bArr);
        return String.format(Locale.US, "%032x", new Object[]{new BigInteger(1, bArr)});
    }

    /* access modifiers changed from: package-private */
    public final int zzyv() {
        zzkD();
        return this.zzbqC;
    }
}
