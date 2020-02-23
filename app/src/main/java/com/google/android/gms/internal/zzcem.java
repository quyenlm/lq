package com.google.android.gms.internal;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.annotation.Nullable;
import android.support.annotation.Size;
import android.text.TextUtils;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.common.util.zzr;
import com.google.android.gms.common.zze;
import com.tencent.imsdk.expansion.downloader.Constants;
import com.tencent.imsdk.framework.consts.InnerErrorCode;
import java.lang.reflect.InvocationTargetException;

public final class zzcem extends zzchi {
    private static String zzbpm = String.valueOf(zze.GOOGLE_PLAY_SERVICES_VERSION_CODE / 1000).replaceAll("(\\d+)(\\d)(\\d\\d)", "$1.$2.$3");
    private Boolean zzagU;

    zzcem(zzcgl zzcgl) {
        super(zzcgl);
    }

    public static boolean zzqB() {
        return zzbdm.zzqB();
    }

    public static long zzwP() {
        return 11020;
    }

    static long zzxA() {
        return 61000;
    }

    static long zzxB() {
        return zzcfb.zzbqB.get().longValue();
    }

    public static String zzxC() {
        return "google_app_measurement.db";
    }

    static String zzxD() {
        return "google_app_measurement_local.db";
    }

    public static boolean zzxE() {
        return false;
    }

    public static long zzxG() {
        return zzcfb.zzbqy.get().longValue();
    }

    public static long zzxH() {
        return zzcfb.zzbqt.get().longValue();
    }

    public static long zzxI() {
        return zzcfb.zzbqu.get().longValue();
    }

    public static long zzxJ() {
        return 1000;
    }

    public static long zzxK() {
        return Math.max(0, zzcfb.zzbpX.get().longValue());
    }

    public static int zzxL() {
        return Math.max(0, zzcfb.zzbqd.get().intValue());
    }

    public static int zzxM() {
        return Math.max(1, zzcfb.zzbqe.get().intValue());
    }

    public static int zzxN() {
        return InnerErrorCode.SDK_ERROR_BASIC_XG;
    }

    public static String zzxO() {
        return zzcfb.zzbql.get();
    }

    public static long zzxP() {
        return zzcfb.zzbpY.get().longValue();
    }

    public static long zzxQ() {
        return Math.max(0, zzcfb.zzbqm.get().longValue());
    }

    public static long zzxR() {
        return Math.max(0, zzcfb.zzbqo.get().longValue());
    }

    public static long zzxS() {
        return Math.max(0, zzcfb.zzbqp.get().longValue());
    }

    public static long zzxT() {
        return Math.max(0, zzcfb.zzbqq.get().longValue());
    }

    public static long zzxU() {
        return Math.max(0, zzcfb.zzbqr.get().longValue());
    }

    public static long zzxV() {
        return Math.max(0, zzcfb.zzbqs.get().longValue());
    }

    public static long zzxW() {
        return zzcfb.zzbqn.get().longValue();
    }

    public static long zzxX() {
        return Math.max(0, zzcfb.zzbqv.get().longValue());
    }

    public static long zzxY() {
        return Math.max(0, zzcfb.zzbqw.get().longValue());
    }

    public static int zzxZ() {
        return Math.min(20, Math.max(0, zzcfb.zzbqx.get().intValue()));
    }

    static String zzxf() {
        return zzcfb.zzbpV.get();
    }

    public static int zzxg() {
        return 25;
    }

    public static int zzxh() {
        return 40;
    }

    public static int zzxi() {
        return 24;
    }

    static int zzxj() {
        return 40;
    }

    static int zzxk() {
        return 100;
    }

    static int zzxl() {
        return 256;
    }

    static int zzxm() {
        return 1000;
    }

    public static int zzxn() {
        return 36;
    }

    public static int zzxo() {
        return 2048;
    }

    static int zzxp() {
        return 500;
    }

    public static long zzxq() {
        return (long) zzcfb.zzbqf.get().intValue();
    }

    public static long zzxr() {
        return (long) zzcfb.zzbqh.get().intValue();
    }

    static int zzxs() {
        return 25;
    }

    static int zzxt() {
        return 1000;
    }

    static int zzxu() {
        return 25;
    }

    static int zzxv() {
        return 1000;
    }

    static long zzxw() {
        return 15552000000L;
    }

    static long zzxx() {
        return 15552000000L;
    }

    static long zzxy() {
        return 3600000;
    }

    static long zzxz() {
        return Constants.WATCHDOG_WAKE_TIMER;
    }

    public static boolean zzyb() {
        return zzcfb.zzbpU.get().booleanValue();
    }

    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    public final long zza(String str, zzcfc<Long> zzcfc) {
        if (str == null) {
            return zzcfc.get().longValue();
        }
        String zzM = super.zzwC().zzM(str, zzcfc.getKey());
        if (TextUtils.isEmpty(zzM)) {
            return zzcfc.get().longValue();
        }
        try {
            return zzcfc.get(Long.valueOf(Long.valueOf(zzM).longValue())).longValue();
        } catch (NumberFormatException e) {
            return zzcfc.get().longValue();
        }
    }

    public final int zzb(String str, zzcfc<Integer> zzcfc) {
        if (str == null) {
            return zzcfc.get().intValue();
        }
        String zzM = super.zzwC().zzM(str, zzcfc.getKey());
        if (TextUtils.isEmpty(zzM)) {
            return zzcfc.get().intValue();
        }
        try {
            return zzcfc.get(Integer.valueOf(Integer.valueOf(zzM).intValue())).intValue();
        } catch (NumberFormatException e) {
            return zzcfc.get().intValue();
        }
    }

    public final int zzdM(@Size(min = 1) String str) {
        return zzb(str, zzcfb.zzbqj);
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public final Boolean zzdN(@Size(min = 1) String str) {
        zzbo.zzcF(str);
        try {
            if (super.getContext().getPackageManager() == null) {
                super.zzwF().zzyx().log("Failed to load metadata: PackageManager is null");
                return null;
            }
            ApplicationInfo applicationInfo = zzbha.zzaP(super.getContext()).getApplicationInfo(super.getContext().getPackageName(), 128);
            if (applicationInfo == null) {
                super.zzwF().zzyx().log("Failed to load metadata: ApplicationInfo is null");
                return null;
            } else if (applicationInfo.metaData == null) {
                super.zzwF().zzyx().log("Failed to load metadata: Metadata bundle is null");
                return null;
            } else if (applicationInfo.metaData.containsKey(str)) {
                return Boolean.valueOf(applicationInfo.metaData.getBoolean(str));
            } else {
                return null;
            }
        } catch (PackageManager.NameNotFoundException e) {
            super.zzwF().zzyx().zzj("Failed to load metadata: Package name not found", e);
            return null;
        }
    }

    public final boolean zzdO(String str) {
        return "1".equals(super.zzwC().zzM(str, "gaia_collection_enabled"));
    }

    public final /* bridge */ /* synthetic */ void zzjC() {
        super.zzjC();
    }

    public final /* bridge */ /* synthetic */ com.google.android.gms.common.util.zze zzkq() {
        return super.zzkq();
    }

    public final boolean zzln() {
        if (this.zzagU == null) {
            synchronized (this) {
                if (this.zzagU == null) {
                    ApplicationInfo applicationInfo = super.getContext().getApplicationInfo();
                    String zzsf = zzr.zzsf();
                    if (applicationInfo != null) {
                        String str = applicationInfo.processName;
                        this.zzagU = Boolean.valueOf(str != null && str.equals(zzsf));
                    }
                    if (this.zzagU == null) {
                        this.zzagU = Boolean.TRUE;
                        super.zzwF().zzyx().log("My process not in the list of running processes");
                    }
                }
            }
        }
        return this.zzagU.booleanValue();
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

    public final boolean zzxF() {
        Boolean zzdN = zzdN("firebase_analytics_collection_deactivated");
        return zzdN != null && zzdN.booleanValue();
    }

    public final String zzya() {
        try {
            return (String) Class.forName("android.os.SystemProperties").getMethod("get", new Class[]{String.class, String.class}).invoke((Object) null, new Object[]{"debug.firebase.analytics.app", ""});
        } catch (ClassNotFoundException e) {
            super.zzwF().zzyx().zzj("Could not find SystemProperties class", e);
        } catch (NoSuchMethodException e2) {
            super.zzwF().zzyx().zzj("Could not find SystemProperties.get() method", e2);
        } catch (IllegalAccessException e3) {
            super.zzwF().zzyx().zzj("Could not access SystemProperties.get()", e3);
        } catch (InvocationTargetException e4) {
            super.zzwF().zzyx().zzj("SystemProperties.get() threw an exception", e4);
        }
        return "";
    }
}
