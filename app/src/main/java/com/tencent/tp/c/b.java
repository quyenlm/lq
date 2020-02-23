package com.tencent.tp.c;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

public class b {
    public void a(Context context, String str) throws PackageManager.NameNotFoundException {
        Intent launchIntentForPackage;
        PackageManager packageManager = context.getPackageManager();
        if (packageManager != null && (launchIntentForPackage = packageManager.getLaunchIntentForPackage(str)) != null) {
            context.startActivity(launchIntentForPackage);
        }
    }
}
