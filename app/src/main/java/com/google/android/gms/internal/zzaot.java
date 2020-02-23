package com.google.android.gms.internal;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import com.tencent.midas.oversea.network.http.APNetworkManager2;

public final class zzaot extends zzamh {
    private boolean zzadF;
    private String zzaeH;
    private String zzaeI;
    private int zzagX;
    protected int zzahZ;
    protected boolean zzaiP;
    private boolean zzaiQ;

    public zzaot(zzamj zzamj) {
        super(zzamj);
    }

    /* access modifiers changed from: protected */
    public final void zzjD() {
        ApplicationInfo applicationInfo;
        int i;
        zzanw zzanw;
        Context context = getContext();
        try {
            applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 129);
        } catch (PackageManager.NameNotFoundException e) {
            zzd("PackageManager doesn't know about the app package", e);
            applicationInfo = null;
        }
        if (applicationInfo == null) {
            zzbr("Couldn't get ApplicationInfo to load global config");
            return;
        }
        Bundle bundle = applicationInfo.metaData;
        if (bundle != null && (i = bundle.getInt("com.google.android.gms.analytics.globalConfigResource")) > 0 && (zzanw = (zzanw) new zzanu(zzkp()).zzP(i)) != null) {
            zzbo("Loading global XML config values");
            if (zzanw.zzaeH != null) {
                String str = zzanw.zzaeH;
                this.zzaeH = str;
                zzb("XML config - app name", str);
            }
            if (zzanw.zzaeI != null) {
                String str2 = zzanw.zzaeI;
                this.zzaeI = str2;
                zzb("XML config - app version", str2);
            }
            if (zzanw.zzahY != null) {
                String lowerCase = zzanw.zzahY.toLowerCase();
                int i2 = "verbose".equals(lowerCase) ? 0 : APNetworkManager2.HTTP_KEY_OVERSEAINFO.equals(lowerCase) ? 1 : "warning".equals(lowerCase) ? 2 : "error".equals(lowerCase) ? 3 : -1;
                if (i2 >= 0) {
                    this.zzagX = i2;
                    zza("XML config - log level", Integer.valueOf(i2));
                }
            }
            if (zzanw.zzahZ >= 0) {
                int i3 = zzanw.zzahZ;
                this.zzahZ = i3;
                this.zzaiP = true;
                zzb("XML config - dispatch period (sec)", Integer.valueOf(i3));
            }
            if (zzanw.zzaia != -1) {
                boolean z = zzanw.zzaia == 1;
                this.zzadF = z;
                this.zzaiQ = true;
                zzb("XML config - dry run", Boolean.valueOf(z));
            }
        }
    }

    public final String zzjG() {
        zzkD();
        return this.zzaeH;
    }

    public final String zzjH() {
        zzkD();
        return this.zzaeI;
    }

    public final boolean zzmg() {
        zzkD();
        return false;
    }

    public final boolean zzmh() {
        zzkD();
        return this.zzaiQ;
    }

    public final boolean zzmi() {
        zzkD();
        return this.zzadF;
    }
}
