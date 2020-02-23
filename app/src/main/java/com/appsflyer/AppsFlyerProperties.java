package com.appsflyer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class AppsFlyerProperties {
    public static final String ADDITIONAL_CUSTOM_DATA = "additionalCustomData";
    public static final String AF_KEY = "AppsFlyerKey";
    public static final String AF_WAITFOR_CUSTOMERID = "waitForCustomerId";
    public static final String APP_ID = "appid";
    public static final String APP_USER_ID = "AppUserId";
    public static final String CHANNEL = "channel";
    public static final String COLLECT_ANDROID_ID = "collectAndroidId";
    public static final String COLLECT_ANDROID_ID_FORCE_BY_USER = "collectAndroidIdForceByUser";
    public static final String COLLECT_FACEBOOK_ATTR_ID = "collectFacebookAttrId";
    public static final String COLLECT_FINGER_PRINT = "collectFingerPrint";
    public static final String COLLECT_IMEI = "collectIMEI";
    public static final String COLLECT_IMEI_FORCE_BY_USER = "collectIMEIForceByUser";
    public static final String COLLECT_MAC = "collectMAC";
    public static final String CURRENCY_CODE = "currencyCode";
    public static final String DEVICE_TRACKING_DISABLED = "deviceTrackingDisabled";
    public static final String DISABLE_KEYSTORE = "keyPropDisableAFKeystore";
    public static final String DISABLE_LOGS_COMPLETELY = "disableLogs";
    public static final String DISABLE_OTHER_SDK = "disableOtherSdk";
    public static final String EMAIL_CRYPT_TYPE = "userEmailsCryptType";
    public static final String ENABLE_GPS_FALLBACK = "enableGpsFallback";
    public static final String EXTENSION = "sdkExtension";
    public static final String IS_MONITOR = "shouldMonitor";
    public static final String IS_UPDATE = "IS_UPDATE";
    public static final String LAUNCH_PROTECT_ENABLED = "launchProtectEnabled";
    public static final String ONELINK_DOMAIN = "onelinkDomain";
    public static final String ONELINK_ID = "oneLinkSlug";
    public static final String ONELINK_SCHEME = "onelinkScheme";
    public static final String USER_EMAIL = "userEmail";
    public static final String USER_EMAILS = "userEmails";
    public static final String USE_HTTP_FALLBACK = "useHttpFallback";

    /* renamed from: ˊ  reason: contains not printable characters */
    private static AppsFlyerProperties f46 = new AppsFlyerProperties();

    /* renamed from: ʽ  reason: contains not printable characters */
    private boolean f47 = false;

    /* renamed from: ˋ  reason: contains not printable characters */
    private Map<String, Object> f48 = new HashMap();

    /* renamed from: ˎ  reason: contains not printable characters */
    private boolean f49;

    /* renamed from: ˏ  reason: contains not printable characters */
    private String f50;

    /* renamed from: ॱ  reason: contains not printable characters */
    private boolean f51;

    public void remove(String str) {
        this.f48.remove(str);
    }

    public enum EmailsCryptType {
        NONE(0),
        SHA1(1),
        MD5(2),
        SHA256(3);
        

        /* renamed from: ˋ  reason: contains not printable characters */
        private final int f53;

        private EmailsCryptType(int i) {
            this.f53 = i;
        }

        public final int getValue() {
            return this.f53;
        }
    }

    private AppsFlyerProperties() {
    }

    public static AppsFlyerProperties getInstance() {
        return f46;
    }

    public void set(String str, String str2) {
        this.f48.put(str, str2);
    }

    public void set(String str, String[] strArr) {
        this.f48.put(str, strArr);
    }

    public void set(String str, int i) {
        this.f48.put(str, Integer.toString(i));
    }

    public void set(String str, long j) {
        this.f48.put(str, Long.toString(j));
    }

    public void set(String str, boolean z) {
        this.f48.put(str, Boolean.toString(z));
    }

    public void setCustomData(String str) {
        this.f48.put(ADDITIONAL_CUSTOM_DATA, str);
    }

    public void setUserEmails(String str) {
        this.f48.put(USER_EMAILS, str);
    }

    public String getString(String str) {
        return (String) this.f48.get(str);
    }

    public boolean getBoolean(String str, boolean z) {
        String string = getString(str);
        return string == null ? z : Boolean.valueOf(string).booleanValue();
    }

    public int getInt(String str, int i) {
        String string = getString(str);
        return string == null ? i : Integer.valueOf(string).intValue();
    }

    public long getLong(String str, long j) {
        String string = getString(str);
        return string == null ? j : Long.valueOf(string).longValue();
    }

    public Object getObject(String str) {
        return this.f48.get(str);
    }

    /* access modifiers changed from: protected */
    public boolean isOnReceiveCalled() {
        return this.f49;
    }

    /* access modifiers changed from: protected */
    public void setOnReceiveCalled() {
        this.f49 = true;
    }

    /* access modifiers changed from: protected */
    public boolean isFirstLaunchCalled() {
        return this.f51;
    }

    /* access modifiers changed from: protected */
    public void setFirstLaunchCalled(boolean z) {
        this.f51 = z;
    }

    /* access modifiers changed from: protected */
    public void setFirstLaunchCalled() {
        this.f51 = true;
    }

    /* access modifiers changed from: protected */
    public void setReferrer(String str) {
        set("AF_REFERRER", str);
        this.f50 = str;
    }

    public String getReferrer(Context context) {
        if (this.f50 != null) {
            return this.f50;
        }
        if (getString("AF_REFERRER") != null) {
            return getString("AF_REFERRER");
        }
        if (context != null) {
            return context.getSharedPreferences("appsflyer-data", 0).getString("referrer", (String) null);
        }
        return null;
    }

    public boolean isEnableLog() {
        return getBoolean("shouldLog", true);
    }

    public boolean isLogsDisabledCompletely() {
        return getBoolean(DISABLE_LOGS_COMPLETELY, false);
    }

    public boolean isOtherSdkStringDisabled() {
        return getBoolean(DISABLE_OTHER_SDK, false);
    }

    @SuppressLint({"CommitPrefEdits"})
    public void saveProperties(SharedPreferences sharedPreferences) {
        String jSONObject = new JSONObject(this.f48).toString();
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString("savedProperties", jSONObject);
        if (Build.VERSION.SDK_INT >= 9) {
            edit.apply();
        } else {
            edit.commit();
        }
    }

    public void loadProperties(Context context) {
        String string;
        if (!this.f47 && (string = context.getSharedPreferences("appsflyer-data", 0).getString("savedProperties", (String) null)) != null) {
            AFLogger.afDebugLog("Loading properties..");
            try {
                JSONObject jSONObject = new JSONObject(string);
                Iterator<String> keys = jSONObject.keys();
                while (keys.hasNext()) {
                    String next = keys.next();
                    if (this.f48.get(next) == null) {
                        this.f48.put(next, jSONObject.getString(next));
                    }
                }
                this.f47 = true;
            } catch (JSONException e) {
                AFLogger.afErrorLog("Failed loading properties", e);
            }
            AFLogger.afDebugLog(new StringBuilder("Done loading properties: ").append(this.f47).toString());
        }
    }
}
