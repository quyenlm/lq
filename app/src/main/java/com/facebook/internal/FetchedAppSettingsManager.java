package com.facebook.internal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.appevents.internal.AutomaticAnalyticsLogger;
import com.facebook.appevents.internal.Constants;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.internal.FetchedAppSettings;
import com.facebook.share.internal.ShareConstants;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class FetchedAppSettingsManager {
    private static final String APPLICATION_FIELDS = "fields";
    private static final String APP_SETTINGS_PREFS_KEY_FORMAT = "com.facebook.internal.APP_SETTINGS.%s";
    private static final String APP_SETTINGS_PREFS_STORE = "com.facebook.internal.preferences.APP_SETTINGS";
    private static final String APP_SETTING_ANDROID_SDK_ERROR_CATEGORIES = "android_sdk_error_categories";
    private static final String APP_SETTING_APP_EVENTS_EVENT_BINDINGS = "auto_event_mapping_android";
    private static final String APP_SETTING_APP_EVENTS_FEATURE_BITMASK = "app_events_feature_bitmask";
    private static final String APP_SETTING_APP_EVENTS_SESSION_TIMEOUT = "app_events_session_timeout";
    private static final String APP_SETTING_CUSTOM_TABS_ENABLED = "gdpv4_chrome_custom_tabs_enabled";
    private static final String APP_SETTING_DIALOG_CONFIGS = "android_dialog_configs";
    private static final String[] APP_SETTING_FIELDS = {APP_SETTING_SUPPORTS_IMPLICIT_SDK_LOGGING, APP_SETTING_NUX_CONTENT, APP_SETTING_NUX_ENABLED, APP_SETTING_CUSTOM_TABS_ENABLED, APP_SETTING_DIALOG_CONFIGS, APP_SETTING_ANDROID_SDK_ERROR_CATEGORIES, APP_SETTING_APP_EVENTS_SESSION_TIMEOUT, APP_SETTING_APP_EVENTS_FEATURE_BITMASK, APP_SETTING_APP_EVENTS_EVENT_BINDINGS, APP_SETTING_SMART_LOGIN_OPTIONS, SMART_LOGIN_BOOKMARK_ICON_URL, SMART_LOGIN_MENU_ICON_URL};
    private static final String APP_SETTING_NUX_CONTENT = "gdpv4_nux_content";
    private static final String APP_SETTING_NUX_ENABLED = "gdpv4_nux_enabled";
    private static final String APP_SETTING_SMART_LOGIN_OPTIONS = "seamless_login";
    private static final String APP_SETTING_SUPPORTS_IMPLICIT_SDK_LOGGING = "supports_implicit_sdk_logging";
    private static final int AUTOMATIC_LOGGING_ENABLED_BITMASK_FIELD = 8;
    private static final int CODELESS_EVENTS_ENABLED_BITMASK_FIELD = 32;
    private static final int IAP_AUTOMATIC_LOGGING_ENABLED_BITMASK_FIELD = 16;
    private static final String SDK_UPDATE_MESSAGE = "sdk_update_message";
    private static final String SMART_LOGIN_BOOKMARK_ICON_URL = "smart_login_bookmark_icon_url";
    private static final String SMART_LOGIN_MENU_ICON_URL = "smart_login_menu_icon_url";
    /* access modifiers changed from: private */
    public static final String TAG = FetchedAppSettingsManager.class.getCanonicalName();
    private static Map<String, FetchedAppSettings> fetchedAppSettings = new ConcurrentHashMap();
    /* access modifiers changed from: private */
    public static AtomicBoolean loadingSettings = new AtomicBoolean(false);
    /* access modifiers changed from: private */
    public static boolean printedSDKUpdatedMessage = false;

    public static void loadAppSettingsAsync() {
        final Context context = FacebookSdk.getApplicationContext();
        final String applicationId = FacebookSdk.getApplicationId();
        boolean canStartLoading = loadingSettings.compareAndSet(false, true);
        if (!Utility.isNullOrEmpty(applicationId) && !fetchedAppSettings.containsKey(applicationId) && canStartLoading) {
            final String settingsKey = String.format(APP_SETTINGS_PREFS_KEY_FORMAT, new Object[]{applicationId});
            FacebookSdk.getExecutor().execute(new Runnable() {
                public void run() {
                    SharedPreferences sharedPrefs = context.getSharedPreferences(FetchedAppSettingsManager.APP_SETTINGS_PREFS_STORE, 0);
                    String settingsJSONString = sharedPrefs.getString(settingsKey, (String) null);
                    FetchedAppSettings appSettings = null;
                    if (!Utility.isNullOrEmpty(settingsJSONString)) {
                        JSONObject settingsJSON = null;
                        try {
                            settingsJSON = new JSONObject(settingsJSONString);
                        } catch (JSONException je) {
                            Utility.logd("FacebookSDK", (Exception) je);
                        }
                        if (settingsJSON != null) {
                            appSettings = FetchedAppSettingsManager.parseAppSettingsFromJSON(applicationId, settingsJSON);
                        }
                    }
                    JSONObject resultJSON = FetchedAppSettingsManager.getAppSettingsQueryResponse(applicationId);
                    if (resultJSON != null) {
                        FetchedAppSettings unused = FetchedAppSettingsManager.parseAppSettingsFromJSON(applicationId, resultJSON);
                        sharedPrefs.edit().putString(settingsKey, resultJSON.toString()).apply();
                    }
                    if (appSettings != null) {
                        String updateMessage = appSettings.getSdkUpdateMessage();
                        if (!FetchedAppSettingsManager.printedSDKUpdatedMessage && updateMessage != null && updateMessage.length() > 0) {
                            boolean unused2 = FetchedAppSettingsManager.printedSDKUpdatedMessage = true;
                            Log.w(FetchedAppSettingsManager.TAG, updateMessage);
                        }
                    }
                    AutomaticAnalyticsLogger.logActivateAppEvent();
                    FetchedAppSettingsManager.startInAppPurchaseAutoLogging(context);
                    FetchedAppSettingsManager.loadingSettings.set(false);
                }
            });
        }
    }

    public static FetchedAppSettings getAppSettingsWithoutQuery(String applicationId) {
        if (applicationId != null) {
            return fetchedAppSettings.get(applicationId);
        }
        return null;
    }

    public static FetchedAppSettings queryAppSettings(String applicationId, boolean forceRequery) {
        if (!forceRequery && fetchedAppSettings.containsKey(applicationId)) {
            return fetchedAppSettings.get(applicationId);
        }
        JSONObject response = getAppSettingsQueryResponse(applicationId);
        if (response == null) {
            return null;
        }
        return parseAppSettingsFromJSON(applicationId, response);
    }

    /* access modifiers changed from: private */
    public static FetchedAppSettings parseAppSettingsFromJSON(String applicationId, JSONObject settingsJSON) {
        FacebookRequestErrorClassification errorClassification;
        JSONArray errorClassificationJSON = settingsJSON.optJSONArray(APP_SETTING_ANDROID_SDK_ERROR_CATEGORIES);
        if (errorClassificationJSON == null) {
            errorClassification = FacebookRequestErrorClassification.getDefaultErrorClassification();
        } else {
            errorClassification = FacebookRequestErrorClassification.createFromJSON(errorClassificationJSON);
        }
        int featureBitmask = settingsJSON.optInt(APP_SETTING_APP_EVENTS_FEATURE_BITMASK, 0);
        FetchedAppSettings result = new FetchedAppSettings(settingsJSON.optBoolean(APP_SETTING_SUPPORTS_IMPLICIT_SDK_LOGGING, false), settingsJSON.optString(APP_SETTING_NUX_CONTENT, ""), settingsJSON.optBoolean(APP_SETTING_NUX_ENABLED, false), settingsJSON.optBoolean(APP_SETTING_CUSTOM_TABS_ENABLED, false), settingsJSON.optInt(APP_SETTING_APP_EVENTS_SESSION_TIMEOUT, Constants.getDefaultAppEventsSessionTimeoutInSeconds()), SmartLoginOption.parseOptions(settingsJSON.optLong(APP_SETTING_SMART_LOGIN_OPTIONS)), parseDialogConfigurations(settingsJSON.optJSONObject(APP_SETTING_DIALOG_CONFIGS)), (featureBitmask & 8) != 0, errorClassification, settingsJSON.optString(SMART_LOGIN_BOOKMARK_ICON_URL), settingsJSON.optString(SMART_LOGIN_MENU_ICON_URL), (featureBitmask & 16) != 0, (featureBitmask & 32) != 0, settingsJSON.optJSONArray(APP_SETTING_APP_EVENTS_EVENT_BINDINGS), settingsJSON.optString(SDK_UPDATE_MESSAGE));
        fetchedAppSettings.put(applicationId, result);
        return result;
    }

    /* access modifiers changed from: private */
    public static JSONObject getAppSettingsQueryResponse(String applicationId) {
        Bundle appSettingsParams = new Bundle();
        appSettingsParams.putString("fields", TextUtils.join(",", new ArrayList<>(Arrays.asList(APP_SETTING_FIELDS))));
        GraphRequest request = GraphRequest.newGraphPathRequest((AccessToken) null, applicationId, (GraphRequest.Callback) null);
        request.setSkipClientToken(true);
        request.setParameters(appSettingsParams);
        return request.executeAndWait().getJSONObject();
    }

    private static Map<String, Map<String, FetchedAppSettings.DialogFeatureConfig>> parseDialogConfigurations(JSONObject dialogConfigResponse) {
        JSONArray dialogConfigData;
        HashMap<String, Map<String, FetchedAppSettings.DialogFeatureConfig>> dialogConfigMap = new HashMap<>();
        if (!(dialogConfigResponse == null || (dialogConfigData = dialogConfigResponse.optJSONArray(ShareConstants.WEB_DIALOG_PARAM_DATA)) == null)) {
            for (int i = 0; i < dialogConfigData.length(); i++) {
                FetchedAppSettings.DialogFeatureConfig dialogConfig = FetchedAppSettings.DialogFeatureConfig.parseDialogConfig(dialogConfigData.optJSONObject(i));
                if (dialogConfig != null) {
                    String dialogName = dialogConfig.getDialogName();
                    Map<String, FetchedAppSettings.DialogFeatureConfig> featureMap = dialogConfigMap.get(dialogName);
                    if (featureMap == null) {
                        featureMap = new HashMap<>();
                        dialogConfigMap.put(dialogName, featureMap);
                    }
                    featureMap.put(dialogConfig.getFeatureName(), dialogConfig);
                }
            }
        }
        return dialogConfigMap;
    }

    /* access modifiers changed from: private */
    public static void startInAppPurchaseAutoLogging(final Context context) {
        CallbackManagerImpl.registerStaticCallback(CallbackManagerImpl.RequestCodeOffset.InAppPurchase.toRequestCode(), new CallbackManagerImpl.Callback() {
            public boolean onActivityResult(int resultCode, Intent data) {
                final int finalResultCode = resultCode;
                final Intent finalData = data;
                FacebookSdk.getExecutor().execute(new Runnable() {
                    public void run() {
                        AutomaticAnalyticsLogger.logInAppPurchaseEvent(context, finalResultCode, finalData);
                    }
                });
                return true;
            }
        });
    }
}
