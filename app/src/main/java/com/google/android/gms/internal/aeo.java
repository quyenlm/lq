package com.google.android.gms.internal;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.customtabs.CustomTabsService;
import android.text.TextUtils;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public final class aeo {
    private static String zzcuz;

    public static String zzbU(Context context) {
        if (zzcuz != null) {
            return zzcuz;
        }
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("http://www.example.com"));
        ResolveInfo resolveActivity = packageManager.resolveActivity(intent, 0);
        String str = resolveActivity != null ? resolveActivity.activityInfo.packageName : null;
        List<ResolveInfo> queryIntentActivities = packageManager.queryIntentActivities(intent, 0);
        ArrayList arrayList = new ArrayList();
        for (ResolveInfo next : queryIntentActivities) {
            Intent intent2 = new Intent();
            intent2.setAction(CustomTabsService.ACTION_CUSTOM_TABS_CONNECTION);
            intent2.setPackage(next.activityInfo.packageName);
            if (packageManager.resolveService(intent2, 0) != null) {
                arrayList.add(next.activityInfo.packageName);
            }
        }
        if (arrayList.isEmpty()) {
            zzcuz = null;
        } else if (arrayList.size() == 1) {
            zzcuz = (String) arrayList.get(0);
        } else if (!TextUtils.isEmpty(str) && !zzk(context, intent) && arrayList.contains(str)) {
            zzcuz = str;
        } else if (arrayList.contains("com.android.chrome")) {
            zzcuz = "com.android.chrome";
        } else if (arrayList.contains("com.chrome.beta")) {
            zzcuz = "com.chrome.beta";
        } else if (arrayList.contains("com.chrome.dev")) {
            zzcuz = "com.chrome.dev";
        } else if (arrayList.contains("com.google.android.apps.chrome")) {
            zzcuz = "com.google.android.apps.chrome";
        }
        return zzcuz;
    }

    private static boolean zzk(Context context, Intent intent) {
        try {
            List<ResolveInfo> queryIntentActivities = context.getPackageManager().queryIntentActivities(intent, 64);
            if (queryIntentActivities == null || queryIntentActivities.size() == 0) {
                return false;
            }
            for (ResolveInfo next : queryIntentActivities) {
                IntentFilter intentFilter = next.filter;
                if (intentFilter != null && intentFilter.countDataAuthorities() != 0 && intentFilter.countDataPaths() != 0 && next.activityInfo != null) {
                    return true;
                }
            }
            return false;
        } catch (RuntimeException e) {
            Log.e("CustomTabsHelper", "Runtime exception while getting specialized handlers");
        }
    }
}
