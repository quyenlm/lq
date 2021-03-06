package com.google.android.gms.internal;

import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.internal.zzbo;
import java.util.Locale;

public final class zzayo {
    private static boolean zzayv = false;
    private String mTag;
    private final boolean zzayw;
    private boolean zzayx;
    private boolean zzayy;
    private String zzayz;

    public zzayo(String str) {
        this(str, false);
    }

    private zzayo(String str, boolean z) {
        zzbo.zzh(str, "The log tag cannot be null or empty.");
        this.mTag = str;
        this.zzayw = str.length() <= 23;
        this.zzayx = false;
        this.zzayy = false;
    }

    private String zzg(String str, Object... objArr) {
        if (objArr.length != 0) {
            str = String.format(Locale.ROOT, str, objArr);
        }
        if (TextUtils.isEmpty(this.zzayz)) {
            return str;
        }
        String valueOf = String.valueOf(this.zzayz);
        String valueOf2 = String.valueOf(str);
        return valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
    }

    private final boolean zzoL() {
        return this.zzayx || (this.zzayw && Log.isLoggable(this.mTag, 3));
    }

    public final void zza(Throwable th, String str, Object... objArr) {
        Log.e(this.mTag, zzg(str, objArr), th);
    }

    public final void zzaa(boolean z) {
        this.zzayx = true;
    }

    public final void zzb(String str, Object... objArr) {
        if (zzoL()) {
            Log.d(this.mTag, zzg(str, objArr));
        }
    }

    public final void zzb(Throwable th, String str, Object... objArr) {
        if (zzoL()) {
            Log.d(this.mTag, zzg(str, objArr), th);
        }
    }

    public final void zzc(String str, Object... objArr) {
        Log.e(this.mTag, zzg(str, objArr));
    }

    public final void zzc(Throwable th, String str, Object... objArr) {
        Log.w(this.mTag, zzg(str, objArr), th);
    }

    public final void zzcn(String str) {
        String format;
        if (TextUtils.isEmpty(str)) {
            format = null;
        } else {
            format = String.format("[%s] ", new Object[]{str});
        }
        this.zzayz = format;
    }

    public final void zze(String str, Object... objArr) {
        Log.i(this.mTag, zzg(str, objArr));
    }

    public final void zzf(String str, Object... objArr) {
        Log.w(this.mTag, zzg(str, objArr));
    }
}
