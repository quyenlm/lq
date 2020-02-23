package com.facebook.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Looper;
import android.util.Log;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.FacebookSdkNotInitializedException;
import java.util.Collection;
import java.util.List;

public final class Validate {
    private static final String CONTENT_PROVIDER_BASE = "com.facebook.app.FacebookContentProvider";
    private static final String CONTENT_PROVIDER_NOT_FOUND_REASON = "A ContentProvider for this app was not set up in the AndroidManifest.xml, please add %s as a provider to your AndroidManifest.xml file. See https://developers.facebook.com/docs/sharing/android for more info.";
    private static final String CUSTOM_TAB_REDIRECT_ACTIVITY_NOT_FOUND_REASON = "FacebookActivity is declared incorrectly in the AndroidManifest.xml, please add com.facebook.FacebookActivity to your AndroidManifest.xml file. See https://developers.facebook.com/docs/android/getting-started for more info.";
    private static final String FACEBOOK_ACTIVITY_NOT_FOUND_REASON = "FacebookActivity is not declared in the AndroidManifest.xml, please add com.facebook.FacebookActivity to your AndroidManifest.xml file. See https://developers.facebook.com/docs/android/getting-started for more info.";
    private static final String NO_INTERNET_PERMISSION_REASON = "No internet permissions granted for the app, please add <uses-permission android:name=\"android.permission.INTERNET\" /> to your AndroidManifest.xml.";
    private static final String TAG = Validate.class.getName();

    public static void notNull(Object arg, String name) {
        if (arg == null) {
            throw new NullPointerException("Argument '" + name + "' cannot be null");
        }
    }

    public static <T> void notEmpty(Collection<T> container, String name) {
        if (container.isEmpty()) {
            throw new IllegalArgumentException("Container '" + name + "' cannot be empty");
        }
    }

    public static <T> void containsNoNulls(Collection<T> container, String name) {
        notNull(container, name);
        for (T item : container) {
            if (item == null) {
                throw new NullPointerException("Container '" + name + "' cannot contain null values");
            }
        }
    }

    public static void containsNoNullOrEmpty(Collection<String> container, String name) {
        notNull(container, name);
        for (String item : container) {
            if (item == null) {
                throw new NullPointerException("Container '" + name + "' cannot contain null values");
            } else if (item.length() == 0) {
                throw new IllegalArgumentException("Container '" + name + "' cannot contain empty values");
            }
        }
    }

    public static <T> void notEmptyAndContainsNoNulls(Collection<T> container, String name) {
        containsNoNulls(container, name);
        notEmpty(container, name);
    }

    public static void runningOnUiThread() {
        if (!Looper.getMainLooper().equals(Looper.myLooper())) {
            throw new FacebookException("This method should be called from the UI thread");
        }
    }

    public static void notNullOrEmpty(String arg, String name) {
        if (Utility.isNullOrEmpty(arg)) {
            throw new IllegalArgumentException("Argument '" + name + "' cannot be null or empty");
        }
    }

    public static void oneOf(Object arg, String name, Object... values) {
        for (Object value : values) {
            if (value != null) {
                if (value.equals(arg)) {
                    return;
                }
            } else if (arg == null) {
                return;
            }
        }
        throw new IllegalArgumentException("Argument '" + name + "' was not one of the allowed values");
    }

    public static void sdkInitialized() {
        if (!FacebookSdk.isInitialized()) {
            throw new FacebookSdkNotInitializedException("The SDK has not been initialized, make sure to call FacebookSdk.sdkInitialize() first.");
        }
    }

    public static String hasAppID() {
        String id = FacebookSdk.getApplicationId();
        if (id != null) {
            return id;
        }
        throw new IllegalStateException("No App ID found, please set the App ID.");
    }

    public static String hasClientToken() {
        String token = FacebookSdk.getClientToken();
        if (token != null) {
            return token;
        }
        throw new IllegalStateException("No Client Token found, please set the Client Token.");
    }

    public static void hasInternetPermissions(Context context) {
        hasInternetPermissions(context, true);
    }

    public static void hasInternetPermissions(Context context, boolean shouldThrow) {
        notNull(context, "context");
        if (context.checkCallingOrSelfPermission("android.permission.INTERNET") != -1) {
            return;
        }
        if (shouldThrow) {
            throw new IllegalStateException(NO_INTERNET_PERMISSION_REASON);
        }
        Log.w(TAG, NO_INTERNET_PERMISSION_REASON);
    }

    public static boolean hasWiFiPermission(Context context) {
        return hasPermission(context, "android.permission.ACCESS_WIFI_STATE");
    }

    public static boolean hasChangeWifiStatePermission(Context context) {
        return hasPermission(context, "android.permission.CHANGE_WIFI_STATE");
    }

    public static boolean hasLocationPermission(Context context) {
        return hasPermission(context, "android.permission.ACCESS_COARSE_LOCATION") || hasPermission(context, "android.permission.ACCESS_FINE_LOCATION");
    }

    public static boolean hasBluetoothPermission(Context context) {
        return hasPermission(context, "android.permission.BLUETOOTH") && hasPermission(context, "android.permission.BLUETOOTH_ADMIN");
    }

    public static boolean hasPermission(Context context, String permission) {
        return context.checkCallingOrSelfPermission(permission) == 0;
    }

    public static void hasFacebookActivity(Context context) {
        hasFacebookActivity(context, true);
    }

    public static void hasFacebookActivity(Context context, boolean shouldThrow) {
        notNull(context, "context");
        PackageManager pm = context.getPackageManager();
        ActivityInfo activityInfo = null;
        if (pm != null) {
            try {
                activityInfo = pm.getActivityInfo(new ComponentName(context, "com.facebook.FacebookActivity"), 1);
            } catch (PackageManager.NameNotFoundException e) {
            }
        }
        if (activityInfo != null) {
            return;
        }
        if (shouldThrow) {
            throw new IllegalStateException(FACEBOOK_ACTIVITY_NOT_FOUND_REASON);
        }
        Log.w(TAG, FACEBOOK_ACTIVITY_NOT_FOUND_REASON);
    }

    public static void checkCustomTabRedirectActivity(Context context) {
        checkCustomTabRedirectActivity(context, true);
    }

    public static void checkCustomTabRedirectActivity(Context context, boolean shouldThrow) {
        if (hasCustomTabRedirectActivity(context)) {
            return;
        }
        if (shouldThrow) {
            throw new IllegalStateException(CUSTOM_TAB_REDIRECT_ACTIVITY_NOT_FOUND_REASON);
        }
        Log.w(TAG, CUSTOM_TAB_REDIRECT_ACTIVITY_NOT_FOUND_REASON);
    }

    public static boolean hasCustomTabRedirectActivity(Context context) {
        notNull(context, "context");
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> infos = null;
        if (pm != null) {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.addCategory("android.intent.category.DEFAULT");
            intent.addCategory("android.intent.category.BROWSABLE");
            intent.setData(Uri.parse("fb" + FacebookSdk.getApplicationId() + "://authorize"));
            infos = pm.queryIntentActivities(intent, 64);
        }
        boolean hasActivity = false;
        if (infos != null) {
            for (ResolveInfo info : infos) {
                ActivityInfo activityInfo = info.activityInfo;
                if (!activityInfo.name.equals("com.facebook.CustomTabActivity") || !activityInfo.packageName.equals(context.getPackageName())) {
                    boolean z = hasActivity;
                    return false;
                }
                hasActivity = true;
            }
        }
        return hasActivity;
    }

    public static void hasContentProvider(Context context) {
        notNull(context, "context");
        String appId = hasAppID();
        PackageManager pm = context.getPackageManager();
        if (pm != null) {
            String providerName = CONTENT_PROVIDER_BASE + appId;
            if (pm.resolveContentProvider(providerName, 0) == null) {
                throw new IllegalStateException(String.format(CONTENT_PROVIDER_NOT_FOUND_REASON, new Object[]{providerName}));
            }
        }
    }
}
