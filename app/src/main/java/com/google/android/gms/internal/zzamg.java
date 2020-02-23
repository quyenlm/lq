package com.google.android.gms.internal;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.zzl;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.common.util.zze;

public class zzamg {
    private final zzamj zzafJ;

    protected zzamg(zzamj zzamj) {
        zzbo.zzu(zzamj);
        this.zzafJ = zzamj;
    }

    private final void zza(int i, String str, Object obj, Object obj2, Object obj3) {
        zzaoc zzaoc = null;
        if (this.zzafJ != null) {
            zzaoc = this.zzafJ.zzkF();
        }
        if (zzaoc != null) {
            String str2 = zzans.zzahg.get();
            if (Log.isLoggable(str2, i)) {
                Log.println(i, str2, zzaoc.zzc(str, obj, obj2, obj3));
            }
            if (i >= 5) {
                zzaoc.zzb(i, str, obj, obj2, obj3);
                return;
            }
            return;
        }
        String str3 = zzans.zzahg.get();
        if (Log.isLoggable(str3, i)) {
            Log.println(i, str3, zzc(str, obj, obj2, obj3));
        }
    }

    protected static String zzc(String str, Object obj, Object obj2, Object obj3) {
        if (str == null) {
            str = "";
        }
        String zzi = zzi(obj);
        String zzi2 = zzi(obj2);
        String zzi3 = zzi(obj3);
        StringBuilder sb = new StringBuilder();
        String str2 = "";
        if (!TextUtils.isEmpty(str)) {
            sb.append(str);
            str2 = ": ";
        }
        if (!TextUtils.isEmpty(zzi)) {
            sb.append(str2);
            sb.append(zzi);
            str2 = ", ";
        }
        if (!TextUtils.isEmpty(zzi2)) {
            sb.append(str2);
            sb.append(zzi2);
            str2 = ", ";
        }
        if (!TextUtils.isEmpty(zzi3)) {
            sb.append(str2);
            sb.append(zzi3);
        }
        return sb.toString();
    }

    public static boolean zzhM() {
        return Log.isLoggable(zzans.zzahg.get(), 2);
    }

    private static String zzi(Object obj) {
        return obj == null ? "" : obj instanceof String ? (String) obj : obj instanceof Boolean ? obj == Boolean.TRUE ? "true" : "false" : obj instanceof Throwable ? ((Throwable) obj).toString() : obj.toString();
    }

    /* access modifiers changed from: protected */
    public final Context getContext() {
        return this.zzafJ.getContext();
    }

    public final void zza(String str, Object obj) {
        zza(2, str, obj, (Object) null, (Object) null);
    }

    public final void zza(String str, Object obj, Object obj2) {
        zza(2, str, obj, obj2, (Object) null);
    }

    public final void zza(String str, Object obj, Object obj2, Object obj3) {
        zza(3, str, obj, obj2, obj3);
    }

    public final void zzb(String str, Object obj) {
        zza(3, str, obj, (Object) null, (Object) null);
    }

    public final void zzb(String str, Object obj, Object obj2) {
        zza(3, str, obj, obj2, (Object) null);
    }

    public final void zzb(String str, Object obj, Object obj2, Object obj3) {
        zza(5, str, obj, obj2, obj3);
    }

    public final void zzbo(String str) {
        zza(2, str, (Object) null, (Object) null, (Object) null);
    }

    public final void zzbp(String str) {
        zza(3, str, (Object) null, (Object) null, (Object) null);
    }

    public final void zzbq(String str) {
        zza(4, str, (Object) null, (Object) null, (Object) null);
    }

    public final void zzbr(String str) {
        zza(5, str, (Object) null, (Object) null, (Object) null);
    }

    public final void zzbs(String str) {
        zza(6, str, (Object) null, (Object) null, (Object) null);
    }

    public final void zzc(String str, Object obj) {
        zza(4, str, obj, (Object) null, (Object) null);
    }

    public final void zzc(String str, Object obj, Object obj2) {
        zza(5, str, obj, obj2, (Object) null);
    }

    public final void zzd(String str, Object obj) {
        zza(5, str, obj, (Object) null, (Object) null);
    }

    public final void zzd(String str, Object obj, Object obj2) {
        zza(6, str, obj, obj2, (Object) null);
    }

    public final void zze(String str, Object obj) {
        zza(6, str, obj, (Object) null, (Object) null);
    }

    /* access modifiers changed from: protected */
    public final zzalx zzkA() {
        return this.zzafJ.zzkI();
    }

    /* access modifiers changed from: protected */
    public final zzamu zzkB() {
        return this.zzafJ.zzkB();
    }

    /* access modifiers changed from: protected */
    public final zzano zzkC() {
        return this.zzafJ.zzkC();
    }

    public final zzamj zzkp() {
        return this.zzafJ;
    }

    /* access modifiers changed from: protected */
    public final zze zzkq() {
        return this.zzafJ.zzkq();
    }

    /* access modifiers changed from: protected */
    public final zzaoc zzkr() {
        return this.zzafJ.zzkr();
    }

    /* access modifiers changed from: protected */
    public final zzank zzks() {
        return this.zzafJ.zzks();
    }

    /* access modifiers changed from: protected */
    public final zzl zzkt() {
        return this.zzafJ.zzkt();
    }

    public final GoogleAnalytics zzku() {
        return this.zzafJ.zzkG();
    }

    /* access modifiers changed from: protected */
    public final zzaly zzkv() {
        return this.zzafJ.zzkv();
    }

    /* access modifiers changed from: protected */
    public final zzanp zzkw() {
        return this.zzafJ.zzkw();
    }

    /* access modifiers changed from: protected */
    public final zzaot zzkx() {
        return this.zzafJ.zzkx();
    }

    /* access modifiers changed from: protected */
    public final zzaog zzky() {
        return this.zzafJ.zzky();
    }

    /* access modifiers changed from: protected */
    public final zzanb zzkz() {
        return this.zzafJ.zzkJ();
    }
}
