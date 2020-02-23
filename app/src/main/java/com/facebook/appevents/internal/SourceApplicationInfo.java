package com.facebook.appevents.internal;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import bolts.AppLinks;
import com.facebook.FacebookSdk;
import com.tencent.tp.a.h;

class SourceApplicationInfo {
    private static final String CALL_APPLICATION_PACKAGE_KEY = "com.facebook.appevents.SourceApplicationInfo.callingApplicationPackage";
    private static final String OPENED_BY_APP_LINK_KEY = "com.facebook.appevents.SourceApplicationInfo.openedByApplink";
    private static final String SOURCE_APPLICATION_HAS_BEEN_SET_BY_THIS_INTENT = "_fbSourceApplicationHasBeenSet";
    private String callingApplicationPackage;
    private boolean openedByAppLink;

    private SourceApplicationInfo(String callingApplicationPackage2, boolean openedByAppLink2) {
        this.callingApplicationPackage = callingApplicationPackage2;
        this.openedByAppLink = openedByAppLink2;
    }

    public static SourceApplicationInfo getStoredSourceApplicatioInfo() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(FacebookSdk.getApplicationContext());
        if (!sharedPreferences.contains(CALL_APPLICATION_PACKAGE_KEY)) {
            return null;
        }
        return new SourceApplicationInfo(sharedPreferences.getString(CALL_APPLICATION_PACKAGE_KEY, (String) null), sharedPreferences.getBoolean(OPENED_BY_APP_LINK_KEY, false));
    }

    public static void clearSavedSourceApplicationInfoFromDisk() {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(FacebookSdk.getApplicationContext()).edit();
        editor.remove(CALL_APPLICATION_PACKAGE_KEY);
        editor.remove(OPENED_BY_APP_LINK_KEY);
        editor.apply();
    }

    public String getCallingApplicationPackage() {
        return this.callingApplicationPackage;
    }

    public boolean isOpenedByAppLink() {
        return this.openedByAppLink;
    }

    public String toString() {
        String openType = "Unclassified";
        if (this.openedByAppLink) {
            openType = "Applink";
        }
        if (this.callingApplicationPackage != null) {
            return openType + h.a + this.callingApplicationPackage + h.b;
        }
        return openType;
    }

    public void writeSourceApplicationInfoToDisk() {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(FacebookSdk.getApplicationContext()).edit();
        editor.putString(CALL_APPLICATION_PACKAGE_KEY, this.callingApplicationPackage);
        editor.putBoolean(OPENED_BY_APP_LINK_KEY, this.openedByAppLink);
        editor.apply();
    }

    public static class Factory {
        public static SourceApplicationInfo create(Activity activity) {
            boolean openedByAppLink = false;
            String callingApplicationPackage = "";
            ComponentName callingApplication = activity.getCallingActivity();
            if (callingApplication != null) {
                callingApplicationPackage = callingApplication.getPackageName();
                if (callingApplicationPackage.equals(activity.getPackageName())) {
                    return null;
                }
            }
            Intent openIntent = activity.getIntent();
            if (openIntent != null && !openIntent.getBooleanExtra(SourceApplicationInfo.SOURCE_APPLICATION_HAS_BEEN_SET_BY_THIS_INTENT, false)) {
                openIntent.putExtra(SourceApplicationInfo.SOURCE_APPLICATION_HAS_BEEN_SET_BY_THIS_INTENT, true);
                Bundle appLinkData = AppLinks.getAppLinkData(openIntent);
                if (appLinkData != null) {
                    openedByAppLink = true;
                    Bundle appLinkReferrerData = appLinkData.getBundle("referer_app_link");
                    if (appLinkReferrerData != null) {
                        callingApplicationPackage = appLinkReferrerData.getString("package");
                    }
                }
            }
            openIntent.putExtra(SourceApplicationInfo.SOURCE_APPLICATION_HAS_BEEN_SET_BY_THIS_INTENT, true);
            return new SourceApplicationInfo(callingApplicationPackage, openedByAppLink);
        }
    }
}
