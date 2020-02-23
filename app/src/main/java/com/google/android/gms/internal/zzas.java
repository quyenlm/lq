package com.google.android.gms.internal;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.http.AndroidHttpClient;
import android.os.Build;
import com.appsflyer.share.Constants;
import java.io.File;

public final class zzas {
    public static zzs zza(Context context, zzan zzan) {
        File file = new File(context.getCacheDir(), "volley");
        String str = "volley/0";
        try {
            String packageName = context.getPackageName();
            str = new StringBuilder(String.valueOf(packageName).length() + 12).append(packageName).append(Constants.URL_PATH_DELIMITER).append(context.getPackageManager().getPackageInfo(packageName, 0).versionCode).toString();
        } catch (PackageManager.NameNotFoundException e) {
        }
        zzs zzs = new zzs(new zzag(file), new zzad(Build.VERSION.SDK_INT >= 9 ? new zzao() : new zzak(AndroidHttpClient.newInstance(str))));
        zzs.start();
        return zzs;
    }
}
