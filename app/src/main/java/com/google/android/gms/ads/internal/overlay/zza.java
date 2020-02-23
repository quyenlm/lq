package com.google.android.gms.ads.internal.overlay;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.appsflyer.share.Constants;
import com.google.android.gms.ads.internal.zzbs;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.internal.zzafr;
import com.google.android.gms.internal.zzagz;
import com.google.android.gms.internal.zzmo;
import com.google.android.gms.internal.zzzn;

@zzzn
public final class zza {
    private static boolean zza(Context context, Intent intent, zzag zzag) {
        try {
            String valueOf = String.valueOf(intent.toURI());
            zzafr.v(valueOf.length() != 0 ? "Launching an intent: ".concat(valueOf) : new String("Launching an intent: "));
            zzbs.zzbz();
            zzagz.zzb(context, intent);
            if (zzag != null) {
                zzag.zzan();
            }
            return true;
        } catch (ActivityNotFoundException e) {
            zzafr.zzaT(e.getMessage());
            return false;
        }
    }

    public static boolean zza(Context context, zzc zzc, zzag zzag) {
        int i;
        if (zzc == null) {
            zzafr.zzaT("No intent data for launcher overlay.");
            return false;
        }
        zzmo.initialize(context);
        if (zzc.intent != null) {
            return zza(context, zzc.intent, zzag);
        }
        Intent intent = new Intent();
        if (TextUtils.isEmpty(zzc.url)) {
            zzafr.zzaT("Open GMSG did not contain a URL.");
            return false;
        }
        if (!TextUtils.isEmpty(zzc.mimeType)) {
            intent.setDataAndType(Uri.parse(zzc.url), zzc.mimeType);
        } else {
            intent.setData(Uri.parse(zzc.url));
        }
        intent.setAction("android.intent.action.VIEW");
        if (!TextUtils.isEmpty(zzc.packageName)) {
            intent.setPackage(zzc.packageName);
        }
        if (!TextUtils.isEmpty(zzc.zzOh)) {
            String[] split = zzc.zzOh.split(Constants.URL_PATH_DELIMITER, 2);
            if (split.length < 2) {
                String valueOf = String.valueOf(zzc.zzOh);
                zzafr.zzaT(valueOf.length() != 0 ? "Could not parse component name from open GMSG: ".concat(valueOf) : new String("Could not parse component name from open GMSG: "));
                return false;
            }
            intent.setClassName(split[0], split[1]);
        }
        String str = zzc.zzOi;
        if (!TextUtils.isEmpty(str)) {
            try {
                i = Integer.parseInt(str);
            } catch (NumberFormatException e) {
                zzafr.zzaT("Could not parse intent flags.");
                i = 0;
            }
            intent.addFlags(i);
        }
        if (((Boolean) zzbs.zzbL().zzd(zzmo.zzGq)).booleanValue()) {
            intent.addFlags(DriveFile.MODE_READ_ONLY);
            intent.putExtra("android.support.customtabs.extra.user_opt_out", true);
        } else {
            if (((Boolean) zzbs.zzbL().zzd(zzmo.zzGp)).booleanValue()) {
                zzbs.zzbz();
                zzagz.zzc(context, intent);
            }
        }
        return zza(context, intent, zzag);
    }
}
