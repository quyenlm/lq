package com.google.android.gms.internal;

import android.content.DialogInterface;
import android.content.Intent;

final class zzcvr implements DialogInterface.OnClickListener {
    private /* synthetic */ zzcvq zzbIC;

    zzcvr(zzcvq zzcvq) {
        this.zzbIC = zzcvq;
    }

    public final void onClick(DialogInterface dialogInterface, int i) {
        String packageName = this.zzbIC.zzapC.getPackageName();
        Intent launchIntentForPackage = this.zzbIC.zzapC.getPackageManager().getLaunchIntentForPackage(packageName);
        if (launchIntentForPackage != null) {
            String valueOf = String.valueOf(packageName);
            zzcvk.zzaS(valueOf.length() != 0 ? "Invoke the launch activity for package name: ".concat(valueOf) : new String("Invoke the launch activity for package name: "));
            this.zzbIC.zzapC.startActivity(launchIntentForPackage);
            return;
        }
        String valueOf2 = String.valueOf(packageName);
        zzcvk.zzaT(valueOf2.length() != 0 ? "No launch activity found for package name: ".concat(valueOf2) : new String("No launch activity found for package name: "));
    }
}
