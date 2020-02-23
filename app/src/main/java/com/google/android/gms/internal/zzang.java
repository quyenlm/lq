package com.google.android.gms.internal;

public enum zzang {
    zzagQ,
    GZIP;

    public static zzang zzby(String str) {
        return "GZIP".equalsIgnoreCase(str) ? GZIP : zzagQ;
    }
}
