package com.google.android.gms.internal;

public final class zzcai {
    public static String zzbc(int i) {
        switch (i) {
            case 0:
                return "DAILY";
            case 1:
                return "WEEKLY";
            case 2:
                return "ALL_TIME";
            default:
                throw new IllegalArgumentException(new StringBuilder(29).append("Unknown time span ").append(i).toString());
        }
    }
}
