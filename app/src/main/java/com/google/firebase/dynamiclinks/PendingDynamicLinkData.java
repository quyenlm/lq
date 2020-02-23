package com.google.firebase.dynamiclinks;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.gms.common.util.zzi;
import com.google.android.gms.internal.zi;

public class PendingDynamicLinkData {
    private final zi zzcjR;

    public PendingDynamicLinkData(zi ziVar) {
        if (ziVar == null) {
            this.zzcjR = null;
            return;
        }
        if (ziVar.getClickTimestamp() == 0) {
            ziVar.zzaA(zzi.zzrY().currentTimeMillis());
        }
        this.zzcjR = ziVar;
    }

    protected PendingDynamicLinkData(String str, int i, long j, Uri uri) {
        this.zzcjR = new zi((String) null, str, i, j, (Bundle) null, uri);
    }

    private final Uri zzJK() {
        if (this.zzcjR == null) {
            return null;
        }
        return this.zzcjR.zzJK();
    }

    public long getClickTimestamp() {
        if (this.zzcjR == null) {
            return 0;
        }
        return this.zzcjR.getClickTimestamp();
    }

    public Uri getLink() {
        String zzJL;
        if (this.zzcjR == null || (zzJL = this.zzcjR.zzJL()) == null) {
            return null;
        }
        return Uri.parse(zzJL);
    }

    public int getMinimumAppVersion() {
        if (this.zzcjR == null) {
            return 0;
        }
        return this.zzcjR.zzJM();
    }

    public Intent getUpdateAppIntent(Context context) {
        if (getMinimumAppVersion() == 0) {
            return null;
        }
        try {
            if (context.getPackageManager().getPackageInfo(context.getApplicationContext().getPackageName(), 0).versionCode >= getMinimumAppVersion() || zzJK() == null) {
                return null;
            }
            return new Intent("android.intent.action.VIEW").setData(zzJK()).setPackage("com.android.vending");
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    public final Bundle zzJJ() {
        return this.zzcjR == null ? new Bundle() : this.zzcjR.zzJN();
    }
}
