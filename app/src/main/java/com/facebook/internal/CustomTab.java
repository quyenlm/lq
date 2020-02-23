package com.facebook.internal;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import com.appsflyer.share.Constants;
import com.facebook.FacebookSdk;

public class CustomTab {
    private Uri uri;

    public CustomTab(String action, Bundle parameters) {
        this.uri = Utility.buildUri(ServerProtocol.getDialogAuthority(), FacebookSdk.getGraphApiVersion() + Constants.URL_PATH_DELIMITER + ServerProtocol.DIALOG_PATH + action, parameters == null ? new Bundle() : parameters);
    }

    public void openCustomTab(Activity activity, String packageName) {
        CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder().build();
        customTabsIntent.intent.setPackage(packageName);
        customTabsIntent.intent.addFlags(1073741824);
        customTabsIntent.launchUrl(activity, this.uri);
    }
}
