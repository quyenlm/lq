package com.google.android.gms.tagmanager;

import android.content.Context;
import android.content.pm.PackageManager;
import com.google.android.gms.internal.zzbf;
import com.google.android.gms.internal.zzbr;
import java.util.Map;

final class zzl extends zzbr {
    private static final String ID = zzbf.APP_VERSION_NAME.toString();
    private final Context mContext;

    public zzl(Context context) {
        super(ID, new String[0]);
        this.mContext = context;
    }

    public final boolean zzAE() {
        return true;
    }

    public final zzbr zzo(Map<String, zzbr> map) {
        try {
            return zzgk.zzI(this.mContext.getPackageManager().getPackageInfo(this.mContext.getPackageName(), 0).versionName);
        } catch (PackageManager.NameNotFoundException e) {
            String valueOf = String.valueOf(this.mContext.getPackageName());
            String valueOf2 = String.valueOf(e.getMessage());
            zzdj.e(new StringBuilder(String.valueOf(valueOf).length() + 25 + String.valueOf(valueOf2).length()).append("Package name ").append(valueOf).append(" not found. ").append(valueOf2).toString());
            return zzgk.zzCh();
        }
    }
}
