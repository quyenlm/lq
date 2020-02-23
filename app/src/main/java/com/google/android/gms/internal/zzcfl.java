package com.google.android.gms.internal;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.measurement.AppMeasurement;

public final class zzcfl extends zzchj {
    private final String zzaIb = zzcem.zzxf();
    private final long zzboC = zzcem.zzwP();
    private final char zzbqL;
    private final zzcfn zzbqM;
    private final zzcfn zzbqN;
    private final zzcfn zzbqO;
    private final zzcfn zzbqP;
    private final zzcfn zzbqQ;
    private final zzcfn zzbqR;
    private final zzcfn zzbqS;
    private final zzcfn zzbqT;
    private final zzcfn zzbqU;

    zzcfl(zzcgl zzcgl) {
        super(zzcgl);
        if (super.zzwH().zzln()) {
            zzcem.zzxE();
            this.zzbqL = 'C';
        } else {
            zzcem.zzxE();
            this.zzbqL = 'c';
        }
        this.zzbqM = new zzcfn(this, 6, false, false);
        this.zzbqN = new zzcfn(this, 6, true, false);
        this.zzbqO = new zzcfn(this, 6, false, true);
        this.zzbqP = new zzcfn(this, 5, false, false);
        this.zzbqQ = new zzcfn(this, 5, true, false);
        this.zzbqR = new zzcfn(this, 5, false, true);
        this.zzbqS = new zzcfn(this, 4, false, false);
        this.zzbqT = new zzcfn(this, 3, false, false);
        this.zzbqU = new zzcfn(this, 2, false, false);
    }

    private static String zza(boolean z, String str, Object obj, Object obj2, Object obj3) {
        if (str == null) {
            str = "";
        }
        String zzc = zzc(z, obj);
        String zzc2 = zzc(z, obj2);
        String zzc3 = zzc(z, obj3);
        StringBuilder sb = new StringBuilder();
        String str2 = "";
        if (!TextUtils.isEmpty(str)) {
            sb.append(str);
            str2 = ": ";
        }
        if (!TextUtils.isEmpty(zzc)) {
            sb.append(str2);
            sb.append(zzc);
            str2 = ", ";
        }
        if (!TextUtils.isEmpty(zzc2)) {
            sb.append(str2);
            sb.append(zzc2);
            str2 = ", ";
        }
        if (!TextUtils.isEmpty(zzc3)) {
            sb.append(str2);
            sb.append(zzc3);
        }
        return sb.toString();
    }

    private static String zzc(boolean z, Object obj) {
        StackTraceElement stackTraceElement;
        String className;
        if (obj == null) {
            return "";
        }
        Object valueOf = obj instanceof Integer ? Long.valueOf((long) ((Integer) obj).intValue()) : obj;
        if (valueOf instanceof Long) {
            if (!z) {
                return String.valueOf(valueOf);
            }
            if (Math.abs(((Long) valueOf).longValue()) < 100) {
                return String.valueOf(valueOf);
            }
            String str = String.valueOf(valueOf).charAt(0) == '-' ? "-" : "";
            String valueOf2 = String.valueOf(Math.abs(((Long) valueOf).longValue()));
            return new StringBuilder(String.valueOf(str).length() + 43 + String.valueOf(str).length()).append(str).append(Math.round(Math.pow(10.0d, (double) (valueOf2.length() - 1)))).append("...").append(str).append(Math.round(Math.pow(10.0d, (double) valueOf2.length()) - 1.0d)).toString();
        } else if (valueOf instanceof Boolean) {
            return String.valueOf(valueOf);
        } else {
            if (!(valueOf instanceof Throwable)) {
                return valueOf instanceof zzcfo ? ((zzcfo) valueOf).zzbqZ : z ? "-" : String.valueOf(valueOf);
            }
            Throwable th = (Throwable) valueOf;
            StringBuilder sb = new StringBuilder(z ? th.getClass().getName() : th.toString());
            String zzea = zzea(AppMeasurement.class.getCanonicalName());
            String zzea2 = zzea(zzcgl.class.getCanonicalName());
            StackTraceElement[] stackTrace = th.getStackTrace();
            int length = stackTrace.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                stackTraceElement = stackTrace[i];
                if (!stackTraceElement.isNativeMethod() && (className = stackTraceElement.getClassName()) != null) {
                    String zzea3 = zzea(className);
                    if (zzea3.equals(zzea) || zzea3.equals(zzea2)) {
                        sb.append(": ");
                        sb.append(stackTraceElement);
                    }
                }
                i++;
            }
            sb.append(": ");
            sb.append(stackTraceElement);
            return sb.toString();
        }
    }

    protected static Object zzdZ(String str) {
        if (str == null) {
            return null;
        }
        return new zzcfo(str);
    }

    private static String zzea(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        int lastIndexOf = str.lastIndexOf(46);
        return lastIndexOf != -1 ? str.substring(0, lastIndexOf) : str;
    }

    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    /* access modifiers changed from: protected */
    public final void zza(int i, boolean z, boolean z2, String str, Object obj, Object obj2, Object obj3) {
        if (!z && zzz(i)) {
            zzk(i, zza(false, str, obj, obj2, obj3));
        }
        if (!z2 && i >= 5) {
            zzbo.zzu(str);
            zzcgg zzyR = this.zzboe.zzyR();
            if (zzyR == null) {
                zzk(6, "Scheduler not set. Not logging error/warn");
            } else if (!zzyR.isInitialized()) {
                zzk(6, "Scheduler not initialized. Not logging error/warn");
            } else {
                int i2 = i < 0 ? 0 : i;
                if (i2 >= 9) {
                    i2 = 8;
                }
                String valueOf = String.valueOf("2");
                char charAt = "01VDIWEA?".charAt(i2);
                char c = this.zzbqL;
                long j = this.zzboC;
                String valueOf2 = String.valueOf(zza(true, str, obj, obj2, obj3));
                String sb = new StringBuilder(String.valueOf(valueOf).length() + 23 + String.valueOf(valueOf2).length()).append(valueOf).append(charAt).append(c).append(j).append(":").append(valueOf2).toString();
                if (sb.length() > 1024) {
                    sb = str.substring(0, 1024);
                }
                zzyR.zzj(new zzcfm(this, sb));
            }
        }
    }

    public final /* bridge */ /* synthetic */ void zzjC() {
        super.zzjC();
    }

    /* access modifiers changed from: protected */
    public final void zzjD() {
    }

    /* access modifiers changed from: protected */
    public final void zzk(int i, String str) {
        Log.println(i, this.zzaIb, str);
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

    public final zzcfn zzyA() {
        return this.zzbqR;
    }

    public final zzcfn zzyB() {
        return this.zzbqS;
    }

    public final zzcfn zzyC() {
        return this.zzbqT;
    }

    public final zzcfn zzyD() {
        return this.zzbqU;
    }

    public final String zzyE() {
        Pair<String, Long> zzmb = super.zzwG().zzbrj.zzmb();
        if (zzmb == null || zzmb == zzcfw.zzbri) {
            return null;
        }
        String valueOf = String.valueOf(String.valueOf(zzmb.second));
        String str = (String) zzmb.first;
        return new StringBuilder(String.valueOf(valueOf).length() + 1 + String.valueOf(str).length()).append(valueOf).append(":").append(str).toString();
    }

    public final zzcfn zzyx() {
        return this.zzbqM;
    }

    public final zzcfn zzyy() {
        return this.zzbqN;
    }

    public final zzcfn zzyz() {
        return this.zzbqP;
    }

    /* access modifiers changed from: protected */
    public final boolean zzz(int i) {
        return Log.isLoggable(this.zzaIb, i);
    }
}
