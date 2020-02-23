package com.google.android.gms.internal;

import android.util.Log;

public final class zzeq {
    private static boolean zzdd = false;

    public static String zza(String str, String str2, Object obj) {
        if (!zzd(6)) {
            return "";
        }
        String zza = zza(str, str2, obj);
        Log.e("ctxmgr", zza);
        return zza;
    }

    public static String zza(String str, String str2, Throwable th) {
        if (!zzd(6)) {
            return "";
        }
        String zza = zza(str, str2, new Object[0]);
        Log.e("ctxmgr", zza, th);
        return zza;
    }

    private static String zza(String str, String str2, Object... objArr) {
        if (objArr.length == 0) {
            return String.format("[%s]%s", new Object[]{str, str2});
        }
        String valueOf = String.valueOf(String.format("[%s]", new Object[]{str}));
        String valueOf2 = String.valueOf(String.format(str2, objArr));
        return valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
    }

    public static String zzd(String str, String str2) {
        if (!zzd(6)) {
            return "";
        }
        String zza = zza(str, str2, new Object[0]);
        Log.e("ctxmgr", zza);
        return zza;
    }

    private static boolean zzd(int i) {
        return Log.isLoggable("ctxmgr", 6);
    }
}
