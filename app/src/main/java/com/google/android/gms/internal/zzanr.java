package com.google.android.gms.internal;

import android.os.Build;

public final class zzanr {
    public static int version() {
        try {
            return Integer.parseInt(Build.VERSION.SDK);
        } catch (NumberFormatException e) {
            zzaob.zzf("Invalid version number", Build.VERSION.SDK);
            return 0;
        }
    }
}
