package com.google.android.gms.tagmanager;

import android.os.Build;
import com.google.android.gms.internal.zzbf;
import com.google.android.gms.internal.zzbr;
import java.util.Map;

final class zzbd extends zzbr {
    private static final String ID = zzbf.DEVICE_NAME.toString();

    public zzbd() {
        super(ID, new String[0]);
    }

    public final boolean zzAE() {
        return true;
    }

    public final zzbr zzo(Map<String, zzbr> map) {
        String str = Build.MANUFACTURER;
        String str2 = Build.MODEL;
        if (!str2.startsWith(str) && !str.equals("unknown")) {
            str2 = new StringBuilder(String.valueOf(str).length() + 1 + String.valueOf(str2).length()).append(str).append(" ").append(str2).toString();
        }
        return zzgk.zzI(str2);
    }
}
